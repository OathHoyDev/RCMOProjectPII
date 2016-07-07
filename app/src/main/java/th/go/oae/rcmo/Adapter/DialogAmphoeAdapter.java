package th.go.oae.rcmo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;
import th.go.oae.rcmo.Module.mAmphoe;
import th.go.oae.rcmo.R;

/**
 * Created by Taweesin on 6/15/2016.
 */
public class DialogAmphoeAdapter extends BaseAdapter{
    private List<mAmphoe.mRespBody> listAmphoeData;

    private LayoutInflater layoutInflater;

    public DialogAmphoeAdapter(Context context, List<mAmphoe.mRespBody> listData) {

        this.listAmphoeData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listAmphoeData.size();
    }

    @Override
    public Object getItem(int position) {
        return listAmphoeData.get(position);
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
            holder.amphoeName = (TextView) convertView.findViewById(R.id.name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.amphoeName.setText(listAmphoeData.get(position).getAmpNameTH().toString());

        return convertView;
    }

    static class ViewHolder {
        TextView amphoeName;
    }
}
