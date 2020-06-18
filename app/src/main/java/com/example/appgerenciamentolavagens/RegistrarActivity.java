package com.example.appgerenciamentolavagens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrarActivity extends AppCompatActivity {
    private EditText rEditEmail;
    private EditText rEditPassword;
    private Button rEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        rEditEmail = findViewById(R.id.editEmail);
        rEditPassword = findViewById(R.id.editPassword);
        rEnter = findViewById(R.id.btnLogin);

        rEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });


    }

    private void createUser(){
        String email = rEditEmail.getText().toString();
        String senha = rEditPassword.getText().toString();
        if(senha == null || senha.isEmpty() || email.isEmpty() || email == null){
            Log.i("Testefeito", "FALTA EMAIL E/OU SENHA");
            return;
        }else{

            Intent intent = new Intent( RegistrarActivity.this, MainActivity.class);
            startActivity(intent);
        }
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.i("teste", task.getResult().getUser().getUid());
                    Intent intent = new Intent(RegistrarActivity.this, MainActivity.class);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("teste", e.getMessage());
            }
        });
    }

}
