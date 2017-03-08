package com.samuscosta.contatudo.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class SQLiteHelper extends SQLiteOpenHelper {

	private static final String TAG = SQLiteHelper.class.getSimpleName();

	private String[] scriptSQLCreate;

	public SQLiteHelper(Context context, String nomeBanco, int versaoBanco, String[] scriptSQLCreate) {
		super(context, nomeBanco, null, versaoBanco);
		this.scriptSQLCreate = scriptSQLCreate;
	}

	// Criar novo banco...
	public void onCreate(SQLiteDatabase db) {
		Log.i(TAG, "Criando banco com SQL");

		for (String sql : scriptSQLCreate) {
			try {
				db.execSQL(sql);
			} catch (SQLException e) {
				Log.i(TAG, "Erro ao executar SQL: " + e.toString());
				Log.i(TAG, sql);
			}
		}
	}

	// Mudou a versão...
	public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int novaVersao) {
		Log.d(TAG, "Iniciando onUpgrade SQLiteHelper");
		Log.d(TAG, "Versão antiga: " + versaoAntiga);
		Log.d(TAG, "Nova versão: " + novaVersao);

		try {
			Log.d(TAG, "onUpgrade SQLiteHelper executado com sucesso");
		} catch (Exception e) {
			Log.d(TAG, "Erro ao executar onUpgrade SQLiteHelper\n" + e.toString());
		}
	}

}