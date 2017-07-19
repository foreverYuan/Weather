package com.example.cyy.weather.adapter;

import android.content.Context;

import com.example.cyy.weather.R;
import com.example.cyy.weather.common.CommonAdapter;
import com.example.cyy.weather.common.CommonViewHolder;
import com.example.cyy.weather.entity.obj.FutureWeatherObj;

import java.util.List;

/**
 * Created by cyy on 2017/1/9.
 *
 * 未来天气Adapter
 */
public class FutureWeatherAdapter extends CommonAdapter<FutureWeatherObj>{

    public FutureWeatherAdapter(Context context, List<FutureWeatherObj> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public int getCount() {
        return mDatas != null ? (mDatas.size() > 3 ? 3 : mDatas.size()) : 0;
    }

    @Override
    public void setItemData(CommonViewHolder viewHolder, FutureWeatherObj obj) {
        viewHolder.setText(R.id.tv_future_weather_lv_item_date, obj.week)
                .setText(R.id.tv_future_weather_lv_item_wea_wind, obj.weather + "|" + obj.wind)
                .setText(R.id.tv_future_weather_lv_item_tem, obj.temperature);
    }
}
