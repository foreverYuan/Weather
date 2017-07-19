package com.example.cyy.weather.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.cyy.weather.CityListActivity;
import com.example.cyy.weather.FutureWeatherActivity;
import com.example.cyy.weather.R;
import com.example.cyy.weather.adapter.FutureWeatherAdapter;
import com.example.cyy.weather.entity.resbody.FutureWeatherRes;
import com.example.cyy.weather.entity.resbody.TodayWeatherRes;
import com.example.cyy.weather.entity.resbody.WeatherAirRes;
import com.example.cyy.weather.utils.ConstantUtils;
import com.example.cyy.weather.utils.FastJsonUtils;
import com.example.cyy.weather.utils.OkhttpUtils;
import com.example.cyy.weather.utils.Tools;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cyy on 2016/10/9.
 */
public class WeatherFragment extends Fragment implements View.OnClickListener{
    private TextView tv_date, tv_week, tv_temperature_curr, tv_city_name_weather, tv_weather_wind, tv_weather_air, tv_weather_hum;
    private Button btn_city_change;
    private View view;
    private ListView lv_weather_future_list;
    private FutureWeatherRes resbody;
    private WeatherAirRes airRes;
    private int tagI = 0;
    private Handler handler;
    private LinearLayout ll_load_animation;
    private RelativeLayout rl_weather_info;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weather, null);
        initView();
        requestTodayWeather();
        load();
        return view;
    }

    private void initView() {
        tv_date = (TextView) view.findViewById(R.id.tv_date);
        tv_week = (TextView) view.findViewById(R.id.tv_week);
        tv_temperature_curr = (TextView) view.findViewById(R.id.tv_temperature_curr);
        tv_city_name_weather = (TextView) view.findViewById(R.id.tv_city_name_weather);
        btn_city_change = (Button) view.findViewById(R.id.btn_city_change);
        btn_city_change.setOnClickListener(this);
        tv_weather_wind = (TextView) view.findViewById(R.id.tv_weather_wind);
        tv_weather_air = (TextView) view.findViewById(R.id.tv_weather_air);
        lv_weather_future_list = (ListView) view.findViewById(R.id.lv_weather_future_list);
        tv_weather_hum = (TextView) view.findViewById(R.id.tv_weather_hum);
        ll_load_animation = (LinearLayout) view.findViewById(R.id.ll_load_animation);
        rl_weather_info = (RelativeLayout) view.findViewById(R.id.rl_weather_info);
    }

    private void setViewData(TodayWeatherRes resbody){
        if(resbody != null){
            //请求未来几天的天气预报
            requestFutureWeather();
            tv_date.setText(resbody.result.days);
            tv_week.setText(resbody.result.week);
            tv_temperature_curr.setText(resbody.result.temperature_curr);
            tv_city_name_weather.setText(resbody.result.citynm + "|" + resbody.result.weather);
            tv_weather_wind.setText(resbody.result.wind + "\n" + resbody.result.winp);
            tv_weather_hum.setText("相对湿度\n" + resbody.result.humidity);
        }
    }

    private void setFutureWeatherData(final FutureWeatherRes resbody){
        if(resbody != null && resbody.result != null) {
            requestAirQuality();
            //加载完数据后隐藏加载动画
            ll_load_animation.setVisibility(View.GONE);
            //显示出天气预报的信息
            rl_weather_info.setVisibility(View.VISIBLE);
            lv_weather_future_list.setVisibility(View.VISIBLE);
            FutureWeatherAdapter adapter = new FutureWeatherAdapter(getActivity(), resbody.result, R.layout.fragment_future_weather_lv_item);
            lv_weather_future_list.setAdapter(adapter);
            if (resbody.result.size() > 3) {
                TextView tv_more = new TextView(getActivity());
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tv_more.setPadding(0, Tools.dip2px(getActivity(), 16), 0, Tools.dip2px(getActivity(), 16));
                tv_more.setGravity(Gravity.CENTER);
                tv_more.setLayoutParams(params);
                tv_more.setText("查看更多天气");
                tv_more.setTextColor(Color.parseColor("#aaaaaa"));
                lv_weather_future_list.addFooterView(tv_more);
            }
            lv_weather_future_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //跳转至查看未来几天天气的页面
                    Intent intent = new Intent(getActivity(), FutureWeatherActivity.class);
                    intent.putParcelableArrayListExtra("futureWeatherList", resbody.result);
                    startActivity(intent);
                }
            });
        }
    }

    public void setWeatherAirData(WeatherAirRes airRes){
        if(airRes != null && airRes.result != null){
            tv_weather_air.setText(airRes.result.aqi_levnm + "\n" + airRes.result.aqi);
        }
    }

    /**
     * 请求实时天气预报返回数据
     */
    private void requestTodayWeather(){
        OkhttpUtils utils = new OkhttpUtils();
        utils.setOnResponseListener(new OkhttpUtils.ResponseListener() {
            @Override
            public void onSuccessAction(final Response response) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            setViewData((TodayWeatherRes) FastJsonUtils.analysisObject(response.body().string(), TodayWeatherRes.class));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("app", ConstantUtils.CITY_WEATHER_NOW_APP)
                .add("weaid", "1")     //需要改
                .add("appkey", ConstantUtils.WEATHER_APPKEY)
                .add("sign", ConstantUtils.WEATHER_SIGN)
                .add("format", ConstantUtils.DATA_FORMAT_JSON);
        utils.requestByPost(ConstantUtils.WEATHER_URL, builder);
    }

    /**
     * 请求未来5-7天的天气
     */
    private void requestFutureWeather(){
        OkhttpUtils utils = new OkhttpUtils();
        utils.setOnResponseListener(new OkhttpUtils.ResponseListener() {
            @Override
            public void onSuccessAction(final Response response) {
                try {
                    resbody = (FutureWeatherRes) FastJsonUtils.analysisObject(response.body().string(), FutureWeatherRes.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Thread(){
                    @Override
                    public void run() {
                        setFutureWeatherData(resbody);
                    }
                });
            }
        });
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("app", ConstantUtils.CITY_WEATHER_FUTURE_APP)
                .add("weaid", "1")//需要改
                .add("appkey", ConstantUtils.WEATHER_APPKEY)
                .add("sign", ConstantUtils.WEATHER_SIGN)
                .add("format", ConstantUtils.DATA_FORMAT_JSON);
        utils.requestByPost(ConstantUtils.WEATHER_URL, builder);
    }

    /**
     * 请求空气质量的数据
     */
    private void requestAirQuality(){
        OkhttpUtils utils = new OkhttpUtils();
        utils.setOnResponseListener(new OkhttpUtils.ResponseListener() {
            @Override
            public void onSuccessAction(Response response) {
                try {
                    airRes = (WeatherAirRes) FastJsonUtils.analysisObject(response.body().string(), WeatherAirRes.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setWeatherAirData(airRes);
                    }
                });
            }
        });
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("app", ConstantUtils.CITY_WEATHER_PM_APP)
                .add("weaid", "1")//此处后期改
                .add("appkey", ConstantUtils.WEATHER_APPKEY)
                .add("sign", ConstantUtils.WEATHER_SIGN)
                .add("format", ConstantUtils.DATA_FORMAT_JSON);
        utils.requestByPost(ConstantUtils.WEATHER_URL, builder);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_city_change:
                Intent intent = new Intent(getActivity(), CityListActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void load(){
        final int background[] = new int[]{R.drawable.load_circle_darker_gray, R.drawable.load_circle_lighter_gray};
        final int iv[] = {R.id.img_load_circle1, R.id.img_load_circle2, R.id.img_load_circle3,
                R.id.img_load_circle4, R.id.img_load_circle5};
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == 111) {
                    for (int i = 0; i < 5; i++) {
                        view.findViewById(iv[i]).setBackgroundResource(i == tagI ? background[0] : background[1]);
                    }
                    tagI++;
                    if (tagI == 5) {
                        tagI = 0;
                    }
                }
            }
        };

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(111);
            }
        }, 0, 500);
    }
}
