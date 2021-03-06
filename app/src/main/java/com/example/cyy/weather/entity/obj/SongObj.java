package com.example.cyy.weather.entity.obj;

import java.io.Serializable;

/**
 * Created by cyy on 2017/1/16.
 */
public class SongObj implements Serializable{
    public String songname; //歌曲名称
    public String seconds; //歌曲时长
    public String albummid; //专辑名称
    public String songid; //歌曲id
    public String singerid; //歌手id
    public String albumpic_big; //专辑大图片，高宽300
    public String albumpic_small; //专辑小图片，高宽90
    public String downUrl; //mp3下载链接
    public String url; //流媒体地址
    public String singername; //歌手名
    public String albumid; //专辑id
}
