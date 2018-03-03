package com.framgia.mysoundcloud.screen.playmusic;

import com.framgia.mysoundcloud.data.model.Track;
import com.framgia.mysoundcloud.utils.music.MusicPlayerController;

/**
 * Created by sonng266 on 03/03/2018.
 */

public class PlayMusicPresenter implements PlayMusicContract.Presenter {

    private PlayMusicContract.View mView;

    @Override
    public void setView(PlayMusicContract.View view) {
        this.mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void downloadTrack(Track track) {
        if (!MusicPlayerController.downloadTrack(track)) {
            mView.notifyCantDownload();
        } else {
            // download
        }
    }
}
