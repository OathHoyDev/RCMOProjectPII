package th.co.rcmo.rcmoapp;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.util.List;

import th.co.rcmo.rcmoapp.API.RequestServices;
import th.co.rcmo.rcmoapp.API.ResponseAPI;
import th.co.rcmo.rcmoapp.Module.mGetPlotDetail;
import th.co.rcmo.rcmoapp.Module.mGetPlotSuit;
import th.co.rcmo.rcmoapp.Module.mGetVariable;
import th.co.rcmo.rcmoapp.Util.CalculateConstant;
import th.co.rcmo.rcmoapp.Util.GPSTracker;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailMapFragment extends Fragment implements View.OnClickListener {

    MapView mapView;
    GoogleMap map;

    int mapType;

    GPSTracker gps;

    FrameLayout fadeView;

    private PopupWindow popupWindow;

    private String productType;

    private String suitFlag;
    private String prdID;
    private String tamCode;
    private String ampCode;
    private String provCode;
    private String plodID;
    double latitude;
    double longitude;

    String suggession;

    private Context context;
    View v;

    public ProductDetailMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_product_detail_map, container, false);

        v = inflater.inflate(R.layout.fragment_product_detail_map, container,
                false);
        context = v.getContext();

        getArgumentFromActivity();

        initialLayout(savedInstanceState);

        API_getPlotDetail(plodID);

        return v;
    }

    private void getArgumentFromActivity(){

        plodID = getArguments().getString("plodID");
        suitFlag = getArguments().getString("suitFlag");
        tamCode = getArguments().getString("tamCode");
        ampCode = getArguments().getString("ampCode");
        provCode = getArguments().getString("provCode");

        productType = getArguments().getString("productType");



    }

    private void initialLayout(Bundle savedInstanceState){

        RelativeLayout suggessBottomBG = (RelativeLayout)v.findViewById(R.id.suggessBottomBG);
        //
        switch (productType){
            case CalculateConstant.PRODUCT_TYPE_PLANT:
                suggessBottomBG.setBackgroundResource(R.color.RcmoPlantTranBG);
                break;
            case CalculateConstant.PRODUCT_TYPE_ANIMAL:
                suggessBottomBG.setBackgroundResource(R.color.RcmoAnimalTranBG);
                break;
            case CalculateConstant.PRODUCT_TYPE_FISH:
                suggessBottomBG.setBackgroundResource(R.color.RcmoFishTranBG);
                break;
        }

        fadeView = (FrameLayout)v.findViewById(R.id.fadeLayout);
        fadeView.setVisibility(View.GONE);

        mapView = (MapView) v.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setMyLocationEnabled(true);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        try {
            MapsInitializer.initialize(this.getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        gps = new GPSTracker(getContext());

        if(gps.canGetLocation() && "2".equalsIgnoreCase(suitFlag)){

//            double latitude = gps.getLatitude();
//            double longitude = gps.getLongitude();

            latitude = 13.7686;
            longitude = 99.8165;

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 17);
            map.animateCamera(cameraUpdate);

            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.pin);

            int newWidth = (int) (bm.getWidth()*0.4);
            int newHeight = (int) (bm.getHeight()*0.4);

            bm = Bitmap.createScaledBitmap(bm, newWidth , newHeight , false);

            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude));
            marker.icon(BitmapDescriptorFactory.fromBitmap(bm));

            map.addMarker(marker);

        }else{

        }


        // Add Button Action
        ImageButton buttonMapStyle = (ImageButton) v.findViewById(R.id.btnMapStyle);

        buttonMapStyle.setBackgroundResource(R.drawable.btn_sate);
        mapType = 1;
        map.setMapType(1);

        buttonMapStyle.setOnClickListener(this);

        ImageButton buttonCenterMarker = (ImageButton) v.findViewById(R.id.btnCenterMarker);

        buttonCenterMarker.setOnClickListener(this);

        Button btnSuggession = (Button) v.findViewById(R.id.btnSuggession);

        btnSuggession.setOnClickListener(this);

    }

    private void API_getPlotDetail(String plodID) {
        /**
         1.TamCode (ไม่บังคับใส่)
         2.AmpCode (บังคับใส่)
         3.ProvCode (บังคับใส่)
         */
        new ResponseAPI(context, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mGetPlotDetail mPlotDetail = (mGetPlotDetail) obj;
                List<mGetPlotDetail.mRespBody> mPlotDetailBodyLists = mPlotDetail.getRespBody();

                if (mPlotDetailBodyLists.size() != 0) {

                    prdID = mPlotDetailBodyLists.get(0).getPrdID();

                    API_getPlotSuit();

                }


            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getPlotDetail +
                "?PlotID=" + plodID +
                "&ImeiCode=" + ServiceInstance.GetDeviceID(context));

    }

    private void API_getPlotSuit() {

        String cmd = "";

        cmd = "?SuitFlag=" + suitFlag + "" +
                "&PrdID=" + prdID +
                "&TamCode=" + tamCode +
                "&AmpCode=" + ampCode +
                "&ProvCode=" + provCode +
                "&Latitude=" + String.valueOf(latitude) +
                "&Longitude=" + String.valueOf(longitude) +
                "&ImeiCode=" + ServiceInstance.GetDeviceID(context);


        new ResponseAPI(context, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mGetPlotSuit mPlotSuit = (mGetPlotSuit) obj;
                List<mGetPlotSuit.mRespBody> mPlotSuitBodyLists = mPlotSuit.getRespBody();

                if (mPlotSuitBodyLists.size() != 0) {

                    displayPlotSuitValue(mPlotSuitBodyLists.get(0));

                }


            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getPlotSuit + cmd);

    }

    private void displayPlotSuitValue(mGetPlotSuit.mRespBody var){
        com.neopixl.pixlui.components.textview.TextView txSuggessPlot = (com.neopixl.pixlui.components.textview.TextView) v.findViewById(R.id.txSuggessPlot);
        com.neopixl.pixlui.components.textview.TextView txAddress = (com.neopixl.pixlui.components.textview.TextView) v.findViewById(R.id.txAddress);
        ImageView suggessStar = (ImageView) v.findViewById(R.id.suggessStar);

        String address = "จ." + var.getProvNameTH() + " อ." + var.getAmpNameTH() + " ต." + var.getTamNameTH();

        txAddress.setText(address);
        txSuggessPlot.setText(var.getSuitLabel());

        switch (var.getSuitLevel()){
            case "1" :
                suggessStar.setImageResource(R.drawable.ic_1star);
                break;
            case "2" :
                suggessStar.setImageResource(R.drawable.ic_2star);
                break;
            case "3" :
                suggessStar.setImageResource(R.drawable.ic_3star);
                break;
            default:
                suggessStar.setImageResource(R.drawable.ic_0star);
                break;
        }

        suggession = var.getSuitLabel() + "\n" + var.getRecommendLabel() + "\n" + var.getRecommendProduct();
    }


    public void onClick(View v){
        if(v.getId() == R.id.btnSuggession){

            Button btnSuggession = (Button) v.findViewById(R.id.btnSuggession);

            if (popupWindow == null) {

                LayoutInflater layoutInflater
                        = (LayoutInflater) getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.layout_suggestion_popup, null);

                FrameLayout borderLayout = (FrameLayout)popupView.findViewById(R.id.borderFrame);


                switch (productType){
                    case CalculateConstant.PRODUCT_TYPE_ANIMAL :
                        borderLayout.setBackgroundResource(R.color.RcmoAnimalBG);
                        break;
                    case CalculateConstant.PRODUCT_TYPE_PLANT :
                        borderLayout.setBackgroundResource(R.color.RcmoPlantBG);
                        break;
                    case CalculateConstant.PRODUCT_TYPE_FISH :
                        borderLayout.setBackgroundResource(R.color.RcmoFishBG);
                        break;
                }

                com.neopixl.pixlui.components.textview.TextView txSuggession = (com.neopixl.pixlui.components.textview.TextView)popupView.findViewById(R.id.txSuggession);
                txSuggession.setText(suggession);

                Display display = getActivity().getWindowManager().getDefaultDisplay();
                int width = display.getWidth();
                int popupScreen = (int) (width*0.7);

                popupWindow = new PopupWindow(
                        popupView , popupScreen , popupScreen);

                popupWindow.setOutsideTouchable(true);
            }

            if (popupWindow.isShowing()) {

                fadeView.setVisibility(View.GONE);

                popupWindow.dismiss();
            }
            else {
                fadeView.setVisibility(View.VISIBLE);
                popupWindow.showAsDropDown(btnSuggession, Gravity.TOP|Gravity.RIGHT, 0);
            }


        }else if(v.getId() == R.id.btnMapStyle){

            if (mapType == 1) {
                mapType = 2;
                map.setMapType(2);
            }else{
                mapType = 1;
                map.setMapType(1);
            }

        }else if(v.getId() == R.id.btnCenterMarker){
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 17);
            map.animateCamera(cameraUpdate);
        }
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null)
            mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
