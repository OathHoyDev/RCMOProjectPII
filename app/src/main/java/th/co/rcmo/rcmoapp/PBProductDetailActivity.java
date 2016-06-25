package th.co.rcmo.rcmoapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.neopixl.pixlui.components.textview.TextView;

import java.util.ArrayList;
import java.util.List;

import th.co.rcmo.rcmoapp.API.ProductService;
import th.co.rcmo.rcmoapp.API.RequestServices;
import th.co.rcmo.rcmoapp.API.ResponseAPI;
import th.co.rcmo.rcmoapp.Adapter.PBCalculatePagerAdapter;
import th.co.rcmo.rcmoapp.Model.STDVarModel;
import th.co.rcmo.rcmoapp.Model.UserPlotModel;
import th.co.rcmo.rcmoapp.Module.mGetVariable;

public class PBProductDetailActivity extends AppCompatActivity {
    public static ViewPager  pager;
    TabLayout tabLayout;
    public static UserPlotModel userPlotModel = new UserPlotModel();
    public static List<STDVarModel> stdVarModelList = new ArrayList<STDVarModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pbproduct_detail);

        setUI(Integer.valueOf(userPlotModel.getPrdGrpID()));

        setAction();

        pager= (ViewPager) findViewById(R.id.view_pager);
        FragmentManager manager=getSupportFragmentManager();
        PBCalculatePagerAdapter adapter=new PBCalculatePagerAdapter(manager);
        pager.setAdapter(adapter);


        tabLayout.setupWithViewPager(pager);
        tabLayout.setBackgroundResource(R.color.RcmoWhiteBG);
    //    pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout) {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
               // Toast toast = Toast.makeText( PBProductDetailActivity.this, "onPageScrolled", Toast.LENGTH_SHORT);
               // toast.show();
            }

            @Override
            public void onPageSelected(int position) {
               // Toast toast = Toast.makeText( PBProductDetailActivity.this, "onPageSelected", Toast.LENGTH_SHORT);
               // toast.show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //tabLayout.setBackgroundResource(R.color.RcmoWhiteBG);
               // Toast toast = Toast.makeText( PBProductDetailActivity.this, "onPageScrollStateChanged", Toast.LENGTH_SHORT);
                //toast.show();
            }
        });

        tabLayout.setTabsFromPagerAdapter(adapter);
       // tabLayout.setBackgroundResource(R.color.RcmoLightTranBG);

        setTabStyle(adapter);

        pager.setCurrentItem(userPlotModel.getPageId());

    }
/*
    private void  reqDataFromServer(){
         API_getVariable(userPlotModel.getPrdID(),userPlotModel.getFisheryType());
        for(STDVarModel tmp:stdVarModelList){
           Log.d("Test "," String : "+tmp.toString());
        }
    }
*/
    private void  setTabStyle(PBCalculatePagerAdapter adapter){
        Typeface typeface = Typeface.createFromAsset(PBProductDetailActivity.this.getAssets(), "fonts/Quark-Bold.otf");

        tabLayout.removeAllTabs();
        ViewGroup slidingTabStrip = (ViewGroup) tabLayout.getChildAt(0);
        for (int i = 0, count = adapter.getCount(); i < count; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            tabLayout.addTab(tab.setText(adapter.getPageTitle(i)));
            AppCompatTextView view = (AppCompatTextView) ((ViewGroup)slidingTabStrip.getChildAt(i)).getChildAt(1);
            view.setTypeface(typeface, Typeface.NORMAL);
            //view.setBackgroundResource(R.color.RcmoLightTranBG);

        }
    }

    private void  setUI(int groupId){

        ((TextView)findViewById(R.id.titleLable)).setText(userPlotModel.getPrdValue());

        if(groupId == 1){

            findViewById(R.id.headerLayout).setBackgroundColor(ContextCompat.getColor(PBProductDetailActivity.this, R.color.RcmoPlantBG));
            tabLayout =  (TabLayout) findViewById(R.id.tab_layout_plant);
            findViewById(R.id.tab_layout_animal).setVisibility(View.GONE);
            findViewById(R.id.tab_layout_fish).setVisibility(View.GONE);

        }else if(groupId == 2){
            findViewById(R.id.headerLayout).setBackgroundColor(ContextCompat.getColor(PBProductDetailActivity.this, R.color.RcmoAnimalBG));
            tabLayout =  (TabLayout) findViewById(R.id.tab_layout_animal);
            findViewById(R.id.tab_layout_plant).setVisibility(View.GONE);
            findViewById(R.id.tab_layout_fish).setVisibility(View.GONE);
        }else{
            findViewById(R.id.headerLayout).setBackgroundColor(ContextCompat.getColor(PBProductDetailActivity.this, R.color.RcmoFishBG));
            tabLayout =  (TabLayout)findViewById(R.id.tab_layout_fish);
            findViewById(R.id.tab_layout_plant).setVisibility(View.GONE);
            findViewById(R.id.tab_layout_animal).setVisibility(View.GONE);

        }

    }
     private void setAction (){
         findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });
     }
/*
    private void API_getVariable(String prdID , final String fisheryType) {

        new ResponseAPI(PBProductDetailActivity.this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mGetVariable mVariable = (mGetVariable) obj;
                List<mGetVariable.mRespBody> mVariableBodyLists = mVariable.getRespBody();

                if (mVariableBodyLists.size() != 0) {

                    stdVarModelList =  ProductService.prepareSTDVarList(mVariableBodyLists.get(0), fisheryType);

                }


            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getVariable +
                "?PrdID=" + prdID +
                "&FisheryType=" + userPlotModel.getFisheryType());

    }
*/
}
