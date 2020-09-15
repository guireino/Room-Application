package br.com.alura.roomapplication.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.Calendar;
import java.util.List;

import br.com.alura.roomapplication.R;
import br.com.alura.roomapplication.database.AlunoDatabase;
import br.com.alura.roomapplication.database.Gerador_BD;
import br.com.alura.roomapplication.database.conversor.Conversor_data;
import br.com.alura.roomapplication.database.conversor.daos.ProvaDao;
import br.com.alura.roomapplication.models.Prova;
import br.com.alura.roomapplication.models.provasDelegate;

public class ListaProvasFragment extends Fragment {

    private provasDelegate delegate;

    private ListView listagem;
    private FloatingActionButton cadastrar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate = (provasDelegate) getActivity();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_lista_provas, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if (item.getItemId() == R.id.menu_lista_prova){

            final Context contexto = getContext();

            LinearLayout linearLayout = new LinearLayout(contexto);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            final EditText campoInicio = new EditText(contexto);
            campoInicio.setHint("Inicio");
            campoInicio.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);

            final EditText campoFim = new EditText(contexto);
            campoFim.setHint("Fim");
            campoFim.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);

            linearLayout.addView(campoInicio);
            linearLayout.addView(campoFim);

             new AlertDialog.Builder(getContext()).setView(linearLayout).setMessage("Digite as datas para busca")
                     .setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int i) {

                             String dataInicioString = campoInicio.getText().toString();
                             String dataFimString = campoFim.getText().toString();

                             Calendar dataInicio = Conversor_data.calendar_string(dataInicioString);
                             Calendar dataFim = Conversor_data.calendar_string(dataFimString);

                             Gerador_BD gerador_bd = new Gerador_BD();
                             AlunoDatabase database = gerador_bd.gera(contexto);
                             ProvaDao provaDao = database.getProvaDao();

                             List<Prova> provas = provaDao.buscaPeloPeriodo(dataInicio, dataFim);

                             configuraAdapter(contexto, provas);
                         }
                     }).setNegativeButton("Cancelar", null).show();
        }

        return true;
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista, container, false);

        configuraCampos(view);

        return view;
    }

    private void configuraCampos(View view) {

        configuraListagem(view);

        cadastrar = view.findViewById(R.id.fragment_lista_fab);
        listenerPara(cadastrar);
    }

    private void configuraListagem(View view) {
        listagem = view.findViewById(R.id.fragment_lista);

        Context contexto = getContext();

        Gerador_BD gerador_bd = new Gerador_BD();
        AlunoDatabase database = gerador_bd.gera(contexto);
        final ProvaDao provaDao = database.getProvaDao();

        List<Prova> provas = provaDao.busca();

        final ArrayAdapter<Prova> adapter = configuraAdapter(contexto, provas);

        listagem.setOnItemClickListener(clickNaLista());
    }

    @NonNull
    private ArrayAdapter<Prova> configuraAdapter(Context contexto, List<Prova> provas) {
        final ArrayAdapter<Prova> adapter = new ArrayAdapter<>(contexto, android.R.layout.simple_list_item_1, provas);
        listagem.setAdapter(adapter);
        return adapter;
    }

    private AdapterView.OnItemClickListener clickNaLista() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Prova prova = (Prova) adapterView.getItemAtPosition(posicao);
                delegate.lidaCom(prova);
            }
        };
    }

    private void listenerPara(FloatingActionButton botaoFlutuante) {
        botaoFlutuante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delegate.lidaComClickDoFAB();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        delegate.alteraNomeActionBar("Provas realizadas");
    }

}