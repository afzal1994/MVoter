package com.example.mohdafzal.voter;

/**
 * Created by Afzal on 23-Mar-18.
 */

public class VoterLanguageModel {

    /**
     * male : 1
     * female : 1
     * Voter_Language : 3
     * E_Language_Name : Bengali
     */

    private int male;
    private int female;
    private String Voter_Language;
    private String E_Language_Name;

    public int getMale() {
        return male;
    }

    public void setMale(int male) {
        this.male = male;
    }

    public int getFemale() {
        return female;
    }

    public void setFemale(int female) {
        this.female = female;
    }

    public String getVoter_Language() {
        return Voter_Language;
    }

    public void setVoter_Language(String Voter_Language) {
        this.Voter_Language = Voter_Language;
    }

    public String getE_Language_Name() {
        return E_Language_Name;
    }

    public void setE_Language_Name(String E_Language_Name) {
        this.E_Language_Name = E_Language_Name;
    }
}
