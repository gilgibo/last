package com.example.gibo.termproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gi bo on 2017-06-15.
 */

public class DataAdapter extends BaseAdapter {

    ArrayList<StoryData> data;
    Context context;

    public DataAdapter(Context context,ArrayList<StoryData> data){
        this.context = context;
        this.data = data;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.customlistview, null);
        }

        TextView tv1= (TextView)convertView.findViewById(R.id.tv1);
        TextView tv2= (TextView)convertView.findViewById(R.id.tv2);
        ImageView img = (ImageView)convertView.findViewById(R.id.img);

        StoryData info = data.get(position);

        tv1.setText(info.GetToday() + " 소요 시간 : "+ info.GetTime() + " 초");
        tv2.setText(info.GetPage()+1 + " 페이지");
        img.setImageResource(info.rawimg[info.GetPage()]);

        return convertView;
    }
}
