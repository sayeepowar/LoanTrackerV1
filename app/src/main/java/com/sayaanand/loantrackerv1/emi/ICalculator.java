package com.sayaanand.loantrackerv1.emi;

import com.sayaanand.loantrackerv1.emi.vo.EMIDetails;

import java.util.Date;
import java.util.List;

/**
 * Created by Nandkishore.Powar on 29/12/2015.
 */
public interface ICalculator {

    public double calculate(double principal, double interest, double tenor);
    public List<EMIDetails> getEMIDetails(double principal, double interest, double tenor, Date startDate);
}
