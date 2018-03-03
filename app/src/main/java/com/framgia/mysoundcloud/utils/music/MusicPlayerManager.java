package com.framgia.mysoundcloud.utils.music;

import android.app.Notification;

import com.framgia.mysoundcloud.data.model.Track;

/**
 * Created by sonng266 on 04/03/2018.
 */

public interface MusicPlayerManager {

    void playTracks(Track... tracks);

    void release();

    boolean isPlaying();

    void changeMediaState();

    void playNextTrack();

    void playPreviousTrack();

    void startProgressCallback();

    void endProgressCallback();

    void setPlaybackInfoListener(PlaybackInfoListener listener);

    void seekTo(int percent);

    Notification getMusicNotification();

    Track getCurrentTrack();

    int getCurrentState();
}
