package com.sayaanand.loantrackerv1.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sayaanand.loantrackerv1.R;
import com.sayaanand.loantrackerv1.db.LoanTrackerDBHelper;
import com.sayaanand.loantrackerv1.utils.LoggerUtils;
import com.sayaanand.loantrackerv1.vo.LoanInfo;
import com.sayaanand.loantrackerv1.vo.PrePaymentInfo;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoanRepaymentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoanRepaymentsFragment extends ListFragment implements IPagerFragmentInfo, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "loanInfo";
    private static final String ARG_PARAM2 = "prePayments";
    private LoanTrackerDBHelper dbHelper;
    private LoanInfo loanInfo;
    private List<PrePaymentInfo> prePayments;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
    private LoanDetailsFragment.LoanInfoUpdateListener loanInfoUpdateListener;
    public static final int INDEX = 1;
    public static String TITLE = "Pre-Payments";


    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public Integer getIndex() {
        return INDEX;
    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public LoanRepaymentsFragment() {
        // Required empty public constructor
    }

    public void setLoanInfoUpdateListener(LoanDetailsFragment.LoanInfoUpdateListener loanInfoUpdateListener) {
        this.loanInfoUpdateListener = loanInfoUpdateListener;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoanRepaymentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoanRepaymentsFragment newInstance(LoanInfo loanInfo, List<PrePaymentInfo> prePayments) {
        LoanRepaymentsFragment fragment = new LoanRepaymentsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable) loanInfo);
        args.putSerializable(ARG_PARAM2, (Serializable) prePayments);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            loanInfo = (LoanInfo)getArguments().getSerializable(ARG_PARAM1);
            prePayments = (List<PrePaymentInfo>)getArguments().getSerializable(ARG_PARAM2);
        }
        setListAdapter(new PrepaymentsAdapter(getActivity(), prePayments));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View)inflater.inflate(R.layout.fragment_loan_repayments, container, false);
        dbHelper = new LoanTrackerDBHelper(getActivity());
        AppCompatImageButton button_add_prepayment = (AppCompatImageButton)view.findViewById(R.id.button_add_prepayment);
        button_add_prepayment.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v)  {
        switch (v.getId()) {
            case R.id.button_add_prepayment :
                onAddPrePayment();
                break;
        }
    }

    public void onAddPrePayment() {
        PrePaymensInfoFragment newFragment = PrePaymensInfoFragment.newInstance(loanInfo,null);
        newFragment.setLoanInfoUpdateListener(this.loanInfoUpdateListener);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        newFragment.show(transaction, "AddLoanRepayment");
    }

    public class PrepaymentsAdapter extends BaseAdapter {
        private final Context context;
        private final List<PrePaymentInfo> values;


        public PrepaymentsAdapter(Context context,List<PrePaymentInfo> values) {
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
                view = mInflater.inflate(R.layout.emi_prepayments_item, null);
            }

            TextView mDateView;
            TextView mAmountView;
            TextView mCommentsView;
            PrePaymentInfo mItem = values.get(position);

            mDateView = (TextView) view.findViewById(R.id.text_prepayment_date);
            mAmountView = (TextView) view.findViewById(R.id.text_prepayment_amount);
            mCommentsView = (TextView) view.findViewById(R.id.text_prepayment_comments);
            AppCompatImageButton button_delete = (AppCompatImageButton) view.findViewById(R.id.button_delete_prepayment);
            button_delete.setOnClickListener(new ActionListener(mItem));

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            DecimalFormat df = new DecimalFormat("#,###.##");


            mDateView.setText(sdf.format(mItem.getDate()));
            mAmountView.setText(df.format(mItem.getAmount()).toString());
            mCommentsView.setText(mItem.getComments());
            return view;
        }
    }

    public class ActionListener implements View.OnClickListener {
        PrePaymentInfo prePaymentInfo;
        public ActionListener(PrePaymentInfo prePaymentInfo) {
            this.prePaymentInfo = prePaymentInfo;
        }

        @Override
        public void onClick(View v) {
            LoggerUtils.logInfo("On Delete called for " + prePaymentInfo);
            dbHelper.delete(prePaymentInfo);
            LoggerUtils.logInfo("No of records in db:" + dbHelper.numberOfRowsPrePayment());
            loanInfoUpdateListener.onUpdate();
        }
    }
}
