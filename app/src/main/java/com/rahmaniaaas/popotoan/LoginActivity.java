package com.rahmaniaaas.popotoan;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    //Membuat log tag
    private static final String TAG = "LoginActivity";

    //inisialisasi variabel yang akan digunakan
    EditText mEmail , mPass;
    TextView tvDaftar;
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // merefer semua variabel yang akan digunakan
        mEmail = (EditText) findViewById(R.id.email1);
        mPass = (EditText) findViewById(R.id.password);
        tvDaftar =(TextView) findViewById(R.id.Daftar);
        mAuth = FirebaseAuth.getInstance();

        // set listener jika textView Sign Up  diklik
        tvDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);

            }
        });

        // set up Firebase
        setUpFirebaseAuth();
        init();
    }
    //Jika editText bernilai kosong
    private boolean StringNull (String string){
        if(string.equals("")){
            return false;
        }else{
            return true;
        }
    }


    //Method dimana untuk menginisialisasi
// dan menangkap nilai dari Edit Text
    private void init(){
        mEmail.setError(null);
        mPass.setError(null);

        //inisialisasi tombol login
        Button mLogin = (Button) findViewById(R.id.btnLogin);


        // Mengatur notifikasi error merah jika EditText kosong
        mEmail.setError(null);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: Mencoba untuk login");

                // menyimpan nilai email &password dalam text dan merubahnya ke String
                String email = mEmail.getText().toString();
                String password = mPass.getText().toString();

                //logika jika field email&password kosong maka muncul toast
                if (!StringNull(email) &&(!StringNull(password))){
                    Toast.makeText(LoginActivity.this, "Tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
                    // fungsi dimana saat email field tidak diisi akan muncul notifikasi error
                    mEmail.setError(getString(R.string.error_field_required));
                    mPass.setError(getString(R.string.error_field_required));
                }else{
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()){
                                        Log.d(TAG,"LoginDenganEmail: GAGAL", task.getException());
                                        Toast.makeText(LoginActivity.this,getString(R.string.auth_fail), Toast.LENGTH_SHORT).show();

                                    }else{
                                        Toast.makeText(LoginActivity.this,getString(R.string.auth_success) , Toast.LENGTH_SHORT).show();

                                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(i);
                                    }
                                }
                            });
                }
            }
        });
    }

    //Method yang disediakan Firebase untuk Aplikasi terhubung dengan Firebase
    private void setUpFirebaseAuth(){
        Log.d(TAG,"SettingFirebaseAuth : Men setting Firebase Auth");

        mAuth = FirebaseAuth.getInstance();
        mAuthListen = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    //LOG JIKA USER LOGIN DAN MENAMPILKAN USER ID NYA SESUAI DATABASE
                    Log.d(TAG,"onAuthStateChanged: Signed in" + user.getUid());
                }else {
                    Log.d(TAG,"onAuthStateChanged: Signed out");
                }

            }
        };
    }
}