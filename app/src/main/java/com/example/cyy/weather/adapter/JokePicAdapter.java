package com.example.cyy.weather.adapter;

import android.content.Context;

import com.example.cyy.weather.R;
import com.example.cyy.weather.common.CommonAdapter;
import com.example.cyy.weather.common.CommonViewHolder;
import com.example.cyy.weather.entity.obj.JokeObj;
import com.example.cyy.weather.utils.Tools;

import java.util.List;

/**
 * Created by cyy on 2016/12/21.
 */
public class JokePicAdapter extends CommonAdapter<JokeObj>{
    private Context mContext;

    public JokePicAdapter(Context context){
        super(context);
    }

    public JokePicAdapter(Context context, int layoutId){
        super(context, layoutId);
        this.mContext = context;
    }

    public JokePicAdapter(Context context, List<JokeObj> datas, int layoutId) {
        super(context, datas, layoutId);
        mContext = context;
    }

    @Override
    public void setItemData(CommonViewHolder viewHolder, JokeObj obj) {
        viewHolder.setText(R.id.tv_joke_title, obj.title)
                .setImageView(mContext, R.id.img_joke, obj.img);
    }
}
