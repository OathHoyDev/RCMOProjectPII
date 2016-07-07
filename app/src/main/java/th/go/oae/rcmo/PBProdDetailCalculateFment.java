package th.go.oae.rcmo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neopixl.pixlui.components.textview.TextView;

import th.go.oae.rcmo.Model.UserPlotModel;

public class PBProdDetailCalculateFment extends Fragment implements  View.OnClickListener {
    ViewHolder holder = new ViewHolder();
    static UserPlotModel userPlotModel;


    public PBProdDetailCalculateFment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.pbprod_datail_calculate__fment, container, false);

        userPlotModel = PBProductDetailActivity.userPlotModel;

        ((TextView) view.findViewById(R.id.showTest)).setText("การคำนวน Plan "+userPlotModel.getFormularCode()+" อยู่ระหว่างพัฒนา");
      //  holder.centerImg  = (TextView) view.findViewById(R.id.centerImg);

      //  holder.calBtn.setOnClickListener(this);

        setUi();
        return view;
    }

    private void setUi(){
        /*
        int groupId = Integer.valueOf(userPlotModel.getPrdGrpID());
        if(groupId == 1){
            holder.calBtn.setBackgroundResource(R.drawable.action_plant_reget);
            holder.centerImg.setBackgroundResource(R.drawable.bottom_green);
        }else if(groupId == 2){
            holder.calBtn.setBackgroundResource(R.drawable.action_animal_reget);
            holder.centerImg.setBackgroundResource(R.drawable.bottom_pink);
        }else{
            holder.calBtn.setBackgroundResource(R.drawable.action_fish_reget);
            holder.centerImg.setBackgroundResource(R.drawable.bottom_blue);

        }
        */
    }

    @Override
    public void onClick(View v) {
        /*
        if (v.getId() == R.id.calBtn) {
            Toast toast = Toast.makeText( v.getContext(), "onClick คำนวน", Toast.LENGTH_SHORT);
            toast.show();
        }
        */
    }

    static class ViewHolder {
        TextView calBtn,centerImg;
    }


}
