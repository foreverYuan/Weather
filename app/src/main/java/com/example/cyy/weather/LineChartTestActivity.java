package com.example.cyy.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.cyy.weather.widget.CustomLineChart;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cyy on 2017/1/10.
 */
public class LineChartTestActivity extends AppCompatActivity{
    private CustomLineChart line_chart_view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart_test);
        initView();
    }

    private void initView() {
        line_chart_view = (CustomLineChart) findViewById(R.id.line_chart_view);
        String[] xAxis = {"a", "b", "c", "d", "e"};
        line_chart_view.setXAxis(xAxis);
        String[] yAxis = {"10", "20", "30", "40", "50"};
        line_chart_view.setYAxis(yAxis);
        HashMap pointDataMap = new HashMap();
        for (int i = 0; i < xAxis.length; i++){
            pointDataMap.put(i, (int) (Math.random() * 5));
        }
        line_chart_view.setData(pointDataMap);
    }
}
