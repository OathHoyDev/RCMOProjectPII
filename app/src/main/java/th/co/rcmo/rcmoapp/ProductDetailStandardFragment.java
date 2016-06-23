package th.co.rcmo.rcmoapp;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

import th.co.rcmo.rcmoapp.API.RequestServices;
import th.co.rcmo.rcmoapp.API.ResponseAPI;
import th.co.rcmo.rcmoapp.Module.mGetPlotDetail;
import th.co.rcmo.rcmoapp.Module.mGetVariable;
import th.co.rcmo.rcmoapp.Util.CalculateConstant;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailStandardFragment extends Fragment implements View.OnClickListener {

    ListView standardList;
    Context context;
    String productType;

    String[] stdName = null;
    String[] stdValue = null;
    String[] stdUnit = null ;

    private String plodID;
    private String prdID;
    private String fisheryType;

    View v;

    public ProductDetailStandardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_product_detail_map, container, false);
        v = inflater.inflate(R.layout.fragment_product_detail_standard, container,
                false);
        context = v.getContext();

        getArgumentFromActivity();

        Log.i("productType", "productType : " + productType);

        initialLayoutType();

        setAction();

        API_getVariable(prdID , fisheryType);

        return v;
    }

    private void getArgumentFromActivity(){

        productType = getArguments().getString("productType");
        plodID = getArguments().getString("plodID");
        prdID = getArguments().getString("prdID");
        fisheryType = getArguments().getString("fisheryType");
        if(fisheryType == ""){
            fisheryType = "0";
        }
    }

    public void initialLayoutType(){

        ImageView buttomImageView = (ImageView)v.findViewById(R.id.buttomImage);
        com.neopixl.pixlui.components.button.Button btnRecalculate = (com.neopixl.pixlui.components.button.Button)v.findViewById(R.id.btnRecalculate);
        com.neopixl.pixlui.components.button.Button btnGetVariable = (com.neopixl.pixlui.components.button.Button)v.findViewById(R.id.btnGetVariable);


        switch (productType){
            case CalculateConstant.PRODUCT_TYPE_PLANT:
                buttomImageView.setImageResource(R.drawable.bottom_green_total);
                btnRecalculate.setBackgroundResource(R.drawable.btn_green_recal);
                btnGetVariable.setBackgroundResource(R.drawable.btn_green_refresh);
                break;
            case CalculateConstant.PRODUCT_TYPE_ANIMAL:
                buttomImageView.setImageResource(R.drawable.bottom_pink_total);
                btnRecalculate.setBackgroundResource(R.drawable.btn_pink_recal);
                btnGetVariable.setBackgroundResource(R.drawable.btn_pink_refresh);
                break;
            case CalculateConstant.PRODUCT_TYPE_FISH:
                buttomImageView.setImageResource(R.drawable.bottom_blue_total);
                btnRecalculate.setBackgroundResource(R.drawable.btn_blue_recal);
                btnGetVariable.setBackgroundResource(R.drawable.btn_blue_refresh);
                break;
        }

    }

    private void API_getPlotDetail(String plodID) {
        /**
         1.TamCode (ไม่บังคับใส่)
         2.AmpCode (บังคับใส่)
         3.ProvCode (บังคับใส่)
         */
        new ResponseAPI(context, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mGetPlotDetail mPlotDetail = (mGetPlotDetail) obj;
                List<mGetPlotDetail.mRespBody> mPlotDetailBodyLists = mPlotDetail.getRespBody();

                if (mPlotDetailBodyLists.size() != 0) {

                    API_getVariable(String.valueOf(mPlotDetailBodyLists.get(0).getPrdID()) , String.valueOf(mPlotDetailBodyLists.get(0).getFisheryType()));

                }


            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getPlotDetail +
                "?PlotID=" + plodID +
                "&ImeiCode=" + ServiceInstance.GetDeviceID(context));

    }

    private void API_getVariable(String prdID , final String fisheryType) {

        new ResponseAPI(context, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mGetVariable mVariable = (mGetVariable) obj;
                List<mGetVariable.mRespBody> mVariableBodyLists = mVariable.getRespBody();

                if (mVariableBodyLists.size() != 0) {

                    displayStandardValue(mVariableBodyLists.get(0) , fisheryType);

                }


            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getVariable +
                "?PrdID=" + prdID +
                "&FisheryType=" + 1);

    }

    public void displayStandardValue(mGetVariable.mRespBody var , String fisheryType){

        Log.d("displayStandardValue", "FormularCode : " + var.getFormularCode());

        String formularCode = var.getFormularCode();

        switch (formularCode) {

            case "A":
            case "B":

                stdName = new String[3];
                stdValue = new String[3];
                stdUnit = new String[3];

                Log.d("displayStandardValue", CalculateConstant.CALCULATE_STANDARD_CONST_AB.get("D") + " : " + var.getD());
                stdName[0] = CalculateConstant.CALCULATE_STANDARD_CONST_AB.get("D");
                stdValue[0] = var.getD();
                stdUnit[0] = "บาท/ไร่";

                Log.d("displayStandardValue", CalculateConstant.CALCULATE_STANDARD_CONST_AB.get("O") + " : " + var.getO());
                stdName[1] = CalculateConstant.CALCULATE_STANDARD_CONST_AB.get("O");
                stdValue[1] = var.getO();
                stdUnit[1] = "บาท/ไร่";

                Log.d("displayStandardValue", CalculateConstant.CALCULATE_STANDARD_CONST_AB.get("CS") + " : " + var.getCS());
                stdName[2] = CalculateConstant.CALCULATE_STANDARD_CONST_AB.get("CS");
                stdValue[2] = var.getCS();
                stdUnit[2] = "บาท/ไร่";

                break;

            case "C":
                stdName = new String[4];
                stdValue = new String[4];
                stdUnit = new String[4];

                Log.d("displayStandardValue", CalculateConstant.CALCULATE_STANDARD_CONST_C.get("D") + " : " + var.getD());
                stdName[0] = CalculateConstant.CALCULATE_STANDARD_CONST_C.get("D");
                stdValue[0] = var.getD();
                stdUnit[0] = "บาท/ไร่";

                Log.d("displayStandardValue", CalculateConstant.CALCULATE_STANDARD_CONST_C.get("O") + " : " + var.getO());
                stdName[1] = CalculateConstant.CALCULATE_STANDARD_CONST_C.get("O");
                stdValue[1] = var.getO();
                stdUnit[1] = "บาท/ไร่";

                Log.d("displayStandardValue", CalculateConstant.CALCULATE_STANDARD_CONST_C.get("CS") + " : " + var.getCS());
                stdName[2] = CalculateConstant.CALCULATE_STANDARD_CONST_C.get("CS");
                stdValue[2] = var.getCS();
                stdUnit[2] = "บาท/ไร่";

                Log.d("displayStandardValue", CalculateConstant.CALCULATE_STANDARD_CONST_C.get("CA") + " : " + var.getCA());
                stdName[3] = CalculateConstant.CALCULATE_STANDARD_CONST_C.get("CA");
                stdValue[3] = var.getCA();
                stdUnit[3] = "บาท/ไร่";
                break;

            case "D":
            case "E":
            case "F":

                stdName = new String[4];
                stdValue = new String[4];
                stdUnit = new String[4];

                Log.d("displayStandardValue", CalculateConstant.CALCULATE_STANDARD_CONST_DEF.get("F") + " : " + var.getF());
                stdName[0] = CalculateConstant.CALCULATE_STANDARD_CONST_DEF.get("F");
                stdValue[0] = var.getF();
                stdUnit[0] = "บาท/ไร่";

                Log.d("displayStandardValue", CalculateConstant.CALCULATE_STANDARD_CONST_DEF.get("B") + " : " + var.getB());
                stdName[1] = CalculateConstant.CALCULATE_STANDARD_CONST_DEF.get("B");
                stdValue[1] = var.getB();
                stdUnit[1] = "บาท/ไร่";

                Log.d("displayStandardValue", CalculateConstant.CALCULATE_STANDARD_CONST_DEF.get("D") + " : " + var.getD());
                stdName[2] = CalculateConstant.CALCULATE_STANDARD_CONST_DEF.get("D");
                stdValue[2] = var.getD();
                stdUnit[2] = "บาท/ไร่";

                Log.d("displayStandardValue", CalculateConstant.CALCULATE_STANDARD_CONST_DEF.get("O") + " : " + var.getO());
                stdName[3] = CalculateConstant.CALCULATE_STANDARD_CONST_DEF.get("O");
                stdValue[3] = var.getO();
                stdUnit[3] = "บาท/ไร่";

                break;

            case "H":

                stdName = new String[3];
                stdValue = new String[3];
                stdUnit = new String[3];

                Log.d("displayStandardValue", CalculateConstant.CALCULATE_STANDARD_CONST_H.get("F") + " : " + var.getF());
                stdName[0] = CalculateConstant.CALCULATE_STANDARD_CONST_DEF.get("F");
                stdValue[0] = var.getF();
                stdUnit[0] = "บาท/ไร่";

                Log.d("displayStandardValue", CalculateConstant.CALCULATE_STANDARD_CONST_H.get("B") + " : " + var.getB());
                stdName[1] = CalculateConstant.CALCULATE_STANDARD_CONST_DEF.get("B");
                stdValue[1] = var.getB();
                stdUnit[1] = "บาท/ไร่";

                Log.d("displayStandardValue", CalculateConstant.CALCULATE_STANDARD_CONST_H.get("D") + " : " + var.getD());
                stdName[2] = CalculateConstant.CALCULATE_STANDARD_CONST_DEF.get("D");
                stdValue[2] = var.getD();
                stdUnit[2] = "บาท/ไร่";

                break;

            case "G":

                stdName = new String[6];
                stdValue = new String[6];
                stdUnit = new String[6];

                stdName[0] = CalculateConstant.CALCULATE_STANDARD_CONST_G.get("F");
                stdValue[0] = var.getF();
                stdUnit[0] = "บาท/ไร่";

                stdName[1] = CalculateConstant.CALCULATE_STANDARD_CONST_G.get("B");
                stdValue[1] = var.getB();
                stdUnit[1] = "บาท/ไร่";

                stdName[2] = CalculateConstant.CALCULATE_STANDARD_CONST_G.get("DH");
                stdValue[2] = var.getDH();
                stdUnit[2] = "บาท/ไร่";

                stdName[3] = CalculateConstant.CALCULATE_STANDARD_CONST_G.get("DD");
                stdValue[3] = var.getDD();
                stdUnit[3] = "บาท/ไร่";

                stdName[4] = CalculateConstant.CALCULATE_STANDARD_CONST_G.get("OH");
                stdValue[4] = var.getOH();
                stdUnit[4] = "บาท/ไร่";

                stdName[5] = CalculateConstant.CALCULATE_STANDARD_CONST_G.get("OD");
                stdValue[5] = var.getOD();
                stdUnit[5] = "บาท/ไร่";

                break;

            case "I":
            case "J":
            case "K":

                stdName = new String[4];
                stdValue = new String[4];
                stdUnit = new String[4];

                stdName[0] = CalculateConstant.CALCULATE_STANDARD_CONST_IJK.get("F");
                stdValue[0] = var.getF();
                stdUnit[0] = "บาท/ไร่";

                stdName[1] = CalculateConstant.CALCULATE_STANDARD_CONST_IJK.get("B");
                stdValue[1] = var.getB();
                stdUnit[1] = "บาท/ไร่";

                if ("1".equalsIgnoreCase(fisheryType)) {
                    stdName[2] = CalculateConstant.CALCULATE_STANDARD_CONST_IJK.get("DP");
                    stdValue[2] = var.getDP();
                    stdUnit[2] = "บาท/ไร่";

                    stdName[3] = CalculateConstant.CALCULATE_STANDARD_CONST_IJK.get("OP");
                    stdValue[3] = var.getOP();
                    stdUnit[3] = "บาท/ไร่";
                } else {
                    stdName[2] = CalculateConstant.CALCULATE_STANDARD_CONST_IJK.get("DB");
                    stdValue[2] = var.getDP();
                    stdUnit[2] = "บาท/ไร่";

                    stdName[3] = CalculateConstant.CALCULATE_STANDARD_CONST_IJK.get("OB");
                    stdValue[3] = var.getOP();
                    stdUnit[3] = "บาท/ไร่";
                }

                break;
        }

        standardList = (ListView) v.findViewById(R.id.listView);
        standardList.setAdapter(new ProductDetailStandardListCustomAdapter(context , productType , stdName,stdValue,stdUnit));

    }

    private void setAction() {

        v.findViewById(R.id.btnGetVariable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("setAction", "onClick: btnRecalculate");
                API_getPlotDetail(plodID);
            }
        });
    }


    public void onClick(View v){
        if(v.getId() == R.id.btnRecalculate){
            API_getPlotDetail(plodID);
        }
    }


}
