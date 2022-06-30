package br.com.appportaria.application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.appportaria.R;
import br.com.appportaria.application.domain.TabEmpresa;

public class EmpresaAdapter  extends ArrayAdapter<TabEmpresa> {
    private final Context context;
    private final List<TabEmpresa> lista;

    public EmpresaAdapter(Context context, List<TabEmpresa> lista) {
        super(context,0, lista);
        this.context = context;
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = LayoutInflater.from(this.context).inflate(R.layout.activity_model_column, null);

        TextView textViewID = (TextView) convertView.findViewById(R.id.txt_id_row);
        textViewID.setText(lista.get(position).getId().toString());

        TextView textViewCnpj = (TextView) convertView.findViewById(R.id.txt_document_row);
        textViewCnpj.setText(lista.get(position).getCnpj());

        TextView textViewTelefone = (TextView) convertView.findViewById(R.id.txt_telefone_row);
        textViewTelefone.setText(lista.get(position).getTelefone());

        TextView textViewColumn = (TextView) convertView.findViewById(R.id.txt_nome_row);
        textViewColumn.setText(lista.get(position).getName());

        return convertView;
    }
}
