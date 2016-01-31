package com.sayaanand.loantrackerv1.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sayaanand.loantrackerv1.R;
import com.sayaanand.loantrackerv1.emi.vo.EMIDetails;

import java.io.Serializable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CashFlowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CashFlowFragment extends ListFragment implements IPagerFragmentInfo {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "emiList";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private List<EMIDetails> mParam1;
    private String mParam2;
    public static final int INDEX = 2;
    public static String TITLE = "Cash Flow";

    public CashFlowFragment() {
        // Required empty public constructor
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
    public static CashFlowFragment newInstance(List<EMIDetails> emiDetailsList, String param2) {
        CashFlowFragment fragment = new CashFlowFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable)emiDetailsList);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (List<EMIDetails>)getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setListAdapter(new CashFlowAdapter(getActivity(), mParam1));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cash_flow, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        setListAdapter(null);
    }
}
