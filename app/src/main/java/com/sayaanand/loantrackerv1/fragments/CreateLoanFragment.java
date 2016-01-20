package com.sayaanand.loantrackerv1.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.sayaanand.loantrackerv1.R;
import com.sayaanand.loantrackerv1.db.LoanTrackerDBHelper;
import com.sayaanand.loantrackerv1.utils.LoggerUtils;
import com.sayaanand.loantrackerv1.vo.LoanInfo;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateLoanFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateLoanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateLoanFragment extends Fragment implements android.view.View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private LoanTrackerDBHelper dbHelper;
    private  int year;
    private int month;
    private int day;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
    static final int DATE_DIALOG_ID = 999;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CreateLoanFragment() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateLoanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateLoanFragment newInstance(String param1, String param2) {
        CreateLoanFragment fragment = new CreateLoanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_loan, container, false);
        android.widget.Button buttonSave = (android.widget.Button)view.findViewById(R.id.button_save);
        buttonSave.setOnClickListener(this);

        android.widget.Button buttonClear = (android.widget.Button)view.findViewById(R.id.button_clear);
        buttonClear.setOnClickListener(this);

        android.widget.Button buttonCalendar = (android.widget.Button)view.findViewById(R.id.button_calendar);
        buttonCalendar.setOnClickListener(this);

        dbHelper = new LoanTrackerDBHelper(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setCurrentDateOnView();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
            case R.id.button_calendar:
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
        EditText name = (EditText)getView().findViewById(R.id.name_ip_val);
        EditText principal = (EditText)getView().findViewById(R.id.principal_ip_num);
        EditText interest = (EditText)getView().findViewById(R.id.interst_ip_num);
        EditText tenor = (EditText)getView().findViewById(R.id.tenor_ip_num);
        EditText emiDate = (EditText)getView().findViewById(R.id.emidate_ip_val);


        Double p = Double.parseDouble(principal.getText().toString());
        Double n = Double.parseDouble(tenor.getText().toString());
        Double r = Double.parseDouble(interest.getText().toString());

        int countBefore = dbHelper.numberOfRows();
        LoanInfo loanInfo = new LoanInfo();
        loanInfo.setName(name.getText().toString());
        loanInfo.setType("O");
        loanInfo.setPrincipal(p);
        loanInfo.setInterst(r);
        loanInfo.setTenure(n);
        loanInfo.setEmiDateStr(emiDate.getText().toString());
        dbHelper.insert(loanInfo);
        LoggerUtils.logInfo("Details saved...");
        int countAfter = dbHelper.numberOfRows();

        LoggerUtils.logInfo("Before:"+countBefore+",After:"+countAfter);

    }

    public void onClear() {
        android.util.Log.i("LC", "Inside onCleare");
    }

    private void setCurrentDateOnView() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        String date = sdf.format(c.getTime());
        // set current date into textview
        setDisplayDate(date);
        // set current date into datepicker
        setDatePickerDate(year, month, day);
    }

    private void setDisplayDate(String date) {
        EditText tvDisplayDate = (EditText) getView().findViewById(R.id.emidate_ip_val);
        tvDisplayDate.setText(date);
    }
    private void setDatePickerDate(int year, int month, int day) {
        android.widget.DatePicker dpResult = (DatePicker) getView().findViewById(R.id.dpResult);
        dpResult.init(year, month, day, null);
    }

    class SelectDateFragment extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener {

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
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            // set selected date into textview
            setDisplayDate(sdf.format(c.getTime()));

            // set selected date into datepicker also
            setDatePickerDate(year, month, day);
        }
    }
}
