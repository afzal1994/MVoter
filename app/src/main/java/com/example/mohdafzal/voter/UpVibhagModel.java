package com.example.mohdafzal.voter;

import com.google.gson.annotations.SerializedName;

class UpVibhagModel {


    /**
     * E_User_id : 2
     * incharge_fname : sanjay
     * incharge_lname : patil
     * phonenumber : 9527467312
     * E_Upvibhag_Name : Patil  wadi
     * E_Upvibhag_id : 1
     * Voter Count : 11
     * Total Voter Count : 10
     */

    private int E_User_id;
    private String incharge_fname;
    private String incharge_lname;
    private String phonenumber;
    private String E_Upvibhag_Name;
    private int E_Upvibhag_id;
    @SerializedName("Voter Count")
    private String _$VoterCount5; // FIXME check this code
    @SerializedName("Total Voter Count")
    private String _$TotalVoterCount327; // FIXME check this code

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

    public String get_$VoterCount5() {
        return _$VoterCount5;
    }

    public void set_$VoterCount5(String _$VoterCount5) {
        this._$VoterCount5 = _$VoterCount5;
    }

    public String get_$TotalVoterCount327() {
        return _$TotalVoterCount327;
    }

    public void set_$TotalVoterCount327(String _$TotalVoterCount327) {
        this._$TotalVoterCount327 = _$TotalVoterCount327;
    }
}
