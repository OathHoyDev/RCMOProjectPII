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
import th.go.oae.rcmo.Model.calculate.FormulaHModel;
import th.go.oae.rcmo.Module.mGetPlotDetail;
import th.go.oae.rcmo.Module.mGetVariable;
import th.go.oae.rcmo.Module.mVarPlanH;
import th.go.oae.rcmo.Util.BitMapHelper;
import th.go.oae.rcmo.Util.PlanHTextWatcher;
import th.go.oae.rcmo.Util.ServiceInstance;
import th.go.oae.rcmo.Util.Util;
import th.go.oae.rcmo.View.DialogCalculateResult;

/**
 * Created by Taweesin on 27/6/2559.
 */
public class PBProdDetailCalculateFmentH extends Fragment implements View.OnClickListener {
    UserPlotModel userPlotModel;
    boolean isCalIncludeOption = false;
    private Context context;
    List<STDVarModel> stdVarModelList = new ArrayList<STDVarModel>();
    FormulaHModel formulaModel;
    ViewHolder h = new ViewHolder();
    th.go.oae.rcmo.Module.mGetPlotDetail.mRespBody mGetPlotDetail = new mGetPlotDetail.mRespBody();
    private boolean havePlotId = false;

    public PBProdDetailCalculateFmentH() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_prod_cal_plan_h, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //================= Set Holder ========================================//
        context = view.getContext();

        ((TextView) view.findViewById(R.id.start)).setText("เมื่อเริ่มเลี้ยงมี");
        ((TextView) view.findViewById(R.id.startPrice)).setText("น้ำหนักเฉลี่ยเมื่อเริ่มเลี้ยง");


        h.group1_item_1 = (EditText) view.findViewById(R.id.group1_item_1);
        h.group1_item_2 = (EditText) view.findViewById(R.id.group1_item_2);
        h.group1_item_3 = (EditText) view.findViewById(R.id.group1_item_3);
        h.group1_item_4 = (EditText) view.findViewById(R.id.group1_item_4);
        h.group1_item_5 = (EditText) view.findViewById(R.id.group1_item_5);
        h.group1_item_6 = (EditText) view.findViewById(R.id.group1_item_6);
        h.group1_item_7 = (EditText) view.findViewById(R.id.group1_item_7);
        h.group1_item_8 = (EditText) view.findViewById(R.id.group1_item_8);
        h.group1_item_9 = (EditText) view.findViewById(R.id.group1_item_9);
        h.group1_item_10 = (EditText) view.findViewById(R.id.group1_item_10);

        h.group2_item_1 = (EditText) view.findViewById(R.id.group2_item_1);
        h.group2_item_2 = (TextView) view.findViewById(R.id.group2_item_2);
        h.group2_item_3 = (TextView) view.findViewById(R.id.group2_item_3);


        h.group3_item_1 = (EditText) view.findViewById(R.id.group3_item_1);
        h.group3_item_2 = (TextView) view.findViewById(R.id.group3_item_2);

        h.group4_item_1 = (EditText) view.findViewById(R.id.group4_item_1);


        h.productIconImg = (ImageView) view.findViewById(R.id.productIconImg);
        h.txStartUnit = (TextView) view.findViewById(R.id.txStartUnit);
        h.txStartNumnakReam = (TextView) view.findViewById(R.id.txStartPrice);

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


//=========================== Setup data ==========================================/

        userPlotModel = PBProductDetailActivity.userPlotModel;

        formulaModel = new FormulaHModel();

        API_getVariable(userPlotModel.getPrdID(), userPlotModel.getFisheryType());

        if (!userPlotModel.getPlotID().equals("") && userPlotModel.getPlotID().equals("0")) {
            initVariableDataFromDB();
        }else{
            formulaModel.NumnukRermLeang =  Util.strToDoubleDefaultZero( userPlotModel.getAnimalWeight());
            formulaModel.RermLeang       =  Util.strToDoubleDefaultZero(userPlotModel.getAnimalNumber());
            formulaModel.calculate();
            setUpCalUI(formulaModel);
        }

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setUI();
        setAction();

        return view;
    }


    private void setAction() {
    /*
        h.txStartUnit.addTextChangedListener(new PlanFTextWatcher(h.txStartUnit, h, "KaPan,costKaSiaOkardRongRaun,calAllEgg,calPriceAllEgg,calAllPonPloyDai"));
    */
        h.txStartNumnakReam.addTextChangedListener(new PlanHTextWatcher( h.txStartNumnakReam, h, "calNumnukTPuem"));

        h.group1_item_1.addTextChangedListener(new PlanHTextWatcher(h.group1_item_1, h, "calNumnukTungmod"));
        h.group1_item_2.addTextChangedListener(new PlanHTextWatcher(h.group1_item_2, h, ""));
        h.group1_item_3.addTextChangedListener(new PlanHTextWatcher(h.group1_item_3, h, ""));
        h.group1_item_4.addTextChangedListener(new PlanHTextWatcher(h.group1_item_4, h, ""));
        h.group1_item_5.addTextChangedListener(new PlanHTextWatcher(h.group1_item_5, h, ""));
        h.group1_item_6.addTextChangedListener(new PlanHTextWatcher(h.group1_item_6, h, ""));
        h.group1_item_7.addTextChangedListener(new PlanHTextWatcher(h.group1_item_7, h, ""));
        h.group1_item_8.addTextChangedListener(new PlanHTextWatcher(h.group1_item_8, h, ""));
        h.group1_item_9.addTextChangedListener(new PlanHTextWatcher(h.group1_item_9, h, ""));
        h.group1_item_10.addTextChangedListener(new PlanHTextWatcher(h.group1_item_10, h, ""));

        h.group2_item_1.addTextChangedListener(new PlanHTextWatcher(h.group2_item_1, h, "calNumnukTungmod,calNumnukTPuem,calRakaTkai"));
        h.group3_item_1.addTextChangedListener(new PlanHTextWatcher(h.group3_item_1, h, "calRakaTkai"));
        h.group4_item_1.addTextChangedListener(new PlanHTextWatcher(h.group4_item_1, h, ""));

      //  calAllEgg
    }

    private void initVariableDataFromDB() {
        API_getPlotDetailANDBlinding(userPlotModel.getPlotID(), formulaModel);


        // set variable to json obj
        //userPlotModel.setVarValue(ProductService.genJsonPlanAVariable(formulaModel));

    }


    private void setUpCalUI(FormulaHModel model) {
        h.group2_item_2.setText(Util.dobbleToStringNumber(Util.verifyDoubleDefaultZero(model.calNumnukTungmod)));
        h.group2_item_3.setText(Util.dobbleToStringNumber(Util.verifyDoubleDefaultZero(model.calNumnukTPuem)));
        h.group3_item_2.setText(Util.dobbleToStringNumber(Util.verifyDoubleDefaultZero(model.calRakaTkai)));


    }

    private void bindingData(FormulaHModel model) {


        model.JumnuanTKai = Util.strToDoubleDefaultZero(h.group1_item_1.getText().toString());
        model.KaPan       = Util.strToDoubleDefaultZero(h.group1_item_2.getText().toString());
        model.KaAHanKon   = Util.strToDoubleDefaultZero(h.group1_item_3.getText().toString());
        model.KaAKanYab   = Util.strToDoubleDefaultZero(h.group1_item_4.getText().toString());
        model.KaYa  = Util.strToDoubleDefaultZero(h.group1_item_5.getText().toString());
        model.KaRang        = Util.strToDoubleDefaultZero(h.group1_item_6.getText().toString());
        model.KaNamKaFai      = Util.strToDoubleDefaultZero(h.group1_item_7.getText().toString());
        model.KaWassaduSinPleung  = Util.strToDoubleDefaultZero(h.group1_item_8.getText().toString());
        model.KaChoaTDin  = Util.strToDoubleDefaultZero(h.group1_item_9.getText().toString());
        model.KaSiaOkardLongtoon  = Util.strToDoubleDefaultZero(h.group1_item_10.getText().toString());

        model.NumnukChalia = Util.strToDoubleDefaultZero(h.group2_item_1.getText().toString());
        model.RakaChalia   = Util.strToDoubleDefaultZero(h.group3_item_1.getText().toString());
        model.RayaWera     = Util.strToDoubleDefaultZero(h.group4_item_1.getText().toString());

        model.RermLeang       = Util.strToDoubleDefaultZero(h.txStartUnit.getText().toString());
        model.NumnukRermLeang = Util.strToDoubleDefaultZero(h.txStartNumnakReam.getText().toString());

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
            h.txStartNumnakReam.setText( Util.dobbleToStringNumberWithClearDigit(Util.strToDoubleDefaultZero(userPlotModel.getAnimalWeight())));
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
            calculateResultModel.formularCode = "H";
            calculateResultModel.calculateResult = formulaModel.calProfitLoss;
            calculateResultModel.productName = userPlotModel.getPrdValue();
            calculateResultModel.unit_t1 = "บาท/กก." ;
            calculateResultModel.value_t1 = formulaModel.calProfitLossPerKg ;
            calculateResultModel.mPlotSuit = PBProductDetailActivity.mPlotSuit;
            calculateResultModel.compareStdResult = 0;

            DialogCalculateResult.userPlotModel = userPlotModel;
            DialogCalculateResult.calculateResultModel = calculateResultModel;
            userPlotModel.setVarValue(ProductService.genJsonPlanVariable(formulaModel));
            List resultArrayResult = new ArrayList();

            String [] tontoonCal_1 = {"ต้นทุนทั้งหมด" , String.format("%,.2f", formulaModel.calCost) , "บาท"};
            resultArrayResult.add(tontoonCal_1);

            String [] tontoonCal_2 = {"" , String.format("%,.2f", formulaModel.calCostPerTua) , "บาท/ตัว"};
            resultArrayResult.add(tontoonCal_2);
            String [] tontoonCal_3 = {"" , String.format("%,.2f", formulaModel.calAllCostPerKg) , "บาท/กก."};
            resultArrayResult.add(tontoonCal_3);

            String [] tontoonCal_4 = {"ผลตอบแทนต้นทุน\nทั้งหมดต่อตัว" , String.format("%,.2f", formulaModel.calCostReturnPerTua) , "บาท/กก."};
            resultArrayResult.add(tontoonCal_4);

            String [] tontoonCal_5 = {"อัตราการแลกเนื้อ\nเฉลี่ยต่อวัน" , String.format("%,.2f", formulaModel.calAttraRak) , ""};
            resultArrayResult.add(tontoonCal_5);


            DialogCalculateResult.calculateResultModel.resultList = resultArrayResult;

            new DialogCalculateResult(context).Show();

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
        //Group1
        public EditText group1_item_1, group1_item_2, group1_item_3, group1_item_4, group1_item_5,group1_item_6, group1_item_7, group1_item_8, group1_item_9, group1_item_10;

        //Group2
        public TextView group2_item_2,group2_item_3;
        public EditText group2_item_1;

        //Group3
        public EditText group3_item_1;
        public TextView group3_item_2;

        public EditText group4_item_1;

        public TextView txStartUnit,txStartNumnakReam;

        private ImageView productIconImg;

        private TextView calBtn, group1_header,group2_header, group3_header,group4_header;

        private LinearLayout group1_items, group2_items, group3_items, group4_items;;

        private ImageView group1_header_arrow,group2_header_arrow, group3_header_arrow,group4_header_arrow;

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
                    formulaModel.KaSermRongRaun = Util.strToDoubleDefaultZero(var.getD());
                   // formulaModel.KaSiaOkardRongRaun = Util.strToDoubleDefaultZero(var.getO());
                    //stub
                   // formulaModel.KaSermRongRaun  =69.36;
                   // formulaModel.KaSiaOkardRongRaun=5.46;
                    // h.group1_item_13.setText(String.valueOf(formulaModel.KaSermOuppakorn));
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

        android.widget.TextView btn_cancel = (android.widget.TextView) dialog.findViewById(R.id.cancel);
        android.widget.TextView btn_ok = (android.widget.TextView) dialog.findViewById(R.id.ok);

        ((TextView) dialog.findViewById(R.id.label_t1)).setText("น้ำหนักเฉลี่ยเมื่อเริ่มเลี้ยง");
        ((TextView) dialog.findViewById(R.id.unit_t1)).setText("กก./ตัว");;
        edit.setText(Util.clearStrNumberFormat(h.txStartUnit.getText().toString()));
        edit_t1.setText(Util.clearStrNumberFormat(h.txStartNumnakReam.getText().toString()));

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                h.txStartUnit.setText(Util.dobbleToStringNumberWithClearDigit(Util.strToDoubleDefaultZero(edit.getText().toString())));
                h.txStartNumnakReam.setText(Util.dobbleToStringNumberWithClearDigit(Util.strToDoubleDefaultZero(edit_t1.getText().toString())));
                //userPlotModel.setPlotRai(String.valueOf(Util.strToDoubleDefaultZero(inputRai.getText().toString())));

                userPlotModel.setAnimalNumber(Util.clearStrNumberFormat(h.txStartUnit.getText().toString()));
                userPlotModel.setAnimalWeight(Util.clearStrNumberFormat(h.txStartNumnakReam.getText().toString()));
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


    private void API_getPlotDetailANDBlinding(String plotID, final FormulaHModel model) {
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
                        mVarPlanH var = new Gson().fromJson(plotDetail.getVarValue(), mVarPlanH.class);


                        model.JumnuanTKai = var.JumnuanTKai;
                        model.KaPan       = var.KaPan;
                        model.KaAHanKon   = var.KaAHanKon;
                        model.KaAKanYab   = var.KaAKanYab;
                        model.KaNamKaFai  = var.KaNamKaFai;
                        model.KaYa        = var.KaYa;
                        model.KaRang      = var.KaRang;
                        model.KaNamKaFai  = var.KaNamKaFai;
                        model.KaChoaTDin  = var.KaChoaTDin;
                        model.KaSiaOkardLongtoon  = var.KaSiaOkardLongtoon;
                        model.NumnukChalia = var.NumnukChalia;
                        model.RakaChalia   = var.RakaChalia;
                        model.RayaWera     = var.RayaWera;
                        model.RermLeang       = var.RermLeang;
                        model.NumnukRermLeang = var.NumnukRermLeang;

                        model.isCalIncludeOption =  var.isCalIncludeOption();
                        setCalKaSermOption( var.isCalIncludeOption());
                        isCalIncludeOption = var.isCalIncludeOption();


                        h.group1_item_1.setText(Util.dobbleToStringNumber(var.JumnuanTKai));
                        h.group1_item_2.setText(Util.dobbleToStringNumber(var.KaPan));
                        h.group1_item_3.setText(Util.dobbleToStringNumber(var.KaAHanKon));
                        h.group1_item_4.setText(Util.dobbleToStringNumber(var.KaAKanYab));
                        h.group1_item_5.setText(Util.dobbleToStringNumber(var.getKaYa()));
                        h.group1_item_6.setText(Util.dobbleToStringNumber(var.KaRang));
                        h.group1_item_7.setText(Util.dobbleToStringNumber(var.KaNamKaFai));
                        h.group1_item_8.setText(Util.dobbleToStringNumber(var.KaWassaduSinPleung));
                        h.group1_item_9.setText(Util.dobbleToStringNumber(var.KaChoaTDin));
                        h.group1_item_10.setText(Util.dobbleToStringNumber(var.KaSiaOkardLongtoon));

                        h.group2_item_1.setText(Util.dobbleToStringNumber(var.NumnukChalia));
                        h.group3_item_1.setText(Util.dobbleToStringNumber(var.RakaChalia));
                        h.group4_item_1.setText(Util.dobbleToStringNumber(var.RayaWera));

                        h.txStartUnit.setText(Util.dobbleToStringNumberWithClearDigit(var.RermLeang));
                        h.txStartNumnakReam.setText(Util.dobbleToStringNumberWithClearDigit(var.NumnukRermLeang));


                        formulaModel.calculate();


                        setUpCalUI(formulaModel);
                    } else {
                        h.txStartUnit.setText(Util.strToDobbleToStrFormat(plotDetail.getAnimalNumber()));
                        h.txStartNumnakReam.setText(Util.strToDobbleToStrFormat(plotDetail.getAnimalWeight()));
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

    public void setCalKaSermOption(boolean isSetOption){
        if (isSetOption) {
            h.btnOption.setBackgroundResource(R.drawable.radio_cal_pink_check);
            isCalIncludeOption = true;
        } else {
            h.btnOption.setBackgroundResource(R.drawable.radio_cal_pink);
            isCalIncludeOption = false;
        }
    }
}