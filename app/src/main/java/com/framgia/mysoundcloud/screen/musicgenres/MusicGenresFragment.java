package com.framgia.mysoundcloud.screen.musicgenres;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.framgia.mysoundcloud.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicGenresFragment extends Fragment implements MusicGenresContract.View {

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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.music_genres, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner mSpinner = view.findViewById(R.id.spinner_genres);
        mSpinner.setAdapter(adapter);

        mPresenter = new MusicGenresPresenter();
        mPresenter.setView(this);
    }
}
