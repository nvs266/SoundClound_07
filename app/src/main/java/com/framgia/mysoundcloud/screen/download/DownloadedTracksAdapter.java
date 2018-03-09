package com.framgia.mysoundcloud.screen.download;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.framgia.mysoundcloud.R;
import com.framgia.mysoundcloud.data.model.Track;
import com.framgia.mysoundcloud.screen.BaseRecyclerViewAdapter;
import com.framgia.mysoundcloud.screen.main.MainViewConstract;
import com.framgia.mysoundcloud.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sonng266 on 11/03/2018.
 */

public class DownloadedTracksAdapter
        extends BaseRecyclerViewAdapter<DownloadedTracksAdapter.ViewHolder> {

    private List<Track> mTracks;
    private LayoutInflater mLayoutInflater;
    private MainViewConstract.TrackListListener mListener;

    public DownloadedTracksAdapter(Context context,
                                   MainViewConstract.TrackListListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }

        View layoutItem = mLayoutInflater.inflate(R.layout.item_downloaded_track,
                parent, false);
        return new ViewHolder(layoutItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mTracks == null || mTracks.isEmpty()) return;
        holder.bindData(mTracks.get(position));
    }

    @Override
    public int getItemCount() {
        return mTracks != null ? mTracks.size() : 0;
    }

    public void replaceData(List<Track> tracks) {
        if (tracks == null) return;
        if (mTracks == null) mTracks = new ArrayList<>();

        mTracks.clear();
        mTracks.addAll(tracks);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextDuration;
        private TextView mTextOptions;
        private TextView mTextTitle;
        private TextView mTextArtist;
        private Track mTrack;

        ViewHolder(View itemView) {
            super(itemView);

            mTextArtist = itemView.findViewById(R.id.text_artist);
            mTextDuration = itemView.findViewById(R.id.text_duration);
            mTextTitle = itemView.findViewById(R.id.text_title);
            mTextOptions = itemView.findViewById(R.id.text_options);
            mTextOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(getContext(), mTextOptions);
                    popupMenu.inflate(R.menu.options_menu_item_download_track);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.action_add_to_next_up:
                                    if (mListener == null) break;
                                    mListener.onAddedToNextUp(mTrack);
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener == null) return;
                    mListener.onTrackClicked(mTrack);
                }
            });
        }

        void bindData(Track track) {
            if (track == null) return;
            mTrack = track;
            mTextDuration.setText(StringUtil.parseMilliSecondsToTimer(track.getDuration()));
            mTextTitle.setText(track.getTitle());
            mTextArtist.setText(track.getUserName());
        }
    }
}
