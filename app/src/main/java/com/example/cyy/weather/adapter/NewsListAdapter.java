package com.example.cyy.weather.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.example.cyy.weather.R;
import com.example.cyy.weather.common.CommonAdapter;
import com.example.cyy.weather.common.CommonViewHolder;
import com.example.cyy.weather.entity.obj.NewsDetailInfoObj;
import java.util.ArrayList;

/**
 * Created by cyy on 2017/1/29.
 */
public class NewsListAdapter extends CommonAdapter<NewsDetailInfoObj>{
    private int type;
    private static final int TYPE_SINGLE_PIC = 0;
    private static final int TYPE_MULTI_PIC = 1;
    private static final int TYPE_NO_PIC = 2;

    public NewsListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        boolean havePic = mDatas.get(position).havePic;
        ArrayList imageUrls = mDatas.get(position).imageurls;
        type = havePic == true && imageUrls != null && imageUrls.size() > 0 ?
                (imageUrls.size() > 1 ? TYPE_MULTI_PIC : TYPE_SINGLE_PIC)
                : TYPE_NO_PIC;
        return type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        NewsDetailInfoObj obj = (NewsDetailInfoObj) getItem(position);
        switch (type){
            case TYPE_SINGLE_PIC:
                CommonViewHolder singlePicHolder = CommonViewHolder.getViewHolder(mContext, convertView, R.layout.fragment_news_info_item_single_pic, null, position);
                singlePicHolder.setText(R.id.tv_fragment_news_info_item_single_pic_title, obj.title)
                        .setText(R.id.tv_fragment_news_info_item_single_pic_source, obj.source)
                        .setText(R.id.tv_fragment_news_info_item_single_item_time, obj.pubDate)
                        .setImageView(mContext, R.id.img_fragment_news_info_item_single_pic, obj.imageurls.get(0).url);
                return singlePicHolder.getConvertView();
            case TYPE_MULTI_PIC:
                CommonViewHolder multiPicHolder = CommonViewHolder.getViewHolder(mContext, convertView, R.layout.fragment_news_info_item_multi_pic, null, position);
                multiPicHolder.setText(R.id.tv_fragment_news_info_item_multi_pic_title, obj.title)
                        .setImageView(mContext, R.id.img_fragment_news_info_item_multi_pic_pic1, obj.imageurls.get(0).url)
                        .setImageView(mContext, R.id.img_fragment_news_info_item_multi_pic_pic2, obj.imageurls.get(1).url)
                        .setText(R.id.tv_fragment_news_info_item_multi_pic_source, obj.source)
                        .setText(R.id.tv_fragment_news_info_item_multi_pic_time, obj.pubDate);
                if(obj.imageurls.size() >= 3){
                    multiPicHolder.getView(R.id.img_fragment_news_info_item_multi_pic_pic3).setVisibility(View.VISIBLE);
                    multiPicHolder.setImageView(mContext, R.id.img_fragment_news_info_item_multi_pic_pic3, obj.imageurls.get(2).url);
                }else{
                    multiPicHolder.getView(R.id.img_fragment_news_info_item_multi_pic_pic3).setVisibility(View.GONE);
                }
                return multiPicHolder.getConvertView();
            case TYPE_NO_PIC:
                CommonViewHolder noPicHolder = CommonViewHolder.getViewHolder(mContext, convertView, R.layout.fragment_news_info_item_no_pic, null, position);
                noPicHolder.setText(R.id.tv_fragment_news_info_item_no_pic_title, obj.title)
                        .setText(R.id.tv_fragment_news_info_item_no_pic_source, obj.source)
                        .setText(R.id.tv_fragment_news_info_item_no_pic_time, obj.pubDate);
                return noPicHolder.getConvertView();
        }
        return null;
    }

    @Override
    public void setItemData(CommonViewHolder viewHolder, NewsDetailInfoObj obj) {

    }

}
