package th.go.oae.rcmo;


import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
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

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.neopixl.pixlui.components.textview.TextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import th.go.oae.rcmo.API.RequestServices;
import th.go.oae.rcmo.API.ResponseAPI;
import th.go.oae.rcmo.API.ResponseAPI_POST;
import th.go.oae.rcmo.Adapter.DialogAmphoeAdapter;
import th.go.oae.rcmo.Adapter.DialogProvinceAdapter;
import th.go.oae.rcmo.Adapter.DialogTumbonAdapter;
import th.go.oae.rcmo.Model.UserPlotModel;
import th.go.oae.rcmo.Module.mAmphoe;
import th.go.oae.rcmo.Module.mCurrentLocation;
import th.go.oae.rcmo.Module.mGetPlotDetail;
import th.go.oae.rcmo.Module.mGetPlotSuit;
import th.go.oae.rcmo.Module.mProvince;
import th.go.oae.rcmo.Module.mSavePlotDetail;
import th.go.oae.rcmo.Module.mTumbon;
import th.go.oae.rcmo.Util.CalculateConstant;
import th.go.oae.rcmo.Util.GPSTracker;
import th.go.oae.rcmo.Util.ServiceInstance;
import th.go.oae.rcmo.Util.Util;
import th.go.oae.rcmo.View.DialogChoice;
import th.go.oae.rcmo.View.ProgressAction;
import th.go.oae.rcmo.View.dialog_amphoe;
import th.go.oae.rcmo.View.dialog_province;
import th.go.oae.rcmo.View.dialog_tambon;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailMapFragment extends Fragment {

    public UserPlotModel userPlotModel;
    mProvince.mRespBody selectedprovince = null;
    mAmphoe.mRespBody selectedAmphoe = null;
    mTumbon.mRespBody selectedTumbon = null;

    mCurrentLocation.mRespBody currentLocation = null;

    MapView mapView;
    GoogleMap map;

    int mapType;

    GPSTracker gps;

    FrameLayout fadeView;

    public static PopupWindow popupWindow = null;

    private String productType;

    private String suitFlag;
//    private String prdID;
//    private String tamCode;
//    private String ampCode;
//    private String provCode;
    private String plodID;
    private String userID;

    private String latitude = "";
    private String longitude = "";

    String suggession;
    String recommend;
    String recommendProduct = "";
    String mapSuggess = "";

    private Context context;
    View fragmentView;
    LayoutInflater inflater;

    boolean isPopup = false;
    boolean isMapDialogDisplay = false;
    boolean isClickBtn = false;

    String TAG = "ProductDetailMapFragment";

    boolean saved = false;

    boolean clickSavePlot = false;

    String plotId;

    public ProductDetailMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_product_detail_map, container, false);

        fragmentView = inflater.inflate(R.layout.fragment_product_detail_map, container,
                false);
        this.inflater = inflater;
        context = fragmentView.getContext();
        userPlotModel = PBProductDetailActivity.userPlotModel;

        SharedPreferences sp = context.getSharedPreferences(ServiceInstance.PREF_NAME, Context.MODE_PRIVATE);
        userID = sp.getString(ServiceInstance.sp_userId, "0");

        getArgumentFromActivity();

        initialLayout(savedInstanceState);

        if (!"".equalsIgnoreCase(userPlotModel.getPlotID())) {
            API_getPlotDetail(plodID);
        } else {
            if ("".equalsIgnoreCase(userPlotModel.getTamCode())) {
//                getCurrentGPSForGetPlotSuit();
                displayPlotSuitDefault();
            } else {
                API_getTumbon(userPlotModel.getProvCode(), userPlotModel.getAmpCode(), userPlotModel.getTamCode());
            }
        }

        return fragmentView;
    }

    private void getCurrentGPSForGetPlotSuit() {

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            gps = new GPSTracker(getContext());

            if (gps.canGetLocation()) {

                double lat = 0;
                double lon = 0;

                lat = gps.getLatitude();
                lon = gps.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(lon);
            }

            showMap(Double.parseDouble(latitude), Double.parseDouble(longitude));
            suitFlag = "2";
            API_getPlotSuit(latitude, longitude, suitFlag);
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
        plodID = userPlotModel.getPlotID();
//        prdID = userPlotModel.getPrdID();
        if (userPlotModel.getTamCode().equals("")) {
            suitFlag = "2";
        } else {
            suitFlag = "1";
        }
//        tamCode = userPlotModel.getTamCode();
//        ampCode = userPlotModel.getAmpCode();
//        provCode = userPlotModel.getProvCode();
        // userID = userPlotModel.getUserID();

        productType = userPlotModel.getPrdGrpID();
    }

    private void initialLayout(Bundle savedInstanceState) {

        RelativeLayout suggessBottomBG = (RelativeLayout) fragmentView.findViewById(R.id.suggessBottomBG);
        RelativeLayout changeLocationLayout = (RelativeLayout) fragmentView.findViewById(R.id.changeLocationLayout);
        changeLocationLayout.setVisibility(View.GONE);

        ImageButton btnCenterMarker = (ImageButton) fragmentView.findViewById(R.id.btnCenterMarker);

        if ("".equalsIgnoreCase(userPlotModel.getProvCode()) &&
            "".equalsIgnoreCase(userPlotModel.getAmpCode()) &&
            "".equalsIgnoreCase(userPlotModel.getTamCode())){
            btnCenterMarker.setVisibility(View.GONE);
            displayPlotSuitDefault();
        }

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

        mapView = (MapView) fragmentView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        map = mapView.getMap();


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

//                latitude = String.valueOf(latLng.latitude);
//                longitude = String.valueOf(latLng.longitude);

                map.clear();
                if (!"".equals(latitude)) {
                    pinPlotLocation(Double.parseDouble(latitude), Double.parseDouble(longitude));
                }

                Log.d(TAG, "onMapClick: " + latLng.toString());
                API_getPlotSuit(String.valueOf(latLng.latitude), String.valueOf(latLng.longitude), "2");

                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.pin_pink);

                int newWidth = (int) (bm.getWidth() * 0.3);
                int newHeight = (int) (bm.getHeight() * 0.3);

                bm = Bitmap.createScaledBitmap(bm, newWidth, newHeight, false);

                MarkerOptions marker = new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude));
                marker.icon(BitmapDescriptorFactory.fromBitmap(bm));
                map.addMarker(marker);

                if (isPopup){
                    isPopup = false;
                    popupWindow.dismiss();
                    popupWindow = null;
                }

            }
        });

        //map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setPadding(0, 250, 0, 400);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        try {
            MapsInitializer.initialize(this.getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add Button Action
        setAction();
    }

    private void showMap(double latitude, double longitude) {

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 12);
        map.animateCamera(cameraUpdate);

        if ("".equalsIgnoreCase(userPlotModel.getTamCode())) {
            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude));
            map.addMarker(marker);
        }else {
            pinPlotLocation(latitude, longitude);
        }
    }

    private void pinPlotLocation(double latitude, double longitude) {

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.pin);

        int newWidth = (int) (bm.getWidth() * 0.4);
        int newHeight = (int) (bm.getHeight() * 0.4);

        bm = Bitmap.createScaledBitmap(bm, newWidth, newHeight, false);

        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude));
        marker.icon(BitmapDescriptorFactory.fromBitmap(bm));

        map.clear();
        map.addMarker(marker);
    }


    private void displayPlotSuitValue(mGetPlotSuit.mRespBody var) {

        com.neopixl.pixlui.components.textview.TextView txSuggessPlot = (com.neopixl.pixlui.components.textview.TextView) fragmentView.findViewById(R.id.txSuggessPlot);
        com.neopixl.pixlui.components.textview.TextView txAddress = (com.neopixl.pixlui.components.textview.TextView) fragmentView.findViewById(R.id.txAddress);
        ImageView suggessStar = (ImageView) fragmentView.findViewById(R.id.suggessStar);

        TextView provinceTextView = (TextView) fragmentView.findViewById(R.id.inputprovice);
        TextView amphoeTextView = (TextView) fragmentView.findViewById(R.id.inputAmphoe);
        TextView tumbonTextView = (TextView) fragmentView.findViewById(R.id.inputTumbon);

        provinceTextView.setText(var.getProvNameTH());
        amphoeTextView.setText(var.getAmpNameTH());
        tumbonTextView.setText(var.getTamNameTH());

        String address = "จ." + var.getProvNameTH() + " อ." + var.getAmpNameTH() + " ต." + var.getTamNameTH();

        txAddress.setText(address);

        if ("".equalsIgnoreCase(var.getSuitValue())) {
            txSuggessPlot.setText("ไม่พบข้อมูลความเหมาะสม");
        } else {
          // txSuggessPlot.setText(var.getSuitValue());
            txSuggessPlot.setText(var.getSuitLabel());
        }

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

    private void displayPlotSuitDefault() {

        com.neopixl.pixlui.components.textview.TextView txSuggessPlot = (com.neopixl.pixlui.components.textview.TextView) fragmentView.findViewById(R.id.txSuggessPlot);
        com.neopixl.pixlui.components.textview.TextView txAddress = (com.neopixl.pixlui.components.textview.TextView) fragmentView.findViewById(R.id.txAddress);
        ImageView suggessStar = (ImageView) fragmentView.findViewById(R.id.suggessStar);

        txAddress.setText("ไม่ได้กำหนดตำแหน่งแปลงที่ดิน");
        txSuggessPlot.setText("ไม่พบข้อมูลความเหมาะสม");

        suggessStar.setImageResource(R.drawable.ic_0star);


        suggession = "ไม่พบข้อมูลความเหมาะสม\n\n";
        recommend = "";
        recommendProduct = "";

        PBProductDetailActivity.mPlotSuit = null;


    }


    public void setAction() {

        fragmentView.findViewById(R.id.btnSuggession).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    isClickBtn = true;
                    isMapDialogDisplay = true;
                    Button btnSuggession = (Button) v.findViewById(R.id.btnSuggession);

                    LayoutInflater layoutInflater
                            = (LayoutInflater) getContext()
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.layout_suggestion_popup, null);

                    RelativeLayout suggess_layout = (RelativeLayout) popupView.findViewById(R.id.suggess_layout);

                    com.neopixl.pixlui.components.textview.TextView txSuggession = (com.neopixl.pixlui.components.textview.TextView) popupView.findViewById(R.id.marketPrice);
                    txSuggession.setText(suggession);

                    com.neopixl.pixlui.components.textview.TextView txRecommend = (com.neopixl.pixlui.components.textview.TextView) popupView.findViewById(R.id.txRecommend);
                    txRecommend.setText(recommend);

                    com.neopixl.pixlui.components.textview.TextView txRecommendProduct = (com.neopixl.pixlui.components.textview.TextView) popupView.findViewById(R.id.txRecommendProduct);
                    txRecommendProduct.setText(recommendProduct);

                    Display display = getActivity().getWindowManager().getDefaultDisplay();
                    int width = display.getWidth();
                    int height = display.getHeight();
                    int popupWidth = (int) (width * 0.95);
                    int popupHeight = (int) (height * 0.8);


                if(popupWindow == null) {
                    popupWindow = new PopupWindow(
                            popupView, popupWidth, popupHeight);

                    popupWindow.setOutsideTouchable(true);

                    Log.d(TAG,"New Popup");
                }

                if (isPopup) {
                    Log.d(TAG,"Close Popup");
                    isPopup = false;
                    popupWindow.dismiss();
                    popupWindow = null;
                } else {
                    Log.d(TAG,"Open Popup");
                    popupWindow.showAsDropDown(btnSuggession, Gravity.TOP | Gravity.RIGHT, 0);
                    isPopup = true;
                }


                }

        });

        fragmentView.findViewById(R.id.btnMapStyle).setOnClickListener(new View.OnClickListener() {
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

        fragmentView.findViewById(R.id.btnCenterMarker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double lat = Double.parseDouble(latitude);
                double lon = Double.parseDouble(longitude);

                map.clear();
                pinPlotLocation(lat, lon);

                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 12);
                map.animateCamera(cameraUpdate);

                API_getPlotSuit(latitude, longitude, suitFlag);
            }
        });

        fragmentView.findViewById(R.id.btnChangeLocation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RelativeLayout changeLocationLayout = (RelativeLayout) fragmentView.findViewById(R.id.changeLocationLayout);

                changeLocationLayout.setVisibility(View.VISIBLE);
            }
        });

        fragmentView.findViewById(R.id.btnCloseChangeLocation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RelativeLayout changeLocationLayout = (RelativeLayout) fragmentView.findViewById(R.id.changeLocationLayout);

                changeLocationLayout.setVisibility(View.GONE);
            }
        });

        fragmentView.findViewById(R.id.inputprovice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressAction.show(context);
                API_getProvince();
            }
        });

        fragmentView.findViewById(R.id.inputAmphoe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedprovince != null) {
                    ProgressAction.show(context);
                    API_getAmphoe(selectedprovince.getProvCode());
                }
            }
        });

        fragmentView.findViewById(R.id.inputTumbon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedprovince != null && selectedAmphoe != null) {
                    ProgressAction.show(context);
                    API_getTumbonList(selectedprovince.getProvCode(), selectedAmphoe.getAmpCode());
                }
            }
        });

        fragmentView.findViewById(R.id.btnSaveChangeLocation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedprovince == null || selectedAmphoe == null || selectedTumbon == null){

                    new DialogChoice(context)
                            .ShowOneChoice("กรุณากรอกข้อมูลให้ครบถ้วน", null);

                }else {

                    userPlotModel.setProvCode(selectedprovince.getProvCode());
                    userPlotModel.setAmpCode(selectedAmphoe.getAmpCode());
                    userPlotModel.setTamCode(selectedTumbon.getTamCode());

                    ImageButton btnCenterMarker = (ImageButton) fragmentView.findViewById(R.id.btnCenterMarker);
                    RelativeLayout changeLocationLayout = (RelativeLayout) fragmentView.findViewById(R.id.changeLocationLayout);

                    changeLocationLayout.setVisibility(View.GONE);

                    selectedprovince = null;
                    selectedAmphoe = null;
                    selectedTumbon = null;

                    btnCenterMarker.setVisibility(View.VISIBLE);

                    clickSavePlot = true;

                    API_getTumbon(userPlotModel.getProvCode(), userPlotModel.getAmpCode(), userPlotModel.getTamCode());

                }
            }
        });


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

                TextView provinceTextView = (TextView) fragmentView.findViewById(R.id.inputprovice);
                TextView amphoeTextView = (TextView) fragmentView.findViewById(R.id.inputAmphoe);
                TextView tumbonTextView = (TextView) fragmentView.findViewById(R.id.inputTumbon);


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

                TextView amphoeTextView = (TextView) fragmentView.findViewById(R.id.inputAmphoe);
                TextView tumbonTextView = (TextView) fragmentView.findViewById(R.id.inputTumbon);

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
                TextView tumbonTextView = (TextView) fragmentView.findViewById(R.id.inputTumbon);
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

                    userPlotModel.setPrdID(String.valueOf(mPlotDetailBodyLists.get(0).getPrdID()));
                    userPlotModel.setProvCode(String.valueOf(mPlotDetailBodyLists.get(0).getProvCode()));
                    userPlotModel.setAmpCode(String.valueOf(mPlotDetailBodyLists.get(0).getAmpCode()));
                    userPlotModel.setTamCode(String.valueOf(mPlotDetailBodyLists.get(0).getTamCode()));

                    if ("".equalsIgnoreCase(userPlotModel.getTamCode())) {
                        getCurrentGPSForGetPlotSuit();
                    } else {
                        ImageButton btnCenterMarker = (ImageButton) fragmentView.findViewById(R.id.btnCenterMarker);
                        btnCenterMarker.setVisibility(View.VISIBLE);

                        API_getTumbon(userPlotModel.getProvCode(), userPlotModel.getAmpCode(), userPlotModel.getTamCode());
                    }


                }


            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
            }
        }).API_Request(false, RequestServices.ws_getPlotDetail +
                "?PlotID=" + plodID +
                "&ImeiCode=" + ServiceInstance.GetDeviceID(context));

    }

    private void API_getPlotSuit(final String currLat, final String curLon, String suitFlag) {

        //SuitFlag (1=ใช้ TamCode / 2 = ใช้ latlng)

        String cmd = "";

        if ("1".equalsIgnoreCase(suitFlag)) {

            cmd = "?SuitFlag=" + suitFlag + "" +
                    "&PrdID=" + userPlotModel.getPrdID() +
                    "&TamCode=" + userPlotModel.getTamCode() +
                    "&AmpCode=" + userPlotModel.getAmpCode() +
                    "&ProvCode=" + userPlotModel.getProvCode() +
                    "&Latitude="  +
                    "&Longitude="  +
                    "&ImeiCode=" + ServiceInstance.GetDeviceID(context);

        } else {

            cmd = "?SuitFlag=" + suitFlag + "" +
                    "&PrdID="  + userPlotModel.getPrdID() +
                    "&TamCode="  +
                    "&AmpCode="  +
                    "&ProvCode=" +
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

                    PBProductDetailActivity.mPlotSuit = mPlotSuitBodyLists.get(0);

                    displayPlotSuitValue(mPlotSuitBodyLists.get(0));

                    API_getCurrentLocation(currLat , curLon);
                }
            }

            @Override
            public void callbackError(int code, String errorMsg) {

                Log.d("Error", errorMsg);

                displayPlotSuitDefault();
            }
        }).API_Request(false, RequestServices.ws_getPlotSuit + cmd);

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
                    //popUpProvinceListDialog(provinceBodyLists);

                    new dialog_province(context,"จังหวัด",provinceBodyLists, new dialog_province.OnSelectChoice() {
                        @Override
                        public void selectChoice(mProvince.mRespBody choice) {
                            selectedprovince = choice;
                            android.widget.TextView provinceTextView = (android.widget.TextView) fragmentView.findViewById(R.id.inputprovice);
                            android.widget.TextView amphoeTextView = (android.widget.TextView) fragmentView.findViewById(R.id.inputAmphoe);
                            android.widget.TextView tumbonTextView = (android.widget.TextView) fragmentView.findViewById(R.id.inputTumbon);
                            selectedAmphoe = null;
                            selectedTumbon = null;
                            if(selectedprovince.getProvCode().equals("0")){
                                selectedprovince = null;
                                provinceTextView.setText("");
                            }else{
                                provinceTextView.setText(selectedprovince.getProvNameTH());
                            }
                            amphoeTextView.setText("");
                            tumbonTextView.setText("");

                        }
                    });
                    ProgressAction.gone(context);
                }
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
                ProgressAction.gone(context);
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
                    //popUpAmphoeListDialog(amphoeBodyLists);

                    new dialog_amphoe(context,"อำเภอ",amphoeBodyLists, new dialog_amphoe.OnSelectChoice() {
                        @Override
                        public void selectChoice(mAmphoe.mRespBody choice) {
                            selectedAmphoe = choice;
                            android.widget.TextView amphoeTextView = (android.widget.TextView) fragmentView.findViewById(R.id.inputAmphoe);
                            android.widget.TextView tumbonTextView = (android.widget.TextView) fragmentView.findViewById(R.id.inputTumbon);

                            selectedTumbon = null;


                            if(selectedAmphoe.getAmpCode().equals("0")) {
                                selectedAmphoe = null;
                                amphoeTextView.setText("");
                            }else{
                                amphoeTextView.setText(selectedAmphoe.getAmpNameTH());
                            }
                            tumbonTextView.setText("");

                        }
                    });

                    ProgressAction.gone(context);
                }
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
                ProgressAction.gone(context);
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
                    //popUpTumbonListDialog(tumbonBodyLists);

                    new dialog_tambon(context,"ตำบล", tumbonBodyLists, new dialog_tambon.OnSelectChoice() {
                        @Override
                        public void selectChoice(mTumbon.mRespBody choice) {
                            selectedTumbon = choice;

                            android.widget.TextView tumbonTextView = (android.widget.TextView) fragmentView.findViewById(R.id.inputTumbon);
                            if(selectedTumbon.getTamCode().equals("0")){
                                selectedTumbon = null;
                                tumbonTextView.setText("");
                            }else{
                                tumbonTextView.setText(selectedTumbon.getTamNameTH());
                            }
                        }
                    });

                    ProgressAction.gone(context);

                }
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
                ProgressAction.gone(context);
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

                suitFlag = "1";

                showMap(Double.parseDouble(latitude), Double.parseDouble(longitude));
                API_getPlotSuit(latitude, longitude, suitFlag);
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

    private void API_getCurrentLocation(String latitude, String longitude) {
        /**
         1.latitude (บังคับใส่)
         2.longitude (บังคับใส่)
         */
        new ResponseAPI(context, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mCurrentLocation currentLocationInfo = (mCurrentLocation) obj;

                final List<mCurrentLocation.mRespBody> currentLocationBodyLists = currentLocationInfo.getRespBody();

                if (currentLocationBodyLists.size() != 0) {
                    //  ProveNameTH
                    currentLocation = currentLocationBodyLists.get(0);
                    if(currentLocation.getProvNameTH()  == null || currentLocation.getProvNameTH().equals("")) {
                        currentLocation.setProvNameTH(currentLocation.getProveNameTH());
                    }
                    selectedprovince = new mProvince.mRespBody();
                    selectedprovince.setProvCode(currentLocation.getProvCode());
                    selectedprovince.setProvNameTH(currentLocation.getProvNameTH());

                    selectedAmphoe = new mAmphoe.mRespBody();
                    selectedAmphoe.setProvCode(currentLocation.getProvCode());
                    selectedAmphoe.setAmpCode(currentLocation.getAmpCode());
                    selectedAmphoe.setAmpNameTH(currentLocation.getAmpNameTH());

                    selectedTumbon = new mTumbon.mRespBody();
                    selectedTumbon.setProvCode(currentLocation.getProvCode());
                    selectedTumbon.setAmpCode(currentLocation.getAmpCode());
                    selectedTumbon.setTamCode(currentLocation.getTamCode());
                    selectedTumbon.setTamNameTH(currentLocation.getTamNameTH());

                    // amphoeBodyLists.add(0, defaultAmphoe);
                    // orgAmphoes = amphoeBodyLists;
                    // amphoes = orgAmphoes;
                    // setAmphoeUI();

                    //ProgressAction.gone(StepOneActivity.this);
//                    setSelectedLocationUI();
                    userPlotModel.setProvCode(selectedprovince.getProvCode());
                    userPlotModel.setAmpCode(selectedAmphoe.getAmpCode());
                    userPlotModel.setTamCode(selectedTumbon.getTamCode());

                    if (clickSavePlot) {
                        upsertUserPlot();
                    }
                }
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
            }

        }).API_Request(false, RequestServices.ws_getCurrentLocation +
                "?Latitude=" + latitude +
                "&Longitude=" + longitude
        );
    }

    private void upsertUserPlot() {

        // API_GetUserPlot(userId,prdId,prdGrpId,plotId);

        // userPlotModel.setUserID(userPlotModel.getUserID());

        clickSavePlot = false;


        if (!"".equalsIgnoreCase(userID) && !userID.equals("0")) {

            if (userPlotModel.getPlotID() != null
                    && !userPlotModel.getPlotID().equals("")
                    && !userPlotModel.getPlotID().equals("0")) {
                API_getPlotDetailAndSave(userPlotModel.getPlotID());
            } else {
                API_SaveProd_POST("1", userPlotModel);
            }

        } else {
            new DialogChoice(context)
                    .ShowOneChoice("ไม่สามารถบันทึกข้อมูล", "- กรุณา Login ก่อนทำการบันทึกข้อมูล"); //save image
        }


    }

    private void API_getPlotDetailAndSave(String plotID) {
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
                    mGetPlotDetail.mRespBody plotDetail = mPlotDetailBodyLists.get(0);

                    if (userPlotModel.getPlotRai().equals("") || userPlotModel.getPlotRai().equals("0")) {
                        if(plotDetail.getPlotRai().equals("0")){
                            plotDetail.setPlotRai("");
                        }
                        userPlotModel.setPlotRai(String.valueOf(plotDetail.getPlotRai()));
                    }
                    if (userPlotModel.getPlotNgan().equals("") || userPlotModel.getPlotNgan().equals("0")) {
                        if(plotDetail.getPlotNgan().equals("0")){
                            plotDetail.setPlotNgan("");
                        }
                        userPlotModel.setPlotNgan(String.valueOf(plotDetail.getPlotNgan()));
                    }
                    if (userPlotModel.getPlotWa().equals("") || userPlotModel.getPlotWa().equals("0")) {
                        if(plotDetail.getPlotWa().equals("0")){
                            plotDetail.setPlotWa("");
                        }
                        userPlotModel.setPlotWa(String.valueOf(plotDetail.getPlotWa()));
                    }
                    if (userPlotModel.getPlotMeter().equals("") || userPlotModel.getPlotMeter().equals("0")) {
                        if(plotDetail.getPlotMeter().equals("0")){
                            plotDetail.setPlotMeter("");
                        }
                        userPlotModel.setPlotMeter(String.valueOf(plotDetail.getPlotMeter()));
                    }


                    if(userPlotModel.getVarValue().equals("")|| userPlotModel.getVarValue().equals("0")){
                        userPlotModel.setVarValue(plotDetail.getVarValue());
                    }

                    if (userPlotModel.getAnimalNumber().equals("") || userPlotModel.getAnimalNumber().equals("0")) {
                        plotDetail.setAnimalNumber(plotDetail.getAnimalNumber().equals("0")?"":plotDetail.getAnimalNumber());
                        userPlotModel.setAnimalNumber(String.valueOf(plotDetail.getAnimalNumber()));
                    }

                    if(userPlotModel.getAnimalPrice().equals("")|| userPlotModel.getAnimalPrice().equals("0")){
                        plotDetail.setAnimalPrice(plotDetail.getAnimalPrice().equals("0")?"":plotDetail.getAnimalPrice());
                        userPlotModel.setAnimalPrice(plotDetail.getAnimalPrice());
                    }

                    if(userPlotModel.getAnimalWeight().equals("")|| userPlotModel.getAnimalWeight().equals("0")){
                        plotDetail.setAnimalWeight(plotDetail.getAnimalWeight().equals("0")?"":plotDetail.getAnimalWeight());
                        userPlotModel.setAnimalWeight(plotDetail.getAnimalWeight());
                    }

                    if(userPlotModel.getPondRai().equals("")|| userPlotModel.getPondRai().equals("0")){
                        plotDetail.setPondRai(plotDetail.getPondRai().equals("0")?"":plotDetail.getPondRai());
                        userPlotModel.setPondRai(plotDetail.getPondRai());
                    }

                    if(userPlotModel.getPondNgan().equals("")|| userPlotModel.getPondNgan().equals("0")){
                        plotDetail.setPondNgan(plotDetail.getPondNgan().equals("0")?"":plotDetail.getPondNgan());
                        userPlotModel.setPondNgan(plotDetail.getPondNgan());
                    }

                    if(userPlotModel.getPondWa().equals("")|| userPlotModel.getPondWa().equals("0")){
                        plotDetail.setPondWa(plotDetail.getPondWa().equals("0")?"":plotDetail.getPondWa());
                        userPlotModel.setPondWa(plotDetail.getPondWa());
                    }

                    if(userPlotModel.getPondMeter().equals("")|| userPlotModel.getPondMeter().equals("0")){
                        plotDetail.setPondMeter(plotDetail.getPondMeter().equals("0")?"":plotDetail.getPondMeter());
                        userPlotModel.setPondMeter(plotDetail.getPondMeter());
                    }

                    if(userPlotModel.getFisheryNumber().equals("")|| userPlotModel.getFisheryNumber().equals("0")){
                        plotDetail.setFisheryNumber(plotDetail.getFisheryNumber().equals("0")?"":plotDetail.getFisheryNumber());
                        userPlotModel.setFisheryNumber(plotDetail.getFisheryNumber());
                    }

                    if(userPlotModel.getFisheryType().equals("")|| userPlotModel.getFisheryType().equals("0")){
                        plotDetail.setFisheryType(plotDetail.getFisheryType().equals("0")?"":plotDetail.getFisheryType());
                        userPlotModel.setFisheryType(plotDetail.getFisheryType());
                    }

                    if(userPlotModel.getFisheryWeight().equals("")|| userPlotModel.getFisheryWeight().equals("0")){
                        plotDetail.setFisheryWeight(plotDetail.getFisheryWeight().equals("0")?"":plotDetail.getFisheryWeight());
                        userPlotModel.setFisheryWeight(plotDetail.getFisheryWeight());
                    }

                    if(userPlotModel.getCoopMeter().equals("")|| userPlotModel.getCoopMeter().equals("0")){
                        plotDetail.setCoopMeter(plotDetail.getCoopMeter().equals("0")?"":plotDetail.getCoopMeter());
                        userPlotModel.setCoopMeter(plotDetail.getCoopMeter());
                    }

                    if(userPlotModel.getCoopNumber().equals("")|| userPlotModel.getCoopNumber().equals("0")){
                        plotDetail.setCoopNumber(plotDetail.getCoopNumber().equals("0")?"":plotDetail.getCoopNumber());
                        userPlotModel.setCoopNumber(plotDetail.getCoopNumber());
                    }


                    // mVarPlanA varA =  new Gson().fromJson(plotDetail.getVarValue(), mVarPlanA.class);
                    //  Log.d("Testttt --> ",varA.toString());
                    API_SaveProd_POST("2", userPlotModel);


                }


            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d("Error", errorMsg);
            }
        }).API_Request(true, RequestServices.ws_getPlotDetail +
                "?PlotID=" + plotID +
                "&ImeiCode=" + ServiceInstance.GetDeviceID(context));

    }

    private void API_SavePlotDetail(String saveFlag, UserPlotModel userPlotInfo) {
        /**
         * 1.SaveFlag (บังคับ)
         2.UserID (บังคับ)
         3.PlotID (บังคับกรณี SaveFlag = 2)
         4.PrdID (บังคับ)
         5.PrdGrpID(บังคับ)
         6.PlotRai
         7.PondRai
         8.PondNgan
         9.PondWa
         10.PondMeter
         11.CoopMeter
         12.CoopNumber
         13.TamCode
         14.AmpCode
         15.ProvCode
         16.AnimalNumber
         17.AnimalWeight
         18.AnimalPrice
         19.FisheryType
         20.FisheryNumType
         21.FisheryNumber
         22.FisheryWeight
         23.ImeiCode
         24.VarName
         25.VarValue

         SaveFlag = 1 บังคับใส่ 1 2 4 5
         SaveFlag = 2 บังคับใส่ 1 - 5

         */

        new ResponseAPI(context, new ResponseAPI.OnCallbackAPIListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void callbackSuccess(Object obj) {

                mSavePlotDetail savePlotDetailInfo = (mSavePlotDetail) obj;
                List<mSavePlotDetail.mRespBody> savePlotDetailBodyLists = savePlotDetailInfo.getRespBody();

                Log.d(TAG, "---- >Size" + savePlotDetailBodyLists.size());
                if (savePlotDetailBodyLists.size() != 0) {

                    plotId = String.valueOf(savePlotDetailBodyLists.get(0).getPlotID());
                    if (plotId == null) {
                        plotId = "";
                    }
                    userPlotModel.setPlotID(plotId);
                    Log.d(TAG, "Response plotId : " + plotId);
                    saved = true;
                    clickSavePlot = false;
                    Util.showDialogAndDismiss(context, "บันทึกข้อมูลสำเร็จ");

                }

            }

            @Override
            public void callbackError(int code, String errorMsg) {
                Log.d(TAG, errorMsg);
            }
        }).API_Request(true, RequestServices.ws_savePlotDetail +

                "?SaveFlag=" + saveFlag +
                "&UserID=" + userID +
                "&PlotID=" + userPlotInfo.getPlotID() +
                "&PrdID=" + userPlotInfo.getPrdID() +
                "&PrdGrpID=" + userPlotInfo.getPrdGrpID() +
                "&PlotRai=" + userPlotInfo.getPlotRai() +
                "&PlotNgan=" + userPlotInfo.getPlotNgan() +
                "&PlotWa=" + userPlotInfo.getPlotWa() +
                "&PlotMeter=" + userPlotInfo.getPlotMeter() +
                "&PondRai=" + userPlotInfo.getPondRai() +
                "&PondNgan=" + userPlotInfo.getPondNgan() +
                "&PondWa=" + userPlotInfo.getPondWa() +
                "&PondMeter=" + userPlotInfo.getPondMeter() +
                "&CoopMeter=" + userPlotInfo.getCoopMeter() +
                "&CoopNumber=" + userPlotInfo.getCoopNumber() +
                "&TamCode=" + Util.defualtNullStringZero(userPlotInfo.getTamCode()) +
                "&AmpCode=" + Util.defualtNullStringZero(userPlotInfo.getAmpCode()) +
                "&ProvCode=" + Util.defualtNullStringZero(userPlotInfo.getProvCode()) +
                "&AnimalNumber=" + userPlotInfo.getAnimalNumber() +
                "&AnimalWeight=" + userPlotInfo.getAnimalWeight() +
                "&AnimalPrice=" + userPlotInfo.getAnimalPrice() +
                "&FisheryType=" + userPlotInfo.getFisheryType() +
                "&FisheryNumType=" + userPlotInfo.getFisheryNumType() +
                "&FisheryNumber=" + userPlotInfo.getFisheryNumber() +
                "&FisheryWeight=" + userPlotInfo.getFisheryWeight() +
                "&ImeiCode=" + ServiceInstance.GetDeviceID(context) +
                "&VarName=" + userPlotInfo.getVarName() +
                "&VarValue=" + userPlotInfo.getVarValue() +
                "&CalResult=" + userPlotInfo.getCalResult()
        );
    }

    private void API_SaveProd_POST(String saveFlag, UserPlotModel userPlotInfo) {
        HashMap<String,Object> param = new HashMap<>();

        param.put("SaveFlag",saveFlag);
        param.put("UserID",userID);
        param.put("PlotID",userPlotInfo.getPlotID() );
        param.put("PrdID",userPlotInfo.getPrdID());
        param.put("PrdGrpID",userPlotInfo.getPrdGrpID());

        param.put("PlotRai",userPlotInfo.getPlotRai());
        param.put("PlotNgan",userPlotInfo.getPlotNgan());
        param.put("PlotWa",userPlotInfo.getPlotWa());
        param.put("PlotMeter",userPlotInfo.getPlotMeter());

        param.put("PondRai",userPlotInfo.getPondRai());
        param.put("PondNgan",userPlotInfo.getPondNgan());
        param.put("PondWa",userPlotInfo.getPondWa());
        param.put("PondMeter",userPlotInfo.getPondMeter());
        param.put("CoopMeter",userPlotInfo.getCoopMeter());
        param.put("CoopNumber",userPlotInfo.getCoopNumber());
        param.put("TamCode", Util.defualtNullStringZero(userPlotInfo.getTamCode()));
        param.put("AmpCode",Util.defualtNullStringZero(userPlotInfo.getAmpCode()));
        param.put("ProvCode",Util.defualtNullStringZero(userPlotInfo.getProvCode()));
        param.put("AnimalNumber",userPlotInfo.getAnimalNumber());
        param.put("AnimalWeight",userPlotInfo.getAnimalWeight());
        param.put("AnimalPrice",userPlotInfo.getAnimalPrice());
        param.put("FisheryType",userPlotInfo.getFisheryType());
        param.put("FisheryNumType",userPlotInfo.getFisheryNumType());
        param.put("FisheryNumber",userPlotInfo.getFisheryNumber() );
        param.put("FisheryWeight",userPlotInfo.getFisheryWeight());
        param.put("ImeiCode",ServiceInstance.GetDeviceID(context));
        param.put("VarName",userPlotInfo.getVarName() );
        param.put("VarValue",userPlotInfo.getVarValue());
        param.put("CalResult",userPlotInfo.getCalResult());



        new ResponseAPI_POST(context, new ResponseAPI_POST.OnCallbackAPIListener() {
            @Override
            public void callbackSuccess(JSONObject obj) {
                mSavePlotDetail mSaveResp = new Gson().fromJson(obj.toString(), mSavePlotDetail.class);
                List<mSavePlotDetail.mRespBody> mRespBody = mSaveResp.getRespBody();
                if(mRespBody.size()>0){
                    Log.d("Test ------>","PlodId "+mRespBody.get(0).getPlotID());
                    plotId = String.valueOf(mRespBody.get(0).getPlotID());
                    if (plotId == null) {
                        plotId = "";
                    }
                    userPlotModel.setPlotID(plotId);
                    saved = true;
                    clickSavePlot = false;
                    Util.showDialogAndDismiss(context, "บันทึกข้อมูลสำเร็จ");
                }
            }

            @Override
            public void callbackError(int code, String errorMsg) {
                //  progressbar.gone(PriceActivity.this);
                //listview.setVisibility(View.INVISIBLE);
                //findViewById(R.id.no_list).setVisibility(View.VISIBLE);

            }
        }).POST(RequestServices.ws_savePlotDetail, param, true, false);

    }

    @Override
    public void onStop() {
        super.onStop();
        if(popupWindow !=null){
              popupWindow.dismiss();
              popupWindow = null;

              isMapDialogDisplay = false;
        }
    }
}
