package br.com.alura.roomapplication.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import br.com.alura.roomapplication.database.conversor.Conversor_data;
import br.com.alura.roomapplication.database.conversor.daos.AlunoDao;
import br.com.alura.roomapplication.database.conversor.daos.ProvaDao;
import br.com.alura.roomapplication.models.Aluno;
import br.com.alura.roomapplication.models.Prova;

@Database(entities = {Aluno.class, Prova.class}, version = 3)
@TypeConverters(Conversor_data.class)
public abstract class AlunoDatabase extends RoomDatabase {

    public abstract AlunoDao getAlunoDao();

    public abstract ProvaDao getProvaDao();
}
