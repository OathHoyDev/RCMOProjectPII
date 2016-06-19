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
import java.util.List;
import th.co.rcmo.rcmoapp.API.RequestServices;
import th.co.rcmo.rcmoapp.API.ResponseAPI;
import th.co.rcmo.rcmoapp.Model.UserModel;
import th.co.rcmo.rcmoapp.Module.mRegister;
import th.co.rcmo.rcmoapp.Util.BitMapHelper;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;
import th.co.rcmo.rcmoapp.View.DialogChoice;

public class RegisterActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
      ;
        findViewById(R.id.mainRegisterLayout).setBackground(  new BitmapDrawable(BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.bg_total, 300, 400)));
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

    private static class  Holder {
        static  EditText  inputName,inputSirName,inputUsername,inputEmail,inputPassword,inputConfirmPassword;
    }

    private void register() {




        Holder.inputName.setBackgroundResource(R.drawable.white_cut_conner_valid);
        Holder.inputSirName.setBackgroundResource(R.drawable.white_cut_conner_valid);
        Holder.inputUsername.setBackgroundResource(R.drawable.white_cut_conner_valid);
        Holder.inputEmail.setBackgroundResource(R.drawable.white_cut_conner_valid);
        Holder.inputPassword.setBackgroundResource(R.drawable.white_cut_conner_valid);
        Holder.inputConfirmPassword.setBackgroundResource(R.drawable.white_cut_conner_valid);



        if ( Holder.inputName.length() > 0
                &&  Holder.inputSirName.length()>0
                &&  Holder.inputUsername.length()>0
                &&  Holder.inputPassword.length()>0
                &&  Holder.inputConfirmPassword.length()>0) {

            if (!Holder.inputPassword.getText().toString().equals( Holder.inputConfirmPassword.getText().toString())) {

                new DialogChoice(RegisterActivity.this)
                        .ShowOneChoice("","ยืนยันรหัสผ่านไม่ถูกต้อง");
                Holder.inputConfirmPassword.setBackgroundResource(R.drawable.white_cut_conner_invalid);
            }else{


                API_Register (    Holder.inputName.getText().toString()
                        ,  Holder.inputSirName.getText().toString()
                        ,  Holder.inputUsername.getText().toString()
                        ,  Holder.inputEmail.getText().toString()
                        ,  Holder.inputPassword.getText().toString());

            }

        } else {

            String errorMsg = "กรุณากรอกข้อมูล  \n";
            if(!( Holder.inputName.length() > 0)) {
                errorMsg += "- ชื่อ \n";
                findViewById(R.id.inputName).setBackgroundResource(R.drawable.white_cut_conner_invalid);
            }

            if (!( Holder.inputSirName.length() > 0)){
                errorMsg += "- นามสกุล \n";
                findViewById(R.id.inputSirName).setBackgroundResource(R.drawable.white_cut_conner_invalid);
            }

            if (!( Holder.inputUsername.length() > 0)){
                errorMsg += "- ชื่อบัญชี \n";
                findViewById(R.id.inputUsername).setBackgroundResource(R.drawable.white_cut_conner_invalid);
            }

            if (!( Holder.inputPassword.length() > 0)){
                errorMsg += "- รหัสผ่าน \n";
                findViewById(R.id.inputPassword).setBackgroundResource(R.drawable.white_cut_conner_invalid);
            }

            if (!( Holder.inputConfirmPassword.length() > 0)){
                errorMsg += "- รหัสผ่านอีกครั้ง ";
                findViewById(R.id.inputConfirmPassword).setBackgroundResource(R.drawable.white_cut_conner_invalid);
            }
            new DialogChoice(RegisterActivity.this)
                    .ShowOneChoice("", errorMsg);
        }







    }


    private void API_Register(  final String inputName
                               ,final String inputSirName
                               ,final String inputUsername
                               ,final String inputEmail
                               ,final String inputPassword
                                ) {


        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mRegister register = (mRegister) obj;
                List<mRegister.mRespBody> mRegisterBodyLists = register.getRespBody();


                if (mRegisterBodyLists.size() != 0) {

                    SharedPreferences sp = getSharedPreferences(ServiceInstance.PREF_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp .edit();

                    //store into file variableE
                    editor.putString(ServiceInstance.sp_userId   , mRegisterBodyLists.get(0).getUserID());
                    editor.putString(ServiceInstance.sp_userName, inputUsername);
                    editor.commit();

//                    {"RespStatus":{"StatusID":2,"StatusMsg":"ไม่พบข้อมูล"},"RespBody":[]}
                   final UserModel user = new UserModel();
                    user.userEmail      = inputEmail;
                    user.userFirstName  = inputName;
                    user.userLastName   = inputSirName;
                    user.userLogin      = inputUsername;
                    user.userID         = String.valueOf(mRegisterBodyLists.get(0).getUserID());


                    final android.app.Dialog dialog =   new DialogChoice(RegisterActivity.this).Show("ลงทะเบียนสำเร็จ","");
                    final Handler handler  = new Handler();
                    final Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            EditUserActivity.userInfo = user;
                            startActivity(new Intent(RegisterActivity.this, UserPlotListActivity.class)
                            );
                            finish();
                        }
                    };
                    handler.postDelayed(runnable, 2000);


                }

            }

            @Override
            public void callbackError(int code, String errorMsg) {
              new DialogChoice(RegisterActivity.this).ShowOneChoice("",errorMsg);
                Holder.inputName.setBackgroundResource(R.drawable.white_cut_conner_invalid);
            }
        }).API_Request(false, RequestServices.ws_saveRegister +
                               "?SaveFlag="       + 1 +
                               "&UserID="         +
                               "&UserLogin="      + inputUsername +
                               "&UserPwd="        + ServiceInstance.md5(inputPassword) +
                               "&UserFirstName="  + inputName +
                               "&UserLastName="   + inputSirName +
                               "&UserEmail="      + inputEmail +
                               "&ImeiCode="       + ServiceInstance.GetDeviceID(RegisterActivity.this)
        );
//http://111.223.34.154/WS_OAE1603/services.asmx/saveRegister?SaveFlag=1&UserID=123&UserLogin=ball&UserPwd=7a10ea1b9b2872da9f375002c44ddfce&UserFirstName=taweesin&UserLastName=suksompoch&UserEmail=taweesin@gmail.com&ImeiCode=3b0cc4924383e736
        //http://111.223.34.154/WS_OAE1603/services.asmx/getPlotList?UserID=1&PlotID=&ImeiCode=
    }
}

