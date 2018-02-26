package com.framgia.mysoundcloud.screen.main;

import com.framgia.mysoundcloud.screen.BasePresenter;

/**
 * Created by sonng266 on 26/02/2018.
 */

interface MainViewConstract {
    /**
     * View.
     */
    interface View {
        void updateTitle(String title);

        void showMiniControlMusic();

        void hideMiniControlMusic();

        void showSearchResults();

        void showTablayout();

        void hideTablayout();
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<MainViewConstract.View> {
        void handleClick();
    }
}
