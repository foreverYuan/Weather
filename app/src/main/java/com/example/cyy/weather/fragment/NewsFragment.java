package com.example.cyy.weather.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.cyy.weather.R;
import com.example.cyy.weather.entity.obj.NewsChannelObj;
import com.example.cyy.weather.entity.resbody.NewsChannelRes;
import com.example.cyy.weather.utils.ConstantUtils;
import com.example.cyy.weather.utils.FastJsonUtils;
import com.example.cyy.weather.utils.OkhttpUtils;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by cyy on 2016/10/9.
 *
 * 新闻
 */
public class NewsFragment extends Fragment {
    private View view;
    private LinearLayout mLLTop;
    private ViewPager mVPContainer;
    private int defaultChannelNum = 7;//默认显示的频道数量
    private ArrayList<NewsChannelObj> channelList = new ArrayList<>();
    private NewsChannelRes channelRes;
    private ArrayList<Fragment> fragmentList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, null);
        initView();
        requestChannel();
        setViewPager();
        return view;
    }

    private void setViewPager() {
        for(int i = 0; i < defaultChannelNum; i++){
            Fragment fragment = new NewsChannelFragment();
            fragmentList.add(fragment);
        }
        mVPContainer.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class MyPagerAdapter extends FragmentPagerAdapter{

        public MyPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
//            String channelId = channelList.get(position).channelId;
//            Bundle bundle = new Bundle();
//            bundle.putString("channelId", channelId);
//            setArguments();
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return defaultChannelNum;
        }
    }

    private void initView() {
        mLLTop = (LinearLayout) view.findViewById(R.id.ll_fragment_news_top);
        mVPContainer = (ViewPager) view.findViewById(R.id.vp_fragment_news_container);
    }

    private void requestChannel() {
        OkhttpUtils utils = new OkhttpUtils();
        utils.setOnResponseListener(new OkhttpUtils.ResponseListener() {
            @Override
            public void onSuccessAction(Response response) {
                try {
                    channelRes = (NewsChannelRes) FastJsonUtils.analysisObject(response.body().string(), NewsChannelRes.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                channelList.addAll(channelRes.showapi_res_body.channelList.subList(0, 7));
                getActivity().runOnUiThread(new Thread(){
                    @Override
                    public void run() {
                        initTop();
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        mVPContainer.setAdapter(new MyPagerAdapter(manager));
                    }
                });
            }
        });
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("showapi_appid", ConstantUtils.SHOWAPI_APPID)
                .add("showapi_sign", ConstantUtils.SHOWAPI_SIGN);
        utils.requestByPost(ConstantUtils.NEWS_CHANNEL_URL, builder);
    }

    private void initTop(){
        for (int i = 0; i < defaultChannelNum; i++) {
            TextView topView = new TextView(getActivity());
            topView.setText(channelList.get(i).name);
            mLLTop.addView(topView);
        }
    }
}
