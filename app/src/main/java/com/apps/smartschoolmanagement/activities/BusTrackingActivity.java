package com.apps.smartschoolmanagement.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition.Builder;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.apps.smartschoolmanagement.R;
import com.apps.smartschoolmanagement.utils.BaseFinishActivity;

public class BusTrackingActivity extends BaseFinishActivity implements OnMapReadyCallback {
    public GoogleMap googleMap;
    public MapView googleMapView;
    double lat = 17.4837d;
    double lng = 78.3158d;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setTitle("Bus Tracking");
        if (!(getIntent().getStringExtra("lat") == null || "".equals(getIntent().getStringExtra("lat")))) {
            this.lat = Double.parseDouble(getIntent().getStringExtra("lat"));
        }
        if (!(getIntent().getStringExtra("long") == null || "".equals(getIntent().getStringExtra("long")))) {
            this.lng = Double.parseDouble(getIntent().getStringExtra("long"));
        }
        this.googleMapView = (MapView) findViewById(R.id.mapView);
//        this.googleMapView.onCreate(savedInstanceState);
        this.googleMapView.onResume();
        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.googleMapView.getMapAsync(this);
    }

    public void onResume() {
        super.onResume();
        this.googleMapView.onResume();
    }

    public void onPause() {
        super.onPause();
        this.googleMapView.onPause();
    }

    public void onLowMemory() {
        super.onLowMemory();
        this.googleMapView.onLowMemory();
    }

    public void onStop() {
        super.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
        this.googleMapView.onDestroy();
    }

    public void onMapReady(GoogleMap gm) {
        this.googleMap = gm;
        if (!(this.googleMap == null || this.lat == 0.0d || this.lng == 0.0d)) {
            this.googleMap.addMarker(new MarkerOptions().position(new LatLng(this.lat, this.lng)).title("Present Location"));
            this.googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(new Builder().target(new LatLng(this.lat, this.lng)).zoom(18.0f).tilt(50.0f).build()));
        }
        this.googleMap.setIndoorEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            this.googleMap.setMyLocationEnabled(true);
            this.googleMap.getUiSettings().setMapToolbarEnabled(true);
            this.googleMap.getUiSettings().setZoomControlsEnabled(true);
            this.googleMap.getUiSettings().setCompassEnabled(true);
            this.googleMap.getUiSettings().setAllGesturesEnabled(true);
            this.googleMap.getUiSettings().setRotateGesturesEnabled(true);
            this.googleMap.getUiSettings().setIndoorLevelPickerEnabled(true);
            this.googleMap.getUiSettings().setIndoorLevelPickerEnabled(true);
            this.googleMap.getUiSettings().setMapToolbarEnabled(true);
        }
    }
}
