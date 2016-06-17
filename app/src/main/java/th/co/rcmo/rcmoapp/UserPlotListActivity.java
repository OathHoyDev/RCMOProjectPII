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
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.neopixl.pixlui.components.textview.TextView;
import java.util.ArrayList;
import java.util.List;
import th.co.rcmo.rcmoapp.API.RequestServices;
import th.co.rcmo.rcmoapp.API.ResponseAPI;
import th.co.rcmo.rcmoapp.Module.mCopyPlot;
import th.co.rcmo.rcmoapp.Module.mDeletePlot;
import th.co.rcmo.rcmoapp.Module.mGetRegister;
import th.co.rcmo.rcmoapp.Module.mUpdateUserPlotSeq;
import th.co.rcmo.rcmoapp.Module.mUserPlotList;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;
import th.co.rcmo.rcmoapp.View.DialogChoice;

public class UserPlotListActivity extends Activity {
    ListView  userPlotListView;
    String TAG = "UserPlotListActivity_TAG";
    public static List<mUserPlotList.mRespBody> userPlotRespBodyList = new ArrayList<>();
    Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_plot_list);

        setUI();
        setAction();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            new DialogChoice(UserPlotListActivity.this, new DialogChoice.OnSelectChoiceListener() {
                @Override
                public void OnSelect(int choice) {
                    if(choice == DialogChoice.OK){
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
            }).ShowTwoChoice("","คุณต้องการออกจากแอพลิเคชั่นหรือไม่?");
        }
        return false;
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

            ViewHolder h = new ViewHolder();

            if(convertView==null){
                LayoutInflater inflater = UserPlotListActivity.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.row_user_plot, parent, false);
                h.labelProductName =  (TextView) convertView.findViewById(R.id.labelProductName);
                h.labelAddress     =  (TextView) convertView.findViewById(R.id.labelAddress);
                h.labelPlotSize    =  (TextView) convertView.findViewById(R.id.labelPlotSize);
                h.labelDate        =  (TextView) convertView.findViewById(R.id.labelDate);
                h.btnProfit        =  (TextView) convertView.findViewById(R.id.btnProfit);
                h.labelProfit      =  (TextView) convertView.findViewById(R.id.labelProfit);
                h.prodImg          =  (ImageView) convertView.findViewById(R.id.prodImg);
                h.prodBg           =  (LinearLayout)convertView.findViewById(R.id.gridDrawBg);
                h.btnDelete       =  (TextView) convertView.findViewById(R.id.btnDeleete);
                h.btnCopy          =  (TextView) convertView.findViewById(R.id.btnCopy);
                h.layoutPlotRow    =  (LinearLayout) convertView.findViewById(R.id.layoutPlotRow);
                convertView.setTag(h);
            }else{
                h = (ViewHolder) convertView.getTag();
            }




            mUserPlotList.mRespBody  respBody =  getItem(position);



            /**
             * Get Layout Obj
             */
           // h.imgProduct = (ImageView) convertView.findViewById(R.id.imgProduct);


            /**
             * Set UI Display value
             */
           if(respBody.getPrdGrpID() == 2){
               h.labelProductName.setTextColor(Color.parseColor("#d5444f"));
               h.prodBg.setBackgroundResource(R.drawable.animal_ic_circle_bg);
           }else if (respBody.getPrdGrpID() == 3){
               h.labelProductName.setTextColor (Color.parseColor("#00b4ed"));
               h.prodBg.setBackgroundResource(R.drawable.fish_ic_circle_bg);
           }else{
               h.labelProductName.setTextColor (Color.parseColor("#4cb748"));
               h.prodBg.setBackgroundResource(R.drawable.plant_ic_circle_bg);
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


            //h.imgProduct.setImageResource( getResources().getIdentifier(ServiceInstance.productPicMap.get(respBody.getPrdID()), "drawable", getPackageName()) );
            String imgName = ServiceInstance.productIMGMap.get(respBody.getPrdID());
            if(imgName!=null) {
                h.prodImg.setImageResource(getResources().getIdentifier(imgName, "drawable", getPackageName()));
            }



            h.labelProductName.setText(respBody.getPrdValue());


            //On Press Action
          //  final TextView btnd =  (TextView) convertView.findViewById(R.id.btnDeleete);
           // final TextView btnc    = (TextView) convertView.findViewById(R.id.btnCopy);
            final  TextView btnd =  h.btnDelete;
            final  TextView btnc = h.btnCopy;
            btnc.setVisibility(View.GONE);
            btnd.setVisibility(View.GONE);


              h.layoutPlotRow.setOnLongClickListener(new View.OnLongClickListener() {
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
            h.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("On remove", "remove position : "+position);



                    new DialogChoice(UserPlotListActivity.this, new DialogChoice.OnSelectChoiceListener() {
                        @Override
                        public void OnSelect(int choice) {

                            if (choice == DialogChoice.OK) {
                                API_DeletePlot(userPlotList.get(position).getPlotID());
                                removeItem(position);
                                notifyDataSetChanged();
                            }
                        }
                    }).ShowTwoChoice("", "ยืนยันการลบข้อมูล");



                }
            });

            //On Click Coppy Action
            SharedPreferences sp = getSharedPreferences(ServiceInstance.PREF_NAME, Context.MODE_PRIVATE);
            final String userId = sp.getString(ServiceInstance.sp_userId, "0");
            h.btnCopy.setOnClickListener(new View.OnClickListener() {
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

                    int addPosition = position+1;
                    userPlotList.add(addPosition,copyDestination);
                    notifyDataSetChanged();

                    API_CopyPlot( userId , String.valueOf(copySource.getPlotID()),addPosition,userPlotList);
                    Log.d(TAG,"API_CopyPlot Complete" );



                }
            });

            return convertView;
        }
    }

    static class ViewHolder {
        private  TextView labelAddress,labelPlotSize,labelProductName,labelProfit,labelDate,btnProfit,btnDelete,btnCopy;
        private  ImageView imgProduct,prodImg;
        private  LinearLayout prodBg,layoutPlotRow ;


    }
/*============================== API ================================*/

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


            }
            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error",errorMsg);
            }
        }).API_Request(true, RequestServices.ws_deletePlot+
                "?PlotID=" + plotID+
                "&ImeiCode="+ServiceInstance.GetDeviceID(UserPlotListActivity.this));

    }

    private void API_updateUserPlotSeq( String userID,String seqNo) {
/**
 1.UserID
 2.SeqPlotID List ,
 */
        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mUpdateUserPlotSeq updateUserPlotseq = (mUpdateUserPlotSeq) obj;

                List<mUpdateUserPlotSeq.mRespBody> updPlotSeqBodyLists = updateUserPlotseq.getRespBody();
            //    if (updPlotSeqBodyLists.size() != 0) {
                    Log.d(TAG,"API_updateUserPlotSeq Complete ");
                    toastMsg("คัดลอกข้อมูลสำเร็จ");
              //  }

            }
            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error",errorMsg);
            }
        }).API_Request(true, RequestServices.ws_updateUserPlotSeq+
                "?UserID=" + userID+
                "&SeqPlotID="+seqNo);

    }

    private void API_CopyPlot(final String userId, final String plotId,final int addposition,final List<mUserPlotList.mRespBody> userPlotList ) {

        /**
         * 1.UserID
         2.PlotID
         */
        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mCopyPlot copyPlot = (mCopyPlot) obj;
                List<mCopyPlot.mRespBody> copyPlotBodyLists = copyPlot.getRespBody();

                if (copyPlotBodyLists.size() != 0) {
                    userPlotList.get(addposition).setPlotID(copyPlotBodyLists.get(0).getPlotID());


                    String listSeq = "";
                    for(int i =0 ; i< userPlotList.size();i++){
                        listSeq+=","+userPlotList.get(i).getPlotID();

                    }
                    if(listSeq!=null && listSeq.length()>0) {
                        listSeq = listSeq.substring(1, listSeq.length());
                    }

                    API_updateUserPlotSeq(userId,listSeq);


                }

            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
            }
        }).API_Request(true, RequestServices.ws_copyPlot +
                "?UserID=" + userId +
                "&PlotID=" + plotId
        );
    }

    private void toastMsg(String msg) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);

        TextView text = (TextView) layout.findViewById(R.id.toast_label);
        text.setText(msg);

        toast.show();
    }





}
