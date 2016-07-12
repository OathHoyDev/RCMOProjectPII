package th.go.oae.rcmo.View;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.neopixl.pixlui.components.edittext.EditText;
import com.neopixl.pixlui.components.textview.TextView;
import java.util.ArrayList;
import java.util.List;

import th.go.oae.rcmo.Module.mAmphoe;
import th.go.oae.rcmo.R;


public class dialog_amphoe {

    Context context;
    List<mAmphoe.mRespBody> items = new ArrayList<>();
    String strtitle;
    EditText input_search;
    ListView listview;
    TextView title;

    OnSelectChoice onSelectChoice;
    android.app.Dialog dialog;
    List<mAmphoe.mRespBody> new_items = new ArrayList<>();
    
    public dialog_amphoe(Context context, String strtitle, List<mAmphoe.mRespBody> items, OnSelectChoice onSelectChoice){
        
        this.context =context;
        this.items =items;
        this.new_items = items;
        this.strtitle =strtitle;
        this.onSelectChoice =onSelectChoice;
        
        Popup_item(items);
    }

    public interface  OnSelectChoice{
        void selectChoice(mAmphoe.mRespBody choice);
    }


    public void Popup_item(final List<mAmphoe.mRespBody> items){
        dialog = new android.app.Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_location);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        title =(TextView) dialog.findViewById(R.id.title);
        listview =(ListView) dialog.findViewById(R.id.listview);
        input_search = (EditText)dialog.findViewById(R.id.input_search);

        title.setText(strtitle);
        input_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(android.widget.TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Search();
                    return true;
                }
                return false;
            }

        });

        listview.setAdapter(new listAdapter(items));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onSelectChoice.selectChoice(new_items.get(position));
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        
        dialog.show();
    }
    
    
    void Search(){

        new_items = new ArrayList<>();
        String key = input_search.getText().toString();

        if(key.length()!=0){
            for(mAmphoe.mRespBody item : items){
                if(item.getAmpNameTH().contains(key)){
                    new_items.add(item);
                }
            }
        }else{
            new_items = items;
        }

        input_search.setText("");
        listview.removeAllViewsInLayout();
        listview.setAdapter(new listAdapter(new_items));

    }



    class listAdapter extends BaseAdapter {

        List<mAmphoe.mRespBody> items;
        listAdapter(List<mAmphoe.mRespBody> item) {
            listAdapter.this.items =item;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public mAmphoe.mRespBody getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row;
            row = inflater.inflate(R.layout.textview, parent, false);
            TextView choice        = (TextView)row.findViewById(R.id.choice);
            choice.setText(getItem(position).getAmpNameTH());
            
            return (row);
        }
    }
}
