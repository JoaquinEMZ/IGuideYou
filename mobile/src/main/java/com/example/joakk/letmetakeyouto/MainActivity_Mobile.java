package com.example.joakk.letmetakeyouto;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity_Mobile extends AppCompatActivity {

    private Button mBtnSearch;
    private EditText mEditProd;
    private static final  String FIRE_LOG = "Fire_log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__mobile);

        mBtnSearch = findViewById(R.id.btnSearch);
        mEditProd = findViewById(R.id.editSearch);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("Tienda").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            QuerySnapshot querySnapshot = task.getResult();
                        } else {
                            Log.d(FIRE_LOG, "Error", task.getException());
                        }
                    }
                });
            }
        });




    }

    public void searchPrd(View view) {
        Intent intent = new Intent(this, MapsActivity_Mobile.class);
        startActivity(intent);

    }
}
