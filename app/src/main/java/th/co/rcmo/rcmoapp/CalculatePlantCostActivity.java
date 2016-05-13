package th.co.rcmo.rcmoapp;

import android.app.Activity;

import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;

public class CalculatePlantCostActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_calculate_plant_cost);


        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

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

    }
}
