package th.co.rcmo.rcmoapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.neopixl.pixlui.components.edittext.EditText;

import java.util.List;

import th.co.rcmo.rcmoapp.API.RequestServices;
import th.co.rcmo.rcmoapp.API.ResponseAPI;
import th.co.rcmo.rcmoapp.Module.mLogin;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;
import th.co.rcmo.rcmoapp.View.Dialog;

public class LoginActivity extends Activity {
    EditText inputUsername, inputPassword;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputUsername = (EditText) findViewById(R.id.inputUsername);
        inputPassword = (EditText) findViewById(R.id.inputPassword);

        setUI();
        setAction();

    }


    private void setUI() {



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
    }

    private void setAction() {

        findViewById(R.id.textLinkRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        findViewById(R.id.textLinkChangePass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ChangePasswordActivity.class));
            }
        });


        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (inputUsername.length() > 8) {

                        if (inputPassword.length() != 0) {
                            //startActivity(new Intent(LoginActivity.this, EditUserActivity.class));
                           Login();
                        } else {
                            new Dialog(LoginActivity.this).ShowOneChoice("", "กรุณากรอกรหัสผ่าน");
                        }

                } else {
                    new Dialog(LoginActivity.this).ShowOneChoice("", "กรุณากรอกรหัสผู้ใช้งานให้ถูกต้อง");
                }
            }
        });

    }


    private void Login() {
            String imeId = "12345";
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
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putString("sp_user_name", username).apply();
                    editor.putString("sp_user_id", loginBodyLists.get(0).getUserID()).apply();
                    // editor.putString("employee_full_name", loginEmployeies.get(0).getEmployeeFullName()).apply();
                    // editor.putString("employee_user_id", loginEmployeies.get(0).getUserID()).apply();
                    // editor.putString("employee_Login_lastdate", Instance.GetDateNow()).apply();
                    editor.apply();

//                    {"RespStatus":{"StatusID":2,"StatusMsg":"ไม่พบข้อมูล"},"RespBody":[]}
//http://111.223.34.154/WS_OAE1603/services.asmx/chkLogin?UserLogin=RCMO1603&UserPwd=RCMO1603&ImeiCode=12345
                    //startActivity(new Intent(LoginActivity.this, EditUserActivity.class)
                    //      .putExtra("IsComeformAllJob", IsComeformAllJob));
                    //finish();
                }

            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Erroo",errorMsg);
            }
        }).API_Request(true, RequestServices.ws_chkLogin +
                "?UserLogin=" + username + "&UserPwd=" + password +
                "&ImeiCode=" + ServiceInstance.GetDeviceID(LoginActivity.this));

    }


}
