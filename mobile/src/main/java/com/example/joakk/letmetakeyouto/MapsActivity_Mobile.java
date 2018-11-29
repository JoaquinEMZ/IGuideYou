package com.example.joakk.letmetakeyouto;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MapsActivity_Mobile extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public ArrayList<String> nombres;
    public double[] puntos;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        puntos = bundle.getDoubleArray("lista_puntos");
        nombres = bundle.getStringArrayList("nombres_tiendas");
        setContentView(R.layout.activity_maps__mobile);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        createMarker(mMap);
    }


    public void createMarker(GoogleMap googleMap){
        mMap = googleMap;
        int contador = 0;

        for(int i =0; i < nombres.size(); i++){
            LatLng position = new LatLng(puntos[contador],puntos[contador+1]);
            mMap.addMarker(new MarkerOptions().position(position).title(nombres.get(i)));
            contador +=2;
        }
        LatLng latLng = new LatLng(-33.011844, -71.549230);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));


    }
}
