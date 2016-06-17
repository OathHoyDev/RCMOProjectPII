package th.co.rcmo.rcmoapp;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import th.co.rcmo.rcmoapp.Util.GPSTracker;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailAnimalMapFragment extends Fragment implements View.OnClickListener {

    MapView mapView;
    GoogleMap map;

    int mapType;

    GPSTracker gps;

    FrameLayout fadeView;

    private PopupWindow popupWindow;

    public ProductDetailAnimalMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_product_detail_animal_map, container, false);

        View v = inflater.inflate(R.layout.fragment_product_detail_animal_map, container,
                false);

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

        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 17);
            map.animateCamera(cameraUpdate);

            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.pin);

            int newWidth = (int) (bm.getWidth()*0.4);
            int newHeight = (int) (bm.getHeight()*0.4);

            bm = Bitmap.createScaledBitmap(bm, newWidth , newHeight , false);

            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude));
            marker.icon(BitmapDescriptorFactory.fromBitmap(bm));

            map.addMarker(marker);

            TextView lbAddress = (TextView) v.findViewById(R.id.lbAddress);
            lbAddress.setText("Test Address");

        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
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


        return v;
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
                borderLayout.setBackgroundResource(R.color.RcmoFishBG);

                popupWindow = new PopupWindow(
                        popupView,
                        500,
                        300);

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
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 17);
            map.animateCamera(cameraUpdate);
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
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
