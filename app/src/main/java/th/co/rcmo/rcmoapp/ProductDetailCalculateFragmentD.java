package th.co.rcmo.rcmoapp;


import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import th.co.rcmo.rcmoapp.Adapter.CalculateCostExpandableListAdapterD;
import th.co.rcmo.rcmoapp.Model.UserPlotModel;
import th.co.rcmo.rcmoapp.Model.calculate.CalculateResultModel;
import th.co.rcmo.rcmoapp.Model.calculate.FormulaDModel;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;
import th.co.rcmo.rcmoapp.View.DialogCalculateResult;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailCalculateFragmentD extends Fragment implements  View.OnClickListener {

    String TAG = "ProductDetailCalculateFragmentD";

    ExpandableListView expandableListView;
    CalculateCostExpandableListAdapterD calculateCostExpandableListAdapter;

    List<String> listDataHeader;
    HashMap<String, List<String[]>> listDataChild;

    private List<String> groupList;

    static UserPlotModel userPlotModel;

    private Context context;
    View view;

    String prdID;
    String userID;
    String prdGrpID;

    FormulaDModel formulaModel;

    RelativeLayout resultFadeView;
    RelativeLayout rootViewLayout;

    boolean isCalIncludeOption = false;

    public ProductDetailCalculateFragmentD() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_product_detail_map, container, false);

        userPlotModel = PBProductDetailActivity.userPlotModel;

        view = inflater.inflate(R.layout.fragment_product_detail_calculate_d, container,
                false);

        context = view.getContext();

        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        rootViewLayout = (RelativeLayout) view.findViewById(R.id.rootLayoutView);

        getArgumentFromActivity();

        initialProductIcon();

        loadDataFromPlotID();

        sampleData();

        getValueFromLayout();

        formulaModel.calculate();
        formulaModel.prepareListData();
        setDimens();

        TextView txStartPrice = (TextView) view.findViewById(R.id.txStartPrice);
        txStartPrice.setText(String.valueOf(formulaModel.getValueFromAttributeName(formulaModel , "RermLeang")));

        TextView txStartUnit = (TextView) view.findViewById(R.id.txStartUnit);
        txStartUnit.setText(String.valueOf(formulaModel.getValueFromAttributeName(formulaModel , "RakaReamLeang")));

        calculateCostExpandableListAdapter = new CalculateCostExpandableListAdapterD(context, formulaModel);

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
                btnOption.setBackgroundResource(R.drawable.radio_cal_pink_check);
            }else{
                btnOption.setBackgroundResource(R.drawable.radio_cal_pink);
            }

        }

    }

    private void loadDataFromPlotID(){





    }

    private void getValueFromLayout(){
        Log.i(TAG, "getValueFromLayout (formula_j_sellFishAvg): " + view.findViewById(R.id.formula_j_sellFishAvg));
    }

    private void sampleData(){

        FormulaDModel jModel = new FormulaDModel();

        jModel.RermLeang = 0;
        jModel.RakaReamLeang = 0;
        jModel.KaPan = 0;
        jModel.KaAHan = 0;
        jModel.KaYa = 0;
        jModel.KaRangGgan = 0;
        jModel.KaNamKaFai = 0;
        jModel.KaNamMan = 0;
        jModel.KaWassaduSinPleung = 0;
        jModel.KaSomRongRaun = 0;
        jModel.KaChoaTDin = 0;
        jModel.NamNakChaLia = 0;
        jModel.JumNounTuaTKai = 0;
        jModel.NamNakTKai = 0;
        jModel.RakaTKai = 0;
        jModel.RaYaWeRaLeang = 0;
        jModel.KaSiaOkardLongtoon = 0;

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

            formulaModel = (FormulaDModel) calculateCostExpandableListAdapter.getDataObj();

            formulaModel.calculate();

            CalculateResultModel calculateResultModel = new CalculateResultModel();
            calculateResultModel.formularCode = "A";
            calculateResultModel.calculateResult = formulaModel.calProfitLoss;
            calculateResultModel.productName = userPlotModel.getPrdValue();
            calculateResultModel.mPlotSuit = PBProductDetailActivity.mPlotSuit;
            calculateResultModel.compareStdResult = 0;

            DialogCalculateResult.userPlotModel = userPlotModel;
            DialogCalculateResult.calculateResultModel = calculateResultModel;

            List resultArrayResult = new ArrayList();

            String [] tontoonCal_1 = {"ต้นทุนทั้งหมด" , String.format("%,.2f", formulaModel.calCost) , "บาท"};
            resultArrayResult.add(tontoonCal_1);

            String [] tontoonCal_2 = {"" , String.format("%,.2f", formulaModel.calCostPerKg) , "บาท/ตัว"};
            resultArrayResult.add(tontoonCal_2);

            String [] tontoonCal_3 = {"" , String.format("%,.2f", formulaModel.calCostPerKg) , "บาท/กก."};
            resultArrayResult.add(tontoonCal_3);


            new DialogCalculateResult(context).Show();



        }else if(v.getId() == R.id.btnOption) {
            Button btnOption = (Button)view.findViewById(R.id.btnOption);
            if(isCalIncludeOption){
                btnOption.setBackgroundResource(R.drawable.radio_cal_pink);
                isCalIncludeOption = false;
            }else{
                btnOption.setBackgroundResource(R.drawable.radio_cal_pink_check);
                isCalIncludeOption = true;
            }

            formulaModel.isCalIncludeOption = isCalIncludeOption;

        }

    }
}
