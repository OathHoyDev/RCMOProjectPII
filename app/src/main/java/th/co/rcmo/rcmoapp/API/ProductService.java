package th.co.rcmo.rcmoapp.API;

import android.util.Log;
import android.widget.ListView;

import th.co.rcmo.rcmoapp.Model.STDVarModel;
import th.co.rcmo.rcmoapp.Module.mGetVariable;
import th.co.rcmo.rcmoapp.ProductDetailStandardListCustomAdapter;
import th.co.rcmo.rcmoapp.R;
import th.co.rcmo.rcmoapp.Util.CalculateConstant;

/**
 * Created by Taweesin on 25/6/2559.
 */
public class ProductService {
   static String TAG = "displayStandardValue";
    /*
    public static STDVarModel prepareSTDVarList(mGetVariable.mRespBody var , String fisheryType){

        Log.d("prepareSTDVarList", "FormularCode : " + var.getFormularCode());

        String formularCode = var.getFormularCode();

        switch (formularCode) {

            case "A":
            case "B":

                stdName = new String[3];
                stdValue = new String[3];
                stdUnit = new String[3];

                Log.d(TAG, CalculateConstant.CALCULATE_STANDARD_CONST_AB.get("D") + " : " + var.getD());
                stdName[0] = CalculateConstant.CALCULATE_STANDARD_CONST_AB.get("D");
                stdValue[0] = var.getD();
                stdUnit[0] = "บาท/ไร่";

                Log.d("TAG, CalculateConstant.CALCULATE_STANDARD_CONST_AB.get("O") + " : " + var.getO());
                stdName[1] = CalculateConstant.CALCULATE_STANDARD_CONST_AB.get("O");
                stdValue[1] = var.getO();
                stdUnit[1] = "บาท/ไร่";

                Log.d(TAG, CalculateConstant.CALCULATE_STANDARD_CONST_AB.get("CS") + " : " + var.getCS());
                stdName[2] = CalculateConstant.CALCULATE_STANDARD_CONST_AB.get("CS");
                stdValue[2] = var.getCS();
                stdUnit[2] = "บาท/ไร่";

                break;

            case "C":
                stdName = new String[4];
                stdValue = new String[4];
                stdUnit = new String[4];

                Log.d(TAG, CalculateConstant.CALCULATE_STANDARD_CONST_C.get("D") + " : " + var.getD());
                stdName[0] = CalculateConstant.CALCULATE_STANDARD_CONST_C.get("D");
                stdValue[0] = var.getD();
                stdUnit[0] = "บาท/ไร่";

                Log.d(TAG, CalculateConstant.CALCULATE_STANDARD_CONST_C.get("O") + " : " + var.getO());
                stdName[1] = CalculateConstant.CALCULATE_STANDARD_CONST_C.get("O");
                stdValue[1] = var.getO();
                stdUnit[1] = "บาท/ไร่";

                Log.d(TAG, CalculateConstant.CALCULATE_STANDARD_CONST_C.get("CS") + " : " + var.getCS());
                stdName[2] = CalculateConstant.CALCULATE_STANDARD_CONST_C.get("CS");
                stdValue[2] = var.getCS();
                stdUnit[2] = "บาท/ไร่";

                Log.d(TAG, CalculateConstant.CALCULATE_STANDARD_CONST_C.get("CA") + " : " + var.getCA());
                stdName[3] = CalculateConstant.CALCULATE_STANDARD_CONST_C.get("CA");
                stdValue[3] = var.getCA();
                stdUnit[3] = "บาท/ไร่";
                break;

            case "D":
            case "E":
            case "F":

                stdName = new String[2];
                stdValue = new String[2];
                stdUnit = new String[2];


                Log.d(TAG, CalculateConstant.CALCULATE_STANDARD_CONST_DEF.get("D") + " : " + var.getD());
                stdName[0] = CalculateConstant.CALCULATE_STANDARD_CONST_DEF.get("D");
                stdValue[0] = var.getD();
                stdUnit[0] = "บาท/ไร่";

                Log.d(TAG, CalculateConstant.CALCULATE_STANDARD_CONST_DEF.get("O") + " : " + var.getO());
                stdName[0] = CalculateConstant.CALCULATE_STANDARD_CONST_DEF.get("O");
                stdValue[0] = var.getO();
                stdUnit[0] = "บาท/ไร่";

                break;

            case "H":

                stdName = new String[1];
                stdValue = new String[1];
                stdUnit = new String[1];

       Log.d(TAG, CalculateConstant.CALCULATE_STANDARD_CONST_H.get("D") + " : " + var.getD());
                stdName[0] = CalculateConstant.CALCULATE_STANDARD_CONST_DEF.get("D");
                stdValue[0] = var.getD();
                stdUnit[0] = "บาท/ไร่";

                break;

            case "G":

                stdName = new String[4];
                stdValue = new String[4];
                stdUnit = new String[4];

                stdName[0] = CalculateConstant.CALCULATE_STANDARD_CONST_G.get("DH");
                stdValue[0] = var.getDH();
                stdUnit[0] = "บาท/ไร่";

                stdName[1] = CalculateConstant.CALCULATE_STANDARD_CONST_G.get("DD");
                stdValue[1] = var.getDD();
                stdUnit[1] = "บาท/ไร่";

                stdName[2] = CalculateConstant.CALCULATE_STANDARD_CONST_G.get("OH");
                stdValue[2] = var.getOH();
                stdUnit[2] = "บาท/ไร่";

                stdName[3] = CalculateConstant.CALCULATE_STANDARD_CONST_G.get("OD");
                stdValue[3] = var.getOD();
                stdUnit[3] = "บาท/ไร่";

                break;

            case "I":
            case "J":
            case "K":

                stdName = new String[2];
                stdValue = new String[2];
                stdUnit = new String[2];


                if ("1".equalsIgnoreCase(fisheryType)) {
                    stdName[0] = CalculateConstant.CALCULATE_STANDARD_CONST_IJK.get("DP");
                    stdValue[0] = var.getDP();
                    stdUnit[0] = "บาท/ไร่";

                    stdName[1] = CalculateConstant.CALCULATE_STANDARD_CONST_IJK.get("OP");
                    stdValue[1] = var.getOP();
                    stdUnit[1] = "บาท/ไร่";
                } else {
                    stdName[0] = CalculateConstant.CALCULATE_STANDARD_CONST_IJK.get("DB");
                    stdValue[0] = var.getDP();
                    stdUnit[0] = "บาท/ไร่";

                    stdName[1] = CalculateConstant.CALCULATE_STANDARD_CONST_IJK.get("OB");
                    stdValue[1] = var.getOP();
                    stdUnit[1] = "บาท/ไร่";
                }

                break;
        }

        //standardList = (ListView) v.findViewById(R.id.listView);
        //standardList.setAdapter(new ProductDetailStandardListCustomAdapter(context , productType , stdName,stdValue,stdUnit));

    }
    */

}
