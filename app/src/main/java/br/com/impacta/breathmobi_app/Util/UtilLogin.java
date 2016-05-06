package br.com.impacta.breathmobi_app.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.firebase.client.Firebase;

/**
 * Created by TGarbulha on 05/05/2016.
 */
public final class UtilLogin {

    public static String PREF = "br.com.impacta.breathmobi.PREF";
    private static Firebase firebase;

    public UtilLogin() {
    }

    public static Firebase getFirebase(){
        if( firebase == null ){
            firebase = new Firebase("https://breathmobi.firebaseio.com");
        }

        return( firebase );
    }



    static public void saveSP(Context context, String key, String value ){
        SharedPreferences sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }

    static public String getSP(Context context, String key ){
        SharedPreferences sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        String token = sp.getString(key, "");
        return( token );
    }


}