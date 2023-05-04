package com.example.newhotelapp.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.newhotelapp.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        b1 = findViewById(R.id.resetPassMail);

        b1.setOnClickListener(v -> {
            startActivity(new Intent(ForgotPasswordActivity.this, EmailResetActivity.class));
        });

    }
}