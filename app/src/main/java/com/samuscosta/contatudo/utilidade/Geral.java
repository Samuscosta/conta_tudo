package com.samuscosta.contatudo.utilidade;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.WindowManager;
import android.widget.Toast;

import com.samuscosta.contatudo.controller.Configuracao_Controller;
import com.samuscosta.contatudo.model.Configuracao_Model;

import java.text.DecimalFormat;

/**
 * Created by Samuel on 07/03/2017.
 */

public class Geral {
    public static void mensagem(final Context context, final String titulo, final String mensagem ) {

        AlertDialog.Builder dialogo = new AlertDialog.Builder(context);
        dialogo.setTitle(titulo);
        dialogo.setMessage(mensagem);
        dialogo.setNeutralButton("OK", null);
        dialogo.show();
    }

    public static void mensagemSimNao(Context ctx, String titulo, String mensagem,
                                      DialogInterface.OnClickListener sim) {

        AlertDialog.Builder dialogo = new AlertDialog.Builder(ctx);
        dialogo.setTitle(titulo);
        dialogo.setIcon(android.R.drawable.ic_menu_help);
        dialogo.setMessage(mensagem);
        dialogo.setPositiveButton("Sim", sim);
        dialogo.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialogo.setCancelable(false);
        dialogo.show();
    }

    public static void toastShort(Context ctx, String mensagem) {
        Toast.makeText(ctx, mensagem, Toast.LENGTH_SHORT).show();
    }

    public static String retornarValorFormatado(double valor) {
        String valorFormatado;
        if (valor % 1 != 0) {
            valorFormatado = String.valueOf(valor).replace(".", ",");
        } else {
            valorFormatado = formatarValor(valor);
        }

        return valorFormatado;
    }

    public static String formatarValor(double valor) {
        DecimalFormat decimal = new DecimalFormat( "0.##" );
        return decimal.format( valor ).replace(",", ".");
    }

    public static void alterarTelaAcesa(Activity activity) {
        Configuracao_Controller controller = new Configuracao_Controller(activity.getBaseContext());
        Configuracao_Model model = controller.obterConfiguracaoPorChave(
                Constantes.CONFIGURACAO_TELA_LIGADA);
        controller.fechar();

        boolean acesa = false;

        if (model != null) {
            acesa = Boolean.getBoolean(model.getValor1());
        }

        if (acesa) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    public static String obterValorConfiguracao(Context ctx, String chave, String valorPadrao) {
        Configuracao_Controller controller = new Configuracao_Controller(ctx);
        Configuracao_Model model = controller.obterConfiguracaoPorChave(chave);
        controller.fechar();

        if (model != null) {
            return model.getValor1();
        }

        //Se não existir a configuração, retorna o valor padrão
        return valorPadrao;
    }
}
