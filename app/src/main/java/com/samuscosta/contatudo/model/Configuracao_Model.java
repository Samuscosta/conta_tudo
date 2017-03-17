package com.samuscosta.contatudo.model;

import android.provider.BaseColumns;

/**
 * Created by Samuel on 13/03/2017.
 */

public class Configuracao_Model implements BaseColumns {

    public static final String NOME_TABELA = "configuracao";

    //Nomes campos
    public static final String CHAVE = "chave";
    public static final String VALOR_1 = "valor1";
    public static final String VALOR_2 = "valor2";

    //Atributos
    private long id = 0;
    private String chave = "";
    private String valor1 = "";
    private String valor2 = "";

    //Construtor
    public Configuracao_Model() {}

    //Getters
    public long getId() {
        return id;
    }

    public String getChave() {
        return chave;
    }

    public String getValor1() {
        return valor1;
    }

    public String getValor2() {
        return valor2;
    }

    //Setters
    public void setId(long id) {
        this.id = id;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public void setValor1(String valor1) {
        this.valor1 = valor1;
    }

    public void setValor2(String valor2) {
        this.valor2 = valor2;
    }
}
