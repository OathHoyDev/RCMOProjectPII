package th.co.rcmo.rcmoapp;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import th.co.rcmo.rcmoapp.Model.ProductDetailModel;
import th.co.rcmo.rcmoapp.Util.CalculateConstant;

public class ProductDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ProductDetailModel productDetailModel;
    private String productType;
    private Bundle bundle;

    public ProductDetailModel getProductDetailModel() {
        return productDetailModel;
    }

    public void setProductDetailModel(ProductDetailModel productDetailModel) {
        this.productDetailModel = productDetailModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // For Test
        testDataMethod();

        bundle = new Bundle();
        bundle.putString("productType", productDetailModel.productType);
        bundle.putString("prodID", productDetailModel.prodID);
        bundle.putString("suitFlag" , productDetailModel.suitFlag);
        bundle.putString("tamCode" , productDetailModel.tamCode);
        bundle.putString("ampCode" , productDetailModel.ampCode);
        bundle.putString("provCode" , productDetailModel.provCode);
        bundle.putString("lat" , "14.200792");
        bundle.putString("lon" , "100.509152");

        initialTab();



    }

    private void testDataMethod(){

        productDetailModel = new ProductDetailModel();

        productDetailModel.prodID = "41";
        productDetailModel.productType = "1";
        productDetailModel.suitFlag = "2";
        productDetailModel.tamCode = "";
        productDetailModel.ampCode = "";
        productDetailModel.provCode = "";


    }

    private void initialTab(){



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        switch (productDetailModel.productType){
            case CalculateConstant.PRODUCT_TYPE_PLANT:
                toolbar.setBackgroundResource(R.color.RcmoPlantBG);
                break;
            case CalculateConstant.PRODUCT_TYPE_ANIMAL:
                toolbar.setBackgroundResource(R.color.RcmoAnimalBG);
                break;
            case CalculateConstant.PRODUCT_TYPE_FISH:
                toolbar.setBackgroundResource(R.color.RcmoFishBG);
                break;
        }

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        viewPager = (ViewPager) findViewById(R.id.viewpager);
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);

        ProductDetailStandardFragment productDetailStandardFragment = new ProductDetailStandardFragment();
        productDetailStandardFragment.setArguments(bundle);
        adapter.addFragment(productDetailStandardFragment);

        ProductDetailCalculateFragment productDetailCalculateFragment = new ProductDetailCalculateFragment();
        productDetailCalculateFragment.setArguments(bundle);
        adapter.addFragment(productDetailCalculateFragment);

        ProductDetailMapFragment productDetailMapFragment = new ProductDetailMapFragment();
        productDetailMapFragment.setArguments(bundle);
        adapter.addFragment(productDetailMapFragment);

        viewPager.setAdapter(adapter);
//        viewPager.setCurrentItem(0);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.addTab(tabLayout.newTab().setText("aaa"));
        tabLayout.addTab(tabLayout.newTab().setText("bbb"));
        tabLayout.addTab(tabLayout.newTab().setText("ccc"));

        //tabLayout.setBackgroundResource(android.R.color.transparent);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        Log.i("Tab", "onTabSelected: " + tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        Log.i("Tab", "onTabUnselected: " + tab.getPosition());
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);

                        Log.i("Tab", "onTabReselected: " + tab.getPosition());
                    }

                }
        );

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        Context context;

        public ViewPagerAdapter(FragmentManager manager, Context context) {

            super(manager);
            this.context = context;
        }

        public View getTabView(int position) {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            View v = LayoutInflater.from(context).inflate(R.layout.custom_product_detail_tab, null);
            TextView txTab = (TextView) v.findViewById(R.id.txTab);
            ImageView tabImage = (ImageView) v.findViewById(R.id.tabImage);
            //tabImage.setBackgroundResource(R.drawable.bg_tab_green_dark);

            switch (productDetailModel.productType){
                case CalculateConstant.PRODUCT_TYPE_PLANT:
                    tabImage.setBackgroundResource(R.drawable.bg_tab_green_dark);
                    break;
                case CalculateConstant.PRODUCT_TYPE_ANIMAL:
                    tabImage.setBackgroundResource(R.drawable.bg_tab_pink_dark);
                    break;
                case CalculateConstant.PRODUCT_TYPE_FISH:
                    tabImage.setBackgroundResource(R.drawable.bg_tab_blue_dark);
                    break;
            }


            switch (position) {

                case 0:
                    txTab.setText("ค่ามาตรฐาน");
                    break;
                case 1:
                    txTab.setText("คำนวนต้นทุน");
                    break;
                case 2:
                    txTab.setText("แผนที่");
                    break;
                default:
                    break;
            }


            return v;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);

        }
    }
}
