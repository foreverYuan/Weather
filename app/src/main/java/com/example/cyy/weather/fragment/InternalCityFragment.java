package com.example.cyy.weather.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.cyy.weather.R;
import com.example.cyy.weather.utils.ConstantUtils;
import com.example.cyy.weather.utils.OkhttpUtils;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Response;

/**
 * Created by cyy on 2016/9/28.
 */
public class InternalCityFragment extends Fragment{
    private ListView lv_city_list;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_internal_city_list, null);
        initView();
        requestCityListData();
        return view;
    }

    private void initView() {
        lv_city_list = (ListView) view.findViewById(R.id.lv_city_list);
    }

    private void requestCityListData() {
        OkhttpUtils utils = new OkhttpUtils();
        utils.setOnResponseListener(new OkhttpUtils.ResponseListener() {
            @Override
            public void onSuccessAction(Response response) {
                getActivity().runOnUiThread(new Runnable() {
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
                .add("format", ConstantUtils.DATA_FORMAT_JSON)
                .add("cou", "1");//cou为int型参数，次数只能使用String，后期优化
        utils.requestByPost("http://api.k780.com:88", builder);
    }
}
