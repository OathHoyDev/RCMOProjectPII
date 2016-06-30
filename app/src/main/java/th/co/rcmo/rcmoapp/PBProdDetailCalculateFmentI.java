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

import com.neopixl.pixlui.components.edittext.EditText;
import com.neopixl.pixlui.components.textview.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import th.co.rcmo.rcmoapp.API.RequestServices;
import th.co.rcmo.rcmoapp.API.ResponseAPI;
import th.co.rcmo.rcmoapp.Model.UserPlotModel;
import th.co.rcmo.rcmoapp.Model.calculate.CalculateResultModel;
import th.co.rcmo.rcmoapp.Model.calculate.FormulaAModel;
import th.co.rcmo.rcmoapp.Model.calculate.FormulaIModel;
import th.co.rcmo.rcmoapp.Module.mGetPlotDetail;
import th.co.rcmo.rcmoapp.Module.mGetVariable;
import th.co.rcmo.rcmoapp.Util.BitMapHelper;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;
import th.co.rcmo.rcmoapp.Util.Util;
import th.co.rcmo.rcmoapp.View.DialogCalculateResult;

/**
 * Created by Taweesin on 27/6/2559.
 */
public class PBProdDetailCalculateFmentI extends Fragment implements View.OnClickListener {
    UserPlotModel userPlotModel;
    ViewHolder h = new ViewHolder();
    View view;
    FormulaIModel formulaModel;

    private boolean havePlotId = false;

    th.co.rcmo.rcmoapp.Module.mGetPlotDetail.mRespBody mGetPlotDetail = new mGetPlotDetail.mRespBody();

    Context context;

    boolean isCalIncludeOption = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_prod_cal_plan_i, container, false);
        context = view.getContext();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setHolder();

        setupData();

        setUI();

        return view;
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
        h.tarangwa = (TextView) view.findViewById(R.id.tarangwa);
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


        h.btnOption = (Button) view.findViewById(R.id.btnOption);
        h.btnOption.setOnClickListener(this);
    }

    private void setupData() {

        userPlotModel = PBProductDetailActivity.userPlotModel;


        FormulaIModel aModel = new FormulaIModel();
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
            h.rookKung.setText(userPlotModel.getFisheryNumber());
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
            bindingData(formulaModel);
            formulaModel.calculate();

            setUpCalUI(formulaModel);
            Util.showDialogAndDismiss(context,"คำนวนสำเร็จ : "+formulaModel.KumraiKadtoon);

            CalculateResultModel calculateResultModel = new CalculateResultModel();
            calculateResultModel.formularCode = "I";
            calculateResultModel.calculateResult = formulaModel.KumraiKadtoon;
            calculateResultModel.productName = userPlotModel.getPrdValue();
            calculateResultModel.mPlotSuit = PBProductDetailActivity.mPlotSuit;

            DialogCalculateResult.userPlotModel = userPlotModel;
            DialogCalculateResult.calculateResultModel = calculateResultModel;

            List resultArrayResult = new ArrayList();

            String [] tontoonCal_1 = {"ต้นทุนทั้งหมด" , String.format("%,.2f", formulaModel.TontoonTungmod) , "บาท"};
            resultArrayResult.add(tontoonCal_1);

            String [] tontoonCal_2 = {"" , String.format("%,.2f", formulaModel.TontoonTorKilo) , "บาท/กก."};
            resultArrayResult.add(tontoonCal_2);

            String [] raydai_1 = {"รายได้" , String.format("%,.2f", formulaModel.TontoonTorRai) , "บาท/ไร่"};
            resultArrayResult.add(raydai_1);

            DialogCalculateResult.calculateResultModel.resultList = resultArrayResult;

            new DialogCalculateResult(context).Show();

        }else if(v.getId() == R.id.btnOption) {

            if(isCalIncludeOption){
                h.btnOption.setBackgroundResource(R.drawable.radio_cal_blue);
                isCalIncludeOption = false;
                formulaModel.isCalIncludeOption =false;
            }else{
                h.btnOption.setBackgroundResource(R.drawable.radio_cal_blue_check);
                isCalIncludeOption = true;
                formulaModel.isCalIncludeOption = true;
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
        }

    }

    private void setUpCalUI(FormulaIModel aModel){
        h.group1_item_9.setText(Util.dobbleToStringNumber(aModel.KaRang));
        h.group2_item_6.setText(Util.dobbleToStringNumber(aModel.KaSiaOkardLongtoon));
    }

    private void bindingData(FormulaIModel aModel) {
        //  aModel.KaRang = 10;

        aModel.Rai = Util.strToDoubleDefaultZero(h.rai.getText().toString());
        aModel.Ngan = Util.strToDoubleDefaultZero(h.ngan.getText().toString());
        aModel.TarangWa = Util.strToDoubleDefaultZero(h.tarangwa.getText().toString());
        aModel.rookKung = Util.strToDoubleDefaultZero(h.rookKung.getText().toString());

        aModel.RakaTuaLa = Util.strToDoubleDefaultZero(h.group1_item_1.getText().toString());
        aModel.RakaPan = Util.strToDoubleDefaultZero(h.group1_item_2.getText().toString());
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
        aModel.RayaWelaTeeLeang = Util.strToDoubleDefaultZero(h.group2_item_5.getText().toString());



    }

    static class ViewHolder {

        // Header
        private TextView rai, ngan, tarangwa, rookKung;
        private ImageView productIconImg;

        // Group 1
        private EditText group1_item_1, group1_item_2, group1_item_3, group1_item_4, group1_item_5, group1_item_6, group1_item_7, group1_item_8;
        private TextView group1_item_9;
        private EditText group1_item_10, group1_item_11, group1_item_12, group1_item_13 , group1_item_14;

        // Group 2
        private EditText group2_item_1, group2_item_2, group2_item_3, group2_item_4, group2_item_5;
        private TextView group2_item_6, group2_item_7, group2_item_8;

        private TextView calBtn, group1_header, group2_header;

        private LinearLayout group1_items, group2_items;

        private ImageView group1_header_arrow, group2_header_arrow;

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
                    formulaModel.KaSermOuppakorn = Util.strToDoubleDefaultZero(var.getDP());
                    formulaModel.KaSiaOkardOuppakorn = Util.strToDoubleDefaultZero(var.getOP());
                    h.group2_item_7.setText(String.valueOf(formulaModel.KaSermOuppakorn));
                    h.group2_item_8.setText(String.valueOf(formulaModel.KaSiaOkardOuppakorn));

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

}