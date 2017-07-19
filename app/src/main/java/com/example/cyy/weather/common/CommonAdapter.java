package com.example.cyy.weather.common;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by cyy on 2016/10/18.
 */
public abstract class CommonAdapter<T> extends BaseAdapter{
    protected Context mContext;
    protected List<T> mDatas;
    private int layoutId;

    public CommonAdapter(Context context){
        this.mContext = context;
    }

    public CommonAdapter(Context context, int layoutId){
        this.mContext = context;
        this.layoutId = layoutId;
    }

    public CommonAdapter(Context context, List<T> datas, int layoutId){
        this.mContext = context;
        this.mDatas = datas;
        this.layoutId = layoutId;
    }

    public void setDatas(List<T> datas){
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    public void setLayoutId(int layoutId){
        this.layoutId = layoutId;
    }
    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas == null ? null : mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T obj = (T) getItem(position);
        CommonViewHolder viewholder = CommonViewHolder.getViewHolder(mContext, convertView, layoutId, parent, position);
        if(obj != null) {
            setItemData(viewholder, obj);
        }
        return viewholder.getConvertView();
    }

    public abstract void setItemData(CommonViewHolder viewHolder, T obj);
}
