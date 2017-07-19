package com.example.cyy.weather.entity.resbody;

import com.example.cyy.weather.entity.obj.WeatherAirObj;
import java.io.Serializable;

/**
 * Created by cyy on 2017/1/13.
 */
public class WeatherAirRes implements Serializable{
    public String success;
    public WeatherAirObj result;
}
