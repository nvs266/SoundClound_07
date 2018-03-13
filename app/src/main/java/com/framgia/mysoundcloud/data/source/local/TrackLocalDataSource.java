package com.framgia.mysoundcloud.data.source.local;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.framgia.mysoundcloud.R;
import com.framgia.mysoundcloud.data.model.Track;
import com.framgia.mysoundcloud.data.source.TrackDataSource;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by sonng266 on 01/03/2018.
 */

public class TrackLocalDataSource implements TrackDataSource.LocalDataSource {

    private static final String QUERY_DIRECTORY_NAME = "%MySoundCloud%";
    private static final String VOLUME_NAME_EXTERNAL = "external";
    private static TrackLocalDataSource sTrackLocalDataSource;
    private Context mContext;

    private TrackLocalDataSource(Context context) {
        mContext = context;
    }

    public static TrackLocalDataSource getInstance() {
        return sTrackLocalDataSource;
    }

    @Override
    public void getTracksLocal(OnFetchDataListener<Track> listener) {
        ArrayList<Track> tracks = new ArrayList<>();

        ContentResolver contentResolver = mContext.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null,
                MediaStore.Audio.Media.DATA + " LIKE ?",
                new String[]{QUERY_DIRECTORY_NAME}, null);

        if (cursor == null) {
            listener.onFetchDataFailure(mContext.getString(R.string.msg_load_downloaded_failed));
            return;
        }

        if (!cursor.moveToFirst()) {
            listener.onFetchDataSuccess(tracks);
            return;
        }

        // Read data
        int titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int filePathColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
        int durationColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
        int idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID);

        do {
            Track track = new Track();
            track.setTitle(cursor.getString(titleColumn));
            track.setUri(cursor.getString(filePathColumn));
            track.setDuration(cursor.getInt(durationColumn));
            track.setId(cursor.getInt(idColumn));
            tracks.add(track);
        } while (cursor.moveToNext());

        listener.onFetchDataSuccess(tracks);
    }

    @Override
    public void searchTracksLocal(String trackName, OnFetchDataListener<Track> listener) {
    }

    @Override
    public boolean deleteTrack(Track track) {
        File file = new File(track.getUri());
        String where = MediaStore.MediaColumns.DATA + "=?";
        String[] selectionArgs = new String[]{file.getAbsolutePath()};
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri filesUri = MediaStore.Files.getContentUri(VOLUME_NAME_EXTERNAL);

        contentResolver.delete(filesUri, where, selectionArgs);
        return file.delete();
    }

    @Override
    public boolean insertTrack(Track newTrack) {
        return false;
    }

    public static void init(Context context) {
        if (sTrackLocalDataSource == null) {
            sTrackLocalDataSource = new TrackLocalDataSource(context);
        }
    }
}
