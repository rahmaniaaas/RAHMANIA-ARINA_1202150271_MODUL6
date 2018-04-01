package com.rahmaniaaas.popotoan;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    EditText nEmail , nPass;
    Button nDaftar;
    private FirebaseAuth nAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupUI();

        nEmail = (EditText) findViewById(R.id.email2);
        nPass = (EditText) findViewById(R.id.password2);
        nDaftar = (Button) findViewById(R.id.btnDaftar);
        nAuth = FirebaseAuth.getInstance();



        nDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    //memasukan data ke database
                    String userEmail = nEmail.getText().toString().trim();
                    String userPass= nPass.getText().toString().trim();

                    nAuth.createUserWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Registrasi Berhasil!",
                                        Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(i);
                            }else {
                                Toast.makeText(RegisterActivity.this, "Registrasi Gagal!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    private void setupUI(){
        nEmail = (EditText) findViewById(R.id.email2);
        nPass = (EditText) findViewById(R.id.password2);

    }
    private boolean validate(){
        Boolean hasil = false;

        String email = nEmail.getText().toString();
        String pass = nPass.getText().toString();

        if (email.isEmpty() && pass.isEmpty()){
            Toast.makeText(this, "Harap di isi semua", Toast.LENGTH_SHORT).show();
        }else{
            hasil = true;
        }
        return hasil;
    }
}