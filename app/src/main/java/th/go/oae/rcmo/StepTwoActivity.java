package th.go.oae.rcmo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.neopixl.pixlui.components.textview.TextView;

import java.util.ArrayList;
import java.util.List;

import me.crosswall.lib.coverflow.CoverFlow;
import me.crosswall.lib.coverflow.core.PagerContainer;
import th.go.oae.rcmo.API.RequestServices;
import th.go.oae.rcmo.API.ResponseAPI;
import th.go.oae.rcmo.Model.ProductModel;
import th.go.oae.rcmo.Model.UserModel;
import th.go.oae.rcmo.Module.mAmphoe;
import th.go.oae.rcmo.Module.mGetProductSuit;
import th.go.oae.rcmo.Module.mPlantGroup;
import th.go.oae.rcmo.Module.mProduct;
import th.go.oae.rcmo.Module.mProvince;
import th.go.oae.rcmo.Module.mRiceProduct;
import th.go.oae.rcmo.Module.mTumbon;
import th.go.oae.rcmo.Util.BitMapHelper;
import th.go.oae.rcmo.Util.ServiceInstance;
import th.go.oae.rcmo.View.DialogChoice;
import th.go.oae.rcmo.View.ProgressAction;


public class StepTwoActivity extends Activity {
   // public static List<mProduct.mRespBody> productInfoLists = new ArrayList<>();
    //public static List<mPlantGroup.mRespBody> plantGroupLists = new ArrayList<>();
    public static List<mGetProductSuit.mRespBody> orgProductSuitLists = new ArrayList<>();
    public static List<mGetProductSuit.mRespBody> productSuitLists = new ArrayList<>();

    public static mGetProductSuit.mRespBody selectedProduct = null;
    public static mProvince.mRespBody  selectedprovince = null;
    public static mAmphoe.mRespBody    selectedAmphoe    = null;
    public static  mTumbon.mRespBody   selectedTumbon   = null;
    String provID;
    String amphoeID;
    String tambonID;
    boolean isPlantSelected =true;
    boolean isAnimalSelected = true;
    boolean isFishSelected =true;
    ProductSuitListAdapter productSuitAdapter = null;
    ViewPager pager = null;
    ViewHolder h = new ViewHolder();

    static class ViewHolder {
        private TextView num_of_market,match_value_label,product_name_label,plant_btn_label,animal_btn_label,fish_btn_label;
        //label_main_search;
        private ImageView star1, star2, star3,product_img,plant_btn_img,animal_btn_img,fish_btn_img,upBtn;
        private LinearLayout layout_zoomInfo,layout_coverFlow,plantBtn,animalBtn,fishBtn;
       // private RelativeLayout layout_province_active, layout_amphoe_active, layout_tambon_active, layout_location_active;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_two);


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
        h.layout_coverFlow  =(LinearLayout) findViewById(R.id.layout_coverFlow);

        h.plantBtn   =(LinearLayout) findViewById(R.id.plantBtn);
        h.animalBtn  =(LinearLayout) findViewById(R.id.animalBtn);
        h.fishBtn    = (LinearLayout) findViewById(R.id.fishBtn);


        setUI();
        setAction();

    }

    private void setUI() {
        initView(false);
        ProgressAction.show(StepTwoActivity.this);
        API_GetProductSuit(provID,amphoeID,tambonID,0,0,0);
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
                    ProductModel prodInfo = new ProductModel(selectedProduct.getPrdID()
                            , selectedProduct.getPrdName()
                            , selectedProduct.getPrdGrpID(),
                            0, 0, null);



                    StepThreeActivity.productionInfo = prodInfo;
                    StepThreeActivity.selectedprovince = selectedprovince;
                    StepThreeActivity.selectedAmphoe   = selectedAmphoe;
                    StepThreeActivity.selectedTumbon   = selectedTumbon;
                    startActivity(new Intent(StepTwoActivity.this, StepThreeActivity.class));

                }
            }
        });





        //tutorial
        findViewById(R.id.btnHowto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    new DialogChoice(StepTwoActivity.this)
                            .ShowTutorial("g10");


            }
        });

        h.plantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(isPlantSelected){
                   isPlantSelected = false;
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
                }else{
                    isFishSelected = true;
                }
                filterActionBtn(3,isFishSelected,h.fishBtn,h.fish_btn_label,h.fish_btn_img);
                filterProductGroup();
            }
        });



    }


    private void initView(boolean isVisible){
        if(isVisible) {
            h.layout_coverFlow.setVisibility(View.VISIBLE);
            h.layout_zoomInfo.setVisibility(View.VISIBLE);
            h.upBtn.setVisibility(View.VISIBLE);
        }else{
            h.layout_coverFlow.setVisibility(View.INVISIBLE);
            h.layout_zoomInfo.setVisibility(View.INVISIBLE);
            h.upBtn.setVisibility(View.GONE);
        }
    }


    private void initCarousels(List<mGetProductSuit.mRespBody> prodInfoList) {
        PagerContainer container = (PagerContainer) findViewById(R.id.pager_container);

        productSuitAdapter = new ProductSuitListAdapter(prodInfoList);
        pager = container.getViewPager();
        pager.setAdapter(productSuitAdapter);
        pager.setClipChildren(false);
        pager.setOffscreenPageLimit(prodInfoList.size());
        if(prodInfoList.size()>0) {
            if(prodInfoList.size()>=5) {
                pager.setCurrentItem(3);
            }
            selectedProduct = prodInfoList.get(pager.getCurrentItem());
            setZoomProductSuitInfo(selectedProduct);

        }else if((productSuitLists.size()==0)){
            initView(false);
        }

        new CoverFlow.Builder()
                .with(pager)
                .scale(0.25f)
                .pagerMargin(-100f)
                .spaceSize(50f)
                .build();

        pager.setPageMargin(30);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            private int index = 0;

            @Override
            public void onPageSelected(int position) {
                index = position;
                //selectedProduct = productSuitLists.get(index);
               // setZoomProductSuitInfo(selectedProduct);

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    selectedProduct = productSuitLists.get(index);
                    setZoomProductSuitInfo(selectedProduct);
                }

            }
        });
       // initView(true);
        ProgressAction.gone(StepTwoActivity.this);
    }


    private void setZoomProductSuitInfo(mGetProductSuit.mRespBody productSuitInfo){
        Log.i("---> ","getPrdName-"+productSuitInfo.getPrdName());
        Log.i("---> ","getSuitLabel-"+productSuitInfo.getSuitLabel());
        Log.i("---> ","getSuitLevel-"+productSuitInfo.getSuitLevel());
        Log.i("---> ","Label-"+productSuitInfo.getMarketCount());



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

        initView(true);

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
        public Object instantiateItem(ViewGroup container, int position) {
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
            if(productSuitLists.size()>=5) {
                pager.setCurrentItem(3);
            }
            selectedProduct = productSuitLists.get(pager.getCurrentItem());
            setZoomProductSuitInfo(selectedProduct);

        }else if((productSuitLists.size()==0)){
            initView(false);
        }


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
                if (productSuitLists.size() != 0) {
                     initCarousels(productSuitLists);
                }
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Erroo", errorMsg);
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
/*
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Step 2", "On Start .....");
        if(productInfoLists!=null && plantGroupLists!=null && riceProductGroupLists!=null ) {
            if (productInfoLists.size() == 0 && plantGroupLists.size() == 0 && riceProductGroupLists.size() == 0) {
                Log.i("Step 2", "Not found data on memory go to start page .....");
                Intent intent = new Intent(StepTwoActivity.this, SplashActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }
    }

    private void setUI() {
        LinearLayout mainLayout  =     (LinearLayout)findViewById(R.id.layoutStepTwo);
        TextView titleText       =      (TextView)findViewById(R.id.titleLable);
        TextView prodHierarchy   =      (TextView)findViewById(R.id.prodHierarchy);

        if(groupId == 1){

            //prodHierarchy.setText(prodHierarchyStr);
            titleText.setText("ชนิดพืช");
            prodHierarchy.setText(prodHierarchyStr);
            //mainLayout.setBackgroundResource(R.drawable.bg_plant);
            mainLayout.setBackground(new BitmapDrawable(BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.bg_plant, 300, 400)));
        }else if(groupId == 2){

            prodHierarchy.setText("");
            titleText.setText("ชนิดปศุสัตว์");
            //mainLayout.setBackgroundResource(R.drawable.bg_meat);
            mainLayout.setBackground(new BitmapDrawable(BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.bg_meat, 300, 400)));
        }else if(groupId == 3){

            prodHierarchy.setText("");
            titleText.setText("ชนิดประมง");
           //mainLayout.setBackgroundResource(R.drawable.bg_fish);
            mainLayout.setBackground(new BitmapDrawable(BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.bg_fish, 300, 400)));
        }
       // ((ImageView) findViewById(R.id.step1_ac)).setImageBitmap(BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.step1_ac, 400, 400));
       // ((ImageView)findViewById(R.id.step2)).setImageBitmap(BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.step2_ac, R.dimen.img_3step_width, R.dimen.img_3step_height));
        //((ImageView) findViewById(R.id.step3)).setImageBitmap (BitMapHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.step3, R.dimen.img_3step_width, R.dimen.img_3step_height));

        productGridView = (GridView) findViewById(R.id.prodGridView);

        if(productInfoLists!=null && productInfoLists.size()>0){

            // Case Animal Fish
            productGridView.setAdapter(new ProductUIAdapter(productInfoLists));
            productInfoLists = null;
        }else if(plantGroupLists!=null && plantGroupLists.size()>0){

            //Case plant group
            productGridView.setAdapter(new PlantProductUIAdapter(plantGroupLists));
            plantGroupLists=null;

        }else if(riceProductGroupLists!=null && riceProductGroupLists.size()>0){

            //Case rice product group
            productGridView.setAdapter(new RiceProductUIAdapter(riceProductGroupLists));
            riceProductGroupLists=null;
        }


    }



    class ProductUIAdapter extends BaseAdapter {
        List<mProduct.mRespBody> productList;

        ProductUIAdapter(List<mProduct.mRespBody> productInfoLists) {
            this.productList = productInfoLists;
        }

        @Override
        public int getCount() {
            return this.productList.size();
        }

        @Override
        public mProduct.mRespBody getItem(int position) {
            return this.productList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public void removeItem(int position) {
            this.productList.remove(position);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder h = new Holder();
            if (convertView == null) {
                LayoutInflater inflater = StepTwoActivity.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.column_product_grid, parent, false);

                h.prodImg = (ImageView) convertView.findViewById(R.id.prodImg);
                h.productLabel = (TextView) convertView.findViewById(R.id.productLabel);
                h.prodBg = (LinearLayout) convertView.findViewById(R.id.gridDrawBg);

                convertView.setTag(h);
            } else {
                h = (Holder) convertView.getTag();
            }


            final mProduct.mRespBody productBodyResp = getItem(position);

            final int pid = productBodyResp.getPrdID();
            final int productGroupId = productBodyResp.getPrdGrpID();
            final String productName = productBodyResp.getPrdName();


            h.productLabel.setText(productBodyResp.getPrdName());

            String imgName = ServiceInstance.productIMGMap.get(productBodyResp.getPrdID());

            if (imgName != null) {
                // h.prodImg.setImageResource(getResources().getIdentifier(imgName, "drawable", getPackageName()));
                h.prodImg.setImageBitmap(BitMapHelper.decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier(imgName, "drawable", getPackageName()), R.dimen.iccircle_img_width, R.dimen.iccircle_img_height));

            }
            if (productGroupId == 1) {
                h.prodBg.setBackgroundResource(R.drawable.action_plant_ic_circle);
            } else if (productGroupId == 2) {
                h.prodBg.setBackgroundResource(R.drawable.action_animal_ic_circle);
            } else {
                h.prodBg.setBackgroundResource(R.drawable.action_fish_ic_circle);
            }


            convertView.findViewById(R.id.gridDrawBg).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (productGroupId == 1 && (pid == 1 || pid == 2)) {

                        API_GetRiceProduct(pid, 0, prodHierarchyStr + "< " + productName);
                    } else {
                        //int prdID, String prdName, int prdGrpID, int plantGrpID, int riceTypeID,String plantHierarchy
                        StepThreeActivity.productionInfo = new ProductModel(productBodyResp.getPrdID()
                                , productBodyResp.getPrdName()
                                , productBodyResp.getPrdGrpID()
                                , gplantGroupId
                                , 0
                                , prodHierarchyStr);

                        startActivity(new Intent(StepTwoActivity.this, StepThreeActivity.class));

                    }
                }
            });


            return convertView;
        }
    }

    class PlantProductUIAdapter extends BaseAdapter {
        List<mPlantGroup.mRespBody> productList;

        PlantProductUIAdapter(List<mPlantGroup.mRespBody> productInfoLists) {
            this.productList = productInfoLists;
        }

        @Override
        public int getCount() {
            return this.productList.size();
        }

        @Override
        public mPlantGroup.mRespBody getItem(int position) {
            return this.productList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public void removeItem(int position) {
            this.productList.remove(position);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder h = new Holder();
            if (convertView == null) {
                LayoutInflater inflater = StepTwoActivity.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.column_product_grid, parent, false);
                h.prodImg = (ImageView) convertView.findViewById(R.id.prodImg);
                h.productLabel = (TextView) convertView.findViewById(R.id.productLabel);
                h.prodBg = (LinearLayout) convertView.findViewById(R.id.gridDrawBg);
                convertView.setTag(h);
            } else {
                h = (Holder) convertView.getTag();
            }


            mPlantGroup.mRespBody productBodyResp = getItem(position);
            final int plantGroupId = productBodyResp.getPlantGrpID();
            final String prodName = productBodyResp.getPlantGrpName();
            gplantGroupId = plantGroupId;


            h.productLabel.setText(productBodyResp.getPlantGrpName());

            String imgName = ServiceInstance.productIMGMap.get(productBodyResp.getPlantGrpID() + 1000);

            if (imgName != null) {
                // h.prodImg.setImageResource(getResources().getIdentifier(imgName, "drawable", getPackageName()));
                h.prodImg.setImageBitmap(BitMapHelper.decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier(imgName, "drawable", getPackageName()), R.dimen.iccircle_img_width, R.dimen.iccircle_img_height));
            }


            h.prodBg.setBackgroundResource(R.drawable.action_plant_ic_circle);

            convertView.findViewById(R.id.gridDrawBg).setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {

                    API_GetProduct(1, plantGroupId, "< " + prodName + " ");
                }
            });

            return convertView;
        }
    }

    class RiceProductUIAdapter extends BaseAdapter {
        List<mRiceProduct.mRespBody> productList;

        RiceProductUIAdapter(List<mRiceProduct.mRespBody> productInfoLists){
            this.productList =productInfoLists;
        }
        @Override
        public int getCount() {
            return  this.productList.size();
        }

        @Override
        public mRiceProduct.mRespBody getItem(int position) {
            return this.productList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public void removeItem(int position) {
            this.productList.remove(position);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder h = new Holder();
            if(convertView==null){
                LayoutInflater inflater = StepTwoActivity.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.column_product_grid, parent, false);

                h.prodImg      = (ImageView) convertView.findViewById(R.id.prodImg);
                h.productLabel = (TextView) convertView.findViewById(R.id.productLabel);
                h.prodBg      =  (LinearLayout)convertView.findViewById(R.id.gridDrawBg);
                convertView.setTag(h);
            }else{
                h = (Holder) convertView.getTag();
            }


           final mRiceProduct.mRespBody productBodyResp = getItem(position);
           final int pid = productBodyResp.getPrdID();


            h.productLabel.setText(productBodyResp.getPrdName());

            String imgName = ServiceInstance.productIMGMap.get(productBodyResp.getPrdID());

            if(imgName!=null) {
                //h.prodImg.setImageResource(getResources().getIdentifier(imgName, "drawable", getPackageName()));
                h.prodImg.setImageBitmap(BitMapHelper.decodeSampledBitmapFromResource(getResources(),getResources().getIdentifier(imgName, "drawable", getPackageName()), R.dimen.iccircle_img_width, R.dimen.iccircle_img_height));
            }


            h.prodBg.setBackgroundResource(R.drawable.action_plant_ic_circle);

//int prdID, String prdName, int prdGrpID, int plantGrpID, int riceTypeID,String plantHierarchy
            convertView.findViewById(R.id.gridDrawBg).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StepThreeActivity.productionInfo =new ProductModel( productBodyResp.getPrdID()
                                                                       ,productBodyResp.getPrdName()
                                                                       ,1
                                                                       ,gplantGroupId
                                                                       ,productBodyResp.getRiceTypeID()
                                                                       ,prodHierarchyStr);
                    startActivity(new Intent(StepTwoActivity.this, StepThreeActivity.class));
                }
            });

            return convertView;
        }
    }

    static class  Holder{
        private TextView productLabel;
        private ImageView prodImg;
        private LinearLayout prodBg;


    }



    private void API_GetRiceProduct(final int subId,final int subOfSubId,final String hierarchyStr) {
        Log.d("TAG", "-->API_GetRiceProduct");
        Log.d("TAG", "subId-->"+subId);
        Log.d("TAG", "SubsubId-->"+subOfSubId);
        Log.d("TAG", "hierarchyStr-->"+hierarchyStr);



        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mRiceProduct mProdist = (mRiceProduct) obj;
                List<mRiceProduct.mRespBody> productLists = mProdist.getRespBody();
                if (productLists.size() != 0) {

                    productLists.get(0).toString();
                    StepTwoActivity.riceProductGroupLists = productLists;
                    startActivity(new Intent(StepTwoActivity.this, StepTwoActivity.class)
                            .putExtra(ServiceInstance.INTENT_GROUP_ID,1)
                            .putExtra(ServiceInstance.INTENT_PROD_HIERARCHY,hierarchyStr));
                }
            }
            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Erroo",errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getRiceProduct +
                "?RiceTypeID=" + subId+"&PrdID=" );

    }
*/
    /*
    private void API_GetProduct(final int prdGrpID, int plantGrpID, final String hierarchyStr) {
        Log.d("TAG", "-->API_GetProduct");
        Log.d("TAG", "prdGrpID-->"+prdGrpID);
        Log.d("TAG", "plantGrpID-->"+plantGrpID);
        Log.d("TAG", "hierarchyStr-->"+hierarchyStr);


        String prdGrpIDStr = "";
        String plantGrpIDStr = "";

        if(prdGrpID == 1){
            plantGrpIDStr = String.valueOf(plantGrpID);
        }

        prdGrpIDStr = String.valueOf(prdGrpID);

        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mProduct mProdist = (mProduct) obj;
                List<mProduct.mRespBody> productLists = mProdist.getRespBody();

                if (productLists.size() != 0) {

                    productLists.get(0).toString();
                    StepTwoActivity.productInfoLists = productLists;
                    startActivity(new Intent(StepTwoActivity.this, StepTwoActivity.class)
                            .putExtra(ServiceInstance.INTENT_GROUP_ID,prdGrpID)
                            .putExtra(ServiceInstance.INTENT_PROD_HIERARCHY,hierarchyStr));
                }
            }
            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Erroo",errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getProduct +
                "?PrdGrpID=" + prdGrpIDStr + "&PlantGrpID="+plantGrpIDStr+
                "&PrdID=" );

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Step 2", "On Stop .....");
    }

*/
}
