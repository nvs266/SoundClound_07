package com.framgia.mysoundcloud.data.source.remote;

import android.os.AsyncTask;

import com.framgia.mysoundcloud.data.model.Track;
import com.framgia.mysoundcloud.data.source.TrackDataSource;
import com.framgia.mysoundcloud.utils.Constant;
import com.framgia.mysoundcloud.utils.StringUtil;

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
 * Created by sonng266 on 13/03/2018.
 */

public abstract class BaseFetchTrackFromUrl extends AsyncTask<String, Void, List<Track>> {

    protected TrackDataSource.OnFetchDataListener<Track> mListener;

    protected BaseFetchTrackFromUrl(TrackDataSource.OnFetchDataListener<Track> listener) {
        mListener = listener;
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

    protected List<Track> getTracksFromJsonObject(JSONObject jsonObject) throws JSONException {
        ArrayList<Track> trackList = new ArrayList<>();

        JSONArray jsonCollection = jsonObject.getJSONArray(Track.TrackEntity.COLLECTION);
        for (int i = 0; i < jsonCollection.length(); i++) {
            JSONObject jsonTrack = getJsonTrack(jsonCollection.getJSONObject(i));
            Track track = parseJsonObjectToTrackObject(jsonTrack);
            if (track != null) {
                trackList.add(track);
            }
        }
        return trackList;
    }

    protected abstract JSONObject getJsonTrack(JSONObject jsonObject) throws JSONException;

    protected String getJSONStringFromURL(String urlString) throws IOException {
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

    protected String parseArtworkUrlToBetter(String artworkUrl) {
        if (artworkUrl != null) {
            return artworkUrl.replace(Track.TrackEntity.LARGE_IMAGE_SIZE,
                    Track.TrackEntity.BETTER_IMAGE_SIZE);
        }
        return null;
    }

    protected Track parseJsonObjectToTrackObject(JSONObject jsonTrack) {
        Track track = new Track();

        try {
            JSONObject jsonUser = jsonTrack.getJSONObject(Track.TrackEntity.USER);
            String artworkUrl = jsonTrack.getString(Track.TrackEntity.ARTWORK_URL);

            // Null artwork is replaced by user's avatar
            if (artworkUrl.equals(Constant.NULL_RESULT)) {
                artworkUrl = jsonTrack.getJSONObject(Track.TrackEntity.USER)
                        .getString(Track.TrackEntity.AVATAR_URL);
            }

            track.setArtworkUrl(parseArtworkUrlToBetter(artworkUrl));
            track.setDescription(jsonTrack.getString(Track.TrackEntity.DESCRIPTION));
            track.setDownloadable(jsonTrack.getBoolean(Track.TrackEntity.DOWNLOADABLE));
            track.setDownloadUrl(jsonTrack.getString(Track.TrackEntity.DOWNLOAD_URL));
            track.setDuration(jsonTrack.getInt(Track.TrackEntity.DURATION));
            track.setId(jsonTrack.getInt(Track.TrackEntity.ID));
            track.setPlaybackCount(jsonTrack.getInt(Track.TrackEntity.PLAYBACK_COUNT));
            track.setTitle(jsonTrack.getString(Track.TrackEntity.TITLE));
            track.setUri(StringUtil.getUrlStreamTrack(jsonTrack.getString(Track.TrackEntity.URI)));
            track.setLikesCount(jsonTrack.getInt(Track.TrackEntity.LIKES_COUNT));
            track.setUserName(jsonUser.getString(Track.TrackEntity.USERNAME));
        } catch (JSONException e) {
            return null;
        }

        return track;
    }
}
