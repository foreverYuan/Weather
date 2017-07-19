package com.example.cyy.weather.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * fastJson解析
 *
 * Created by cyy on 2016/9/29.
 */
public class FastJsonUtils {
    /**
     *解析对象
     */
    public static Object analysisObject(String jsonString, Class cls){
        return JSON.parseObject(jsonString, cls);
    }

    private static List<Object> analysisList(String jsonString, Class cls){
        return JSON.parseArray(jsonString, cls);
    }
}
