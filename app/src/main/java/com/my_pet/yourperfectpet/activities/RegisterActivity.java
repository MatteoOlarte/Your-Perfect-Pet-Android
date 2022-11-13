package com.my_pet.yourperfectpet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.my_pet.yourperfectpet.App;
import com.my_pet.yourperfectpet.R;
import com.my_pet.yourperfectpet.entity.AppUser;
import com.my_pet.yourperfectpet.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private LinearProgressIndicator progressIndicator;
    private Button registerButton;

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
        List<String> jobItems = Arrays.asList(getResources().getStringArray(R.array.jobs));
        List<String> petItems = Arrays.asList(getResources().getStringArray(R.array.pets));
        progressIndicator = findViewById(R.id.register_progress_indicator);
        registerButton = findViewById(R.id.register_btn_register);

        fields.add(inputFirstName);
        fields.add(inputLastName);
        fields.add(inputEmail);
        fields.add(inputPassword);
        fields.add(inputUserJob);
        fields.add(inputFavoritePet);

        inputUserJob.setAdapter(new ArrayAdapter<String>(this, R.layout.list_layout, jobItems));
        inputFavoritePet.setAdapter(new ArrayAdapter<String>(this, R.layout.list_layout, petItems));
        registerButton.setOnClickListener(view -> {

            if (!App.emptyFields(fields)) {
                Toast.makeText(this, R.string.msg_empty_fields, Toast.LENGTH_SHORT).show();
            } else if (!App.verifyEmailAddress(inputEmail.getText().toString())) {
                TextInputLayout textInputLayout = (TextInputLayout) inputEmail.getParent().getParent();
                textInputLayout.setErrorEnabled(true);
                textInputLayout.setError(getString(R.string.msg_invalid_email));
            } else {
                AppUser user = new AppUser(
                        inputEmail.getText().toString(),
                        inputFirstName.getText().toString(),
                        inputLastName.getText().toString(),
                        inputUserJob.getText().toString(),
                        inputFavoritePet.getText().toString()
                );
                progressIndicator.show();
                registerButton.setEnabled(false);
                registerUser(user, inputPassword.getText().toString());
            }
        });
    }

    private void registerUser(final AppUser user, final String password) {
        var intent = new Intent(this, MainActivity.class);
        var firebaseAuth = FirebaseAuth.getInstance();
        var firebaseStorage = FirebaseFirestore.getInstance();

        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                firebaseStorage.collection("users").document(user.getEmail()).set(user).addOnFailureListener(command -> {
                    var errorDialog = new MaterialAlertDialogBuilder(this);
                    errorDialog.setTitle(R.string.text_error);
                    errorDialog.setMessage(command.getMessage());
                    errorDialog.setCancelable(false);
                    errorDialog.setPositiveButton(R.string.text_accept, (dialog, which) -> {
                        dialog.dismiss();
                    });
                }).addOnSuccessListener(command -> {
                    progressIndicator.hide();
                    intent.putExtra("user_email", user.getEmail());
                    startActivity(intent);
                });
            } else {
                var errorDialog = new MaterialAlertDialogBuilder(this);
                errorDialog.setTitle(R.string.text_error);
                errorDialog.setMessage(task.getException().getMessage());
                errorDialog.setCancelable(false);
                errorDialog.setPositiveButton(R.string.text_accept, (dialog, which) -> {
                    progressIndicator.hide();
                    progressIndicator.setVisibility(View.GONE);
                    registerButton.setEnabled(true);
                    dialog.dismiss();
                });
                errorDialog.show();
            }
        });
    }
}