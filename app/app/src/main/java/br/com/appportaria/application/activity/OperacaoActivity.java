package br.com.appportaria.application.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import br.com.appportaria.R;

import br.com.appportaria.application.adapter.OperacaoAdapter;
import br.com.appportaria.application.dao.ConnRest;

import br.com.appportaria.application.domain.TabOperacao;
import br.com.appportaria.application.util.UtilApp;

public class OperacaoActivity extends AppCompatActivity {

    private EditText editTextNomeOperacao;
    private EditText editTextDesecricao;
    private TextView editTextData;

    private Button buttonSave, buttonReturn;
    private ListView listViewOperacao;

    private UtilApp utilApp;
    private ConnRest client;

    private TextView textViewTitle;
    private TabOperacao operacao;

    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operacao);

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

    public void createFindByID(){
        editTextNomeOperacao  = findViewById(R.id.txt_ColumnModelOperacao);
        editTextDesecricao =  findViewById(R.id.txt_DescOperacao);

        editTextNomeOperacao.setImeOptions(EditorInfo.IME_ACTION_NONE);
        editTextNomeOperacao.setImeActionLabel("Custom text", KeyEvent.KEYCODE_ENTER);
        editTextNomeOperacao.setOnKeyListener(new View.OnKeyListener(){
            public boolean onKey(View v, int keyCode, KeyEvent event){
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    switch (keyCode){
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            save(editTextNomeOperacao, editTextDesecricao, v);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        buttonSave = findViewById(R.id.btn_save_operacao);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save(editTextNomeOperacao, editTextDesecricao, view);
            }
        });

        buttonReturn = findViewById(R.id.btn_return_opeacao);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operacao = null;
                createFindByID();
            }
        });

        textViewTitle = findViewById(R.id.lbl_title_operacao);
        if (operacao != null){
            textViewTitle.setText("Selecione o item para atualizar: " + operacao.getId().toString());
            buttonReturn.setVisibility(View.VISIBLE);
            editTextNomeOperacao.setText(operacao.getName());
            editTextDesecricao.setText(operacao.getObervacao());

        }else{
            textViewTitle.setText("CADASTRO DE NOVAS OPERAÇÕES | PRESSIONE O NOME DA OPERAÇÃO PARA EDITAR!");
            buttonReturn.setVisibility(View.GONE);
            editTextNomeOperacao.setText("");
            editTextDesecricao.setText("");
        }
        // list obj
        list();
    }

    public void list(){
        client = new ConnRest();
        utilApp = new UtilApp();
        try {
            listViewOperacao = findViewById(R.id.list_ColumnOperacao);
              List<TabOperacao> modelList = client.listOperacaoAll();
            if (modelList.size() > 0){
                OperacaoAdapter modelAdapter = new OperacaoAdapter(this, modelList);
                listViewOperacao.setAdapter(modelAdapter);
                listViewOperacao.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position >= 0) {
                            operacao = new TabOperacao();
                            operacao = (TabOperacao) parent.getItemAtPosition(position);
                            SelectOption(view);
                        }
                        return false;
                    }
                });
            }else{
                listViewOperacao.setAdapter(null);
            }
        }catch (Exception e){
            e.getStackTrace();
        }
    }

    public void save(EditText editTextNomeOperacao,EditText editTextDesecricao, View view) {
        client = new ConnRest();
        utilApp = new UtilApp();
        try {
            String value = editTextNomeOperacao.getText().toString();
            String value1 = editTextDesecricao.getText().toString();
            if (validateSave(value) && validateSave(value1)) {
                TabOperacao t = new TabOperacao();
                if (operacao != null){
                    t.setId(operacao.getId());
                }
                t.setName(value);
                t.setObervacao(value1);
                client.salvarOperacao(t);
                infMsg(view,"Registro salvo com sucesso!!",true);
            }else{
                infMsg(view,"Valor inválido!",false);
            }
        }catch (Exception e){
            utilApp.msgToastLong("Script:" + e.getMessage(), view.getContext());
        }finally {
            operacao = null;
            createFindByID();
        }
    }

    public void delete(View v){
        client = new ConnRest();
        utilApp = new UtilApp();
        try {
            client.removerOperacao(operacao.getId().toString());
            operacao = null;
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