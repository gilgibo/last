package com.example.gibo.termproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    final static int STORYBOARDDATA = 10;
    ArrayList<StoryData> savedata = new ArrayList<StoryData>();
    ArrayList<Integer> numdata = new ArrayList<Integer>();
    StoryData getdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }




    public void onClick(View v){
        if(v.getId() == R.id.btnstart) {
            Intent intent = new Intent(MainActivity.this, Story1Activity.class);
            startActivityForResult(intent, STORYBOARDDATA);
        }
        if(v.getId() == R.id.btnrestart){
            Intent intent = new Intent(MainActivity.this , ResumeActivity.class);
            intent.putExtra("num",savedata.size());
            for(int i = 0 ; i < savedata.size() ; i ++) {
                intent.putExtra(numdata.get(i)+"", savedata.get(i));
            }
            startActivity(intent);
            finish();
        }
        if(v.getId() == R.id.btnintro){
            Intent intent = new Intent(MainActivity.this, IntroActivity.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.btnhomepage){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ollybolly.org/"));
            startActivity(intent);
        }
        if(v.getId() == R.id.btnexit) {
           finish();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == STORYBOARDDATA){
            if(resultCode == RESULT_OK){
                getdata = data.getParcelableExtra("info");
                savedata.add(getdata);
                numdata.add(savedata.size());
            }
        }
    }
}
