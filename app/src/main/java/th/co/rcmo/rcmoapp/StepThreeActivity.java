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
import android.view.inputmethod.EditorInfo;
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
import th.co.rcmo.rcmoapp.Model.UserPlotModel;
import th.co.rcmo.rcmoapp.Module.mAmphoe;
import th.co.rcmo.rcmoapp.Module.mProvince;
import th.co.rcmo.rcmoapp.Module.mSavePlotDetail;
import th.co.rcmo.rcmoapp.Module.mTumbon;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;
import th.co.rcmo.rcmoapp.View.DialogChoice;

public class StepThreeActivity extends Activity {
     mProvince.mRespBody  selectedprovince = null;
     mAmphoe.mRespBody    selectedAmphoe    = null;
     mTumbon.mRespBody    selectedTumbon   = null;
    public static ProductModel productionInfo = null;
    UserPlotModel userPlotModel = new UserPlotModel();
    String TAG = "StepThreeActivity_TAG";
    boolean kcSelected = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = getSharedPreferences(ServiceInstance.PREF_NAME, Context.MODE_PRIVATE);
        final String userId = sp.getString(ServiceInstance.sp_userId, "0");

        userPlotModel.setPrdGrpID(String.valueOf(productionInfo.getPrdGrpID()));
        userPlotModel.setPrdID(String.valueOf(productionInfo.getPrdID()));
        userPlotModel.setPrdGrpID( String.valueOf(productionInfo.getPrdGrpID()));
        userPlotModel.setUserID(userId);

        setUI();
        setAction();

    }


    private void setUI() {
        int groupId = Integer.valueOf(userPlotModel.getPrdGrpID());

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




            if(   userPlotModel.getPrdID().equals("39")
                    || userPlotModel.getPrdID().equals("40")
                    || userPlotModel.getPrdID().equals("41")
                    || userPlotModel.getPrdID().equals("42")){
                h.labelWeightPerUnit = (com.neopixl.pixlui.components.textview.TextView) findViewById(R.id.labelWeightPerUnit);
                h.inputWeightPerUnit = (com.neopixl.pixlui.components.edittext.EditText) findViewById(R.id.inputWeightPerUnit);

                h.labelWeightPerUnit.setVisibility(View.GONE);
                h.inputWeightPerUnit.setVisibility(View.GONE);


            }else if(userPlotModel.getPrdID().equals("43")){
                h.labelNumberOfStart = (com.neopixl.pixlui.components.textview.TextView) findViewById(R.id.labelnumberOfStart);
                h.inputNumberOfStart = (com.neopixl.pixlui.components.edittext.EditText) findViewById(R.id.inputNumberOfStart);
                h.labelPricePerUnit  = (com.neopixl.pixlui.components.textview.TextView) findViewById(R.id.labelPricePerUnit);
                h.inputPricePerUnit  = (com.neopixl.pixlui.components.edittext.EditText) findViewById(R.id.inputPricePerUnit);
                h.labelWeightPerUnit = (com.neopixl.pixlui.components.textview.TextView) findViewById(R.id.labelWeightPerUnit);
                h.inputWeightPerUnit = (com.neopixl.pixlui.components.edittext.EditText) findViewById(R.id.inputWeightPerUnit);

                h.inputNumberOfStart.setImeOptions(EditorInfo.IME_ACTION_DONE);
                h.labelNumberOfStart.setText("จำนวนแม่โครีดนม");
                h.labelPricePerUnit.setVisibility(View.GONE);
                h.inputPricePerUnit.setVisibility(View.GONE);
                h.labelWeightPerUnit.setVisibility(View.GONE);
                h.inputWeightPerUnit.setVisibility(View.GONE);


            }else if(userPlotModel.getPrdID().equals("44")){
                h.labelPricePerUnit = (com.neopixl.pixlui.components.textview.TextView) findViewById(R.id.labelPricePerUnit);
                h.inputPricePerUnit = (com.neopixl.pixlui.components.edittext.EditText) findViewById(R.id.inputPricePerUnit);

                h.labelPricePerUnit.setVisibility(View.GONE);
                h.inputPricePerUnit.setVisibility(View.GONE);

            }


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

            if(userPlotModel.getPrdID().equals("49")){

              //   h.fishKcLayout  = (LinearLayout) findViewById(R.id.fishKcLayout);
                 //h.fishVaLayout  = (LinearLayout) findViewById(R.id.fishVaLayout);

                //h.fishBoLayout.setVisibility(View.GONE);
                //h.fishKcLayout.setVisibility(View.GONE);


            }else {

                h.fishBoLayout  = (LinearLayout) findViewById(R.id.bo_layout);
               // h.fishKcLayout  = (LinearLayout) findViewById(R.id.fishKcLayout);
                h.fishVaLayout  = (LinearLayout) findViewById(R.id.fishVaLayout);

                h.fishBoLayout.setVisibility(View.GONE);

            }

        }else{
            startActivity(new Intent(StepThreeActivity.this, LoginActivity.class));
            finish();

        }

    }

    private void setAction() {

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


                if ("1".equals(userPlotModel.getPrdGrpID())) {

                    isValidate = isValidPlantInputData();
                    preparePlantDataForInsert(userPlotModel);

                }else if("2".equals(userPlotModel.getPrdGrpID())){

                    isValidate = isValidAnimalInputData();
                    prepareAnimalDataForInsert(userPlotModel);

                }else if("3".equals(userPlotModel.getPrdGrpID())){

                    isValidFishInputData ();
                    prepareFishDataForInsert(userPlotModel);

                }else{
                    isValidate = false;
                }


                if (isValidate) {
                    new DialogChoice(StepThreeActivity.this, new DialogChoice.OnSelectChoiceListener() {
                        @Override
                        public void OnSelect(int choice) {

                            if (choice == DialogChoice.OK) {
                                upsertUserPlot(userPlotModel);

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

        if("3".equals(userPlotModel.getPrdGrpID())){

            if(!userPlotModel.getPrdID().equals("49")){


                findViewById(R.id.imgKcChoice).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      //  if (!kcSelected) {
                        findViewById(R.id.bo_layout).setVisibility(View.GONE);
                            findViewById(R.id.kc_layout).setVisibility(View.VISIBLE);


                            ((ImageView)findViewById(R.id.imgKcChoice)).setImageResource(R.drawable.radio_select);
                            ((ImageView)findViewById(R.id.imgBoChoice)).setImageResource(R.drawable.radio_not_select);
                            kcSelected = true;
                        //}
                    }
                });


                findViewById(R.id.imgBoChoice).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG,"kcSelected __>"+  kcSelected);
                       // if (kcSelected) {
                            findViewById(R.id.kc_layout).setVisibility(View.GONE);
                            findViewById(R.id.bo_layout).setVisibility(View.VISIBLE);

                            ((ImageView)findViewById(R.id.imgKcChoice)).setImageResource(R.drawable.radio_not_select);
                            ((ImageView)findViewById(R.id.imgBoChoice)).setImageResource(R.drawable.radio_select);
                            kcSelected = false;
                      //  }
                    }
                });


            }


        }

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


                selectedAmphoe = null;
                selectedTumbon = null;
                if(provinceRespList.get(position).getProvCode().equals("0")){
                    selectedprovince = null;
                    provinceTextView.setText("");
                }else{
                    selectedprovince = provinceRespList.get(position);
                    provinceTextView.setText(selectedprovince.getProvNameTH());

                }

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

                selectedTumbon = null;

                if(amphoeRespList.get(position).getAmpCode().equals("0")) {
                    selectedAmphoe = null;
                    amphoeTextView.setText("");
                }else{
                    selectedAmphoe = amphoeRespList.get(position);
                    amphoeTextView.setText(selectedAmphoe.getAmpNameTH());
                }

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

             //   TextView amphoeTextView = (TextView) findViewById(R.id.inputAmphoe);
                TextView tumbonTextView = (TextView) findViewById(R.id.inputTumbon);
                if(tunbonRespList.get(position).getTamCode().equals("0")){
                    selectedTumbon = null;
                    tumbonTextView.setText("");
                }else{
                    selectedTumbon = tunbonRespList.get(position);
                    tumbonTextView.setText(selectedTumbon.getTamNameTH());
                }
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
            mProvince.mRespBody defaultProvince = new mProvince.mRespBody();
            defaultProvince.setProvCode("0");
            defaultProvince.setProvNameTH("ไม่ระบุ");

            if (provinceBodyLists.size() != 0) {
                provinceBodyLists.add(0,defaultProvince);
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
                mAmphoe.mRespBody defaultAmphoe = new mAmphoe.mRespBody();
                defaultAmphoe.setAmpCode("0");
                defaultAmphoe.setAmpNameTH("ไม่ระบุ");

                if (amphoeBodyLists.size() != 0) {
                    amphoeBodyLists.add(0,defaultAmphoe);
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
                mTumbon.mRespBody defaultTumbon = new mTumbon.mRespBody();
                defaultTumbon.setTamCode("0");
                defaultTumbon.setTamNameTH("ไม่ระบุ");

                if (tumbonBodyLists.size() != 0) {
                    tumbonBodyLists.add(0,defaultTumbon);
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

    private void API_SavePlotDetail(String saveFlag, final UserPlotModel userPlotInfo) {
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

                    String plotId =  String.valueOf(savePlotDetailBodyLists.get(0).getPlotID());
                    Log.d(TAG,"Response plotId : "+plotId);
                    userPlotInfo.setPlotID(plotId);
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
                "&ImeiCode=" +ServiceInstance.GetDeviceID(StepThreeActivity.this)+
                "&VarName=" +userPlotInfo.getVarName()+
                "&VarValue=" +userPlotInfo.getVarValue()+
                "&CalResult="+userPlotInfo.getCalResult()
        );
    }

    private void upsertUserPlot(UserPlotModel userPlotInfol){

        // API_GetUserPlot(userId,prdId,prdGrpId,plotId);

        if("".equals(userPlotInfol.getPlotID())){
            Log.d(TAG, "Go to save plot Module ");
            API_SavePlotDetail("1", userPlotInfol);
        }else{

            Log.d(TAG, "Go to update plot Module ");
            API_SavePlotDetail("2", userPlotInfol);

        }

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

    //======================== Prepare data && validate ===============================

    private boolean isValidPlantInputData (){
        boolean isValid = false;
        EditText inputRai = (EditText) findViewById(R.id.inputRai);

        if(inputRai.getText() == null || inputRai.getText().toString().equals("")){
            new DialogChoice(StepThreeActivity.this).ShowOneChoice("", "กรุณากรอกขนาดแปลงที่ดิน");
        }else{
            isValid = true;
        }


        /*
        else if(selectedprovince == null || selectedAmphoe == null || selectedTumbon == null){
            new DialogChoice(StepThreeActivity.this).ShowOneChoice("", "กรุณากรอกตำแหน่งแปลงทีดิน");
        }
         */

        return isValid;

    }

    private boolean isValidAnimalInputData () {
        boolean isValid = false;


        if(   userPlotModel.getPrdID().equals("39")
                || userPlotModel.getPrdID().equals("40")
                || userPlotModel.getPrdID().equals("41")
                || userPlotModel.getPrdID().equals("42")){

            EditText inputNumberOfStart = (EditText) findViewById(R.id.inputNumberOfStart);
            EditText inputPricePerUnit = (EditText) findViewById(R.id.inputPricePerUnit);

            if (inputNumberOfStart.getText() == null || inputNumberOfStart.getText().toString().equals("")) {
                new DialogChoice(StepThreeActivity.this).ShowOneChoice("", "กรุณากรอก จำนวนเมื่อเริ่มเลี้ยง");
            } else if (inputPricePerUnit.getText() == null || inputPricePerUnit.getText().toString().equals("")){
                new DialogChoice(StepThreeActivity.this).ShowOneChoice("", "กรุณากรอก ราคาเมื่อเริ่มเลียง");
            } else{
                isValid = true;
            }

        }else if( userPlotModel.getPrdID().equals("43")){
            EditText inputNumberOfStart = (EditText) findViewById(R.id.inputNumberOfStart);

            if (inputNumberOfStart.getText() == null || inputNumberOfStart.getText().toString().equals("")) {
                new DialogChoice(StepThreeActivity.this).ShowOneChoice("", "กรุณากรอก จำนวนแม่โครีดนม");
            } else{
                isValid = true;
            }


        }else if( userPlotModel.getPrdID().equals("44")){
            EditText inputNumberOfStart = (EditText) findViewById(R.id.inputNumberOfStart);
            EditText inputWeightPerUnit = (EditText) findViewById(R.id.inputWeightPerUnit);

            if (inputNumberOfStart.getText() == null || inputNumberOfStart.getText().toString().equals("")) {
                new DialogChoice(StepThreeActivity.this).ShowOneChoice("", "กรุณากรอก จำนวนเมื่อเริ่มเลี้ยง");
            } else if (inputWeightPerUnit.getText() == null || inputWeightPerUnit.getText().toString().equals("")){
                new DialogChoice(StepThreeActivity.this).ShowOneChoice("", "กรุณากรอก น้ำหนักเฉลี่ยเมื่อเลียง");
            } else{
                isValid = true;
            }

        }else{
            isValid = false;
        }

        return isValid;

    }

    private boolean isValidFishInputData () {
        boolean isValid = false;

        return isValid;

    }

    private UserPlotModel preparePlantDataForInsert(UserPlotModel userPlotInfo){

        if(selectedTumbon!=null)   { userPlotModel.setTamCode(selectedTumbon.getTamCode());}
        if(selectedAmphoe!=null)   { userPlotModel.setAmpCode(selectedAmphoe.getAmpCode());}
        if(selectedprovince!=null) {userPlotModel.setProvCode(selectedprovince.getProvCode());}

        userPlotModel.setPlotRai(((EditText)findViewById(R.id.inputRai)).getText().toString());
        return userPlotInfo;
    }

    private UserPlotModel prepareAnimalDataForInsert(UserPlotModel userPlotInfo){

        if(selectedTumbon!=null)   { userPlotModel.setTamCode(selectedTumbon.getTamCode());}
        if(selectedAmphoe!=null)   { userPlotModel.setAmpCode(selectedAmphoe.getAmpCode());}
        if(selectedprovince!=null) {userPlotModel.setProvCode(selectedprovince.getProvCode());}

        if(   userPlotModel.getPrdID().equals("39")
                || userPlotModel.getPrdID().equals("40")
                || userPlotModel.getPrdID().equals("41")
                || userPlotModel.getPrdID().equals("42")){


            userPlotModel.setAnimalNumber(((EditText)findViewById(R.id.inputNumberOfStart)).getText().toString());
            userPlotModel.setAnimalPrice(((EditText)findViewById(R.id.inputPricePerUnit)).getText().toString());

        }else if(userPlotModel.getPrdID().equals("43")){

            userPlotModel.setAnimalNumber(((EditText)findViewById(R.id.inputNumberOfStart)).getText().toString());

        }else if(userPlotModel.getPrdID().equals("44")){

            userPlotModel.setAnimalNumber(((EditText)findViewById(R.id.inputNumberOfStart)).getText().toString());
            userPlotModel.setAnimalWeight(((EditText)findViewById(R.id.inputWeightPerUnit)).getText().toString());
        }


        return userPlotInfo;
    }

    private UserPlotModel prepareFishDataForInsert(UserPlotModel userPlotInfo){

        if(   userPlotModel.getPrdID().equals("39")
                || userPlotModel.getPrdID().equals("40")
                || userPlotModel.getPrdID().equals("41")
                || userPlotModel.getPrdID().equals("42")){

            if(selectedTumbon!=null)   { userPlotModel.setTamCode(selectedTumbon.getTamCode());}
            if(selectedAmphoe!=null)   { userPlotModel.setAmpCode(selectedAmphoe.getAmpCode());}
            if(selectedprovince!=null) {userPlotModel.setProvCode(selectedprovince.getProvCode());}

        }


        return userPlotInfo;
    }

    class Holder{

        ImageView prodImg ;
        LinearLayout prodBg,fishBoLayout,fishKcLayout,fishVaLayout;
        com.neopixl.pixlui.components.textview.TextView labelProductName,labelProductHierarchy,labelNumberOfStart
                ,labelPricePerUnit,labelWeightPerUnit;
       com.neopixl.pixlui.components.edittext.EditText inputPricePerUnit ,inputWeightPerUnit,inputNumberOfStart;
    }


}

/*
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


                }

            }

            @Override
            public void callbackError(int code, String errorMsg) {

            }
        }).API_Request(false, RequestServices.ws_getPlotList +
                "?UserID=" + userId +
                "&PlotID=" + plotId +
                "&ImeiCode=" + ServiceInstance.GetDeviceID(StepThreeActivity.this)
        );

    }
*/
