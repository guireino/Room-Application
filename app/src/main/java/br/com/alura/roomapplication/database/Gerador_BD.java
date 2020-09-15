package br.com.alura.roomapplication.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

public class Gerador_BD {

    public AlunoDatabase gera(Context contexto){
        AlunoDatabase aluraDB = Room.databaseBuilder(contexto, AlunoDatabase.class, "AluraDB").allowMainThreadQueries()
                .addMigrations(trocaVercao()).build();
        return aluraDB;
    }

    private Migration trocaVercao(){
        return new Migration(2,3) {
            @Override
            public void migrate(@NonNull SupportSQLiteDatabase database) {

                String sql = "alter table Prova add column calendarData integer;";
                database.execSQL(sql);
            }
        };
    }


}