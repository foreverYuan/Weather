package com.example.cyy.weather.entity.obj;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cyy on 2017/1/29.
 */
public class NewsListPageObj implements Serializable{
    public int allPages;
    public int currentPage;
    public int allNum;
    public int maxResult;
    public ArrayList<NewsDetailInfoObj> contentlist;
}
