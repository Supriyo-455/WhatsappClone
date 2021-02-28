package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class otp_screen extends AppCompatActivity {

    private EditText otpView;
    private Button btnVerify, btnResendOtp;
    private String otp;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_screen);

        mAuth = FirebaseAuth.getInstance();

        otpView = findViewById(R.id.otpView);
        btnVerify = findViewById(R.id.btnContinue);
        btnResendOtp = findViewById(R.id.otpResend);

        otp = getIntent().getStringExtra("otp");

        btnVerify.setOnClickListener(v -> {
            String verification_code = otpView.getText().toString();
            if(!verification_code.isEmpty()){
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otp,verification_code);
                signIn(credential);
            }else{
                Toast.makeText(otp_screen.this, "Please enter the otp", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void signIn(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(otp_screen.this, "Signed In", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(otp_screen.this,setUpProfileActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }else{
                    Toast.makeText(otp_screen.this, "Sign In failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}