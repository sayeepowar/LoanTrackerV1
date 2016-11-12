package com.sayaanand.loantrackerv1.emi.vo;

import java.util.Date;

/**
 * Created by Nandkishore.Powar on 30/12/2015.
 */
public class EMIDetails {

    private Date emiDate;
    private Double principal;
    private Double interest;

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
}
