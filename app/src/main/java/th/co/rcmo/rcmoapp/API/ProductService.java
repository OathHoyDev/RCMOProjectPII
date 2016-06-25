package th.co.rcmo.rcmoapp.API;

import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

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
   static String[] B_StdDisplay = {"D","O","CS"};
   static String[] C_StdDisplay = {"D","O","CS","CA"};
   static String[] F_StdDisplay = {"D","O"};
   static String[] H_StdDisplay = {"O"};
   static String[] G_StdDisplay = {"DH","DD","OH","OD"};
   static String[] K_StdDisplay = {"DP","OP","DB","OB"};

    public static  List<STDVarModel>  prepareSTDVarList(mGetVariable.mRespBody var , String fisheryType){

        Log.d("prepareSTDVarList", "FormularCode : " + var.getFormularCode());

        String formularCode = var.getFormularCode();

       List<STDVarModel> stdVarModelList= new ArrayList<STDVarModel>();
       String tempVar[] = null;
       java.lang.reflect.Method method;;
       String varValue = "";
       String[] keyDisplay_Type = null;
       Hashtable hashValue_Type = null;
       //tempVar[0] = variable name in thai
       //tempVar[1] = variable unit in thai

        switch (formularCode) {

            case "A":
            case "B":
               keyDisplay_Type = B_StdDisplay;
               hashValue_Type  = CalculateConstant.PB_CALCULATE_STANDARD_CONST_AB;
               break;

            case "C":
               keyDisplay_Type = C_StdDisplay;
               hashValue_Type  = CalculateConstant.PB_CALCULATE_STANDARD_CONST_C;
               break;

            case "D":
            case "E":
            case "F":

               keyDisplay_Type = F_StdDisplay;
               hashValue_Type  = CalculateConstant.PB_CALCULATE_STANDARD_CONST_DEF;
               break;

            case "H":

               keyDisplay_Type = H_StdDisplay;
               hashValue_Type  = CalculateConstant.PB_CALCULATE_STANDARD_CONST_DEF;
               break;

            case "G":

               keyDisplay_Type =G_StdDisplay;
               hashValue_Type  = CalculateConstant.PB_CALCULATE_STANDARD_CONST_G;
               break;

            case "I":
            case "J":
            case "K":
               keyDisplay_Type =K_StdDisplay;
               hashValue_Type  =CalculateConstant.PB_CALCULATE_STANDARD_CONST_IJK;

                break;
        }

       if(keyDisplay_Type != null && hashValue_Type != null) {
          for (String key : keyDisplay_Type) {
             try {
                method = var.getClass().getMethod("get" + key);
                varValue = (String) method.invoke(var);
             } catch (Exception e) {
                varValue = "";
             }
             Log.d(TAG, ((String[])(hashValue_Type.get(key)))[0] + " : " + varValue);
              Log.d(TAG, "Key  : " +key);
             tempVar = (String[]) hashValue_Type.get(key);
             stdVarModelList.add(new STDVarModel(tempVar[0], varValue, tempVar[1]));
          }
       }else{
          stdVarModelList.add(new STDVarModel("ขออภัยอยู่ระหว่างพัฒนา","", ""));
       }
        //standardList = (ListView) v.findViewById(R.id.listView);
        //standardList.setAdapter(new ProductDetailStandardListCustomAdapter(context , productType , stdName,stdValue,stdUnit));
      return stdVarModelList;
    }


}
