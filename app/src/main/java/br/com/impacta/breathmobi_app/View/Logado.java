package br.com.impacta.breathmobi_app.View;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import br.com.impacta.breathmobi_app.Constantes;
import br.com.impacta.breathmobi_app.Controller.ClienteHelper;
import br.com.impacta.breathmobi_app.Listener.ChildEventListener;
import br.com.impacta.breathmobi_app.Model.Cliente;
import br.com.impacta.breathmobi_app.R;
import br.com.impacta.breathmobi_app.Util.ComumActivity;
import br.com.impacta.breathmobi_app.Util.UtilLogin;
import br.com.impacta.breathmobi_app.View.Frag.FragEditarPerfil;
import br.com.impacta.breathmobi_app.View.Frag.FragHistorico;
import br.com.impacta.breathmobi_app.View.Frag.FragTesteAlcool;
import br.com.impacta.breathmobi_app.View.Frag.FragVoltaSegura;


/**
 * Created by TGarbulha on 07/05/2016.
 */
public class Logado extends ComumActivity implements ValueEventListener, Firebase.CompletionListener {
    private Drawer.Result HamburguerLeft;
    private AccountHeader.Result headerNavigationLeft;
    private Toolbar mToolbar;
    private Firebase firebase;
    private ChildEventListener ceListener;

    private FragTesteAlcool fragAlcool;
    private FragVoltaSegura fragVolta;
    private FragHistorico fragHistorico;
    private FragEditarPerfil fragPerfil;
    private Cliente cliente;
    private ClienteHelper cHelper;
    private String sTag = "";

    public static int ENABLE_BLUETOOTH = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telalogado);
        firebase.setAndroidContext(this);
        //
        inicializarVariavel();
        inicializarAcao(savedInstanceState);
    }

    private void inicializarVariavel() {
        cliente = new Cliente();
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        firebase = UtilLogin.getFirebase().child("Usuario");
        ceListener = new ChildEventListener();
        firebase.addChildEventListener(ceListener);
        cHelper = new ClienteHelper();
    }


    private void inicializarAcao(Bundle savedInstanceState) {
        FragmentManager fm = getSupportFragmentManager();

        fragAlcool = new FragTesteAlcool();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container_frag, fragAlcool, "fragAlcool");
        ft.commit();

        mToolbar.setTitleTextColor(getApplicationContext().getResources().getColor(android.R.color.white));

        headerNavigationLeft = new AccountHeader()
                .withActivity(this)
                .withSelectionListEnabled(false)
                .withCompactStyle(true)
                .withHeaderBackground(R.drawable.gradient)
                .addProfiles(new ProfileDrawerItem()
                                .withName("Seja Bem-Vindo")
                                .withIcon(getResources()
                                        .getDrawable(R.drawable.logo))

                )
                .withOnAccountHeaderSelectionViewClickListener(new AccountHeader.OnAccountHeaderSelectionViewClickListener() {

                    @Override
                    public boolean onClick(View view, IProfile profile) {
                        return true;
                    }
                })
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {

                        return true;
                    }
                })
                .build();


        HamburguerLeft = new Drawer()
                .withActivity(Logado.this)
                .withToolbar(mToolbar)
                .withDisplayBelowToolbar(false)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.LEFT)
                .withSavedInstance(savedInstanceState)
                .withSelectedItem(0)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerNavigationLeft)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {

                        FragmentManager fm = getSupportFragmentManager();


                        switch (i) {
                            case 0:
                                sTag = "fragAlcool";
                                //
                                if (fragAlcool == null) {
                                    fragAlcool = new FragTesteAlcool();
                                }
                                //
                                if (fm.findFragmentByTag(sTag) == null) {
                                    mToolbar.setTitle(((PrimaryDrawerItem) iDrawerItem).getName());
                                    FragmentTransaction ft = fm.beginTransaction();
                                    ft.replace(R.id.container_frag, fragAlcool, sTag);
                                    ft.commit();
                                }

                                break;
                            case 1:
                                sTag = "fragPerfil";
                                //
                                if (fragPerfil == null) {
                                    fragPerfil = new FragEditarPerfil();
                                }
                                //
                                if (fm.findFragmentByTag(sTag) == null) {
                                    mToolbar.setTitle(((PrimaryDrawerItem) iDrawerItem).getName());
                                    FragmentTransaction ft = fm.beginTransaction();
                                    ft.replace(R.id.container_frag, fragPerfil, sTag);
                                    ft.commit();

                                }
                                fragPerfil.setOnClickListener(new FragEditarPerfil.IFragPerfil() {
                                    @Override
                                    public void update() {

                                    }
                                });

                                break;

                            case 2:
                                sTag = "fragVolta";
                                //
                                if (fragVolta == null) {
                                    fragVolta = new FragVoltaSegura();
                                }
                                //
                                if (fm.findFragmentByTag(sTag) == null) {
                                    mToolbar.setTitle(((PrimaryDrawerItem) iDrawerItem).getName());
                                    FragmentTransaction ft = fm.beginTransaction();
                                    ft.replace(R.id.container_frag, fragVolta, sTag);
                                    ft.commit();
                                }
                                break;

                            case 3:
                                sTag = "fragHistorico";
                                //
                                if (fragHistorico == null) {
                                    fragHistorico = new FragHistorico();
                                }
                                //
                                if (fm.findFragmentByTag(sTag) == null) {
                                    mToolbar.setTitle(((PrimaryDrawerItem) iDrawerItem).getName());
                                    FragmentTransaction ft = fm.beginTransaction();
                                    ft.replace(R.id.container_frag, fragHistorico, sTag);
                                    ft.commit();
                                }
                                break;

                            case 5:
                                AlertDialog.Builder alerta = new AlertDialog.Builder(Logado.this);
                                alerta.setTitle("Sair!");
                                alerta.setTitle("Deseja realmente sair?");
                                alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        final ProgressDialog p_dialog;
                                        p_dialog = ProgressDialog.show(Logado.this, "", "Saindo...", false, true);
                                        p_dialog.setProgressStyle(ProgressDialog.BUTTON_POSITIVE);
                                        p_dialog.setProgressStyle(R.style.ProgressBar);
                                        firebase.unauth();
                                        startActivity(new Intent(Logado.this, MainActivity.class));
                                        p_dialog.dismiss();
                                        finish();
                                    }
                                });
                                alerta.setNegativeButton("Não", null);
                                alerta.show();
                                break;
                            default:

                                break;

                        }

                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        return false;
                    }
                })
                .build();

        // Adicionando items no menu HAMBURGUER //
        HamburguerLeft.addItem(new PrimaryDrawerItem()
                .withName("Realizar Teste")
                .withIcon(getResources()
                        .getDrawable(R.drawable.circle_blue)));
        //
        HamburguerLeft.addItem(new PrimaryDrawerItem()
                .withName("Perfil")
                .withIcon(getResources()
                        .getDrawable(R.drawable.perfil1)));
        //
        HamburguerLeft.addItem(new PrimaryDrawerItem()
                .withName("Volta Segura")
                .withIcon(getResources()
                        .getDrawable(R.drawable.uber_badge)));
        //
        HamburguerLeft.addItem(new PrimaryDrawerItem()
                .withName("Histórico")
                .withIcon(getResources()
                        .getDrawable(R.drawable.circle_orange)));
        //
        HamburguerLeft.addItem(new DividerDrawerItem());

        //
        HamburguerLeft.addItem(new PrimaryDrawerItem()
                .withName("Sair")
                .withIcon(getResources()
                        .getDrawable(R.mipmap.logo)));


    }

    @Override
    public void onBackPressed() {
        if (HamburguerLeft.isDrawerOpen()) {
            HamburguerLeft.closeDrawer();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        cHelper.contextDataDB(Logado.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        firebase.removeEventListener(ceListener);
    }


    @Override
    public void onComplete(FirebaseError firebaseError, Firebase firebase) {

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        this.cliente = dataSnapshot.getValue(Cliente.class);

        if (sTag == "fragPerfil") {
            fragPerfil.setInfos(this.cliente);

        }
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ENABLE_BLUETOOTH) {
            if (resultCode == RESULT_OK) {
                showToast("Bluetooth ativado :D");
            } else {
                showToast("Bluetooth não ativado :(");
            }
        }
    }
}
