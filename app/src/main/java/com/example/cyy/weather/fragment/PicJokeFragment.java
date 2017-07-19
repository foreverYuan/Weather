package com.example.cyy.weather.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.cyy.weather.R;
import com.example.cyy.weather.adapter.JokePicAdapter;
import com.example.cyy.weather.entity.obj.JokeObj;
import com.example.cyy.weather.entity.resbody.JokesRes;
import com.example.cyy.weather.utils.ConstantUtils;
import com.example.cyy.weather.utils.FastJsonUtils;
import com.example.cyy.weather.utils.OkhttpUtils;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by cyy on 2016/12/21.
 *
 * 带图的笑话列表
 */
public class PicJokeFragment extends Fragment{
    private View view;
    private PullToRefreshListView mPullToRefreshListView;
    private JokesRes jokesRes;
    private JokePicAdapter adapter;
    private int mPage = 1;//请求列表的页数
    private ArrayList<JokeObj> contentlist = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pic_joke, null);
        initView();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        requestJokeData(String.valueOf(mPage));
    }

    private void initView() {
        mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.lv_pic_joke);
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        ILoadingLayout layout = mPullToRefreshListView.getLoadingLayoutProxy(false, true);
        layout.setPullLabel("加载更多");
        layout.setReleaseLabel("放开刷新");
        layout.setRefreshingLabel("正在加载，请稍候...");
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                requestJokeData(String.valueOf(mPage));
            }
        });
        adapter = new JokePicAdapter(getActivity(), R.layout.lv_pic_joke_item);
        mPullToRefreshListView.setAdapter(adapter);
    }

    private void requestJokeData(String page) {
        OkhttpUtils utils = new OkhttpUtils();
        utils.setOnResponseListener(new OkhttpUtils.ResponseListener() {
            @Override
            public void onSuccessAction(final Response response) {
                try {
                    jokesRes = (JokesRes) FastJsonUtils.analysisObject(response.body().string(), JokesRes.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Thread(){
                    @Override
                    public void run() {
                        contentlist.addAll(jokesRes.showapi_res_body.contentlist);
                        adapter.setDatas(contentlist);
                        adapter.notifyDataSetChanged();
                        mPullToRefreshListView.onRefreshComplete();
                        int currentPage = Integer.parseInt(jokesRes.showapi_res_body.currentPage);
                        mPage = ++currentPage;
                    }
                });
            }
        });
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("showapi_appid", ConstantUtils.SHOWAPI_APPID)
                .add("showapi_sign", ConstantUtils.SHOWAPI_SIGN)
                .add("time", "2016-01-01")
                .add("page", page)
                .add("maxResult", "20");
        utils.requestByPost(ConstantUtils.PIC_JOKE, builder);
    }

}
