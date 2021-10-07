package com.example.cake_sasanka;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ThankPaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_payment);

        //set the page title
        getSupportActionBar().setTitle("Successfully Done");
    }
}