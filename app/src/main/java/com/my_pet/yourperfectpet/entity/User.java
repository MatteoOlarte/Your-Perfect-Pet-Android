package com.my_pet.yourperfectpet.entity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.my_pet.yourperfectpet.App;

import org.jetbrains.annotations.NotNull;

public class User {
    private String email;
    private String firstName;
    private String lastName;

    public static void getFromFirebase(String email) {
        FirebaseFirestore.getInstance().collection("users").document(email).get().addOnSuccessListener(
                command -> App.user = command.toObject(AppUser.class)
        );
    }

    public User(@NotNull String email, @NotNull String firstName, @NotNull String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User() {
        this("example@user.com", "Example", "User");
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setEmail(@NotNull String email) {
        this.email = email;
    }

    public void setFirstName(@NotNull String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(@NotNull String lastName) {
        this.lastName = lastName;
    }
}