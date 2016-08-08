package th.go.oae.rcmo;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.neopixl.pixlui.components.textview.TextView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import th.go.oae.rcmo.API.RequestServices;
import th.go.oae.rcmo.API.ResponseAPI;
import th.go.oae.rcmo.Adapter.DialogTumbonAdapter;
import th.go.oae.rcmo.Model.UserPlotModel;
import th.go.oae.rcmo.Module.mGetMarketList;
import th.go.oae.rcmo.Module.mGetMarketPrice;
import th.go.oae.rcmo.Module.mTumbon;
import th.go.oae.rcmo.Util.GPSTracker;
import th.go.oae.rcmo.Util.ServiceInstance;
import th.go.oae.rcmo.View.DialogChoice;

/**
 * Created by SilVeriSm on 8/1/2016 AD.
 */
public class StepTwoMapActivity extends FragmentActivity {

    GoogleMap map;
    GPSTracker gps;
    MapView mapView;
    boolean isShowing = false;
    View marketListView;

    boolean isChooseAllProduct = false;

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

    List<mGetMarketPrice.mRespBody> marketPriceList = new ArrayList<mGetMarketPrice.mRespBody>();

    private List<Marker> markerList = new ArrayList<Marker>();

    Context context;

    static class ViewHolder {
        private ListView marketList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_two_map);

        context = this;

        initMap(savedInstanceState);
        initialLayout();
        setAction();

    }

    private void addMarketMarker(List<mGetMarketList.MarketListObj> marketList) {

        double minLat = 10000;
        double minLon = 10000;

        double maxLat = 0;
        double maxLon = 0;

        markerList = new ArrayList<Marker>();

        for (Iterator itr = marketList.iterator(); itr.hasNext(); ) {

            mGetMarketList.MarketListObj market = (mGetMarketList.MarketListObj) itr.next();

            if (market.getLatitude() != "" && market.getLongitude() != "") {

                pinPlotLocation(market);

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

    private void pinPlotLocation(mGetMarketList.MarketListObj marketObj) {

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.pin_market);

        int newWidth = (int) (bm.getWidth() * 0.1);
        int newHeight = (int) (bm.getHeight() * 0.1);

        bm = Bitmap.createScaledBitmap(bm, newWidth, newHeight, false);



        MarkerOptions markerOption = new MarkerOptions().position(new LatLng(Double.parseDouble(marketObj.getLatitude()), Double.parseDouble(marketObj.getLongitude())));
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(bm));
        markerOption.title(marketObj.getMarketName());

        if (marketObj.getPriceValue() == ""){
            markerOption.snippet("0");
        }else {
            markerOption.snippet(marketObj.getPriceValue());
        }

        Marker marker = map.addMarker(markerOption);


        markerList.add(marker);
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

        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker marker) {
                View v = getLayoutInflater().inflate(R.layout.dialog_market_plot_detail, null);

                TextView marketName = (TextView) v.findViewById(R.id.marketName);
                marketName.setText(marker.getTitle());

                TextView marketPrice = (TextView) v.findViewById(R.id.marketPrice);
                marketPrice.setText(marker.getSnippet());

                return v;
            }

            @Override
            public View getInfoContents(Marker marker) {

                View v = getLayoutInflater().inflate(R.layout.dialog_market_plot_detail, null);

                return null;

            }
        });

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

//                latitude = String.valueOf(latLng.latitude);
//                longitude = String.valueOf(latLng.longitude);

                Log.d(TAG, "onMapClick: " + latLng.toString());

            }
        });

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                marker.showInfoWindow();

                return true;
            }
        });

        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng latLng) {
                for(Marker marker : markerList) {
                    if(Math.abs(marker.getPosition().latitude - latLng.latitude) < 0.05 && Math.abs(marker.getPosition().longitude - latLng.longitude) < 0.05) {
                        //Toast.makeText(StepTwoMapActivity.this, "got clicked", Toast.LENGTH_SHORT).show(); //do some stuff
                        break;
                    }
                }

            }
        });

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                List<mGetMarketList.MarketListObj> tmpMarletList = new ArrayList<mGetMarketList.MarketListObj>();

                if (isChooseAllProduct) {

                    tmpMarletList = allProductMarketList;

                }else{

                    tmpMarletList = selectProductMarketList;
                }

                for (mGetMarketList.MarketListObj marketObj : tmpMarletList) {
                    if (marketObj.getLatitude() != "") {
                        if (Math.abs(marker.getPosition().latitude - Double.parseDouble(marketObj.getLatitude())) < 0.05 && Math.abs(marker.getPosition().longitude - Double.parseDouble(marketObj.getLongitude())) < 0.05) {
                            API_GetMarketPrice(marketObj.getMarketID(), userPlotModel.getUserID() , marketObj.getMarketName());
                            break;
                        }
                    }
                }
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
        private TextView marketName, marketPrice , marketUnit;
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

                pch.marketUnit = (TextView) convertView.findViewById(R.id.marketPriceUnit);

                convertView.setTag(pch);
            } else {
                pch = (MarketListViewHolder) convertView.getTag();
            }

            final mGetMarketList.MarketListObj marketObj = marketList.get(position);

            pch.marketName.setText(marketObj.getMarketName());

            if (marketObj.getPriceValue() == ""){
                pch.marketPrice.setText("0");
            }else {
                pch.marketPrice.setText(marketObj.getPriceValue());
            }
            if(isChooseAllProduct){
                pch.marketPrice.setVisibility(View.GONE);
            }else{
                pch.marketPrice.setVisibility(View.VISIBLE);
            }
            pch.marketName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (marketObj.getLongitude() != "" && marketObj.getLatitude() != ""){
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                                new LatLng(
                                        Double.parseDouble(marketObj.getLatitude()),
                                        Double.parseDouble(marketObj.getLongitude())), 10);
                        map.animateCamera(cameraUpdate);

                        for(Marker marker : markerList) {
                            if(Math.abs(marker.getPosition().latitude - Double.parseDouble(marketObj.getLatitude())) < 0.05 && Math.abs(marker.getPosition().longitude - Double.parseDouble(marketObj.getLongitude())) < 0.05) {
                                //Toast.makeText(StepTwoMapActivity.this, "got clicked", Toast.LENGTH_SHORT).show(); //do some stuff
                                marker.showInfoWindow();
                                break;
                            }
                        }
                    }else{
                        new DialogChoice(context).ShowOneChoice("", "ไม่พบตำแหน่งตลาด");
                    }
                }
            });

            pch.marketName.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {

                    API_GetMarketPrice(marketObj.getMarketID() , userPlotModel.getUserID() , marketObj.getMarketName());

                    return true;

                }
            });

            pch.marketPrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (marketObj.getLongitude() != "" && marketObj.getLatitude() != ""){
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                                new LatLng(
                                        Double.parseDouble(marketObj.getLatitude()),
                                        Double.parseDouble(marketObj.getLongitude())), 10);
                        map.animateCamera(cameraUpdate);
                    }
                }
            });

            return convertView;
        }
    }

    private void initSlideView() {

        marketListView = findViewById(R.id.marketList);

        marketListAdaptor = new MarketListAdaptor(selectProductMarketList);

        TextView numberMarket = (TextView) findViewById(R.id.bar);
        numberMarket.setText("รายชื่อจุดรับซื้อ " + selectProductMarketList.size() + " แห่ง");

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

        mLayout.setCoveredFadeColor(0);

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

                findViewById(R.id.market_price_label).setVisibility(View.GONE);

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

                findViewById(R.id.market_price_label).setVisibility(View.VISIBLE);
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

        findViewById(R.id.btnHowto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DialogChoice(StepTwoMapActivity.this)
                        .ShowTutorial("g8");


            }
        });

    }

    private String strtitle = "";
    List<mGetMarketPrice.mRespBody> priceLists = new ArrayList<mGetMarketPrice.mRespBody>();

    private void popUpMarketPriceListDialog(String marketName) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_market_price);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        // set the custom dialog components - text, image and button
        TextView titleProduct = (TextView) dialog.findViewById(R.id.titleProduct);
        titleProduct.setText("จุดรับซื้อ " + marketName);

        ListView priceList = (ListView)dialog.findViewById(R.id.marketPriceListview);

        priceList.setAdapter(new listAdapter(marketPriceList));


        LinearLayout dialogButton = (LinearLayout) dialog.findViewById(R.id.closeArea);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShowing = false;
                dialog.dismiss();
            }
        });

        if(!isShowing){
            isShowing = true;
            Log.d("Check ","dialog.isShowing() : false");
            dialog.show();
        }else{
            Log.d("Check ","dialog.isShowing() : true");
        }



    }

    class listAdapter extends BaseAdapter {

        List<mGetMarketPrice.mRespBody> priceLists;
        listAdapter(List<mGetMarketPrice.mRespBody> priceLists) {
            listAdapter.this.priceLists =priceLists;
        }

        @Override
        public int getCount() {
            return priceLists.size();
        }

        @Override
        public mGetMarketPrice.mRespBody getItem(int position) {
            return priceLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row;
            row = inflater.inflate(R.layout.row_market_price_popup, parent, false);
            TextView marketName        = (TextView)row.findViewById(R.id.marketName);
            TextView marketPrice        = (TextView)row.findViewById(R.id.marketPrice);
            TextView marketPriceUnit        = (TextView)row.findViewById(R.id.marketPriceUnit);

            marketName.setText(priceLists.get(position).getPrdMkName());
            marketPrice.setText(priceLists.get(position).getPriceValue());
            marketPriceUnit.setText(priceLists.get(position).getUnitValue());

            return (row);
        }
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

    private void API_GetMarketPrice(String marketID, String userID , final String marketName) {

        new ResponseAPI(this, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mGetMarketPrice mMarketPrice = (mGetMarketPrice) obj;

                List<mGetMarketPrice.mRespBody> mMarketPriceRespBody = mMarketPrice.getRespBody();



                if (mMarketPriceRespBody.size() != 0) {

                    marketPriceList = new ArrayList<mGetMarketPrice.mRespBody>();
                    marketPriceList = mMarketPriceRespBody;

                    popUpMarketPriceListDialog(marketName);


                }


            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Erroo", errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getMarketPrice +
                "?MarketID=" + marketID
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

                addMarketMarker(selectProductMarketList);

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
