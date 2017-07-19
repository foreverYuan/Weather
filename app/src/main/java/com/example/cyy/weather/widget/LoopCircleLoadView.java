package com.example.cyy.weather.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.cyy.weather.R;
import com.example.cyy.weather.utils.Tools;

/**
 * Created by cyy on 2017/1/9.
 */
public class LoopCircleLoadView extends LinearLayout{
    private Context context;
    View view;
    public LoopCircleLoadView(Context context) {
        super(context);
        this.context = context;
    }

    public LoopCircleLoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        view = (LoopCircleLoadView) LayoutInflater.from(context).inflate(R.layout.load_animation_layout, null);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        LinearLayout.LayoutParams params = new LayoutParams(Tools.dip2px(context, 10), Tools.dip2px(context, 10));
        ImageView img = new ImageView(context);
        img.setLayoutParams(params);
        img.setBackgroundResource(R.drawable.load_circle_darker_gray);
        this.setOrientation(HORIZONTAL);
        if(this.getChildCount() > 0) {
            this.removeAllViews();
        }
        for (int i = 0; i < 5; i++){
            this.addView(img);
        }

    }
}
