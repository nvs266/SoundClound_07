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

        void showTabLayout();

        void hideTabLayout();
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<MainViewConstract.View> {
    }
}
