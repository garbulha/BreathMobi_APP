package br.com.impacta.breathmobi_app.View;

import android.os.Bundle;

import com.firebase.client.Firebase;

import br.com.impacta.breathmobi_app.Listeners.FirebaseValueEventListener;
import br.com.impacta.breathmobi_app.R;
import br.com.impacta.breathmobi_app.Util.ComumActivity;
import br.com.impacta.breathmobi_app.Util.UtilLogin;

/**
 * Created by TGarbulha on 06/05/2016.
 */
public class EditarCliente extends ComumActivity {
    private Firebase firebase;
    FirebaseValueEventListener fve_listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.altera_cliente);
        firebase.setAndroidContext(this);
        iniciarVariavel();

    }

    private void iniciarVariavel() {
        firebase = UtilLogin.getFirebase();
        firebase.addValueEventListener(fve_listener);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        firebase.removeEventListener(fve_listener);
    }
}
