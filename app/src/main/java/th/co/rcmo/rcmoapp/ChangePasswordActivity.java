package th.co.rcmo.rcmoapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.neopixl.pixlui.components.edittext.EditText;
import com.neopixl.pixlui.components.textview.TextView;

import java.util.List;
import th.co.rcmo.rcmoapp.API.RequestServices;
import th.co.rcmo.rcmoapp.API.ResponseAPI;
import th.co.rcmo.rcmoapp.Module.mLogin;
import th.co.rcmo.rcmoapp.Module.mRegister;
import th.co.rcmo.rcmoapp.Util.BitMapHelper;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;
import th.co.rcmo.rcmoapp.View.DialogChoice;

public class ChangePasswordActivity extends Activity {
    String name,sirname,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        findViewById(R.id.mainChangePassLayout).setBackground(new BitmapDrawable(BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.bg_total, 300, 400)));


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            name = bundle.getString("fname");
            sirname = bundle.getString("lname");
            email = bundle.getString("email");
        }

        Holder.inputPassword        =  (EditText)findViewById(R.id.inputPassword);
        Holder.inputConfirmPassword =  (EditText)findViewById(R.id.inputConfirmPassword);
        Holder.inputOldPassword     =  (EditText)findViewById(R.id.inputOldPassword);

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

        findViewById(R.id.btnChangePass).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Holder.inputOldPassword.setBackgroundResource(R.drawable.white_cut_conner_valid);
                Holder.inputPassword.setBackgroundResource(R.drawable.white_cut_conner_valid);
                Holder.inputConfirmPassword.setBackgroundResource(R.drawable.white_cut_conner_valid);


                if ( Holder.inputOldPassword.length() > 0
                        &&  Holder.inputPassword.length()>0
                        &&  Holder.inputConfirmPassword.length()>0 ){

                    if(!(Holder.inputConfirmPassword.getText().toString()).equals(Holder.inputPassword.getText().toString())){
                        Holder.inputConfirmPassword.setBackgroundResource(R.drawable.white_cut_conner_invalid);
                        new DialogChoice(ChangePasswordActivity.this).ShowOneChoice("ยืนยันรหัสผ่านใหม่ไม่ถูกต้อง","");
                    }else{
                        SharedPreferences sp = getSharedPreferences(ServiceInstance.PREF_NAME, Context.MODE_PRIVATE);
                        String userId = sp.getString(ServiceInstance.sp_userId, "0");
                        String username = sp.getString(ServiceInstance.sp_userName,"");
                        API_CheckOldPassword(userId,username
                                ,Holder.inputOldPassword.getText().toString()
                                ,Holder.inputPassword.getText().toString()
                                ,name,sirname,email);
                    }

                }else{

                    String errorMsg = "";
                    if(!( Holder.inputOldPassword.length() > 0)) {
                        errorMsg += "- รหัสผ่านเก่า \n";
                        Holder.inputOldPassword.setBackgroundResource(R.drawable.white_cut_conner_invalid);
                    }

                    if (!( Holder.inputPassword.length() > 0)){
                        errorMsg += "- รหัสผ่านใหม่ \n";
                        Holder.inputPassword.setBackgroundResource(R.drawable.white_cut_conner_invalid);
                    }

                    if (!( Holder.inputConfirmPassword.length() > 0)){
                        errorMsg += "- ยืนยันรหัสผ่านใหม่ ";
                        Holder.inputConfirmPassword.setBackgroundResource(R.drawable.white_cut_conner_invalid);
                    }
                    new DialogChoice(ChangePasswordActivity.this)
                            .ShowOneChoice("กรุณากรอกข้อมูล", errorMsg);


                }


            }
        });
    }




    private void API_ChangePassword(
            final String userId
            ,final String password
            ,final String fname
            ,final String lname
            ,final String email
    ) {
/*
        1.SaveFlag (บังคับ)
        2.UserID (บังคับกรณี SaveFlag=2)
        3.UserLogin (บังคับกรณี SaveFlag=1 /SaverFlag=2 ไม่ต้องส่งมา ห้ามเปลี่ยน userLogin)
        4.UserPwd (บังคับกรณี SaveFlag=1 /SaveFlag=2 ส่งมาเมื่อเปลี่ยน PWD) ** field นี้เข้ารหัส
        5.UserFirstName (บังคับใส่)
        6.UserLastName (บังคับใส่)
        7.UserEmail (ไม่บังคับ)
        8.ImeiCode
        */
        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {


                mRegister register = (mRegister) obj;
                List<mRegister.mRespBody> mRegisterBodyLists = register.getRespBody();


                if (mRegisterBodyLists.size() != 0) {

                    toastDisplayCustom_API("เปลียนรหัสผ่านสำเร็จ");
                }

            }

            @Override
            public void callbackError(int code, String errorMsg) {
                new DialogChoice(ChangePasswordActivity.this)
                        .ShowOneChoice(errorMsg, "");
            }

        }).API_Request(false, RequestServices.ws_saveRegister +
                "?SaveFlag=" + 2 +
                "&UserID=" +userId+
                "&UserLogin="+
                "&UserPwd=" +ServiceInstance.md5(password)+
                "&UserFirstName="+fname+
                "&UserLastName="+lname+
                "&UserEmail=" +
                "&ImeiCode=" + ServiceInstance.GetDeviceID(ChangePasswordActivity.this)
        );
    }

    private  void toastDisplayCustom_API(String msg){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);

        com.neopixl.pixlui.components.textview.TextView text = (com.neopixl.pixlui.components.textview.TextView) layout.findViewById(R.id.toast_label);
        text.setText(msg);

        toast.show();
    }


    private void API_CheckOldPassword(final String userId ,final String username, String password, final String newPassword,final String fname, final String lname,final String email) {
        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mLogin login = (mLogin) obj;
                List<mLogin.mRespBody> loginBodyLists = login.getRespBody();

                if (loginBodyLists.size() != 0) {
                    API_ChangePassword(userId,newPassword,fname,lname,email);
                }

            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Holder.inputOldPassword.setBackgroundResource(R.drawable.white_cut_conner_invalid);
                new DialogChoice(ChangePasswordActivity.this).ShowOneChoice("รหัสผ่านเดิมไม่ถูกต้อง", "");
            }
        }).API_Request(false, RequestServices.ws_chkLogin +
                "?UserLogin=" + username + "&UserPwd=" + ServiceInstance.md5(password) +
                "&ImeiCode=" + ServiceInstance.GetDeviceID(ChangePasswordActivity.this));

    }

    private static class  Holder {
        static  EditText  inputPassword,inputConfirmPassword,inputOldPassword;

    }
}
