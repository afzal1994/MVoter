package com.example.mohdafzal.voter;

/**
 * Created by Afzal on 25-Feb-18.
 */

public class ChartPartyModel {

    /**
     * male : 0
     * female : 1
     * E_User_Id : 1
     * Election_Multiple_Worker_id : 1
     * Election_multiple_Area_id : 2
     * Election_Area_Name : karvenager
     * Voter_Id : 15
     * Voter_Area_id : 2
     * Election_Area_id : 2
     * Election_Party_id : 25
     * Election_Party_Name : aap
     * Voter_for_party_vote : 25
     */

    private int male;
    private int female;
    private int E_User_Id;
    private int Election_Multiple_Worker_id;
    private int Election_multiple_Area_id;
    private String Election_Area_Name;
    private int Voter_Id;
    private int Voter_Area_id;
    private int Election_Area_id;
    private int Election_Party_id;
    private String Election_Party_Name;
    private String Voter_for_party_vote;

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

    public int getE_User_Id() {
        return E_User_Id;
    }

    public void setE_User_Id(int E_User_Id) {
        this.E_User_Id = E_User_Id;
    }

    public int getElection_Multiple_Worker_id() {
        return Election_Multiple_Worker_id;
    }

    public void setElection_Multiple_Worker_id(int Election_Multiple_Worker_id) {
        this.Election_Multiple_Worker_id = Election_Multiple_Worker_id;
    }

    public int getElection_multiple_Area_id() {
        return Election_multiple_Area_id;
    }

    public void setElection_multiple_Area_id(int Election_multiple_Area_id) {
        this.Election_multiple_Area_id = Election_multiple_Area_id;
    }

    public String getElection_Area_Name() {
        return Election_Area_Name;
    }

    public void setElection_Area_Name(String Election_Area_Name) {
        this.Election_Area_Name = Election_Area_Name;
    }

    public int getVoter_Id() {
        return Voter_Id;
    }

    public void setVoter_Id(int Voter_Id) {
        this.Voter_Id = Voter_Id;
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

    public int getElection_Party_id() {
        return Election_Party_id;
    }

    public void setElection_Party_id(int Election_Party_id) {
        this.Election_Party_id = Election_Party_id;
    }

    public String getElection_Party_Name() {
        return Election_Party_Name;
    }

    public void setElection_Party_Name(String Election_Party_Name) {
        this.Election_Party_Name = Election_Party_Name;
    }

    public String getVoter_for_party_vote() {
        return Voter_for_party_vote;
    }

    public void setVoter_for_party_vote(String Voter_for_party_vote) {
        this.Voter_for_party_vote = Voter_for_party_vote;
    }
}
