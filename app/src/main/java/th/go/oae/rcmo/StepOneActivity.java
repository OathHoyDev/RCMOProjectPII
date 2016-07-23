package th.go.oae.rcmo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import th.go.oae.rcmo.Module.mPlantGroup;
import th.go.oae.rcmo.Module.mProduct;
import th.go.oae.rcmo.Module.mProvince;
import th.go.oae.rcmo.Module.mTumbon;
import th.go.oae.rcmo.Util.ServiceInstance;
import th.go.oae.rcmo.View.DialogChoice;
import th.go.oae.rcmo.View.ProgressAction;


public class StepOneActivity extends Activity {
    mProvince.mRespBody selectedprovince = null;
    mAmphoe.mRespBody selectedAmphoe = null;
    mTumbon.mRespBody selectedTumbon = null;
    List<mProvince.mRespBody> provinces = new ArrayList<>();
    List<mProvince.mRespBody> orgProvinces = new ArrayList<>();
    EditText input_search;
    ListView listview;
    ViewHolder h = new ViewHolder();



    static class ViewHolder {
        private  TextView input_province ,input_amphoe,input_tambon,input_location;
        private  ImageView bg_province,bg_amphoe,bg_tambon,bg_location;
        private LinearLayout layout_click_province,layout_click_amphoe,layout_click_tambon,layout_click_location;
        private RelativeLayout layout_province_active,layout_amphoe_active,layout_tambon_active,layout_location_active;
        //private  LinearLayout.LayoutParams params;
        //private  String userId;


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

        h.input_province = (TextView) findViewById(R.id.input_province);
        h.input_amphoe = (TextView) findViewById(R.id.input_amphoe);
        h.input_tambon = (TextView) findViewById(R.id.input_tambon);
        h.input_location = (TextView) findViewById(R.id.input_location);

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

        //tutorial
        findViewById(R.id.layout_click_province).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                findViewById(R.id.label_main_search).setVisibility(View.VISIBLE);
                findViewById(R.id.layout_search).setVisibility(View.VISIBLE);
                displayProvinceSearch();

            }
        });
    }


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
                    StepTwoActivity.plantGroupLists = productLists;
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
                    StepTwoActivity.productInfoLists = productLists;
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
                mProvince.mRespBody defaultProvince = new mProvince.mRespBody();
                defaultProvince.setProvCode("0");
                defaultProvince.setProvNameTH("ไม่ระบุ");


                if (provinceBodyLists.size() != 0) {
                    provinceBodyLists.add(0, defaultProvince);
                    orgProvinces = provinceBodyLists;
                    provinces = orgProvinces;
                    setProvinceUI();


                    // popUpProvinceListDialog(provinceBodyLists);

                    /*
                    new dialog_province(StepOneActivity.this,"จังหวัด",provinceBodyLists, new dialog_province.OnSelectChoice() {
                        @Override
                        public void selectChoice(mProvince.mRespBody choice) {
                            selectedprovince = choice;
                            android.widget.TextView provinceTextView = (android.widget.TextView) findViewById(R.id.inputprovice);
                            android.widget.TextView amphoeTextView = (android.widget.TextView) findViewById(R.id.inputAmphoe);
                            android.widget.TextView tumbonTextView = (android.widget.TextView) findViewById(R.id.inputTumbon);
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
                    */

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




    class listAdapter extends BaseAdapter {

        List<mProvince.mRespBody> provinces;

        listAdapter(List<mProvince.mRespBody> province) {
            listAdapter.this.provinces = province;
        }

        @Override
        public int getCount() {
            return provinces.size();
        }

        @Override
        public mProvince.mRespBody getItem(int position) {
            return provinces.get(position);
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



    // Province Method



    private void displayProvinceSearch(){
        setSelectedProvince(false);
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
        if (selected) {
            h.input_province.setText(selectedprovince.getProvNameTH());
            h.layout_province_active.setVisibility(View.GONE);
            h.layout_click_province.setVisibility(View.VISIBLE);
            h.bg_province.setVisibility(View.VISIBLE);
        } else {
            h.layout_province_active.setVisibility(View.VISIBLE);
            h.layout_click_province.setVisibility(View.GONE);
            h.bg_province.setVisibility(View.GONE);
        }
    }

    private void setProvinceUI() {

        listview = (ListView) findViewById(R.id.listview);
        input_search = (EditText)findViewById(R.id.input_search);
        input_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(android.widget.TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    SearchProvince();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(input_search.getWindowToken(),
                            InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    return true;
                }
                return false;
            }

        });

        listview.setAdapter(new listAdapter(orgProvinces));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedprovince = provinces.get(position);
                setSelectedProvince(true);
            }
        });
    }

    private void SearchProvince(){

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
        listview.setAdapter(new listAdapter(provinces));

    }


    //============
    private void setSelectedAmphoe(boolean selected) {
        if (selected) {
            h.input_amphoe.setText(selectedAmphoe.getAmpNameTH());
            h.layout_amphoe_active.setVisibility(View.GONE);
            h.layout_click_amphoe.setVisibility(View.VISIBLE);
            h.bg_amphoe.setVisibility(View.VISIBLE);
        } else {
            h.layout_amphoe_active.setVisibility(View.VISIBLE);
            h.layout_click_amphoe.setVisibility(View.GONE);
            h.bg_amphoe.setVisibility(View.GONE);
        }
        //Util.showDialogAndDismiss(StepOneActivity.this,selectedAmphoe.getProvNameTH());
    }

    private void setSelectedTambon(boolean selected) {
        if (selected) {
            h.input_tambon.setText(selectedTumbon.getTamNameTH());
            h.layout_tambon_active.setVisibility(View.GONE);
            h.layout_click_tambon.setVisibility(View.VISIBLE);
            h.bg_tambon.setVisibility(View.VISIBLE);
        } else {
            h.layout_tambon_active.setVisibility(View.VISIBLE);
            h.layout_click_tambon.setVisibility(View.GONE);
            h.bg_tambon.setVisibility(View.GONE);
        }
        //Util.showDialogAndDismiss(StepOneActivity.this,selectedAmphoe.getProvNameTH());
    }

    private void setSelectedLocation(boolean selected) {
        // h.input_location.setText(selectedTumbon.getTamNameTH());
        if (selected) {
            h.layout_location_active.setVisibility(View.GONE);
            h.layout_click_location.setVisibility(View.VISIBLE);
            h.bg_location.setVisibility(View.VISIBLE);
        } else {
            h.layout_location_active.setVisibility(View.VISIBLE);
            h.layout_click_location.setVisibility(View.GONE);
            h.bg_location.setVisibility(View.GONE);
        }
        //Util.showDialogAndDismiss(StepOneActivity.this,selectedAmphoe.getProvNameTH());
    }


}
