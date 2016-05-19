package th.co.rcmo.rcmoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import th.co.rcmo.rcmoapp.API.StubApi;
import th.co.rcmo.rcmoapp.Adapter.ProvinceAdapter;
import th.co.rcmo.rcmoapp.Module.mProvince;

public class ProvinceListActivity extends AppCompatActivity {
    public static List<mProvince.mRespBody> provinceList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province_list);

        TextView appbarTitleLabel =  (TextView)findViewById(R.id.mainAppBar_Title);
        appbarTitleLabel.setText(R.string.select_province_label);

        TextView appbarBackLabel =  (TextView)findViewById(R.id.mainAppBar_Back);
        appbarBackLabel.setText(R.string.appbar_cancel_label);

        findViewById(R.id.mainAppBar_Back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        ProvinceAdapter adapter = new ProvinceAdapter(getApplicationContext(), StubApi.getProvinceList(provinceList));
        ListView listView = (ListView)findViewById(R.id.province_list_view);
        listView.setAdapter(adapter);

        adapter = new ProvinceAdapter(getApplicationContext(), StubApi.getFavoriteProvinceList(provinceList));
        ListView listFavoriteView = (ListView)findViewById(R.id.favorites_province_list_view);
        listFavoriteView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            }
        });


    }
}
