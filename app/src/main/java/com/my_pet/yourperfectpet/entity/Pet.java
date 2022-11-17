package com.my_pet.yourperfectpet.entity;

import com.my_pet.yourperfectpet.util.Pets;

public class Pet {
    private String ownerId;
    private String name;
    private Pets race;
    private int age;
    private float weight;
    private boolean vaccinesApplied;
    private String uriId;
    private String description;

    public Pet(String ownerId, String name, Pets race, int age, float weight, boolean vaccinesApplied, String uriId, String description) {
        this.ownerId = ownerId;
        this.name = name;
        this.race = race;
        this.age = age;
        this.weight = weight;
        this.vaccinesApplied = vaccinesApplied;
        this.uriId = uriId;
        this.description = description;
    }

    public Pets getRace() {
        return race;
    }

    public void setRace(Pets race) {
        this.race = race;
    }

    public Pet() {

    }

    public int health(){
        return 0;
    }

    public void setVaccinesApplied(boolean vaccinesApplied) {
        this.vaccinesApplied = vaccinesApplied;
    }

    public String getUriId() {
        return uriId;
    }

    public void setUriId(String uriId) {
        this.uriId = uriId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return uriId;
    }

    public void setId(String uriId) {
        this.uriId = uriId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public boolean isVaccinesApplied() {
        return vaccinesApplied;
    }
}
