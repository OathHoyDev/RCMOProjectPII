package th.go.oae.rcmo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.neopixl.pixlui.components.textview.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import th.go.oae.rcmo.API.RequestServices;
import th.go.oae.rcmo.API.ResponseAPI;
import th.go.oae.rcmo.Adapter.CompareProductDetailAdapter;
import th.go.oae.rcmo.Adapter.CompareProductHeaderAdapter;
import th.go.oae.rcmo.Module.mProductCompare;
import th.go.oae.rcmo.Util.ServiceInstance;
import th.go.oae.rcmo.Util.Util;
import th.go.oae.rcmo.View.DialogChoice;
import th.go.oae.rcmo.View.ProgressAction;

public class CompareProductActivity extends Activity {
    ViewHolder h = new ViewHolder();

    String prodId1 = "";
    String prodId2 = "";
    String prodId3 = "";
    String prodId4 = "";
    String prodGroupId = "0";

    String userId = "";



    class ViewHolder {
        private ListView list_prod1, list_prod2, list_prod3, list_prod4, list_label;
        private LinearLayout prod1_selected, prod2_selected, prod3_selected, prod4_selected,c_table;
        private LinearLayout bg_row_product_1, bg_row_product_2, bg_row_product_3, bg_row_product_4;
        private LinearLayout c_column_2, c_column_3, c_column_4, c_column_5;
        private ImageView img_row_product_1,img_row_product_2,img_row_product_3,img_row_product_4;
        private TextView label_row_product_1,label_row_product_2,label_row_product_3,label_row_product_4;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_product);

        SharedPreferences sp = getSharedPreferences(ServiceInstance.PREF_NAME, Context.MODE_PRIVATE);
        userId = sp.getString(ServiceInstance.sp_userId, "0");

        Bundle b = getIntent().getExtras();

        if(b!=null) {
            prodId1 =b.getString("prodId1","");
            prodId2 =b.getString("prodId2","");
            prodId3 =b.getString("prodId3","");
            prodId4 =b.getString("prodId4","");
            prodGroupId =b.getString("prodGroupId","");

            Log.d("CompareProductActivity","prodId seq :"+prodId1+" , "+prodId2+" , "+prodId3+" , "+prodId4 );
        }


        h.list_prod1 = (ListView) findViewById(R.id.list_prod1);
        h.list_prod2 = (ListView) findViewById(R.id.list_prod2);
        h.list_prod3 = (ListView) findViewById(R.id.list_prod3);
        h.list_prod4 = (ListView) findViewById(R.id.list_prod4);
        h.list_label = (ListView) findViewById(R.id.list_label);

        h.prod1_selected = (LinearLayout) findViewById(R.id.prod1_selected);
        h.prod2_selected = (LinearLayout) findViewById(R.id.prod2_selected);
        h.prod3_selected = (LinearLayout) findViewById(R.id.prod3_selected);
        h.prod4_selected = (LinearLayout) findViewById(R.id.prod4_selected);

        h.c_column_2 = (LinearLayout) findViewById(R.id.c_column_2);
        h.c_column_3 = (LinearLayout) findViewById(R.id.c_column_3);
        h.c_column_4 = (LinearLayout) findViewById(R.id.c_column_4);
        h.c_column_5 = (LinearLayout) findViewById(R.id.c_column_5);

        h.img_row_product_1 =  (ImageView) findViewById(R.id.img_row_product_1);
        h.img_row_product_2 =  (ImageView) findViewById(R.id.img_row_product_2);
        h.img_row_product_3 =  (ImageView) findViewById(R.id.img_row_product_3);
        h.img_row_product_4 =  (ImageView) findViewById(R.id.img_row_product_4);

        h.bg_row_product_1 = (LinearLayout) findViewById(R.id.bg_row_product_1);
        h.bg_row_product_2 = (LinearLayout) findViewById(R.id.bg_row_product_2);
        h.bg_row_product_3 = (LinearLayout) findViewById(R.id.bg_row_product_3);
        h.bg_row_product_4 = (LinearLayout) findViewById(R.id.bg_row_product_4);

        h.label_row_product_1 = (TextView) findViewById(R.id.label_row_product_1);
        h.label_row_product_2 = (TextView) findViewById(R.id.label_row_product_2);
        h.label_row_product_3 = (TextView) findViewById(R.id.label_row_product_3);
        h.label_row_product_4 = (TextView) findViewById(R.id.label_row_product_4);

        h.c_table = (LinearLayout) findViewById(R.id.c_table);

        setUI();
        setAction();

    }

    private void setUI() {

        h.c_table.setVisibility(View.GONE);
        h.c_column_2.setVisibility(View.GONE);
        h.c_column_3.setVisibility(View.GONE);
        h.c_column_4.setVisibility(View.GONE);
        h.c_column_5.setVisibility(View.GONE);

        API_GetProductCompare(prodId1,prodId2,prodId3,prodId4,prodGroupId,userId);


    }

    private void setAction() {
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        h.c_column_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (h.prod1_selected.getVisibility() == View.GONE) {
                    h.prod1_selected.setVisibility(View.VISIBLE);
                    h.prod2_selected.setVisibility(View.GONE);
                    h.prod3_selected.setVisibility(View.GONE);
                    h.prod4_selected.setVisibility(View.GONE);
                } else {
                    h.prod1_selected.setVisibility(View.GONE);
                }

            }
        });

        h.c_column_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (h.prod2_selected.getVisibility() == View.GONE) {
                    h.prod1_selected.setVisibility(View.GONE);
                    h.prod2_selected.setVisibility(View.VISIBLE);
                    h.prod3_selected.setVisibility(View.GONE);
                    h.prod4_selected.setVisibility(View.GONE);
                } else {
                    h.prod1_selected.setVisibility(View.GONE);
                }

            }
        });

        h.c_column_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (h.prod3_selected.getVisibility() == View.GONE) {
                    h.prod1_selected.setVisibility(View.GONE);
                    h.prod2_selected.setVisibility(View.GONE);
                    h.prod3_selected.setVisibility(View.VISIBLE);
                    h.prod4_selected.setVisibility(View.GONE);
                } else {
                    h.prod1_selected.setVisibility(View.GONE);
                }

            }
        });

        h.c_column_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (h.prod4_selected.getVisibility() == View.GONE) {
                    h.prod1_selected.setVisibility(View.GONE);
                    h.prod2_selected.setVisibility(View.GONE);
                    h.prod3_selected.setVisibility(View.GONE);
                    h.prod4_selected.setVisibility(View.VISIBLE);
                } else {
                    h.prod1_selected.setVisibility(View.GONE);
                }

            }
        });

        //tutorial
        findViewById(R.id.btnHowto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DialogChoice(CompareProductActivity.this)
                        .ShowTutorial("g19");


            }
        });

    }


    private void API_GetProductCompare(final String prodId1,final String prodId2, final String prodId3, final String prodId4, final String prodGroupId, final String userId) {
        /*
        1.PrdID1
        2.PrdID2
        3.PrdID3
        4.PrdID4
        5.PrdGrpID
        6.UserID
        7.ImeiCode
         */

        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mProductCompare productCompare = (mProductCompare) obj;
                List<mProductCompare.mRespBody> productComparRespList = productCompare.getRespBody();
                if (productComparRespList.size() != 0) {

                    mProductCompare.mRespBody pcRespBody =  productComparRespList.get(0);
                    List<mProductCompare.mProductValue> prodValList =  pcRespBody.getProductValue();

                    HashMap<String,mProductCompare.mProductValue> hMap = new HashMap<String, mProductCompare.mProductValue>();
                    for(mProductCompare.mProductValue prodVal : prodValList ){
                        hMap.put(prodVal.getPrdID(),prodVal);
                    }

                    List<mProductCompare.mProductValue> sortProdValList = new ArrayList<mProductCompare.mProductValue>();

                    if(hMap.get(prodId1)!=null) {sortProdValList.add(hMap.get(prodId1));}
                    if(hMap.get(prodId2)!=null) {sortProdValList.add(hMap.get(prodId2));}
                    if(hMap.get(prodId3)!=null) {sortProdValList.add(hMap.get(prodId3));}
                    if(hMap.get(prodId4)!=null) {sortProdValList.add(hMap.get(prodId4));}


                    h.list_label.setAdapter(new CompareProductHeaderAdapter(CompareProductActivity.this, pcRespBody.getParamName()));
                    int i =1;
                    int groupId = Util.strToIntegerDefaultZero(prodGroupId);

                    for(mProductCompare.mProductValue prodVal : sortProdValList ){

                        if(i == 1){
                            h.c_column_2.setVisibility(View.VISIBLE);
                            setProdImgUI(i, groupId,Util.strToIntegerDefaultZero(prodVal.getPrdID()),prodVal.getPrdName());
                            h.list_prod1.setAdapter(new CompareProductDetailAdapter(CompareProductActivity.this, prodVal.getParamValue()));
                        }else if(i == 2){
                            h.c_column_3.setVisibility(View.VISIBLE);
                            setProdImgUI(i, groupId,Util.strToIntegerDefaultZero(prodVal.getPrdID()),prodVal.getPrdName());
                            h.list_prod2.setAdapter(new CompareProductDetailAdapter(CompareProductActivity.this, prodVal.getParamValue()));
                        }else if(i == 3){
                            h.c_column_4.setVisibility(View.VISIBLE);
                            setProdImgUI(i, groupId, Util.strToIntegerDefaultZero(prodVal.getPrdID()),prodVal.getPrdName());
                            h.list_prod3.setAdapter(new CompareProductDetailAdapter(CompareProductActivity.this, prodVal.getParamValue()));
                        }else if(i == 4){
                            h.c_column_5.setVisibility(View.VISIBLE);
                            setProdImgUI(i,groupId, Util.strToIntegerDefaultZero(prodVal.getPrdID()),prodVal.getPrdName());
                            h.list_prod4.setAdapter(new CompareProductDetailAdapter(CompareProductActivity.this, prodVal.getParamValue()));
                        }

                     i++;
                    }
                    h.c_table.setVisibility(View.VISIBLE);
                }
                ProgressAction.gone(CompareProductActivity.this);
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
                ProgressAction.gone(CompareProductActivity.this);
            }
        }).API_Request(true, RequestServices.ws_getProductCompare +
                "?PrdID1=" + prodId1
                + "&PrdID2=" + prodId2
                + "&PrdID3=" + prodId3
                + "&PrdID4=" + prodId4
                + "&PrdGrpID=" + prodGroupId
                + "&userId=" + userId
                + "&ImeiCode=" + ServiceInstance.GetDeviceID(CompareProductActivity.this)
        );

    }

    private void setProdImgUI(int prodSeq,int prodGroupId , int prodId,String prodName){


        ImageView tmpImg = null;
        LinearLayout tmpBg = null;
        TextView tmpLabel = null;
        if(prodSeq == 1){
            tmpImg = h.img_row_product_1;
            tmpBg  = h.bg_row_product_1;
            tmpLabel = h.label_row_product_1;
        }else if(prodSeq == 2){
            tmpImg = h.img_row_product_2;
            tmpBg  = h.bg_row_product_2;
            tmpLabel = h.label_row_product_2;
        }else if(prodSeq == 3){
            tmpImg = h.img_row_product_3;
            tmpBg  = h.bg_row_product_3;
            tmpLabel = h.label_row_product_3;
        }else if(prodSeq == 4){
            tmpImg = h.img_row_product_4;
            tmpBg  = h.bg_row_product_4;
            tmpLabel = h.label_row_product_4;
        }


        String imgName = ServiceInstance.productIMGMap.get(prodId);
        if (imgName != null) {
            tmpImg.setImageResource(getResources().getIdentifier(imgName, "drawable", getPackageName()));
        }

        tmpLabel.setText(prodName);

        if (prodGroupId==1) {
            tmpBg.setBackgroundResource(R.drawable.plant_ic_circle_bg);
        } else if (prodGroupId==2) {
            tmpBg.setBackgroundResource(R.drawable.animal_ic_circle_bg);
        } else {
            tmpBg.setBackgroundResource(R.drawable.fish_ic_circle_bg);
        }
    }


}
