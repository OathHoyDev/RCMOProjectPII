package th.go.oae.rcmo.Util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import th.go.oae.rcmo.PBProdDetailCalculateFmentJ;
import th.go.oae.rcmo.PBProdDetailCalculateFmentK;


/**
 * Created by Taweesin on 28/6/2559.
 */
public class PlanKTextWatcher implements TextWatcher {


    private boolean hasFractionalPart;
    PBProdDetailCalculateFmentK.ViewHolder h;
    private String name;
    private EditText et ;
    private TextView t;

    public PlanKTextWatcher(EditText editText , PBProdDetailCalculateFmentK.ViewHolder h, String name) {

        hasFractionalPart = false;
        this.h = h;
        this.name = name;
        this.et = editText;

    }

    public PlanKTextWatcher(TextView t, PBProdDetailCalculateFmentK.ViewHolder h, String name) {

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
        if(name.contains("calRakaPan")){
            value = Util.strToDoubleDefaultZero(h.lookPla.getText().toString())
                    * (Util.strToDoubleDefaultZero(h.jumnounKachang.getText().toString()))
                    * (Util.strToDoubleDefaultZero(h.group1_item_1.getText().toString()));

            h.group1_item_2.setText(Util.dobbleToStringNumber(value));
        }

        if(name.contains("calKaRang")){

            value = (   Util.strToDoubleDefaultZero(h.group1_item_9.getText().toString())
                    /30.42
                    *Util.strToDoubleDefaultZero(h.group2_item_1.getText().toString())
            )
                    + Util.strToDoubleDefaultZero(h.group1_item_10.getText().toString());

            h.group1_item_8.setText(Util.dobbleToStringNumber(value));
        }

        if(name.contains("calKaSiaOkardLongtoon")){
            value = Util.strToDoubleDefaultZero(h.group1_item_2.getText().toString());
            value+=Util.strToDoubleDefaultZero(h.group1_item_3.getText().toString());
            value+=Util.strToDoubleDefaultZero(h.group1_item_8.getText().toString());
            value+=Util.strToDoubleDefaultZero(h.group1_item_4.getText().toString());
            value+=Util.strToDoubleDefaultZero(h.group1_item_5.getText().toString());
            value+=Util.strToDoubleDefaultZero(h.group1_item_6.getText().toString());
            value+= (Util.strToDoubleDefaultZero(h.group1_item_7.getText().toString())/30.42)
                    * Util.strToDoubleDefaultZero(h.group2_item_1.getText().toString());

            value+=Util.strToDoubleDefaultZero(h.group1_item_11.getText().toString());

            value+=Util.strToDoubleDefaultZero(h.group1_item_12.getText().toString());


            value = value*0.0675*(Util.strToDoubleDefaultZero(h.group2_item_1.getText().toString())/365);


            h.group2_item_2.setText(Util.dobbleToStringNumber(value));
        }
        if(name.contains("calNamnakTKai")){


            value = Util.round(Util.strToDoubleDefaultZero(h.group3_item_1.getText().toString())
                    *Util.strToDoubleDefaultZero(h.jumnounKachang.getText().toString()),2);
            value = Util.verifyDoubleDefaultZero(value);

            h.group3_item_2.setText(Util.dobbleToStringNumber(value));

        }

        if(name.contains("calRaidai")){

            value = Util.strToDoubleDefaultZero(h.group3_item_2.getText().toString())
                    *Util.strToDoubleDefaultZero(h.group3_item_3.getText().toString());
            value = Util.verifyDoubleDefaultZero(value);

            h.group3_item_5.setText(Util.dobbleToStringNumber(value));

        }

        if(name.contains("calRakaTKai1")){

            value = Util.strToDoubleDefaultZero(h.group4_item_1_2.getText().toString())
                    *Util.strToDoubleDefaultZero(h.group4_item_1_3.getText().toString());
            value = Util.verifyDoubleDefaultZero(value);

            h.group4_item_1_4.setText(Util.dobbleToStringNumber(value));

        }

        if(name.contains("calRakaTKai2")){

            value = Util.strToDoubleDefaultZero(h.group4_item_2_2.getText().toString())
                    *Util.strToDoubleDefaultZero(h.group4_item_2_3.getText().toString());
            value = Util.verifyDoubleDefaultZero(value);

            h.group4_item_2_4.setText(Util.dobbleToStringNumber(value));

        }

        if(name.contains("calRakaTKai3")){

            value = Util.strToDoubleDefaultZero(h.group4_item_3_2.getText().toString())
                    *Util.strToDoubleDefaultZero(h.group4_item_3_3.getText().toString());
            value = Util.verifyDoubleDefaultZero(value);

            h.group4_item_3_4.setText(Util.dobbleToStringNumber(value));

        }

        if(name.contains("calRakaTKai4")){

            value = Util.strToDoubleDefaultZero(h.group4_item_4_2.getText().toString())
                    *Util.strToDoubleDefaultZero(h.group4_item_4_3.getText().toString());
            value = Util.verifyDoubleDefaultZero(value);

            h.group4_item_4_4.setText(Util.dobbleToStringNumber(value));

        }

        if(name.contains("AvgFishSize")){

            double sumNumnak  =  Util.strToDoubleDefaultZero(h.group4_item_1_3.getText().toString())
                               + Util.strToDoubleDefaultZero(h.group4_item_2_3.getText().toString())
                               + Util.strToDoubleDefaultZero(h.group4_item_3_3.getText().toString())
                               + Util.strToDoubleDefaultZero(h.group4_item_4_3.getText().toString());

            double sumFishSize =   Util.strToDoubleDefaultZero(h.group4_item_1_1.getText().toString())*Util.strToDoubleDefaultZero(h.group4_item_1_3.getText().toString())
                                 + Util.strToDoubleDefaultZero(h.group4_item_2_1.getText().toString())*Util.strToDoubleDefaultZero(h.group4_item_2_3.getText().toString())
                                 + Util.strToDoubleDefaultZero(h.group4_item_3_1.getText().toString())*Util.strToDoubleDefaultZero(h.group4_item_3_3.getText().toString())
                                 + Util.strToDoubleDefaultZero(h.group4_item_4_1.getText().toString())*Util.strToDoubleDefaultZero(h.group4_item_4_3.getText().toString());

            value = sumFishSize/sumNumnak;
            value = Util.verifyDoubleDefaultZero(value);

            h.group4_size_avg_item.setText(Util.dobbleToStringNumber(value));

        }

        if(name.contains("AvgFishPrice")){

            double sumNumnak  =  Util.strToDoubleDefaultZero(h.group4_item_1_3.getText().toString())
                    + Util.strToDoubleDefaultZero(h.group4_item_2_3.getText().toString())
                    + Util.strToDoubleDefaultZero(h.group4_item_3_3.getText().toString())
                    + Util.strToDoubleDefaultZero(h.group4_item_4_3.getText().toString());
            double sumFishPrice =   Util.strToDoubleDefaultZero(h.group4_item_1_2.getText().toString())*Util.strToDoubleDefaultZero(h.group4_item_1_3.getText().toString())
                    + Util.strToDoubleDefaultZero(h.group4_item_2_2.getText().toString())*Util.strToDoubleDefaultZero(h.group4_item_2_3.getText().toString())
                    + Util.strToDoubleDefaultZero(h.group4_item_3_2.getText().toString())*Util.strToDoubleDefaultZero(h.group4_item_3_3.getText().toString())
                    + Util.strToDoubleDefaultZero(h.group4_item_4_2.getText().toString())*Util.strToDoubleDefaultZero(h.group4_item_4_3.getText().toString());

            value = sumFishPrice/sumNumnak;

            value = Util.verifyDoubleDefaultZero(value);

            h.group4_price_avg_item.setText(Util.dobbleToStringNumber(value));

        }


        /*

     public static double calKaSiaOkardLongtoon = 0;
         */
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