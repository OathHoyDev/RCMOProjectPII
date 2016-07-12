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
import android.widget.ImageView;
import android.widget.ListView;
import com.neopixl.pixlui.components.edittext.EditText;
import com.neopixl.pixlui.components.textview.TextView;


import java.util.ArrayList;
import java.util.List;

import th.go.oae.rcmo.Module.mProvince;
import th.go.oae.rcmo.R;


public class dialog_province {

    Context context;
    List<mProvince.mRespBody> provinces = new ArrayList<>();
    List<mProvince.mRespBody> new_provinces = new ArrayList<>();
    String strtitle;
    EditText input_search;
    ListView listview;
    TextView title;
    ImageView close;
    android.app.Dialog dialog;

    OnSelectChoice onSelectChoice;

    public dialog_province(Context context, String strtitle, List<mProvince.mRespBody> provinces, OnSelectChoice onSelectChoice){

        this.context =context;
        this.provinces =provinces;
        this.new_provinces = provinces;
        this.strtitle =strtitle;
        this.onSelectChoice =onSelectChoice;

        Popup_Province(provinces);
    }

    public interface  OnSelectChoice{
        void selectChoice(mProvince.mRespBody choice);
    }


    public void Popup_Province(final List<mProvince.mRespBody> provinces){
        dialog = new android.app.Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_location);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        title =(TextView) dialog.findViewById(R.id.title);
        listview =(ListView) dialog.findViewById(R.id.listview);
        //my_location =(ImageView) dialog.findViewById(R.id.my_location);
        close =(ImageView) dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        input_search = (EditText)dialog.findViewById(R.id.input_search);

       // my_location.setVisibility(View.VISIBLE);
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

        listview.setAdapter(new listAdapter(provinces));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onSelectChoice.selectChoice(new_provinces.get(position));
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
/*
        my_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetMyLocation();
            }
        });

*/



        dialog.show();
    }

    /*
    private void GetMyLocation() {

        GPSTracker gps = new GPSTracker(context);
        new AjaxResponseAPI(context, new AjaxResponseAPI.OnCallbackAPIListener() {
            @Override
            public void callbackSuccess(JSONObject obj) {
                Type listType = new TypeToken<BaseResponse<List<mProvince>>>() {
                }.getType();
                BaseResponse<List<mProvince>> BProvinces = new Gson().fromJson(obj.toString(), listType);
               try{
                   onSelectChoice.selectChoice(BProvinces.getRespBody().get(0));
                   dialog.dismiss();
               }catch (Exception e){}

            }

            @Override
            public void callbackError(int code, String errorMsg) {
            }
        }).POST(confix.getProvince, ParameterRequestAPI.PgetProvince("",gps.getLatitude()+"",gps.getLongitude()+""), true, true);
    }

*/
    void Search(){

        new_provinces = new ArrayList<>();
        String key = input_search.getText().toString();

        if(key.length()!=0){
            for(mProvince.mRespBody province : provinces){
                if(province.getProvNameTH().contains(key)){
                    new_provinces.add(province);
                }
            }
        }else{
            new_provinces = provinces;
        }
        input_search.setText("");
        listview.removeAllViewsInLayout();
        listview.setAdapter(new listAdapter(new_provinces));

    }



    class listAdapter extends BaseAdapter {

        List<mProvince.mRespBody> provinces;
        listAdapter(List<mProvince.mRespBody> province) {
            listAdapter.this.provinces =province;
        }

        @Override
        public int getCount() {
            return provinces.size();
        }

        @Override
        public mProvince.mRespBody getItem(int position) {
            return provinces.get(position);
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
            choice.setText(getItem(position).getProvNameTH());

            return (row);
        }
    }
}
