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
import th.co.rcmo.rcmoapp.Adapter.AmphoeAdapter;
import th.co.rcmo.rcmoapp.Adapter.ProvinceAdapter;
import th.co.rcmo.rcmoapp.Module.mAmphoe;


public class AmphoeListActivity extends AppCompatActivity {
    public static List<mAmphoe.mRespBody> amphoeList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amphoe_list);

        TextView appbarTitleLabel =  (TextView)findViewById(R.id.mainAppBar_Title);
        appbarTitleLabel.setText(R.string.select_amphoe_label);

        TextView appbarBackLabel =  (TextView)findViewById(R.id.mainAppBar_Back);
        appbarBackLabel.setText(R.string.appbar_cancel_label);

        findViewById(R.id.mainAppBar_Back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        AmphoeAdapter adapter = new AmphoeAdapter(getApplicationContext(), StubApi.getAmphoeList(amphoeList));
        ListView listView = (ListView)findViewById(R.id.amphoe_list_view);
        listView.setAdapter(adapter);

        adapter = new AmphoeAdapter(getApplicationContext(), StubApi.getFavoriteAmphoeList(amphoeList));
        ListView listFavoriteView = (ListView)findViewById(R.id.favorites_amphoe_list_view);
        listFavoriteView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            }
        });


    }



}
