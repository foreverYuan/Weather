package com.example.cyy.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.cyy.weather.entity.obj.FutureWeatherObj;
import com.example.cyy.weather.widget.FutureWeatherTrendView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by cyy on 2017/1/11.
 */
public class FutureWeatherActivity extends AppCompatActivity{
    private TextView mTvPeriod;//时间段
    private HorizontalScrollView  mHsTrend;//横向的HorizontalScrollView
    private ArrayList<FutureWeatherObj> weatherList;//上个页面传过来的天气信息
    private Button mBtnClose;//关闭按钮

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_weather);
        initView();
        //得到上一个页面传递过来的数据
        getIntentData();
        //设置数据
        if(weatherList != null && weatherList.size() > 0) {
            setData();
        }
    }

    private void initView() {
        mTvPeriod = (TextView) findViewById(R.id.tv_activity_future_weather_period);
        mHsTrend = (HorizontalScrollView) findViewById(R.id.hs_activity_future_weather);
        mBtnClose = (Button) findViewById(R.id.btn_activity_future_weather_close);
        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FutureWeatherActivity.this.finish();
            }
        });
    }

    private void getIntentData(){
        Intent intent = getIntent();
        weatherList = intent.getParcelableArrayListExtra("futureWeatherList");
    }

    private void setData() {
        mTvPeriod.setText(weatherList.get(0).days + "---" + weatherList.get(weatherList.size() - 1).days);
        View itemView = null;
        FutureWeatherObj lastWeatherObj = null;
        FutureWeatherObj nextWeatherObj = null;
        int lowestTem = 0, highestTem = 0;
        LinearLayout ll = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ll.setLayoutParams(params);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.removeAllViews();
        ArrayList<Integer> lowTemList = new ArrayList<Integer>();//存放低温温度值的集合
        ArrayList<Integer> highTemList = new ArrayList<Integer>();//存放高温温度值的集合
        for (int i = 0; i < weatherList.size(); i++){
            FutureWeatherObj weatherObj = weatherList.get(i);
            lowTemList.add(Integer.parseInt(weatherObj.temp_low));
            highTemList.add(Integer.parseInt(weatherObj.temp_high));
            Collections.sort(lowTemList);//按照升序排列所有低温温度值
            Collections.sort(highTemList);//按照升序排列所有高温温度值
        }
        FutureWeatherTrendView.setLowestTem(lowTemList.get(0));
        FutureWeatherTrendView.setHighestTem(highTemList.get(weatherList.size() - 1));
        for(int i = 0; i < weatherList.size(); i++){
            float lowTemArray[] = new float[3];
            float highTemArray[] = new float[3];
            float lastNextTem[] = new float[4];
            FutureWeatherObj weatherObj = weatherList.get(i);
            if(i == 0){ //没有前一天只有后一天
                lastWeatherObj = null;
                nextWeatherObj = weatherList.get(i + 1);
            } else if(i > 0 && i < weatherList.size() - 1){ //有前一天和后一天
                lastWeatherObj = weatherList.get(i - 1);
                nextWeatherObj = weatherList.get(i + 1);
            } else if(i == weatherList.size() - 1){ //只有前一天没有后一天
                lastWeatherObj = weatherList.get(i - 1);
                nextWeatherObj = null;
            }
            if(lastWeatherObj != null) {
                lastNextTem[0] = Integer.parseInt(lastWeatherObj.temp_low);
                lastNextTem[1] = Integer.parseInt(lastWeatherObj.temp_high);
            }
            if(weatherObj != null){
                lowTemArray[1] = Integer.parseInt(weatherObj.temp_low);
                highTemArray[1] = Integer.parseInt(weatherObj.temp_high);
            }
            if(nextWeatherObj != null) {
                lastNextTem[2] = Integer.parseInt(nextWeatherObj.temp_low);
                lastNextTem[3] = Integer.parseInt(nextWeatherObj.temp_high);
            }
            if(i == 0){
                lowTemArray[2] = (lowTemArray[1] + lastNextTem[2]) / 2;
                highTemArray[2] = (highTemArray[1] + lastNextTem[3]) / 2;
            }else if(i > 0 && i < weatherList.size() - 1){
                lowTemArray[0] = (lastNextTem[0] + lowTemArray[1]) / 2;
                highTemArray[0] = (lastNextTem[1] + highTemArray[1]) / 2;
                lowTemArray[2] = (lowTemArray[1] + lastNextTem[2]) / 2;
                highTemArray[2] = (highTemArray[1] + lastNextTem[3]) / 2;
            }else if(i == weatherList.size() - 1){
                lowTemArray[0] = (lastNextTem[0] + lowTemArray[1]) / 2;
                highTemArray[0] = (lastNextTem[1] + highTemArray[1]) / 2;
            }
            itemView = LayoutInflater.from(this).inflate(R.layout.activity_future_weather_horizontal_item, null);
            TextView mTvDate = (TextView) itemView.findViewById(R.id.tv_horizontal_item_date);
            ImageView mIvDayPic = (ImageView) itemView.findViewById(R.id.iv_horizontal_item_day_pic);
            TextView mTvDayWeather = (TextView) itemView.findViewById(R.id.tv_horizontal_item_day_weather);
            FutureWeatherTrendView mViewTrend = (FutureWeatherTrendView) itemView.findViewById(R.id.view_horizontal_item_trend);
            ImageView mIvNightPic = (ImageView) itemView.findViewById(R.id.iv_horizontal_item_night_pic);
            TextView mTvNightWeather = (TextView) itemView.findViewById(R.id.tv_horizontal_item_night_weather);
            TextView mTvWind = (TextView) itemView.findViewById(R.id.tv_horizontal_item_wind);
            mTvDate.setText(weatherObj.week + "\n" + weatherObj.days);
            Picasso.with(this).load(weatherObj.weather_icon).into(mIvDayPic);
            Picasso.with(this).load(weatherObj.weather_icon1).into(mIvNightPic);
            if(!weatherObj.weather.contains("转")){
                mTvDayWeather.setText(weatherObj.weather);
                mTvNightWeather.setText(weatherObj.weather);
            }else{
                String weather[] = weatherObj.weather.split("转");
                mTvDayWeather.setText(weather[0]);
                mTvNightWeather.setText(weather[1]);
            }
            mTvWind.setText(weatherObj.wind + "\n" + weatherObj.winp);
            mViewTrend.setLowTemArray(lowTemArray)
                    .setHighTemArray(highTemArray)
                    .setType(i == 0 ? 0 : i == weatherList.size() - 1 ? 1 : 2);
            ll.addView(itemView);
        }
        mHsTrend.addView(ll);
    }
}
