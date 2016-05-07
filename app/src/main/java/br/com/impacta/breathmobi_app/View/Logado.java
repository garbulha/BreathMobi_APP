package br.com.impacta.breathmobi_app.View;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.firebase.client.Firebase;


import br.com.impacta.breathmobi_app.Listener.ChildEventListener;
import br.com.impacta.breathmobi_app.R;
import br.com.impacta.breathmobi_app.Util.ComumActivity;
import br.com.impacta.breathmobi_app.Util.UtilLogin;

/**
 * Created by TGarbulha on 05/05/2016.
 */
public class Logado extends ComumActivity implements View.OnClickListener {
    private Button btn_logout;
    private Button btn_editarcliente;
    private Firebase firebase;
    private ChildEventListener ceListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.telalogado);
        firebase.setAndroidContext(this);
        iniciarVariavel();

        btn_logout.setOnClickListener(this);
        btn_editarcliente.setOnClickListener(this);

    }

    private void iniciarVariavel() {
        btn_logout = (Button) findViewById(R.id.btn_logado_logout);
        btn_editarcliente = (Button) findViewById(R.id.btn_logado_editarcliente);
        firebase = UtilLogin.getFirebase().child("Usuario");
        ceListener = new ChildEventListener();
        firebase.addChildEventListener(ceListener);

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
            case R.id.btn_logado_editarcliente:
                Intent mIntent1 = new Intent(this, EditarCliente.class);
                startActivity(mIntent1);

                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        firebase.removeEventListener(ceListener);
    }
}

