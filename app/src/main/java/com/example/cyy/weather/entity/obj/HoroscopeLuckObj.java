package com.example.cyy.weather.entity.obj;

import java.io.Serializable;

/**
 * Created by cyy on 2017/1/14.
 *
 * 星座今日，明日，本周，本月的运势实体类
 */
public class HoroscopeLuckObj implements Serializable{
    public String summary_star; //综合指数，最高5分
    public String love_star; //爱情指数，最高5分
    public String money_star; //财富指数，最高5分
    public String work_star; //工作指数，最高5分
    public String general_index; //综合指数，最高100分
    public String love_index; //爱情指数，最高100分
    public String money_index; //财富指数，最高100分
    public String work_index; //工作指数，最高100分
    public String grxz; //贵人星座
    public String lucky_num; //幸运数字  day week
    public String lucky_time; //吉时  day week
    public String lucky_day; //幸运日期   week
    public String lucky_direction; //吉利方位
    public String lucky_color; //吉色   day week
    public String love_txt; //爱情运势
    public String work_txt; //工作运势
    public String money_txt; //财富运势
    public String general_txt; //运势简评
    public String health_txt; //健康运势   week
    public String time; //时间
    public String day_notice; //今日/明日提醒   day
    public String week_notice; //本周提醒   week
    public String oneword; //一句话简评
    public String yfxz; //缘份星座   month
    public String xrxz; //小人星座   week month
    public String month_advantage; //本月优势
    public String month_weakness; //本月弱势
}
