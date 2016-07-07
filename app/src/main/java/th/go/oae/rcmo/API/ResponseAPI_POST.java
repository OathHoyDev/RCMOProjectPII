package th.go.oae.rcmo.API;

import android.content.Context;
import android.util.Log;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;
import org.json.JSONObject;
import java.util.HashMap;
import th.go.oae.rcmo.Module.mStatus;
import th.go.oae.rcmo.Util.Util;
import th.go.oae.rcmo.View.DialogChoice;


public class ResponseAPI_POST {

    Context c;
    OnCallbackAPIListener onCallbackAPIListener;

    public interface OnCallbackAPIListener {
        void callbackSuccess(JSONObject obj);
        void callbackError(int code, String errorMsg);
    }

    public ResponseAPI_POST(Context c, OnCallbackAPIListener onCallbackAPIListener){
        this.c =c;
        this.onCallbackAPIListener =onCallbackAPIListener;
    }

    public void POST(String url,HashMap<String,Object> hash,boolean ShowErrorInternet, final boolean ShownErrorServer) {

        if(Util.isNetworkAvailable(c)){

            Log.i("ResponseAPI_POST", "==============> "+url);
            Log.i("ResponseAPI_POST", "Parameter: " + hash.toString());
            new AQuery(c).ajax(url, hash, JSONObject.class, new AjaxCallback<JSONObject>() {
                        @Override
                        public void callback(String url, JSONObject object, AjaxStatus status) {
                            super.callback(url, object, status);

                            Log.i("ResponseAPI_POST", "JsonResponse: " + object);

                            if(object!=null){
                                mStatus _status = new Gson().fromJson(object.toString(),mStatus.class);

                                if(_status.getRespStatus().getStatusID()==0){
                                    onCallbackAPIListener.callbackSuccess(object);
                                }else{
                                    if(ShownErrorServer){
                                        new DialogChoice(c).ShowOneChoice("", _status.getRespStatus().getStatusMsg());
                                    }

                                    onCallbackAPIListener.callbackError(_status.getRespStatus().getStatusID(),
                                            _status.getRespStatus().getStatusMsg());
                                }

                            }else
                                onCallbackAPIListener.callbackError(-1,"Error");
                        }
                    }
            );

        }else{
            if(ShowErrorInternet)
                new DialogChoice(c).ShowOneChoice("", "กรุณาเชื่อมต่ออินเตอร์เน็ต");
            onCallbackAPIListener.callbackError(-1, "Connect fail");
        }

    }



}
