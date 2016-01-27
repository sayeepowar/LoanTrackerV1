package com.sayaanand.loantrackerv1.emi.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Nandkishore.Powar on 30/12/2015.
 */
public class EMIDetails implements Serializable{

    private Date emiDate;
    private Double principal;
    private Double interest;
    private Double tillDatePrincipal;
    private Double tillDateInterest;
    private Double tillDateTotal;

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Double getTotal() {
        return this.principal + this.interest;
    }

    public Date getEmiDate() {
        return emiDate;
    }

    public void setEmiDate(Date emiDate) {
        this.emiDate = emiDate;
    }

    public Double getTillDatePrincipal() {
        return tillDatePrincipal;
    }

    public void setTillDatePrincipal(Double tillDatePrincipal) {
        this.tillDatePrincipal = tillDatePrincipal;
    }

    public Double getTillDateInterest() {
        return tillDateInterest;
    }

    public void setTillDateInterest(Double tillDateInterest) {
        this.tillDateInterest = tillDateInterest;
    }

    public Double getTillDateTotal() {
        return tillDateTotal;
    }

    public void setTillDateTotal(Double tillDateTotal) {
        this.tillDateTotal = tillDateTotal;
    }
}
