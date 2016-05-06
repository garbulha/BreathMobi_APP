package br.com.impacta.breathmobi_app.Util;


import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by TGarbulha on 05/05/2016.
 */
public class ComumActivity extends AppCompatActivity {
    protected AutoCompleteTextView email;
    protected EditText password;
    protected ProgressBar progressBar;

    protected void showToast( String message ){
        Toast.makeText(this,
                message,
                Toast.LENGTH_LONG)
                .show();
    }

    protected void openProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    protected void closeProgressBar(){
        progressBar.setVisibility( View.GONE );
    }

    protected void progreesDialog(){
        ProgressDialog.show(getApplicationContext(),"Validando Usuario", null);
    }
}
