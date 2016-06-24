package th.co.rcmo.rcmoapp;


import android.content.Context;
import android.content.Intent;
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

import th.co.rcmo.rcmoapp.Adapter.CalculateCostExpandableListAdapterA;
import th.co.rcmo.rcmoapp.Adapter.CalculateCostExpandableListAdapterD;
import th.co.rcmo.rcmoapp.Model.UserPlotModel;
import th.co.rcmo.rcmoapp.Model.calculate.FormulaAModel;
import th.co.rcmo.rcmoapp.Model.calculate.FormulaDModel;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailCalculateFragmentA extends Fragment implements  View.OnClickListener {

    String TAG = "ProductDetailCalculateFragmentA";

    ExpandableListView expandableListView;
    CalculateCostExpandableListAdapterA calculateCostExpandableListAdapter;

    List<String> listDataHeader;
    HashMap<String, List<String[]>> listDataChild;

    private List<String> groupList;

    static UserPlotModel userPlotModel;

    private Context context;
    View view;

    String prdID;
    String userID;
    String prdGrpID;

    FormulaAModel formulaModel;

    RelativeLayout resultFadeView;
    RelativeLayout rootViewLayout;

    boolean isCalIncludeOption = false;

    public ProductDetailCalculateFragmentA() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_product_detail_map, container, false);

        userPlotModel = ((ProductDetailActivity)this.getActivity()).userPlotModel;

        view = inflater.inflate(R.layout.fragment_product_detail_calculate_a, container,
                false);

        context = view.getContext();

        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        resultFadeView = (RelativeLayout) view.findViewById(R.id.resultFadeLayout);
        rootViewLayout = (RelativeLayout) view.findViewById(R.id.rootLayoutView);

        getArgumentFromActivity();

        initialProductIcon();

        loadDataFromPlotID();

        FormulaAModel aModel = new FormulaAModel();

        formulaModel = aModel;

        formulaModel.calculate();
        formulaModel.prepareListData();

        TextView txStartUnit = (TextView) view.findViewById(R.id.txStartUnit);
        txStartUnit.setText(String.valueOf(formulaModel.getValueFromAttributeName(formulaModel , "KaNardPlangTDin")));

        calculateCostExpandableListAdapter = new CalculateCostExpandableListAdapterA(context, formulaModel);

        // setting list adapter
        expandableListView.setAdapter(calculateCostExpandableListAdapter);

//        for (int i=0 ; i<calculateCostExpandableListAdapter.getGroupCount(); i++) {
//            expandableListView.expandGroup(i);
//        }

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

        Button btnOption = (Button) view.findViewById(R.id.btnOption);
        btnOption.setOnClickListener(this);


        resultFadeView.setVisibility(View.GONE);


        return view;
    }

    private void getArgumentFromActivity(){

        prdID = getArguments().getString("prdID");
        userID = getArguments().getString("userID");
        prdGrpID = getArguments().getString("prdGrpID");

    }

    private void initialProductIcon(){

        android.widget.ImageView productIcon = (android.widget.ImageView)view.findViewById(R.id.productIcon);

        String imgName = ServiceInstance.productIMGMap.get(Integer.parseInt(prdID));

        int imgIDInt = getResources().getIdentifier(imgName, "drawable", context.getPackageName());

        productIcon.setImageResource(imgIDInt);

        if (isCalIncludeOption){
            Button btnOption = (Button)view.findViewById(R.id.btnOption);

            if(isCalIncludeOption){
                btnOption.setBackgroundResource(R.drawable.radio_cal_green_check);
            }else{
                btnOption.setBackgroundResource(R.drawable.radio_cal_green);
            }

        }

    }

    private void loadDataFromPlotID(){





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

            formulaModel = (FormulaAModel) calculateCostExpandableListAdapter.getDataObj();

            formulaModel.calculate();

            ImageView calResultProfitLossImage = (ImageView) resultFadeView.findViewById(R.id.calResultProfitLossImage);
            TextView txResultString = (TextView) resultFadeView.findViewById(R.id.txResultString);
            TextView txResult = (TextView) resultFadeView.findViewById(R.id.txResult);
            TextView txResultValue = (TextView) resultFadeView.findViewById(R.id.txResultValue);

            if ((double)formulaModel.getValueFromAttributeName(formulaModel , "calProfitLoss") > 0){
                calResultProfitLossImage.setImageResource(R.drawable.ic_profit);

                txResultString.setText("ยินดีด้วย");
                txResult.setText("คุณได้กำไร");
                txResultValue.setText("จำนวน " + String.format("%,.2f", formulaModel.getValueFromAttributeName(formulaModel , "calProfitLoss")) + " บาท");
            }else{
                calResultProfitLossImage.setImageResource(R.drawable.ic_losecost);

                txResultString.setText("เสียใจด้วย");
                txResult.setText("คุณได้ขาดทุน");
                txResultValue.setText("จำนวน " + String.format("%,.2f", formulaModel.getValueFromAttributeName(formulaModel , "calProfitLoss")) + " บาท");
            }

            resultFadeView.setVisibility(View.VISIBLE);


        }else if(v.getId() == R.id.btnOption) {
            Button btnOption = (Button)view.findViewById(R.id.btnOption);
            if(isCalIncludeOption){
                btnOption.setBackgroundResource(R.drawable.radio_cal_green);
                isCalIncludeOption = false;
            }else{
                btnOption.setBackgroundResource(R.drawable.radio_cal_green_check);
                isCalIncludeOption = true;
            }

            formulaModel.isCalIncludeOption = isCalIncludeOption;

        }else if(v.getId() == R.id.btnShowReport){

            resultFadeView.setVisibility(View.GONE);

            CalculateResultActivity.resultModel = null;
            CalculateResultActivity.userPlotModel = userPlotModel;

            startActivity(new Intent(context, CalculateResultActivity.class));
        }

    }
}
