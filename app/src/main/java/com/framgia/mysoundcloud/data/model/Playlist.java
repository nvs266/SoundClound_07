package com.framgia.mysoundcloud.data.model;

import java.util.List;

/**
 * Created by sonng266 on 14/03/2018.
 */

public class Playlist {

    private String mName;
    private List<Track> mTracks;

    public Playlist(String name, List<Track> tracks) {
        mName = name;
        mTracks = tracks;
    }

    public Playlist() {

    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<Track> getTracks() {
        return mTracks;
    }

    public void setTracks(List<Track> tracks) {
        mTracks = tracks;
    }
}
