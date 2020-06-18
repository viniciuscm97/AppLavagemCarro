package com.example.appgerenciamentolavagens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaSession2Service;
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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnRegistrar;
    private EditText etSenha;
    private EditText etLogin;

    //Conexão com o banco
    private FirebaseAuth auth;
    private FirebaseUser usuario;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnLogin = findViewById(R.id.btnLogin);
        etSenha = findViewById(R.id.etSenha);
        etLogin = findViewById(R.id.etLogin);


        //Instanciando banco

        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                usuario = auth.getCurrentUser();
                if(usuario != null){
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    startActivity(intent);
                }else{
                    Log.i("Conexão de usuário criado", "Erro");
                }
            }
        };

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etLogin.getText().toString();
                String senha = etSenha.getText().toString();

                Log.i("Teste", "senha");
                Log.i("Teste","email");

                if(email == null || email.isEmpty() || senha.isEmpty() || senha == null){
                    Log.i("Login","Email ou senha incorretos");
                    openDialog();
                    return;
                }
                    FirebaseAuth.getInstance().signInWithEmailAndPassword( email , senha )
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.i("maisumteste", task.getResult().getUser().getUid());
                                        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    } else {
                                        openDialog();
                                    }
                                }


                            /*.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.i("Teste conexão falou", e.getMessage());
                                    //Adicionar pop up avisando o erro
                                    //openDialog();
                                    openDialog();
                                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                    startActivity(intent);
                        }*/
                    });

            }
        });



        //Login sem usuário
        /*btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity( intent );
            }
        });*/

        //Registrar
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrarActivity.class);
                startActivity(intent);
            }
        });

    }
    public void openDialog(){
        Dialog dialog = new Dialog();
        dialog.show(getSupportFragmentManager(), "TESTE");
    }
}
