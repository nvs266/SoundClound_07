package com.framgia.mysoundcloud.screen.download;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.framgia.mysoundcloud.R;
import com.framgia.mysoundcloud.data.model.Track;
import com.framgia.mysoundcloud.screen.BaseFragment;
import com.framgia.mysoundcloud.screen.main.MainViewConstract;
import com.framgia.mysoundcloud.utils.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadedTracksFragment extends BaseFragment
        implements DownloadViewContract.View, View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_STORAGE = 2;
    private DownloadViewContract.Presenter mPresenter;
    private DownloadedTracksAdapter mDownloadedTracksAdapter;
    private MainViewConstract.TrackListListener mTrackListListener;
    private TextView mTextNumberTracks;
    private ImageView mImagePlayList;
    private List<Track> mTracks;

    public static DownloadedTracksFragment newInstance(
            MainViewConstract.TrackListListener listListener) {
        DownloadedTracksFragment downloadedTracksFragment = new DownloadedTracksFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constant.ARGUMENT_TRACK_LIST_LISTENER, listListener);
        downloadedTracksFragment.setArguments(args);
        return downloadedTracksFragment;
    }

    public DownloadedTracksFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initializeUI(View view) {
        mPresenter = new DownloadedTracksPresenter();
        mPresenter.setView(this);

        mImagePlayList = view.findViewById(R.id.action_image_play_list);
        mImagePlayList.setOnClickListener(this);
        mTextNumberTracks = view.findViewById(R.id.text_top_50);
        mTextNumberTracks.setText(R.string.msg_0_track);

        mTrackListListener = getArguments().getParcelable(Constant.ARGUMENT_TRACK_LIST_LISTENER);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        mDownloadedTracksAdapter = new DownloadedTracksAdapter(getContext(), mTrackListListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mDownloadedTracksAdapter);

        initializePermissionReadStorage();
    }

    @Override
    protected int getLayoutFragmentId() {
        return R.layout.fragment_download;
    }

    @Override
    public void showTracks(ArrayList<Track> trackList) {
        mTextNumberTracks.setText(String.format("%d tracks", trackList.size()));
        mDownloadedTracksAdapter.replaceData(trackList);
        mTracks = trackList;
    }

    @Override
    public void showNoTrack() {
        mTextNumberTracks.setText(R.string.msg_0_track);
    }

    @Override
    public void showLoadingTracksError(String message) {
        mTextNumberTracks.setText(R.string.msg_0_track);
    }

    @Override
    public void showLoadingIndicator() {

    }

    @Override
    public void hideLoadingIndicator() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_STORAGE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPresenter.loadTrack();
                } else {
                    Toast.makeText(getActivity(), R.string.msg_permission_denied, Toast.LENGTH_SHORT).show();
                }
            default:
                break;
        }
    }

    private void initializePermissionReadStorage() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    Constant.PERMISSONS, MY_PERMISSIONS_REQUEST_READ_STORAGE);
        } else {
            mPresenter.loadTrack();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_image_play_list:
                if (mTrackListListener == null) break;
                mTrackListListener.onPlayList(mTracks);
                break;
        }
    }
}
