package com.samuscosta.contatudo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.samuscosta.contatudo.model.Configuracao_Model;
import com.samuscosta.contatudo.model.Contador_Model;

/**
 * Created by Samuel on 03/03/2017.
 */

public class SQLiteDataBase {

    private static final String TAG = SQLiteDataBase.class.getSimpleName();

    //Contador
    private static final String CONTADOR = "CREATE TABLE IF NOT EXISTS " +
            Contador_Model.NOME_TABELA + " ( " +
            Contador_Model._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ON CONFLICT IGNORE UNIQUE ON CONFLICT IGNORE " +
            ", " + Contador_Model.DATA_HORA_CRIACAO + " TEXT NOT NULL ON CONFLICT IGNORE " +
            ", " + Contador_Model.NOME + " TEXT NOT NULL ON CONFLICT IGNORE " +
            ", " + Contador_Model.VALOR_ATUAL + " NUMERIC NOT NULL ON CONFLICT IGNORE DEFAULT 0 " +
            ", " + Contador_Model.VALOR_INCREMENTO + " NUMERIC NOT NULL ON CONFLICT IGNORE DEFAULT 0 " +
            " )";

    private static final String CONFIGURACAO = "CREATE TABLE IF NOT EXISTS " +
            Configuracao_Model.NOME_TABELA + " ( " +
            Configuracao_Model._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ON CONFLICT IGNORE UNIQUE ON CONFLICT IGNORE " +
            ", " + Configuracao_Model.CHAVE + " TEXT NOT NULL ON CONFLICT IGNORE " +
            ", " + Configuracao_Model.VALOR_1 + " TEXT " +
            ", " + Configuracao_Model.VALOR_2 + " TEXT " +
            " ) ";

    //Script de criação
    private static final String[] SCRIPT_DATABASE_CREATE = new String[] {
            CONTADOR, CONFIGURACAO
    };

    public static final String NOME_BANCO = "dbContaTudo";
    private static final int VERSAO_BANCO = 1;

    public static void onCreate(Context ctx) {
        try {
            SQLiteDatabase db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
            db.close();

            SQLiteHelper dbHelper = new SQLiteHelper(ctx, NOME_BANCO, VERSAO_BANCO, SCRIPT_DATABASE_CREATE);
            dbHelper.getWritableDatabase();
            dbHelper.close();
        } catch (Exception e) {
            Log.e(TAG, "onCreate: " + e.toString());
        }
    }

}
