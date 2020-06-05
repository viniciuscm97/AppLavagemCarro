package com.example.appgerenciamentolavagens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        if(!nome.isEmpty()){
            Funcionarios f = new Funcionarios();

            reference = FirebaseDatabase.getInstance().getReference();

            reference.child("funcionarios").push().setValue(f);

            finish();
        }
    }
}
