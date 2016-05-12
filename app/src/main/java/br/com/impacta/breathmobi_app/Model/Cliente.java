package br.com.impacta.breathmobi_app.Model;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by TGarbulha on 26/04/2016.
 */
@JsonIgnoreProperties({"id", "senha", "usuario"})
public class Cliente{

    private static String PREF = "BREATHMOBI";
    private String Id;
    private String nome;
    private String idade;
    private String usuario;
    private String senha;
    private String sexo;
    private String macAdress;
    private String altura;
    private String peso;



    public Cliente() {
        this.Id = "";
        this.nome = "";
        this.idade = "";
        this.usuario = "";
        this.senha = "";
        this.sexo = "";
        this.macAdress = "";
        this.altura = "";
        this.peso = "";



    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getMacAdress() {
        return macAdress;
    }

    public void setMacAdress(String macAdress) {
        this.macAdress = macAdress;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    static public void saveToken(Context context, String key, String value ){
        SharedPreferences sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }
    static public void saveID(Context context, String key, String value ){
        SharedPreferences sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }
    static public String getToken(Context context, String key ){
        SharedPreferences sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        String token = sp.getString(key, "");
        return( token );
    }


}

