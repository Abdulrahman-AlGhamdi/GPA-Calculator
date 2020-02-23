package com.game.gpacalculator.EditTextFormatter;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberTextWatcher implements TextWatcher {

    private EditText editText;

    public NumberTextWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        editText.removeTextChangedListener(this);

        try {
            double number = Double.parseDouble(s.toString());
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.GERMAN);
            if(Double.parseDouble(s.toString()) <= 5.00){
                formatter.applyPattern("#,##");
                editText.setText(s);
                editText.setSelection(editText.getText().length());
            }else if(Double.parseDouble(s.toString()) >= 10.00 && Double.parseDouble(s.toString()) <= 50.00){
                formatter.applyPattern("#,#");
                String text = formatter.format(number);
                editText.setText(text);
                editText.setSelection(editText.getText().length());
            }else{
                editText.setText(String.valueOf(5));
                editText.setSelection(editText.getText().length());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        editText.addTextChangedListener(this);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
