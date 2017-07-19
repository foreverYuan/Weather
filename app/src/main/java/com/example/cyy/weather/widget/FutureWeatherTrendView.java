package com.example.cyy.weather.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import com.example.cyy.weather.utils.Tools;

/**
 * Created by cyy on 2017/1/11.
 *
 * //未来天气趋势的一个自定义view
 */
public class FutureWeatherTrendView extends View {
    //为当前view指定一个默认最小宽度和默认最小高度
    private int mDefLeastWidth = 80, mDefLeastHeight = 150;
    //上下文对象
    private Context mContext;
    //分别代表画点的画笔，画文本的画笔和画线的画笔
    private Paint mCirclePaint, mTextPaint, mLinePaint;
    //字体大小
    private int mPaintTextSize = 16;
    //画的点的半径
    private int mCircleRadius = 3;
    //包含有字体度量信息的对象
    private Paint.FontMetrics mFontMetrics;
    //低温的左，中，右和高温的左，中，右，中间的温度为当天的温度，左边的温度是当天和上一天的中间值，
    // 右边的温度是当天和下一天的中间值
    private float mLowTemArray[], mHighTemArray[];
    //未来几天中温度的最低值和最高值
    private static float mLowestTem, mHighestTem;
    //未来几天中最低温度点的Y坐标
    private float mLowestTemY;
    //文本到点之间的间距
    private int mTextCircleDis = 3;
    //标记第一天和最后一天，如果type=0代表第一天，type=1代表最后一天，type=2代表第一天和最后一天之间的天
    private int mType;

    public FutureWeatherTrendView(Context context) {
        super(context);
    }

    public FutureWeatherTrendView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initPaint();
    }

    /**
     * 初始化各种画笔
     */
    private void initPaint(){
        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.YELLOW);

        mTextPaint = new Paint();
        mTextPaint.setTextSize(Tools.sp2px(mContext, mPaintTextSize));
        mTextPaint.setColor(Color.BLACK);
        mFontMetrics = mTextPaint.getFontMetrics();

        mLinePaint = new Paint();
        mLinePaint.setStrokeWidth(3);
        mLinePaint.setColor(Color.BLUE);
        //抗锯齿
        mLinePaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //view的宽度和高度
        int width, height;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        width = measureWidthHeight(widthMode, widthSize, 0);
        height = measureWidthHeight(heightMode, heightSize, 0);
        setMeasuredDimension(width, height);
    }

    /**
     * 测量view的宽度和高度
     * @param mode
     * @param size
     * @param tag 0表示宽度，1表示高度
     * @return
     */
    private int measureWidthHeight(int mode, int size, int tag) {
        int resultSize = 0;
        if(mode == MeasureSpec.EXACTLY){
            resultSize = size;
        }else{
            if(tag == 0){
                //view的宽度不能小于最小宽度
                resultSize = Tools.dip2px(mContext, mDefLeastWidth) + getPaddingLeft() + getPaddingRight();
            }else{
                //view的高度不能小于最小高度
                resultSize = Tools.dip2px(mContext, mDefLeastHeight) + getPaddingTop() + getPaddingBottom();
            }
            if(mode == MeasureSpec.AT_MOST){
                resultSize = Math.min(size, resultSize);
            }
        }
        return resultSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //低温Y坐标
        float lowTemTextY = getTemY(mLowTemArray[1]) + getExtraHeight();
        drawLowOrHigh(canvas, mLowTemArray, lowTemTextY, mType);
        drawLowOrHigh(canvas, mHighTemArray, getTextHeight(), mType);

    }

    /**
     * 画低温和高温的点，温度文本值以及趋势
     * @param canvas 画布
     * @param temArray 低温或者高温左中右温度集合
     * @param textY 低温或者高温文本的Y坐标
     */
    private void drawLowOrHigh(Canvas canvas, float temArray[], float textY, int type) {
        //第一步，画低温点，即中间的那个温度点(需要找到圆圈中心的坐标）
        //X坐标 中心
        int temX = getWidth() / 2;
        //Y坐标 中心
        float temY = getTemY(temArray[1]);
        //画点
        canvas.drawCircle(temX, temY, Tools.dip2px(mContext, mCircleRadius), mCirclePaint);
        //第二步，写低温下边的温度值（从文字左下角开始画，需要找到左下角的坐标）
        //X坐标
        float temTextX = getWidth() / 2 - mTextPaint.measureText(temArray[1] + "°") / 2;
        //Y坐标
        float temTextY = textY;
        //写文本
        canvas.drawText(temArray[1] + "°", temTextX, temTextY, mTextPaint);
        //第三步，得到低温左边和右边点的坐标
        int temLeftX = 0;
        float temLeftY = getTemY(temArray[0]);
        int temRightX = getWidth();
        float temRightY = getTemY(temArray[2]);
        //第四步，中间的温度点和左边以及右边的连线
        if(type == 0){
            canvas.drawLine(temX + Tools.dip2px(mContext, mCircleRadius), temY, temRightX, temRightY, mLinePaint);
        } else if(type == 1){
            canvas.drawLine(temLeftX, temLeftY, temX - Tools.dip2px(mContext, mCircleRadius), temY, mLinePaint);
        } else{
            canvas.drawLine(temLeftX, temLeftY, temX - Tools.dip2px(mContext, mCircleRadius), temY, mLinePaint);
            canvas.drawLine(temX + Tools.dip2px(mContext, mCircleRadius), temY, temRightX, temRightY, mLinePaint);
        }
    }

    /**
     * 获得文本所占的高度
     * @return
     */
    private float getTextHeight(){
        //文本的高度
        float textHeight = mFontMetrics.bottom - mFontMetrics.top;
        return textHeight;
    }

    /**
     * 文本高度加上文本到温度点的间距
     */
    private float getExtraHeight(){
        return getTextHeight() + Tools.dip2px(mContext, mTextCircleDis);
    }

    /**
     * 获得未来几天中最低温度所对应的点中心的Y坐标值
     */
    private float getLowestTemY(){
        //最低点的Y坐标值=整个view的高度减去底下的文本的高度以及文本与温度点之间的间距
        mLowestTemY = getHeight() - getExtraHeight();
        return mLowestTemY;
    }

    /**
     * 平均一温度所占据的高度值
     * @return
     */
    private float getPerTemHeight(){
        //最高温度和最低温度的高度差值
        float lowestHighestHeightDis = getHeight() - 2 * getExtraHeight();
        //最高温度和最低温度的差值
        float lowestHighestTemDis = mHighestTem - mLowestTem;
        SystemClock.elapsedRealtime();
        return lowestHighestHeightDis / lowestHighestTemDis;
    }

    /**
     * 获得tem温度所在点的纵坐标
     * @param tem
     */
    private float getTemY(float tem){
        float temY = getLowestTemY() - (tem - mLowestTem) * getPerTemHeight();
        return temY;
    }

    /**
     * 设置低温和低温左右的温度值
     * @param lowTemArray
     */
    public FutureWeatherTrendView setLowTemArray(float lowTemArray[]){
        this.mLowTemArray = lowTemArray;
        return this;
    }

    /**
     * 设置高温和高温左右的温度值
     * @param highTemArray
     */
    public FutureWeatherTrendView setHighTemArray(float highTemArray[]){
        this.mHighTemArray = highTemArray;
        return this;
    }

    public FutureWeatherTrendView setType(int type){
        this.mType = type;
        return this;
    }

    /**
     * 设置未来几天中最低的温度值
     * @param lowestTem
     */
    public static void setLowestTem(int lowestTem){
        mLowestTem = lowestTem;
    }

    /**
     * 设置未来几天中最高的温度值
     * @param highestTem
     */
    public static void setHighestTem(int highestTem){
        mHighestTem = highestTem;
    }

}
