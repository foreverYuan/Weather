package com.example.cyy.weather;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.cyy.weather.fragment.PicJokeFragment;
import com.example.cyy.weather.fragment.TextJokeFragment;

/**
 * Created by cyy on 2016/12/21.
 */
public class JokeActivity extends Activity {
    private TextView tv_pic_joke, tv_text_joke;
    private FragmentManager manager;
    private Fragment picJokeFragment, textJokeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        initView();
        initFragment();
    }

    private void initFragment() {
        manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        picJokeFragment = new PicJokeFragment();
        transaction.add(R.id.ll_container, picJokeFragment);
        transaction.commit();
    }

    private void initView() {
        tv_pic_joke = (TextView) findViewById(R.id.tv_pic_joke);
        tv_text_joke = (TextView) findViewById(R.id.tv_text_joke);
    }

    public void doClick(View view){
        FragmentTransaction transaction = manager.beginTransaction();
        switch (view.getId()){
            case R.id.tv_pic_joke:
                picJokeFragment = picJokeFragment == null ? new PicJokeFragment() : picJokeFragment;
                transaction.replace(R.id.ll_container, picJokeFragment);
                tv_pic_joke.setTextColor(Color.parseColor("#5DCBE6"));
                tv_pic_joke.setTextSize(18);
                tv_text_joke.setTextColor(Color.parseColor("#777777"));
                tv_text_joke.setTextSize(15);
                break;
            case R.id.tv_text_joke:
                textJokeFragment = textJokeFragment == null ? new TextJokeFragment() : textJokeFragment;
                transaction.replace(R.id.ll_container, textJokeFragment);
                tv_pic_joke.setTextColor(Color.parseColor("#777777"));
                tv_pic_joke.setTextSize(15);
                tv_text_joke.setTextColor(Color.parseColor("#5DCBE6"));
                tv_text_joke.setTextSize(18);
                break;
        }
        transaction.commit();
    }

}
