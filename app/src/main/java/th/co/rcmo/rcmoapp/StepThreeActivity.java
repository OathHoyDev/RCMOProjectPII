package th.co.rcmo.rcmoapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import th.co.rcmo.rcmoapp.API.RequestServices;
import th.co.rcmo.rcmoapp.API.ResponseAPI;
import th.co.rcmo.rcmoapp.Adapter.DialogAmphoeAdapter;
import th.co.rcmo.rcmoapp.Adapter.DialogProvinceAdapter;
import th.co.rcmo.rcmoapp.Adapter.DialogTumbonAdapter;
import th.co.rcmo.rcmoapp.Model.ProductModel;
import th.co.rcmo.rcmoapp.Module.mAmphoe;
import th.co.rcmo.rcmoapp.Module.mDeletePlot;
import th.co.rcmo.rcmoapp.Module.mGetRegister;
import th.co.rcmo.rcmoapp.Module.mProduct;
import th.co.rcmo.rcmoapp.Module.mProvince;
import th.co.rcmo.rcmoapp.Module.mRiceProduct;
import th.co.rcmo.rcmoapp.Module.mTumbon;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;

public class StepThreeActivity extends Activity {
     mProvince.mRespBody  selectedprovince = null;
     mAmphoe.mRespBody    selectedAmphoe    = null;
     mTumbon.mRespBody    selectedTumbon   = null;
    public static ProductModel productionInfo = null;



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
        }else{
            setContentView(R.layout.activity_fish_step_three);
        }

    }

    class Holder{

        ImageView prodImg;
        LinearLayout prodBg;
        com.neopixl.pixlui.components.textview.TextView labelProductName,labelProductHierarchy;


    }

    private void setAction() {



        findViewById(R.id.inputprovice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                API_getProvince();

            }
        });
        findViewById(R.id.inputAmphoe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedprovince!=null){
                    API_getAmphoe(selectedprovince.getProvCode());
                }

            }
        });

        findViewById(R.id.inputTumbon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedprovince!=null && selectedAmphoe!=null){
                    API_getTumbon(selectedprovince.getProvCode(),selectedAmphoe.getAmpCode());
                }

            }
        });
    }





    private void popUpProvinceListDialog(final List<mProvince.mRespBody> provinceRespList){
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

                    TextView provinceTextView = (TextView)findViewById(R.id.inputprovice);
                    TextView amphoeTextView = (TextView)findViewById(R.id.inputAmphoe);
                    TextView tumbonTextView = (TextView)findViewById(R.id.inputTumbon);

                    selectedprovince = provinceRespList.get(position);
                    selectedAmphoe   = null;
                    selectedTumbon   = null;

                    provinceTextView.setText(selectedprovince.getProvNameTH());
                    amphoeTextView.setText("");
                    tumbonTextView.setText("");

                    dialog.dismiss();

            }
        });

        dialog.setContentView(view);
        dialog.show();
    }

    private void popUpAmphoeListDialog(final List<mAmphoe.mRespBody> amphoeRespList){
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

                TextView amphoeTextView = (TextView)findViewById(R.id.inputAmphoe);
                TextView tumbonTextView = (TextView)findViewById(R.id.inputTumbon);

                selectedAmphoe   = amphoeRespList.get(position);
                selectedTumbon   = null;

                amphoeTextView.setText(selectedAmphoe.getAmpNameTH());
                tumbonTextView.setText("");

                dialog.dismiss();

            }
        });

        dialog.setContentView(view);
        dialog.show();
    }

    private void popUpTumbonListDialog(final List<mTumbon.mRespBody> tunbonRespList){
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

                TextView amphoeTextView = (TextView)findViewById(R.id.inputAmphoe);
                TextView tumbonTextView = (TextView)findViewById(R.id.inputTumbon);

                selectedTumbon   = tunbonRespList.get(position);

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
                Log.d("Error",errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getProvince+
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
                Log.d("Error",errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getAmphoe+
                "?AmpCode="+
                "&ProvCode="+provinceId
        );

    }

    private void API_getTumbon(String provinceId,String amphoeId) {
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
                Log.d("Error",errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getTambon+
                "?TamCode="+
                "&AmpCode="+amphoeId+
                "&ProvCode="+provinceId
        );

    }
}
