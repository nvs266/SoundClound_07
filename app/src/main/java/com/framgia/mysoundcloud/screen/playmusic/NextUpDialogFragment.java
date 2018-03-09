package com.framgia.mysoundcloud.screen.playmusic;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.mysoundcloud.R;
import com.framgia.mysoundcloud.data.model.Track;
import com.framgia.mysoundcloud.utils.Constant;

import java.util.List;

public class NextUpDialogFragment extends BottomSheetDialogFragment {

    private static List<Track> mTracks;

    public static NextUpDialogFragment newInstance(
            List<Track> tracks, NextUpItemClickedListener listener) {
        NextUpDialogFragment fragment = new NextUpDialogFragment();
        mTracks = tracks;
        Bundle args = new Bundle();
        args.putParcelable(Constant.ARGUMENT_NEXT_UP_LISTENER, listener);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_next_up_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        NextUpItemClickedListener listener =
                getArguments().getParcelable(Constant.ARGUMENT_NEXT_UP_LISTENER);
        RecyclerView recyclerView = (RecyclerView) view;
        NextUpAdapter nextUpAdapter = new NextUpAdapter(getContext(), listener, mTracks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(nextUpAdapter);
    }

    public interface NextUpItemClickedListener extends Parcelable {
        void onTrackPositionClicked(int position);
    }
}
