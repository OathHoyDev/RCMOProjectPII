package th.co.rcmo.rcmoapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.neopixl.pixlui.components.edittext.EditText;
import com.neopixl.pixlui.components.textview.TextView;

import th.co.rcmo.rcmoapp.Model.UserPlotModel;
import th.co.rcmo.rcmoapp.Util.BitMapHelper;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;

/**
 * Created by Taweesin on 27/6/2559.
 */
public class PBProdDetailCalculateFmentC extends Fragment implements View.OnClickListener {
    UserPlotModel userPlotModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewHolder h = new ViewHolder();
        View view = inflater.inflate(R.layout.frag_prod_cal_plan_c, container, false);


        h.productIconImg = (ImageView) view.findViewById(R.id.productIconImg);
        h.txStartUnit = (TextView) view.findViewById(R.id.txStartUnit);


        userPlotModel = PBProductDetailActivity.userPlotModel;
        String imgName = ServiceInstance.productIMGMap.get(Integer.valueOf(userPlotModel.getPrdID()));
        if (imgName != null) {
            h.productIconImg.setImageBitmap(BitMapHelper.decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier(imgName, "drawable", getActivity().getPackageName()), R.dimen.iccircle_img_width, R.dimen.iccircle_img_height));
        }
        if (!userPlotModel.getPlotID().equals("") && !userPlotModel.getPlotID().equals("0")) {
            //  API_getPlotDetail(userPlotModel.getPlotID());
            //havePlotId =true;
        } else {
            h.txStartUnit.setText(userPlotModel.getPlotRai());

        }
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        return view;
    }
    @Override
    public void onClick(View v) {

    }

    static class ViewHolder {
        private TextView group1_item_1,group1_item_6,group1_item_11,group1_item_13,group1_item_14;
        private EditText group1_item_2,group1_item_3,group1_item_4,group1_item_5,group1_item_7,group1_item_8,group1_item_9,group1_item_10,group1_item_12;

        private EditText group2_item_1;
        private EditText group3_item_1;
        private EditText group4_item_1;

        private ImageView productIconImg;

        private TextView  txStartUnit,calBtn,group1_header,group2_header,group3_header,group4_header;

        private LinearLayout group1_items
                ,group2_items
                ,group3_items
                ,group4_items;

        private ImageView group1_header_arrow
                ,group2_header_arrow
                ,group3_header_arrow
                ,group4_header_arrow;

        private Button btnOption;

        private RelativeLayout headerLayout;
    }
}