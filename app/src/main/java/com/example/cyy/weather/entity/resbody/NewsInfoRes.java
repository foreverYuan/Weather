package com.example.cyy.weather.entity.resbody;

import com.example.cyy.weather.entity.obj.NewsListObj;

import java.io.Serializable;

/**
 * Created by cyy on 2017/1/29.
 */
public class NewsInfoRes implements Serializable{
    public String showapi_res_code;
    public String showapi_res_error;
    public NewsListObj showapi_res_body;
}
