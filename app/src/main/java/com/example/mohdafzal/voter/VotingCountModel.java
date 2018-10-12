package com.example.mohdafzal.voter;

/**
 * Created by Afzal on 20-Feb-18.
 */

public class VotingCountModel {

    /**
     * Totalvoters : 0
     * VotedVoters : 0
     * NotVotedVoters : 1
     * Election_Booth_Id : 7
     * Election_Booth_Name : banner
     */

    private int Totalvoters;
    private int VotedVoters;
    private int NotVotedVoters;
    private int Election_Booth_Id;
    private String Election_Booth_Name;

    public int getTotalvoters() {
        return Totalvoters;
    }

    public void setTotalvoters(int Totalvoters) {
        this.Totalvoters = Totalvoters;
    }

    public int getVotedVoters() {
        return VotedVoters;
    }

    public void setVotedVoters(int VotedVoters) {
        this.VotedVoters = VotedVoters;
    }

    public int getNotVotedVoters() {
        return NotVotedVoters;
    }

    public void setNotVotedVoters(int NotVotedVoters) {
        this.NotVotedVoters = NotVotedVoters;
    }

    public int getElection_Booth_Id() {
        return Election_Booth_Id;
    }

    public void setElection_Booth_Id(int Election_Booth_Id) {
        this.Election_Booth_Id = Election_Booth_Id;
    }

    public String getElection_Booth_Name() {
        return Election_Booth_Name;
    }

    public void setElection_Booth_Name(String Election_Booth_Name) {
        this.Election_Booth_Name = Election_Booth_Name;
    }
}
