package com.example.cyy.weather.common;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cyy.weather.R;
import com.squareup.picasso.Picasso;

/**
 * Created by cyy on 2016/10/18.
 */
public class CommonViewHolder {
    private View mConvertView;
    private SparseArray<View> mViews;
    private static int mPosition;
    public CommonViewHolder(Context context, int layoutId, ViewGroup parent, int position){
        this.mViews = new SparseArray<View>();
        this.mPosition = position;
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
    }

    public static CommonViewHolder getViewHolder(Context context, View convertView, int layoutId, ViewGroup parent, int position){
        mPosition = position;
        if(convertView == null){
            return new CommonViewHolder(context, layoutId, parent, position);
        }else{
            return (CommonViewHolder) convertView.getTag();
        }
    }

    public View getView(int viewId){
        View view = mViews.get(viewId);
        if(view == null){
          view = mConvertView.findViewById(viewId);
          mViews.put(viewId, view);
        }
        return view;
    }

    public CommonViewHolder setText(int viewId, String text){
        ((TextView)getView(viewId)).setText(text);
        return this;
    }

    public CommonViewHolder setImageView(Context context, int viewId, String url){
        Picasso.with(context)
                .load(url)
//                .resize(targetWidth, targetHeight)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into((ImageView) getView(viewId));
        return this;
    }

    public View getConvertView(){
        return mConvertView;
    }
}
