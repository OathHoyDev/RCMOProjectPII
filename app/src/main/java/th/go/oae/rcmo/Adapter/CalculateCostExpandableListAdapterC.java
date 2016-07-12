package th.go.oae.rcmo.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.neopixl.pixlui.components.edittext.EditText;
import com.neopixl.pixlui.components.textview.TextView;

import java.util.HashMap;
import java.util.List;

import th.go.oae.rcmo.Model.calculate.FormulaCModel;
import th.go.oae.rcmo.R;

/**
 * Created by SilVeriSm on 6/18/2016 AD.
 */
public class CalculateCostExpandableListAdapterC extends BaseExpandableListAdapter {

    String TAG = "CalculateCostExpandableListAdapter";

    private Context _context;
    // header titles
    private static List<String> _listDataHeader;
    // child data in format of header title, child title
    private static HashMap<String, List<String[]>> _listDataChild;

    FormulaCModel model;


    public CalculateCostExpandableListAdapterC(Context context, FormulaCModel dataObj) {
//        List<String> listDataHeader,
//
//                                              HashMap<String, List<String[]>> listChildData) {
        model = dataObj;

        this._context = context;
        this._listDataHeader = model.listDataHeader;
        this._listDataChild = model.listDataChild;
    }

    @Override
    public String[] getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String[] childText = (String[]) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.calculate_cost_list_item, null);

            if (isLastChild) {
                convertView.setBackgroundResource(R.drawable.light_gray_cut_bottom_conner);
            }
        }


        TextView txCostName = (TextView) convertView
                .findViewById(R.id.txCostName);
        txCostName.setText(childText[1]);

        EditText txCostValue = (EditText) convertView
                .findViewById(R.id.txCostValue);
        txCostValue.setText(String.valueOf(model.getValueFromAttributeName(model , childText[4])));


        txCostValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    model.setValueFromAttributeName(model, childText[4], s.toString());
                } catch (Exception e) {
                    Log.e("Error", "Error addTextChangedListener" + e.getMessage());
                }
            }
        });

        if ("true".equalsIgnoreCase(childText[0])) {
            txCostValue.setBackgroundColor(Color.WHITE);
            txCostValue.setLines(1);
            txCostValue.setEnabled(true);
        } else {
            txCostValue.setBackgroundColor(Color.TRANSPARENT);
            txCostValue.setEnabled(false);
        }

        TextView txCostUnit = (TextView) convertView
                .findViewById(R.id.txCostUnit);
        txCostUnit.setText(childText[3]);

        String layoutId = "formula_j_" + childText[4];

        int id = _context.getResources().getIdentifier(layoutId, "id", _context.getPackageName());

        convertView.setId(id);

        Log.d(TAG, "getChildView: Set ID : " + layoutId + " -> " + id);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.calculate_cost_list_group, null);

        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setText(headerTitle);
        lblListHeader.setBackgroundResource(R.drawable.green_cut_top_conner);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public FormulaCModel getDataObj() {
        return model;
    }

}
