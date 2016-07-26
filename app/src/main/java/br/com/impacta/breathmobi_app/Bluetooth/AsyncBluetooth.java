package br.com.impacta.breathmobi_app.Bluetooth;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Timer;
import java.util.UUID;

import br.com.impacta.breathmobi_app.View.Frag.FragTesteAlcool;

/**
 * Created by TGarbulha on 11/07/2016.
 */
public class AsyncBluetooth extends AsyncTask<String,String,String> {
    private static final int MESSAGE_READ = 0;
    BluetoothSocket btSocket = null;
    String btDevAddress = null;
    String myUUID = "00001101-0000-1000-8000-00805F9B34FB";

    boolean server;
    boolean running = false;
    private ProgressDialog load;
    private Context cx;

    public AsyncBluetooth(Context cx) {
        this.cx = cx;
        execute();
    }


    @Override
    protected String doInBackground(String... params) {
         /*  Anuncia que a thread está sendo executada.
            Pega uma referência para o adaptador Bluetooth padrão.
         */
        this.running = true;
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

        /*  Cliente. */
        try {

                /*  Obtem uma representação do dispositivo Bluetooth com
                endereço btDevAddress.
                    Cria um socket Bluetooth.
                 */
            Thread.sleep(2000);
            BluetoothDevice btDevice = btAdapter.getRemoteDevice(btDevAddress);
            btSocket = btDevice.createRfcommSocketToServiceRecord(UUID.fromString(myUUID));



                /*  Envia ao sistema um comando para cancelar qualquer processo
                de descoberta em execução.
                 */
            btAdapter.cancelDiscovery();

                /*  Solicita uma conexão ao dispositivo cujo endereço é
                btDevAddress.
                    Permanece em estado de espera até que a conexão seja
                estabelecida.
                 */
            if (btSocket != null) {
                btSocket.connect();

            }
        } catch (IOException e) {

                /*  Caso ocorra alguma exceção, exibe o stack trace para debug.
                    Envia um código para a Activity principal, informando que
                a conexão falhou.
                 */
            e.printStackTrace();
            // toMainActivity("---N".getBytes());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        /*  Pronto, estamos conectados! Agora, só precisamos gerenciar a conexão.
           ...
        */

        if (btSocket != null) {
            try {

               /*  Obtem referências para os fluxos de entrada e saída do
               socket Bluetooth.
                */
                InputStream input = btSocket.getInputStream();

               /*  Cria um byte array para armazenar temporariamente uma
               mensagem recebida.
                   O inteiro bytes representará o número de bytes lidos na
               última mensagem recebida.
                */
                byte[] buffer = new byte[1024];
                int bytes;
                Timer tm = new Timer();



                while (running) {
                    tm.wait(6000);
                    bytes = input.read(buffer);
                    //toMainActivity(Arrays.copyOfRange(buffer, 0, bytes));

                }

            } catch (IOException e) {
               /*  Caso ocorra alguma exceção, exibe o stack trace para debug.
                   Envia um código para a Activity principal, informando que
               a conexão falhou.
                */
                e.printStackTrace();
                onPostExecute("falha");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        load = ProgressDialog.show(cx, "Aguarde...", "Calibrando seu Bafometro");
    }





    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
    }



}
