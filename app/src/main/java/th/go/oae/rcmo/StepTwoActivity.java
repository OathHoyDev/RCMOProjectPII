package th.go.oae.rcmo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.neopixl.pixlui.components.textview.TextView;
import java.util.ArrayList;
import java.util.List;
import th.go.oae.rcmo.API.RequestServices;
import th.go.oae.rcmo.API.ResponseAPI;
import th.go.oae.rcmo.Model.ProductModel;
import th.go.oae.rcmo.Module.mPlantGroup;
import th.go.oae.rcmo.Module.mProduct;
import th.go.oae.rcmo.Module.mRiceProduct;
import th.go.oae.rcmo.Util.BitMapHelper;
import th.go.oae.rcmo.Util.ServiceInstance;
import th.go.oae.rcmo.View.DialogChoice;


public class StepTwoActivity extends Activity {
    public static List<mProduct.mRespBody> productInfoLists =  new ArrayList<>();
    public static List<mPlantGroup.mRespBody> plantGroupLists =  new ArrayList<>();
    public static List<mRiceProduct.mRespBody> riceProductGroupLists =  new ArrayList<>();
    int groupId =0;
    String prodHierarchyStr = "";
    GridView  productGridView;
    int gplantGroupId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        gplantGroupId = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_two);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            groupId = bundle.getInt(ServiceInstance.INTENT_GROUP_ID);
            prodHierarchyStr = bundle.getString(ServiceInstance.INTENT_PROD_HIERARCHY);
            if(prodHierarchyStr == null){prodHierarchyStr="";}

        }

        setUI();
        setAction();

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

    private void setAction() {
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.prodHierarchy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //tutorial
        findViewById(R.id.btnHowto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(groupId==1) {
                    if(gplantGroupId == 0) {
                        new DialogChoice(StepTwoActivity.this)
                                .ShowTutorial("g8");
                    }else{
                        new DialogChoice(StepTwoActivity.this)
                                .ShowTutorial("g7");
                    }
                }else if(groupId==2){
                    new DialogChoice(StepTwoActivity.this)
                            .ShowTutorial("g9");
                }else{
                    new DialogChoice(StepTwoActivity.this)
                            .ShowTutorial("g10");
                }

            }
        });



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


/*====================== API ============================*/

    private void API_GetRiceProduct(final int subId,final int subOfSubId,final String hierarchyStr) {
        Log.d("TAG", "-->API_GetRiceProduct");
        Log.d("TAG", "subId-->"+subId);
        Log.d("TAG", "SubsubId-->"+subOfSubId);
        Log.d("TAG", "hierarchyStr-->"+hierarchyStr);

         /*
          1.RiceTypeID (ไม่บังคับใส่)
          2.PrdID (ไม่บังคับ)
        */


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

    private void API_GetProduct(final int prdGrpID, int plantGrpID, final String hierarchyStr) {
        Log.d("TAG", "-->API_GetProduct");
        Log.d("TAG", "prdGrpID-->"+prdGrpID);
        Log.d("TAG", "plantGrpID-->"+plantGrpID);
        Log.d("TAG", "hierarchyStr-->"+hierarchyStr);

         /*
        1.PrdGrpID (ไม่บังคับใส่)
        2.PlantGrpID (บังคับกรณี PrdGrpID = 1)
        3.PrdID (ไม่บังคับ)
        */
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


}
