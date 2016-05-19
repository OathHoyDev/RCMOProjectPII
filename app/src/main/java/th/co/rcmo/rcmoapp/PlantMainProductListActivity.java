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

public class PlantMainProductListActivity extends AppCompatActivity {
    public static List<mMainProductList.mRespBody> productList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_product_list);

        TextView appbarTitleLabel =  (TextView)findViewById(R.id.mainAppBar_Title);
        appbarTitleLabel.setText("กลุ่มของพืช");

        TextView appbarBackLabel =  (TextView)findViewById(R.id.mainAppBar_Back);
        appbarBackLabel.setText(R.string.appbar_cancel_label);

        findViewById(R.id.mainAppBar_Back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        productList = new ArrayList<>();

        mMainProductList.mRespBody productInfo = new mMainProductList().new mRespBody(R.drawable.plant_icon,"ข้าว/พืชไร่");
        productList.add(productInfo);

        productInfo = new mMainProductList().new mRespBody(R.drawable.small_plan_icon,"พืชไร่มีอายุ");
        productList.add(productInfo);

        productInfo = new mMainProductList().new mRespBody(R.drawable.tree_icon,"ไม้ผล/ไม้ยืนต้น");
        productList.add(productInfo);

        productInfo = new mMainProductList().new mRespBody(R.drawable.vegetable_icon,"พืชผัก");
        productList.add(productInfo);

        productInfo = new mMainProductList().new mRespBody(R.drawable.flower_icon,"ไม้ดอก/ไม้ประดับ");
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
