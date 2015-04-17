package br.edu.ucpel.agenda.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 * @author Miguel Aguiar Barbosa
 */
public class DatabaseHelper extends SQLiteOpenHelper{
	
	public static final String TBL_AGENDA = "agenda";
        public static final String TBL_USUARIO = "usuarios";
        
        public static final String AGENDA_ID = "_id";
	public static final String AGENDA_NOME = "nome";
	public static final String AGENDA_ENDERECO = "endereco";
	public static final String AGENDA_TELEFONE = "telefone";

	private static final String DATABASE_NAME = "agenda.db";
	private static final int DATABASE_VERSION = 1;

	public DatabaseHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		//Tabela de usuários
		db.execSQL("create table usuarios(_id integer primary key autoincrement, "
				+ "nome text not null, login text not null, senha text not null)");
		
		//Tabela de Agenda
		db.execSQL("create table " + TBL_AGENDA
			+ "( " + AGENDA_ID + " integer primary key autoincrement, "
			+ AGENDA_NOME + " text not null, " + AGENDA_ENDERECO
			+ " text not null, " + AGENDA_TELEFONE + " text not null);");
		
		//Inserir um Usuário
		db.execSQL("insert into usuarios(nome, login, senha) values('Miguel', 'admin', '123')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TBL_AGENDA);
            db.execSQL("DROP TABLE IF EXISTS " + TBL_USUARIO);
            onCreate(db);
		
	}
	
	public static class Usuarios{
		public static final String TABELA = "usuarios";
		public static final String _ID 	  = "_id";
		public static final String NOME   = "nome";
		public static final String LOGIN  = "login";
		public static final String SENHA  = "senha";
		
		public static final String[] COLUNAS = new String[]{
				_ID, NOME, LOGIN, SENHA
		};
	}

}
