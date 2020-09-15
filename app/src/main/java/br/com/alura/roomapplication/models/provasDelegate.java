package br.com.alura.roomapplication.models;

import android.support.annotation.NonNull;

public interface provasDelegate {

    void alteraNomeActionBar(@NonNull String nomeASerExibido);

    void lidaComClickDoFAB();

    void retornaParaTelaAnterior();

    void lidaCom(Prova prova);
    
}
