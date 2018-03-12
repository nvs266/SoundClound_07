package com.framgia.mysoundcloud.screen.musicgenres;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.framgia.mysoundcloud.R;
import com.framgia.mysoundcloud.data.model.Track;
import com.framgia.mysoundcloud.data.source.remote.DownloadTrackManager;
import com.framgia.mysoundcloud.screen.BaseRecyclerViewAdapter;
import com.framgia.mysoundcloud.screen.main.MainViewConstract;
import com.framgia.mysoundcloud.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sonng266 on 02/03/2018.
 */

public class MusicGenresAdapter extends BaseRecyclerViewAdapter<MusicGenresAdapter.ItemViewHolder>
        implements DownloadTrackManager.DownloadListener {

    private List<Track> mTracks;
    private MainViewConstract.TrackListListener mTrackListListener;
    private LayoutInflater mLayoutInflater;

    MusicGenresAdapter(Context context, MainViewConstract.TrackListListener trackListListener) {
        super(context);
        this.mTrackListListener = trackListListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(getContext());
        }
        View layoutItem = mLayoutInflater.inflate(R.layout.item_song, parent, false);
        return new ItemViewHolder(layoutItem, mTrackListListener);
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

    @Override
    public void onDownloadError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDownloading() {
        Toast.makeText(getContext(), getContext().getString(R.string.msg_downloading),
                Toast.LENGTH_SHORT).show();
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
        private TextView mTextOptions;

        ItemViewHolder(View itemView, final MainViewConstract.TrackListListener trackListListener) {
            super(itemView);

            mTextTitle = itemView.findViewById(R.id.text_title);
            mTextArtist = itemView.findViewById(R.id.text_artist);
            mTextDuration = itemView.findViewById(R.id.text_duration);
            mTextLikeCount = itemView.findViewById(R.id.text_number_favorite);
            mTextPlaybackCount = itemView.findViewById(R.id.text_number_play);
            mImageArtWork = itemView.findViewById(R.id.image_song);

            setupOptionsMenu(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (trackListListener == null) return;
                    trackListListener.onTrackClicked(mTrack);
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

        private void setupOptionsMenu(View itemView) {
            mTextOptions = itemView.findViewById(R.id.text_options);
            mTextOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(getContext(), mTextOptions);
                    popupMenu.inflate(R.menu.options_menu_item_track);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.action_add_to_next_up:
                                    if (mTrackListListener == null) break;
                                    mTrackListListener.onAddedToNextUp(mTrack);
                                    break;
                                case R.id.action_download:
                                    new DownloadTrackManager(getContext(),
                                            MusicGenresAdapter.this)
                                            .downloadTrack(mTrack);
                                    break;
                                default:
                                    break;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
        }
    }
}
