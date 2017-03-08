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
    public static final String VALOR_MINIMO = "valorMinimo";
    public static final String VALOR_MAXIMO = "valorMaximo";
    public static final String USAR_MINIMO = "usarMinimo";
    public static final String USAR_MAXIMO = "usarMaximo";

    //Atributos
    private long id = 0;
    private String dataHoraCriacao;
    private String nome = "";
    private double valorAtual = 0;
    private double valorIncremento = 0;
    private double valorMinimo = 0;
    private double valorMaximo = 0;
    private boolean usarMinimo = false;
    private boolean usarMaximo = false;

    //Construtor
    public Contador_Model() {
    }

    //Getters
    public long getId() {
        return id;
    }

    public String getDataHoraCriacao() {
        return dataHoraCriacao;
    }

    public String getNome() {
        return nome;
    }

    public double getValorAtual() {
        return valorAtual;
    }

    public double getValorIncremento() {
        return valorIncremento;
    }

    public double getValorMaximo() {
        return valorMaximo;
    }

    public double getValorMinimo() {
        return valorMinimo;
    }

    public boolean getUsarMinimo() {
        return usarMinimo;
    }

    public boolean getUsarMaximo() {
        return usarMaximo;
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

    public void setValorAtual(double valorAtual) {
        this.valorAtual = valorAtual;
    }

    public void setValorIncremento(double valorIncremento) {
        this.valorIncremento = valorIncremento;
    }

    public void setValorMaximo(double valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public void setValorMinimo(double valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public void setUsarMinimo(boolean usarMinimo) {
        this.usarMinimo = usarMinimo;
    }

    public void setUsarMaximo(boolean usarMaximo) {
        this.usarMaximo = usarMaximo;
    }
}
