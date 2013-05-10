package com.cubead.jinjili.domain.model;

public class KeywordRank {
    private long keywordID;
    private long adGroupID;
    private int leftPercent;
    private double leftAvgRank;
    private double rightAvgRank;
    public long getKeywordID() {
        return keywordID;
    }
    public void setKeywordID(long keywordID) {
        this.keywordID = keywordID;
    }
    public long getAdGroupID() {
        return adGroupID;
    }
    public void setAdGroupID(long adGroupID) {
        this.adGroupID = adGroupID;
    }
    public int getLeftPercent() {
        return leftPercent;
    }
    public void setLeftPercent(int leftPercent) {
        this.leftPercent = leftPercent;
    }
    public double getLeftAvgRank() {
        return leftAvgRank;
    }
    public void setLeftAvgRank(double leftAvgRank) {
        this.leftAvgRank = leftAvgRank;
    }
    public double getRightAvgRank() {
        return rightAvgRank;
    }
    public void setRightAvgRank(double rightAvgRank) {
        this.rightAvgRank = rightAvgRank;
    }
    
    
}
