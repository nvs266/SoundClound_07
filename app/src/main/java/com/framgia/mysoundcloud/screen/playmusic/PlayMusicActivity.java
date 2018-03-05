package com.framgia.mysoundcloud.screen.playmusic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.framgia.mysoundcloud.R;
import com.framgia.mysoundcloud.data.model.Track;
import com.framgia.mysoundcloud.utils.Constant;
import com.framgia.mysoundcloud.widget.DialogManager;

public class PlayMusicActivity extends AppCompatActivity
        implements PlayMusicContract.View, View.OnClickListener {

    private Track mTrack;
    private PlayMusicContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        mPresenter = new PlayMusicPresenter();
        mPresenter.setView(this);

        initializeUI();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_show_next_up:
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu_next_up, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_button_action_description:
                new DialogManager(this).dialogMessage(mTrack.getDescription(),
                        getString(R.string.title_description));
                break;
            case R.id.image_button_action_download:
                break;
            case R.id.image_action_next_song:
                break;
            case R.id.image_action_previous_song:
                break;
            case R.id.image_action_play_pause:
                break;
            case R.id.image_action_shuffle:
                break;
            case R.id.image_action_loop_type:
                break;
            default:
                break;
        }
    }

    private void initializeUI() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;

        mTrack = bundle.getParcelable(Constant.BUNDLE_TRACK);
        if (mTrack == null) return;

        getSupportActionBar().setTitle(mTrack.getTitle());
        getSupportActionBar().setSubtitle(mTrack.getUserName());
        ImageView imageBackground = findViewById(R.id.image_background);
        ImageView imageSong = findViewById(R.id.image_song);
        Glide.with(this).load(mTrack.getArtworkUrl()).into(imageBackground);
        Glide.with(this).load(mTrack.getArtworkUrl()).into(imageSong);
    }
}
