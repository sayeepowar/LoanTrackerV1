package com.sayaanand.loantrackerv1.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

import com.sayaanand.loantrackerv1.R;
import com.sayaanand.loantrackerv1.db.LoanTrackerDBHelper;
import com.sayaanand.loantrackerv1.utils.LoggerUtils;
import com.sayaanand.loantrackerv1.vo.LoanInfo;
import com.sayaanand.loantrackerv1.vo.PrePaymentInfo;

import java.text.DecimalFormat;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends ListFragment {

    private LoanTrackerDBHelper dbHelper;
    private String[] headers = new String[] {"Name","Type","Principal","Interest","Tenure","EMI Date"};
    private TableLayout tl;
    private OnMainFragmentInteractionListener mListener;

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
        List<LoanInfo> loans = dbHelper.getAllLoans();
        setListAdapter(new LoanListAdapter(getActivity(), loans));
        /*tl = (TableLayout) view.findViewById(R.id.loan_table);
        if (tl!=null) {
            addHeader();
            addContent();
        }
*/
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMainFragmentInteractionListener) {
            mListener = (OnMainFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMainFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
                    LoanDetailsFragment loanDetailsFragment = LoanDetailsFragment.newInstance(currentLoanInfo.getId(), null);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, loanDetailsFragment);
                    transaction.addToBackStack(null);
                    // Commit the transaction
                    transaction.commit();
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

    class LoanListAdapter extends BaseAdapter {
        private Context context;
        private List<LoanInfo> values;
        public LoanListAdapter(Context context, List<LoanInfo> values) {
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
                view = mInflater.inflate(R.layout.fragment_loaninfo, null);
            }
            final LoanInfo item = values.get(position);

            TextView loan_idTextView = (TextView) view.findViewById(R.id.loan_id);
            TextView loan_NameTextView = (TextView) view.findViewById(R.id.loan_name);
            TextView loan_TypeTextView = (TextView) view.findViewById(R.id.loan_type);
            TextView loan_PrincipalTextView = (TextView) view.findViewById(R.id.loan_principal);
            TextView loan_InterestTextView = (TextView) view.findViewById(R.id.loan_interest);
            TextView loan_tenureTextView = (TextView) view.findViewById(R.id.loan_tenure);
            TextView loan_emi_dateTextView = (TextView) view.findViewById(R.id.loan_emi_date);
            AppCompatImageButton button_delete = (AppCompatImageButton) view.findViewById(R.id.button_delete_prepayment);
            button_delete.setOnClickListener(new ActionListener(item));

            DecimalFormat df = new DecimalFormat("#,###.##");


            loan_idTextView.setText(String.valueOf(item.getId()));
            loan_NameTextView.setText(item.getName());
            loan_TypeTextView.setText(item.getType());
            loan_PrincipalTextView.setText(df.format(item.getPrincipal()));
            loan_InterestTextView.setText(df.format(item.getInterst()));
            loan_tenureTextView.setText(df.format(item.getTenure()));
            loan_emi_dateTextView.setText(item.getEmiDateStr());

            view.setOnClickListener(new View.OnClickListener() {
                private LoanInfo currentLoanInfo = item;
                @Override
                public void onClick(View v) {
                    LoggerUtils.logInfo("Clicked the row");
                    LoanDetailsFragment loanDetailsFragment = LoanDetailsFragment.newInstance(currentLoanInfo.getId(), null);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, loanDetailsFragment);
                    transaction.addToBackStack(null);
                    // Commit the transaction
                    transaction.commit();
                }
            });

            return view;


        }
    }

    public interface OnMainFragmentInteractionListener {
        // TODO: Update argument type and name
        void reloadMainFragment();
    }
    public class ActionListener implements View.OnClickListener {
        LoanInfo loanInfo;
        public ActionListener(LoanInfo loanInfo) {
            this.loanInfo = loanInfo;
        }

        @Override
        public void onClick(View v) {
            LoggerUtils.logInfo("On Delete called for " + loanInfo);
            dbHelper.delete(loanInfo);
            LoggerUtils.logInfo("No of records in db:" + dbHelper.numberOfRowsPrePayment());
            mListener.reloadMainFragment();
        }
    }
}
