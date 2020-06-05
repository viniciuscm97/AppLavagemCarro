package com.example.appgerenciamentolavagens;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CarrosActivity extends AppCompatActivity {

    private ListView lvCarros;

    private List<Carros> carros;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ChildEventListener childEventListener;
    private Query query;
    private ArrayAdapter<Carros> adapter;

    private CoordinatorLayout telaCarros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carros);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fabCarros);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CarrosActivity.this, FormCarrosActivity.class);
                startActivity( intent );
            }
        });

        lvCarros = findViewById(R.id.lvCarros);

        telaCarros = findViewById(R.id.telaCarros);

        carros = new ArrayList<>();
        adapter = new ArrayAdapter<Carros>(
                CarrosActivity.this, android.R.layout.simple_list_item_1, carros);
        lvCarros.setAdapter( adapter );
    }


    @Override
    protected void onStart() {
        super.onStart();

        carros.clear();

        reference = FirebaseDatabase.getInstance().getReference();
        query = reference.child("carros").orderByChild("data_cadastro");

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Carros c = new Carros();
                c.id = dataSnapshot.getKey();
                c.data_cadastro = dataSnapshot.child("data_cadastro").getValue(String.class);
                c.placa = dataSnapshot.child("placa").getValue(String.class);
                c.modelo = dataSnapshot.child("modelo").getValue(String.class);
                c.marca = dataSnapshot.child("marca").getValue(String.class);
                c.portas = dataSnapshot.child("portas").getValue(String.class);



                carros.add( c );

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        query.addChildEventListener( childEventListener );

    }

    @Override
    protected void onStop() {
        super.onStop();

        query.removeEventListener( childEventListener );
    }

}
