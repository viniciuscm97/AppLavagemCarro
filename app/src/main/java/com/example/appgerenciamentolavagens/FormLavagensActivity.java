package com.example.appgerenciamentolavagens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class FormLavagensActivity extends AppCompatActivity {
    private EditText etDataEntrega, etValorLavagem,etQuantidadeLavagem;
    private Button btnSalvarLavagem;
    private Spinner spFuncionarios,spCarros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_lavagens);

        etDataEntrega = findViewById(R.id.etDataEntrega);
        etValorLavagem = findViewById(R.id.etValorLavagem);
        etQuantidadeLavagem = findViewById(R.id.etQuantidadeLavagem);

        btnSalvarLavagem = findViewById(R.id.btnSalvarLavagem);


    }
}
