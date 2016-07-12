package th.go.oae.rcmo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;
import th.go.oae.rcmo.Module.mProvince;
import th.go.oae.rcmo.R;

/**
 * Created by Taweesin on 6/15/2016.
 */
public class DialogProvinceAdapter extends BaseAdapter {
    private List<mProvince.mRespBody> listProvinceData;

    private LayoutInflater layoutInflater;

    public DialogProvinceAdapter(Context context, List<mProvince.mRespBody> listData) {

        this.listProvinceData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listProvinceData.size();
    }

    @Override
    public Object getItem(int position) {
        return listProvinceData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_location, null);
            holder = new ViewHolder();
            holder.provinceName = (TextView) convertView.findViewById(R.id.name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.provinceName.setText(listProvinceData.get(position).getProvNameTH().toString());

        return convertView;
    }

    static class ViewHolder {
        TextView provinceName;
    }
}
