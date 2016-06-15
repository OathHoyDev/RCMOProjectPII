package th.co.rcmo.rcmoapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import th.co.rcmo.rcmoapp.API.RequestServices;
import th.co.rcmo.rcmoapp.API.ResponseAPI;
import th.co.rcmo.rcmoapp.Adapter.DialogAmphoeAdapter;
import th.co.rcmo.rcmoapp.Adapter.DialogProvinceAdapter;
import th.co.rcmo.rcmoapp.Adapter.DialogTumbonAdapter;
import th.co.rcmo.rcmoapp.Model.ProductModel;
import th.co.rcmo.rcmoapp.Module.mAmphoe;
import th.co.rcmo.rcmoapp.Module.mProvince;
import th.co.rcmo.rcmoapp.Module.mSavePlotDetail;
import th.co.rcmo.rcmoapp.Module.mTumbon;
import th.co.rcmo.rcmoapp.Module.mUserPlotList;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;
import th.co.rcmo.rcmoapp.View.DialogChoice;

public class StepThreeActivity extends Activity {
     mProvince.mRespBody  selectedprovince = null;
     mAmphoe.mRespBody    selectedAmphoe    = null;
     mTumbon.mRespBody    selectedTumbon   = null;
    public static ProductModel productionInfo = null;
    String TAG = "StepThreeActivity_TAG";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUI();
        setAction();

    }


    private void setUI() {
        int groupId = productionInfo.getPrdGrpID();

        if(groupId == 1){
            setContentView(R.layout.activity_plant_step_three);

            //Get Object
            Holder h = new Holder();
            h.prodImg      = (ImageView) findViewById(R.id.prodImg);
            h.prodBg      =  (LinearLayout)findViewById(R.id.gridDrawBg);
            h.labelProductName      = (com.neopixl.pixlui.components.textview.TextView) findViewById(R.id.labelProductName);
            h.labelProductHierarchy = (com.neopixl.pixlui.components.textview.TextView) findViewById(R.id.labelProductHierarchy);


            //Set Dynamic Ui
            h.labelProductName.setText(productionInfo.getPrdName());
            h.labelProductHierarchy.setText(ServiceInstance.genProdHierarchy("กลุ่มพืช",productionInfo.getProdHierarchy()));

            String imgName = ServiceInstance.productIMGMap.get(productionInfo.getPrdID());
            if(imgName!=null) {
                h.prodImg.setImageResource(getResources().getIdentifier(imgName, "drawable", getPackageName()));
            }

            h.prodBg.setBackgroundResource(R.drawable.plant_ic_circle_bg);

        }else if(groupId == 2){
            setContentView(R.layout.activity_animal_step_three);

            Holder h = new Holder();
            h.prodImg      = (ImageView) findViewById(R.id.prodImg);
            h.prodBg      =  (LinearLayout)findViewById(R.id.gridDrawBg);
            h.labelProductName      = (com.neopixl.pixlui.components.textview.TextView) findViewById(R.id.labelProductName);
            h.labelProductHierarchy = (com.neopixl.pixlui.components.textview.TextView) findViewById(R.id.labelProductHierarchy);


            //Set Dynamic Ui
            h.labelProductName.setText(productionInfo.getPrdName());
            h.labelProductHierarchy.setText(ServiceInstance.genProdHierarchy("กลุ่มปศุสัตว์",productionInfo.getProdHierarchy()));

            String imgName = ServiceInstance.productIMGMap.get(productionInfo.getPrdID());
            if(imgName!=null) {
                h.prodImg.setImageResource(getResources().getIdentifier(imgName, "drawable", getPackageName()));
            }

            h.prodBg.setBackgroundResource(R.drawable.animal_ic_circle_bg);


        }else if(groupId==3){
            setContentView(R.layout.activity_fish_step_three);

            Holder h = new Holder();
            h.prodImg      = (ImageView) findViewById(R.id.prodImg);
            h.prodBg      =  (LinearLayout)findViewById(R.id.gridDrawBg);
            h.labelProductName      = (com.neopixl.pixlui.components.textview.TextView) findViewById(R.id.labelProductName);
            h.labelProductHierarchy = (com.neopixl.pixlui.components.textview.TextView) findViewById(R.id.labelProductHierarchy);


            //Set Dynamic Ui
            h.labelProductName.setText(productionInfo.getPrdName());
            h.labelProductHierarchy.setText(ServiceInstance.genProdHierarchy("กลุ่มประมง",productionInfo.getProdHierarchy()));

            String imgName = ServiceInstance.productIMGMap.get(productionInfo.getPrdID());
            if(imgName!=null) {
                h.prodImg.setImageResource(getResources().getIdentifier(imgName, "drawable", getPackageName()));
            }

            h.prodBg.setBackgroundResource(R.drawable.fish_ic_circle_bg);
        }else{
            startActivity(new Intent(StepThreeActivity.this, LoginActivity.class));
            finish();

        }

    }

    class Holder{

        ImageView prodImg;
        LinearLayout prodBg;
        com.neopixl.pixlui.components.textview.TextView labelProductName,labelProductHierarchy;


    }

    private void setAction() {
        SharedPreferences sp = getSharedPreferences(ServiceInstance.PREF_NAME, Context.MODE_PRIVATE);
        final String userId = sp.getString(ServiceInstance.sp_userId, "0");
        final String plotId = sp.getString(ServiceInstance.sp_plot_Id, "9999");

        final int groupId = productionInfo.getPrdGrpID();

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValidate = false;
                if (groupId == 1) {
                    isValidate = isValidPlantInputData();
                }else{
                    isValidate = true;
                }


                if (isValidate) {
                    new DialogChoice(StepThreeActivity.this, new DialogChoice.OnSelectChoiceListener() {
                        @Override
                        public void OnSelect(int choice) {
                            if (choice == DialogChoice.OK) {
                                upsertUserPlot(userId,String.valueOf(productionInfo.getPrdID()),String.valueOf(productionInfo.getPrdGrpID()),plotId);

                            }
                        }
                    }).ShowTwoChoice("\"" + ((TextView) findViewById(R.id.labelProductName)).getText().toString() + "\"", "คุนต้องการบันทึกข้อมูล");
                }


            }
        });

        findViewById(R.id.inputprovice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                API_getProvince();

            }
        });

        findViewById(R.id.inputprovice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                API_getProvince();

            }
        });
        findViewById(R.id.inputAmphoe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedprovince != null) {
                    API_getAmphoe(selectedprovince.getProvCode());
                }

            }
        });

        findViewById(R.id.inputTumbon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedprovince != null && selectedAmphoe != null) {
                    API_getTumbon(selectedprovince.getProvCode(), selectedAmphoe.getAmpCode());
                }

            }
        });
    }


    private void popUpProvinceListDialog(final List<mProvince.mRespBody> provinceRespList) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = getLayoutInflater().inflate(R.layout.spin_location_dialog, null);

        view.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        ListView listView = (ListView) view.findViewById(R.id.listLocation);

        DialogProvinceAdapter provinceAdapter = new DialogProvinceAdapter(StepThreeActivity.this, provinceRespList);
        listView.setAdapter(provinceAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView provinceTextView = (TextView) findViewById(R.id.inputprovice);
                TextView amphoeTextView = (TextView) findViewById(R.id.inputAmphoe);
                TextView tumbonTextView = (TextView) findViewById(R.id.inputTumbon);

                selectedprovince = provinceRespList.get(position);
                selectedAmphoe = null;
                selectedTumbon = null;

                provinceTextView.setText(selectedprovince.getProvNameTH());
                amphoeTextView.setText("");
                tumbonTextView.setText("");

                dialog.dismiss();

            }
        });

        dialog.setContentView(view);
        dialog.show();
    }

    private void popUpAmphoeListDialog(final List<mAmphoe.mRespBody> amphoeRespList) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = getLayoutInflater().inflate(R.layout.spin_location_dialog, null);

        view.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        ListView listView = (ListView) view.findViewById(R.id.listLocation);

        DialogAmphoeAdapter amphoeAdapter = new DialogAmphoeAdapter(StepThreeActivity.this, amphoeRespList);
        listView.setAdapter(amphoeAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView amphoeTextView = (TextView) findViewById(R.id.inputAmphoe);
                TextView tumbonTextView = (TextView) findViewById(R.id.inputTumbon);

                selectedAmphoe = amphoeRespList.get(position);
                selectedTumbon = null;

                amphoeTextView.setText(selectedAmphoe.getAmpNameTH());
                tumbonTextView.setText("");

                dialog.dismiss();

            }
        });

        dialog.setContentView(view);
        dialog.show();
    }

    private void popUpTumbonListDialog(final List<mTumbon.mRespBody> tunbonRespList) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = getLayoutInflater().inflate(R.layout.spin_location_dialog, null);

        view.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        ListView listView = (ListView) view.findViewById(R.id.listLocation);

        DialogTumbonAdapter tumbonAdapter = new DialogTumbonAdapter(StepThreeActivity.this, tunbonRespList);
        listView.setAdapter(tumbonAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView amphoeTextView = (TextView) findViewById(R.id.inputAmphoe);
                TextView tumbonTextView = (TextView) findViewById(R.id.inputTumbon);

                selectedTumbon = tunbonRespList.get(position);

                tumbonTextView.setText(selectedTumbon.getTamNameTH());

                dialog.dismiss();

            }
        });

        dialog.setContentView(view);
        dialog.show();
    }


//====================== API =============================================
private void API_getProvince() {
    /**
     1.ProvCode (ไม่บังคับใส่)
     */
    new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void callbackSuccess(Object obj) {

            mProvince provinceInfo = (mProvince) obj;

            final List<mProvince.mRespBody> provinceBodyLists = provinceInfo.getRespBody();

            if (provinceBodyLists.size() != 0) {
                popUpProvinceListDialog(provinceBodyLists);
            }
        }

        @Override
        public void callbackError(int code, String errorMsg) {
            Log.d("Error", errorMsg);
        }
    }).API_Request(true, RequestServices.ws_getProvince +
            "?ProvCode="
    );

}

    private void API_getAmphoe(String provinceId) {
        /**
         1.AmpCode (ไม่บังคับใส่)
         2.ProvCode (บังคับใส่)
         */
        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mAmphoe amphoeInfo = (mAmphoe) obj;

                final List<mAmphoe.mRespBody> amphoeBodyLists = amphoeInfo.getRespBody();

                if (amphoeBodyLists.size() != 0) {
                    popUpAmphoeListDialog(amphoeBodyLists);
                }
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getAmphoe +
                "?AmpCode=" +
                "&ProvCode=" + provinceId
        );

    }

    private void API_getTumbon(String provinceId, String amphoeId) {
        /**
         1.TamCode (ไม่บังคับใส่)
         2.AmpCode (บังคับใส่)
         3.ProvCode (บังคับใส่)
         */
        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mTumbon tumbonInfo = (mTumbon) obj;

                final List<mTumbon.mRespBody> tumbonBodyLists = tumbonInfo.getRespBody();

                if (tumbonBodyLists.size() != 0) {
                    popUpTumbonListDialog(tumbonBodyLists);
                }
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getTambon +
                "?TamCode=" +
                "&AmpCode=" + amphoeId +
                "&ProvCode=" + provinceId
        );

    }


    private void toastMSG(String msg) {
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


    private void upsertUserPlot(final String userId
            , final String prdId
            , final String prdGrpId
            , final String plotId){

        API_GetUserPlot(userId,prdId,prdGrpId,plotId);
    }


    private void API_GetUserPlot(final String userId
                               , final String prdId
                               , final String prdGrpId
                               , final String plotId) {
        Log.d(TAG, "userId : " + userId);


        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mUserPlotList mPlotList = (mUserPlotList) obj;
                List<mUserPlotList.mRespBody> userPlotBodyLists = mPlotList.getRespBody();

                if (userPlotBodyLists.size() != 0) {
                    userPlotBodyLists.get(0).toString();

                    Log.d(TAG, "Go to update plot Module ");
                    API_SavePlotDetail("2", userId,String.valueOf(userPlotBodyLists.get(0).getPlotID()),prdId,prdGrpId);


                }

            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d(TAG, "Go to save plot Module ");
                API_SavePlotDetail("1", userId, "",prdId,prdGrpId);
            }
        }).API_Request(false, RequestServices.ws_getPlotList +
                "?UserID=" + userId +
                "&PlotID=" + plotId +
                "&ImeiCode=" + ServiceInstance.GetDeviceID(StepThreeActivity.this)
        );

    }

    private void API_SavePlotDetail(String saveFlag,String userId,String plotId,String prdId, String  prdGrup) {
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
                    toastMsg("คัดลอกข้อมูลสำเร็จ");
                }
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d(TAG, errorMsg);
            }
        }).API_Request(true, RequestServices.ws_savePlotDetail +

                "?SaveFlag=" + saveFlag +
                "&UserID=" +userId+
                "&PlotID=" +plotId+
                "&PrdID=" + prdId +
                "&PrdGrpID=" + prdGrup +
                "&PlotRai=" +
                "&PondRai=" +
                "&PondNgan=" +
                "&PondWa=" +
                "&PondMeter=" +
                "&CoopMeter=" +
                "&CoopNumber=" +
                "&TamCode=" +
                "&AmpCode=" +
                "&ProvCode=" +
                "&AnimalNumber=" +
                "&AnimalWeight=" +
                "&AnimalPrice=" +
                "&FisheryType=" +
                "&FisheryNumType=" +
                "&FisheryNumber=" +
                "&FisheryWeight=" +
                "&ImeiCode=" +
                "&VarName=" +
                "&VarValue=" +
                "&CalResult="
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
    //validate ============================================

    private boolean isValidPlantInputData (){
        boolean isValid = false;
        EditText inputPricePerUnit = (EditText) findViewById(R.id.inputPricePerUnit);

        if(inputPricePerUnit.getText() == null || inputPricePerUnit.getText().toString().equals("")){
            new DialogChoice(StepThreeActivity.this).ShowOneChoice("", "กรุณากรอกขนาดแปลงที่ดิน");
        }else if(selectedprovince == null || selectedAmphoe == null || selectedTumbon == null){
            new DialogChoice(StepThreeActivity.this).ShowOneChoice("", "กรุณากรอกตำแหน่งแปลงทีดิน");
        }else{
            isValid = true;
        }

        return isValid;

    }
}
