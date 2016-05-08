package br.com.impacta.breathmobi_app.View.Frag;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.impacta.breathmobi_app.R;

/**
 * Created by TGarbulha on 07/05/2016.
 */
public class FragTesteAlcool extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_testealcool, container, false);
        return view;
    }


}
