package com.sayaanand.loantrackerv1.validations;

import android.widget.EditText;

import com.sayaanand.loantrackerv1.db.LoanTrackerDBHelper;
import com.sayaanand.loantrackerv1.utils.LoggerUtils;
import com.sayaanand.loantrackerv1.vo.LoanInfo;

/**
 * Created by Nandkishore.Powar on 01/02/2016.
 */
public class LoanNameValidator extends TextValidator {

    private LoanTrackerDBHelper dbHelper;
    private LoanInfo loanInfo;
    public LoanNameValidator(EditText textView, LoanInfo loanInfo,  LoanTrackerDBHelper dbHelper) {
        super(textView);
        this.loanInfo = loanInfo;
        this.dbHelper = dbHelper;
    }

    @Override
    public void validate(EditText textView, String text) {
        try {
            boolean isDuplicate = dbHelper.checkIfExists(loanInfo.getId(), text);
            if (isDuplicate) {
                textView.setError("Duplicate Loan Name.");
            }
        } catch (Exception e) {
            //invalid number
            textView.setError("Invalid Number.");
        }
    }
}
