package com.example.cyy.weather.entity.resbody;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.cyy.weather.entity.obj.FutureWeatherObj;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cyy on 2017/1/9.
 */
public class FutureWeatherRes implements Parcelable{
    public String success;
    public ArrayList<FutureWeatherObj> result;

    protected FutureWeatherRes(){

    }

    protected FutureWeatherRes(Parcel in) {
        success = in.readString();
        result = in.createTypedArrayList(FutureWeatherObj.CREATOR);
    }

    public static final Creator<FutureWeatherRes> CREATOR = new Creator<FutureWeatherRes>() {
        @Override
        public FutureWeatherRes createFromParcel(Parcel in) {
            return new FutureWeatherRes(in);
        }

        @Override
        public FutureWeatherRes[] newArray(int size) {
            return new FutureWeatherRes[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(success);
        dest.writeTypedList(result);
    }
}
