package br.edu.ucpel.agenda;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.edu.ucpel.agenda.adapter.ContatoAdapter;
import br.edu.ucpel.agenda.bean.Contato;
import br.edu.ucpel.agenda.dao.ContatoDAO;
import br.edu.ucpel.agenda.util.Mensagem;
import br.tche.ucpel.tads.miguel.ui.R;


/**
 *
 * @author Miguel Aguiar Barbosa
 */
public class IndexActivity extends ListActivity {

    private static final int INCLUIR = 0;
    private static final int ALTERAR = 1;

    private ContatoDAO lContaoDAO;
    List<Contato> lstContatos;
    ContatoAdapter adapter;

    boolean blnShort = false;
    int Posicao = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_layout);

        lContaoDAO = new ContatoDAO(this);
        lContaoDAO.open();

        lstContatos = lContaoDAO.Consultar();

        adapter = new ContatoAdapter(this, lstContatos);
        setListAdapter(adapter);

        registerForContextMenu(getListView());
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:
                InserirContato();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Contato lAgendaVO = null;

        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == Activity.RESULT_OK) {
                lAgendaVO = (Contato) data.getExtras().getSerializable("agenda");

                if (requestCode == INCLUIR) {
                    if (!lAgendaVO.getNome().equals("")) {
                        lContaoDAO.open();

                        lContaoDAO.Inserir(lAgendaVO);

                        lstContatos.add(lAgendaVO);
                    }
                } else if (requestCode == ALTERAR) {
                    lContaoDAO.open();
                    lContaoDAO.Alterar(lAgendaVO);

                    lstContatos.set(Posicao, lAgendaVO);
                }

                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
    }

    private void InserirContato() {
        try {
            Intent it = new Intent(this, ContatoActivity.class);
            it.putExtra("tipo", INCLUIR);
            startActivityForResult(it, INCLUIR);
        } catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
    }

    @Override
    protected void onResume() {
        lContaoDAO.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        lContaoDAO.close();
        super.onPause();
    }

    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void trace(String msg) {
        toast(msg);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        try {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            if (!blnShort) {
                Posicao = info.position;
            }
            blnShort = false;

            menu.setHeaderTitle("Selecione:");

            String[] menuItems = getResources().getStringArray(R.array.menu);
            for (int i = 0; i < menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        } catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Contato lAgendaVO = null;
        try {
            int menuItemIndex = item.getItemId();

            lAgendaVO = (Contato) getListAdapter().getItem(Posicao);

            if (menuItemIndex == 0) {

                Intent it = new Intent(this, ContatoActivity.class);
                it.putExtra("tipo", ALTERAR);
                it.putExtra("agenda", lAgendaVO);
                startActivityForResult(it, ALTERAR);
            } else if (menuItemIndex == 1) {
                lContaoDAO.Excluir(lAgendaVO);
                lstContatos.remove(lAgendaVO);
                adapter.notifyDataSetChanged();

                Mensagem.Msg(this, getString(R.string.mensagem_excluir));
            }
        } catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
        return true;

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Posicao = position;
        blnShort = true;
        this.openContextMenu(l);
    }
}
