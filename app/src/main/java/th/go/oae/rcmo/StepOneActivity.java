package th.go.oae.rcmo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.neopixl.pixlui.components.edittext.EditText;
import com.neopixl.pixlui.components.textview.TextView;
import java.util.ArrayList;
import java.util.List;
import th.go.oae.rcmo.API.RequestServices;
import th.go.oae.rcmo.API.ResponseAPI;
import th.go.oae.rcmo.Module.mAmphoe;
import th.go.oae.rcmo.Module.mCurrentLocation;
import th.go.oae.rcmo.Module.mPlantGroup;
import th.go.oae.rcmo.Module.mProduct;
import th.go.oae.rcmo.Module.mProvince;
import th.go.oae.rcmo.Module.mTumbon;
import th.go.oae.rcmo.Util.GPSTracker;
import th.go.oae.rcmo.Util.ServiceInstance;
import th.go.oae.rcmo.View.DialogChoice;
import th.go.oae.rcmo.View.ProgressAction;


public class StepOneActivity extends Activity {
    mProvince.mRespBody selectedprovince = null;
    mAmphoe.mRespBody selectedAmphoe = null;
    mTumbon.mRespBody selectedTumbon = null;

    List<mProvince.mRespBody> provinces = new ArrayList<>();
    List<mProvince.mRespBody> orgProvinces = new ArrayList<>();

    List<mAmphoe.mRespBody> amphoes = new ArrayList<>();
    List<mAmphoe.mRespBody> orgAmphoes = new ArrayList<>();

    List<mTumbon.mRespBody> tambons = new ArrayList<>();
    List<mTumbon.mRespBody> orgTambons = new ArrayList<>();

    mCurrentLocation.mRespBody currentLocation = null;

    EditText input_search;
    ListView listview;
    ViewHolder h = new ViewHolder();

    boolean goNextStep = false;



    static class ViewHolder {
        private  TextView input_province ,input_amphoe,input_tambon,input_location;
                //label_main_search;
        private  ImageView bg_province,bg_amphoe,bg_tambon,bg_location,img_label_location,step2;
        private LinearLayout layout_click_province,layout_click_amphoe,layout_click_tambon,layout_click_location,layout_search;
        private RelativeLayout layout_province_active,layout_amphoe_active,layout_tambon_active,layout_location_active;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ServiceInstance.isTablet(StepOneActivity.this)) {
            Log.d("TEST", "-->TabLet");
            setContentView(R.layout.activity_step_one_tablet);
        } else {
            setContentView(R.layout.activity_step_one);
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setUI();
        setAction();
    }
    private void setUI() {
        h.bg_province = (ImageView) findViewById(R.id.bg_province);
        h.bg_amphoe = (ImageView) findViewById(R.id.bg_amphoe);
        h.bg_tambon = (ImageView) findViewById(R.id.bg_tambon);
        h.bg_location = (ImageView) findViewById(R.id.bg_amphoe);

        h.img_label_location = (ImageView) findViewById(R.id.img_label_location);
        h.step2 = (ImageView) findViewById(R.id.step2);

      //  h.label_main_search = (TextView) findViewById(R.id.label_main_search);
        h.input_province = (TextView) findViewById(R.id.input_province);
        h.input_amphoe = (TextView) findViewById(R.id.input_amphoe);
        h.input_tambon = (TextView) findViewById(R.id.input_tambon);
        h.input_location = (TextView) findViewById(R.id.input_location);

        h.layout_search = (LinearLayout) findViewById(R.id.layout_search);
        h.layout_click_province = (LinearLayout) findViewById(R.id.layout_click_province);
        h.layout_click_amphoe= (LinearLayout) findViewById(R.id.layout_click_amphoe);
        h.layout_click_tambon = (LinearLayout) findViewById(R.id.layout_click_tambon);
        h.layout_click_location = (LinearLayout) findViewById(R.id.layout_click_location);

        h.layout_province_active = (RelativeLayout) findViewById(R.id.layout_province_active);
        h.layout_amphoe_active = (RelativeLayout) findViewById(R.id.layout_amphoe_active);
        h.layout_tambon_active = (RelativeLayout) findViewById(R.id.layout_tambon_active);
        h.layout_location_active = (RelativeLayout) findViewById(R.id.layout_location_active);


    }

    private void setAction() {
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //tutorial
        findViewById(R.id.btnHowto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogChoice(StepOneActivity.this)
                        .ShowTutorial("g6");

            }
        });

        //province
        h.layout_click_province.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayProvinceSearch();

            }
        });

        //amphoe
        h.layout_click_amphoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedprovince!=null) {
                    displayAmphoeSearch();
                }

            }
        });

        //tambon
        h.layout_click_tambon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedprovince!=null && selectedAmphoe != null) {
                    displayTambonSearch();
                }

            }
        });

        //currentLocation
        h.layout_click_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    displayCurrentLocation();

            }
        });

        //nextStep
        h.step2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goNextStep){
                    StepTwoActivity.selectedprovince = selectedprovince;
                    StepTwoActivity.selectedAmphoe   = selectedAmphoe;
                    StepTwoActivity.selectedTumbon   = selectedTumbon;

                    startActivity(new Intent(StepOneActivity.this, StepTwoActivity.class));
                    /*
                            .putExtra("provCode", selectedprovince.getProvCode())
                            .putExtra("amphoeCode", selectedAmphoe.getAmpCode())
                            .putExtra("tambonCode", selectedTumbon.getTamCode()));
                   // Util.showDialogAndDismiss(StepOneActivity.this,"Go Next Step");
                   */
                }


            }
        });
    }

//============================== API =======================================================
    private void API_GetPlantGroup(final int plantGrpID) {
         /*
       1.PlantGrpID (ไม่บังคับใส่)
        */

        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mPlantGroup mProdist = (mPlantGroup) obj;
                List<mPlantGroup.mRespBody> productLists = mProdist.getRespBody();

                if (productLists.size() != 0) {
                    productLists.get(0).toString();
                  //  StepTwoActivity.plantGroupLists = productLists;
                    startActivity(new Intent(StepOneActivity.this, StepTwoActivity.class)
                            .putExtra(ServiceInstance.INTENT_GROUP_ID, 1));

                }
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getPlantGroup +
                "?PlantGrpID=");

    }

    private void API_GetProduct(final int prdGrpID, int plantGrpID) {
         /*
        1.PrdGrpID (ไม่บังคับใส่)
        2.PlantGrpID (บังคับกรณี PrdGrpID = 1)
        3.PrdID (ไม่บังคับ)
        */
        String prdGrpIDStr = "";
        String plantGrpIDStr = "";

        if (prdGrpID == 1) {
            plantGrpIDStr = String.valueOf(plantGrpID);
        }

        prdGrpIDStr = String.valueOf(prdGrpID);

        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mProduct mProdist = (mProduct) obj;
                List<mProduct.mRespBody> productLists = mProdist.getRespBody();

                if (productLists.size() != 0) {
                    productLists.get(0).toString();
                   // StepTwoActivity.productInfoLists = productLists;
                    startActivity(new Intent(StepOneActivity.this, StepTwoActivity.class)
                            .putExtra(ServiceInstance.INTENT_GROUP_ID, prdGrpID));
                }
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Erroo", errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getProduct +
                "?PrdGrpID=" + prdGrpIDStr + "&PlantGrpID=" + plantGrpIDStr +
                "&PrdID=");

    }

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
               // mProvince.mRespBody defaultProvince = new mProvince.mRespBody();
               // defaultProvince.setProvCode("0");
                //defaultProvince.setProvNameTH("ไม่ระบุ");

                if (provinceBodyLists.size() != 0) {
                   // provinceBodyLists.add(0, defaultProvince);
                    orgProvinces = provinceBodyLists;
                    provinces = orgProvinces;
                    setProvinceUI();

                    ProgressAction.gone(StepOneActivity.this);
                }
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
                ProgressAction.gone(StepOneActivity.this);
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
               // mAmphoe.mRespBody defaultAmphoe = new mAmphoe.mRespBody();
                //defaultAmphoe.setAmpCode("0");
                //defaultAmphoe.setAmpNameTH("ไม่ระบุ");

                if (amphoeBodyLists.size() != 0) {
                   // amphoeBodyLists.add(0, defaultAmphoe);
                    orgAmphoes = amphoeBodyLists;
                    amphoes = orgAmphoes;
                    setAmphoeUI();


                    ProgressAction.gone(StepOneActivity.this);
                }
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
                ProgressAction.gone(StepOneActivity.this);
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
               // mTumbon.mRespBody defaultTumbon = new mTumbon.mRespBody();
                //defaultTumbon.setTamCode("0");
                //defaultTumbon.setTamNameTH("ไม่ระบุ");

                if (tumbonBodyLists.size() != 0) {
                   // tumbonBodyLists.add(0,defaultTumbon);
                    orgTambons = tumbonBodyLists;
                    tambons = orgTambons;
                    setTambonUI();

                    ProgressAction.gone(StepOneActivity.this);
                }
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
                ProgressAction.gone(StepOneActivity.this);
            }
        }).API_Request(true, RequestServices.ws_getTambon +
                "?TamCode=" +
                "&AmpCode=" + amphoeId +
                "&ProvCode=" + provinceId
        );

    }

    private void API_getCurrentLocation(String latitude, String longitude) {
        /**
         1.latitude (บังคับใส่)
         2.longitude (บังคับใส่)
         */
        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mCurrentLocation currentLocationInfo = (mCurrentLocation) obj;

                final List<mCurrentLocation.mRespBody> currentLocationBodyLists = currentLocationInfo.getRespBody();

                if (currentLocationBodyLists.size() != 0) {
                 //  ProveNameTH
                    currentLocation = currentLocationBodyLists.get(0);
                    if(currentLocation.getProvNameTH()  == null || currentLocation.getProvNameTH().equals("")) {
                        currentLocation.setProvNameTH(currentLocation.getProveNameTH());
                    }
                    selectedprovince = new mProvince.mRespBody();
                    selectedprovince.setProvCode(currentLocation.getProvCode());
                    selectedprovince.setProvNameTH(currentLocation.getProvNameTH());

                    selectedAmphoe = new mAmphoe.mRespBody();
                    selectedAmphoe.setProvCode(currentLocation.getProvCode());
                    selectedAmphoe.setAmpCode(currentLocation.getAmpCode());
                    selectedAmphoe.setAmpNameTH(currentLocation.getAmpNameTH());

                    selectedTumbon = new mTumbon.mRespBody();
                    selectedTumbon.setProvCode(currentLocation.getProvCode());
                    selectedTumbon.setAmpCode(currentLocation.getAmpCode());
                    selectedTumbon.setTamCode(currentLocation.getTamCode());
                    selectedTumbon.setTamNameTH(currentLocation.getTamNameTH());

                    // amphoeBodyLists.add(0, defaultAmphoe);
                   // orgAmphoes = amphoeBodyLists;
                   // amphoes = orgAmphoes;
                   // setAmphoeUI();

                   //ProgressAction.gone(StepOneActivity.this);
                    setSelectedLocationUI();
                }
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
                ProgressAction.gone(StepOneActivity.this);
            }
        }).API_Request(true, RequestServices.ws_getCurrentLocation +
                "?Latitude=" + latitude +
                "&Longitude=" + longitude
        );
    }

    //============== Adapter Class ==============================
    class ProvinceAdapter extends BaseAdapter {

        List<mProvince.mRespBody> provinceList;

        ProvinceAdapter(List<mProvince.mRespBody> province) {
            ProvinceAdapter.this.provinceList = province;
        }

        @Override
        public int getCount() {
            return provinceList.size();
        }

        @Override
        public mProvince.mRespBody getItem(int position) {
            return provinceList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) StepOneActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row;
            row = inflater.inflate(R.layout.pii_textview, parent, false);
            TextView choice = (TextView) row.findViewById(R.id.choice);
            choice.setText(getItem(position).getProvNameTH());

            return (row);
        }
    }

    class AmphoeAdapter extends BaseAdapter {

        List<mAmphoe.mRespBody> amphoeList;

        AmphoeAdapter(List<mAmphoe.mRespBody> amphoe) {
            AmphoeAdapter.this.amphoeList = amphoe;
        }

        @Override
        public int getCount() {
            return amphoeList.size();
        }

        @Override
        public mAmphoe.mRespBody getItem(int position) {
            return amphoeList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) StepOneActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row;
            row = inflater.inflate(R.layout.pii_textview, parent, false);
            TextView choice = (TextView) row.findViewById(R.id.choice);
            choice.setText(getItem(position).getAmpNameTH());

            return (row);
        }
    }

    class TambonAdapter extends BaseAdapter {

        List<mTumbon.mRespBody> tambonList;

        TambonAdapter(List<mTumbon.mRespBody> tambon) {
            TambonAdapter.this.tambonList = tambon;
        }

        @Override
        public int getCount() {
            return tambonList.size();
        }

        @Override
        public mTumbon.mRespBody getItem(int position) {
            return tambonList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) StepOneActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row;
            row = inflater.inflate(R.layout.pii_textview, parent, false);
            TextView choice = (TextView) row.findViewById(R.id.choice);
            choice.setText(getItem(position).getTamNameTH());

            return (row);
        }
    }

//==================== Method =============================
    // Province Method

    private void displayProvinceSearch(){
        setSelectedProvince(false);
        prepareNextStep(false);
        if(orgProvinces!=null && orgProvinces.size()>0) {
            provinces = orgProvinces;
            Log.d("Step1","Use orgProvinces old object");
            setProvinceUI();
        }else{
            ProgressAction.show(StepOneActivity.this);
            Log.d("Step1","init orgProvinces object");
            API_getProvince();

        }
    }

    private void setSelectedProvince(boolean selected) {
        selectedAmphoe = null;
        selectedTumbon = null;
        h.input_amphoe.setText("");
        h.input_tambon.setText("");
        if (selected) {

            if(selectedprovince.getProvCode().equals("0")){
                selectedprovince = null;
                h.input_province.setText("");
            }else{
                h.input_province.setText(selectedprovince.getProvNameTH());
                amphoes.clear();
                tambons.clear();

            }

        //    h.label_main_search.setVisibility(View.GONE);
            h.layout_search.setVisibility(View.GONE);

            h.layout_province_active.setVisibility(View.GONE);
            h.layout_click_province.setVisibility(View.VISIBLE);
            h.bg_province.setVisibility(View.VISIBLE);

            displayAmphoeSearch();
        } else {

            selectedprovince = null;
          //  h.label_main_search.setVisibility(View.VISIBLE);
            h.layout_search.setVisibility(View.VISIBLE);

            h.layout_search.startAnimation(AnimationUtils.loadAnimation(this, R.anim.move_in));

            h.layout_province_active.setVisibility(View.VISIBLE);
            h.layout_click_province.setVisibility(View.GONE);
            h.bg_province.setVisibility(View.GONE);

            h.layout_amphoe_active.setVisibility(View.GONE);
            h.layout_click_amphoe.setVisibility(View.VISIBLE);
            h.bg_amphoe.setVisibility(View.VISIBLE);

            h.layout_tambon_active.setVisibility(View.GONE);
            h.layout_click_tambon.setVisibility(View.VISIBLE);
            h.bg_tambon.setVisibility(View.VISIBLE);


        }
    }

    private void setProvinceUI() {

        listview = (ListView) findViewById(R.id.listview);
        input_search = (EditText)findViewById(R.id.input_search);
        input_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(android.widget.TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchProvince();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(input_search.getWindowToken(),
                            InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    return true;
                }
                return false;
            }

        });

        listview.setAdapter(new ProvinceAdapter(orgProvinces));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedprovince = provinces.get(position);
                setSelectedProvince(true);
            }
        });
    }

    private void searchProvince(){

        provinces = new ArrayList<>();
        String key = input_search.getText().toString();

        if(key.length()!=0){
            for(mProvince.mRespBody province : orgProvinces){
                if(province.getProvNameTH().contains(key)){
                    provinces.add(province);
                }
            }
        }else{
            provinces = orgProvinces;
        }
        input_search.setText("");
        listview.removeAllViewsInLayout();
        listview.setAdapter(new ProvinceAdapter(provinces));

    }


    //Amphoe Method

    private void displayAmphoeSearch(){
        setSelectedAmphoe(false);
        prepareNextStep(false);
        if(orgAmphoes!=null && orgAmphoes.size()>0) {
            amphoes = orgAmphoes;
            Log.d("Step1","Use orgAmphoes old object");
            setAmphoeUI();
        }else{
            ProgressAction.show(StepOneActivity.this);
            Log.d("Step1","init orgAmphoes object");
            API_getAmphoe(selectedprovince.getProvCode());

        }
    }

    private void setSelectedAmphoe(boolean selected) {
        selectedTumbon = null;
        h.input_tambon.setText("");
        if (selected) {

            if(selectedAmphoe.getAmpCode().equals("0")) {
                selectedAmphoe = null;
                h.input_amphoe.setText("");
            }else{
                tambons.clear();
                h.input_amphoe.setText(selectedAmphoe.getAmpNameTH());
            }
            h.input_tambon.setText("");

          //  h.label_main_search.setVisibility(View.GONE);
            h.layout_search.setVisibility(View.GONE);

            h.layout_amphoe_active.setVisibility(View.GONE);
            h.layout_click_amphoe.setVisibility(View.VISIBLE);
            h.bg_amphoe.setVisibility(View.VISIBLE);

            displayTambonSearch();

        } else {
            selectedAmphoe = null;
            //h.label_main_search.setVisibility(View.VISIBLE);
            h.layout_search.setVisibility(View.VISIBLE);
            h.layout_search.startAnimation(AnimationUtils.loadAnimation(this, R.anim.move_in));

            h.layout_amphoe_active.setVisibility(View.VISIBLE);
            h.layout_click_amphoe.setVisibility(View.GONE);
            h.bg_amphoe.setVisibility(View.GONE);

            h.layout_tambon_active.setVisibility(View.GONE);
            h.layout_click_tambon.setVisibility(View.VISIBLE);
            h.bg_tambon.setVisibility(View.VISIBLE);
        }
    }

    private void setAmphoeUI() {

        listview = (ListView) findViewById(R.id.listview);
        input_search = (EditText)findViewById(R.id.input_search);
        input_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(android.widget.TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchAmphoe();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(input_search.getWindowToken(),
                            InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    return true;
                }
                return false;
            }

        });

        listview.setAdapter(new AmphoeAdapter(orgAmphoes));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedAmphoe = amphoes.get(position);
                setSelectedAmphoe(true);
            }
        });
    }

    private void searchAmphoe(){

        amphoes = new ArrayList<>();
        String key = input_search.getText().toString();

        if(key.length()!=0){
            for(mAmphoe.mRespBody amphoe : orgAmphoes){
                if(amphoe.getAmpNameTH().contains(key)){
                    amphoes.add(amphoe);
                }
            }
        }else{
            amphoes = orgAmphoes;
        }
        input_search.setText("");
        listview.removeAllViewsInLayout();
        listview.setAdapter(new AmphoeAdapter(amphoes));

    }


    //Tambon Method
    private void displayTambonSearch(){
        setSelectedTambon(false);
        prepareNextStep(false);
        if(orgTambons!=null && orgTambons.size()>0) {
            tambons = orgTambons;
            Log.d("Step1","Use orgTambons old object");
            setTambonUI();
        }else{
            ProgressAction.show(StepOneActivity.this);
            Log.d("Step1","init orgTambons object");
            API_getTumbon(selectedAmphoe.getProvCode(),selectedAmphoe.getAmpCode());

        }
    }

    private void setSelectedTambon(boolean selected) {
        if (selected) {
          //  h.label_main_search.setVisibility(View.GONE);
            h.layout_search.setVisibility(View.GONE);

            h.input_tambon.setText(selectedTumbon.getTamNameTH());
            h.layout_tambon_active.setVisibility(View.GONE);
            h.layout_click_tambon.setVisibility(View.VISIBLE);
            h.bg_tambon.setVisibility(View.VISIBLE);
            prepareNextStep(true);
        } else {
            selectedTumbon = null;

           // h.label_main_search.setVisibility(View.VISIBLE);
            h.layout_search.setVisibility(View.VISIBLE);
            h.layout_search.startAnimation(AnimationUtils.loadAnimation(this, R.anim.move_in));

            h.layout_tambon_active.setVisibility(View.VISIBLE);
            h.layout_click_tambon.setVisibility(View.GONE);
            h.bg_tambon.setVisibility(View.GONE);
        }

    }

    private void setTambonUI() {

        listview = (ListView) findViewById(R.id.listview);
        input_search = (EditText)findViewById(R.id.input_search);
        input_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(android.widget.TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchTambon();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(input_search.getWindowToken(),
                            InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    return true;
                }
                return false;
            }

        });

        listview.setAdapter(new TambonAdapter(orgTambons));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedTumbon = tambons.get(position);
                setSelectedTambon(true);
            }
        });
    }

    private void searchTambon(){

        tambons = new ArrayList<>();
        String key = input_search.getText().toString();

        if(key.length()!=0){
            for(mTumbon.mRespBody tambon : orgTambons){
                    if(tambon.getTamNameTH().contains(key)){
                    tambons.add(tambon);
                }
            }
        }else{
            tambons = orgTambons;
        }
        input_search.setText("");
        listview.removeAllViewsInLayout();
        listview.setAdapter(new TambonAdapter(tambons));

    }



//=======================
private void displayCurrentLocation() {
    setSelectedLocation(false);
   // h.label_main_search.setVisibility(View.GONE);
    h.layout_search.setVisibility(View.GONE);
    /*
    if (currentLocation != null ) {
        //provinces = orgProvinces;
        Log.d("Step1", "Use currentLocation old object");
        selectedprovince = new mProvince.mRespBody();
        selectedprovince.setProvCode(currentLocation.getProvCode());
        selectedprovince.setProvNameTH(currentLocation.getProvNameTH());

        selectedAmphoe = new mAmphoe.mRespBody();
        selectedAmphoe.setProvCode(currentLocation.getProvCode());
        selectedAmphoe.setAmpCode(currentLocation.getAmpCode());
        selectedAmphoe.setAmpNameTH(currentLocation.getAmpNameTH());

        selectedTumbon = new mTumbon.mRespBody();
        selectedTumbon.setProvCode(currentLocation.getProvCode());
        selectedTumbon.setAmpCode(currentLocation.getAmpCode());
        selectedTumbon.setTamCode(currentLocation.getTamCode());
        selectedTumbon.setTamNameTH(currentLocation.getTamNameTH());

        setSelectedLocationUI();
    } else {
        ProgressAction.show(StepOneActivity.this);
        Log.d("Step1", "init orgProvinces object");
        String latitude = "0";
        String longitude = "0";
        LocationManager locationManager = (LocationManager) StepOneActivity.this.getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            GPSTracker gps = new GPSTracker(StepOneActivity.this);

            if (gps.canGetLocation()) {

                double lat = 0;
                double lon = 0;

                lat = gps.getLatitude();
                lon = gps.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(lon);
            }

            API_getCurrentLocation(latitude, longitude);

        }
    }

    */
    ProgressAction.show(StepOneActivity.this);
    Log.d("Step1", "init currentLocation object");
    String latitude = "0";
    String longitude = "0";
    LocationManager locationManager = (LocationManager) StepOneActivity.this.getSystemService(Context.LOCATION_SERVICE);

    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        GPSTracker gps = new GPSTracker(StepOneActivity.this);

        if (gps.canGetLocation()) {

            double lat = 0;
            double lon = 0;

            lat = gps.getLatitude();
            lon = gps.getLongitude();

            latitude = String.valueOf(lat);
            longitude = String.valueOf(lon);
        }

        API_getCurrentLocation(latitude, longitude);

    }

}
    private void setSelectedLocation(boolean selected) {

        if (selected) {

          //  h.layout_location_active.setVisibility(View.GONE);
           // h.layout_click_location.setVisibility(View.VISIBLE);
        //    h.bg_location.setVisibility(View.VISIBLE);
            h.img_label_location.startAnimation(AnimationUtils.loadAnimation(StepOneActivity.this, R.anim.clockwise));
            prepareNextStep(true);

        } else {


            provinces.clear();
            amphoes.clear();
            tambons.clear();

            h.layout_province_active.setVisibility(View.GONE);
            h.layout_click_province.setVisibility(View.VISIBLE);
            h.bg_province.setVisibility(View.VISIBLE);

            h.layout_amphoe_active.setVisibility(View.GONE);
            h.layout_click_amphoe.setVisibility(View.VISIBLE);
            h.bg_amphoe.setVisibility(View.VISIBLE);

            h.layout_tambon_active.setVisibility(View.GONE);
            h.layout_click_tambon.setVisibility(View.VISIBLE);
            h.bg_tambon.setVisibility(View.VISIBLE);

          //  h.layout_location_active.setVisibility(View.VISIBLE);
         //   h.layout_click_location.setVisibility(View.GONE);
         //   h.bg_location.setVisibility(View.GONE);
        }

    }

    private void setSelectedLocationUI() {
        h.input_province.setText(currentLocation.getProvNameTH());
        h.input_amphoe.setText(currentLocation.getAmpNameTH());
        h.input_tambon.setText(currentLocation.getTamNameTH());
        setSelectedLocation(true);
    }


    private void prepareNextStep (boolean nextStepEnable) {
        if (nextStepEnable) {
            if(!goNextStep) {
                h.step2.setImageResource(R.drawable.step2);
            }

            goNextStep = true;

            h.step2.postDelayed(new Runnable() {
                int i = 0;

                public void run() {
                    h.step2.setImageResource(
                            i++ % 2 == 0 ?
                                    R.drawable.step_next_edge :
                                    R.drawable.action_next_step);
                    if (i < 4) {
                        h.step2.postDelayed(this, 500);
                    }
                }
            }, 500);
        }else{
            if(goNextStep){
                h.step2.setImageResource(R.drawable.step2);
            }
            goNextStep = false;
        }
    }

}
