package br.com.impacta.breathmobi_app.WebService;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseException;


import java.util.HashMap;

import br.com.impacta.breathmobi_app.Model.Cliente;
import br.com.impacta.breathmobi_app.Model.HMCliente;
import br.com.impacta.breathmobi_app.View.MainActivity;

/**
 * Created by TGarbulha on 02/05/2016.
 */
public class AsyncCliente extends AppCompatActivity  {
    private ProgressDialog load;
    private Firebase firebase;
    private Context cx;


    public class cadastraCliente extends AsyncTask<Void,Integer,Void>{
        @Override
        protected void onPreExecute() {
            load = ProgressDialog.show(cx, "Por favor Aguarde ...", "Cadastrando...");

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                firebase = new Firebase("https://breathmobi.firebaseio.com/");
            } catch (FirebaseException e) {
                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            Firebase refInserir = firebase.child("usuario");
            Cliente cliente = new Cliente();

            HMCliente hmCliente = new HMCliente();

            hmCliente.put("nome", cliente.getNome().toString());
            hmCliente.put("idade", String.valueOf(cliente.getIdade()));
            hmCliente.put("sexo", cliente.getSexo().toString());
            hmCliente.put("altura", String.valueOf(cliente.getAltura()));
            hmCliente.put("altura", String.valueOf(cliente.getPeso()));
            hmCliente.put("macaddres", cliente.getMacAdress().toString());
            hmCliente.put("usuario", cliente.getUsuario().toString());
            hmCliente.put("senha", cliente.getSenha().toString());

            refInserir.push().setValue(hmCliente);

            Toast.makeText(cx, "Registro salvo!!!", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

}
