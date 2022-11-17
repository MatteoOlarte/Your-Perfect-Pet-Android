package com.my_pet.yourperfectpet.util;

import android.content.Context;
import android.widget.Toast;

import com.my_pet.yourperfectpet.R;

public enum JobState implements State{
    EMPLOYED(0),
    SELF_EMPLOYED(1),
    UNEMPLOYED(2);

    private final int stateID;

    JobState(int id) {
        this.stateID = id;
    }

    @Override
    public String getValue(Context context) {
        return context.getResources().getStringArray(R.array.jobs)[this.stateID];
    }

    public static State of(Context context, String state) {
        if (EMPLOYED.getValue(context).toUpperCase().equals(state.toUpperCase())) {
            return EMPLOYED;
        }

        if (SELF_EMPLOYED.getValue(context).toUpperCase().equals(state.toUpperCase())) {
            return SELF_EMPLOYED;
        }

        if (UNEMPLOYED.getValue(context).toUpperCase().equals(state.toUpperCase())) {
            return UNEMPLOYED;
        }

        return null;
    }
}
