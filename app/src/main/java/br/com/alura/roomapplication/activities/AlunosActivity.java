package br.com.alura.roomapplication.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;

import br.com.alura.roomapplication.R;
import br.com.alura.roomapplication.models.alunosDelegate;
import br.com.alura.roomapplication.fragments.FormularioAlunosFragment;
import br.com.alura.roomapplication.fragments.ListaAlunosFragment;
import br.com.alura.roomapplication.models.Aluno;

public class AlunosActivity extends AppCompatActivity implements alunosDelegate{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alunos);

        exibe(new ListaAlunosFragment(), false);
    }

    public void exibe(Fragment fragment, boolean empilhado){

        FragmentManager gerenciadorDeFragment = getSupportFragmentManager();

        FragmentTransaction transacao = gerenciadorDeFragment.beginTransaction();

        transacao.replace(R.id.alunos_frame, fragment);

        if (empilhado){
            transacao.addToBackStack(null);
        }
        transacao.commit();
    }

    @Override
    public void lidaComClickFAB() {
        exibe(new FormularioAlunosFragment(), true);
    }

    @Override
    public void voltaParaTelaAnterior() {
        onBackPressed();
    }

    @Override
    public void alteraNomeDaActivity(String nome) {
        setTitle(nome);
    }

    @Override
    public void lidaComAlunoSelecionado(Aluno aluno) {
        exibe(formularioCom(aluno), true);
    }

    @NonNull
    private FormularioAlunosFragment formularioCom(Aluno aluno){
        FormularioAlunosFragment formulario = new FormularioAlunosFragment();

        Bundle argumentos = new Bundle();

        argumentos.putSerializable("aluno", aluno);

        formulario.setArguments(argumentos);

        return formulario;
    }
}