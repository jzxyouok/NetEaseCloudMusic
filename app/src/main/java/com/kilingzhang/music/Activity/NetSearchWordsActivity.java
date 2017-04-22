package com.kilingzhang.music.Activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.kilingzhang.music.R;
import com.kilingzhang.music.View.FlowLayout;

public class NetSearchWordsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String[] tags = new String[]{
            "实名制", "Something Just Like This","Faded","动物世界","进击的巨人",
            "一生所爱", "薛之谦", "寂寞寂寞就好", "许嵩", "陈奕迅"
    };
    private FlowLayout mFlowLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_search_words);
        initViews();
    }

    private void initViews() {
        initToolBar();
        mFlowLayout = (FlowLayout) findViewById(R.id.flow_layout);
        initFlowLayout();
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
    }

    private void initFlowLayout() {
        LayoutInflater mInflater = LayoutInflater.from(this);
        for (int i = 0; i < tags.length; i++) {
            Button view = (Button) mInflater.inflate(R.layout.flow_view_item, mFlowLayout, false);
            view.setText(tags[i]);
            mFlowLayout.addView(view);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
