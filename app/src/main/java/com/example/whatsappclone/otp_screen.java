package com.example.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.accessibility.AccessibilityRecord;

import com.example.whatsappclone.databinding.ActivityOtpScreenBinding;

public class otp_screen extends AppCompatActivity {
    ActivityOtpScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        binding.phoneLabel.setText("Verify "+phoneNumber);
    }
}