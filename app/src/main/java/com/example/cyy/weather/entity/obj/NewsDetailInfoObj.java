package com.example.cyy.weather.entity.obj;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cyy on 2017/1/29.
 */
public class NewsDetailInfoObj implements Serializable{
    public String pubDate;
    public boolean havePic;
    public String title;
    public String channelName;
    public ArrayList<ImageUrlObj> imageurls;
    public String desc;
    public String source;
    public String channelId;
    public String link;

    public class ImageUrlObj implements Serializable{
        public String height;
        public String width;
        public String url;
    }
}
