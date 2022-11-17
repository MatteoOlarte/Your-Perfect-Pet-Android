package com.my_pet.yourperfectpet.entity;

import com.my_pet.yourperfectpet.util.JobState;
import com.my_pet.yourperfectpet.util.Pets;

public class AppUser extends BasicUser {
    private JobState jobState;
    private Pets favoritePet;
    private String nickName;

    public AppUser(String email, String firstName, String lastName, JobState jobState, Pets favoritePet) {
        super(email, firstName, lastName);
        this.jobState = jobState;
        this.favoritePet = favoritePet;
        this.nickName = getFirstName() + "_" + getLastName();
    }

    public AppUser(JobState jobState, Pets favoritePet) {
        this.jobState = jobState;
        this.favoritePet = favoritePet;
    }

    public AppUser() {

    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public JobState getJobState() {
        return jobState;
    }

    public void setJobState(JobState jobState) {
        this.jobState = jobState;
    }

    public Pets getFavoritePet() {
        return favoritePet;
    }

    public void setFavoritePet(Pets favoritePet) {
        this.favoritePet = favoritePet;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "jobState=" + jobState +
                ", favoritePet=" + favoritePet +
                '}';
    }
}