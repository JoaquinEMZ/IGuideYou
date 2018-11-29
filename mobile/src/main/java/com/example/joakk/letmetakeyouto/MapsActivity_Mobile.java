package com.example.joakk.letmetakeyouto;

import android.Manifest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;

import android.content.pm.PackageManager;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.support.v7.widget.Toolbar;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import java.util.ArrayList;

public class MapsActivity_Mobile extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnInfoWindowClickListener {

<<<<<<< Updated upstream
    private GoogleMap mMap;
    private Marker marker;
    private Context mContext=MapsActivity_Mobile.this;

    public ArrayList<String> nombres;
    public double[] puntos;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
=======

        String canal = "my_channel_01";
>>>>>>> Stashed changes

        private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

        private GoogleMap mMap;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_maps__mobile);
            Bundle bundle = getIntent().getExtras();
            puntos = bundle.getDoubleArray("lista_puntos");
            nombres = bundle.getStringArrayList("nombres_tiendas");
            /*Toolbar tb = findViewById(R.id.toolbar);
            tb.setSubtitle("Dejame Guiarte A");*/

            SupportMapFragment mapFragment =
                    (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        }

        /** Called when the map is ready. */
        @Override
        public void onMapReady(GoogleMap map) {
            mMap = map;
            enableMyLocationIfPermitted();
            createMarker();
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE); //This class provides access to the system location services. These services allow applications to obtain periodic updates of the device's geographical location
            Criteria criteria = new Criteria(); //A class indicating the application criteria for selecting a location provider.
            String provider = locationManager.getBestProvider(criteria, true); //Providers may be ordered according to accuracy, power usage, ability to report altitude, speed, bearing, and monetary cost.
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = locationManager.getLastKnownLocation(provider); //Location: A data class representing a geographic location.

            //POSICION ACTUAL                                                                    //getLastKnownLocation: Returns a Location indicating the data from the last known location fix obtained from the given provider.
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(latitude,
                        longitude));
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

                mMap.moveCamera(center);
                mMap.animateCamera(zoom);
            }


            mMap.setOnMyLocationClickListener(onMyLocationClickListener);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.setOnInfoWindowClickListener(this);


        }
  
    private void enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

    private GoogleMap.OnMyLocationClickListener onMyLocationClickListener =
            new GoogleMap.OnMyLocationClickListener() {
                @Override
                public void onMyLocationClick(@NonNull Location location) {

                    CircleOptions circleOptions = new CircleOptions();
                    circleOptions.center(new LatLng(location.getLatitude(),
                            location.getLongitude()));

                    mMap.addCircle(circleOptions);
                }
            };

    private void showDefaultLocation() {
        Toast.makeText(this, "Location permission not granted, " +
                        "showing default location",
                Toast.LENGTH_SHORT).show();
        LatLng redmond = new LatLng(-33.013368, -71.541475);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(redmond));
    }
  
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocationIfPermitted();
                } else {
                    showDefaultLocation();
                }
                return;
            }

        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity_Mobile.this);
        builder.setMessage("Seleccione tecnología a usar").setPositiveButton("Wear OS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MapsActivity_Mobile.this, "Acepto", Toast.LENGTH_SHORT).show();

            }
        }).setNegativeButton("Android Auto", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MapsActivity_Mobile.this, "NOPE", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

<<<<<<< Updated upstream
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
=======
    public void mostrarNotificacion(int id, Notification notificacion) {
        NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "my channel";
            String description = "channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(canal, name, importance);
            channel.setDescription(description);

            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            manager.createNotificationChannel(channel);
        }

        mNotificationManager.notify(id, notificacion);

>>>>>>> Stashed changes
    }
}
