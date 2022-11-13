package com.my_pet.yourperfectpet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.my_pet.yourperfectpet.R;
import com.my_pet.yourperfectpet.fragments.MainFragment;
import com.my_pet.yourperfectpet.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.main_activity_toolbar));

        final int frameLayoutID = R.id.main_activity_frame_layout;
        BottomNavigationView navigationBar = findViewById(R.id.main_activity_bottom_navigation);
        Fragment fragment = new MainFragment();

        getSupportFragmentManager().beginTransaction().replace(frameLayoutID, fragment).commit();
        navigationBar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case (R.id.navigation_action_pets) -> {
                    Toast.makeText(this, "Moving to Pets", Toast.LENGTH_SHORT).show();
                    Fragment profile = new MainFragment();
                    getSupportFragmentManager().beginTransaction().replace(frameLayoutID, profile).commit();
                    getSupportActionBar().setTitle("Pets");
                    return true;
                }
                case (R.id.navigation_action_profile) -> {
                    Toast.makeText(this, "Moving to Profile", Toast.LENGTH_SHORT).show();
                    Fragment profile = new ProfileFragment();
                    getSupportFragmentManager().beginTransaction().replace(frameLayoutID, profile).commit();
                    getSupportActionBar().setTitle("Profile");
                    return true;
                }
            }
            return false;
        });
    }
}