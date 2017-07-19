package com.example.cyy.weather.entity.obj;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cyy on 2017/1/24.
 *
 * 新闻频道的信息和频道列表
 */
public class NewsChannelsObj implements Serializable{
    public String totalNum; //总记录数
    public String ret_code; //
    public ArrayList<NewsChannelObj> channelList; //频道列表
}
