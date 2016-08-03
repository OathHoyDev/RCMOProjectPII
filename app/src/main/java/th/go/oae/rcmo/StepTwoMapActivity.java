package th.go.oae.rcmo;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.neopixl.pixlui.components.textview.TextView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import th.go.oae.rcmo.API.RequestServices;
import th.go.oae.rcmo.API.ResponseAPI;
import th.go.oae.rcmo.Model.UserPlotModel;
import th.go.oae.rcmo.Module.mGetMarketList;
import th.go.oae.rcmo.Module.mTumbon;
import th.go.oae.rcmo.Util.GPSTracker;
import th.go.oae.rcmo.Util.ServiceInstance;

/**
 * Created by SilVeriSm on 8/1/2016 AD.
 */
public class StepTwoMapActivity extends FragmentActivity {

    GoogleMap map;
    GPSTracker gps;
    MapView mapView;

    View marketListView;

    boolean isChooseAllProduct = true;

    public static UserPlotModel userPlotModel;

    int mapType = 1;

    ViewHolder h = new ViewHolder();

    private SlidingUpPanelLayout mLayout;

    private static final String TAG = "StepTwoMapActivity";

    MarketListAdaptor marketListAdaptor = null;

    double centerLatitude = 0;
    double centerLongitude = 0;

    List<mGetMarketList.MarketListObj> allProductMarketList = new ArrayList<mGetMarketList.MarketListObj>();
    List<mGetMarketList.MarketListObj> selectProductMarketList = new ArrayList<mGetMarketList.MarketListObj>();

    static class ViewHolder {
        private ListView marketList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_two_map);

        initMap(savedInstanceState);
        initialLayout();
        setAction();

    }

    private void addMarketMarker(List<mGetMarketList.MarketListObj> marketList) {

        double minLat = 10000;
        double minLon = 10000;

        double maxLat = 0;
        double maxLon = 0;

        for (Iterator itr = marketList.iterator(); itr.hasNext(); ) {

            mGetMarketList.MarketListObj market = (mGetMarketList.MarketListObj) itr.next();

            if (market.getLatitude() != "" && market.getLongitude() != "") {
                pinPlotLocation(Double.parseDouble(market.getLatitude()), Double.parseDouble(market.getLongitude()));

                if (Double.parseDouble(market.getLatitude()) < minLat){
                    minLat = Double.parseDouble(market.getLatitude());
                }

                if (Double.parseDouble(market.getLatitude()) > maxLat){
                    maxLat = Double.parseDouble(market.getLatitude());
                }

                if (Double.parseDouble(market.getLongitude()) < minLon){
                    minLon = Double.parseDouble(market.getLongitude());
                }

                if (Double.parseDouble(market.getLongitude()) > maxLon){
                    maxLon = Double.parseDouble(market.getLongitude());
                }

            }

        }

        if (centerLatitude == 0 && centerLongitude == 0){
            centerLatitude = (minLat + maxLat)/2;
            centerLongitude = (minLon + maxLon)/2;

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(centerLatitude, centerLongitude), 10);
            map.animateCamera(cameraUpdate);
        }
    }

    private void pinPlotLocation(double latitude, double longitude) {

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.pin_market);

        int newWidth = (int) (bm.getWidth() * 0.1);
        int newHeight = (int) (bm.getHeight() * 0.1);

        bm = Bitmap.createScaledBitmap(bm, newWidth, newHeight, false);

        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude));
        marker.icon(BitmapDescriptorFactory.fromBitmap(bm));

        map.addMarker(marker);
    }

    private void pinCenterPlotLocation(double latitude, double longitude) {

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.pin);

        int newWidth = (int) (bm.getWidth() * 0.4);
        int newHeight = (int) (bm.getHeight() * 0.4);

        bm = Bitmap.createScaledBitmap(bm, newWidth, newHeight, false);

        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude));
        marker.icon(BitmapDescriptorFactory.fromBitmap(bm));

        map.addMarker(marker);
    }


    private void initialLayout() {

        h.marketList = (ListView) findViewById(R.id.marketList);

        switchProductButton();

        if (userPlotModel.getProvCode() != "" && userPlotModel.getAmpCode() != "" && userPlotModel.getTamCode() != "") {

            API_getTumbon(userPlotModel.getProvCode(), userPlotModel.getAmpCode(), userPlotModel.getTamCode());

        }else{
            API_GetAllMarketList(userPlotModel.getProvCode(), "", userPlotModel.getUserID());
        }


    }



    private void switchProductButton() {

        TextView btnMapSelectProduct = (TextView) findViewById(R.id.btnMapSelectProduct);
        btnMapSelectProduct.setText(userPlotModel.getPrdValue());

        TextView btnMapSelectAll = (TextView) findViewById(R.id.btnMapSelectAll);

        if (isChooseAllProduct) {
            btnMapSelectProduct.setTextColor(getResources().getColor(R.color.RcmoPlantBG));
            btnMapSelectProduct.setBackgroundResource(R.drawable.white_cut_left_conner);

            btnMapSelectAll.setTextColor(getResources().getColor(R.color.RcmoWhiteBG));
            btnMapSelectAll.setBackgroundResource(R.drawable.green_cut_right_conner);
        } else {
            btnMapSelectProduct.setTextColor(getResources().getColor(R.color.RcmoWhiteBG));
            btnMapSelectProduct.setBackgroundResource(R.drawable.green_cut_left_conner);

            btnMapSelectAll.setTextColor(getResources().getColor(R.color.RcmoPlantBG));
            btnMapSelectAll.setBackgroundResource(R.drawable.white_cut_right_conner);
        }
    }

    private void initMap(Bundle savedInstanceState) {

        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        map = mapView.getMap();


        // Gets to GoogleMap from the MapView and does initialization stuff
        if (ContextCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);

        }

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

//                latitude = String.valueOf(latLng.latitude);
//                longitude = String.valueOf(latLng.longitude);

                Log.d(TAG, "onMapClick: " + latLng.toString());

            }
        });


        //map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setPadding(0, 100, 0, 200);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            Log.e(TAG, "Error Initial Map : " + e.getMessage());
        }

        Log.d(TAG, "initMap Complete");

    }

    class MarketListViewHolder {
        private TextView marketName, marketPrice;
    }



    class MarketListAdaptor extends BaseAdapter {
        List<mGetMarketList.MarketListObj> marketList;

        MarketListAdaptor(List<mGetMarketList.MarketListObj> marketList) {
            this.marketList = marketList;
        }

        public  void resetMarketList (List<mGetMarketList.MarketListObj> newMarketList){
            //resultList.clear();


            marketList = newMarketList;
            Log.i(TAG,"R-->newresultList Size "+newMarketList.size());
            notifyDataSetChanged();
            map.clear();

        }

        @Override
        public int getCount() {
            return marketList.size();
        }

        @Override
        public mGetMarketList.MarketListObj getItem(int position) {

            return marketList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            MarketListViewHolder pch = new MarketListViewHolder();

            if (convertView == null) {
                LayoutInflater inflater = StepTwoMapActivity.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.row_market_price, parent, false);

                pch.marketName = (TextView) convertView.findViewById(R.id.marketName);
                pch.marketPrice = (TextView) convertView.findViewById(R.id.marketPrice);

                convertView.setTag(pch);
            } else {
                pch = (MarketListViewHolder) convertView.getTag();
            }

            final mGetMarketList.MarketListObj marketObj = marketList.get(position);

            pch.marketName.setText(marketObj.getMarketName());
            pch.marketPrice.setText(marketObj.getPriceValue());

            return convertView;
        }
    }

    private void initSlideView() {

        marketListView = findViewById(R.id.marketList);

        marketListAdaptor = new MarketListAdaptor(allProductMarketList);

        TextView numberMarket = (TextView) findViewById(R.id.bar);
        numberMarket.setText("รายชื่อจุดรับซื้อ " + allProductMarketList.size() + " แห่ง");

        h.marketList.setAdapter(marketListAdaptor);

        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.i(TAG, "onPanelStateChanged " + newState);
                if (SlidingUpPanelLayout.PanelState.EXPANDED.equals(newState)) {
                    ImageView btnView = (ImageView) findViewById(R.id.upBtn);
                    btnView.setImageResource(R.drawable.down);
                }
                if (SlidingUpPanelLayout.PanelState.COLLAPSED.equals(newState)) {
                    ImageView btnView = (ImageView) findViewById(R.id.upBtn);
                    btnView.setImageResource(R.drawable.up);
                }
            }
        });


    }

    public void setAction() {

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btnMapSelectAll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isChooseAllProduct = true;
                switchProductButton();

                marketListAdaptor.resetMarketList(allProductMarketList);
                addMarketMarker(allProductMarketList);

                pinCenterPlotLocation(centerLatitude, centerLongitude);

                TextView numberMarket = (TextView) findViewById(R.id.bar);
                numberMarket.setText("รายชื่อจุดรับซื้อ " + allProductMarketList.size() + " แห่ง");
            }
        });

        findViewById(R.id.btnMapSelectProduct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isChooseAllProduct = false;
                switchProductButton();

                marketListAdaptor.resetMarketList(selectProductMarketList);
                addMarketMarker(selectProductMarketList);

                pinCenterPlotLocation(centerLatitude, centerLongitude);

                TextView numberMarket = (TextView) findViewById(R.id.bar);
                numberMarket.setText("รายชื่อจุดรับซื้อ " + selectProductMarketList.size() + " แห่ง");
            }
        });

        findViewById(R.id.btnMapStyle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageButton btnMap = (ImageButton) v.findViewById(R.id.btnMapStyle);

                if (mapType == 1) {
                    mapType = 2;
                    map.setMapType(2);
                    btnMap.setBackgroundResource(R.drawable.btn_road);
                } else {
                    mapType = 1;
                    map.setMapType(1);
                    btnMap.setBackgroundResource(R.drawable.btn_sate);
                }
            }
        });

        findViewById(R.id.btnCenterMarker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(centerLatitude, centerLongitude), 10);
            map.animateCamera(cameraUpdate);

            }
        });

    }

    private void API_getTumbon(String provinceId, String amphoeId, String tambonId) {
        /**
         1.TamCode (ไม่บังคับใส่)
         2.AmpCode (บังคับใส่)
         3.ProvCode (บังคับใส่)
         */
        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mTumbon tumbonInfo = (mTumbon) obj;

                final List<mTumbon.mRespBody> tumbonBodyLists = tumbonInfo.getRespBody();
                mTumbon.mRespBody tumbon = tumbonBodyLists.get(0);

                centerLatitude = Double.parseDouble(tumbon.getLatitude());
                centerLongitude = Double.parseDouble(tumbon.getLongitude());

                pinCenterPlotLocation(centerLatitude , centerLongitude);

                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(centerLatitude, centerLongitude), 10);
                map.animateCamera(cameraUpdate);

                API_GetAllMarketList(userPlotModel.getProvCode(), "", userPlotModel.getUserID());

            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getTambon +
                "?TamCode=" + tambonId +
                "&AmpCode=" + amphoeId +
                "&ProvCode=" + provinceId
        );

    }

    private void API_GetAllMarketList(String provCode, String prdID, String userID) {

        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mGetMarketList mMarketList = (mGetMarketList) obj;

                List<mGetMarketList.mRespBody> mMarketListRespBody = mMarketList.getRespBody();

                List<mGetMarketList.MarketListObj> marketListObjList = new ArrayList<mGetMarketList.MarketListObj>();

                if (mMarketListRespBody.size() != 0) {

                    mGetMarketList.mRespBody respBody = mMarketListRespBody.get(0);

                    marketListObjList = respBody.getMarketList();

                    if (marketListObjList.size() > 0) {
                        allProductMarketList = marketListObjList;
                    }

                }

                API_GetSelectMarketList(userPlotModel.getProvCode(), userPlotModel.getPrdID(), userPlotModel.getUserID());
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Erroo", errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getMarketList +
                "?ProvCode=" + provCode
                + "&PrdID=" + prdID
                + "&UserID=" + userID
                + "&ImeiCode=" + ServiceInstance.GetDeviceID(StepTwoMapActivity.this)
        );

    }

    private void API_GetSelectMarketList(String provCode, String prdID, String userID) {
        /*
        1.TamCode (บังคับ)
        2.AmpCode (บังคับ)
        3.ProvCode ( บังคับ)
        4.PlantFlag
        5.AnimalFlag
        6.FisheryFlag
         */


        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mGetMarketList mMarketList = (mGetMarketList) obj;

                List<mGetMarketList.mRespBody> mMarketListRespBody = mMarketList.getRespBody();

                List<mGetMarketList.MarketListObj> marketListObjList = new ArrayList<mGetMarketList.MarketListObj>();

                if (mMarketListRespBody.size() != 0) {

                    mGetMarketList.mRespBody respBody = mMarketListRespBody.get(0);

                    marketListObjList = respBody.getMarketList();

                    if (marketListObjList.size() > 0) {
                        selectProductMarketList = marketListObjList;
                    }

                }

                addMarketMarker(allProductMarketList);

                initSlideView();
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Erroo", errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getMarketList +
                "?ProvCode=" + provCode
                + "&PrdID=" + prdID
                + "&UserID=" + userID
                + "&ImeiCode=" + ServiceInstance.GetDeviceID(StepTwoMapActivity.this)
        );

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
