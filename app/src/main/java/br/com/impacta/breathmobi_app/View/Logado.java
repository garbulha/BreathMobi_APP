package br.com.impacta.breathmobi_app.View;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;

import com.firebase.client.Firebase;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import br.com.impacta.breathmobi_app.Listener.ChildEventListener;
import br.com.impacta.breathmobi_app.R;
import br.com.impacta.breathmobi_app.Util.ComumActivity;
import br.com.impacta.breathmobi_app.Util.UtilLogin;
import br.com.impacta.breathmobi_app.View.Frag.FragHistorico;
import br.com.impacta.breathmobi_app.View.Frag.FragTesteAlcool;
import br.com.impacta.breathmobi_app.View.Frag.FragVoltaSegura;


/**
 * Created by TGarbulha on 07/05/2016.
 */
public class Logado extends ComumActivity {
    private Context context;
    private Drawer.Result HamburguerLeft;
    private AccountHeader.Result headerNavigationLeft;
    private Toolbar mToolbar;
    private Fragment frag;
    private FragmentTransaction ft;
    private Firebase firebase;
    private ChildEventListener ceListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telalogado);
        //
        inicializarVariavel();
        inicializarAcao(savedInstanceState);
    }

    private void inicializarVariavel() {
        context = getBaseContext();
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        ft = getSupportFragmentManager().beginTransaction();
        firebase = UtilLogin.getFirebase().child("Usuario");
        ceListener = new ChildEventListener();
        firebase.addChildEventListener(ceListener);

    }


    private void inicializarAcao(Bundle savedInstanceState) {


        headerNavigationLeft = new AccountHeader()
                .withActivity(this)
                .withCompactStyle(false)
                .withHeaderBackground(R.drawable.gradient)
                .addProfiles(new ProfileDrawerItem()
                                .withName("Thiago")
                                .withEmail("thiago@hotmail.com")
                                .withIcon(getResources()
                                        .getDrawable(R.drawable.perfil1))

                )
                .withOnAccountHeaderSelectionViewClickListener(new AccountHeader.OnAccountHeaderSelectionViewClickListener() {

                    @Override
                    public boolean onClick(View view, IProfile profile) {
                        ft.replace(R.id.container_frag, frag, "frag");
                        ft.commit();
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

                       mToolbar.setTitle(((PrimaryDrawerItem) iDrawerItem).getName());

                            if(i == 0){ // Realizar Teste
                                frag = new FragTesteAlcool();
                            }
                            else if(i == 1){ // LUXURY CAR
                                frag = new FragVoltaSegura();
                            }
                            else if(i == 2){ // SPORT CAR
                                frag = new FragHistorico();
                            }
                            else if(i == 3){ // OLD CAR
                                showToast("Logout");
                            }

                        ft.replace(R.id.container_frag, frag, "frag");
                        ft.commit();



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
                        .getDrawable(R.drawable.facebook_button)));
        //

        HamburguerLeft.addItem(new PrimaryDrawerItem()
                .withName("Volta Segura")
                .withIcon(getResources()
                        .getDrawable(R.drawable.circle_orange)));
        //
        HamburguerLeft.addItem(new PrimaryDrawerItem()
                .withName("Histórico")
                .withIcon(getResources()
                        .getDrawable(R.drawable.circle_orange)));
        //
        HamburguerLeft.addItem(new PrimaryDrawerItem()
                .withName("Sair")
                .withIcon(getResources()
                        .getDrawable(R.drawable.circle_orange)));


    }

    @Override
    public void onBackPressed() {
        if (HamburguerLeft.isDrawerOpen()) {
            HamburguerLeft.closeDrawer();
        }
        /*else if(fab.isOpened()){
            fab.close(true);
        }*/
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        firebase.removeEventListener(ceListener);
    }


}
