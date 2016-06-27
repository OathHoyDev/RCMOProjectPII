package th.co.rcmo.rcmoapp.Util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import th.co.rcmo.rcmoapp.PBProdDetailCalculateFmentA;
import th.co.rcmo.rcmoapp.PBProdDetailCalculateFmentC;

/**
 * Created by Taweesin on 28/6/2559.
 */
public class PlanCTextWatcher implements TextWatcher {


    private boolean hasFractionalPart;
    PBProdDetailCalculateFmentC.ViewHolder h;
    private String name;
    private EditText et ;

    public PlanCTextWatcher(EditText editText , PBProdDetailCalculateFmentC.ViewHolder h, String name) {

        hasFractionalPart = false;
        this.h = h;
        this.name = name;
        this.et = editText;

    }



    @SuppressWarnings("unused")
    private static final String TAG = "NumberTextWatcher";

    @Override
    public void afterTextChanged(Editable s) {
        et.removeTextChangedListener(this);

        try {
            String originalString = s.toString();

            Long longval;
            if (originalString.contains(",")) {
                originalString = originalString.replaceAll(",", "");
            }
            longval = Long.parseLong(originalString);

            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            formatter.applyPattern("#,###,###,###");
            String formattedString = formatter.format(longval);

            //setting text after format to EditText
            et.setText(formattedString);
            et.setSelection(et.getText().length());
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        //formulaAModel.calculate();
        double value = 0;
        if("Karang".equalsIgnoreCase(name)) {
            value =   (Util.strToDoubleDefaultZero(h.group1_item_2.getText().toString()))
                    + (Util.strToDoubleDefaultZero(h.group1_item_3.getText().toString()))
                    + (Util.strToDoubleDefaultZero(h.group1_item_4.getText().toString()))
                    + (Util.strToDoubleDefaultZero(h.group1_item_5.getText().toString()));

            h.group1_item_1.setText(Util.dobbleToStringNumber(value));
        }else if("KaWassadu".equalsIgnoreCase(name)) {
            value =   (Util.strToDoubleDefaultZero(h.group1_item_7.getText().toString()))
                    + (Util.strToDoubleDefaultZero(h.group1_item_8.getText().toString()))
                    + (Util.strToDoubleDefaultZero(h.group1_item_9.getText().toString()))
                    + (Util.strToDoubleDefaultZero(h.group1_item_10.getText().toString()));

            h.group1_item_6.setText(Util.dobbleToStringNumber(value));

        }


        et.addTextChangedListener(this);
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
}