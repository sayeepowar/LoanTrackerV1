package com.sayaanand.loantrackerv1.validations;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Nandkishore.Powar on 01/02/2016.
 */
public abstract class TextValidator implements TextWatcher {
    private final EditText textView;

    public TextValidator(EditText textView) {
        this.textView = textView;
    }

    public abstract void validate(EditText textView, String text);

    @Override
    final public void afterTextChanged(Editable s) {
        String text = textView.getText().toString();
        validate(textView, text);
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Don't care */ }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) { /* Don't care */ }
}
