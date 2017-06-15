package com.example.gibo.termproject;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class ResumeActivity extends AppCompatActivity {

    ListView liststory;
    StoryData getdata;
    ArrayList<StoryData> stdata = new ArrayList<StoryData>();
    DataAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        Intent intent = getIntent();
        int num = intent.getExtras().getInt("num");


        for(int i = 0 ; i < num ; i ++) {
            getdata = intent.getParcelableExtra(i + 1 + "");
            stdata.add(getdata);
        }
        setlistview();
    }


    public void setlistview(){
        liststory = (ListView)findViewById(R.id.liststory);
        adapter = new DataAdapter(this, stdata);
        liststory.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        liststory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ResumeActivity.this, Story1Activity.class);
                intent.putExtra("resume", stdata.get(position));
                startActivityForResult(intent, 20);
            }
        });

        liststory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(ResumeActivity.this);
                dlg.setTitle("").setTitle("삭제").setMessage("삭제 하시겠습니까?").setPositiveButton("닫기",null)
                        .setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        stdata.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                }).show();
                return false;
            }
        });
    }

    public void onClick(View v){
        int num = stdata.size();
        Intent intent = new Intent(ResumeActivity.this, Main2Activity.class);
        intent.putExtra("resize", stdata.size());
        for(int i = 0 ; i < num ; i ++) {
            intent.putExtra(i+"", stdata.get(i));
        }
        startActivity(intent);
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 20){
            if(resultCode == RESULT_OK){
                getdata = data.getParcelableExtra("reinfo");
                stdata.add(getdata);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
