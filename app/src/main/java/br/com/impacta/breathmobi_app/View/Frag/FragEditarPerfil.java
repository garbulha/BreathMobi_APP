package br.com.impacta.breathmobi_app.View.Frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import br.com.impacta.breathmobi_app.View.Logado;


/**
 * Created by TGarbulha on 07/05/2016.
 */
public class FragEditarPerfil extends Fragment {
    private Firebase firebase;
    private ChildEventListener ceListener;
    private EditText ed_nome;
    private EditText ed_dtnasc;
    private EditText ed_sexo;
    private EditText ed_altura;
    private EditText ed_peso;
    private IFragPerfil delegate;

    private EditText ed_macaddres;
    private Button btn_alterar;

    private ClienteHelper cHelper;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_editarperfil, container, false);
        firebase.setAndroidContext(view.getContext());
        cHelper = new ClienteHelper();
        cHelper.contextDataDB(view.getContext());
        inicializarVariavel(view);
        inicializarAcao();

        return view;

    }

    private void inicializarVariavel(View v) {
        firebase = UtilLogin.getFirebase().child("Usuario");
        ceListener = new ChildEventListener();
        firebase.addChildEventListener(ceListener);
        btn_alterar = (Button) v.findViewById(R.id.alterar_btn_alterar);
        ed_nome = (EditText) v.findViewById(R.id.altera_nome);
        ed_dtnasc = (EditText) v.findViewById(R.id.altera_idade);
        ed_sexo = (EditText) v.findViewById(R.id.altera_sexo);
        ed_altura = (EditText) v.findViewById(R.id.altera_altura);
        ed_peso = (EditText) v.findViewById(R.id.altera_peso);
        ed_macaddres = (EditText) v.findViewById(R.id.altera_macaddres);
    }

    private void inicializarAcao() {
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
                    cHelper.updateDB(getContext(), cliente);
                    Toast.makeText(getContext(), "ALteração com Sucesso", Toast.LENGTH_SHORT).show();
                } catch (Exception exc) {
                    Toast.makeText(getContext(), exc.getMessage(), Toast.LENGTH_SHORT).show();
                } finally {

                }

            }
        });
    }

    public interface IFragPerfil {
        public void update();
    }


    public void setOnClickListener(IFragPerfil delegate) {
        this.delegate = delegate;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        firebase.removeEventListener(ceListener);
    }


    public void setInfos(Cliente cliente) {
        ed_nome.setText(cliente.getNome());
        ed_sexo.setText(cliente.getSexo());
        ed_dtnasc.setText(cliente.getIdade());
        ed_macaddres.setText(cliente.getMacAdress());
        ed_altura.setText(cliente.getAltura());
        ed_peso.setText(cliente.getPeso());
    }
}
