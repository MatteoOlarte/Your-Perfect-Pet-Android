package com.my_pet.yourperfectpet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.my_pet.yourperfectpet.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setSupportActionBar(findViewById(R.id.register_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<EditText> fields = new ArrayList<>();
        EditText inputFirstName = findViewById(R.id.register_input_user_fist_name);
        EditText inputLastName = findViewById(R.id.register_input_user_last_name);
        EditText inputEmail = findViewById(R.id.register_input_user_email);
        EditText inputPassword = findViewById(R.id.register_input_user_password);
        AutoCompleteTextView inputUserJob = findViewById(R.id.register_input_user_job_state);
        AutoCompleteTextView inputFavoritePet = findViewById(R.id.register_input_user_pet_preferences);
        Button registerButton = findViewById(R.id.register_btn_register);
        List<String> jobItems = Arrays.asList(getResources().getStringArray(R.array.jobs));
        List<String> petItems = Arrays.asList(getResources().getStringArray(R.array.pets));

        fields.add(inputFirstName);
        fields.add(inputLastName);
        fields.add(inputEmail);
        fields.add(inputPassword);
        fields.add(inputUserJob);
        fields.add(inputFavoritePet);

        inputUserJob.setAdapter(new ArrayAdapter<String>(this, R.layout.list_layout, jobItems));
        inputFavoritePet.setAdapter(new ArrayAdapter<String>(this, R.layout.list_layout, petItems));
        registerButton.setOnClickListener(view -> {
            var intent = new Intent(this, CreatePetActivity.class);

            if (!emptyFields(fields)) {
                Toast.makeText(this, R.string.msg_empty_fields, Toast.LENGTH_SHORT).show();
            } else if (!verifyEmailAddress(inputEmail.getText().toString())) {
                TextInputLayout textInputLayout = (TextInputLayout) inputEmail.getParent().getParent();
                textInputLayout.setErrorEnabled(true);
                textInputLayout.setError(getString(R.string.msg_invalid_email));
            } else {
                startActivity(intent);
            }

        });
    }

    public static boolean emptyFields(ArrayList<EditText> fields) {
        for (EditText editText : fields) {
            if (editText.getText().toString().isEmpty())
                return false;
        }
        return true;
    }

    public static boolean verifyEmailAddress(String email) {
        int atCounter = 0;
        int dotCounter = 0;

        for (char i : email.toCharArray()) {
            if (i == '@')
                atCounter++;
            if (i == '.')
                dotCounter++;
        }

        if (atCounter != 1)
            return false;

        return dotCounter >= 1;
    }
}