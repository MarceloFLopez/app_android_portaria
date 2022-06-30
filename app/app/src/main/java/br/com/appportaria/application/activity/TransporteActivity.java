package br.com.appportaria.application.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import br.com.appportaria.R;

import br.com.appportaria.application.adapter.TransporteAdapter;
import br.com.appportaria.application.dao.ConnRest;

import br.com.appportaria.application.domain.TabTransporte;
import br.com.appportaria.application.util.UtilApp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class TransporteActivity extends AppCompatActivity {

    private EditText editTextModelo;
    private EditText editTextPlaca;
    private EditText editTextTipo;
    private Button buttonSave, buttonReturn;
    private ListView listViewTransporte;

    private UtilApp utilApp;
    private ConnRest client;

    private TextView textViewTitle;
    private TabTransporte transporte;

    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporte);
        if (android.os.Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        createFindByID();
    }

    @Override
    protected void onResume() {
        super.onResume();
        createFindByID();
    }

    public void createFindByID() {
        editTextModelo = findViewById(R.id.txt_ColumnModelTransporte);
        editTextPlaca = findViewById(R.id.txt_ColumnPlacaTransporte);
        editTextTipo = findViewById(R.id.txt_ColumTipoTransporte);

        editTextModelo.setImeOptions(EditorInfo.IME_ACTION_NONE);
        editTextModelo.setImeActionLabel("Custom text", KeyEvent.KEYCODE_ENTER);
        editTextModelo.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            save(editTextModelo, editTextPlaca, editTextTipo, v);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        buttonSave = findViewById(R.id.btn_save_transporte);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save(editTextModelo, editTextPlaca, editTextTipo, view);
            }
        });

        buttonReturn = findViewById(R.id.btn_return_transporte);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transporte = null;
                createFindByID();
            }
        });

        textViewTitle = findViewById(R.id.lbl_title_transporte);
        if (transporte != null) {
            textViewTitle.setText("Selecione o item para atualizar: " + transporte.getId().toString());
            buttonReturn.setVisibility(View.VISIBLE);
            editTextModelo.setText(transporte.getModelo().toUpperCase());
            editTextPlaca.setText(transporte.getPlaca().toUpperCase());
            editTextTipo.setText(transporte.getTipo().toUpperCase());
        } else {
            textViewTitle.setText("CADASTRO DE NOVOS USUÁRIOS | PRESSIONE O NOME DO USUÁRIO PARA EDITAR!");
            buttonReturn.setVisibility(View.GONE);
            editTextModelo.setText("");
            editTextPlaca.setText("");
            editTextTipo.setText("");
        }
        list();
    }

    public void list(){
        client = new ConnRest();
        utilApp = new UtilApp();
        try {
            listViewTransporte = (ListView) findViewById(R.id.list_ColumnTransporte);
            List<TabTransporte> modelList = client.listTransporteAll();
            if (modelList.size() > 0){
                TransporteAdapter modelAdapter = new TransporteAdapter(this, modelList);
                listViewTransporte.setAdapter(modelAdapter);
                listViewTransporte.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position >= 0) {
                            transporte = new TabTransporte();
                            transporte = (TabTransporte) parent.getItemAtPosition(position);
                            SelectOption(view);
                        }
                        return false;
                    }
                });
            }else{
                listViewTransporte.setAdapter(null);
            }
        }catch (Exception e){
            e.getStackTrace();
        }
    }
    public void save(EditText editTextModelo,EditText editTextPlaca,EditText editTextTipo, View view) {
        client = new ConnRest();
        utilApp = new UtilApp();
        try {
            String value = editTextModelo.getText().toString().toUpperCase();
            String value1 = editTextPlaca.getText().toString().toUpperCase();
            String value2 = editTextTipo.getText().toString().toUpperCase();
            if (validateSave(value) && validateSave(value1) && validateSave(value2)) {
                TabTransporte t = new TabTransporte();
                if (transporte != null){
                    t.setId(transporte.getId());
                }
                t.setModelo(value);
                t.setPlaca(value1);
                t.setTipo(value2);
                client.salvarTransporte(t);
                infMsg(view,"Registro salvo com sucesso!!",true);
            }else{
                infMsg(view,"Valor inválido!",false);
            }
        }catch (Exception e){
            utilApp.msgToastLong("Script:" + e.getMessage(), view.getContext());
        }finally {
            transporte = null;
            createFindByID();
        }
    }

    public void delete(View v){
        client = new ConnRest();
        utilApp = new UtilApp();
        try {
            client.removerTransporte(transporte.getId().toString());
            transporte = null;
            infMsg(v,"Registro deletado com sucesso!",true);
        }catch (Exception e){
            e.getStackTrace();
        }finally {
            createFindByID();
        }
    }

    public boolean validateSave(String value){
        if (value.length() == 0){
            return false;
        }
        return true;
    }

    public void SelectOption(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setIcon(R.drawable.warning);
        builder.setTitle("ALERTA!");
        builder.setMessage("SELECIONE UMA OPÇÃO...");
        builder.setNegativeButton(" EDITAR  ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                createFindByID();
            }
        });
        builder.setPositiveButton(" DELETAR  ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                delete(view);
            }
        });
        builder.setNeutralButton("CANCELAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {}
        });
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

    public void infMsg(View view, String msg, boolean b){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        if (!b){
            builder.setIcon(R.drawable.warning);
            builder.setTitle("ALERTA!");
            builder.setMessage(msg);
        }else if (b){
            //set icone
            builder.setIcon(R.drawable.inf_alert);
            builder.setTitle("INFO!");
            builder.setMessage(msg);
        }
        builder.setNegativeButton(" SAIR  ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {}
        });
        alerta = builder.create();
        alerta.show();
    }
}