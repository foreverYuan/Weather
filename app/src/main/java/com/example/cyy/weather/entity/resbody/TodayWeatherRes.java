package com.example.cyy.weather.entity.resbody;

import com.example.cyy.weather.entity.obj.TodayWeatherObj;

import java.io.Serializable;

/**
 * Created by cyy on 2016/9/28.
 */
public class TodayWeatherRes implements Serializable{
    public String success;
    public TodayWeatherObj result;

}
