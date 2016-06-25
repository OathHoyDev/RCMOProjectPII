package th.co.rcmo.rcmoapp;


import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.util.List;

import th.co.rcmo.rcmoapp.API.RequestServices;
import th.co.rcmo.rcmoapp.API.ResponseAPI;
import th.co.rcmo.rcmoapp.Adapter.DialogAmphoeAdapter;
import th.co.rcmo.rcmoapp.Adapter.DialogProvinceAdapter;
import th.co.rcmo.rcmoapp.Adapter.DialogTumbonAdapter;
import th.co.rcmo.rcmoapp.Model.UserPlotModel;
import th.co.rcmo.rcmoapp.Module.mAmphoe;
import th.co.rcmo.rcmoapp.Module.mGetPlotDetail;
import th.co.rcmo.rcmoapp.Module.mGetPlotSuit;
import th.co.rcmo.rcmoapp.Module.mProvince;
import th.co.rcmo.rcmoapp.Module.mTumbon;
import th.co.rcmo.rcmoapp.Util.CalculateConstant;
import th.co.rcmo.rcmoapp.Util.GPSTracker;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailMapFragment extends Fragment implements View.OnClickListener {

    public UserPlotModel userPlotModel;
    mProvince.mRespBody selectedprovince = null;
    mAmphoe.mRespBody selectedAmphoe = null;
    mTumbon.mRespBody selectedTumbon = null;

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
    private String userID;

    private String latitude;
    private String longitude;

    String suggession;
    String recommend;
    String recommendProduct = "";
    String mapSuggess = "";

    private Context context;
    View v;
    LayoutInflater inflater;

    boolean isPopup = false;

    String TAG = "ProductDetailMapFragment";

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
        this.inflater = inflater;
        context = v.getContext();
        userPlotModel = PBProductDetailActivity.userPlotModel;
        getArgumentFromActivity();

        initialLayout(savedInstanceState);

        if (!"".equalsIgnoreCase(userID)) {
            API_getPlotDetail(plodID);
        }

        return v;
    }

    private void getCurrentGPS() {

        gps = new GPSTracker(getContext());

        if (gps.canGetLocation()) {

            double lat = 0;
            double lon = 0;

            lat = gps.getLatitude();
            lon = gps.getLongitude();

            latitude = String.valueOf(lat);
            longitude = String.valueOf(lon);
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

    private void getArgumentFromActivity() {

         /*
        plodID = getArguments().getString("plodID");
        suitFlag = getArguments().getString("suitFlag");
        tamCode = getArguments().getString("tamCode");
        ampCode = getArguments().getString("ampCode");
        provCode = getArguments().getString("provCode");
        userID = getArguments().getString("userID");
        productType = getArguments().getString("productType");
        */
        plodID = userPlotModel.getPrdID();
        if (userPlotModel.getTamCode().equals("")) {
            suitFlag = "2";
        } else {
            suitFlag = "1";
        }
        tamCode = userPlotModel.getTamCode();
        ampCode = userPlotModel.getAmpCode();
        provCode = userPlotModel.getProvCode();
        userID = userPlotModel.getUserID();

        productType = userPlotModel.getPrdGrpID();
    }

    private void initialLayout(Bundle savedInstanceState) {

        RelativeLayout suggessBottomBG = (RelativeLayout) v.findViewById(R.id.suggessBottomBG);
        //
        switch (productType) {
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

        fadeView = (FrameLayout) v.findViewById(R.id.fadeLayout);
        fadeView.setVisibility(View.GONE);

        mapView = (MapView) v.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(true);

        // Gets to GoogleMap from the MapView and does initialization stuff
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);


        }

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.d(TAG, "onMapClick: " + latLng.toString());
                API_getPlotSuit(String.valueOf(latLng.latitude), String.valueOf(latLng.longitude), "2");
            }
        });

        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setPadding(0, 250, 0, 400);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        try {
            MapsInitializer.initialize(this.getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

//        gps = new GPSTracker(getContext());
//
//        if (gps.canGetLocation()) {
//
//            double lat = 0;
//            double lon = 0;
//
//            if ("".equalsIgnoreCase(tamCode)) {
//                lat = gps.getLatitude();
//                lon = gps.getLongitude();
//
//                latitude = String.valueOf(lat);
//                longitude = String.valueOf(lon);
//
//                showMap(lat, lon);
//            }
////            else {
////                API_getTumbon(provCode, ampCode, tamCode);
////            }
//
//
//        }


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

    private void showMap(double latitude, double longitude) {

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 12);
        map.animateCamera(cameraUpdate);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.pin);

        int newWidth = (int) (bm.getWidth() * 0.4);
        int newHeight = (int) (bm.getHeight() * 0.4);

        bm = Bitmap.createScaledBitmap(bm, newWidth, newHeight, false);

        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude));
        marker.icon(BitmapDescriptorFactory.fromBitmap(bm));

        map.addMarker(marker);
    }


    private void displayPlotSuitValue(mGetPlotSuit.mRespBody var) {
        com.neopixl.pixlui.components.textview.TextView txSuggessPlot = (com.neopixl.pixlui.components.textview.TextView) v.findViewById(R.id.txSuggessPlot);
        com.neopixl.pixlui.components.textview.TextView txAddress = (com.neopixl.pixlui.components.textview.TextView) v.findViewById(R.id.txAddress);
        ImageView suggessStar = (ImageView) v.findViewById(R.id.suggessStar);

        String address = "จ." + var.getProvNameTH() + " อ." + var.getAmpNameTH() + " ต." + var.getTamNameTH();

        txAddress.setText(address);
        txSuggessPlot.setText(var.getSuitValue());

        switch (var.getSuitLevel()) {
            case "1":
                suggessStar.setImageResource(R.drawable.ic_1star);
                break;
            case "2":
                suggessStar.setImageResource(R.drawable.ic_2star);
                break;
            case "3":
                suggessStar.setImageResource(R.drawable.ic_3star);
                break;
            default:
                suggessStar.setImageResource(R.drawable.ic_0star);
                break;
        }


        suggession = var.getSuitLabel();
        recommend = var.getRecommendLabel();
        recommendProduct = "";

        if (!"".equalsIgnoreCase(var.getRecommendProduct())) {
            String tmpString = var.getRecommendProduct();
            String[] splitTmpString = tmpString.split(",");

            for (int i = 0; i < splitTmpString.length; i++) {
                recommendProduct += "- " + splitTmpString[i].trim() + "\n";
            }
        }
    }


    public void onClick(View v) {

        if (v.getId() == R.id.btnSuggession) {

            Button btnSuggession = (Button) v.findViewById(R.id.btnSuggession);

            LayoutInflater layoutInflater
                    = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View popupView = layoutInflater.inflate(R.layout.layout_suggestion_popup, null);

//            RelativeLayout borderLayout = (RelativeLayout)popupView.findViewById(R.id.borderFrame);

//            switch (productType){
//                case CalculateConstant.PRODUCT_TYPE_ANIMAL :
//                    borderLayout.setBackgroundResource(R.color.RcmoAnimalBG);
//                    break;
//                case CalculateConstant.PRODUCT_TYPE_PLANT :
//                    borderLayout.setBackgroundResource(R.color.RcmoPlantBG);
//                    break;
//                case CalculateConstant.PRODUCT_TYPE_FISH :
//                    borderLayout.setBackgroundResource(R.color.RcmoFishBG);
//                    break;
//            }

            com.neopixl.pixlui.components.textview.TextView txSuggession = (com.neopixl.pixlui.components.textview.TextView) popupView.findViewById(R.id.txSuggession);
            txSuggession.setText(suggession);

            com.neopixl.pixlui.components.textview.TextView txRecommend = (com.neopixl.pixlui.components.textview.TextView) popupView.findViewById(R.id.txRecommend);
            txRecommend.setText(recommend);

            com.neopixl.pixlui.components.textview.TextView txRecommendProduct = (com.neopixl.pixlui.components.textview.TextView) popupView.findViewById(R.id.txRecommendProduct);
            txRecommendProduct.setText(recommendProduct);

            if (popupWindow == null) {

                Display display = getActivity().getWindowManager().getDefaultDisplay();
                int width = display.getWidth();
                int popupWidth = (int) (width * 0.7);
                int popupHeight = (int) (width * 0.6);

                popupWindow = new PopupWindow(
                        popupView, popupWidth, popupHeight);

                popupWindow.setOutsideTouchable(true);

                fadeView.setVisibility(View.VISIBLE);
                popupWindow.showAsDropDown(btnSuggession, Gravity.TOP | Gravity.RIGHT, 0);
                isPopup = true;

            } else {

                if (isPopup) {
                    fadeView.setVisibility(View.GONE);
                    popupWindow.dismiss();
                    popupWindow = null;
                    isPopup = false;
                } else {
                    fadeView.setVisibility(View.VISIBLE);
                    popupWindow.showAsDropDown(btnSuggession, Gravity.TOP | Gravity.RIGHT, 0);
                    isPopup = true;
                }
            }


        } else if (v.getId() == R.id.btnMapStyle) {

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

        } else if (v.getId() == R.id.btnCenterMarker) {

            double lat = Double.parseDouble(latitude);
            double lon = Double.parseDouble(longitude);

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 12);
            map.animateCamera(cameraUpdate);
        }
    }


    // API --------------------------------------------

    private void popUpProvinceListDialog(final List<mProvince.mRespBody> provinceRespList) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.spin_location_dialog, null);

        view.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        ListView listView = (ListView) view.findViewById(R.id.listLocation);

        DialogProvinceAdapter provinceAdapter = new DialogProvinceAdapter(context, provinceRespList);
        listView.setAdapter(provinceAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView provinceTextView = (TextView) view.findViewById(R.id.inputprovice);
                TextView amphoeTextView = (TextView) view.findViewById(R.id.inputAmphoe);
                TextView tumbonTextView = (TextView) view.findViewById(R.id.inputTumbon);


                selectedAmphoe = null;
                selectedTumbon = null;
                if (provinceRespList.get(position).getProvCode().equals("0")) {
                    selectedprovince = null;
                    provinceTextView.setText("");
                } else {
                    selectedprovince = provinceRespList.get(position);
                    provinceTextView.setText(selectedprovince.getProvNameTH());

                }

                amphoeTextView.setText("");
                tumbonTextView.setText("");

                dialog.dismiss();

            }
        });

        dialog.setContentView(view);
        dialog.show();
    }

    private void popUpAmphoeListDialog(final List<mAmphoe.mRespBody> amphoeRespList) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.spin_location_dialog, null);

        view.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        ListView listView = (ListView) view.findViewById(R.id.listLocation);

        DialogAmphoeAdapter amphoeAdapter = new DialogAmphoeAdapter(context, amphoeRespList);
        listView.setAdapter(amphoeAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView amphoeTextView = (TextView) view.findViewById(R.id.inputAmphoe);
                TextView tumbonTextView = (TextView) view.findViewById(R.id.inputTumbon);

                selectedTumbon = null;

                if (amphoeRespList.get(position).getAmpCode().equals("0")) {
                    selectedAmphoe = null;
                    amphoeTextView.setText("");
                } else {
                    selectedAmphoe = amphoeRespList.get(position);
                    amphoeTextView.setText(selectedAmphoe.getAmpNameTH());
                }

                tumbonTextView.setText("");

                dialog.dismiss();

            }
        });

        dialog.setContentView(view);
        dialog.show();
    }

    private void popUpTumbonListDialog(final List<mTumbon.mRespBody> tunbonRespList) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.spin_location_dialog, null);

        view.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        ListView listView = (ListView) view.findViewById(R.id.listLocation);

        DialogTumbonAdapter tumbonAdapter = new DialogTumbonAdapter(context, tunbonRespList);
        listView.setAdapter(tumbonAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //   TextView amphoeTextView = (TextView) view.findViewById(R.id.inputAmphoe);
                TextView tumbonTextView = (TextView) view.findViewById(R.id.inputTumbon);
                if (tunbonRespList.get(position).getTamCode().equals("0")) {
                    selectedTumbon = null;
                    tumbonTextView.setText("");
                } else {
                    selectedTumbon = tunbonRespList.get(position);
                    tumbonTextView.setText(selectedTumbon.getTamNameTH());
                }
                dialog.dismiss();

            }
        });

        dialog.setContentView(view);
        dialog.show();
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
                    provCode = mPlotDetailBodyLists.get(0).getProvCode();
                    ampCode = mPlotDetailBodyLists.get(0).getAmpCode();
                    tamCode = mPlotDetailBodyLists.get(0).getTamCode();

                    if ("".equalsIgnoreCase(tamCode)) {
                        getCurrentGPS();
                        API_getPlotSuit(latitude , longitude , "2");
                    }else{
                        API_getTumbon(provCode , ampCode , tamCode);
                    }



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

    private void API_getPlotSuit(String currLat, String curLon, String suitFlag) {

        String cmd = "";

        if ("1".equalsIgnoreCase(suitFlag)) {

            cmd = "?SuitFlag=" + suitFlag + "" +
                    "&PrdID=" + prdID +
                    "&TamCode=" + tamCode +
                    "&AmpCode=" + ampCode +
                    "&ProvCode=" + provCode +
                    "&Latitude=" + currLat +
                    "&Longitude=" + curLon +
                    "&ImeiCode=" + ServiceInstance.GetDeviceID(context);

        } else {

            cmd = "?SuitFlag=" + suitFlag + "" +
                    "&PrdID=" + prdID +
                    "&TamCode=" + tamCode +
                    "&AmpCode=" + ampCode +
                    "&ProvCode=" + provCode +
                    "&Latitude=" + currLat +
                    "&Longitude=" + curLon +
                    "&ImeiCode=" + ServiceInstance.GetDeviceID(context);
        }

        new ResponseAPI(context, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mGetPlotSuit mPlotSuit = (mGetPlotSuit) obj;
                List<mGetPlotSuit.mRespBody> mPlotSuitBodyLists = mPlotSuit.getRespBody();

                if (mPlotSuitBodyLists.size() != 0) {

                    ((PBProductDetailActivity) getActivity()).mPlotSuit = mPlotSuitBodyLists.get(0);

                    displayPlotSuitValue(mPlotSuitBodyLists.get(0));

                    showMap(Double.parseDouble(latitude) , Double.parseDouble(longitude));

                }


            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getPlotSuit + cmd);

    }

    private void API_getProvince() {
        /**
         1.ProvCode (ไม่บังคับใส่)
         */
        new ResponseAPI(context, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mProvince provinceInfo = (mProvince) obj;

                final List<mProvince.mRespBody> provinceBodyLists = provinceInfo.getRespBody();
                mProvince.mRespBody defaultProvince = new mProvince.mRespBody();
                defaultProvince.setProvCode("0");
                defaultProvince.setProvNameTH("ไม่ระบุ");

                if (provinceBodyLists.size() != 0) {
                    provinceBodyLists.add(0, defaultProvince);
                    popUpProvinceListDialog(provinceBodyLists);
                }
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getProvince +
                "?ProvCode="
        );

    }

    private void API_getAmphoe(String provinceId) {
        /**
         1.AmpCode (ไม่บังคับใส่)
         2.ProvCode (บังคับใส่)
         */
        new ResponseAPI(context, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mAmphoe amphoeInfo = (mAmphoe) obj;

                final List<mAmphoe.mRespBody> amphoeBodyLists = amphoeInfo.getRespBody();
                mAmphoe.mRespBody defaultAmphoe = new mAmphoe.mRespBody();
                defaultAmphoe.setAmpCode("0");
                defaultAmphoe.setAmpNameTH("ไม่ระบุ");

                if (amphoeBodyLists.size() != 0) {
                    amphoeBodyLists.add(0, defaultAmphoe);
                    popUpAmphoeListDialog(amphoeBodyLists);
                }
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getAmphoe +
                "?AmpCode=" +
                "&ProvCode=" + provinceId
        );

    }

    private void API_getTumbonList(String provinceId, String amphoeId) {
        /**
         1.TamCode (ไม่บังคับใส่)
         2.AmpCode (บังคับใส่)
         3.ProvCode (บังคับใส่)
         */
        new ResponseAPI(context, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mTumbon tumbonInfo = (mTumbon) obj;

                final List<mTumbon.mRespBody> tumbonBodyLists = tumbonInfo.getRespBody();
                mTumbon.mRespBody defaultTumbon = new mTumbon.mRespBody();
                defaultTumbon.setTamCode("0");
                defaultTumbon.setTamNameTH("ไม่ระบุ");

                if (tumbonBodyLists.size() != 0) {
                    tumbonBodyLists.add(0, defaultTumbon);
                    popUpTumbonListDialog(tumbonBodyLists);
                }
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getTambon +
                "?TamCode=" +
                "&AmpCode=" + amphoeId +
                "&ProvCode=" + provinceId
        );

    }

    private void API_getTumbon(String provinceId, String amphoeId, String tambonId) {
        /**
         1.TamCode (ไม่บังคับใส่)
         2.AmpCode (บังคับใส่)
         3.ProvCode (บังคับใส่)
         */
        new ResponseAPI(context, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mTumbon tumbonInfo = (mTumbon) obj;

                final List<mTumbon.mRespBody> tumbonBodyLists = tumbonInfo.getRespBody();
                mTumbon.mRespBody tumbon = tumbonBodyLists.get(0);

                latitude = tumbon.getLatitude();
                longitude = tumbon.getLongitude();

                API_getPlotSuit(latitude, longitude, "1");



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


}