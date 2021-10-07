package com.example.cake_sasanka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    //variable
    private EditText name_input, phone_number_input, address_input, town_input;
    private Button Button_Save;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set the page title
        getSupportActionBar().setTitle("Address");
        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //hooks to all xml elements in address_activity
        name_input = (EditText) findViewById(R.id.name_input);
        phone_number_input = (EditText) findViewById(R.id.phone_number_input);
        address_input = (EditText) findViewById(R.id.address_input);
        town_input = (EditText) findViewById(R.id.town_input);
        Button_Save = (Button) findViewById(R.id.save_address_btn);

        db = FirebaseFirestore.getInstance();

        //save data in firebase on button click
        Button_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InsertAddressData();
            }
        });
    }

    //insert method
    private void InsertAddressData(){

        //assigned the entered data in the front end to back end variables
        String name = name_input.getText().toString();
        String addresss = address_input.getText().toString();
        String phone = phone_number_input.getText().toString();
        String town = town_input.getText().toString();

        //assign a random generate ID for the document table in database
        String id = UUID.randomUUID().toString();

        //check the name is empty
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please write your name..",Toast.LENGTH_SHORT);
        }
        //check the phone number is empty
        else if(TextUtils.isEmpty(phone)){
            Toast.makeText(this,"Please write your phone number..",Toast.LENGTH_SHORT);
        }
        //validate the phone number to 10 numbers
        else if(!phone_number_input.getText().toString().matches("[0-9]{10}")) {
            phone_number_input.setError("Enter valid phone Number");
        }
        //check the address is empty
        else if(TextUtils.isEmpty(addresss)){
            Toast.makeText(this,"Please enter your address..",Toast.LENGTH_SHORT);
        }
        //check the town is empty
        else if(TextUtils.isEmpty(town)){
            Toast.makeText(this,"Please enter your town..",Toast.LENGTH_SHORT);
        }
        else {
            //store the entered data to a hashmap to store in database
            HashMap<String, Object> map =new HashMap<>();
            map.put("id",id);
            map.put("address",addresss);
            map.put("phone",phone);
            map.put("town",town);
            map.put("name",name);

            //store in documents under the random generated id
            db.collection("Documents").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Data saved", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });

            //path to the next activity
            Intent intent = new Intent(MainActivity.this, AddressConfirmActivity.class);

            //send the data to next activity
            intent.putExtra("keyname",name);
            intent.putExtra("keyphone",phone);
            intent.putExtra("keyaddress",addresss);
            intent.putExtra("keytown",town);
            intent.putExtra("keyid",id);
            intent.putExtra("keyTotal",1500);
            startActivity(intent);
        }
    }
}