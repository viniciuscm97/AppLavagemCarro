package com.example.appgerenciamentolavagens;

import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class LavagensActivity extends AppCompatActivity {

    private ListView lvLavagens;

    private List<Lavagens> lavagens;
    private DatabaseReference reference;
    private ChildEventListener childEventListener;
    private Query query;
    private ArrayAdapter<Lavagens> adapter;

    private CoordinatorLayout telaLavagens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lavagens);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fabLavagens);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LavagensActivity.this, FormLavagensActivity.class);
                startActivity( intent );
            }
        });

        lvLavagens = findViewById(R.id.lvLavagens);

        telaLavagens = findViewById(R.id.telaLavagens);

        lavagens = new ArrayList<>();
        adapter = new ArrayAdapter<Lavagens>(
                LavagensActivity.this, android.R.layout.simple_list_item_1, lavagens);
        lvLavagens.setAdapter( adapter );

        lvLavagens.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                excluirLavagem(position);
                return true;
            }
        });
    }

    private void excluirLavagem(final int position) {

        final Lavagens itemSelecionado = lavagens.get(position);
        AlertDialog.Builder alerta = new AlertDialog.Builder(LavagensActivity.this);
        alerta.setTitle("Excluir Item");
        alerta.setIcon( android.R.drawable.ic_delete );
        alerta.setMessage("Confirma a exclusão do Item de dados " + itemSelecionado.toString() + "?");
        alerta.setNeutralButton("Cancelar", null);
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                reference.child("lavagens").child( itemSelecionado.id ).removeValue();

                lavagens.remove( position );
                adapter.notifyDataSetChanged();
            }
        });
        alerta.show();
    }


    @Override
    protected void onStart() {
        super.onStart();

        lavagens.clear();

        reference = FirebaseDatabase.getInstance().getReference();
        query = reference.child("lavagens").orderByChild("data_entrega");

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Lavagens l = new Lavagens();
                l.id = dataSnapshot.getKey();
                l.data_entrega = dataSnapshot.child("data_entrega").getValue(String.class);
                l.data_cadastro = dataSnapshot.child("data_cadastro").getValue(String.class);
                l.valor = dataSnapshot.child("valor").getValue(Double.class);
                l.carro = dataSnapshot.child("carro").getValue(Carros.class);
                l.funcionario = dataSnapshot.child("funcionario").getValue(Funcionarios.class);



                lavagens.add( l );

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


