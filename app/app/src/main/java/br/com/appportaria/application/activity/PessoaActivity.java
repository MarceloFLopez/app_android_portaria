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

import br.com.appportaria.application.adapter.PessoaAdapter;
import br.com.appportaria.application.dao.ConnRest;

import br.com.appportaria.application.domain.TabPessoa;
import br.com.appportaria.application.util.UtilApp;

public class PessoaActivity extends AppCompatActivity {

    private EditText editTextNomePessoa;//nome
    private EditText editTextCpfPessoa;//cnpj
    private EditText editTextTelefonePessoa;//telefone
    private Button buttonSave, buttonReturn;
    private ListView listViewPessoa;

    private UtilApp utilApp;
    private ConnRest client;

    private TextView textViewTitle;
    private TabPessoa pessoa;

    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa);

        if (android.os.Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        // initialize
        createFindByID();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // initialize
        createFindByID();
    }

    public void createFindByID(){

        editTextNomePessoa  = findViewById(R.id.txt_ColumnModelPessoa);
        editTextCpfPessoa =  findViewById(R.id.txt_ColumnCpfPessoa);
        editTextTelefonePessoa =  findViewById(R.id.txt_ColumTelefonePessoa);

        editTextNomePessoa.setImeOptions(EditorInfo.IME_ACTION_NONE);
        editTextNomePessoa.setImeActionLabel("Custom text", KeyEvent.KEYCODE_ENTER);
        editTextNomePessoa.setOnKeyListener(new View.OnKeyListener(){
            public boolean onKey(View v, int keyCode, KeyEvent event){
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    switch (keyCode){
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:

                            //enter
                            save(editTextNomePessoa, editTextCpfPessoa,editTextTelefonePessoa,v);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        buttonSave = findViewById(R.id.btn_save_pessoa);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save
                save(editTextNomePessoa,editTextCpfPessoa,editTextTelefonePessoa, view);
            }
        });

        buttonReturn = findViewById(R.id.btn_return_pessoa);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pessoa = null;
                createFindByID();
            }
        });

        //set title
        textViewTitle = findViewById(R.id.lbl_title_pessoa);
        if (pessoa != null){
            textViewTitle.setText("Selecione o item para atualizar: " + pessoa.getId().toString());

            // visible button
            buttonReturn.setVisibility(View.VISIBLE);
            // set value select
            editTextNomePessoa.setText(pessoa.getName());
            editTextCpfPessoa.setText(pessoa.getCpf());
            editTextTelefonePessoa.setText(pessoa.getTelefone());
        }else{
            textViewTitle.setText("CADASTRO DE NOVAS PESSOAS | PRESSIONE O NOME DA PESSOA PARA EDITAR!");

            // gone button
            buttonReturn.setVisibility(View.GONE);
            // set value clear
            editTextNomePessoa.setText("");
            editTextCpfPessoa.setText("");
            editTextTelefonePessoa.setText("");
        }
        // list obj
        list();
    }

    public void list(){
        /**
         * connn
         */
        client = new ConnRest();
        utilApp = new UtilApp();
        try {
            listViewPessoa = (ListView) findViewById(R.id.list_ColumnPessoa);
            // fill list
            List<TabPessoa> modelList = client.listPessoaAll();
            // set adapter
            if (modelList.size() > 0){
                PessoaAdapter modelAdapter = new PessoaAdapter(this, modelList);
                listViewPessoa.setAdapter(modelAdapter);

                // set item update
                listViewPessoa.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position >= 0) {
                            pessoa = new TabPessoa();
                            pessoa = (TabPessoa) parent.getItemAtPosition(position);

                            // select option
                            SelectOption(view);
                        }
                        return false;
                    }
                });
            }else{
                // null
                listViewPessoa.setAdapter(null);
            }
        }catch (Exception e){
            e.getStackTrace();
        }
    }

    public void save(EditText editTextNomePessoa,EditText editTextCpfPessoa,EditText editTextTelefonePessoa, View view) {
        client = new ConnRest();
        utilApp = new UtilApp();
        try {
            String value = editTextNomePessoa.getText().toString();
            String value1 = editTextCpfPessoa.getText().toString();
            String value2 = editTextTelefonePessoa.getText().toString();
            // validate and save value
            if (validateSave(value) && validateSave(value1) && validateSave(value2)) {

                // save
                TabPessoa t = new TabPessoa();
                if (pessoa != null){
                    // set id if select to edit item
                    t.setId(pessoa.getId());
                }
                t.setName(value);
                t.setCpf(value1);
                t.setTelefone(value2);
                client.salvarPessoa(t);

                infMsg(view,"Registro salvo com sucesso!!",true);
            }else{
                infMsg(view,"Valor inválido!",false);
            }
        }catch (Exception e){
            utilApp.msgToastLong("Script:" + e.getMessage(), view.getContext());
        }finally {
            pessoa = null;
            createFindByID();
        }
    }

    public void delete(View v){
        client = new ConnRest();
        utilApp = new UtilApp();
        try {
            client.removerPessoa(pessoa.getId().toString());
            pessoa = null;
            infMsg(v,"Registro deletado com sucesso!",true);
        }catch (Exception e){
            e.getStackTrace();
        }finally {
            createFindByID();
        }
    }

    /**
     * validateSave
     * @param value
     * @return
     */
    public boolean validateSave(String value){
        if (value.length() == 0){
            return false;
        }
        return true;
    }

    public void SelectOption(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        //set icone
        builder.setIcon(R.drawable.warning);
        builder.setTitle("ALERTA!");
        builder.setMessage("SELECIONE UMA OPÇÃO...");

        // set edit item
        builder.setNegativeButton(" EDITAR  ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                // refresh
                createFindByID();
            }
        });

        // set delete item
        builder.setPositiveButton(" DELETAR  ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                delete(view);
            }
        });

        builder.setNeutralButton("CANCELAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {}
        });

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

    public void infMsg(View view, String msg, boolean b){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        if (!b){
            //set icone
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