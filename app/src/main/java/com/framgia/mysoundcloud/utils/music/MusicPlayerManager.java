package com.framgia.mysoundcloud.utils.music;

import com.framgia.mysoundcloud.data.model.Track;

/**
 * Created by sonng266 on 04/03/2018.
 */

public interface MusicPlayerManager {

    void playTracks(Track... tracks);

    void release();

    void changeMediaState();

    void playNextTrack();

    void playPreviousTrack();

    void startProgressCallback();

    void endProgressCallback();

    void setPlaybackInfoListener(PlaybackInfoListener listener);

    void seekTo(int percent);

    Track getCurrentTrack();

    int getCurrentState();
}
