package com.framgia.mysoundcloud.screen.newmusicgenres;

import com.framgia.mysoundcloud.data.repository.TrackRepository;

import java.util.List;

/**
 * Created by sonng266 on 14/04/2018.
 */

public class NewMusicGenresPresenter implements NewMusicGenrensContract.Presenter {

    private NewMusicGenrensContract.View mView;
    private TrackRepository mTrackRepository;
    private boolean mShouldShowError;

    public NewMusicGenresPresenter(TrackRepository trackRepository) {
        mTrackRepository = trackRepository;
        mShouldShowError = true;
    }

    @Override
    public void setView(NewMusicGenrensContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void getTracksAndGenres() {
        if (mTrackRepository == null) {
            mView.showErrorView();
            return;
        }

        List<String> genres = mTrackRepository.getListGenre();
        if (genres == null || genres.size() == 0) {
            mView.showErrorView();
            return;
        }

        mView.showProgressBar();
        for (int i = 0; i < genres.size(); i++) {
            getTracksByGenre(genres.get(i));
        }
    }

    @Override
    public void getTracksByGenre(String genre) {
    }
}
