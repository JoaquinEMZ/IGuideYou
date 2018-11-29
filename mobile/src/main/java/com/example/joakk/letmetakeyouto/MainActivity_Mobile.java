package com.example.joakk.letmetakeyouto;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.widget.Toast;
import org.w3c.dom.Document;

public class MainActivity_Mobile extends AppCompatActivity {

    String canal = "my_channel_01";
    private EditText mEditProd;
    private TextView mTxtViewShop;
    public Button mBtnProd;
    private Button notificar;

    public String data = "";

    public Button mBtnShop;
    public GeoPoint geoPoint;
    public double[] lista_puntos;
    public ArrayList<String> nombres_tiendas;
    private static final  String FIRE_LOG = "Fire_log";
    final FirebaseFirestore  db = FirebaseFirestore.getInstance();
    private CollectionReference shopReferences = db.collection("tienda");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__mobile);

        mEditProd = findViewById(R.id.etProduct);
        mBtnProd = findViewById(R.id.search_product);
        mBtnShop = findViewById(R.id.search_shop);


        final int notificationId = 1;

        mBtnProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchProduct();
            }
        });
        mBtnShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchShop();
            }
        });

        /*final int notificationId = 1;

        // Build intent for notification content
        Intent viewIntent = new Intent(this, MainActivity_Mobile.class);
        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent, 0);

        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, canal)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Notificación Simple")
                .setContentText("Esta es una notificación simple de prueba")
                .setSubText("Toque para abrir una actividad de prueba")
                // En el teléfono este intent se gatilla cuando se presiona la notificación
                // En el reloj este intent se gatilla cuando se presiona el botón "Abrir en teléfono"
                .setContentIntent(viewPendingIntent);

        notificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarNotificacion(notificationId, notificationBuilder.build());
            }

        });
        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPrd(v);
            }
        });

        });*/

    }

    @Override
    protected void onStart(){
        super.onStart();
    }


    //VERSION 1 RETIREVEDATA FUNCIONA SIN MOSTRAR EL ARRAY

    public void searchProduct (EditText editText){

        if (editText.getText().toString().trim() == "") {
            Toast.makeText(getApplicationContext(),"Ingrese lo que desee buscar", Toast.LENGTH_SHORT).show();
        }
        else{
            shopReferences.whereArrayContains("Producto", editText.getText().toString()).get().addOnCompleteListener(
                    new OnCompleteListener<QuerySnapshot>(){
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        }
                    }
            ).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }

        shopReferences.whereArrayContains("Producto", "Fideo").get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                            data += "Tienda: " + queryDocumentSnapshot.get("Nombre_tienda") + "\n" +
                            "Coordendas: " +  queryDocumentSnapshot.get("Coordenada") +"\n" ;
                        }
                        Toast.makeText(getApplicationContext(),data, Toast.LENGTH_SHORT).show();
                        mTxtViewShop.setText(data);
                    }
                }
        );

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

    public void searchShop() {
        lista_puntos = new double[24];
        nombres_tiendas = new ArrayList<>();

        if (mEditProd.getText().toString().trim().equals("")){
            Toast.makeText(MainActivity_Mobile.this, "ingrese un producto o tienda", Toast.LENGTH_SHORT).show();
        }
        else{
            shopReferences.whereEqualTo("Tipo_tienda",mEditProd.getText().toString().trim()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int contador = 0;
                        for (QueryDocumentSnapshot query : task.getResult()){
                            geoPoint = (GeoPoint) query.get("Coordenada");
                            String nombre = (String) query.get("Nombre_tienda");
                            double lat = geoPoint.getLatitude();
                            double log = geoPoint.getLongitude();
                            nombres_tiendas.add(nombre);
                            lista_puntos[contador] =  lat;
                            lista_puntos[contador+1] = log;
                            contador +=2;
                        }
                        Intent intent = new Intent(MainActivity_Mobile.this, MapsActivity_Mobile.class);
                        intent.putExtra("lista_puntos",lista_puntos);
                        intent.putExtra("nombres_tiendas", nombres_tiendas);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity_Mobile.this, "No se encuentra la tienda", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void searchProduct() {
        lista_puntos = new double[24];
        geoPoint = new GeoPoint(0,0);
        nombres_tiendas = new ArrayList<>();

        if (mEditProd.getText().toString().trim().equals("")){
            Toast.makeText(MainActivity_Mobile.this, "ingrese un producto o tienda", Toast.LENGTH_SHORT).show();
        }
        else {
            shopReferences.whereArrayContains("Producto", mEditProd.getText().toString().trim()).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            int contador = 0;
                            for (QueryDocumentSnapshot query : task.getResult()) {
                                geoPoint = (GeoPoint) query.get("Coordenada");
                                double lat = geoPoint.getLatitude();
                                double log = geoPoint.getLongitude();
                                String nombre = (String) query.get("Nombre_tienda");
                                lista_puntos[contador] = lat;
                                lista_puntos[contador + 1] = log;
                                nombres_tiendas.add(nombre);
                                contador = contador + 2;
                            }
                            Intent intent = new Intent(MainActivity_Mobile.this, MapsActivity_Mobile.class);
                            intent.putExtra("lista_puntos", lista_puntos);
                            intent.putExtra("nombres_tiendas", nombres_tiendas);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity_Mobile.this, "No se encuentra el producto", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void guiaapk(View view) {
        Intent intent = new Intent(this, GuiaApk.class);
        startActivity(intent);
    }
}







