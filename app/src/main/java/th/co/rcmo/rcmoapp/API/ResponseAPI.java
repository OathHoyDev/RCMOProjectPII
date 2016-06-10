package th.co.rcmo.rcmoapp.API;

import android.content.Context;
import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;

import th.co.rcmo.rcmoapp.Module.mLogin;
import th.co.rcmo.rcmoapp.Module.mStatus;
import th.co.rcmo.rcmoapp.View.Dialog;

/**
 * Created by Taweesin on 6/10/2016.
 */
public class ResponseAPI {
    String TAG = "ResponseAPI";
    OnCallbackAPIListener onCallbackAPIListener;
    Context c;
    AQuery aq;

    public interface OnCallbackAPIListener {
        void callbackSuccess(Object obj);
        void callbackError(int code, String errorMsg);
    }
    public ResponseAPI(Context c) {
        this.c = c;
    }

    public ResponseAPI(Context c, OnCallbackAPIListener onCallbackAPIListener) {
        this.c = c;
        this.onCallbackAPIListener = onCallbackAPIListener;
        aq = new AQuery(c);
    }

    public void API_Request(final boolean ShowErrorcase,final String url) {
        Log.i("", "API_Request "+url);
        aq.ajax(url,
                String.class, new AjaxCallback<String>() {

                    @Override
                    public void callback(String url_ws, String html, AjaxStatus status) {

                        Log.i(RequestServices.TAG, "API_Request url : " +url_ws);
                        Log.i(RequestServices.TAG, "API_Request Response : " +html);
                        mStatus _status = new Gson().fromJson(html, mStatus.class);
                        if(_status !=null){
                            Log.i(RequestServices.TAG, "API_Request status : " + _status.getRespStatus().toString());
                            if (status.getCode()==200) {
                                if (_status.getRespStatus().getStatusID()==1) {
                                    Object object = null;
                                    if(url_ws.contains(RequestServices.ws_chkLogin)){
                                        object = new Gson().fromJson(html, mLogin.class);
                                    }
                                    onCallbackAPIListener.callbackSuccess(object);
                                } else {
                                    if(ShowErrorcase)
                                        new Dialog(c).ShowOneChoice("",_status.getRespStatus().getStatusMsg());
                                    else onCallbackAPIListener.callbackError(_status.getRespStatus().getStatusID()
                                            ,_status.getRespStatus().getStatusMsg());
                                }
                            }else {
                                new Dialog(c).ShowOneChoice("","กรุณาเชื่อมต่ออินเตอร์เน็ต");
                            }
                        }

                    }

                });
    }
}
