package br.com.impacta.breathmobi_app.Listener;

import android.util.Log;


import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import br.com.impacta.breathmobi_app.Controller.ClienteHelper;
import br.com.impacta.breathmobi_app.Model.Cliente;

/**
 * Created by TGarbulha on 06/05/2016.
 */
public class ChildEventListener implements com.firebase.client.ChildEventListener {
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        // s chave do cliente que esta no firebase
        Cliente cliente = dataSnapshot.getValue(Cliente.class);
        Log.i("log", "ADDDE");
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Cliente cliente = dataSnapshot.getValue(Cliente.class);
        Log.i("log", "CHANGED");
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        Cliente cliente = dataSnapshot.getValue(Cliente.class);
        Log.i("log", "REMOVE");
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }
}
