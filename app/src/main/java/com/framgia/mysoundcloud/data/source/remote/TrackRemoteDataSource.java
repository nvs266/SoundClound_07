package com.framgia.mysoundcloud.data.source.remote;

import com.framgia.mysoundcloud.data.model.Track;
import com.framgia.mysoundcloud.data.source.TrackDataSource;
import com.framgia.mysoundcloud.utils.StringUtil;

/**
 * Created by sonng266 on 28/02/2018.
 */

public class TrackRemoteDataSource implements TrackDataSource.RemoteDataSource{

    private static TrackRemoteDataSource sTrackRemoteDataSource;

    public static TrackRemoteDataSource getInstance() {
        if (sTrackRemoteDataSource == null) {
            sTrackRemoteDataSource = new TrackRemoteDataSource();
        }
        return sTrackRemoteDataSource;
    }

    @Override
    public void getTracksRemote(
            String genre, int limit, int offSet, OnFetchDataListener<Track> listener) {
        new FetchTrackListFromUrl(listener)
                .execute(StringUtil.convertUrlFetchMusicGenre(genre, limit, offSet));
    }

    @Override
    public void searchTracksRemote(String trackName, OnFetchDataListener<Track> listener) {

    }
}
