package br.com.appportaria.application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.appportaria.R;
import br.com.appportaria.application.domain.TabTransporte;
import br.com.appportaria.application.domain.Usuario;

public class UsuarioAdapter extends ArrayAdapter<Usuario>{

    private Context context;
    private List<Usuario> lista;

    public UsuarioAdapter(Context context,  List<Usuario> lista) {
        super(context, 0,lista);
        this.context = context;
        this.lista = lista;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = LayoutInflater.from(this.context).inflate(R.layout.activity_model_column_usuario, null);

        TextView textViewID = convertView.findViewById(R.id.txt_id_row);
        textViewID.setText(lista.get(position).getId().toString());

        TextView textViewlogin = convertView.findViewById(R.id.txt_login_row);
        textViewlogin.setText(lista.get(position).getNome());

        TextView textViewSenha = convertView.findViewById(R.id.txt_senha_row);
        textViewSenha.setText(lista.get(position).getSenha());

        TextView textViewSituacao = convertView.findViewById(R.id.txt_situacao_row);
        if (String.valueOf(lista.get(position).getAtivo()) != "false") {
              textViewSituacao.setText("Ativo");
        }else{
            textViewSituacao.setText("Inativo");
        }
        return convertView;
    }
}
