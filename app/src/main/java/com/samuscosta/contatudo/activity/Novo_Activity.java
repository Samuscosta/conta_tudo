package com.samuscosta.contatudo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.samuscosta.contatudo.R;
import com.samuscosta.contatudo.controller.Contador_Controller;
import com.samuscosta.contatudo.model.Contador_Model;
import com.samuscosta.contatudo.utilidade.Geral;
import com.samuscosta.contatudo.utilidade.Tempo;

public class Novo_Activity extends AppCompatActivity {

    private TextView txtNome;
    private TextView txtValorInicial;
    private TextView txtValorIncremento;
    private TextView txtValorMinimo;
    private TextView txtValorMaximo;
    private CheckBox chkMinimo;
    private CheckBox chkMaximo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo);

        setaComponentes();
    }

    private void setaComponentes() {
        txtNome = (TextView) findViewById(R.id.activityNovoNome);
        txtValorInicial = (TextView) findViewById(R.id.activityNovoValorInicial);
        txtValorIncremento = (TextView) findViewById(R.id.activityNovoValorIncremento);
        txtValorMinimo = (TextView) findViewById(R.id.activityNovoValorMinimo);
        txtValorMaximo = (TextView) findViewById(R.id.activityNovoValorMaximo);
        chkMinimo = (CheckBox) findViewById(R.id.activityNovoCheckMinimo);
        chkMaximo = (CheckBox) findViewById(R.id.activityNovoCheckMaximo);
    }

    public void activityNovoNovoOnClick(View view) {
        if ( ! validar()) {
            return;
        }

        Contador_Model contador = new Contador_Model();

        contador.setNome(txtNome.getText().toString());
        contador.setDataHoraCriacao(Tempo.retornarDataHoraAtual());
        contador.setValorAtual(Double.parseDouble(txtValorInicial.getText().toString()));
        contador.setValorIncremento(Double.parseDouble(txtValorIncremento.getText().toString()));
        contador.setUsarMinimo(chkMinimo.isChecked());
        contador.setUsarMaximo(chkMaximo.isChecked());

        String minimo = txtValorMinimo.getText().toString();
        if ( ! minimo.equals("")) {
            contador.setValorMinimo(Double.parseDouble(minimo));
        }

        String maximo = txtValorMaximo.getText().toString();
        if ( ! maximo.equals("")) {
            contador.setValorMaximo(Double.parseDouble(maximo));
        }

        Contador_Controller contador_controller = new Contador_Controller(Novo_Activity.this);
        long id = contador_controller.salvar(contador);
        contador_controller.fechar();

        if (id <= 0) {
            Geral.toastShort(Novo_Activity.this, "Não salvou");
        }

        finish();
    }

    private boolean validar() {
        if (txtNome.getText().toString().equals("")) {
            Geral.mensagem(this, "Campo faltando", "É necessário dar um nome");
            return false;
        }

        String inicial = txtValorInicial.getText().toString();
        if (inicial.equals("")) {
            Geral.mensagem(this, "Campo faltando", "É necessário indicar o valor inicial");
            return false;
        }
        String incremento = txtValorIncremento.getText().toString();
        if (incremento.equals("")) {
            Geral.mensagem(this, "Campo faltando", "É necessário indicar o valor de incremento");
            return false;
        }

        if (chkMinimo.isChecked()) {
            String minimo = txtValorMinimo.getText().toString();
            if ( ! minimo.equals("")) {
                if (Double.parseDouble(minimo) > Double.parseDouble(inicial)) {
                    Geral.mensagem(this, "Valor inválido", "Valor mínimo não pode ser menor que o valor inicial");
                    return false;
                }
            }
        }

        if (chkMaximo.isChecked()) {
            String maximo = txtValorMaximo.getText().toString();
            if ( ! maximo.equals("")) {
                if (Double.parseDouble(maximo) < Double.parseDouble(inicial)) {
                    Geral.mensagem(this, "Valor inválido", "Valor inicial não pode ser maior que o valor máximo");
                    return false;
                }
            }
        }

        return true;
    }

    public void activityNovoCheckMinimoOnClick(View view) {
        txtValorMinimo.setEnabled(chkMinimo.isChecked());
    }

    public void activityNovoCheckMaximoOnClick(View view) {
        txtValorMaximo.setEnabled(chkMaximo.isChecked());
    }
}
