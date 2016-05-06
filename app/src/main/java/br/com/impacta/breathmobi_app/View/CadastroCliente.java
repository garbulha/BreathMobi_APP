package br.com.impacta.breathmobi_app.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.impacta.breathmobi_app.Controller.ClienteHelper;
import br.com.impacta.breathmobi_app.Model.Cliente;
import br.com.impacta.breathmobi_app.R;


/**
 * Created by TGarbulha on 01/05/2016.
 */
public class CadastroCliente extends AppCompatActivity {

    private ClienteHelper chelper;
    private Button btn_cadastrarcliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_cliente);

        //recuperar Intent//
        Intent mIntent = getIntent();
        //devolver Serializable de cliente//
        Cliente cliente = (Cliente) mIntent.getSerializableExtra("cliente");

        if(cliente != null){
            chelper.preecheFormulario(cliente);
        }

        btn_cadastrarcliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
