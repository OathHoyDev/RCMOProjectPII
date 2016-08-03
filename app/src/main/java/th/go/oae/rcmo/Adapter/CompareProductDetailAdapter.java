package th.go.oae.rcmo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.neopixl.pixlui.components.textview.TextView;

import java.util.ArrayList;
import java.util.List;

import th.go.oae.rcmo.R;

/**
 * Created by Taweesin on 8/2/2016.
 */
public class CompareProductDetailAdapter extends BaseAdapter {
    List<String> rowDetailList = new ArrayList<String>();
    Context context;

    public CompareProductDetailAdapter(Context context, List<String> rowDetailList) {
        this.context = context;
        this.rowDetailList = rowDetailList;
    }

    @Override
    public int getCount() {
        return rowDetailList.size();
    }

    @Override
    public String getItem(int position) {
        return rowDetailList.get(position);
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
            convertView = inflater.inflate(R.layout.compare_value_list_view, null);
            h.label_compare_value =  (TextView) convertView.findViewById(R.id.label_compare_value);
            h.layout_compare_value =  (LinearLayout) convertView.findViewById(R.id.layout_compare_value);
            convertView.setTag(h);
        }else{
            h = (ViewHolder) convertView.getTag();
        }

        String label = getItem(position);

        h.label_compare_value.setText(label);

        if(((position+1)%2)==0){
            h.layout_compare_value.setBackgroundResource(R.color.RcmoWhiteBG);

        }else{
            h.layout_compare_value.setBackgroundResource(R.color.PIIRcmoResultRowBG);
        }

        return convertView;
    }

    static class ViewHolder {
        private TextView label_compare_value;
        private LinearLayout layout_compare_value;
    }
}
