package br.com.impacta.breathmobi_app.View;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import br.com.impacta.breathmobi_app.Controller.ClienteHelper;
import br.com.impacta.breathmobi_app.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_cadastrar;
    private TextView tv_esqueceuSenha;
    private TextView tv_senha;

    private CheckBox ch_exibirsenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.telainicial_login);

        inicializarVariavel();

        tv_cadastrar.setOnClickListener(this);
        tv_esqueceuSenha.setOnClickListener(this);
        ch_exibirsenha.setOnClickListener(this);

    }

    private void inicializarVariavel() {
        tv_cadastrar = (TextView) findViewById(R.id.telainicial_cadastrar);
        tv_esqueceuSenha = (TextView) findViewById(R.id.telainicial_esqueceusenha);
        tv_senha = (TextView) findViewById(R.id.telainicial_senha);
        ch_exibirsenha = (CheckBox) findViewById(R.id.telainicial_exibirsenha);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.telainicial_cadastrar :
                Intent mIntent = new Intent(getApplicationContext(), CadastroCliente.class);
                startActivity(mIntent);
                break;
            case R.id.telainicial_esqueceusenha :
                Intent intent = new Intent(getApplicationContext(), CadastroCliente.class);
                startActivity(intent);
                break;
            case R.id.telainicial_exibirsenha :
                if (ch_exibirsenha.isChecked()) {
                    Log.d("CHECK", "OK");
                    tv_senha.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    Log.d("CHECK", "FALSE");
                    tv_senha.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;

        }
    }
}
