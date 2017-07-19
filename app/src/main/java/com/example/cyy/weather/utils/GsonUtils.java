package com.example.cyy.weather.utils;

import com.google.gson.Gson;

/**
 * Created by cyy on 2016/9/29.
 */
public class GsonUtils {
    /**
     * Gson解析json字符串
     * @param jsonString
     * @return
     */
    private Object analysisJsonByGson(String jsonString, Class cls){
        return new Gson().fromJson(jsonString,cls);
    }

    /**
     * JsonObject解析json字符串
     * @param jsonString
     * @return
     *//*
    private TodayWeatherRes analysisJson(String jsonString){
        TodayWeatherRes outResbody = new TodayWeatherRes();
        TodayWeatherObj resbody = new TodayWeatherObj();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject jsonWeatherObj = jsonObject.getJSONObject("result");
            resbody.days = jsonWeatherObj.getString("days");
            resbody.week = jsonWeatherObj.getString("week");
            resbody.citynm = jsonWeatherObj.getString("citynm");
            resbody.weather = jsonWeatherObj.getString("weather");
            resbody.weather_icon = jsonWeatherObj.getString("weather_icon");
            resbody.temperature_curr = jsonWeatherObj.getString("temperature_curr");
            outResbody.result = resbody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return outResbody;
    }*/
}
