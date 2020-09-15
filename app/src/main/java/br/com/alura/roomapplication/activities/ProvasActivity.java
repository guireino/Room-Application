package br.com.alura.roomapplication.activities;

import android.arch.persistence.room.Database;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import br.com.alura.roomapplication.R;
import br.com.alura.roomapplication.fragments.FormularioProvasFragment;
import br.com.alura.roomapplication.fragments.ListaAlunosFragment;
import br.com.alura.roomapplication.fragments.ListaProvasFragment;
import br.com.alura.roomapplication.models.Prova;
import br.com.alura.roomapplication.models.provasDelegate;


public class ProvasActivity extends AppCompatActivity implements provasDelegate {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        exibe(new ListaProvasFragment(), false);
    }


    public void exibe(Fragment fragment, boolean empilhado) {
        FragmentManager gerenciadorDeFragment = getSupportFragmentManager();

        FragmentTransaction transacao = gerenciadorDeFragment.beginTransaction();

        transacao.replace(R.id.provas_frame, fragment);

        if (empilhado) {
            transacao.addToBackStack(null);
        }
        transacao.commit();
    }

    @Override
    public void lidaComClickDoFAB() {
        exibe(new FormularioProvasFragment(), true);
    }

    @Override
    public void retornaParaTelaAnterior() {

        onBackPressed();
    }

    @Override
    public void lidaCom(Prova provaSelecionada) {
        exibe(formularioCom(provaSelecionada), true);
    }

    private FormularioProvasFragment formularioCom(Prova prova) {

        Bundle argumentos = new Bundle();
        argumentos.putSerializable(FormularioProvasFragment.PROVA, prova);

        FormularioProvasFragment formulario = new FormularioProvasFragment();
        formulario.setArguments(argumentos);

        return formulario;
    }

    @Override
    public void alteraNomeActionBar(@NonNull String nomeASerExibido) {
        setTitle(nomeASerExibido);
    }
}