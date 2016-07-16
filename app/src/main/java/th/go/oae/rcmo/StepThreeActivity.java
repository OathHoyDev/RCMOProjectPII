package th.go.oae.rcmo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import th.go.oae.rcmo.API.RequestServices;
import th.go.oae.rcmo.API.ResponseAPI;
import th.go.oae.rcmo.Adapter.DialogAmphoeAdapter;
import th.go.oae.rcmo.Adapter.DialogProvinceAdapter;
import th.go.oae.rcmo.Adapter.DialogTumbonAdapter;
import th.go.oae.rcmo.Model.ProductModel;
import th.go.oae.rcmo.Model.UserPlotModel;
import th.go.oae.rcmo.Module.mAmphoe;
import th.go.oae.rcmo.Module.mGetVariable;
import th.go.oae.rcmo.Module.mProvince;
import th.go.oae.rcmo.Module.mSavePlotDetail;
import th.go.oae.rcmo.Module.mTumbon;
import th.go.oae.rcmo.Module.mUserPlotList;
import th.go.oae.rcmo.Util.BitMapHelper;
import th.go.oae.rcmo.Util.InputFilterMinMax;
import th.go.oae.rcmo.Util.ServiceInstance;
import th.go.oae.rcmo.Util.StepIIITextWatcher;
import th.go.oae.rcmo.Util.Util;
import th.go.oae.rcmo.View.DialogChoice;
import th.go.oae.rcmo.View.dialog_amphoe;
import th.go.oae.rcmo.View.dialog_province;
import th.go.oae.rcmo.View.dialog_tambon;

public class StepThreeActivity extends Activity {
     mProvince.mRespBody  selectedprovince = null;
     mAmphoe.mRespBody    selectedAmphoe    = null;
     mTumbon.mRespBody    selectedTumbon   = null;
    public static ProductModel productionInfo = null;
    UserPlotModel userPlotModel = new UserPlotModel();
    String TAG = "StepThreeActivity_TAG";
    boolean kcSelected =true , tuaSelected = true;
    String userId ="" ;
    String plotId ="";
    boolean saved = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences sp = getSharedPreferences(ServiceInstance.PREF_NAME, Context.MODE_PRIVATE);
        userId = sp.getString(ServiceInstance.sp_userId, "0");

        userPlotModel.setPrdGrpID(String.valueOf(productionInfo.getPrdGrpID()));
        userPlotModel.setPrdID(String.valueOf(productionInfo.getPrdID()));
        userPlotModel.setPrdGrpID( String.valueOf(productionInfo.getPrdGrpID()));
        userPlotModel.setUserID(userId);
        saved  = false;

        setUI();
        setAction();

    }


    private void setUI() {
        int groupId = Integer.valueOf(userPlotModel.getPrdGrpID());
       this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if(groupId == 1){
            setContentView(R.layout.activity_plant_step_three);
            findViewById(R.id.mainLayoutPlantStepThree).setBackground(new BitmapDrawable(BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.bg_plant, 300, 400)));
            //Get Object
            Holder h = new Holder();
            h.prodImg      = (ImageView) findViewById(R.id.prodImg);
            h.prodBg      =  (LinearLayout)findViewById(R.id.gridDrawBg);
            h.labelProductName      = (com.neopixl.pixlui.components.textview.TextView) findViewById(R.id.labelProductName);
            h.labelProductHierarchy = (com.neopixl.pixlui.components.textview.TextView) findViewById(R.id.labelProductHierarchy);


            //Set Dynamic Ui
            h.labelProductName.setText(productionInfo.getPrdName());
            h.labelProductHierarchy.setText(ServiceInstance.genProdHierarchy("กลุ่มพืช",productionInfo.getProdHierarchy()));

            EditText inputRai   =  (EditText)findViewById(R.id.inputRai);
            EditText inputNgan  =  (EditText)findViewById(R.id.inputNgan);
            EditText inputWa    =  (EditText)findViewById(R.id.inputWa);
            EditText inputMeter =  (EditText)findViewById(R.id.inputMeter);

            inputRai.addTextChangedListener(new StepIIITextWatcher(inputRai, h, ""));
            inputNgan.setFilters(new InputFilter[]{ new InputFilterMinMax(0, 4)});
            inputWa.setFilters(new InputFilter[]{ new InputFilterMinMax(0, 100)});


            inputMeter.setFilters(new InputFilter[]{ new InputFilterMinMax(0, 400)});
            inputMeter.setText("0");
            inputMeter.setVisibility(View.GONE);

            String imgName = ServiceInstance.productIMGMap.get(productionInfo.getPrdID());
            if(imgName!=null) {
                h.prodImg.setImageResource(getResources().getIdentifier(imgName, "drawable", getPackageName()));
            }

            h.prodBg.setBackgroundResource(R.drawable.plant_ic_circle_bg);

        }else if(groupId == 2){
            setContentView(R.layout.activity_animal_step_three);
            findViewById(R.id.mainLayoutAnimalStepThree).setBackground(new BitmapDrawable(BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.bg_meat, 300, 400)));

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
            findViewById(R.id.mainLayoutFishStepThree).setBackground(new BitmapDrawable(BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.bg_fish, 300, 400)));
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

                h.fishKcLayout  = (LinearLayout) findViewById(R.id.kc_layout);
                h.fishBoLayout  = (LinearLayout) findViewById(R.id.bo_layout);

                h.fishKcLayout.setVisibility(View.GONE);
                h.fishBoLayout.setVisibility(View.GONE);
                findViewById(R.id.layout_imgBoChoice).setVisibility(View.GONE);
                findViewById(R.id.layout_imgKcChoice).setVisibility(View.GONE);

                EditText va_inputRai = (EditText) findViewById(R.id.va_inputRai);
                va_inputRai.addTextChangedListener(new StepIIITextWatcher(va_inputRai, h, ""));


                EditText va_inputNuberOfva = (EditText) findViewById(R.id.va_inputNuberOfva);
                va_inputNuberOfva.addTextChangedListener(new StepIIITextWatcher(va_inputNuberOfva, h, ""));

                EditText va_inputNgan = (EditText) findViewById(R.id.va_inputNgan);
                va_inputNgan.setFilters(new InputFilter[]{ new InputFilterMinMax(0, 4)});



                EditText va_inputSqWa = (EditText) findViewById(R.id.va_inputSqWa);
                va_inputSqWa.setFilters(new InputFilter[]{ new InputFilterMinMax(0, 100)});




            }else {
                //kc
                EditText kc_inputSqMPerKC = (EditText) findViewById(R.id.kc_inputSqMPerKC);
                kc_inputSqMPerKC.addTextChangedListener(new StepIIITextWatcher(kc_inputSqMPerKC, h, ""));

                EditText kc_inputNuberOfKC = (EditText) findViewById(R.id.kc_inputNuberOfKC);
                kc_inputNuberOfKC.addTextChangedListener(new StepIIITextWatcher(kc_inputNuberOfKC, h, ""));

                EditText kc_inputFishPerKC = (EditText) findViewById(R.id.kc_inputFishPerKC);
                kc_inputFishPerKC.addTextChangedListener(new StepIIITextWatcher(kc_inputFishPerKC, h, ""));


                //bo
                EditText bo_inputRai = (EditText) findViewById(R.id.bo_inputRai);
                bo_inputRai.addTextChangedListener(new StepIIITextWatcher(bo_inputRai, h, ""));

                EditText bo_inputNgan = (EditText) findViewById(R.id.bo_inputNgan);
                bo_inputNgan.setFilters(new InputFilter[]{ new InputFilterMinMax(0, 4)});

                EditText bo_inputSqWa = (EditText) findViewById(R.id.bo_inputSqWa);
                bo_inputSqWa.setFilters(new InputFilter[]{ new InputFilterMinMax(0, 100)});

                EditText bo_inputSqMeter = (EditText) findViewById(R.id.bo_inputSqMeter);
                bo_inputSqMeter.setFilters(new InputFilter[]{ new InputFilterMinMax(0, 400)});

                EditText bo_inputNuberOfUnit = (EditText) findViewById(R.id.bo_inputNuberOfUnit);
                bo_inputNuberOfUnit.addTextChangedListener(new StepIIITextWatcher(bo_inputNuberOfUnit, h, ""));




                h.fishBoLayout  = (LinearLayout) findViewById(R.id.bo_layout);
                h.fishVaLayout  = (LinearLayout) findViewById(R.id.va_layout);

                h.fishBoLayout.setVisibility(View.GONE);
                h.fishVaLayout.setVisibility(View.GONE);

            }

        }else{
            startActivity(new Intent(StepThreeActivity.this, LoginActivity.class));
            finish();

        }

    }

    private void setAction() {
        //tutorial
        findViewById(R.id.btnHowto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(productionInfo.getPrdGrpID()==1) {
                    new DialogChoice(StepThreeActivity.this)
                            .ShowTutorial("g11");
                }else if(productionInfo.getPrdGrpID()==2){
                    new DialogChoice(StepThreeActivity.this)
                            .ShowTutorial("g12");
                }else{
                    new DialogChoice(StepThreeActivity.this)
                            .ShowTutorial("g13");
                }
            }
        });
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(userPlotModel.getUserID().equals("0")) && saved ){
                    API_GetUserPlot(userPlotModel.getUserID());
                }else{
                    finish();

                }
            }
        });

        findViewById(R.id.btnCal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPlotModel = new UserPlotModel();
                userPlotModel.setPrdGrpID(String.valueOf(productionInfo.getPrdGrpID()));
                userPlotModel.setPrdID(String.valueOf(productionInfo.getPrdID()));
                userPlotModel.setPrdGrpID(String.valueOf(productionInfo.getPrdGrpID()));
                userPlotModel.setPrdValue(productionInfo.getPrdName());
                userPlotModel.setPlotID(plotId);
                //userPlotModel.setUserID(userId);

                boolean isValidate = false;
                if ("1".equals(userPlotModel.getPrdGrpID())) {
                    isValidate = isValidPlantInputData();
                    preparePlantDataForInsert();

                } else if ("2".equals(userPlotModel.getPrdGrpID())) {
                    isValidate = isValidAnimalInputData();
                    prepareAnimalDataForInsert();
                } else if ("3".equals(userPlotModel.getPrdGrpID())) {
                    isValidate = isValidFishInputData();
                    prepareFishDataForInsert();
                }

                if(isValidate) {
                    API_getVariable();

                }else{

                }
            }


        });


        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userPlotModel.setUserID(userId);
                if (userPlotModel.getUserID() == null || userPlotModel.getUserID().equals("0") || userPlotModel.getUserID().equals("")) {
                    new DialogChoice(StepThreeActivity.this)
                            .ShowOneChoice("ไม่สามารถบันทึกข้อมูล", "- กรุณา Login ก่อนทำการบันทึกข้อมูล");

                } else {
                    boolean isValidate = false;
                    Log.d(TAG, "Product group Id :" + userPlotModel.getPrdGrpID());

                    userPlotModel = new UserPlotModel();
                    userPlotModel.setPrdGrpID(String.valueOf(productionInfo.getPrdGrpID()));
                    userPlotModel.setPrdID(String.valueOf(productionInfo.getPrdID()));
                    userPlotModel.setPrdGrpID(String.valueOf(productionInfo.getPrdGrpID()));
                    userPlotModel.setUserID(userId);
                    userPlotModel.setPlotID(plotId);

                    if ("1".equals(userPlotModel.getPrdGrpID())) {

                        isValidate = isValidPlantInputData();
                        preparePlantDataForInsert();

                    } else if ("2".equals(userPlotModel.getPrdGrpID())) {

                        isValidate = isValidAnimalInputData();
                        prepareAnimalDataForInsert();

                    } else if ("3".equals(userPlotModel.getPrdGrpID())) {

                        isValidate = isValidFishInputData();
                        prepareFishDataForInsert();

                    } else {
                        isValidate = false;
                    }


                    if (isValidate) {
                        new DialogChoice(StepThreeActivity.this, new DialogChoice.OnSelectChoiceListener() {
                            @Override
                            public void OnSelect(int choice) {

                                if (choice == DialogChoice.OK) {
                                    upsertUserPlot();

                                }
                            }
                        }).ShowTwoChoice("\"" + ((TextView) findViewById(R.id.labelProductName)).getText().toString() + "\"", "คุนต้องการบันทึกข้อมูล");
                    }


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

            if(!userPlotModel.getPrdID().equals("49")) {


                findViewById(R.id.layout_imgKcChoice).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!kcSelected) {
                            findViewById(R.id.bo_layout).setVisibility(View.GONE);
                            findViewById(R.id.kc_layout).setVisibility(View.VISIBLE);


                            ((ImageView) findViewById(R.id.imgKcChoice)).setImageResource(R.drawable.radio_select);
                            ((ImageView) findViewById(R.id.imgBoChoice)).setImageResource(R.drawable.radio_not_select);
                            kcSelected = true;
                        }
                    }
                });


                findViewById(R.id.layout_imgBoChoice).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "kcSelected __>" + kcSelected);
                        if (kcSelected) {
                            findViewById(R.id.kc_layout).setVisibility(View.GONE);
                            findViewById(R.id.bo_layout).setVisibility(View.VISIBLE);

                            ((ImageView) findViewById(R.id.imgKcChoice)).setImageResource(R.drawable.radio_not_select);
                            ((ImageView) findViewById(R.id.imgBoChoice)).setImageResource(R.drawable.radio_select);
                            kcSelected = false;
                        }
                    }
                });



                    findViewById(R.id.layout_boimgTuaChoice).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d(TAG, "bo_tuaSelected __>" +tuaSelected);
                            if (!tuaSelected) {

                                ((ImageView) findViewById(R.id.bo_imgTuaSelected)).setImageResource(R.drawable.radio_select);
                                ((ImageView) findViewById(R.id.bo_imgkkSelected)).setImageResource(R.drawable.radio_not_select);
                                EditText inputUnit = (EditText)findViewById(R.id.bo_inputNuberOfUnit);
                                //inputUnit.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                                inputUnit.setHint("ตัว");
                                tuaSelected = true;
                            }
                        }
                    });


                    findViewById(R.id.layout_boimgKkChoice).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d(TAG, "bo_tuaSelected __>" +tuaSelected);
                            if (tuaSelected) {


                                ((ImageView) findViewById(R.id.bo_imgkkSelected)).setImageResource(R.drawable.radio_select);
                                ((ImageView) findViewById(R.id.bo_imgTuaSelected)).setImageResource(R.drawable.radio_not_select);
                                EditText inputUnit = (EditText)findViewById(R.id.bo_inputNuberOfUnit);
                                inputUnit.setHint("กิโลกรัม");
                                //inputUnit.setRawInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                                tuaSelected = false;
                            }
                        }
                    });

            }else {




            }

        }

    }


    private void popUpProvinceListDialog(final List<mProvince.mRespBody> provinceRespList) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
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
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
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
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
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
               // popUpProvinceListDialog(provinceBodyLists);

                new dialog_province(StepThreeActivity.this,"จังหวัด",provinceBodyLists, new dialog_province.OnSelectChoice() {
                    @Override
                    public void selectChoice(mProvince.mRespBody choice) {
                        selectedprovince = choice;
                        TextView provinceTextView = (TextView) findViewById(R.id.inputprovice);
                        TextView amphoeTextView = (TextView) findViewById(R.id.inputAmphoe);
                        TextView tumbonTextView = (TextView) findViewById(R.id.inputTumbon);
                        selectedAmphoe = null;
                        selectedTumbon = null;
                        if(selectedprovince.getProvCode().equals("0")){
                            selectedprovince = null;
                            provinceTextView.setText("");
                        }else{
                            provinceTextView.setText(selectedprovince.getProvNameTH());
                        }
                        amphoeTextView.setText("");
                        tumbonTextView.setText("");

                    }
                });
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
                   // popUpAmphoeListDialog(amphoeBodyLists);
                    new dialog_amphoe(StepThreeActivity.this,"อำเภอ",amphoeBodyLists, new dialog_amphoe.OnSelectChoice() {
                        @Override
                        public void selectChoice(mAmphoe.mRespBody choice) {
                            selectedAmphoe = choice;
                            TextView amphoeTextView = (TextView) findViewById(R.id.inputAmphoe);
                            TextView tumbonTextView = (TextView) findViewById(R.id.inputTumbon);

                            selectedTumbon = null;


                            if(selectedAmphoe.getAmpCode().equals("0")) {
                                selectedAmphoe = null;
                                amphoeTextView.setText("");
                            }else{
                                amphoeTextView.setText(selectedAmphoe.getAmpNameTH());
                            }
                            tumbonTextView.setText("");

                        }
                    });
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
                  //  popUpTumbonListDialog(tumbonBodyLists);
                    new dialog_tambon(StepThreeActivity.this,"ตำบล", tumbonBodyLists, new dialog_tambon.OnSelectChoice() {
                        @Override
                        public void selectChoice(mTumbon.mRespBody choice) {
                            selectedTumbon = choice;

                            TextView tumbonTextView = (TextView) findViewById(R.id.inputTumbon);
                            if(selectedTumbon.getTamCode().equals("0")){
                                selectedTumbon = null;
                                tumbonTextView.setText("");
                            }else{
                                tumbonTextView.setText(selectedTumbon.getTamNameTH());
                            }
                        }
                    });
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
                    Log.d(TAG,"Response plotId : "+plotId);
                    saved = true;
                    Util.showDialogAndDismiss(StepThreeActivity.this,"บันทึกข้อมูลสำเร็จ");
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
                "&PlotNgan=" +userPlotInfo.getPlotNgan() +
                "&PlotWa=" +userPlotInfo.getPlotWa() +
                "&PlotMeter=" +userPlotInfo.getPlotMeter() +
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

                    Intent intent = new Intent(StepThreeActivity.this, UserPlotListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                List<mUserPlotList.mRespBody> userPlotList = new ArrayList<mUserPlotList.mRespBody>();
                UserPlotListActivity.userPlotRespBodyList = userPlotList;


                startActivity(new Intent(StepThreeActivity.this, UserPlotListActivity.class)
                        .putExtra("userId", userId));
                finish();

            }
        }).API_Request(false, RequestServices.ws_getPlotList +
                "?UserID=" + userId + "&PlotID=" +
                "&ImeiCode=" + ServiceInstance.GetDeviceID(StepThreeActivity.this));

    }

    private void API_getVariable() {

        new ResponseAPI(StepThreeActivity.this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mGetVariable mVariable = (mGetVariable) obj;
                List<mGetVariable.mRespBody> mVariableBodyLists = mVariable.getRespBody();

                if (mVariableBodyLists.size() != 0) {

                    PBProductDetailActivity.userPlotModel = userPlotModel;
                    userPlotModel.setPageId(1);
                    userPlotModel.setFormularCode(mVariableBodyLists.get(0).getFormularCode());

                    startActivity(new Intent(StepThreeActivity.this, PBProductDetailActivity.class));

                }


            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getVariable +
                "?PrdID=" + userPlotModel.getPrdID() +
                "&FisheryType=" + userPlotModel.getFisheryType());

    }

    private void upsertUserPlot(){

        // API_GetUserPlot(userId,prdId,prdGrpId,plotId);

            if ("".equals(userPlotModel.getPlotID())) {
                Log.d(TAG, "Go to save plot Module ");
                API_SavePlotDetail("1", userPlotModel);
            } else {

                Log.d(TAG, "Go to update plot Module ");
                API_SavePlotDetail("2", userPlotModel);

            }



    }

    /*
    private void toastMsg(String msg) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);

        com.neopixl.pixlui.components.textview.TextView text = (com.neopixl.pixlui.components.textview.TextView) layout.findViewById(R.id.toast_label);
        text.setText(msg);

        toast.show();
    }
    */


    //======================== Prepare data && validate ===============================

    private boolean isValidPlantInputData (){
        boolean isValid = true;
        EditText inputRai = (EditText) findViewById(R.id.inputRai);

        String errorMsg = "";
        if(inputRai.getText() == null || inputRai.getText().toString().equals("")){
            errorMsg += "- ขนาดแปลงที่ดิน\n";
            isValid = false;
        }


        if(selectedprovince!=null){
            if(selectedAmphoe!=null){
                if(selectedTumbon == null){
                    errorMsg+= "- ตำบล \n";
                    isValid = false;
                }
            }else{
                errorMsg+=  "- อำเภอ \n- ตำบล";
                isValid = false;
            }
        }

        if(!isValid){
            new DialogChoice(StepThreeActivity.this).ShowOneChoice("กรุณากรอกข้อมูล", errorMsg);
        }


        return isValid;

    }

    private boolean isValidAnimalInputData () {
        boolean isValid = true;

        Log.d(TAG,"Province : "+selectedprovince);
        Log.d(TAG,"Amphoe : "+selectedAmphoe);
        Log.d(TAG,"Tumbon : "+selectedTumbon);

        if(   userPlotModel.getPrdID().equals("39")
                || userPlotModel.getPrdID().equals("40")
                || userPlotModel.getPrdID().equals("41")
                || userPlotModel.getPrdID().equals("42")){

            EditText inputNumberOfStart = (EditText) findViewById(R.id.inputNumberOfStart);
            EditText inputPricePerUnit = (EditText) findViewById(R.id.inputPricePerUnit);

            String errorMsg = "";
            if (inputNumberOfStart.getText() == null || inputNumberOfStart.getText().toString().equals("")) {
                errorMsg+= "- จำนวนเมื่อเริ่มเลี้ยง \n";
                isValid = false;
            }

            if (inputPricePerUnit.getText() == null || inputPricePerUnit.getText().toString().equals("")){
                errorMsg+= "- ราคาเมื่อเริ่มเลียง \n";
                isValid = false;
            }

            if(selectedprovince!=null){
                if(selectedAmphoe!=null){
                    if(selectedTumbon == null){
                        errorMsg+= "- ตำบล \n";
                        isValid = false;
                    }
                }else{
                    errorMsg+=  "- อำเภอ \n- ตำบล";
                    isValid = false;
                }
            }


            if(!isValid){
                new DialogChoice(StepThreeActivity.this).ShowOneChoice("กรุณากรอกข้อมูล", errorMsg);
            }

        }else if( userPlotModel.getPrdID().equals("43")){
            EditText inputNumberOfStart = (EditText) findViewById(R.id.inputNumberOfStart);
            String errorMsg = "";
            if (inputNumberOfStart.getText() == null || inputNumberOfStart.getText().toString().equals("")) {
                errorMsg+="- จำนวนแม่โครีดนม\n";
                isValid = false;
            }

            if(selectedprovince!=null){
                if(selectedAmphoe!=null){
                    if(selectedTumbon == null){
                        errorMsg+= "- ตำบล \n";
                        isValid = false;
                    }
                }else{
                    errorMsg+=  "- อำเภอ \n- ตำบล";
                    isValid = false;
                }
            }

            if(!isValid){
                new DialogChoice(StepThreeActivity.this).ShowOneChoice("กรุณากรอกข้อมูล", errorMsg);
            }



        }else if( userPlotModel.getPrdID().equals("44")){
            EditText inputNumberOfStart = (EditText) findViewById(R.id.inputNumberOfStart);
            EditText inputWeightPerUnit = (EditText) findViewById(R.id.inputWeightPerUnit);

            String errorMsg = "";
            if (inputNumberOfStart.getText() == null || inputNumberOfStart.getText().toString().equals("")) {
                errorMsg+= "- จำนวนเมื่อเริ่มเลี้ยง \n";
                isValid = false;
            }

            if (inputWeightPerUnit.getText() == null || inputWeightPerUnit.getText().toString().equals("")){
                errorMsg+= "- น้ำหนักเฉลี่ยเมื่อเลียง \n ";
                isValid = false;
            }


            if(selectedprovince!=null){
                if(selectedAmphoe!=null){
                    if(selectedTumbon == null){
                        errorMsg+= "- ตำบล \n";
                        isValid = false;
                    }

                }else{
                    errorMsg+=  "- อำเภอ \n- ตำบล";
                    isValid = false;
                }
            }

            if(!isValid){
                new DialogChoice(StepThreeActivity.this).ShowOneChoice("กรุณากรอกข้อมูล", errorMsg);
            }

        }else{
            isValid = false;
        }

        return isValid;

    }

    private boolean isValidFishInputData() {
        boolean isValid = true;

        if (userPlotModel.getPrdID().equals("49")) {

            EditText va_inputRai       = (EditText) findViewById(R.id.va_inputRai);
            EditText va_inputNgan      = (EditText) findViewById(R.id.va_inputNgan);
            EditText va_inputSqWa      = (EditText) findViewById(R.id.va_inputSqWa);
            EditText va_inputNuberOfva = (EditText) findViewById(R.id.va_inputNuberOfva);
            String errorMsg = "";
            if (       (va_inputRai.getText() == null || va_inputRai.getText().toString().equals(""))
                  &&   (va_inputNgan.getText() == null || va_inputNgan.getText().toString().equals(""))
                  &&   (va_inputSqWa.getText() == null || va_inputSqWa.getText().toString().equals(""))
                    )  {
                errorMsg+= "- ขนาดแปลงที่ดิน \n";
                isValid = false;
            }

            if (va_inputNuberOfva.getText() == null || va_inputNuberOfva.getText().toString().equals("")) {
                errorMsg+= "- จำนวนลูกกุ้งที่ปล่อย \n";
                isValid = false;
            }

            if(selectedprovince!=null){
                if(selectedAmphoe!=null){
                    if(selectedTumbon == null){
                        errorMsg+= "- ตำบล \n";
                        isValid = false;
                    }
                }else{
                    errorMsg+=  "- อำเภอ \n- ตำบล";
                    isValid = false;
                }
            }

            if(!isValid){
                new DialogChoice(StepThreeActivity.this).ShowOneChoice("กรุณากรอกข้อมูล", errorMsg);
            }

        } else {
            // kc is a : kachang(กระชัง) in thai
            // bo is a : (บ่อ) in thai
            // va is a : แวนนาโม(กุ้ง)
            String errorMsg = "";
            if (kcSelected) {
                EditText kc_inputSqMPerKC = (EditText) findViewById(R.id.kc_inputSqMPerKC);
                EditText kc_inputNumberOfKC = (EditText) findViewById(R.id.kc_inputNuberOfKC);
                EditText kc_inputFishPerKC = (EditText) findViewById(R.id.kc_inputFishPerKC);

                if (kc_inputSqMPerKC.getText() == null || kc_inputSqMPerKC.getText().toString().equals("")) {

                    errorMsg+= "- ขนาดแปลงที่ดิน \n";
                    isValid = false;
                }
                if (kc_inputNumberOfKC.getText() == null || kc_inputNumberOfKC.getText().toString().equals("")) {
                    errorMsg+= "- จำนวนกระชังที่เลี้ยงในรุ่นนี้ \n";
                    isValid = false;
                }
                if (kc_inputFishPerKC.getText() == null || kc_inputFishPerKC.getText().toString().equals("")) {
                    errorMsg+= "- จำนวนลูกปลาที่ปล่อย ต่อ 1 กระชัง";
                    isValid = false;
                }

                if(selectedprovince!=null){
                    if(selectedAmphoe!=null){
                        if(selectedTumbon == null){
                            errorMsg+= "- ตำบล \n";
                            isValid = false;
                        }
                    }else{
                        errorMsg+=  "- อำเภอ \n- ตำบล";
                        isValid = false;
                    }
                }

                if(!isValid){
                    new DialogChoice(StepThreeActivity.this).ShowOneChoice("กรุณากรอกข้อมูล", errorMsg);
                }


            } else {

                EditText bo_inputRai       = (EditText) findViewById(R.id.bo_inputRai);
                EditText bo_inputNgan      = (EditText) findViewById(R.id.bo_inputNgan);
                EditText bo_inputSqWa      = (EditText) findViewById(R.id.bo_inputSqWa);
                EditText bo_inputSqMeter     = (EditText) findViewById(R.id.bo_inputSqMeter);

                EditText bo_inputNuberOfUnit = (EditText) findViewById(R.id.bo_inputNuberOfUnit);

                if (       (bo_inputRai.getText() == null || bo_inputRai.getText().toString().equals(""))
                        &&   (bo_inputNgan.getText() == null || bo_inputNgan.getText().toString().equals(""))
                        &&   (bo_inputSqWa.getText() == null || bo_inputSqWa.getText().toString().equals(""))
                        &&   (bo_inputSqMeter.getText() == null || bo_inputSqMeter.getText().toString().equals(""))
                        )  {

                    errorMsg+= "- ขนาดแปลงที่ดิน \n";
                    isValid = false;
                }
                if (bo_inputNuberOfUnit.getText() == null || bo_inputNuberOfUnit.getText().toString().equals("")) {
                    errorMsg+= "- จำนวนลูกปลาที่ปล่อย ";
                    isValid = false;
                }

                if(selectedprovince!=null){
                    if(selectedAmphoe!=null){
                        if(selectedTumbon == null){
                            errorMsg+= "- ตำบล \n";
                            isValid = false;
                        }
                    }else{
                        errorMsg+=  "- อำเภอ \n- ตำบล";
                        isValid = false;
                    }
                }

                if(!isValid){
                    new DialogChoice(StepThreeActivity.this).ShowOneChoice("กรุณากรอกข้อมูล", errorMsg);
                }

            }

        }

        return isValid;

    }

    private void preparePlantDataForInsert(){

        if(selectedTumbon!=null)   { userPlotModel.setTamCode(selectedTumbon.getTamCode());}
        if(selectedAmphoe!=null)   { userPlotModel.setAmpCode(selectedAmphoe.getAmpCode());}
        if(selectedprovince!=null) {userPlotModel.setProvCode(selectedprovince.getProvCode());}

        EditText inputRai   =  (EditText)findViewById(R.id.inputRai);
        EditText inputNgan  =  (EditText)findViewById(R.id.inputNgan);
        EditText inputWa    =  (EditText)findViewById(R.id.inputWa);
        EditText inputMeter =  (EditText)findViewById(R.id.inputMeter);

        userPlotModel.setPlotRai(Util.clearStrNumberFormat(inputRai.getText().toString()));
        userPlotModel.setPlotNgan(inputNgan.getText().toString());
        userPlotModel.setPlotWa(inputWa.getText().toString());
        userPlotModel.setPlotMeter(inputMeter.getText().toString());

    }

    private void prepareAnimalDataForInsert(){

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

    }

    private void prepareFishDataForInsert(){
        if(selectedTumbon!=null)   { userPlotModel.setTamCode(selectedTumbon.getTamCode());}
        if(selectedAmphoe!=null)   { userPlotModel.setAmpCode(selectedAmphoe.getAmpCode());}
        if(selectedprovince!=null) {userPlotModel.setProvCode(selectedprovince.getProvCode());}


        if (userPlotModel.getPrdID().equals("49")) {

            EditText va_inputRai       = (EditText) findViewById(R.id.va_inputRai);
            EditText va_inputNgan      = (EditText) findViewById(R.id.va_inputNgan);
            EditText va_inputSqWa      = (EditText) findViewById(R.id.va_inputSqWa);
            EditText va_inputNuberOfva = (EditText) findViewById(R.id.va_inputNuberOfva);

//StringUtil.strToDoubleDefaultZero();

            this.userPlotModel.setPondRai(Util.clearStrNumberFormat(va_inputRai.getText().toString()));

            this.userPlotModel.setPondNgan(Util.clearStrNumberFormat(va_inputNgan.getText().toString()));

            this.userPlotModel.setPondWa(Util.clearStrNumberFormat(va_inputSqWa.getText().toString()));


            this.userPlotModel.setFisheryNumber(Util.clearStrNumberFormat(va_inputNuberOfva.getText().toString()));

            this.userPlotModel.setFisheryNumType(ServiceInstance.FISHERY_NUM_TYPE_TUA);
            this.userPlotModel.setFisheryType(ServiceInstance.FISHERY_TYPE_BO);



        } else {
            // kc is a : kachang(กระชัง) in thai
            // bo is a : (บ่อ) in thai
            // va is a : แวนนาโม(กุ้ง)
            Log.d("Test","KaChang Flag"+kcSelected);
            if (kcSelected) {
                EditText kc_inputSqMPerKC = (EditText) findViewById(R.id.kc_inputSqMPerKC);
                EditText kc_inputNumberOfKC = (EditText) findViewById(R.id.kc_inputNuberOfKC);
                EditText kc_inputFishPerKC = (EditText) findViewById(R.id.kc_inputFishPerKC);

                Log.d(TAG,"CoopMeter : "+kc_inputSqMPerKC.getText().toString());

                this.userPlotModel.setCoopMeter(Util.clearStrNumberFormat(kc_inputSqMPerKC.getText().toString()));

                this.userPlotModel.setCoopNumber(Util.clearStrNumberFormat(kc_inputNumberOfKC.getText().toString()));

                this.userPlotModel.setFisheryNumber(Util.clearStrNumberFormat(kc_inputFishPerKC.getText().toString()));

                this.userPlotModel.setFisheryNumType(ServiceInstance.FISHERY_NUM_TYPE_TUA);
                this.userPlotModel.setFisheryType(ServiceInstance.FISHERY_TYPE_KC);


            } else {

                EditText bo_inputRai       = (EditText) findViewById(R.id.bo_inputRai);
                EditText bo_inputNgan      = (EditText) findViewById(R.id.bo_inputNgan);
                EditText bo_inputSqWa      = (EditText) findViewById(R.id.bo_inputSqWa);
                EditText bo_inputSqMeter     = (EditText) findViewById(R.id.bo_inputSqMeter);

                EditText bo_inputNuberOfUnit = (EditText) findViewById(R.id.bo_inputNuberOfUnit);


                this.userPlotModel.setPondRai(Util.clearStrNumberFormat((bo_inputRai.getText().toString())));

                this.userPlotModel.setPondNgan(Util.clearStrNumberFormat((bo_inputNgan.getText().toString())));

                this.userPlotModel.setPondWa(Util.clearStrNumberFormat((bo_inputSqWa.getText().toString())));

                this.userPlotModel.setPondMeter(Util.clearStrNumberFormat((bo_inputSqMeter.getText().toString())));

                if(tuaSelected) {
                    this.userPlotModel.setFisheryNumType(ServiceInstance.FISHERY_NUM_TYPE_TUA);
                    this.userPlotModel.setFisheryNumber(Util.clearStrNumberFormat(bo_inputNuberOfUnit.getText().toString()));
                    this.userPlotModel.setFisheryWeight("");
                }else{
                    this.userPlotModel.setFisheryNumType(ServiceInstance.FISHERY_NUM_TYPE_KK);
                    this.userPlotModel.setFisheryWeight(Util.clearStrNumberFormat(bo_inputNuberOfUnit.getText().toString()));
                    this.userPlotModel.setFisheryNumber("");

                }
                this.userPlotModel.setFisheryType(ServiceInstance.FISHERY_TYPE_BO);

                }
            }

    }

   public class Holder{

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
