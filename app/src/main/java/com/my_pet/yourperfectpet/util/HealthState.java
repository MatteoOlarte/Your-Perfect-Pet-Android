package com.my_pet.yourperfectpet.util;

import android.content.Context;

import com.my_pet.yourperfectpet.R;
import com.my_pet.yourperfectpet.entity.Pet;

public enum HealthState implements State{
    EXCELLENT(0),
    GOOD(1),
    AVERAGE(2),
    BAD(3);

    private int stateID;
    HealthState(int value) {
        this.stateID = value;
    }

    @Override
    public String getValue(Context context) {
        return context.getResources().getStringArray(R.array.health_state)[this.stateID];
    }

    public static State of(Context context, String value) {

        if (EXCELLENT.getValue(context).toUpperCase().equals(value.toUpperCase())) {
            return EXCELLENT;
        }

        if (GOOD.getValue(context).toUpperCase().equals(value.toUpperCase())) {
            return GOOD;
        }

        if (AVERAGE.getValue(context).toUpperCase().equals(value.toUpperCase())) {
            return AVERAGE;
        }

        if (BAD.getValue(context).toUpperCase().equals(value.toUpperCase())) {
            return BAD;
        }

        return null;
    }

    public static State of(Context context, Pet value) {
        if (value.health() < 5) {
            return AVERAGE;
        } else if (value.health() < 7) {
            return GOOD;
        } else if (value.health() < 10) {
            return EXCELLENT;
        }

        return BAD;
    }
}
