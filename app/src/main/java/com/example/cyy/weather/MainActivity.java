package com.example.cyy.weather;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.cyy.weather.fragment.DisportFragment;
import com.example.cyy.weather.fragment.NewsFragment;
import com.example.cyy.weather.fragment.WeatherFragment;

public class MainActivity extends FragmentActivity implements View.OnClickListener{
    private TextView tv_weather_tab, tv_disport_tab, tv_news_tab;
    private RelativeLayout rl_container;
    private FragmentManager manager;
    private Fragment weatherFragment, disportFragment, newsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tv_weather_tab = (TextView) findViewById(R.id.tv_weather_tab);
        tv_disport_tab = (TextView) findViewById(R.id.tv_disport_tab);
        tv_news_tab = (TextView) findViewById(R.id.tv_news_tab);
        tv_weather_tab.setOnClickListener(this);
        tv_disport_tab.setOnClickListener(this);
        tv_news_tab.setOnClickListener(this);
        rl_container = (RelativeLayout) findViewById(R.id.rl_container);
        setDefaultFragment();
    }

    private void setDefaultFragment(){
        manager = getSupportFragmentManager();
        //开启fragment事务
        FragmentTransaction transaction = manager.beginTransaction();
        weatherFragment = new WeatherFragment();
        transaction.add(R.id.rl_container, weatherFragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = manager.beginTransaction();
        switch(v.getId()){
            case R.id.tv_weather_tab:
              if(weatherFragment == null){
                  weatherFragment = new WeatherFragment();
              }
                transaction.replace(R.id.rl_container, weatherFragment);
                setTabBackground(tv_weather_tab, tv_disport_tab, tv_news_tab);
              break;
            case R.id.tv_disport_tab:
                if(disportFragment == null){
                    disportFragment = new DisportFragment();
                }
                transaction.replace(R.id.rl_container, disportFragment);
                setTabBackground(tv_disport_tab, tv_weather_tab, tv_news_tab);
                break;
            case R.id.tv_news_tab:
                if(newsFragment == null){
                    newsFragment = new NewsFragment();
                }
                transaction.replace(R.id.rl_container, newsFragment);
                setTabBackground(tv_news_tab, tv_weather_tab, tv_disport_tab);
                break;
        }
        transaction.commit();
    }

    /**
     *
     * @param tv1 选中的tab
     * @param tv2 未选中的tab
     * @param tv3 未选中的tab
     */
    private void setTabBackground(TextView tv1, TextView tv2, TextView tv3){
        tv1.setBackgroundColor(Color.parseColor("#6aa84f"));
        tv1.setTextColor(getResources().getColor(R.color.white));
        tv2.setBackgroundResource(R.color.white);
        tv2.setTextColor(getResources().getColor(R.color.black));
        tv3.setBackgroundResource(R.color.white);
        tv3.setTextColor(getResources().getColor(R.color.black));
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onMenuItemSelected(int featureId, MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.item_city_change:
//                Intent intent = new Intent(this, CityListActivity.class);
//                startActivity(intent);
//                break;
//        }
//        return true;
//    }
}
