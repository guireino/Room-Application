package br.com.alura.roomapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import br.com.alura.roomapplication.R;
import br.com.alura.roomapplication.models.alunosDelegate;
import br.com.alura.roomapplication.database.conversor.daos.AlunoDao;
import br.com.alura.roomapplication.database.Gerador_BD;
import br.com.alura.roomapplication.models.Aluno;

public class FormularioAlunosFragment extends Fragment{

    private Aluno aluno = new Aluno();

    private EditText campoNome;
    private EditText campoEmail;
    private alunosDelegate delegate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        delegate = (alunosDelegate) getActivity();
        delegate.alteraNomeDaActivity("Cadastro de Aluno");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_formulario_alunos, container, false);

        configuraComponentesDa(view);
        colocaAlunoNaTelaSeNecessario();

        return view;
    }

    private void colocaAlunoNaTelaSeNecessario() {
        Bundle argumentos = getArguments();

        if (argumentos != null){
            this.aluno = (Aluno) argumentos.getSerializable("aluno");

            campoNome.setText(aluno.getName());
            campoEmail.setText(aluno.getEmail());
        }
    }

    private void configuraComponentesDa(View view) {
        this.campoEmail = view.findViewById(R.id.formulario_alunos_email);
        this.campoNome = view.findViewById(R.id.formulario_alunos_nome);

        Button cadastrar = view.findViewById(R.id.formulario_alunos_cadastrar);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atualizaInformacoesDoAluno();
                persisteAluno();

                delegate.voltaParaTelaAnterior();
            }
        });
    }

    private void persisteAluno() {
        Gerador_BD gerador = new Gerador_BD();
        AlunoDao alunoDao = gerador.gera(getContext()).getAlunoDao();

        if(ehAlunoNovo()){
            alunoDao.insere(aluno);
        }else{
            alunoDao.altera(aluno);
        }
    }

    private boolean ehAlunoNovo() {
        return aluno.getId() == null;
    }

    private void atualizaInformacoesDoAluno() {
        aluno.setName(campoNome.getText().toString());
        aluno.setEmail(campoEmail.getText().toString());
    }

}