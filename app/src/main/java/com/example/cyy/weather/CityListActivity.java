package com.example.cyy.weather;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cyy.weather.utils.ConstantUtils;
import com.example.cyy.weather.utils.OkhttpUtils;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Response;

/**
 * Created by cyy on 2016/9/28.
 */
public class CityListActivity extends Activity implements View.OnClickListener{
    private TextView tv_internal, tv_external;
    private LinearLayout ll_container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        initView();
        requestCityListData();
    }

    private void requestCityListData() {
        OkhttpUtils utils = new OkhttpUtils();
        utils.setOnResponseListener(new OkhttpUtils.ResponseListener() {
            @Override
            public void onSuccessAction(Response response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("app", ConstantUtils.CITY_LIST_APP)
                .add("appkey", ConstantUtils.WEATHER_APPKEY)
                .add("sign", ConstantUtils.WEATHER_SIGN)
                .add("format", ConstantUtils.DATA_FORMAT_JSON);
        utils.requestByPost("http://api.k780.com:88", builder);
    }

    private void initView() {
        tv_internal = (TextView) findViewById(R.id.tv_internal);
        tv_internal.setOnClickListener(this);
        tv_external = (TextView) findViewById(R.id.tv_external);
        tv_external.setOnClickListener(this);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_internal:
                tv_internal.setBackgroundColor(Color.parseColor("#999999"));
                tv_external.setBackgroundColor(Color.parseColor("#00000000"));
                break;

            case R.id.tv_external:
                tv_internal.setBackgroundColor(Color.parseColor("#00000000"));
                tv_external.setBackgroundColor(Color.parseColor("#999999"));
                break;

            default:
                break;
        }
    }
}
