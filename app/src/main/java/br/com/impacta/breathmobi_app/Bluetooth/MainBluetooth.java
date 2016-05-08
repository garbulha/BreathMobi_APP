//package br.com.impacta.breathmobi_app.Bluetooth;
//
//
//
//import android.bluetooth.BluetoothAdapter;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import br.com.impacta.breathmobi_app.R;
//import br.com.impacta.breathmobi_app.Util.ComumActivity;
//
///**
// * Created by TGarbulha on 08/05/2016.
// */
//public class MainBluetooth extends ComumActivity {
//    static TextView statusMsn;
//
//    BluetoothAdapter BAdpter = BluetoothAdapter.getDefaultAdapter();
//    public static int ENABLE_BLUETOOTH = 1;
//    public static int SELECT_PAIRED_DEVICE = 2;
//    public static int SELECT_DISCOVERED_DEVICE = 3;
//    public Context cx;
//
//    public String MACAdress = "98:D3:31:FD:06:03";
//
//    public static TextView tv_recebe;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        static TextView statusMsn;
//
//        BluetoothAdapter BAdpter = BluetoothAdapter.getDefaultAdapter();
//        public static int ENABLE_BLUETOOTH = 1;
//        public static int SELECT_PAIRED_DEVICE = 2;
//        public static int SELECT_DISCOVERED_DEVICE = 3;
//        public Context cx;
//
//        public String MACAdress = "98:D3:31:FD:06:03";
//
//        public static TextView tv_recebe;
//
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_main);
//
//            statusMsn = (TextView) findViewById(R.id.tv_bluetooth);
//            tv_recebe = (TextView) findViewById(R.id.tv_recebe);
//
//            cx = getBaseContext();
//            // Verifica se o dispositivo comporta aplicação com Bluetooth e seu hardware esta ok //
//            HelpBluetooth hb = new HelpBluetooth();
//            statusMsn.setText(hb.verificaHardwareBluetooth());
//            //
//            // Solicita a ativação do bluetooth por parte do usuario se estiver desligado.
//            //   solicitaAtivarBluetooth(cx);
//            // verifica e ativa automaticamente//
//            verificaBluetoothAtivado();
//
//            ConnectionThread connect = new ConnectionThread(MACAdress);
//            connect.start();
//
//
//        }
//
//    private void verificaBluetoothAtivado() {
//        if(!BAdpter.isEnabled()){
//            //ativando bluetooth//
//            BAdpter.enable();
//            statusMsn.setText("Bluetooth Ativado Automatico :D");
//
//
//        }else{
//            statusMsn.setText("Bluetooth já esta Ativado  :o");
//        }
//
//
//        ConnectionThread connect = new ConnectionThread(MACAdress);
//        connect.start();
//    }
//
//
//    public void solicitaAtivarBluetooth(Context cx) {
//        if (!BAdpter.isEnabled()) {
//            Intent IBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(IBluetooth, ENABLE_BLUETOOTH);
//
//
//        } else {
//            Toast.makeText(cx, "Bluetooth Ligado :)", Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode == ENABLE_BLUETOOTH) {
//            if(resultCode == RESULT_OK) {
//                statusMsn.setText("Bluetooth ativado :D");
//            }
//            else {
//                statusMsn.setText("Bluetooth não ativado :(");
//            }
//        }
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        BAdpter.disable();
//        super.onDestroy();
//    }
//
//
//    public static Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//
//            Bundle bundle = msg.getData();
//            byte[] data = bundle.getByteArray("data");
//
//            String dataString = new String(data);
//
//
//            if(dataString.equals("---N"))
//                statusMsn.setText("Ocorreu um erro durante a conexão D:");
//            else if(dataString.equals("---S"))
//                statusMsn.setText("Conectado :D");
//            else {
//
//                tv_recebe.setText(new String(data));
//            }
//        }
//    };
//}
