package th.co.rcmo.rcmoapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.neopixl.pixlui.components.relativelayout.RelativeLayout;
import com.neopixl.pixlui.components.textview.TextView;

import java.util.List;
import th.co.rcmo.rcmoapp.API.RequestServices;
import th.co.rcmo.rcmoapp.API.ResponseAPI;
import th.co.rcmo.rcmoapp.Module.mPlantGroup;
import th.co.rcmo.rcmoapp.Module.mProduct;
import th.co.rcmo.rcmoapp.Util.BitMapHelper;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;
import th.co.rcmo.rcmoapp.Util.Util;

public class StepOneActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

if(ServiceInstance.isTablet(StepOneActivity.this)){
    Log.d("TEST","-->TabLet");
    setContentView(R.layout.activity_step_one_tablet);
}else{
    setContentView(R.layout.activity_step_one);
}


        setUI();
        setAction();

    }

    private  void setUI(){
        findViewById(R.id.mainStepOneLayout).setBackground(new BitmapDrawable(BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.bg_total, 300, 400)));
        ((ImageView) findViewById(R.id.plantImg)).setImageBitmap(BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.p1_1, R.dimen.iccircle_img_width, R.dimen.iccircle_bg_height));
        ((ImageView)findViewById(R.id.animalImg)).setImageBitmap(BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.m10, R.dimen.iccircle_img_width, R.dimen.iccircle_bg_height));
        ((ImageView) findViewById(R.id.fishImg)).setImageBitmap (BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.f4, R.dimen.iccircle_img_width, R.dimen.iccircle_bg_height));

        final LinearLayout plant =  (LinearLayout)findViewById(R.id.ic_bg_g1);
        final android.widget.RelativeLayout animal =  ( android.widget.RelativeLayout)findViewById(R.id.grAnimalBg);
        final LinearLayout fish =  (LinearLayout)findViewById(R.id.ic_bg_g3);

        final ImageView lineCircle = (ImageView)findViewById(R.id.lineCircle);

        final TextView plantLabel  = (TextView)findViewById(R.id.ic_label_g1);
        final TextView animalLabel  = (TextView)findViewById(R.id.ic_label_g2);
        final TextView fishLabel   = (TextView)findViewById(R.id.ic_label_47);


        final Animation fade
                = AnimationUtils.loadAnimation(this, R.anim.fade);
        final  Animation mainMoveIn
                = AnimationUtils.loadAnimation(this, R.anim.move_in);
        final  Animation moveInCircle
                = AnimationUtils.loadAnimation(this, R.anim.move_in);
        final  Animation moveInAnimal
                = AnimationUtils.loadAnimation(this, R.anim.move_in2);
        final  Animation moveInPlant
                = AnimationUtils.loadAnimation(this, R.anim.move_in);

/*
        plant.setVisibility(View.INVISIBLE);
        meat.setVisibility(View.INVISIBLE);
        fish.setVisibility(View.INVISIBLE);
        plantLabel.setVisibility(View.INVISIBLE);
        meatLabel.setVisibility(View.INVISIBLE);
        fishLabel.setVisibility(View.INVISIBLE);
        */
        lineCircle.setVisibility(View.INVISIBLE);
        Animation.AnimationListener animation1Listener = new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                lineCircle.setVisibility(View.VISIBLE);
                lineCircle.startAnimation(moveInCircle);

               /*
                plant.setVisibility(View.VISIBLE);
                meat.setVisibility(View.VISIBLE);
                fish.setVisibility(View.VISIBLE);

                plantLabel.setVisibility(View.VISIBLE);
                meatLabel.setVisibility(View.VISIBLE);
                fishLabel.setVisibility(View.VISIBLE);

                plant.startAnimation(moveIn2);
                meat.startAnimation(moveIn3);
                fish.startAnimation(moveIn2);
                plantLabel.startAnimation(fade);
                meatLabel.startAnimation(fade);
                fishLabel.startAnimation(fade);
                */

            }
        };


        plant.startAnimation(moveInPlant);
        animal.startAnimation(moveInAnimal);

        mainMoveIn.setAnimationListener(animation1Listener);
        fish.startAnimation(mainMoveIn);

        plantLabel.startAnimation(fade);
        animalLabel.startAnimation(fade);
        fishLabel.startAnimation(fade);
        /*
        moveIn.setAnimationListener(animation1Listener);
        lineCircle.startAnimation(moveIn);
        */







       // ((ImageView) findViewById(R.id.step1_ac)).setImageBitmap(BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.step1_ac, R.dimen.img_3step_width, R.dimen.img_3step_height));
        //((ImageView)findViewById(R.id.step2)).setImageBitmap(BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.step2, R.dimen.img_3step_width, R.dimen.img_3step_height));
        //((ImageView) findViewById(R.id.step3)).setImageBitmap (BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.step3, R.dimen.img_3step_width, R.dimen.img_3step_height));

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
                final ImageView img =  ((ImageView) findViewById(R.id.bringImageView));
                bringImg(img);

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


    public void bringImg(final ImageView img) {

      //  img.setImageBitmap (BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.bring1, 90, 90));

        final int ms = 200; // Delay in seconds

        Util.delay(ms, new Util.DelayCallback() {
            @Override
            public void afterDelay() {
                img.setImageBitmap (BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.bring1, 90, 90));
                Util.delay(ms, new Util.DelayCallback() {
                    @Override
                    public void afterDelay() {
                        img.setImageBitmap (BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.bring2, 100, 100));

                        Util.delay(ms, new Util.DelayCallback() {
                            @Override
                            public void afterDelay() {
                                img.setImageBitmap (BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.bring3, 120, 120));

                                Util.delay(ms, new Util.DelayCallback() {
                                    @Override
                                    public void afterDelay() {

                                        img.setImageBitmap (BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.bring2, 100, 100));
                                        Util.delay(ms, new Util.DelayCallback() {
                                            @Override
                                            public void afterDelay() {

                                                img.setImageBitmap (BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.bring1, 90, 90));
                                                API_GetProduct(2,0);
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }
}
