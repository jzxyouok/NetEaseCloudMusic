package com.kilingzhang.music.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.kilingzhang.music.Fragement.GroupFragement;
import com.kilingzhang.music.Fragement.MymusicFragement;
import com.kilingzhang.music.Fragement.NetEaseMusicFragement;
import com.kilingzhang.music.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    /**
     * Fragement
     */
    private Fragment myMusicFragement;
    private Fragment netEaseMusicFragement;
    private Fragment groupFragement;

    /**
     * ViewContorl
     */
    private ImageView myMusicIcon;
    private ImageView netEaseMusicIcon;
    private ImageView groupIcon;
    public static Display display;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;

    /**
     * ViewPager
     */
    private ViewPager fragementViewPager;

    /**
     * Adapter
     */

    private FragmentPagerAdapter mAdapter;


    /**
     * DataList
     */

    private List<Fragment> fragements = new ArrayList<Fragment>();


    private static int MusicSource = 0;

    /**
     *
     */
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = getWindow().getWindowManager().getDefaultDisplay();


        initViews();
        initEvents();
        fragementViewPager.setCurrentItem(1);
    }


    /**
     * 初始化Views
     */
    private void initViews() {

        initToolBar();

        fragementViewPager = (ViewPager) findViewById(R.id.id_top_veiwPager);

        myMusicIcon = (ImageView) findViewById(R.id.id_top_linearLayout_music_icon);
        netEaseMusicIcon = (ImageView) findViewById(R.id.id_top_linearLayout_NetEasemusic_icon);
        groupIcon = (ImageView) findViewById(R.id.id_top_linearLayout_group_icon);

        myMusicFragement = new MymusicFragement();
        netEaseMusicFragement = new NetEaseMusicFragement();
        groupFragement = new GroupFragement();

        fragements.add(myMusicFragement);
        fragements.add(netEaseMusicFragement);
        fragements.add(groupFragement);

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragements.get(position);
            }

            @Override
            public int getCount() {
                return fragements.size();
            }
        };

        fragementViewPager.setAdapter(mAdapter);

    }

    private void initToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }


    /**
     * 初始化View事件
     */
    private void initEvents() {
        myMusicIcon.setOnClickListener(this);
        netEaseMusicIcon.setOnClickListener(this);
        groupIcon.setOnClickListener(this);
        fragementViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                initTopIcon();
                switch (position){
                    case 0:
                        myMusicIcon.setImageResource(R.drawable.ic_music_press);
                        break;
                    case 1:
                        netEaseMusicIcon.setImageResource(R.drawable.ic_net_ease_music_press);
                        break;
                    case 2:
                        groupIcon.setImageResource(R.drawable.ic_group_press);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "onPageScrollStateChanged: "+state);
            }
        });
    }

    public void initTopIcon(){
        myMusicIcon.setImageResource(R.drawable.ic_music_default);
        netEaseMusicIcon.setImageResource(R.drawable.ic_net_ease_music_default);
        groupIcon.setImageResource(R.drawable.ic_group_default);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_top_linearLayout_music_icon:
                initTopIcon();
                myMusicIcon.setImageResource(R.drawable.ic_music_press);
                fragementViewPager.setCurrentItem(0);
                break;
            case R.id.id_top_linearLayout_NetEasemusic_icon:
                initTopIcon();
                netEaseMusicIcon.setImageResource(R.drawable.ic_net_ease_music_press);
                fragementViewPager.setCurrentItem(1);
                break;
            case R.id.id_top_linearLayout_group_icon:
                initTopIcon();
                groupIcon.setImageResource(R.drawable.ic_group_press);
                fragementViewPager.setCurrentItem(2);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                Intent intent = new Intent(MainActivity.this,MusicSearchWordsActivity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    /**
     * 双击返回桌面
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - time > 1000)) {
                Toast.makeText(this, "再按一次返回桌面", Toast.LENGTH_SHORT).show();
                time = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }
}
