package th.co.rcmo.rcmoapp.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.neopixl.pixlui.components.textview.TextView;

import java.util.ArrayList;
import java.util.List;

import th.co.rcmo.rcmoapp.Model.STDVarModel;
import th.co.rcmo.rcmoapp.R;
import th.co.rcmo.rcmoapp.Util.CalculateConstant;

/**
 * Created by Taweesin on 6/25/2016.
 */
public class ProdDetailStandardAdapter extends BaseAdapter {
    List<STDVarModel> stdVarModelList = new ArrayList<STDVarModel>();
    int productType = 0;
    Context context;

    public ProdDetailStandardAdapter(Context context, List<STDVarModel> stdVarModelList, int productType){
        this.stdVarModelList =stdVarModelList;
        this.productType = productType;
        this.context = context;
    }
    @Override
    public int getCount() {
        return stdVarModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return stdVarModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder h = new ViewHolder();

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.product_detail_standard_list_layout, null);
            h.txStandardName =  (TextView) convertView.findViewById(R.id.txStandardName);
            h.txStandardUnit =  (TextView) convertView.findViewById(R.id.txStandardUnit);
            h.txStandardValue =  (TextView) convertView.findViewById(R.id.txStandardValue);
            h.bgColor          = (ImageView) convertView.findViewById(R.id.bgColor);
            convertView.setTag(h);
        }else{
            h = (ViewHolder) convertView.getTag();
        }

        switch (productType){
            case 1:
                h.bgColor.setBackgroundResource(R.drawable.plant_selected_cut_conner);
                h.txStandardValue.setTextColor(ContextCompat.getColor(convertView.getContext() , R.color.RcmoPlantBG));
                break;
            case 2:
                h.bgColor.setBackgroundResource(R.drawable.animal_selected_cut_conner);
                h.txStandardValue.setTextColor(ContextCompat.getColor(convertView.getContext() , R.color.RcmoAnimalBG));
                break;
            case 3:
                h.bgColor.setBackgroundResource(R.drawable.fish_selected_cut_conner);
                h.txStandardValue.setTextColor(ContextCompat.getColor(convertView.getContext() , R.color.RcmoFishBG));
                break;
        }

        STDVarModel tempVar = (STDVarModel) getItem(position);


        h.txStandardName.setText(tempVar.name);
        h.txStandardValue.setText(tempVar.value);
        h.txStandardUnit.setText(tempVar.unit);

        return convertView;
    }

    static class ViewHolder {
        private TextView txStandardName,txStandardUnit,txStandardValue;
        private ImageView bgColor;



    }
}
