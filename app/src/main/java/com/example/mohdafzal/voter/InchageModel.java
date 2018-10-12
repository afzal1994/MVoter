package com.example.mohdafzal.voter;

/**
 * Created by Afzal on 10-Oct-17.
 */

public class InchageModel {



    private int E_User_id;
    private String incharge_fname;
    private String incharge_lname;
    private String E_User_PhoneNo;
    private int votercount;
    private String Area;

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

    public String getE_User_PhoneNo() {
        return E_User_PhoneNo;
    }

    public void setE_User_PhoneNo(String E_User_PhoneNo) {
        this.E_User_PhoneNo = E_User_PhoneNo;
    }

    public int getVotercount() {
        return votercount;
    }

    public void setVotercount(int votercount) {
        this.votercount = votercount;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String Area) {
        this.Area = Area;
    }
}
