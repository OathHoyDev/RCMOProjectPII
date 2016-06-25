package th.co.rcmo.rcmoapp;


import android.content.Context;
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

import java.util.HashMap;
import java.util.List;

import th.co.rcmo.rcmoapp.Adapter.CalculateCostExpandableListAdapterA;
import th.co.rcmo.rcmoapp.Adapter.CalculateCostExpandableListAdapterB;
import th.co.rcmo.rcmoapp.Model.UserPlotModel;
import th.co.rcmo.rcmoapp.Model.calculate.CalculateResultModel;
import th.co.rcmo.rcmoapp.Model.calculate.FormulaAModel;
import th.co.rcmo.rcmoapp.Model.calculate.FormulaBModel;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;
import th.co.rcmo.rcmoapp.View.DialogCalculateResult;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailCalculateFragmentB extends Fragment implements  View.OnClickListener {

    String TAG = "ProductDetailCalculateFragmentB";

    ExpandableListView expandableListView;
    CalculateCostExpandableListAdapterB calculateCostExpandableListAdapter;

    List<String> listDataHeader;
    HashMap<String, List<String[]>> listDataChild;

    private List<String> groupList;

    static UserPlotModel userPlotModel;

    private Context context;
    View view;

    String prdID;
    String userID;
    String prdGrpID;

    FormulaBModel formulaModel;

    RelativeLayout resultFadeView;
    RelativeLayout rootViewLayout;

    boolean isCalIncludeOption = false;

    String prdName;

    public ProductDetailCalculateFragmentB() {
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
        rootViewLayout = (RelativeLayout) view.findViewById(R.id.rootLayoutView);

        getArgumentFromActivity();

        initialProductIcon();

        formulaModel = new FormulaBModel();

        formulaModel.calculate();
        formulaModel.prepareListData();

        TextView txStartUnit = (TextView) view.findViewById(R.id.txStartUnit);
        txStartUnit.setText(String.valueOf(formulaModel.getValueFromAttributeName(formulaModel , "KaNardPlangTDin")));

        calculateCostExpandableListAdapter = new CalculateCostExpandableListAdapterB(context, formulaModel);

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


    }

    public void onClick(View v) {
        if(v.getId() == R.id.btnCalculate){

            formulaModel = (FormulaBModel) calculateCostExpandableListAdapter.getDataObj();

            formulaModel.calculate();

            CalculateResultModel calculateResultModel = new CalculateResultModel();
            calculateResultModel.formularCode = "B";
            calculateResultModel.calculateResult = formulaModel.calSumCost;
            calculateResultModel.productName = ((ProductDetailActivity)this.getActivity()).productName;
            calculateResultModel.mPlotSuit = ((ProductDetailActivity)this.getActivity()).mPlotSuit;
            calculateResultModel.compareStdResult = formulaModel.calSumCost - formulaModel.TontumMattratarn;

            DialogCalculateResult.userPlotModel = userPlotModel;
            DialogCalculateResult.calculateResultModel = calculateResultModel;



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
