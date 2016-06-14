package th.co.rcmo.rcmoapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
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
import th.co.rcmo.rcmoapp.API.RequestServices;
import th.co.rcmo.rcmoapp.API.ResponseAPI;
import th.co.rcmo.rcmoapp.Module.mPlantGroup;
import th.co.rcmo.rcmoapp.Module.mProduct;
import th.co.rcmo.rcmoapp.Module.mRiceProduct;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;


public class StepTwoActivity extends Activity {
    public static List<mProduct.mRespBody> productInfoLists =  new ArrayList<>();
    public static List<mPlantGroup.mRespBody> plantGroupLists =  new ArrayList<>();
    public static List<mRiceProduct.mRespBody> riceProductGroupLists =  new ArrayList<>();

    GridView  productGridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_two);

        setUI();
        setAction();

    }

    private void setUI() {
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





    }


    class ProductUIAdapter extends BaseAdapter {
        List<mProduct.mRespBody> productList;

        ProductUIAdapter(List<mProduct.mRespBody> productInfoLists){
            this.productList =productInfoLists;
        }
        @Override
        public int getCount() {
            return  this.productList.size();
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
            if(convertView==null){
                LayoutInflater inflater = StepTwoActivity.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.column_product_grid, parent, false);
            }


            mProduct.mRespBody productBodyResp = getItem(position);

            final int pid =  productBodyResp.getPrdID();
            final int productGroupId = productBodyResp.getPrdGrpID();

            Log.d("TEST"," : pid ->> "+pid);
            Log.d("TEST"," : productGroupId ->> "+productGroupId);

            Holder h = new Holder();
            h.prodImg      = (ImageView) convertView.findViewById(R.id.prodImg);
            h.productLabel = (TextView) convertView.findViewById(R.id.productLabel);
            h.prodBg      =  (LinearLayout)convertView.findViewById(R.id.gridDrawBg);

            h.productLabel.setText(productBodyResp.getPrdName());

            String imgName = ServiceInstance.productIMGMap.get(productBodyResp.getPrdID());

            if(imgName!=null) {
                h.prodImg.setImageResource(getResources().getIdentifier(imgName, "drawable", getPackageName()));
            }
            if(productGroupId == 1){
                h.prodBg.setBackgroundResource(R.drawable.action_plant_ic_circle);
            }else if(productGroupId == 2){
                h.prodBg.setBackgroundResource(R.drawable.action_animal_ic_circle);
            }else {
                h.prodBg.setBackgroundResource(R.drawable.action_fish_ic_circle);
            }



                convertView.findViewById(R.id.gridDrawBg).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(productGroupId == 1 && (pid==1||pid==2)) {
                            API_GetRiceProduct(pid,0);
                        }else{
                            startActivity(new Intent(StepTwoActivity.this, StepThreeActivity.class));

                        }
                    }
                });



            return convertView;
        }
    }


    class PlantProductUIAdapter extends BaseAdapter {
        List<mPlantGroup.mRespBody> productList;

        PlantProductUIAdapter(List<mPlantGroup.mRespBody> productInfoLists){
            this.productList =productInfoLists;
        }
        @Override
        public int getCount() {
            return  this.productList.size();
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
            if(convertView==null){
                LayoutInflater inflater = StepTwoActivity.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.column_product_grid, parent, false);
            }


            mPlantGroup.mRespBody productBodyResp = getItem(position);
           final int plantGroupId =   productBodyResp.getPlantGrpID();



            Holder h = new Holder();
            h.prodImg      = (ImageView) convertView.findViewById(R.id.prodImg);
            h.productLabel = (TextView) convertView.findViewById(R.id.productLabel);
            h.prodBg      =  (LinearLayout)convertView.findViewById(R.id.gridDrawBg);

            h.productLabel.setText(productBodyResp.getPlantGrpName());

            String imgName = ServiceInstance.productIMGMap.get(productBodyResp.getPlantGrpID()+1000);

            if(imgName!=null) {
                h.prodImg.setImageResource(getResources().getIdentifier(imgName, "drawable", getPackageName()));
            }


                h.prodBg.setBackgroundResource(R.drawable.action_plant_ic_circle);

            convertView.findViewById(R.id.gridDrawBg).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   API_GetProduct(1,plantGroupId);
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
            if(convertView==null){
                LayoutInflater inflater = StepTwoActivity.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.column_product_grid, parent, false);
            }


            mRiceProduct.mRespBody productBodyResp = getItem(position);
           final int pid = productBodyResp.getPrdID();
            Holder h = new Holder();
            h.prodImg      = (ImageView) convertView.findViewById(R.id.prodImg);
            h.productLabel = (TextView) convertView.findViewById(R.id.productLabel);
            h.prodBg      =  (LinearLayout)convertView.findViewById(R.id.gridDrawBg);

            h.productLabel.setText(productBodyResp.getPrdName());

            String imgName = ServiceInstance.productIMGMap.get(productBodyResp.getPrdID());

            if(imgName!=null) {
                h.prodImg.setImageResource(getResources().getIdentifier(imgName, "drawable", getPackageName()));
            }


            h.prodBg.setBackgroundResource(R.drawable.action_plant_ic_circle);


            convertView.findViewById(R.id.gridDrawBg).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(StepTwoActivity.this, StepThreeActivity.class)
                            .putExtra(ServiceInstance.INTENT_GROUP_ID_,pid));
                }
            });

            return convertView;
        }
    }


    class Holder{
        TextView productLabel;
        ImageView prodImg;
        LinearLayout prodBg;


    }




    private void API_GetRiceProduct(final int subId,final int subOfSubId) {
        Log.d("TAG", "-->API_GetRiceProduct");
        Log.d("TAG", "subId-->"+subId);
        Log.d("TAG", "SubsubId-->"+subOfSubId);

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
                    startActivity(new Intent(StepTwoActivity.this, StepTwoActivity.class));
                }
            }
            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Erroo",errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getRiceProduct +
                "?RiceTypeID=" + subId+"&PrdID=" );

    }

//Animal && Fish
    private void API_GetProduct(final int prdGrpID,int plantGrpID) {
        Log.d("TAG", "-->API_GetProduct");
        Log.d("TAG", "prdGrpID-->"+prdGrpID);
        Log.d("TAG", "plantGrpID-->"+plantGrpID);

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
                    startActivity(new Intent(StepTwoActivity.this, StepTwoActivity.class));
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


    /**
     *
     */

        /*
        if(groupId == 1) {
            subGroupId = getIntent().getExtras().getInt("SUB_GROUP_ID");

            if(subGroupId == 1) {
                subOfSubGroupId = getIntent().getExtras().getInt("SUB_OF_SUB_GROUP_ID");

                if(subOfSubGroupId == 1){

                }else if(subOfSubGroupId == 2){

                }


            }else if(subGroupId == 2){
                API_GetProduct(1,2);
            }else if(subGroupId == 3){
                API_GetProduct(1,3);
            }else if(subGroupId == 4){
                API_GetProduct(1,4);
            }else{
                API_GetProduct(1,0);
            }
        }else if(groupId == 2){
            API_GetProduct(2,0);
        }else if(groupId == 3){
            API_GetProduct(3,0);

        }
*/
}
