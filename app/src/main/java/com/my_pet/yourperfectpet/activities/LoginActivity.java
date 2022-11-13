package com.my_pet.yourperfectpet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.my_pet.yourperfectpet.App;
import com.my_pet.yourperfectpet.R;
import com.my_pet.yourperfectpet.entity.User;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private LinearProgressIndicator progressIndicator;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setSupportActionBar(findViewById(R.id.login_activity_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<EditText> fields = new ArrayList<>();
        EditText inputEmail = findViewById(R.id.login_input_user_email);
        EditText inputPassword = findViewById(R.id.login_input_user_password);
        loginButton = findViewById(R.id.login_btn_login);
        progressIndicator = findViewById(R.id.login_progress_indicator);

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
                progressIndicator.show();
                loginButton.setEnabled(false);
                loginUser(inputEmail.getText().toString(), inputPassword.getText().toString());
            }
        });
    }

    private void loginUser(final String email, final String password) {
        var intent = new Intent(this, MainActivity.class);
        var firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                progressIndicator.hide();
                intent.putExtra("user_email", email);
                startActivity(intent);
            } else {
                var errorDialog = new MaterialAlertDialogBuilder(this);
                var errorMessage = getString(R.string.msg_login_error);
                errorDialog.setTitle(R.string.text_error);
                errorDialog.setMessage(errorMessage);
                errorDialog.setCancelable(false);
                errorDialog.setPositiveButton(R.string.text_accept, (dialog, which) -> {
                    progressIndicator.hide();
                    progressIndicator.setVisibility(View.GONE);
                    loginButton.setEnabled(true);
                    dialog.dismiss();
                });
                errorDialog.show();
            }
        });
    }
}