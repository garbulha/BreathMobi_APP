package br.com.impacta.breathmobi_app.View;


import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.media.audiofx.BassBoost;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.enums.SnackbarType;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.nispok.snackbar.listeners.EventListenerAdapter;


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
        if (!UtilLogin.VerifyInternet(this)) {
            SnackbarManager.show(
                    Snackbar.with(this)
                            .text("Sem Conexão com a Internet. Por Favor Conectar !!!")
                            .color(getResources().getColor(android.R.color.black))
                            .textColor(getResources().getColor(android.R.color.white))

            );
//            Toast.makeText(getBaseContext(), "Sem Conexão Internet", Toast.LENGTH_LONG).show();
        }
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.telainicial_cadastrar:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Novo Cadastro");
                builder.setView(R.layout.cadastro_cliente_inicio);

                builder.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openProgressBar();
                        cHelper = new ClienteHelper(dialog);
                        cadastrarUsuario(cliente = cHelper.pegaCliente());

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
                final String login = tv_login.getText().toString();
                final String senha = tv_senha.getText().toString();
                final ProgressDialog p_dialog;
                p_dialog = ProgressDialog.show(this, "Validando Login", "Aguarde...", false, true);
                p_dialog.setProgressStyle(ProgressDialog.BUTTON_POSITIVE);
                p_dialog.setProgressStyle(R.style.ProgressBar);
//                final ProgressDialog p;
//                p = ProgressDialog.show(this,"","");
//                p.setContentView(R.layout.actionbar_layout);

                firebase.authWithPassword(
                        login,
                        senha,
                        new Firebase.AuthResultHandler() {
                            @Override
                            public void onAuthenticated(AuthData authData) {
                                cHelper.saveIdSP(MainActivity.this, authData.getUid());
                                Intent mIntent = new Intent(getApplicationContext(), Logado.class);
                                startActivity(mIntent);

                                p_dialog.dismiss();
                                finish();
                            }

                            @Override
                            public void onAuthenticationError(FirebaseError firebaseError) {

                                p_dialog.dismiss();
                                showToast("E-mail ou Senha Inválido!");
                            }
                        }
                );

                break;

            case R.id.telainicial_esqueceusenha:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
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
