package com.my_pet.yourperfectpet.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.my_pet.yourperfectpet.R;
import com.my_pet.yourperfectpet.entity.User;
import com.my_pet.yourperfectpet.fragments.MainFragment;
import com.my_pet.yourperfectpet.fragments.ProfileFragment;
import static com.my_pet.yourperfectpet.App.user;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.main_activity_toolbar));

        final int frameLayoutID = R.id.main_activity_frame_layout;
        final String email = getIntent().getExtras().getString("user_email");
        BottomNavigationView navigationBar = findViewById(R.id.main_activity_bottom_navigation);
        Fragment fragment = new MainFragment();
        User.getFromFirebase(email);

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
                    Fragment profile = new ProfileFragment(user);
                    getSupportFragmentManager().beginTransaction().replace(frameLayoutID, profile).commit();
                    getSupportActionBar().setTitle("Profile");
                    return true;
                }
            }
            return false;
        });
    }
}