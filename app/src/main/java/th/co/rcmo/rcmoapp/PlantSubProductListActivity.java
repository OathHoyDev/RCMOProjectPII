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
import th.co.rcmo.rcmoapp.Adapter.PlantSubProductAdapter;
import th.co.rcmo.rcmoapp.Module.mMainProductList;
import th.co.rcmo.rcmoapp.Module.mSubProductList;

public class PlantSubProductListActivity extends AppCompatActivity {
    public static List<mSubProductList.mRespBody> subProductList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_product_list);

        TextView appbarTitleLabel =  (TextView)findViewById(R.id.mainAppBar_Title);
        appbarTitleLabel.setText(R.string.appbar_product_name_label);

        TextView subProductHeaderLeLabel =  (TextView)findViewById(R.id.sub_product_header);
        subProductHeaderLeLabel.setText("ข้าวนาปี");

        TextView appbarBackLabel =  (TextView)findViewById(R.id.mainAppBar_Back);
        appbarBackLabel.setText(R.string.appbar_cancel_label);

        findViewById(R.id.mainAppBar_Back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        subProductList = new ArrayList<>();

        mSubProductList.mRespBody subProductInfo = new mSubProductList().new mRespBody("ข้าวปทุมธานี");
        subProductList.add(subProductInfo);

        subProductInfo = new mSubProductList().new mRespBody("ข้าวเจ้า");
        subProductList.add(subProductInfo);

        subProductInfo = new mSubProductList().new mRespBody("ข้าวหอมมะลิ");
        subProductList.add(subProductInfo);

        subProductInfo = new mSubProductList().new mRespBody("ข้าวเหนียว");
        subProductList.add(subProductInfo);


        PlantSubProductAdapter adapter = new PlantSubProductAdapter(getApplicationContext(), subProductList);

        ListView listView = (ListView)findViewById(R.id.sub_product_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            }
        });
    }
}
