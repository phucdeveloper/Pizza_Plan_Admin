package com.philipstudio.pizzaplanadmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class QuanLyChiNhanhFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener{

    private GoogleMap mGoogleMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quanlychinhanh, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapClick(LatLng latLng) {
  //      showMarker(latLng, "Current Marker" + latLng.latitude + ", " + latLng.longitude);
        showMarker(latLng, "text");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(true);
        mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.setOnMapClickListener(this);
        mGoogleMap.setTrafficEnabled(true);


    }

    private void showMarker(LatLng latLng, String description){
//        mGoogleMap.clear();
//        MarkerOptions options = new MarkerOptions().position(latLng);
//        options.title(description)
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_baseline_place));
//        mGoogleMap.addMarker(options);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18.0f);
        mGoogleMap.animateCamera(cameraUpdate);

    }
}
