package com.example.whatsappclone;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class PhoneNumberActivity extends AppCompatActivity {

    //Declaring the variables
    private EditText phoneNumber;
    private Button btnContinue;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mCode;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);

        getSupportActionBar().hide();

        //Connecting the xml buttons, edit text and text views with java
        phoneNumber = findViewById(R.id.phoneNum);
        btnContinue = findViewById(R.id.btnContinue);

        //Getting the instance of the firebase auth
        mAuth = FirebaseAuth.getInstance();

        //Setting the mCallbacks for phone number verification
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signIn(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(PhoneNumberActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                mCode = s;
                mResendToken = forceResendingToken;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(PhoneNumberActivity.this,otp_screen.class);
                        intent.putExtra("otp",mCode);
                        startActivity(intent);
                        finish();
                    }
                }, 1000);

            }
        };

        //Setting the onclick listener
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sPhoneNumber = phoneNumber.getText().toString();
                if(!validatePhoneNum(sPhoneNumber)){
                    return;
                }else {
                    startPhoneVerification(sPhoneNumber);
                }
            }
        });
    }

    private void startPhoneVerification(String sPhoneNumber) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(sPhoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .setForceResendingToken(mResendToken)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private boolean validatePhoneNum(String phoneNum){
        if(TextUtils.isEmpty(phoneNum)){
            Toast.makeText(this, "Please enter a valid phone number!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            sendToMain();
        }
    }

    private void sendToMain() {
        Intent mainIntent = new Intent(PhoneNumberActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    private void signIn(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(PhoneNumberActivity.this, "Signed In", Toast.LENGTH_SHORT).show();
                    sendToMain();
                }else{
                    Toast.makeText(PhoneNumberActivity.this, "Sign In failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}