package br.com.impacta.breathmobi_app.Controller;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

import br.com.impacta.breathmobi_app.Model.Cliente;
import br.com.impacta.breathmobi_app.R;
import br.com.impacta.breathmobi_app.Util.UtilLogin;


/**
 * Created by TGarbulha on 01/05/2016.
 */
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

    public ClienteHelper(){}

    public void saveDB(){
        Firebase firebase =  UtilLogin.getFirebase().child("Usuario").child(cliente.getId());
        firebase.setValue(cliente);
    }

    public ClienteHelper (DialogInterface dialog) {

        // Pegando o que foi digitado no formulario //
        this.nome = (EditText) ((Dialog) dialog).findViewById(R.id.cadastro_nome);
        this.idade = (EditText) ((Dialog) dialog).findViewById(R.id.cadastro_idade);
        this.usuario = (EditText) ((Dialog) dialog).findViewById(R.id.cadastro_usuario);
        this.senha = (EditText) ((Dialog) dialog).findViewById(R.id.cadastro_senha);
        this.macAddres = (EditText) ((Dialog) dialog).findViewById(R.id.cadastro_macaddres);
        this.sexo = (EditText) ((Dialog) dialog).findViewById(R.id.cadastro_sexo);
        this.altura = (EditText) ((Dialog) dialog).findViewById(R.id.cadastro_altura);
        this.peso = (EditText) ((Dialog) dialog).findViewById(R.id.cadastro_peso);
        this.cliente = new Cliente();
    }

    //
    public Cliente pegaCliente() {

        this.cliente.setNome(nome.getText().toString());
        this.cliente.setIdade(Integer.valueOf(idade.getText().toString()));
        this.cliente.setUsuario(usuario.getText().toString());
        this.cliente.setSenha(senha.getText().toString());
        this.cliente.setMacAdress(macAddres.getText().toString());
        this.cliente.setSexo(sexo.getText().toString());
        this.cliente.setAltura(Double.valueOf(altura.getText().toString()));
        this.cliente.setPeso(Double.valueOf(peso.getText().toString()));
        return this.cliente;
    }
    public void contextDataDB( Context context ){
        //retrieveIdSP(context);
        Firebase firebase = UtilLogin.getFirebase().child("users").child(cliente.getId());

        firebase.addListenerForSingleValueEvent((ValueEventListener) context);
    }
    //Preencher com as informações do Usuario
    public void preecheFormulario(Cliente cliente) {
        nome.setText(cliente.getNome());
        idade.setText(cliente.getIdade());
        usuario.setText(cliente.getUsuario());
        senha.setText(cliente.getSenha());
        macAddres.setText(cliente.getMacAdress());
        altura.setText((int) cliente.getAltura());
        peso.setText((int) cliente.getPeso());

        this.cliente = cliente;
    }

    public String getTokenSP(Context context ){
        return( UtilLogin.getSP( context, TOKEN ) );
    }
    public void saveTokenSP(Context context, String token ){
        UtilLogin.saveSP( context, TOKEN, token );
    }
    public void saveIdSP(Context context, String token ){
        UtilLogin.saveSP( context, ID, token );
    }


}
