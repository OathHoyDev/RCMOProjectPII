package th.co.rcmo.rcmoapp;

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
import com.google.gson.GsonBuilder;
import com.neopixl.pixlui.components.edittext.EditText;
import com.neopixl.pixlui.components.textview.TextView;

import java.util.ArrayList;
import java.util.List;

import th.co.rcmo.rcmoapp.API.ProductService;
import th.co.rcmo.rcmoapp.API.RequestServices;
import th.co.rcmo.rcmoapp.API.ResponseAPI;
import th.co.rcmo.rcmoapp.Model.STDVarModel;
import th.co.rcmo.rcmoapp.Model.UserPlotModel;
import th.co.rcmo.rcmoapp.Model.calculate.CalculateResultModel;
import th.co.rcmo.rcmoapp.Model.calculate.FormulaAModel;
import th.co.rcmo.rcmoapp.Module.mGetPlotDetail;
import th.co.rcmo.rcmoapp.Module.mGetVariable;
import th.co.rcmo.rcmoapp.Module.mVarPlanA;
import th.co.rcmo.rcmoapp.Util.BitMapHelper;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;
import th.co.rcmo.rcmoapp.Util.Util;
import th.co.rcmo.rcmoapp.View.DialogCalculateResult;

/**
 * Created by Taweesin on 26/6/2559.
 */
public class PBProdDetailCalculateFmentA extends Fragment implements View.OnClickListener {
    UserPlotModel userPlotModel;
    boolean isCalIncludeOption = false;
    private Context context;
    List<STDVarModel> stdVarModelList = new ArrayList<STDVarModel>();
    FormulaAModel formulaModel;
    ViewHolder h = new ViewHolder();
    mGetPlotDetail.mRespBody mGetPlotDetail = new mGetPlotDetail.mRespBody();
    private boolean havePlotId = false;

    public PBProdDetailCalculateFmentA() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_prod_cal_plan_a, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //================= Set Holder ========================================//
        context = view.getContext();
        h.group1_item_1 = (TextView) view.findViewById(R.id.group1_item_1);
        h.group1_item_2 = (EditText) view.findViewById(R.id.group1_item_2);
        h.group1_item_3 = (EditText) view.findViewById(R.id.group1_item_3);
        h.group1_item_4 = (EditText) view.findViewById(R.id.group1_item_4);
        h.group1_item_5 = (EditText) view.findViewById(R.id.group1_item_5);
        h.group1_item_6 = (TextView) view.findViewById(R.id.group1_item_6);
        h.group1_item_7 = (EditText) view.findViewById(R.id.group1_item_7);
        h.group1_item_8 = (EditText) view.findViewById(R.id.group1_item_8);
        h.group1_item_9 = (EditText) view.findViewById(R.id.group1_item_9);
        h.group1_item_10 = (EditText) view.findViewById(R.id.group1_item_10);
        h.group1_item_11 = (TextView) view.findViewById(R.id.group1_item_11);
        h.group1_item_12 = (EditText) view.findViewById(R.id.group1_item_12);
        h.group1_item_13 = (TextView) view.findViewById(R.id.group1_item_13);
        h.group1_item_14 = (TextView) view.findViewById(R.id.group1_item_14);
        h.group2_item_1 = (EditText) view.findViewById(R.id.group2_item_1);
        h.group3_item_1 = (EditText) view.findViewById(R.id.group3_item_1);
        h.group4_item_1 = (EditText) view.findViewById(R.id.group4_item_1);

        h.productIconImg = (ImageView) view.findViewById(R.id.productIconImg);
        h.txStartUnit = (TextView) view.findViewById(R.id.txStartUnit);

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

        FormulaAModel aModel = new FormulaAModel();

        formulaModel = aModel;

        API_getVariable(userPlotModel.getPrdID(), userPlotModel.getFisheryType());

        if (!userPlotModel.getPlotID().equals("") && userPlotModel.getPlotID().equals("0")) {
            initVariableDataFromDB();
        }

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setUI();

        return view;
    }

    private void initVariableDataFromDB() {
        API_getPlotDetailANDBlinding(userPlotModel.getPlotID(), formulaModel);
        formulaModel.calculate();
        setUpCalUI(formulaModel);
    }


    private void setUpCalUI(FormulaAModel aModel) {
        h.group1_item_1.setText(Util.dobbleToStringNumber(aModel.KaRang));
        h.group1_item_6.setText(Util.dobbleToStringNumber(aModel.KaWassadu));
        h.group1_item_11.setText(Util.dobbleToStringNumber(aModel.KaSiaOkardOuppakorn));
        h.group1_item_12.setText(Util.dobbleToStringNumber(aModel.KaChaoTDin));
        h.group1_item_13.setText(Util.dobbleToStringNumber(aModel.KaSermOuppakorn));
        h.group1_item_14.setText(Util.dobbleToStringNumber(aModel.KaSiaOkardOuppakorn));

    }

    private void bindingData(FormulaAModel aModel) {
        //  aModel.KaRang = 10;
        aModel.KaTreamDin = Util.strToDoubleDefaultZero(h.group1_item_2.getText().toString());
        aModel.KaPluk = Util.strToDoubleDefaultZero(h.group1_item_3.getText().toString());
        aModel.KaDoolae = Util.strToDoubleDefaultZero(h.group1_item_4.getText().toString());
        aModel.KaGebGeaw = Util.strToDoubleDefaultZero(h.group1_item_5.getText().toString());
        aModel.KaPan = Util.strToDoubleDefaultZero(h.group1_item_7.getText().toString());
        aModel.KaPuy = Util.strToDoubleDefaultZero(h.group1_item_8.getText().toString());
        aModel.KaYaplab = Util.strToDoubleDefaultZero(h.group1_item_9.getText().toString());
        aModel.KaWassaduUn = Util.strToDoubleDefaultZero(h.group1_item_10.getText().toString());
        aModel.KaChaoTDin = Util.strToDoubleDefaultZero(h.group1_item_12.getText().toString());
        aModel.PonPalid = Util.strToDoubleDefaultZero(h.group2_item_1.getText().toString());
        aModel.predictPrice = Util.strToDoubleDefaultZero(h.group3_item_1.getText().toString());
        aModel.AttraDokbia = Util.strToDoubleDefaultZero(h.group4_item_1.getText().toString());
        aModel.KaNardPlangTDin = Util.strToDoubleDefaultZero(h.txStartUnit.getText().toString());

        userPlotModel.setVarValue(ProductService.genJsonPlanAVariable(aModel));
/*
        mVarPlanA varA   = new mVarPlanA();
        varA.KaTreamDin  = h.group1_item_2.getText().toString();
        varA.KaPluk      = h.group1_item_3.getText().toString();
        varA.KaDoolae    = h.group1_item_4.getText().toString();
        varA.KaGebGeaw   = h.group1_item_5.getText().toString();
        varA.KaPan       = h.group1_item_7.getText().toString();
        varA.KaPuy       = h.group1_item_8.getText().toString();
        varA.KaYaplab    = h.group1_item_9.getText().toString();
        varA.KaWassaduUn = h.group1_item_10.getText().toString();
        //varA.KaChaoTDin = h.group1_item_12.getText().toString();
        varA.PonPalid    = h.group2_item_1.getText().toString();
        varA.Raka        = h.group3_item_1.getText().toString();
        varA.AttraDokbia = h.group4_item_1.getText().toString();
        varA.KaNardPlangTDin = h.txStartUnit.getText().toString();
        */





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
            h.txStartUnit.setText(userPlotModel.getPlotRai());

        }

        if (isCalIncludeOption) {

            if (isCalIncludeOption) {
                h.btnOption.setBackgroundResource(R.drawable.radio_cal_green_check);
            } else {
                h.btnOption.setBackgroundResource(R.drawable.radio_cal_green);
            }

            formulaModel.isCalIncludeOption = isCalIncludeOption;
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.calBtn) {

            bindingData(formulaModel);
            formulaModel.calculate();
            setUpCalUI(formulaModel);
         //   Util.showDialogAndDismiss(context, "คำนวนสำเร็จ" + formulaModel.calProfitLoss);

            CalculateResultModel calculateResultModel = new CalculateResultModel();
            calculateResultModel.formularCode = "A";
            calculateResultModel.calculateResult = formulaModel.calProfitLoss;
            calculateResultModel.productName = userPlotModel.getPrdValue();
            calculateResultModel.mPlotSuit = PBProductDetailActivity.mPlotSuit;
            calculateResultModel.compareStdResult = formulaModel.calSumCost - formulaModel.TontumMattratarn;

            DialogCalculateResult.userPlotModel = userPlotModel;
            DialogCalculateResult.calculateResultModel = calculateResultModel;

            //  String str = new StringEntity(ProductService.genJsonPlanAVariable(formulaModel), HTTP.UTF_8);


            userPlotModel.setVarValue(ProductService.genJsonPlanAVariable(formulaModel));


            List resultArrayResult = new ArrayList();

            String[] tontoonCal_1 = {"ต้นทุนรวมเกษตร", String.format("%,.2f", formulaModel.calSumCost), "บาท"};
            resultArrayResult.add(tontoonCal_1);

            String[] tontoonCal_2 = {"", String.format("%,.2f", formulaModel.calSumCostPerRai), "บาท/ไร่"};
            resultArrayResult.add(tontoonCal_2);

            String[] raydai_1 = {"รายได้", String.format("%,.2f", formulaModel.calIncome), "บาท"};
            resultArrayResult.add(raydai_1);

            String[] raydai_2 = {"", String.format("%,.2f", formulaModel.calIncomePerRai), "บาท/ไร่"};
            resultArrayResult.add(raydai_2);

            String[] tontoon = {"ต้นทุนเฉลี่ย", String.format("%,.2f", formulaModel.TontumMattratarnPerRai), "บาท/ไร่"};
            resultArrayResult.add(tontoon);

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
        } else if (v.getId() == R.id.btnOption) {

            if (isCalIncludeOption) {
                h.btnOption.setBackgroundResource(R.drawable.radio_cal_green);
                isCalIncludeOption = false;
            } else {
                h.btnOption.setBackgroundResource(R.drawable.radio_cal_green_check);
                isCalIncludeOption = true;
            }

            formulaModel.isCalIncludeOption = isCalIncludeOption;

        } else if (v.getId() == R.id.headerLayout) {


            popUpTumbonListDialog();
        }

    }

    static class ViewHolder {
        private TextView group1_item_1, group1_item_6, group1_item_11, group1_item_13, group1_item_14;
        private EditText group1_item_2, group1_item_3, group1_item_4, group1_item_5, group1_item_7, group1_item_8, group1_item_9, group1_item_10, group1_item_12;

        private EditText group2_item_1;
        private EditText group3_item_1;
        private EditText group4_item_1;

        private ImageView productIconImg;

        private TextView txStartUnit, calBtn, group1_header, group2_header, group3_header, group4_header;

        private LinearLayout group1_items, group2_items, group3_items, group4_items;

        private ImageView group1_header_arrow, group2_header_arrow, group3_header_arrow, group4_header_arrow;

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
                    formulaModel.KaSermOuppakorn = Util.strToDoubleDefaultZero(var.getD());
                    formulaModel.KaSiaOkardOuppakorn = Util.strToDoubleDefaultZero(var.getO());
                    h.group1_item_13.setText(String.valueOf(formulaModel.KaSermOuppakorn));
                    h.group1_item_14.setText(String.valueOf(formulaModel.KaSiaOkardOuppakorn));

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


    private void popUpTumbonListDialog() {


        final android.app.Dialog dialog = new android.app.Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_plant);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        android.widget.TextView title = (android.widget.TextView) dialog.findViewById(R.id.edit_rai_label);
        final android.widget.TextView inputRai = (android.widget.TextView) dialog.findViewById(R.id.edit_rai);
        android.widget.TextView btn_cancel = (android.widget.TextView) dialog.findViewById(R.id.cancel);
        android.widget.TextView btn_ok = (android.widget.TextView) dialog.findViewById(R.id.ok);

        title.setText("ขนาดแปลงที่ดิน");
        inputRai.setText(h.txStartUnit.getText());

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                h.txStartUnit.setText(inputRai.getText());
                //userPlotModel.setPlotRai(String.valueOf(Util.strToDoubleDefaultZero(inputRai.getText().toString())));

                userPlotModel.setPlotRai(inputRai.getText().toString());
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


    private void API_getPlotDetailANDBlinding(String plotID, final FormulaAModel aModel) {
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
                        mVarPlanA varA = new Gson().fromJson(plotDetail.getVarValue(), mVarPlanA.class);

                        aModel.KaTreamDin = Util.strToDoubleDefaultZero(varA.getKaTreamDin());
                        aModel.KaPluk = Util.strToDoubleDefaultZero(varA.getKaPluk());
                        aModel.KaDoolae = Util.strToDoubleDefaultZero(varA.getKaDoolae());
                        aModel.KaGebGeaw = Util.strToDoubleDefaultZero(varA.getKaGebGeaw());


                        aModel.KaPan = Util.strToDoubleDefaultZero(varA.getKaPan());
                        aModel.KaPuy = Util.strToDoubleDefaultZero(varA.getKaPuy());
                        aModel.KaYaplab = Util.strToDoubleDefaultZero(varA.getKaYaplab());
                        aModel.KaWassaduUn = Util.strToDoubleDefaultZero(varA.getKaWassaduUn());

                        aModel.KaChaoTDin = Util.strToDoubleDefaultZero(varA.getKaChaoTDin());

                        aModel.PonPalid = Util.strToDoubleDefaultZero(varA.getPonPalid());
                        aModel.predictPrice = Util.strToDoubleDefaultZero(varA.getRaka());
                        aModel.AttraDokbia = Util.strToDoubleDefaultZero(varA.getAttraDokbia());
                        aModel.KaNardPlangTDin = Util.strToDoubleDefaultZero(varA.getKaNardPlangTDin());


                        h.group1_item_2.setText(varA.KaTreamDin);
                        h.group1_item_3.setText(varA.KaPluk);
                        h.group1_item_4.setText(varA.KaDoolae);
                        h.group1_item_5.setText(varA.KaGebGeaw);
                        h.group1_item_7.setText(varA.KaPan);
                        h.group1_item_8.setText(varA.KaPuy);
                        h.group1_item_9.setText(varA.KaYaplab);
                        h.group1_item_10.setText(varA.KaWassaduUn);
                        h.group1_item_12.setText(varA.KaChaoTDin);
                        h.group2_item_1.setText(varA.PonPalid);
                        h.group3_item_1.setText(varA.getRaka());
                        h.group4_item_1.setText(varA.AttraDokbia);

                        h.txStartUnit.setText(varA.getKaNardPlangTDin());

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
