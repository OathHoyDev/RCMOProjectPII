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
import th.co.rcmo.rcmoapp.Model.calculate.FormulaDModel;
import th.co.rcmo.rcmoapp.Module.mGetPlotDetail;
import th.co.rcmo.rcmoapp.Module.mGetVariable;
import th.co.rcmo.rcmoapp.Module.mVarPlanD;
import th.co.rcmo.rcmoapp.Util.BitMapHelper;
import th.co.rcmo.rcmoapp.Util.PlanDTextWatcher;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;
import th.co.rcmo.rcmoapp.Util.Util;
import th.co.rcmo.rcmoapp.View.DialogCalculateResult;

/**
 * Created by Taweesin on 27/6/2559.
 */
public class PBProdDetailCalculateFmentD extends Fragment implements View.OnClickListener {
    UserPlotModel userPlotModel;
    boolean isCalIncludeOption = false;
    private Context context;
    List<STDVarModel> stdVarModelList = new ArrayList<STDVarModel>();
    FormulaDModel formulaModel;
    ViewHolder h = new ViewHolder();
    th.co.rcmo.rcmoapp.Module.mGetPlotDetail.mRespBody mGetPlotDetail = new mGetPlotDetail.mRespBody();
    private boolean havePlotId = false;

    public PBProdDetailCalculateFmentD() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_prod_cal_plan_d, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //================= Set Holder ========================================//
        context = view.getContext();
        h.group1_item_1 = (TextView) view.findViewById(R.id.group1_item_1);
        h.group1_item_2 = (EditText) view.findViewById(R.id.group1_item_2);
        h.group1_item_3 = (EditText) view.findViewById(R.id.group1_item_3);
        h.group1_item_4 = (EditText) view.findViewById(R.id.group1_item_4);
        h.group1_item_5 = (EditText) view.findViewById(R.id.group1_item_5);
        h.group1_item_6 = (EditText) view.findViewById(R.id.group1_item_6);
        h.group1_item_7 = (EditText) view.findViewById(R.id.group1_item_7);
        h.group1_item_8 = (EditText) view.findViewById(R.id.group1_item_8);
        h.group1_item_9 = (EditText) view.findViewById(R.id.group1_item_9);

        h.group2_item_1 = (TextView) view.findViewById(R.id.group2_item_1);

        h.group3_item_1 = (EditText) view.findViewById(R.id.group3_item_1);
        h.group3_item_2 = (EditText) view.findViewById(R.id.group3_item_2);
        h.group3_item_3 = (TextView) view.findViewById(R.id.group3_item_3);
        h.group3_item_4 = (EditText) view.findViewById(R.id.group3_item_4);
        h.group3_item_5 = (EditText) view.findViewById(R.id.group3_item_5);
        h.group3_item_6 = (TextView) view.findViewById(R.id.group3_item_6);

        h.productIconImg = (ImageView) view.findViewById(R.id.productIconImg);
        h.txStartUnit = (TextView) view.findViewById(R.id.txStartUnit);
        h.txStartPrice = (TextView) view.findViewById(R.id.txStartPrice);

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


        h.headerLayout = (RelativeLayout) view.findViewById(R.id.headerLayout);
        h.headerLayout.setOnClickListener(this);

        h.calBtn = (TextView) view.findViewById(R.id.calBtn);
        h.calBtn.setOnClickListener(this);

        h.btnOption = (Button) view.findViewById(R.id.btnOption);
        h.btnOption.setOnClickListener(this);


//=========================== Setup data ==========================================/

        userPlotModel = PBProductDetailActivity.userPlotModel;

        formulaModel = new FormulaDModel();

        API_getVariable(userPlotModel.getPrdID(), userPlotModel.getFisheryType());
        Log.d("userPlotModel : ",userPlotModel.getPlotID());
        if (!userPlotModel.getPlotID().equals("") && userPlotModel.getPlotID().equals("0")) {
            initVariableDataFromDB();
        }else{
            formulaModel.RakaReamLeang =  Util.strToDoubleDefaultZero( userPlotModel.getAnimalPrice());
            formulaModel.RermLeang     =  Util.strToDoubleDefaultZero(userPlotModel.getAnimalNumber());
            formulaModel.calculate();
            setUpCalUI(formulaModel);
            h.group2_item_1.setText(Util.dobbleToStringNumberWithClearDigit(0)+"%");
        }

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setUI();
        setAction();

        return view;
    }


    private void setAction() {
        h.txStartUnit.addTextChangedListener(new PlanDTextWatcher(h.txStartUnit, h, "KaPan,costKaSiaOkardRongRaun"));
        h.txStartPrice.addTextChangedListener(new PlanDTextWatcher( h.txStartPrice,h, "KaPan"));

        h.group1_item_2.addTextChangedListener(new PlanDTextWatcher(h.group1_item_2, h, "costKaSiaOkardRongRaun"));
        h.group1_item_3.addTextChangedListener(new PlanDTextWatcher(h.group1_item_3, h, "costKaSiaOkardRongRaun"));
        h.group1_item_4.addTextChangedListener(new PlanDTextWatcher(h.group1_item_4, h, "costKaSiaOkardRongRaun"));
        h.group1_item_5.addTextChangedListener(new PlanDTextWatcher(h.group1_item_5, h, "costKaSiaOkardRongRaun"));
        h.group1_item_6.addTextChangedListener(new PlanDTextWatcher(h.group1_item_6, h, "costKaSiaOkardRongRaun"));
        h.group1_item_7.addTextChangedListener(new PlanDTextWatcher(h.group1_item_7, h, "costKaSiaOkardRongRaun"));
        h.group1_item_8.addTextChangedListener(new PlanDTextWatcher(h.group1_item_8, h, "costKaSiaOkardRongRaun"));
        h.group1_item_9.addTextChangedListener(new PlanDTextWatcher(h.group1_item_9, h, ""));
        h.group3_item_1.addTextChangedListener(new PlanDTextWatcher(h.group3_item_1, h, "NamNakTKai"));
        h.group3_item_2.addTextChangedListener(new PlanDTextWatcher(h.group3_item_2, h, "DieRate,NamNakTKai"));
        h.group3_item_4.addTextChangedListener(new PlanDTextWatcher(h.group3_item_4, h, ""));
        h.group3_item_5.addTextChangedListener(new PlanDTextWatcher(h.group3_item_5, h, "costKaSiaOkardRongRaun"));


    }

    private void initVariableDataFromDB() {
        API_getPlotDetailANDBlinding(userPlotModel.getPlotID(), formulaModel);


        // set variable to json obj
        //userPlotModel.setVarValue(ProductService.genJsonPlanAVariable(formulaModel));

    }


    private void setUpCalUI(FormulaDModel model) {
        h.group1_item_1.setText(Util.dobbleToStringNumber(model.KaPan));
        h.group2_item_1.setText(Util.dobbleToStringNumberWithClearDigit(model.dieRatio)+"%");
        h.group3_item_3.setText(Util.dobbleToStringNumber(model.NamNakTKai));
        h.group3_item_6.setText(Util.dobbleToStringNumber(model.KaSiaOkardLongtoon));

    }

    private void bindingData(FormulaDModel model) {

        model.KaAHan = Util.strToDoubleDefaultZero(h.group1_item_2.getText().toString());
        model.KaYa = Util.strToDoubleDefaultZero(h.group1_item_3.getText().toString());
        model.KaRangGgan = Util.strToDoubleDefaultZero(h.group1_item_4.getText().toString());
        model.KaNamKaFai = Util.strToDoubleDefaultZero(h.group1_item_5.getText().toString());
        model.KaNamMan = Util.strToDoubleDefaultZero(h.group1_item_6.getText().toString());
        model.KaWassaduSinPleung = Util.strToDoubleDefaultZero(h.group1_item_7.getText().toString());
        model.KaSomRongRaun = Util.strToDoubleDefaultZero(h.group1_item_8.getText().toString());
        model.KaChoaTDin = Util.strToDoubleDefaultZero(h.group1_item_9.getText().toString());

        model.NamNakChaLia = Util.strToDoubleDefaultZero(h.group3_item_1.getText().toString());
        model.JumNounTuaTKai = Util.strToDoubleDefaultZero(h.group3_item_2.getText().toString());
        model.RakaTKai = Util.strToDoubleDefaultZero(h.group3_item_4.getText().toString());
        model.RaYaWeRaLeang = Util.strToDoubleDefaultZero(h.group3_item_5.getText().toString());

        model.RermLeang = Util.strToDoubleDefaultZero(h.txStartUnit.getText().toString());
        model.RakaReamLeang = Util.strToDoubleDefaultZero(h.txStartPrice.getText().toString());




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
            h.txStartPrice.setText( Util.dobbleToStringNumberWithClearDigit(Util.strToDoubleDefaultZero(userPlotModel.getAnimalPrice())));
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
            calculateResultModel.formularCode = "D";
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

            String [] tontoonCal_2 = {"" , String.format("%,.2f", formulaModel.calCostPerUnit) , "บาท/ตัว"};
            resultArrayResult.add(tontoonCal_2);

            String [] tontoonCal_3 = {"" , String.format("%,.2f", formulaModel.calCostPerKg) , "บาท/กก."};
            resultArrayResult.add(tontoonCal_3);
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
        } else if (v.getId() == R.id.btnOption) {

            if (isCalIncludeOption) {
                h.btnOption.setBackgroundResource(R.drawable.radio_cal_pink);
                isCalIncludeOption = false;
            } else {
                h.btnOption.setBackgroundResource(R.drawable.radio_cal_pink_check);
                isCalIncludeOption = true;
            }

            formulaModel.isCalIncludeOption = isCalIncludeOption;

        } else if (v.getId() == R.id.headerLayout) {


            popUpTumbonListDialog();
        }

    }

    public static class ViewHolder {
        //Group1
        public TextView group1_item_1;
        public EditText group1_item_2, group1_item_3, group1_item_4, group1_item_5,group1_item_6, group1_item_7, group1_item_8, group1_item_9;

        //Group2
        public TextView group2_item_1;

        //Group3
        public EditText group3_item_1,group3_item_2,group3_item_4,group3_item_5;
        public TextView group3_item_3,group3_item_6;

        public TextView txStartUnit,txStartPrice;

        private ImageView productIconImg;

        private TextView calBtn, group1_header, group2_header, group3_header;

        private LinearLayout group1_items, group2_items, group3_items;

        private ImageView group1_header_arrow, group2_header_arrow, group3_header_arrow;

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
                    formulaModel.KaSiaOkardRongRaun = Util.strToDoubleDefaultZero(var.getO());
                    //stub
                    //formulaModel.KaSermRongRaun  =0.7;
                    //formulaModel.KaSiaOkardRongRaun=0.58;
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


    private void popUpTumbonListDialog() {

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


        edit.setText(h.txStartUnit.getText());
        edit_t1.setText(h.txStartPrice.getText());

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                h.txStartUnit.setText(Util.dobbleToStringNumberWithClearDigit(Util.strToDoubleDefaultZero(edit.getText().toString())));
                h.txStartPrice.setText(Util.dobbleToStringNumberWithClearDigit(Util.strToDoubleDefaultZero(edit_t1.getText().toString())));
                //userPlotModel.setPlotRai(String.valueOf(Util.strToDoubleDefaultZero(inputRai.getText().toString())));

                userPlotModel.setAnimalNumber(h.txStartUnit.getText().toString());
                userPlotModel.setAnimalPrice(h.txStartPrice.getText().toString());
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


    private void API_getPlotDetailANDBlinding(String plotID, final FormulaDModel model) {
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
                        mVarPlanD var = new Gson().fromJson(plotDetail.getVarValue(), mVarPlanD.class);

                        model.KaAHan = var.KaAHan;
                        model.KaYa   = var.KaYa;
                        model.KaRangGgan = var.KaRangGgan;
                        model.KaNamKaFai = var.KaNamKaFai;
                        model.KaNamMan = var.KaNamMan;
                        model.KaWassaduSinPleung = var.KaWassaduSinPleung;
                        model.KaSomRongRaun =var.KaSomRongRaun;
                        model.KaChoaTDin = var.KaChoaTDin;

                        model.NamNakChaLia = var.NamNakChaLia;
                        model.JumNounTuaTKai =var.JumNounTuaTKai;
                        model.RakaTKai = var.RakaTKai;
                        model.RaYaWeRaLeang = var.RaYaWeRaLeang;

                        model.RermLeang =var.RermLeang;
                        model.RakaReamLeang =var.RakaReamLeang;


                        h.group1_item_2.setText(Util.dobbleToStringNumber(var.KaAHan));
                        h.group1_item_3.setText(Util.dobbleToStringNumber(var.KaYa));
                        h.group1_item_4.setText(Util.dobbleToStringNumber(var.KaRangGgan));
                        h.group1_item_5.setText(Util.dobbleToStringNumber(var.KaNamKaFai));
                        h.group1_item_6.setText(Util.dobbleToStringNumber(var.KaNamMan));
                        h.group1_item_7.setText(Util.dobbleToStringNumber(var.KaWassaduSinPleung));
                        h.group1_item_8.setText(Util.dobbleToStringNumber(var.KaSomRongRaun));
                        h.group1_item_9.setText(Util.dobbleToStringNumber(var.KaChoaTDin));

                        h.group3_item_1.setText(Util.dobbleToStringNumber(var.NamNakChaLia));
                        h.group3_item_2.setText(Util.dobbleToStringNumber(var.JumNounTuaTKai));
                        h.group3_item_4.setText(Util.dobbleToStringNumber(var.RakaTKai));
                        h.group3_item_5.setText(Util.dobbleToStringNumber(var.RaYaWeRaLeang));

                        h.txStartUnit.setText(Util.dobbleToStringNumberWithClearDigit(var.RermLeang));
                        h.txStartPrice.setText(Util.dobbleToStringNumberWithClearDigit(var.RakaReamLeang));

                        formulaModel.calculate();


                        setUpCalUI(formulaModel);
                    } else {
                        h.txStartUnit.setText(Util.strToDobbleToStrFormat(plotDetail.getAnimalNumber()));
                        h.txStartPrice.setText(Util.strToDobbleToStrFormat(plotDetail.getAnimalPrice()));
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