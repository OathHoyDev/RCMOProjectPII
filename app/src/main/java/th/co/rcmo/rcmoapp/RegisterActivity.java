package th.co.rcmo.rcmoapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.neopixl.pixlui.components.edittext.EditText;
import java.util.List;
import th.co.rcmo.rcmoapp.API.RequestServices;
import th.co.rcmo.rcmoapp.API.ResponseAPI;
import th.co.rcmo.rcmoapp.Module.mRegister;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;

public class RegisterActivity extends AppCompatActivity {
    EditText inputName, inputSirName,inputUsername,inputEmail,inputPassword,inputConfirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputName            = (EditText) findViewById(R.id.inputName);
        inputSirName         = (EditText) findViewById(R.id.inputSirName);
        inputUsername        = (EditText) findViewById(R.id.inputUsername);
        inputEmail           = (EditText) findViewById(R.id.inputEmail);
        inputPassword        = (EditText) findViewById(R.id.inputPassword);
        inputConfirmPassword = (EditText) findViewById(R.id.inputConfirmPassword);

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


    private void register() {

        API_Login (   inputName.getText().toString()
                    , inputSirName.getText().toString()
                    , inputUsername.getText().toString()
                    , inputEmail.getText().toString()
                    , inputPassword.getText().toString());
    }


    private void API_Login(     final String inputName
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

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();

                    //store into file variableE
                    editor.putString("sp_inputName"   , inputName).apply();
                    editor.putString("sp_inputSirName", inputSirName).apply();
                    editor.putString("sp_inputSirName", inputUsername).apply();
                    editor.putString("sp_inputSirName", inputEmail).apply();
                    editor.putString("sp_inputSirName", inputPassword).apply();
                    editor.putString("sp_userId" , mRegisterBodyLists.get(0).getUserID()).apply();
                    editor.apply();

//                    {"RespStatus":{"StatusID":2,"StatusMsg":"ไม่พบข้อมูล"},"RespBody":[]}

                    startActivity(new Intent(RegisterActivity.this, EditUserActivity.class)
                            .putExtra("userId", mRegisterBodyLists.get(0).getUserID()));
                    finish();
                }

            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error",errorMsg);
            }
        }).API_Request(true, RequestServices.ws_saveRegister +
                               "?SaveFlag="       + 1 +
                               "&UserID="         +
                               "&UserLogin="      + inputUsername +
                               "&UserPwd="        + ServiceInstance.md5(inputUsername) +
                               "&UserFirstName="  + inputName +
                               "&UserLastName="   + inputSirName +
                               "&UserEmail="      + inputEmail +
                               "&ImeiCode="       + ServiceInstance.GetDeviceID(RegisterActivity.this)
        );
//http://111.223.34.154/WS_OAE1603/services.asmx/saveRegister?SaveFlag=1&UserID=123&UserLogin=ball&UserPwd=7a10ea1b9b2872da9f375002c44ddfce&UserFirstName=taweesin&UserLastName=suksompoch&UserEmail=taweesin@gmail.com&ImeiCode=3b0cc4924383e736
        //http://111.223.34.154/WS_OAE1603/services.asmx/getPlotList?UserID=1&PlotID=&ImeiCode=
    }
}

