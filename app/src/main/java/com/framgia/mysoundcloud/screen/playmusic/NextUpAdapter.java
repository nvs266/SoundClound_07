package com.framgia.mysoundcloud.screen.playmusic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.framgia.mysoundcloud.R;
import com.framgia.mysoundcloud.data.model.Track;
import com.framgia.mysoundcloud.screen.BaseRecyclerViewAdapter;
import com.framgia.mysoundcloud.utils.StringUtil;

import java.util.List;

/**
 * Created by sonng266 on 08/03/2018.
 */

public class NextUpAdapter extends BaseRecyclerViewAdapter<NextUpAdapter.ViewHolder> {

    private NextUpDialogFragment.NextUpItemClickedListener mListener;
    private List<Track> mTracks;

    protected NextUpAdapter(Context mContext,
                            NextUpDialogFragment.NextUpItemClickedListener listener,
                            List<Track> tracks) {
        super(mContext);
        mListener = listener;
        mTracks = tracks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return mTracks != null ? mTracks.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextTitle;
        private TextView mTextArtist;
        private ImageView mImageTrack;
        private TextView mTextDuration;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_next_up_dialog_item, parent, false));

            mTextTitle = itemView.findViewById(R.id.text_title);
            mTextArtist = itemView.findViewById(R.id.text_artist);
            mTextDuration = itemView.findViewById(R.id.text_duration);
            mImageTrack = itemView.findViewById(R.id.image_song);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener == null) return;
                    mListener.onTrackPositionClicked(getAdapterPosition());
                }
            });
        }

        void bindData(int position) {
            if (mTracks == null) return;
            mTextTitle.setText(mTracks.get(position).getTitle());
            mTextArtist.setText(mTracks.get(position).getUserName());
            mTextDuration.setText(StringUtil.parseMilliSecondsToTimer(
                    mTracks.get(position).getDuration()));
            Glide.with(getContext()).load(mTracks.get(position).getArtworkUrl())
                    .into(mImageTrack);
        }
    }
}
