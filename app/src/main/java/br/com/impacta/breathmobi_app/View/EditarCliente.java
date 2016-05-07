package br.com.impacta.breathmobi_app.View;

import android.content.Intent;
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
    private EditText ed_dtnasc;
    private EditText ed_sexo;
    private EditText ed_altura;
    private EditText ed_peso;
    private EditText ed_macaddres;


    private Button btn_alterar;
    private ClienteHelper cHelper;


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
                try {
                    Cliente cliente = new Cliente();
                    cliente.setNome(ed_nome.getText().toString());
                    cliente.setIdade(ed_dtnasc.getText().toString());
                    cliente.setSexo(ed_sexo.getText().toString());
                    cliente.setAltura(ed_altura.getText().toString());
                    cliente.setPeso(ed_peso.getText().toString());
                    cliente.setMacAdress(ed_macaddres.getText().toString());
                    cHelper.updateDB(EditarCliente.this, cliente);
                    showToast("Alteração com Sucesso");
                } catch (Exception exc) {
                    showToast(exc.getMessage());
                }finally {
                    startActivity(new Intent(getApplicationContext(), Logado.class));
                    finish();
                }

            }
        });

    }

    private void iniciarVariavel() {
        firebase = UtilLogin.getFirebase().child("Usuario");
        ceListener = new ChildEventListener();
        firebase.addChildEventListener(ceListener);
        btn_alterar = (Button) findViewById(R.id.alterar_btn_alterar);
        ed_nome = (EditText) findViewById(R.id.altera_nome);
        ed_dtnasc = (EditText) findViewById(R.id.altera_idade);
        ed_sexo = (EditText) findViewById(R.id.altera_sexo);
        ed_altura = (EditText) findViewById(R.id.altera_altura);
        ed_peso = (EditText) findViewById(R.id.altera_peso);
        ed_macaddres = (EditText) findViewById(R.id.altera_macaddres);


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
        ed_sexo.setText(cliente.getSexo());
        ed_dtnasc.setText(cliente.getIdade());
        ed_macaddres.setText(cliente.getMacAdress());
        ed_altura.setText(cliente.getAltura());
        ed_peso.setText(cliente.getPeso());

    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {
    }

    @Override
    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
        if (firebaseError != null) {
            showToast("Erro");
        } else {
            showToast("Alteração com Sucesso!!");
        }
    }
}
