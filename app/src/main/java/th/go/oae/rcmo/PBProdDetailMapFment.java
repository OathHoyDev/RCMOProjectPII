package th.go.oae.rcmo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import th.go.oae.rcmo.Model.UserPlotModel;

public class PBProdDetailMapFment extends Fragment implements  View.OnClickListener{
    public PBProdDetailMapFment() {
    }
    ViewHolder holder = new ViewHolder();
    static UserPlotModel userPlotModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.pbprod_datail_map_fment, container, false);

        userPlotModel = PBProductDetailActivity.userPlotModel;

        holder.suggessBottomBG  = (RelativeLayout) view.findViewById(R.id.suggessBottomBG);
        holder.fadeLayout       = (FrameLayout) view.findViewById(R.id.fadeLayout);

      //  holder.recalBtn.setOnClickListener(this);
       // holder.regetlBtn.setOnClickListener(this);
        setUi();


        return view;
    }


    private void setUi(){
        //if(holder.fadeLayout.isShown()){
            holder.fadeLayout.setVisibility(View.GONE);
        //}
        int groupId = Integer.valueOf(userPlotModel.getPrdGrpID());
        if(groupId == 1){
            holder.suggessBottomBG.setBackgroundResource(R.color.RcmoPlantTranBG);

        }else if(groupId == 2){
            holder.suggessBottomBG.setBackgroundResource(R.color.RcmoAnimalTranBG);
        }else{
            holder.suggessBottomBG.setBackgroundResource(R.color.RcmoFishTranBG);
            //fadeLayout
        }
    }

    @Override
    public void onClick(View v) {

    }


    static class ViewHolder {
        RelativeLayout suggessBottomBG;
        FrameLayout fadeLayout;

    }
}
