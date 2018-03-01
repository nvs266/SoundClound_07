package com.framgia.mysoundcloud.data.source.remote;

import android.os.AsyncTask;

import com.framgia.mysoundcloud.data.model.Track;
import com.framgia.mysoundcloud.data.source.TrackDataSource;
import com.framgia.mysoundcloud.utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sonng266 on 28/02/2018.
 */

public class FetchTrackListFromUrl extends AsyncTask<String, Void, List<Track>> {

    private TrackDataSource.OnFetchDataListener<Track> mListener;

    public FetchTrackListFromUrl(TrackDataSource.OnFetchDataListener<Track> mListener) {
        this.mListener = mListener;
    }

    @Override
    protected List<Track> doInBackground(String... strings) {
        try {
            JSONObject jsonObject = new JSONObject(getJSONStringFromURL(strings[0]));
            return getTracksFromJsonObject(jsonObject);
        } catch (JSONException e) {
            mListener.onFetchDataFailure(e.getMessage());
        } catch (IOException e) {
            mListener.onFetchDataFailure(e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Track> tracks) {
        if (mListener != null) {
            mListener.onFetchDataSuccess(tracks);
        }
    }

    private List<Track> getTracksFromJsonObject(JSONObject jsonObject) throws JSONException {
        ArrayList<Track> trackList = new ArrayList<>();

        JSONArray jsonCollection = jsonObject.getJSONArray(Track.TrackEntity.COLLECTION);
        for (int i = 0; i < jsonCollection.length(); i++) {
            // Parse track
            JSONObject jsonTrack = jsonCollection.getJSONObject(i)
                    .getJSONObject(Track.TrackEntity.TRACK);
            Track track = new Track();
            track.setArtworkUrl(jsonTrack.getString(Track.TrackEntity.ARTWORK_URL));
            track.setDescription(jsonTrack.getString(Track.TrackEntity.DESCRIPTION));
            track.setDownloadable(jsonTrack.getBoolean(Track.TrackEntity.DOWNLOADABLE));
            track.setDownloadUrl(jsonTrack.getString(Track.TrackEntity.DOWNLOAD_URL));
            track.setDuration(jsonTrack.getInt(Track.TrackEntity.DURATION));
            track.setId(jsonTrack.getInt(Track.TrackEntity.ID));
            track.setPlaybackCount(jsonTrack.getInt(Track.TrackEntity.PLAYBACK_COUNT));
            track.setTitle(jsonTrack.getString(Track.TrackEntity.TITLE));
            track.setUri(jsonTrack.getString(Track.TrackEntity.URI));
            track.setLikesCount(jsonTrack.getInt(Track.TrackEntity.LIKES_COUNT));

            // Parse user
            JSONObject jsonUser = jsonTrack.getJSONObject(Track.TrackEntity.USER);
            track.setUserName(jsonUser.getString(Track.TrackEntity.USERNAME));

            trackList.add(track);
        }
        return trackList;
    }

    private String getJSONStringFromURL(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod(Constant.REQUEST_METHOD_GET);
        httpURLConnection.setReadTimeout(Constant.READ_TIME_OUT);
        httpURLConnection.setConnectTimeout(Constant.CONNECT_TIME_OUT);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append(Constant.BREAK_LINE);
        }
        br.close();
        httpURLConnection.disconnect();
        return sb.toString();
    }
}
