package com.example.cyy.weather.entity.obj;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cyy on 2017/1/9.
 */
public class FutureWeatherObj implements Parcelable{
    public String weaid; //"1",
    public String days; //"2014-07-30",
    public String week; //"星期三",
    public String cityno; //"beijing",
    public String citynm; //"北京",
    public String cityid; //"101010100",
    public String temperature; //"31℃/24℃",
    public String humidity; //"0℉/0℉", /*湿度,后期气像局未提供,如有需要可使用weather.today接口 */
    public String weather; //多云转晴",
    public String weather_icon; //http://api.k780.com:88/upload/weather/d/1.gif", /*气象图标 下载*/
    public String weather_icon1; //http://api.k780.com:88/upload/weather/d/0.gif", /*气象图标 下载*/
    public String wind; //"微风", /*风向*/
    public String winp; //小于3级", /*风力*/
    public String temp_high; //"31", /*最高温度*/
    public String temp_low; //"24", /*最低温度*/
    public String humi_high; //"0",
    public String humi_low; //"0",
    public String weatid; //"2", /*白天天气ID*/
    public String weatid1; //"1", /*夜间天气ID*/
    public String windid; //"1", /*风向ID*/

    protected FutureWeatherObj(Parcel in) {
        weaid = in.readString();
        days = in.readString();
        week = in.readString();
        cityno = in.readString();
        citynm = in.readString();
        cityid = in.readString();
        temperature = in.readString();
        humidity = in.readString();
        weather = in.readString();
        weather_icon = in.readString();
        weather_icon1 = in.readString();
        wind = in.readString();
        winp = in.readString();
        temp_high = in.readString();
        temp_low = in.readString();
        humi_high = in.readString();
        humi_low = in.readString();
        weatid = in.readString();
        weatid1 = in.readString();
        windid = in.readString();
    }

    protected FutureWeatherObj(){

    }

    public static final Creator<FutureWeatherObj> CREATOR = new Creator<FutureWeatherObj>() {
        @Override
        public FutureWeatherObj createFromParcel(Parcel in) {
            return new FutureWeatherObj(in);
        }

        @Override
        public FutureWeatherObj[] newArray(int size) {
            return new FutureWeatherObj[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(weaid);
        dest.writeString(days);
        dest.writeString(week);
        dest.writeString(cityno);
        dest.writeString(citynm);
        dest.writeString(cityid);
        dest.writeString(temperature);
        dest.writeString(humidity);
        dest.writeString(weather);
        dest.writeString(weather_icon);
        dest.writeString(weather_icon1);
        dest.writeString(wind);
        dest.writeString(winp);
        dest.writeString(temp_high);
        dest.writeString(temp_low);
        dest.writeString(humi_high);
        dest.writeString(humi_low);
        dest.writeString(weatid);
        dest.writeString(weatid1);
        dest.writeString(windid);
    }
}
