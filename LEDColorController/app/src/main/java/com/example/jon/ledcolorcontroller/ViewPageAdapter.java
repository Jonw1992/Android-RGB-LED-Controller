package com.example.jon.ledcolorcontroller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Jon on 5/31/16.
 */
public class ViewPageAdapter extends FragmentStatePagerAdapter{

    public ViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
        {
            return new ColorsFragment();
        }


        return new OtherFragment();

    }

    @Override
    public int getCount() {
        return 4;
    }
}
