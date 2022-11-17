package com.my_pet.yourperfectpet.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.my_pet.yourperfectpet.R;
import com.my_pet.yourperfectpet.entity.AppUser;
import com.my_pet.yourperfectpet.entity.BasicUser;

public class ProfileFragment extends Fragment {

    private BasicUser user;

    public ProfileFragment(BasicUser user) {
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView outputUserName = view.findViewById(R.id.fragment_profile_name);
        TextView outputNickname = view.findViewById(R.id.fragment_profile_nickname);
        TextView outputPet = view.findViewById(R.id.fragment_profile_pet_preference);
        TextView outputJob = view.findViewById(R.id.fragment_profile_job_status);

        if (FirebaseAuth.getInstance().getCurrentUser() != null && user instanceof AppUser appUser) {
            outputUserName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            outputNickname.setText(appUser.getNickName());
            outputJob.setText(appUser.getJobState().getValue(view.getContext()));
            outputPet.setText(appUser.getFavoritePet().getValue(view.getContext()));
        }
    }
}