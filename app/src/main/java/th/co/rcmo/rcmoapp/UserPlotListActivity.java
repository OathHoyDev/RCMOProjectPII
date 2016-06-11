package th.co.rcmo.rcmoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.neopixl.pixlui.components.textview.TextView;

import java.util.ArrayList;
import java.util.List;

import th.co.rcmo.rcmoapp.Module.mUserPlotList;

public class UserPlotListActivity extends Activity {
    ListView  userPlotListView;
    public static List<mUserPlotList.mRespBody> userPlotList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_plot_list);

        userPlotList.add(new mUserPlotList.mRespBody() );
        userPlotList.add(new mUserPlotList.mRespBody() );
        userPlotList.add(new mUserPlotList.mRespBody() );

        userPlotListView = (ListView) findViewById(R.id.listviewPlotUser);

        userPlotListView.setAdapter(new UserPlotAdapter(userPlotList));
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

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                LayoutInflater inflater = UserPlotListActivity.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.row_user_plot, parent, false);
            }

            Holder h = new Holder();

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
/*
            h.btn_detail = (ImageView) convertView.findViewById(R.id.btn_detail);
            h.name = (TextView) convertView.findViewById(R.id.name);
            h.nametype = (TextView) convertView.findViewById(R.id.nametype);
            h.date = (TextView) convertView.findViewById(R.id.date);
            h.icon_alert = (ImageView) convertView.findViewById(R.id.icon_alert);
            h.layout_options = (FrameLayout) convertView.findViewById(R.id.layout_options);

            h.nametype.setText(getItem(position).getEmployerName());
            h.name.setText(getItem(position).getJobPosition());

            String[] announceArr = getItem(position).getAnnounceUpdate().split(" ");
            Log.i("", "announceArr : " + announceArr.length + "//" + getItem(position).getAnnounceUpdate());

            h.date.setText(getItem(position).getAnnounceUpdate());

            h.date.setVisibility(View.VISIBLE);
            h.icon_alert.setVisibility(View.INVISIBLE);

            h.btn_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ResultSearchActivity.this,ProfileJobActivity.class)
                            .putExtra("JobAnnounceID",getItem(position).getJobAnnounceID()));
                    finish();
                }
            });
            */

            return convertView;
        }
    }


    class Holder{
        TextView labelAddress,labelUnitCount,labelUnit,labelProductName,labelProfit,labelDate,btnProfit,btnDeleete,btnCopy;
        ImageView imgProduct;


    }
}
