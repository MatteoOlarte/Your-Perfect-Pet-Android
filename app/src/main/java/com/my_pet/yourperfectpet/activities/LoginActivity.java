package com.my_pet.yourperfectpet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.my_pet.yourperfectpet.App;
import com.my_pet.yourperfectpet.R;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setSupportActionBar(findViewById(R.id.login_activity_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<EditText> fields = new ArrayList<>();
        EditText inputEmail = findViewById(R.id.login_input_user_email);
        EditText inputPassword = findViewById(R.id.login_input_user_password);
        Button loginButton = findViewById(R.id.login_btn_login);

        fields.add(inputEmail);
        fields.add(inputPassword);

        loginButton.setOnClickListener(view -> {
            var intent = new Intent(this, MainActivity.class);

            if (!App.emptyFields(fields)) {
                Toast.makeText(this, R.string.msg_empty_fields, Toast.LENGTH_SHORT).show();
            } else if (!App.verifyEmailAddress(inputEmail.getText().toString())) {
                TextInputLayout textInputLayout = (TextInputLayout) inputEmail.getParent().getParent();
                textInputLayout.setErrorEnabled(true);
                textInputLayout.setError(getString(R.string.msg_invalid_email));
            } else {
                startActivity(intent);
            }
        });
    }
}