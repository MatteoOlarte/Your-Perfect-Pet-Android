package com.my_pet.yourperfectpet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;

import com.google.android.material.textfield.TextInputLayout;
import com.my_pet.yourperfectpet.R;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout inputUserJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setSupportActionBar(findViewById(R.id.register_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inputUserJob = findViewById(R.id.register_input_user_job_state);
        if (inputUserJob instanceof AutoCompleteTextView view) {

        }
    }
}