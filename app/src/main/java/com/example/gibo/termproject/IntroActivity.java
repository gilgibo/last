package com.example.gibo.termproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IntroActivity extends AppCompatActivity {

    ImageView img;
    TextView tv;
    Button btnnext, btnprev;
    int[] rawimg = {R.drawable.intro1,R.drawable.intro2,R.drawable.intro3};
    String[] text = new String[4];
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        img = (ImageView)findViewById(R.id.img);
        tv = (TextView)findViewById(R.id.tv1);
        btnnext = (Button)findViewById(R.id.next);
        btnprev = (Button)findViewById(R.id.prev);
        getdatafromfile();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0,"처음으로");
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v){
        if(v.getId() == R.id.next){
            if( index == 0 ) {
                index++;
                img.setImageResource(rawimg[index]);
                tv.setText(text[index+1]);
                btnprev.setEnabled(true);
            }else if(index == 1) {
                index++;
                img.setImageResource(rawimg[index]);
                tv.setText(text[index+1]);
                btnnext.setEnabled(false);

            }
        }if(v.getId() == R.id.prev){
                if( index == 1 ) {
                    index--;
                    img.setImageResource(rawimg[index]);
                    tv.setText(text[index+1]);
                    btnprev.setEnabled(false);
                }else if(index == 2) {
                    index--;
                    img.setImageResource(rawimg[index]);
                    tv.setText(text[index+1]);
                    btnnext.setEnabled(true);
                }
        }
    }

    public void getdatafromfile(){
        int pos = 1;
        try{
            InputStream in = getResources().openRawResource(R.raw.introtext);
            if(in != null){
                InputStreamReader stream = new InputStreamReader(in, "utf-8");
                BufferedReader buffer = new BufferedReader(stream);

                String read;
                StringBuilder sb = new StringBuilder("");

                while((read=buffer.readLine())!=null){
                    if(read.equals(pos+"")){
                        text[pos-1] = sb.toString();
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


}
