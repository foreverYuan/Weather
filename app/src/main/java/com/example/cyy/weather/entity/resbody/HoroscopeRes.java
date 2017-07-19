package com.example.cyy.weather.entity.resbody;

import com.example.cyy.weather.entity.obj.HoroscopeObj;
import java.io.Serializable;

/**
 * Created by cyy on 2017/1/14.
 */
public class HoroscopeRes implements Serializable{
    public String showapi_res_code;
    public String showapi_res_error;
    public HoroscopeObj showapi_res_body;
}
