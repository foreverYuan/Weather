package com.example.cyy.weather.entity.resbody;

import com.example.cyy.weather.entity.obj.NewsChannelsObj;

import java.io.Serializable;

/**
 * Created by cyy on 2017/1/24.
 *
 * 新闻频道查询
 */
public class NewsChannelRes implements Serializable{
    public String showapi_res_code; //易源返回标志,0为成功，其他为失败
    public String showapi_res_error; //错误信息的展示
    public NewsChannelsObj showapi_res_body; //消息体的JSON封装，所有应用级的返回参数将嵌入此对象
}
