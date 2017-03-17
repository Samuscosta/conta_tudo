package com.samuscosta.contatudo.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.samuscosta.contatudo.database.SQLiteDataBase;
import com.samuscosta.contatudo.model.Configuracao_Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samuel on 06/03/2017.
 */

public class Configuracao_Controller {
    private static final String TAG = Configuracao_Controller.class.getSimpleName();
    private SQLiteDatabase db;

    public Configuracao_Controller(Context ctx) {
        db = ctx.openOrCreateDatabase(SQLiteDataBase.NOME_BANCO, Context.MODE_PRIVATE, null);
    }

    private ContentValues contentValues(Configuracao_Model model) {
        ContentValues values = new ContentValues();

        values.put(Configuracao_Model.CHAVE, model.getChave());
        values.put(Configuracao_Model.VALOR_1, model.getValor1());
        values.put(Configuracao_Model.VALOR_2, model.getValor2());

        return values;
    }

    private long inserir(Configuracao_Model model) {
        return inserir(contentValues(model));
    }

    private long inserir(ContentValues values) {
        return db.insert(Configuracao_Model.NOME_TABELA, "", values);
    }

    private int atualizar(Configuracao_Model model) {
        String _id = String.valueOf(model.getId());

        String where = Configuracao_Model._ID + "=?";
        String[] whereArgs = new String[] { _id };

        return atualizar(contentValues(model), where, whereArgs);
    }

    private int atualizar(ContentValues valores, String where, String[] whereArgs) {
        return db.update(Configuracao_Model.NOME_TABELA, valores, where, whereArgs);
    }

    public int deletar(long id) {
        String _id = String.valueOf(id);
        String[] whereArgs = new String[] { _id };
        return deletar(Configuracao_Model._ID + "=?", whereArgs);
    }

    private int deletar(String where, String[] whereArgs) {
        return db.delete(Configuracao_Model.NOME_TABELA, where, whereArgs);
    }

    public long salvar(Configuracao_Model model) {
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

    private List<Configuracao_Model> getList(String sql) {
        Cursor cursor = null;

        try {
            cursor = getCursorSQL(sql);
            if (cursor != null && cursor.moveToFirst()) {

                int idxId = cursor.getColumnIndex(Configuracao_Model._ID);
                int idxChave = cursor.getColumnIndex(Configuracao_Model.CHAVE);
                int idxValor1 = cursor.getColumnIndex(Configuracao_Model.VALOR_1);
                int idxValor2 = cursor.getColumnIndex(Configuracao_Model.VALOR_2);

                List<Configuracao_Model> list = new ArrayList<>();

                do {
                    Configuracao_Model model = new Configuracao_Model();

                    model.setId( idxId > -1 ? cursor.getLong(idxId) : 0);
                    model.setChave( idxChave > -1 ? cursor.getString(idxChave) : "");
                    model.setValor1( idxValor1 > -1 ? cursor.getString(idxValor1) : "");
                    model.setValor2( idxValor2 > -1 ? cursor.getString(idxValor2) : "");

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

    public List<Configuracao_Model> obterListaOrdenada() {
        String sql = " SELECT * " +
                " FROM " + Configuracao_Model.NOME_TABELA +
                " ORDER BY " + Configuracao_Model._ID;

        return getList(sql);
    }

    public Configuracao_Model obterConfiguracaoPorId(long id) {
        try {
            String sql = " SELECT * " +
                    " FROM " + Configuracao_Model.NOME_TABELA +
                    " WHERE " + Configuracao_Model._ID + " = " + id;

            List<Configuracao_Model> list = getList(sql);

            if (list != null && list.size() > 0) {
                return list.get(0);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        return null;
    }

    public Configuracao_Model obterConfiguracaoPorChave(String chave) {
        try {
            String sql = " SELECT * " +
                    " FROM " + Configuracao_Model.NOME_TABELA +
                    " WHERE " + Configuracao_Model.CHAVE + " = '" + chave + "'";

            List<Configuracao_Model> list = getList(sql);

            if (list != null && list.size() > 0) {
                return list.get(0);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        return null;
    }

    public void fechar() {
        if (db != null) {
            db.close();
        }
    }

}
