package com.example.appgerenciamentolavagens;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class FormLavagensActivity extends AppCompatActivity {
    private EditText etDataEntrega, etValorLavagem,etQuantidadeLavagem;
    private Button btnSalvarLavagem;
    private Spinner spFuncionarios,spCarros;

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ChildEventListener childEventListener;
    private Query query;

    private List<Carros> carros;
    private ArrayAdapter<Carros> adapterCarros;

    private List<Funcionarios> funcionarios;
    private ArrayAdapter<Funcionarios> adapterFuncionarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_lavagens);

        etDataEntrega = findViewById(R.id.etDataEntrega);
        etValorLavagem = findViewById(R.id.etValorLavagem);

        spFuncionarios = findViewById(R.id.spFuncionarios);
        spCarros = findViewById(R.id.spCarros);

        carros = new ArrayList<>();
        adapterCarros = new ArrayAdapter<Carros>(
                FormLavagensActivity.this, android.R.layout.simple_spinner_item, carros);

        spCarros.setAdapter(adapterCarros);

//        adapterFuncionarios = new ArrayAdapter<Funcionarios>(
//                FormLavagensActivity.this, android.R.layout.simple_spinner_dropdown_item, funcionarios);
//
//        spCarros.setAdapter(adapterFuncionarios);




//        btnSalvarLavagem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                salvarLavagem();
//            }
//        });


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

                adapterCarros.notifyDataSetChanged();
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

    private void salvarLavagem() {

    }
}
