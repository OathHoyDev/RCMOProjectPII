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
import th.go.oae.rcmo.Model.STDVarModel;
import th.go.oae.rcmo.Model.UserPlotModel;
import th.go.oae.rcmo.Model.calculate.CalculateResultModel;
import th.go.oae.rcmo.Model.calculate.FormulaBModel;
import th.go.oae.rcmo.Module.mGetPlotDetail;
import th.go.oae.rcmo.Module.mGetVariable;
import th.go.oae.rcmo.Module.mVarPlanB;
import th.go.oae.rcmo.Util.BitMapHelper;
import th.go.oae.rcmo.Util.InputFilterMinMax;
import th.go.oae.rcmo.Util.PlanBTextWatcher;
import th.go.oae.rcmo.Util.ServiceInstance;
import th.go.oae.rcmo.Util.Util;
import th.go.oae.rcmo.View.DialogCalculateResult;
/**
 * Created by Taweesin on 27/6/2559.
 */
public class PBProdDetailCalculateFmentB extends Fragment implements View.OnClickListener {
    UserPlotModel userPlotModel;
    boolean isCalIncludeOption = false;
    private Context context;
    List<STDVarModel> stdVarModelList = new ArrayList<STDVarModel>();
    FormulaBModel formulaModel;
    ViewHolder h = new ViewHolder();
    mGetPlotDetail.mRespBody mGetPlotDetail = new mGetPlotDetail.mRespBody();
    private boolean havePlotId = false;

    public PBProdDetailCalculateFmentB() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_prod_cal_plan_b, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        //================= Set Holder ========================================//
        context = view.getContext();
        h.group0_item_1 = (EditText) view.findViewById(R.id.group0_item_1);
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
        h.rai = (TextView) view.findViewById(R.id.rai);
        h.ngan = (TextView) view.findViewById(R.id.ngan);
        h.wa = (TextView) view.findViewById(R.id.wa);
        h.meter = (TextView) view.findViewById(R.id.meter);

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

        h.headerLayout = (RelativeLayout) view.findViewById(R.id.headerLayout);
        h.headerLayout.setOnClickListener(this);

        h.calBtn = (TextView) view.findViewById(R.id.calBtn);
        h.calBtn.setOnClickListener(this);

        h.btnOption = (Button) view.findViewById(R.id.btnOption);
        h.btnOption.setOnClickListener(this);

        h.raiLabel = (TextView) view.findViewById(R.id.raiLabel);
        h.nganLabel = (TextView) view.findViewById(R.id.nganLabel);
        h.waLabel = (TextView) view.findViewById(R.id.waLabel);

//=========================== Setup data ==========================================/

        userPlotModel = PBProductDetailActivity.userPlotModel;

        //13 อ้อยโรงงาน
        if(!userPlotModel.getPrdID().equals("13")) {
            ((TextView) view.findViewById(R.id.group0_header)).setText("จำนวนรอบ(มีด)");
            ((TextView) view.findViewById(R.id.group0_unit_1)).setText("รวมปีปลูก");
        }else{
            ((TextView) view.findViewById(R.id.group0_header)).setText("จำนวนปี");
            ((TextView) view.findViewById(R.id.group0_unit_1)).setText("ปี(ตอ)ไม่รวมปีปลูก");

        }

        formulaModel = new FormulaBModel();

        API_getVariable(userPlotModel.getPrdID(), userPlotModel.getFisheryType(),formulaModel);

        if (!userPlotModel.getPlotID().equals("") && userPlotModel.getPlotID().equals("0")) {
            initVariableDataFromDB();
        }else{
            //formulaModel.KaNardPlangTDin       =  Util.strToDoubleDefaultZero(userPlotModel.getPlotRai());
            formulaModel.Rai = Util.strToDoubleDefaultZero (userPlotModel.getPlotRai());
            formulaModel.Ngan = Util.strToDoubleDefaultZero(userPlotModel.getPlotNgan());
            formulaModel.Wa = Util.strToDoubleDefaultZero(userPlotModel.getPlotWa());
            formulaModel.Meter = Util.strToDoubleDefaultZero(userPlotModel.getPlotMeter());
            formulaModel.calculate();
            setUpCalUI(formulaModel);
        }

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setUI();
        setAction();

        return view;
    }


    private void setAction() {

        h.rai.addTextChangedListener(new PlanBTextWatcher(h.rai, h,formulaModel, "KaSermOuppakorn,KaSiaOkardOuppakorn"));
        h.wa.addTextChangedListener(new PlanBTextWatcher(h.wa, h,formulaModel, "KaSermOuppakorn,KaSiaOkardOuppakorn"));
        h.ngan.addTextChangedListener(new PlanBTextWatcher(h.ngan, h,formulaModel, "KaSermOuppakorn,KaSiaOkardOuppakorn"));
        h.meter.addTextChangedListener(new PlanBTextWatcher(h.meter, h,formulaModel, "KaSermOuppakorn,KaSiaOkardOuppakorn"));
        h.group0_item_1.addTextChangedListener(new PlanBTextWatcher(h.group0_item_1, h,formulaModel, ""));

        h.group1_item_2.addTextChangedListener(new PlanBTextWatcher(h.group1_item_2, h, "Karang,KaSiaOkardLongtoon"));
        h.group1_item_3.addTextChangedListener(new PlanBTextWatcher(h.group1_item_3, h, "Karang,KaSiaOkardLongtoon"));
        h.group1_item_4.addTextChangedListener(new PlanBTextWatcher(h.group1_item_4, h, "Karang,KaSiaOkardLongtoon"));
        h.group1_item_5.addTextChangedListener(new PlanBTextWatcher(h.group1_item_5, h, "Karang,KaSiaOkardLongtoon"));

        h.group1_item_7.addTextChangedListener(new PlanBTextWatcher(h.group1_item_7, h, "KaWassadu,KaSiaOkardLongtoon"));
        h.group1_item_8.addTextChangedListener(new PlanBTextWatcher(h.group1_item_8, h, "KaWassadu,KaSiaOkardLongtoon"));
        h.group1_item_9.addTextChangedListener(new PlanBTextWatcher(h.group1_item_9, h, "KaWassadu,KaSiaOkardLongtoon"));
        h.group1_item_10.addTextChangedListener(new PlanBTextWatcher(h.group1_item_10, h, "KaWassadu,KaSiaOkardLongtoon"));
        h.group1_item_12.addTextChangedListener(new PlanBTextWatcher(h.group1_item_12, h, ""));

        h.group2_item_1.addTextChangedListener(new PlanBTextWatcher(h.group2_item_1, h, ""));

        h.group3_item_1.addTextChangedListener(new PlanBTextWatcher(h.group3_item_1, h, ""));

        h.group4_item_1.addTextChangedListener(new PlanBTextWatcher(h.group4_item_1, h, "KaSiaOkardLongtoon"));
    }

    private void initVariableDataFromDB() {
        API_getPlotDetailANDBlinding(userPlotModel.getPlotID(), formulaModel);


        // set variable to json obj
        //userPlotModel.setVarValue(ProductService.genJsonPlanAVariable(formulaModel));

    }


    private void setUpCalUI(FormulaBModel model) {
        h.group1_item_1.setText(Util.dobbleToStringNumber(model.KaRang));
        h.group1_item_6.setText(Util.dobbleToStringNumber(model.KaWassadu));
        h.group1_item_11.setText(Util.dobbleToStringNumber(model.KaSiaOkardLongtoon));
       // h.group1_item_12.setText(Util.dobbleToStringNumber(model.KaChaoTDin));
        h.group1_item_13.setText(Util.dobbleToStringNumber(model.costKaSermOuppakorn));
        h.group1_item_14.setText(Util.dobbleToStringNumber(model.costKaSiaOkardOuppakorn));


    }

    private void bindingData(FormulaBModel model) {

        Log.d("Year : ","year   --->"+h.group0_item_1.getText().toString());
        model.Year =  Util.strToDoubleDefaultZero(h.group0_item_1.getText().toString());
        model.KaTreamDin = Util.strToDoubleDefaultZero(h.group1_item_2.getText().toString());
        model.KaPluk = Util.strToDoubleDefaultZero(h.group1_item_3.getText().toString());
        model.KaDoolae = Util.strToDoubleDefaultZero(h.group1_item_4.getText().toString());
        model.KaGebGeaw = Util.strToDoubleDefaultZero(h.group1_item_5.getText().toString());
        model.KaPan = Util.strToDoubleDefaultZero(h.group1_item_7.getText().toString());
        model.KaPuy = Util.strToDoubleDefaultZero(h.group1_item_8.getText().toString());
        model.KaYaplab = Util.strToDoubleDefaultZero(h.group1_item_9.getText().toString());
        model.KaWassaduUn = Util.strToDoubleDefaultZero(h.group1_item_10.getText().toString());
        model.KaChaoTDin = Util.strToDoubleDefaultZero(h.group1_item_12.getText().toString());
        model.PonPalid = Util.strToDoubleDefaultZero(h.group2_item_1.getText().toString());
        model.predictPrice = Util.strToDoubleDefaultZero(h.group3_item_1.getText().toString());
        model.AttraDokbia = Util.strToDoubleDefaultZero(h.group4_item_1.getText().toString());
        model.Rai = Util.strToDoubleDefaultZero (h.rai.getText().toString());
        model.Ngan = Util.strToDoubleDefaultZero(h.ngan.getText().toString());
        model.Wa = Util.strToDoubleDefaultZero(h.wa.getText().toString());
        model.Meter = Util.strToDoubleDefaultZero(h.meter.getText().toString());


    }

    private void setUI() {

        String imgName = ServiceInstance.productIMGMap.get(Integer.valueOf(userPlotModel.getPrdID()));
        if (imgName != null) {
            h.productIconImg.setImageBitmap(BitMapHelper.decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier(imgName, "drawable", getActivity().getPackageName()), R.dimen.iccircle_img_width, R.dimen.iccircle_img_height));
        }
        if (!userPlotModel.getPlotID().equals("") && !userPlotModel.getPlotID().equals("0")) {
            API_getVariable(userPlotModel.getPrdID(), userPlotModel.getFisheryType(),formulaModel);
            initVariableDataFromDB();
            havePlotId = true;
        } else {
            h.rai.setText(Util.strToDobbleToStrFormat(userPlotModel.getPlotRai()));
            h.ngan.setText(Util.strToDobbleToStrFormat(userPlotModel.getPlotNgan()));
            h.wa.setText(Util.strToDobbleToStrFormat(userPlotModel.getPlotWa()));
            h.meter.setText(Util.strToDobbleToStrFormat(userPlotModel.getPlotMeter()));

            checkVisibility( Util.strToDoubleDefaultZero(userPlotModel.getPlotRai())
                    ,Util.strToDoubleDefaultZero(userPlotModel.getPlotNgan())
                    ,Util.strToDoubleDefaultZero(userPlotModel.getPlotWa()));

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
            calculateResultModel.formularCode = "B";
            calculateResultModel.calculateResult = formulaModel.calProfitLoss;
            calculateResultModel.unit_t1 = "บาท/ไร่" ;
            calculateResultModel.value_t1 = formulaModel.calProfitLossPerRai ;
            calculateResultModel.productName = userPlotModel.getPrdValue();
            calculateResultModel.mPlotSuit = PBProductDetailActivity.mPlotSuit;
            calculateResultModel.compareStdResult = formulaModel.calSumCost - formulaModel.TontumMattratarn;

            DialogCalculateResult.userPlotModel = userPlotModel;
            DialogCalculateResult.calculateResultModel = calculateResultModel;

            //  String str = new StringEntity(ProductService.genJsonPlanAVariable(formulaModel), HTTP.UTF_8);


            userPlotModel.setVarValue(ProductService.genJsonPlanVariable(formulaModel));


            List resultArrayResult = new ArrayList();

            String[] tontoonCal_1 = {"ต้นทุนรวมเกษตรกร", String.format("%,.2f", formulaModel.calSumCost), "บาท"};
            resultArrayResult.add(tontoonCal_1);

            String[] tontoonCal_2 = {"", String.format("%,.2f", formulaModel.calSumCostPerRai), "บาท/ไร่"};
            resultArrayResult.add(tontoonCal_2);

            String[] raydai_1 = {"รายได้", String.format("%,.2f", formulaModel.calIncome), "บาท"};
            resultArrayResult.add(raydai_1);

            String[] raydai_2 = {"", String.format("%,.2f", formulaModel.calIncomePerRai), "บาท/ไร่"};
            resultArrayResult.add(raydai_2);

            String[] tontoon = {"ต้นทุนเฉลี่ย", String.format("%,.2f", formulaModel.TontumMattratarn), "บาท"};
            resultArrayResult.add(tontoon);

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


            popUpEditDialog();
        }

    }

    public static class ViewHolder {
        public TextView group1_item_1, group1_item_6, group1_item_11, group1_item_13, group1_item_14;
        public EditText group0_item_1,group1_item_2, group1_item_3, group1_item_4, group1_item_5, group1_item_7, group1_item_8, group1_item_9, group1_item_10, group1_item_12;

        public EditText group2_item_1;
        public EditText group3_item_1;
        public EditText group4_item_1;
       // public TextView txStartUnit;
       public TextView rai,ngan,wa,meter;
        private ImageView productIconImg;

        private TextView  calBtn, group0_header, group1_header, group2_header, group3_header, group4_header;

        private LinearLayout group0_items,group1_items, group2_items, group3_items, group4_items;

        private ImageView group0_header_arrow,group1_header_arrow, group2_header_arrow, group3_header_arrow, group4_header_arrow;

        private Button btnOption;

        private RelativeLayout headerLayout;

        private TextView raiLabel,nganLabel,waLabel;


    }


    private void API_getVariable(String prdID, final String fisheryType,final FormulaBModel formulaModel) {

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
                    formulaModel.TontumMattratarnPerRai = Util.strToDoubleDefaultZero(var.getCS());

                    formulaModel.SumRai = ((formulaModel.Rai*4*400)+(formulaModel.Ngan*400)+(formulaModel.Wa*4)+formulaModel.Meter)/1600;
                     Log.d("Test ","Sum Rai --> "+formulaModel.SumRai);

                    h.group1_item_13.setText(Util.dobbleToStringNumber(formulaModel.KaSermOuppakorn*formulaModel.SumRai));
                    h.group1_item_14.setText(Util.dobbleToStringNumber(formulaModel.KaSiaOkardOuppakorn*formulaModel.SumRai));


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
        dialog.setContentView(R.layout.dialog_edit_plant);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        android.widget.TextView title = (android.widget.TextView) dialog.findViewById(R.id.edit_rai_label);

        final android.widget.TextView rai = (android.widget.TextView) dialog.findViewById(R.id.edit_rai);
        final android.widget.TextView ngan = (android.widget.TextView) dialog.findViewById(R.id.edit_ngan);
        final android.widget.TextView wa = (android.widget.TextView) dialog.findViewById(R.id.edit_wa);
        final android.widget.TextView meter = (android.widget.TextView) dialog.findViewById(R.id.edit_meter);

        android.widget.TextView btn_cancel = (android.widget.TextView) dialog.findViewById(R.id.cancel);
        android.widget.TextView btn_ok = (android.widget.TextView) dialog.findViewById(R.id.ok);

        title.setText("ขนาดแปลงที่ดิน");
        if("0".equals(h.rai.getText().toString()) || "".equals(h.rai.getText().toString())) {
            rai.setText("");
        }else{
            rai.setText(Util.clearStrNumberFormat(h.rai.getText().toString()));
        }
        if("0".equals(h.ngan.getText().toString()) || "".equals(h.ngan.getText().toString())) {
            ngan.setText("");
        }else{
            ngan.setText(Util.clearStrNumberFormat(h.ngan.getText().toString()));
        }
        if("0".equals(h.wa.getText().toString()) || "".equals(h.wa.getText().toString())) {
            wa.setText("");
        }else{
            wa.setText(Util.clearStrNumberFormat(h.wa.getText().toString()));
        }
        if("0".equals(h.meter.getText().toString()) || "".equals(h.meter.getText().toString())) {
            meter.setText("");
        }else{
            meter.setText(Util.clearStrNumberFormat(h.meter.getText().toString()));
        }

        ngan.setFilters(new InputFilter[]{ new InputFilterMinMax(0, 4)});
        wa.setFilters(new InputFilter[]{ new InputFilterMinMax(0, 100)});
        meter.setFilters(new InputFilter[]{ new InputFilterMinMax(0, 400)});

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                h.rai.setText  (rai.getText());
                h.ngan.setText (ngan.getText());
                h.wa.setText   (wa.getText());
                h.meter.setText(meter.getText());

                userPlotModel.setPlotRai  (Util.clearStrNumberFormat(rai.getText().toString()));
                userPlotModel.setPlotNgan (Util.clearStrNumberFormat(ngan.getText().toString()));
                userPlotModel.setPlotWa   (Util.clearStrNumberFormat(wa.getText().toString()));
                userPlotModel.setPlotMeter(Util.clearStrNumberFormat(meter.getText().toString()));

                checkVisibility( Util.strToDoubleDefaultZero(rai.getText().toString())
                        ,Util.strToDoubleDefaultZero((ngan.getText().toString()))
                        ,Util.strToDoubleDefaultZero(wa.getText().toString()));
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


    private void API_getPlotDetailANDBlinding(String plotID, final FormulaBModel model) {
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
                        mVarPlanB var = new Gson().fromJson(plotDetail.getVarValue(), mVarPlanB.class);

                        model.Year = Util.strToDoubleDefaultZero(var.getJumNoun());
                        model.KaTreamDin = Util.strToDoubleDefaultZero(var.getKaTreamDin());
                        model.KaPluk = Util.strToDoubleDefaultZero(var.getKaPluk());
                        model.KaDoolae = Util.strToDoubleDefaultZero(var.getKaDoolae());
                        model.KaGebGeaw = Util.strToDoubleDefaultZero(var.getKaGebGeaw());
                        model.KaPan = Util.strToDoubleDefaultZero(var.getKaPan());
                        model.KaPuy = Util.strToDoubleDefaultZero(var.getKaPuy());
                        model.KaYaplab = Util.strToDoubleDefaultZero(var.getKaYaplab());
                        model.KaWassaduUn = Util.strToDoubleDefaultZero(var.getKaWassaduUn());
                        model.KaChaoTDin = Util.strToDoubleDefaultZero(var.getKaChoaTDin());
                        model.PonPalid = Util.strToDoubleDefaultZero(var.getPonPalid());
                        model.predictPrice = Util.strToDoubleDefaultZero(var.getRaka());
                        model.AttraDokbia = Util.strToDoubleDefaultZero(var.getAttraDokbia());
                        model.Rai              = Util.strToDoubleDefaultZero(plotDetail.getPlotRai());
                        model.Ngan             = Util.strToDoubleDefaultZero(plotDetail.getPlotNgan());
                        model.Wa               = Util.strToDoubleDefaultZero(plotDetail.getPlotWa());
                        model.Meter            = Util.strToDoubleDefaultZero(plotDetail.getPlotMeter());

                        model.isCalIncludeOption =  var.isCalIncludeOption();
                        setCalKaSermOption( var.isCalIncludeOption());
                        isCalIncludeOption = var.isCalIncludeOption();

                        h.group0_item_1.setText(Util.dobbleToStringNumber(Util.strToDoubleDefaultZero(var.JumNoun)));

                        h.group1_item_2.setText(Util.dobbleToStringNumber(Util.strToDoubleDefaultZero(var.KaTreamDin)));
                        h.group1_item_3.setText(Util.dobbleToStringNumber(Util.strToDoubleDefaultZero(var.KaPluk)));
                        h.group1_item_4.setText(Util.dobbleToStringNumber(Util.strToDoubleDefaultZero(var.KaDoolae)));
                        h.group1_item_5.setText(Util.dobbleToStringNumber(Util.strToDoubleDefaultZero(var.KaGebGeaw)));
                        h.group1_item_7.setText(Util.dobbleToStringNumber(Util.strToDoubleDefaultZero(var.KaPan)));
                        h.group1_item_8.setText(Util.dobbleToStringNumber(Util.strToDoubleDefaultZero(var.KaPuy)));
                        h.group1_item_9.setText(Util.dobbleToStringNumber(Util.strToDoubleDefaultZero(var.KaYaplab)));
                        h.group1_item_10.setText(Util.dobbleToStringNumber(Util.strToDoubleDefaultZero(var.KaWassaduUn)));
                        h.group1_item_12.setText(Util.dobbleToStringNumber(Util.strToDoubleDefaultZero(var.KaChoaTDin)));
                        h.group2_item_1.setText(Util.dobbleToStringNumber(Util.strToDoubleDefaultZero(var.PonPalid)));
                        h.group3_item_1.setText(Util.dobbleToStringNumber(Util.strToDoubleDefaultZero(var.getRaka())));
                        h.group4_item_1.setText(Util.dobbleToStringNumber(Util.strToDoubleDefaultZero(var.AttraDokbia)));

                        h.rai.setText(Util.strToDobbleToStrFormat(plotDetail.getPlotRai()));
                        h.ngan.setText(Util.strToDobbleToStrFormat(plotDetail.getPlotNgan()));
                        h.wa.setText(Util.strToDobbleToStrFormat(plotDetail.getPlotWa()));
                        h.meter.setText(Util.strToDobbleToStrFormat(plotDetail.getPlotMeter()));

                        // h.group1_item_13.setText(String.valueOf(formulaModel.KaSermOuppakorn*var.getYear()));
                       // h.group1_item_14.setText(String.valueOf(formulaModel.KaSiaOkardOuppakorn*var.getYear()));
                        formulaModel.calculate();

                        setUpCalUI(formulaModel);
                    } else {
                        h.rai.setText(Util.strToDobbleToStrFormat(plotDetail.getPlotRai()));
                        h.ngan.setText(Util.strToDobbleToStrFormat(plotDetail.getPlotNgan()));
                        h.wa.setText(Util.strToDobbleToStrFormat(plotDetail.getPlotWa()));
                        h.meter.setText(Util.strToDobbleToStrFormat(plotDetail.getPlotMeter()));
                    }

                    checkVisibility( Util.strToDoubleDefaultZero(plotDetail.getPlotRai())
                            ,Util.strToDoubleDefaultZero(plotDetail.getPlotNgan())
                            ,Util.strToDoubleDefaultZero(plotDetail.getPlotWa()));
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
            h.btnOption.setBackgroundResource(R.drawable.radio_cal_green_check);
            isCalIncludeOption = true;
        } else {

            h.btnOption.setBackgroundResource(R.drawable.radio_cal_green);
            isCalIncludeOption = false;
        }
    }

    private void  checkVisibility(double rai ,double ngan,double wa){
        Log.d("checkVisibility","Rai = "+rai);
        Log.d("checkVisibility","ngan = "+ngan);
        Log.d("checkVisibility","wa = "+wa);

        if(rai == 0){
            h.rai.setVisibility(View.INVISIBLE);
            h.raiLabel.setVisibility(View.GONE);
        }else{
            h.rai.setVisibility(View.VISIBLE);
            h.raiLabel.setVisibility(View.VISIBLE);
        }

        if(ngan == 0){
            h.ngan.setVisibility(View.GONE);
            h.nganLabel.setVisibility(View.GONE);
        }else{
            h.ngan.setVisibility(View.VISIBLE);
            h.nganLabel.setVisibility(View.VISIBLE);
        }

        if(wa == 0){
            h.wa.setVisibility(View.GONE);
            h.waLabel.setVisibility(View.GONE);
        }else{
            h.wa.setVisibility(View.VISIBLE);
            h.waLabel.setVisibility(View.VISIBLE);
        }
    }
}