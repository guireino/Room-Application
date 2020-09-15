package br.com.alura.roomapplication.fragments;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.com.alura.roomapplication.R;
import br.com.alura.roomapplication.models.alunosDelegate;
import br.com.alura.roomapplication.database.conversor.daos.AlunoDao;
import br.com.alura.roomapplication.database.AlunoDatabase;
import br.com.alura.roomapplication.database.Gerador_BD;
import br.com.alura.roomapplication.models.Aluno;

public class ListaAlunosFragment extends Fragment {

    private alunosDelegate delegate;
    private FloatingActionButton botaoAdd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        delegate = (alunosDelegate) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista, container, false);

        configuraComponentesDa(view);

        return view;
    }

    private void configuraComponentesDa(View view) {
        configuraLista(view);
        configuraFAB(view);
    }

    private void configuraFAB(View view) {
        botaoAdd = view.findViewById(R.id.fragment_lista_fab);

        botaoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delegate.lidaComClickFAB();
            }
        });
    }

    private void configuraLista(View view) {
        final ListView lista = view.findViewById(R.id.fragment_lista);

        final Gerador_BD gerador_bd = new Gerador_BD();
        AlunoDatabase database = gerador_bd.gera(getContext());
        final AlunoDao alunoDao = database.getAlunoDao();

        List<Aluno> alunos = alunoDao.busca();
        final ArrayAdapter<Aluno> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, alunos);

        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Aluno aluno = (Aluno) lista.getItemAtPosition(posicao);
                delegate.lidaComAlunoSelecionado(aluno);
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int posicao, long id) {

                final Aluno aluno = (Aluno) lista.getItemAtPosition(posicao);

                String mensagem = "Excluir aluno " + aluno.getName() + " ?";
                Snackbar.make(botaoAdd, mensagem,Snackbar.LENGTH_LONG).setAction("Sim", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alunoDao.deleta(aluno);
                                adapter.remove(aluno);
                            }

                        }).show();

                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        delegate.alteraNomeDaActivity("Lista de Alunos");
    }
}