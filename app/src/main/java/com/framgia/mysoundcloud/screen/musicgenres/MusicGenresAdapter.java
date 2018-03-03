package com.framgia.mysoundcloud.screen.musicgenres;

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
import com.framgia.mysoundcloud.screen.BaseRecylerViewAdapter;
import com.framgia.mysoundcloud.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sonng266 on 02/03/2018.
 */

public class MusicGenresAdapter extends BaseRecylerViewAdapter<MusicGenresAdapter.ItemViewHolder> {

    private List<Track> mTracks;
    private ItemClickListener mItemClickListener;
    private LayoutInflater mLayoutInflater;

    protected MusicGenresAdapter(Context context, ItemClickListener itemClickListener) {
        super(context);
        this.mItemClickListener = itemClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(getContext());
        }
        View layoutItem = mLayoutInflater.inflate(R.layout.item_song, parent, false);
        return new ItemViewHolder(layoutItem, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if (mTracks != null) {
            holder.setData(mTracks.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mTracks == null ? 0 : mTracks.size();
    }

    void replaceData(List<Track> tracks) {
        if (mTracks == null) mTracks = new ArrayList<>();

        mTracks.clear();
        if (tracks != null) {
            mTracks.addAll(tracks);
        }

        notifyDataSetChanged();
    }

    /**
     * ItemViewHolder
     */
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextTitle;
        private TextView mTextArtist;
        private ImageView mImageArtWork;
        private TextView mTextPlaybackCount;
        private TextView mTextLikeCount;
        private TextView mTextDuration;
        private Track mTrack;

        public ItemViewHolder(View itemView, final ItemClickListener itemClickListener) {
            super(itemView);

            mTextTitle = itemView.findViewById(R.id.text_title);
            mTextArtist = itemView.findViewById(R.id.text_artist);
            mTextDuration = itemView.findViewById(R.id.text_duration);
            mTextLikeCount = itemView.findViewById(R.id.text_number_favorite);
            mTextPlaybackCount = itemView.findViewById(R.id.text_number_play);
            mImageArtWork = itemView.findViewById(R.id.image_song);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClicked(mTrack);
                }
            });
        }

        public void setData(Track track) {
            if (track == null) return;

            this.mTrack = track;
            mTextTitle.setText(track.getTitle());
            mTextPlaybackCount.setText(String.valueOf(track.getPlaybackCount()));
            mTextArtist.setText(track.getUserName());
            mTextLikeCount.setText(String.valueOf(track.getLikesCount()));
            mTextDuration.setText(StringUtil.parseMilliSecondsToTimer(track.getDuration()));
            Glide.with(getContext()).load(track.getArtworkUrl()).into(mImageArtWork);
        }
    }


    /**
     * ItemClickListener
     */
    public interface ItemClickListener {
        void onItemClicked(Track track);
    }
}
