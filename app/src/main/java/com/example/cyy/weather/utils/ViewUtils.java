package com.example.cyy.weather.utils;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

/**
 * Created by cyy on 2017/1/15.
 */
public class ViewUtils {

    /**
     * 给TextView赋值，如果数据为空的则隐藏TextView
     * @param tv
     * @param fixStr
     * @param requestStr
     */
    public static void showText(TextView tv, String fixStr, String requestStr){
        if(tv != null) {
            if (!TextUtils.isEmpty(requestStr)) {
                tv.setVisibility(View.VISIBLE);
                tv.setText(fixStr + requestStr);
            } else {
                tv.setVisibility(View.GONE);
            }
        }
    }
}
