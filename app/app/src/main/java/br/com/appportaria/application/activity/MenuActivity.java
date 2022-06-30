package br.com.appportaria.application.activity;

import androidx.appcompat.app.AppCompatActivity;
import br.com.appportaria.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button btn_em, btn_ps,btn_tr,btn_op,btn_sair;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        createFindByID();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btn_em.setOnClickListener(v -> {
            Intent i = new Intent(MenuActivity.this, EmpresaActivity.class);
            startActivity(i);
        });

        btn_ps.setOnClickListener(v -> {
            Intent i = new Intent(MenuActivity.this, PessoaActivity.class);
            startActivity(i);
        });

        btn_tr.setOnClickListener(v -> {
            Intent i = new Intent(MenuActivity.this, TransporteActivity.class);
            startActivity(i);
        });

        btn_op.setOnClickListener(v -> {
            Intent i = new Intent(MenuActivity.this, OperacaoActivity.class);
            startActivity(i);
        });

        btn_sair.setOnClickListener(v -> finish());
    }

    public void createFindByID(){
        btn_em = findViewById(R.id.btn_empresa);
        btn_ps = findViewById(R.id.btn_pessoa);
        btn_tr = findViewById(R.id.btn_transporte);
        btn_op = findViewById(R.id.btn_operacao);
        btn_sair = findViewById(R.id.btn_sair);
    }
}