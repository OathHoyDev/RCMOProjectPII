package th.go.oae.rcmo.Util;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.neopixl.pixlui.components.textview.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import th.go.oae.rcmo.Model.calculate.FormulaAModel;
import th.go.oae.rcmo.PBProdDetailCalculateFmentA;

/**
 * Created by Taweesin on 28/6/2559.
 */
public class PlanATextWatcher implements TextWatcher {


    private boolean hasFractionalPart;
    PBProdDetailCalculateFmentA.ViewHolder h;
    private String name;
    private EditText et ;
    private FormulaAModel f;
    private android.widget.TextView t;

    public PlanATextWatcher(EditText editText , PBProdDetailCalculateFmentA.ViewHolder h, String name) {

        hasFractionalPart = false;
        this.h = h;
        this.name = name;
        this.et = editText;

    }

    public PlanATextWatcher(EditText editText , PBProdDetailCalculateFmentA.ViewHolder h, FormulaAModel f, String name) {

        hasFractionalPart = false;
        this.h = h;
        this.f = f;
        this.name = name;
        this.et = editText;

    }

    public PlanATextWatcher(TextView t, PBProdDetailCalculateFmentA.ViewHolder h, String name) {
        this.h = h;
        this.name = name;
        this.t = t;
    }

    public PlanATextWatcher(TextView t, PBProdDetailCalculateFmentA.ViewHolder h,FormulaAModel f, String name) {
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

            sumRai = Util.verifyDoubleDefaultZero(sumRai);

            value =   sumRai*f.KaSermOuppakorn;


            h.group1_item_13.setText(Util.dobbleToStringNumber(value));

        }
        if(name.contains("KaSiaOkardOuppakorn")) {

            double sumRai = (   (Util.strToDoubleDefaultZero(h.rai.getText().toString())*4*400)
                    + (Util.strToDoubleDefaultZero(h.ngan.getText().toString())*400)
                    + (Util.strToDoubleDefaultZero(h.wa.getText().toString())*4)
                    +  Util.strToDoubleDefaultZero(h.meter.getText().toString())
            )/1600;

            sumRai = Util.verifyDoubleDefaultZero(sumRai);

            value =    sumRai*f.KaSiaOkardOuppakorn;
            h.group1_item_14.setText(Util.dobbleToStringNumber(value));

        }

        if(name.contains("KaSiaOkardLongtoon")) {
            value  =Util.round(
                                (  Util.strToDoubleDefaultZero(h.group1_item_1.getText().toString())
                                  +Util.strToDoubleDefaultZero(h.group1_item_6.getText().toString())
                                )
                                * (Util.strToDoubleDefaultZero(h.group4_item_1.getText().toString())/100)
                                * (0.5)
                               ,2 );
                   // *(Util.strToDoubleDefaultZero(h.group4_item_1.getText().toString())/100)*(6/12),2);
           // value =    (Util.strToDoubleDefaultZero(h.txStartUnit.getText().toString()))*f.KaSiaOkardOuppakorn;
            Log.d("Label Value",Util.dobbleToStringNumber(value));
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