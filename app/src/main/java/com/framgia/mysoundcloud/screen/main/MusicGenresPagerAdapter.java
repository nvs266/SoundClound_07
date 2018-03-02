package com.framgia.mysoundcloud.screen.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.framgia.mysoundcloud.screen.musicgenres.MusicGenresFragment;

/**
 * Created by sonng266 on 27/02/2018.
 */

public class MusicGenresPagerAdapter extends FragmentPagerAdapter {

    private static final int TAB_NUMBER = 2;

    public MusicGenresPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new MusicGenresFragment();
    }

    @Override
    public int getCount() {
        return TAB_NUMBER;
    }
}
