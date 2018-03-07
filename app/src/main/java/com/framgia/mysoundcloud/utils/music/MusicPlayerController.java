package com.framgia.mysoundcloud.utils.music;

import android.app.Notification;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;

import com.framgia.mysoundcloud.data.model.Track;
import com.framgia.mysoundcloud.utils.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by sonng266 on 04/03/2018.
 */

public class MusicPlayerController implements MusicPlayerManager, MediaPlayer.OnPreparedListener {

    private static final int PLAYBACK_POSITION_REFRESH_INTERVAL_MS = 1000;
    private static final int MSG_UPDATE_SEEK_BAR_POSITION = 0;

    private ScheduledExecutorService mExecutor;
    private Runnable mSeekBarPositionUpdateTask;
    private Handler mHandler;
    private Notification mNotification;
    private MediaPlayer mMediaPlayer;
    private PlaybackInfoListener mListener;
    private int mCurrentTrackPosition;
    private List<Track> mTracks;
    @PlaybackInfoListener.State
    private int mState;

    /**
     * handle both case: 1 track or list track
     *
     * @param tracks
     */
    @Override
    public void playTracks(Track... tracks) {
        if (tracks == null || tracks.length == 0) {
            notifyChangingState(PlaybackInfoListener.State.INVALID);
            return;
        }

        this.mTracks = new ArrayList<>();
        Collections.addAll(this.mTracks, tracks);
        mCurrentTrackPosition = 0;
        prepareLoadingTrack();
    }

    /**
     * stop playing music
     */
    @Override
    public void release() {
        if (mMediaPlayer == null) return;
        mMediaPlayer.release();
        mMediaPlayer = null;
    }

    /**
     * just return true when music is playing
     *
     * @return
     */
    @Override
    public boolean isPlaying() {
        return mMediaPlayer != null && mMediaPlayer.isPlaying();
    }

    /**
     * PAUSE <-> PLAY
     */
    @Override
    public void changeMediaState() {
        if (mMediaPlayer == null) return;

        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            notifyChangingState(PlaybackInfoListener.State.PAUSE);
        } else {
            mMediaPlayer.start();
            notifyChangingState(PlaybackInfoListener.State.PLAYING);

            if (mListener == null) return;
            if (mListener.isUpdatingProgressSeekBar() && mExecutor == null) {
                startProgressCallback();
            }
        }
    }

    @Override
    public void playNextTrack() {
        if (mCurrentTrackPosition == mTracks.size() - 1) return;
        mCurrentTrackPosition++;
        prepareLoadingTrack();
    }

    @Override
    public void playPreviousTrack() {
        if (mCurrentTrackPosition == 0) return;
        mCurrentTrackPosition--;
        prepareLoadingTrack();
    }

    /**
     * {@link ScheduledExecutorService} for scheduling call {@link Runnable}
     * {@link Runnable} call {@link Handler} to send message
     * {@link Handler} receive message at UI thread to updating seek bar
     */
    @Override
    public void startProgressCallback() {
        if (mExecutor == null) {
            mExecutor = Executors.newSingleThreadScheduledExecutor();
        }

        if (mSeekBarPositionUpdateTask == null) {
            mHandler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    if (msg.what == MSG_UPDATE_SEEK_BAR_POSITION) {
                        updateProgressCallbackTask();
                        return true;
                    }
                    return false;
                }
            });

            mSeekBarPositionUpdateTask = new Runnable() {
                @Override
                public void run() {
                    if (mHandler != null) {
                        mHandler.sendEmptyMessage(MSG_UPDATE_SEEK_BAR_POSITION);
                    }
                }
            };
        }

        mExecutor.scheduleAtFixedRate(mSeekBarPositionUpdateTask,
                0,
                PLAYBACK_POSITION_REFRESH_INTERVAL_MS,
                TimeUnit.MILLISECONDS);
    }

    /**
     * Stop updating seek bar
     */
    @Override
    public void endProgressCallback() {
        if (mExecutor == null) return;

        mExecutor.shutdownNow();
        mExecutor = null;
        mSeekBarPositionUpdateTask = null;

        if (mListener == null) return;
        mListener.onProgressUpdate(0);
    }

    @Override
    public void setPlaybackInfoListener(PlaybackInfoListener listener) {
        mListener = listener;

        if (listener != null && listener.isUpdatingProgressSeekBar()) {
            startProgressCallback();
        }
    }

    @Override
    public void seekTo(int percent) {
        if (getCurrentState() == PlaybackInfoListener.State.PAUSE ||
                getCurrentState() == PlaybackInfoListener.State.PLAYING) {
            mMediaPlayer.seekTo(mMediaPlayer.getDuration() / Constant.MAX_SEEK_BAR * percent);
        }
    }

    @Override
    public Notification getMusicNotification() {
        return null;
    }

    @Override
    public Track getCurrentTrack() {
        return mTracks != null ? mTracks.get(mCurrentTrackPosition) : null;
    }

    @Override
    public int getCurrentState() {
        return mState;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mMediaPlayer.start();
        notifyChangingState(PlaybackInfoListener.State.PLAYING);

        if (mListener == null) return;
        if (mListener.isUpdatingProgressSeekBar()) {
            startProgressCallback();
        }
    }

    private void prepareLoadingTrack() {
        if (mTracks == null || mTracks.isEmpty()) {
            notifyChangingState(PlaybackInfoListener.State.INVALID);
            return;
        }

        endProgressCallback();
        release();

        notifyChangingState(PlaybackInfoListener.State.PREPARE);
        loadTrack();

        if (mListener == null) return;
        mListener.onTrackChanged(mTracks.get(mCurrentTrackPosition));
    }

    private void loadTrack() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                endProgressCallback();
                notifyChangingState(PlaybackInfoListener.State.COMPLETED);
                playNextTrack();
            }
        });

        try {
            mMediaPlayer.setDataSource(mTracks.get(mCurrentTrackPosition).getUri());
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(this);
        } catch (IOException e) {
            notifyChangingState(PlaybackInfoListener.State.INVALID);
        }
    }

    private void updateProgressCallbackTask() {
        if (mMediaPlayer == null || !mMediaPlayer.isPlaying()) return;

        int currentPosition = mMediaPlayer.getCurrentPosition();
        if (mListener == null) return;

        mListener.onProgressUpdate((double) currentPosition /
                mMediaPlayer.getDuration() * Constant.MAX_SEEK_BAR);
    }

    private void notifyChangingState(@PlaybackInfoListener.State int state) {
        mState = state;
        if (mListener == null) return;
        mListener.onStateChanged(mState);
    }

    public static boolean downloadTrack(Track track) {
        if (track.isDownloadable()) {
            // download
        }
        return track.isDownloadable();
    }
}
