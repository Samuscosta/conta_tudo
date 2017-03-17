package com.samuscosta.contatudo.model;

import android.provider.BaseColumns;

/**
 * Created by Samuel on 02/03/2017.
 */

public class Contador_Model implements BaseColumns {

    public static final String NOME_TABELA = "contador";

    //Nomes campos
    public static final String DATA_HORA_CRIACAO = "dataHoraCriacao";
    public static final String NOME = "nome";
    public static final String VALOR_ATUAL = "valorAtual";
    public static final String VALOR_INCREMENTO = "valorIncremento";

    //Atributos
    private long id = 0;
    private String dataHoraCriacao;
    private String nome = "";
    private int valorAtual = 0;
    private int valorIncremento = 0;

    //Construtor
    public Contador_Model() {}

    //Gettersa
    public long getId() {
        return id;
    }

    public String getDataHoraCriacao() {
        return dataHoraCriacao;
    }

    public String getNome() {
        return nome;
    }

    public int getValorAtual() {
        return valorAtual;
    }

    public int getValorIncremento() {
        return valorIncremento;
    }

    //Setters
    public void setId(long id) {
        this.id = id;
    }

    public void setDataHoraCriacao(String dataHoraCriacao) {
        this.dataHoraCriacao = dataHoraCriacao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setValorAtual(int valorAtual) {
        this.valorAtual = valorAtual;
    }

    public void setValorIncremento(int valorIncremento) {
        this.valorIncremento = valorIncremento;
    }

}
