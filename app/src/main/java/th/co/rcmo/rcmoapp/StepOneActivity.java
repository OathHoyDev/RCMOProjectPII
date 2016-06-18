package th.co.rcmo.rcmoapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.List;
import th.co.rcmo.rcmoapp.API.RequestServices;
import th.co.rcmo.rcmoapp.API.ResponseAPI;
import th.co.rcmo.rcmoapp.Module.mPlantGroup;
import th.co.rcmo.rcmoapp.Module.mProduct;
import th.co.rcmo.rcmoapp.Util.BitMapHelper;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;

public class StepOneActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_one);

        setUI();
        setAction();

    }

    private  void setUI(){
        findViewById(R.id.mainStepOneLayout).setBackground(new BitmapDrawable(BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.bg_total, 400, 400)));
        ((ImageView) findViewById(R.id.plantImg)).setImageBitmap(BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.p1_1, R.dimen.iccircle_img_width, R.dimen.iccircle_bg_height));
        ((ImageView)findViewById(R.id.animalImg)).setImageBitmap(BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.m10, R.dimen.iccircle_img_width, R.dimen.iccircle_bg_height));
        ((ImageView) findViewById(R.id.fishImg)).setImageBitmap (BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.f4, R.dimen.iccircle_img_width, R.dimen.iccircle_bg_height));

        ((ImageView) findViewById(R.id.step1_ac)).setImageBitmap(BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.step1_ac, R.dimen.img_3step_width, R.dimen.img_3step_height));
        ((ImageView)findViewById(R.id.step2)).setImageBitmap(BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.step2, R.dimen.img_3step_width, R.dimen.img_3step_height));
        ((ImageView) findViewById(R.id.step3)).setImageBitmap (BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.step3, R.dimen.img_3step_width, R.dimen.img_3step_height));

    }

    private void setAction() {
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.ic_bg_g1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API_GetPlantGroup(1);
            }
        });

        findViewById(R.id.ic_bg_g2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API_GetProduct(2,0);
            }
        });

        findViewById(R.id.ic_bg_g3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API_GetProduct(3,0);
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
                            .putExtra(ServiceInstance.INTENT_GROUP_ID,1));

                }
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error",errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getPlantGroup +
                "?PlantGrpID=");

    }


    private void API_GetProduct(final int prdGrpID,int plantGrpID) {
         /*
        1.PrdGrpID (ไม่บังคับใส่)
        2.PlantGrpID (บังคับกรณี PrdGrpID = 1)
        3.PrdID (ไม่บังคับ)
        */
        String prdGrpIDStr = "";
        String plantGrpIDStr = "";

        if(prdGrpID == 1){
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
                            .putExtra(ServiceInstance.INTENT_GROUP_ID,prdGrpID));
                }
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Erroo",errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getProduct +
                "?PrdGrpID=" + prdGrpIDStr + "&PlantGrpID="+plantGrpIDStr+
                "&PrdID=" );

    }
}
