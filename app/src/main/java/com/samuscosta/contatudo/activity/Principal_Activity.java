package com.samuscosta.contatudo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.samuscosta.contatudo.R;
import com.samuscosta.contatudo.adapter.ItemPorcentagem_Adapter;
import com.samuscosta.contatudo.adapter.ItemPrincipal_Adapter;
import com.samuscosta.contatudo.controller.Contador_Controller;
import com.samuscosta.contatudo.database.SQLiteDataBase;
import com.samuscosta.contatudo.model.Contador_Model;
import com.samuscosta.contatudo.utilidade.Constantes;
import com.samuscosta.contatudo.utilidade.DividerItemDecoration;
import com.samuscosta.contatudo.utilidade.Geral;

import java.util.ArrayList;
import java.util.List;

public class Principal_Activity extends AppCompatActivity {

    Context ctx;
    ItemPrincipal_Adapter adapter;
    ItemPorcentagem_Adapter adapterPorcentagem;
    List<Contador_Model> listaContador = new ArrayList<>();
    FloatingActionButton floatingActionButton;
    TextView txtQuantidade;
    TextView txtWBC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        ctx = this;
        SQLiteDataBase.onCreate(ctx);

        setaComponentes();
        setaRecyclerViewItens();
        setaRecyclerViewPorcentagem();

        listenersButtons();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Contador_Controller controller = new Contador_Controller(ctx);
        listaContador = controller.obterListaOrdenada();
        controller.fechar();

        if (listaContador == null) listaContador = new ArrayList<>();

        setaRecyclerViewItens();
        setaRecyclerViewPorcentagem();
        adapter.notifyDataSetChanged();
        adapterPorcentagem.notifyDataSetChanged();
        alterarQuantidade();
        alterarWBC();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.novo:
                startActivity(new Intent(ctx, Novo_Activity.class));
                return (true);

            case R.id.configuracao:
                startActivity(new Intent(ctx, Configuracao_Activity.class));
                return (true);

        }

        return(super.onOptionsItemSelected(item));
    }


    private void setaRecyclerViewItens(){

        //Aqui é instanciado o Recyclerview
        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.activityPrincipal_recyclerItens);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ctx);
        mRecyclerView.setLayoutManager(mLayoutManager);

        adapter = new ItemPrincipal_Adapter(listaContador);

        adapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mRecyclerView.getChildAdapterPosition(v);

                Intent it =  new Intent(ctx, Detalhe_Activity.class);
                it.putExtra(Contador_Model._ID, listaContador.get(position).getId());
                startActivity(it);
            }
        });

        adapter.setMenosButtonListener(new ItemPrincipal_Adapter.OnMenosItemClickListener() {
            @Override
            public void onMenosIsClick(View button, int position) {
                Contador_Model item = listaContador.get(position);
                item.setValorAtual(item.getValorAtual() - item.getValorIncremento());
                gravarAlteracao(item);
            }
        });

        adapter.setMaisButtonListener(new ItemPrincipal_Adapter.OnMaisItemClickListener() {
            @Override
            public void onMaisIsClick(View button, int position) {
                Contador_Model item = listaContador.get(position);
                item.setValorAtual(item.getValorAtual() + item.getValorIncremento());
                gravarAlteracao(item);
            }
        });

        mRecyclerView.addItemDecoration(new DividerItemDecoration(ctx, LinearLayoutManager.VERTICAL));

        mRecyclerView.setAdapter(adapter);
    }

    private void setaRecyclerViewPorcentagem(){

        //Aqui é instanciado o Recyclerview
        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.activityPrincipal_recyclerPorcentagem);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ctx);
        mRecyclerView.setLayoutManager(mLayoutManager);

        adapterPorcentagem = new ItemPorcentagem_Adapter(listaContador);

        //mRecyclerView.addItemDecoration(new DividerItemDecoration(ctx, LinearLayoutManager.HORIZONTAL));

        mRecyclerView.setAdapter(adapterPorcentagem);
    }

    private void gravarAlteracao(Contador_Model model) {
        Contador_Controller controller = new Contador_Controller(ctx);
        controller.salvar(model);
        controller.fechar();

        adapter.notifyDataSetChanged();
        adapterPorcentagem.notifyDataSetChanged();
    }

    private void setaComponentes(){
        floatingActionButton = (FloatingActionButton) findViewById(R.id.activityPrincipal_fab);
        txtQuantidade = (TextView) findViewById(R.id.activityPrincipal_txtQuantidade);
        txtWBC = (TextView) findViewById(R.id.activityPrincipal_txtWbc);
    }

    /**
     * Chama os listeners para os botões
     */
    private void listenersButtons() {

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ctx, Novo_Activity.class));
            }
        });
    }

    private void alterarQuantidade() {
        txtQuantidade.setText(retornarStringResources(R.string.quantidade, listaContador.size()));
    }

    private void alterarWBC() {
        txtWBC.setText(retornarStringResources(R.string.wbc_string,
                Geral.obterValorConfiguracao(ctx, Constantes.CONFIGURACAO_WBC, "4000")));
    }

    private String retornarStringResources(int id, Object... args) {
        return String.format(getResources().getString(id), args);
    }

}
