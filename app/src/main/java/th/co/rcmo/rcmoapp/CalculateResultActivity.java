package th.co.rcmo.rcmoapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.neopixl.pixlui.components.button.Button;
import com.neopixl.pixlui.components.textview.TextView;

import java.util.List;

import th.co.rcmo.rcmoapp.API.RequestServices;
import th.co.rcmo.rcmoapp.API.ResponseAPI;
import th.co.rcmo.rcmoapp.Model.UserPlotModel;
import th.co.rcmo.rcmoapp.Model.calculate.CalculateResultModel;
import th.co.rcmo.rcmoapp.Module.mGetPlotSuit;
import th.co.rcmo.rcmoapp.Module.mSavePlotDetail;
import th.co.rcmo.rcmoapp.Util.CalculateConstant;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;
import th.co.rcmo.rcmoapp.View.DialogChoice;

public class CalculateResultActivity extends Activity {
    String TAG = "CalculateResultActivity";
    String plotId;
    boolean saved = false;
    String prdID;

    public static UserPlotModel userPlotModel;
    public static CalculateResultModel calculateResultModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_result);
        initialDetail();
    }



    private void initialDetail() {

        ImageView bgImage = (android.widget.ImageView) findViewById(R.id.productIconBG);
        TextView canterTextview = (TextView) findViewById(R.id.centerImg);
        TextView btnRecalculate = (TextView) findViewById(R.id.btnRecalculate);
        TextView btnSavePlotDetail = (TextView) findViewById(R.id.btnSavePlotDetail);

        TextView recommandLocation = (TextView) findViewById(R.id.recommandLocation);
        TextView recommandPrice = (TextView) findViewById(R.id.recommandPrice);

        TextView titleLable = (TextView) findViewById(R.id.titleLable);
        TextView productNameLabel = (TextView) findViewById(R.id.productNameLabel);

        TextView txProfitLoss = (TextView) findViewById(R.id.txProfitLoss);
        TextView txProfitLossValue = (TextView) findViewById(R.id.txProfitLossValue);

        switch (userPlotModel.getPrdGrpID()) {
            case CalculateConstant.PRODUCT_TYPE_PLANT:
                bgImage.setBackgroundResource(R.drawable.plant_ic_gr_circle_bg);
                btnRecalculate.setBackgroundResource(R.drawable.action_plant_recal);
                btnSavePlotDetail.setBackgroundResource(R.drawable.action_plant_reget);
                titleLable.setBackgroundColor(getResources().getColor(R.color.RcmoPlantBG));
                productNameLabel.setBackgroundColor(getResources().getColor(R.color.RcmoPlantDarkBG));
                canterTextview.setBackgroundResource(R.drawable.bottom_green_total);
                txProfitLossValue.setTextColor(getResources().getColor(R.color.RcmoPlantBG));
                break;
            case CalculateConstant.PRODUCT_TYPE_ANIMAL:
                bgImage.setBackgroundResource(R.drawable.animal_ic_gr_circle_bg);
                btnRecalculate.setBackgroundResource(R.drawable.action_animal_recal);
                btnSavePlotDetail.setBackgroundResource(R.drawable.action_animal_reget);
                titleLable.setBackgroundColor(getResources().getColor(R.color.RcmoAnimalBG));
                productNameLabel.setBackgroundColor(getResources().getColor(R.color.RcmoAnimalDarkBG));
                canterTextview.setBackgroundResource(R.drawable.bottom_pink_total);
                txProfitLossValue.setTextColor(getResources().getColor(R.color.RcmoAnimalBG));
                break;
            case CalculateConstant.PRODUCT_TYPE_FISH:
                bgImage.setBackgroundResource(R.drawable.fish_ic_gr_circle_bg);
                btnRecalculate.setBackgroundResource(R.drawable.action_fish_recal);
                btnSavePlotDetail.setBackgroundResource(R.drawable.action_fish_reget);
                titleLable.setBackgroundColor(getResources().getColor(R.color.RcmoFishBG));
                productNameLabel.setBackgroundColor(getResources().getColor(R.color.RcmoFishDarkBG));
                canterTextview.setBackgroundResource(R.drawable.bottom_blue_total);
                txProfitLossValue.setTextColor(getResources().getColor(R.color.RcmoFishBG));
                break;
        }

        productNameLabel.setText(calculateResultModel.productName);

        android.widget.ImageView productIcon = (android.widget.ImageView) findViewById(R.id.productIcon);

        String imgName = ServiceInstance.productIMGMap.get(Integer.parseInt(userPlotModel.getPrdID()));

        int imgIDInt = getResources().getIdentifier(imgName, "drawable", getPackageName());

        productIcon.setImageResource(imgIDInt);

        recommandLocation.setText(calculateResultModel.mPlotSuit.getSuitLabel());

        if (calculateResultModel.compareStdResult > 0){
            recommandPrice.setText("ต้นทุนเกินกว่าค่ามาตรฐาน");
        }else if (calculateResultModel.compareStdResult == 0) {
            recommandPrice.setText("ต้นทุนเทียบเท่าค่ามาตรฐาน");
        }else {
            recommandPrice.setText("ต้นทุนต่ำกว่าค่ามาตรฐาน");
        }

        if (calculateResultModel.calculateResult >= 0){
            txProfitLoss.setText("กำไร");
            txProfitLossValue.setText(String.format("%,.2f", calculateResultModel.calculateResult));
        }else{
            txProfitLoss.setText("ขาดทุน");
            txProfitLossValue.setText(String.format("%,.2f", calculateResultModel.calculateResult));
        }

    }


    private void upsertUserPlot() {

        // API_GetUserPlot(userId,prdId,prdGrpId,plotId);
        SharedPreferences sp = getSharedPreferences(ServiceInstance.PREF_NAME, Context.MODE_PRIVATE);
        String userId = sp.getString(ServiceInstance.sp_userId, "0");
        userPlotModel.setUserID(userId);
        if (!userId.equals("0")) {
            if (!saved) {
                Log.d(TAG, "Go to save plot Module ");
                API_SavePlotDetail("1", userPlotModel);
            } else {
                Log.d(TAG, "Go to update plot Module ");
                API_SavePlotDetail("2", userPlotModel);

            }
        } else {
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

                Log.d(TAG, "---- >Size" + savePlotDetailBodyLists.size());
                if (savePlotDetailBodyLists.size() != 0) {

                    plotId = String.valueOf(savePlotDetailBodyLists.get(0).getPlotID());
                    if (plotId == null) {
                        plotId = "";
                    }
                    userPlotModel.setPlotID(plotId);
                    Log.d(TAG, "Response plotId : " + plotId);
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
                "&UserID=" + userPlotInfo.getUserID() +
                "&PlotID=" + userPlotInfo.getPlotID() +
                "&PrdID=" + userPlotInfo.getPrdID() +
                "&PrdGrpID=" + userPlotInfo.getPrdGrpID() +
                "&PlotRai=" + userPlotInfo.getPlotRai() +
                "&PondRai=" + userPlotInfo.getPondRai() +
                "&PondNgan=" + userPlotInfo.getPondNgan() +
                "&PondWa=" + userPlotInfo.getPondWa() +
                "&PondMeter=" + userPlotInfo.getPondMeter() +
                "&CoopMeter=" + userPlotInfo.getCoopMeter() +
                "&CoopNumber=" + userPlotInfo.getCoopNumber() +
                "&TamCode=" + userPlotInfo.getTamCode() +
                "&AmpCode=" + userPlotInfo.getAmpCode() +
                "&ProvCode=" + userPlotInfo.getProvCode() +
                "&AnimalNumber=" + userPlotInfo.getAnimalNumber() +
                "&AnimalWeight=" + userPlotInfo.getAnimalWeight() +
                "&AnimalPrice=" + userPlotInfo.getAnimalPrice() +
                "&FisheryType=" + userPlotInfo.getFisheryType() +
                "&FisheryNumType=" + userPlotInfo.getFisheryNumType() +
                "&FisheryNumber=" + userPlotInfo.getFisheryNumber() +
                "&FisheryWeight=" + userPlotInfo.getFisheryWeight() +
                "&ImeiCode=" + ServiceInstance.GetDeviceID(CalculateResultActivity.this) +
                "&VarName=" + userPlotInfo.getVarName() +
                "&VarValue=" + userPlotInfo.getVarValue() +
                "&CalResult=" + userPlotInfo.getCalResult()
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
