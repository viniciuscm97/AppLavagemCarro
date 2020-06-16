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

public class FormFuncActivity extends AppCompatActivity {

    private EditText etNome;
    private Button btnSalvarFuncionario;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_funcionarios);

        etNome = findViewById(R.id.etNomeFuncionario);
        btnSalvarFuncionario = findViewById(R.id.btnSalvarFunciaonario);

        btnSalvarFuncionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarFuncionario();
            }
        });


    }

    private void salvarFuncionario() {
        String nome = etNome.getText().toString();

        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
        Date data = new Date();
        String data_cadastro = formataData.format(data);

        if(!nome.isEmpty()){
            Funcionarios f = new Funcionarios();

            f.nome = nome;
            f.data_cadastro = data_cadastro;

            reference = FirebaseDatabase.getInstance().getReference();

            reference.child("funcionarios").push().setValue(f);

            finish();
        }
    }
}
