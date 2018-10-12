package com.example.mohdafzal.voter;

import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohdafzal.voter.navigationdrawer.BaseActivity;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.github.mikephil.charting.charts.Chart.LOG_TAG;

public class AddNewVoter extends BaseActivity implements View.OnClickListener, TextView.OnEditorActionListener, PlaceSelectionListener {
    private EditText fname, lname, mname, gln, aadhar;
    private RadioButton femalerad;
    private LinearLayout femalelinear;
    private int x;
    private int y;
    private static final int REQUEST_SELECT_PLACE = 1000;

    private Button add;
    private RadioGroup radiogroupmale;
    private String fathersname;
    private String fathersaddress;
    private EditText etfathername;
    private EditText etfathersaddress;
    private EditText idcardno;
    private EditText mobilenumber;
    private EditText housenumber;
    private EditText address;
    private EditText societyname;
    private EditText floornumber;
    private EditText wingnumber;
    private EditText flatnumber;
    private RadioGroup staystatus;
    private ImageView datepicker;
    private EditText dateofbith;
    private Calendar calendar;
    private int year;
    private int month;
    private int day;
    private String ocuupationradio;
    private String staystatus1;
    private EditText streenumber;
    private int areapos;
    private String radiovalue;
    private int voter_id;
    private EditText femiddlename;
    private EditText felastname;
    private EditText femalehousenumber;
    private RadioGroup voteridstatus;
    private RadioButton nooption;
    private String spinpartypos;
    private String voter_idstatus;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private RadioButton stayYes;
    private Spinner spinnerOccupation;
    private EditText booth_number;
    private List<UpVibhagModel> yourList;
    private Spinner upVibhagSpinner;
    private int created_by;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_voter);
        getSupportActionBar().setTitle("Add New Voter");
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load
        navMenuIcons = getResources()
            .obtainTypedArray(R.array.nav_drawer_icons);// load icons from
        set(navMenuTitles, navMenuIcons);
        initui();
        initlistner();
        if (NetworkUtil.isNetworkAvailable(this)) {

        } else {
            Toast.makeText(this, "Not connected to internet", Toast.LENGTH_SHORT).show();
        }

        Bundle extras = getIntent().getExtras();
        voter_id = extras.getInt("voter_id");
        if (voter_id != 0) {
            SharedPreferences sharedPreference = getSharedPreferences("userid", MODE_PRIVATE);
            int user_id = sharedPreference.getInt("user_id", 1);
            String url = "http://electionapp.uxservices.in/Web_Services/Update_Voter.asmx/Update?voter_id=" + voter_id;
            System.out.println("voterdetails" + url);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                    try {

                        final JSONArray jsonArray1 = new JSONArray(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));
                        System.out.println("voterdetails" + jsonArray1);
                        JSONObject jsonObject = jsonArray1.getJSONObject(0);
                        fname.setText(jsonObject.getString("Voter_Eng_FName") == null ? "" : jsonObject.getString("Voter_Eng_FName"));
                        mname.setText(jsonObject.getString("Voter_Eng_MName").matches("null") ? "" : jsonObject.getString("Voter_Eng_MName"));
                        lname.setText(jsonObject.getString("Voter_Eng_LName").matches("null") ? "" : jsonObject.getString("Voter_Eng_LName"));
                        address.setText(jsonObject.getString("Voter_Address").matches("null") ? "" : jsonObject.getString("Voter_Address"));
                        mobilenumber.setText(jsonObject.getString("Voter_PhoneNo").matches("null") ? "" : jsonObject.getString("Voter_PhoneNo"));
                        gln.setText(jsonObject.getString("Voter_Govt_SNo").matches("null") ? "" : jsonObject.getString("Voter_Govt_SNo"));
                        idcardno.setText(jsonObject.getString("Voter_IdCard_No").matches("null") ? "" : jsonObject.getString("Voter_IdCard_No"));
                        etfathername.setText(jsonObject.getString("Voter_Father_Husband_Name").matches("null") ? "" : jsonObject.getString("Voter_Father_Husband_Name"));
                        etfathersaddress.setText(jsonObject.getString("Voter_Genderaddress").matches("null") ? "" : jsonObject.getString("Voter_Genderaddress"));
                        societyname.setText(jsonObject.getString("Voter_Socity_Buildingname").matches("null") ? "" : jsonObject.getString("Voter_Socity_Buildingname"));
                        wingnumber.setText(jsonObject.getString("Voter_Wing").matches("null") ? "" : jsonObject.getString("Voter_Wing"));
                        flatnumber.setText(jsonObject.getString("Voter_Flat_No").matches("null") ? "" : jsonObject.getString("Voter_Flat_No"));
                        floornumber.setText(jsonObject.getString("Voter_Floor_No").matches("null") ? "" : jsonObject.getString("Voter_Floor_No"));
                        streenumber.setText(jsonObject.getString("Voter_Street_No").matches("null") ? "" : jsonObject.getString("Voter_Street_No"));
                        aadhar.setText(jsonObject.getString("Voter_Aadharcard_No").matches("null") ? "" : jsonObject.getString("Voter_Aadharcard_No"));
                        mobilenumber.setText(jsonObject.getString("Voter_PhoneNo").matches("null") ? "" : jsonObject.getString("Voter_PhoneNo"));
                        femiddlename.setText(jsonObject.getString("Voter_Fe_mname").matches("null") ? "" : jsonObject.getString("Voter_Fe_mname"));
                        felastname.setText(jsonObject.getString("Voter_Fe_Lname").matches("null") ? "" : jsonObject.getString("Voter_Fe_Lname"));
                        flatnumber.setText(jsonObject.getString("Voter_Fe_Flat_no").matches("null") ? "" : jsonObject.getString("Voter_Fe_Flat_no"));
                        femalehousenumber.setText(jsonObject.getString("Voter_Fe_House_no").matches("null") ? "" : jsonObject.getString("Voter_Fe_House_no"));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            requestQueue.add(stringRequest);


        }
    }


    private void initlistner() {

        femiddlename.setOnEditorActionListener(this);
        datepicker.setOnClickListener(this);
        felastname.setOnEditorActionListener(this);
        floornumber.setOnEditorActionListener(this);
        femalehousenumber.setOnEditorActionListener(this);
        fname.setOnEditorActionListener(this);
        mname.setOnEditorActionListener(this);
        lname.setOnEditorActionListener(this);
        idcardno.setOnEditorActionListener(this);
        mobilenumber.setOnEditorActionListener(this);
        gln.setOnEditorActionListener(this);
        aadhar.setOnEditorActionListener(this);
        address.setOnEditorActionListener(this);
        societyname.setOnEditorActionListener(this);
        floornumber.setOnEditorActionListener(this);
        streenumber.setOnEditorActionListener(this);
        floornumber.setOnEditorActionListener(this);
        wingnumber.setOnEditorActionListener(this);
        flatnumber.setOnEditorActionListener(this);
        aadhar.setOnEditorActionListener(this);
        add.setOnClickListener(this);
    }

    private void initui() {
        etfathersaddress = (EditText) findViewById(R.id.addressfather);


        spinnerOccupation = (Spinner) findViewById(R.id.spinnerOccupation);
        femiddlename = (EditText) findViewById(R.id.femiddlename);
        felastname = (EditText) findViewById(R.id.felastname);
        stayYes = (RadioButton) findViewById(R.id.stayYes);
        spinnerOccupation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1 || position == 2 || position == 3 || position == 4) {
                    findViewById(R.id.businessVisibilityLayout).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.businessVisibilityLayout).setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        stayYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    findViewById(R.id.outOfTownVisibilityLayout).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.outOfTownVisibilityLayout).setVisibility(View.GONE);
                }
            }
        });
        femalehousenumber = (EditText) findViewById(R.id.femalehousenumber);
        dateofbith = (EditText) findViewById(R.id.dateofbirth);
        SharedPreferences sharedPreference2 = getSharedPreferences("userid", MODE_PRIVATE);
        int user_id2 = sharedPreference2.getInt("user_id", 1);
        if (user_id2 == 5) {
            findViewById(R.id.upvibhagSpinnerRow).setVisibility(View.VISIBLE);
            upVibhagSpinner = (Spinner) findViewById(R.id.upvibhagSpinner);
            getUpvibhaWorkers();
        }
        datepicker = (ImageView) findViewById(R.id.datepicker);
        voteridstatus = (RadioGroup) findViewById(R.id.voteridstatus);
        radiogroupmale = (RadioGroup) findViewById(R.id.radiogrupgender);
        add = (Button) findViewById(R.id.add);
        fname = (EditText) findViewById(R.id.fname);
        streenumber = (EditText) findViewById(R.id.streenumber);
        etfathername = (EditText) findViewById(R.id.fathersname);
        idcardno = (EditText) findViewById(R.id.idcardnumber);
        mobilenumber = (EditText) findViewById(R.id.mobilenumber);
        address = (EditText) findViewById(R.id.address);
        societyname = (EditText) findViewById(R.id.societyname);
        floornumber = (EditText) findViewById(R.id.floornumber);
        wingnumber = (EditText) findViewById(R.id.wingnumber);
        flatnumber = (EditText) findViewById(R.id.flatnumber);
        staystatus = (RadioGroup) findViewById(R.id.staystatus);
        lname = (EditText) findViewById(R.id.lname);
        mname = (EditText) findViewById(R.id.mname);
        gln = (EditText) findViewById(R.id.gln);
        booth_number = (EditText) findViewById(R.id.booth_number);
        aadhar = (EditText) findViewById(R.id.aadharcard);
        femalerad = (RadioButton) findViewById(R.id.rdfemale);
        nooption = (RadioButton) findViewById(R.id.nooption);
        femalelinear = (LinearLayout) findViewById(R.id.feamlelinear);
        nooption.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (idcardno.isShown()) {
                    idcardno.setVisibility(View.GONE);
                } else {
                    idcardno.setVisibility(View.VISIBLE);

                }
            }
        });

        femalerad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (femalelinear.isShown()) {
                    femalelinear.setVisibility(View.GONE);
                } else {
                    femalelinear.setVisibility(View.VISIBLE);

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.add:
                sendreq();
                break;
            case R.id.datepicker:
                new SpinnerDatePickerDialogBuilder()
                    .context(AddNewVoter.this)
                    .callback(new com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            dateofbith.setText((monthOfYear) + "/" + dayOfMonth + "/"
                                + year);
                        }
                    })
                    .spinnerTheme(R.style.AppTheme)
                    .showTitle(true)
                    .defaultDate(1990, 8, 22)
                    .build()
                    .show();
                break;
        }

    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            day = selectedDay;
            month = selectedMonth;
            year = selectedYear;
            dateofbith.setText((selectedMonth + 1) + "/" + selectedDay + "/"
                + selectedYear);
        }
    };

    private void sendreq() {


        if (fname.getText().toString().matches("")) {
            fname.setError("Please Enter Name");
            fname.requestFocus();
        } else if (lname.getText().toString().matches("")) {
            lname.setError("Please Enter Name");

            lname.requestFocus();
        } else {
            if (radiogroupmale.getCheckedRadioButtonId() == -1) {
                radiovalue = "";
            } else {
                radiovalue = ((RadioButton) findViewById(radiogroupmale.getCheckedRadioButtonId())).getText().toString();
            }
            if (voteridstatus.getCheckedRadioButtonId() == -1) {
                voter_idstatus = "";
            } else {
                voter_idstatus = ((RadioButton) findViewById(voteridstatus.getCheckedRadioButtonId())).getText().toString();
            }


            if (radiovalue.matches("male")) {
                fathersname = "";
                fathersaddress = "";
            } else {
                fathersname = etfathername.getText().toString();
                fathersaddress = etfathersaddress.getText().toString();
            }


            if (staystatus.getCheckedRadioButtonId() == -1) {
                staystatus1 = "";
            } else {
                staystatus1 = ((RadioButton) findViewById(staystatus.getCheckedRadioButtonId())).getText().toString();
            }
            if (!NetworkUtil.isNetworkAvailable(AddNewVoter.this)) {
/*

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("ECandidateFName_eng", fname.getText().toString());
                        jsonObject.put("ECandidateMName_Eng", mname.getText().toString());
                        jsonObject.put("ECandidateLName_Eng", lname.getText().toString());
                        jsonObject.put("ECandidateFName_Marthi", "");
                        jsonObject.put("ECandidateMName_Marthi", "");
                        jsonObject.put("ECandidateLName_Marthi", "");
                        jsonObject.put("gender", radiovalue);
                        jsonObject.put("ECandidateAddress", address.getText().toString());
                        jsonObject.put("&ECandidatePhoneNo", mobilenumber.getText().toString());
                        jsonObject.put("ECandidateImg", "");
                        jsonObject.put("EWardId", "");
                        jsonObject.put("VoterwardNumber", warspinpos);
                        jsonObject.put("age", "");
                        jsonObject.put("dob", dateofbith.getText().toString());
                        jsonObject.put("houseno", housenumber.getText().toString());
                        jsonObject.put("occupation", ocuupationradio);
                        jsonObject.put("occp_details", occupationdetails.getText().toString());
                        jsonObject.put("govt_sno", gln.getText().toString());
                        jsonObject.put("currentstay_status", staystatus1);
                        jsonObject.put("idcardno", idcardno.getText().toString());
                        jsonObject.put("fatherhasbandname", fathersname);
                        jsonObject.put("Genderadd", fathersaddress);
                        jsonObject.put("Pin_no", picode.getText().toString());
                        jsonObject.put("residentialtype", resindentialspinpos);
                        jsonObject.put("regional", regionalspinpos);
                        jsonObject.put("caste", castespinpos);
                        jsonObject.put("Lang", languagespinpos);
                        jsonObject.put("buildingnam", societyname.getText().toString());
                        jsonObject.put("wing_no", wingnumber.getText().toString());
                        jsonObject.put("flatno", flatnumber.getText().toString());
                        jsonObject.put("floorno", floornumber.getText().toString());
                        jsonObject.put("street_no", staydetails.getText().toString());
                        jsonObject.put("aadharcard_no", aadhar.getText().toString());
                        jsonObject.put("state", "Maharashra");
                        jsonObject.put("distric", districspinpos);
                        jsonObject.put("sub_distric", subdistrictspinpos);
                        jsonObject.put("cityvilage", villagespinpos);
                        jsonObject.put("area_id", areapos);
                        jsonObject.put("ward_id", warspinpos);
                        jsonObject.put("staydetails", staystatus1);
                        jsonObject.put("fe_mname", femiddlename.getText().toString());
                        jsonObject.put("fe_lname", felastname.getText().toString());
                        jsonObject.put("fe_buildingname", femalebuildingname.getText().toString());
                        jsonObject.put("fe_floor", femaleflorrnumber.getText().toString());
                        jsonObject.put("fe_street", femalestreennumber.getText().toString());
                        jsonObject.put("fe_wing", femaleWingnumber.getText().toString());
                        jsonObject.put("flat_number", femaleflatnumber.getText().toString());
                        jsonObject.put("fe_state", "Maharashtra");
                        jsonObject.put("fe_distric", femaledistricspinpos);
                        jsonObject.put("fe_subdistric", femalesubdistrictspinpos);
                        jsonObject.put("fe_cityvilage", femalevillagespinpos);
                        jsonObject.put("fe_houseno", femalehousenumber.getText().toString());
                        jsonObject.put("fe_phnenumber", femalePhonenumber.getText().toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    DatabaseHandler db = new DatabaseHandler(this);

                    *//**
                 * CRUD Operations
                 * *//*
                    // Inserting Contacts
                    OfflineModel offlineModel = new OfflineModel();
                    Log.d("Insert: ", "Inserting ..");
                    offlineModel.setJsonobject(jsonObject.toString());
                    db.addContact(offlineModel);
                    Toast.makeText(this, "Voter Details Added to local dataBase", Toast.LENGTH_SHORT).show();
                    finish();
                    return;*/
            } else {
                SharedPreferences sharedPreference2 = getSharedPreferences("userid", MODE_PRIVATE);
                int user_id2 = sharedPreference2.getInt("user_id", 1);
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.show();
                String s = "http://electionapp.uxservices.in/Web_Services/Update_Voter_Details.asmx/Updates?" +
                    "voter_id=" + voter_id +
                    "&ECandidateFName_eng=" + fname.getText().toString() + "&ECandidateMName_Eng=" + mname.getText().toString()
                    + "&ECandidateLName_Eng=" + lname.getText().toString() +
                    "&ECandidateFName_Marthi=vvv&ECandidateMName_Marthi=jj&ECandidateLName_Marthi=bbb&gender=" + radiovalue +
                    "&ECandidateAddress=" + address.getText().toString() + "&ECandidatePhoneNo=" + mobilenumber.getText().toString()
                    + "&ECandidateImg=jhh&EWardId=123" +
                    "&VoterwardNumber=" + booth_number.getText().toString() +
                    "&age=23&dob=" + dateofbith.getText().toString() +
                    "&houseno=" +
                    "&occupation=" + ocuupationradio + "&occp_details=" + "" +
                    "&govt_sno=" + gln.getText().toString() + "&currentstay_status=" + staystatus1 + "&idcardno=" + idcardno.getText().toString()
                    + "&fatherhasbandname=" + fathersname +
                    "&Genderadd=" + fathersaddress + "&Pin_no=" + "" + "&residentialtype=" + "" +
                    "&regional=" + "" + "&caste=" + "" +
                    "&Lang=" + "" + "&buildingnam=" + societyname.getText().toString() + "&wing_no=" + wingnumber.getText().toString()
                    + "&flatno=" + flatnumber.getText().toString() + "&floorno=" + floornumber.getText().toString() +
                    "&street_no=" + "" + "sdzx" + "&aadharcard_no=" + aadhar.getText().toString() +
                    "&state=&distric=" + "" + "&sub_distric=" + "" + "&cityvilage=" + "" + "&area_id=" + areapos +
                    "&ward_id=" + "" + "&user_id=" + user_id2 + "&staydetails=" + staystatus1 + "&fe_mname=" + femiddlename.getText().toString() + "&fe_lname=" + felastname.getText().toString() + "&fe_buildingname=" + "" +
                    "&fe_floor=" + "" + "&fe_street=" + "" + "&fe_wing=" + "" + wingnumber.getText().toString()
                    + "&fe_state=" + "&fe_distric=" + "" + "&fe_subdistric=" + "" + "&fe_cityvilage=" + "" + "&fe_houseno=" + femalehousenumber.getText().toString() +
                    "&fe_phnenumber=" + "" + "&flat_number=" + ""+"&created_by="+created_by + "&voterid_avialble=" + voter_idstatus + "&partytovote=" + spinpartypos;
                String s1 = "http://electionapp.uxservices.in/Web_Services/Add_Voter.asmx/Voter_Add?" +
                    "ECandidateFName_eng=" + fname.getText().toString() +"&created_by="+created_by + "&ECandidateMName_Eng=" + mname.getText().toString()
                    + "&ECandidateLName_Eng=" + lname.getText().toString() +
                    "&ECandidateFName_Marthi=vvv&ECandidateMName_Marthi=jj&ECandidateLName_Marthi=bbb&gender=" + radiovalue +
                    "&ECandidateAddress=" + address.getText().toString() + "&ECandidatePhoneNo=" + mobilenumber.getText().toString()
                    + "&ECandidateImg=jhh&EWardId=123" +
                    "&VoterwardNumber=" + booth_number.getText().toString() + "&age=23&dob=" + dateofbith.getText().toString() + "&houseno=" +
                    "&occupation=" + ocuupationradio + "&occp_details=" + "" +
                    "&govt_sno=" + gln.getText().toString() + "&currentstay_status=" + staystatus1 + "&idcardno=" + idcardno.getText().toString()
                    + "&fatherhasbandname=" + fathersname +
                    "&Genderadd=" + fathersaddress + "&Pin_no=" + "" + "&residentialtype=" + "" +
                    "&regional=" + "" + "&caste=" + "" +
                    "&Lang=" + "" + "&buildingnam=" + societyname.getText().toString() + "&wing_no=" + wingnumber.getText().toString()
                    + "&flatno=" + flatnumber.getText().toString() + "&floorno=" + floornumber.getText().toString() +
                    "&street_no=" + "sdzx" + "&aadharcard_no=" + aadhar.getText().toString() +
                    "&state=&distric=" + "" + "&sub_distric=" + "" + "&cityvilage=" + "" + "&area_id=" + areapos +
                    "&ward_id=" + "" + "&staydetails=" + staystatus1 + "&fe_mname=" + femiddlename.getText().toString() + "&fe_lname=" + felastname.getText().toString() + "&fe_buildingname=" + "" +
                    "&fe_floor=" + "" + "&fe_street=" + "" + "&fe_wing=" + "" + wingnumber.getText().toString()
                    + "&fe_state=" + "Maharashtra" + "&fe_distric=" + "" + "&fe_subdistric=" + "" + "&fe_cityvilage=" + "" + "&fe_houseno=" + femalehousenumber.getText().toString() +
                    "&fe_phnenumber=" + "" + "&user_id=" + user_id2 + "&flat_number=" + "" + "&availablity_voterid=" + voter_idstatus + "&votetoparty=" + spinpartypos;
                System.out.println(s);

                 /*   String s = "http://electionapp.uxservices.in/Web_Services/Add_Voter.asmx/Voter_Add?" +
                        "ECandidateFName_eng=" + fname.getText().toString() + "&ECandidateMName_Eng=" + mname.getText().toString()
                        + "&ECandidateLName_Eng=" + lname.getText().toString() +
                        "&ECandidateFName_Marthi=vvv&ECandidateMName_Marthi=jj&ECandidateLName_Marthi=bbb&gender=" + radiovalue +
                        "&ECandidateAddress=" + address.getText().toString() + "&ECandidatePhoneNo=" + mobilenumber.getText().toString()
                        + "&ECandidateImg=jhh&EWardId=123" +
                        "&VoterwardNumber=" + "1" + "&age=23&dob=" + dateofbith.getText().toString() + "&houseno=" + housenumber.getText().toString() +
                        "&occupation=" + ocuupationradio + "&occp_details=" + occupationdetails.getText().toString() +
                        "&govt_sno=" + gln.getText().toString() + "&currentstay_status=" + staystatus1 + "&idcardno=" + idcardno.getText().toString()
                        + "&fatherhasbandname=" + fathersname +
                        "&Genderadd=" + fathersaddress + "&Pin_no=" + "" + "&residentialtype=" + "" +
                        "&regional=" + "" + "&caste=" + "" +
                        "&Lang=" + "" + "&buildingnam=" + societyname.getText().toString() + "&wing_no=" + wingnumber.getText().toString()
                        + "&flatno=" + flatnumber.getText().toString() + "&floorno=" + floornumber.getText().toString() +
                        "&street_no=" + staydetails.getText().toString() + "sdzx" + "&aadharcard_no=" + aadhar.getText().toString() +
                        "&state=mp&distric=" + "" + "&sub_distric=" + "" + "&cityvilage=" + "" + "&area_id=" + areapos +
                        "&ward_id=" + "" + "&staydetails=" + staystatus1 + "&fe_mname=" + femiddlename.getText().toString() + "&fe_lname=" + felastname.getText().toString() + "&fe_buildingname=" + "" +
                        "&fe_floor=" + femaleflorrnumber.getText().toString() + "&fe_street=" + femalestreennumber.getText().toString() + "&fe_wing=" + "" + wingnumber.getText().toString()
                        + "&fe_state=" + "Maharashtra" + "&fe_distric=" + "" + "&fe_subdistric=" + "" + "&fe_cityvilage=" + "" + "&fe_houseno=" + femalehousenumber.getText().toString() +
                        "&fe_phnenumber=" + "" + "&flat_number=" + "" + "&availablity_voterid=" + voter_idstatus + "&votetoparty=" + spinpartypos;   System.out.println(s);
                */
                StringRequest stringRequest = new StringRequest(Request.Method.GET, voter_id == 0 ? s1.replaceAll(" ", "%20") : s.replaceAll(" ", "%20"), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.hide();
                        System.out.println("first" + response);
                        String newString = response.replace("http://electionapp.uxservices.in", "");
                        String newString1 = newString.replace("<string xmlns=\"\">", "");
                        String newString2 = newString1.replace("</string>", "");
                        String newstring3 = newString2.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                        System.out.println("dasdxaws" + newstring3);

                        try {
                            JSONArray jsonArray = new JSONArray(newstring3.replace("<string xmlns=\"/\">", ""));
/*
                                if (jsonArray.getJSONObject(0).getString("Status").matches("true")) {
*/
                            progressDialog.hide();
                            Toast.makeText(AddNewVoter.this, jsonArray.getJSONObject(0).getString("msg"), Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(AddNewVoter.this, Voterlist.class);
                            finish();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                startActivity(intent, ActivityOptions
                                    .makeSceneTransitionAnimation(AddNewVoter.this).toBundle());
                            } else {
                                startActivity(intent);
                            }
                                /*} else {
                                    progressDialog.hide();
                                    Toast.makeText(AddNewVoter.this, "please check internet settings", Toast.LENGTH_SHORT).show();
                                }*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(AddNewVoter.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                int socketTimeout = 10000;//30 seconds - change to what you want
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(policy);
                requestQueue.add(stringRequest);
            }


        }

    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (v.getId()) {
            case R.id.fname:

                fname.clearFocus();
                mname.requestFocus();

                break;


            case R.id.lname:


                lname.clearFocus();

                housenumber.requestFocus();


                break;


            case R.id.mname:

                fname.clearFocus();
                lname.requestFocus();


                break;

            case R.id.idcardnumber:

                idcardno.clearFocus();
                mobilenumber.requestFocus();


                break;
            case R.id.mobilenumber:

                mobilenumber.clearFocus();
                gln.requestFocus();

                break;

            case R.id.gln:

                gln.clearFocus();
                aadhar.requestFocus();

                break;


            case R.id.address:

                add.clearFocus();
                societyname.requestFocus();

                break;


            case R.id.societyname:

                societyname.clearFocus();
                floornumber.requestFocus();

                break;


            case R.id.floornumber:

                floornumber.clearFocus();
                streenumber.requestFocus();

                break;


            case R.id.streenumber:

                streenumber.clearFocus();
                wingnumber.requestFocus();

                break;

            case R.id.wingnumber:

                wingnumber.clearFocus();
                flatnumber.requestFocus();


                break;

            case R.id.fathersname:

                etfathername.clearFocus();
                etfathersaddress.requestFocus();
                break;
            case R.id.addressfather:

                etfathersaddress.clearFocus();
                femiddlename.requestFocus();
                break;
            case R.id.femiddlename:

                femiddlename.clearFocus();
                felastname.requestFocus();
                break;


        }
        return false;
    }

    @Override
    public void onPlaceSelected(Place place) {
        Log.i(LOG_TAG, "Place Selected: " + place.getName());
      /*  locationTextView.setText(getString(R.string.formatted_place_data, place
            .getName(), place.getAddress(), place.getPhoneNumber(), place
            .getWebsiteUri(), place.getRating(), place.getId()));*/
        if (!TextUtils.isEmpty(place.getAttributions())) {
            etfathersaddress.setText(Html.fromHtml(place.getAttributions().toString()));
        }
    }

    @Override
    public void onError(Status status) {
        Log.e(LOG_TAG, "onError: Status = " + status.toString());
        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
            Toast.LENGTH_SHORT).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_PLACE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                this.onPlaceSelected(place);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                this.onError(status);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void getUpvibhaWorkers() {


        String url2 = "http://electionapp.uxservices.in/Web_Services/Manager_Worker_list.asmx/Manager_Worker_List?user_id=5";
        System.out.println("thisurl" + url2);
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                try {
                    System.out.println("this" + jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));

                    JSONObject jsonObject = new JSONObject(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "").replace("<string xmlns=\"http://electionapp.uxservices.in\">", ""));
                    JSONArray jsonArray1 = jsonObject.getJSONArray("data:");
                    Type listType = new TypeToken<List<UpVibhagModel>>() {
                    }.getType();
                    yourList = new Gson().fromJson(String.valueOf(jsonArray1), listType);
                    ArrayList<String> strings = new ArrayList<>();
                    for (int i = 0; i < yourList.size(); i++) {
                        strings.add(yourList.get(i).getIncharge_fname()+" "+yourList.get(i).getIncharge_lname());

                    }
                    System.out.println("listarea" + strings);
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddNewVoter.this,
                        android.R.layout.simple_spinner_item, strings);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    upVibhagSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            created_by=yourList.get(position).getE_User_id();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    upVibhagSpinner.setAdapter(dataAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue2 = Volley.newRequestQueue(this);
        requestQueue2.add(stringRequest2);

    }

}
