package com.framgia.mysoundcloud.screen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

/**
 * Created by sonng266 on 26/02/2018.
 */

public abstract class BaseRecyclerViewAdapter<V extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<V> {

    private final Context mContext;

    protected BaseRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    protected Context getContext() {
        return mContext;
    }
}
