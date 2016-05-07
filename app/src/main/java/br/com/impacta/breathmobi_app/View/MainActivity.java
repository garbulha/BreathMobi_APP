package br.com.impacta.breathmobi_app.View;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;


import java.util.Map;

import br.com.impacta.breathmobi_app.Controller.ClienteHelper;
import br.com.impacta.breathmobi_app.Model.Cliente;
import br.com.impacta.breathmobi_app.R;
import br.com.impacta.breathmobi_app.Util.ComumActivity;
import br.com.impacta.breathmobi_app.Util.UtilLogin;


public class MainActivity extends ComumActivity implements View.OnClickListener {
    private TextView tv_login;
    private TextView tv_cadastrar;
    private TextView tv_esqueceuSenha;
    private TextView tv_senha;

    private Button btn_login;

    private Firebase firebase;
    private Cliente cliente;

    private ClienteHelper cHelper;

    private CheckBox ch_exibirsenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.telainicial_login);
        firebase.setAndroidContext(this);

        inicializarVariavel();

        tv_cadastrar.setOnClickListener(this);
        tv_esqueceuSenha.setOnClickListener(this);
        ch_exibirsenha.setOnClickListener(this);
        btn_login.setOnClickListener(this);


    }

    private void inicializarVariavel() {
        tv_cadastrar = (TextView) findViewById(R.id.telainicial_cadastrar);
        tv_esqueceuSenha = (TextView) findViewById(R.id.telainicial_esqueceusenha);
        tv_senha = (TextView) findViewById(R.id.telainicial_senha);
        tv_login = (TextView) findViewById(R.id.telainicial_login);
        ch_exibirsenha = (CheckBox) findViewById(R.id.telainicial_exibirsenha);
        btn_login = (Button) findViewById(R.id.btn_login);
        firebase = UtilLogin.getFirebase();
        progressBar = (ProgressBar) findViewById(R.id.login_progressbar);
        cHelper = new ClienteHelper();


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.telainicial_cadastrar:

                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Novo Cadastro");
                builder.setView(R.layout.cadastro_cliente);

                builder.setPositiveButton("Inserir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openProgressBar();
                        cHelper = new ClienteHelper(dialog);
                        cliente = cHelper.pegaCliente();
                        cadastrarUsuario(cliente);

                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();
                break;

            case R.id.btn_login:
                openProgressBar();
                //Verifica  no firebase se esta logado
                firebase.authWithPassword(
                        tv_login.getText().toString(),
                        tv_senha.getText().toString(),
                        new Firebase.AuthResultHandler() {
                            @Override
                            public void onAuthenticated(AuthData authData) {
                                cHelper.saveIdSP(MainActivity.this, authData.getUid());
                                closeProgressBar();
                                showToast("Autenticado");
                                Intent mIntent = new Intent(getApplicationContext(), Logado.class);
                                startActivity(mIntent);
                                finish();
                            }

                            @Override
                            public void onAuthenticationError(FirebaseError firebaseError) {
                                closeProgressBar();
                                showToast("E-mail ou Senha Inválido!");
                            }
                        }
                );
                break;

            case R.id.telainicial_esqueceusenha:

                final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("Esqueceu Senha");
                builder1.setView(R.layout.esqueceusenha);

                builder1.setPositiveButton("Inserir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        showToast("Aguarde o email!");
                    }
                });
                builder1.show();
                break;
            case R.id.telainicial_exibirsenha:
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


    // Metodo para cadastrar Usuario novo //
    private void cadastrarUsuario(final Cliente cliente) {
        try {
            firebase.createUser(
                    cliente.getUsuario(),
                    cliente.getSenha(),
                    new Firebase.ValueResultHandler<Map<String, Object>>() {
                        @Override
                        public void onSuccess(Map<String, Object> stringObjectMap) {
                            cliente.setId(stringObjectMap.get("uid").toString());
                            cHelper.saveDB(); // cadastro no bd
                            firebase.unauth(); // Deslogue
                            closeProgressBar();
                            showToast("Cadastro Realizado com Sucesso!!!");

                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            closeProgressBar();
                            showToast(firebaseError.getMessage());

                        }
                    }
            );
        } catch (Exception e) {
            closeProgressBar();
            showToast("Erro ao Cadastrar!!");
        }

    }


}
