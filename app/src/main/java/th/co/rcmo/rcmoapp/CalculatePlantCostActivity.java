package th.co.rcmo.rcmoapp;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

public class CalculatePlantCostActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_calculate_plant_cost);
        TextView appbarTitleLabel =  (TextView)findViewById(R.id.mainAppBar_Title);
        appbarTitleLabel.setText(R.string.land_size);


        findViewById(R.id.mainAppBar_Back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();
        
        this.setNewTab( host, "tab1","พืช",  R.id.plantLayout);
        this.setNewTab( host, "tab2","ปศุสัตว์", R.id.livestockLayout);
        this.setNewTab( host, "tab3","ประมง", R.id.fishingLayout);
    }

    private void setNewTab( TabHost tabHost, String tag, String title, int contentID ){

        TabHost.TabSpec tabSpec = tabHost.newTabSpec(tag);
        tabSpec.setIndicator(getTabIndicator(tabHost.getContext(), title)); // new function to inject our own tab layout
        tabSpec.setContent(contentID);
        tabHost.addTab(tabSpec);
    }

    private View getTabIndicator(Context context,  String title) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);

        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setText(title);
        return view;
    }

    public void actionToPlantMainProductListActivity(View view)
    {
        Intent intent = new Intent(CalculatePlantCostActivity.this, PlantMainProductListActivity.class);
        startActivity(intent);
    }

    public void actionToFishProductListActivity(View view)
    {
        Intent intent = new Intent(CalculatePlantCostActivity.this, FishProductListActivity.class);
        startActivity(intent);
    }

    public void actionToLivestockProductListActivity(View view)
    {
        Intent intent = new Intent(CalculatePlantCostActivity.this, LivestockProductListActivity.class);
        startActivity(intent);
    }

    public void actionToPlantSubProductListActivity(View view)
    {
        Intent intent = new Intent(CalculatePlantCostActivity.this, PlantSubProductListActivity.class);
        startActivity(intent);
    }

    public void actionToProvinceListActivity(View view)
    {
        Intent intent = new Intent(CalculatePlantCostActivity.this, ProvinceListActivity.class);
        startActivity(intent);
    }

    public void actionToAmphoeListActivity(View view)
    {
        Intent intent = new Intent(CalculatePlantCostActivity.this, AmphoeListActivity.class);
        startActivity(intent);
    }
  /*
    private void setNewTab(Context context, TabHost tabHost, String tag, int title, int icon, int contentID ){
        String a = "1231";
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(tag);
        tabSpec.setIndicator(getTabIndicator(tabHost.getContext(), title, icon)); // new function to inject our own tab layout
        tabSpec.setContent(contentID);
        tabHost.addTab(tabSpec);
    }

       private View getTabIndicator(Context context, int title, int icon) {
           View view = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);
           ImageView iv = (ImageView) view.findViewById(R.id.imageView);
           iv.setImageResource(icon);

        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setText("ทดสอบ");
        return view;
    }
    */
}
