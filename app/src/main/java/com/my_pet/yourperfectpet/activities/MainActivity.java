package com.my_pet.yourperfectpet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.my_pet.yourperfectpet.R;
import com.my_pet.yourperfectpet.entity.AppUser;
import com.my_pet.yourperfectpet.entity.BasicUser;
import com.my_pet.yourperfectpet.fragments.MainFragment;
import com.my_pet.yourperfectpet.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.main_activity_toolbar));

        final int frameLayoutID = R.id.main_activity_frame_layout;
        BottomNavigationView navigationBar = findViewById(R.id.main_activity_bottom_navigation);
        Fragment fragment = new MainFragment();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        LinearProgressIndicator progressIndicator = findViewById(R.id.main_activity_progress_indicator);

        Toast.makeText(this, FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl() +"", Toast.LENGTH_LONG).show();

        getSupportFragmentManager().beginTransaction().replace(frameLayoutID, fragment).commit();
        navigationBar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case (R.id.navigation_action_pets) -> {
                    Fragment profile = new MainFragment();
                    getSupportFragmentManager().beginTransaction().replace(frameLayoutID, profile).commit();
                    getSupportActionBar().setTitle("Pets");
                    return true;
                }
                case (R.id.navigation_action_profile) -> {
                    progressIndicator.show();

                    if (firebaseAuth.getCurrentUser() != null) {
                        String id = firebaseAuth.getCurrentUser().getUid();
                        db.collection("users").document(id).get()
                                .addOnSuccessListener(documentSnapshot ->  {
                                    BasicUser user = documentSnapshot.toObject(AppUser.class);
                                    Fragment profile = new ProfileFragment(user);

                                    progressIndicator.hide();
                                    progressIndicator.setVisibility(View.GONE);
                                    getSupportFragmentManager().beginTransaction().replace(frameLayoutID, profile).commit();
                                    getSupportActionBar().setTitle("Profile");
                                })
                                .addOnFailureListener(exception -> {
                                    var errorDialog = new MaterialAlertDialogBuilder(this);
                                    errorDialog.setTitle(R.string.text_error);
                                    errorDialog.setMessage(exception.getMessage());
                                    errorDialog.setCancelable(false);
                                    errorDialog.setPositiveButton(R.string.text_accept, (dialog, which) -> {
                                        Intent intent = new Intent(this, WelcomeActivity.class);
                                        progressIndicator.hide();
                                        progressIndicator.setVisibility(View.GONE);
                                        startActivity(intent);
                                    });
                                    errorDialog.show();
                                });

                        return true;
                    }
                }
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (firebaseAuth.getCurrentUser() == null) {
            firebaseAuth.signOut();
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.action_add_pet) -> {
                var intent = new Intent(this, CreatePetActivity.class);
                startActivity(intent);
            }
            case (R.id.action_sign_out) -> {
                var intent = new Intent(this, WelcomeActivity.class);
                firebaseAuth.signOut();
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}