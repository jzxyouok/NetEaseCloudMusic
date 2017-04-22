package com.kilingzhang.music.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kilingzhang.music.R;
import com.kilingzhang.music.View.FlowLayout;
import com.kilingzhang.music.View.RippleButton;
import com.kilingzhang.music.View.RippleTextView;

import static com.kilingzhang.music.Config.APP_Token;

public class MusicSearchWordsActivity extends AppCompatActivity {

    private static final String TAG = "MusicSearchWordsActivit";
    private Toolbar toolbar;
    private String[] tags = new String[]{
            "实名制", "Something Just Like This","Faded","动物世界","进击的巨人",
            "一生所爱", "薛之谦", "寂寞寂寞就好", "许嵩", "陈奕迅"
    };
    private FlowLayout mFlowLayout;

    private EditText searchWordsEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_search_words);
        initViews();
        ininEvents();
    }

    private void ininEvents() {
        searchWordsEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    Intent intent = new Intent(MusicSearchWordsActivity.this,MusicSearchResponseActivity.class);
                    intent.putExtra(APP_Token+"searchWords",searchWordsEdit.getText().toString().trim());
                    startActivity(intent);
                }
                return false;
            }
        });
    }

    private void initViews() {
        initToolBar();
        mFlowLayout = (FlowLayout) findViewById(R.id.flow_layout);
        initFlowLayout();
        searchWordsEdit = (EditText) findViewById(R.id.id_search_words_edit);


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
        for (int i = 0; i <tags.length; i++) {
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
