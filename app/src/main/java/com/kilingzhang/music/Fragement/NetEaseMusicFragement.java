package com.kilingzhang.music.Fragement;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kilingzhang.music.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slight on 2017/4/6.
 */

public class NetEaseMusicFragement extends Fragment implements View.OnClickListener {

    /**
     * Fragement
     */
    private Fragment recommenFragement;
    private Fragment radioFragement;
    private Fragment ranklistFragement;
    private Fragment songlistFragement;

    /**
     * ViewContorl
     */
    private TextView recommenTab;
    private TextView songlistTab;
    private TextView radioTab;
    private TextView ranklistTab;
    private View view;
    private ImageView tabIndicator;


    /**
     * ViewPager
     */
    private ViewPager mViewPager;

    /**
     * Adapter
     */

    private FragmentPagerAdapter mAdapter;


    /**
     * DataList
     */

    private List<Fragment> fragements = new ArrayList<Fragment>();

    /**
     *
     */
    private int mScreenTab;
    private int mCurrentPage = 0;
    private static final String TAG = "NetEaseMusicFragement";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews(inflater, container);
        initScreenTabLine();
        initEvents();
        return view;
    }

    private void initScreenTabLine() {
        DisplayMetrics outMetrics = new DisplayMetrics();
        com.kilingzhang.music.Activity.MainActivity.display.getMetrics(outMetrics);
        mScreenTab = outMetrics.widthPixels / fragements.size();
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tabIndicator.getLayoutParams();
        layoutParams.width = mScreenTab;
        tabIndicator.setLayoutParams(layoutParams);
    }


    private void initViews(LayoutInflater inflater, @Nullable ViewGroup container) {
        view = inflater.inflate(R.layout.net_ease_music_fragement, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.netEaseMusic_viewpager);

        recommenTab = (TextView) view.findViewById(R.id.netEaseMusic_tab_recommen);
        songlistTab = (TextView) view.findViewById(R.id.netEaseMusic_tab_songlist);
        radioTab = (TextView) view.findViewById(R.id.netEaseMusic_tab_radio);
        ranklistTab = (TextView) view.findViewById(R.id.netEaseMusic_tab_ranklist);
        tabIndicator = (ImageView) view.findViewById(R.id.tab_indicator);

        recommenFragement = new RecommenFragement();
        songlistFragement = new SonglistFragement();
        radioFragement = new RadioFragement();
        ranklistFragement = new RanklistFragement();

        fragements.add(recommenFragement);
        fragements.add(songlistFragement);
        fragements.add(radioFragement);
        fragements.add(ranklistFragement);

        mAdapter = new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragements.get(position);
            }

            @Override
            public int getCount() {
                return fragements.size();
            }
        };

        mViewPager.setAdapter(mAdapter);

    }


    private void initEvents() {
        recommenTab.setOnClickListener(this);
        songlistTab.setOnClickListener(this);
        radioTab.setOnClickListener(this);
        ranklistTab.setOnClickListener(this);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tabIndicator.getLayoutParams();
                if (mCurrentPage == 0 && position == 0) {
                    layoutParams.leftMargin = (int) (positionOffset * mScreenTab + mCurrentPage * mScreenTab);
                } else if (mCurrentPage == 1 && position == 0) {
                    layoutParams.leftMargin = (int) ((positionOffset - 1) * mScreenTab + mCurrentPage * mScreenTab);
                } else if (mCurrentPage == 1 && position == 1) {
                    layoutParams.leftMargin = (int) (positionOffset * mScreenTab + mCurrentPage * mScreenTab);
                } else if (mCurrentPage == 1 && position == 2) {
                    layoutParams.leftMargin = (int) (positionOffset * mScreenTab + mCurrentPage * mScreenTab);
                } else if (mCurrentPage == 2 && position == 1) {
                    layoutParams.leftMargin = (int) ((positionOffset - 1) * mScreenTab + mCurrentPage * mScreenTab);
                } else if (mCurrentPage == 2 && position == 2) {
                    layoutParams.leftMargin = (int) (positionOffset * mScreenTab + mCurrentPage * mScreenTab);
                } else if (mCurrentPage == 2 && position == 3) {
                    layoutParams.leftMargin = (int) (positionOffset * mScreenTab + mCurrentPage * mScreenTab);
                } else if (mCurrentPage == 3 && position == 2) {
                    layoutParams.leftMargin = (int) ((positionOffset - 1) * mScreenTab + mCurrentPage * mScreenTab);
                } else if (mCurrentPage == 3 && position == 3) {
                    layoutParams.leftMargin = (int) (positionOffset * mScreenTab + mCurrentPage * mScreenTab);
                }
                tabIndicator.setLayoutParams(layoutParams);

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                initTitleColor();
                selectItemTitleColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "onPageScrollStateChanged: "+state);
            }
        });
    }

    private void initTitleColor() {
        recommenTab.setTextColor(Color.parseColor("#666666"));
        songlistTab.setTextColor(Color.parseColor("#666666"));
        radioTab.setTextColor(Color.parseColor("#666666"));
        ranklistTab.setTextColor(Color.parseColor("#666666"));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.netEaseMusic_tab_recommen:
                initTitleColor();
                selectItemTitleColor(0);
                mViewPager.setCurrentItem(0);
                break;
            case R.id.netEaseMusic_tab_songlist:
                initTitleColor();
                selectItemTitleColor(1);
                mViewPager.setCurrentItem(1);
                break;
            case R.id.netEaseMusic_tab_radio:
                initTitleColor();
                selectItemTitleColor(2);
                mViewPager.setCurrentItem(2);
                break;
            case R.id.netEaseMusic_tab_ranklist:
                initTitleColor();
                selectItemTitleColor(3);
                mViewPager.setCurrentItem(3);
                break;
            default:
                break;
        }
    }

    private void selectItemTitleColor(int position) {
        switch (position) {
            case 0:
                recommenTab.setTextColor(Color.parseColor("#CC3333"));
                break;
            case 1:
                songlistTab.setTextColor(Color.parseColor("#CC3333"));
                break;
            case 2:
                radioTab.setTextColor(Color.parseColor("#CC3333"));
                break;
            case 3:
                ranklistTab.setTextColor(Color.parseColor("#CC3333"));
                break;
            default:
                break;
        }
    }
}
