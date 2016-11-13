package com.sayaanand.loantrackerv1.emi;

import com.sayaanand.loantrackerv1.emi.vo.EMIDetails;
import com.sayaanand.loantrackerv1.utils.LoggerUtils;
import com.sayaanand.loantrackerv1.vo.PrePaymentInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Nandkishore.Powar on 29/12/2015.
 */
public class EMICalculator implements ICalculator {

    @Override
    public double calculate(double principal, double interest, double tenor) {
        LoggerUtils.logInfo("principal:" + principal);
        LoggerUtils.logInfo("interest:"+interest);
        LoggerUtils.logInfo("tenor:"+tenor);

        /**
         * EMI = [P x R x (1+R)^N]/[(1+R)^N-1],
         *              where P stands for the loan amount or principal,
         *                      R is the interest rate per month
         *                      [if the interest rate per annum is 11%, then the rate of interest will be 11/(12 x 100)],
         *                      and N is the number of monthly instalments.
         */
        double monthlyRest = interest / (12 * 100);
        double noOfINstallements = tenor * 12;
        double part1 = Math.pow(1 + monthlyRest, noOfINstallements);
        double part2 = part1 - 1;

        LoggerUtils.logInfo("monthlyRest:"+monthlyRest);
        LoggerUtils.logInfo("part1:"+part1);
        LoggerUtils.logInfo("part2:"+part2);
        double emi = principal * monthlyRest * part1 / part2 ;

        LoggerUtils.logInfo("emi:"+emi);


        return emi;
    }


    @Override
    public List<EMIDetails> getEMIDetails(double principal, double interest, double tenor, Date startDate, List<PrePaymentInfo> prePayments) {
        LoggerUtils.logInfo("principal:"+principal);
        LoggerUtils.logInfo("interest:"+interest);
        LoggerUtils.logInfo("tenor:"+tenor);
        LoggerUtils.logInfo("startDate:"+startDate);

        List<EMIDetails> results = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);

        TreeMap<Calendar,PrePaymentInfo> mapPrePayments = new TreeMap<>();
        if (prePayments!=null && !prePayments.isEmpty()) {
            for (PrePaymentInfo prePaymentInfo :
                    prePayments) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(prePaymentInfo.getDate());
                if (calendar.before(c)) { //ignore if it is before first emi.
                    continue;
                }
                mapPrePayments.put(calendar, prePaymentInfo);
            }
        }


        double outstaningAmount = principal;
        double monthlyRest = interest / (12 * 100);

        double tillDatePrincipal=0.0,tillDateInterest=0.0,tillDateTotal=0.0,tillDatePrePaymentTotal=0.0;

        boolean lastEMI = false;
        Double emi = calculate(principal, interest, tenor);
        outer:
        for (int i=1; i<= tenor ; i++) { // for all tenors
            for (int month = 1; month <=12 ; month++) { // for each month
                double prepayment = 0.0;
                //check if there is any prepayment
                for(Calendar prePaymentCal:mapPrePayments.keySet()) {
                    if (prePaymentCal.get(Calendar.YEAR) == c.get(Calendar.YEAR)
                            && prePaymentCal.get(Calendar.MONTH) == c.get(Calendar.MONTH)) { // current month
                        PrePaymentInfo prePaymentInfo = mapPrePayments.get(prePaymentCal);
                        prepayment += prePaymentInfo.getAmount();
                    }
                }

                tillDatePrePaymentTotal += prepayment;
                outstaningAmount -= prepayment;
                double simpleMonthInterest =  outstaningAmount * monthlyRest;

                if (outstaningAmount+simpleMonthInterest < emi) {
                    lastEMI = true;
                    emi = outstaningAmount+simpleMonthInterest;
                }


                EMIDetails emiDetails = new EMIDetails();
                emiDetails.setEmiDate(c.getTime());
                emiDetails.setPrincipal(emi - simpleMonthInterest);
                emiDetails.setInterest(simpleMonthInterest);
                emiDetails.setPrePayment(prepayment);

                results.add(emiDetails);
                //reduce interest
                outstaningAmount = outstaningAmount - emiDetails.getPrincipal();
                //advance date
                c.add(Calendar.MONTH, 1);

                tillDatePrincipal += emiDetails.getPrincipal();
                tillDatePrincipal +=prepayment;
                emiDetails.setTillDatePrincipal(tillDatePrincipal);

                tillDateInterest += emiDetails.getInterest();
                emiDetails.setTillDateInterest(tillDateInterest);

                tillDateTotal += emiDetails.getTotal();
                tillDateTotal += prepayment;
                emiDetails.setTillDateTotal(tillDateTotal);

                emiDetails.setTillDatePrePayment(tillDatePrePaymentTotal);
                emiDetails.setOutstanding(outstaningAmount*-1.0);

                if (lastEMI) {
                    break outer;
                }
            }
        }
        return results;
    }
}
