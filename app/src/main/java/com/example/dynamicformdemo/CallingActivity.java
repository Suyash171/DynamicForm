package com.example.dynamicformdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CallingActivity extends AppCompatActivity  {

    private Button btnInlfator,btnRecyler,btnFetchContact;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_activity);

        btnInlfator = findViewById(R.id.btnINflate);
        btnRecyler = findViewById(R.id.btnRecylerView);
        btnFetchContact = findViewById(R.id.btnFetchDetails);

        //btnCustomInflator = findViewById(R.id.btnCustom);

        btnRecyler.setOnClickListener(view -> {
            MainActivity.start(this,false);
        });

        btnInlfator.setOnClickListener(view -> {
            MainActivity.start(this,true);
        });

        btnFetchContact.setOnClickListener(view -> {
            GetContactDetailsActivity.start(this);
        });

    }

}
