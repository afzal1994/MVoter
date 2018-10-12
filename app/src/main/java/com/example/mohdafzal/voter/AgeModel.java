package com.example.mohdafzal.voter;

/**
 * Created by Afzal on 23-Mar-18.
 */

public class AgeModel {

    /**
     * male : 1
     * female : 0
     * Election_Multiple_Worker_id : 1
     * Election_Area_Name : karvenager
     * Voter_DOB : /Date(952281000000)/
     * Voter_idcard_Avilable : null
     */

    private int male;
    private int female;
    private int Election_Multiple_Worker_id;
    private String Election_Area_Name;
    private String Voter_DOB;
    private Object Voter_idcard_Avilable;

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

    public int getElection_Multiple_Worker_id() {
        return Election_Multiple_Worker_id;
    }

    public void setElection_Multiple_Worker_id(int Election_Multiple_Worker_id) {
        this.Election_Multiple_Worker_id = Election_Multiple_Worker_id;
    }

    public String getElection_Area_Name() {
        return Election_Area_Name;
    }

    public void setElection_Area_Name(String Election_Area_Name) {
        this.Election_Area_Name = Election_Area_Name;
    }

    public String getVoter_DOB() {
        return Voter_DOB;
    }

    public void setVoter_DOB(String Voter_DOB) {
        this.Voter_DOB = Voter_DOB;
    }

    public Object getVoter_idcard_Avilable() {
        return Voter_idcard_Avilable;
    }

    public void setVoter_idcard_Avilable(Object Voter_idcard_Avilable) {
        this.Voter_idcard_Avilable = Voter_idcard_Avilable;
    }
}
