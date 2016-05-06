package br.com.impacta.breathmobi_app.Listeners;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import br.com.impacta.breathmobi_app.Controller.ClienteHelper;
import br.com.impacta.breathmobi_app.Model.Cliente;

/**
 * Created by TGarbulha on 06/05/2016.
 */
public class FirebaseValueEventListener implements ValueEventListener {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot d : dataSnapshot.getChildren()){
            ClienteHelper c = d.getValue(ClienteHelper.class);

            Log.d("log ","Name: "+c.pegaCliente().getNome());
            Log.d("log ","Email: "+c.pegaCliente().getUsuario());
        }

    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }
}
