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
import br.com.appportaria.application.adapter.UsuarioAdapter;
import br.com.appportaria.application.dao.ConnRest;
import br.com.appportaria.application.domain.Usuario;
import br.com.appportaria.application.util.UtilApp;

public class UsuarioActivity extends AppCompatActivity {

    private EditText editTextUsuario;
    private EditText editTextSenha;
    private EditText editTextSituacao;
    private Button buttonSave, buttonReturn;
    private ListView listViewUsuario;

    private UtilApp utilApp;
    private ConnRest client;

    private TextView textViewTitle;
    private Usuario usuario;

    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

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

    public void createFindByID() {
        editTextUsuario = findViewById(R.id.txt_ColumnUsuario);
        editTextSenha = findViewById(R.id.txt_ColumnSenha);
        editTextSituacao = findViewById(R.id.txt_ColumSituacao);

        editTextUsuario.setImeOptions(EditorInfo.IME_ACTION_NONE);
        editTextUsuario.setImeActionLabel("Custom text", KeyEvent.KEYCODE_ENTER);
        editTextUsuario.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:

                            //enter
                            save(editTextUsuario, editTextSenha,editTextSituacao, v);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        buttonSave = findViewById(R.id.btn_save_usuario);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save
                save(editTextUsuario, editTextSenha,editTextSituacao,  view);
            }
        });

        buttonReturn = findViewById(R.id.btn_return_usuario);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = null;
                createFindByID();
            }
        });

        //set title
        textViewTitle = findViewById(R.id.lbl_title_usuario);
        if (usuario != null) {
            textViewTitle.setText("Selecione o item para atualizar: " + usuario.getId().toString());

            // visible button
            buttonReturn.setVisibility(View.VISIBLE);
            // set value select
            editTextUsuario.setText(usuario.getNome().toUpperCase());
            editTextSenha.setText(usuario.getSenha().toUpperCase());
            editTextSituacao.setText(String.valueOf(usuario.getAtivo()));
        } else {
            textViewTitle.setText("CADASTRO DE NOVOS USUÁRIOS | PRESSIONE O NOME DO USUÁRIO PARA EDITAR!");

            // gone button
            buttonReturn.setVisibility(View.GONE);
            // set value clear
            editTextUsuario.setText("");
            editTextSenha.setText("");
            editTextSituacao.setText("");
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
            listViewUsuario = (ListView) findViewById(R.id.list_ColumnUsuarios);
            // fill list
            List<Usuario> modelList = client.listUsuariosAll();
            // set adapter
            if (modelList.size() > 0){
                UsuarioAdapter modelAdapter = new UsuarioAdapter(this, modelList);
                listViewUsuario.setAdapter(modelAdapter);

                // set item update
                listViewUsuario.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position >= 0) {
                            usuario = new Usuario();
                            usuario = (Usuario) parent.getItemAtPosition(position);

                            // select option
                            SelectOption(view);
                        }
                        return false;
                    }
                });
            }else{
                // null
                listViewUsuario.setAdapter(null);
            }
        }catch (Exception e){
            e.getStackTrace();
        }
    }
    public void save(EditText editTextUsuario,EditText editTextSenha,EditText editTextSituacao, View view) {
        client = new ConnRest();
        utilApp = new UtilApp();
        try {
            String value = editTextUsuario.getText().toString().toUpperCase();
            String value1 = editTextSenha.getText().toString().toUpperCase();
            String value2 = String.valueOf(editTextSituacao.getText());

            // validate and save value
            if (validateSave(value) && validateSave(value1) && validateSave(value2)) {

                // save
                Usuario t = new Usuario();
                if (usuario != null){
                    // set id if select to edit item
                    t.setId(usuario.getId());
                }
                t.setNome(value);
                t.setSenha(value1);
                t.setAtivo(Boolean.valueOf(value2));
                client.salvarUsuarios(t);

                infMsg(view,"Registro salvo com sucesso!!",true);
            }else{
                infMsg(view,"Valor inválido!",false);
            }
        }catch (Exception e){
            utilApp.msgToastLong("Script:" + e.getMessage(), view.getContext());
        }finally {
            usuario = null;
            createFindByID();
        }
    }

    public void delete(View v){
        client = new ConnRest();
        utilApp = new UtilApp();
        try {
            client.removerUsuarios(usuario.getId().toString());
            usuario = null;
            infMsg(v,"Registro deletado com sucesso!",true);
        }catch (Exception e){
            e.getStackTrace();
             infMsg(v,"Não foi possível deletar o registro",false);
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