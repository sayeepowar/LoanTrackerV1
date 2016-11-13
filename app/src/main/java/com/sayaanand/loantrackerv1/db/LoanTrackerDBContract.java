package com.sayaanand.loantrackerv1.db;

import android.provider.BaseColumns;
/**
 * Created by Nandkishore.Powar on 16/01/2016.
 */
public final class LoanTrackerDBContract {



    public static abstract class LoanDetails implements BaseColumns {
        public static final String TABLE_NAME = "loans";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_ENTRY_ID = "entry_id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_PRINCIPAL = "principal";
        public static final String COLUMN_NAME_INTEREST = "interest";
        public static final String COLUMN_NAME_TENURE = "tenure";
        public static final String COLUMN_NAME_EMI_DATE = "emiDate";
    }

    public static abstract class EMIPrepayments implements BaseColumns {
        public static final String TABLE_NAME = "prepayments";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_LOAN_ID = "loan_id";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_COMMENTS = "comments";
    }
}
