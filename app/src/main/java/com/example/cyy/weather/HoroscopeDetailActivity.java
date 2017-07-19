package com.example.cyy.weather;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.cyy.weather.entity.obj.HoroscopeLuckObj;
import com.example.cyy.weather.entity.obj.HoroscopeObj;
import com.example.cyy.weather.entity.resbody.HoroscopeRes;
import com.example.cyy.weather.utils.ConstantUtils;
import com.example.cyy.weather.utils.FastJsonUtils;
import com.example.cyy.weather.utils.OkhttpUtils;
import com.example.cyy.weather.utils.ViewUtils;
import com.example.cyy.weather.widget.CircleImageView;
import com.example.cyy.weather.widget.CircleLayout;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by cyy on 2016/10/10.
 *
 * 星座详情页
 */
public class HoroscopeDetailActivity extends Activity implements CircleLayout.OnItemSelectedListener{
    private String mStar;
    private String mName;
    private CircleLayout circleMenu;
    //星座, 标题，时间
    private TextView mTvName, mTvTitle, mTvTime;
    //综合指数，爱情指数，财富指数，工作指数
    private TextView mTvGeneralIndex, mTvLoveIndex, mTvMoneyIndex, mTvWorkIndex;
    //星星评分展示
    private RatingBar mRbGeneral, mRbLove, mRbMoney, mRbWork;
    //幸运linearlayout，星座linearlayuot
    private LinearLayout mLLLucky, mLLHoroscope;
    //幸运数字，幸运日期，幸运颜色，吉利方位，吉时
    private TextView mTvLuckyNum, mTvLuckyDate, mTvLuckyColor, mTvLuckyDerection, mTvLuckyTime;
    //贵人星座，缘分星座，小人星座
    private TextView mTvGrxz, mTvYfxz, mTvXrxz;
    //运势简评，爱情运势，财富运势，工作运势，健康运势
    private TextView mTvYsjp, mTvLoveLuck, mTvMoneyLuck, mTvWorkLuck, mTvHealthLuck;
    //运势内容
    private TextView mTvLuckContent;
    //提醒
    private TextView mTvNotice;
    //优势，弱势
    private TextView mTvMonthAdvantage, mTvMonthWeekness;
    private HoroscopeRes resbody;
    private HoroscopeObj horoscopeObj;
    private ArrayList<TextView> luckList = new ArrayList<TextView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horoscope_detail);
        initView();
        getIntentData();
        requestData();
    }

    private void initView() {
        circleMenu = (CircleLayout) findViewById(R.id.circle_layout);

        mTvName = (TextView) findViewById(R.id.tv_activity_horoscope_detail_name);
        mTvTitle = (TextView) findViewById(R.id.tv_activity_horoscope_detail_title);
        mTvTime = (TextView) findViewById(R.id.tv_activity_horoscope_detail_time);

//        mTvGeneralIndex = (TextView) findViewById(R.id.tv_activity_horoscope_detail_general_index);
//        mTvLoveIndex = (TextView) findViewById(R.id.tv_activity_horoscope_detail_love_index);
//        mTvMoneyIndex = (TextView) findViewById(R.id.tv_activity_horoscope_detail_money_index);
//        mTvWorkIndex = (TextView) findViewById(R.id.tv_activity_horoscope_detail_work_index);
        mRbGeneral = (RatingBar) findViewById(R.id.rb_activity_horoscope_detail_general);
        mRbLove = (RatingBar) findViewById(R.id.rb_activity_horoscope_detail_love);
        mRbMoney = (RatingBar) findViewById(R.id.rb_activity_horoscope_detail_money);
        mRbWork = (RatingBar) findViewById(R.id.rb_activity_horoscope_detail_work);

        mLLLucky = (LinearLayout) findViewById(R.id.ll_activity_horoscope_detail_lucky);
        mTvLuckyNum = (TextView) findViewById(R.id.tv_activity_horoscope_detail_lucky_num);
        mTvLuckyDate = (TextView) findViewById(R.id.tv_activity_horoscope_detail_lucky_date);
        mTvLuckyColor = (TextView) findViewById(R.id.tv_activity_horoscope_detail_lucky_color);
        mTvLuckyDerection = (TextView) findViewById(R.id.tv_activity_horoscope_detail_lucky_derection);
        mTvLuckyTime = (TextView) findViewById(R.id.tv_activity_horoscope_detail_lucky_time);

        mLLHoroscope = (LinearLayout) findViewById(R.id.ll_activity_horoscope_detail_horoscope);
        mTvGrxz = (TextView) findViewById(R.id.tv_activity_horoscope_detail_grxz);
        mTvYfxz = (TextView) findViewById(R.id.tv_activity_horoscope_detail_yfxz);
        mTvXrxz = (TextView) findViewById(R.id.tv_activity_horoscope_detail_xrxz);

        mTvYsjp = (TextView) findViewById(R.id.tv_activity_horoscope_detail_ysjp);
        mTvLoveLuck = (TextView) findViewById(R.id.tv_activity_horoscope_detail_love_luck);
        mTvMoneyLuck = (TextView) findViewById(R.id.tv_activity_horoscope_detail_money_luck);
        mTvWorkLuck = (TextView) findViewById(R.id.tv_activity_horoscope_detail_work_luck);
        mTvHealthLuck = (TextView) findViewById(R.id.tv_activity_horoscope_detail_health_luck);
        mTvLuckContent = (TextView) findViewById(R.id.tv_activity_horoscope_detail_luck_content);

        mTvNotice = (TextView) findViewById(R.id.tv_activity_horoscope_detail_notice);
        mTvMonthAdvantage = (TextView) findViewById(R.id.tv_activity_horoscope_detail_month_advantage);
        mTvMonthWeekness = (TextView) findViewById(R.id.tv_activity_horoscope_detail_month_weakness);

        mTvTitle.setText(((CircleImageView)circleMenu.getSelectedItem()).getName());
        circleMenu.setOnItemSelectedListener(this);
    }

    private void getIntentData(){
        mStar = getIntent().getStringExtra("star");
        mName = getIntent().getStringExtra("name");
    }

    public void requestData(){
        OkhttpUtils utils = new OkhttpUtils();
        utils.setOnResponseListener(new OkhttpUtils.ResponseListener() {
            @Override
            public void onSuccessAction(Response response) {
                try {
                    resbody = (HoroscopeRes) FastJsonUtils.analysisObject(response.body().string(), HoroscopeRes.class);
                    horoscopeObj = resbody.showapi_res_body;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Thread(){
                    @Override
                    public void run() {
                        if(horoscopeObj != null) {
                            setData(horoscopeObj.day, 0, horoscopeObj.day.day_notice);
                        }
                    }
                });
            }
        });
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("showapi_appid", ConstantUtils.SHOWAPI_APPID)
                .add("showapi_sign", ConstantUtils.SHOWAPI_SIGN)
                .add("star", mStar)
                .add("needTomorrow", "1")
                .add("needWeek", "1")
                .add("needMonth", "1")
                .add("needYear", "1");
        utils.requestByPost(ConstantUtils.HOROSCOPR_URL, builder);
    }

    @Override
    public void onItemSelected(View view, int position, long id, String name) {
        mTvTitle.setText(name);
        switch (position){
            case 0:
                setData(horoscopeObj.day, 0, horoscopeObj.day.day_notice);
                break;
            case 1:
                setData(horoscopeObj.tomorrow, 1, horoscopeObj.day.day_notice);
                break;
            case 2:
                setData(horoscopeObj.week, 2, horoscopeObj.week.week_notice);
                break;
            case 3:
                setData(horoscopeObj.month, 3, "");
                break;
            case 4:
                setData(horoscopeObj.year, 4, horoscopeObj.year.oneword);
                break;
        }
    }
    private void setData(HoroscopeLuckObj obj, int postion, String notice){
        if(obj != null) {
            mTvName.setText(mName);
            ViewUtils.showText(mTvTime, "", obj.time);

//        ViewUtils.showText(mTvGeneralIndex, "综合指数：", obj.summary_star);
//        ViewUtils.showText(mTvLoveIndex, "爱情指数：", obj.love_star);
//        ViewUtils.showText(mTvMoneyIndex, "财富指数：", obj.money_star);
//        ViewUtils.showText(mTvWorkIndex, "工作指数：", obj.work_star);
            mRbGeneral.setRating(postion == 4 ? Float.parseFloat(obj.general_index.split("分")[0]) / 20 : Float.parseFloat(obj.summary_star));
            mRbLove.setRating(postion == 4 ? Float.parseFloat(obj.love_index.split("分")[0]) / 20 : Float.parseFloat(obj.love_star));
            mRbMoney.setRating(postion == 4 ? Float.parseFloat(obj.money_index.split("分")[0]) / 20 : Float.parseFloat(obj.money_star));
            mRbWork.setRating(postion == 4 ? Float.parseFloat(obj.work_index.split("分")[0]) / 20 : Float.parseFloat(obj.work_star));

            ViewUtils.showText(mTvLuckyNum, "幸运数字：", obj.lucky_num);
            ViewUtils.showText(mTvLuckyDate, "幸运日期：", obj.lucky_day);
            ViewUtils.showText(mTvLuckyColor, "幸运颜色：", obj.lucky_color);
            ViewUtils.showText(mTvLuckyDerection, "吉利方位：", obj.lucky_direction);
            ViewUtils.showText(mTvLuckyTime, "吉时：", obj.lucky_time);

            ViewUtils.showText(mTvGrxz, "贵人星座：", obj.grxz);
            ViewUtils.showText(mTvYfxz, "缘分星座：", obj.yfxz);
            ViewUtils.showText(mTvXrxz, "小人星座：", obj.xrxz);

            setLuckTitleList(mTvYsjp, obj.general_txt);
            setLuckTitleList(mTvLoveLuck, obj.love_txt);
            setLuckTitleList(mTvMoneyLuck, obj.money_txt);
            setLuckTitleList(mTvWorkLuck, obj.work_txt);
            setLuckTitleList(mTvHealthLuck, obj.health_txt);
            //默认显示第一个
            mTvYsjp.setTextColor(Color.parseColor("#ff669900"));
            mTvLuckContent.setText(obj.general_txt);
            setLuckClick(mTvYsjp, obj.general_txt);
            setLuckClick(mTvLoveLuck, obj.love_txt);
            setLuckClick(mTvMoneyLuck, obj.money_txt);
            setLuckClick(mTvWorkLuck, obj.work_txt);
            setLuckClick(mTvHealthLuck, obj.health_txt);
            ViewUtils.showText(mTvNotice, postion == 4 ? "一句话简评：" : "提醒：", notice);
            ViewUtils.showText(mTvMonthAdvantage, "本月优势：", obj.month_advantage);
            ViewUtils.showText(mTvMonthWeekness, "本月弱势：", obj.month_weakness);
        }
    }

    private void setLuckTitleList(TextView tv, String str){
        if(!TextUtils.isEmpty(str)){
            tv.setVisibility(View.VISIBLE);
            luckList.add(tv);
        } else{
            tv.setVisibility(View.GONE);
            luckList.remove(tv);
        }
    }

    private void setLuckClick(final TextView tv, final String str){
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (TextView luckTv : luckList) {
                    luckTv.setTextColor(Color.GRAY);
                }
                tv.setTextColor(Color.parseColor("#ff669900"));
                mTvLuckContent.setText(str);
            }
        });
    }
}
