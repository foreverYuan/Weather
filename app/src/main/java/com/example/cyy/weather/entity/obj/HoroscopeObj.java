package com.example.cyy.weather.entity.obj;

import java.io.Serializable;

/**
 * Created by cyy on 2017/1/14.
 */
public class HoroscopeObj implements Serializable{
    public String ret_code; //0
    public String star; //"star": "baiyang",
    public HoroscopeLuckObj tomorrow;
    public HoroscopeLuckObj month;
    public HoroscopeLuckObj year;
    public HoroscopeLuckObj day;
    public HoroscopeLuckObj week;
}
