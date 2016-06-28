package th.co.rcmo.rcmoapp.Util;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import th.co.rcmo.rcmoapp.PBProdDetailCalculateFmentA;
import th.co.rcmo.rcmoapp.PBProdDetailCalculateFmentD;

/**
 * Created by Taweesin on 28/6/2559.
 */
public class PlanDTextWatcher implements TextWatcher {


    private boolean hasFractionalPart;
    PBProdDetailCalculateFmentD.ViewHolder h;
    private String name;
    private EditText et ;

    public PlanDTextWatcher(EditText editText , PBProdDetailCalculateFmentD.ViewHolder h, String name) {

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
           // nfe.printStackTrace();
        }
        //formulaAModel.calculate();
        double value = 0;
        if("KaPan".equalsIgnoreCase(name)) {
            value =   (Util.strToDoubleDefaultZero(h.txStartPrice.getText().toString()))
                    * (Util.strToDoubleDefaultZero(h.txStartUnit.getText().toString()));


            h.group1_item_1.setText(Util.dobbleToStringNumber(value));
        }else if("DieRate".equalsIgnoreCase(name)) {
           value =   ((Util.strToDoubleDefaultZero(h.txStartUnit.getText().toString())) - (Util.strToDoubleDefaultZero(h.group3_item_2.getText().toString())));
          //  Log.d("Test ","-----> "+value);
                        value =  value/(Util.strToDoubleDefaultZero(h.txStartUnit.getText().toString())) * 100;
            Log.d("Test ","2-----> "+value);

            if(value<0){
                h.group2_item_1.setText("100%");
            }else {
                h.group2_item_1.setText(Util.dobbleToStringNumberWithClearDigit(value) + "%");
            }

        }


        et.addTextChangedListener(this);
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
}