package com.my_pet.yourperfectpet;

import android.app.Application;
import android.widget.EditText;

import java.util.ArrayList;

public class App extends Application {

    public static boolean emptyFields(ArrayList<EditText> fields) {
        for (EditText editText : fields) {
            if (editText.getText().toString().isEmpty())
                return false;
        }
        return true;
    }

    public static boolean verifyEmailAddress(String email) {
        int atCounter = 0;
        int dotCounter = 0;

        for (char i : email.toCharArray()) {
            if (i == '@')
                atCounter++;
            if (i == '.')
                dotCounter++;
        }

        if (atCounter != 1)
            return false;

        return dotCounter >= 1;
    }
}
