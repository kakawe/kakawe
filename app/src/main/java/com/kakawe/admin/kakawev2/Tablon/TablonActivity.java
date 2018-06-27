package com.kakawe.admin.kakawev2.Tablon;

import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import com.kakawe.admin.kakawev2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.pixelcan.inkpageindicator.InkPageIndicator;

public class TablonActivity extends AppCompatActivity implements MenuComunidadesFragment.CierraDrawer, MenuPrincipalFragment.CierraDrawer {

    DrawerLayout menu;
    String nombrecom;
    private boolean isOutSideClicked;
    Fragment crear;
    PaginadorMenu paginadorMenu;

    private static DatabaseReference referencia;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_menu);


        nombrecom = getIntent().getStringExtra("comunidad");
        Fragment fragmentoSeleccionado2 = new ListaAnuncioFragment();
        FragmentManager fm2 = getSupportFragmentManager();
        FragmentTransaction t2 = fm2.beginTransaction();
        t2.replace(R.id.contenedorTablon, fragmentoSeleccionado2);
        Bundle datos = new Bundle();
        datos.putString("nombreCom", nombrecom);
        datos.putString("tipo", "Ofrezco");
        fragmentoSeleccionado2.setArguments(datos);
        t2.commit();

        //el nombre que se envia, será la comunidad a la que se registre nada mas entrar, o si hace login, habrá que buscar en que comunidad está metido y
        // mostrar los anuncios de esa como predeterminados


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tablón de anuncios");
        toolbar.setNavigationIcon(R.drawable.ic_menu_kakawe);
        setSupportActionBar(toolbar);

        ViewPager vp_menu = (ViewPager) findViewById(R.id.vp_menu);
        paginadorMenu = new PaginadorMenu(getSupportFragmentManager());

        paginadorMenu.cargaDatos(nombrecom);

        vp_menu.setAdapter(paginadorMenu);
        //1 es el último fragment. Con esto fuerzo que al salir, la paginación sea RTL (de derecha a izquierda)
        vp_menu.setCurrentItem(1);


        InkPageIndicator inkPageIndicator = (InkPageIndicator) findViewById(R.id.indicator);
        inkPageIndicator.setViewPager(vp_menu);

        crear = new MenuComunidadesFragment();
        menu = (DrawerLayout) findViewById(R.id.menu);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, menu, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        menu.addDrawerListener(toggle);
        //Adjunto oyente al drawer para que puede cambiarse de página en el ViewPager sin que se cierre el Drawer
        menu.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                //Cuando se abra, quiero que se bloquee abierto hasta que se perciba que se quiere cerrar
                //Si no lo bloqueara, el que escucharía el deslizamiento no sería el Pager sino el Drawer, así
                // que no podría deslizarme entra páginas
                menu.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
                Log.v("Drawer", "Abierto");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                Log.v("Drawer", "Cerrado");
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        toggle.syncState();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (menu.isDrawerOpen(Gravity.START)) {

                View content = findViewById(R.id.vp_menu);
                int[] contentLocation = new int[2];
                content.getLocationOnScreen(contentLocation);
                Rect rect = new Rect(contentLocation[0],
                        contentLocation[1],
                        contentLocation[0] + content.getWidth(),
                        contentLocation[1] + content.getHeight());

                if (!(rect.contains((int) event.getX(), (int) event.getY()))) {
                    isOutSideClicked = true;
                } else {
                    isOutSideClicked = false;
                }

            } else {
                return super.dispatchTouchEvent(event);
            }
        } else if (event.getAction() == MotionEvent.ACTION_DOWN && isOutSideClicked) {
            isOutSideClicked = false;
            return super.dispatchTouchEvent(event);
        } else if (event.getAction() == MotionEvent.ACTION_MOVE && isOutSideClicked) {
            isOutSideClicked = false;
            return super.dispatchTouchEvent(event);
        }
        if (isOutSideClicked) {
            menu.closeDrawer(GravityCompat.START, true);
        }


        return super.dispatchTouchEvent(event);
    }


    @Override
    public void cerrarDrawer() {
        menu.closeDrawer(GravityCompat.START, true);
    }
}
