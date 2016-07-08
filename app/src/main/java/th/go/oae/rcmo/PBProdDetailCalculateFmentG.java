package th.go.oae.rcmo;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import th.go.oae.rcmo.Model.STDVarModel;
import th.go.oae.rcmo.Model.UserPlotModel;
import th.go.oae.rcmo.Model.calculate.CalculateResultModel;
import th.go.oae.rcmo.Model.calculate.FormulaGModel;
import th.go.oae.rcmo.Module.mGetPlotDetail;
import th.go.oae.rcmo.Module.mGetVariable;
import th.go.oae.rcmo.Module.mVarPlanG;
import th.go.oae.rcmo.Util.BitMapHelper;
import th.go.oae.rcmo.Util.PlanGTextWatcher;
import th.go.oae.rcmo.Util.ServiceInstance;
import th.go.oae.rcmo.Util.Util;
import th.go.oae.rcmo.View.DialogCalculateResult;

/**
 * Created by Taweesin on 27/6/2559.
 */
public class PBProdDetailCalculateFmentG extends Fragment implements View.OnClickListener {
    UserPlotModel userPlotModel;
    boolean isCalIncludeOption = false;
    private Context context;
    List<STDVarModel> stdVarModelList = new ArrayList<STDVarModel>();
    FormulaGModel formulaModel;
    ViewHolder h = new ViewHolder();
    th.go.oae.rcmo.Module.mGetPlotDetail.mRespBody mGetPlotDetail = new mGetPlotDetail.mRespBody();
    private boolean havePlotId = false;

    public PBProdDetailCalculateFmentG() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_prod_cal_plan_g, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //================= Set Holder ========================================//
        context = view.getContext();

        h.group0_item_1 = (EditText) view.findViewById(R.id.group0_item_1);

        h.group1_item_1 = (EditText) view.findViewById(R.id.group1_item_1);
        h.group1_item_2 = (EditText) view.findViewById(R.id.group1_item_2);
        h.group1_item_3 = (EditText) view.findViewById(R.id.group1_item_3);
        h.group1_item_4 = (EditText) view.findViewById(R.id.group1_item_4);
        h.group1_item_5 = (TextView) view.findViewById(R.id.group1_item_5);

        h.group2_item_1 = (TextView) view.findViewById(R.id.group2_item_1);
        h.group2_item_2 = (EditText) view.findViewById(R.id.group2_item_2);
        h.group2_item_3 = (EditText) view.findViewById(R.id.group2_item_3);
        h.group2_item_4 = (TextView) view.findViewById(R.id.group2_item_4);
        h.group2_item_5 = (EditText) view.findViewById(R.id.group2_item_5);
        h.group2_item_6 = (EditText) view.findViewById(R.id.group2_item_6);
        h.group2_item_7 = (EditText) view.findViewById(R.id.group2_item_7);
        h.group2_item_8 = (EditText) view.findViewById(R.id.group2_item_8);
        h.group2_item_9 = (EditText) view.findViewById(R.id.group2_item_9);
        h.group2_item_10 = (EditText) view.findViewById(R.id.group2_item_10);
        h.group2_item_11 = (EditText) view.findViewById(R.id.group2_item_11);
        h.group2_item_12 = (EditText) view.findViewById(R.id.group2_item_12);
        h.group2_item_13 = (EditText) view.findViewById(R.id.group2_item_13);
        h.group2_item_14 = (EditText) view.findViewById(R.id.group2_item_14);
        h.group2_item_15 = (TextView) view.findViewById(R.id.group2_item_15);


        h.group3_item_1 = (EditText) view.findViewById(R.id.group3_item_1);
        h.group3_item_2 = (EditText) view.findViewById(R.id.group3_item_2);
        h.group3_item_3 = (EditText) view.findViewById(R.id.group3_item_3);
        h.group3_item_4 = (EditText) view.findViewById(R.id.group3_item_4);
        h.group3_item_5 = (EditText) view.findViewById(R.id.group3_item_5);


        h.group4_item_1 = (EditText) view.findViewById(R.id.group4_item_1);
        h.group4_item_2 = (TextView) view.findViewById(R.id.group4_item_2);
        h.group4_item_3 = (TextView) view.findViewById(R.id.group4_item_3);
        h.group4_item_4 = (TextView) view.findViewById(R.id.group4_item_4);
        h.group4_item_5 = (TextView) view.findViewById(R.id.group4_item_5);

        h.group5_item_1 = (EditText) view.findViewById(R.id.group5_item_1);


        h.productIconImg = (ImageView) view.findViewById(R.id.productIconImg);
        h.txStartUnit = (TextView) view.findViewById(R.id.txStartUnit);

        h.group0_items = (LinearLayout) view.findViewById(R.id.group0_items);
        h.group0_header = (TextView) view.findViewById(R.id.group0_header);
        h.group0_header_arrow = (ImageView) view.findViewById(R.id.group0_header_arrow);
        h.group0_header.setOnClickListener(this);

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

        h.group5_items = (LinearLayout) view.findViewById(R.id.group5_items);
        h.group5_header = (TextView) view.findViewById(R.id.group5_header);
        h.group5_header_arrow = (ImageView) view.findViewById(R.id.group5_header_arrow);
        h.group5_header.setOnClickListener(this);


        h.headerLayout = (RelativeLayout) view.findViewById(R.id.headerLayout);
        h.headerLayout.setOnClickListener(this);

        h.calBtn = (TextView) view.findViewById(R.id.calBtn);
        h.calBtn.setOnClickListener(this);

        h.btnOption = (Button) view.findViewById(R.id.btnOption);
        h.btnOption.setOnClickListener(this);


//=========================== Setup data ==========================================/

        userPlotModel = PBProductDetailActivity.userPlotModel;

        formulaModel = new FormulaGModel();

        API_getVariable(userPlotModel.getPrdID(), userPlotModel.getFisheryType());

        if (!userPlotModel.getPlotID().equals("") && userPlotModel.getPlotID().equals("0")) {
            initVariableDataFromDB();
        }else{
            formulaModel.JumuanMaeKo       =  Util.strToDoubleDefaultZero(userPlotModel.getAnimalNumber());
            formulaModel.calculate();
            setUpCalUI(formulaModel);
        }

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setUI();
        setAction();

        return view;
    }


    private void setAction() {

        h.txStartUnit.addTextChangedListener(new PlanGTextWatcher(h.txStartUnit, h, ""));


        h.group0_item_1.addTextChangedListener(new PlanGTextWatcher(h.group0_item_1, h, ""));

        h.group1_item_1.addTextChangedListener(new PlanGTextWatcher(h.group1_item_1, h, "calMoonkaAnimalTuangNumnuk"));
        h.group1_item_2.addTextChangedListener(new PlanGTextWatcher(h.group1_item_2, h, "calMoonkaAnimalTuangNumnuk"));
        h.group1_item_3.addTextChangedListener(new PlanGTextWatcher(h.group1_item_3, h, "calMoonkaAnimalTuangNumnuk"));
        h.group1_item_4.addTextChangedListener(new PlanGTextWatcher(h.group1_item_4, h, "calMoonkaAnimalTuangNumnuk"));


        h.group2_item_1.addTextChangedListener(new PlanGTextWatcher(h.group2_item_1, h, "costKaSiaOkardRongRaun"));
        h.group2_item_2.addTextChangedListener(new PlanGTextWatcher(h.group2_item_2, h, "calKaRang"));
        h.group2_item_3.addTextChangedListener(new PlanGTextWatcher(h.group2_item_3, h, "calKaRang"));
        h.group2_item_4.addTextChangedListener(new PlanGTextWatcher(h.group2_item_4, h, "costKaSiaOkardRongRaun"));

        h.group2_item_5.addTextChangedListener(new PlanGTextWatcher(h.group2_item_5, h, "calKawassadu"));
        h.group2_item_6.addTextChangedListener(new PlanGTextWatcher(h.group2_item_6, h, "calKawassadu"));
        h.group2_item_7.addTextChangedListener(new PlanGTextWatcher(h.group2_item_7, h, "calKawassadu"));
        h.group2_item_8.addTextChangedListener(new PlanGTextWatcher(h.group2_item_8, h, "calKawassadu"));
        h.group2_item_9.addTextChangedListener(new PlanGTextWatcher(h.group2_item_9, h, "calKawassadu"));
        h.group2_item_10.addTextChangedListener(new PlanGTextWatcher(h.group2_item_10, h, "calKawassadu"));
        h.group2_item_11.addTextChangedListener(new PlanGTextWatcher(h.group2_item_11, h, "calKawassadu"));
        h.group2_item_12.addTextChangedListener(new PlanGTextWatcher(h.group2_item_12, h, "calKawassadu"));
        h.group2_item_13.addTextChangedListener(new PlanGTextWatcher(h.group2_item_13, h, "calKawassadu"));
        h.group2_item_14.addTextChangedListener(new PlanGTextWatcher(h.group2_item_14, h, "calKawassadu"));




        h.group3_item_1.addTextChangedListener(new PlanGTextWatcher(h.group3_item_1, h, ""));
        h.group3_item_2.addTextChangedListener(new PlanGTextWatcher(h.group3_item_2, h, ""));
        h.group3_item_3.addTextChangedListener(new PlanGTextWatcher(h.group3_item_3, h, ""));
        h.group3_item_4.addTextChangedListener(new PlanGTextWatcher(h.group3_item_4, h, ""));
        h.group3_item_5.addTextChangedListener(new PlanGTextWatcher(h.group3_item_5, h, ""));

        h.group4_item_1.addTextChangedListener(new PlanGTextWatcher(h.group4_item_1, h, ""));

        h.group5_item_1.addTextChangedListener(new PlanGTextWatcher(h.group5_item_1, h, ""));



      //  calAllEgg
    }

    private void initVariableDataFromDB() {
        API_getPlotDetailANDBlinding(userPlotModel.getPlotID(), formulaModel);


        // set variable to json obj
        //userPlotModel.setVarValue(ProductService.genJsonPlanAVariable(formulaModel));

    }


    private void setUpCalUI(FormulaGModel model) {
        h.group1_item_5.setText(Util.dobbleToStringNumber(model.calMoonkaAnimalTuangNumnuk));

        if(Double.isNaN(model.calKaRang)){
            h.group2_item_1.setText(Util.dobbleToStringNumber(0));
        }else{
            h.group2_item_1.setText(Util.dobbleToStringNumber(model.calKaRang));
        }
        if(Double.isNaN(model.calKawassadu)){
            h.group2_item_4.setText(Util.dobbleToStringNumber(0));
        }else{
            h.group2_item_4.setText(Util.dobbleToStringNumber(model.calKawassadu));
        }
        h.group2_item_15.setText(Util.dobbleToStringNumber(model.calKaSiaOkardLongtoon));


    }

    private void bindingData(FormulaGModel model) {


        model.ParimanNumnom =  Util.strToDoubleDefaultZero(h.group0_item_1.getText().toString());

        model.KoRakRakGerd =  Util.strToDoubleDefaultZero(h.group1_item_1.getText().toString());
        model.Ko1_2        =  Util.strToDoubleDefaultZero(h.group1_item_2.getText().toString());
        model.Ko2          =  Util.strToDoubleDefaultZero(h.group1_item_3.getText().toString());
        model.MaeKoReedNom =  Util.strToDoubleDefaultZero(h.group1_item_4.getText().toString());


        model.KaReedNom           =  Util.strToDoubleDefaultZero(h.group2_item_2.getText().toString());
        model.KaRangReang         =  Util.strToDoubleDefaultZero(h.group2_item_3.getText().toString());
        model.KaPasomPan          =  Util.strToDoubleDefaultZero(h.group2_item_5.getText().toString());
        model.KaAHan              =  Util.strToDoubleDefaultZero(h.group2_item_6.getText().toString());
        model.KaAHanYab           =  Util.strToDoubleDefaultZero(h.group2_item_7.getText().toString());
        model.KaYa                =  Util.strToDoubleDefaultZero(h.group2_item_8.getText().toString());
        model.KaNamKaFai          =  Util.strToDoubleDefaultZero(h.group2_item_9.getText().toString());
        model.KaNamMan            =  Util.strToDoubleDefaultZero(h.group2_item_10.getText().toString());
        model.KaWassaduSinPleung  =  Util.strToDoubleDefaultZero(h.group2_item_11.getText().toString());
        model.KaSomsamOuppakorn   =  Util.strToDoubleDefaultZero(h.group2_item_12.getText().toString());
        model.KaKonsong           =  Util.strToDoubleDefaultZero(h.group2_item_13.getText().toString());
        model.KaChaiJay           =  Util.strToDoubleDefaultZero(h.group2_item_14.getText().toString());


        model.PerKaNamKaFai          =  Util.strToDoubleDefaultZero(h.group3_item_1.getText().toString());
        model.PerKaNamMan            =  Util.strToDoubleDefaultZero(h.group3_item_2.getText().toString());
        model.PerKaWassaduSinPleung  =  Util.strToDoubleDefaultZero(h.group3_item_3.getText().toString());
        model.PerKaSomsamOuppakorn   =  Util.strToDoubleDefaultZero(h.group3_item_4.getText().toString());
        model.PerKaChaiJay           =  Util.strToDoubleDefaultZero(h.group3_item_5.getText().toString());

        model.KaChaoTDin     =  Util.strToDoubleDefaultZero(h.group4_item_1.getText().toString());

        model.RakaTkai       =  Util.strToDoubleDefaultZero(h.group5_item_1.getText().toString());

        model.JumuanMaeKo       =  Util.strToDoubleDefaultZero(h.txStartUnit.getText().toString());

    }

    private void setUI() {

        String imgName = ServiceInstance.productIMGMap.get(Integer.valueOf(userPlotModel.getPrdID()));
        if (imgName != null) {
            h.productIconImg.setImageBitmap(BitMapHelper.decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier(imgName, "drawable", getActivity().getPackageName()), R.dimen.iccircle_img_width, R.dimen.iccircle_img_height));
        }
        if (!userPlotModel.getPlotID().equals("") && !userPlotModel.getPlotID().equals("0")) {

            initVariableDataFromDB();
            havePlotId = true;
        } else {
            h.txStartUnit.setText( Util.dobbleToStringNumberWithClearDigit(Util.strToDoubleDefaultZero(userPlotModel.getAnimalNumber())));
        }

        if (isCalIncludeOption) {

            if (isCalIncludeOption) {
                h.btnOption.setBackgroundResource(R.drawable.radio_cal_pink_check);
            } else {
                h.btnOption.setBackgroundResource(R.drawable.radio_cal_pink);
            }

            formulaModel.isCalIncludeOption = isCalIncludeOption;
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.calBtn) {
            //formulaModel = new FormulaDModel();
            bindingData(formulaModel);
            formulaModel.calculate();
            setUpCalUI(formulaModel);

            CalculateResultModel calculateResultModel = new CalculateResultModel();
            calculateResultModel.formularCode = "G";
            calculateResultModel.calculateResult = formulaModel.calProfitLoss;
            calculateResultModel.productName = userPlotModel.getPrdValue();
            calculateResultModel.unit_t1 = "" ;
            calculateResultModel.value_t1 = 0 ;
            calculateResultModel.mPlotSuit = PBProductDetailActivity.mPlotSuit;
            calculateResultModel.compareStdResult = 0;

            DialogCalculateResult.userPlotModel = userPlotModel;
            DialogCalculateResult.calculateResultModel = calculateResultModel;
            userPlotModel.setVarValue(ProductService.genJsonPlanVariable(formulaModel));
            List resultArrayResult = new ArrayList();

            String [] tontoonCal_1 = {"ต้นทุนทั้งหมด" , String.format("%,.2f", formulaModel.calCost) , "บาท"};
            resultArrayResult.add(tontoonCal_1);
            ;
            String [] tontoonCal_3 = {"" , String.format("%,.2f", formulaModel.costTontunPalitNamnomPerKg) , "บาท/กก."};
            resultArrayResult.add(tontoonCal_3);

            String [] tontoonCal_4 = {"ปริมาณน้ำนมดิบที่รีดได้เฉลี่ย" , String.format("%,.2f", formulaModel.calNamnomTReedCharia) , "กก./ตัว/วัน"};
            resultArrayResult.add(tontoonCal_4);

            String [] tontoonCal_5 = {"ราคาที่เกษตรกรขายได้" , String.format("%,.2f", formulaModel.RakaTkai) , "บาท/กก."};
            resultArrayResult.add(tontoonCal_5);


            DialogCalculateResult.calculateResultModel.resultList = resultArrayResult;

            new DialogCalculateResult(context).Show();

        } else if (v.getId() == R.id.group0_header) {

            if (h.group0_items.getVisibility() == View.GONE) {
                h.group0_items.setVisibility(View.VISIBLE);
                h.group0_header_arrow.setImageBitmap(BitMapHelper.
                        decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier("arrow_hide", "drawable", context.getPackageName()), 30, 30));
            } else {
                h.group0_header_arrow.setImageBitmap(BitMapHelper.
                        decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier("arrow_show", "drawable", context.getPackageName()), 30, 30));

                h.group0_items.setVisibility(View.GONE);

            }
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
        }  else if (v.getId() == R.id.group5_header) {

            if (h.group5_items.getVisibility() == View.GONE) {
                h.group5_items.setVisibility(View.VISIBLE);
                h.group5_header_arrow.setImageBitmap(BitMapHelper.
                        decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier("arrow_hide", "drawable", context.getPackageName()), 30, 30));
            } else {
                h.group5_header_arrow.setImageBitmap(BitMapHelper.
                        decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier("arrow_show", "drawable", context.getPackageName()), 30, 30));

                h.group5_items.setVisibility(View.GONE);

            }
        }  else if (v.getId() == R.id.btnOption) {

            if (isCalIncludeOption) {
                h.btnOption.setBackgroundResource(R.drawable.radio_cal_pink);
                isCalIncludeOption = false;
            } else {
                h.btnOption.setBackgroundResource(R.drawable.radio_cal_pink_check);
                isCalIncludeOption = true;
            }

            formulaModel.isCalIncludeOption = isCalIncludeOption;

        } else if (v.getId() == R.id.headerLayout) {


            popUpEditDialog();
        }

    }

    public static class ViewHolder {
        public EditText group0_item_0;

        //Group1
        public EditText group0_item_1;

        //Group1
        public EditText group1_item_1, group1_item_2, group1_item_3, group1_item_4;
        public TextView  group1_item_5;

        //Group2
        public EditText group2_item_2 , group2_item_3  , group2_item_5  , group2_item_6  , group2_item_7 , group2_item_8
                       ,group2_item_9 , group2_item_10 , group2_item_11 , group2_item_12 , group2_item_13,group2_item_14;
        public TextView group2_item_1,group2_item_4,group2_item_15;

        //Group3
        public EditText group3_item_1,group3_item_2,group3_item_3,group3_item_4,group3_item_5;

        //Group4
        public EditText group4_item_1;
        public TextView group4_item_2,group4_item_3,group4_item_4,group4_item_5;

        //Group5
        public EditText group5_item_1;




        public TextView txStartUnit;;

        private ImageView productIconImg;

        private TextView calBtn, group0_header, group1_header,group2_header, group3_header,group4_header,group5_header;

        private LinearLayout group0_items,group1_items, group2_items, group3_items, group4_items, group5_items;

        private ImageView group0_header_arrow,group1_header_arrow,group2_header_arrow, group3_header_arrow,group4_header_arrow,group5_header_arrow;

        private Button btnOption;

        private RelativeLayout headerLayout;

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

                    formulaModel.KaSermRongRaun = Util.strToDoubleDefaultZero(var.getDH());
                    formulaModel.KaSermMaeKo    =Util.strToDoubleDefaultZero(var.getDD());
                    formulaModel.KaSiaOkardRongRaun = Util.strToDoubleDefaultZero(var.getOH());
                    formulaModel.KaSiaOkardMaeKo    =Util.strToDoubleDefaultZero(var.getOD());

                    h.group4_item_2.setText(String.valueOf(Util.strToDoubleDefaultZero(var.getDH())));
                    h.group4_item_3.setText(String.valueOf(Util.strToDoubleDefaultZero(var.getDD())));
                    h.group4_item_4.setText(String.valueOf(Util.strToDoubleDefaultZero(var.getOH())));
                    h.group4_item_5.setText(String.valueOf(Util.strToDoubleDefaultZero(var.getOD())));

                    /*     //stub
                    formulaModel.KaSermRongRaun = 66.27;
                    formulaModel.KaSermMaeKo    =46.19;
                    formulaModel.KaSiaOkardRongRaun = 69.36;
                    formulaModel.KaSiaOkardMaeKo    =22.17;

                    h.group4_item_2.setText(String.valueOf( formulaModel.KaSermRongRaun));
                    h.group4_item_3.setText(String.valueOf(formulaModel.KaSermMaeKo));
                    h.group4_item_4.setText(String.valueOf(formulaModel.KaSiaOkardRongRaun));
                    h.group4_item_5.setText(String.valueOf( formulaModel.KaSiaOkardMaeKo));
*/

                   // formulaModel.KaSiaOkardRongRaun = Util.strToDoubleDefaultZero(var.getO());
                    //stub
                    //formulaModel.KaSermRongRaun  =69.36;
                   // formulaModel.KaSiaOkardRongRaun=5.46;

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


    private void popUpEditDialog() {

        final android.app.Dialog dialog = new android.app.Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_animal);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        //android.widget.TextView title = (android.widget.TextView) dialog.findViewById(R.id.edit_rai_label);
        // final android.widget.TextView inputRai = (android.widget.TextView) dialog.findViewById(R.id.edit_rai);

        final EditText edit = (EditText) dialog.findViewById(R.id.edit);
        final EditText edit_t1 = (EditText) dialog.findViewById(R.id.edit_t1);

        edit_t1.setVisibility(View.GONE);

        android.widget.TextView btn_cancel = (android.widget.TextView) dialog.findViewById(R.id.cancel);
        android.widget.TextView btn_ok = (android.widget.TextView) dialog.findViewById(R.id.ok);

        ((TextView) dialog.findViewById(R.id.label_t1)).setVisibility(View.GONE);
        ((TextView) dialog.findViewById(R.id.unit_t1)).setVisibility(View.GONE);

        edit.setText(Util.clearStrNumberFormat(h.txStartUnit.getText().toString()));
      //  edit_t1.setText(h.txStartNumnakReam.getText());

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                h.txStartUnit.setText(Util.dobbleToStringNumberWithClearDigit(Util.strToDoubleDefaultZero(edit.getText().toString())));
               // h.txStartNumnakReam.setText(Util.dobbleToStringNumberWithClearDigit(Util.strToDoubleDefaultZero(edit_t1.getText().toString())));
                //userPlotModel.setPlotRai(String.valueOf(Util.strToDoubleDefaultZero(inputRai.getText().toString())));

                userPlotModel.setAnimalNumber(Util.clearStrNumberFormat(h.txStartUnit.getText().toString()));
               // userPlotModel.setAnimalWeight(h.txStartNumnakReam.getText().toString());
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


    private void API_getPlotDetailANDBlinding(String plotID, final FormulaGModel model) {
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
                        mVarPlanG var = new Gson().fromJson(plotDetail.getVarValue(), mVarPlanG.class);




                        model.ParimanNumnom =  var.ParimanNumnom;

                        model.KoRakRakGerd        =  var.KoRakRakGerd;
                        model.Ko1_2               =  var.Ko1_2;
                        model.Ko2                 =  var.Ko2;
                        model.MaeKoReedNom        =  var.MaeKoReedNom;

                        model.KaReedNom           =  var.KaReedNom;
                        model.KaRangReang         =  var.KaRangReang;

                        model.KaPasomPan          =  var.KaPasomPan;
                        model.KaAHan              =  var.KaAHan;
                        model.KaAHanYab           =  var.KaAHanYab;
                        model.KaYa                =  var.KaYa;
                        model.KaNamKaFai          =  var.KaNamKaFai;
                        model.KaNamMan            =  var.KaNamMan;
                        model.KaWassaduSinPleung  =  var.KaWassaduSinPleung;
                        model.KaSomsamOuppakorn   =  var.KaSomsamOuppakorn;
                        model.KaKonsong           =  var.KaKonsong;
                        model.KaChaiJay           =  var.KaChaiJay;
                        model.PerKaNamKaFai          =  var.PerKaNamKaFai;
                        model.PerKaNamMan            =  var.PerKaNamMan;
                        model.PerKaWassaduSinPleung  =  var.PerKaWassaduSinPleung;
                        model.PerKaSomsamOuppakorn   =  var.PerKaSomsamOuppakorn;
                        model.PerKaChaiJay           =  var.PerKaChaiJay;
                        model.KaChaoTDin           =  var.KaChaoTDin;
                        model.RakaTkai             =  var.Raka;
                        model.JumuanMaeKo          =  var.JumuanMaeKo;


                        h.group0_item_1.setText(Util.dobbleToStringNumber(var.ParimanNumnom));

                        h.group1_item_1.setText(Util.dobbleToStringNumber(var.KoRakRakGerd));
                        h.group1_item_2.setText(Util.dobbleToStringNumber(var.Ko1_2));
                        h.group1_item_3.setText(Util.dobbleToStringNumber(var.Ko2));
                        h.group1_item_4.setText(Util.dobbleToStringNumber(var.MaeKoReedNom));


                        h.group2_item_2.setText(Util.dobbleToStringNumber(var.KaReedNom));
                        h.group2_item_3.setText(Util.dobbleToStringNumber(var.KaRangReang));

                        h.group2_item_5.setText(Util.dobbleToStringNumber(var.KaPasomPan));
                        h.group2_item_6.setText(Util.dobbleToStringNumber(var.KaAHan));
                        h.group2_item_7.setText(Util.dobbleToStringNumber(var.KaAHanYab));
                        h.group2_item_8.setText(Util.dobbleToStringNumber(var.KaYa));
                        h.group2_item_9.setText(Util.dobbleToStringNumber(var.KaNamKaFai));
                        h.group2_item_10.setText(Util.dobbleToStringNumber(var.KaNamMan));
                        h.group2_item_11.setText(Util.dobbleToStringNumber(var.KaWassaduSinPleung));
                        h.group2_item_12.setText(Util.dobbleToStringNumber(var.KaSomsamOuppakorn));
                        h.group2_item_13.setText(Util.dobbleToStringNumber(var.KaKonsong));
                        h.group2_item_14.setText(Util.dobbleToStringNumber(var.KaChaiJay));

                        h.group3_item_1.setText(Util.dobbleToStringNumber(var.PerKaNamKaFai));
                        h.group3_item_2.setText(Util.dobbleToStringNumber(var.PerKaNamMan));
                        h.group3_item_3.setText(Util.dobbleToStringNumber(var.PerKaWassaduSinPleung));
                        h.group3_item_4.setText(Util.dobbleToStringNumber(var.PerKaSomsamOuppakorn));
                        h.group3_item_5.setText(Util.dobbleToStringNumber(var.PerKaChaiJay));

                        h.group4_item_1.setText(Util.dobbleToStringNumber(var.KaChaoTDin));

                        h.group5_item_1.setText(Util.dobbleToStringNumber(var.Raka));

                        h.txStartUnit.setText(Util.dobbleToStringNumber(var.JumuanMaeKo));


                        formulaModel.calculate();


                        setUpCalUI(formulaModel);
                    } else {
                        h.txStartUnit.setText(Util.strToDobbleToStrFormat(plotDetail.getAnimalNumber()));
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