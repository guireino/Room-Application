package br.com.alura.roomapplication.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import br.com.alura.roomapplication.R;
import br.com.alura.roomapplication.database.conversor.Conversor_data;
import br.com.alura.roomapplication.models.provasDelegate;
import br.com.alura.roomapplication.database.Gerador_BD;
import br.com.alura.roomapplication.database.conversor.daos.ProvaDao;
import br.com.alura.roomapplication.models.Prova;

public class FormularioProvasFragment extends Fragment {

    private Prova prova = new Prova();

    public static final String PROVA = "prova";

    private EditText materia;
    private EditText data;
    private provasDelegate delegate;
    private Button cadastrar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate = (provasDelegate) getActivity();
        delegate.alteraNomeActionBar("Adicionar prova");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_formulario_provas, container, false);

        buscaCamposDa(view);

        populaCamposSeNecessario();

        listenerParaBotaoCadastrar();

        return view;
    }

    private void listenerParaBotaoCadastrar() {

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atualizaInformacoesDaProva();

                persisteProva();

                delegate.retornaParaTelaAnterior();
            }
        });
    }

    private void persisteProva() {

        Gerador_BD gerador_bd = new Gerador_BD();

        ProvaDao provaDao = gerador_bd.gera(getContext()).getProvaDao();

        if (ehProvaNovo()) {
            provaDao.insere(prova);
        }
    }

    private boolean ehProvaNovo() {
        return prova.getId() == null;
    }

    private void atualizaInformacoesDaProva() {
        prova.setMateria(materia.getText().toString());
        prova.setData(Conversor_data.calendar_string(data.getText().toString()));
    }

    private void populaCamposSeNecessario() {
        Bundle argumentos = getArguments();
        if (argumentos != null) {
            this.prova = (Prova) argumentos.get(PROVA);

            data.setText(Conversor_data.converteData(prova.getData()));
            materia.setText(prova.getMateria());
        }
    }

    private void buscaCamposDa(View view) {
        materia = view.findViewById(R.id.formulario_prova_materia);
        cadastrar = view.findViewById(R.id.formulario_prova_cadastrar);
        data = view.findViewById(R.id.formulario_prova_data);

    }

}