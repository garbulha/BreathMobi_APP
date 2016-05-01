package br.com.impacta.breathmobi_app.Controller;

import android.renderscript.Double2;
import android.widget.EditText;

import br.com.impacta.breathmobi_app.Model.Cliente;
import br.com.impacta.breathmobi_app.R;
import br.com.impacta.breathmobi_app.View.CadastroCliente;

/**
 * Created by TGarbulha on 01/05/2016.
 */
public class ClienteHelper {

    private final EditText nome;
    private final EditText idade;
    private final EditText usuario;
    private final EditText senha;
    private final EditText sexo;
    private final EditText macAdress;
    private final EditText altura;
    private final EditText peso;

    private Cliente cliente;

    public ClienteHelper(CadastroCliente activity) {

        // Pegando o que foi digitado no formulario //
        nome = (EditText) activity.findViewById(R.id.cadastro_nome);
        idade = (EditText) activity.findViewById(R.id.cadastro_idade);
        usuario = (EditText) activity.findViewById(R.id.cadastro_usuario);
        senha = (EditText) activity.findViewById(R.id.cadastro_senha);
        macAdress = (EditText) activity.findViewById(R.id.cadastro_macadress);
        sexo = (EditText) activity.findViewById(R.id.cadastro_sexo);
        altura = (EditText) activity.findViewById(R.id.cadastro_altura);
        peso = (EditText) activity.findViewById(R.id.cadastro_peso);
        cliente = new Cliente();

    }
    //
    public Cliente pegaCliente() {

        cliente.setNome(nome.getText().toString());
        cliente.setIdade(Integer.valueOf(idade.getText().toString()));
        cliente.setUsuario(usuario.getText().toString());
        cliente.setSenha(senha.getText().toString());
        cliente.setMacAdress(macAdress.getText().toString());
        cliente.setAltura(Double.valueOf(altura.getText().toString()));
        cliente.setPeso(Double.valueOf(peso.getText().toString()));
        return cliente;
    }

    public void preecheFormulario(Cliente cliente) {
        nome.setText(cliente.getNome());
        idade.setText(cliente.getIdade());
        usuario.setText(cliente.getUsuario());
        senha.setText(cliente.getSenha());
        macAdress.setText(cliente.getMacAdress());
        altura.setText((int) cliente.getAltura());
        peso.setText((int) cliente.getPeso());

        this.cliente = cliente;
    }

}
