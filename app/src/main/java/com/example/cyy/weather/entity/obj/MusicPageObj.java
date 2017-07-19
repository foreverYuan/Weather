package com.example.cyy.weather.entity.obj;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cyy on 2017/1/16.
 */
public class MusicPageObj implements Serializable{
    public ArrayList<SongObj> songlist;
    public String total_song_num;
    public String ret_code;
    public String update_time;
    public String color;
    public String cur_song_num;
    public String comment_num;
    public String currentPage;
    public String song_begin;
    public String totalpage;
}
