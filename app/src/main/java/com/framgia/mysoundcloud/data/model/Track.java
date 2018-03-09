package com.framgia.mysoundcloud.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sonng266 on 28/02/2018.
 */

public class Track implements Parcelable {

    private String mArtworkUrl;
    private String mDescription;
    private boolean mDownloadable;
    private String mDownloadUrl;
    private int mDuration;
    private int mId;
    private int mLikesCount;
    private String mTitle;
    private String mUri;
    private String mUserName;
    private int mPlaybackCount;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mArtworkUrl);
        dest.writeString(mDescription);
        dest.writeByte((byte) (mDownloadable ? 1 : 0));
        dest.writeString(mDownloadUrl);
        dest.writeInt(mDuration);
        dest.writeInt(mId);
        dest.writeInt(mLikesCount);
        dest.writeString(mTitle);
        dest.writeString(mUri);
        dest.writeString(mUserName);
        dest.writeInt(mPlaybackCount);
    }

    public Track(Parcel in) {
        mArtworkUrl = in.readString();
        mDescription = in.readString();
        mDownloadable = in.readByte() != 0;
        mDownloadUrl = in.readString();
        mDuration = in.readInt();
        mId = in.readInt();
        mLikesCount = in.readInt();
        mTitle = in.readString();
        mUri = in.readString();
        mUserName = in.readString();
        mPlaybackCount = in.readInt();
    }

    /**
     * Set default variables
     */
    public Track() {
        mArtworkUrl = "";
        mDescription = "";
        mDownloadable = false;
        mDownloadUrl = "";
        mDuration = 0;
        mId = 0;
        mLikesCount = 0;
        mTitle = "";
        mUri = "";
        mUserName = "";
        mPlaybackCount = 0;
    }

    public String getArtworkUrl() {
        return mArtworkUrl;
    }

    public void setArtworkUrl(String artworkUrl) {
        mArtworkUrl = artworkUrl;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean isDownloadable() {
        return mDownloadable;
    }

    public void setDownloadable(boolean downloadable) {
        mDownloadable = downloadable;
    }

    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        mDownloadUrl = downloadUrl;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getLikesCount() {
        return mLikesCount;
    }

    public void setLikesCount(int likesCount) {
        mLikesCount = likesCount;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public int getPlaybackCount() {
        return mPlaybackCount;
    }

    public void setPlaybackCount(int playbackCount) {
        mPlaybackCount = playbackCount;
    }

    public static final Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };

    public static class TrackEntity {
        public static final String COLLECTION = "collection";
        public static final String ARTWORK_URL = "artwork_url";
        public static final String DESCRIPTION = "description";
        public static final String DOWNLOADABLE = "downloadable";
        public static final String DOWNLOAD_URL = "download_url";
        public static final String DURATION = "duration";
        public static final String ID = "id";
        public static final String PLAYBACK_COUNT = "playback_count";
        public static final String TITLE = "title";
        public static final String URI = "uri";
        public static final String USER = "user";
        public static final String TRACK = "track";
        public static final String USERNAME = "username";
        public static final String LIKES_COUNT = "likes_count";
        public static final String AVATAR_URL = "avatar_url";
        public static final String LARGE_IMAGE_SIZE = "large";
        public static final String BETTER_IMAGE_SIZE = "original";
    }
}
