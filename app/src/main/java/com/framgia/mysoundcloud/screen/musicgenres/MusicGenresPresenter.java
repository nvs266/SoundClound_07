package com.framgia.mysoundcloud.screen.musicgenres;

/**
 * Created by sonng266 on 27/02/2018.
 */

public class MusicGenresPresenter implements MusicGenresContract.Presenter {
    private MusicGenresContract.View mView;

    @Override
    public void setView(MusicGenresContract.View view) {
        this.mView = mView;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
