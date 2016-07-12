package th.go.oae.rcmo.Util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import th.go.oae.rcmo.PBProdDetailCalculateFmentH;

/**
 * Created by Taweesin on 28/6/2559.
 */
public class PlanHTextWatcher implements TextWatcher {


    private boolean hasFractionalPart;
    PBProdDetailCalculateFmentH.ViewHolder h;
    private String name;
    private EditText et ;
    private TextView t;

    public PlanHTextWatcher(EditText editText , PBProdDetailCalculateFmentH.ViewHolder h, String name) {

        hasFractionalPart = false;
        this.h = h;
        this.name = name;
        this.et = editText;

    }

    public PlanHTextWatcher(TextView t, PBProdDetailCalculateFmentH.ViewHolder h, String name) {

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



        if(name.contains("calNumnukTungmod")){
            value = Util.strToDoubleDefaultZero(h.group1_item_1.getText().toString())
                    * Util.strToDoubleDefaultZero(h.group2_item_1.getText().toString());

            h.group2_item_2.setText(Util.dobbleToStringNumber(value));
        }

        if(name.contains("calNumnukTPuem")){
            value = Util.strToDoubleDefaultZero(h.group2_item_1.getText().toString())
                    - Util.strToDoubleDefaultZero(h.txStartNumnakReam.getText().toString());


            h.group2_item_3.setText(Util.dobbleToStringNumber(value));
        }

        if(name.contains("calRakaTkai")){
            value =Util.strToDoubleDefaultZero(h.group3_item_1.getText().toString())/
                    Util.strToDoubleDefaultZero(h.group2_item_1.getText().toString());

            h.group3_item_2.setText(Util.dobbleToStringNumber(value));
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