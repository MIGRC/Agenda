package br.edu.ucpel.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.ucpel.agenda.bean.Usuario;
import br.edu.ucpel.agenda.db.DatabaseHelper;

/**
 *
 * @author Miguel Aguiar Barbosa
 */
public class UsuarioDAO {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public UsuarioDAO(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    private SQLiteDatabase getDatabase() {
        if (database == null) {
            database = databaseHelper.getWritableDatabase();
        }

        return database;
    }

    private Usuario criarUsuario(Cursor c) {
        Usuario u = new Usuario(
                c.getInt(c.getColumnIndex(DatabaseHelper.Usuarios._ID)),
                c.getString(c.getColumnIndex(DatabaseHelper.Usuarios.NOME)),
                c.getString(c.getColumnIndex(DatabaseHelper.Usuarios.LOGIN)),
                c.getString(c.getColumnIndex(DatabaseHelper.Usuarios.SENHA))
        );

        return u;
    }

    public List<Usuario> listaUsuarios() {
        Cursor c = getDatabase().query(DatabaseHelper.Usuarios.TABELA,
                DatabaseHelper.Usuarios.COLUNAS,
                null, null, null,
                DatabaseHelper.Usuarios.NOME,
                null, null);

        List<Usuario> usuarios = new ArrayList<Usuario>();
        while (c.moveToNext()) {
            Usuario u = criarUsuario(c);
            usuarios.add(u);
        }

        c.close();
        return usuarios;
    }

    public long salvarUsuario(Usuario u) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.Usuarios.NOME, Usuario.getNome());
        cv.put(DatabaseHelper.Usuarios.LOGIN, Usuario.getLogin());
        cv.put(DatabaseHelper.Usuarios.SENHA, Usuario.getSenha());

        if (Usuario.get_id() != null) {
            return getDatabase().update(DatabaseHelper.Usuarios.TABELA, cv, "_id = ?", new String[]{Usuario.get_id().toString()});
        } else {
            return getDatabase().insert(DatabaseHelper.Usuarios.TABELA, null, cv);
        }
    }

    public boolean removerUsuario(int id) {
        return getDatabase().delete(DatabaseHelper.Usuarios.TABELA, "_id = ?", new String[]{Integer.toString(id)}) > 0;
    }

    public Usuario buscarUsuarioPorId(int id) {
        Cursor c = getDatabase().query(DatabaseHelper.Usuarios.TABELA,
                DatabaseHelper.Usuarios.COLUNAS,
                "_id = ?",
                new String[]{Integer.toString(id)},
                null, null, null);
        if (c.moveToNext()) {
            Usuario u = criarUsuario(c);
            c.close();
            return u;
        } else {
            return null;
        }
    }

    public boolean logar(String usuario, String senha) {
        Cursor c = getDatabase().query(DatabaseHelper.Usuarios.TABELA,
                null, "LOGIN = ? AND SENHA = ?", new String[]{usuario, senha}, null, null, null);

        if (c.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public void close() {
        databaseHelper.close();
        database = null;
    }
}
