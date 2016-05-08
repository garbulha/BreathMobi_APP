package br.com.impacta.breathmobi_app.View;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import br.com.impacta.breathmobi_app.R;
import br.com.impacta.breathmobi_app.Util.ComumActivity;


/**
 * Created by TGarbulha on 07/05/2016.
 */
public class LogadoTeste extends ComumActivity {
    private Context context;
    private Drawer.Result HamburguerLeft;
    private AccountHeader.Result headerNavigationLeft;
    private Toolbar mToolbar;
    private ViewPager mViewPager;

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


    }


    private void inicializarAcao(Bundle savedInstanceState) {


        headerNavigationLeft = new AccountHeader()
                .withActivity(this)
                .withCompactStyle(false)
                .withHeaderBackground(R.drawable.gradient)
                .addProfiles( new ProfileDrawerItem()
                                .withName("Thiago")
                                .withEmail("thiago@hotmail.com")
                                .withIcon(getResources()
                                        .getDrawable(R.drawable.circle_orange))
                )
                .withOnAccountHeaderSelectionViewClickListener(new AccountHeader.OnAccountHeaderSelectionViewClickListener() {

                    @Override
                    public boolean onClick(View view, IProfile profile) {
                        startActivity(new Intent(getApplicationContext(),EditarCliente.class));
                        return false;
                    }
                })
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        showToast("Aqui");
                        return false;
                    }
                })
                .build();


        HamburguerLeft = new Drawer()
                .withActivity(LogadoTeste.this)
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
                        showToast(String.valueOf(i));

                            /*Fragment frag = null;
                            mItemDrawerSelected = i;

                            if(i == 0){ // ALL CARS
                                frag = new CarFragment();
                            }
                            else if(i == 1){ // LUXURY CAR
                                frag = new LuxuryCarFragment();
                            }
                            else if(i == 2){ // SPORT CAR
                                frag = new SportCarFragment();
                            }
                            else if(i == 3){ // OLD CAR
                                frag = new OldCarFragment();
                            }
                            else if(i == 4){ // POPULAR CAR
                                frag = new PopularCarFragment();
                            }

                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.rl_fragment_container, frag, "mainFrag");
                            ft.commit();

                            mToolbar.setTitle( ((PrimaryDrawerItem) iDrawerItem).getName() );*/

                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        Toast.makeText(LogadoTeste.this, "onItemLongClick: " + i, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })

                .build();
        // Adicionando items no menu HAMBURGUER //
        HamburguerLeft.addItem(new PrimaryDrawerItem()
                .withName("Facebook")
                .withIcon(getResources()
                .getDrawable(R.drawable.facebook_button)));
        //
        HamburguerLeft.addItem(new PrimaryDrawerItem()
                .withName("Gmail")
                .withIcon(getResources()
                        .getDrawable(R.drawable.circle_orange)));





    }



}
