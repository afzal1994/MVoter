package com.example.mohdafzal.voter;

/**
 * Created by Afzal on 25-Feb-18.
 */

public class ChartRegionalModel {

    /**
     * male : 1
     * female : 0
     * Voter_Regional : 1
     * E_Regional_Id : 1
     * E_Regional_Name : 	Marathi
     * Voter_Area_id : 1
     * Election_Area_id : 1
     * Election_Area_Name : Shivajinager
     */

    private int male;
    private int female;
    private String Voter_Regional;
    private int E_Regional_Id;
    private String E_Regional_Name;
    private int Voter_Area_id;
    private int Election_Area_id;
    private String Election_Area_Name;

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

    public String getVoter_Regional() {
        return Voter_Regional;
    }

    public void setVoter_Regional(String Voter_Regional) {
        this.Voter_Regional = Voter_Regional;
    }

    public int getE_Regional_Id() {
        return E_Regional_Id;
    }

    public void setE_Regional_Id(int E_Regional_Id) {
        this.E_Regional_Id = E_Regional_Id;
    }

    public String getE_Regional_Name() {
        return E_Regional_Name;
    }

    public void setE_Regional_Name(String E_Regional_Name) {
        this.E_Regional_Name = E_Regional_Name;
    }

    public int getVoter_Area_id() {
        return Voter_Area_id;
    }

    public void setVoter_Area_id(int Voter_Area_id) {
        this.Voter_Area_id = Voter_Area_id;
    }

    public int getElection_Area_id() {
        return Election_Area_id;
    }

    public void setElection_Area_id(int Election_Area_id) {
        this.Election_Area_id = Election_Area_id;
    }

    public String getElection_Area_Name() {
        return Election_Area_Name;
    }

    public void setElection_Area_Name(String Election_Area_Name) {
        this.Election_Area_Name = Election_Area_Name;
    }
}
