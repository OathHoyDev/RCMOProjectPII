package th.go.oae.rcmo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;
import th.go.oae.rcmo.Module.mTumbon;
import th.go.oae.rcmo.R;

/**
 * Created by Taweesin on 6/15/2016.
 */
public class DialogTumbonAdapter extends BaseAdapter{
    private List<mTumbon.mRespBody> listTumbonData;

    private LayoutInflater layoutInflater;

    public DialogTumbonAdapter(Context context, List<mTumbon.mRespBody> listData) {

        this.listTumbonData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listTumbonData.size();
    }

    @Override
    public Object getItem(int position) {
        return listTumbonData.get(position);
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
            holder.tumbonName = (TextView) convertView.findViewById(R.id.name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tumbonName.setText(listTumbonData.get(position).getTamNameTH().toString());

        return convertView;
    }

    static class ViewHolder {
        TextView tumbonName;
    }
}
