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
import th.go.oae.rcmo.Model.calculate.FormulaIModel;
import th.go.oae.rcmo.Module.mGetPlotDetail;
import th.go.oae.rcmo.Module.mGetVariable;
import th.go.oae.rcmo.Module.mVarPlanI;
import th.go.oae.rcmo.Util.BitMapHelper;
import th.go.oae.rcmo.Util.InputFilterMinMax;
import th.go.oae.rcmo.Util.PlanITextWatcher;
import th.go.oae.rcmo.Util.ServiceInstance;
import th.go.oae.rcmo.Util.Util;
import th.go.oae.rcmo.View.DialogCalculateResult;
import th.go.oae.rcmo.View.DialogChoice;

/**
 * Created by Taweesin on 27/6/2559.
 */
public class PBProdDetailCalculateFmentI extends Fragment implements View.OnClickListener {
    UserPlotModel userPlotModel;
    ViewHolder h = new ViewHolder();
    View view;
    FormulaIModel formulaModel;

    private boolean havePlotId = false;

    th.go.oae.rcmo.Module.mGetPlotDetail.mRespBody mGetPlotDetail = new mGetPlotDetail.mRespBody();

    Context context;

    boolean isCalIncludeOption = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_prod_cal_plan_i, container, false);
        context = view.getContext();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

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
        h.group2_item_2 = (EditText) view.findViewById(R.id.group2_item_2);
        h.group2_item_3 = (EditText) view.findViewById(R.id.group2_item_3);
        h.group2_item_4 = (EditText) view.findViewById(R.id.group2_item_4);
        h.group2_item_5 = (EditText) view.findViewById(R.id.group2_item_5);
        h.group2_item_6 = (TextView) view.findViewById(R.id.group2_item_6);

        h.group2_item_7 = (TextView) view.findViewById(R.id.group2_item_7);
        h.group2_item_8 = (TextView) view.findViewById(R.id.group2_item_8);


        h.productIconImg = (ImageView) view.findViewById(R.id.productIconImg);
        h.rai = (TextView) view.findViewById(R.id.rai);
        h.ngan = (TextView) view.findViewById(R.id.ngan);
        h.wa = (TextView) view.findViewById(R.id.wa);
        h.rookKung = (TextView) view.findViewById(R.id.rookKung);

        h.group1_items = (LinearLayout) view.findViewById(R.id.group1_items);
        h.group1_header = (TextView) view.findViewById(R.id.group1_header);
        h.group1_header_arrow = (ImageView) view.findViewById(R.id.group1_header_arrow);
        h.group1_header.setOnClickListener(this);

        h.group2_items = (LinearLayout) view.findViewById(R.id.group2_items);
        h.group2_header = (TextView) view.findViewById(R.id.group2_header);
        h.group2_header_arrow = (ImageView) view.findViewById(R.id.group2_header_arrow);
        h.group2_header.setOnClickListener(this);

        h.headerLayout = (RelativeLayout) view.findViewById(R.id.headerLayout);
        h.headerLayout.setOnClickListener(this);

        h.calBtn = (TextView) view.findViewById(R.id.calBtn);
        h.calBtn.setOnClickListener(this);

        h.raiLabel = (TextView) view.findViewById(R.id.raiLabel);
        h.nganLabel = (TextView) view.findViewById(R.id.nganLabel);
        h.waLabel = (TextView) view.findViewById(R.id.waLabel);

        h.btnOption = (Button) view.findViewById(R.id.btnOption);
        h.btnOption.setOnClickListener(this);

        h.group2_7_item = (LinearLayout) view.findViewById(R.id.group2_7_item);
        h.group2_8_item = (LinearLayout) view.findViewById(R.id.group2_8_item);

        userPlotModel = PBProductDetailActivity.userPlotModel;

        FormulaIModel aModel = new FormulaIModel();
        formulaModel = aModel;

        API_getVariable(userPlotModel.getPrdID(), userPlotModel.getFisheryType());
        if (!userPlotModel.getPlotID().equals("") && userPlotModel.getPlotID().equals("0")) {
            initVariableDataFromDB();
        }else{
            formulaModel.Rai           =  Util.strToDoubleDefaultZero( userPlotModel.getPondRai());
            formulaModel.Ngan          =  Util.strToDoubleDefaultZero(userPlotModel.getPondNgan());
            formulaModel.TarangWa      =  Util.strToDoubleDefaultZero( userPlotModel.getPondWa());
            formulaModel.rookKung      =  Util.strToDoubleDefaultZero(userPlotModel.getFisheryNumber());

            formulaModel.calculate();
            setUpCalUI(formulaModel);
        }

        setUI();
        setAction();

        return view;
    }


    private void setAction() {

        h.rookKung.addTextChangedListener(new PlanITextWatcher(h.rookKung, h, "calRakaPan"));

        h.rai.addTextChangedListener(new PlanITextWatcher(h.rai, h, "calRayDaiChalia"));
        h.ngan.addTextChangedListener(new PlanITextWatcher(h.ngan, h, "calRayDaiChalia"));
        h.wa.addTextChangedListener(new PlanITextWatcher(h.wa, h, "calRayDaiChalia"));

        h.group1_item_1.addTextChangedListener(new PlanITextWatcher(h.group1_item_1, h, "calRakaPan"));
        h.group1_item_2.addTextChangedListener(new PlanITextWatcher(h.group1_item_2, h, "calKaSiaOkardLongtoon"));
        h.group1_item_3.addTextChangedListener(new PlanITextWatcher(h.group1_item_3, h, "calKaSiaOkardLongtoon"));
        h.group1_item_4.addTextChangedListener(new PlanITextWatcher(h.group1_item_4, h, "calKaSiaOkardLongtoon"));
        h.group1_item_5.addTextChangedListener(new PlanITextWatcher(h.group1_item_5, h, "calKaSiaOkardLongtoon"));
        h.group1_item_6.addTextChangedListener(new PlanITextWatcher(h.group1_item_6, h, "calKaSiaOkardLongtoon"));
        h.group1_item_7.addTextChangedListener(new PlanITextWatcher(h.group1_item_7, h, "calKaSiaOkardLongtoon"));
        h.group1_item_8.addTextChangedListener(new PlanITextWatcher(h.group1_item_8, h, "calKaSiaOkardLongtoon"));
        h.group1_item_9.addTextChangedListener(new PlanITextWatcher(h.group1_item_9, h, "calKaSiaOkardLongtoon"));
        h.group1_item_10.addTextChangedListener(new PlanITextWatcher(h.group1_item_10, h, "calKaRang"));
        h.group1_item_11.addTextChangedListener(new PlanITextWatcher(h.group1_item_11, h, "calKaRang"));
        h.group1_item_12.addTextChangedListener(new PlanITextWatcher(h.group1_item_12, h, "calKaSiaOkardLongtoon"));
        h.group1_item_13.addTextChangedListener(new PlanITextWatcher(h.group1_item_13, h, "calKaSiaOkardLongtoon"));
        h.group1_item_14.addTextChangedListener(new PlanITextWatcher(h.group1_item_14, h, ""));

        h.group2_item_1.addTextChangedListener(new PlanITextWatcher(h.group2_item_1, h, "calRayDaiTungmod"));
        h.group2_item_2.addTextChangedListener(new PlanITextWatcher(h.group2_item_2, h, "calRayDaiTungmod"));
        h.group2_item_3.addTextChangedListener(new PlanITextWatcher(h.group2_item_3, h, "calRayDaiChalia"));
        h.group2_item_5.addTextChangedListener(new PlanITextWatcher(h.group2_item_5, h, "calKaRang"));

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
            h.rookKung.setText(Util.dobbleToStringNumberWithClearDigit(Util.strToDoubleDefaultZero(userPlotModel.getFisheryNumber())));

            checkVisibility( Util.strToDoubleDefaultZero(userPlotModel.getPondRai())
                    ,Util.strToDoubleDefaultZero(userPlotModel.getPondNgan())
                    ,Util.strToDoubleDefaultZero(userPlotModel.getPondWa()));
        }

        if (isCalIncludeOption) {

            if (isCalIncludeOption) {
                h.btnOption.setBackgroundResource(R.drawable.radio_cal_blue_check);
               // h.group2_7_item.setVisibility(View.VISIBLE);
              //  h.group2_8_item.setVisibility(View.VISIBLE);
            } else {
                h.btnOption.setBackgroundResource(R.drawable.radio_cal_blue);
              //  h.group2_7_item.setVisibility(View.GONE);
              //  h.group2_8_item.setVisibility(View.GONE);
            }

            formulaModel.isCalIncludeOption = isCalIncludeOption;
        }

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.calBtn) {
            if(validateInputData()) {
                bindingData(formulaModel);
                formulaModel.calculate();
                setUpCalUI(formulaModel);
                //  Util.showDialogAndDismiss(context,"คำนวนสำเร็จ : "+formulaModel.KumraiKadtoon);

                CalculateResultModel calculateResultModel = new CalculateResultModel();
                calculateResultModel.formularCode = "I";
                calculateResultModel.calculateResult = formulaModel.KumraiKadtoon;
                calculateResultModel.productName = userPlotModel.getPrdValue();
                calculateResultModel.unit_t1 = "บาท/ไร่.";
                calculateResultModel.value_t1 = formulaModel.KumraiKadtoonTorRai;
                calculateResultModel.unit_t2 = "บาท/กก.";
                calculateResultModel.value_t2 = formulaModel.KumraiKadtoonTorKilo;
                calculateResultModel.mPlotSuit = PBProductDetailActivity.mPlotSuit;
                calculateResultModel.compareStdResult = 0;

                DialogCalculateResult.userPlotModel = userPlotModel;
                DialogCalculateResult.calculateResultModel = calculateResultModel;

                userPlotModel.setVarValue(ProductService.genJsonPlanVariable(formulaModel));
                userPlotModel.setFisheryNumType("1");
                List resultArrayResult = new ArrayList();

                String[] tontoonCal_1 = {"ต้นทุนทั้งหมด", String.format("%,.2f", formulaModel.TontoonTungmod), "บาท"};
                resultArrayResult.add(tontoonCal_1);

                String[] tontoonCal_2 = {"", String.format("%,.2f", formulaModel.TontoonTorKilo), "บาท/กก."};
                resultArrayResult.add(tontoonCal_2);

                String[] raydai_1 = {"", String.format("%,.2f", formulaModel.TontoonTorRai), "บาท/ไร่"};
                resultArrayResult.add(raydai_1);

                DialogCalculateResult.calculateResultModel.resultList = resultArrayResult;

                new DialogCalculateResult(context).Show();
            }
        }else if(v.getId() == R.id.btnOption) {

            if(isCalIncludeOption){
                h.btnOption.setBackgroundResource(R.drawable.radio_cal_blue);
                isCalIncludeOption = false;
                formulaModel.isCalIncludeOption =false;
               // h.group2_7_item.setVisibility(View.GONE);
              //  h.group2_8_item.setVisibility(View.GONE);
            }else{
                h.btnOption.setBackgroundResource(R.drawable.radio_cal_blue_check);
                isCalIncludeOption = true;
                formulaModel.isCalIncludeOption = true;
               // h.group2_7_item.setVisibility(View.VISIBLE);
               // h.group2_8_item.setVisibility(View.VISIBLE);
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
        }if (v.getId() == R.id.headerLayout){

            popUpEditDialog();

        }

    }

    private void initVariableDataFromDB() {
        API_getPlotDetailANDBlinding(userPlotModel.getPlotID(), formulaModel);
    }

    private void setUpCalUI(FormulaIModel aModel){
        h.group1_item_9.setText(Util.dobbleToStringNumber(aModel.calKaRang));
        h.group1_item_2.setText(Util.dobbleToStringNumber(aModel.calRakaPan));

        h.group2_item_3.setText(Util.dobbleToStringNumber(aModel.calRayDaiTungmod));
        h.group2_item_4.setText(Util.dobbleToStringNumber(aModel.calRayDaiChalia));
        h.group2_item_6.setText(Util.dobbleToStringNumber(aModel.calKaSiaOkardLongtoon));
    }

    private void bindingData(FormulaIModel aModel) {
        //  aModel.KaRang = 10;

        aModel.Rai = Util.strToDoubleDefaultZero(h.rai.getText().toString());
        aModel.Ngan = Util.strToDoubleDefaultZero(h.ngan.getText().toString());
        aModel.TarangWa = Util.strToDoubleDefaultZero(h.wa.getText().toString());
        aModel.rookKung = Util.strToDoubleDefaultZero(h.rookKung.getText().toString());

        aModel.RakaTuaLa = Util.strToDoubleDefaultZero(h.group1_item_1.getText().toString());
       // aModel.RakaPan = Util.strToDoubleDefaultZero(h.group1_item_2.getText().toString());
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

        aModel.RayaWelaTeeLeang = Util.strToDoubleDefaultZero(h.group2_item_5.getText().toString());



    }

    public static class ViewHolder {

        // Header
        public TextView rai, ngan, wa, rookKung;
        private ImageView productIconImg;

        // Group 1
        public EditText group1_item_1, group1_item_3, group1_item_4, group1_item_5, group1_item_6, group1_item_7, group1_item_8;
        public TextView group1_item_9,group1_item_2;
        public EditText group1_item_10, group1_item_11, group1_item_12, group1_item_13 , group1_item_14;

        // Group 2
        public EditText group2_item_1, group2_item_2, group2_item_3, group2_item_4, group2_item_5;
        public TextView group2_item_6, group2_item_7, group2_item_8;

        public LinearLayout group2_7_item , group2_8_item;

        private TextView calBtn, group1_header, group2_header;

        private LinearLayout group1_items, group2_items;

        private ImageView group1_header_arrow, group2_header_arrow;

        private Button btnOption;

        private RelativeLayout headerLayout;

        private TextView raiLabel,nganLabel,waLabel;
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
                  //  h.group2_item_7.setText(String.valueOf(formulaModel.KaSermOuppakorn));
                  //  h.group2_item_8.setText(String.valueOf(formulaModel.KaSiaOkardOuppakorn));

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

                        aModel.Rai = Util.strToDoubleDefaultZero(plotDetail.getPondRai());
                        aModel.Ngan = Util.strToDoubleDefaultZero(plotDetail.getPondNgan());
                        aModel.TarangWa = Util.strToDoubleDefaultZero(plotDetail.getPondWa());;

                        aModel.rookKung = Util.strToDoubleDefaultZero(plotDetail.getFisheryNumber());

                        aModel.RakaTuaLa = Util.strToDoubleDefaultZero(varI.getRakaTuaLa());
                        aModel.KaAHan = Util.strToDoubleDefaultZero(varI.getKaAHan());
                        aModel.KaYa = Util.strToDoubleDefaultZero(varI.getKaYa());
                        aModel.KaSankemee = Util.strToDoubleDefaultZero(varI.getKaSankemee());
                        aModel.KaNamMan = Util.strToDoubleDefaultZero(varI.getKaNamMan());
                        aModel.KaFai = Util.strToDoubleDefaultZero(varI.getKaFai());
                        aModel.KaRokRain = Util.strToDoubleDefaultZero(varI.getKaRokRain());
                        aModel.KaLeang = Util.strToDoubleDefaultZero(varI.getKaLeang());
                        aModel.KaJub = Util.strToDoubleDefaultZero(varI.getKaJub());
                        aModel.KaSomsamOuppakorn = Util.strToDoubleDefaultZero(varI.getKaSomsamOuppakorn());
                        aModel.KaChaiJay = Util.strToDoubleDefaultZero(varI.getKaChaiJay());
                        aModel.KaChaoTDin = Util.strToDoubleDefaultZero(varI.getKaChaoTDin());

                        aModel.PonPalidKung = Util.strToDoubleDefaultZero(varI.getPonPalidKung());
                        aModel.RakaChalia = Util.strToDoubleDefaultZero(varI.getRakaChalia());
                        aModel.RayaWelaTeeLeang = Util.strToDoubleDefaultZero(varI.getRayaWelaTeeLeang());

                       // aModel.KaSermOuppakorn = varI.getKaSermOuppakorn();
                      //  aModel.KaSiaOkardOuppakorn = varI.getKaSiaOkardOuppakorn();

                        aModel.isCalIncludeOption =  varI.isCalIncludeOption();
                        setCalKaSermOption( varI.isCalIncludeOption());
                        isCalIncludeOption = varI.isCalIncludeOption();

                        h.rai.setText(Util.strToDobbleToStrFormat(plotDetail.getPondRai()));
                        h.ngan.setText(Util.strToDobbleToStrFormat(plotDetail.getPondNgan()));
                        h.wa.setText(Util.strToDobbleToStrFormat(plotDetail.getPondWa()));
                        h.rookKung.setText(Util.strToDobbleToStrFormat(plotDetail.getFisheryNumber()));

                        h.group1_item_1.setText(Util.dobbleToStringNumber(Util.strToDoubleDefaultZero(varI.RakaTuaLa)));
                        h.group1_item_3.setText(Util.dobbleToStringNumber(Util.strToDoubleDefaultZero(varI.KaAHan)));
                        h.group1_item_4.setText(Util.dobbleToStringNumber(Util.strToDoubleDefaultZero(varI.KaYa)));
                        h.group1_item_5.setText(Util.dobbleToStringNumber(Util.strToDoubleDefaultZero(varI.KaSankemee)));
                        h.group1_item_6.setText(Util.dobbleToStringNumber(Util.strToDoubleDefaultZero(varI.KaNamMan)));
                        h.group1_item_7.setText(Util.dobbleToStringNumber(Util.strToDoubleDefaultZero(varI.KaFai)));
                        h.group1_item_8.setText(Util.dobbleToStringNumber(Util.strToDoubleDefaultZero(varI.KaRokRain)));

                        h.group1_item_10.setText(Util.dobbleToStringNumber(Util.strToDoubleDefaultZero(varI.KaLeang)));
                        h.group1_item_11.setText(Util.dobbleToStringNumber(Util.strToDoubleDefaultZero(varI.KaJub)));
                        h.group1_item_12.setText(Util.dobbleToStringNumber(Util.strToDoubleDefaultZero(varI.KaSomsamOuppakorn)));
                        h.group1_item_13.setText(Util.dobbleToStringNumber(Util.strToDoubleDefaultZero(varI.KaChaiJay)));
                        h.group1_item_14.setText(Util.dobbleToStringNumber(Util.strToDoubleDefaultZero(varI.KaChaoTDin)));

                        h.group2_item_1.setText(Util.dobbleToStringNumber(Util.strToDoubleDefaultZero(varI.PonPalidKung)));
                        h.group2_item_2.setText(Util.dobbleToStringNumber(Util.strToDoubleDefaultZero(varI.RakaChalia)));
                        h.group2_item_5.setText(Util.dobbleToStringNumber(Util.strToDoubleDefaultZero(varI.RayaWelaTeeLeang)));


                        formulaModel.calculate();

                        setUpCalUI(formulaModel);
                    }else{
                        h.rai.setText(Util.strToDobbleToStrFormat(plotDetail.getPondRai()));
                        h.ngan.setText(Util.strToDobbleToStrFormat(plotDetail.getPondNgan()));
                        h.wa.setText(Util.strToDobbleToStrFormat(plotDetail.getPondWa()));
                        h.rookKung.setText(Util.strToDobbleToStrFormat(plotDetail.getFisheryNumber()));
                    }

                    checkVisibility( Util.strToDoubleDefaultZero(plotDetail.getPondRai())
                            ,Util.strToDoubleDefaultZero(plotDetail.getPondNgan())
                            ,Util.strToDoubleDefaultZero(plotDetail.getPondWa()));
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
        dialog.setContentView(R.layout.dialog_edit_shrimp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        //android.widget.TextView title = (android.widget.TextView) dialog.findViewById(R.id.edit_rai_label);
        // final android.widget.TextView inputRai = (android.widget.TextView) dialog.findViewById(R.id.edit_rai);

        final EditText rai = (EditText) dialog.findViewById(R.id.rai);

        final EditText ngan = (EditText) dialog.findViewById(R.id.ngan);
        ngan.setFilters(new InputFilter[]{ new InputFilterMinMax(0.00d, 3.99d)});

        final EditText sqaWa = (EditText) dialog.findViewById(R.id.sqaWa);
        sqaWa.setFilters(new InputFilter[]{ new InputFilterMinMax(0.00d, 99.99d)});

        final EditText unit = (EditText) dialog.findViewById(R.id.unit);

        android.widget.TextView btn_cancel = (android.widget.TextView) dialog.findViewById(R.id.cancel);
        android.widget.TextView btn_ok = (android.widget.TextView) dialog.findViewById(R.id.ok);

        rai.setText(Util.clearStrNumberFormat(h.rai.getText().toString()));
        ngan.setText( Util.clearStrNumberFormat(h.ngan.getText().toString()));
        sqaWa.setText(Util.clearStrNumberFormat(h.wa.getText().toString()));
        unit.setText(Util.clearStrNumberFormat(h.rookKung.getText().toString()));

        h.rai = (TextView) view.findViewById(R.id.rai);
        h.ngan = (TextView) view.findViewById(R.id.ngan);
        h.wa = (TextView) view.findViewById(R.id.wa);
        h.rookKung = (TextView) view.findViewById(R.id.rookKung);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                h.rai.setText(Util.strToDobbleToStrFormat(rai.getText().toString()));
                h.ngan.setText(Util.strToDobbleToStrFormat(ngan.getText().toString()));
                h.wa.setText(Util.strToDobbleToStrFormat(sqaWa.getText().toString()));
                h.rookKung.setText(Util.strToDobbleToStrFormat(unit.getText().toString()));
                //userPlotModel.setPlotRai(String.valueOf(Util.strToDoubleDefaultZero(inputRai.getText().toString())));

                userPlotModel.setPondRai(Util.clearStrNumberFormat(h.rai.getText().toString()));
                userPlotModel.setPondNgan(Util.clearStrNumberFormat(h.ngan.getText().toString()));
                userPlotModel.setPondWa(Util.clearStrNumberFormat(h.wa.getText().toString()));
                userPlotModel.setFisheryNumber(Util.clearStrNumberFormat(h.rookKung.getText().toString()));

                checkVisibility( Util.strToDoubleDefaultZero(rai.getText().toString())
                        ,Util.strToDoubleDefaultZero(ngan.getText().toString())
                        ,Util.strToDoubleDefaultZero(sqaWa.getText().toString()));

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


    public void setCalKaSermOption(boolean isSetOption){
        if (isSetOption) {
            h.btnOption.setBackgroundResource(R.drawable.radio_cal_blue_check);
            isCalIncludeOption = true;
        } else {
            h.btnOption.setBackgroundResource(R.drawable.radio_cal_blue);
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
    private boolean validateInputData(){

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


                        Util.strToDoubleDefaultZero(h.group2_item_1.getText().toString()) +
                        Util.strToDoubleDefaultZero(h.group2_item_2.getText().toString()) +
                        Util.strToDoubleDefaultZero(h.group2_item_3.getText().toString()) +
                        Util.strToDoubleDefaultZero(h.group2_item_4.getText().toString()) +
                        Util.strToDoubleDefaultZero(h.group2_item_5.getText().toString()) ;


        if(value == 0){
            new DialogChoice(context).ShowOneChoice("", "กรุณากรอกข้อมูล เพื่อคำนวณต้นทุน");
            return false;
        }else{
            return true;
        }
    }

}