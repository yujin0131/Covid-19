package com.lyj.project;

public class WeatherVO {
//pm10 : 미세먼지 pm2.5 : 초미세먼지 no2 : 이산화질소 khai : 통합대기환경수치
    private String w_dataTime, w_pm10, w_pm25, w_no2, w_khai, w_pm10value, w_pm25value, w_no2value;

    public String getW_dataTime() {
        return w_dataTime;
    }

    public String getW_pm10value() {
        return w_pm10value;
    }

    public void setW_pm10value(String w_pm10value) {
        this.w_pm10value = w_pm10value;
    }

    public String getW_pm25value() {
        return w_pm25value;
    }

    public void setW_pm25value(String w_pm25value) {
        this.w_pm25value = w_pm25value;
    }

    public String getW_no2value() {
        return w_no2value;
    }

    public void setW_no2value(String w_no2value) {
        this.w_no2value = w_no2value;
    }

    public void setW_dataTime(String w_dataTime) {
        this.w_dataTime = w_dataTime;
    }

    public String getW_pm10() {
        return w_pm10;
    }

    public void setW_pm10(String w_pm10) {
        this.w_pm10 = w_pm10;
    }

    public String getW_pm25() {
        return w_pm25;
    }

    public void setW_pm25(String w_pm25) {
        this.w_pm25 = w_pm25;
    }

    public String getW_no2() {
        return w_no2;
    }

    public void setW_no2(String w_no2) {
        this.w_no2 = w_no2;
    }

    public String getW_khai() {
        return w_khai;
    }

    public void setW_khai(String w_khai) {
        this.w_khai = w_khai;
    }
}
