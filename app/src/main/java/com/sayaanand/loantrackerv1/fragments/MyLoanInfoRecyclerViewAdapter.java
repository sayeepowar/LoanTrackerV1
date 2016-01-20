package com.sayaanand.loantrackerv1.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sayaanand.loantrackerv1.R;
import com.sayaanand.loantrackerv1.fragments.LoanInfoFragment.OnListFragmentInteractionListener;
import com.sayaanand.loantrackerv1.fragments.dummy.DummyContent.DummyItem;
import com.sayaanand.loantrackerv1.vo.LoanInfo;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyLoanInfoRecyclerViewAdapter extends RecyclerView.Adapter<MyLoanInfoRecyclerViewAdapter.ViewHolder> {

    private final List<LoanInfo> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyLoanInfoRecyclerViewAdapter(List<LoanInfo> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_loaninfo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.valueOf(mValues.get(position).getId()));
        holder.mNameView.setText(mValues.get(position).getName());
        holder.mTypeView.setText(mValues.get(position).getType());
        holder.mPrincipalView.setText(String.valueOf(mValues.get(position).getPrincipal()));
        holder.mInterestView.setText(String.valueOf(mValues.get(position).getInterst()));
        holder.mTenureView.setText(String.valueOf(mValues.get(position).getTenure()));
        holder.mEmiDateView.setText(mValues.get(position).getEmiDateStr());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mNameView;
        public final TextView mTypeView;
        public final TextView mPrincipalView;
        public final TextView mInterestView;
        public final TextView mTenureView;
        public final TextView mEmiDateView;
        public LoanInfo mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.loan_id);
            mNameView = (TextView) view.findViewById(R.id.loan_name);
            mTypeView = (TextView) view.findViewById(R.id.loan_type);
            mPrincipalView = (TextView) view.findViewById(R.id.loan_principal);
            mInterestView = (TextView) view.findViewById(R.id.loan_interest);
            mTenureView = (TextView) view.findViewById(R.id.loan_tenure);
            mEmiDateView = (TextView) view.findViewById(R.id.loan_emi_date);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
