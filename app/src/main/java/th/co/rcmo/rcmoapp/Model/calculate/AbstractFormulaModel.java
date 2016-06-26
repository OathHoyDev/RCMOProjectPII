package th.co.rcmo.rcmoapp.Model.calculate;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import th.co.rcmo.rcmoapp.API.RequestServices;
import th.co.rcmo.rcmoapp.API.ResponseAPI;
import th.co.rcmo.rcmoapp.Module.mGetPlotDetail;
import th.co.rcmo.rcmoapp.Module.mSavePlotDetail;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;

/**
 * Created by SilVeriSm on 6/22/2016 AD.
 */
public abstract class AbstractFormulaModel {


    public abstract void calculate();

    public abstract void prepareListData();

    public Object getValueFromAttributeName(Object obj, String fieldName) {

        Object result = null;

        try {

            Field field = obj.getClass().getDeclaredField(fieldName);

            if (field.getType() == Character.TYPE) {
                result = field.getChar(obj);
            }
            if (field.getType() == Short.TYPE) {
                result = field.getShort(obj);
            } else if (field.getType() == Integer.TYPE) {
                result = field.getShort(obj);
            } else if (field.getType() == Long.TYPE) {
                result = field.getLong(obj);
            } else if (field.getType() == Float.TYPE) {
                result = field.getFloat(obj);
            } else if (field.getType() == Double.TYPE) {
                result = field.getDouble(obj);
            } else if (field.getType() == Byte.TYPE) {
                result = field.getByte(obj);
            } else if (field.getType() == Boolean.TYPE) {
                result = field.getBoolean(obj);
            } else {
                result = field.getBoolean(obj);
            }

        } catch (NoSuchFieldException noE) {

        } catch (IllegalAccessException ilE) {

        }

        return result;

    }

    public void setValueFromAttributeName(Object obj, String fieldName, String value) throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        if (field.getType() == Character.TYPE) {
            field.set(obj, value.charAt(0));
            return;
        }
        if (field.getType() == Short.TYPE) {
            field.set(obj, Short.parseShort(value));
            return;
        }
        if (field.getType() == Integer.TYPE) {
            field.set(obj, Integer.parseInt(value));
            return;
        }
        if (field.getType() == Long.TYPE) {
            field.set(obj, Long.parseLong(value));
            return;
        }
        if (field.getType() == Float.TYPE) {
            field.set(obj, Float.parseFloat(value));
            return;
        }
        if (field.getType() == Double.TYPE) {
            field.set(obj, Double.parseDouble(value));
            return;
        }
        if (field.getType() == Byte.TYPE) {
            field.set(obj, Byte.parseByte(value));
            return;
        }
        if (field.getType() == Boolean.TYPE) {
            field.set(obj, Boolean.parseBoolean(value));
            return;
        }
        field.set(obj, value);
    }

    private void API_savePlotDetail(Context context, String userID, mGetPlotDetail plotDetail, String varName, String varValue) {

        new ResponseAPI(context, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mSavePlotDetail mPlotDetail = (mSavePlotDetail) obj;
                List<mSavePlotDetail.mRespBody> mPlotDetailBodyLists = mPlotDetail.getRespBody();

                if (mPlotDetailBodyLists.size() != 0) {

                    String plotID = String.valueOf(mPlotDetailBodyLists.get(0).getPlotID());
                }


            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getPlotDetail +
                "?SaveFlag=2" +
                "&UserID=" + userID +
                "&PlotID=" + plotDetail.getRespBody().get(0).getPlotID() +
                "&PrdID=" + plotDetail.getRespBody().get(0).getPrdID() +
                "&PrdGrpID=" + plotDetail.getRespBody().get(0).getPrdGrpID() +
                "&PlotRai=" + plotDetail.getRespBody().get(0).getPlotRai() +
                "&PondRai=" + plotDetail.getRespBody().get(0).getPondRai() +
                "&PondNgan=" + plotDetail.getRespBody().get(0).getPondNgan() +
                "&PondWa=" + plotDetail.getRespBody().get(0).getPondWa() +
                "&PondMeter=" + plotDetail.getRespBody().get(0).getPondMeter() +
                "&CoopMeter=" + plotDetail.getRespBody().get(0).getCoopMeter() +
                "&CoopNumber=" + plotDetail.getRespBody().get(0).getCoopNumber() +
                "&TamCode=" + plotDetail.getRespBody().get(0).getTamCode() +
                "&AmpCode=" + plotDetail.getRespBody().get(0).getAmpCode() +
                "&ProvCode=" + plotDetail.getRespBody().get(0).getProvCode() +
                "&AnimalNumber=" + plotDetail.getRespBody().get(0).getAnimalNumber() +
                "&AnimalWeight=" + plotDetail.getRespBody().get(0).getAnimalWeight() +
                "&AnimalPrice=" + plotDetail.getRespBody().get(0).getAnimalPrice() +
                "&FisheryType=" + plotDetail.getRespBody().get(0).getFisheryType() +
                "&FisheryNumType=2" +
                "&FisheryNumber=" + plotDetail.getRespBody().get(0).getFisheryNumber() +
                "&FisheryWeight=" + plotDetail.getRespBody().get(0).getFisheryWeight() +
                "&ImeiCode=" + ServiceInstance.GetDeviceID(context) +
                "&VarName=" + varName +
                "&VarValue=" + varValue +
                "&CalResult=''");

    }

    public String getParamValue (Object obj ,Hashtable<String, String> hashValue){

        String resultParam = "";

        for(Iterator itr = hashValue.keySet().iterator() ; itr.hasNext();){
            String key = (String) itr.next();

            String value = getValueFromAttributeName(obj , key).toString();

            resultParam+=value+"|";

        }

        return resultParam.substring(0 , resultParam.lastIndexOf("|"));

    }

    public String getParamName (Object obj ,Hashtable<String, String> hashValue){

        String resultParam = "";

        for(Iterator itr = hashValue.keySet().iterator() ; itr.hasNext();){
            String key = (String) itr.next();

            resultParam+=key+"|";

        }

        return resultParam.substring(0 , resultParam.lastIndexOf("|"));

    }


}
