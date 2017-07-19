package com.example.cyy.weather.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cyy.weather.R;

/**
 * Created by cyy on 2017/1/16.
 *
 * 在线音乐播放列表
 */
public class OnLineMusicFragment extends Fragment{
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_local_music, null);
        return view;
    }
}
