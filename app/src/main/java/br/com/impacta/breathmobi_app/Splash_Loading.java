package br.com.impacta.breathmobi_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import br.com.impacta.breathmobi_app.Controller.ClienteHelper;
import br.com.impacta.breathmobi_app.Listener.ChildEventListener;
import br.com.impacta.breathmobi_app.Model.Cliente;
import br.com.impacta.breathmobi_app.Util.ComumActivity;
import br.com.impacta.breathmobi_app.Util.UtilLogin;
import br.com.impacta.breathmobi_app.View.Frag.FragEditarPerfil;
import br.com.impacta.breathmobi_app.View.Logado;
import br.com.impacta.breathmobi_app.View.MainActivity;

/**
 * Created by TGarbulha on 25/04/2016.
 */
public class Splash_Loading extends ComumActivity implements Runnable, ValueEventListener, Firebase.CompletionListener {
    private Firebase firebase;
    private String Flag = "nLogado";
    private ClienteHelper cHelper;
    private ChildEventListener ceListener;
    private Cliente cliente;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telainicial_loading);
        Handler handler = new Handler();
        handler.postDelayed(this, 3000);
        firebase.setAndroidContext(this);
        cHelper = new ClienteHelper();
        firebase = UtilLogin.getFirebase().child("Usuario");
        ceListener = new ChildEventListener();
        firebase.addChildEventListener(ceListener);
        cliente = new Cliente();


    }

    @Override
    public void run() {
        firebase = UtilLogin.getFirebase();
        verifyUserLogged();

        if (Flag == "sLogado" && UtilLogin.VerifyInternet(this)) {

            startActivity(new Intent(this, Logado.class));
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }

    private void verifyUserLogged() {
        if (firebase.getAuth() != null) {
            Flag = "sLogado";

        } else {

            if (!cHelper.getTokenSP(getBaseContext()).isEmpty()) {
                firebase.authWithPassword(
                        "password",
                        cHelper.getTokenSP(Splash_Loading.this),
                        new Firebase.AuthResultHandler() {
                            @Override
                            public void onAuthenticated(AuthData authData) {
                                cHelper.saveTokenSP(Splash_Loading.this, authData.getToken());
                                cHelper.saveIdSP(Splash_Loading.this, authData.getUid());
                                startActivity(new Intent(getApplicationContext(), Logado.class));
                                finish();
                            }

                            @Override
                            public void onAuthenticationError(FirebaseError firebaseError) {
                            }
                        }
                );
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        cHelper.contextDataDB(Splash_Loading.this);
    }

    @Override
    public void onComplete(FirebaseError firebaseError, Firebase firebase) {

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        //cliente = dataSnapshot.getValue(Cliente.class);
        UtilLogin.setNOME(cliente);
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
