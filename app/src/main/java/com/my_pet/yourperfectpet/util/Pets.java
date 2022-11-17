package com.my_pet.yourperfectpet.util;

import android.content.Context;

import com.my_pet.yourperfectpet.R;

public enum Pets {
    DOG(0),
    CAT(1);

    final private int petID;

    Pets(int petID) {
        this.petID = petID;
    }

    public String getValue(Context context) {
        return context.getResources().getStringArray(R.array.pets)[petID];
    }

    public static Pets of(Context context, String pet) {
        if (DOG.getValue(context).toUpperCase().equals(pet.toUpperCase())) {
            return DOG;
        }

        if (CAT.getValue(context).toUpperCase().equals(pet.toUpperCase())) {
            return CAT;
        }

        return null;
    }
}
