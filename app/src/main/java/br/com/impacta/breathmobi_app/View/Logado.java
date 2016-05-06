package br.com.impacta.breathmobi_app.View;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;

import br.com.impacta.breathmobi_app.Listeners.FirebaseValueEventListener;
import br.com.impacta.breathmobi_app.R;
import br.com.impacta.breathmobi_app.Util.ComumActivity;
import br.com.impacta.breathmobi_app.Util.UtilLogin;

/**
 * Created by TGarbulha on 05/05/2016.
 */
public class Logado extends ComumActivity implements View.OnClickListener {
    private Button btn_logout;
    private Firebase firebase;
    private FirebaseValueEventListener fvEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.telalogado);
        firebase.setAndroidContext(this);
        iniciarVariavel();

        btn_logout.setOnClickListener(this);

    }

    private void iniciarVariavel() {
        btn_logout = (Button) findViewById(R.id.btn_logado_logout);
        firebase = UtilLogin.getFirebase();
        firebase.addValueEventListener(fvEventListener);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logado_logout:
                firebase.unauth();

                Intent mIntent = new Intent(this, MainActivity.class);
                startActivity(mIntent);
                finish();
                break;
        }
    }

}

