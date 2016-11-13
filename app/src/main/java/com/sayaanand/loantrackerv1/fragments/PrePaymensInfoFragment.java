package com.sayaanand.loantrackerv1.fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.sayaanand.loantrackerv1.R;
import com.sayaanand.loantrackerv1.db.LoanTrackerDBHelper;
import com.sayaanand.loantrackerv1.vo.LoanInfo;
import com.sayaanand.loantrackerv1.vo.PrePaymentInfo;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrePaymensInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrePaymensInfoFragment extends DialogFragment implements View.OnClickListener  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "loanInfo";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private LoanInfo loanInfo;
    private String mParam2;
    private LoanTrackerDBHelper dbHelper;
    private LoanDetailsFragment.LoanInfoUpdateListener loanInfoUpdateListener;


    public PrePaymensInfoFragment() {
        // Required empty public constructor
    }

    public void setLoanInfoUpdateListener(LoanDetailsFragment.LoanInfoUpdateListener loanInfoUpdateListener) {
        this.loanInfoUpdateListener = loanInfoUpdateListener;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param loanInfo Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrePaymensInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrePaymensInfoFragment newInstance(LoanInfo loanInfo, String param2) {
        PrePaymensInfoFragment fragment = new PrePaymensInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, loanInfo);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            loanInfo = (LoanInfo)getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Add Pre Payment Details ");
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_pre_paymens_info, container, false);
        Button addButton = (Button)view.findViewById(R.id.button_add_prepayment);
        addButton.setOnClickListener(this);
        Button cancelButton = (Button)view.findViewById(R.id.button_cancel_prepayment);
        cancelButton.setOnClickListener(this);
        dbHelper = new LoanTrackerDBHelper(getActivity());

        return  view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add_prepayment:
                saveEntry();
                dismiss();
                break;
            case R.id.button_cancel_prepayment:
                dismiss();
                break;
        }
    }

    public void saveEntry() {
        EditText amountText = (EditText)getView().findViewById(R.id.text_input_amount_prepayment);
        EditText commentText = (EditText)getView().findViewById(R.id.text_input_prepayment_comments);
        DatePicker datePicker = (DatePicker)getView().findViewById(R.id.dp_input_prepayment_date);

        PrePaymentInfo prePaymentInfo = new PrePaymentInfo();
        prePaymentInfo.setLoanId(loanInfo.getId());
        prePaymentInfo.setAmount(Float.parseFloat(amountText.getText().toString()));
        prePaymentInfo.setComments(commentText.getText().toString());
        prePaymentInfo.setDate(getDateFromDatePicker(datePicker));

        dbHelper.insert(prePaymentInfo);
        this.loanInfoUpdateListener.onUpdate();
    }

    /**
     *
     * @param datePicker
     * @return a java.util.Date
     */
    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}
