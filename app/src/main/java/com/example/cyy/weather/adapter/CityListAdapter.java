package com.example.cyy.weather.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cyy.weather.R;

import java.util.List;

/**
 * Created by cyy on 2016/9/29.
 */
public class CityListAdapter extends BaseAdapter{
    private List<String> mTitleItemList;
    private List<String> mContentItemList;
    private final int TITLE_TYPE = 1;
    private final int CONTENT_TYPE = 2;
    private Context context;

    public CityListAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return mTitleItemList.size() + mContentItemList.size();
    }

    @Override
    public Object getItem(int position) {
        if(TITLE_TYPE == getItemViewType(position)) {
            return mTitleItemList.get(position);
        }
        return mContentItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TitleViewHolder titleViewHolder;
        ContentViewHolder contentViewHolder;
        if(convertView == null){
            if(TITLE_TYPE == getItemViewType(position)) {
                convertView = View.inflate(context, R.layout.city_list_title_layout, null);
                titleViewHolder = new TitleViewHolder();
                titleViewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                convertView.setTag(titleViewHolder);
            }else{
                convertView = View.inflate(context, R.layout.city_list_content_layout, null);
                contentViewHolder = new ContentViewHolder();
                contentViewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
                convertView.setTag(contentViewHolder);
            }
        }else{
            if(TITLE_TYPE == getItemViewType(position)){
                titleViewHolder = (TitleViewHolder) convertView.getTag();
//            titleViewHolder.tv_title.setText();
            }else{
                contentViewHolder = (ContentViewHolder) convertView.getTag();
//                contentViewHolder.tv_content.setText();
            }
        }
        return convertView;
    }

    class TitleViewHolder{
        TextView tv_title;
    }

    class ContentViewHolder{
        TextView tv_content;
    }

    @Override
    public int getItemViewType(int position) {
        if(mTitleItemList.get(position) != null){
            return TITLE_TYPE;
        }
        return CONTENT_TYPE;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
}
