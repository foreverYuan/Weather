package com.example.cyy.weather.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cyy.weather.R;
import com.example.cyy.weather.adapter.NewsListAdapter;
import com.example.cyy.weather.entity.resbody.NewsChannelRes;
import com.example.cyy.weather.entity.resbody.NewsInfoRes;
import com.example.cyy.weather.utils.ConstantUtils;
import com.example.cyy.weather.utils.FastJsonUtils;
import com.example.cyy.weather.utils.OkhttpUtils;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by cyy on 2017/1/29.
 */
public class NewsChannelFragment extends Fragment{
    private View view;
    private PullToRefreshListView mPullToRefreshListView;
    private NewsListAdapter newsListAdapter;
    private String channelId = "5572a108b3cdc86cf39001cd";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news_channel_list_layout, null);
        initView();
//        getArgumentsData();
        requestData();
        return view;
    }

    private void requestData() {
        OkhttpUtils utils = new OkhttpUtils();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("showapi_appid", ConstantUtils.SHOWAPI_APPID)
                .add("showapi_sign", ConstantUtils.SHOWAPI_SIGN)
                .add("channelId", channelId);
        utils.setOnResponseListener(new OkhttpUtils.ResponseListener() {
            @Override
            public void onSuccessAction(Response response) {
                try {
                    final NewsInfoRes newsInfoRes = (NewsInfoRes) FastJsonUtils.analysisObject(response.body().string(), NewsInfoRes.class);
                    getActivity().runOnUiThread(new Thread(){
                        @Override
                        public void run() {
                            newsListAdapter.setDatas(newsInfoRes.showapi_res_body.pagebean.contentlist);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        utils.requestByPost(ConstantUtils.NEWS_INFO_URL, builder);
    }

    private void initView() {
        mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.lv_fragment_news_channel_list_layout);
        newsListAdapter = new NewsListAdapter(getActivity());
        mPullToRefreshListView.setAdapter(newsListAdapter);
    }

    public void getArgumentsData() {
        Bundle bundle = getArguments();
        channelId = bundle.getString("channelId");
    }
}
