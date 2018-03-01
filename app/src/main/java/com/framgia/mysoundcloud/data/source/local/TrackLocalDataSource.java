package com.framgia.mysoundcloud.data.source.local;

import com.framgia.mysoundcloud.data.model.Track;
import com.framgia.mysoundcloud.data.source.TrackDataSource;

/**
 * Created by sonng266 on 01/03/2018.
 */

public class TrackLocalDataSource implements TrackDataSource.LocalDataSource {

    private static TrackLocalDataSource sTrackLocalDataSource;

    public static TrackLocalDataSource getInstance() {
        if (sTrackLocalDataSource == null) {
            sTrackLocalDataSource = new TrackLocalDataSource();
        }
        return sTrackLocalDataSource;
    }

    @Override
    public void getTracksLocal(OnFetchDataListener<Track> listener) {

    }

    @Override
    public void searchTracksLocal(String trackName, OnFetchDataListener<Track> listener) {

    }

    @Override
    public boolean deleteTrack(Track track) {
        return false;
    }

    @Override
    public boolean insertTrack(Track newTrack) {
        return false;
    }
}
