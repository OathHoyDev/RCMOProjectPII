package th.go.oae.rcmo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import th.go.oae.rcmo.Adapter.CompareProductDetailAdapter;
import th.go.oae.rcmo.Adapter.CompareProductHeaderAdapter;

public class CompareProductActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_product);

        List<String> headerList = new ArrayList<>();
        headerList.add("ต้นทุน ต่อไร่");
        headerList.add("ต้นทุน ต่อหน่วย");
        headerList.add("ผลตอบแทน ต่อไร่");
        headerList.add("ผลตอบแทน ต่อหน่วย");
        ListView rowHeaderRowList = (ListView) findViewById(R.id.list_label);
        rowHeaderRowList.setAdapter(new CompareProductHeaderAdapter(CompareProductActivity.this,headerList));


        List<String> detailList = new ArrayList<>();
        detailList.add("78,000.00");
        detailList.add("95,000.00");
        detailList.add("10,000,000.00");
        detailList.add("-");

        ListView list_prod1 = (ListView) findViewById(R.id.list_prod1);
        list_prod1.setAdapter(new CompareProductDetailAdapter(CompareProductActivity.this,detailList));

        ListView list_prod2 = (ListView) findViewById(R.id.list_prod2);
        list_prod2.setAdapter(new CompareProductDetailAdapter(CompareProductActivity.this,detailList));

        ListView list_prod3 = (ListView) findViewById(R.id.list_prod3);
        list_prod3.setAdapter(new CompareProductDetailAdapter(CompareProductActivity.this,detailList));

        ListView list_prod4 = (ListView) findViewById(R.id.list_prod4);
        list_prod4.setAdapter(new CompareProductDetailAdapter(CompareProductActivity.this,detailList));

        setAction();

    }

    private void setAction(){
       final LinearLayout prod1_selected = (LinearLayout) findViewById(R.id.prod1_selected);
        findViewById(R.id.c_column_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prod1_selected.getVisibility() == View.GONE){
                    prod1_selected.setVisibility(View.VISIBLE);
                }else{
                    prod1_selected.setVisibility(View.GONE);
                }

            }
        });

    }
}
