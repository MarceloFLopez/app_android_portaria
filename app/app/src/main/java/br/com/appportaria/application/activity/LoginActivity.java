package br.com.appportaria.application.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import br.com.appportaria.R;
import br.com.appportaria.application.dao.ConnRest;
import br.com.appportaria.application.domain.Usuario;
import br.com.appportaria.application.util.UtilApp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText ed1,ed2;
    private Button btn1, btn2;
    private ListView  view;
    private UtilApp utilApp;
    private ConnRest client;

    private TextView textViewTitle;
    private Usuario usuario;

    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ed1 = findViewById(R.id.user);
        ed2 = findViewById(R.id.pass);

        btn1 = findViewById(R.id.btn_login);
        btn2 = findViewById(R.id.btn_sair);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Login();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void Login(){

       String username = ed1.getText().toString();
       String password = ed2.getText().toString();

//        client = new ConnRest();
//        utilApp = new UtilApp();
//
//        List<Usuario> list = client.listUsuariosAll();
//
//        for (int i = 0; i < list.size(); i++) {
//            if(list.get(i).getNome().equalsIgnoreCase(username) && list.get(i).getSenha().equalsIgnoreCase(password)) {
//                Toast.makeText(this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();
//                break;
//            }else {
//                Toast.makeText(this, "Usuário ou Senha inválido!", Toast.LENGTH_SHORT).show();
//            }
//        }


        if(username.equals("") && password.equals("")){
            Toast.makeText(this, "FAVOR DIGITAR A SENHA OU PASSWORD CORRETO", Toast.LENGTH_SHORT).show();
        }else if(username.equals("san") && password.equals("123")){
            Toast.makeText(this, "LOGIN EFETUADO COM SUCESSO!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "SENHA OU PASSWORD INVÁLIDO!", Toast.LENGTH_SHORT).show();
        }
    }

    public void list(){
        client = new ConnRest();
        utilApp = new UtilApp();
        try {
            List<Usuario> modelList = client.listUsuariosAll();
        }catch (Exception e){
            e.getStackTrace();
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