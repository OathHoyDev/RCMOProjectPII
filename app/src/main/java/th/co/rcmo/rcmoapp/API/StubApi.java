package th.co.rcmo.rcmoapp.API;

import java.util.ArrayList;
import java.util.List;

import th.co.rcmo.rcmoapp.Module.mProvince;
import th.co.rcmo.rcmoapp.R;

/**
 * Created by Taweesin on 18/5/2559.
 */
public class StubApi {
    public static  List<mProvince.mRespBody> getProvinceList(List<mProvince.mRespBody> provicnceList){
        provicnceList = new ArrayList<>();

        mProvince.mRespBody provicnceInfo = new mProvince().new mRespBody(R.drawable.star_unselected_icon,1,"สระบุรี");
        provicnceList.add(provicnceInfo);

        provicnceInfo = new mProvince().new mRespBody(R.drawable.star_unselected_icon,2,"กรุงเทพ");
        provicnceList.add(provicnceInfo);

        provicnceInfo = new mProvince().new mRespBody(R.drawable.star_unselected_icon,3,"น่าน");
        provicnceList.add(provicnceInfo);

        provicnceInfo = new mProvince().new mRespBody(R.drawable.star_unselected_icon,4,"แพร่");
        provicnceList.add(provicnceInfo);

        provicnceInfo = new mProvince().new mRespBody(R.drawable.star_unselected_icon,5,"ลำปาง");
        provicnceList.add(provicnceInfo);

        provicnceInfo = new mProvince().new mRespBody(R.drawable.star_unselected_icon,6,"ลำพูน");
        provicnceList.add(provicnceInfo);

        provicnceInfo = new mProvince().new mRespBody(R.drawable.star_unselected_icon,7,"เชียงใหม่");
        provicnceList.add(provicnceInfo);

        provicnceInfo = new mProvince().new mRespBody(R.drawable.star_unselected_icon,8,"เชียงราย");
        provicnceList.add(provicnceInfo);

        return provicnceList;
    }

    public static  List<mProvince.mRespBody> getFavoriteProvinceList(List<mProvince.mRespBody> provicnceList){
        provicnceList = new ArrayList<>();

        mProvince.mRespBody provicnceInfo = new mProvince().new mRespBody(R.drawable.star_selected_icon,1,"เลย");
        provicnceList.add(provicnceInfo);

        provicnceInfo = new mProvince().new mRespBody(R.drawable.star_selected_icon,2,"ขอนแก่น");
        provicnceList.add(provicnceInfo);


        return provicnceList;
    }
}
