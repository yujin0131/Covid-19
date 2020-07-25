package com.lyj.project;

/**
 * 시도별 카운터 관련 Value Object 클래스
 */
public class CovidAreaCoronaData {

    String countryName;
    String death;
    String newCase;
    String newCcase;
    String newFcase;
    String percentage;
    String recovered;
    String totalCase;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getDeath() {
        return death;
    }

    public void setDeath(String death) {
        this.death = death;
    }

    public String getNewCase() {
        return newCase;
    }

    public void setNewCase(String newCase) {
        this.newCase = newCase;
    }

    public String getNewCcase() {
        return newCcase;
    }

    public void setNewCcase(String newCcase) {
        this.newCcase = newCcase;
    }

    public String getNewFcase() {
        return newFcase;
    }

    public void setNewFcase(String newFcase) {
        this.newFcase = newFcase;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getTotalCase() {
        return totalCase;
    }

    public void setTotalCase(String totalCase) {
        this.totalCase = totalCase;
    }
}
