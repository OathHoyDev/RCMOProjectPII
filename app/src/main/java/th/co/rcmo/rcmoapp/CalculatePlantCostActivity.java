package th.co.rcmo.rcmoapp;

import android.app.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class CalculatePlantCostActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_calculate_plant_cost);



        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        /*

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.plantLayout);
        spec.setIndicator("พืช");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.livestockLayout);
        spec.setIndicator("Tab Two");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.fishingLayout);
        spec.setIndicator("Tab Three");
        host.addTab(spec);

*/


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
