package th.co.rcmo.rcmoapp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.neopixl.pixlui.components.button.Button;
import com.neopixl.pixlui.components.imageview.ImageView;
import com.neopixl.pixlui.components.textview.TextView;

import java.util.HashMap;
import java.util.List;

import th.co.rcmo.rcmoapp.Adapter.CalculateCostExpandableListAdapter;
import th.co.rcmo.rcmoapp.Model.calculate.FormulaJModel;
import th.co.rcmo.rcmoapp.Util.CalculateConstant;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailCalculateFragment extends Fragment implements  View.OnClickListener {

    String TAG = "ProductDetailCalculateFragment";

    ExpandableListView expandableListView;
    CalculateCostExpandableListAdapter calculateCostExpandableListAdapter;

    List<String> listDataHeader;
    HashMap<String, List<String[]>> listDataChild;

    private List<String> groupList;

    private Context context;
    View view;

    String prdID;
    String userID;
    String prdGrpID;

    FormulaJModel formulaModel;

    RelativeLayout resultFadeView;
    RelativeLayout rootViewLayout;

    public ProductDetailCalculateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_product_detail_map, container, false);

        view = inflater.inflate(R.layout.fragment_product_detail_calculate, container,
                false);

        context = view.getContext();

        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        resultFadeView = (RelativeLayout) view.findViewById(R.id.resultFadeLayout);
        rootViewLayout = (RelativeLayout) view.findViewById(R.id.rootLayoutView);

        getArgumentFromActivity();

        initialProductIcon();

        loadDataFromPlotID();

        sampleData();

        getValueFromLayout();

        formulaModel.calculate();
        formulaModel.prepareListData();

        TextView fishpondSizeRai = (TextView) view.findViewById(R.id.lbFishpondSizeRai);
        fishpondSizeRai.setText(String.valueOf(formulaModel.getValueFromAttributeName(formulaModel , "fishpondSizeRai")));

        TextView lbFishpondSizeNgan = (TextView) view.findViewById(R.id.lbFishpondSizeNgan);
        lbFishpondSizeNgan.setText(String.valueOf(formulaModel.getValueFromAttributeName(formulaModel , "fishpondSizeNgan")));

        TextView fishpondSizeSqrWah = (TextView) view.findViewById(R.id.txStartUnit);
        fishpondSizeSqrWah.setText(String.valueOf(formulaModel.getValueFromAttributeName(formulaModel , "fishpondSizeSqrWah")));

        TextView fishpondSizeSqrMeters = (TextView) view.findViewById(R.id.txStartPrice);
        fishpondSizeSqrMeters.setText(String.valueOf(formulaModel.getValueFromAttributeName(formulaModel , "fishpondSizeSqrMeters")));

        TextView amountFish = (TextView) view.findViewById(R.id.lbAmountFish);
        amountFish.setText(String.valueOf(formulaModel.getValueFromAttributeName(formulaModel , "amountFish")));

        calculateCostExpandableListAdapter = new CalculateCostExpandableListAdapter(context, formulaModel, "J");

        // setting list adapter
        expandableListView.setAdapter(calculateCostExpandableListAdapter);

        setListViewHeight(expandableListView, formulaModel.listDataHeader.size()-1 , true);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                setListViewHeight(parent, groupPosition , false);
                return false;
            }
        });

        Button btnCalculate = (Button) view.findViewById(R.id.btnCalculate);
        btnCalculate.setOnClickListener(this);

        ImageButton btnShowReport = (ImageButton) view.findViewById(R.id.btnShowReport);
        btnShowReport.setOnClickListener(this);


        resultFadeView.setVisibility(View.GONE);


        return view;
    }

    private void getArgumentFromActivity(){

        prdID = getArguments().getString("prdID");
        userID = getArguments().getString("userID");
        prdGrpID = getArguments().getString("prdGrpID");

    }

    private void initialProductIcon(){

        android.widget.ImageView productIconBG = (android.widget.ImageView)view.findViewById(R.id.productIconBG);
        android.widget.ImageView productIcon = (android.widget.ImageView)view.findViewById(R.id.productIcon);
        ImageButton btnOption = (ImageButton) view.findViewById(R.id.btnOption);
        TextView optionText = (TextView) view.findViewById(R.id.optionText);

        if (CalculateConstant.PRODUCT_TYPE_PLANT.equalsIgnoreCase(prdGrpID)){
            productIconBG.setImageResource(R.drawable.plant_ic_gr_circle_bg);
            btnOption.setBackgroundResource(R.drawable.radio_cal_green);
            optionText.setTextColor(getResources().getColor(R.color.RcmoPlantBG));
        }else if(CalculateConstant.PRODUCT_TYPE_ANIMAL.equalsIgnoreCase(prdGrpID)){
            productIconBG.setImageResource(R.drawable.animal_ic_gr_circle_bg);
            btnOption.setBackgroundResource(R.drawable.radio_cal_pink);
            optionText.setTextColor(getResources().getColor(R.color.RcmoAnimalBG));
        }else{
            productIconBG.setImageResource(R.drawable.fish_ic_gr_circle_bg);
            btnOption.setBackgroundResource(R.drawable.radio_cal_blue);
            optionText.setTextColor(getResources().getColor(R.color.RcmoFishBG));
        }

        String imgName = ServiceInstance.productIMGMap.get(Integer.parseInt(prdID));

        int imgIDInt = getResources().getIdentifier(imgName, "drawable", context.getPackageName());

        productIcon.setImageResource(imgIDInt);





    }

    private void loadDataFromPlotID(){





    }

    private void getValueFromLayout(){
        Log.i(TAG, "getValueFromLayout (formula_j_sellFishAvg): " + view.findViewById(R.id.formula_j_sellFishAvg));
    }

    private void sampleData(){

        FormulaJModel jModel = new FormulaJModel();

        jModel.fishpondSizeRai = 10;
        jModel.fishpondSizeNgan = 2;
        jModel.fishpondSizeSqrWah = 50;
        jModel.fishpondSizeSqrMeters = 0;
        jModel.costFishSpecies = 3500;
        jModel.costFood = 500000;
        jModel.costFeed = 500;
        jModel.costFishing = 450;
        jModel.costMedicine = 350;
        jModel.costChemical = 500;
        jModel.costFuel = 500;
        jModel.costElectricGas = 450;
        jModel.costDredgeUpMud = 300;
        jModel.costRepairEquip = 250;
        jModel.costOther = 100;
        jModel.costLandLease = 3000;
        jModel.feedTime = 150;

        jModel.sellFish = 50000;
        jModel.sellPrice = 20;

        jModel.fishSize1Weight = 10;
        jModel.fishSize1SumWeight = 10000;

        jModel.fishSize2Weight = 8;
        jModel.fishSize2SumWeight = 25000;

        jModel.fishSize3Weight = 7;
        jModel.fishSize3SumWeight = 10000;

        jModel.fishSize4Weight = 5;
        jModel.fishSize4SumWeight = 5000;

        jModel.fishSize1Sell = 25;
        jModel.fishSize2Sell = 30;
        jModel.fishSize3Sell = 30;
        jModel.fishSize4Sell = 20;

        jModel.stdDepreEquip = 6.27;
        jModel.stdOpportEquip = 5.09;

        formulaModel = jModel;
    }

    private void setListViewHeight(ExpandableListView listView,
                                   int group , boolean isFirstLoadView) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;

        float density = getActivity().getResources().getDisplayMetrics().density;
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int pxHeight = display.getHeight();


        int screenHeight = display.getHeight();

        int remainScreenHeight = (int) (screenHeight - ((120 + 130 + 30 + 100)*density));

        if (isFirstLoadView || height < remainScreenHeight){
            height = (remainScreenHeight);
        }

        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

        int allLayoutHeight = (int)((120 + 120 + 100)*density);

        ViewGroup.LayoutParams resultFadeViewLayoutParams = resultFadeView.getLayoutParams();
        resultFadeViewLayoutParams.height = totalHeight + allLayoutHeight;
        resultFadeView.setLayoutParams(resultFadeViewLayoutParams);
        resultFadeView.requestLayout();

        ImageButton btnShowReport = (ImageButton) resultFadeView.findViewById(R.id.btnShowReport);
        ViewGroup.LayoutParams btnShowReportParams = btnShowReport.getLayoutParams();
        btnShowReportParams.height = totalHeight + allLayoutHeight;
        btnShowReport.setLayoutParams(btnShowReportParams);
        btnShowReport.requestLayout();

    }

    public void onClick(View v) {
        if(v.getId() == R.id.btnCalculate){
            formulaModel = (FormulaJModel) calculateCostExpandableListAdapter.getDataObj();
            formulaModel.calculate();

            ImageView calResultProfitLossImage = (ImageView) resultFadeView.findViewById(R.id.calResultProfitLossImage);
            TextView txResultString = (TextView) resultFadeView.findViewById(R.id.txResultString);
            TextView txResult = (TextView) resultFadeView.findViewById(R.id.txResult);
            TextView txResultValue = (TextView) resultFadeView.findViewById(R.id.txResultValue);

            if ((double)formulaModel.getValueFromAttributeName(formulaModel , "calMixProfitLost") > 0){
                calResultProfitLossImage.setImageResource(R.drawable.ic_profit);

                txResultString.setText("ยินดีด้วย");
                txResult.setText("คุณได้กำไร");
                txResultValue.setText("จำนวน " + String.format("%,.2f", formulaModel.getValueFromAttributeName(formulaModel , "calMixProfitLost")) + " บาท");
            }else{
                calResultProfitLossImage.setImageResource(R.drawable.ic_losecost);

                txResultString.setText("เสียใจด้วย");
                txResult.setText("คุณได้ขาดทุน");
                txResultValue.setText("จำนวน " + String.format("%,.2f", formulaModel.getValueFromAttributeName(formulaModel , "calMixProfitLost")) + " บาท");
            }

            resultFadeView.setVisibility(View.VISIBLE);


        }else if(v.getId() == R.id.btnShowReport){
            Log.d("btnShowReport", "onClick: btnShowReport");
            resultFadeView.setVisibility(View.GONE);
        }

    }
}
