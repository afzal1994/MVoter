package com.example.mohdafzal.voter.models;

import com.google.gson.annotations.SerializedName;

public class VotingDayModel {

    /**
     * votercount : 0
     * E_User_id : 3
     * incharge_fname : ajit
     * incharge_lname : mali
     * phonenumber : 9422594555
     * E_Upvibhag_Name : Patil wadi
     * Done Voter Count : 0
     * Pending Voter Count : 0
     */

    private int votercount;
    private int E_User_id;
    private String incharge_fname;
    private String incharge_lname;
    private String phonenumber;
    private String E_Upvibhag_Name;
    @SerializedName("Done Voter Count")
    private String _$DoneVoterCount211; // FIXME check this code
    @SerializedName("Pending Voter Count")
    private String _$PendingVoterCount305; // FIXME check this code

    public int getVotercount() {
        return votercount;
    }

    public void setVotercount(int votercount) {
        this.votercount = votercount;
    }

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

    public String get_$DoneVoterCount211() {
        return _$DoneVoterCount211;
    }

    public void set_$DoneVoterCount211(String _$DoneVoterCount211) {
        this._$DoneVoterCount211 = _$DoneVoterCount211;
    }

    public String get_$PendingVoterCount305() {
        return _$PendingVoterCount305;
    }

    public void set_$PendingVoterCount305(String _$PendingVoterCount305) {
        this._$PendingVoterCount305 = _$PendingVoterCount305;
    }
}
