package com.framgia.mysoundcloud.screen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by sonng266 on 01/03/2018.
 */

public abstract class BaseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(getLayoutFragmentId(), container, false);
        initializeUI(view);
        return view;
    }

    protected abstract void initializeUI(View view);

    protected abstract int getLayoutFragmentId();
}
