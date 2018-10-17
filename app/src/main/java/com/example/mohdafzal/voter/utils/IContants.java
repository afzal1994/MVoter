package com.example.mohdafzal.voter.utils;

public class IContants {
    static String FIRST_LEVEL = "Zila Parishad";
    static String SECOND_LEVEL = "Panchayat Samiti";
    static String THIRD_LEVEL = "Gram Panchayat";
    static String FOURTH_LEVEL = "Prabhag";
    static String FIFTH_LEVEL = "Upvibhag";

    public static String getName(int id) {
        switch (id) {
            case 1:
                return FIRST_LEVEL;

            case 2:
                return SECOND_LEVEL;

            case 3:
                return THIRD_LEVEL;

            case 4:
                return FOURTH_LEVEL;

            case 5:
                return FIFTH_LEVEL;

            default:
                return FIRST_LEVEL;

        }

    }

}
