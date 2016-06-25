package th.co.rcmo.rcmoapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.neopixl.pixlui.components.button.Button;
import com.neopixl.pixlui.components.textview.TextView;

import th.co.rcmo.rcmoapp.Model.UserPlotModel;


public class PBProdDetailStandradFment extends Fragment implements  View.OnClickListener {
    ViewHolder holder = new ViewHolder();
    static UserPlotModel userPlotModel;
    public PBProdDetailStandradFment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pbprod_datail_standrad_fment, container, false);

        userPlotModel = PBProductDetailActivity.userPlotModel;



        holder.recalBtn  = (TextView) view.findViewById(R.id.reCalBtn);
        holder.regetlBtn = (TextView) view.findViewById(R.id.reGetBtn);
        holder.centerImg  = (TextView) view.findViewById(R.id.centerImg);



        holder.recalBtn.setOnClickListener(this);
        holder.regetlBtn.setOnClickListener(this);
        setUi();
        return view;
    }

    public void onClick(View v) {
        if (v.getId() == R.id.reCalBtn) {
            Toast toast = Toast.makeText( v.getContext(), "onClick คำนวนไหม่", Toast.LENGTH_SHORT);
            toast.show();
        }else if (v.getId() == R.id.reGetBtn){
            Toast toast = Toast.makeText( v.getContext(), "onClick ดึงข้อมูลใหม่", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void setUi(){

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



    static class ViewHolder {
        TextView recalBtn;
        TextView regetlBtn;
        TextView centerImg;

    }
}
