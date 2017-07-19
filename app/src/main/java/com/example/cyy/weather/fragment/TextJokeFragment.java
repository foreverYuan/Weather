package com.example.cyy.weather.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.cyy.weather.R;
import com.example.cyy.weather.adapter.JokeTextAdapter;
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
 */
public class TextJokeFragment extends Fragment{
    private View view;
    private PullToRefreshListView mPullToRefreshListView;
    private JokesRes jokesRes;
    JokeTextAdapter adapter;
    private int mPage = 1;//请求的页数
    private ArrayList<JokeObj> contentlist = new ArrayList<JokeObj>();//数据集
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_text_joke, null);
        initView();
        requestTextJokeData(mPage);
        return view;
    }

    private void initView() {
        mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.lv_text_joke);
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);//只能上拉加载更多，不能下拉刷新
        ILoadingLayout layout = mPullToRefreshListView.getLoadingLayoutProxy(false, true);
        layout.setPullLabel("加载更多");
        layout.setReleaseLabel("放手刷新");
        layout.setRefreshingLabel("正在加载，请稍后...");
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                requestTextJokeData(mPage);
            }
        });
        adapter = new JokeTextAdapter(getActivity(), R.layout.lv_text_joke_item);
        mPullToRefreshListView.setAdapter(adapter);
    }

    private void requestTextJokeData(int page) {
        OkhttpUtils utils = new OkhttpUtils();
        utils.setOnResponseListener(new OkhttpUtils.ResponseListener() {
            @Override
            public void onSuccessAction(Response response) {
                try {
                    jokesRes = (JokesRes) FastJsonUtils.analysisObject(response.body().string(), JokesRes.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Thread(){
                    @Override
                    public void run() {
                        if(jokesRes != null && jokesRes.showapi_res_body != null && jokesRes.showapi_res_body.contentlist != null &&
                                jokesRes.showapi_res_body.contentlist.size() > 0) {
                            contentlist.addAll(jokesRes.showapi_res_body.contentlist);
                            adapter.setDatas(contentlist);
                            mPullToRefreshListView.onRefreshComplete();//加载数据成功
                            int currentPage = Integer.parseInt(jokesRes.showapi_res_body.currentPage);//当前页数
                            mPage = ++currentPage;
                        }
                    }
                });
            }
        });
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("showapi_appid", ConstantUtils.SHOWAPI_APPID)
                .add("showapi_sign", ConstantUtils.SHOWAPI_SIGN)
                .add("time", "2016-01-01")
                .add("page", String.valueOf(page))
                .add("maxResult", "20");
        utils.requestByPost(ConstantUtils.TEXT_JOKE, builder);
    }
}
