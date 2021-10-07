package com.example.cake_sasanka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddressConfirmActivity extends AppCompatActivity {

    //variable
    private TextView name_con, phone_con, address_con, town_con;
    Button Update_Address, Delete_Address, confirm_btn;
    private FirebaseFirestore db;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_confirm);

        //set the page title
        getSupportActionBar().setTitle("Address Confirm");
        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //hooks to all xml elements in address_confirm_activity
        name_con = findViewById(R.id.text_name);
        phone_con = findViewById(R.id.text_phone_number);
        address_con = findViewById(R.id.text_address);
        town_con = findViewById(R.id.text_town);
        Update_Address = findViewById(R.id.update_btn);
        Delete_Address = findViewById(R.id.delete_btn);
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

        db = FirebaseFirestore.getInstance();

        //call the update address method after enter click on the button
        Update_Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //path to next activity
                Intent intent = new Intent(AddressConfirmActivity.this, UpdateAddressActivity.class);

                //send the entered data to the next activity
                intent.putExtra("keyname",name);
                intent.putExtra("keyphone",phone);
                intent.putExtra("keyaddress",addresss);
                intent.putExtra("keytown",town);
                intent.putExtra("keyid",id);
                startActivity(intent);

            }
        });

        //call the delete function after click on the delete button
        Delete_Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //data delete from the database to the relevant id
                db.collection("Documents").document(id).delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(AddressConfirmActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(AddressConfirmActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddressConfirmActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                //path to go to the next activity after delete the address
                Intent intent = new Intent(AddressConfirmActivity.this, MainActivity.class);
                startActivity(intent);
            }

        });

        //set the path to go to the next activity after click the confirm button
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddressConfirmActivity.this, CardDetailsActivity.class);

                //passed the id to next activity
                intent.putExtra("keyid",id);
                startActivity(intent);
            }
        });
    }

}
