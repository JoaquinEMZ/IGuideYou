package com.example.joakk.letmetakeyouto;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

public class MainActivity_Mobile extends AppCompatActivity {

    String canal = "my_channel_01";

    private EditText mEditProd;
    private TextView mTxtViewShop;
    private Button mBtnSearch;
    private static final  String FIRE_LOG = "Fire_log";
    final FirebaseFirestore  db = FirebaseFirestore.getInstance();
    private CollectionReference shopReferences = db.collection("tienda");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__mobile);
        mTxtViewShop = findViewById(R.id.txtViewShop);
        mEditProd = findViewById(R.id.etProduct);
        mBtnSearch = findViewById(R.id.btnSearch);
    }

    @Override
    protected void onStart(){
        super.onStart();
        /*shopReferences.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if(e != null){
                    return;
                }
                String data ="";
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Tienda tienda = documentSnapshot.toObject(Tienda.class);
                    tienda.setDOCUMENTID(documentSnapshot.getId());
                    tienda.setNOMBRE_TIENDA(documentSnapshot.getString("Nombre_tienda"));
                    tienda.setDIRECCION(documentSnapshot.getString("Direccion"));
                    tienda.setCOORDENADAS(documentSnapshot.getGeoPoint("Coordenadas"));
                    tienda.setTIPO_TIENDA(documentSnapshot.getString("Tipo_tienda"));


                    String docId = tienda.getDOCUMENTID();
                    String nombre = tienda.getNOMBRE_TIENDA();
                    String direccion = tienda.getDIRECCION();


                    data+= "ID: " + docId + "\nNombre de la Tienda " + nombre + "\nDirección " + direccion + "\n\n";

                }

                mTxtViewShop.setText(data);
            }
        });*/
    }

    public void searchPrd(View view) {
        Intent intent = new Intent(this, MapsActivity_Mobile.class);
        startActivity(intent);
    }

    //VERSION 2
    /*public void retrieveDoc (View v) {
        mBtnSearch.setEnabled(true);
        shopReferences.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                            Tienda tienda = queryDocumentSnapshot.toObject(Tienda.class);
                            if(tienda.getPRODUCTOS().contains(queryDocumentSnapshot.get("Producto"))){

                            }
                        }
                    }
                });
    }*/




    //VERSION 1 RETIREVEDATA FUNCIONA SIN MOSTRAR EL ARRAY
    public void retrieveDoc (View v){
        mBtnSearch.setEnabled(true);
        shopReferences.get()
        //shopReferences.whereArrayContains("Productos", mEditProd.toString()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";
                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Tienda tienda = documentSnapshot.toObject(Tienda.class);
                            tienda.setDOCUMENTID(documentSnapshot.getId());
                            tienda.setNOMBRE_TIENDA(documentSnapshot.getString("Nombre_tienda"));
                            tienda.setDIRECCION(documentSnapshot.getString("Direccion"));
                            tienda.setCOORDENADAS(documentSnapshot.getGeoPoint("Coordenadas"));
                            tienda.setTIPO_TIENDA(documentSnapshot.getString("Tipo_tienda"));


                            String docId = tienda.getDOCUMENTID();
                            String nombre = tienda.getNOMBRE_TIENDA();
                            String direccion = tienda.getDIRECCION();


                            data+= "ID: " + docId + "\nNombre de la tienda " + nombre + "\nDirección " + direccion + "\n\n";

                            /*for(String productos : ){ //NO ME MUESTRA LOS PRODUCTOS Y SE CAE LA APLICACION
                                data+= "\n -" + productos;
                            }*/
                            data+="\n\n";
                        }
                        mTxtViewShop.setText(data);
                    }
                });
    }

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

    }

    public void searchPrdReloj(View view) {
        int notificationId = 3;

        Intent actionIntent = new Intent(this, MainActivity_Mobile.class);
        PendingIntent actionPendingIntent =
                PendingIntent.getActivity(this, 0, actionIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        Uri geoUri = Uri.parse("geo:0,0?q=" + Uri.encode("Valparaíso"));
        mapIntent.setData(geoUri);
        PendingIntent mapPendingIntent =
                PendingIntent.getActivity(this, 0, mapIntent, 0);

        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.ic_map_white,"Test", actionPendingIntent)
                        .build();



// Build the notification and add the action via WearableExtender
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, canal)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Notificación con Mapa")
                        .setContentText("Esta es una notificación con Mapa de prueba")
                        .setSubText("Toque la notificación para abrir una actividad de prueba o " +
                                "toque el botón VER MAPA para abrir Google Maps")
                        .setContentIntent(actionPendingIntent)
                        // En el teléfono aparecerá un botón en la notificación y en el reloj
                        // aparecerá un botón grande al presionar la notificación
                        //.addAction(R.drawable.ic_map_white, "Ver mapa", mapPendingIntent)
                        .extend(new NotificationCompat.WearableExtender().addAction(action));

        mostrarNotificacion(notificationId, notificationBuilder.build());

  

    }
}







