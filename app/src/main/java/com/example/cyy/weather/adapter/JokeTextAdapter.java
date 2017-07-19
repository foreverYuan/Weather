package com.example.cyy.weather.adapter;

import android.content.Context;
import android.text.Html;

import com.example.cyy.weather.R;
import com.example.cyy.weather.common.CommonAdapter;
import com.example.cyy.weather.common.CommonViewHolder;
import com.example.cyy.weather.entity.obj.JokeObj;

import java.util.List;

/**
 * Created by cyy on 2016/12/21.
 */
public class JokeTextAdapter extends CommonAdapter<JokeObj>{

    public JokeTextAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    public JokeTextAdapter(Context context, List<JokeObj> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void setItemData(CommonViewHolder viewHolder, JokeObj obj) {
        viewHolder.setText(R.id.tv_text_joke_title, Html.fromHtml(obj.title).toString())
                .setText(R.id.tv_text_joke_content, Html.fromHtml(obj.text).toString());
    }
}