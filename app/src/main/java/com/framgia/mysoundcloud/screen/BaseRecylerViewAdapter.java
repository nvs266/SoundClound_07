package com.framgia.mysoundcloud.screen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

/**
 * Created by sonng266 on 26/02/2018.
 */

public abstract class BaseRecylerViewAdapter<V extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<V> {

    private final Context mContext;

    protected BaseRecylerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    protected Context getContext() {
        return mContext;
    }
}
