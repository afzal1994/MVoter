package com.example.mohdafzal.voter.models;

import com.google.gson.annotations.SerializedName;

public class PostsHirarchyModel {


    /**
     * E_User_id : 6
     * incharge_fname : nitsh
     * incharge_lname : Patil
     * phonenumber : 7507847935
     * E_Upvibhag_Name : Patil wadi
     * E_Upvibhag_id : 1
     * Voter Count : 6
     * Total Voter Count : 10
     * Out of town Voter Count : 1
     */

    private int E_User_id;
    private String incharge_fname;
    private String incharge_lname;
    private String phonenumber;
    private String E_Upvibhag_Name;
    private int E_Upvibhag_id;
    @SerializedName("Voter Count")
    private String _$VoterCount0; // FIXME check this code
    @SerializedName("Total Voter Count")
    private String _$TotalVoterCount233; // FIXME check this code
    @SerializedName("Out of town Voter Count")
    private String _$OutOfTownVoterCount259; // FIXME check this code

    public int getE_User_id() {
        return E_User_id;
    }

    public void setE_User_id(int E_User_id) {
        this.E_User_id = E_User_id;
    }

    public String getIncharge_fname() {
        return incharge_fname;
    }

    public void setIncharge_fname(String incharge_fname) {
        this.incharge_fname = incharge_fname;
    }

    public String getIncharge_lname() {
        return incharge_lname;
    }

    public void setIncharge_lname(String incharge_lname) {
        this.incharge_lname = incharge_lname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getE_Upvibhag_Name() {
        return E_Upvibhag_Name;
    }

    public void setE_Upvibhag_Name(String E_Upvibhag_Name) {
        this.E_Upvibhag_Name = E_Upvibhag_Name;
    }

    public int getE_Upvibhag_id() {
        return E_Upvibhag_id;
    }

    public void setE_Upvibhag_id(int E_Upvibhag_id) {
        this.E_Upvibhag_id = E_Upvibhag_id;
    }

    public String get_$VoterCount0() {
        return _$VoterCount0;
    }

    public void set_$VoterCount0(String _$VoterCount0) {
        this._$VoterCount0 = _$VoterCount0;
    }

    public String get_$TotalVoterCount233() {
        return _$TotalVoterCount233;
    }

    public void set_$TotalVoterCount233(String _$TotalVoterCount233) {
        this._$TotalVoterCount233 = _$TotalVoterCount233;
    }

    public String get_$OutOfTownVoterCount259() {
        return _$OutOfTownVoterCount259;
    }

    public void set_$OutOfTownVoterCount259(String _$OutOfTownVoterCount259) {
        this._$OutOfTownVoterCount259 = _$OutOfTownVoterCount259;
    }
}
