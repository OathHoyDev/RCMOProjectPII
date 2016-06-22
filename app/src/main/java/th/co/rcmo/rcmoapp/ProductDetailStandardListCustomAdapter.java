package th.co.rcmo.rcmoapp;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import th.co.rcmo.rcmoapp.Util.CalculateConstant;

/**
 * Created by SilVeriSm on 6/18/2016 AD.
 */
public class ProductDetailStandardListCustomAdapter extends BaseAdapter {

    private static Context context;
    private Activity activity;
    private static LayoutInflater inflater=null;
    public Resources res;
    String[] stdName = {};
    String[] stdValue = {};
    String[] stdUnit = {};

    String productType = "";

    public ProductDetailStandardListCustomAdapter(Context context , String productType , String[] stdName, String[] stdValue , String[] stdUnit) {
        // TODO Auto-generated constructor stub
        this.context = context;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.stdName = stdName;
        this.stdValue = stdValue;
        this.stdUnit = stdUnit;

        this.productType = productType;
    }

    @Override
    public int getCount() {
        if (stdName == null)
            return 0;
        else
            return stdName.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ListItem
    {
        TextView txStdName;
        TextView txStdValue;
        TextView txStdUnit;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListItem item = new ListItem();

        View rowView;
        rowView = inflater.inflate(R.layout.product_detail_standard_list_layout, null);

        ImageView bgView = (ImageView)rowView.findViewById(R.id.bgColor);
        com.neopixl.pixlui.components.textview.TextView txView = (com.neopixl.pixlui.components.textview.TextView)rowView.findViewById(R.id.txStandardValue);

        switch (productType){
            case CalculateConstant.PRODUCT_TYPE_PLANT:
                bgView.setBackgroundResource(R.drawable.plant_selected_cut_conner);
                txView.setTextColor(ContextCompat.getColor(context , R.color.RcmoPlantBG));
                break;
            case CalculateConstant.PRODUCT_TYPE_ANIMAL:
                bgView.setBackgroundResource(R.drawable.animal_selected_cut_conner);
                txView.setTextColor(ContextCompat.getColor(context , R.color.RcmoAnimalBG));
                break;
            case CalculateConstant.PRODUCT_TYPE_FISH:
                bgView.setBackgroundResource(R.drawable.fish_selected_cut_conner);
                txView.setTextColor(ContextCompat.getColor(context , R.color.RcmoFishBG));
                break;
        }

        item.txStdName = (TextView) rowView.findViewById(R.id.txStandardName);
        item.txStdValue = (TextView) rowView.findViewById(R.id.txStandardValue);
        item.txStdUnit = (TextView) rowView.findViewById(R.id.txStandardUnit);

        item.txStdName.setText(stdName[position]);
        item.txStdValue.setText(stdValue[position]);
        item.txStdUnit.setText(stdUnit[position]);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }


}
