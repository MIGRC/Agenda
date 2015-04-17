package br.edu.ucpel.agenda.dao;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import br.edu.ucpel.agenda.bean.Contato;
import br.edu.ucpel.agenda.db.DatabaseHelper;

/**
 *
 * @author Miguel Aguiar Barbosa
 */
public class ContatoDAO {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    private String[] colunas = {dbHelper.AGENDA_ID, dbHelper.AGENDA_NOME,
        dbHelper.AGENDA_ENDERECO, dbHelper.AGENDA_TELEFONE};

    public ContatoDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    private SQLiteDatabase getDatabase() {
        if (database == null) {
            database = dbHelper.getWritableDatabase();
        }

        return database;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long Inserir(Contato contato) {
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.AGENDA_NOME, contato.getNome());
        cv.put(dbHelper.AGENDA_ENDERECO, contato.getEndereco());
        cv.put(dbHelper.AGENDA_TELEFONE, contato.getTelefone());

        return database.insert(dbHelper.TBL_AGENDA, null, cv);
    }

    public int Alterar(Contato contato) {
        long id = contato.getId();
        ContentValues values = new ContentValues();

        values.put(dbHelper.AGENDA_NOME, contato.getNome());
        values.put(dbHelper.AGENDA_ENDERECO, contato.getEndereco());
        values.put(dbHelper.AGENDA_TELEFONE, contato.getTelefone());

        return database.update(dbHelper.TBL_AGENDA, values, dbHelper.AGENDA_ID + " = " + id, null);
    }

    public void Excluir(Contato pValue) {
        long id = pValue.getId();

        database.delete(dbHelper.TBL_AGENDA, dbHelper.AGENDA_ID + " = " + id, null);
    }

    public List<Contato> Consultar() {
        List<Contato> lstAgenda = new ArrayList<Contato>();

        Cursor cursor = database.query(dbHelper.TBL_AGENDA, colunas, null, null,
                null, null, dbHelper.AGENDA_NOME);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Contato lContatoVO = cursorToContato(cursor);
            lstAgenda.add(lContatoVO);
            cursor.moveToNext();
        }

        cursor.close();
        return lstAgenda;
    }

    private Contato cursorToContato(Cursor cursor) {
        Contato lContatoVO = new Contato();
        lContatoVO.setId(cursor.getInt(0));
        lContatoVO.setNome(cursor.getString(1));
        lContatoVO.setEndereco(cursor.getString(2));
        lContatoVO.setTelefone(cursor.getString(3));
        return lContatoVO;
    }
}
