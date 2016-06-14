package th.co.rcmo.rcmoapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import th.co.rcmo.rcmoapp.Module.mGetRegister;
import th.co.rcmo.rcmoapp.Module.mRegister;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;

public class EditUserActivity extends Activity {

    EditText inputName,inputSirName,inputUsername;
    TextView inputEmail;
    public static mGetRegister.mRespBody  userInfoRespBody = new mGetRegister.mRespBody();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        setUI();
        setAction();




    }


    private void setUI() {

         inputName     =  (EditText)findViewById(R.id.inputName);
         inputSirName  =  (EditText)findViewById(R.id.inputSirName);
         inputUsername =  (EditText)findViewById(R.id.inputUsername);
         inputEmail    =  (TextView)findViewById(R.id.inputEmail);

        inputName.setText(userInfoRespBody.getUserFirstName());
        inputSirName.setText(userInfoRespBody.getUserLastName());
        inputUsername.setText(userInfoRespBody.getUserLogin());
        inputEmail.setText(userInfoRespBody.getUserEmail());

    }
    private void setAction() {

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //change password
        findViewById(R.id.textLinkChangePass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditUserActivity.this, ChangePasswordActivity.class));
              /*  startActivity(new Intent(LoginActivity.this, WebActivity.class)
                        .putExtra("link", "http://www.google.co.th/"));
                        */
            }
        });

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputName     =  (EditText)findViewById(R.id.inputName);
                inputSirName  =  (EditText)findViewById(R.id.inputSirName);
                inputUsername =  (EditText)findViewById(R.id.inputUsername);
                inputEmail    =  (TextView)findViewById(R.id.inputEmail);

                SharedPreferences sp = getSharedPreferences(ServiceInstance.PREF_NAME, Context.MODE_PRIVATE);
                String userId = sp.getString(ServiceInstance.sp_userId, "0");

                API_EditUser(  userId
                             , inputUsername.getText().toString()
                             , inputName.getText().toString()
                             , inputSirName.getText().toString()
                             , inputEmail.getText().toString());
                /*
                Context context = getApplicationContext();
                CharSequence text = "ปรับปรุงข้อมูลผู้ใช้งานสำเร็จ";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                */
            }
        });



        //change logout
        findViewById(R.id.textLinkLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences(ServiceInstance.PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(ServiceInstance.sp_userId, "0");
                editor.commit();

                Intent  i= new Intent(EditUserActivity.this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();

              /*  startActivity(new Intent(LoginActivity.this, WebActivity.class)
                        .putExtra("link", "http://www.google.co.th/"));
                        */
            }
        });


        //tutorial
        findViewById(R.id.btnQuestion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  startActivity(new Intent(LoginActivity.this, WebActivity.class)
                        .putExtra("link", "http://www.google.co.th/"));
                        */
            }
        });


    }


    private void API_EditUser(
             final String userId
            ,final String username
            ,final String name
            ,final String sirName
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

                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_layout,
                            (ViewGroup) findViewById(R.id.toast_layout_root));

                    TextView text = (TextView) layout.findViewById(R.id.toast_label);
                    text.setText("ปรับปรุงข้อมูลผู้ใช้งานสำเร็จ");

                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }

            }

            @Override
            public void callbackError(int code, String errorMsg) {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout,
                        (ViewGroup) findViewById(R.id.toast_layout_root));

                TextView text = (TextView) layout.findViewById(R.id.toast_label);
                text.setText("ไม่สามารถปรับปรุงข้อมูลผู้ใชได้้");

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        }).API_Request(true, RequestServices.ws_saveRegister +
                "?SaveFlag=" + 2 +
                "&UserID=" + userId +
                "&UserLogin=" + username +
                "&UserPwd=" +
                "&UserFirstName=" + name +
                "&UserLastName=" + sirName +
                "&UserEmail=" + email +
                "&ImeiCode=" + ServiceInstance.GetDeviceID(EditUserActivity.this)
        );
    }

}
