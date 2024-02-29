package com.example.hw224a10357.Activities.Fragments;

import android.os.Bundle;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hw224a10357.Model.Player;
import com.example.hw224a10357.Utility.SignalManager;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.hw224a10357.R;


public class MapFragment extends Fragment implements OnMapReadyCallback {


    private GoogleMap googleMap;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        // Async map
        supportMapFragment.getMapAsync(this);
        // Return view
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // When map is loaded
        this.googleMap = googleMap;
        googleMap.setOnMapClickListener(latLng -> {
            // When clicked on map
            // Initialize marker options
            MarkerOptions markerOptions = new MarkerOptions();
            // Set position of marker
            markerOptions.position(latLng);
            // Set title of marker
            markerOptions.title(latLng.latitude + " : " + latLng.longitude);
            // Remove all marker
            this.googleMap.clear();
            // Animating to zoom the marker
            this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
            // Add marker on map
            this.googleMap.addMarker(markerOptions);
        });
    }

    public void showOnMap(Player player) {
        googleMap.clear();
        String name = player.getName();
        LatLng myLocation = new LatLng(player.getLatitude(), player.getLongitude());
        addMarker(myLocation, name);
        setCameraPosition(10, myLocation);
    }

    private void addMarker(LatLng myLocation, String name) {
        googleMap.addMarker(new MarkerOptions()
                .position(myLocation)
                .title(name));
    }

    private void setCameraPosition(int zoom, LatLng myLocation) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(myLocation)
                .zoom(zoom)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

}