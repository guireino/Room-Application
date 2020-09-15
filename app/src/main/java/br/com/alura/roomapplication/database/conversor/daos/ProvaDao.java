package br.com.alura.roomapplication.database.conversor.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Calendar;
import java.util.List;

import br.com.alura.roomapplication.models.Prova;

@Dao
public interface ProvaDao {

    @Insert
    void insere(Prova prova);

    @Query("select * from Prova")
    List<Prova> busca();

    @Query("select * from Prova where data between :inicio and :fim")
    List<Prova> buscaPeloPeriodo(Calendar inicio, Calendar fim);

    @Update
    void altera(Prova prova);

    @Delete
    void exclui(Prova prova);

}
