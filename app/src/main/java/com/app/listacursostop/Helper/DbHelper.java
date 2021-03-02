package com.app.listacursostop.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    //criação de constantes para passar no construtor
    public static double VERSION = 1.1;

    public static String NOME_DB = "db_cursos";

    //colocando o nome da tabela numa constante;
    public static String TABELA_CURSOS = "cursos";

    public DbHelper(Context context) {
        super(context, NOME_DB, null, (int) VERSION);
    }



    @Override
        public void onCreate(SQLiteDatabase db) {
            String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_CURSOS
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " nomeCurso TEXT NOT NULL, anoLancamento TEXT NOT NULL," +
                    "categoriaCurso TEXT NOT NULL);";

            try {
                db.execSQL(sql);
                Log.i("Info DB", "Sucesso Ao Criar tabela");

            }catch (Exception e){
                Log.i("Info DB", "Erro ao criar tabela" + e.getMessage());
            }
        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABELA_CURSOS + ";";

        try {
            db.execSQL(sql);
            onCreate(db);
            Log.i("Info DB", "Sucesso Ao atualizar app");

        }catch (Exception e){
            Log.i("Info DB", "Erro ao criar tabela" + e.getMessage());
        }
    }
}
