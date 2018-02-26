package com.example.admin.kakawev2.Tablon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

/**
 * Created by admin on 19/02/2018.
 */

public class PaginadorMenu extends FragmentPagerAdapter {
    Fragment[] fragmentos=new Fragment[2];

    public PaginadorMenu(FragmentManager fm) {

        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0)
        {
            return new MenuComunidadesFragment();
        }
        else
        {
            return new MenuPrincipalFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}