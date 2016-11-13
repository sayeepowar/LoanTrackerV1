package com.sayaanand.loantrackerv1.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Nandkishore.Powar on 24/01/2016.
 */
public class PrePaymentInfo implements Serializable {
    private static java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MMM-yyyy");
    private int id;
    private int loanId;
    private float amount;
    private Date date;
    private String comments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateStr() {
        try {
            return sdf.format(this.date);
        }catch (Exception e) {}
        return null;
    }

    public Date setDateStr(String date) {
        try {
            this.date = sdf.parse(date);
        }catch (Exception e) {}
        return null;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "PrePaymentInfo{" +
                "id=" + id +
                ", loanId=" + loanId +
                ", amount=" + amount +
                ", date=" + date +
                ", comments='" + comments + '\'' +
                '}';
    }
}
