package com.samuscosta.contatudo.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.samuscosta.contatudo.R;
import com.samuscosta.contatudo.controller.Contador_Controller;
import com.samuscosta.contatudo.model.Contador_Model;
import com.samuscosta.contatudo.utilidade.Geral;

public class Detalhe_Activity extends AppCompatActivity {

    TextView txtNome;
    TextView txtAtual;
    TextView txtIncremento;
    TextView txtMinimo;
    TextView txtMaximo;
    TextView txtUsaMinimo;
    TextView txtUsaMaximo;
    TextView txtDataHora;
    Contador_Model model;
    Context ctx;
    boolean carregouCerto = false;

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

        setaValores();
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
        txtNome = (TextView) findViewById(R.id.activityDetalhe_txtNome);
        txtAtual = (TextView) findViewById(R.id.activityDetalhe_txtAtual);
        txtIncremento = (TextView) findViewById(R.id.activityDetalhe_txtIncremento);
        txtMinimo = (TextView) findViewById(R.id.activityDetalhe_txtMinimo);
        txtMaximo = (TextView) findViewById(R.id.activityDetalhe_txtMaximo);
        txtUsaMinimo = (TextView) findViewById(R.id.activityDetalhe_txtUsaMinimo);
        txtUsaMaximo = (TextView) findViewById(R.id.activityDetalhe_txtUsaMaximo);
        txtDataHora = (TextView) findViewById(R.id.activityDetalhe_txtDataHora);
    }

    private void setaValores() {
        txtNome.setText(retornarStringResources(R.string.texto_string, model.getNome()));
        txtAtual.setText(retornarStringResources(R.string.valor_atual_string,
                Geral.retornarValorFormatado(model.getValorAtual())));
        txtIncremento.setText(retornarStringResources(R.string.valor_incremento_string,
                Geral.retornarValorFormatado(model.getValorIncremento())));
        txtDataHora.setText(retornarStringResources(R.string.texto_string,
                model.getDataHoraCriacao()));

        if (model.getUsarMinimo()) {
            txtUsaMinimo.setVisibility(View.GONE);
            txtMinimo.setText(retornarStringResources(R.string.valor_minimo_string,
                    Geral.retornarValorFormatado(model.getValorMinimo())));
        } else {
            txtMinimo.setVisibility(View.GONE);
        }

        if (model.getUsarMaximo()) {
            txtUsaMaximo.setVisibility(View.GONE);
            txtMaximo.setText(retornarStringResources(R.string.valor_maximo_string,
                    Geral.retornarValorFormatado(model.getValorMaximo())));
        } else {
            txtMaximo.setVisibility(View.GONE);
        }
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
