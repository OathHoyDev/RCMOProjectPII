package th.go.oae.rcmo.API;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import th.go.oae.rcmo.Model.STDVarModel;
import th.go.oae.rcmo.Model.calculate.FormulaAModel;
import th.go.oae.rcmo.Model.calculate.FormulaBModel;
import th.go.oae.rcmo.Model.calculate.FormulaCModel;
import th.go.oae.rcmo.Model.calculate.FormulaDModel;
import th.go.oae.rcmo.Model.calculate.FormulaEModel;
import th.go.oae.rcmo.Model.calculate.FormulaFModel;
import th.go.oae.rcmo.Model.calculate.FormulaGModel;
import th.go.oae.rcmo.Model.calculate.FormulaHModel;
import th.go.oae.rcmo.Model.calculate.FormulaIModel;
import th.go.oae.rcmo.Model.calculate.FormulaJModel;
import th.go.oae.rcmo.Model.calculate.FormulaKModel;
import th.go.oae.rcmo.Module.mGetVariable;
import th.go.oae.rcmo.Module.mVarPlanA;
import th.go.oae.rcmo.Module.mVarPlanB;
import th.go.oae.rcmo.Module.mVarPlanC;
import th.go.oae.rcmo.Module.mVarPlanD;
import th.go.oae.rcmo.Module.mVarPlanE;
import th.go.oae.rcmo.Module.mVarPlanF;
import th.go.oae.rcmo.Module.mVarPlanG;
import th.go.oae.rcmo.Module.mVarPlanH;
import th.go.oae.rcmo.Module.mVarPlanI;
import th.go.oae.rcmo.Module.mVarPlanJ;
import th.go.oae.rcmo.Module.mVarPlanK;
import th.go.oae.rcmo.Util.CalculateConstant;
import th.go.oae.rcmo.Util.ServiceInstance;
import th.go.oae.rcmo.Util.Util;

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
               hashValue_Type  = CalculateConstant.PB_CALCULATE_STANDARD_CONST_H;
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

    public static String genJsonPlanVariable(FormulaAModel aModel){
        mVarPlanA  varA = new mVarPlanA();
        varA.setAttraDokbia(Util.dobbleToStringNumber(aModel.AttraDokbia));
        varA.setKaDoolae(Util.dobbleToStringNumber(aModel.KaDoolae));
        varA.setKaGebGeaw(Util.dobbleToStringNumber(aModel.KaGebGeaw));
       // varA.setKaNardPlangTDin(aModel.KaNardPlangTDin);
        varA.setKaPan(Util.dobbleToStringNumber(aModel.KaPan));
        varA.setKaPluk(Util.dobbleToStringNumber(aModel.KaPluk));
        varA.setKaPuy(Util.dobbleToStringNumber(aModel.KaPuy));
        varA.setKaRang(Util.dobbleToStringNumber(aModel.KaRang));
        varA.setKaSermOuppakorn(Util.dobbleToStringNumber(aModel.KaSermOuppakorn));
        varA.setKaSiaOkardLongtoon(Util.dobbleToStringNumber(aModel.KaSiaOkardLongtoon));
        varA.setKaSiaOkardOuppakorn(Util.dobbleToStringNumber(aModel.KaSiaOkardOuppakorn));
        varA.setKaTreamDin(Util.dobbleToStringNumber(aModel.KaTreamDin));
        varA.setKaWassadu(Util.dobbleToStringNumber(aModel.KaWassadu));
        varA.setKaWassaduUn(Util.dobbleToStringNumber(aModel.KaWassaduUn));
        varA.setKaYaplab(Util.dobbleToStringNumber(aModel.KaYaplab));
        varA.setRaka(Util.dobbleToStringNumber(aModel.predictPrice));
        varA.setPonPalid(Util.dobbleToStringNumber(aModel.PonPalid));
        varA.setKaChoaTDin(Util.dobbleToStringNumber(aModel.KaChaoTDin));
        varA.setCalIncludeOption(aModel.isCalIncludeOption);

        //jsonPlanA = TextUtils.htmlEncode(jsonPlanA);
       String json =(new Gson().toJson(varA));
        String value = "";
       try {

           byte ptext[] = json.getBytes("ISO-8859-1");
           value = new String(ptext, "UTF-8");
           Log.d("Test "," -----------------------------> "+value);
       }catch(Exception e){
         e.printStackTrace();
       }

        Log.d("GSON","Json -> "+json);

        return value;
    }

    public static String genJsonPlanVariable(FormulaBModel model){
        mVarPlanB var = new mVarPlanB();
        var.setJumNoun(Util.dobbleToStringNumber(model.Year));
        var.setAttraDokbia(Util.dobbleToStringNumber(model.AttraDokbia));
        var.setKaDoolae(Util.dobbleToStringNumber(model.KaDoolae));
        var.setKaGebGeaw(Util.dobbleToStringNumber(model.KaGebGeaw));
       // var.setKaNardPlangTDin(model.KaNardPlangTDin);
        var.setKaPan(Util.dobbleToStringNumber(model.KaPan));
        var.setKaPluk(Util.dobbleToStringNumber(model.KaPluk));
        var.setKaPuy(Util.dobbleToStringNumber(model.KaPuy));
        var.setKaRang(Util.dobbleToStringNumber(model.KaRang));
        var.setKaSermOuppakorn(Util.dobbleToStringNumber(model.KaSermOuppakorn));
        var.setKaSiaOkardLongtoon(Util.dobbleToStringNumber(model.KaSiaOkardLongtoon));
        var.setKaSiaOkardOuppakorn(Util.dobbleToStringNumber(model.KaSiaOkardOuppakorn));
        var.setKaTreamDin(Util.dobbleToStringNumber(model.KaTreamDin));
        var.setKaWassadu(Util.dobbleToStringNumber(model.KaWassadu));
        var.setKaWassaduUn(Util.dobbleToStringNumber(model.KaWassaduUn));
        var.setKaYaplab(Util.dobbleToStringNumber(model.KaYaplab));
        var.setRaka(Util.dobbleToStringNumber(model.predictPrice));
        var.setPonPalid(Util.dobbleToStringNumber(model.PonPalid));
        var.setKaChoaTDin(Util.dobbleToStringNumber(model.KaChaoTDin));
        var.setCalIncludeOption(model.isCalIncludeOption);


        //jsonPlanA = TextUtils.htmlEncode(jsonPlanA);
        String json =(new Gson().toJson(var));
        String value = "";
        try {

            byte ptext[] = json.getBytes("ISO-8859-1");
            value = new String(ptext, "UTF-8");
            Log.d("Test "," -----------------------------> "+value);
        }catch(Exception e){
            e.printStackTrace();
        }

        Log.d("GSON","Json -> "+json);

        return value;
    }

    public static String genJsonPlanVariable(FormulaCModel model){
        mVarPlanC var = new mVarPlanC();

        var.setAttraDokbia(Util.dobbleToStringNumber(model.AttraDokbia));
        var.setKaDoolae(Util.dobbleToStringNumber(model.KaDoolae));
        var.setKaGebGeaw(Util.dobbleToStringNumber(model.KaGebGeaw));
        //var.setKaNardPlangTDin(model.KaNardPlangTDin);
        var.setKaPan(Util.dobbleToStringNumber(model.KaPan));
        var.setKaPluk(Util.dobbleToStringNumber(model.KaPluk));
        var.setKaPuy(Util.dobbleToStringNumber(model.KaPuy));
        var.setKaRang(Util.dobbleToStringNumber(model.KaRang));
        var.setKaSermOuppakorn(Util.dobbleToStringNumber(model.KaSermOuppakorn));
        var.setKaSiaOkardLongtoon(Util.dobbleToStringNumber(model.KaSiaOkardLongtoon));
        var.setKaSiaOkardOuppakorn(Util.dobbleToStringNumber(model.KaSiaOkardOuppakorn));
        var.setKaTreamDin(Util.dobbleToStringNumber(model.KaTreamDin));
        var.setKaWassadu(Util.dobbleToStringNumber(model.KaWassadu));
        var.setKaWassaduUn(Util.dobbleToStringNumber(model.KaWassaduUn));
        var.setKaYaplab(Util.dobbleToStringNumber(model.KaYaplab));
        var.setRaka(Util.dobbleToStringNumber(model.predictPrice));
        var.setPonPalid(Util.dobbleToStringNumber(model.PonPalid));
        var.setKaChoaTDin(Util.dobbleToStringNumber(model.KaChaoTDin));
        var.setCalIncludeOption(model.isCalIncludeOption);



        //jsonPlanA = TextUtils.htmlEncode(jsonPlanA);
        String jsonPlanA =(new Gson().toJson(var));
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

    public static String genJsonPlanVariable(FormulaDModel model){
        mVarPlanD var = new mVarPlanD();
        var.KaAHan      = Util.dobbleToStringNumber(model.KaAHan);
        var.KaYa        = Util.dobbleToStringNumber(model.KaYa);
        var.KaRangGgan  =  Util.dobbleToStringNumber(model.KaRangGgan);
        var.KaNamKaFai  = Util.dobbleToStringNumber(model.KaNamKaFai);
        var.KaNamMan    = Util.dobbleToStringNumber(model.KaNamMan);
        var.KaWassaduSinPleung =  Util.dobbleToStringNumber(model.KaWassaduSinPleung) ;
        var.KaSomRongRaun = Util.dobbleToStringNumber(model.KaSomRongRaun) ;
        var.KaChoaTDin = Util.dobbleToStringNumber(model.KaChoaTDin);

        var.NamNakChaLia = Util.dobbleToStringNumber(model.NamNakChaLia);
        var.JumNounTuaTKai = Util.dobbleToStringNumber(model.JumNounTuaTKai);
        var.RakaTKai =  Util.dobbleToStringNumber(model.RakaTKai);
        var.RaYaWeRaLeang = Util.dobbleToStringNumber(model.RaYaWeRaLeang);

        //var.RermLeang = model.RermLeang;
       // var.RakaReamLeang =  model.RakaReamLeang;
        var.setCalIncludeOption(model.isCalIncludeOption);


        //jsonPlanA = TextUtils.htmlEncode(jsonPlanA);
        String json =(new Gson().toJson(var));
        String value = "";
        try {

            byte ptext[] = json.getBytes("ISO-8859-1");
            value = new String(ptext, "UTF-8");
            Log.d("Test "," -----------------------------> "+value);
        }catch(Exception e){
            e.printStackTrace();
        }

        Log.d("GSON","Json -> "+json);

        return value;
    }

    public static String genJsonPlanVariable(FormulaEModel model){
        mVarPlanE var = new mVarPlanE();
        var.KaAHan      = Util.dobbleToStringNumber(model.KaAHan);
        var.KaYa        = Util.dobbleToStringNumber(model.KaYa);
        var.KaRangGgan  =  Util.dobbleToStringNumber(model.KaRangGgan);
        var.KaNamKaFai  = Util.dobbleToStringNumber(model.KaNamKaFai);
        var.KaNamMan    = Util.dobbleToStringNumber(model.KaNamMan);
        var.KaWassaduSinPleung =  Util.dobbleToStringNumber(model.KaWassaduSinPleung);
        var.KaSomRongRaun = Util.dobbleToStringNumber(model.KaSomRongRaun);
        var.KaChoaTDin = Util.dobbleToStringNumber(model.KaChoaTDin);

        var.RakaTKai =  Util.dobbleToStringNumber(model.RakaTKai) ;
        var.RaYaWeRaLeang = Util.dobbleToStringNumber(model.RaYaWeRaLeang);

       // var.RermLeang = model.RermLeang;
        //var.RakaReamLeang =  model.RakaReamLeang;
        var.setCalIncludeOption(model.isCalIncludeOption);


        //jsonPlanA = TextUtils.htmlEncode(jsonPlanA);
        String json =(new Gson().toJson(var));
        String value = "";
        try {

            byte ptext[] = json.getBytes("ISO-8859-1");
            value = new String(ptext, "UTF-8");
            Log.d("Test "," -----------------------------> "+value);
        }catch(Exception e){
            e.printStackTrace();
        }

        Log.d("GSON","Json -> "+json);

        return value;
    }


    public static String genJsonPlanVariable(FormulaFModel model){
        mVarPlanF var = new mVarPlanF();
        var.KaAHan = Util.dobbleToStringNumber(model.KaAHan);
        var.KaYa   = Util.dobbleToStringNumber(model.KaYa);
        var.KaRangGgan = Util.dobbleToStringNumber(model.KaRangGgan);
        var.KaNamKaFai = Util.dobbleToStringNumber(model.KaNamKaFai);
        var.KaNamMan = Util.dobbleToStringNumber(model.KaNamMan);
        var.KaWassaduSinPleung = Util.dobbleToStringNumber(model.KaWassaduSinPleung);
        var.KaSomRongRaun =Util.dobbleToStringNumber(model.KaSomRongRaun);
        var.KaChoaTDin = Util.dobbleToStringNumber(model.KaChoaTDin);

        var.KaiTDaiTangTaeRoem = Util.dobbleToStringNumber(model.KaiTDaiTangTaeRoem);
        var.RakaTKai = Util.dobbleToStringNumber(model.RakaTKai);
        var.PonPloyDai =   Util.dobbleToStringNumber(model.PonPloyDai);
        var.RaYaWeRaLeang = Util.dobbleToStringNumber(model.RaYaWeRaLeang);

        //var.RermLeang =model.RermLeang;
      //  var.RakaReamLeang =model.RakaReamLeang;
        var.setCalIncludeOption(model.isCalIncludeOption);


        //jsonPlanA = TextUtils.htmlEncode(jsonPlanA);
        String json =(new Gson().toJson(var));
        String value = "";
        try {

            byte ptext[] = json.getBytes("ISO-8859-1");
            value = new String(ptext, "UTF-8");
            Log.d("Test "," -----------------------------> "+value);
        }catch(Exception e){
            e.printStackTrace();
        }

        Log.d("GSON","Json -> "+json);

        return value;
    }


    public static String genJsonPlanVariable(FormulaHModel model){
        mVarPlanH var = new mVarPlanH();

        var.JumnuanTKai = Util.dobbleToStringNumber(model.JumnuanTKai);
        var.KaPan       = Util.dobbleToStringNumber(model.KaPan);
        var.KaAHanKon   = Util.dobbleToStringNumber(model.KaAHanKon);
        var.KaAKanYab   = Util.dobbleToStringNumber(model.KaAKanYab);
        var.KaNamKaFai  = Util.dobbleToStringNumber(model.KaNamKaFai);
        var.KaYa        = Util.dobbleToStringNumber(model.KaYa);
        var.KaRang      = Util.dobbleToStringNumber(model.KaRang);
        var.KaNamKaFai  = Util.dobbleToStringNumber(model.KaNamKaFai);
        var.KaChoaTDin  = Util.dobbleToStringNumber(model.KaChoaTDin);
        var.KaSiaOkardLongtoon  = Util.dobbleToStringNumber(model.KaSiaOkardLongtoon);
        var.NumnukChalia = Util.dobbleToStringNumber(model.NumnukChalia);
        var.RakaChalia   = Util.dobbleToStringNumber(model.RakaChalia);
        var.RayaWera     = Util.dobbleToStringNumber(model.RayaWera);
       // var.RermLeang       = model.RermLeang;
      //  var.NumnukRermLeang = model.NumnukRermLeang;
        var.setCalIncludeOption(model.isCalIncludeOption);


        //jsonPlanA = TextUtils.htmlEncode(jsonPlanA);
        String json =(new Gson().toJson(var));
        String value = "";
        try {

            byte ptext[] = json.getBytes("ISO-8859-1");
            value = new String(ptext, "UTF-8");
            Log.d("Test "," -----------------------------> "+value);
        }catch(Exception e){
            e.printStackTrace();
        }

        Log.d("GSON","Json -> "+json);

        return value;
    }

    public static String genJsonPlanVariable(FormulaIModel model){
        mVarPlanI var = new mVarPlanI();


        var.RakaTuaLa = Util.dobbleToStringNumber(model.RakaTuaLa);
        var.KaAHan = Util.dobbleToStringNumber(model.KaAHan);
        var.KaYa = Util.dobbleToStringNumber(model.KaYa);
        var.KaSankemee = Util.dobbleToStringNumber(model.KaSankemee);
        var.KaNamMan = Util.dobbleToStringNumber(model.KaNamMan);
        var.KaFai = Util.dobbleToStringNumber(model.KaFai);
        var.KaRokRain = Util.dobbleToStringNumber(model.KaRokRain);
        var.KaLeang = Util.dobbleToStringNumber(model.KaLeang);
        var.KaJub = Util.dobbleToStringNumber(model.KaJub);
        var.KaSomsamOuppakorn = Util.dobbleToStringNumber(model.KaSomsamOuppakorn);
        var.KaChaiJay = Util.dobbleToStringNumber(model.KaChaiJay);
        var.KaChaoTDin = Util.dobbleToStringNumber(model.KaChaoTDin);

        var.PonPalidKung = Util.dobbleToStringNumber(model.PonPalidKung);
        var.RakaChalia = Util.dobbleToStringNumber(model.RakaChalia);
        var.RayaWelaTeeLeang = Util.dobbleToStringNumber(model.RayaWelaTeeLeang);

        var.setCalIncludeOption(model.isCalIncludeOption);



        //jsonPlanA = TextUtils.htmlEncode(jsonPlanA);
        String json =(new Gson().toJson(var));
        String value = "";
        try {

            byte ptext[] = json.getBytes("ISO-8859-1");
            value = new String(ptext, "UTF-8");
            Log.d("Test "," -----------------------------> "+value);
        }catch(Exception e){
            e.printStackTrace();
        }

        Log.d("GSON","Json -> "+json);

        return value;
    }

    public static String genJsonPlanVariable(FormulaJModel model){
        mVarPlanJ var = new mVarPlanJ();


        var.CalType = String.valueOf(model.CalType);
        var.Raka = Util.dobbleToStringNumber(model.Raka);
        var.KaAHan = Util.dobbleToStringNumber(model.KaAHan);
        var.KaRangNganLeang =Util.dobbleToStringNumber( model.KaRangNganLeang);
        var.KaRangNganJub = Util.dobbleToStringNumber(model.KaRangNganJub);
        var.KaYa = Util.dobbleToStringNumber(model.KaYa);
        var.KaSanKMe = Util.dobbleToStringNumber(model.KaSanKMe);
        var.KaNamman = Util.dobbleToStringNumber(model.KaNamman);
        var.KaFaifa = Util.dobbleToStringNumber(model.KaFaifa);
        var.KaLoklen = Util.dobbleToStringNumber(model.KaLoklen);
        var.KaSomSam = Util.dobbleToStringNumber(model.KaSomSam);
        var.KaChaijai = Util.dobbleToStringNumber(model.KaChaijai);
        var.KaChoaTDin = Util.dobbleToStringNumber(model.KaChoaTDin);
        var.RayaWela = Util.dobbleToStringNumber(model.RayaWela);

        var.NamnakTKai = Util.dobbleToStringNumber(model.NamnakTKai);
        var.RakaTKai =Util.dobbleToStringNumber( model.RakaTKai);
        //var.KanardPlaChalia = model.KanardPlaChalia;

        var.KanardPla1 =Util.dobbleToStringNumber( model.KanardPla1);
        var.NamnakPla1 = Util.dobbleToStringNumber(model.NamnakPla1);
        var.RakaPla1 = Util.dobbleToStringNumber(model.RakaPla1);

        var.KanardPla2 = Util.dobbleToStringNumber(model.KanardPla2);
        var.NamnakPla2 = Util.dobbleToStringNumber(model.NamnakPla2);
        var.RakaPla2 = Util.dobbleToStringNumber(model.RakaPla2);

        var.KanardPla3 = Util.dobbleToStringNumber(model.KanardPla3);
        var.NamnakPla3 = Util.dobbleToStringNumber(model.NamnakPla3);
        var.RakaPla3 = Util.dobbleToStringNumber(model.RakaPla3);

        var.KanardPla4 =Util.dobbleToStringNumber( model.KanardPla4);
        var.NamnakPla4 = Util.dobbleToStringNumber(model.NamnakPla4);
        var.RakaPla4 = Util.dobbleToStringNumber(model.RakaPla4);

        var.setCalIncludeOption(model.isCalIncludeOption);

        //jsonPlanA = TextUtils.htmlEncode(jsonPlanA);
        String json =(new Gson().toJson(var));
        String value = "";
        try {

            byte ptext[] = json.getBytes("ISO-8859-1");
            value = new String(ptext, "UTF-8");
            Log.d("Test "," -----------------------------> "+value);
        }catch(Exception e){
            e.printStackTrace();
        }

        Log.d("GSON","Json -> "+json);

        return value;
    }

    public static String genJsonPlanVariable(FormulaKModel model){
        mVarPlanK var = new mVarPlanK();

        var.CalType = model.CalType;
        var.TuaOrKilo = model.TuaOrKilo;
        var.CustomSize = model.CustomSize;

        var.JumnounKachang = model.JumnounKachang;
        var.KanardKachang = model.KanardKaChang;
        var.LookPla = model.LookPla;

        var.Raka = model.Raka;
        var.KaAHan = model.KaAHan;
        var.KaRangNganLeang = model.KaRangNganLeang;
        var.KaRangNganJub = model.KaRangNganJub;
        var.KaYa = model.KaYa;
        var.KaSanKMe = model.KaSanKMe;
        var.KaNamman = model.KaNamman;
        var.KaFaifa = model.KaFaifa;
        var.KaSomSam = model.KaSomSam;
        var.KaChaijai = model.KaChaijai;
      //  var.KaChoaTDin = model.
        var.RayaWela = model.RayaWela;

        var.NamnakTKai = model.NamnakTKai;
        var.RakaTKai = model.RakaTKai;
        var.KanardPlaChalia = Util.verifyDoubleDefaultZero(model.KanardPlaChalia);

        var.KanardPla1 = model.KanardPla1;
        var.NamnakPla1 = model.NamnakPla1;
        var.RakaPla1 = model.RakaPla1;

        var.KanardPla2 = model.KanardPla2;
        var.NamnakPla2 = model.NamnakPla2;
        var.RakaPla2 = model.RakaPla2;

        var.KanardPla3 = model.KanardPla3;
        var.NamnakPla3 = model.NamnakPla3;
        var.RakaPla3 = model.RakaPla3;

        var.KanardPla4 = model.KanardPla4;
        var.NamnakPla4 = model.NamnakPla4;
        var.RakaPla4 = model.RakaPla4;

        var.setCalIncludeOption(model.isCalIncludeOption);

        //jsonPlanA = TextUtils.htmlEncode(jsonPlanA);
        String json =(new Gson().toJson(var));
        String value = "";
        try {

            byte ptext[] = json.getBytes("ISO-8859-1");
            value = new String(ptext, "UTF-8");
            Log.d("Test "," -----------------------------> "+value);
        }catch(Exception e){
            e.printStackTrace();
        }

        Log.d("GSON","Json -> "+json);

        return value;
    }

    public static String genJsonPlanVariable(FormulaGModel model){
        mVarPlanG var = new mVarPlanG();

        var.ParimanNumnom =  Util.dobbleToStringNumber(model.ParimanNumnom);

        var.KoRakRakGerd        =  Util.dobbleToStringNumber(model.KoRakRakGerd);
        var.Ko1_2               =  Util.dobbleToStringNumber(model.Ko1_2);
        var.Ko2                 =  Util.dobbleToStringNumber(model.Ko2);
        var.MaeKoReedNom        =  Util.dobbleToStringNumber(model.MaeKoReedNom);

        var.KaReedNom           =  Util.dobbleToStringNumber(model.KaReedNom);
        var.KaRangReang         =  Util.dobbleToStringNumber(model.KaRangReang);

        var.KaPasomPan          =  Util.dobbleToStringNumber(model.KaPasomPan);
        var.KaAHan              =  Util.dobbleToStringNumber(model.KaAHan);
        var.KaAHanYab           =  Util.dobbleToStringNumber(model.KaAHanYab);
        var.KaYa                =  Util.dobbleToStringNumber(model.KaYa);
        var.KaNamKaFai          =  Util.dobbleToStringNumber(model.KaNamKaFai);
        var.KaNamMan            =  Util.dobbleToStringNumber(model.KaNamMan);
        var.KaWassaduSinPleung  =  Util.dobbleToStringNumber(model.KaWassaduSinPleung);
        var.KaSomsamOuppakorn   =  Util.dobbleToStringNumber(model.KaSomsamOuppakorn);
        var.KaKonsong           =  Util.dobbleToStringNumber(model.KaKonsong);
        var.KaChaiJay           =  Util.dobbleToStringNumber(model.KaChaiJay);
        var.PerKaNamKaFai          =  Util.dobbleToStringNumber(model.PerKaNamKaFai);
        var.PerKaNamMan            =  Util.dobbleToStringNumber(model.PerKaNamMan);
        var.PerKaWassaduSinPleung  =  Util.dobbleToStringNumber(model.PerKaWassaduSinPleung);
        var.PerKaSomsamOuppakorn   =  Util.dobbleToStringNumber(model.PerKaSomsamOuppakorn);
        var.PerKaChaiJay           =  Util.dobbleToStringNumber(model.PerKaChaiJay);
        var.KaChaoTDin           =  Util.dobbleToStringNumber(model.KaChaoTDin);
        var.Raka                =  Util.dobbleToStringNumber(model.RakaTkai);
       // var.JumuanMaeKo          =  model.JumuanMaeKo;
        var.setCalIncludeOption(model.isCalIncludeOption);

        //jsonPlanA = TextUtils.htmlEncode(jsonPlanA);
        String json =(new Gson().toJson(var));
        String value = "";
        try {

            byte ptext[] = json.getBytes("ISO-8859-1");
            value = new String(ptext, "UTF-8");
            Log.d("Test "," -----------------------------> "+value);
        }catch(Exception e){
            e.printStackTrace();
        }

        Log.d("GSON","Json -> "+json);

        return value;
    }






}
