package com.sayaanand.loantrackerv1.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.sayaanand.loantrackerv1.R;
import com.sayaanand.loantrackerv1.db.LoanTrackerDBHelper;
import com.sayaanand.loantrackerv1.emi.EMICalculator;
import com.sayaanand.loantrackerv1.emi.ICalculator;
import com.sayaanand.loantrackerv1.emi.vo.EMIDetails;
import com.sayaanand.loantrackerv1.vo.LoanInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoanDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoanDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoanDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "loanId";
    private static final String ARG_PARAM2 = "param2";
    private LoanTrackerDBHelper dbHelper;
    private ICalculator calculator = new EMICalculator();

    // TODO: Rename and change types of parameters
    private Integer loanId;
    private String mParam2;
    private static LoanInfo loanInfo;
    private static List<EMIDetails> emiDetails;

    private LoanDetailsPagerAdapter loanDetailsPagerAdapter;

    private OnFragmentInteractionListener mListener;

    public LoanDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoanDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoanDetailsFragment newInstance(Integer loanId, String param2) {
        LoanDetailsFragment fragment = new LoanDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, loanId);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            loanId = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loan_details, container, false);

        loanDetailsPagerAdapter = new LoanDetailsPagerAdapter(getActivity().getSupportFragmentManager());
        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setAdapter(loanDetailsPagerAdapter);

        dbHelper = new LoanTrackerDBHelper(getActivity());
        loanInfo = dbHelper.select(loanId);
        emiDetails = calculator.getEMIDetails(loanInfo.getPrincipal()
                , loanInfo.getInterst()
                , loanInfo.getTenure()
                , loanInfo.getEmiDate());
        TextView textView = (TextView)view.findViewById(R.id.text_emi_val);
        textView.setText(emiDetails.get(0).getTotal().toString());
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteractionLoanDetails(uri);
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
        void onFragmentInteractionLoanDetails(Uri uri);
    }

    public static class LoanDetailsPagerAdapter extends FragmentStatePagerAdapter {

        public LoanDetailsPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Fragment lineChartFragment = new LineChartFragment();
                    Bundle lineArgs = new Bundle();
                    lineChartFragment.setArguments(lineArgs);
                    return lineChartFragment;
                case 1:
                    Fragment barChartFragment = new BarChartFragment();
                    Bundle barArgs = new Bundle();
                    barChartFragment.setArguments(barArgs);
                    return barChartFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: return "Line Chart";
                case 1: return "Bar Chart";
            }
            return "OBJECT " + (position + 1);
        }
    }

    public static class LineChartFragment extends Fragment {
        //List<EMIDetails> emiDetails;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_linechart, container, false);
            Bundle args = getArguments();
            LineChart lineChart = (LineChart) rootView.findViewById(R.id.lineChart);
            LineData lineData = new LineData(getXAxisValues(emiDetails), getLineDataSet(emiDetails));
            lineChart.setData(lineData);
            lineChart.setDescription("EMI");
            lineChart.animateXY(2000, 2000);
            lineChart.invalidate();
            return rootView;
        }
    }

    public static class BarChartFragment extends Fragment {
        //List<EMIDetails> emiDetails;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_barchart, container, false);
            Bundle args = getArguments();
            BarChart chart = (BarChart) rootView.findViewById(R.id.sampleBarChart);
            BarData data = new BarData(getXAxisValues(emiDetails), getDataSet(emiDetails));
            chart.setData(data);
            chart.setDescription("EMI");
            chart.animateXY(2000, 2000);
            chart.invalidate();
            return rootView;
        }
    }

    private static ArrayList<LineDataSet> getLineDataSet(List<EMIDetails> emiDetails) {
        ArrayList<LineDataSet> dataSets = null;

        ArrayList<Entry> valueSet1 = new ArrayList<>();
        ArrayList<Entry> valueSet2 = new ArrayList<>();
        ArrayList<Entry> valueSet3 = new ArrayList<>();

        int counter = 0;
        float totalPrincipal = 0.0f, totalInterest = 0.0f, totalTotal = 0.0f;
        for (EMIDetails emiDetail : emiDetails) {
            totalPrincipal += emiDetail.getPrincipal().floatValue();
            totalInterest += emiDetail.getInterest().floatValue();
            totalTotal += emiDetail.getTotal().floatValue();

            Entry principal = new Entry(totalPrincipal, counter);
            valueSet1.add(principal);
            Entry interest = new Entry(totalInterest, counter); // Jan
            valueSet2.add(interest);
            Entry total = new Entry(totalTotal, counter);
            valueSet3.add(total);

            counter++;
        }

        LineDataSet lineDataSet1 = new LineDataSet(valueSet1, "Principal");
        lineDataSet1.setColor(Color.rgb(0, 155, 0));
        LineDataSet lineDataSet2 = new LineDataSet(valueSet2, "Interest");
        lineDataSet2.setColor(Color.rgb(155, 0, 0));
        LineDataSet lineDataSet3 = new LineDataSet(valueSet3, "Total");
        lineDataSet3.setColor(Color.rgb(0, 0, 155));

        dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);
        dataSets.add(lineDataSet2);
        dataSets.add(lineDataSet3);
        return dataSets;
    }

    private static ArrayList<BarDataSet> getDataSet(List<EMIDetails> emiDetails) {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        ArrayList<BarEntry> valueSet2 = new ArrayList<>();

        int counter = 0;
        for (EMIDetails emiDetail : emiDetails) {
            BarEntry principal = new BarEntry(emiDetail.getPrincipal().floatValue(), counter);
            valueSet1.add(principal);
            BarEntry interest = new BarEntry(emiDetail.getInterest().floatValue(), counter++); // Jan
            valueSet2.add(interest);
        }

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Principal");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Interest");
        barDataSet2.setColor(Color.rgb(155, 0, 0));

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        return dataSets;
    }

    private static ArrayList<String> getXAxisValues(List<EMIDetails> emiDetails) {
        ArrayList<String> xAxis = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM-yy");
        for(EMIDetails emiDetail : emiDetails) {
            xAxis.add(sdf.format(emiDetail.getEmiDate()));
        }
        return xAxis;
    }

}
