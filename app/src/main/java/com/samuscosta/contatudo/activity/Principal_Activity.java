package com.samuscosta.contatudo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.samuscosta.contatudo.R;
import com.samuscosta.contatudo.adapter.Principal_Adapter;
import com.samuscosta.contatudo.controller.Contador_Controller;
import com.samuscosta.contatudo.database.SQLiteDataBase;
import com.samuscosta.contatudo.model.Contador_Model;
import com.samuscosta.contatudo.utilidade.DividerItemDecoration;
import com.samuscosta.contatudo.utilidade.Geral;

import java.util.ArrayList;
import java.util.List;

public class Principal_Activity extends AppCompatActivity {

    Context ctx;
    Principal_Adapter adapter;
    List<Contador_Model> listaContador = new ArrayList<>();
    FloatingActionButton floatingActionButton;
    TextView txtQuantidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        ctx = this;
        SQLiteDataBase.onCreate(ctx);

        setaComponentes();
        setaRecyclerView();

        listenersButtons();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Contador_Controller controller = new Contador_Controller(Principal_Activity.this);
        listaContador = controller.obterListaOrdenada();
        controller.fechar();

        if (listaContador == null) listaContador = new ArrayList<>();

        setaRecyclerView();
        adapter.notifyDataSetChanged();
        alterarQuantidade();
    }

    private void setaRecyclerView(){

        //Aqui é instanciado o Recyclerview
        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.activityPrincipalRecycler);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        adapter = new Principal_Adapter(listaContador);

        adapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mRecyclerView.getChildAdapterPosition(v);
                Contador_Model item = listaContador.get(position);

                String mensagem = "Valor incremento: " + String.valueOf(item.getValorIncremento());

                if (item.getUsarMinimo()) {
                    mensagem += "\nValor mínimo: " + String.valueOf(item.getValorMinimo());
                }
                if (item.getUsarMaximo()) {
                    mensagem += "\nValor máximo: " + String.valueOf(item.getValorMaximo());
                }
                Geral.toastShort(ctx, mensagem);
            }
        });

        adapter.setMenosButtonListener(new Principal_Adapter.OnMenosItemClickListener() {
            @Override
            public void onMenosIsClick(View button, int position) {
                Contador_Model item = listaContador.get(position);

                double novoValor = item.getValorAtual() - item.getValorIncremento();

                if (item.getUsarMinimo()) {
                    if (novoValor <= item.getValorMinimo()) {
                        novoValor = item.getValorMinimo();
                        Geral.toastShort(ctx, item.getNome() + " alcançou o valor mínimo");
                    }
                }

                item.setValorAtual(novoValor);
                gravarAlteracao(item);
            }
        });

        adapter.setMaisButtonListener(new Principal_Adapter.OnMaisItemClickListener() {
            @Override
            public void onMaisIsClick(View button, int position) {
                Contador_Model item = listaContador.get(position);

                double novoValor = item.getValorAtual() + item.getValorIncremento();

                if (item.getUsarMaximo()) {
                    if (novoValor >= item.getValorMaximo()) {
                        novoValor = item.getValorMaximo();
                        Geral.toastShort(ctx, item.getNome() + " alcançou o valor máximo");
                    }
                }

                item.setValorAtual(novoValor);
                gravarAlteracao(item);
            }
        });

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        mRecyclerView.setAdapter(adapter);
    }

    private void gravarAlteracao(Contador_Model model) {
        Contador_Controller controller = new Contador_Controller(ctx);
        long retorno = controller.salvar(model);
        controller.fechar();

        adapter.notifyDataSetChanged();
    }

    private void setaComponentes(){

        floatingActionButton = (FloatingActionButton) findViewById(R.id.activityPrincipalFab);
        txtQuantidade = (TextView) findViewById(R.id.activityPrincipalTxtQuantidade);
    }

    /**
     * Chama os listeners para os botões
     */
    private void listenersButtons() {

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Principal_Activity.this, Novo_Activity.class));
            }
        });
    }

    private void alterarQuantidade() {
        String quantidade = String.format(getResources().getString(R.string.quantidade), listaContador.size());
        txtQuantidade.setText(quantidade);
    }

}
