package com.lyj.project;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 시/도별 감염현황 데이터 중 왼쪽 줄
 */
public class CovidLeftAreaItemView extends LinearLayout {
    private TextView tv_areaName, tv_totalCase, tv_newCase,
            tv_recovered, tv_death = null;

    public CovidLeftAreaItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CovidLeftAreaItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.covid_left_view_item_area,this,true);
    }

    public void setItemView(CovidAreaCoronaData data) {
        tv_areaName = (TextView)findViewById(R.id.tv_areaName);
        tv_totalCase = (TextView)findViewById(R.id.tv_totalCase);
        tv_newCase = (TextView)findViewById(R.id.tv_newCase);
        tv_recovered = (TextView)findViewById(R.id.tv_recovered);
        tv_death = (TextView)findViewById(R.id.tv_death);

        tv_areaName.setText(data.getCountryName());
        tv_totalCase.setText(data.getTotalCase());
        tv_newCase.setText(data.getNewCase());
        tv_recovered.setText(data.getRecovered());
        tv_death.setText(data.getDeath());
    }
}
