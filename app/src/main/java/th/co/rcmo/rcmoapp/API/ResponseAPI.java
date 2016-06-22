package th.co.rcmo.rcmoapp.API;

import android.content.Context;
import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;

import th.co.rcmo.rcmoapp.Module.mAmphoe;
import th.co.rcmo.rcmoapp.Module.mCopyPlot;
import th.co.rcmo.rcmoapp.Module.mDeletePlot;
import th.co.rcmo.rcmoapp.Module.mGetRegister;
import th.co.rcmo.rcmoapp.Module.mLogin;
import th.co.rcmo.rcmoapp.Module.mPlantGroup;
import th.co.rcmo.rcmoapp.Module.mProduct;
import th.co.rcmo.rcmoapp.Module.mProvince;
import th.co.rcmo.rcmoapp.Module.mRegister;
import th.co.rcmo.rcmoapp.Module.mRiceProduct;
import th.co.rcmo.rcmoapp.Module.mSavePlotDetail;
import th.co.rcmo.rcmoapp.Module.mStatus;
import th.co.rcmo.rcmoapp.Module.mTumbon;
import th.co.rcmo.rcmoapp.Module.mUpdateUserPlotSeq;
import th.co.rcmo.rcmoapp.Module.mUserPlotList;
import th.co.rcmo.rcmoapp.View.DialogChoice;

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
                        if(html!= null) {

                            mStatus _status = new Gson().fromJson(html, mStatus.class);
                            if (_status != null) {
                                Log.i(RequestServices.TAG, "API_Request status : " + _status.getRespStatus().toString());
                                if (status.getCode() == 200) {
                                    if (_status.getRespStatus().getStatusID() == 0) {
                                        Object object = null;
                                        if (url_ws.contains(RequestServices.ws_chkLogin)) {
                                            object = new Gson().fromJson(html, mLogin.class);
                                        } else if (url_ws.contains(RequestServices.ws_saveRegister)) {
                                            object = new Gson().fromJson(html, mRegister.class);
                                        } else if (url_ws.contains(RequestServices.ws_getPlotList)) {
                                            object = new Gson().fromJson(html, mUserPlotList.class);
                                        } else if (url_ws.contains(RequestServices.ws_getProduct)) {
                                            object = new Gson().fromJson(html, mProduct.class);
                                        } else if (url_ws.contains(RequestServices.ws_getRiceProduct)) {
                                            object = new Gson().fromJson(html, mRiceProduct.class);
                                        } else if (url_ws.contains(RequestServices.ws_getPlantGroup)) {
                                            object = new Gson().fromJson(html, mPlantGroup.class);
                                        } else if (url_ws.contains(RequestServices.ws_getRegister)) {
                                            object = new Gson().fromJson(html, mGetRegister.class);
                                        } else if (url_ws.contains(RequestServices.ws_savePlotDetail)) {
                                            object = new Gson().fromJson(html, mSavePlotDetail.class);
                                        } else if (url_ws.contains(RequestServices.ws_deletePlot)) {
                                            object = new Gson().fromJson(html, mDeletePlot.class);
                                        } else if (url_ws.contains(RequestServices.ws_updateUserPlotSeq)) {
                                            object = new Gson().fromJson(html, mUpdateUserPlotSeq.class);
                                        } else if (url_ws.contains(RequestServices.ws_getProvince)) {
                                            object = new Gson().fromJson(html, mProvince.class);
                                        } else if (url_ws.contains(RequestServices.ws_getAmphoe)) {
                                            object = new Gson().fromJson(html, mAmphoe.class);
                                        } else if (url_ws.contains(RequestServices.ws_getTambon)) {
                                            object = new Gson().fromJson(html, mTumbon.class);
                                        } else if (url_ws.contains(RequestServices.ws_copyPlot)) {
                                            object = new Gson().fromJson(html, mCopyPlot.class);
                                        }
                                        onCallbackAPIListener.callbackSuccess(object);
                                    } else {
                                        if (ShowErrorcase)
                                            new DialogChoice(c).ShowOneChoice("", _status.getRespStatus().getStatusMsg());
                                        else
                                            onCallbackAPIListener.callbackError(_status.getRespStatus().getStatusID()
                                                    , _status.getRespStatus().getStatusMsg());
                                    }
                                } else {
                                    new DialogChoice(c).ShowOneChoice("", "กรุณาเชื่อมต่ออินเตอร์เน็ต");
                                }
                            }
                        }else{
                            new DialogChoice(c).ShowOneChoice("", "การเชื่อมต่อ Server ผิดพลาด");
                        }

                    }

                });
    }
}
