package br.com.impacta.breathmobi_app.View.Frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.com.impacta.breathmobi_app.R;

/**
 * Created by TGarbulha on 07/05/2016.
 */
public class Frag_opc extends Fragment {
    private Button btn_opc;

    public interface IF_Opc{
        public void mudoficarTexto(String texto);
    }

    private IF_Opc delegate;

    public void setOnFOpcClickListener(IF_Opc delegate) {
        this.delegate = delegate;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_opc, container, false);
        //
        inicializarVariavel(view);
        inicializarAcao();
        //
        return view;
    }

    private void inicializarVariavel(View view) {
        btn_opc = (Button)
                view.findViewById(R.id.frag_opc_btn_mudar_valor);
    }

    private void inicializarAcao() {
        btn_opc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delegate != null){
                    delegate.mudoficarTexto("Android Maria Mole");
                }
            }
        });
    }
}
