package th.co.rcmo.rcmoapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import com.neopixl.pixlui.components.edittext.EditText;
import java.util.ArrayList;
import java.util.List;
import th.co.rcmo.rcmoapp.API.RequestServices;
import th.co.rcmo.rcmoapp.API.ResponseAPI;
import th.co.rcmo.rcmoapp.Module.mRegister;
import th.co.rcmo.rcmoapp.Module.mUserPlotList;
import th.co.rcmo.rcmoapp.Util.BitMapHelper;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;
import th.co.rcmo.rcmoapp.View.DialogChoice;

public class RegisterActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ;
        findViewById(R.id.mainRegisterLayout).setBackground(new BitmapDrawable(BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.bg_total, 300, 400)));
        Holder.inputName = (EditText) findViewById(R.id.inputName);
        Holder.inputSirName = (EditText) findViewById(R.id.inputSirName);
        Holder.inputUsername = (EditText) findViewById(R.id.inputUsername);
        Holder.inputEmail = (EditText) findViewById(R.id.inputEmail);
        Holder.inputName = (EditText) findViewById(R.id.inputName);
        Holder.inputPassword = (EditText) findViewById(R.id.inputPassword);
        Holder.inputConfirmPassword = (EditText) findViewById(R.id.inputConfirmPassword);

        setUI();
        setAction();

    }


    private void setUI() {

    }

    private void setAction() {
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();

            }
        });
    }

    private static class Holder {
        static EditText inputName, inputSirName, inputUsername, inputEmail, inputPassword, inputConfirmPassword;
    }

    private void register() {


        Holder.inputName.setBackgroundResource(R.drawable.white_cut_conner_valid);
        Holder.inputSirName.setBackgroundResource(R.drawable.white_cut_conner_valid);
        Holder.inputUsername.setBackgroundResource(R.drawable.white_cut_conner_valid);
        Holder.inputEmail.setBackgroundResource(R.drawable.white_cut_conner_valid);
        Holder.inputPassword.setBackgroundResource(R.drawable.white_cut_conner_valid);
        Holder.inputConfirmPassword.setBackgroundResource(R.drawable.white_cut_conner_valid);


        if (Holder.inputName.length() > 0
                && Holder.inputSirName.length() > 0
                && Holder.inputUsername.length() > 0
                && Holder.inputPassword.length() > 0
                && Holder.inputConfirmPassword.length() > 0) {

            if (!(Holder.inputPassword.getText().toString()).equals(Holder.inputConfirmPassword.getText().toString())) {
                Holder.inputConfirmPassword.setBackgroundResource(R.drawable.white_cut_conner_invalid);
                Holder.inputConfirmPassword.requestFocus();
                new DialogChoice(RegisterActivity.this)
                        .ShowOneChoice("ยืนยันรหัสผ่านไม่ถูกต้อง", "");

            } else {


                API_Register(Holder.inputName.getText().toString()
                        , Holder.inputSirName.getText().toString()
                        , Holder.inputUsername.getText().toString()
                        , Holder.inputEmail.getText().toString()
                        , Holder.inputPassword.getText().toString());

            }

        } else {
            boolean isFocus = false;
            String errorMsg = "";
            if (!(Holder.inputName.length() > 0)) {
                errorMsg += "- ชื่อ \n";
                Holder.inputName.setBackgroundResource(R.drawable.white_cut_conner_invalid);
                if(!isFocus) {
                    Holder.inputName.requestFocus();
                    isFocus =true;
                }
            }

            if (!(Holder.inputSirName.length() > 0)) {
                errorMsg += "- นามสกุล \n";
                Holder.inputSirName.setBackgroundResource(R.drawable.white_cut_conner_invalid);
                if(!isFocus) {
                    Holder.inputSirName.requestFocus();
                    isFocus =true;
                }
            }

            if (!(Holder.inputUsername.length() > 0)) {
                errorMsg += "- ชื่อบัญชี \n";
                Holder.inputUsername.setBackgroundResource(R.drawable.white_cut_conner_invalid);
                if(!isFocus) {
                    Holder.inputUsername.requestFocus();
                    isFocus =true;
                }
            }

            if (!(Holder.inputPassword.length() > 0)) {
                errorMsg += "- รหัสผ่าน \n";
                Holder.inputPassword.setBackgroundResource(R.drawable.white_cut_conner_invalid);
                if(!isFocus) {
                    Holder.inputPassword.requestFocus();
                    isFocus =true;
                }
            }

            if (!(Holder.inputConfirmPassword.length() > 0)) {
                errorMsg += "- รหัสผ่านอีกครั้ง ";
                Holder.inputConfirmPassword.setBackgroundResource(R.drawable.white_cut_conner_invalid);
                if(!isFocus) {
                    Holder.inputConfirmPassword.requestFocus();
                    isFocus =true;
                }
            }
            new DialogChoice(RegisterActivity.this)
                    .ShowOneChoice("กรุณากรอกข้อมูล", errorMsg);
        }


    }


    private void API_Register(final String inputName
            , final String inputSirName
            , final String inputUsername
            , final String inputEmail
            , final String inputPassword
    ) {


        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mRegister register = (mRegister) obj;
                List<mRegister.mRespBody> mRegisterBodyLists = register.getRespBody();


                if (mRegisterBodyLists.size() != 0) {

                    final String userID = mRegisterBodyLists.get(0).getUserID().toString();
                    SharedPreferences sp = getSharedPreferences(ServiceInstance.PREF_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();


                    editor.putString(ServiceInstance.sp_userId, userID);
                    editor.putString(ServiceInstance.sp_userName, inputUsername);
                    editor.commit();


                    final android.app.Dialog dialog = new DialogChoice(RegisterActivity.this).Show("ลงทะเบียนสำเร็จ", "");
                    Handler handler = new Handler();
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            API_GetUserPlot(userID);
                        }
                    };
                    handler.postDelayed(runnable, 2000);


                }

            }

            @Override
            public void callbackError(int code, String errorMsg) {
                new DialogChoice(RegisterActivity.this).ShowOneChoice(errorMsg, "");
                Holder.inputName.setBackgroundResource(R.drawable.white_cut_conner_invalid);
            }
        }).API_Request(false, RequestServices.ws_saveRegister +
                "?SaveFlag=" + 1 +
                "&UserID=" +
                "&UserLogin=" + inputUsername +
                "&UserPwd=" + ServiceInstance.md5(inputPassword) +
                "&UserFirstName=" + inputName +
                "&UserLastName=" + inputSirName +
                "&UserEmail=" + inputEmail +
                "&ImeiCode=" + ServiceInstance.GetDeviceID(RegisterActivity.this)
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
                    startActivity(new Intent(RegisterActivity.this, UserPlotListActivity.class)
                            .putExtra("userId", userId));
                    finish();

                }
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                List<mUserPlotList.mRespBody> userPlotList = new ArrayList<mUserPlotList.mRespBody>();
                UserPlotListActivity.userPlotRespBodyList = userPlotList;


                startActivity(new Intent(RegisterActivity.this, UserPlotListActivity.class)
                        .putExtra("userId", userId));
                finish();

            }
        }).API_Request(false, RequestServices.ws_getPlotList +
                "?UserID=" + userId + "&PlotID=" +
                "&ImeiCode=" + ServiceInstance.GetDeviceID(RegisterActivity.this));

    }
}

