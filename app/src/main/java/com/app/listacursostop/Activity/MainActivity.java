package com.app.listacursostop.Activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.app.listacursostop.Adapter.CursoAdapter;
import com.app.listacursostop.DAO.CursosDAO;
import com.app.listacursostop.Helper.DbHelper;
import com.app.listacursostop.Helper.RecyclerItemClickListener;
import com.app.listacursostop.Model.Curso;
import com.app.listacursostop.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerCurso;
    private CursoAdapter cursoAdapter;
    private List<Curso> cursoList = new ArrayList<>();
    private Curso cursoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerCurso = findViewById(R.id.recyclerCurso);

        //Instanceando a classe DbHelper
        DbHelper db = new DbHelper(getApplicationContext());
        //estrutura para salvar as informações
        ContentValues cv = new ContentValues();
        db.getWritableDatabase().insert("cursos",
                null, cv);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AdicionarCursoActivity.class);
                startActivity(intent);

            }
        });
    }

    public void CarregarListaDeCursos(){
        //lista cursos

        CursosDAO cursosDAO = new CursosDAO( getApplicationContext());
        cursoList = cursosDAO.listar();

        /*
        Exibir a lista de tarefas
         */

        //configurar Adapter

        cursoAdapter = new CursoAdapter(cursoList);



        //Configurar RecyclerView
        RecyclerView.LayoutManager layout = new LinearLayoutManager(getApplicationContext());
        recyclerCurso.setLayoutManager(layout);
        recyclerCurso.setHasFixedSize(true);
        recyclerCurso.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerCurso.setAdapter(cursoAdapter);

        //evento de click na lista
        recyclerCurso.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(),
                recyclerCurso, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //pegando a tarefa clicada
                Curso cursoSelecionado = cursoList.get(position);

                //enviando para tela  adicionar tarefa
                Intent intent = new Intent(MainActivity.this, AdicionarCursoActivity.class);
                intent.putExtra("cursoSelecionado", cursoSelecionado);

                startActivity(intent);

            }

            @Override
            public void onLongItemClick(View view, int position) {

                    cursoSelecionado = cursoList.get(position);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Confirmar Exclusão do Curso");
                alertDialog.setMessage("Você deseja Excluir o curso: " + cursoSelecionado.getNomeCurso() + " ?");

                alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        CursosDAO cursoDelete = new CursosDAO(getApplicationContext());
                        if(cursoDelete.deletar(cursoSelecionado)){
                            CarregarListaDeCursos();
                            Toast.makeText(getApplicationContext(),
                                    "Sucesso ao Excluir o Curso",
                                    Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "Erro ao Excluir o Curso",
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                });

                alertDialog.setNegativeButton("Não", null);

                alertDialog.show();
                alertDialog.create();
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));

    }

    @Override
    protected void onStart() {
        CarregarListaDeCursos();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
