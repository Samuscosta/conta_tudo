package com.samuscosta.contatudo.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.samuscosta.contatudo.R;
import com.samuscosta.contatudo.controller.Contador_Controller;
import com.samuscosta.contatudo.model.Contador_Model;
import com.samuscosta.contatudo.utilidade.Geral;

public class Detalhe_Activity extends AppCompatActivity {

    private TextView txtDataHora;
    private TextView txtNome;
    private TextView txtAtual;
    private TextView txtIncremento;
    private Contador_Model model;
    private Context ctx;
    private boolean carregouCerto = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        ctx = this;

        setaComponentes();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey(Contador_Model._ID)) {
                long id = extras.getLong(Contador_Model._ID);

                Contador_Controller controller = new Contador_Controller(ctx);
                model = controller.obterContadorPorId(id);
                controller.fechar();

                carregouCerto = model != null;
            }
        }

        if ( ! carregouCerto) {
            Geral.toastShort(ctx, "Problema ao carregar os detalhes do contador selecionado");
            finish();
        }

        setarValores();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalhe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.alterar:

                Intent it =  new Intent(ctx, Novo_Activity.class);
                it.putExtra(Contador_Model._ID, model.getId());
                startActivity(it);

                finish();

                return (true);
            case R.id.excluir:
                excluir();
                return (true);
        }

        return(super.onOptionsItemSelected(item));
    }

    private void setaComponentes(){
        txtDataHora = (TextView) findViewById(R.id.activityDetalhe_txtDataHora);
        txtNome = (TextView) findViewById(R.id.activityDetalhe_txtNome);
        txtAtual = (TextView) findViewById(R.id.activityDetalhe_txtAtual);
        txtIncremento = (TextView) findViewById(R.id.activityDetalhe_txtIncremento);
    }

    private void setarValores() {
        txtNome.setText(retornarStringResources(R.string.texto_string, model.getNome()));
        txtAtual.setText(retornarStringResources(R.string.valor_atual_string, model.getValorAtual()));
        txtIncremento.setText(retornarStringResources(R.string.valor_incremento_string, model.getValorIncremento()));
        txtDataHora.setText(retornarStringResources(R.string.texto_string, model.getDataHoraCriacao()));
    }

    private String retornarStringResources(int id, Object... args) {
        try {
            return String.format(getResources().getString(id), args);
        } catch (Exception e) {
            Log.e(Detalhe_Activity.class.getSimpleName(), e.toString());
            return getResources().getString(id);
        }
    }

    private void excluir () {
        Geral.mensagemSimNao(ctx, "Exclusão", "Realmente deseja excluir " + model.getNome() + "?",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Contador_Controller controller = new Contador_Controller(ctx);
                        long id = controller.deletar(model.getId());
                        controller.fechar();

                        if (id == 0) {
                            Geral.toastShort(ctx, "Problema ao excluir " + model.getNome());
                        } else {
                            Geral.toastShort(ctx, model.getNome() + " excluído");
                        }

                        finish();
                    }
                });
    }
}
