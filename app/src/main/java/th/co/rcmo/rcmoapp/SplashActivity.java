package th.co.rcmo.rcmoapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import th.co.rcmo.rcmoapp.API.RequestServices;
import th.co.rcmo.rcmoapp.API.ResponseAPI;
import th.co.rcmo.rcmoapp.Module.mUserPlotList;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;
import th.co.rcmo.rcmoapp.View.DialogChoice;

public class SplashActivity extends Activity {


    //Handler handler;
    //Runnable runnable;
    //Long delay_time;
    //Long time = 3000L;
    boolean IsConnected;
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        SharedPreferences sp = getSharedPreferences(ServiceInstance.PREF_NAME, Context.MODE_PRIVATE);
        final  String userId = sp.getString(ServiceInstance.sp_userId, "0");

        setContentView(R.layout.activity_splash);
        Log.d("Splash", "UserId : " + userId);
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (IsConnected) {
                    if (userId.equals("0")) {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        //Intent intent = new Intent(SplashActivity.this, ProductDetailActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        API_GetUserPlot(userId);
                    }
                }
            }
        }, SPLASH_DISPLAY_LENGTH);

       /*
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                SharedPreferences sp = getSharedPreferences(ServiceInstance.PREF_NAME, Context.MODE_PRIVATE);
                String userId = sp.getString(ServiceInstance.sp_userId, "0");

                Log.d("Splash","UserId : "+userId);

                if(userId.equals("0")){
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    API_GetUserPlot(userId);
                }


            }
        };
        */
    }

    /*
    public void onResume() {
        super.onResume();
        delay_time = time;
        handler.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();
    }

    public void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }
    */

    private void API_GetUserPlot(final String userId) {


        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mUserPlotList mPlotList = (mUserPlotList) obj;
                List<mUserPlotList.mRespBody> userPlotBodyLists = mPlotList.getRespBody();

                if (userPlotBodyLists.size() != 0) {
                    userPlotBodyLists.get(0).toString();

                    UserPlotListActivity.userPlotRespBodyList  = userPlotBodyLists;
                    startActivity(new Intent(SplashActivity.this, UserPlotListActivity.class)
                            .putExtra("userId", userId));
                    finish();
                }

            }

            @Override
            public void callbackError(int code, String errorMsg) {

                Intent intent = new Intent(SplashActivity.this, UserPlotListActivity.class);
                startActivity(intent);
                finish();
            }
        }).API_Request(false, RequestServices.ws_getPlotList +
                "?UserID=" + userId + "&PlotID="+
                "&ImeiCode=" + ServiceInstance.GetDeviceID(SplashActivity.this));

    }

    @Override
    protected void onResume() {
        super.onResume();
        IsConnected = CheckInternet();
        if(!IsConnected){

            new DialogChoice(SplashActivity.this, new DialogChoice.OnSelectChoiceListener() {
                @Override
                public void OnSelect(int choice) {
                    startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
                }
            }).ShowOneChoice("","กรุณาเชื่อมต่ออินเตอร์เนต");
        }
    }




    private boolean CheckInternet() {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
