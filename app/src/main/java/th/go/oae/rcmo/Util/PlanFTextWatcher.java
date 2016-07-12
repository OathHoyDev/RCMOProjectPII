package th.go.oae.rcmo.Util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import th.go.oae.rcmo.PBProdDetailCalculateFmentF;

/**
 * Created by Taweesin on 28/6/2559.
 */
public class PlanFTextWatcher implements TextWatcher {


    private boolean hasFractionalPart;
    PBProdDetailCalculateFmentF.ViewHolder h;
    private String name;
    private EditText et ;
    private TextView t;

    public PlanFTextWatcher(EditText editText , PBProdDetailCalculateFmentF.ViewHolder h, String name) {

        hasFractionalPart = false;
        this.h = h;
        this.name = name;
        this.et = editText;

    }

    public PlanFTextWatcher(TextView t, PBProdDetailCalculateFmentF.ViewHolder h, String name) {

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
        if(name.contains("KaPan")) {
            value =   (Util.strToDoubleDefaultZero(h.txStartPrice.getText().toString()))
                    * (Util.strToDoubleDefaultZero(h.txStartUnit.getText().toString()));


            h.group1_item_1.setText(Util.dobbleToStringNumber(value));
        }

        if(name.contains("costKaSiaOkardRongRaun")){
            value = Util.strToDoubleDefaultZero(h.group1_item_1.getText().toString());
            value+= Util.strToDoubleDefaultZero(h.group1_item_2.getText().toString());
            value+= Util.strToDoubleDefaultZero(h.group1_item_3.getText().toString());
            value+= (Util.strToDoubleDefaultZero(h.group1_item_4.getText().toString())/30.42)*(Util.strToDoubleDefaultZero(h.group3_item_4.getText().toString())*7);
            value+= (Util.strToDoubleDefaultZero(h.group1_item_5.getText().toString())/30.42)*(Util.strToDoubleDefaultZero(h.group3_item_4.getText().toString())*7);
            value+= (Util.strToDoubleDefaultZero(h.group1_item_6.getText().toString())/30.42)*(Util.strToDoubleDefaultZero(h.group3_item_4.getText().toString())*7);
            value+= Util.strToDoubleDefaultZero(h.group1_item_7.getText().toString());
            value+= Util.strToDoubleDefaultZero(h.group1_item_8.getText().toString());

            //value = value*0.0675/365*Util.strToDoubleDefaultZero(h.group3_item_2.getText().toString());
            value = value*0.0675;
            h.group3_item_5.setText(Util.dobbleToStringNumber(value));
        }

        if(name.contains("calAllEgg")){
            value = Util.strToDoubleDefaultZero(h.txStartUnit.getText().toString())
                    * Util.strToDoubleDefaultZero(h.group3_item_1.getText().toString());

            h.group3_item_6.setText(Util.dobbleToStringNumber(value));
        }

        if(name.contains("calPriceAllEgg")){
            value = Util.strToDoubleDefaultZero(h.txStartUnit.getText().toString())
                    * Util.strToDoubleDefaultZero(h.group3_item_1.getText().toString())
                    * Util.strToDoubleDefaultZero(h.group3_item_2.getText().toString());

            h.group3_item_7.setText(Util.dobbleToStringNumber(value));
        }

        if(name.contains("calAllPonPloyDai")){
            value =Util.strToDoubleDefaultZero(h.txStartUnit.getText().toString())*
                    Util.strToDoubleDefaultZero(h.group3_item_3.getText().toString());

            h.group3_item_8.setText(Util.dobbleToStringNumber(value));
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