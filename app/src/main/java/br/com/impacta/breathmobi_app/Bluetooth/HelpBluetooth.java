package br.com.impacta.breathmobi_app.Bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by TGarbulha on 10/02/2016.
 */
public class HelpBluetooth {
    BluetoothAdapter BAdpter = BluetoothAdapter.getDefaultAdapter();


    // Verifica se o dispositivo comporta aplicação com Bluetooth e seu hardware esta ok //
    public String verificaHardwareBluetooth() {
        if (BAdpter == null) {
            return "Que pena! Hardware Bluetooth não está funcionando :(";
        } else {

            return "Ótimo! Hardware Bluetooth está funcionando :)";
        }
    }






}
