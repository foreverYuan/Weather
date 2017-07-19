package com.example.cyy.weather.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.cyy.weather.HoroscopeActivity;
import com.example.cyy.weather.JokeActivity;
import com.example.cyy.weather.MusicActivity;
import com.example.cyy.weather.R;
import com.example.cyy.weather.widget.MyImageView;

/**
 * Created by cyy on 2016/10/9.
 *
 * 娱乐
 */
public class DisportFragment extends Fragment implements View.OnClickListener{
    private View view;
    private MyImageView img_music, img_horoscope, img_joke, img_duke_dream;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_disport, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        initView();
        return view;
    }

    private void initView() {
        img_music = (MyImageView) view.findViewById(R.id.img_music);
        img_horoscope = (MyImageView) view.findViewById(R.id.img_horoscope);
        img_joke = (MyImageView) view.findViewById(R.id.img_joke);
        img_duke_dream = (MyImageView) view.findViewById(R.id.img_duke_dream);
        img_music.setOnClickListener(this);
        img_horoscope.setOnClickListener(this);
        img_joke.setOnClickListener(this);
        img_duke_dream.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_music: //音乐
                Intent intent_music = new Intent(getActivity(), MusicActivity.class);
                startActivity(intent_music);
                break;
            case R.id.img_horoscope: //星座运势
                Intent intent_horoscope = new Intent(getActivity(), HoroscopeActivity.class);
                startActivity(intent_horoscope);
                break;
            case R.id.img_joke: //笑话
                Intent intent_joke = new Intent(getActivity(), JokeActivity.class);
                startActivity(intent_joke);
                break;
            case R.id.img_duke_dream: //周公解梦

                break;
        }
    }
}
