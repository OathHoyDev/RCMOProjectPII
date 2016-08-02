package th.go.oae.rcmo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.neopixl.pixlui.components.edittext.EditText;

import java.util.ArrayList;
import java.util.List;
import th.go.oae.rcmo.API.RequestServices;
import th.go.oae.rcmo.API.ResponseAPI;
import th.go.oae.rcmo.Module.mLogin;
import th.go.oae.rcmo.Module.mUserPlotList;
import th.go.oae.rcmo.Util.BitMapHelper;
import th.go.oae.rcmo.Util.ServiceInstance;
import th.go.oae.rcmo.Util.Util;
import th.go.oae.rcmo.View.DialogChoice;

public class LoginActivity extends Activity {
    EditText inputUsername, inputPassword;
    ProgressBar progress;
    String callBy =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //findViewById(R.id.mainLoginLayout).setBackgroundResource(R.drawable.bg_blue);
       //findViewById(R.id.mainLoginLayout).setBackground(new BitmapDrawable(BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.bg_total, 300, 400)));
        inputUsername = (EditText) findViewById(R.id.inputUsername);
        inputPassword = (EditText) findViewById(R.id.inputPassword);


        inputUsername.requestFocus();
         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            callBy = bundle.getString("callBy",null);
        }

        setUI();
        setAction();
        MediaPlayer mp = MediaPlayer.create(LoginActivity.this, R.raw.login);
        mp.start();

    }


    private void setUI() {
        SharedPreferences sp = getSharedPreferences(ServiceInstance.PREF_NAME, Context.MODE_PRIVATE);
        inputUsername.setText(sp.getString(ServiceInstance.sp_userName,""));

        final ImageView farmer_login = (ImageView) findViewById(R.id.farmer_login);

        final AnimationDrawable farmer_ani = (AnimationDrawable) farmer_login.getBackground();
        //farmer_ani.start();
        if(!farmer_ani.isRunning()){
            farmer_ani.start();
        }
/*
        final ImageView imageSwitcher = (ImageView)findViewById(R.id.logoLogin);
        imageSwitcher.postDelayed(new Runnable() {
            int i = 0;
            public void run() {
                imageSwitcher.setImageResource(
                        i++ % 2 == 0 ?
                                R.drawable.logo_cloeseyes :
                                R.drawable.logo_openeyes);
                imageSwitcher.postDelayed(this, 1000);
            }
        }, 1000);

*/

    }

    private void setAction() {

        findViewById(R.id.btnHowto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                // finish();
                new DialogChoice(LoginActivity.this)
                        .ShowTutorial("g1");
            }
        });


        findViewById(R.id.imgGotoOtherApp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                // finish();
                new DialogChoice(LoginActivity.this)
                        .ShowAppLink();
            }
        });

        findViewById(R.id.textLinkRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
               // finish();
            }
        });

        findViewById(R.id.textLinkForgetPass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           //     startActivity(new Intent(LoginActivity.this, UserPlotListActivity.class));
                startActivity(new Intent(LoginActivity.this, WebActivity.class)
                        .putExtra("link", ServiceInstance.forgotPassURL));

            }
        });

        findViewById(R.id.btn_cal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ((ImageView)findViewById(R.id.btn_cal)).startAnimation(AnimationUtils.loadAnimation(LoginActivity.this, R.anim.fade_out));
                Util.delay(1000, new Util.DelayCallback() {
                    @Override
                    public void afterDelay() {
                        if(callBy == null) {
                            startActivity(new Intent(LoginActivity.this, StepOneActivity.class));
                        }else{
                            finish();
                        }
                    }
                });


            }
        });




        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.inputUsername).setBackgroundResource(R.drawable.white_cut_top_conner_valid);
                findViewById(R.id.inputPassword).setBackgroundResource(R.drawable.white_cut_conner_valid);
                boolean isFocus = false;

                if (inputUsername.length() > 0 && inputPassword.length()>0) {

                        if (inputPassword.length() != 0) {
                           Login();
                        }

                } else {
                    String errorMsg = "";
                    if(!(inputUsername.length() > 0)) {
                        errorMsg = "- บัญชีผู้ใช้ \n";
                        EditText inputUsername =  (EditText)findViewById(R.id.inputUsername);
                        inputUsername.setBackgroundResource(R.drawable.white_cut_top_conner_invalid);
                        if(!isFocus) {
                            inputUsername.requestFocus();
                            isFocus = true;
                        }
                    }

                    if (!(inputPassword.length() > 0)){
                        errorMsg += "- รหัสผ่าน ";
                        EditText  inputPassword =  (EditText) findViewById(R.id.inputPassword);
                        inputPassword.setBackgroundResource(R.drawable.white_cut_conner_invalid);
                        if(!isFocus) {
                            inputPassword.requestFocus();
                            isFocus = true;
                        }

                    }
                    new DialogChoice(LoginActivity.this)
                            .ShowOneChoice("กรุณากรอกข้อมูล", errorMsg);                }
            }
        });

    }


    private void Login() {

            API_Login(inputUsername.getText().toString(), inputPassword.getText().toString());
    }


    private void API_Login(final String username, String password) {


        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mLogin login = (mLogin) obj;
                List<mLogin.mRespBody> loginBodyLists = login.getRespBody();

                if (loginBodyLists.size() != 0) {
                    SharedPreferences preferences = getSharedPreferences(ServiceInstance.PREF_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    //store into file variableE
                    editor.putString(ServiceInstance.sp_userName, username);
                    editor.putString(ServiceInstance.sp_userId, loginBodyLists.get(0).getUserID());
                    editor.commit();

                    if(callBy == null) {
                        API_GetUserPlot(loginBodyLists.get(0).getUserID());
                    }else{
                        finish();
                    }
                }

            }

            @Override
            public void callbackError(int code, String errorMsg) {
                new DialogChoice(LoginActivity.this).ShowOneChoice("ไม่พบบัญชีผู้ใช้งาน หรือ รหัสผ่านไม่ถูกต้อง","");

            }
        }).API_Request(false, RequestServices.ws_chkLogin +
                "?UserLogin=" + username + "&UserPwd=" + ServiceInstance.md5(password) +
                "&ImeiCode=" + ServiceInstance.GetDeviceID(LoginActivity.this));

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
                     /*
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();

                    /*store into file variableE
                    editor.putString("sp_user_name", username).apply();
                    editor.putString("sp_user_id", loginBodyLists.get(0).getUserID()).apply();
                    editor.apply();
                  */
                    final android.app.Dialog dialog =   new DialogChoice(LoginActivity.this).Show("เข้าสู่ระบบสำเร็จ","");
                    final Handler handler  = new Handler();
                    final Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            UserPlotListActivity.userPlotRespBodyList  = userPlotBodyLists;
                            startActivity(new Intent(LoginActivity.this, UserPlotListActivity.class)
                                    .putExtra("userId", userId));
                            finish();
                        }
                    };
                    handler.postDelayed(runnable, 2000);


                }

            }

            @Override
            public void callbackError(int code, String errorMsg) {
                List<mUserPlotList.mRespBody>  userPlotList = new ArrayList<mUserPlotList.mRespBody>();
                UserPlotListActivity.userPlotRespBodyList  = userPlotList;


                startActivity(new Intent(LoginActivity.this, UserPlotListActivity.class)
                        .putExtra("userId", userId));
                finish();

            }
        }).API_Request(false, RequestServices.ws_getPlotList +
                "?UserID=" + userId + "&PlotID="+
                "&ImeiCode=" + ServiceInstance.GetDeviceID(LoginActivity.this));

    }

}
