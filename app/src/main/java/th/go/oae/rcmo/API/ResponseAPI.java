package th.go.oae.rcmo.API;

import android.content.Context;
import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;

import th.go.oae.rcmo.Module.mAmphoe;
import th.go.oae.rcmo.Module.mCopyPlot;
import th.go.oae.rcmo.Module.mCurrentLocation;
import th.go.oae.rcmo.Module.mDeletePlot;
import th.go.oae.rcmo.Module.mGetPlotDetail;
import th.go.oae.rcmo.Module.mGetPlotSuit;
import th.go.oae.rcmo.Module.mGetRegister;
import th.go.oae.rcmo.Module.mGetVariable;
import th.go.oae.rcmo.Module.mLogin;
import th.go.oae.rcmo.Module.mPlantGroup;
import th.go.oae.rcmo.Module.mProduct;
import th.go.oae.rcmo.Module.mProvince;
import th.go.oae.rcmo.Module.mRegister;
import th.go.oae.rcmo.Module.mRiceProduct;
import th.go.oae.rcmo.Module.mSavePlotDetail;
import th.go.oae.rcmo.Module.mStatus;
import th.go.oae.rcmo.Module.mTumbon;
import th.go.oae.rcmo.Module.mUpdateUserPlotSeq;
import th.go.oae.rcmo.Module.mUserPlotList;
import th.go.oae.rcmo.View.DialogChoice;

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

    public void API_Request(final boolean ShowErrorcase, final String url) {
        Log.i("", "API_Request " + url);
        aq.ajax(url,
                String.class, new AjaxCallback<String>() {

                    @Override
                    public void callback(String url_ws, String html, AjaxStatus status) {

                        Log.i(RequestServices.TAG, "API_Request url : " + url_ws);
                        Log.i(RequestServices.TAG, "API_Request Response : " + html);
                        if (html != null) {

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
                                        } else if (url_ws.contains(RequestServices.ws_getPlotDetail)) {
                                            object = new Gson().fromJson(html, mGetPlotDetail.class);
                                        } else if (url_ws.contains(RequestServices.ws_getVariable)) {
                                            object = new Gson().fromJson(html, mGetVariable.class);
                                        } else if (url_ws.contains(RequestServices.ws_getPlotSuit)) {
                                            object = new Gson().fromJson(html, mGetPlotSuit.class);
                                        }else if (url_ws.contains(RequestServices.ws_getCurrentLocation)) {
                                            object = new Gson().fromJson(html, mCurrentLocation.class);
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
                            } else {
                                new DialogChoice(c).ShowOneChoice("", "การเชื่อมต่อ Server ผิดพลาด");
                            }

                        }

                    }


                }
        );
    }
}
