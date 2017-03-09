package com.samuscosta.contatudo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samuscosta.contatudo.R;
import com.samuscosta.contatudo.model.Contador_Model;
import com.samuscosta.contatudo.utilidade.Geral;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Samuel on 02/03/2017.
 */

public class Principal_Adapter extends RecyclerView.Adapter<Principal_Adapter.ViewHolder>{
    private List<Contador_Model> lista;

    public interface OnMenosItemClickListener {
        void onMenosIsClick(View button, int position);
    }

    public interface OnMaisItemClickListener {
        void onMaisIsClick(View button, int position);
    }

    public Principal_Adapter(List<Contador_Model> lista) {
        this.lista = lista;
    }

    private View.OnClickListener clickListener;
    private OnMenosItemClickListener menosListener;
    private OnMaisItemClickListener maisListener;

    public void setOnItemClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setMenosButtonListener(OnMenosItemClickListener  menosListener) {
        this.menosListener = menosListener;
    }

    public void setMaisButtonListener(OnMaisItemClickListener  maisListener) {
        this.maisListener = maisListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_principal, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if (clickListener != null) {
            holder.itemView.setOnClickListener(clickListener);
        }

        if (menosListener!= null) {
            holder.getMenosClick().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menosListener.onMenosIsClick(v, holder.getAdapterPosition());
                }
            });
        }

        if (maisListener!= null) {
            holder.getMaisClick().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    maisListener.onMaisIsClick(v, holder.getAdapterPosition());
                }
            });
        }

        holder.bind(lista.get(position), position);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView viewPosicao;
        TextView viewNome;
        TextView viewValor;
        CircleImageView viewMenos;
        CircleImageView viewMais;

        ViewHolder(final View itemView) {
            super(itemView);

            viewPosicao = (TextView) itemView.findViewById(R.id.itemPrincipalPosicao);
            viewNome = (TextView) itemView.findViewById(R.id.itemPrincipalNome);
            viewValor = (TextView) itemView.findViewById(R.id.itemPrincipalValor);
            viewMenos = (CircleImageView) itemView.findViewById(R.id.itemPrincipalMenos);
            viewMais = (CircleImageView) itemView.findViewById(R.id.itemPrincipalMais);
        }

        CircleImageView getMenosClick() {
            return viewMenos;
        }

        CircleImageView getMaisClick() {
            return viewMais;
        }

        void bind(final Contador_Model item, int position) {
            viewPosicao.setText(String.valueOf(position + 1));
            viewNome.setText(item.getNome());
            viewValor.setText(Geral.retornarValorFormatado(item.getValorAtual()));
        }
    }

}

