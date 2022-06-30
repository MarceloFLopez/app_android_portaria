package br.com.appportaria.application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.appportaria.R;
import br.com.appportaria.application.domain.TabOperacao;

public class OperacaoAdapter extends ArrayAdapter<TabOperacao> {

    Date data = new Date();

    private Context context;
    private List<TabOperacao> lista;

    public OperacaoAdapter(Context context, List<TabOperacao> lista) {
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = LayoutInflater.from(this.context).inflate(R.layout.activity_model_column_operacao, null);

        TextView textViewID = convertView.findViewById(R.id.txt_id_row);
        textViewID.setText(lista.get(position).getId().toString());

        TextView textViewOperacao = convertView.findViewById(R.id.txt_nome_row);
        textViewOperacao.setText(lista.get(position).getName().toString());

        TextView textViewObs = convertView.findViewById(R.id.txt_telefone_row);
        textViewObs.setText(lista.get(position).getObervacao().toString());

        return convertView;
    }
}

