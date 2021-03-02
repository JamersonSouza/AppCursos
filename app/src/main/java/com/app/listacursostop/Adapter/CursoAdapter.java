package com.app.listacursostop.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.listacursostop.Model.Curso;
import com.app.listacursostop.R;

import java.util.List;

public class CursoAdapter extends RecyclerView.Adapter<CursoAdapter.MyViewHolder> {

    private List<Curso> listaCurso;

    //construtor da ClasseCurso Adapter
    //configurar o adapter sempre para receber uma LISTA
    public CursoAdapter(List<Curso> lista){
        listaCurso = lista;
    }

    //metodos implementados
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_curso, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Curso cursos = listaCurso.get(position);
        holder.nomeCurso.setText(cursos.getNomeCurso());
        holder.anoLancamento.setText(cursos.getAnoLancamento());
        holder.categoriaCurso.setText(cursos.getCategoriaCurso());

    }

    @Override
    public int getItemCount() {
        return listaCurso.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nomeCurso;
        TextView anoLancamento;
        TextView categoriaCurso;

        //construtor
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeCurso =  itemView.findViewById(R.id.nomeCurso);
            anoLancamento = itemView.findViewById(R.id.anoLancamento);
            categoriaCurso = itemView.findViewById(R.id.categoriaCurso);
        }


    }







}
