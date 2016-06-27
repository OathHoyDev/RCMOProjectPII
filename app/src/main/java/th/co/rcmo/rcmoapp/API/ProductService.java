package th.co.rcmo.rcmoapp.API;

import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import th.co.rcmo.rcmoapp.Model.STDVarModel;
import th.co.rcmo.rcmoapp.Model.calculate.FormulaAModel;
import th.co.rcmo.rcmoapp.Module.mGetVariable;
import th.co.rcmo.rcmoapp.Module.mVarPlanA;
import th.co.rcmo.rcmoapp.ProductDetailStandardListCustomAdapter;
import th.co.rcmo.rcmoapp.R;
import th.co.rcmo.rcmoapp.Util.CalculateConstant;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;

/**
 * Created by Taweesin on 25/6/2559.
 */
public class ProductService {
   static String TAG = "displayStandardValue";
   static String[] B_StdDisplay = {"D","O","CS"};
   static String[] C_StdDisplay = {"D","O","CS","CA"};
   static String[] F_StdDisplay = {"D","O"};
   static String[] H_StdDisplay = {"D"};
   static String[] G_StdDisplay = {"DH","DD","OH","OD"};
   static String[] KB_StdDisplay = {"DP","OP"};
   static String[] KC_StdDisplay = {"DB","OB"};

    public static  List<STDVarModel>  prepareSTDVarList(mGetVariable.mRespBody var , String fisheryType){

        Log.d("prepareSTDVarList", "FormularCode : " + var.getFormularCode()+" fisheryType :"+fisheryType);

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
                if(ServiceInstance.FISHERY_TYPE_KC.equals(fisheryType)) {
                    keyDisplay_Type = KC_StdDisplay;
                }else{
                    keyDisplay_Type = KB_StdDisplay;
                }

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

    public static String genJsonPlanAVariable(FormulaAModel aModel){
        mVarPlanA  varA = new mVarPlanA();

        varA.setAttraDokbia(String.valueOf(aModel.AttraDokbia));
        varA.setKaDoolae(String.valueOf(aModel.KaDoolae));
        varA.setKaGebGeaw(String.valueOf(aModel.KaGebGeaw));
        varA.setKaNardPlangTDin(String.valueOf(aModel.KaNardPlangTDin));

        varA.setKaPan(String.valueOf(aModel.KaPan));
        varA.setKaPluk(String.valueOf(aModel.KaPluk));
        varA.setKaPuy(String.valueOf(aModel.KaPuy));
        varA.setKaRang(String.valueOf(aModel.KaRang));

        varA.setKaSermOuppakorn(String.valueOf(aModel.KaSermOuppakorn));
        varA.setKaSiaOkardLongtoon(String.valueOf(aModel.KaSiaOkardLongtoon));
        varA.setKaSiaOkardOuppakorn(String.valueOf(aModel.KaSiaOkardOuppakorn));
        varA.setKaTreamDin(String.valueOf(aModel.KaTreamDin));

        varA.setKaWassadu(String.valueOf(aModel.KaWassadu));
        varA.setKaWassaduUn(String.valueOf(aModel.KaWassaduUn));
        varA.setKaYaplab(String.valueOf(aModel.KaYaplab));
        varA.setRaka(String.valueOf(aModel.predictPrice));

        varA.setPonPalid(String.valueOf(aModel.PonPalid));
        varA.setKaChaoTDin(String.valueOf(aModel.KaChaoTDin));


        //jsonPlanA = TextUtils.htmlEncode(jsonPlanA);
       String jsonPlanA =(new Gson().toJson(varA));
        String value = "";
       try {

           byte ptext[] = jsonPlanA.getBytes("ISO-8859-1");
           value = new String(ptext, "UTF-8");
           Log.d("Test "," -----------------------------> "+value);
       }catch(Exception e){
         e.printStackTrace();
       }

        Log.d("GSON","Json -> "+jsonPlanA);

        return value;
    }

}
