package com.sayaanand.loantrackerv1.validations;

import android.widget.EditText;

import com.sayaanand.loantrackerv1.utils.LoggerUtils;

/**
 * Created by Nandkishore.Powar on 01/02/2016.
 */
public class NumberValidator extends TextValidator {
    public NumberValidator(EditText textView) {
        super(textView);
    }

    @Override
    public void validate(EditText textView, String text) {
        try {
            Double.parseDouble(text);
            LoggerUtils.logInfo("Valid number->" + text);
        } catch (Exception e) {
            //invalid number
            textView.setError("Invalid Number.");
        }
    }
}
