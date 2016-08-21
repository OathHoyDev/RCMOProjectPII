package th.go.oae.rcmo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.neopixl.pixlui.components.textview.TextView;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import th.go.oae.rcmo.API.ResponseAPI_POST;
import th.go.oae.rcmo.API.RequestServices;
import th.go.oae.rcmo.API.ResponseAPI;
import th.go.oae.rcmo.Model.UserPlotModel;
import th.go.oae.rcmo.Model.calculate.CalculateResultModel;
import th.go.oae.rcmo.Module.mGetPlotDetail;
import th.go.oae.rcmo.Module.mSavePlotDetail;
import th.go.oae.rcmo.Module.mUserPlotList;
import th.go.oae.rcmo.Util.CalculateConstant;
import th.go.oae.rcmo.Util.ServiceInstance;
import th.go.oae.rcmo.Util.Util;
import th.go.oae.rcmo.View.DialogChoice;
import th.go.oae.rcmo.View.ProgressAction;

public class PBCalculateResultActivity extends Activity {
    String TAG = "PBCalculateResultActivity";
    String plotId;
    boolean saved = false;
    String prdID;

    public static UserPlotModel userPlotModel;
    public static CalculateResultModel calculateResultModel;
    MediaPlayer mp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pbcalculate_result);

        mp = MediaPlayer.create(PBCalculateResultActivity.this, R.raw.login_noti);
        SharedPreferences sp = getSharedPreferences(ServiceInstance.PREF_NAME, Context.MODE_PRIVATE);
        String userId = sp.getString(ServiceInstance.sp_userId, "0");
        userPlotModel.setUserID(userId);
        initialDetail();

        ListView listView = (ListView) findViewById(R.id.listViewSummary);
        listView.setAdapter(new CalResultAdapter(calculateResultModel.resultList));

        setAction();


    }



    private void setAction() {

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(userPlotModel.getUserID().equals("0")) && saved ){
                    ProgressAction.show(PBCalculateResultActivity.this);
                    API_GetUserPlot(userPlotModel.getUserID());
                }else{
                    if((userPlotModel.getUserID().equals("0")||userPlotModel.getUserID().equals("") )){
                        finish();
                    }else{
                        new DialogChoice(PBCalculateResultActivity.this, new DialogChoice.OnSelectChoiceListener() {
                            @Override
                            public void OnSelect(int choice) {

                                if (choice == DialogChoice.OK) {
                                    upsertUserPlot();

                                }else{
                                    finish();
                                }
                            }
                        }).ShowTwoChoice("", "คุณต้องการบันทึกข้อมูลหรือไม่");

                    }
                }
            }
        });


        findViewById(R.id.btnRecalculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });





        findViewById(R.id.btnSavePlotDetail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences(ServiceInstance.PREF_NAME, Context.MODE_PRIVATE);
                String userId = sp.getString(ServiceInstance.sp_userId, "0");
                userPlotModel.setUserID(userId);
                if (userPlotModel.getUserID() == null || userPlotModel.getUserID().equals("0") || userPlotModel.getUserID().equals("")) {
                  //  new DialogChoice(PBCalculateResultActivity.this)
                    //        .ShowOneChoice("ไม่สามารถบันทึกข้อมูล", "- กรุณา Login ก่อนทำการบันทึกข้อมูล");
                    new DialogChoice(PBCalculateResultActivity.this, new DialogChoice.OnSelectChoiceListener() {
                        @Override
                        public void OnSelect(int choice) {
                            if(choice == DialogChoice.LOGIN){
                                ProgressAction.show(PBCalculateResultActivity.this);
                                startActivity(new Intent(PBCalculateResultActivity.this, LoginActivity.class)
                                        .putExtra("callBy", StepThreeActivity.class.getName()));
                            }else  if(choice == DialogChoice.REGISTER){
                                ProgressAction.show(PBCalculateResultActivity.this);
                                startActivity(new Intent(PBCalculateResultActivity.this, RegisterActivity.class)
                                        .putExtra("callBy", PBCalculateResultActivity.class.getName()));
                            }
                        }
                    }).ShowLogInNoti(mp);

                } else {


                    new DialogChoice(PBCalculateResultActivity.this, new DialogChoice.OnSelectChoiceListener() {
                        @Override
                        public void OnSelect(int choice) {

                            if (choice == DialogChoice.OK) {
                                upsertUserPlot();

                            }
                        }
                    }).ShowTwoChoice("\"" + userPlotModel.getPrdValue() + "\"", "คุนต้องการบันทึกข้อมูล");
                }

            }
        });

        findViewById(R.id.btnHowto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogChoice(PBCalculateResultActivity.this)
                        .ShowTutorial("g17");

            }
        });
    }


    private void initialDetail() {

        ImageView bgImage = (ImageView) findViewById(R.id.productIconBG);
        TextView canterTextview = (TextView) findViewById(R.id.centerImg);
        TextView btnRecalculate = (TextView) findViewById(R.id.btnRecalculate);
        TextView btnSavePlotDetail = (TextView) findViewById(R.id.btnSavePlotDetail);

        TextView recommandLocation = (TextView) findViewById(R.id.recommandLocation);
        TextView recommandPrice = (TextView) findViewById(R.id.recommandPrice);
        TextView recommandpriceLabel = (TextView) findViewById(R.id.priceLabel);

        TextView titleLable = (TextView) findViewById(R.id.titleLable);
        TextView productNameLabel = (TextView) findViewById(R.id.productNameLabel);

        TextView txProfitLoss = (TextView) findViewById(R.id.txProfitLoss);
        TextView txProfitLossValue = (TextView) findViewById(R.id.txProfitLossValue);

        LinearLayout t1  = (LinearLayout)findViewById(R.id.t1);
        LinearLayout t2  = (LinearLayout)findViewById(R.id.t2);
        TextView unit_t1 = (TextView)findViewById(R.id.unit_t1);
        TextView value_t1 = (TextView)findViewById(R.id.value_t1);
        TextView unit_t2 = (TextView)findViewById(R.id.unit_t2);
        TextView value_t2 = (TextView)findViewById(R.id.value_t2);
        TextView unitLabel = (TextView)findViewById(R.id.unitLabel);

        TextView prefix_unit = (TextView)findViewById(R.id.prefix_unit);
        TextView prefix_unit_t1 = (TextView)findViewById(R.id.prefix_unit_t1);
        TextView prefix_unit_t2 = (TextView)findViewById(R.id.prefix_unit_t2);


        t1.setVisibility(View.INVISIBLE);
        t2.setVisibility(View.INVISIBLE);

        switch (userPlotModel.getPrdGrpID()) {
            case CalculateConstant.PRODUCT_TYPE_PLANT:

                bgImage.setBackgroundResource(R.drawable.plant_ic_gr_circle_bg);
                btnRecalculate.setBackgroundResource(R.drawable.action_plant_recal);
                btnSavePlotDetail.setBackgroundResource(R.drawable.action_plant_reget);
                titleLable.setBackgroundColor(getResources().getColor(R.color.RcmoPlantBG));
                productNameLabel.setBackgroundColor(getResources().getColor(R.color.RcmoPlantDarkBG));
                canterTextview.setBackgroundResource(R.drawable.bottom_green_total);
                txProfitLossValue.setTextColor(getResources().getColor(R.color.RcmoPlantBG));

                prefix_unit.setVisibility(View.VISIBLE);
                prefix_unit_t1.setVisibility(View.VISIBLE);
                unitLabel.setVisibility(View.VISIBLE);
                unitLabel.setText(calculateResultModel.unitLabel);
                t1.setVisibility(View.VISIBLE);

                unit_t1.setText(calculateResultModel.unit_t1);
                value_t1.setText(Util.dobbleToStringNumber(calculateResultModel.value_t1));
                value_t1.setTextColor(getResources().getColor(R.color.RcmoPlantBG));

               // if(calculateResultModel.formularCode.equals("C")) {
                    t2.setVisibility(View.VISIBLE);
                    prefix_unit_t2.setVisibility(View.VISIBLE);
                    unit_t2.setText(calculateResultModel.unit_t2);
                    value_t2.setText(Util.dobbleToStringNumber(calculateResultModel.value_t2));
                    value_t2.setTextColor(getResources().getColor(R.color.RcmoPlantBG));
              //  }


                break;
            case CalculateConstant.PRODUCT_TYPE_ANIMAL:
                t1.setVisibility(View.VISIBLE);

                bgImage.setBackgroundResource(R.drawable.animal_ic_gr_circle_bg);
                btnRecalculate.setBackgroundResource(R.drawable.action_animal_recal);
                btnSavePlotDetail.setBackgroundResource(R.drawable.action_animal_reget);
                titleLable.setBackgroundColor(getResources().getColor(R.color.RcmoAnimalBG));
                productNameLabel.setBackgroundColor(getResources().getColor(R.color.RcmoAnimalDarkBG));
                canterTextview.setBackgroundResource(R.drawable.bottom_pink_total);
                txProfitLossValue.setTextColor(getResources().getColor(R.color.RcmoAnimalBG));


                unit_t1.setText(calculateResultModel.unit_t1);
                value_t1.setText(Util.dobbleToStringNumber(calculateResultModel.value_t1));
                value_t1.setTextColor(getResources().getColor(R.color.RcmoAnimalBG));


                if("43".equals(userPlotModel.getPrdID())){
                    ((TextView) findViewById(R.id.unit)).setText("บาท/กก.");
                    unit_t1.setVisibility(View.GONE);
                    value_t1.setVisibility(View.GONE);
                }else{
                    unit_t1.setText(calculateResultModel.unit_t1);
                    value_t1.setText(Util.dobbleToStringNumber(calculateResultModel.value_t1));
                    value_t1.setTextColor(getResources().getColor(R.color.RcmoAnimalBG));
                }

                break;
            case CalculateConstant.PRODUCT_TYPE_FISH:
                t1.setVisibility(View.VISIBLE);

                bgImage.setBackgroundResource(R.drawable.fish_ic_gr_circle_bg);
                btnRecalculate.setBackgroundResource(R.drawable.action_fish_recal);
                btnSavePlotDetail.setBackgroundResource(R.drawable.action_fish_reget);
                titleLable.setBackgroundColor(getResources().getColor(R.color.RcmoFishBG));
                productNameLabel.setBackgroundColor(getResources().getColor(R.color.RcmoFishDarkBG));
                canterTextview.setBackgroundResource(R.drawable.bottom_blue_total);
                txProfitLossValue.setTextColor(getResources().getColor(R.color.RcmoFishBG));

                unit_t1.setText(calculateResultModel.unit_t1);
                value_t1.setText(Util.dobbleToStringNumber(calculateResultModel.value_t1));
                value_t1.setTextColor(getResources().getColor(R.color.RcmoFishBG));

                if(calculateResultModel.formularCode.equals("I") || calculateResultModel.formularCode.equals("K")) {
                    t2.setVisibility(View.VISIBLE);
                    unit_t2.setText(calculateResultModel.unit_t2);
                    value_t2.setText(Util.dobbleToStringNumber(calculateResultModel.value_t2));
                    value_t2.setTextColor(getResources().getColor(R.color.RcmoFishBG));
                }
                break;
        }

        productNameLabel.setText(calculateResultModel.productName);

        ImageView productIcon = (ImageView) findViewById(R.id.productIcon);

        String imgName = ServiceInstance.productIMGMap.get(Integer.parseInt(userPlotModel.getPrdID()));

        int imgIDInt = getResources().getIdentifier(imgName, "drawable", getPackageName());

        productIcon.setImageResource(imgIDInt);

        if (calculateResultModel.mPlotSuit != null) {
            recommandLocation.setText(calculateResultModel.mPlotSuit.getSuitLabel());
        } else {
            recommandLocation.setText("ไม่พบข้อมูล");
        }

/*
if(userPlotModel.getPrdID().equals("40")
        || userPlotModel.getPrdID().equals("41")
        || userPlotModel.getPrdID().equals("42")
        || userPlotModel.getPrdID().equals("43")
        || userPlotModel.getPrdID().equals("44")
        || userPlotModel.getPrdID().equals("49")
        || "D".equalsIgnoreCase(calculateResultModel.formularCode)
        || "J".equalsIgnoreCase(calculateResultModel.formularCode)
        || "K".equalsIgnoreCase(calculateResultModel.formularCode)) {
   recommandpriceLabel.setVisibility(View.INVISIBLE);
    recommandPrice.setVisibility(View.INVISIBLE);
}else{
    if (calculateResultModel.compareStdResult > 0) {
        recommandPrice.setText("ต้นทุนสูงกว่าค่าเฉลี่ย");
    } else if (calculateResultModel.compareStdResult == 0) {
        recommandPrice.setText("ต้นทุนเทียบเท่าค่าเฉลี่ย");
    } else {
        recommandPrice.setText("ต้นทุนต่ำกว่าค่าเฉลี่ย");
    }

}
*/




        if (calculateResultModel.calculateResult >= 0) {
            txProfitLoss.setText("กำไร");
            txProfitLossValue.setText(Util.dobbleToStringNumber(calculateResultModel.calculateResult));
        } else {
            txProfitLoss.setText("ขาดทุน");
            txProfitLossValue.setText(Util.dobbleToStringNumber(calculateResultModel.calculateResult));
        }

        if (calculateResultModel.calculateResult > 0) {
            recommandPrice.setText("คณุมีโอกาสเป็นเศรษฐี");
        } else if (calculateResultModel.calculateResult == 0) {
            recommandPrice.setText("คณุพอมีพอกิน ต้องลดต้นทุน");
        } else {
            recommandPrice.setText("คณุมีโอกาสจะยากจน ต้องลดต้นทุน");
        }


        //  calculateResultModel.resultList;
    }


    private void upsertUserPlot() {

        // API_GetUserPlot(userId,prdId,prdGrpId,plotId);

        userPlotModel.setUserID(userPlotModel.getUserID());

        if (!userPlotModel.getUserID().equals("0")) {
            if (!saved) {
                Log.d(TAG, "Go to save plot Module ");
                if (userPlotModel.getPlotID() != null
                        && !userPlotModel.getPlotID().equals("")
                        && !userPlotModel.getPlotID().equals("0")) {
                    //  API_SavePlotDetail("2", userPlotModel);
                    API_getPlotDetailAndSave(userPlotModel.getPlotID());
                } else {
                    API_SaveProd_POST("1", userPlotModel);
                }
            } else {
                Log.d(TAG, "Go to update plot Module ");
                API_getPlotDetailAndSave(userPlotModel.getPlotID());
                // API_SavePlotDetail("2", userPlotModel);

            }
        } else {
            new DialogChoice(PBCalculateResultActivity.this)
                    .ShowOneChoice("ไม่สามารถบันทึกข้อมูล", "- กรุณา Login ก่อนทำการบันทึกข้อมูล"); //save image
        }


    }

    private void API_SavePlotDetail_GET(String saveFlag, UserPlotModel userPlotInfo) {
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
                    takeScreenshot();
                    saved = true;
                    Util.showDialogAndDismiss(PBCalculateResultActivity.this, "บันทึกข้อมูลสำเร็จ");
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
                "&PlotRai=" +userPlotInfo.getPlotRai() +
                "&PlotNgan=" +userPlotInfo.getPlotNgan() +
                "&PlotWa=" +userPlotInfo.getPlotWa() +
                "&PlotMeter=" +userPlotInfo.getPlotMeter() +
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
                "&ImeiCode=" + ServiceInstance.GetDeviceID(PBCalculateResultActivity.this) +
                "&VarName=" + userPlotInfo.getVarName() +
                "&VarValue=" + userPlotInfo.getVarValue() +
                "&CalResult=" + userPlotInfo.getCalResult()
        );
    }

    private void API_SaveProd_POST(String saveFlag, UserPlotModel userPlotInfo) {
        HashMap<String,Object> param = new HashMap<>();

        param.put("SaveFlag",saveFlag);
        param.put("UserID",userPlotInfo.getUserID());
        param.put("PlotID",userPlotInfo.getPlotID() );
        param.put("PrdID",userPlotInfo.getPrdID());
        param.put("PrdGrpID",userPlotInfo.getPrdGrpID());

        param.put("PlotRai",userPlotInfo.getPlotRai());
        param.put("PlotNgan",userPlotInfo.getPlotNgan());
        param.put("PlotWa",userPlotInfo.getPlotWa());
        param.put("PlotMeter",userPlotInfo.getPlotMeter());

        param.put("PondRai",userPlotInfo.getPondRai());
        param.put("PondNgan",userPlotInfo.getPondNgan());
        param.put("PondWa",userPlotInfo.getPondWa());
        param.put("PondMeter",userPlotInfo.getPondMeter());
        param.put("CoopMeter",userPlotInfo.getCoopMeter());
        param.put("CoopNumber",userPlotInfo.getCoopNumber());
        param.put("TamCode",Util.defualtNullStringZero(userPlotInfo.getTamCode()));
        param.put("AmpCode",Util.defualtNullStringZero(userPlotInfo.getAmpCode()));
        param.put("ProvCode",Util.defualtNullStringZero(userPlotInfo.getProvCode()));
        param.put("AnimalNumber",userPlotInfo.getAnimalNumber());
        param.put("AnimalWeight",userPlotInfo.getAnimalWeight());
        param.put("AnimalPrice",userPlotInfo.getAnimalPrice());
        param.put("FisheryType",userPlotInfo.getFisheryType());
        param.put("FisheryNumType",userPlotInfo.getFisheryNumType());
        param.put("FisheryNumber",userPlotInfo.getFisheryNumber() );
        param.put("FisheryWeight",userPlotInfo.getFisheryWeight());
        param.put("ImeiCode",ServiceInstance.GetDeviceID(PBCalculateResultActivity.this));
        param.put("VarName",userPlotInfo.getVarName() );
        param.put("VarValue",userPlotInfo.getVarValue());
        param.put("CalResult",userPlotInfo.getCalResult());



        new ResponseAPI_POST(PBCalculateResultActivity.this, new ResponseAPI_POST.OnCallbackAPIListener() {
            @Override
            public void callbackSuccess(JSONObject obj) {
                mSavePlotDetail mSaveResp = new Gson().fromJson(obj.toString(), mSavePlotDetail.class);
                List<mSavePlotDetail.mRespBody> mRespBody = mSaveResp.getRespBody();
                if(mRespBody.size()>0){
                    Log.d("Test ------>","PlodId "+mRespBody.get(0).getPlotID());
                    plotId = String.valueOf(mRespBody.get(0).getPlotID());
                    if (plotId == null) {
                        plotId = "";
                    }
                    userPlotModel.setPlotID(plotId);
                    Log.d(TAG, "Response plotId : " + plotId);
                    takeScreenshot();
                    saved = true;
                    Util.showDialogAndDismiss(PBCalculateResultActivity.this, "บันทึกข้อมูลสำเร็จ");
                }
            }

            @Override
            public void callbackError(int code, String errorMsg) {
              //  progressbar.gone(PriceActivity.this);
                //listview.setVisibility(View.INVISIBLE);
                //findViewById(R.id.no_list).setVisibility(View.VISIBLE);

            }
        }).POST(RequestServices.ws_savePlotDetail, param, true, false);

    }


    private void API_GetUserPlot(final String userId) {


        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mUserPlotList mPlotList = (mUserPlotList) obj;
                final List<mUserPlotList.mRespBody> userPlotBodyLists = mPlotList.getRespBody();

                if (userPlotBodyLists.size() != 0) {
                    userPlotBodyLists.get(0).toString();

                    UserPlotListActivity.userPlotRespBodyList = userPlotBodyLists;

                    Intent intent = new Intent(PBCalculateResultActivity.this, UserPlotListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                List<mUserPlotList.mRespBody> userPlotList = new ArrayList<mUserPlotList.mRespBody>();
                UserPlotListActivity.userPlotRespBodyList = userPlotList;


                startActivity(new Intent(PBCalculateResultActivity.this, UserPlotListActivity.class)
                        .putExtra("userId", userId));
                finish();

            }
        }).API_Request(false, RequestServices.ws_getPlotList +
                "?UserID=" + userId + "&PlotID=" +
                "&ImeiCode=" + ServiceInstance.GetDeviceID(PBCalculateResultActivity.this));

    }





    private void API_getPlotDetailAndSave(String plotID) {
        /**
         1.TamCode (ไม่บังคับใส่)
         2.AmpCode (บังคับใส่)
         3.ProvCode (บังคับใส่)
         */
        new ResponseAPI(PBCalculateResultActivity.this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mGetPlotDetail mPlotDetail = (mGetPlotDetail) obj;
                List<mGetPlotDetail.mRespBody> mPlotDetailBodyLists = mPlotDetail.getRespBody();

                if (mPlotDetailBodyLists.size() != 0) {
                    mGetPlotDetail.mRespBody plotDetail = mPlotDetailBodyLists.get(0);

                    if (userPlotModel.getPlotRai().equals("") || userPlotModel.getPlotRai().equals("0")) {
                        if(plotDetail.getPlotRai().equals("0")){
                            plotDetail.setPlotRai("");
                        }
                        userPlotModel.setPlotRai(String.valueOf(plotDetail.getPlotRai()));
                    }
                    if (userPlotModel.getPlotNgan().equals("") || userPlotModel.getPlotNgan().equals("0")) {
                        if(plotDetail.getPlotNgan().equals("0")){
                            plotDetail.setPlotNgan("");
                        }
                        userPlotModel.setPlotNgan(String.valueOf(plotDetail.getPlotNgan()));
                    }
                    if (userPlotModel.getPlotWa().equals("") || userPlotModel.getPlotWa().equals("0")) {
                        if(plotDetail.getPlotWa().equals("0")){
                            plotDetail.setPlotWa("");
                        }
                        userPlotModel.setPlotWa(String.valueOf(plotDetail.getPlotWa()));
                    }
                    if (userPlotModel.getPlotMeter().equals("") || userPlotModel.getPlotMeter().equals("0")) {
                        if(plotDetail.getPlotMeter().equals("0")){
                            plotDetail.setPlotMeter("");
                        }
                        userPlotModel.setPlotMeter(String.valueOf(plotDetail.getPlotMeter()));
                    }


                    if (userPlotModel.getProvCode().equals("") || userPlotModel.getProvCode().equals("0")) {
                        userPlotModel.setProvCode(String.valueOf(plotDetail.getProvCode()));
                    }
                    if (userPlotModel.getAmpCode().equals("") || userPlotModel.getAmpCode().equals("0")) {
                        userPlotModel.setAmpCode(String.valueOf(plotDetail.getAmpCode()));
                    }
                    if (userPlotModel.getTamCode().equals("") || userPlotModel.getTamCode().equals("0")) {
                        userPlotModel.setTamCode(String.valueOf(plotDetail.getTamCode()));
                    }

                    if(userPlotModel.getVarValue().equals("")|| userPlotModel.getVarValue().equals("0")){
                        userPlotModel.setVarValue(plotDetail.getVarValue());
                    }

                    if (userPlotModel.getAnimalNumber().equals("") || userPlotModel.getAnimalNumber().equals("0")) {
                        plotDetail.setAnimalNumber(plotDetail.getAnimalNumber().equals("0")?"":plotDetail.getAnimalNumber());
                        userPlotModel.setAnimalNumber(String.valueOf(plotDetail.getAnimalNumber()));
                    }

                    if(userPlotModel.getAnimalPrice().equals("")|| userPlotModel.getAnimalPrice().equals("0")){
                        plotDetail.setAnimalPrice(plotDetail.getAnimalPrice().equals("0")?"":plotDetail.getAnimalPrice());
                        userPlotModel.setAnimalPrice(plotDetail.getAnimalPrice());
                    }

                    if(userPlotModel.getAnimalWeight().equals("")|| userPlotModel.getAnimalWeight().equals("0")){
                        plotDetail.setAnimalWeight(plotDetail.getAnimalWeight().equals("0")?"":plotDetail.getAnimalWeight());
                        userPlotModel.setAnimalWeight(plotDetail.getAnimalWeight());
                    }

                    if(userPlotModel.getPondRai().equals("")|| userPlotModel.getPondRai().equals("0")){
                        plotDetail.setPondRai(plotDetail.getPondRai().equals("0")?"":plotDetail.getPondRai());
                        userPlotModel.setPondRai(plotDetail.getPondRai());
                    }

                    if(userPlotModel.getPondNgan().equals("")|| userPlotModel.getPondNgan().equals("0")){
                        plotDetail.setPondNgan(plotDetail.getPondNgan().equals("0")?"":plotDetail.getPondNgan());
                        userPlotModel.setPondNgan(plotDetail.getPondNgan());
                    }

                    if(userPlotModel.getPondWa().equals("")|| userPlotModel.getPondWa().equals("0")){
                        plotDetail.setPondWa(plotDetail.getPondWa().equals("0")?"":plotDetail.getPondWa());
                        userPlotModel.setPondWa(plotDetail.getPondWa());
                    }

                    if(userPlotModel.getPondMeter().equals("")|| userPlotModel.getPondMeter().equals("0")){
                        plotDetail.setPondMeter(plotDetail.getPondMeter().equals("0")?"":plotDetail.getPondMeter());
                        userPlotModel.setPondMeter(plotDetail.getPondMeter());
                    }

                    if(userPlotModel.getFisheryNumber().equals("")|| userPlotModel.getFisheryNumber().equals("0")){
                        plotDetail.setFisheryNumber(plotDetail.getFisheryNumber().equals("0")?"":plotDetail.getFisheryNumber());
                        userPlotModel.setFisheryNumber(plotDetail.getFisheryNumber());
                    }

                    if(userPlotModel.getFisheryType().equals("")|| userPlotModel.getFisheryType().equals("0")){
                        plotDetail.setFisheryType(plotDetail.getFisheryType().equals("0")?"":plotDetail.getFisheryType());
                        userPlotModel.setFisheryType(plotDetail.getFisheryType());
                    }

                    if(userPlotModel.getFisheryWeight().equals("")|| userPlotModel.getFisheryWeight().equals("0")){
                        plotDetail.setFisheryWeight(plotDetail.getFisheryWeight().equals("0")?"":plotDetail.getFisheryWeight());
                        userPlotModel.setFisheryWeight(plotDetail.getFisheryWeight());
                    }

                    if(userPlotModel.getCoopMeter().equals("")|| userPlotModel.getCoopMeter().equals("0")){
                        plotDetail.setCoopMeter(plotDetail.getCoopMeter().equals("0")?"":plotDetail.getCoopMeter());
                        userPlotModel.setCoopMeter(plotDetail.getCoopMeter());
                    }

                    if(userPlotModel.getCoopNumber().equals("")|| userPlotModel.getCoopNumber().equals("0")){
                        plotDetail.setCoopNumber(plotDetail.getCoopNumber().equals("0")?"":plotDetail.getCoopNumber());
                        userPlotModel.setCoopNumber(plotDetail.getCoopNumber());
                    }


                    // mVarPlanA varA =  new Gson().fromJson(plotDetail.getVarValue(), mVarPlanA.class);
                  //  Log.d("Testttt --> ",varA.toString());
                    API_SaveProd_POST("2", userPlotModel);


                }


            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getPlotDetail +
                "?PlotID=" + plotID +
                "&ImeiCode=" + ServiceInstance.GetDeviceID(PBCalculateResultActivity.this));

    }





    class CalResultAdapter extends BaseAdapter {
        List<String[]> resultList;

        CalResultAdapter(List<String[]> resultList) {
            this.resultList = resultList;
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String[] getItem(int position) {

            return resultList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder h = new ViewHolder();

            if (convertView == null) {
                LayoutInflater inflater = PBCalculateResultActivity.this.getLayoutInflater();
                if("A".equalsIgnoreCase(calculateResultModel.formularCode)
                        ||"B".equalsIgnoreCase(calculateResultModel.formularCode)
                        ||"C".equalsIgnoreCase(calculateResultModel.formularCode)){
                    convertView = inflater.inflate(R.layout.row_result_pii, parent, false);
                }else {
                    convertView = inflater.inflate(R.layout.row_result, parent, false);
                }

                h.name = (TextView) convertView.findViewById(R.id.name);
                h.value = (TextView) convertView.findViewById(R.id.value);
                h.unit = (TextView) convertView.findViewById(R.id.unit);
                h.layout_name = (LinearLayout) convertView.findViewById(R.id.layout_name);
                convertView.setTag(h);
            } else {
                h = (ViewHolder) convertView.getTag();
            }

            String[] calResult = resultList.get(position);

            if("A".equalsIgnoreCase(calculateResultModel.formularCode)
                    ||"B".equalsIgnoreCase(calculateResultModel.formularCode)
                    ||"C".equalsIgnoreCase(calculateResultModel.formularCode)){
                h.name.setText(calResult[0]);
            }else {

                if (calResult[0] == null || "".equals(calResult[0])) {
                    h.layout_name.setVisibility(View.GONE);
                } else {
                    h.layout_name.setVisibility(View.VISIBLE);
                    h.name.setText(calResult[0]);
                }
            }


            Log.d(TAG,"h.value"+calResult[1]);
            Log.d(TAG," h.unit"+calResult[2]);

            h.value.setText(calResult[1]);
            h.unit.setText(calResult[2]);

            if(userPlotModel.getPrdGrpID().equals("2")) {
                h.value.setTextColor(getResources().getColor(R.color.RcmoAnimalBG));
            }else if(userPlotModel.getPrdGrpID().equals("3")){
                h.value.setTextColor(getResources().getColor(R.color.RcmoFishBG));
            }

            return convertView;
        }

    }

    static class ViewHolder {
        private TextView name, value, unit;
        private LinearLayout layout_name;


    }


    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            Log.d("TEST","Path : "+mPath);
            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

           // openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();  // Always call the superclass method first
        Log.d(TAG, "onResume  ProgressAction.gone ... ");
        ProgressAction.gone(PBCalculateResultActivity.this);
        // Release the Camera because we don't need it when paused
        // and other activities might need to use it.

    }
}


