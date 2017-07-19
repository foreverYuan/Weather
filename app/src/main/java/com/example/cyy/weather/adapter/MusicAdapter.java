package com.example.cyy.weather.adapter;

import android.content.Context;

import com.example.cyy.weather.R;
import com.example.cyy.weather.common.CommonAdapter;
import com.example.cyy.weather.common.CommonViewHolder;
import com.example.cyy.weather.entity.obj.LocalSongObj;

import java.util.List;

/**
 * Created by cyy on 2017/1/16.
 */
public class MusicAdapter extends CommonAdapter<LocalSongObj>{
    public MusicAdapter(Context context) {
        super(context);
    }

    public MusicAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    public MusicAdapter(Context context, List<LocalSongObj> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void setItemData(CommonViewHolder viewHolder, LocalSongObj obj) {
        viewHolder.setText(R.id.tv_music_lv_item_song_name, obj.songname)
                .setText(R.id.tv_music_lv_item_singer_name, obj.singername);
    }
}
