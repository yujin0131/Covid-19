package com.lyj.project;

/**
 * 국내 Total 카운터 관련 Value Object 클래스
 */
public class CovidTotalCoronaData {

    String TotalCase;
    String TotalRecovered;
    String TotalDeath;
    String NowCase;
    String recoveredPercentage;
    String deathPercentage;
    String checkingCounter;
    String checkingPercentage;
    String caseCount;
    String casePercentage;
    String notcaseCount;
    String notcasePercentage;
    String TotalChecking;
    String TodayRecovered;
    String TodayDeath;
    String TotalCaseBefore;
    String updateTime;

    public String getTotalCase() {
        return TotalCase;
    }

    public void setTotalCase(String totalCase) {
        TotalCase = totalCase;
    }

    public String getTotalRecovered() {
        return TotalRecovered;
    }

    public void setTotalRecovered(String totalRecovered) {
        TotalRecovered = totalRecovered;
    }

    public String getTotalDeath() {
        return TotalDeath;
    }

    public void setTotalDeath(String totalDeath) {
        TotalDeath = totalDeath;
    }

    public String getNowCase() {
        return NowCase;
    }

    public void setNowCase(String nowCase) {
        NowCase = nowCase;
    }

    public String getRecoveredPercentage() {
        return recoveredPercentage;
    }

    public void setRecoveredPercentage(String recoveredPercentage) {
        this.recoveredPercentage = recoveredPercentage;
    }

    public String getDeathPercentage() {
        return deathPercentage;
    }

    public void setDeathPercentage(String deathPercentage) {
        this.deathPercentage = deathPercentage;
    }

    public String getCheckingCounter() {
        return checkingCounter;
    }

    public void setCheckingCounter(String checkingCounter) {
        this.checkingCounter = checkingCounter;
    }

    public String getCheckingPercentage() {
        return checkingPercentage;
    }

    public void setCheckingPercentage(String checkingPercentage) {
        this.checkingPercentage = checkingPercentage;
    }

    public String getCaseCount() {
        return caseCount;
    }

    public void setCaseCount(String caseCount) {
        this.caseCount = caseCount;
    }

    public String getCasePercentage() {
        return casePercentage;
    }

    public void setCasePercentage(String casePercentage) {
        this.casePercentage = casePercentage;
    }

    public String getNotcaseCount() {
        return notcaseCount;
    }

    public void setNotcaseCount(String notcaseCount) {
        this.notcaseCount = notcaseCount;
    }

    public String getNotcasePercentage() {
        return notcasePercentage;
    }

    public void setNotcasePercentage(String notcasePercentage) {
        this.notcasePercentage = notcasePercentage;
    }

    public String getTotalChecking() {
        return TotalChecking;
    }

    public void setTotalChecking(String totalChecking) {
        TotalChecking = totalChecking;
    }

    public String getTodayRecovered() {
        return TodayRecovered;
    }

    public void setTodayRecovered(String todayRecovered) {
        TodayRecovered = todayRecovered;
    }

    public String getTodayDeath() {
        return TodayDeath;
    }

    public void setTodayDeath(String todayDeath) {
        TodayDeath = todayDeath;
    }

    public String getTotalCaseBefore() {
        return TotalCaseBefore;
    }

    public void setTotalCaseBefore(String totalCaseBefore) {
        TotalCaseBefore = totalCaseBefore;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
