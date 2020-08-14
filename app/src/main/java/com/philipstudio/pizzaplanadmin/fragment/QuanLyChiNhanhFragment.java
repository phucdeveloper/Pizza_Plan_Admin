package com.philipstudio.pizzaplanadmin.fragment;

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
import com.philipstudio.pizzaplanadmin.R;

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

        setUpViTriCacCuaHang();

    }

    private void showMarker(LatLng latLng, String description){
        MarkerOptions options = new MarkerOptions().position(latLng);
        options.title(description);
        mGoogleMap.addMarker(options);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18.0f);
        mGoogleMap.animateCamera(cameraUpdate);
    }

    private void setUpViTriCacCuaHang() {
        LatLng latLng1 = new LatLng(10.760398, 106.670878);
        showMarker(latLng1, "91 Nguyễn Chí Thanh, phường 9, Quận 5, Hồ Chí Minh");
        LatLng latLng2 = new LatLng(10.773527, 106.678397);
        showMarker(latLng2, "162 Cao Thắng, Quận 10, Hồ Chí Minh");
        LatLng latLng3 = new LatLng(10.770437, 106.676542);
        showMarker(latLng3, "567 Điện Biên Phủ, phường 1, Quận 3, Hồ Chí Minh");
        LatLng latLng4 = new LatLng(10.796334, 106.666035);
        showMarker(latLng4, "266 Đường Lê Văn Sỹ, Phường 1, Tân Bình, Hồ Chí Minh");
        LatLng latLng5 = new LatLng(10.810307, 106.695229);
        showMarker(latLng5, "132 Nơ Trang Long, phường 14, Bình Thạnh, Hồ Chí Minh");
        LatLng latLng6 = new LatLng(10.757893, 106.700130);
        showMarker(latLng6, "56 Đường Khánh Hội, phường 4, Quận 4, Hồ Chí Minh");
    }

}
