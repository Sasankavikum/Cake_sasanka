package com.example.cake_sasanka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ContinuePayActivity extends AppCompatActivity {

    //variable
    private TextView name_con, phone_con, address_con, town_con;
    Button confirm_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_pay);

        //set the page title
        getSupportActionBar().setTitle("Continue Payment");

        //hooks to all xml elements in continue_pay_activity
        name_con = findViewById(R.id.text_name);
        phone_con = findViewById(R.id.text_phone_number);
        address_con = findViewById(R.id.text_address);
        town_con = findViewById(R.id.text_town);
        confirm_btn = findViewById(R.id.confirm_payment);

        //catch the data that passed from the main activity
        String name = getIntent().getStringExtra("keyname");
        String addresss = getIntent().getStringExtra("keyaddress");
        String phone = getIntent().getStringExtra("keyphone");
        String town = getIntent().getStringExtra("keytown");
        String id = getIntent().getStringExtra("keyid");

        //set the front end catch data to the back end variables
        name_con.setText(name);
        phone_con.setText(phone);
        address_con.setText(addresss);
        town_con.setText(town);

        //navigate to next activity after click the button
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContinuePayActivity.this, CardDetailsActivity.class);

                //send the id for the next activity
                intent.putExtra("keyid",id);
                startActivity(intent);
            }
        });
    }
}