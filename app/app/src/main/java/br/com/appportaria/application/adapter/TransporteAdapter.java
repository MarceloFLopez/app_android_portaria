package br.com.appportaria.application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.appportaria.R;
import br.com.appportaria.application.domain.TabPessoa;
import br.com.appportaria.application.domain.TabTransporte;

public class TransporteAdapter extends ArrayAdapter<TabTransporte> {

    private Context context;
    private List<TabTransporte> lista;

    public TransporteAdapter(Context context,  List<TabTransporte> lista) {
        super(context, 0,lista);
        this.context = context;
        this.lista = lista;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = LayoutInflater.from(this.context).inflate(R.layout.activity_model_column, null);

        TextView textViewID = convertView.findViewById(R.id.txt_id_row);
        textViewID.setText(lista.get(position).getId().toString());

        TextView textViewCpf = convertView.findViewById(R.id.txt_document_row);
        textViewCpf.setText(lista.get(position).getPlaca());

        TextView textViewNome = convertView.findViewById(R.id.txt_nome_row);
        textViewNome.setText(lista.get(position).getModelo());

        TextView textViewTelefone = convertView.findViewById(R.id.txt_telefone_row);
        textViewTelefone.setText(lista.get(position).getTipo());

        return convertView;
    }
}
