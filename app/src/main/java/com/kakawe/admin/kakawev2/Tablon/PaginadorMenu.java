package com.kakawe.admin.kakawev2.Tablon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by admin on 19/02/2018.
 */

public class PaginadorMenu extends FragmentPagerAdapter {
    Fragment[] fragmentos=new Fragment[2];
    String nombreComunidadActual;

    public PaginadorMenu(FragmentManager fm) {

        super(fm);
    }

    public void cargaDatos(String nombrecom) {
        nombreComunidadActual=nombrecom;
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0)
        {
            Fragment FS1 = new MenuComunidadesFragment();
            Bundle datos = new Bundle();
            datos.putString("nombreCom",nombreComunidadActual);
            FS1.setArguments(datos);

            return FS1;
        }
        else
        {
            Fragment FS2 = new MenuPrincipalFragment();
            Bundle datos = new Bundle();
            datos.putString("nombreCom",nombreComunidadActual);
            FS2.setArguments(datos);
            return FS2;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }


}