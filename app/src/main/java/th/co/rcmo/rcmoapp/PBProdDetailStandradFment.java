package th.co.rcmo.rcmoapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.neopixl.pixlui.components.button.Button;
import com.neopixl.pixlui.components.textview.TextView;

import java.util.ArrayList;
import java.util.List;

import th.co.rcmo.rcmoapp.API.ProductService;
import th.co.rcmo.rcmoapp.API.RequestServices;
import th.co.rcmo.rcmoapp.API.ResponseAPI;
import th.co.rcmo.rcmoapp.Adapter.ProdDetailStandardAdapter;
import th.co.rcmo.rcmoapp.Model.STDVarModel;
import th.co.rcmo.rcmoapp.Model.UserPlotModel;
import th.co.rcmo.rcmoapp.Module.mGetVariable;
import th.co.rcmo.rcmoapp.Util.Util;


public class PBProdDetailStandradFment extends Fragment implements  View.OnClickListener {
    ViewHolder holder = new ViewHolder();
    static UserPlotModel userPlotModel;
    public  static  List<STDVarModel> stdVarModelList =  new ArrayList<STDVarModel>();
    Context context;
    //static List<STDVarModel> stdVarModelList = new ArrayList<STDVarModel>();
    public PBProdDetailStandradFment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pbprod_datail_standrad_fment, container, false);
        context =  view.getContext();
        userPlotModel = PBProductDetailActivity.userPlotModel;
       // stdVarModelList = PBProductDetailActivity.stdVarModelList;


        holder.recalBtn  = (TextView) view.findViewById(R.id.reCalBtn);
        holder.regetlBtn = (TextView) view.findViewById(R.id.reGetBtn);
        holder.centerImg  = (TextView) view.findViewById(R.id.centerImg);
        holder.listView   = (ListView) view.findViewById(R.id.listView);



        holder.recalBtn.setOnClickListener(this);
        holder.regetlBtn.setOnClickListener(this);
        setUi(view);

        API_getVariable(userPlotModel.getPrdID(),userPlotModel.getFisheryType());

        return view;
    }

    public void onClick(View v) {
        if (v.getId() == R.id.reCalBtn) {
           // PBProductDetailActivity.userPlotModel = userPlotModel;
           // startActivity(new Intent(context, PBProductDetailActivity.class));
            //PBProductDetailActivity.finish();

            //Toast toast = Toast.makeText( v.getContext(), "คำนวนใหม่สำเร็จ่", Toast.LENGTH_SHORT);
          //  toast.show();
            Util.showDialogAndDismiss(v.getContext(),"คำนวนใหม่สำเร็จ");
            PBProductDetailActivity.pager.setCurrentItem(1);

        }else if (v.getId() == R.id.reGetBtn){

            API_getVariable(userPlotModel.getPrdID(),userPlotModel.getFisheryType());
            Util.showDialogAndDismiss(v.getContext(),"ดึงข้อมูลล่าสุดเรียบร้อย");
           // Toast toast = Toast.makeText( v.getContext(), "ดึงข้อมูลใหม่สำเร็จ", Toast.LENGTH_SHORT);
           // toast.show();
        }
    }

    private void setUi(View v){
        
        int groupId = Integer.valueOf(userPlotModel.getPrdGrpID());

        if(groupId == 1){
            holder.recalBtn.setBackgroundResource(R.drawable.action_plant_recal);
            holder.centerImg.setBackgroundResource(R.drawable.bottom_green_total);
            holder.regetlBtn.setBackgroundResource(R.drawable.action_plant_reget);

        }else if(groupId == 2){
            holder.recalBtn.setBackgroundResource(R.drawable.action_animal_recal);
            holder.centerImg.setBackgroundResource(R.drawable.bottom_pink_total);
            holder.regetlBtn.setBackgroundResource(R.drawable.action_animal_reget);
        }else{
            holder.recalBtn.setBackgroundResource(R.drawable.action_fish_recal);
            holder.centerImg.setBackgroundResource(R.drawable.bottom_blue_total);
            holder.regetlBtn.setBackgroundResource(R.drawable.action_fish_reget);

        }
    }

    private void API_getVariable(String prdID , final String fisheryType) {

        new ResponseAPI(context, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mGetVariable mVariable = (mGetVariable) obj;
                List<mGetVariable.mRespBody> mVariableBodyLists = mVariable.getRespBody();

                if (mVariableBodyLists.size() != 0) {

                    stdVarModelList =  ProductService.prepareSTDVarList(mVariableBodyLists.get(0), fisheryType);

                    holder.listView.setAdapter(new ProdDetailStandardAdapter( context,stdVarModelList,Integer.valueOf(userPlotModel.getPrdGrpID())));

                }


            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getVariable +
                "?PrdID=" + prdID +
                "&FisheryType=" + fisheryType);

    }

    static class ViewHolder {
        TextView recalBtn;
        TextView regetlBtn;
        TextView centerImg;
        ListView listView;

    }
}
