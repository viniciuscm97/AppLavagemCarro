package com.example.appgerenciamentolavagens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormCarrosActivity extends AppCompatActivity {
    private EditText etPlaca,etModelo,etAno,etPortas,etMarca;
    private Button btnSalvarCarro;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_carros);

        etPlaca = findViewById(R.id.etPlaca);
        etModelo = findViewById(R.id.etModelo);
        etAno = findViewById(R.id.etAno);
        etPortas = findViewById(R.id.etPortas);
        etMarca = findViewById(R.id.etMarca);

        btnSalvarCarro = findViewById(R.id.btnSalvarCarro);

        btnSalvarCarro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarCarro();

            }
        });

    }

    private void salvarCarro() {
        String placa = etPlaca.getText().toString();
        String modelo = etModelo.getText().toString();
        String ano = etAno.getText().toString();
        String portas = etPortas.getText().toString();
        String marca = etMarca.getText().toString();

        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
        Date data = new Date();
        String data_cadastro = formataData.format(data);


        if (!placa.isEmpty()&&!modelo.isEmpty()&&!ano.isEmpty()&&!portas.isEmpty()&&!marca.isEmpty()){
            placa = placa.replace("-", "");
            Carros c = new Carros();

            c.placa = placa;
            c.modelo = modelo;
            c.ano = ano;
            c.portas = portas;
            c.marca = marca;
            c.data_cadastro = data_cadastro;

            reference = FirebaseDatabase.getInstance().getReference();
            reference.child("carros").push().setValue( c );

            finish();

        }

    }
}
