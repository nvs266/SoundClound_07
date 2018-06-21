package com.framgia.mysoundcloud.screen.newmusicgenres;

import com.framgia.mysoundcloud.data.model.Track;
import com.framgia.mysoundcloud.screen.BasePresenter;

import java.util.List;

/**
 * Created by sonng266 on 14/04/2018.
 */

public interface NewMusicGenrensContract {
    /**
     * View
     */
    interface View {
        void showProgressBar();

        void hideProgressBar();

        void showTracksByGenre(String genre, List<Track> tracks);

        void showErrorView();

        void hideErrorView();
    }

    /**
     * Presenter
     */
    interface Presenter extends BasePresenter<View> {
        void getTracksAndGenres();

        void getTracksByGenre(String genre);
    }
}
