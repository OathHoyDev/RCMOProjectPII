package th.co.rcmo.rcmoapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import com.neopixl.pixlui.components.textview.TextView;
import java.util.ArrayList;
import java.util.List;

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
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserPlotListActivity.this, EditUserActivity.class));
              /*  startActivity(new Intent(LoginActivity.this, WebActivity.class)
                        .putExtra("link", "http://www.google.co.th/"));
                        */
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
                    removeItem(position);
                    notifyDataSetChanged();

                }
            });

            //On Click Coppy Action
            convertView.findViewById(R.id.btnCopy).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("On Copy", " position : "+position);


                }
            });



            return convertView;
        }
    }


    class Holder{
        TextView labelAddress,labelPlotSize,labelProductName,labelProfit,labelDate,btnProfit;
        ImageView imgProduct;


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
