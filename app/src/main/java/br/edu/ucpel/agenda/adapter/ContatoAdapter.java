package br.edu.ucpel.agenda.adapter;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.ucpel.agenda.bean.Contato;
import br.tche.ucpel.tads.miguel.ui.R;

/**
 *
 * @author Miguel Aguiar Barbosa
 */
public class ContatoAdapter extends BaseAdapter {

    private Context context;
    private List<Contato> lstContato;
    private LayoutInflater inflater;

    public ContatoAdapter(Context context, List<Contato> listAgenda) {
        this.context = context;
        this.lstContato = listAgenda;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void notifyDataSetChanged() {
        try {
            super.notifyDataSetChanged();
        } catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
    }

    public int getCount() {
        return lstContato.size();
    }

    public void remove(final Contato item) {
        this.lstContato.remove(item);
    }

    public void add(final Contato item) {
        this.lstContato.add(item);
    }

    public Object getItem(int position) {
        return lstContato.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        try {

            Contato contato = lstContato.get(position);
            ViewHolder holder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.contato_edit_layout, null);

                holder = new ViewHolder();
                holder.tvNome = (TextView) convertView.findViewById(R.id.txtNome);
                holder.tvEndereco = (TextView) convertView
                        .findViewById(R.id.txtEndereco);
                holder.tvTelefone = (TextView) convertView
                        .findViewById(R.id.txtTelefone);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvNome.setText(contato.getNome());
            holder.tvEndereco.setText("Rua: " + contato.getEndereco());
            holder.tvTelefone.setText("Tel: " + contato.getTelefone());

            return convertView;

        } catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }
        return convertView;
    }

    public void toast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    private void trace(String msg) {
        toast(msg);
    }

    static class ViewHolder {
        public TextView tvNome;
        public TextView tvEndereco;
        public TextView tvTelefone;
    }
}
