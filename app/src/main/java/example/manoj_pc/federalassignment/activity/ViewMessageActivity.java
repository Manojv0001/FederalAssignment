package example.manoj_pc.federalassignment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;

import example.manoj_pc.federalassignment.R;
import example.manoj_pc.federalassignment.adapter.MsgListAdapter;
import example.manoj_pc.federalassignment.app.Config;
import example.manoj_pc.federalassignment.util.SharedPreference;

/**
 * Created by MANOJ-PC on 4/3/2018.
 */

public class ViewMessageActivity extends AppCompatActivity {
    private RecyclerView rvList;
    private SharedPreference sharedPreference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);
        sharedPreference = new SharedPreference();
        initView();
        initData();
    }

    private void initData() {
        ArrayList<String> msgList = (ArrayList<String>) getIntent().getSerializableExtra(Config.MSGLIST);
        ArrayList<String> allMsgList = sharedPreference.getFavorites(ViewMessageActivity.this);
        if(allMsgList!=null&&allMsgList.size()>0){
            MsgListAdapter msgListAdapter = new MsgListAdapter(allMsgList);
            rvList.setAdapter(msgListAdapter);
        }

    }

    private void initView() {
        rvList = (RecyclerView)findViewById(R.id.rvList);
        rvList.setLayoutManager(new LinearLayoutManager(ViewMessageActivity.this));
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ViewMessageActivity.this,MainActivity.class);
        intent.putExtra(Config.STATUS,1);
        startActivity(intent);
    }
}
