package com.framgia.mysoundcloud.data.source.remote;

import com.framgia.mysoundcloud.data.model.Track;
import com.framgia.mysoundcloud.data.source.TrackDataSource;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sonng266 on 28/02/2018.
 */

public class FetchGenresFromUrl extends BaseFetchTrackFromUrl {

    protected FetchGenresFromUrl(TrackDataSource.OnFetchDataListener<Track> listener) {
        super(listener);
    }

    @Override
    protected JSONObject getJsonTrack(JSONObject jsonObject) throws JSONException {
        return jsonObject.getJSONObject(Track.TrackEntity.TRACK);
    }
}
