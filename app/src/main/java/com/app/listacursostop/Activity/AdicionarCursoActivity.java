package com.app.listacursostop.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.Toast;

import com.app.listacursostop.DAO.CursosDAO;
import com.app.listacursostop.Model.Curso;
import com.app.listacursostop.R;
import com.google.android.material.textfield.TextInputEditText;

public class AdicionarCursoActivity extends AppCompatActivity {

    private TextInputEditText textNomeCurso;
    private TextInputEditText textAnoLancamento;
    private CheckBox checkProgramacao, checkDzn, checkIdiomas, checkAdm;
    private Curso cursoAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_curso);

        textNomeCurso = findViewById(R.id.textNomeCurso);
        textAnoLancamento = findViewById(R.id.textAnoLancamento);
        checkAdm = findViewById(R.id.checkAdm);
        checkDzn = findViewById(R.id.checkDzn);
        checkIdiomas = findViewById(R.id.checkIdiomas);
        checkProgramacao = findViewById(R.id.checkProgramacao);

        //recuperando o curso, caso seja edição
        cursoAtual = (Curso) getIntent().getSerializableExtra("cursoSelecionado");

        //configurar a caixa de texto
        if(cursoAtual != null){
            textNomeCurso.setText(cursoAtual.getNomeCurso());
            textAnoLancamento.setText(cursoAtual.getAnoLancamento());
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_curso, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.salvar:
                //executar algum código..
                CursosDAO cursosDAO = new CursosDAO(getApplicationContext());
                // antes de salvar tem que instancear o objeto
                //pegando os valores dos campos da tela
                String nomeDoCurso = textNomeCurso.getText().toString();
                String anoLancamentoCurso = textAnoLancamento.getText().toString();
                String cursoEscolhido = "";
                if(checkProgramacao.isChecked()){
                    cursoEscolhido = checkProgramacao.getText().toString();
                }else if(checkIdiomas.isChecked()){
                    cursoEscolhido = checkIdiomas.getText().toString();
                }else if(checkDzn.isChecked()){
                    cursoEscolhido = checkDzn.getText().toString();
                }else if(checkAdm.isChecked()){
                    cursoEscolhido = checkAdm.getText().toString();
                }

                Curso cursos = new Curso();

                if(cursoAtual != null){//edição

                    if(!nomeDoCurso.isEmpty() && !anoLancamentoCurso.isEmpty()){
                        Curso curso = new Curso();
                        curso.setNomeCurso(nomeDoCurso);
                        curso.setId(cursoAtual.getId());
                        curso.setCategoriaCurso(cursoEscolhido);
                        curso.setAnoLancamento(anoLancamentoCurso);

                        //salvando
                        if(cursosDAO.atualizar(curso)){
                            finish();
                            Toast.makeText(getApplicationContext(),
                                    "Sucesso Ao Atualizar o Curso",
                                    Toast.LENGTH_LONG).show();

                        }else{

                            Toast.makeText(getApplicationContext(),
                                    "Erro ao Atualizar o Curso",
                                    Toast.LENGTH_LONG).show();

                        }
                    }

                }else{

                    if(!nomeDoCurso.isEmpty() && !anoLancamentoCurso.isEmpty()){
                        cursos.setNomeCurso(nomeDoCurso);
                        cursos.setAnoLancamento(anoLancamentoCurso);
                        cursos.setCategoriaCurso(cursoEscolhido);
                        if(cursosDAO.salvar(cursos)){
                            finish();
                            Toast.makeText(getApplicationContext(),
                                    "Sucesso Ao Salvar o Curso",
                                        Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "Erro Ao Salvar o Curso",
                                    Toast.LENGTH_LONG).show();
                        }

                    }

                }

                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
