package th.co.rcmo.rcmoapp.Adapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.List;

import th.co.rcmo.rcmoapp.API.RequestServices;
import th.co.rcmo.rcmoapp.API.ResponseAPI;
import th.co.rcmo.rcmoapp.Model.UserPlotModel;
import th.co.rcmo.rcmoapp.Module.mGetVariable;
import th.co.rcmo.rcmoapp.PBProdDetailCalculateFment;
import th.co.rcmo.rcmoapp.PBProdDetailMapFment;
import th.co.rcmo.rcmoapp.PBProdDetailStandradFment;
import th.co.rcmo.rcmoapp.ProductDetailMapFragment;

/**
 * Created by Taweesin on 24/6/2559.
 */
public class PBCalculatePagerAdapter extends FragmentStatePagerAdapter {

    UserPlotModel userPlotModel;

    public PBCalculatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag=null;
        switch (position){
            case 0:
                frag=new PBProdDetailStandradFment();
                break;
            case 1:
                //userPlotModel = get
                frag=new PBProdDetailCalculateFment();
                break;
            case 2:
                frag=new ProductDetailMapFragment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title=" ";
        switch (position){
            case 0:
                title="ค่ามาตรฐาน";
                break;
            case 1:
                title="คำนวนต้นทุน";
                break;
            case 2:
                title="แผนที่";
                break;
        }

        return title;
    }

//    private void API_getVariable(String prdID , final String fisheryType) {
//
//        new ResponseAPI(context, new ResponseAPI.OnCallbackAPIListener() {
//            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
//            @Override
//            public void callbackSuccess(Object obj) {
//
//                mGetVariable mVariable = (mGetVariable) obj;
//                List<mGetVariable.mRespBody> mVariableBodyLists = mVariable.getRespBody();
//
//                if (mVariableBodyLists.size() != 0) {
//
//                    formularCode = mVariableBodyLists.get(0).getFormularCode();
//
//                    initialTab();
//
//                }
//
//
//            }
//
//            @Override
//            public void callbackError(int code, String errorMsg) {
//                Log.d("Error", errorMsg);
//            }
//        }).API_Request(true, RequestServices.ws_getVariable +
//                "?PrdID=" + prdID +
//                "&FisheryType=" + 1);
//
//    }

}
