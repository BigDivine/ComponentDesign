package com.divine.yang.nmjc_main.mvp.beans;

/**
 * Author: Divine
 * CreateDate: 2021/1/13
 * Describe:
 */
public class BillBean {
    private String billTitle, billState, billDate, billJourneyNum, billAirCode;

    public String getBillTitle() {
        return billTitle;
    }

    public void setBillTitle(String billTitle) {
        this.billTitle = billTitle;
    }

    public String getBillState() {
        return billState;
    }

    public void setBillState(String billState) {
        this.billState = billState;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getBillJourneyNum() {
        return billJourneyNum;
    }

    public void setBillJourneyNum(String billJourneyNum) {
        this.billJourneyNum = billJourneyNum;
    }

    public String getBillAirCode() {
        return billAirCode;
    }

    public void setBillAirCode(String billAirCode) {
        this.billAirCode = billAirCode;
    }
}
