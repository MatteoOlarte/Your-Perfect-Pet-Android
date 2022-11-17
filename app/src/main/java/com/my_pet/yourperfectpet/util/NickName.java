package com.my_pet.yourperfectpet.util;

import java.util.Locale;
import java.util.Random;

public class NickName {
    private final String[] fullName = new String[2];
    private final String firstName;
    private final String lastName;

    public NickName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName[0] = this.firstName;
        this.fullName[1] = this.lastName;
    }

    public NickName modifyFirstName() {
        int number = new Random().nextInt(100);
        String textNumb = (number < 10) ? "0" + number : number + "";
        fullName[0] = firstName + textNumb;
        return this;
    }

    public NickName modifyLastName() {
        final int size = lastName.length();

        if (containsSpaces(lastName) > 2) {
            String part1 = lastName.substring(0, containsSpaces(lastName));
            String part2 = getSecondaryLastNames(lastName);

            if (part2.length() > 0) {
                part2 = part2.toUpperCase().charAt(0) + "";
            }

            fullName[1] = part1 + part2;
        }

        return this;
    }

    private String getSecondaryLastNames(String lastName) {
        StringBuilder builder = new StringBuilder();

        for (int i = containsSpaces(lastName); i < lastName.length(); i++) {
            if (lastName.charAt(i) != ' ') {
                builder.append(lastName.charAt(i));
            }
        }
        return builder.toString();
    }

    public NickName addNumbers(int seed) {

        return this;
    }

    private int containsSpaces(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                return i;
            }
        }
        return -1;
    }

    public String getFullName() {
        return fullName[0] + "_" + fullName[1];
    }
}
