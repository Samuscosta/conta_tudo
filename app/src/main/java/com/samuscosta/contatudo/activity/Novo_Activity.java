package com.samuscosta.contatudo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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
    private TextView txtValorInicialLabel;
    private long idAlteracao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo);

        setaComponentes();
        obterContador();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_novo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.salvar:
                salvar();
                return (true);

        }

        return(super.onOptionsItemSelected(item));
    }

    private void obterContador () {

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey(Contador_Model._ID)) {
                long id = extras.getLong(Contador_Model._ID);

                Contador_Controller controller = new Contador_Controller(Novo_Activity.this);
                Contador_Model model = controller.obterContadorPorId(id);
                controller.fechar();

                idAlteracao = id;

                txtValorInicialLabel.setText(getResources().getText(R.string.valor_atual));

                txtNome.setText(model.getNome());
                txtValorInicial.setText(String.valueOf(model.getValorAtual()));
                txtValorIncremento.setText(String.valueOf(model.getValorIncremento()));
            }
        }
    }

    private void setaComponentes() {
        txtNome = (TextView) findViewById(R.id.activityNovo_txtNome);
        txtValorInicial = (TextView) findViewById(R.id.activityNovo_txtInicial);
        txtValorIncremento = (TextView) findViewById(R.id.activityNovo_txtIncremento);
        txtValorInicialLabel = (TextView) findViewById(R.id.activityNovo_txtInicialLabel);
    }

    private void salvar() {
        if ( ! validar()) {
            return;
        }

        Contador_Model contador = new Contador_Model();

        //Se idAlteracao for 0, irá criar um novo contador
        //Senão, irá alterar o contador existente
        contador.setId(idAlteracao);
        contador.setNome(txtNome.getText().toString());
        contador.setDataHoraCriacao(Tempo.retornarDataHoraAtual());
        contador.setValorAtual(Integer.parseInt(txtValorInicial.getText().toString()));
        contador.setValorIncremento(Integer.parseInt(txtValorIncremento.getText().toString()));

        Contador_Controller contador_controller = new Contador_Controller(Novo_Activity.this);
        long id = contador_controller.salvar(contador);
        contador_controller.fechar();

        if (id <= 0) {
            Geral.toastShort(Novo_Activity.this, "Não salvou contador");
        } else if (idAlteracao > 0) {
            Geral.toastShort(Novo_Activity.this, contador.getNome() + " alterado");
        }

        finish();
    }

    private boolean validar() {
        if (txtNome.getText().toString().equals("")) {
            Geral.mensagem(this, "Campo vazio", "É necessário dar um txtNome");
            return false;
        }

        String inicial = txtValorInicial.getText().toString();
        if (inicial.equals("")) {
            Geral.mensagem(this, "Campo vazio", "É necessário indicar o valor txtAtual");
            return false;
        }
        String incremento = txtValorIncremento.getText().toString();
        if (incremento.equals("")) {
            Geral.mensagem(this, "Campo vazio", "É necessário indicar o valor de txtIncremento");
            return false;
        }

        return true;
    }

}
