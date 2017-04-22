package com.kilingzhang.music.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kilingzhang.music.Fragement.SearchResponseAblumFragement;
import com.kilingzhang.music.Fragement.SearchResponseMvFragement;
import com.kilingzhang.music.Fragement.SearchResponseRadioFragement;
import com.kilingzhang.music.Fragement.SearchResponseSingerFragement;
import com.kilingzhang.music.Fragement.SearchResponseSongFragement;
import com.kilingzhang.music.Fragement.SearchResponseSonglistFragement;
import com.kilingzhang.music.Fragement.SearchResponseUserFragement;
import com.kilingzhang.music.R;

import java.util.ArrayList;
import java.util.List;

import static com.kilingzhang.music.Config.APP_Token;

public class MusicSearchResponseActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MusicSearchResponseActi";
    private String searchWords = "";
    private EditText searchWordsEdit;
    private Toolbar toolbar;

    private ViewPager mViewPager;
    private TextView song;
    private TextView singer;
    private TextView album;
    private TextView songlist;
    private TextView mv;
    private TextView radio;
    private TextView user;
    private ImageView tabIndicator;
    private HorizontalScrollView horizontalScrollView;

    private FragmentPagerAdapter mAdapter;
    private int pageIndex = 0;
    private int mScreenTab;
    private List<Fragment> mFregements = new ArrayList<Fragment>();
    private List<TextView> mTextViews = new ArrayList<TextView>();
    private List<Integer> mTextWidth = new ArrayList<Integer>();

    private Fragment SearchResponseSong;
    private Fragment SearchResponseSinger;
    private Fragment SearchResponseAblum;
    private Fragment SearchResponseSonglist;
    private Fragment SearchResponseMv;
    private Fragment SearchResponseRadio;
    private Fragment SearchResponseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_search_response);
        Intent intent = getIntent();
        if (intent != null) {
            searchWords = intent.getStringExtra(APP_Token + "searchWords");
        }
        ininViews();
        initViewWidth();
        ininEvents();
        setCurrentItem(0);
    }


    private void ininViews() {
        initToolBar();
        searchWordsEdit = (EditText) findViewById(R.id.id_search_words_edit);
        searchWordsEdit.setText(searchWords);
        mViewPager = (ViewPager) findViewById(R.id.id_search_words_edit_response_viewpager);

        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.id_music_search_response_scrollview);

        song = (TextView) findViewById(R.id.id_search_words_edit_response_tab_song);
        singer = (TextView) findViewById(R.id.id_search_words_edit_response_tab_singer);
        album = (TextView) findViewById(R.id.id_search_words_edit_response_tab_album);
        songlist = (TextView) findViewById(R.id.id_search_words_edit_response_tab_songlist);
        mv = (TextView) findViewById(R.id.id_search_words_edit_response_tab_mv);
        radio = (TextView) findViewById(R.id.id_search_words_edit_response_tab_radio);
        user = (TextView) findViewById(R.id.id_search_words_edit_response_tab_user);


        mTextViews.add(song);
        mTextViews.add(singer);
        mTextViews.add(album);
        mTextViews.add(songlist);
        mTextViews.add(mv);
        mTextViews.add(radio);
        mTextViews.add(user);


        tabIndicator = (ImageView) findViewById(R.id.id_search_words_edit_response_tab_indicator);

        SearchResponseSong = new SearchResponseUserFragement();
        SearchResponseSinger = new SearchResponseSingerFragement();
        SearchResponseAblum = new SearchResponseAblumFragement();
        SearchResponseSonglist = new SearchResponseSonglistFragement();
        SearchResponseMv = new SearchResponseMvFragement();
        SearchResponseRadio = new SearchResponseRadioFragement();
        SearchResponseUser = new SearchResponseUserFragement();

        mFregements.add(SearchResponseSong);
        mFregements.add(SearchResponseSinger);
        mFregements.add(SearchResponseAblum);
        mFregements.add(SearchResponseSonglist);
        mFregements.add(SearchResponseMv);
        mFregements.add(SearchResponseRadio);
        mFregements.add(SearchResponseUser);

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFregements.get(position);
            }

            @Override
            public int getCount() {
                return mFregements.size();
            }
        };

        mViewPager.setAdapter(mAdapter);

    }

    private void ininEvents() {
        song.setOnClickListener(this);
        singer.setOnClickListener(this);
        album.setOnClickListener(this);
        songlist.setOnClickListener(this);
        mv.setOnClickListener(this);
        radio.setOnClickListener(this);
        user.setOnClickListener(this);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tabIndicator.getLayoutParams();
                tabIndicator.setLayoutParams(layoutParams);
                int margin = 0;
                for (int i = 0; i < position; i++) {
                    margin += mTextWidth.get(i);
                }
                if (pageIndex > position) {
                    margin += (positionOffset) * mTextWidth.get(position);
                } else if (pageIndex < position) {
                    margin += -(positionOffset) * mTextWidth.get(position);
                } else if (pageIndex == position) {
                    margin += (positionOffset) * mTextWidth.get(position);
                }
                if (position > 1) {
                    horizontalScrollView.scrollTo(margin - mTextWidth.get(0) - mTextWidth.get(1), 0);
                }
                layoutParams.width = mTextWidth.get(pageIndex);
                layoutParams.leftMargin = margin;
                tabIndicator.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageSelected(int position) {
                pageIndex = position;
                setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
    }

    //大坑
    private void initViewWidth() {
        for (int i = 0; i < mTextViews.size(); i++) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mTextViews.get(i).getLayoutParams();
            int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            mTextViews.get(i).measure(width, height);
            width = mTextViews.get(i).getMeasuredWidth();
            mTextWidth.add(width);
        }
    }

    private void initIndicator(int position) {

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mTextViews.get(position).getLayoutParams();
        LinearLayout.LayoutParams indicatorLayoutParams = (LinearLayout.LayoutParams) tabIndicator.getLayoutParams();
        indicatorLayoutParams.width = layoutParams.width;
    }

    private void ininTextColor() {
        song.setTextColor(0xff000000);
        singer.setTextColor(0xff000000);
        album.setTextColor(0xff000000);
        songlist.setTextColor(0xff000000);
        mv.setTextColor(0xff000000);
        radio.setTextColor(0xff000000);
        user.setTextColor(0xff000000);
    }

    private void setCurrentItem(int position) {
        pageIndex = position;
        ininTextColor();
        switch (position) {
            case 0:
                song.setTextColor(Color.parseColor("#cc3300"));
                break;
            case 1:
                singer.setTextColor(Color.parseColor("#cc3300"));
                break;
            case 2:
                album.setTextColor(Color.parseColor("#cc3300"));
                break;
            case 3:
                songlist.setTextColor(Color.parseColor("#cc3300"));
                break;
            case 4:
                mv.setTextColor(Color.parseColor("#cc3300"));
                break;
            case 5:
                radio.setTextColor(Color.parseColor("#cc3300"));
                break;
            case 6:
                user.setTextColor(Color.parseColor("#cc3300"));
                break;
            default:
                break;
        }
        initIndicator(position);
        mViewPager.setCurrentItem(position);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_search_words_edit_response_tab_song:
                setCurrentItem(0);
                break;
            case R.id.id_search_words_edit_response_tab_singer:
                setCurrentItem(1);
                break;
            case R.id.id_search_words_edit_response_tab_album:
                setCurrentItem(2);
                break;
            case R.id.id_search_words_edit_response_tab_songlist:
                setCurrentItem(3);
                break;
            case R.id.id_search_words_edit_response_tab_mv:
                setCurrentItem(4);
                break;
            case R.id.id_search_words_edit_response_tab_radio:
                setCurrentItem(5);
                break;
            case R.id.id_search_words_edit_response_tab_user:
                setCurrentItem(6);
                break;
            default:
                break;
        }
    }
}
