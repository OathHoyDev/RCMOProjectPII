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
import th.go.oae.rcmo.Model.calculate.FormulaJModel;
import th.go.oae.rcmo.Module.mGetPlotDetail;
import th.go.oae.rcmo.Module.mGetVariable;
import th.go.oae.rcmo.Module.mVarPlanJ;
import th.go.oae.rcmo.Util.BitMapHelper;
import th.go.oae.rcmo.Util.InputFilterMinMax;
import th.go.oae.rcmo.Util.PlanJTextWatcher;
import th.go.oae.rcmo.Util.ServiceInstance;
import th.go.oae.rcmo.Util.Util;
import th.go.oae.rcmo.View.DialogCalculateResult;
import th.go.oae.rcmo.View.DialogChoice;

/**
 * Created by Taweesin on 27/6/2559.
 */
public class PBProdDetailCalculateFmentJ extends Fragment implements View.OnClickListener {

    String tuaOrKilo = ""; // 0 = จำนวนตัว , 1 = จำนวนกิโล
    int calType = 0; // ประเภทการขายปลา : 1 = คละขนาด , 2 = แยกขนาด
    int customSize = 0; // จำนวนขนาดปลา

    UserPlotModel userPlotModel;
    ViewHolder h = new ViewHolder();
    View view;
    FormulaJModel formulaModel;

    private boolean havePlotId = false;

    th.go.oae.rcmo.Module.mGetPlotDetail.mRespBody mGetPlotDetail = new mGetPlotDetail.mRespBody();

    Context context;

    boolean isCalIncludeOption = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_prod_cal_plan_j, container, false);
        context = view.getContext();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        userPlotModel = PBProductDetailActivity.userPlotModel;
        setHolder();


        FormulaJModel aModel = new FormulaJModel();
        formulaModel = aModel;

        API_getVariable(userPlotModel.getPrdID(), userPlotModel.getFisheryType());

        if (!userPlotModel.getPlotID().equals("") && !userPlotModel.getPlotID().equals("0")) {
            initVariableDataFromDB();
        } else {
            formulaModel.Rai = Util.strToDoubleDefaultZero(userPlotModel.getPondRai());
            formulaModel.Ngan = Util.strToDoubleDefaultZero(userPlotModel.getPondNgan());
            formulaModel.TarangWa = Util.strToDoubleDefaultZero(userPlotModel.getPondWa());
            formulaModel.TarangMeter = Util.strToDoubleDefaultZero(userPlotModel.getPondMeter());

            if (ServiceInstance.FISHERY_NUM_TYPE_KK.equals(userPlotModel.getFisheryNumType())) {
                formulaModel.LookPla = Util.strToDoubleDefaultZero(userPlotModel.getFisheryWeight());
            } else {
                formulaModel.LookPla = Util.strToDoubleDefaultZero(userPlotModel.getFisheryNumber());
            }
            tuaOrKilo = userPlotModel.getFisheryNumType();
            formulaModel.TuaOrKilo = Integer.valueOf(userPlotModel.getFisheryNumType());
            formulaModel.calculate();
            setUpCalUI(formulaModel);
        }

        setUI();

        setAction();
        //sampleData();

        return view;
    }


    private void setHolder() {

        //public static final String FISHERY_TYPE_KC = "2";
        //public static final String FISHERY_TYPE_BO = "1";
        //public static final String FISHERY_NUM_TYPE_TUA = "1";
        //public static final String FISHERY_NUM_TYPE_KK  = "2";
        if (ServiceInstance.FISHERY_NUM_TYPE_KK.equals(userPlotModel.getFisheryNumType())) {
            ((TextView) view.findViewById(R.id.unit_type)).setText("กก.");
            ((TextView) view.findViewById(R.id.price_unit_type)).setText("บาท/กก.");
        }
        h.group1_item_1 = (EditText) view.findViewById(R.id.group1_item_1);
        h.group1_item_2 = (TextView) view.findViewById(R.id.group1_item_2);
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
        // h.group2_item_3 = (TextView) view.findViewById(R.id.group2_item_3);
        // h.group2_item_4 = (TextView) view.findViewById(R.id.group2_item_4);

        //h.group2_3_item = (LinearLayout) view.findViewById(R.id.group2_3_item);
        //h.group2_4_item = (LinearLayout) view.findViewById(R.id.group2_4_item);

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

        h.group4_size_avg_item = (TextView) view.findViewById(R.id.group4_size_avg_item);
        h.group4_price_avg_item = (TextView) view.findViewById(R.id.group4_price_avg_item);

        h.group4_1_header = (LinearLayout) view.findViewById(R.id.group4_1_header);
        h.group4_2_header = (LinearLayout) view.findViewById(R.id.group4_2_header);
        h.group4_3_header = (LinearLayout) view.findViewById(R.id.group4_3_header);
        h.group4_4_header = (LinearLayout) view.findViewById(R.id.group4_4_header);


        h.productIconImg = (ImageView) view.findViewById(R.id.productIconImg);
        h.rai = (TextView) view.findViewById(R.id.rai);
        h.ngan = (TextView) view.findViewById(R.id.ngan);
        h.wa = (TextView) view.findViewById(R.id.wa);
        h.meter = (TextView) view.findViewById(R.id.meter);
        h.rookPla = (TextView) view.findViewById(R.id.rookPla);

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

        h.raiLabel = (TextView) view.findViewById(R.id.raiLabel);
        h.nganLabel = (TextView) view.findViewById(R.id.nganLabel);
        h.waLabel = (TextView) view.findViewById(R.id.waLabel);
        h.meterLabel = (TextView) view.findViewById(R.id.meterLabel);

        //  h.group2_3_item.setVisibility(View.GONE);
        // h.group2_4_item.setVisibility(View.GONE);
    }

    private void setAction() {

        h.rookPla.addTextChangedListener(new PlanJTextWatcher(h.rookPla, h, "calRakaPan"));
        h.rai.addTextChangedListener(new PlanJTextWatcher(h.rai, h, "calNamnakTKai"));
        h.ngan.addTextChangedListener(new PlanJTextWatcher(h.ngan, h, "calNamnakTKai"));
        h.wa.addTextChangedListener(new PlanJTextWatcher(h.wa, h, "calNamnakTKai"));
        h.meter.addTextChangedListener(new PlanJTextWatcher(h.meter, h, "calNamnakTKai"));

        h.group1_item_1.addTextChangedListener(new PlanJTextWatcher(h.group1_item_1, h, "calRakaPan"));
        h.group1_item_2.addTextChangedListener(new PlanJTextWatcher(h.group1_item_2, h, "calKaSiaOkardLongtoon"));
        h.group1_item_3.addTextChangedListener(new PlanJTextWatcher(h.group1_item_3, h, "calKaSiaOkardLongtoon"));
        h.group1_item_4.addTextChangedListener(new PlanJTextWatcher(h.group1_item_4, h, "calKaSiaOkardLongtoon"));
        h.group1_item_5.addTextChangedListener(new PlanJTextWatcher(h.group1_item_5, h, "calKaSiaOkardLongtoon"));
        h.group1_item_6.addTextChangedListener(new PlanJTextWatcher(h.group1_item_6, h, "calKaSiaOkardLongtoon"));
        h.group1_item_7.addTextChangedListener(new PlanJTextWatcher(h.group1_item_7, h, "calKaSiaOkardLongtoon"));
        h.group1_item_8.addTextChangedListener(new PlanJTextWatcher(h.group1_item_8, h, "calKaSiaOkardLongtoon"));
        h.group1_item_9.addTextChangedListener(new PlanJTextWatcher(h.group1_item_9, h, "calKaSiaOkardLongtoon"));
        h.group1_item_10.addTextChangedListener(new PlanJTextWatcher(h.group1_item_10, h, "calKaRang"));
        h.group1_item_11.addTextChangedListener(new PlanJTextWatcher(h.group1_item_11, h, "calKaRang"));
        h.group1_item_12.addTextChangedListener(new PlanJTextWatcher(h.group1_item_12, h, "calKaSiaOkardLongtoon"));
        h.group1_item_13.addTextChangedListener(new PlanJTextWatcher(h.group1_item_13, h, "calKaSiaOkardLongtoon"));
        h.group1_item_14.addTextChangedListener(new PlanJTextWatcher(h.group1_item_14, h, ""));


        h.group2_item_1.addTextChangedListener(new PlanJTextWatcher(h.group2_item_1, h, "calKaRang"));
        h.group2_item_2.addTextChangedListener(new PlanJTextWatcher(h.group2_item_2, h, ""));
        // h.group2_item_3.addTextChangedListener(new PlanJTextWatcher(h.group2_item_3, h, ""));
        // h.group2_item_4.addTextChangedListener(new PlanJTextWatcher(h.group2_item_4, h, ""));

        h.group3_item_1.addTextChangedListener(new PlanJTextWatcher(h.group3_item_1, h, "calNamnakTKai,calRaidai"));
        h.group3_item_2.addTextChangedListener(new PlanJTextWatcher(h.group3_item_2, h, ""));
        h.group3_item_3.addTextChangedListener(new PlanJTextWatcher(h.group3_item_3, h, "calRaidai"));
        h.group3_item_4.addTextChangedListener(new PlanJTextWatcher(h.group3_item_4, h, ""));
        h.group3_item_5.addTextChangedListener(new PlanJTextWatcher(h.group3_item_5, h, ""));

        h.group4_item_1_1.addTextChangedListener(new PlanJTextWatcher(h.group4_item_1_1, h, "AvgFishSize,AvgFishPrice"));
        h.group4_item_1_2.addTextChangedListener(new PlanJTextWatcher(h.group4_item_1_2, h, "calRakaTKai1,AvgFishSize,AvgFishPrice"));
        h.group4_item_1_3.addTextChangedListener(new PlanJTextWatcher(h.group4_item_1_3, h, "calRakaTKai1,AvgFishSize,AvgFishPrice"));
        h.group4_item_1_4.addTextChangedListener(new PlanJTextWatcher(h.group4_item_1_4, h, ""));

        h.group4_item_2_1.addTextChangedListener(new PlanJTextWatcher(h.group4_item_2_1, h, "AvgFishSize,AvgFishPrice"));
        h.group4_item_2_2.addTextChangedListener(new PlanJTextWatcher(h.group4_item_2_2, h, "calRakaTKai2,AvgFishSize,AvgFishPrice"));
        h.group4_item_2_3.addTextChangedListener(new PlanJTextWatcher(h.group4_item_2_3, h, "calRakaTKai2,AvgFishSize,AvgFishPrice"));
        h.group4_item_2_4.addTextChangedListener(new PlanJTextWatcher(h.group4_item_2_4, h, ""));

        h.group4_item_3_1.addTextChangedListener(new PlanJTextWatcher(h.group4_item_3_1, h, "AvgFishSize,AvgFishPrice"));
        h.group4_item_3_2.addTextChangedListener(new PlanJTextWatcher(h.group4_item_3_2, h, "calRakaTKai3,AvgFishSize,AvgFishPrice"));
        h.group4_item_3_3.addTextChangedListener(new PlanJTextWatcher(h.group4_item_3_3, h, "calRakaTKai3,AvgFishSize,AvgFishPrice"));
        h.group4_item_3_4.addTextChangedListener(new PlanJTextWatcher(h.group4_item_3_4, h, ""));

        h.group4_item_4_1.addTextChangedListener(new PlanJTextWatcher(h.group4_item_4_1, h, "AvgFishSize,AvgFishPrice"));
        h.group4_item_4_2.addTextChangedListener(new PlanJTextWatcher(h.group4_item_4_2, h, "calRakaTKai4,AvgFishSize,AvgFishPrice"));
        h.group4_item_4_3.addTextChangedListener(new PlanJTextWatcher(h.group4_item_4_3, h, "calRakaTKai4,AvgFishSize,AvgFishPrice"));
        h.group4_item_4_4.addTextChangedListener(new PlanJTextWatcher(h.group4_item_4_4, h, ""));


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
            h.rai.setText(Util.dobbleToStringNumberWithClearDigit(Util.strToDoubleDefaultZero(userPlotModel.getPondRai())));
            h.ngan.setText(Util.dobbleToStringNumberWithClearDigit(Util.strToDoubleDefaultZero(userPlotModel.getPondNgan())));
            h.wa.setText(Util.dobbleToStringNumberWithClearDigit(Util.strToDoubleDefaultZero(userPlotModel.getPondWa())));
            h.meter.setText(Util.dobbleToStringNumberWithClearDigit(Util.strToDoubleDefaultZero(userPlotModel.getPondMeter())));

            checkVisibility(Util.strToDoubleDefaultZero(userPlotModel.getPondRai())
                    , Util.strToDoubleDefaultZero(userPlotModel.getPondNgan())
                    , Util.strToDoubleDefaultZero(userPlotModel.getPondWa())
                    , Util.strToDoubleDefaultZero(userPlotModel.getPondMeter()));

            if (ServiceInstance.FISHERY_NUM_TYPE_KK.equals(userPlotModel.getFisheryNumType())) {
                h.rookPla.setText(Util.dobbleToStringNumberWithClearDigit(Util.strToDoubleDefaultZero(userPlotModel.getFisheryWeight())));
            } else {
                h.rookPla.setText(Util.dobbleToStringNumberWithClearDigit(Util.strToDoubleDefaultZero(userPlotModel.getFisheryNumber())));
            }

        }


        if (isCalIncludeOption) {

            if (isCalIncludeOption) {
                h.btnOption.setBackgroundResource(R.drawable.radio_cal_blue_check);
            } else {
                h.btnOption.setBackgroundResource(R.drawable.radio_cal_blue);
            }

            formulaModel.isCalIncludeOption = isCalIncludeOption;
        }

        if (formulaModel.CalType == 1) {
            h.group3_header_check.setImageBitmap(BitMapHelper.
                    decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier("radio_cal_blue_check", "drawable", context.getPackageName()), 20, 20));
            h.group3_header.setBackgroundResource(R.drawable.blue_cut_top_conner);
            h.group4_header_check.setImageBitmap(BitMapHelper.
                    decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier("radio_cal_blue", "drawable", context.getPackageName()), 20, 20));
            h.group4_header.setBackgroundResource(R.drawable.gray_cut_top_conner);

            if (h.group3_items.getVisibility() == View.GONE) {
                h.group3_items.setVisibility(View.VISIBLE);
                h.group3_header_arrow.setImageBitmap(BitMapHelper.
                        decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier("arrow_hide", "drawable", context.getPackageName()), 30, 30));
            }

        } else if (formulaModel.CalType == 2) {
            h.group4_header_check.setImageBitmap(BitMapHelper.
                    decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier("radio_cal_blue_check", "drawable", context.getPackageName()), 20, 20));
            h.group4_header.setBackgroundResource(R.drawable.blue_cut_top_conner);
            h.group3_header_check.setImageBitmap(BitMapHelper.
                    decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier("radio_cal_blue", "drawable", context.getPackageName()), 20, 20));
            h.group3_header.setBackgroundResource(R.drawable.gray_cut_top_conner);

            switch (formulaModel.CustomSize) {
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
            if (validateInputData()) {
                if (calType == 0) {

                    new DialogChoice(context).ShowOneChoice("กรุณาระบุประเภทการขาย", "");

                } else {


                    bindingData(formulaModel);
                    formulaModel.calculate();

                    setUpCalUI(formulaModel);

                    CalculateResultModel calculateResultModel = new CalculateResultModel();

                    if (calType == 1) {
                        // Util.showDialogAndDismiss(context, "คำนวนสำเร็จ : " + formulaModel.KumraiKadtoonMix);
                        calculateResultModel.unit_t1 = "บาท/กก.";
                        calculateResultModel.value_t1 = formulaModel.KumraiKadtoonMixTorKilo;
                        calculateResultModel.calculateResult = formulaModel.KumraiKadtoonMix;
                    } else if (calType == 2) {
                        // Util.showDialogAndDismiss(context, "คำนวนสำเร็จ : " + formulaModel.KumraiKadtoonSize);
                        calculateResultModel.unit_t1 = "บาท/กก.";
                        calculateResultModel.value_t1 = formulaModel.KumraiKadtoonSizeTorKilo;
                        calculateResultModel.calculateResult = formulaModel.KumraiKadtoonSize;
                    }

                    calculateResultModel.formularCode = "J";

                    calculateResultModel.productName = userPlotModel.getPrdValue();
                    calculateResultModel.mPlotSuit = PBProductDetailActivity.mPlotSuit;
                    calculateResultModel.compareStdResult = 0;

                    DialogCalculateResult.userPlotModel = userPlotModel;
                    DialogCalculateResult.calculateResultModel = calculateResultModel;

                    userPlotModel.setVarValue(ProductService.genJsonPlanVariable(formulaModel));

                    List resultArrayResult = new ArrayList();

                    if (calType == 1) {
                        String[] type = {"ขายแบบคละขนาด", "", ""};
                        resultArrayResult.add(type);
                        String[] tontoonCal_1 = {"ต้นทุนทั้งหมด", String.format("%,.2f", formulaModel.costTontoonMix), "บาท"};
                        resultArrayResult.add(tontoonCal_1);
                        String[] tontoonCal_2 = {"", String.format("%,.2f", formulaModel.costTontoonMixTorKilo), "บาท/กก."};
                        resultArrayResult.add(tontoonCal_2);
                        String[] raydai_1 = {"", String.format("%,.2f", formulaModel.costTontoonMixTorRai), "บาท/ไร่"};
                        resultArrayResult.add(raydai_1);
                    } else if (calType == 2) {
                        String[] type = {"ขายแบบแยกขนาด", "", ""};
                        resultArrayResult.add(type);
                        String[] tontoonCal_1 = {"ต้นทุนทั้งหมด", String.format("%,.2f", formulaModel.costTontoonSize), "บาท"};
                        resultArrayResult.add(tontoonCal_1);
                        String[] tontoonCal_2 = {"", String.format("%,.2f", formulaModel.costTontoonSizeTorKilo), "บาท/กก."};
                        resultArrayResult.add(tontoonCal_2);
                        String[] raydai_1 = {"", String.format("%,.2f", formulaModel.costTontoonSizeTorRai), "บาท/ไร่"};
                        resultArrayResult.add(raydai_1);
                    }


                    DialogCalculateResult.calculateResultModel.resultList = resultArrayResult;

                    new DialogCalculateResult(context).Show();

                }

            }
        } else if (v.getId() == R.id.btnOption) {

            if (isCalIncludeOption) {
                h.btnOption.setBackgroundResource(R.drawable.radio_cal_blue);
                isCalIncludeOption = false;
                formulaModel.isCalIncludeOption = false;
                //  h.group2_3_item.setVisibility(View.GONE);
                //  h.group2_4_item.setVisibility(View.GONE);
            } else {
                h.btnOption.setBackgroundResource(R.drawable.radio_cal_blue_check);
                isCalIncludeOption = true;
                formulaModel.isCalIncludeOption = true;
                // h.group2_3_item.setVisibility(View.VISIBLE);
                // h.group2_4_item.setVisibility(View.VISIBLE);
            }

            formulaModel.isCalIncludeOption = isCalIncludeOption;

        } else if (v.getId() == R.id.group1_header) {

            if (h.group1_items.getVisibility() == View.GONE) {
                h.group1_items.setVisibility(View.VISIBLE);
                h.group1_header_arrow.setImageBitmap(BitMapHelper.
                        decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier("arrow_hide", "drawable", context.getPackageName()), 30, 30));
            } else {
                h.group1_header_arrow.setImageBitmap(BitMapHelper.
                        decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier("arrow_show", "drawable", context.getPackageName()), 30, 30));

                h.group1_items.setVisibility(View.GONE);

            }
        } else if (v.getId() == R.id.group2_header) {

            if (h.group2_items.getVisibility() == View.GONE) {
                h.group2_items.setVisibility(View.VISIBLE);
                h.group2_header_arrow.setImageBitmap(BitMapHelper.
                        decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier("arrow_hide", "drawable", context.getPackageName()), 30, 30));
            } else {
                h.group2_header_arrow.setImageBitmap(BitMapHelper.
                        decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier("arrow_show", "drawable", context.getPackageName()), 30, 30));

                h.group2_items.setVisibility(View.GONE);

            }
        } else if (v.getId() == R.id.group3_header) {

            if (h.group3_items.getVisibility() == View.GONE) {
                h.group3_items.setVisibility(View.VISIBLE);
                h.group3_header_arrow.setImageBitmap(BitMapHelper.
                        decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier("arrow_hide", "drawable", context.getPackageName()), 30, 30));
            } else {
                h.group3_header_arrow.setImageBitmap(BitMapHelper.
                        decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier("arrow_show", "drawable", context.getPackageName()), 30, 30));

                h.group3_items.setVisibility(View.GONE);

            }
        } else if (v.getId() == R.id.group4_header) {

            if (h.group4_items.getVisibility() == View.GONE) {
                h.group4_items.setVisibility(View.VISIBLE);
                h.group4_header_arrow.setImageBitmap(BitMapHelper.
                        decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier("arrow_hide", "drawable", context.getPackageName()), 30, 30));
            } else {
                h.group4_header_arrow.setImageBitmap(BitMapHelper.
                        decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier("arrow_show", "drawable", context.getPackageName()), 30, 30));

                h.group4_items.setVisibility(View.GONE);

            }
        } else if (v.getId() == R.id.group3_header_check) {
            calType = 1;
            h.group3_header_check.setImageBitmap(BitMapHelper.
                    decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier("radio_cal_blue_check", "drawable", context.getPackageName()), 20, 20));
            h.group3_header.setBackgroundResource(R.drawable.blue_cut_top_conner);
            h.group4_header_check.setImageBitmap(BitMapHelper.
                    decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier("radio_cal_blue", "drawable", context.getPackageName()), 20, 20));
            h.group4_header.setBackgroundResource(R.drawable.gray_cut_top_conner);
            formulaModel.CalType = 1;

        } else if (v.getId() == R.id.group4_header_check) {
            calType = 2;
            h.group4_header_check.setImageBitmap(BitMapHelper.
                    decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier("radio_cal_blue_check", "drawable", context.getPackageName()), 20, 20));
            h.group4_header.setBackgroundResource(R.drawable.blue_cut_top_conner);
            h.group3_header_check.setImageBitmap(BitMapHelper.
                    decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier("radio_cal_blue", "drawable", context.getPackageName()), 20, 20));
            h.group3_header.setBackgroundResource(R.drawable.gray_cut_top_conner);

            formulaModel.CalType = 2;

        } else if (v.getId() == R.id.delete_group4_1) {

            switch (customSize) {
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
        } else if (v.getId() == R.id.delete_group4_2) {

            switch (customSize) {
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

        } else if (v.getId() == R.id.delete_group4_3) {

            if (customSize == 3) {

                h.group4_item_3_1.setText("");
                h.group4_item_3_2.setText("");
                h.group4_item_3_3.setText("");
                h.group4_item_3_4.setText("");

                h.group4_3_header.setVisibility(View.GONE);

                customSize = 2;

            } else {

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

        } else if (v.getId() == R.id.delete_group4_4) {

            customSize = 3;

            h.group4_4_header.setVisibility(View.GONE);

            h.group4_item_4_1.setText("");
            h.group4_item_4_2.setText("");
            h.group4_item_4_3.setText("");
            h.group4_item_4_4.setText("");

        } else if (v.getId() == R.id.group4_add_item_btn) {

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
        if (v.getId() == R.id.headerLayout) {

            popUpEditDialog();

        }

    }

    private void deleteCustomSize(int size) {
        if (size < customSize) {
            int difSize = customSize - size;


        }
    }

    private void initVariableDataFromDB() {
        API_getPlotDetailANDBlinding(userPlotModel.getPlotID(), formulaModel);
    }

    private void setUpCalUI(FormulaJModel model) {

        //h.group1_item_9.setText(Util.dobbleToStringNumber(model.calKaRang));
        h.group1_item_2.setText(Util.dobbleToStringNumber(model.calKaPan));
        h.group1_item_9.setText(Util.dobbleToStringNumber(model.calKaRangNgan));
        h.group2_item_2.setText(Util.dobbleToStringNumber(model.calKaSiaOkardLongtoon));


        // h.group2_item_3.setText(Util.dobbleToStringNumber(aModel.calRayDaiTungmod));
        // h.group2_item_4.setText(Util.dobbleToStringNumber(aModel.calRayDaiChalia));
        // h.group2_item_6.setText(Util.dobbleToStringNumber(aModel.calKaSiaOkardLongtoon));


        if (model.CalType == 1) {
            h.group3_header_check.setImageBitmap(BitMapHelper.
                    decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier("radio_cal_blue_check", "drawable", context.getPackageName()), 20, 20));
            h.group3_header.setBackgroundResource(R.drawable.blue_cut_top_conner);
            h.group4_header_check.setImageBitmap(BitMapHelper.
                    decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier("radio_cal_blue", "drawable", context.getPackageName()), 20, 20));
            h.group4_header.setBackgroundResource(R.drawable.gray_cut_top_conner);

            if (h.group3_items.getVisibility() == View.GONE) {
                h.group3_items.setVisibility(View.VISIBLE);
                h.group3_header_arrow.setImageBitmap(BitMapHelper.
                        decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier("arrow_hide", "drawable", context.getPackageName()), 30, 30));
            }


            h.group3_item_2.setText(Util.dobbleToStringNumber(model.calNamnakTKai));
            h.group3_item_5.setText(Util.dobbleToStringNumber(model.calRaidai));

        } else if (model.CalType == 2) {
            h.group4_header_check.setImageBitmap(BitMapHelper.
                    decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier("radio_cal_blue_check", "drawable", context.getPackageName()), 20, 20));
            h.group4_header.setBackgroundResource(R.drawable.blue_cut_top_conner);
            h.group3_header_check.setImageBitmap(BitMapHelper.
                    decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier("radio_cal_blue", "drawable", context.getPackageName()), 20, 20));
            h.group3_header.setBackgroundResource(R.drawable.gray_cut_top_conner);

            switch (model.CustomSize) {
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

    private void bindingData(FormulaJModel aModel) {

        aModel.Rai = Util.strToDoubleDefaultZero(h.rai.getText().toString());
        aModel.Ngan = Util.strToDoubleDefaultZero(h.ngan.getText().toString());
        aModel.TarangWa = Util.strToDoubleDefaultZero(h.wa.getText().toString());
        aModel.TarangMeter = Util.strToDoubleDefaultZero(h.meter.getText().toString());
        aModel.LookPla = Util.strToDoubleDefaultZero(h.rookPla.getText().toString());

        aModel.Raka = Util.strToDoubleDefaultZero(h.group1_item_1.getText().toString());
        aModel.KaAHan = Util.strToDoubleDefaultZero(h.group1_item_3.getText().toString());
        aModel.KaYa = Util.strToDoubleDefaultZero(h.group1_item_4.getText().toString());
        aModel.KaSanKMe = Util.strToDoubleDefaultZero(h.group1_item_5.getText().toString());
        aModel.KaNamman = Util.strToDoubleDefaultZero(h.group1_item_6.getText().toString());
        aModel.KaFaifa = Util.strToDoubleDefaultZero(h.group1_item_7.getText().toString());
        aModel.KaLoklen = Util.strToDoubleDefaultZero(h.group1_item_8.getText().toString());

        aModel.KaRangNganLeang = Util.strToDoubleDefaultZero(h.group1_item_10.getText().toString());
        aModel.KaRangNganJub = Util.strToDoubleDefaultZero(h.group1_item_11.getText().toString());
        aModel.KaSomSam = Util.strToDoubleDefaultZero(h.group1_item_12.getText().toString());
        aModel.KaChaijai = Util.strToDoubleDefaultZero(h.group1_item_13.getText().toString());
        aModel.KaChoaTDin = Util.strToDoubleDefaultZero(h.group1_item_14.getText().toString());

        aModel.RayaWela = Util.strToDoubleDefaultZero(h.group2_item_1.getText().toString());

        aModel.NamnakTKai = Util.strToDoubleDefaultZero(h.group3_item_1.getText().toString());
        aModel.RakaTKai = Util.strToDoubleDefaultZero(h.group3_item_3.getText().toString());
        aModel.KanardPlaChalia = Util.strToDoubleDefaultZero(h.group3_item_4.getText().toString());

        aModel.KanardPla1 = Util.strToDoubleDefaultZero(h.group4_item_1_1.getText().toString());
        aModel.RakaPla1 = Util.strToDoubleDefaultZero(h.group4_item_1_2.getText().toString());
        aModel.NamnakPla1 = Util.strToDoubleDefaultZero(h.group4_item_1_3.getText().toString());

        aModel.KanardPla2 = Util.strToDoubleDefaultZero(h.group4_item_2_1.getText().toString());
        aModel.RakaPla2 = Util.strToDoubleDefaultZero(h.group4_item_2_2.getText().toString());
        aModel.NamnakPla2 = Util.strToDoubleDefaultZero(h.group4_item_2_3.getText().toString());

        aModel.KanardPla3 = Util.strToDoubleDefaultZero(h.group4_item_3_1.getText().toString());
        aModel.RakaPla3 = Util.strToDoubleDefaultZero(h.group4_item_3_2.getText().toString());
        aModel.NamnakPla3 = Util.strToDoubleDefaultZero(h.group4_item_3_3.getText().toString());

        aModel.KanardPla4 = Util.strToDoubleDefaultZero(h.group4_item_4_1.getText().toString());
        aModel.RakaPla4 = Util.strToDoubleDefaultZero(h.group4_item_4_2.getText().toString());
        aModel.NamnakPla4 = Util.strToDoubleDefaultZero(h.group4_item_4_3.getText().toString());


    }

    public static class ViewHolder {

        // Header
        public TextView rai, ngan, wa, meter, rookPla;
        public ImageView productIconImg;

        // Group 1
        public EditText group1_item_1, group1_item_3, group1_item_4, group1_item_5, group1_item_6, group1_item_7, group1_item_8;
        public TextView group1_item_9, group1_item_2;
        public EditText group1_item_10, group1_item_11, group1_item_12, group1_item_13, group1_item_14;

        // Group 2
        public EditText group2_item_1;
        public TextView group2_item_2;
        // public TextView group2_item_3;
        // public TextView group2_item_4;

        // public LinearLayout group2_3_item, group2_4_item;

        // Group 3
        public EditText group3_item_1, group3_item_3, group3_item_4;
        public TextView group3_item_2, group3_item_5;
        public ImageView group3_header_check;

        // Group 4
        private TextView group4_add_item_btn;
        private ImageView group4_header_check;

        public EditText group4_item_1_1, group4_item_1_2, group4_item_1_3;
        public TextView group4_item_1_4;
        private TextView delete_group4_1;

        public EditText group4_item_2_1, group4_item_2_2, group4_item_2_3;
        public TextView group4_item_2_4;
        public TextView delete_group4_2;

        public EditText group4_item_3_1, group4_item_3_2, group4_item_3_3;
        public TextView group4_item_3_4;
        public TextView delete_group4_3;

        public EditText group4_item_4_1, group4_item_4_2, group4_item_4_3;
        public TextView group4_item_4_4;
        public TextView delete_group4_4;

        public LinearLayout group4_1_header, group4_2_header, group4_3_header, group4_4_header;


        public TextView calBtn, group1_header, group2_header, group3_header, group4_header;

        public LinearLayout group1_items, group2_items, group3_items, group4_items;

        public ImageView group1_header_arrow, group2_header_arrow, group3_header_arrow, group4_header_arrow;

        public Button btnOption;

        public TextView group4_size_avg_item, group4_price_avg_item;

        public RelativeLayout headerLayout;

        private TextView raiLabel, nganLabel, waLabel, meterLabel;
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
                    formulaModel.KaSermOuppakorn = Util.strToDoubleDefaultZero(var.getDP());
                    formulaModel.KaSiaOkardOuppakorn = Util.strToDoubleDefaultZero(var.getOP());
                    //  h.group2_item_3.setText(String.valueOf(formulaModel.KaSermOuppakorn * formulaModel.NueaTeeBor));
                    //  h.group2_item_4.setText(String.valueOf(formulaModel.KaSiaOkardOuppakorn * formulaModel.NueaTeeBor));
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

    private void API_getPlotDetailANDBlinding(String plotID, final FormulaJModel aModel) {
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

                        mVarPlanJ varJ = new Gson().fromJson(plotDetail.getVarValue(), mVarPlanJ.class);

                        calType = Util.strToIntegerDefaultZero(varJ.getCalType());

                        aModel.CalType = Util.strToIntegerDefaultZero(varJ.getCalType());
                        aModel.TuaOrKilo = Util.strToIntegerDefaultZero(varJ.getCalType());


                        aModel.Rai = Util.strToDoubleDefaultZero(plotDetail.getPondRai());
                        aModel.Ngan = Util.strToDoubleDefaultZero(plotDetail.getPondNgan());
                        aModel.TarangWa = Util.strToDoubleDefaultZero(plotDetail.getPondWa());
                        aModel.TarangMeter = Util.strToDoubleDefaultZero(plotDetail.getPondMeter());

                        aModel.LookPla = Util.strToDoubleDefaultZero(plotDetail.getFisheryNumber());

                        aModel.Raka = Util.strToDoubleDefaultZero(varJ.getRaka());
                        aModel.KaAHan = Util.strToDoubleDefaultZero(varJ.getKaAHan());
                        aModel.KaRangNganLeang = Util.strToDoubleDefaultZero(varJ.getKaRangNganLeang());
                        aModel.KaRangNganJub = Util.strToDoubleDefaultZero(varJ.getKaRangNganJub());
                        aModel.KaYa = Util.strToDoubleDefaultZero(varJ.getKaYa());
                        aModel.KaSanKMe = Util.strToDoubleDefaultZero(varJ.getKaSanKMe());
                        aModel.KaNamman = Util.strToDoubleDefaultZero(varJ.getKaNamman());
                        aModel.KaFaifa = Util.strToDoubleDefaultZero(varJ.getKaFaifa());
                        aModel.KaLoklen = Util.strToDoubleDefaultZero(varJ.getKaLoklen());
                        aModel.KaSomSam = Util.strToDoubleDefaultZero(varJ.getKaSomSam());
                        aModel.KaChaijai = Util.strToDoubleDefaultZero(varJ.getKaChaijai());
                        aModel.KaChoaTDin = Util.strToDoubleDefaultZero(varJ.getKaChoaTDin());
                        aModel.RayaWela = Util.strToDoubleDefaultZero(varJ.getRayaWela());

                        aModel.NamnakTKai = Util.strToDoubleDefaultZero(varJ.getNamnakTKai());
                        aModel.RakaTKai = Util.strToDoubleDefaultZero(varJ.getRakaTKai());
                        //aModel.KanardPlaChalia = varJ.getKanardPlaChalia();

                        aModel.KanardPla1 = Util.strToDoubleDefaultZero(varJ.getKanardPla1());
                        aModel.NamnakPla1 = Util.strToDoubleDefaultZero(varJ.getNamnakPla1());
                        aModel.RakaPla1 = Util.strToDoubleDefaultZero(varJ.getRakaPla1());

                        aModel.KanardPla2 = Util.strToDoubleDefaultZero(varJ.getKanardPla2());
                        aModel.NamnakPla2 = Util.strToDoubleDefaultZero(varJ.getNamnakPla2());
                        aModel.RakaPla2 = Util.strToDoubleDefaultZero(varJ.getRakaPla2());

                        aModel.KanardPla3 = Util.strToDoubleDefaultZero(varJ.getKanardPla3());
                        aModel.NamnakPla3 = Util.strToDoubleDefaultZero(varJ.getNamnakPla3());
                        aModel.RakaPla3 = Util.strToDoubleDefaultZero(varJ.getRakaPla3());

                        aModel.KanardPla4 = Util.strToDoubleDefaultZero(varJ.getKanardPla4());
                        aModel.NamnakPla4 = Util.strToDoubleDefaultZero(varJ.getNamnakPla4());
                        aModel.RakaPla4 = Util.strToDoubleDefaultZero(varJ.getRakaPla4());

                        aModel.isCalIncludeOption = varJ.isCalIncludeOption();
                        setCalKaSermOption(varJ.isCalIncludeOption());
                        isCalIncludeOption = varJ.isCalIncludeOption();

                        h.rai.setText(Util.strToDobbleToStrFormat(plotDetail.getPondRai()));
                        h.ngan.setText(Util.strToDobbleToStrFormat(plotDetail.getPondNgan()));
                        h.wa.setText(Util.strToDobbleToStrFormat(plotDetail.getPondWa()));
                        h.meter.setText(Util.strToDobbleToStrFormat(plotDetail.getPondMeter()));


                        h.group1_item_1.setText(Util.dobbleToStringNumberToDisplay(Util.strToDoubleDefaultZero(varJ.Raka)));

                        h.group1_item_3.setText(Util.dobbleToStringNumberToDisplay(Util.strToDoubleDefaultZero(varJ.KaAHan)));
                        h.group1_item_4.setText(Util.dobbleToStringNumberToDisplay(Util.strToDoubleDefaultZero(varJ.KaYa)));
                        h.group1_item_5.setText(Util.dobbleToStringNumberToDisplay(Util.strToDoubleDefaultZero(varJ.KaSanKMe)));
                        h.group1_item_6.setText(Util.dobbleToStringNumberToDisplay(Util.strToDoubleDefaultZero(varJ.KaNamman)));
                        h.group1_item_7.setText(Util.dobbleToStringNumberToDisplay(Util.strToDoubleDefaultZero(varJ.KaFaifa)));
                        h.group1_item_8.setText(Util.dobbleToStringNumberToDisplay(Util.strToDoubleDefaultZero(varJ.KaLoklen)));

                        h.group1_item_10.setText(Util.dobbleToStringNumberToDisplay(Util.strToDoubleDefaultZero(varJ.KaRangNganLeang)));
                        h.group1_item_11.setText(Util.dobbleToStringNumberToDisplay(Util.strToDoubleDefaultZero(varJ.KaRangNganJub)));
                        h.group1_item_12.setText(Util.dobbleToStringNumberToDisplay(Util.strToDoubleDefaultZero(varJ.KaSomSam)));
                        h.group1_item_13.setText(Util.dobbleToStringNumberToDisplay(Util.strToDoubleDefaultZero(varJ.KaChaijai)));
                        h.group1_item_14.setText(Util.dobbleToStringNumberToDisplay(Util.strToDoubleDefaultZero(varJ.KaChoaTDin)));

                        h.group2_item_1.setText(Util.dobbleToStringNumberToDisplay(Util.strToDoubleDefaultZero(varJ.RayaWela)));

                        h.group3_item_1.setText(Util.dobbleToStringNumberToDisplay(Util.strToDoubleDefaultZero(varJ.NamnakTKai)));
                        h.group3_item_3.setText(Util.dobbleToStringNumberToDisplay(Util.strToDoubleDefaultZero(varJ.RakaTKai)));
                        h.group3_item_4.setText(Util.dobbleToStringNumberToDisplay(Util.strToDoubleDefaultZero(varJ.KanardPlaChalia)));

                        h.group4_item_1_1.setText(Util.dobbleToStringNumberToDisplay(Util.strToDoubleDefaultZero(varJ.KanardPla1)));
                        h.group4_item_1_2.setText(Util.dobbleToStringNumberToDisplay(Util.strToDoubleDefaultZero(varJ.RakaPla1)));
                        h.group4_item_1_3.setText(Util.dobbleToStringNumberToDisplay(Util.strToDoubleDefaultZero(varJ.NamnakPla1)));

                        h.group4_item_2_1.setText(Util.dobbleToStringNumberToDisplay(Util.strToDoubleDefaultZero(varJ.KanardPla2)));
                        h.group4_item_2_2.setText(Util.dobbleToStringNumberToDisplay(Util.strToDoubleDefaultZero(varJ.RakaPla2)));
                        h.group4_item_2_3.setText(Util.dobbleToStringNumberToDisplay(Util.strToDoubleDefaultZero(varJ.NamnakPla2)));

                        h.group4_item_3_1.setText(Util.dobbleToStringNumberToDisplay(Util.strToDoubleDefaultZero(varJ.KanardPla3)));
                        h.group4_item_3_2.setText(Util.dobbleToStringNumberToDisplay(Util.strToDoubleDefaultZero(varJ.RakaPla3)));
                        h.group4_item_3_3.setText(Util.dobbleToStringNumberToDisplay(Util.strToDoubleDefaultZero(varJ.NamnakPla3)));

                        h.group4_item_4_1.setText(Util.dobbleToStringNumberToDisplay(Util.strToDoubleDefaultZero(varJ.KanardPla4)));
                        h.group4_item_4_2.setText(Util.dobbleToStringNumberToDisplay(Util.strToDoubleDefaultZero(varJ.RakaPla4)));
                        h.group4_item_4_3.setText(Util.dobbleToStringNumberToDisplay(Util.strToDoubleDefaultZero(varJ.NamnakPla4)));

                        userPlotModel.setFisheryNumType(plotDetail.getFisheryNumType());
                        if (ServiceInstance.FISHERY_NUM_TYPE_KK.equals(plotDetail.getFisheryNumType())) {
                            h.rookPla.setText(Util.strToDobbleToStrFormat(plotDetail.getFisheryWeight()));
                            userPlotModel.setFisheryWeight(plotDetail.getFisheryWeight());
                        } else {
                            h.rookPla.setText(Util.strToDobbleToStrFormat(plotDetail.getFisheryNumber()));
                            userPlotModel.setFisheryNumber(plotDetail.getFisheryNumber());
                        }
                        // userPlotModel.setFisheryNumber(plotDetail.getFisheryNumber());

                        formulaModel.calculate();

                        setUpCalUI(formulaModel);


                    } else {
                        h.rai.setText(Util.strToDobbleToStrFormat(plotDetail.getPondRai()));
                        h.ngan.setText(Util.strToDobbleToStrFormat(plotDetail.getPondNgan()));
                        h.wa.setText(Util.strToDobbleToStrFormat(plotDetail.getPondWa()));
                        h.meter.setText(Util.strToDobbleToStrFormat(plotDetail.getPondMeter()));

                        //h.rookPla.setText(plotDetail.getFisheryNumber());
                        userPlotModel.setFisheryNumType(plotDetail.getFisheryNumType());

                        if (ServiceInstance.FISHERY_NUM_TYPE_KK.equals(plotDetail.getFisheryNumType())) {
                            h.rookPla.setText(Util.strToDobbleToStrFormat(plotDetail.getFisheryWeight()));
                            userPlotModel.setFisheryWeight(plotDetail.getFisheryWeight());
                        } else {
                            h.rookPla.setText(Util.strToDobbleToStrFormat(plotDetail.getFisheryNumber()));
                            userPlotModel.setFisheryNumber(plotDetail.getFisheryNumber());
                        }
                    }

                    checkVisibility(Util.strToDoubleDefaultZero(plotDetail.getPondRai())
                            , Util.strToDoubleDefaultZero(plotDetail.getPondNgan())
                            , Util.strToDoubleDefaultZero(plotDetail.getPondWa())
                            , Util.strToDoubleDefaultZero(plotDetail.getPondMeter()));
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
        dialog.setContentView(R.layout.dialog_edit_fish);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        LinearLayout bo_layout = (LinearLayout) dialog.findViewById(R.id.bo_layout);


        android.widget.TextView btn_cancel = (android.widget.TextView) dialog.findViewById(R.id.cancel);
        android.widget.TextView btn_ok = (android.widget.TextView) dialog.findViewById(R.id.ok);

        final EditText rai = (EditText) dialog.findViewById(R.id.rai);

        final EditText ngan = (EditText) dialog.findViewById(R.id.ngan);
        ngan.setFilters(new InputFilter[]{new InputFilterMinMax(0, 9)});

        final EditText sqaWa = (EditText) dialog.findViewById(R.id.sqaWa);
        sqaWa.setFilters(new InputFilter[]{new InputFilterMinMax(0, 99)});

        final EditText sqM = (EditText) dialog.findViewById(R.id.sqM);
        sqM.setFilters(new InputFilter[]{new InputFilterMinMax(0, 399)});

        final EditText unit = (EditText) dialog.findViewById(R.id.unit);


        rai.setText(Util.clearStrNumberFormat(h.rai.getText().toString()));
        ngan.setText(Util.clearStrNumberFormat(h.ngan.getText().toString()));
        sqaWa.setText(Util.clearStrNumberFormat(h.wa.getText().toString()));
        sqM.setText(Util.clearStrNumberFormat(h.meter.getText().toString()));
        unit.setText(Util.clearStrNumberFormat(h.rookPla.getText().toString()));

        h.rai = (TextView) view.findViewById(R.id.rai);
        h.ngan = (TextView) view.findViewById(R.id.ngan);
        h.wa = (TextView) view.findViewById(R.id.wa);
        h.meter = (TextView) view.findViewById(R.id.meter);
        h.rookPla = (TextView) view.findViewById(R.id.rookPla);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                h.rai.setText(Util.dobbleToStringNumberWithClearDigit(Util.strToDoubleDefaultZero(rai.getText().toString())));
                h.ngan.setText(Util.dobbleToStringNumberWithClearDigit(Util.strToDoubleDefaultZero(ngan.getText().toString())));
                h.wa.setText(Util.dobbleToStringNumberWithClearDigit(Util.strToDoubleDefaultZero(sqaWa.getText().toString())));
                h.meter.setText(Util.dobbleToStringNumberWithClearDigit(Util.strToDoubleDefaultZero(sqM.getText().toString())));
                h.rookPla.setText(Util.dobbleToStringNumberWithClearDigit(Util.strToDoubleDefaultZero(unit.getText().toString())));
                //userPlotModel.setPlotRai(String.valueOf(Util.strToDoubleDefaultZero(inputRai.getText().toString())));

                checkVisibility(Util.strToDoubleDefaultZero(rai.getText().toString())
                        , Util.strToDoubleDefaultZero(ngan.getText().toString())
                        , Util.strToDoubleDefaultZero(sqaWa.getText().toString())
                        , Util.strToDoubleDefaultZero(sqM.getText().toString()));

                userPlotModel.setPondRai(Util.clearStrNumberFormat(h.rai.getText().toString()));
                userPlotModel.setPondNgan(Util.clearStrNumberFormat(h.ngan.getText().toString()));
                userPlotModel.setPondWa(Util.clearStrNumberFormat(h.wa.getText().toString()));
                userPlotModel.setPondMeter(Util.clearStrNumberFormat(h.meter.getText().toString()));

                if (ServiceInstance.FISHERY_NUM_TYPE_KK.equals(userPlotModel.getFisheryNumType())) {
                    userPlotModel.setFisheryWeight(Util.clearStrNumberFormat(h.rookPla.getText().toString()));
                } else {
                    userPlotModel.setFisheryNumber(Util.clearStrNumberFormat(h.rookPla.getText().toString()));
                }
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

    public void setCalKaSermOption(boolean isSetOption) {
        if (isSetOption) {
            h.btnOption.setBackgroundResource(R.drawable.radio_cal_blue_check);
            isCalIncludeOption = true;
        } else {
            h.btnOption.setBackgroundResource(R.drawable.radio_cal_blue);
            isCalIncludeOption = false;
        }
    }


    private void checkVisibility(double rai, double ngan, double wa, double meter) {
        Log.d("checkVisibility", "Rai = " + rai);
        Log.d("checkVisibility", "ngan = " + ngan);
        Log.d("checkVisibility", "wa = " + wa);
        Log.d("checkVisibility", "meter = " + meter);

        if (rai == 0) {
            h.rai.setVisibility(View.INVISIBLE);
            h.raiLabel.setVisibility(View.GONE);
        } else {
            h.rai.setVisibility(View.VISIBLE);
            h.raiLabel.setVisibility(View.VISIBLE);
        }

        if (ngan == 0) {
            h.ngan.setVisibility(View.GONE);
            h.nganLabel.setVisibility(View.GONE);
        } else {
            h.ngan.setVisibility(View.VISIBLE);
            h.nganLabel.setVisibility(View.VISIBLE);
        }

        if (wa == 0) {
            h.wa.setVisibility(View.GONE);
            h.waLabel.setVisibility(View.GONE);
        } else {
            h.wa.setVisibility(View.VISIBLE);
            h.waLabel.setVisibility(View.VISIBLE);
        }

        if (meter == 0) {
            h.meter.setVisibility(View.GONE);
            h.meterLabel.setVisibility(View.GONE);
        } else {
            h.meter.setVisibility(View.VISIBLE);
            h.meterLabel.setVisibility(View.VISIBLE);
        }
    }

    private boolean validateInputData() {

        double value =
                Util.strToDoubleDefaultZero(h.group1_item_1.getText().toString()) +
                        Util.strToDoubleDefaultZero(h.group1_item_3.getText().toString()) +
                        Util.strToDoubleDefaultZero(h.group1_item_4.getText().toString()) +
                        Util.strToDoubleDefaultZero(h.group1_item_5.getText().toString()) +
                        Util.strToDoubleDefaultZero(h.group1_item_6.getText().toString()) +
                        Util.strToDoubleDefaultZero(h.group1_item_7.getText().toString()) +
                        Util.strToDoubleDefaultZero(h.group1_item_8.getText().toString()) +
                        Util.strToDoubleDefaultZero(h.group1_item_10.getText().toString()) +
                        Util.strToDoubleDefaultZero(h.group1_item_11.getText().toString()) +
                        Util.strToDoubleDefaultZero(h.group1_item_12.getText().toString()) +
                        Util.strToDoubleDefaultZero(h.group1_item_13.getText().toString()) +
                        Util.strToDoubleDefaultZero(h.group1_item_14.getText().toString()) +


                        Util.strToDoubleDefaultZero(h.group2_item_1.getText().toString());


        if (value == 0) {
            new DialogChoice(context).ShowOneChoice("", "กรุณากรอกข้อมูล เพื่อคำนวณต้นทุน");
            return false;
        } else {
            return true;
        }
    }

}