package br.com.appportaria.application.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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

import br.com.appportaria.R;
import br.com.appportaria.application.adapter.EmpresaAdapter;
import br.com.appportaria.application.dao.ConnRest;
import br.com.appportaria.application.domain.TabEmpresa;
import br.com.appportaria.application.util.UtilApp;

public class EmpresaActivity extends AppCompatActivity {

    private EditText editTextNomeEmpresa;//nome
    private EditText editTextCnpjEmpresa;//cnpj
    private EditText editTextTelefoneEmpresa;//telefone
    private Button btn_voltar;

    private UtilApp utilApp;
    private ConnRest client;

    private TabEmpresa empresa;

    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        createFindByID();
    }

    @Override
    protected void onResume() {
        super.onResume();
        createFindByID();
    }

    public void createFindByID(){
        editTextNomeEmpresa  = findViewById(R.id.txt_ColumnModelEmpresa);
        editTextCnpjEmpresa =  findViewById(R.id.txt_ColumnCnpjEmpresa);
        editTextTelefoneEmpresa =  findViewById(R.id.txt_ColumTelefoneEmpresa);

        editTextNomeEmpresa.setImeOptions(EditorInfo.IME_ACTION_NONE);
        editTextNomeEmpresa.setImeActionLabel("Custom text", KeyEvent.KEYCODE_ENTER);
        editTextNomeEmpresa.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN){
                switch (keyCode){
                    case KeyEvent.KEYCODE_DPAD_CENTER:
                    case KeyEvent.KEYCODE_ENTER:
                        save(editTextNomeEmpresa, editTextCnpjEmpresa,editTextTelefoneEmpresa,v);
                        return true;
                    default:
                        break;
                }
            }
            return false;
        });

        Button buttonSave = findViewById(R.id.btn_save_empresa);
        buttonSave.setOnClickListener(view -> {
            save(editTextNomeEmpresa,editTextCnpjEmpresa,editTextTelefoneEmpresa, view);
        });

        Button buttonReturn = findViewById(R.id.btn_return_empresa);
        buttonReturn.setOnClickListener(view -> {
            empresa = null;
            createFindByID();
        });

        TextView textViewTitle = findViewById(R.id.lbl_title_empresa);
        if (empresa != null){
            textViewTitle.setText("Selecione o item para atualizar: " + empresa.getId().toString());
            buttonReturn.setVisibility(View.VISIBLE);
            editTextNomeEmpresa.setText(empresa.getName());
            editTextCnpjEmpresa.setText(empresa.getCnpj());
            editTextTelefoneEmpresa.setText(empresa.getTelefone());
        }else{
            textViewTitle.setText("CADASTRO DE NOVAS EMPRESAS | PRESSIONE O NOME DA EMPRESA PARA EDITAR!");
            buttonReturn.setVisibility(View.GONE);
            editTextNomeEmpresa.setText("");
            editTextCnpjEmpresa.setText("");
            editTextTelefoneEmpresa.setText("");
        }
        list();
    }

    public void list(){
        client = new ConnRest();
        utilApp = new UtilApp();
        try {
            ListView listViewEmpresa = (ListView) findViewById(R.id.list_ColumnEmpresa);
            List<TabEmpresa> modelList = client.listEmpresaAll();
            if (modelList.size() > 0){
                EmpresaAdapter modelAdapter = new EmpresaAdapter(this, modelList);
                listViewEmpresa.setAdapter(modelAdapter);
                listViewEmpresa.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position >= 0) {
                            empresa = new TabEmpresa();
                            empresa = (TabEmpresa) parent.getItemAtPosition(position);
                            SelectOption(view);
                        }
                        return false;
                    }
                });
            }else{
                listViewEmpresa.setAdapter(null);
            }
        }catch (Exception e){
            e.getStackTrace();
        }
    }

    public void save(EditText editTextNomeEmpresa,EditText editTextCnpjEmpresa,EditText editTextTelefoneEmpresa, View view) {
        client = new ConnRest();
        utilApp = new UtilApp();
        try {
            String value = editTextNomeEmpresa.getText().toString();
            String value1 = editTextCnpjEmpresa.getText().toString();
            String value2 = editTextTelefoneEmpresa.getText().toString();
            if (validateSave(value) && validateSave(value1) && validateSave(value2)) {
                TabEmpresa t = new TabEmpresa();
                if (empresa != null){
                    t.setId(empresa.getId());
                }
                t.setName(value);
                t.setCnpj(value1);
                t.setTelefone(value2);
                client.salvarEmpresa(t);

                 infMsg(view,"Registro salvo com sucesso!!",true);
            }else{
                infMsg(view,"Valor inválido!",false);
            }
        }catch (Exception e){
            utilApp.msgToastLong("Script:" + e.getMessage(), view.getContext());
        }finally {
            empresa = null;
            createFindByID();
        }
    }

    public void delete(View v){
        client = new ConnRest();
        utilApp = new UtilApp();
        try {
            client.removeEmpresa(empresa.getId().toString());
            empresa = null;
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
        alerta.show();
    }

    public void infMsg(View view, String msg, boolean b){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        if (!b){
            builder.setIcon(R.drawable.warning);
            builder.setTitle("ALERTA!");
            builder.setMessage(msg);
        }else if (b){
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