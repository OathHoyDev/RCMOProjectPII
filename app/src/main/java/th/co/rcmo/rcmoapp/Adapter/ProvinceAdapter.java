package th.co.rcmo.rcmoapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import th.co.rcmo.rcmoapp.Module.mProvince;
import th.co.rcmo.rcmoapp.R;

/**
 * Created by Taweesin on 18/5/2559.
 */
public class ProvinceAdapter extends BaseAdapter {
    Context mContext;
    List<mProvince.mRespBody> provinceList;

    public ProvinceAdapter(Context mContext, List<mProvince.mRespBody> provinceList) {
        this.mContext = mContext;
        this.provinceList = provinceList;
    }

    @Override
    public int getCount() {
        return provinceList.size();
    }

    @Override
    public mProvince.mRespBody getItem(int position) {
        return provinceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater mInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.province_listview_row, parent, false);
        }

        mProvince.mRespBody provinceInfo = getItem(position);

        TextView textView = (TextView)convertView.findViewById(R.id.province_name_text);
        textView.setText(provinceInfo.getProvinceName());

        ImageView imageView = (ImageView)convertView.findViewById(R.id.star_select_img);
        imageView.setBackgroundResource(provinceInfo.getProvinceImg());

        return convertView;
    }
}
