package th.co.rcmo.rcmoapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import th.co.rcmo.rcmoapp.Module.mAmphoe;
import th.co.rcmo.rcmoapp.Module.mProvince;
import th.co.rcmo.rcmoapp.R;

/**
 * Created by Taweesin on 18/5/2559.
 */
public class AmphoeAdapter extends BaseAdapter {

    Context mContext;
    List<mAmphoe.mRespBody> amphoeList;

    public AmphoeAdapter(Context mContext, List<mAmphoe.mRespBody> amphoeList) {
        this.mContext = mContext;
        this.amphoeList = amphoeList;
    }

    @Override
    public int getCount() {
        return amphoeList.size();
    }

    @Override
    public mAmphoe.mRespBody getItem(int position) {
        return amphoeList.get(position);
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
            convertView = mInflater.inflate(R.layout.amphoe_listview_row, parent, false);
        }

        mAmphoe.mRespBody amphoeInfo = getItem(position);

        TextView textView = (TextView)convertView.findViewById(R.id.amphoe_name_text);
        textView.setText(amphoeInfo.getAmphoeName());

        ImageView imageView = (ImageView)convertView.findViewById(R.id.star_select_img);
        imageView.setBackgroundResource(amphoeInfo.getAmphoeImg());

        return convertView;
    }


}
