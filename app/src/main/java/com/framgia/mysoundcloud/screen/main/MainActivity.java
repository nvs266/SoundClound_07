package com.framgia.mysoundcloud.screen.main;

import android.graphics.PorterDuff;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.framgia.mysoundcloud.R;
import com.framgia.mysoundcloud.screen.musicgenres.MusicGenresPagerAdapter;
import com.framgia.mysoundcloud.widget.DialogManager;

public class MainActivity extends AppCompatActivity implements MainViewConstract.View, SearchView.OnQueryTextListener, TabLayout.OnTabSelectedListener {

    private MainViewConstract.Presenter mPresenter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeToolbar();
        initializeUI();
    }

    private void initializeUI() {
        mPresenter = new MainViewPresenter();
        mPresenter.setView(this);

        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.viewpager);

        mViewPager.setAdapter(new MusicGenresPagerAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        initializeSearchView(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void updateTitle(String title) {
        setTitle(title);
    }

    @Override
    public void showMiniControlMusic() {

    }

    @Override
    public void hideMiniControlMusic() {

    }

    @Override
    public void showSearchResults() {

    }

    @Override
    public void showTablayout() {
        mTabLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTablayout() {
        mTabLayout.setVisibility(View.GONE);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        new DialogManager(this).dialogMessage(getString(R.string.msg_feature_is_coming), getString(R.string.msg_opps));
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private void initializeToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        updateTitle(getString(R.string.music_genres));
    }

    private void initializeSearchView(Menu menu) {
        MenuItem searchMenu = menu.findItem(R.id.action_search);
        if (searchMenu == null) return;

        SearchView searchView = (SearchView) searchMenu.getActionView();
        searchView.setQueryHint(getString(R.string.msg_finding_tracks));
        searchView.setOnQueryTextListener(this);

        // Hanlde when searchView closed
        searchMenu.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // do something
                hideTablayout();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // do something
                showTablayout();
                return true;
            }
        });
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int tabIconColor = ContextCompat.getColor(this, R.color.colorAccent);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);

        if (tab.getPosition() == 0) {
            updateTitle(getString(R.string.music_genres));
        } else updateTitle(getString(R.string.action_download));

        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        int tabIconColor = ContextCompat.getColor(this, R.color.colorPrimary);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
