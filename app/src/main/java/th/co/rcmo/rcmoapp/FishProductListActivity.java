package th.co.rcmo.rcmoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import th.co.rcmo.rcmoapp.Adapter.PlantMainProductAdapter;
import th.co.rcmo.rcmoapp.Module.mMainProductList;

public class FishProductListActivity extends AppCompatActivity {
    public static List<mMainProductList.mRespBody> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_product_list);

        TextView appbarTitleLabel =  (TextView)findViewById(R.id.mainAppBar_Title);
        appbarTitleLabel.setText(R.string.appbar_product_name_label);

        TextView appbarBackLabel =  (TextView)findViewById(R.id.mainAppBar_Back);
        appbarBackLabel.setText(R.string.appbar_cancel_label);


        findViewById(R.id.mainAppBar_Back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        productList = new ArrayList<>();

        mMainProductList.mRespBody productInfo = new mMainProductList().new mRespBody(R.drawable.fish_icon,"ปลาดุก");
        productList.add(productInfo);

        productInfo = new mMainProductList().new mRespBody(R.drawable.fish_icon,"ปลาทับทิม");
        productList.add(productInfo);

        productInfo = new mMainProductList().new mRespBody(R.drawable.shrimp_icon,"กุ้งขาวแวนนาไม");
        productList.add(productInfo);

        productInfo = new mMainProductList().new mRespBody(R.drawable.fish_icon,"ปลานิล");
        productList.add(productInfo);

        productInfo = new mMainProductList().new mRespBody(R.drawable.fish_icon,"ปลาช่อน");
        productList.add(productInfo);



        PlantMainProductAdapter adapter = new PlantMainProductAdapter(getApplicationContext(), productList);

        ListView listView = (ListView)findViewById(R.id.product_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            }
        });
    }
}
