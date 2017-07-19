package com.example.cyy.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.cyy.weather.adapter.GalleryAdapter;
import com.example.cyy.weather.widget.CustomGallery;

/**
 * Created by cyy on 2016/10/9.
 * 星座页
 */
public class HoroscopeActivity extends Activity{
    private CustomGallery gallery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horoscope);
        initView();
    }

    private void initView() {
        gallery = (CustomGallery) findViewById(R.id.custom_gallery);
        int[] horoscopeArray = new int[]{R.mipmap.baiyang, R.mipmap.jinniu, R.mipmap.shuangzi, R.mipmap.juxie,
                R.mipmap.shizi, R.mipmap.chunv, R.mipmap.tianchen, R.mipmap.tianxie, R.mipmap.sheshou, R.mipmap.mojie,
                R.mipmap.shuiping, R.mipmap.shuangyu};
        final String horoscopeYwName[] = new String[]{"baiyang", "jinniu", "shuangzi", "juxie", "shizi", "chunv", "tiancheng",
                "tianxie", "sheshou", "mojie", "shuiping", "shuangyu"};
        final String horoscopeZwName[] = new String[]{"白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座",
        "天蝎座", "射手座", "摩羯座", "水瓶座", "双鱼座"};
        GalleryAdapter adapter = new GalleryAdapter(this, horoscopeArray);
        gallery.setAdapter(adapter);
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HoroscopeActivity.this, HoroscopeDetailActivity.class);
                intent.putExtra("star", horoscopeYwName[position]);
                intent.putExtra("name", horoscopeZwName[position]);
                startActivity(intent);
            }
        });
    }
}
