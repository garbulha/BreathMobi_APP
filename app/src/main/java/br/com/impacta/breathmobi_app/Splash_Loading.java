package br.com.impacta.breathmobi_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import br.com.impacta.breathmobi_app.View.MainActivity;

/**
 * Created by TGarbulha on 25/04/2016.
 */
public class Splash_Loading extends Activity implements Runnable {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telainicial_loading);

        Handler handler = new Handler();
        handler.postDelayed(this, 3000);
    }


    @Override
    public void run() {

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
