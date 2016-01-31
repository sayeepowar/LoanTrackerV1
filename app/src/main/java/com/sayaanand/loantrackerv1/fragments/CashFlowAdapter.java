package com.sayaanand.loantrackerv1.fragments;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sayaanand.loantrackerv1.R;
import com.sayaanand.loantrackerv1.db.LoanTrackerDBContract;
import com.sayaanand.loantrackerv1.emi.vo.EMIDetails;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Nandkishore.Powar on 24/01/2016.
 */
public class CashFlowAdapter extends BaseAdapter {

    private final Context context;
    private final List<EMIDetails> values;


    public CashFlowAdapter(Context context,List<EMIDetails> values) {
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.size();
    }
    @Override
    public Object getItem(int position) {
        return values.get(position);
    }
    @Override
    public long getItemId(int position) {
        return values.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.emi_list_item, null);
        }

        TextView mIdView;
        TextView mNameView;
        TextView mTypeView;

        mIdView = (TextView) view.findViewById(R.id.text_emi_date);
        mNameView = (TextView) view.findViewById(R.id.text_emi_prinicipal);
        mTypeView = (TextView) view.findViewById(R.id.text_emi_interest);
        TextView mTotalView = (TextView) view.findViewById(R.id.text_emi_total);
        TextView mTotalPrincipalView = (TextView) view.findViewById(R.id.text_emi_total_prinicipal);
        TextView mTotalInterestView = (TextView) view.findViewById(R.id.text_emi_total_interest);
        TextView mPrePaymentView = (TextView) view.findViewById(R.id.text_emi_prepayment);
        TextView mTotalPrePaymentView = (TextView) view.findViewById(R.id.text_emi_total_prepayment);
        TextView mTotalOutStandingView = (TextView) view.findViewById(R.id.text_emi_outstanding);


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        DecimalFormat df = new DecimalFormat("#,###.##");

        EMIDetails mItem = values.get(position);
        mIdView.setText(sdf.format(mItem.getEmiDate()));
        mNameView.setText(df.format(mItem.getPrincipal()).toString());
        mTypeView.setText(df.format(mItem.getInterest()).toString());
        mPrePaymentView.setText(df.format(mItem.getPrePayment()));
        mTotalPrincipalView.setText(df.format(mItem.getTillDatePrincipal()));
        mTotalInterestView.setText(df.format(mItem.getTillDateInterest()));
        mTotalPrePaymentView.setText(df.format(mItem.getTillDatePrePayment()));
        mTotalView.setText(df.format(mItem.getTillDateTotal()));
        mTotalOutStandingView.setText(df.format(mItem.getOutstanding()));
        return view;
    }

}
