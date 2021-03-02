package com.app.listacursostop.DAO;

import com.app.listacursostop.Model.Curso;

import java.util.List;

public interface iCursosDAO {

    public boolean salvar(Curso curso);
    public List<Curso> listar();
    public boolean deletar(Curso curso);
    public boolean atualizar(Curso curso);
}
