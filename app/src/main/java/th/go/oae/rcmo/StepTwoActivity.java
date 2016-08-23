package th.go.oae.rcmo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.neopixl.pixlui.components.textview.TextView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.crosswall.lib.coverflow.CoverFlow;
import me.crosswall.lib.coverflow.core.PagerContainer;
import th.go.oae.rcmo.API.RequestServices;
import th.go.oae.rcmo.API.ResponseAPI;
import th.go.oae.rcmo.Model.ProductModel;
import th.go.oae.rcmo.Model.UserPlotModel;
import th.go.oae.rcmo.Module.mAmphoe;
import th.go.oae.rcmo.Module.mCompareStatus;
import th.go.oae.rcmo.Module.mGetProductSuit;
import th.go.oae.rcmo.Module.mProvince;
import th.go.oae.rcmo.Module.mTumbon;
import th.go.oae.rcmo.Util.ServiceInstance;
import th.go.oae.rcmo.View.DialogChoice;
import th.go.oae.rcmo.View.ProgressAction;


public class StepTwoActivity extends Activity {
    public static List<mGetProductSuit.mRespBody> orgProductSuitLists = new ArrayList<>();
    public static List<mGetProductSuit.mRespBody> productSuitLists = new ArrayList<>();
    public static List<mGetProductSuit.mRespBody> productSuitCompareLists = new ArrayList<>();
    public static mGetProductSuit.mRespBody selectedProduct = null;
    public static mProvince.mRespBody  selectedprovince = null;
    public static mAmphoe.mRespBody    selectedAmphoe    = null;
    public static  mTumbon.mRespBody   selectedTumbon   = null;
    String provID;
    String amphoeID;
    String tambonID;
    boolean isTablet = false;
    boolean isPlantSelected =true;
    boolean isAnimalSelected = true;
    boolean isFishSelected =true;
    Map<Integer,mGetProductSuit.mRespBody> map = new LinkedHashMap();
    ProductSuitListAdapter productSuitAdapter = null;
    ViewPager pager = null;
    Map<Integer,Integer> mapCompare = new HashMap<Integer,Integer>();
    ViewHolder h = new ViewHolder();
    private SlidingUpPanelLayout mLayout;
    int currentPosition = 0;
    private static final String TAG = "StepTwoActivity";


    CompareProductListAdaptor compareProductListAdaptor = null;

    static class ViewHolder {
        private TextView num_of_market,match_value_label,product_name_label,plant_btn_label,animal_btn_label,fish_btn_label,prod_info_ref;
        private ImageView star1, star2, star3,product_img,plant_btn_img,animal_btn_img,fish_btn_img,upBtn;
        private LinearLayout layout_zoomInfo,plantBtn,animalBtn,fishBtn;
        private ListView productList ;
        private MediaPlayer chg_prd_sound  , slide_up_down_sound ;
        private RelativeLayout layout_coverFlow;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_two);
        if(ServiceInstance.isTablet(StepTwoActivity.this)){
          isTablet = true;
        }else{
            isTablet = false;
        }
        /*
        if(ServiceInstance.isTablet(StepOneActivity.this)){
            Log.d("TEST","-->TabLet");
            setContentView(R.layout.activity_step_one_tablet);
        }else{
            setContentView(R.layout.activity_step_one);
        }

*/
            provID = selectedprovince.getProvCode();
            amphoeID = selectedAmphoe.getAmpCode();
            tambonID =selectedTumbon.getTamCode();



        h.num_of_market  =(TextView) findViewById(R.id.num_of_market);
        h.match_value_label  =(TextView) findViewById(R.id.match_value_label);
        h.product_name_label  =(TextView) findViewById(R.id.product_name_label);

        h.plant_btn_label  =(TextView) findViewById(R.id.plant_btn_label);
        h.animal_btn_label  =(TextView) findViewById(R.id.animal_btn_label);
        h.fish_btn_label  =(TextView) findViewById(R.id.fish_btn_label);

        h.star1  =(ImageView) findViewById(R.id.star1);
        h.star2  =(ImageView) findViewById(R.id.star2);
        h.star3 =(ImageView) findViewById(R.id.star3);
        h.product_img =(ImageView) findViewById(R.id.product_img);
        h.upBtn =(ImageView) findViewById(R.id.upBtn);


        h.plant_btn_img  =(ImageView) findViewById(R.id.plant_btn_img);
        h.animal_btn_img  =(ImageView) findViewById(R.id.animal_btn_img);
        h.fish_btn_img =(ImageView) findViewById(R.id.fish_btn_img);

        h.layout_zoomInfo  =(LinearLayout) findViewById(R.id.layout_zoomInfo);
        h.prod_info_ref  =(TextView) findViewById(R.id.prod_info_ref);
        h.layout_coverFlow  =(RelativeLayout) findViewById(R.id.layout_coverFlow);

        h.plantBtn   =(LinearLayout) findViewById(R.id.plantBtn);
        h.animalBtn  =(LinearLayout) findViewById(R.id.animalBtn);
        h.fishBtn    = (LinearLayout) findViewById(R.id.fishBtn);

        h.productList = (ListView) findViewById(R.id.list);

        h.chg_prd_sound = MediaPlayer.create(StepTwoActivity.this, R.raw.step2_chg_prod);
        h.slide_up_down_sound = MediaPlayer.create(StepTwoActivity.this, R.raw.step2_sli_compare);



        setUI();
        setAction();

    }

    private void setUI() {
        initView(false);
        ProgressAction.show(StepTwoActivity.this);
        API_GetCompareStatus();

    }

    private void setAction() {
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.step3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedProduct != null){
                    ProgressAction.show(StepTwoActivity.this);
                    ProductModel prodInfo = new ProductModel(selectedProduct.getPrdID()
                            , selectedProduct.getPrdName()
                            , selectedProduct.getPrdGrpID(),
                            0, 0, null);



                    StepThreeActivity.productionInfo = prodInfo;
                    StepThreeActivity.selectedprovince = selectedprovince;
                    StepThreeActivity.selectedAmphoe   = selectedAmphoe;
                    StepThreeActivity.selectedTumbon   = selectedTumbon;
                    startActivity(new Intent(StepTwoActivity.this, StepThreeActivity.class));

                }else{
                    new DialogChoice(StepTwoActivity.this)
                           .ShowOneChoice("", "กรุณาเลือกสินค้าก่อนกดปุ่ม'ถัดไป'");
                }
            }
        });





        //tutorial
        findViewById(R.id.btnHowto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    new DialogChoice(StepTwoActivity.this)
                            .ShowTutorial("g7");


            }
        });

        h.plantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(isPlantSelected){
                   isPlantSelected = false;
                   checkResetSelectProduct();
               }else{
                   isPlantSelected = true;
               }

                filterActionBtn(1,isPlantSelected,h.plantBtn,h.plant_btn_label,h.plant_btn_img);
                filterProductGroup();
            }
        });

        h.animalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAnimalSelected){
                    isAnimalSelected = false;
                    checkResetSelectProduct();
                }else{
                    isAnimalSelected = true;
                }

                filterActionBtn(2,isAnimalSelected,h.animalBtn,h.animal_btn_label,h.animal_btn_img);
                filterProductGroup();
            }
        });


        h.fishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFishSelected){
                    isFishSelected = false;
                    checkResetSelectProduct();
                }else{
                    isFishSelected = true;
                }
                filterActionBtn(3,isFishSelected,h.fishBtn,h.fish_btn_label,h.fish_btn_img);
                filterProductGroup();
            }
        });

        findViewById(R.id.compareBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent i =new Intent(StepTwoActivity.this, CompareProductActivity.class);

                i.putExtra("prodId1"    , String.valueOf(selectedProduct.getPrdID()));
                i.putExtra("prodGroupId", String.valueOf(selectedProduct.getPrdGrpID()));

                int index = 2;
                for ( Integer key : map.keySet() ) {
                    i.putExtra("prodId"+index, String.valueOf(key));
                    index++;
                }
                startActivity(i);


            }
        });
        findViewById(R.id.market_map_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedProduct != null) {
                    StepTwoMapActivity.userPlotModel = new UserPlotModel();
                    StepTwoMapActivity.userPlotModel.setPrdID(String.valueOf(selectedProduct.getPrdID()));
                    StepTwoMapActivity.userPlotModel.setPrdValue(selectedProduct.getPrdName());
                    StepTwoMapActivity.userPlotModel.setProvCode(provID);
                    StepTwoMapActivity.userPlotModel.setAmpCode(amphoeID);
                    StepTwoMapActivity.userPlotModel.setTamCode(tambonID);
                    startActivity(new Intent(StepTwoActivity.this, StepTwoMapActivity.class));


                }


            }
        });



    }

    private void initView(boolean isVisible){
        if(isVisible) {
            h.layout_coverFlow.setVisibility(View.VISIBLE);
            h.layout_zoomInfo.setVisibility(View.VISIBLE);
            h.prod_info_ref.setVisibility(View.VISIBLE);
          //  h.upBtn.setVisibility(View.VISIBLE);
        }else{
            h.layout_coverFlow.setVisibility(View.INVISIBLE);
            h.layout_zoomInfo.setVisibility(View.INVISIBLE);
            h.upBtn.setVisibility(View.GONE);
            h.prod_info_ref.setVisibility(View.INVISIBLE);
        }
    }

    private void initCarousels(List<mGetProductSuit.mRespBody> prodInfoList) {
        PagerContainer container = (PagerContainer) findViewById(R.id.pager_container);

        productSuitAdapter = new ProductSuitListAdapter(prodInfoList);
        pager = container.getViewPager();
        pager.setAdapter(productSuitAdapter);
        pager.setClipChildren(false);
        pager.setOffscreenPageLimit(prodInfoList.size());

/*

                pager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Log.i("Tag","0000000000000000000000000000000");
            }


    });
    */

        if(prodInfoList.size()>0) {
            /*
            if(prodInfoList.size()>=5) {
                pager.setCurrentItem(3);
            }
            */
            selectedProduct = prodInfoList.get(pager.getCurrentItem());
            setZoomProductSuitInfo(selectedProduct);
            filterProductGroupProductToCompare(selectedProduct.getPrdGrpID());
            checkDisplayCompareBtn();
           // compareProductList.resetProductList(setListProductToCompareView(selectedProduct.getPrdGrpID()));

        }else if((productSuitLists.size()==0)){
            initView(false);
        }
        if(isTablet){
            new CoverFlow.Builder()
                    .with(pager)
                    .scale(0.2f)
                    .pagerMargin(0f)
                    .spaceSize(0f)
                    .build();

            pager.setPageMargin(-25);
        }else{
            new CoverFlow.Builder()
                    .with(pager)
                    .scale(0.25f)
                    .pagerMargin(-100f)
                    .spaceSize(50f)
                    .build();

            pager.setPageMargin(20);
        }




        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            private int index = 0;

            @Override
            public void onPageSelected(int position) {
                index = position;
                currentPosition = position;
                //selectedProduct = productSuitLists.get(index);
               // setZoomProductSuitInfo(selectedProduct);

                int oldGroup = 0;
                if( selectedProduct != null) {
                     oldGroup = selectedProduct.getPrdGrpID();
                }
                int newGroup = productSuitLists.get(index).getPrdGrpID();

                selectedProduct = productSuitLists.get(index);
                setZoomProductSuitInfo(selectedProduct);

                if(oldGroup!=newGroup) {
                    Log.i(TAG,"Old Group :"+oldGroup);
                    Log.i(TAG,"New Group :"+newGroup);
                    //initView(true);
                    filterProductGroupProductToCompare(selectedProduct.getPrdGrpID());
                    checkDisplayCompareBtn();
                }else{
                    compareProductListAdaptor.resetProductList(productSuitCompareLists);
                }
                displaySound(h.chg_prd_sound);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {

                }

            }
        });
       initView(true);
        ProgressAction.gone(StepTwoActivity.this);
    }

    private void checkDisplayCompareBtn(){
        if(mapCompare.get(selectedProduct.getPrdGrpID())==0){
            h.upBtn.setVisibility(View.INVISIBLE);
            mLayout.setEnabled(false);
        }else {
            h.upBtn.setVisibility(View.VISIBLE);
            mLayout.setEnabled(true);
        }
    }

    private void initSlideView(List productToCompareList){


        compareProductListAdaptor = new CompareProductListAdaptor(productToCompareList);
        h.productList.setAdapter(compareProductListAdaptor);


        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

                Log.i(TAG, "onPanelStateChanged " + newState);
                if(SlidingUpPanelLayout.PanelState.EXPANDED.equals(newState)){
                    findViewById(R.id.upBtn).setVisibility(View.INVISIBLE);
                    findViewById(R.id.downBtn).setVisibility(View.VISIBLE);

                }
                if(SlidingUpPanelLayout.PanelState.EXPANDED.equals(previousState)){

                    displaySound(h.slide_up_down_sound);

                }

                if(SlidingUpPanelLayout.PanelState.COLLAPSED.equals(newState)){


                        findViewById(R.id.upBtn).setVisibility(View.VISIBLE);
                        findViewById(R.id.downBtn).setVisibility(View.INVISIBLE);



                }
                if(SlidingUpPanelLayout.PanelState.COLLAPSED.equals(previousState)){
                    displaySound(h.slide_up_down_sound);

                }


            }
        });
        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                /mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }
        });


    }

    private void displaySound(MediaPlayer sound) {
        if (sound != null) {

            sound.start();
        }
    }

    private void checkResetSelectProduct(){
        if(!(isPlantSelected || isAnimalSelected || isFishSelected)){
               selectedProduct = null;
                filterProductGroupProductToCompare(99);
        }
    }

    private void setZoomProductSuitInfo(mGetProductSuit.mRespBody productSuitInfo){
        /*
        Log.i("---> ","getPrdName-"+productSuitInfo.getPrdName());
        Log.i("---> ","getSuitLabel-"+productSuitInfo.getSuitLabel());
        Log.i("---> ","getSuitLevel-"+productSuitInfo.getSuitLevel());
        Log.i("---> ","Label-"+productSuitInfo.getMarketCount());
*/


        h.product_name_label.setText(productSuitInfo.getPrdName());
        h.num_of_market.setText(String.valueOf(productSuitInfo.getMarketCount()));
        if(productSuitInfo.getSuitLabel() !=null && !productSuitInfo.getSuitLabel().equals("")) {
            h.match_value_label.setText(productSuitInfo.getSuitLabel());
        }else{
            h.match_value_label.setText("ไม่พบข้อมูล");
        }

        String imgName = ServiceInstance.productIMGMap.get(productSuitInfo.getPrdID());

        if (imgName != null) {
            h.product_img.setImageResource(getResources().getIdentifier(imgName, "drawable", getPackageName()));
        }

        setStar(productSuitInfo.getSuitLevel(),h.star1,h.star2,h.star3);

       // initView(true);

    }

    private void setStar(String level,ImageView s1,ImageView s2,ImageView s3){
       if("1".equals(level)){
           s1.setImageResource(R.drawable.start_3d);
           s2.setImageResource(R.drawable.start_3d_no);
           s3.setImageResource(R.drawable.start_3d_no);
       }else if("2".equals(level)){
           s1.setImageResource(R.drawable.start_3d);
           s2.setImageResource(R.drawable.start_3d);
           s3.setImageResource(R.drawable.start_3d_no);
       }else if("3".equals(level)){
           s1.setImageResource(R.drawable.start_3d);
           s2.setImageResource(R.drawable.start_3d);
           s3.setImageResource(R.drawable.start_3d);
       }else{
           s1.setImageResource(R.drawable.start_3d_no);
           s2.setImageResource(R.drawable.start_3d_no);
           s3.setImageResource(R.drawable.start_3d_no);
       }


    }

    private class ProductSuitListAdapter extends PagerAdapter {
        List<mGetProductSuit.mRespBody> productList = new ArrayList<mGetProductSuit.mRespBody>();

        public ProductSuitListAdapter(List<mGetProductSuit.mRespBody> products) {
            this.productList = products;
        }


        public void setAdapterList(List<mGetProductSuit.mRespBody> products){
            this.productList = products;
            notifyDataSetChanged();
        }


        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            mGetProductSuit.mRespBody prod = productList.get(position);


            View         rootView     = getLayoutInflater().inflate(R.layout.row_carousels, container, false);
            ImageView    imageView    = (ImageView) rootView.findViewById(R.id.prodImg);
            LinearLayout row_product  = (LinearLayout) rootView.findViewById(R.id.row_product);
            String imgName = ServiceInstance.productIMGMap.get(prod.getPrdID());

            if (imgName != null) {
                imageView.setImageResource(getResources().getIdentifier(imgName, "drawable", getPackageName()));
            }

            if (prod.getPrdGrpID()==1) {
                row_product.setBackgroundResource(R.drawable.plant_ic_circle_bg);
            } else if (prod.getPrdGrpID()==2) {
                row_product.setBackgroundResource(R.drawable.animal_ic_circle_bg);
            } else {
                row_product.setBackgroundResource(R.drawable.fish_ic_circle_bg);
            }

/*
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG,"Clickkkkkkkkkkkkk"+position);
                    pager.setCurrentItem(position);

                }
            });
*/

            setStar(prod.getSuitLevel()
                    , (ImageView) rootView.findViewById(R.id.row_star1)
                    , (ImageView) rootView.findViewById(R.id.row_star2)
                    , (ImageView) rootView.findViewById(R.id.row_star3));

            container.addView(rootView);

            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return productList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }
    }

    private void filterActionBtn(int grdId,boolean isActive , LinearLayout bg , TextView label , ImageView activeImg){
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(isActive){

            if(grdId == 1) {
                bg.setBackground(ContextCompat.getDrawable(this, R.drawable.plant_cut_conner));
            }else if(grdId == 2){
                bg.setBackground(ContextCompat.getDrawable(this, R.drawable.animal_cut_conner));
            }else{
                bg.setBackground(ContextCompat.getDrawable(this, R.drawable.fish_cut_conner));
            }

            label.setTextColor(ContextCompat.getColor(this,R.color.RcmoWhiteBG));
            activeImg.setVisibility(View.VISIBLE);
        }else{
            label.setTextColor(ContextCompat.getColor(this,R.color.text_gray_soft));
            bg.setBackground(ContextCompat.getDrawable(this, R.drawable.gray_cut_conner));
            activeImg.setVisibility(View.GONE);
        }
    }

    private void filterProductGroup(){
        int grdId =0;
        productSuitLists = new ArrayList<>();
          for(mGetProductSuit.mRespBody prodSuit : orgProductSuitLists){

              grdId = prodSuit.getPrdGrpID();
               if(isPlantSelected && grdId == 1){
                   productSuitLists.add(prodSuit);
               }else if(isAnimalSelected && grdId == 2){
                   productSuitLists.add(prodSuit);
               }else if(isFishSelected && grdId == 3){
                   productSuitLists.add(prodSuit);
               }
          }


        productSuitAdapter.setAdapterList(productSuitLists);
         pager.setAdapter(new ProductSuitListAdapter(productSuitLists));
        pager.setOffscreenPageLimit(productSuitLists.size());
        if(productSuitLists.size()>0) {
            /*
            if(productSuitLists.size()>=5) {
                pager.setCurrentItem(3);
            }
            */
            initView(true);
            selectedProduct = productSuitLists.get(pager.getCurrentItem());
            setZoomProductSuitInfo(selectedProduct);
           filterProductGroupProductToCompare(selectedProduct.getPrdGrpID());
            checkDisplayCompareBtn();

        }else if((productSuitLists.size()==0)){
            initView(false);
        }


    }

    private void filterProductGroupProductToCompare(int flag){
        productSuitCompareLists  = new ArrayList<>();
       // productSuitCompareLists.clear();
        Log.i(TAG," Select group Flag = "+flag);
        for(mGetProductSuit.mRespBody tempProd : orgProductSuitLists){
            if(flag == tempProd.getPrdGrpID()){
                productSuitCompareLists.add(tempProd);
            }
        }
        compareProductListAdaptor.resetProductList(productSuitCompareLists);
       // return productSuitCompareLists;
    }

    private void API_GetProductSuit(String provId, String amphoeId, String tambonId, int plantFlg, int animalFlg, int fishFlg) {
        /*
        1.TamCode (บังคับ)
        2.AmpCode (บังคับ)
        3.ProvCode ( บังคับ)
        4.PlantFlag
        5.AnimalFlag
        6.FisheryFlag
         */

        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mGetProductSuit productSuit = (mGetProductSuit) obj;
                orgProductSuitLists = productSuit.getRespBody();
                productSuitLists = orgProductSuitLists;
                productSuitCompareLists = orgProductSuitLists;
                if (productSuitLists.size() != 0) {
                     initSlideView(productSuitCompareLists);
                     initCarousels(productSuitLists);

                }
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
                ProgressAction.gone(StepTwoActivity.this);
            }
        }).API_Request(true, RequestServices.ws_getProductSuit +
                "?TamCode=" + tambonId
                + "&AmpCode=" + amphoeId
                + "&ProvCode=" + provId
                + "&PlantFlag=" + plantFlg
                + "&AnimalFlag=" + animalFlg
                + "&FisheryFlag=" + fishFlg
        );

    }

    private void API_GetCompareStatus() {


        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mCompareStatus compareStatus = (mCompareStatus) obj;
                List<mCompareStatus.mRespBody> compareStatusLists = compareStatus.getRespBody();

                if (compareStatusLists.size() != 0) {
                    for (mCompareStatus.mRespBody tmpList : compareStatusLists) {

                        mapCompare.put(tmpList.getPrdGrpID(), tmpList.getCompareStatus());

                        //Log.i(TAG, "Compare status obj :" + tmpList.toString());
                    }
                }

                API_GetProductSuit(provID, amphoeID, tambonID, 0, 0, 0);
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
                ProgressAction.gone(StepTwoActivity.this);
            }
        }).API_Request(true, RequestServices.ws_getCompareStatus
        );

    }

     class PCViewHolder {
        private TextView pc_product_name_label,pc_star_label;
        private ImageView pc_row_prodImg,pc_row_prodImg_selected,pc_row_star1, pc_row_star2, pc_row_star3;
        private LinearLayout pc_row_product,row_layout;

        private  LinearLayout pc_row_product_active,pc_row_product_selected;
    }

    class CompareProductListAdaptor extends BaseAdapter {
        List<mGetProductSuit.mRespBody> resultList;

        CompareProductListAdaptor(List<mGetProductSuit.mRespBody> resultList) {
            this.resultList = resultList;
        }

        public  void resetProductList (List<mGetProductSuit.mRespBody> newresultList){
            //resultList.clear();


            resultList = newresultList;
            Log.i(TAG,"R-->newresultList Size "+newresultList.size());
            notifyDataSetChanged();
            map.clear();

        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public mGetProductSuit.mRespBody getItem(int position) {

            return resultList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            PCViewHolder pch = new PCViewHolder();

            if (convertView == null) {
                LayoutInflater inflater = StepTwoActivity.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.row_prod_to_compare, parent, false);
                pch.pc_star_label = (TextView) convertView.findViewById(R.id.pc_star_label);
                pch.pc_product_name_label = (TextView) convertView.findViewById(R.id.pc_product_name_label);
                pch.pc_row_prodImg = (ImageView) convertView.findViewById(R.id.pc_row_prodImg);
                pch.pc_row_prodImg_selected = (ImageView) convertView.findViewById(R.id.pc_row_prodImg_selected);
                pch.pc_row_star1 = (ImageView) convertView.findViewById(R.id.pc_row_star1);
                pch.pc_row_star2 = (ImageView) convertView.findViewById(R.id.pc_row_star2);
                pch.pc_row_star3 = (ImageView) convertView.findViewById(R.id.pc_row_star3);
                pch.pc_row_product = (LinearLayout) convertView.findViewById(R.id.pc_row_product);
                pch.row_layout = (LinearLayout) convertView.findViewById(R.id.row_layout);
                pch.pc_row_product_active =  (LinearLayout) convertView.findViewById(R.id.pc_row_product_active);
                pch.pc_row_product_selected = (LinearLayout) convertView.findViewById(R.id.pc_row_product_selected);
                convertView.setTag(pch);
            } else {
                pch = (PCViewHolder) convertView.getTag();
            }

          final  mGetProductSuit.mRespBody prodList = resultList.get(position);


            if (selectedProduct.getPrdID() == prodList.getPrdID()) {
                pch.pc_row_product_selected.setVisibility(View.VISIBLE);
            }else{
                pch.pc_row_product_selected.setVisibility(View.GONE);
            }
            if(map.get(prodList.getPrdID())==null){
                pch.pc_row_product_active.setVisibility(View.GONE);
            }else{
                pch.pc_row_product_active.setVisibility(View.VISIBLE);
            }

            pch.pc_product_name_label.setText(prodList.getPrdName());

            if(prodList.getSuitLabel() !=null && !prodList.getSuitLabel().equals("")) {
                pch.pc_star_label.setText(prodList.getSuitLabel());
            }else{
                pch.pc_star_label.setText("ไม่พบข้อมูล");
            }

            //pch.pc_star_label.setText(prodList.getSuitLabel());


            setStar(prodList.getSuitLevel(), pch.pc_row_star1, pch.pc_row_star2, pch.pc_row_star3);

            String imgName = ServiceInstance.productIMGMap.get(prodList.getPrdID());

            if (imgName != null) {
                pch.pc_row_prodImg.setImageResource(getResources().getIdentifier(imgName, "drawable", getPackageName()));
            }

            if (prodList.getPrdGrpID()==1) {
                pch.pc_row_product.setBackgroundResource(R.drawable.plant_ic_circle_bg);
            } else if (prodList.getPrdGrpID()==2) {
                pch.pc_row_product.setBackgroundResource(R.drawable.animal_ic_circle_bg);
            } else {
                pch.pc_row_product.setBackgroundResource(R.drawable.fish_ic_circle_bg);
            }

               final  LinearLayout  activeLayout =  pch.pc_row_product_active;
                pch.row_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "pc_row_product_active-->setOnLongClickListener ");


                        if (activeLayout.getVisibility() == View.GONE) {
                            if (map.size() < 3 && selectedProduct.getPrdID() != prodList.getPrdID()) {
                                map.put(prodList.getPrdID(), prodList);
                                activeLayout.setVisibility(View.VISIBLE);
                            }

                        } else {
                            map.remove(prodList.getPrdID());
                            activeLayout.setVisibility(View.GONE);

                        }

                    }
                });

            return convertView;
        }

    }


    @Override
    public void onStop() {
        super.onStop();
        Log.d("StepTwoActivity", "onResume  ProgressAction.gone ... ");
        ProgressAction.gone(StepTwoActivity.this);
    }
}
