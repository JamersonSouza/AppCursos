package com.app.listacursostop.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.app.listacursostop.Helper.DbHelper;
import com.app.listacursostop.Model.Curso;

import java.util.ArrayList;
import java.util.List;

public class CursosDAO implements iCursosDAO{

    private SQLiteDatabase escreve;
    private SQLiteDatabase ler;

    public CursosDAO(Context context) {

        DbHelper db = new DbHelper(context);
        escreve = db.getWritableDatabase();
        ler = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Curso curso) {


        ContentValues cv = new ContentValues();
        cv.put("nomeCurso", curso.getNomeCurso());
        cv.put("anoLancamento", curso.getAnoLancamento());
        cv.put("categoriaCurso", curso.getCategoriaCurso());

        try {

            escreve.insert(DbHelper.TABELA_CURSOS,
                    null,
                   cv);
            Log.i("Info db", "Cursos salvo com sucesso");

        }catch (Exception e){
            Log.i("Info db", "Erro ao salvar os dados" + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Curso> listar() {
      List<Curso> curso = new ArrayList<>();
      String sql = "SELECT * FROM " + DbHelper.TABELA_CURSOS + ";";
        Cursor c = ler.rawQuery(sql, null);

        while(c.moveToNext()){
            Curso cursos = new Curso();

            Long id = c.getLong(c.getColumnIndex("id"));
            String nomeCurso = c.getString(c.getColumnIndex("nomeCurso"));
            String anoLancamento = c.getString(c.getColumnIndex("anoLancamento"));
            String categoriaCurso = c.getString(c.getColumnIndex("categoriaCurso"));

            cursos.setId(id);
            cursos.setNomeCurso(nomeCurso);
            cursos.setAnoLancamento(anoLancamento);
            cursos.setCategoriaCurso(categoriaCurso);

            curso.add(cursos);
        }
      return curso;
    }

    @Override
    public boolean deletar(Curso curso) {

        try {
            String[] args = {curso.getId().toString()};
            escreve.delete(DbHelper.TABELA_CURSOS, "id=?", args);
            Log.i("Info db", "Cursos deletados com sucesso");

        }catch (Exception e){
            Log.i("Info db", "Erro ao deletar os dados" + e.getMessage());
            return false;
        }
        return true;
    }



    @Override
    public boolean atualizar(Curso curso) {

        ContentValues cv = new ContentValues();
        cv.put("nomeCurso", curso.getNomeCurso());
        cv.put("anoLancamento", curso.getAnoLancamento());
        cv.put("categoriaCurso", curso.getCategoriaCurso());

        try {
            String[] args = {curso.getId().toString()};
            escreve.update(DbHelper.TABELA_CURSOS, cv, "id=?", args);
            Log.i("Info db", "Cursos salvo com sucesso");

        }catch (Exception e){
            Log.i("Info db", "Erro ao salvar os dados" + e.getMessage());
            return false;
        }
        return true;
    }
}
