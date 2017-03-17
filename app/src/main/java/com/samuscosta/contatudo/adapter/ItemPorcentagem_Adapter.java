package com.samuscosta.contatudo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samuscosta.contatudo.R;
import com.samuscosta.contatudo.model.Contador_Model;
import com.samuscosta.contatudo.utilidade.Constantes;
import com.samuscosta.contatudo.utilidade.Geral;

import java.util.List;

/**
 * Created by Samuel on 16/03/2017.
 */

public class ItemPorcentagem_Adapter extends RecyclerView.Adapter<ItemPorcentagem_Adapter.ViewHolder> {
    private List<Contador_Model> lista;
    private Context ctx;

    public ItemPorcentagem_Adapter(List<Contador_Model> lista) {
        this.lista = lista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_porcentagem, viewGroup, false);
        ctx = viewGroup.getContext();

        return new ItemPorcentagem_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(lista.get(position));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView viewNome;
        TextView viewRelativo;
        TextView viewTotal;

        ViewHolder(final View itemView) {
            super(itemView);

            viewNome = (TextView) itemView.findViewById(R.id.itemPorcentagem_txtNome);
            viewRelativo = (TextView) itemView.findViewById(R.id.itemPorcentagem_txtRelativo);
            viewTotal = (TextView) itemView.findViewById(R.id.itemPorcentagem_txtTotal);
        }

        void bind(final Contador_Model item) {
            viewNome.setText(item.getNome());
            viewRelativo.setText(calcularPorcentagem(item.getValorAtual()));
            viewTotal.setText("0");
        }
    }

    private String calcularPorcentagem(int valor) {
        double novoValor = valor * 100 / Double.parseDouble(Geral.obterValorConfiguracao(
                ctx, Constantes.CONFIGURACAO_WBC, "4000"));

        return Geral.formatarValor(novoValor);
    }
}
