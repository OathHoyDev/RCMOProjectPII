package th.co.rcmo.rcmoapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.neopixl.pixlui.components.textview.TextView;
import java.util.ArrayList;
import java.util.List;

import th.co.rcmo.rcmoapp.API.RequestServices;
import th.co.rcmo.rcmoapp.API.ResponseAPI;
import th.co.rcmo.rcmoapp.Module.mDeletePlot;
import th.co.rcmo.rcmoapp.Module.mGetRegister;
import th.co.rcmo.rcmoapp.Module.mLogin;
import th.co.rcmo.rcmoapp.Module.mSavePlotDetail;
import th.co.rcmo.rcmoapp.Module.mUserPlotList;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;
import th.co.rcmo.rcmoapp.View.Dialog;

public class UserPlotListActivity extends Activity {
    ListView  userPlotListView;

    public static List<mUserPlotList.mRespBody> userPlotRespBodyList = new ArrayList<>();
    Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_plot_list);

        setUI();
        setAction();
    }



    private void setUI() {

        userPlotListView = (ListView) findViewById(R.id.listviewPlotUser);
        userPlotListView.setAdapter(new UserPlotAdapter(userPlotRespBodyList));
    }

    private void setAction() {

        //Add
        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserPlotListActivity.this, StepOneActivity.class));
              /*  startActivity(new Intent(LoginActivity.this, WebActivity.class)
                        .putExtra("link", "http://www.google.co.th/"));
                        */
            }
        });

        //User profile
        findViewById(R.id.btnProfile).setOnClickListener(new View.OnClickListener() {
            SharedPreferences sp = getSharedPreferences(ServiceInstance.PREF_NAME, Context.MODE_PRIVATE);
            String userId = sp.getString(ServiceInstance.sp_userId, "0");
            @Override
            public void onClick(View v) {
                API_GetRegister(Integer.valueOf(userId));
            }
        });

        //tutorial
        findViewById(R.id.btnQuestion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  startActivity(new Intent(LoginActivity.this, WebActivity.class)
                        .putExtra("link", "http://www.google.co.th/"));
                        */
            }
        });


    }



    class UserPlotAdapter extends BaseAdapter {
        List<mUserPlotList.mRespBody> userPlotList;

        UserPlotAdapter(List<mUserPlotList.mRespBody> userPlotList){
            this.userPlotList =userPlotList;
        }
        @Override
        public int getCount() {
            return  userPlotList.size();
        }

        @Override
        public mUserPlotList.mRespBody getItem(int position) {
            return userPlotList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public void removeItem(int position) {
            userPlotList.remove(position);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                LayoutInflater inflater = UserPlotListActivity.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.row_user_plot, parent, false);
            }




            mUserPlotList.mRespBody  respBody =  getItem(position);
            Holder h = new Holder();

            /**
             * Get Layout Obj
             */
            h.imgProduct = (ImageView) convertView.findViewById(R.id.imgProduct);
            h.labelProductName =  (TextView) convertView.findViewById(R.id.labelProductName);
            h.labelAddress     =  (TextView) convertView.findViewById(R.id.labelAddress);
            h.labelPlotSize    =  (TextView) convertView.findViewById(R.id.labelPlotSize);
            h.labelDate        =  (TextView) convertView.findViewById(R.id.labelDate);
            h.btnProfit        =  (TextView) convertView.findViewById(R.id.btnProfit);
            h.labelProfit      =  (TextView) convertView.findViewById(R.id.labelProfit);


            /**
             * Set UI Display value
             */
           if(respBody.getPrdGrpID() == 2){
               h.labelProductName.setTextColor(Color.parseColor("#d5444f"));
           }else if (respBody.getPrdGrpID() == 3){
               h.labelProductName.setTextColor (Color.parseColor("#00b4ed"));
           }else{
               h.labelProductName.setTextColor (Color.parseColor("#4cb748"));
           }


            String calResult = respBody.getCalResult();

            if(calResult!= null
                    &&  calResult.length()>0
                    &&  "-".equals(calResult.substring(0,1))){
                h.btnProfit.setBackgroundResource(R.drawable.orange_cut_conner);
                h.btnProfit.setText("ขาดทุน");
            }else{
                h.btnProfit.setBackgroundResource(R.drawable.green_cut_conner);
                h.btnProfit.setText("กำไร");
            }

            h.labelProfit.setText(calResult);
            h.labelAddress.setText(respBody.getPlotLocation());
            h.labelPlotSize.setText(respBody.getPlotSize());
            h.labelDate.setText(ServiceInstance.formatStrDate(respBody.getDateUpdated()));
            h.imgProduct.setImageResource( getResources().getIdentifier(ServiceInstance.productPicMap.get(respBody.getPrdID()), "drawable", getPackageName()) );
            h.labelProductName.setText(respBody.getPrdValue());


            //On Press Action
            final TextView btnd =  (TextView) convertView.findViewById(R.id.btnDeleete);
            final TextView btnc    = (TextView) convertView.findViewById(R.id.btnCopy);
            btnc.setVisibility(View.GONE);
            btnd.setVisibility(View.GONE);
              convertView.findViewById(R.id.layoutPlotRow).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("Is Onpress", "Show Button");

                    if(btnc.getVisibility() == View.GONE || btnd.getVisibility() == View.GONE) {
                        btnc.setVisibility(View.VISIBLE);
                        btnd.setVisibility(View.VISIBLE);
                    }else{
                        btnc.setVisibility(View.GONE);
                        btnd.setVisibility(View.GONE);
                    }

                    return true;
                }
            });


            //On Click Delete Action
            convertView.findViewById(R.id.btnDeleete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("On remove", "remove position : "+position);
                    API_DeletePlot(userPlotList.get(position).getPlotID());
                    removeItem(position);
                    notifyDataSetChanged();
                }
            });

            //On Click Coppy Action
            convertView.findViewById(R.id.btnCopy).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("On Copy", " position : "+position);

                    mUserPlotList.mRespBody copySource =  userPlotList.get(position);
                    mUserPlotList.mRespBody copyDestination = new mUserPlotList.mRespBody();
                    try {
                         copyDestination =(mUserPlotList.mRespBody) copySource.clone();
                    }catch (Exception e){
                        Log.d("Error",e.getMessage());
                    }


                    userPlotList.add(position+1,copyDestination);
                    notifyDataSetChanged();
                    String listSeq = "";
                    for(int i =(position+1) ; i< userPlotList.size();i++){
                           Log.d("Seq" ,"Seq     ----------->"+userPlotList.get(i).getSeqNo());

                        listSeq+=","+userPlotList.get(i).getSeqNo();
                        if(i+1 == userPlotList.size() ){
                            listSeq+=userPlotList.get(i).getSeqNo();
                        }
                        Log.d("TEST","SeqList : "+listSeq);

                        //insert and update
                    }

                }
            });

            return convertView;
        }
    }


    class Holder{
        TextView labelAddress,labelPlotSize,labelProductName,labelProfit,labelDate,btnProfit;
        ImageView imgProduct;


    }

    private void API_GetRegister(final int userId) {
          //1.UserID (บังคับใส่)

        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mGetRegister registerInfo = (mGetRegister) obj;

                List<mGetRegister.mRespBody> loginBodyLists = registerInfo.getRespBody();

                if (loginBodyLists.size() != 0) {

                    EditUserActivity.userInfoRespBody = loginBodyLists.get(0);
                    startActivity(new Intent(UserPlotListActivity.this, EditUserActivity.class));

                }

            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error",errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getRegister+
                "?UserID=" + userId );

    }

    private void API_DeletePlot(final int plotID) {
/**
  1.PlotID (บังคับใส่)
 2.ImeiCode
 */
        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mDeletePlot registerInfo = (mDeletePlot) obj;

                List<mDeletePlot.mRespBody> loginBodyLists = registerInfo.getRespBody();

                if (loginBodyLists.size() != 0) {

                }
            }
            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error",errorMsg);
            }
        }).API_Request(true, RequestServices.ws_deletePlot+
                "?PlotID=" + plotID+
                "&ImeiCode="+ServiceInstance.GetDeviceID(UserPlotListActivity.this));

    }

    private void API_updateUserPlotSeq( int userID,int seqNo) {
/**
 1.UserID
 2.SeqPlotID List ,
 */
        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mDeletePlot registerInfo = (mDeletePlot) obj;

                List<mDeletePlot.mRespBody> loginBodyLists = registerInfo.getRespBody();

                if (loginBodyLists.size() != 0) {

                }
            }
            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error",errorMsg);
            }
        }).API_Request(true, RequestServices.ws_updateUserPlotSeq+
                "?UserID=" + userID+
                "&SeqPlotID="+seqNo);

    }


    private void API_SavePlotDetail(mUserPlotList.mRespBody userPlotInfo) {

        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mSavePlotDetail savePlotDetailInfo = (mSavePlotDetail) obj;

                List<mSavePlotDetail.mRespBody> savePlotDetailBodyLists = savePlotDetailInfo.getRespBody();

                if (savePlotDetailBodyLists.size() != 0) {

                   // EditUserActivity.userInfoRespBody = loginBodyLists.get(0);
                    //startActivity(new Intent(UserPlotListActivity.this, EditUserActivity.class));
                }

            }
            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
            }
        }).API_Request(true, RequestServices.ws_savePlotDetail +

                "?SaveFlag=" + 1 +
                "&UserID=" +
                "&PlotID=" +
                "&PrdID=" + userPlotInfo.getPrdID() +
                "&PrdGrpID=" + userPlotInfo.getPrdGrpID() +
                "&PlotRai=" +
                "&PondRai=" +
                "&PondNgan=" +
                "&PondWa=" +
                "&PondMeter=" +
                "&CoopMeter=" +
                "&CoopNumber=" +
                "&TamCode=" +
                "&AmpCode=" +
                "&ProvCode=" +
                "&AnimalNumber=" + userPlotInfo.getAnimalNumberValue() +
                "&AnimalWeight=" + userPlotInfo.getAnimalWeightValue() +
                "&AnimalPrice=" + userPlotInfo.getAnimalPriceValue() +
                "&FisheryType=" +
                "&FisheryNumType=" +
                "&FisheryNumber=" + userPlotInfo.getFisheryNumberValue() +
                "&FisheryWeight=" +
                "&ImeiCode=" +
                "&VarName=" +
                "&VarValue=" +
                "&CalResult=" + userPlotInfo.getCalResult()
        );
    }



    /*

"RespStatus":{"StatusID":0,"StatusMsg":"Success"},
"RespBody":[{"PlotID":"1",
             "PrdGrpID":"1",
			 "PlantGrpID":"1",
			 "PrdID":"3",
			 "PrdValue":"ข้าวหอมมะลิ(ข้าวนาปี)","
			 PlotLocation":"ต.กง อ.กงไกรลาศ จ.จ.สุโขทัย",
			 "PlotSize":"250.00 ไร่","
			 AnimalNumberValue":"",
			 "AnimalPriceValue":"",
			 "AnimalWeightValue":"","
			 FisheryNumberValue":"",
			 "DateUpdated":"14/5/2559 14:09:11","SeqNo":"1"}


          */

}
