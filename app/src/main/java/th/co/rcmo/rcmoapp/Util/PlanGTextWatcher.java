package th.co.rcmo.rcmoapp.Util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import th.co.rcmo.rcmoapp.PBProdDetailCalculateFmentG;
import th.co.rcmo.rcmoapp.PBProdDetailCalculateFmentH;

/**
 * Created by Taweesin on 28/6/2559.
 */
public class PlanGTextWatcher implements TextWatcher {


    private boolean hasFractionalPart;
    PBProdDetailCalculateFmentG.ViewHolder h;
    private String name;
    private EditText et ;
    private TextView t;

    public PlanGTextWatcher(EditText editText , PBProdDetailCalculateFmentG.ViewHolder h, String name) {

        hasFractionalPart = false;
        this.h = h;
        this.name = name;
        this.et = editText;

    }

    public PlanGTextWatcher(TextView t, PBProdDetailCalculateFmentG.ViewHolder h, String name) {

        hasFractionalPart = false;
        this.h = h;
        this.name = name;
        this.t = t;


    }


    @SuppressWarnings("unused")
    private static final String TAG = "NumberTextWatcher";

    @Override
    public void afterTextChanged(Editable s) {

        if(et != null) {
            et.removeTextChangedListener(this);
        }else{
            t.removeTextChangedListener(this);
        }
        if (et != null) {
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
        }
        //formulaAModel.calculate();
        double value = 0;



        if(name.contains("calMoonkaAnimalTuangNumnuk")){
            value  =  Util.strToDoubleDefaultZero(h.group1_item_1.getText().toString())
                    + Util.strToDoubleDefaultZero(h.group1_item_2.getText().toString())
                    + Util.strToDoubleDefaultZero(h.group1_item_3.getText().toString())
                    + Util.strToDoubleDefaultZero(h.group1_item_4.getText().toString());

            h.group1_item_5.setText(Util.dobbleToStringNumber(value));
        }

        if(name.contains("calKaRang")){
            value = Util.strToDoubleDefaultZero(h.group2_item_2.getText().toString())
                   + Util.strToDoubleDefaultZero(h.group2_item_3.getText().toString());

            h.group2_item_1.setText(Util.dobbleToStringNumber(value));
        }

        if(name.contains("calKawassadu")){
            value  =  Util.strToDoubleDefaultZero(h.group2_item_5.getText().toString())
                    + Util.strToDoubleDefaultZero(h.group2_item_6.getText().toString())
                    + Util.strToDoubleDefaultZero(h.group2_item_7.getText().toString())
                    + Util.strToDoubleDefaultZero(h.group2_item_8.getText().toString())
                    + Util.strToDoubleDefaultZero(h.group2_item_9.getText().toString())
                    + Util.strToDoubleDefaultZero(h.group2_item_10.getText().toString())
                    + Util.strToDoubleDefaultZero(h.group2_item_11.getText().toString())
                    + Util.strToDoubleDefaultZero(h.group2_item_12.getText().toString())
                    + Util.strToDoubleDefaultZero(h.group2_item_13.getText().toString())
                    + Util.strToDoubleDefaultZero(h.group2_item_14.getText().toString());;

            h.group2_item_4.setText(Util.dobbleToStringNumber(value));
        }

        if(name.contains("costKaSiaOkardRongRaun")){
            value = Util.strToDoubleDefaultZero(h.group2_item_1.getText().toString());
            value+= Util.strToDoubleDefaultZero(h.group2_item_4.getText().toString());

            value = Util.round(value*(0.07)*(30.42/365),2);

            h.group2_item_15.setText(Util.dobbleToStringNumber(value));
        }

        if(et != null) {
            et.addTextChangedListener(this);
        }else{
            t.addTextChangedListener(this);
        }

    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
}