package com.my_pet.yourperfectpet.entity;

import androidx.annotation.NonNull;

public class AppUser extends User {
    private String job;
    private String petPreference;

    public AppUser(@NonNull String email, @NonNull String firstName, @NonNull String lastName, String job, String petPreference) {
        super(email, firstName, lastName);
        this.job = job;
        this.petPreference = petPreference;
    }

    public AppUser() {
        this("example@user.com", "Example", "User", "none", "none");
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getPetPreference() {
        return petPreference;
    }

    public void setPetPreference(String petPreference) {
        this.petPreference = petPreference;
    }
}
