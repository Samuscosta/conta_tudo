package com.samuscosta.contatudo.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.samuscosta.contatudo.database.SQLiteDataBase;
import com.samuscosta.contatudo.model.Contador_Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samuel on 06/03/2017.
 */

public class Contador_Controller {
    private static final String TAG = Contador_Controller.class.getSimpleName();
    private SQLiteDatabase db;

    public Contador_Controller (Context ctx) {
        db = ctx.openOrCreateDatabase(SQLiteDataBase.NOME_BANCO, Context.MODE_PRIVATE, null);
    }

    private ContentValues contentValues(Contador_Model model) {
        ContentValues values = new ContentValues();

        values.put(Contador_Model.DATA_HORA_CRIACAO, model.getDataHoraCriacao());
        values.put(Contador_Model.NOME, model.getNome());
        values.put(Contador_Model.VALOR_ATUAL, model.getValorAtual());
        values.put(Contador_Model.VALOR_INCREMENTO, model.getValorIncremento());
        values.put(Contador_Model.VALOR_MAXIMO, model.getValorMaximo());
        values.put(Contador_Model.VALOR_MINIMO, model.getValorMinimo());
        values.put(Contador_Model.USAR_MAXIMO, model.getUsarMaximo());
        values.put(Contador_Model.USAR_MINIMO, model.getUsarMinimo());

        return values;
    }

    private long inserir(Contador_Model model) {
        return inserir(contentValues(model));
    }

    private long inserir(ContentValues values) {
        return db.insert(Contador_Model.NOME_TABELA, "", values);
    }

    private int atualizar(Contador_Model model) {
        String _id = String.valueOf(model.getId());

        String where = Contador_Model._ID + "=?";
        String[] whereArgs = new String[] { _id };

        return atualizar(contentValues(model), where, whereArgs);
    }

    private int atualizar(ContentValues valores, String where, String[] whereArgs) {
        return db.update(Contador_Model.NOME_TABELA, valores, where, whereArgs);
    }

    public int deletar(long id) {
        String _id = String.valueOf(id);
        String[] whereArgs = new String[] { _id };
        return deletar(Contador_Model._ID + "=?", whereArgs);
    }

    private int deletar(String where, String[] whereArgs) {
        return db.delete(Contador_Model.NOME_TABELA, where, whereArgs);
    }

    public long salvar(Contador_Model model) {
        long id = model.getId();
        if (id != 0) {
            atualizar(model);
        } else {
            id = inserir(model);
        }
        return id;
    }

    private Cursor getCursorSQL(String sql) {
        try {
            return db.rawQuery(sql, null);
        } catch (Exception e) {
            Log.e(TAG, "getCursorSQL: " + e.toString() );
            Log.e(TAG, "SQL do erro: " + sql);
            return null;
        }
    }

    private List<Contador_Model> getList(String sql) {
        Cursor cursor = null;

        try {
            cursor = getCursorSQL(sql);
            if (cursor != null && cursor.moveToFirst()) {

                int idxId = cursor.getColumnIndex(Contador_Model._ID);
                int idxData = cursor.getColumnIndex(Contador_Model.DATA_HORA_CRIACAO);
                int idxNome = cursor.getColumnIndex(Contador_Model.NOME);
                int idxInicial = cursor.getColumnIndex(Contador_Model.VALOR_ATUAL);
                int idxIncremento = cursor.getColumnIndex(Contador_Model.VALOR_INCREMENTO);
                int idxMinimo = cursor.getColumnIndex(Contador_Model.VALOR_MINIMO);
                int idxMaximo = cursor.getColumnIndex(Contador_Model.VALOR_MAXIMO);
                int idxUsarMinimo = cursor.getColumnIndex(Contador_Model.USAR_MINIMO);
                int idxUsarMaximo = cursor.getColumnIndex(Contador_Model.USAR_MAXIMO);

                List<Contador_Model> list = new ArrayList<>();

                do {
                    Contador_Model model = new Contador_Model();

                    model.setId( idxId > -1 ? cursor.getLong(idxId) : 0);
                    model.setDataHoraCriacao( idxData > -1 ? cursor.getString(idxData) : "");
                    model.setNome( idxNome > -1 ? cursor.getString(idxNome) : "");
                    model.setValorAtual( idxInicial > -1 ? cursor.getDouble(idxInicial) : 0);
                    model.setValorIncremento( idxIncremento > -1 ? cursor.getDouble(idxIncremento) : 0);
                    model.setValorMinimo( idxMinimo > -1 ? cursor.getDouble(idxMinimo) : 0);
                    model.setValorMaximo( idxMaximo > -1 ? cursor.getDouble(idxMaximo) : 0);
                    model.setUsarMinimo(idxUsarMinimo > -1 && cursor.getDouble(idxUsarMinimo) > 0);
                    model.setUsarMaximo(idxUsarMaximo > -1 && cursor.getDouble(idxUsarMaximo) > 0);

                    list.add(model);
                } while (cursor.moveToNext());

                return  list;
            }

        } catch (Exception e) {
            Log.e(TAG, "getList: " + e.toString());
        } finally {
            if (cursor != null) cursor.close();
        }

        return null;
    }

    public List<Contador_Model> obterListaOrdenada() {
        String sql = " SELECT * " +
                " FROM " + Contador_Model.NOME_TABELA +
                " ORDER BY " + Contador_Model._ID;

        return getList(sql);
    }

    public void fechar() {
        if (db != null) {
            db.close();
        }
    }

}
