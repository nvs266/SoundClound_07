package com.framgia.mysoundcloud.screen.musicgenres;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.framgia.mysoundcloud.R;
import com.framgia.mysoundcloud.data.model.Track;
import com.framgia.mysoundcloud.data.repository.TrackRepository;
import com.framgia.mysoundcloud.data.source.TrackDataSource;
import com.framgia.mysoundcloud.utils.Constant;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicGenresFragment extends Fragment implements MusicGenresContract.View,
        TrackDataSource.OnFetchDataListener<Track> {

    private MusicGenresContract.Presenter mPresenter;

    public MusicGenresFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music_genres, container, false);
        initializeUI(view);
        return view;
    }

    private void initializeUI(View view) {
        ArrayAdapter<CharSequence> genresAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.music_genres, android.R.layout.simple_spinner_item);
        genresAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinnerGenres = view.findViewById(R.id.spinner_genres);
        spinnerGenres.setAdapter(genresAdapter);

        ArrayAdapter<CharSequence> topTypeAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.top_types, android.R.layout.simple_spinner_item);
        topTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinnerTops = view.findViewById(R.id.spinner_picker_top_songs);
        spinnerTops.setAdapter(topTypeAdapter);

        mPresenter = new MusicGenresPresenter();
        mPresenter.setView(this);

        TrackRepository trackRepository = TrackRepository.getInstance();
        trackRepository.getTracksRemote(Constant.GENRE_DEFAULT,
                Constant.LIMIT_DEFAULT, this);
    }

    @Override
    public void onFetchDataSuccess(List<Track> data) {
    }

    @Override
    public void onFetchDataFailure(String message) {
    }
}
