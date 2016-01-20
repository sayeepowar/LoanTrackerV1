package com.sayaanand.loantrackerv1.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.sayaanand.loantrackerv1.R;
import com.sayaanand.loantrackerv1.db.LoanTrackerDBHelper;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private LoanTrackerDBHelper dbHelper;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new LoanTrackerDBHelper(getActivity());


        /*LoanInfoFragment loanInfoFragment = new LoanInfoFragment();
        Bundle args = new Bundle();
        loanInfoFragment.setArguments(args);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(loanInfoFragment, "LoanInfo");
        transaction.commit();*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        /*ListFragment listFragment = (ListFragment)getFragmentManager().findFragmentById(R.id.fragment2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Planets, R.layout.loan_item);
        listFragment.setListAdapter(adapter);*/
        return view;
    }

}
