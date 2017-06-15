package com.example.gibo.termproject;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gi bo on 2017-06-15.
 */

public class StoryData implements Parcelable{

    int time = 0 ;
    int page = 0 ;
    int size = 0 ;
    int[] rawimg = {R.drawable.story1,R.drawable.story2,R.drawable.story3,
            R.drawable.story4,R.drawable.story5,R.drawable.story6,
            R.drawable.story7,R.drawable.story8};

    StoryData(){}

    protected StoryData(Parcel in) {
        time = in.readInt();
        page = in.readInt();
        size = in.readInt();
        rawimg = in.createIntArray();
    }

    public static final Creator<StoryData> CREATOR = new Creator<StoryData>() {
        @Override
        public StoryData createFromParcel(Parcel in) {
            return new StoryData(in);
        }

        @Override
        public StoryData[] newArray(int size) {
            return new StoryData[size];
        }
    };

    public int GetTime(){
        return this.time;
    }

    public int GetPage(){
        return this.page;
    }

    public int GetSize() { return this.size;}

    public String GetToday(){
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분");

        Date date = new Date();
        String today = dateformat.format(date);

        return today;
    }

    public void increaseSize(){
        size = size+1;
    }

    public void decreaseSize(){
        size = size-1;
    }

    public void SetTime(int time){
        this.time = time;
    }

    public void SetPage(int page){
        this.page = page;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(time);
        dest.writeInt(page);
        dest.writeInt(size);
        dest.writeIntArray(rawimg);
    }
}
