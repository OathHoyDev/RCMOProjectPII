package th.go.oae.rcmo;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.neopixl.pixlui.components.edittext.EditText;
import com.neopixl.pixlui.components.textview.TextView;

import java.util.ArrayList;
import java.util.List;

import th.go.oae.rcmo.API.ProductService;
import th.go.oae.rcmo.API.RequestServices;
import th.go.oae.rcmo.API.ResponseAPI;
import th.go.oae.rcmo.Model.UserPlotModel;
import th.go.oae.rcmo.Model.calculate.CalculateResultModel;
import th.go.oae.rcmo.Model.calculate.FormulaKModel;
import th.go.oae.rcmo.Module.mGetPlotDetail;
import th.go.oae.rcmo.Module.mGetVariable;
import th.go.oae.rcmo.Module.mVarPlanK;
import th.go.oae.rcmo.Util.BitMapHelper;
import th.go.oae.rcmo.Util.InputFilterMinMax;
import th.go.oae.rcmo.Util.PlanKTextWatcher;
import th.go.oae.rcmo.Util.ServiceInstance;
import th.go.oae.rcmo.Util.Util;
import th.go.oae.rcmo.View.DialogCalculateResult;
import th.go.oae.rcmo.View.DialogChoice;

/**
 * Created by Taweesin on 27/6/2559.
 */
public class PBProdDetailCalculateFmentK extends Fragment implements View.OnClickListener {

    int tuaOrKilo = 0; // 0 = จำนวนตัว , 1 = จำนวนกิโล
    int calType = 0; // ประเภทการขายปลา : 1 = คละขนาด , 2 = แยกขนาด
    int customSize = 0; // จำนวนขนาดปลา

    UserPlotModel userPlotModel;
    ViewHolder h = new ViewHolder();
    View view;
    FormulaKModel formulaModel;

    private boolean havePlotId = false;

    th.go.oae.rcmo.Module.mGetPlotDetail.mRespBody mGetPlotDetail = new mGetPlotDetail.mRespBody();

    Context context;

    boolean isCalIncludeOption = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_prod_cal_plan_k, container, false);
        context = view.getContext();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        userPlotModel = PBProductDetailActivity.userPlotModel;
        setHolder();

        FormulaKModel model = new FormulaKModel();
        formulaModel = model;

        API_getVariable(userPlotModel.getPrdID(), userPlotModel.getFisheryType());

        if (!userPlotModel.getPlotID().equals("") && !userPlotModel.getPlotID().equals("0")) {
            initVariableDataFromDB();
        }else{

            formulaModel.KanardKaChang      =  Util.strToDoubleDefaultZero( userPlotModel.getCoopMeter());
            formulaModel.KanardKaChang      =  Util.strToDoubleDefaultZero( userPlotModel.getCoopNumber());
            formulaModel.LookPla            =  Util.strToDoubleDefaultZero(userPlotModel.getFisheryNumber());
            formulaModel.TuaOrKilo =  1;
            formulaModel.calculate();
            setUpCalUI(formulaModel);
        }

        setUI();

        setAction();
        //sampleData();

        return view;
    }


    private void setHolder() {

        h.productIconImg = (ImageView) view.findViewById(R.id.productIconImg);
        h.kanardKachang = (TextView) view.findViewById(R.id.KanardKachang);
        h.jumnounKachang = (TextView) view.findViewById(R.id.JumnounKachang);

        h.lookPla = (TextView) view.findViewById(R.id.rookPla);

        h.group1_item_1 = (EditText) view.findViewById(R.id.group1_item_1);
        h.group1_item_2 = (TextView) view.findViewById(R.id.group1_item_2);
        h.group1_item_3 = (EditText) view.findViewById(R.id.group1_item_3);
        h.group1_item_4 = (EditText) view.findViewById(R.id.group1_item_4);
        h.group1_item_5 = (EditText) view.findViewById(R.id.group1_item_5);
        h.group1_item_6 = (EditText) view.findViewById(R.id.group1_item_6);
        h.group1_item_7 = (EditText) view.findViewById(R.id.group1_item_7);
        h.group1_item_8 = (TextView) view.findViewById(R.id.group1_item_8);
        h.group1_item_9 = (EditText) view.findViewById(R.id.group1_item_9);
        h.group1_item_10 = (EditText) view.findViewById(R.id.group1_item_10);
        h.group1_item_11 = (EditText) view.findViewById(R.id.group1_item_11);
        h.group1_item_12 = (EditText) view.findViewById(R.id.group1_item_12);

        h.group2_item_1 = (EditText) view.findViewById(R.id.group2_item_1);
        h.group2_item_2 = (TextView) view.findViewById(R.id.group2_item_2);
      //  h.group2_item_3 = (TextView) view.findViewById(R.id.group2_item_3);
       // h.group2_item_4 = (TextView) view.findViewById(R.id.group2_item_4);

       // h.group2_3_item = (LinearLayout) view.findViewById(R.id.group2_3_item);
      //  h.group2_4_item = (LinearLayout) view.findViewById(R.id.group2_4_item);

        h.group3_header_check = (ImageView) view.findViewById(R.id.group3_header_check);

        h.group3_item_1 = (EditText) view.findViewById(R.id.group3_item_1);
        h.group3_item_2 = (TextView) view.findViewById(R.id.group3_item_2);
        h.group3_item_3 = (EditText) view.findViewById(R.id.group3_item_3);
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

       // h.group2_3_item.setVisibility(View.GONE);
       // h.group2_4_item.setVisibility(View.GONE);
    }
    private void setAction() {

        h.lookPla.addTextChangedListener(new PlanKTextWatcher(h.lookPla, h, "calRakaPan"));
       // h.rai.addTextChangedListener(new PlanKTextWatcher(h.rai, h, "calNamnakTKai"));  calRakaPan
     //   h.ngan.addTextChangedListener(new PlanKTextWatcher(h.ngan, h, "calNamnakTKai"));
     //   h.tarangwa.addTextChangedListener(new PlanKTextWatcher(h.tarangwa, h, "calNamnakTKai"));
        h.jumnounKachang.addTextChangedListener(new PlanKTextWatcher(h.jumnounKachang, h, "calNamnakTKai,calRakaPan"));

        h.group1_item_1.addTextChangedListener(new PlanKTextWatcher(h.group1_item_1, h, "calRakaPan"));
        h.group1_item_2.addTextChangedListener(new PlanKTextWatcher(h.group1_item_2, h, "calKaSiaOkardLongtoon"));
        h.group1_item_3.addTextChangedListener(new PlanKTextWatcher(h.group1_item_3, h, "calKaSiaOkardLongtoon"));
        h.group1_item_4.addTextChangedListener(new PlanKTextWatcher(h.group1_item_4, h, "calKaSiaOkardLongtoon"));
        h.group1_item_5.addTextChangedListener(new PlanKTextWatcher(h.group1_item_5, h, "calKaSiaOkardLongtoon"));
        h.group1_item_6.addTextChangedListener(new PlanKTextWatcher(h.group1_item_6, h, "calKaSiaOkardLongtoon"));
        h.group1_item_7.addTextChangedListener(new PlanKTextWatcher(h.group1_item_7, h, "calKaSiaOkardLongtoon"));
        h.group1_item_8.addTextChangedListener(new PlanKTextWatcher(h.group1_item_8, h, "calKaSiaOkardLongtoon"));
        h.group1_item_9.addTextChangedListener(new PlanKTextWatcher(h.group1_item_9, h, "calKaRang"));
        h.group1_item_10.addTextChangedListener(new PlanKTextWatcher(h.group1_item_10, h, "calKaRang"));
        h.group1_item_11.addTextChangedListener(new PlanKTextWatcher(h.group1_item_11, h, "calKaSiaOkardLongtoon"));
        h.group1_item_12.addTextChangedListener(new PlanKTextWatcher(h.group1_item_12, h, "calKaSiaOkardLongtoon"));


        h.group2_item_1.addTextChangedListener(new PlanKTextWatcher(h.group2_item_1, h, "calKaRang"));
        h.group2_item_2.addTextChangedListener(new PlanKTextWatcher(h.group2_item_2, h, ""));
        // h.group2_item_3.addTextChangedListener(new PlanJTextWatcher(h.group2_item_3, h, ""));
        // h.group2_item_4.addTextChangedListener(new PlanJTextWatcher(h.group2_item_4, h, ""));

        h.group3_item_1.addTextChangedListener(new PlanKTextWatcher(h.group3_item_1, h, "calNamnakTKai,calRaidai"));
        h.group3_item_2.addTextChangedListener(new PlanKTextWatcher(h.group3_item_2, h, "calRaidai"));
        h.group3_item_3.addTextChangedListener(new PlanKTextWatcher(h.group3_item_3, h, "calRaidai"));
        h.group3_item_4.addTextChangedListener(new PlanKTextWatcher(h.group3_item_4, h, ""));
        h.group3_item_5.addTextChangedListener(new PlanKTextWatcher(h.group3_item_5, h, ""));

        h.group4_item_1_1.addTextChangedListener(new PlanKTextWatcher(h.group4_item_1_1, h, ""));
        h.group4_item_1_2.addTextChangedListener(new PlanKTextWatcher(h.group4_item_1_2, h, "calRakaTKai1"));
        h.group4_item_1_3.addTextChangedListener(new PlanKTextWatcher(h.group4_item_1_3, h, "calRakaTKai1"));
        h.group4_item_1_4.addTextChangedListener(new PlanKTextWatcher(h.group4_item_1_4, h, ""));

        h.group4_item_2_1.addTextChangedListener(new PlanKTextWatcher(h.group4_item_2_1, h, ""));
        h.group4_item_2_2.addTextChangedListener(new PlanKTextWatcher(h.group4_item_2_2, h, "calRakaTKai2"));
        h.group4_item_2_3.addTextChangedListener(new PlanKTextWatcher(h.group4_item_2_3, h, "calRakaTKai2"));
        h.group4_item_2_4.addTextChangedListener(new PlanKTextWatcher(h.group4_item_2_4, h, ""));

        h.group4_item_3_1.addTextChangedListener(new PlanKTextWatcher(h.group4_item_3_1, h, ""));
        h.group4_item_3_2.addTextChangedListener(new PlanKTextWatcher(h.group4_item_3_2, h, "calRakaTKai3"));
        h.group4_item_3_3.addTextChangedListener(new PlanKTextWatcher(h.group4_item_3_3, h, "calRakaTKai3"));
        h.group4_item_3_4.addTextChangedListener(new PlanKTextWatcher(h.group4_item_3_4, h, ""));

        h.group4_item_4_1.addTextChangedListener(new PlanKTextWatcher(h.group4_item_4_1, h, ""));
        h.group4_item_4_2.addTextChangedListener(new PlanKTextWatcher(h.group4_item_4_2, h, "calRakaTKai4"));
        h.group4_item_4_3.addTextChangedListener(new PlanKTextWatcher(h.group4_item_4_3, h, "calRakaTKai4"));
        h.group4_item_4_4.addTextChangedListener(new PlanKTextWatcher(h.group4_item_4_4, h, ""));


        // h.group2_item_5.addTextChangedListener(new PlanITextWatcher(h.group2_item_5, h, "calKaRang"));

        //  calAllEgg
    }

    private void setUI() {

        String imgName = ServiceInstance.productIMGMap.get(Integer.valueOf(userPlotModel.getPrdID()));
        if (imgName != null) {
            h.productIconImg.setImageBitmap(BitMapHelper.decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier(imgName, "drawable", getActivity().getPackageName()), R.dimen.iccircle_img_width, R.dimen.iccircle_img_height));
        }
        if (!userPlotModel.getPlotID().equals("") && !userPlotModel.getPlotID().equals("0")) {
            API_getPlotDetail(userPlotModel.getPlotID());
            initVariableDataFromDB();
            havePlotId = true;
        } else {
            h.kanardKachang.setText(userPlotModel.getCoopMeter());
            h.jumnounKachang.setText(userPlotModel.getCoopNumber());
            h.lookPla.setText(userPlotModel.getFisheryNumber());
        }



        if (isCalIncludeOption) {

            if (isCalIncludeOption) {
                h.btnOption.setBackgroundResource(R.drawable.radio_cal_blue_check);
            } else {
                h.btnOption.setBackgroundResource(R.drawable.radio_cal_blue);
            }

            formulaModel.isCalIncludeOption = isCalIncludeOption;
        }

        if (formulaModel.CalType == 1){
            h.group3_header_check.setImageBitmap(BitMapHelper.
                    decodeSampledBitmapFromResource(getResources(),getResources().getIdentifier("radio_cal_blue_check", "drawable", context.getPackageName()), 20, 20));
            h.group3_header.setBackgroundResource(R.drawable.blue_cut_top_conner);
            h.group4_header_check.setImageBitmap(BitMapHelper.
                    decodeSampledBitmapFromResource(getResources(),getResources().getIdentifier("radio_cal_blue", "drawable", context.getPackageName()), 20, 20));
            h.group4_header.setBackgroundResource(R.drawable.gray_cut_top_conner);

            if(h.group3_items.getVisibility() == View.GONE ){
                h.group3_items.setVisibility(View.VISIBLE);
                h.group3_header_arrow.setImageBitmap(BitMapHelper.
                        decodeSampledBitmapFromResource(getResources(),getResources().getIdentifier("arrow_hide", "drawable", context.getPackageName()), 30, 30));
            }

        }else if(formulaModel.CalType == 2){
            h.group4_header_check.setImageBitmap(BitMapHelper.
                    decodeSampledBitmapFromResource(getResources(),getResources().getIdentifier("radio_cal_blue_check", "drawable", context.getPackageName()), 20, 20));
            h.group4_header.setBackgroundResource(R.drawable.blue_cut_top_conner);
            h.group3_header_check.setImageBitmap(BitMapHelper.
                    decodeSampledBitmapFromResource(getResources(),getResources().getIdentifier("radio_cal_blue", "drawable", context.getPackageName()), 20, 20));
            h.group3_header.setBackgroundResource(R.drawable.gray_cut_top_conner);

            switch (formulaModel.CustomSize){
                case 1:
                    h.group4_1_header.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    h.group4_1_header.setVisibility(View.VISIBLE);
                    h.group4_2_header.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    h.group4_1_header.setVisibility(View.VISIBLE);
                    h.group4_2_header.setVisibility(View.VISIBLE);
                    h.group4_3_header.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    h.group4_1_header.setVisibility(View.VISIBLE);
                    h.group4_2_header.setVisibility(View.VISIBLE);
                    h.group4_3_header.setVisibility(View.VISIBLE);
                    h.group4_4_header.setVisibility(View.VISIBLE);
                    break;
            }

        }

    }


    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.calBtn) {

            if (calType == 0){

                new DialogChoice(context).ShowOneChoice("กรุณาระบุประเภทการขาย", "");

            }else {


                bindingData(formulaModel);
                formulaModel.calculate();

                setUpCalUI(formulaModel);

                CalculateResultModel calculateResultModel = new CalculateResultModel();

                if (calType == 1) {
                  //  Util.showDialogAndDismiss(context, "คำนวนสำเร็จ : " + formulaModel.KumraiKadtoonMix);
                    calculateResultModel.unit_t1 = "บาท/กระชัง" ;
                    calculateResultModel.value_t1 = formulaModel.KumraiKadtoonMixTorKaChang ;

                    calculateResultModel.unit_t2 = "บาท/กก." ;
                    calculateResultModel.value_t2 = formulaModel.KumraiKadtoonMixTorKilo ;

                    calculateResultModel.calculateResult = formulaModel.KumraiKadtoonMix;
                } else if (calType == 2) {
                  //  Util.showDialogAndDismiss(context, "คำนวนสำเร็จ : " + formulaModel.KumraiKadtoonSize);
                    calculateResultModel.unit_t1 = "บาท/กระชัง" ;
                    calculateResultModel.value_t1 = formulaModel.KumraiKadtoonSizeTorKaChang ;
                    calculateResultModel.unit_t2 = "บาท/กก." ;
                    calculateResultModel.value_t2 = formulaModel.KumraiKadtoonSizeTorKilo ;

                    calculateResultModel.calculateResult = formulaModel.KumraiKadtoonSize;
                }

                calculateResultModel.formularCode = "K";

                calculateResultModel.productName = userPlotModel.getPrdValue();
                calculateResultModel.mPlotSuit = PBProductDetailActivity.mPlotSuit;
                calculateResultModel.compareStdResult = 0;

                DialogCalculateResult.userPlotModel = userPlotModel;
                DialogCalculateResult.calculateResultModel = calculateResultModel;
                userPlotModel.setFisheryNumType("1");
                userPlotModel.setVarValue(ProductService.genJsonPlanVariable(formulaModel));

                List resultArrayResult = new ArrayList();

                if (calType == 1) {
                    String[] type = {"ขายแบบคละขนาด", "", ""};
                    resultArrayResult.add(type);
                    String[] tontoonCal_1 = {"ต้นทุนทั้งหมด", String.format("%,.2f", formulaModel.costTontoonMix), "บาท/รุ่น"};
                    resultArrayResult.add(tontoonCal_1);
                    String[] tontoonCal_2 = {"", String.format("%,.2f", formulaModel.costTontoonMixTorRai), "บาท/กระชัง"};
                    resultArrayResult.add(tontoonCal_2);
                    String[] raydai_1 = {"", String.format("%,.2f", formulaModel.costTontoonMixTorKilo), "บาท/กก."};
                    resultArrayResult.add(raydai_1);
                } else if (calType == 2) {
                    String[] type = {"ขายแบบแยกขนาด", "", ""};
                    resultArrayResult.add(type);
                    String[] tontoonCal_1 = {"ต้นทุนทั้งหมด", String.format("%,.2f", formulaModel.costTontoonSize), "บาท/รุ่น"};
                    resultArrayResult.add(tontoonCal_1);
                    String[] tontoonCal_2 = {"", String.format("%,.2f", formulaModel.costTontoonSizeTorKilo), "บาท/กระชัง"};
                    resultArrayResult.add(tontoonCal_2);
                    String[] raydai_1 = {"", String.format("%,.2f", formulaModel.costTontoonSizeTorRai), "บาท/กก."};
                    resultArrayResult.add(raydai_1);
                }


                DialogCalculateResult.calculateResultModel.resultList = resultArrayResult;

                new DialogCalculateResult(context).Show();

            }


        }else if(v.getId() == R.id.btnOption) {

            if(isCalIncludeOption){
                h.btnOption.setBackgroundResource(R.drawable.radio_cal_blue);
                isCalIncludeOption = false;
                formulaModel.isCalIncludeOption =false;
               // h.group2_3_item.setVisibility(View.GONE);
               // h.group2_4_item.setVisibility(View.GONE);
            }else{
                h.btnOption.setBackgroundResource(R.drawable.radio_cal_blue_check);
                isCalIncludeOption = true;
                formulaModel.isCalIncludeOption = true;
               // h.group2_3_item.setVisibility(View.VISIBLE);
              //  h.group2_4_item.setVisibility(View.VISIBLE);
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
            calType = 1;
            h.group3_header_check.setImageBitmap(BitMapHelper.
                    decodeSampledBitmapFromResource(getResources(),getResources().getIdentifier("radio_cal_blue_check", "drawable", context.getPackageName()), 20, 20));
            h.group3_header.setBackgroundResource(R.drawable.blue_cut_top_conner);
            h.group4_header_check.setImageBitmap(BitMapHelper.
                    decodeSampledBitmapFromResource(getResources(),getResources().getIdentifier("radio_cal_blue", "drawable", context.getPackageName()), 20, 20));
            h.group4_header.setBackgroundResource(R.drawable.gray_cut_top_conner);
            formulaModel.CalType = 1;

        }else if(v.getId() == R.id.group4_header_check) {
            calType = 2;
            h.group4_header_check.setImageBitmap(BitMapHelper.
                    decodeSampledBitmapFromResource(getResources(),getResources().getIdentifier("radio_cal_blue_check", "drawable", context.getPackageName()), 20, 20));
            h.group4_header.setBackgroundResource(R.drawable.blue_cut_top_conner);
            h.group3_header_check.setImageBitmap(BitMapHelper.
                    decodeSampledBitmapFromResource(getResources(),getResources().getIdentifier("radio_cal_blue", "drawable", context.getPackageName()), 20, 20));
            h.group3_header.setBackgroundResource(R.drawable.gray_cut_top_conner);

            formulaModel.CalType = 2;

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

        }if (v.getId() == R.id.headerLayout){

            popUpEditDialog();

        }

    }

    private void deleteCustomSize(int size){
        if(size < customSize){
            int difSize = customSize - size;


        }
    }

    private void initVariableDataFromDB() {
        API_getPlotDetailANDBlinding(userPlotModel.getPlotID(), formulaModel);
    }

    private void setUpCalUI(FormulaKModel model){
        h.group1_item_2.setText(Util.dobbleToStringNumber(model.calKaPan));
        h.group1_item_8.setText(Util.dobbleToStringNumber(model.calKaRangNgan));
        h.group2_item_2.setText(Util.dobbleToStringNumber(model.calKaSiaOkardLongtoon));


        if (model.CalType == 1){
            h.group3_header_check.setImageBitmap(BitMapHelper.
                    decodeSampledBitmapFromResource(getResources(),getResources().getIdentifier("radio_cal_blue_check", "drawable", context.getPackageName()), 20, 20));
            h.group3_header.setBackgroundResource(R.drawable.blue_cut_top_conner);
            h.group4_header_check.setImageBitmap(BitMapHelper.
                    decodeSampledBitmapFromResource(getResources(),getResources().getIdentifier("radio_cal_blue", "drawable", context.getPackageName()), 20, 20));
            h.group4_header.setBackgroundResource(R.drawable.gray_cut_top_conner);

            if(h.group3_items.getVisibility() == View.GONE ){
                h.group3_items.setVisibility(View.VISIBLE);
                h.group3_header_arrow.setImageBitmap(BitMapHelper.
                        decodeSampledBitmapFromResource(getResources(),getResources().getIdentifier("arrow_hide", "drawable", context.getPackageName()), 30, 30));
            }

            h.group3_item_2.setText(Util.dobbleToStringNumber(model.calNamnakTKai));
            h.group3_item_5.setText(Util.dobbleToStringNumber(model.calRaidai));

        }else if(model.CalType == 2){
            h.group4_header_check.setImageBitmap(BitMapHelper.
                    decodeSampledBitmapFromResource(getResources(),getResources().getIdentifier("radio_cal_blue_check", "drawable", context.getPackageName()), 20, 20));
            h.group4_header.setBackgroundResource(R.drawable.blue_cut_top_conner);
            h.group3_header_check.setImageBitmap(BitMapHelper.
                    decodeSampledBitmapFromResource(getResources(),getResources().getIdentifier("radio_cal_blue", "drawable", context.getPackageName()), 20, 20));
            h.group3_header.setBackgroundResource(R.drawable.gray_cut_top_conner);

            switch (model.CustomSize){
                case 1:
                    h.group4_1_header.setVisibility(View.VISIBLE);
                    h.group4_item_1_4.setText(Util.dobbleToStringNumber(model.calRakaTKai1));
                    break;
                case 2:
                    h.group4_1_header.setVisibility(View.VISIBLE);
                    h.group4_item_1_4.setText(Util.dobbleToStringNumber(model.calRakaTKai1));
                    h.group4_2_header.setVisibility(View.VISIBLE);
                    h.group4_item_2_4.setText(Util.dobbleToStringNumber(model.calRakaTKai2));
                    break;
                case 3:
                    h.group4_1_header.setVisibility(View.VISIBLE);
                    h.group4_item_1_4.setText(Util.dobbleToStringNumber(model.calRakaTKai1));
                    h.group4_2_header.setVisibility(View.VISIBLE);
                    h.group4_item_2_4.setText(Util.dobbleToStringNumber(model.calRakaTKai2));
                    h.group4_3_header.setVisibility(View.VISIBLE);
                    h.group4_item_3_4.setText(Util.dobbleToStringNumber(model.calRakaTKai3));
                    break;
                case 4:
                    h.group4_1_header.setVisibility(View.VISIBLE);
                    h.group4_item_1_4.setText(Util.dobbleToStringNumber(model.calRakaTKai1));
                    h.group4_2_header.setVisibility(View.VISIBLE);
                    h.group4_item_2_4.setText(Util.dobbleToStringNumber(model.calRakaTKai2));
                    h.group4_3_header.setVisibility(View.VISIBLE);
                    h.group4_item_3_4.setText(Util.dobbleToStringNumber(model.calRakaTKai3));
                    h.group4_4_header.setVisibility(View.VISIBLE);
                    h.group4_item_4_4.setText(Util.dobbleToStringNumber(model.calRakaTKai4));
                    break;
            }

        }


    }

    private void bindingData(FormulaKModel model) {

        model.KanardKaChang = Util.strToDoubleDefaultZero(h.kanardKachang.getText().toString());
        model.JumnounKachang = Util.strToDoubleDefaultZero(h.jumnounKachang.getText().toString());

        model.LookPla = Util.strToDoubleDefaultZero(h.lookPla.getText().toString());

        model.Raka = Util.strToDoubleDefaultZero(h.group1_item_1.getText().toString());
        model.KaAHan = Util.strToDoubleDefaultZero(h.group1_item_3.getText().toString());
        model.KaYa = Util.strToDoubleDefaultZero(h.group1_item_4.getText().toString());
        model.KaSanKMe = Util.strToDoubleDefaultZero(h.group1_item_5.getText().toString());
        model.KaNamman = Util.strToDoubleDefaultZero(h.group1_item_6.getText().toString());
        model.KaFaifa = Util.strToDoubleDefaultZero(h.group1_item_7.getText().toString());

        model.KaRangNganLeang = Util.strToDoubleDefaultZero(h.group1_item_9.getText().toString());
        model.KaRangNganJub = Util.strToDoubleDefaultZero(h.group1_item_10.getText().toString());
        model.KaSomSam = Util.strToDoubleDefaultZero(h.group1_item_11.getText().toString());
        model.KaChaijai = Util.strToDoubleDefaultZero(h.group1_item_12.getText().toString());

        model.RayaWela = Util.strToDoubleDefaultZero(h.group2_item_1.getText().toString());

        model.NamnakTKai = Util.strToDoubleDefaultZero(h.group3_item_1.getText().toString());
        model.RakaTKai = Util.strToDoubleDefaultZero(h.group3_item_3.getText().toString());
        model.KanardPlaChalia = Util.strToDoubleDefaultZero(h.group3_item_4.getText().toString());

        model.KanardPla1 = Util.strToDoubleDefaultZero(h.group4_item_1_1.getText().toString());
        model.RakaPla1 = Util.strToDoubleDefaultZero(h.group4_item_1_2.getText().toString());
        model.NamnakPla1 = Util.strToDoubleDefaultZero(h.group4_item_1_3.getText().toString());

        model.KanardPla2 = Util.strToDoubleDefaultZero(h.group4_item_2_1.getText().toString());
        model.RakaPla2 = Util.strToDoubleDefaultZero(h.group4_item_2_2.getText().toString());
        model.NamnakPla2 = Util.strToDoubleDefaultZero(h.group4_item_2_3.getText().toString());

        model.KanardPla3 = Util.strToDoubleDefaultZero(h.group4_item_3_1.getText().toString());
        model.RakaPla3 = Util.strToDoubleDefaultZero(h.group4_item_3_2.getText().toString());
        model.NamnakPla3 = Util.strToDoubleDefaultZero(h.group4_item_3_3.getText().toString());

        model.KanardPla4 = Util.strToDoubleDefaultZero(h.group4_item_4_1.getText().toString());
        model.RakaPla4 = Util.strToDoubleDefaultZero(h.group4_item_4_2.getText().toString());
        model.NamnakPla4 = Util.strToDoubleDefaultZero(h.group4_item_4_3.getText().toString());

    }

    public static class ViewHolder {

        // Header
        public TextView kanardKachang;
        public TextView jumnounKachang;
        public TextView lookPla;
        public ImageView productIconImg;

        // Group 1
        public EditText group1_item_1, group1_item_3, group1_item_4, group1_item_5, group1_item_6, group1_item_7;
        public TextView group1_item_8,group1_item_2;
        public EditText group1_item_9 , group1_item_10, group1_item_11, group1_item_12;

        // Group 2
        public EditText group2_item_1;
        public TextView group2_item_2;
       // public TextView group2_item_3;
       // public TextView group2_item_4;

      //  public LinearLayout group2_3_item, group2_4_item;

        // Group 3
        public EditText group3_item_1, group3_item_3 ,group3_item_4;
        public TextView group3_item_2, group3_item_5;
        public ImageView group3_header_check;

        // Group 4
        public TextView group4_add_item_btn;
        public  ImageView group4_header_check;

        public EditText group4_item_1_1 , group4_item_1_2 , group4_item_1_3;
        public TextView group4_item_1_4;
        public TextView delete_group4_1;

        public EditText group4_item_2_1 , group4_item_2_2 , group4_item_2_3;
        public TextView group4_item_2_4;
        public TextView delete_group4_2;

        public EditText group4_item_3_1 , group4_item_3_2 , group4_item_3_3;
        public TextView group4_item_3_4;
        public TextView delete_group4_3;

        public EditText group4_item_4_1 , group4_item_4_2 , group4_item_4_3;
        public TextView group4_item_4_4;
        public TextView delete_group4_4;

        public LinearLayout group4_1_header , group4_2_header , group4_3_header , group4_4_header;

        public TextView calBtn, group1_header, group2_header , group3_header , group4_header;

        public LinearLayout group1_items, group2_items , group3_items , group4_items;

        public ImageView group1_header_arrow, group2_header_arrow , group3_header_arrow , group4_header_arrow;

        public Button btnOption;

        public RelativeLayout headerLayout;
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
                    formulaModel.KaSermOuppakorn = Util.strToDoubleDefaultZero(var.getDB());
                    formulaModel.KaSiaOkardOuppakorn = Util.strToDoubleDefaultZero(var.getOB());
                   // h.group2_item_3.setText(String.valueOf(formulaModel.KaSermOuppakorn * Integer.parseInt(userPlotModel.getCoopNumber())));
                   // h.group2_item_4.setText(String.valueOf(formulaModel.KaSiaOkardOuppakorn * Integer.parseInt(userPlotModel.getCoopNumber())));
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

    private void API_getPlotDetailANDBlinding(String plotID, final FormulaKModel model) {
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

                        mVarPlanK var = new Gson().fromJson(plotDetail.getVarValue(), mVarPlanK.class);
                        calType = var.getCalType();
                        model.CalType = var.getCalType();
                        model.TuaOrKilo = var.getTuaOrKilo();
                        model.CustomSize = var.getCustomSize();

                        model.KanardKaChang = var.getKanardKachang();
                        model.JumnounKachang = var.getJumnounKachang();


                        model.LookPla = var.getLookPla();

                        model.Raka = var.getRaka();
                        model.KaAHan = var.getKaAHan();
                        model.KaRangNganLeang = var.getKaRangNganLeang();
                        model.KaRangNganJub = var.getKaRangNganJub();
                        model.KaYa = var.getKaYa();
                        model.KaSanKMe = var.getKaSanKMe();
                        model.KaNamman = var.getKaNamman();
                        model.KaFaifa = var.getKaFaifa();

                        model.KaSomSam = var.getKaSomSam();
                        model.KaChaijai = var.getKaChaijai();

                        model.RayaWela = var.getRayaWela();

                        model.NamnakTKai = var.getNamnakTKai();
                        model.RakaTKai = var.getRakaTKai();
                        model.KanardPlaChalia = var.getKanardPlaChalia();

                        model.KanardPla1 = var.getKanardPla1();
                        model.NamnakPla1 = var.getNamnakPla1();
                        model.RakaPla1 = var.getRakaPla1();

                        model.KanardPla2 = var.getKanardPla2();
                        model.NamnakPla2 = var.getNamnakPla2();
                        model.RakaPla2 = var.getRakaPla2();

                        model.KanardPla3 = var.getKanardPla3();
                        model.NamnakPla3 = var.getNamnakPla3();
                        model.RakaPla3 = var.getRakaPla3();

                        model.KanardPla4 = var.getKanardPla4();
                        model.NamnakPla4 = var.getNamnakPla4();
                        model.RakaPla4 = var.getRakaPla4();

                        h.kanardKachang.setText(Util.dobbleToStringNumber(var.KanardKachang));
                        h.jumnounKachang.setText(Util.dobbleToStringNumber(var.JumnounKachang));

                        h.lookPla.setText(Util.dobbleToStringNumber(var.LookPla));

                        h.group1_item_1.setText(Util.dobbleToStringNumber(var.Raka));

                        h.group1_item_3.setText(Util.dobbleToStringNumber(var.KaAHan));
                        h.group1_item_4.setText(Util.dobbleToStringNumber(var.KaYa));
                        h.group1_item_5.setText(Util.dobbleToStringNumber(var.KaSanKMe));
                        h.group1_item_6.setText(Util.dobbleToStringNumber(var.KaNamman));
                        h.group1_item_7.setText(Util.dobbleToStringNumber(var.KaFaifa));

                        h.group1_item_9.setText(Util.dobbleToStringNumber(var.KaRangNganLeang));
                        h.group1_item_10.setText(Util.dobbleToStringNumber(var.KaRangNganJub));
                        h.group1_item_11.setText(Util.dobbleToStringNumber(var.KaSomSam));
                        h.group1_item_12.setText(Util.dobbleToStringNumber(var.KaChaijai));

                        h.group2_item_1.setText(Util.dobbleToStringNumber(var.RayaWela));

                        h.group3_item_1.setText(Util.dobbleToStringNumber(var.NamnakTKai));
                        h.group3_item_3.setText(Util.dobbleToStringNumber(var.RakaTKai));
                        h.group3_item_4.setText(Util.dobbleToStringNumber(var.KanardPlaChalia));

                        h.group4_item_1_1.setText(Util.dobbleToStringNumber(var.KanardPla1));
                        h.group4_item_1_2.setText(Util.dobbleToStringNumber(var.RakaPla1));
                        h.group4_item_1_3.setText(Util.dobbleToStringNumber(var.NamnakPla1));

                        h.group4_item_2_1.setText(Util.dobbleToStringNumber(var.KanardPla2));
                        h.group4_item_2_2.setText(Util.dobbleToStringNumber(var.RakaPla2));
                        h.group4_item_2_3.setText(Util.dobbleToStringNumber(var.NamnakPla2));

                        h.group4_item_3_1.setText(Util.dobbleToStringNumber(var.KanardPla3));
                        h.group4_item_3_2.setText(Util.dobbleToStringNumber(var.RakaPla3));
                        h.group4_item_3_3.setText(Util.dobbleToStringNumber(var.NamnakPla3));

                        h.group4_item_4_1.setText(Util.dobbleToStringNumber(var.KanardPla4));
                        h.group4_item_4_2.setText(Util.dobbleToStringNumber(var.RakaPla4));
                        h.group4_item_4_3.setText(Util.dobbleToStringNumber(var.NamnakPla4));

                        formulaModel.calculate();

                        setUpCalUI(formulaModel);


                    }else{
                        h.kanardKachang.setText(plotDetail.getCoopMeter());
                        h.jumnounKachang.setText(plotDetail.getCoopNumber());
                        h.lookPla.setText(plotDetail.getFisheryNumber());

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
    private void popUpEditDialog() {

        final android.app.Dialog dialog = new android.app.Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_fish_kc);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        android.widget.TextView btn_cancel = (android.widget.TextView) dialog.findViewById(R.id.cancel);
        android.widget.TextView btn_ok = (android.widget.TextView) dialog.findViewById(R.id.ok);

            final EditText kc_unit = (EditText) dialog.findViewById(R.id.kc_unit);
            final EditText unit = (EditText) dialog.findViewById(R.id.unit);
            final EditText sqM = (EditText) dialog.findViewById(R.id.sqM);



              sqM.setText(h.kanardKachang.getText());
              kc_unit.setText(h.jumnounKachang.getText());
              unit.setText(h.lookPla.getText());

           // h.kanardKachang = (TextView) view.findViewById(R.id.rai);
          //  h.jumnounKachang = (TextView) view.findViewById(R.id.ngan);
          //  h.lookPla = (TextView) view.findViewById(R.id.tarangwa);


            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    h.kanardKachang.setText(Util.dobbleToStringNumberWithClearDigit(Util.strToDoubleDefaultZero(sqM.getText().toString())));
                    h.jumnounKachang.setText(Util.dobbleToStringNumberWithClearDigit(Util.strToDoubleDefaultZero(kc_unit.getText().toString())));
                    h.lookPla.setText(Util.dobbleToStringNumberWithClearDigit(Util.strToDoubleDefaultZero(unit.getText().toString())));

                    userPlotModel.setCoopMeter(h.kanardKachang.getText().toString());
                    userPlotModel.setCoopNumber(h.jumnounKachang.getText().toString());
                    userPlotModel.setFisheryNumber(h.lookPla.getText().toString());

                    dialog.dismiss();
                }
            });



        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.cancel();
            }
        });
        dialog.show();

    }
}