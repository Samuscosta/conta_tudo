package com.samuscosta.contatudo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.samuscosta.contatudo.R;
import com.samuscosta.contatudo.controller.Configuracao_Controller;
import com.samuscosta.contatudo.model.Configuracao_Model;
import com.samuscosta.contatudo.utilidade.Constantes;
import com.samuscosta.contatudo.utilidade.Geral;

public class Configuracao_Activity extends AppCompatActivity {

    private EditText edtWbc;
    private Switch swtTela;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        ctx = this;

        setarComponentes();
        criarListeners();

        setarValores();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        salvarTodasConfiguracoes();
    }

    private void salvarTodasConfiguracoes(){
        Configuracao_Model model;

        //Salvar WBC
        model = new Configuracao_Model();
        model.setChave(Constantes.CONFIGURACAO_WBC);
        model.setValor1(edtWbc.getText().toString());
        salvarConfiguracao(model);

        //Salvar tela acesa
        model = new Configuracao_Model();
        model.setChave(Constantes.CONFIGURACAO_TELA_LIGADA);
        model.setValor1(String.valueOf(swtTela.isChecked()));
        salvarConfiguracao(model);
    }

    private void criarListeners() {
        swtTela.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                //Altera a configuração tela
                controlarTelaAcesa(isChecked);
            }
        });
    }

    private void salvarConfiguracao(Configuracao_Model model) {
        Configuracao_Controller controller = new Configuracao_Controller(ctx);
        Configuracao_Model newModel = controller.obterConfiguracaoPorChave(model.getChave());

        if (newModel == null) {
            newModel = new Configuracao_Model();

            newModel.setChave(model.getChave());
        }

        newModel.setValor1(model.getValor1());
        newModel.setValor2(model.getValor2());

        controller.salvar(newModel);
        controller.fechar();
    }

    private void setarComponentes() {
        edtWbc = (EditText) findViewById(R.id.activityConfiguracao_edtWbc);
        swtTela = (Switch) findViewById(R.id.activityConfiguracao_swtTelaLigada);
    }

    private void setarValores() {
        edtWbc.setText( Geral.obterValorConfiguracao(ctx, Constantes.CONFIGURACAO_WBC, "4000"));
        swtTela.setChecked( Boolean.parseBoolean( Geral.obterValorConfiguracao(ctx,
                Constantes.CONFIGURACAO_TELA_LIGADA, "false")) );
    }

    private void controlarTelaAcesa(boolean telaAcesa) {
        if (telaAcesa) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

}
