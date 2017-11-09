package com.example.mohdafzal.mvoter;

/**
 * Created by Afzal on 10-Oct-17.
 */

public class WorkerModel {

    /**
     * votercount : 3
     * E_User_id : 2
     * incharge_fname : Ridhi
     * incharge_lname :  Khan
     */

    private int votercount;
    private int E_User_id;
    private String incharge_fname;
    private String incharge_lname;

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
}
