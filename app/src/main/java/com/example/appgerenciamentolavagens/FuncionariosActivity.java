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

public class FuncionariosActivity extends AppCompatActivity {

    private ListView lvFuncionarios;

    private List<Funcionarios> funcionarios;
    private DatabaseReference reference;
    private ChildEventListener childEventListener;
    private Query query;
    private ArrayAdapter<Funcionarios> adapter;

    private CoordinatorLayout telaFuncionarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionarios);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fabFuncionarios);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FuncionariosActivity.this, FormFuncActivity.class);
                startActivity( intent );
            }
        });

        lvFuncionarios = findViewById(R.id.lvFuncionarios);
        telaFuncionarios = findViewById(R.id.telaFuncionarios);

        funcionarios = new ArrayList<>();
        adapter = new ArrayAdapter<Funcionarios>(
                FuncionariosActivity.this, android.R.layout.simple_list_item_1, funcionarios);
        lvFuncionarios.setAdapter( adapter );


    }

    @Override
    protected void onStart() {
        super.onStart();

        reference = FirebaseDatabase.getInstance().getReference();
        query = reference.child("funcionarios").orderByChild("nome");

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Funcionarios f = new Funcionarios();
                f.id = dataSnapshot.getKey();
                f.nome = dataSnapshot.child("nome").getValue(String.class);

                funcionarios.add( f );

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
