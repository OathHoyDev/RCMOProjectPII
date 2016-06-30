package th.co.rcmo.rcmoapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.neopixl.pixlui.components.edittext.EditText;
import com.neopixl.pixlui.components.textview.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import th.co.rcmo.rcmoapp.API.ProductService;
import th.co.rcmo.rcmoapp.API.RequestServices;
import th.co.rcmo.rcmoapp.API.ResponseAPI;
import th.co.rcmo.rcmoapp.Model.UserPlotModel;
import th.co.rcmo.rcmoapp.Model.calculate.CalculateResultModel;
import th.co.rcmo.rcmoapp.Model.calculate.FormulaIModel;
import th.co.rcmo.rcmoapp.Model.calculate.FormulaJModel;
import th.co.rcmo.rcmoapp.Module.mGetPlotDetail;
import th.co.rcmo.rcmoapp.Module.mGetVariable;
import th.co.rcmo.rcmoapp.Module.mVarPlanI;
import th.co.rcmo.rcmoapp.Util.BitMapHelper;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;
import th.co.rcmo.rcmoapp.Util.Util;
import th.co.rcmo.rcmoapp.View.DialogCalculateResult;

/**
 * Created by Taweesin on 27/6/2559.
 */
public class PBProdDetailCalculateFmentJ extends Fragment implements View.OnClickListener {
    UserPlotModel userPlotModel;
    ViewHolder h = new ViewHolder();
    View view;
    FormulaJModel formulaModel;

    int sizeType = 0;

    int customSize = 0;

    private boolean havePlotId = false;

    th.co.rcmo.rcmoapp.Module.mGetPlotDetail.mRespBody mGetPlotDetail = new mGetPlotDetail.mRespBody();

    Context context;

    boolean isCalIncludeOption = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_prod_cal_plan_j, container, false);
        context = view.getContext();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setHolder();

        setupData();

        setUI();

        //sampleData();

        return view;
    }

    private void sampleData () {

        h.group1_item_1.setText("10");
        h.group1_item_3.setText("500000");
        h.group1_item_4.setText("350");
        h.group1_item_5.setText("500");
        h.group1_item_6.setText("500");
        h.group1_item_7.setText("450");
        h.group1_item_8.setText("300");

        h.group1_item_10.setText("500");
        h.group1_item_11.setText("450");
        h.group1_item_12.setText("100");
        h.group1_item_13.setText("3000");

        h.group2_item_1.setText("150");

    }

    private void setHolder() {


        h.group1_item_1 = (EditText) view.findViewById(R.id.group1_item_1);
        h.group1_item_2 = (EditText) view.findViewById(R.id.group1_item_2);
        h.group1_item_3 = (EditText) view.findViewById(R.id.group1_item_3);
        h.group1_item_4 = (EditText) view.findViewById(R.id.group1_item_4);
        h.group1_item_5 = (EditText) view.findViewById(R.id.group1_item_5);
        h.group1_item_6 = (EditText) view.findViewById(R.id.group1_item_6);
        h.group1_item_7 = (EditText) view.findViewById(R.id.group1_item_7);
        h.group1_item_8 = (EditText) view.findViewById(R.id.group1_item_8);
        h.group1_item_9 = (TextView) view.findViewById(R.id.group1_item_9);
        h.group1_item_10 = (EditText) view.findViewById(R.id.group1_item_10);
        h.group1_item_11 = (EditText) view.findViewById(R.id.group1_item_11);
        h.group1_item_12 = (EditText) view.findViewById(R.id.group1_item_12);
        h.group1_item_13 = (EditText) view.findViewById(R.id.group1_item_13);
        h.group1_item_14 = (EditText) view.findViewById(R.id.group1_item_14);

        h.group2_item_1 = (EditText) view.findViewById(R.id.group2_item_1);
        h.group2_item_2 = (TextView) view.findViewById(R.id.group2_item_2);
        h.group2_item_3 = (TextView) view.findViewById(R.id.group2_item_3);
        h.group2_item_4 = (TextView) view.findViewById(R.id.group2_item_4);

        h.group2_3_item = (LinearLayout) view.findViewById(R.id.group2_3_item);
        h.group2_4_item = (LinearLayout) view.findViewById(R.id.group2_4_item);

        h.group3_header_check = (ImageView) view.findViewById(R.id.group3_header_check);

        h.group3_item_1 = (EditText) view.findViewById(R.id.group3_item_1);
        h.group3_item_2 = (EditText) view.findViewById(R.id.group3_item_2);
        h.group3_item_3 = (TextView) view.findViewById(R.id.group3_item_3);
        h.group3_item_4 = (EditText) view.findViewById(R.id.group3_item_4);
        h.group3_item_5 = (TextView) view.findViewById(R.id.group3_item_5);

        h.group4_header_check = (ImageView) view.findViewById(R.id.group4_header_check);

        h.group4_add_item_btn = (TextView) view.findViewById(R.id.group4_add_item_btn);

        h.group4_item_1_1 = (EditText) view.findViewById(R.id.group4_item_1_1);
        h.group4_item_1_2 = (EditText) view.findViewById(R.id.group4_item_1_2);
        h.group4_item_1_3 = (EditText) view.findViewById(R.id.group4_item_1_3);
        h.group4_item_1_4 = (TextView) view.findViewById(R.id.group4_item_1_4);
        h.delete_group4_1 = (TextView) view.findViewById(R.id.delete_group4_1);
        h.delete_group4_1.setOnClickListener(this);

        h.group4_item_2_1 = (EditText) view.findViewById(R.id.group4_item_2_1);
        h.group4_item_2_2 = (EditText) view.findViewById(R.id.group4_item_2_2);
        h.group4_item_2_3 = (EditText) view.findViewById(R.id.group4_item_2_3);
        h.group4_item_2_4 = (TextView) view.findViewById(R.id.group4_item_2_4);
        h.delete_group4_2 = (TextView) view.findViewById(R.id.delete_group4_2);
        h.delete_group4_2.setOnClickListener(this);

        h.group4_item_3_1 = (EditText) view.findViewById(R.id.group4_item_3_1);
        h.group4_item_3_2 = (EditText) view.findViewById(R.id.group4_item_3_2);
        h.group4_item_3_3 = (EditText) view.findViewById(R.id.group4_item_3_3);
        h.group4_item_3_4 = (TextView) view.findViewById(R.id.group4_item_3_4);
        h.delete_group4_3 = (TextView) view.findViewById(R.id.delete_group4_3);
        h.delete_group4_3.setOnClickListener(this);

        h.group4_item_4_1 = (EditText) view.findViewById(R.id.group4_item_4_1);
        h.group4_item_4_2 = (EditText) view.findViewById(R.id.group4_item_4_2);
        h.group4_item_4_3 = (EditText) view.findViewById(R.id.group4_item_4_3);
        h.group4_item_4_4 = (TextView) view.findViewById(R.id.group4_item_4_4);
        h.delete_group4_4 = (TextView) view.findViewById(R.id.delete_group4_4);
        h.delete_group4_4.setOnClickListener(this);

        h.group4_1_header = (LinearLayout) view.findViewById(R.id.group4_1_header);
        h.group4_2_header = (LinearLayout) view.findViewById(R.id.group4_2_header);
        h.group4_3_header = (LinearLayout) view.findViewById(R.id.group4_3_header);
        h.group4_4_header = (LinearLayout) view.findViewById(R.id.group4_4_header);


        h.productIconImg = (ImageView) view.findViewById(R.id.productIconImg);
        h.rai = (TextView) view.findViewById(R.id.rai);
        h.ngan = (TextView) view.findViewById(R.id.ngan);
        h.tarangwa = (TextView) view.findViewById(R.id.tarangwa);
        h.tarangMeter = (TextView) view.findViewById(R.id.tarangMeter);
        h.rookKung = (TextView) view.findViewById(R.id.rookKung);

        h.group1_items = (LinearLayout) view.findViewById(R.id.group1_items);
        h.group1_header = (TextView) view.findViewById(R.id.group1_header);
        h.group1_header_arrow = (ImageView) view.findViewById(R.id.group1_header_arrow);
        h.group1_header.setOnClickListener(this);

        h.group2_items = (LinearLayout) view.findViewById(R.id.group2_items);
        h.group2_header = (TextView) view.findViewById(R.id.group2_header);
        h.group2_header_arrow = (ImageView) view.findViewById(R.id.group2_header_arrow);
        h.group2_header.setOnClickListener(this);

        h.group3_items = (LinearLayout) view.findViewById(R.id.group3_items);
        h.group3_header = (TextView) view.findViewById(R.id.group3_header);
        h.group3_header_arrow = (ImageView) view.findViewById(R.id.group3_header_arrow);
        h.group3_header.setOnClickListener(this);

        h.group4_items = (LinearLayout) view.findViewById(R.id.group4_items);
        h.group4_header = (TextView) view.findViewById(R.id.group4_header);
        h.group4_header_arrow = (ImageView) view.findViewById(R.id.group4_header_arrow);
        h.group4_header.setOnClickListener(this);

        h.headerLayout = (RelativeLayout) view.findViewById(R.id.headerLayout);
        h.headerLayout.setOnClickListener(this);

        h.calBtn = (TextView) view.findViewById(R.id.calBtn);
        h.calBtn.setOnClickListener(this);


        h.btnOption = (Button) view.findViewById(R.id.btnOption);
        h.btnOption.setOnClickListener(this);

        h.group4_add_item_btn.setOnClickListener(this);

        h.group3_header_check.setOnClickListener(this);
        h.group4_header_check.setOnClickListener(this);


        h.group4_1_header.setVisibility(View.GONE);
        h.group4_2_header.setVisibility(View.GONE);
        h.group4_3_header.setVisibility(View.GONE);
        h.group4_4_header.setVisibility(View.GONE);

        h.group2_3_item.setVisibility(View.GONE);
        h.group2_4_item.setVisibility(View.GONE);
    }

    private void setupData() {

        userPlotModel = PBProductDetailActivity.userPlotModel;


        FormulaJModel aModel = new FormulaJModel();
        formulaModel = aModel;

        API_getVariable(userPlotModel.getPrdID(), userPlotModel.getFisheryType());

    }

    private void setUI() {

        String imgName = ServiceInstance.productIMGMap.get(Integer.valueOf(userPlotModel.getPrdID()));
        if (imgName != null) {
            h.productIconImg.setImageBitmap(BitMapHelper.decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier(imgName, "drawable", getActivity().getPackageName()), R.dimen.iccircle_img_width, R.dimen.iccircle_img_height));
        }
        if (!userPlotModel.getPlotID().equals("") && !userPlotModel.getPlotID().equals("0")) {
            API_getPlotDetail(userPlotModel.getPlotID());
            havePlotId = true;
        } else {
            h.rai.setText(userPlotModel.getPondRai());
            h.ngan.setText(userPlotModel.getPondNgan());
            h.tarangwa.setText(userPlotModel.getPondWa());
            //h.rookKung.setText(userPlotModel.getFisheryNumber());
        }

        if (isCalIncludeOption) {

            if (isCalIncludeOption) {
                h.btnOption.setBackgroundResource(R.drawable.radio_cal_blue_check);
            } else {
                h.btnOption.setBackgroundResource(R.drawable.radio_cal_blue);
            }

            formulaModel.isCalIncludeOption = isCalIncludeOption;
        }

    }


    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.calBtn) {

            /*
            //bindingData(formulaModel);
            formulaModel.calculate();

            //setUpCalUI(formulaModel);
            Util.showDialogAndDismiss(context,"คำนวนสำเร็จ : "+formulaModel.KumraiKadtoon);

            CalculateResultModel calculateResultModel = new CalculateResultModel();
            calculateResultModel.formularCode = "I";
            calculateResultModel.calculateResult = formulaModel.KumraiKadtoon;
            calculateResultModel.productName = userPlotModel.getPrdValue();
            calculateResultModel.mPlotSuit = PBProductDetailActivity.mPlotSuit;
            calculateResultModel.compareStdResult = 0;

            DialogCalculateResult.userPlotModel = userPlotModel;
            DialogCalculateResult.calculateResultModel = calculateResultModel;

            userPlotModel.setVarValue(ProductService.genJsonPlanVariable(formulaModel));

            List resultArrayResult = new ArrayList();

            String [] tontoonCal_1 = {"ต้นทุนทั้งหมด" , String.format("%,.2f", formulaModel.TontoonTungmod) , "บาท"};
            resultArrayResult.add(tontoonCal_1);

            String [] tontoonCal_2 = {"" , String.format("%,.2f", formulaModel.TontoonTorKilo) , "บาท/กก."};
            resultArrayResult.add(tontoonCal_2);

            String [] raydai_1 = {"" , String.format("%,.2f", formulaModel.TontoonTorRai) , "บาท/ไร่"};
            resultArrayResult.add(raydai_1);

            DialogCalculateResult.calculateResultModel.resultList = resultArrayResult;

            new DialogCalculateResult(context).Show();
            */

        }else if(v.getId() == R.id.btnOption) {

            if(isCalIncludeOption){
                h.btnOption.setBackgroundResource(R.drawable.radio_cal_blue);
                isCalIncludeOption = false;
                formulaModel.isCalIncludeOption =false;
                h.group2_3_item.setVisibility(View.GONE);
                h.group2_4_item.setVisibility(View.GONE);
            }else{
                h.btnOption.setBackgroundResource(R.drawable.radio_cal_blue_check);
                isCalIncludeOption = true;
                formulaModel.isCalIncludeOption = true;
                h.group2_3_item.setVisibility(View.VISIBLE);
                h.group2_4_item.setVisibility(View.VISIBLE);
            }

            formulaModel.isCalIncludeOption = isCalIncludeOption;

        }else if(v.getId() == R.id.group1_header){

            if(h.group1_items.getVisibility() == View.GONE ){
                h.group1_items.setVisibility(View.VISIBLE);
                h.group1_header_arrow.setImageBitmap(BitMapHelper.
                        decodeSampledBitmapFromResource(getResources(),getResources().getIdentifier("arrow_hide", "drawable", context.getPackageName()), 30, 30));
            }else{
                h.group1_header_arrow.setImageBitmap(BitMapHelper.
                        decodeSampledBitmapFromResource(getResources(),getResources().getIdentifier("arrow_show", "drawable", context.getPackageName()), 30, 30));

                h.group1_items.setVisibility(View.GONE);

            }
        }else if(v.getId() == R.id.group2_header){

            if(h.group2_items.getVisibility() == View.GONE ){
                h.group2_items.setVisibility(View.VISIBLE);
                h.group2_header_arrow.setImageBitmap(BitMapHelper.
                        decodeSampledBitmapFromResource(getResources(),getResources().getIdentifier("arrow_hide", "drawable", context.getPackageName()), 30, 30));
            }else{
                h.group2_header_arrow.setImageBitmap(BitMapHelper.
                        decodeSampledBitmapFromResource(getResources(),getResources().getIdentifier("arrow_show", "drawable", context.getPackageName()), 30, 30));

                h.group2_items.setVisibility(View.GONE);

            }
        }else if(v.getId() == R.id.group3_header){

            if(h.group3_items.getVisibility() == View.GONE ){
                h.group3_items.setVisibility(View.VISIBLE);
                h.group3_header_arrow.setImageBitmap(BitMapHelper.
                        decodeSampledBitmapFromResource(getResources(),getResources().getIdentifier("arrow_hide", "drawable", context.getPackageName()), 30, 30));
            }else{
                h.group3_header_arrow.setImageBitmap(BitMapHelper.
                        decodeSampledBitmapFromResource(getResources(),getResources().getIdentifier("arrow_show", "drawable", context.getPackageName()), 30, 30));

                h.group3_items.setVisibility(View.GONE);

            }
        }else if(v.getId() == R.id.group4_header){

            if(h.group4_items.getVisibility() == View.GONE ){
                h.group4_items.setVisibility(View.VISIBLE);
                h.group4_header_arrow.setImageBitmap(BitMapHelper.
                        decodeSampledBitmapFromResource(getResources(),getResources().getIdentifier("arrow_hide", "drawable", context.getPackageName()), 30, 30));
            }else{
                h.group4_header_arrow.setImageBitmap(BitMapHelper.
                        decodeSampledBitmapFromResource(getResources(),getResources().getIdentifier("arrow_show", "drawable", context.getPackageName()), 30, 30));

                h.group4_items.setVisibility(View.GONE);

            }
        }else if(v.getId() == R.id.group3_header_check) {
            sizeType = 1;
            h.group3_header_check.setImageBitmap(BitMapHelper.
                    decodeSampledBitmapFromResource(getResources(),getResources().getIdentifier("radio_cal_blue_check", "drawable", context.getPackageName()), 20, 20));
            h.group3_header.setBackgroundResource(R.drawable.blue_cut_top_conner);
            h.group4_header_check.setImageBitmap(BitMapHelper.
                    decodeSampledBitmapFromResource(getResources(),getResources().getIdentifier("radio_cal_blue", "drawable", context.getPackageName()), 20, 20));
            h.group4_header.setBackgroundResource(R.drawable.gray_cut_top_conner);

        }else if(v.getId() == R.id.group4_header_check) {
            sizeType = 1;
            h.group4_header_check.setImageBitmap(BitMapHelper.
                    decodeSampledBitmapFromResource(getResources(),getResources().getIdentifier("radio_cal_blue_check", "drawable", context.getPackageName()), 20, 20));
            h.group4_header.setBackgroundResource(R.drawable.blue_cut_top_conner);
            h.group3_header_check.setImageBitmap(BitMapHelper.
                    decodeSampledBitmapFromResource(getResources(),getResources().getIdentifier("radio_cal_blue", "drawable", context.getPackageName()), 20, 20));
            h.group3_header.setBackgroundResource(R.drawable.gray_cut_top_conner);

        }else if(v.getId() == R.id.delete_group4_1){

            switch (customSize){
                case 1:

                    h.group4_1_header.setVisibility(View.GONE);

                    h.group4_item_1_1.setText("");
                    h.group4_item_1_2.setText("");
                    h.group4_item_1_3.setText("");
                    h.group4_item_1_4.setText("");

                    customSize = 0;
                    break;
                case 2:

                    h.group4_item_1_1.setText(h.group4_item_2_1.getText());
                    h.group4_item_1_2.setText(h.group4_item_2_2.getText());
                    h.group4_item_1_3.setText(h.group4_item_2_3.getText());
                    h.group4_item_1_4.setText(h.group4_item_2_4.getText());

                    h.group4_item_2_1.setText("");
                    h.group4_item_2_2.setText("");
                    h.group4_item_2_3.setText("");
                    h.group4_item_2_4.setText("");

                    h.group4_2_header.setVisibility(View.GONE);
                    customSize = 1;
                    break;
                case 3:

                    h.group4_item_1_1.setText(h.group4_item_2_1.getText());
                    h.group4_item_1_2.setText(h.group4_item_2_2.getText());
                    h.group4_item_1_3.setText(h.group4_item_2_3.getText());
                    h.group4_item_1_4.setText(h.group4_item_2_4.getText());

                    h.group4_item_2_1.setText(h.group4_item_3_1.getText());
                    h.group4_item_2_2.setText(h.group4_item_3_2.getText());
                    h.group4_item_2_3.setText(h.group4_item_3_3.getText());
                    h.group4_item_2_4.setText(h.group4_item_3_4.getText());

                    h.group4_item_3_1.setText("");
                    h.group4_item_3_2.setText("");
                    h.group4_item_3_3.setText("");
                    h.group4_item_3_4.setText("");

                    customSize = 2;

                    h.group4_3_header.setVisibility(View.GONE);

                    break;

                case 4:

                    h.group4_item_1_1.setText(h.group4_item_2_1.getText());
                    h.group4_item_1_2.setText(h.group4_item_2_2.getText());
                    h.group4_item_1_3.setText(h.group4_item_2_3.getText());
                    h.group4_item_1_4.setText(h.group4_item_2_4.getText());

                    h.group4_item_2_1.setText(h.group4_item_3_1.getText());
                    h.group4_item_2_2.setText(h.group4_item_3_2.getText());
                    h.group4_item_2_3.setText(h.group4_item_3_3.getText());
                    h.group4_item_2_4.setText(h.group4_item_3_4.getText());

                    h.group4_item_3_1.setText(h.group4_item_4_1.getText());
                    h.group4_item_3_2.setText(h.group4_item_4_2.getText());
                    h.group4_item_3_3.setText(h.group4_item_4_3.getText());
                    h.group4_item_3_4.setText(h.group4_item_4_4.getText());

                    h.group4_item_4_1.setText("");
                    h.group4_item_4_2.setText("");
                    h.group4_item_4_3.setText("");
                    h.group4_item_4_4.setText("");

                    customSize = 3;

                    h.group4_4_header.setVisibility(View.GONE);

                    break;
            }
        }else if(v.getId() == R.id.delete_group4_2){

            switch (customSize){
                case 2:

                    h.group4_item_2_1.setText("");
                    h.group4_item_2_2.setText("");
                    h.group4_item_2_3.setText("");
                    h.group4_item_2_4.setText("");

                    h.group4_2_header.setVisibility(View.GONE);

                    customSize = 1;
                    break;

                case 3:

                    h.group4_item_2_1.setText(h.group4_item_3_1.getText());
                    h.group4_item_2_2.setText(h.group4_item_3_2.getText());
                    h.group4_item_2_3.setText(h.group4_item_3_3.getText());
                    h.group4_item_2_4.setText(h.group4_item_3_4.getText());

                    h.group4_item_3_1.setText("");
                    h.group4_item_3_2.setText("");
                    h.group4_item_3_3.setText("");
                    h.group4_item_3_4.setText("");

                    h.group4_3_header.setVisibility(View.GONE);

                    customSize = 2;

                    break;

                case 4:

                    h.group4_item_2_1.setText(h.group4_item_3_1.getText());
                    h.group4_item_2_2.setText(h.group4_item_3_2.getText());
                    h.group4_item_2_3.setText(h.group4_item_3_3.getText());
                    h.group4_item_2_4.setText(h.group4_item_3_4.getText());

                    h.group4_item_3_1.setText(h.group4_item_4_1.getText());
                    h.group4_item_3_2.setText(h.group4_item_4_2.getText());
                    h.group4_item_3_3.setText(h.group4_item_4_3.getText());
                    h.group4_item_3_4.setText(h.group4_item_4_4.getText());

                    h.group4_item_4_1.setText("");
                    h.group4_item_4_2.setText("");
                    h.group4_item_4_3.setText("");
                    h.group4_item_4_4.setText("");

                    customSize = 3;

                    h.group4_4_header.setVisibility(View.GONE);

                    break;
            }

        }else if(v.getId() == R.id.delete_group4_3){

            if (customSize == 3) {

                h.group4_item_3_1.setText("");
                h.group4_item_3_2.setText("");
                h.group4_item_3_3.setText("");
                h.group4_item_3_4.setText("");

                h.group4_3_header.setVisibility(View.GONE);

                customSize = 2;

            }else {

                h.group4_item_3_1.setText(h.group4_item_4_1.getText());
                h.group4_item_3_2.setText(h.group4_item_4_2.getText());
                h.group4_item_3_3.setText(h.group4_item_4_3.getText());
                h.group4_item_3_4.setText(h.group4_item_4_4.getText());

                h.group4_item_4_1.setText("");
                h.group4_item_4_2.setText("");
                h.group4_item_4_3.setText("");
                h.group4_item_4_4.setText("");

                customSize = 3;

                h.group4_4_header.setVisibility(View.GONE);
            }

        }else if(v.getId() == R.id.delete_group4_4){

            customSize = 3;

            h.group4_4_header.setVisibility(View.GONE);

            h.group4_item_4_1.setText("");
            h.group4_item_4_2.setText("");
            h.group4_item_4_3.setText("");
            h.group4_item_4_4.setText("");

        }else if (v.getId() == R.id.group4_add_item_btn){

            if (customSize <= 4) {

                switch (customSize) {
                    case 0:
                        h.group4_1_header.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        h.group4_2_header.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        h.group4_3_header.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        h.group4_4_header.setVisibility(View.VISIBLE);
                        break;

                }

                customSize += 1;
            }

        }

    }

    private void deleteCustomSize(int size){
        if(size < customSize){
            int difSize = customSize - size;


        }
    }

    private void initVariableDataFromDB() {
        //API_getPlotDetailANDBlinding(userPlotModel.getPlotID(), formulaModel);
    }

    private void setUpCalUI(FormulaIModel aModel){
        h.group2_item_3.setText(Util.dobbleToStringNumber(aModel.KaRang));
        h.group2_item_4.setText(Util.dobbleToStringNumber(aModel.KaSiaOkardLongtoon));
    }

    private void bindingData(FormulaJModel aModel) {
        //  aModel.KaRang = 10;

        /*

        aModel.Rai = Util.strToDoubleDefaultZero(h.rai.getText().toString());
        aModel.Ngan = Util.strToDoubleDefaultZero(h.ngan.getText().toString());
        aModel.TarangWa = Util.strToDoubleDefaultZero(h.tarangwa.getText().toString());
        aModel.TarangMeter = Util.strToDoubleDefaultZero(h.tarangwa.getText().toString());
        aModel.RookPla = Util.strToDoubleDefaultZero(h.rookKung.getText().toString());

        aModel.Raka = Util.strToDoubleDefaultZero(h.group1_item_1.getText().toString());

        aModel.KaAHan = Util.strToDoubleDefaultZero(h.group1_item_3.getText().toString());
        aModel.KaYa = Util.strToDoubleDefaultZero(h.group1_item_4.getText().toString());

        aModel.KaSankemee = Util.strToDoubleDefaultZero(h.group1_item_5.getText().toString());
        aModel.KaNamMan = Util.strToDoubleDefaultZero(h.group1_item_6.getText().toString());
        aModel.KaFai = Util.strToDoubleDefaultZero(h.group1_item_7.getText().toString());
        aModel.KaRokRain = Util.strToDoubleDefaultZero(h.group1_item_8.getText().toString());

        aModel.KaLeang = Util.strToDoubleDefaultZero(h.group1_item_10.getText().toString());
        aModel.KaJub = Util.strToDoubleDefaultZero(h.group1_item_11.getText().toString());
        aModel.KaSomsamOuppakorn = Util.strToDoubleDefaultZero(h.group1_item_12.getText().toString());
        aModel.KaChaiJay = Util.strToDoubleDefaultZero(h.group1_item_13.getText().toString());
        aModel.KaChaoTDin = Util.strToDoubleDefaultZero(h.group1_item_14.getText().toString());

        aModel.PonPalidKung = Util.strToDoubleDefaultZero(h.group2_item_1.getText().toString());
        aModel.RakaChalia = Util.strToDoubleDefaultZero(h.group2_item_2.getText().toString());
        aModel.RayDaiTungmod = Util.strToDoubleDefaultZero(h.group2_item_3.getText().toString());
        aModel.RayDaiChalia = Util.strToDoubleDefaultZero(h.group2_item_4.getText().toString());
        //aModel.RayaWelaTeeLeang = Util.strToDoubleDefaultZero(h.group2_item_5.getText().toString());

        */

    }

    static class ViewHolder {

        // Header
        private TextView rai, ngan, tarangwa, tarangMeter , rookKung;
        private ImageView productIconImg;

        // Group 1
        private EditText group1_item_1, group1_item_2, group1_item_3, group1_item_4, group1_item_5, group1_item_6, group1_item_7, group1_item_8;
        private TextView group1_item_9;
        private EditText group1_item_10, group1_item_11, group1_item_12, group1_item_13 , group1_item_14;

        // Group 2
        private EditText group2_item_1;
        private TextView group2_item_2;
        private TextView group2_item_3;
        private TextView group2_item_4;

        private LinearLayout group2_3_item, group2_4_item;

        // Group 3
        private EditText group3_item_1, group3_item_2,group3_item_4;
        private TextView group3_item_3, group3_item_5;
        private ImageView group3_header_check;

        // Group 4
        private TextView group4_add_item_btn;
        private  ImageView group4_header_check;

        private EditText group4_item_1_1 , group4_item_1_2 , group4_item_1_3;
        private TextView group4_item_1_4;
        private TextView delete_group4_1;

        private EditText group4_item_2_1 , group4_item_2_2 , group4_item_2_3;
        private TextView group4_item_2_4;
        private TextView delete_group4_2;

        private EditText group4_item_3_1 , group4_item_3_2 , group4_item_3_3;
        private TextView group4_item_3_4;
        private TextView delete_group4_3;

        private EditText group4_item_4_1 , group4_item_4_2 , group4_item_4_3;
        private TextView group4_item_4_4;
        private TextView delete_group4_4;

        private LinearLayout group4_1_header , group4_2_header , group4_3_header , group4_4_header;



        private TextView calBtn, group1_header, group2_header , group3_header , group4_header;

        private LinearLayout group1_items, group2_items , group3_items , group4_items;

        private ImageView group1_header_arrow, group2_header_arrow , group3_header_arrow , group4_header_arrow;

        private Button btnOption;

        private RelativeLayout headerLayout;
    }

    private void API_getPlotDetail(String plodID) {
        /**
         1.TamCode (ไม่บังคับใส่)
         2.AmpCode (บังคับใส่)
         3.ProvCode (บังคับใส่)
         */
        new ResponseAPI(context, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mGetPlotDetail mPlotDetail = (mGetPlotDetail) obj;
                List<mGetPlotDetail.mRespBody> mPlotDetailBodyLists = mPlotDetail.getRespBody();

                if (mPlotDetailBodyLists.size() != 0) {
                    mGetPlotDetail = mPlotDetailBodyLists.get(0);
                }

            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getPlotDetail +
                "?PlotID=" + plodID +
                "&ImeiCode=" + ServiceInstance.GetDeviceID(context));

    }

    private void API_getVariable(String prdID, final String fisheryType) {

        new ResponseAPI(context, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mGetVariable mVariable = (mGetVariable) obj;
                List<mGetVariable.mRespBody> mVariableBodyLists = mVariable.getRespBody();

                if (mVariableBodyLists.size() != 0) {
                    mGetVariable.mRespBody var = mVariableBodyLists.get(0);
                    //formulaModel.KaSermOuppakorn = Util.strToDoubleDefaultZero(var.getDP());
                    //formulaModel.KaSiaOkardOuppakorn = Util.strToDoubleDefaultZero(var.getOP());
                    //h.group2_item_3.setText(String.valueOf(formulaModel.KaSermOuppakorn));
                    //h.group2_item_4.setText(String.valueOf(formulaModel.KaSiaOkardOuppakorn));

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

    private void API_getPlotDetailANDBlinding(String plotID, final FormulaIModel aModel) {
        /**
         1.TamCode (ไม่บังคับใส่)
         2.AmpCode (บังคับใส่)
         3.ProvCode (บังคับใส่)
         */
        new ResponseAPI(context, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mGetPlotDetail mPlotDetail = (mGetPlotDetail) obj;
                List<mGetPlotDetail.mRespBody> mPlotDetailBodyLists = mPlotDetail.getRespBody();

                if (mPlotDetailBodyLists.size() != 0) {
                    mGetPlotDetail.mRespBody plotDetail = mPlotDetailBodyLists.get(0);
                    if (!plotDetail.getVarValue().equals("")) {
                        mVarPlanI varI = new Gson().fromJson(plotDetail.getVarValue(), mVarPlanI.class);

                        aModel.Rai = varI.getRai();
                        aModel.Ngan = varI.getNgan();
                        aModel.TarangWa = varI.getTarangWa();

                        aModel.rookKung = varI.getRookKung();

                        aModel.RakaTuaLa = varI.getRakaTuaLa();
                        aModel.KaAHan = varI.getKaAHan();
                        aModel.KaYa = varI.getKaYa();
                        aModel.KaSankemee = varI.getKaSankemee();
                        aModel.KaNamMan = varI.getKaNamMan();
                        aModel.KaFai = varI.getKaFai();
                        aModel.KaRokRain = varI.getKaRokRain();
                        aModel.KaLeang = varI.getKaLeang();
                        aModel.KaJub = varI.getKaJub();
                        aModel.KaSomsamOuppakorn = varI.getKaSomsamOuppakorn();
                        aModel.KaChaiJay = varI.getKaChaiJay();
                        aModel.KaChaoTDin = varI.getKaChaoTDin();

                        aModel.PonPalidKung = varI.getPonPalidKung();
                        aModel.RakaChalia = varI.getRakaChalia();
                        aModel.RayaWelaTeeLeang = varI.getRayaWelaTeeLeang();

                        aModel.KaSermOuppakorn = varI.getKaSermOuppakorn();
                        aModel.KaSiaOkardOuppakorn = varI.getKaSiaOkardOuppakorn();

                        h.rai.setText(Util.dobbleToStringNumber(varI.Rai));
                        h.ngan.setText(Util.dobbleToStringNumber(varI.Ngan));
                        h.tarangwa.setText(Util.dobbleToStringNumber(varI.TarangWa));
                        h.rookKung.setText(Util.dobbleToStringNumber(varI.rookKung));

                        h.group1_item_1.setText(Util.dobbleToStringNumber(varI.RakaTuaLa));
                        h.group1_item_3.setText(Util.dobbleToStringNumber(varI.KaAHan));
                        h.group1_item_4.setText(Util.dobbleToStringNumber(varI.KaYa));
                        h.group1_item_5.setText(Util.dobbleToStringNumber(varI.KaSankemee));
                        h.group1_item_6.setText(Util.dobbleToStringNumber(varI.KaNamMan));
                        h.group1_item_7.setText(Util.dobbleToStringNumber(varI.KaFai));
                        h.group1_item_8.setText(Util.dobbleToStringNumber(varI.KaRokRain));

                        h.group1_item_10.setText(Util.dobbleToStringNumber(varI.KaLeang));
                        h.group1_item_11.setText(Util.dobbleToStringNumber(varI.KaJub));
                        h.group1_item_12.setText(Util.dobbleToStringNumber(varI.KaSomsamOuppakorn));
                        h.group1_item_13.setText(Util.dobbleToStringNumber(varI.KaChaiJay));
                        h.group1_item_14.setText(Util.dobbleToStringNumber(varI.KaChaoTDin));

                        h.group2_item_1.setText(Util.dobbleToStringNumber(varI.PonPalidKung));
                        h.group2_item_2.setText(Util.dobbleToStringNumber(varI.RakaChalia));
                        //h.group2_item_5.setText(Util.dobbleToStringNumber(varI.RayaWelaTeeLeang));


                        formulaModel.calculate();

                        //setUpCalUI(formulaModel);
                    }else{
                        h.rai.setText(plotDetail.getPondRai());
                        h.ngan.setText(plotDetail.getPondNgan());
                        h.tarangwa.setText(plotDetail.getPondWa());
                        h.rookKung.setText(plotDetail.getFisheryNumber());
                    }
                }
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getPlotDetail +
                "?PlotID=" + plotID +
                "&ImeiCode=" + ServiceInstance.GetDeviceID(context));

    }

}