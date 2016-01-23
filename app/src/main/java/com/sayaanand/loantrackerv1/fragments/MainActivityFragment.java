package com.sayaanand.loantrackerv1.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

import com.sayaanand.loantrackerv1.R;
import com.sayaanand.loantrackerv1.db.LoanTrackerDBHelper;
import com.sayaanand.loantrackerv1.utils.LoggerUtils;
import com.sayaanand.loantrackerv1.vo.LoanInfo;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private LoanTrackerDBHelper dbHelper;
    private String[] headers = new String[] {"Name","Type","Principal","Interest","Tenure","EMI Date"};
    private TableLayout tl;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new LoanTrackerDBHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        tl = (TableLayout) view.findViewById(R.id.loan_table);
        if (tl!=null) {
            addHeader();
            addContent();
        }

        return view;
    }

    private void addHeader() {
        /** Create a TableRow dynamically **/
        TableRow tr = new TableRow(getContext());
        tr.setLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        TableRow separator = new TableRow(getContext());
        separator.setLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));

        for (String header:headers) {
            TextView textView = new TextView(getContext());
            textView.setText(header);
            textView.setPadding(5,5,5,5);
            textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            tr.addView(textView);

            TextView sepTextView = new TextView(getContext());
            sepTextView.setText("----");
            sepTextView.setPadding(5, 5, 5,5);
            sepTextView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            separator.addView(sepTextView);
        }
        //add row to table
        tl.addView(tr, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        //add separator row to table
        tl.addView(separator, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

    }

    private void addContent() {
        List<LoanInfo> loans = dbHelper.getAllLoans();
        for(final LoanInfo loanInfo:loans) {
            /** Create a TableRow dynamically **/
            TableRow tr = new TableRow(getContext());
            tr.setClickable(true);
            tr.setOnClickListener(new View.OnClickListener() {
                private LoanInfo currentLoanInfo = loanInfo;
                @Override
                public void onClick(View v) {
                    LoggerUtils.logInfo("Clicked the row");

                }
            });
            tr.setLayoutParams(new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));

            TextView nameTextView = new TextView(getContext());
            nameTextView.setText(loanInfo.getName());
            nameTextView.setPadding(5, 5, 5, 5);
            nameTextView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            tr.addView(nameTextView);

            TextView typeTextView = new TextView(getContext());
            typeTextView.setText(loanInfo.getType());
            typeTextView.setPadding(5, 5, 5, 5);
            typeTextView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            tr.addView(typeTextView);

            TextView principalTextView = new TextView(getContext());
            principalTextView.setText(loanInfo.getPrincipal().toString());
            principalTextView.setPadding(5, 5, 5, 5);
            principalTextView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            tr.addView(principalTextView);

            TextView interestTextView = new TextView(getContext());
            interestTextView.setText(loanInfo.getInterst().toString());
            interestTextView.setPadding(5, 5, 5, 5);
            interestTextView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            tr.addView(interestTextView);

            TextView tenureTextView = new TextView(getContext());
            tenureTextView.setText(loanInfo.getTenure().toString());
            tenureTextView.setPadding(5, 5, 5, 5);
            tenureTextView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            tr.addView(tenureTextView);

            TextView emiDateTextView = new TextView(getContext());
            emiDateTextView.setText(loanInfo.getEmiDateStr());
            emiDateTextView.setPadding(5, 5, 5, 5);
            emiDateTextView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            tr.addView(emiDateTextView);

            //add row to table
            tl.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
        }
    }
}
