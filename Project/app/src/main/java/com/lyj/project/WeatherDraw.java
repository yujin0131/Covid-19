package com.lyj.project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

public class WeatherDraw extends View {

    public WeatherDraw(Context context){
        super(context);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        final float ZERO = -90f; //이래야 위부터 시작

        if(WeatherActivity.w_type.equals("pm10")) {
            final float DOTONE = 3.6f; //한개당 각도 - 요놈때문에 type나눔 쫘증,,
            float score = Float.parseFloat(WeatherActivity.pm10value);

            float degree = score * DOTONE;

            //글자
            Paint p = new Paint();
            p.setTextSize(60);
            canvas.drawText("미세먼지", canvas.getWidth() / 2 - 110, 70, p);

            //그래프
            p.setAntiAlias(true);//부드럽게 윤곽
            p.setStyle(Paint.Style.STROKE);//윤곽선만할거니까
            p.setStrokeWidth(50);//윤곽두께 좀 더 올려도 될 듯,,멀ㄹ라
            p.setAlpha(0x00);//배경원 투명도

            //아닐때 부분
            p.setColor(Color.GRAY);
            RectF rectF = new RectF(canvas.getWidth() / 4, canvas.getHeight() / 5 + 20, canvas.getWidth() * 3 / 4, canvas.getHeight() * 3 / 4 + 25);
            canvas.drawArc(rectF, ZERO, -360 + degree, false, p);
            //해당 그래프
            p.setColor(Color.parseColor("#B2CCFF"));
            p.setStrokeCap(Paint.Cap.ROUND);
            canvas.drawArc(rectF, -90, degree, false, p);

            p.reset();//초기화

            //값
            p.setTextSize(80);
            canvas.drawText(WeatherActivity.pm10value, canvas.getWidth() / 2 - 40, canvas.getWidth() / 2 - 75, p);

            //등급
            p.setTextSize(45);
            canvas.drawText(WeatherActivity.pm10grade + "등급", canvas.getWidth() / 2 - 55, canvas.getWidth() / 2 - 17, p);

        }else if(WeatherActivity.w_type.equals("pm25")){
            final float DOTONE = 7f; //한개당 각도 - 요놈때문에 type나눔 쫘증,,
            float score = Float.parseFloat(WeatherActivity.pm25value);

            float degree = score * DOTONE;

            //글자
            Paint p = new Paint();
            p.setTextSize(60);
            canvas.drawText("초미세먼지", canvas.getWidth() / 2 - 140, 70, p);

            //그래프
            p.setAntiAlias(true);//부드럽게 윤곽
            p.setStyle(Paint.Style.STROKE);//윤곽선만할거니까
            p.setStrokeWidth(50);//윤곽두께 좀 더 올려도 될 듯,,멀ㄹ라
            p.setAlpha(0x00);//배경원 투명도

            //아닐때 부분
            p.setColor(Color.GRAY);
            RectF rectF = new RectF(canvas.getWidth() / 4, canvas.getHeight() / 5 + 20, canvas.getWidth() * 3 / 4, canvas.getHeight() * 3 / 4 + 25);
            canvas.drawArc(rectF, ZERO, -360 + degree, false, p);
            //해당 그래프
            p.setColor(Color.parseColor("#B2CCFF"));
            p.setStrokeCap(Paint.Cap.ROUND);
            canvas.drawArc(rectF, -90, degree, false, p);

            p.reset();//초기화

            //값
            p.setTextSize(80);
            canvas.drawText(WeatherActivity.pm25value, canvas.getWidth() / 2 - 40, canvas.getWidth() / 2 - 75, p);

            //등급
            p.setTextSize(45);
            canvas.drawText(WeatherActivity.pm25grade + "등급", canvas.getWidth() / 2 - 55, canvas.getWidth() / 2 - 17, p);

        }else if(WeatherActivity.w_type.equals("no2")){
            final float DOTONE = 20f; //한개당 각도 - 요놈때문에 type나눔 쫘증,,
            float score = Float.parseFloat(WeatherActivity.no2value)*100;

            float degree = score * DOTONE;

            //글자
            Paint p = new Paint();
            p.setTextSize(60);
            canvas.drawText("이산화질소", canvas.getWidth() / 2 - 140, 70, p);

            //그래프
            p.setAntiAlias(true);//부드럽게 윤곽
            p.setStyle(Paint.Style.STROKE);//윤곽선만할거니까
            p.setStrokeWidth(50);//윤곽두께 좀 더 올려도 될 듯,,멀ㄹ라
            p.setAlpha(0x00);//배경원 투명도

            //아닐때 부분
            p.setColor(Color.GRAY);
            RectF rectF = new RectF(canvas.getWidth() / 4, canvas.getHeight() / 5 + 20, canvas.getWidth() * 3 / 4, canvas.getHeight() * 3 / 4 + 25);
            canvas.drawArc(rectF, ZERO, -360 + degree, false, p);
            //해당 그래프
            p.setColor(Color.parseColor("#B2CCFF"));
            p.setStrokeCap(Paint.Cap.ROUND);
            canvas.drawArc(rectF, -90, degree, false, p);

            p.reset();//초기화

            //값
            p.setTextSize(80);
            canvas.drawText(WeatherActivity.no2value, canvas.getWidth() / 2 - 100, canvas.getWidth() / 2 - 75, p);

            //등급
            p.setTextSize(45);
            canvas.drawText(WeatherActivity.no2grade + "등급", canvas.getWidth() / 2 - 57, canvas.getWidth() / 2 - 17, p);

        }
        Log.i("pm", ""+WeatherActivity.pm10value);
    }
}
