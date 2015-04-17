package br.edu.ucpel.agenda;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ucpel.agenda.bean.Contato;
import br.edu.ucpel.agenda.util.Mensagem;
import br.tche.ucpel.tads.miguel.ui.R;


/**
 *
 * @author Miguel Aguiar Barbosa
 */
public class ContatoActivity extends Activity {

    private static final int INCLUIR = 0;
    private Contato contato;
    EditText txtNome;
    EditText txtEndereco;
    EditText txtTelefone;
    private int idContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contato_layout);

        try {

            final Bundle data = (Bundle) getIntent().getExtras();
            int lint = data.getInt("tipo");
            if (lint == INCLUIR) {
                contato = new Contato();
            } else {
                contato = (Contato) data.getSerializable("agenda");
            }

            txtNome = (EditText) findViewById(R.id.edtNome);
            txtEndereco = (EditText) findViewById(R.id.edtEndereco);
            txtTelefone = (EditText) findViewById(R.id.edtTelefone);

            txtNome.setText(contato.getNome());
            txtEndereco.setText(contato.getEndereco());
            txtTelefone.setText(contato.getTelefone());
        } catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
    }

    public void btnConfirmar_click(View view) {
        try {
            String nome = txtNome.getText().toString();
            String endereco = txtEndereco.getText().toString();
            String telefone = txtTelefone.getText().toString();

            boolean validacao = true;

            if (nome == null || nome.equals("")) {
                validacao = false;
                txtNome.setError(getString(R.string.cadastro_valNome));
            }

            if (endereco == null || endereco.equals("")) {
                validacao = false;
                txtEndereco.setError(getString(R.string.cadastro_valEndereco));
            }

            if (telefone == null || telefone.equals("")) {
                validacao = false;
                txtTelefone.setError(getString(R.string.cadastro_valTelefone));
            }

            if (validacao) {
                Intent data = new Intent();
                contato.setNome(txtNome.getText().toString());
                contato.setEndereco(txtEndereco.getText().toString());
                contato.setTelefone(txtTelefone.getText().toString());

                data.putExtra("agenda", contato);
                setResult(Activity.RESULT_OK, data);
                Mensagem.Msg(this, getString(R.string.mensagem_cadastro));
                finish();

            }
        } catch (Exception e) {
            Mensagem.Msg(this, getString(R.string.mensagem_erro));
            trace("Erro : " + e.getMessage());
        }
    }

    public void btnCancelar_click(View view) {
        try {
            setResult(Activity.RESULT_CANCELED);
            finish();
        } catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
    }

    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void trace(String msg) {
        toast(msg);
    }
}
