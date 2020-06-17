package com.example.appgerenciamentolavagens;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FormLavagensActivity extends AppCompatActivity {
    private EditText etDataEntrega, etValorLavagem,etQuantidadeLavagem;
    private Button btnSalvarLavagem;
    private Spinner spFuncionarios,spCarros;

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ChildEventListener childEventListenerCarros;
    private ChildEventListener childEventListenerFuncionarios;
    private Query queryCarros;
    private Query queryFuncionarios;

    private List<Carros> carros;
    private ArrayAdapter<Carros> adapterCarros;

    private List<Funcionarios> funcionarios;
    private ArrayAdapter<Funcionarios> adapterFuncionarios;

    private Carros carroSelecionado;
    private Funcionarios funcionarioSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_lavagens);

        etDataEntrega = findViewById(R.id.etDataEntrega);
        etValorLavagem = findViewById(R.id.etValorLavagem);

        spFuncionarios = findViewById(R.id.spFuncionarios);
        spCarros = findViewById(R.id.spCarros);

        btnSalvarLavagem= findViewById(R.id.btnSalvarLavagem);

        carros = new ArrayList<>();
        adapterCarros = new ArrayAdapter<Carros>(
                FormLavagensActivity.this, android.R.layout.simple_spinner_item, carros);
        spCarros.setAdapter(adapterCarros);

        funcionarios = new ArrayList<>();
        adapterFuncionarios = new ArrayAdapter<Funcionarios>(
                FormLavagensActivity.this, android.R.layout.simple_spinner_item, funcionarios);

        spFuncionarios.setAdapter(adapterFuncionarios);

        btnSalvarLavagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarLavagem();
            }
        });

        spCarros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final Carros carroSpinner = carros.get(position);
                carroSelecionado = carroSpinner;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                carroSelecionado = null;
            }
        });

        spFuncionarios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final Funcionarios funcioSpinner = funcionarios.get(position);
                funcionarioSelecionado = funcioSpinner;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                funcionarioSelecionado= null;

            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();

        carros.clear();

        reference = FirebaseDatabase.getInstance().getReference();
        queryCarros = reference.child("carros").orderByChild("data_cadastro");

        childEventListenerCarros = new ChildEventListener() {
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

        queryFuncionarios = reference.child("funcionarios").orderByChild("nome");

        childEventListenerFuncionarios = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Funcionarios f = new Funcionarios();
                f.id = dataSnapshot.getKey();
                f.nome = dataSnapshot.child("nome").getValue(String.class);
                f.data_cadastro = dataSnapshot.child("data_cadastro").getValue(String.class);

                funcionarios.add(f);

                adapterFuncionarios.notifyDataSetChanged();

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

        queryCarros.addChildEventListener( childEventListenerCarros );
        queryFuncionarios.addChildEventListener(childEventListenerFuncionarios);
    }

    @Override
    protected void onStop() {
        super.onStop();
        queryCarros.removeEventListener( childEventListenerCarros );
        queryFuncionarios.removeEventListener( childEventListenerFuncionarios );
    }

    private void salvarLavagem() {

        String valor = etValorLavagem.getText().toString();
        String dataEntrega = etDataEntrega.getText().toString();

        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
        Date data = new Date();
        String data_cadastro = formataData.format(data);


        if (!valor.isEmpty()){
            valor = valor.replace(",", ".");
            double preco = Double.valueOf( valor );

            Lavagens l = new Lavagens();
            l.valor = preco;
            l.data_cadastro = data_cadastro;
            l.data_entrega = dataEntrega;
            l.carro = carroSelecionado;
            l.funcionario = funcionarioSelecionado;

            reference = FirebaseDatabase.getInstance().getReference();
            reference.child("lavagens").push().setValue( l );

            finish();

        }

    }
}
