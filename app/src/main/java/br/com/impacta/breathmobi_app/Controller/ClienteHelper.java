package br.com.impacta.breathmobi_app.Controller;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import br.com.impacta.breathmobi_app.Model.Cliente;
import br.com.impacta.breathmobi_app.R;
import br.com.impacta.breathmobi_app.Util.UtilLogin;

/**
 * Created by TGarbulha on 01/05/2016.
 */
@JsonIgnoreProperties({"id", "password"})
public class ClienteHelper {

    public static String TOKEN = "br.com.impacta.brathmobi.Model.ClienteHelper.TOKEN";
    public static String ID = "br.com.impacta.brathmobi.Model.ClienteHelper.ID";

    private EditText nome;
    private EditText idade;
    private EditText usuario;
    private EditText senha;
    private EditText sexo;
    private EditText macAddres;
    private EditText altura;
    private EditText peso;

    private Cliente cliente;

    public ClienteHelper() {

    }

    public void saveDB() {
        Firebase firebase = UtilLogin.getFirebase().child("Usuario").child(cliente.getId());
        firebase.setValue(cliente);
    }

    public void updateDB(Context context, Cliente c) {

        retrieveIdSp(context);
        Firebase firebase = UtilLogin.getFirebase().child("Usuario").child(cliente.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("nome", c.getNome());
        map.put("idade", c.getIdade());
        map.put("sexo", c.getSexo());
        map.put("altura", c.getAltura());
        map.put("peso", c.getPeso());
        map.put("macAdress", c.getMacAdress());

        if(map.isEmpty()){
            return;
        }

        firebase.updateChildren(map);
    }

    public ClienteHelper(DialogInterface dialog) {
        try {
            // Pegando o que foi digitado no formulario //
            this.usuario = (EditText) ((Dialog) dialog).findViewById(R.id.cadastro_usuario);
            this.senha = (EditText) ((Dialog) dialog).findViewById(R.id.cadastro_senha);
            this.nome = (EditText) ((Dialog) dialog).findViewById(R.id.cadastro_nome);
            this.idade = (EditText) ((Dialog) dialog).findViewById(R.id.cadastro_idade);
            this.macAddres = (EditText) ((Dialog) dialog).findViewById(R.id.cadastro_macaddres);
            this.sexo = (EditText) ((Dialog) dialog).findViewById(R.id.cadastro_sexo);
            this.altura = (EditText) ((Dialog) dialog).findViewById(R.id.cadastro_altura);
            this.peso = (EditText) ((Dialog) dialog).findViewById(R.id.cadastro_peso);
            this.cliente = new Cliente();
        }finally {
            this.cliente = new Cliente();
        }

    }
       // usado no cadastro
    public Cliente pegaCliente() {
        try {
            this.cliente.setUsuario(usuario.getText().toString());
            this.cliente.setSenha(senha.getText().toString());
            this.cliente.setMacAdress(macAddres.getText().toString());
            this.cliente.setNome(nome.getText().toString());
            this.cliente.setIdade(idade.getText().toString());
            this.cliente.setSexo(sexo.getText().toString());
            this.cliente.setAltura(altura.getText().toString());
            this.cliente.setPeso(peso.getText().toString());
            return this.cliente;
        } finally {
            return this.cliente;
        }

    }

    public void contextDataDB(Context context) {
        retrieveIdSp(context);
        Firebase firebase = UtilLogin.getFirebase().child("Usuario").child(cliente.getId());
        firebase.addListenerForSingleValueEvent((ValueEventListener) context);
    }

    //Preencher com as informações do Usuario
    public void preecheFormulario(Cliente cliente) {

        this.nome.setText(cliente.getNome());
        this.idade.setText(cliente.getIdade());
        this.usuario.setText(cliente.getUsuario());
        this.senha.setText(cliente.getSenha());
        this.macAddres.setText(cliente.getMacAdress());
        this.altura.setText(cliente.getAltura());
        this.peso.setText(cliente.getPeso());

        this.cliente = cliente;
    }

    public String getTokenSP(Context context) {
        return (UtilLogin.getSP(context, TOKEN));
    }

    public void saveTokenSP(Context context, String token) {
        UtilLogin.saveSP(context, TOKEN, token);
    }

    public void saveIdSP(Context context, String token) {
        UtilLogin.saveSP(context, ID, token);
    }

    public void retrieveIdSp(Context context){
        this.cliente = new Cliente();
        Log.i("xiiii",UtilLogin.getSP(context, ID ));
        this.cliente.setId(UtilLogin.getSP(context, ID));
    }
    }





