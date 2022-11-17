package com.my_pet.yourperfectpet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.my_pet.yourperfectpet.App;
import com.my_pet.yourperfectpet.R;
import com.my_pet.yourperfectpet.adapters.TextWatcherAdapter;
import com.my_pet.yourperfectpet.entity.AppUser;
import com.my_pet.yourperfectpet.entity.BasicUser;
import com.my_pet.yourperfectpet.util.JobState;
import com.my_pet.yourperfectpet.util.NickName;
import com.my_pet.yourperfectpet.util.Pets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private LinearProgressIndicator progressIndicator;
    private Button registerButton;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setSupportActionBar(findViewById(R.id.register_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Main Variables
        ArrayList<EditText> fields = new ArrayList<>();
        EditText inputFirstName = findViewById(R.id.register_input_user_fist_name);
        EditText inputLastName = findViewById(R.id.register_input_user_last_name);
        EditText inputNickName = findViewById(R.id.register_input_user_nick_name);
        EditText inputEmail = findViewById(R.id.register_input_user_email);
        EditText inputPassword = findViewById(R.id.register_input_user_password);
        AutoCompleteTextView inputUserJob = findViewById(R.id.register_input_user_job_state);
        AutoCompleteTextView inputFavoritePet = findViewById(R.id.register_input_user_pet_preferences);
        ImageView profilePicture = findViewById(R.id.register_activity_profile_picture);
        List<String> jobItems = Arrays.asList(getResources().getStringArray(R.array.jobs));
        List<String> petItems = Arrays.asList(getResources().getStringArray(R.array.pets));
        TextWatcher nameChanged = new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                final String firstName = inputFirstName.getText().toString();
                final String lastName = inputLastName.getText().toString();

                inputNickName.setText(new NickName(firstName, lastName).modifyLastName().modifyFirstName().getFullName());
            }
        };

        progressIndicator = findViewById(R.id.register_progress_indicator);
        registerButton = findViewById(R.id.register_btn_register);

        fields.add(inputFirstName);
        fields.add(inputLastName);
        fields.add(inputEmail);
        fields.add(inputPassword);
        fields.add(inputUserJob);
        fields.add(inputFavoritePet);

        inputFirstName.addTextChangedListener(nameChanged);
        inputLastName.addTextChangedListener(nameChanged);
        inputUserJob.setAdapter(new ArrayAdapter<>(this, R.layout.list_layout, jobItems));
        inputFavoritePet.setAdapter(new ArrayAdapter<>(this, R.layout.list_layout, petItems));

        //Register User
        registerButton.setOnClickListener(view -> {

            if (!App.notEmptyFields(fields)) {
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
                        (JobState) JobState.of(this, inputUserJob.getText().toString()),
                        Pets.of(this, inputFavoritePet.getText().toString())
                );

                user.setNickName(inputNickName.getText().toString());
                progressIndicator.show();
                registerButton.setEnabled(false);
                registerUser(user, inputPassword.getText().toString());
            }
        });
    }

    private void registerUser(final BasicUser user, final String password) {
        var intent = new Intent(this, MainActivity.class);
        var auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(user.getEmail(), password).addOnSuccessListener(task -> {
            task.getUser().updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(user.getUserName()).build()).addOnSuccessListener(task1 -> {
                db.collection("users").document(task.getUser().getUid()).set(user).addOnSuccessListener(task2 -> {
                    progressIndicator.hide();
                    progressIndicator.setVisibility(View.GONE);
                    registerButton.setEnabled(true);
                    startActivity(intent);
                });
            });
        }).addOnFailureListener(exception -> {
            var errorDialog = new MaterialAlertDialogBuilder(this);
            errorDialog.setTitle(R.string.text_error);
            errorDialog.setMessage(exception.getMessage());
            errorDialog.setCancelable(false);
            errorDialog.setPositiveButton(R.string.text_accept, (dialog, which) -> {
                progressIndicator.hide();
                progressIndicator.setVisibility(View.GONE);
                registerButton.setEnabled(true);
                dialog.dismiss();
            });
            errorDialog.show();
        });

    }
}