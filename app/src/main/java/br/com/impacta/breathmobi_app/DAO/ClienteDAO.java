package br.com.impacta.breathmobi_app.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import br.com.impacta.breathmobi_app.Model.Cliente;

/**
 * Created by TGarbulha on 26/04/2016.
 */
public class ClienteDAO extends SQLiteOpenHelper {

    public ClienteDAO(Context context) {
        super(context, "BreathMobiBD", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criando tabela no BD SqlLite

        String sqlCreateTable = "CREATE TABLE TCliente (id INTEGER PRIMARY KEY, " +
                "nome TEXT NOT NULL, " +
                "idade INT," +
                "altura DOUBLE," +
                "peso double," +
                " sexo TEXT" +
                " usuario TEXT, " +
                "senha TEXT," +
                "macAdress TEXT," +
                ")";
        db.execSQL(sqlCreateTable);
        //
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXIST TCliente;";
        db.execSQL(sql);
        onCreate(db);
    }
    //
    public void insereCliente(Cliente cliente){
        SQLiteDatabase db = getReadableDatabase();

        ContentValues dados = pegaDadosAluno(cliente);
        db.insert("TCliente", null, dados);


    }
    //
    public void alteraCliente(Cliente cliente){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosAluno(cliente);
        String[] params = {cliente.getId().toString()};
        db.update("Aluno",dados,"id = ?",params);
    }
    //
    public void deletaCliente(Cliente cliente){
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {cliente.getId().toString()};
        db.delete("TCliente", "id = ?", params);

    }
    //

    @NonNull
    private ContentValues pegaDadosAluno(Cliente cliente) {
        ContentValues dados = new ContentValues();
        dados.put("nome",cliente.getNome());
        dados.put("idade",cliente.getIdade());
        dados.put("altura",cliente.getAltura());
        dados.put("peso",cliente.getPeso());
        dados.put("sexo", cliente.getSexo());
        dados.put("Usuario", cliente.getUsuario());
        dados.put("Senha", cliente.getSenha());
        dados.put("MacAdress", cliente.getMacAdress());
        return dados;
    }
}
