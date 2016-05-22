package br.com.impacta.breathmobi_app.View.Frag;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.impacta.breathmobi_app.R;

import com.uber.sdk.android.rides.RequestButton;
import com.uber.sdk.android.rides.RideParameters;

import java.util.Locale;

import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.content.BroadcastReceiver;
import android.widget.Button;
import android.widget.Toast;


/**
 * Created by TGarbulha on 07/05/2016.
 */
public class FragVoltaSegura extends Fragment {
    private static final String DROPOFF_ADDR = "";
    private static float DROPOFF_LAT = -23.5384581069176f;
    private static float DROPOFF_LONG = -46.43885709999995f;
    private static final String DROPOFF_NICK = "";

    private static final String PICKUP_ADDR = "";
    private static float PICKUP_LAT = -23.5384581069176f;
    private static float PICKUP_LONG = -46.43885709999995f;
    private static final String PICKUP_NICK = "";
    private static final String UBERX_PRODUCT_ID = "a1111c8c-c720-46c3-8534-2fcdd730040d";

    private LocationManager lm;
    private PicaretaLocation receiver;
    private Context context;

    private RequestButton uberButtonBlack;
    private Button contatosButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_voltasegura, container, false);

        inicializarVariavel(view);
        iniciarAcao(view);

        receiver = new PicaretaLocation();
        IntentFilter filter = new IntentFilter("android.location.PROVIDERS_CHANGED");
        //
        context.registerReceiver(receiver, filter);

        return view;
    }

    private void iniciarAcao(View view) {
        if (checkLocation()) {
            lm.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    0,
                    0,
                    servico_loc
            );

            uberButtonBlack = (RequestButton) view.findViewById(R.id.uber_button_black);

            RideParameters rideParameters = new RideParameters.Builder()
                    .setProductId(UBERX_PRODUCT_ID)
                    .setPickupLocation(PICKUP_LAT, PICKUP_LONG, PICKUP_NICK, PICKUP_ADDR)
                    .setDropoffLocation(DROPOFF_LAT, DROPOFF_LONG, DROPOFF_NICK, DROPOFF_ADDR)
                    .build();

            uberButtonBlack.setRideParameters(rideParameters);
        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(view.getContext());
            alerta.setTitle("Serviço de Locazacao");
            alerta.setMessage("Localizacao desabilitada. Deseja ir para configuracoes?");
            //
            alerta.setPositiveButton("Sim",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent mIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(mIntent);
                        }
                    });
            alerta.setNegativeButton("Não", null);
            alerta.show();
        }


        contatosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder contact = new AlertDialog.Builder(v.getContext());
                contact.setTitle("Contatos");
                contact.setMessage("Deseja abrir lista de contatos?");
                //
                contact.setPositiveButton("Sim",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Uri uri = Uri.parse("content://com.android.contacts/contacts/");
                                Intent mIntent = new Intent(Intent.ACTION_PICK, uri);
                                startActivity(mIntent);
                            }
                        });
                contact.setNegativeButton("Não", null);
                contact.show();

            }
        });
    }

    @Override
    public void onDestroy() {
        context.unregisterReceiver(receiver);
        super.onDestroy();
    }


    private void inicializarVariavel(View view) {
        context = getContext();

        lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        contatosButton = (Button) view.findViewById(R.id.contatosButton);
    }

    private LocationListener servico_loc = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {

            DROPOFF_LAT = (float) location.getLatitude();
            DROPOFF_LONG = (float) location.getLongitude();

            PICKUP_LAT = (float) location.getLatitude();
            PICKUP_LONG = (float) location.getLongitude();


        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };


    private class PicaretaLocation extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            if(checkLocation()){
//                Toast.makeText(context,"Locaition Habilitado",Toast.LENGTH_SHORT).show();
//
//            }else{
//                Toast.makeText(context,"Locaition Desabilitado",Toast.LENGTH_SHORT).show();
//            }
        }
    }

    private boolean checkLocation() {
        if (lm != null && lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return true;
        }
        return false;
    }

}
