package com.example.cyy.weather.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cyy on 2017/1/10.
 *
 * 简单折线图
 */
public class CustomLineChart extends View {
    //自定义view的宽和高
    private int mWidth, mHeight;
    //X轴文字集合, Y轴文字集合
    private String mXAxis[], mYAxis[];
    //点的集合
    private HashMap<Integer, Integer> pointMap;

    public CustomLineChart(Context context) {
        super(context);
    }

    public CustomLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if(widthMode == MeasureSpec.EXACTLY){
            mWidth = width;
        }else{

        }
        if(heightMode == MeasureSpec.EXACTLY){
            mHeight = height;
        }else{

        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //初始化画笔
        Paint paint = new Paint();
        paint.setTextSize(30);
        paint.setColor(Color.parseColor("#0f0fe5"));
        //Y轴刻度集合
        int[] yScale = new int[mXAxis.length];
        //计算Y轴刻度间距
        int yGap = (mHeight - 90) / (mYAxis.length + 1);
        //画Y轴上的刻度值
        for (int i = 0; i < mYAxis.length; i++){
            canvas.drawText(mYAxis[i], 50, yGap * (i + 1), paint);
            yScale[i] = yGap * (i + 1);
        }

        //X轴刻度集合
        int[] xScale = new int[mYAxis.length];
        //计算X轴刻度间距
        int xGap = (int) ((mWidth - paint.measureText(mYAxis[0]) - 50) / (mXAxis.length + 1));
        //画X轴上的刻度值
        for (int i = 0; i < mXAxis.length; i++) {
            canvas.drawText(mXAxis[i], paint.measureText(mYAxis[0]) + 50 + xGap * (i + 1), yGap * (mYAxis.length + 1) + 10, paint);
            xScale[i] = (int) (paint.measureText(mYAxis[0]) + 50 + xGap * (i + 1));
        }

        //画点
        Paint pointPaint = new Paint();
        pointPaint.setColor(Color.parseColor("#ff0000"));
        Paint linePoint = new Paint();
        linePoint.setColor(Color.parseColor("#000000"));
        linePoint.setStrokeWidth(3);
        for (int i = 0; i < mXAxis.length; i++){
            canvas.drawCircle(xScale[i], yScale[pointMap.get(i)], 10, pointPaint);
            //连线
            if(i > 0) {
                canvas.drawLine(xScale[i - 1], yScale[pointMap.get(i - 1)], xScale[i], yScale[pointMap.get(i)], linePoint);
            }
        }
    }

    public void setXAxis(String[] xAxis){
        this.mXAxis = xAxis;
    }

    public void setYAxis(String[] yAxis){
        this.mYAxis = yAxis;
    }

    public void setData(HashMap map){
        this.pointMap = map;
    }
}