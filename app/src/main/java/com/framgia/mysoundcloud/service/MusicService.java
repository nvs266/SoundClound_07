package com.framgia.mysoundcloud.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.framgia.mysoundcloud.data.model.Track;
import com.framgia.mysoundcloud.utils.music.MusicPlayerController;
import com.framgia.mysoundcloud.utils.music.MusicPlayerManager;
import com.framgia.mysoundcloud.utils.music.PlaybackInfoListener;


public class MusicService extends Service {

    // Action
    public static final String ACTION_CHANGE_MEDIA_STATE = "ACTION_PLAY_PAUSE";
    public static final String ACTION_NEXT_TRACK = "ACTION_NEXT_TRACK";
    public static final String ACTION_PREVIOUS_TRACK = "ACTION_PREVIOUS_TRACK";

    // Notification
    public static final int NOTIFY_ID = 1;

    private final IBinder mBinder = new LocalBinder();

    private MusicPlayerManager mMusicPlayerManager;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleIntent(intent);
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        handleIntent(intent);
        return mBinder;
    }


    public void handleIntent(Intent intent) {
        String action = intent != null ? intent.getAction() : null;
        if (action == null) return;

        switch (action) {
            case ACTION_CHANGE_MEDIA_STATE:
                changeMediaState();
                break;
            case ACTION_PREVIOUS_TRACK:
                playPrevious();
                break;
            case ACTION_NEXT_TRACK:
                playNext();
                break;
            default:
                break;
        }
    }

    public void playTracks(Track... tracks) {
        if (mMusicPlayerManager == null) {
            mMusicPlayerManager = new MusicPlayerController();
        }

        mMusicPlayerManager.playTracks(tracks);
    }

    public void setPlaybackListener(PlaybackInfoListener playbackListener) {
        if (mMusicPlayerManager == null) return;
        mMusicPlayerManager.setPlaybackInfoListener(playbackListener);
    }

    public Track getCurrentTrack() {
        return mMusicPlayerManager != null ? mMusicPlayerManager.getCurrentTrack() : null;
    }

    public boolean isPlaying() {
        return mMusicPlayerManager != null && mMusicPlayerManager.isPlaying();
    }

    public void actionSeekTo(int userSelectedPosition) {
        if (mMusicPlayerManager == null) return;
        mMusicPlayerManager.seekTo(userSelectedPosition);
    }

    public void changeMediaState() {
        if (mMusicPlayerManager == null) return;
        mMusicPlayerManager.changeMediaState();
    }

    public void playNext() {
        if (mMusicPlayerManager == null) return;
        mMusicPlayerManager.playNextTrack();
    }

    public void playPrevious() {
        if (mMusicPlayerManager == null) return;
        mMusicPlayerManager.playPreviousTrack();
    }

    public int getMediaState() {
        if (mMusicPlayerManager == null) return PlaybackInfoListener.State.INVALID;
        return mMusicPlayerManager.getCurrentState();
    }

    public class LocalBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }
}
