package br.com.alura.roomapplication.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.alura.roomapplication.R;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button cadastroAlunos = findViewById(R.id.dash_btn_aluno);
        Button cadastroProva = findViewById(R.id.dash_btn_provas);

        cadastroAlunos.setOnClickListener(vaiPara(AlunosActivity.class));
        cadastroProva.setOnClickListener(vaiPara(ProvasActivity.class));
    }

    @NonNull
    private View.OnClickListener vaiPara(final Class<?> clazz) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencao = new Intent(DashboardActivity.this, clazz);
                startActivity(intencao);
            }
        };
    }
}