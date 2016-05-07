package br.com.impacta.breathmobi_app.View;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


import br.com.impacta.breathmobi_app.Controller.ClienteHelper;
import br.com.impacta.breathmobi_app.Listener.ChildEventListener;
import br.com.impacta.breathmobi_app.Model.Cliente;
import br.com.impacta.breathmobi_app.R;
import br.com.impacta.breathmobi_app.Util.ComumActivity;
import br.com.impacta.breathmobi_app.Util.UtilLogin;

/**
 * Created by TGarbulha on 06/05/2016.
 */
public class EditarCliente extends ComumActivity implements ValueEventListener, Firebase.CompletionListener {
    private Firebase firebase;
    private ChildEventListener ceListener;
    private EditText ed_nome;
    private Button btn_alterar;
    private ClienteHelper cHelper;
    private Cliente cliente;


    @Override
    protected void onPostResume() {
        super.onPostResume();
        cHelper = new ClienteHelper();
        cHelper.contextDataDB(EditarCliente.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.altera_cliente);
        firebase.setAndroidContext(this);
        iniciarVariavel();

        btn_alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cliente cliente = new Cliente();
                cliente.setNome(ed_nome.getText().toString());
                cHelper.updateDB(EditarCliente.this,cliente);
            }
        });

    }

    private void iniciarVariavel() {
        firebase = UtilLogin.getFirebase().child("Usuario");
        ceListener = new ChildEventListener();
        firebase.addChildEventListener(ceListener);
        btn_alterar = (Button) findViewById(R.id.alterar_btn_alterar);
        ed_nome = (EditText) findViewById(R.id.altera_nome);

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        firebase.removeEventListener(ceListener);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Cliente cliente = dataSnapshot.getValue(Cliente.class);
        ed_nome.setText(cliente.getNome());


    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {
    }

    @Override
    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
        if (firebaseError != null) {
            showToast("Erro");
        }else{
            showToast("Alteração com Sucesso!!");
        }
    }
}
