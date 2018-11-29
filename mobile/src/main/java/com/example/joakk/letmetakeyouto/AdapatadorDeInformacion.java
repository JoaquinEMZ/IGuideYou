package com.example.joakk.letmetakeyouto;

import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import android.content.Context;
import android.app.Activity;
import android.widget.TextView;

public class AdapatadorDeInformacion implements GoogleMap.InfoWindowAdapter {

    private Context context;
    TextView nombre;
    TextView direccion;

    public AdapatadorDeInformacion(Context context1){
        this.context = context1;
    }

    @Override
    public View getInfoWindow(final Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.ventana_informacion, null);
        nombre = view.findViewById(R.id.info_window_nombre);
        direccion = view.findViewById(R.id.info_window_direccion);
        nombre.setText(marker.getTitle());
        direccion.setText(marker.getSnippet());

        Tienda infoData = (Tienda)marker.getTag();

        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
