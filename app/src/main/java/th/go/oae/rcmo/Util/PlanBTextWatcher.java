package th.go.oae.rcmo.Util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.neopixl.pixlui.components.textview.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import th.go.oae.rcmo.Model.calculate.FormulaBModel;
import th.go.oae.rcmo.PBProdDetailCalculateFmentB;

/**
 * Created by Taweesin on 28/6/2559.
 */
public class PlanBTextWatcher implements TextWatcher {


    private boolean hasFractionalPart;
    PBProdDetailCalculateFmentB.ViewHolder h;
    private String name;
    private EditText et ;
    private FormulaBModel f;
    private android.widget.TextView t;

    public PlanBTextWatcher(EditText editText , PBProdDetailCalculateFmentB.ViewHolder h, String name) {

        hasFractionalPart = false;
        this.h = h;
        this.name = name;
        this.et = editText;

    }

    public PlanBTextWatcher(EditText editText , PBProdDetailCalculateFmentB.ViewHolder h, FormulaBModel f, String name) {

        hasFractionalPart = false;
        this.h = h;
        this.f = f;
        this.name = name;
        this.et = editText;

    }

    public PlanBTextWatcher(TextView t, PBProdDetailCalculateFmentB.ViewHolder h, String name) {
        this.h = h;
        this.name = name;
        this.t = t;
    }

    public PlanBTextWatcher(TextView t, PBProdDetailCalculateFmentB.ViewHolder h,FormulaBModel f, String name) {
        this.h = h;
        this.f = f;
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
        if(et != null) {
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
                // et.addTextChangedListener(this);
            } catch (NumberFormatException nfe) {
                // nfe.printStackTrace();
            }
        }
        //formulaAModel.calculate();
        double value = 0;
        if(name.contains("Karang")) {
            value =   (Util.strToDoubleDefaultZero(h.group1_item_2.getText().toString()))
                    + (Util.strToDoubleDefaultZero(h.group1_item_3.getText().toString()))
                    + (Util.strToDoubleDefaultZero(h.group1_item_4.getText().toString()))
                    + (Util.strToDoubleDefaultZero(h.group1_item_5.getText().toString()));

            h.group1_item_1.setText(Util.dobbleToStringNumber(value));
        }
        if(name.contains("KaWassadu")) {
            value =   (Util.strToDoubleDefaultZero(h.group1_item_7.getText().toString()))
                    + (Util.strToDoubleDefaultZero(h.group1_item_8.getText().toString()))
                    + (Util.strToDoubleDefaultZero(h.group1_item_9.getText().toString()))
                    + (Util.strToDoubleDefaultZero(h.group1_item_10.getText().toString()));

            h.group1_item_6.setText(Util.dobbleToStringNumber(value));

        }
        if(name.contains("KaSermOuppakorn")) {
            double sumRai = (   (Util.strToDoubleDefaultZero(h.rai.getText().toString())*4*400)
                    + (Util.strToDoubleDefaultZero(h.ngan.getText().toString())*400)
                    + (Util.strToDoubleDefaultZero(h.wa.getText().toString())*4)
                    +  Util.strToDoubleDefaultZero(h.meter.getText().toString())
            )/1600;
            value =   sumRai*f.KaSermOuppakorn;


            h.group1_item_13.setText(Util.dobbleToStringNumber(value));

        }
        if(name.contains("KaSiaOkardOuppakorn")) {
            double sumRai = (   (Util.strToDoubleDefaultZero(h.rai.getText().toString())*4*400)
                    + (Util.strToDoubleDefaultZero(h.ngan.getText().toString())*400)
                    + (Util.strToDoubleDefaultZero(h.wa.getText().toString())*4)
                    +  Util.strToDoubleDefaultZero(h.meter.getText().toString())
            )/1600;
            value =    sumRai*f.KaSiaOkardOuppakorn;
            h.group1_item_14.setText(Util.dobbleToStringNumber(value));

        }

        if(name.contains("KaSiaOkardLongtoon")) {

            value=   Util.round(  ( Util.strToDoubleDefaultZero(h.group1_item_1.getText().toString())
                    + Util.strToDoubleDefaultZero(h.group1_item_6.getText().toString()))
                    * (Util.strToDoubleDefaultZero(h.group4_item_1.getText().toString()) / 100) * (12 / 12), 2);

            h.group1_item_11.setText(Util.dobbleToStringNumber(value));

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