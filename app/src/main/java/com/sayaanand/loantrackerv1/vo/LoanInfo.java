package com.sayaanand.loantrackerv1.vo;

import java.util.Date;
/**
 * Created by Nandkishore.Powar on 16/01/2016.
 */
public class LoanInfo {

    private static java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MMM-yyyy");
    private int id;
    private String name;
    private String type="O";
    private Double principal;
    private Double interst;
    private Double tenure;
    private Date emiDate;

    public LoanInfo(){}

    public LoanInfo(java.lang.String name, java.lang.String type, java.lang.Double principal, java.lang.Double interst, java.lang.Double tenure, Date emiDate) {
        this.name = name;
        this.type = type;
        this.principal = principal;
        this.interst = interst;
        this.tenure = tenure;
        this.emiDate = emiDate;
    }

    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public java.lang.String getType() {
        return type;
    }

    public void setType(java.lang.String type) {
        this.type = type;
    }

    public java.lang.Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(java.lang.Double principal) {
        this.principal = principal;
    }

    public java.lang.Double getInterst() {
        return interst;
    }

    public void setInterst(java.lang.Double interst) {
        this.interst = interst;
    }

    public java.lang.Double getTenure() {
        return tenure;
    }

    public void setTenure(java.lang.Double tenure) {
        this.tenure = tenure;
    }

    public Date getEmiDate() {
        return emiDate;
    }

    public void setEmiDate(Date emiDate) {
        this.emiDate = emiDate;
    }

    public String getEmiDateStr() {
        try {
            return sdf.format(this.emiDate);
        }catch (Exception e) {}
        return null;
    }

    public Date setEmiDateStr(String emiDate) {
        try {
            this.emiDate = sdf.parse(emiDate);
        }catch (Exception e) {}
        return null;
    }

}
