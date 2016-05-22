package br.com.impacta.breathmobi_app.View.Frag;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.enums.SnackbarType;

import br.com.impacta.breathmobi_app.Bluetooth.ConnectionThread;
import br.com.impacta.breathmobi_app.Bluetooth.HelpBluetooth;
import br.com.impacta.breathmobi_app.R;
import br.com.impacta.breathmobi_app.View.MainActivity;

/**
 * Created by TGarbulha on 07/05/2016.
 */
public class FragTesteAlcool extends Fragment {
    static TextView statusMsn;

    BluetoothAdapter BAdpter = BluetoothAdapter.getDefaultAdapter();
    public static int ENABLE_BLUETOOTH = 1;
    public static int SELECT_PAIRED_DEVICE = 2;
    public static int SELECT_DISCOVERED_DEVICE = 3;
    private ConnectionThread connect;

    public String MACAdress = "98:D3:31:FD:06:03";

    public static TextView testealcool_qtd;

    private Button btn_testealcool_realizar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_testealcool, container, false);
        inicializarVariavel(view);
        btn_testealcool_realizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inicializarAcao();
            }
        });

        return view;
    }

    private void inicializarAcao() {
        // Verifica se o dispositivo comporta aplicação com Bluetooth e seu hardware esta ok //
        HelpBluetooth hb = new HelpBluetooth();
        // statusMsn.setText(hb.verificaHardwareBluetooth());
        //
        // Solicita a ativação do bluetooth por parte do usuario se estiver desligado.
        //   solicitaAtivarBluetooth(cx);
        // verifica e ativa automaticamente//
        verificaBluetoothAtivado();

        ConnectionThread connect = new ConnectionThread(MACAdress);
        connect.start();
    }

    private void inicializarVariavel(View v) {
        statusMsn = (TextView) v.findViewById(R.id.testealcool_qtd);
        btn_testealcool_realizar = (Button) v.findViewById(R.id.btn_testealcool_realizar);

    }

    private void verificaBluetoothAtivado() {
        if (!BAdpter.isEnabled()) {
            //ativando bluetooth//
            BAdpter.enable();
            Log.i("BLUETOOTH", "Bluetooth Ativado Automatico :D");
            //statusMsn.setText("Bluetooth Ativado Automatico :D");

        } else {
            Log.i("BLUETOOTH", "Bluetooth já Ativado  :o");
            //statusMsn.setText("Bluetooth já esta Ativado  :o");
        }
        connect = new ConnectionThread(MACAdress);
        connect.start();
    }


    public void solicitaAtivarBluetooth(Context cx) {
        if (!BAdpter.isEnabled()) {
            Intent IBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(IBluetooth, ENABLE_BLUETOOTH);


        } else {
            Toast.makeText(cx, "Bluetooth Ligado :)", Toast.LENGTH_SHORT).show();
        }

    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            Bundle bundle = msg.getData();
            byte[] data = bundle.getByteArray("data");
            String dataString = new String(data);

            if (dataString.equals("---N")) {
                statusMsn.setText("Ligue seu bafômetro");
                Log.i("Bluetooth", "Ocorreu um erro durante a conexão D:");
                //Toast.makeText(getContext(),"Por favor Tente Novamente",Toast.LENGTH_SHORT).show();

            } else if (dataString.equals("---S")) {
                //statusMsn.setText("Conectado :D");
                Log.i("Bluetooth", "Conectado :D");
            } else {
                statusMsn.setText(new String(data));
            }
        }
    };

}
