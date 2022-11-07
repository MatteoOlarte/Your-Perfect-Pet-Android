package com.my_pet.yourperfectpet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.my_pet.yourperfectpet.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setSupportActionBar(findViewById(R.id.login_activity_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}