package br.com.impacta.breathmobi_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import br.com.impacta.breathmobi_app.Controller.ClienteHelper;
import br.com.impacta.breathmobi_app.Util.ComumActivity;
import br.com.impacta.breathmobi_app.Util.UtilLogin;
import br.com.impacta.breathmobi_app.View.Logado;
import br.com.impacta.breathmobi_app.View.MainActivity;

/**
 * Created by TGarbulha on 25/04/2016.
 */
public class Splash_Loading extends ComumActivity implements Runnable {
    private Firebase firebase;
    private ClienteHelper cHelper;
    private String Flag = "nLogado";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telainicial_loading);
        cHelper = new ClienteHelper();
        firebase.setAndroidContext(this);


        Handler handler = new Handler();
        handler.postDelayed(this, 3000);
    }


    @Override
    public void run() {
        firebase = UtilLogin.getFirebase();
        verifyUserLogged();

        if(Flag == "sLogado"){
            startActivity(new Intent(this, Logado.class));
        }else{
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
                            public void onAuthenticationError(FirebaseError firebaseError) { }
                        }
                );
            }
        }
    }
}
