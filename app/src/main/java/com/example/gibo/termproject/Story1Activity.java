package com.example.gibo.termproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Story1Activity extends AppCompatActivity {

    StoryData storydata ,startdata;
    final String question[] = {"만족","보통","별로"};
    int time;
    MThread th = new MThread();
    ImageView background;
    Button btnnext, btnprev;
    TextView story;
    int[] rawimg = {R.drawable.story1,R.drawable.story2,R.drawable.story3,
            R.drawable.story4,R.drawable.story5,R.drawable.story6,
            R.drawable.story7,R.drawable.story8};
    String[] storytext = new String[9];
    int indeximg ;
    DB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story1);

        db = new DB(this);
        Intent intent = getIntent();
        startdata = intent.getParcelableExtra("resume");
        if(startdata == null){
            time = 0;
            indeximg = 0;
        }else{
            time = startdata.GetTime();
            indeximg = startdata.GetPage();
        }
        background = (ImageView) findViewById(R.id.background);
        story = (TextView)findViewById(R.id.story);
        btnnext = (Button)findViewById(R.id.btnnext);
        btnprev = (Button)findViewById(R.id.btnprev);
        getdatafromfile();
        th.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0,"처음으로");
        menu.add(0,2,0,"저장하기");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == 1){
            finish();
        }
        if(item.getItemId() == 2){
            storydata = new StoryData();
            storydata.SetTime(time);
            storydata.SetPage(indeximg);

            th.interrupt();
            Intent intent = new Intent();
            intent.putExtra("reinfo",storydata);
            intent.putExtra("info",storydata);
            setResult(RESULT_OK, intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public void onClick(View v){
        if(v.getId() == R.id.btnnext){
            if(0 < indeximg && indeximg < 6) {
                indeximg++;
                background.setImageResource(rawimg[indeximg]);
                story.setText(storytext[indeximg+1]+"");
            }else if(indeximg == 6) {
                indeximg++;
                background.setImageResource(rawimg[indeximg]);
                story.setText(storytext[indeximg + 1] + "");
            }else if( indeximg == 7){
                AlertDialog.Builder dlg = new AlertDialog.Builder(Story1Activity.this);
                dlg.setTitle("만족도 조사").setSingleChoiceItems(question, 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.open();
                        db.insert(GetToday(),question[which]);
                        db.close();
                    }
                }).setPositiveButton("닫기",null)
                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();    }
                        }).show();
            }else if(indeximg == 0){
                indeximg++;
                background.setImageResource(rawimg[indeximg]);
                story.setText(storytext[indeximg+1]+"");
                btnprev.setEnabled(true);
            }
        }
        if(v.getId() == R.id.btnprev){
            if(1 < indeximg && indeximg < 7) {
                indeximg--;
                background.setImageResource(rawimg[indeximg]);
                story.setText(storytext[indeximg+1]+"");
            }else if(indeximg == 7){
                indeximg--;
                background.setImageResource(rawimg[indeximg]);
                story.setText(storytext[indeximg+1]+"");
                btnnext.setEnabled(true);
            }else if (indeximg == 1){
                indeximg--;
                background.setImageResource(rawimg[indeximg]);
                story.setText(storytext[indeximg+1]+"");
                btnprev.setEnabled(false);
            }
        }
    }

    class MThread extends Thread {
        public void run(){
            super.run();
            for(int i = 0 ; ; i ++){
                try {
                    Thread.sleep(1000);
                    time++;
                    Message msg = handler.obtainMessage();
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }        }
    }

    public void getdatafromfile(){
        int pos = 1;
        try{
            InputStream in = getResources().openRawResource(R.raw.storytext);
            if(in != null){
                InputStreamReader stream = new InputStreamReader(in, "utf-8");
                BufferedReader buffer = new BufferedReader(stream);

                String read;
                StringBuilder sb = new StringBuilder("");

                while((read=buffer.readLine())!=null){
                    if(read.equals(pos+"")){
                        storytext[pos-1] = sb.toString();
                        pos++;
                        sb = new StringBuilder("");
                    }else if(!read.equals(pos+"") || !sb.toString().equals(""))
                        sb.append(read+"\n");
                }
                in.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String GetToday(){
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분");

        Date date = new Date();
        String today = dateformat.format(date);

        return today;
    }

}
