package com.example.appgerenciamentolavagens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button btnFuncionarios, btnLavagens,btnCarros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnFuncionarios = findViewById(R.id.btnFuncionarios);
        btnLavagens = findViewById(R.id.btnLavagens);
        btnCarros = findViewById(R.id.btnCarros);

        btnLavagens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, LavagensActivity.class);
                startActivity( intent );
            }
        });
        btnFuncionarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, FuncionariosActivity.class);
                startActivity( intent );
            }
        });
        btnCarros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, CarrosActivity.class);
                startActivity( intent );
            }
        });
    }
}
