package th.co.rcmo.rcmoapp;


import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.neopixl.pixlui.components.button.Button;
import com.neopixl.pixlui.components.textview.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import th.co.rcmo.rcmoapp.Adapter.CalculateCostExpandableListAdapterB;
import th.co.rcmo.rcmoapp.Adapter.CalculateCostExpandableListAdapterC;
import th.co.rcmo.rcmoapp.Model.UserPlotModel;
import th.co.rcmo.rcmoapp.Model.calculate.CalculateResultModel;
import th.co.rcmo.rcmoapp.Model.calculate.FormulaBModel;
import th.co.rcmo.rcmoapp.Model.calculate.FormulaCModel;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;
import th.co.rcmo.rcmoapp.View.DialogCalculateResult;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailCalculateFragmentC extends Fragment implements  View.OnClickListener {

    String TAG = "ProductDetailCalculateFragment";

    ExpandableListView expandableListView;
    CalculateCostExpandableListAdapterC calculateCostExpandableListAdapter;

    List<String> listDataHeader;
    HashMap<String, List<String[]>> listDataChild;

    private List<String> groupList;

    static UserPlotModel userPlotModel;

    private Context context;
    View view;

    String prdID;
    String userID;
    String prdGrpID;

    FormulaCModel formulaModel;

    RelativeLayout rootViewLayout;

    boolean isCalIncludeOption = false;

    String prdName;

    public ProductDetailCalculateFragmentC() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_product_detail_map, container, false);

        userPlotModel = PBProductDetailActivity.userPlotModel;

        view = inflater.inflate(R.layout.fragment_product_detail_calculate_c, container,
                false);

        context = view.getContext();

        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        rootViewLayout = (RelativeLayout) view.findViewById(R.id.rootLayoutView);

        getArgumentFromActivity();

        initialProductIcon();

        formulaModel = new FormulaCModel();

        formulaModel.calculate();
        formulaModel.prepareListData();
        setDimens();

        TextView txStartUnit = (TextView) view.findViewById(R.id.txStartUnit);
        txStartUnit.setText(String.valueOf(formulaModel.getValueFromAttributeName(formulaModel , "KaNardPlangTDin")));

        calculateCostExpandableListAdapter = new CalculateCostExpandableListAdapterC(context, formulaModel);

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

        Button btnOption = (Button) view.findViewById(R.id.btnOption);
        btnOption.setOnClickListener(this);

        return view;
    }

    public void setDimens()
    {
        float density = getActivity().getResources().getDisplayMetrics().density;
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Display display2 = getActivity().getWindowManager().getDefaultDisplay();
        int width2 = display2.getWidth();  // deprecated
        int height2 = display2.getHeight();  // deprecated
        //expListView.setIndicatorBounds(width2-GetDipsFromPixel(35), width2-GetDipsFromPixel(5)); //not works
        expandableListView.setIndicatorBounds(expandableListView.getRight()- 40, expandableListView.getWidth()); //not works

    }

    public int GetDipsFromPixel(float pixels)
    {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    private void getArgumentFromActivity(){

        prdID = userPlotModel.getPrdID();
        userID = userPlotModel.getUserID();
        prdGrpID = userPlotModel.getPrdGrpID();

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
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1)) + 50;
        if (height < 10)
            height = 200;

        float density = getActivity().getResources().getDisplayMetrics().density;
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int pxHeight = display.getHeight();


        int screenHeight = display.getHeight();

        int remainScreenHeight = (int) (screenHeight - ((120 + 130 + 50 + 100)*density));

        if (isFirstLoadView || height < remainScreenHeight){
            height = (remainScreenHeight);
        }

        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();


    }

    public void onClick(View v) {
        if(v.getId() == R.id.btnCalculate){

            formulaModel = (FormulaCModel) calculateCostExpandableListAdapter.getDataObj();

            formulaModel.calculate();

            CalculateResultModel calculateResultModel = new CalculateResultModel();
            calculateResultModel.formularCode = "B";
            calculateResultModel.calculateResult = formulaModel.calSumCost;
            calculateResultModel.productName = userPlotModel.getPrdValue();
            calculateResultModel.mPlotSuit = PBProductDetailActivity.mPlotSuit;
            calculateResultModel.compareStdResult = formulaModel.calSumCost - formulaModel.TontumMattratarn;

            DialogCalculateResult.userPlotModel = userPlotModel;
            DialogCalculateResult.calculateResultModel = calculateResultModel;

            List resultArrayResult = new ArrayList();

            String [] tontoonCal_1 = {"ต้นทุนรวมเกษตรทั้งแปลง" , String.format("%,.2f", formulaModel.calSumCost) , "บาท"};
            resultArrayResult.add(tontoonCal_1);

            String [] tontoonCal_2 = {"" , String.format("%,.2f", formulaModel.calStartCostPerRai) , "บาท"};
            resultArrayResult.add(tontoonCal_2);

            String [] tontoonCal_3 = {"ต้นทุนรวมเกษตรเฉลี่ยต่อไร่" , String.format("%,.2f", formulaModel.calStartCostPerRai) , "บาท"};
            resultArrayResult.add(tontoonCal_3);

            String [] tontoonCal_4 = {"ต้นทุนรวมของเกษตรกร เฉลี่ยต่อปี ตลอดอายุขัยเฉลี่ยของพืชนั้น" , String.format("%,.2f", formulaModel.calLifeCostPerRai) , "บาท"};
            resultArrayResult.add(tontoonCal_4);

            String [] raydai_1 = {"รายได้" , String.format("%,.2f", formulaModel.calIncome) , "บาท"};
            resultArrayResult.add(raydai_1);

            String [] raydai_2 = {"" , String.format("%,.2f", formulaModel.calIncomePerRai) , "บาท"};
            resultArrayResult.add(raydai_2);

            String [] tontoon = {"ต้นทุนมาตรฐานของ สศก." , String.format("%,.2f", formulaModel.TontumMattratarnPerRai) , "บาท"};
            resultArrayResult.add(tontoon);

            DialogCalculateResult.calculateResultModel.resultList = resultArrayResult;

            new DialogCalculateResult(context).Show();


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

        }

    }
}
