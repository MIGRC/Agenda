package br.edu.ucpel.agenda;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import br.edu.ucpel.agenda.dao.UsuarioDAO;
import br.edu.ucpel.agenda.util.Mensagem;
import br.tche.ucpel.tads.miguel.ui.R;


/**
 *
 * @author Miguel Aguiar Barbosa
 */
public class LoginActivity extends Activity {

    private EditText edtUsuario, edtSenha;
    private UsuarioDAO helper;
    private CheckBox ckbConectado;

    private static final String MANTER_CONECTADO = "manter_conectado";
    private static final String PREFERENCE_NAME = "LoginActivityPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        edtUsuario = (EditText) findViewById(R.id.login_edtUsuario);
        edtSenha = (EditText) findViewById(R.id.login_edtSenha);
        ckbConectado = (CheckBox) findViewById(R.id.login_ckbConectado);

        helper = new UsuarioDAO(this);

        SharedPreferences pref = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        boolean conectado = pref.getBoolean(MANTER_CONECTADO, false);

        if (conectado) {
            inicializarIndexActivity();
        }
    }

    public void logar(View v) {
        String usuario = edtUsuario.getText().toString();
        String senha = edtSenha.getText().toString();

        boolean validacao = true;

        if (usuario == null || usuario.equals("")) {
            validacao = false;
            edtUsuario.setError(getString(R.string.login_valUsuario));
        }

        if (senha == null || senha.equals("")) {
            validacao = false;
            edtSenha.setError(getString(R.string.login_valSenha));
        }

        if (validacao) {
            if (helper.logar(usuario, senha)) {
                if (ckbConectado.isChecked()) {
                    SharedPreferences sp = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();

                    editor.putBoolean(MANTER_CONECTADO, true);
                    editor.commit();
                }

                inicializarIndexActivity();
            } else {
                Mensagem.Msg(this, getString(R.string.msg_login_incorreto));
            }
        }
    }

    private void inicializarIndexActivity() {
        startActivity(new Intent(this, IndexActivity.class));
        finish();
    }
}
