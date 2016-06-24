package th.co.rcmo.rcmoapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import th.co.rcmo.rcmoapp.API.RequestServices;
import th.co.rcmo.rcmoapp.API.ResponseAPI;
import th.co.rcmo.rcmoapp.Model.UserPlotModel;
import th.co.rcmo.rcmoapp.Model.calculate.FormulaJModel;
import th.co.rcmo.rcmoapp.Module.mSavePlotDetail;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;
import th.co.rcmo.rcmoapp.View.DialogChoice;

import th.co.rcmo.rcmoapp.Model.UserPlotModel;
import th.co.rcmo.rcmoapp.Model.calculate.FormulaDModel;

public class CalculateResultActivity extends Activity {
    String TAG ="CalculateResultActivity";
    String plotId;
    boolean saved = false;
   public static UserPlotModel userPlotModel =new UserPlotModel();

    public static FormulaDModel resultModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_result);
    }



    private void upsertUserPlot(){

        // API_GetUserPlot(userId,prdId,prdGrpId,plotId);
        SharedPreferences sp = getSharedPreferences(ServiceInstance.PREF_NAME, Context.MODE_PRIVATE);
        String userId = sp.getString(ServiceInstance.sp_userId, "0");
        userPlotModel.setUserID(userId);
        if(!userId.equals("0")) {
            if (!saved) {
                Log.d(TAG, "Go to save plot Module ");
                API_SavePlotDetail("1", userPlotModel);
            } else {
                Log.d(TAG, "Go to update plot Module ");
                API_SavePlotDetail("2", userPlotModel);

            }
        }else{
            new DialogChoice(CalculateResultActivity.this)
                    .ShowOneChoice("ไม่สามารถบันทึกข้อมูล", "- กรุณา Login ก่อนทำการบันทึกข้อมูล"); //save image
        }


    }

    private void API_SavePlotDetail(String saveFlag, UserPlotModel userPlotInfo) {
        /**
         * 1.SaveFlag (บังคับ)
         2.UserID (บังคับ)
         3.PlotID (บังคับกรณี SaveFlag = 2)
         4.PrdID (บังคับ)
         5.PrdGrpID(บังคับ)
         6.PlotRai
         7.PondRai
         8.PondNgan
         9.PondWa
         10.PondMeter
         11.CoopMeter
         12.CoopNumber
         13.TamCode
         14.AmpCode
         15.ProvCode
         16.AnimalNumber
         17.AnimalWeight
         18.AnimalPrice
         19.FisheryType
         20.FisheryNumType
         21.FisheryNumber
         22.FisheryWeight
         23.ImeiCode
         24.VarName
         25.VarValue

         SaveFlag = 1 บังคับใส่ 1 2 4 5
         SaveFlag = 2 บังคับใส่ 1 - 5

         */

        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mSavePlotDetail savePlotDetailInfo = (mSavePlotDetail) obj;
                List<mSavePlotDetail.mRespBody> savePlotDetailBodyLists = savePlotDetailInfo.getRespBody();

                Log.d(TAG,"---- >Size"+savePlotDetailBodyLists.size());
                if (savePlotDetailBodyLists.size() != 0) {

                    plotId =  String.valueOf(savePlotDetailBodyLists.get(0).getPlotID());
                    if(plotId==null){plotId="";}
                    userPlotModel.setPlotID(plotId);
                    Log.d(TAG,"Response plotId : "+plotId);
                    saved = true;
                    toastMsg("บันทึกข้อมูลสำเร็จ");
                }

            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d(TAG, errorMsg);
            }
        }).API_Request(true, RequestServices.ws_savePlotDetail +

                "?SaveFlag=" + saveFlag +
                "&UserID=" +userPlotInfo.getUserID()+
                "&PlotID=" +userPlotInfo.getPlotID()+
                "&PrdID=" + userPlotInfo.getPrdID() +
                "&PrdGrpID=" + userPlotInfo.getPrdGrpID() +
                "&PlotRai=" +userPlotInfo.getPlotRai() +
                "&PondRai=" +userPlotInfo.getPondRai()+
                "&PondNgan=" +userPlotInfo.getPondNgan()+
                "&PondWa=" +userPlotInfo.getPondWa()+
                "&PondMeter=" +userPlotInfo.getPondMeter()+
                "&CoopMeter=" +userPlotInfo.getCoopMeter()+
                "&CoopNumber=" +userPlotInfo.getCoopNumber()+
                "&TamCode=" +userPlotInfo.getTamCode()+
                "&AmpCode=" +userPlotInfo.getAmpCode()+
                "&ProvCode=" +userPlotInfo.getProvCode()+
                "&AnimalNumber=" +userPlotInfo.getAnimalNumber()+
                "&AnimalWeight=" +userPlotInfo.getAnimalWeight()+
                "&AnimalPrice=" +userPlotInfo.getAnimalPrice()+
                "&FisheryType=" +userPlotInfo.getFisheryType()+
                "&FisheryNumType=" +userPlotInfo.getFisheryNumType()+
                "&FisheryNumber=" +userPlotInfo.getFisheryNumber()+
                "&FisheryWeight=" +userPlotInfo.getFisheryWeight()+
                "&ImeiCode=" + ServiceInstance.GetDeviceID(CalculateResultActivity.this)+
                "&VarName=" +userPlotInfo.getVarName()+
                "&VarValue=" +userPlotInfo.getVarValue()+
                "&CalResult="+userPlotInfo.getCalResult()
        );
    }

    private void toastMsg(String msg) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);

        com.neopixl.pixlui.components.textview.TextView text = (com.neopixl.pixlui.components.textview.TextView) layout.findViewById(R.id.toast_label);
        text.setText(msg);

        toast.show();
    }
}
