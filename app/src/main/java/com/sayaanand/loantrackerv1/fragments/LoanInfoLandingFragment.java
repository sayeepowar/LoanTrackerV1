package com.sayaanand.loantrackerv1.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.sayaanand.loantrackerv1.R;
import com.sayaanand.loantrackerv1.db.LoanTrackerDBHelper;
import com.sayaanand.loantrackerv1.emi.vo.EMIDetails;
import com.sayaanand.loantrackerv1.vo.LoanInfo;
import com.sayaanand.loantrackerv1.vo.PrePaymentInfo;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Nandkishore.Powar on 26/01/2016.
 */
public class LoanInfoLandingFragment extends ListFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "loanInfo";
    private static final String ARG_PARAM2 = "prePayments";
    private LoanTrackerDBHelper dbHelper;
    // TODO: Rename and change types of parameters
    private LoanInfo loanInfo;
    private List<PrePaymentInfo> prePayments;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");


    public LoanInfoLandingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param emiDetailsList Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CashFlowFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoanInfoLandingFragment newInstance(LoanInfo loanInfo, List<PrePaymentInfo> prePayments) {
        LoanInfoLandingFragment fragment = new LoanInfoLandingFragment();
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
        View view = inflater.inflate(R.layout.fragment_loan_landing, container, false);
        dbHelper = new LoanTrackerDBHelper(getActivity());
        android.widget.Button buttonEdite = (android.widget.Button)view.findViewById(R.id.button);
        buttonEdite.setOnClickListener(this);

        android.widget.Button buttonSave = (android.widget.Button)view.findViewById(R.id.button_save);
        buttonSave.setOnClickListener(this);

        android.widget.Button buttonClear = (android.widget.Button)view.findViewById(R.id.button_clear);
        buttonClear.setOnClickListener(this);

        android.widget.Button buttonCalendar = (android.widget.Button)view.findViewById(R.id.button2);
        buttonCalendar.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textViewName = (TextView)view.findViewById(R.id.loan_text_info_name);
        textViewName.setText(loanInfo.getName());
        EditText editTextPrincipal = (EditText)view.findViewById(R.id.loan_text_info_principal);
        editTextPrincipal.setText(loanInfo.getPrincipal().toString());
        EditText editTextInterest = (EditText)view.findViewById(R.id.loan_text_info_interest);
        editTextInterest.setText(loanInfo.getInterst().toString());
        EditText editTextTenure = (EditText)view.findViewById(R.id.loan_text_info_tenure);
        editTextTenure.setText(loanInfo.getTenure().toString());
        EditText editTextDate = (EditText)view.findViewById(R.id.loan_text_info_first_emit_date);
        editTextDate.setText(loanInfo.getEmiDateStr());
    }

    private void setDisplayDate(String date) {
        EditText tvDisplayDate = (EditText) getView().findViewById(R.id.loan_text_info_first_emit_date);
        tvDisplayDate.setText(date);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        setListAdapter(null);
    }

    @Override
    public void onClick(View v)  {
        switch (v.getId()) {
            case R.id.button_save :
                onSave();
                break;
            case R.id.button_clear:
                onClear();
                break;
            case R.id.button2:
                showDatePickerDialog();
                break;

        }
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new SelectDateFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        newFragment.show(transaction, "datePicker");
    }

    public void onSave() {

        android.util.Log.i("LC", "Inside onSave");
    }

    public void onClear() {

        android.util.Log.i("LC", "Inside onClear");
    }

    public static class PrepaymentsAdapter extends BaseAdapter {
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

            mDateView = (TextView) view.findViewById(R.id.text_prepayment_date);
            mAmountView = (TextView) view.findViewById(R.id.text_prepayment_amount);
            mCommentsView = (TextView) view.findViewById(R.id.text_prepayment_comments);

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            DecimalFormat df = new DecimalFormat("#,###.##");

            PrePaymentInfo mItem = values.get(position);
            mDateView.setText(sdf.format(mItem.getDate()));
            mAmountView.setText(df.format(mItem.getAmount()).toString());
            mCommentsView.setText(mItem.getComments());
            return view;
        }
    }

    public class SelectDateFragment extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            int year = selectedYear;
            int month = selectedMonth;
            int day = selectedDay;

            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            // set selected date into textview
            setDisplayDate(sdf.format(c.getTime()));
        }
    }
}
