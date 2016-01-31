package com.sayaanand.loantrackerv1.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sayaanand.loantrackerv1.R;
import com.sayaanand.loantrackerv1.db.LoanTrackerDBHelper;
import com.sayaanand.loantrackerv1.utils.LoggerUtils;
import com.sayaanand.loantrackerv1.utils.Utility;
import com.sayaanand.loantrackerv1.vo.LoanInfo;
import com.sayaanand.loantrackerv1.vo.PrePaymentInfo;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Nandkishore.Powar on 26/01/2016.
 */
public class LoanInfoLandingFragment extends Fragment implements View.OnClickListener,IPagerFragmentInfo {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "loanInfo";
    private static final String ARG_PARAM2 = "prePayments";
    private LoanTrackerDBHelper dbHelper;
    // TODO: Rename and change types of parameters
    private LoanInfo loanInfo;
    private List<PrePaymentInfo> prePayments;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
    private EditText editTextDate = null;
    private LoanDetailsFragment.LoanInfoUpdateListener loanInfoUpdateListener;
    public static final int INDEX = 0;
    public static String TITLE = "Loan Details";

    public LoanInfoLandingFragment() {
        // Required empty public constructor
    }

    public void setLoanInfoUpdateListener(LoanDetailsFragment.LoanInfoUpdateListener loanInfoUpdateListener) {
        this.loanInfoUpdateListener = loanInfoUpdateListener;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public Integer getIndex() {
        return INDEX;
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


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loan_landing, container, false);
        dbHelper = new LoanTrackerDBHelper(getActivity());
        AppCompatImageButton buttonEdite = (AppCompatImageButton)view.findViewById(R.id.button);
        buttonEdite.setOnClickListener(this);

        android.widget.Button buttonSave = (android.widget.Button)view.findViewById(R.id.button_save);
        buttonSave.setOnClickListener(this);

        android.widget.Button buttonReset = (android.widget.Button)view.findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(this);

        android.widget.Button buttonCalendar = (android.widget.Button)view.findViewById(R.id.button2);
        buttonCalendar.setOnClickListener(this);

        //setListAdapter(new PrepaymentsAdapter(getActivity(), prePayments));

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText textViewName = (EditText)view.findViewById(R.id.loan_text_info_name);
        textViewName.setText(loanInfo.getName());
        EditText editTextPrincipal = (EditText)view.findViewById(R.id.loan_text_info_principal);
        editTextPrincipal.setText(loanInfo.getPrincipal().toString());
        EditText editTextInterest = (EditText)view.findViewById(R.id.loan_text_info_interest);
        editTextInterest.setText(loanInfo.getInterst().toString());
        EditText editTextTenure = (EditText)view.findViewById(R.id.loan_text_info_tenure);
        editTextTenure.setText(loanInfo.getTenure().toString());
        editTextDate = (EditText)view.findViewById(R.id.loan_text_info_first_emit_date);
        editTextDate.setText(loanInfo.getEmiDateStr());

    }

    private void setDisplayDate(String date) {
        EditText tvDisplayDate = (EditText) getView().findViewById(R.id.loan_text_info_first_emit_date);
        tvDisplayDate.setText(date);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //setListAdapter(null);
    }

    @Override
    public void onClick(View v)  {
        switch (v.getId()) {
            case R.id.button_save :
                onSave();
                break;
            case R.id.button_reset:
                onReset();
                break;
            case R.id.button2:
                showDatePickerDialog();
                break;
            case R.id.button:
                enableForEdit();
                break;

        }
    }

    public void enableForEdit() {
        EditText textViewName = (EditText)getView().findViewById(R.id.loan_text_info_name);
        EditText editTextPrincipal = (EditText)getView().findViewById(R.id.loan_text_info_principal);
        EditText editTextInterest = (EditText)getView().findViewById(R.id.loan_text_info_interest);
        EditText editTextTenure = (EditText)getView().findViewById(R.id.loan_text_info_tenure);
        EditText editTextDate = (EditText)getView().findViewById(R.id.loan_text_info_first_emit_date);
        Button buttonSave = (Button)getView().findViewById(R.id.button_save);
        Button buttonReset = (Button)getView().findViewById(R.id.button_reset);
        Button buttonCalendar = (Button)getView().findViewById(R.id.button2);

        textViewName.setEnabled(true);
        editTextPrincipal.setEnabled(true);
        editTextInterest.setEnabled(true);
        editTextTenure.setEnabled(true);
        editTextDate.setEnabled(true);
        buttonSave.setEnabled(true);
        buttonReset.setEnabled(true);
        buttonCalendar.setEnabled(true);

    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new SelectDateFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        newFragment.show(transaction, "datePicker");
    }

    public void onSave() {
        android.util.Log.i("LC", "Inside onSave");
        EditText textViewName = (EditText)getView().findViewById(R.id.loan_text_info_name);
        EditText editTextPrincipal = (EditText)getView().findViewById(R.id.loan_text_info_principal);
        EditText editTextInterest = (EditText)getView().findViewById(R.id.loan_text_info_interest);
        EditText editTextTenure = (EditText)getView().findViewById(R.id.loan_text_info_tenure);
        EditText editTextDate = (EditText)getView().findViewById(R.id.loan_text_info_first_emit_date);

        Double p = Double.parseDouble(editTextPrincipal.getText().toString());
        Double n = Double.parseDouble(editTextTenure.getText().toString());
        Double r = Double.parseDouble(editTextInterest.getText().toString());

        int countBefore = dbHelper.numberOfRows();
        LoanInfo loanInfo = new LoanInfo();
        loanInfo.setId(this.loanInfo.getId());
        loanInfo.setName(textViewName.getText().toString());
        loanInfo.setType("O");
        loanInfo.setPrincipal(p);
        loanInfo.setInterst(r);
        loanInfo.setTenure(n);
        loanInfo.setEmiDateStr(editTextDate.getText().toString());
        dbHelper.update(loanInfo);
        LoggerUtils.logInfo("Details updated...");
        int countAfter = dbHelper.numberOfRows();

        LoggerUtils.logInfo("Before:"+countBefore+",After:"+countAfter);
        Utility.showToast(getActivity(), "Loan Details updated sucessfully!");

        this.loanInfoUpdateListener.onUpdate();

        android.util.Log.i("LC", "Inside onSave");
    }

    public void onReset() {
        android.util.Log.i("LC", "Inside onReset");

        EditText textViewName = (EditText)getView().findViewById(R.id.loan_text_info_name);
        textViewName.setText(loanInfo.getName());
        EditText editTextPrincipal = (EditText)getView().findViewById(R.id.loan_text_info_principal);
        editTextPrincipal.setText(loanInfo.getPrincipal().toString());
        EditText editTextInterest = (EditText)getView().findViewById(R.id.loan_text_info_interest);
        editTextInterest.setText(loanInfo.getInterst().toString());
        EditText editTextTenure = (EditText)getView().findViewById(R.id.loan_text_info_tenure);
        editTextTenure.setText(loanInfo.getTenure().toString());
        EditText editTextDate = (EditText)getView().findViewById(R.id.loan_text_info_first_emit_date);
        editTextDate.setText(loanInfo.getEmiDateStr());

        textViewName.setEnabled(false);
        editTextPrincipal.setEnabled(false);
        editTextInterest.setEnabled(false);
        editTextTenure.setEnabled(false);
        editTextDate.setEnabled(false);

        Button buttonSave = (Button)getView().findViewById(R.id.button_save);
        Button buttonReset = (Button)getView().findViewById(R.id.button_reset);
        Button buttonCalendar = (Button)getView().findViewById(R.id.button2);
        buttonSave.setEnabled(false);
        buttonReset.setEnabled(false);
        buttonCalendar.setEnabled(false);
    }


    public class SelectDateFragment extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            String dateStr = editTextDate.getText().toString();
            try {
                Date date = sdf.parse(dateStr);
                calendar.setTime(date);
            } catch (Exception e) {}


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
