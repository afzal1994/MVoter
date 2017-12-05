package com.example.mohdafzal.mvoter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddNewVoter extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {
    private EditText fname, lname, mname, gln, aadhar;
    private ImageView logout;
    private Spinner areaspin;
    private Spinner warspin;
    private Spinner boothspin;
    private RadioButton femalerad;
    private LinearLayout femalelinear;
    private Spinner resindentialspin, regionalspin, castespin, languagespin, districspin, subdistrictspin, villagespin, statespin;
    private int x;
    private int y;
    private LinearLayout linearpersonal, linearpersonalinner, linearAddress, linearaddressinner, linearadditional, linearadditionalinner;
    private int warspinpos;
    private int boothspinpos;
    private int castespinpos;
    private int regionalspinpos;
    private int languagespinpos;
    private int resindentialspinpos;
    private int districspinpos;
    private int subdistrictspinpos;
    private String villagespinpos;
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
    private EditText occupationdetails;
    private EditText picode;
    private EditText staydetails;
    private RadioGroup occupatonradio;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_voter);
        initui();
        initlistner();
        loadarea();
        loadward();
        loadbooth();
        loadresidential();
        loadlanguage();
        loadregion();
        loadcaste();
        loaddistrict();
        loadState();
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
                        fname.setText(jsonObject.getString("Voter_Eng_FName"));
                        mname.setText(jsonObject.getString("Voter_Eng_MName"));
                        lname.setText(jsonObject.getString("Voter_Eng_LName"));
                        address.setText(jsonObject.getString("Voter_Address"));
                        mobilenumber.setText(jsonObject.getString("Voter_PhoneNo"));
                        housenumber.setText(jsonObject.getString("Voter_House_No"));
                        occupationdetails.setText(jsonObject.getString("Voter_Occupation"));
                        staydetails.setText(jsonObject.getString("Voter_Stay_Details"));
                        gln.setText(jsonObject.getString("Voter_Govt_SNo"));
                        idcardno.setText(jsonObject.getString("Voter_IdCard_No"));
                        etfathername.setText(jsonObject.getString("Voter_Father_Husband_Name"));
                        etfathersaddress.setText(jsonObject.getString("Voter_Genderaddress"));
                        picode.setText(jsonObject.getString("Voter_PinCode"));
                        societyname.setText(jsonObject.getString("Voter_Socity_Buildingname"));
                        wingnumber.setText(jsonObject.getString("Voter_Wing"));
                        flatnumber.setText(jsonObject.getString("Voter_Flat_No"));
                        floornumber.setText(jsonObject.getString("Voter_Floor_No"));
                        streenumber.setText(jsonObject.getString("Voter_Street_No"));
                        aadhar.setText(jsonObject.getString("Voter_Aadharcard_No"));


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
        datepicker.setOnClickListener(this);
        fname.setOnEditorActionListener(this);
        mname.setOnEditorActionListener(this);
        lname.setOnEditorActionListener(this);
        housenumber.setOnEditorActionListener(this);
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
        occupationdetails.setOnEditorActionListener(this);
        picode.setOnEditorActionListener(this);
        staydetails.setOnEditorActionListener(this);

        aadhar.setOnEditorActionListener(this);
        logout.setOnClickListener(this);
        linearadditional.setOnClickListener(this);
        linearAddress.setOnClickListener(this);
        linearpersonal.setOnClickListener(this);
        add.setOnClickListener(this);
    }

    private void initui() {
        dateofbith = (EditText) findViewById(R.id.dateofbirth);
        datepicker = (ImageView) findViewById(R.id.datepicker);
        radiogroupmale = (RadioGroup) findViewById(R.id.radiogrupgender);
        add = (Button) findViewById(R.id.add);
        logout = (ImageView) findViewById(R.id.logout);
        logout.setOnClickListener(this);
        fname = (EditText) findViewById(R.id.fname);
        streenumber = (EditText) findViewById(R.id.streenumber);

        etfathername = (EditText) findViewById(R.id.fathersname);
        etfathersaddress = (EditText) findViewById(R.id.addressfather);
        idcardno = (EditText) findViewById(R.id.idcardnumber);
        mobilenumber = (EditText) findViewById(R.id.mobilenumber);
        housenumber = (EditText) findViewById(R.id.housenumber);
        address = (EditText) findViewById(R.id.address);
        societyname = (EditText) findViewById(R.id.societyname);
        floornumber = (EditText) findViewById(R.id.floornumber);
        wingnumber = (EditText) findViewById(R.id.wingnumber);
        flatnumber = (EditText) findViewById(R.id.flatnumber);
        occupationdetails = (EditText) findViewById(R.id.occupationdetails);
        picode = (EditText) findViewById(R.id.pincode);
        staydetails = (EditText) findViewById(R.id.staydetails);
        occupatonradio = (RadioGroup) findViewById(R.id.occupation);
        staystatus = (RadioGroup) findViewById(R.id.staystatus);
        lname = (EditText) findViewById(R.id.lname);
        mname = (EditText) findViewById(R.id.mname);
        gln = (EditText) findViewById(R.id.gln);
        aadhar = (EditText) findViewById(R.id.aadharcard);
        areaspin = (Spinner) findViewById(R.id.spinarea);
        statespin = (Spinner) findViewById(R.id.spinstate);

        warspin = (Spinner) findViewById(R.id.spinward);
        boothspin = (Spinner) findViewById(R.id.spinbooth);
        femalerad = (RadioButton) findViewById(R.id.rdfemale);
        femalelinear = (LinearLayout) findViewById(R.id.feamlelinear);
        resindentialspin = (Spinner) findViewById(R.id.spinresidential);
        languagespin = (Spinner) findViewById(R.id.spinlanguage);
        regionalspin = (Spinner) findViewById(R.id.spinregion);
        castespin = (Spinner) findViewById(R.id.spincaste);
        districspin = (Spinner) findViewById(R.id.spindistric);
        subdistrictspin = (Spinner) findViewById(R.id.spinsubdistrict);
        villagespin = (Spinner) findViewById(R.id.spinvillage);
        linearpersonal = (LinearLayout) findViewById(R.id.lin2);
        linearpersonalinner = (LinearLayout) findViewById(R.id.lin5);

        linearAddress = (LinearLayout) findViewById(R.id.lin3);
        linearaddressinner = (LinearLayout) findViewById(R.id.lin6);

        linearadditional = (LinearLayout) findViewById(R.id.lin4);
        linearadditionalinner = (LinearLayout) findViewById(R.id.lin7);


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
            case R.id.logout:
                Intent intent = new Intent(this, LoginActivity.class);
                finish();
                finishAffinity();
                SharedPreferences.Editor editor=getSharedPreferences("Loginstatus",MODE_PRIVATE).edit();
                editor.putBoolean("loginstatus",false);
                editor.clear();
                editor.apply();

                startActivity(intent);
                break;
            case R.id.lin2:
                if (linearpersonalinner.isShown()) {
                    linearpersonalinner.setVisibility(View.GONE);
                } else {
                    linearpersonalinner.setVisibility(View.VISIBLE);

                }
                break;
            case R.id.lin3:
                if (linearaddressinner.isShown()) {
                    linearaddressinner.setVisibility(View.GONE);
                } else {
                    linearaddressinner.setVisibility(View.VISIBLE);

                }
                break;
            case R.id.lin4:
                if (linearadditionalinner.isShown()) {
                    linearadditionalinner.setVisibility(View.GONE);
                } else {
                    linearadditionalinner.setVisibility(View.VISIBLE);

                }
                break;
            case R.id.add:
                sendreq();
                break;
            case R.id.datepicker:
                showDialog(0);
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
            linearpersonalinner.setVisibility(View.VISIBLE);
            fname.requestFocus();
        } else if (lname.getText().toString().matches("")) {
            lname.setError("Please Enter Name");
            linearpersonalinner.setVisibility(View.VISIBLE);

            lname.requestFocus();
        } else if (radiogroupmale.getCheckedRadioButtonId() == -1) {
            lname.requestFocus();
            linearpersonalinner.setVisibility(View.VISIBLE);

            Toast.makeText(this, "Please Select your gender", Toast.LENGTH_SHORT).show();
        } else {
            radiovalue = ((RadioButton) findViewById(radiogroupmale.getCheckedRadioButtonId())).getText().toString();

            if (radiovalue.matches("male")) {
                fathersname = "";
                fathersaddress = "";
            } else {
                fathersname = etfathername.getText().toString();
                fathersaddress = etfathersaddress.getText().toString();
            }

            if (occupatonradio.getCheckedRadioButtonId() == -1) {
                ocuupationradio = "";
            } else {
                ocuupationradio = ((RadioButton) findViewById(occupatonradio.getCheckedRadioButtonId())).getText().toString();
            }
            if (staystatus.getCheckedRadioButtonId() == -1) {
                staystatus1 = "";
            } else {
                staystatus1 = ((RadioButton) findViewById(staystatus.getCheckedRadioButtonId())).getText().toString();
            }
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.show();
            /*JSONObject jsonObject = new JSONObject();
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
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
            String s = "http://electionapp.uxservices.in/Web_Services/Add_Voter.asmx/Voter_Add?" +
                    "ECandidateFName_eng=" + fname.getText().toString() + "&ECandidateMName_Eng=" + mname.getText().toString()
                    + "&ECandidateLName_Eng=" + lname.getText().toString() +
                    "&ECandidateFName_Marthi=vvv&ECandidateMName_Marthi=jj&ECandidateLName_Marthi=bbb&gender=" + radiovalue +
                    "&ECandidateAddress=" + address.getText().toString() + "&ECandidatePhoneNo=" + mobilenumber.getText().toString()
                    + "&ECandidateImg=jhh&EWardId=123" +
                    "&VoterwardNumber=" + warspinpos + "&age=23&dob=" + dateofbith.getText().toString() + "&houseno=" + housenumber.getText().toString() +
                    "&occupation=" + ocuupationradio + "&occp_details=" + occupationdetails.getText().toString() +
                    "&govt_sno=" + gln.getText().toString() + "&currentstay_status=" + staystatus1 + "&idcardno=" + idcardno.getText().toString()
                    + "&fatherhasbandname=" + fathersname +
                    "&Genderadd=" + fathersaddress + "&Pin_no=" + picode.getText().toString() + "&residentialtype=" + resindentialspinpos +
                    "&regional=" + regionalspinpos + "&caste=" + castespinpos +
                    "&Lang=" + languagespinpos + "&buildingnam=" + societyname.getText().toString() + "&wing_no=" + wingnumber.getText().toString()
                    + "&flatno=" + flatnumber.getText().toString() + "&floorno=" + floornumber.getText().toString() +
                    "&street_no=" + staydetails.getText().toString() + "sdzx" + "&aadharcard_no=" + aadhar.getText().toString() +
                    "&state=mp&distric=" + districspinpos + "&sub_distric=" + subdistrictspinpos + "&cityvilage=" + villagespinpos + "&area_id=" + areapos +
                    "&ward_id=" + warspinpos + "&staydetails=" + staystatus1 + "";
            System.out.println(s);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, s, new Response.Listener<String>() {
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
                        if (jsonArray.getJSONObject(0).getString("Status").matches("true")) {
                            progressDialog.hide();
                            Toast.makeText(AddNewVoter.this, jsonArray.getJSONObject(0).getString("msg"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddNewVoter.this, DashBoard.class);
                            finish();
                            startActivity(intent);
                        } else {
                            progressDialog.hide();
                            Toast.makeText(AddNewVoter.this, "please check internet settings", Toast.LENGTH_SHORT).show();
                        }

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


    private void loadarea() {
        SharedPreferences sharedPreference = getSharedPreferences("userid", MODE_PRIVATE);
        int user_id = sharedPreference.getInt("user_id", 1);
        String url = "http://electionapp.uxservices.in/Web_Services/Area_List.asmx/Getlist?user_id=" + user_id;
        System.out.println("thisurl" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                try {
                    System.out.println("this" + jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));

                    JSONArray jsonArray1 = new JSONArray(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));
                    JSONObject jsonObject = jsonArray1.getJSONObject(0);
                    Type listType = new TypeToken<List<AreaModel>>() {
                    }.getType();
                    final List<AreaModel> yourList = new Gson().fromJson(String.valueOf(jsonArray1), listType);
                    SharedPreferences sharedPreferences = getSharedPreferences("userid", MODE_PRIVATE);
                    List<String> list = new ArrayList<String>();
                    list.add("Select Area");
                    for (int i = 0; i < yourList.size(); i++) {
                        list.add(yourList.get(i).getElection_Area_Name());
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddNewVoter.this,
                            android.R.layout.simple_spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    areaspin.setAdapter(dataAdapter);
                    areaspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            areapos = yourList.get(position).getElection_Area_id();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


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

    private void loadward() {
        SharedPreferences sharedPreference = getSharedPreferences("userid", MODE_PRIVATE);
        int user_id = sharedPreference.getInt("user_id", 1);
        String url = "http://electionapp.uxservices.in/Web_Services/Bind_Ward.asmx/Ward_Bind";
        System.out.println("thisurl" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                try {

                    final JSONArray jsonArray1 = new JSONArray(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));
                    System.out.println("ward" + jsonArray1);

                    JSONObject jsonObject = jsonArray1.getJSONObject(0);
                    final List<String> list = new ArrayList<String>();
                    list.add("Select Ward");
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        list.add(jsonArray1.getJSONObject(i).getString("WardName"));
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddNewVoter.this,
                            android.R.layout.simple_spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    warspin.setAdapter(dataAdapter);
                    warspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            try {
                                if (position == 0) {
                                    warspinpos = 0;
                                } else {
                                    warspinpos = jsonArray1.getJSONObject(position - 1).getInt("WardId");

                                }
                                //    Toast.makeText(AddNewVoter.this, String.valueOf(warspinpos), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
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

    private void loadbooth() {
        SharedPreferences sharedPreference = getSharedPreferences("userid", MODE_PRIVATE);
        int user_id = sharedPreference.getInt("user_id", 1);
        String url = "http://electionapp.uxservices.in/Web_Services/Bind_Booth.asmx/Booth_Bind";
        System.out.println("thisurl" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                try {

                    final JSONArray jsonArray3 = new JSONArray(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));
                    System.out.println("ward" + jsonArray3);

                    JSONObject jsonObject = jsonArray3.getJSONObject(0);
                    List<String> list = new ArrayList<String>();
                    list.add("Select Booth");

                    for (int i = 0; i < jsonArray3.length(); i++) {
                        list.add(jsonArray3.getJSONObject(i).getString("ElectionBoothName"));
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddNewVoter.this,
                            android.R.layout.simple_spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    boothspin.setAdapter(dataAdapter);
                    boothspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            try {
                                if (position == 0) {
                                    boothspinpos = 0;
                                } else {
                                    boothspinpos = jsonArray3.getJSONObject(position - 1).getInt("ElectionBoothId");
                                }
                                //  Toast.makeText(AddNewVoter.this, String.valueOf(boothspinpos), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
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

    private void loadcaste() {
        SharedPreferences sharedPreference = getSharedPreferences("userid", MODE_PRIVATE);
        int user_id = sharedPreference.getInt("user_id", 1);
        String url = "http://electionapp.uxservices.in/Web_Services/Caste_Dropdown.asmx/BindDatatoDropdown";
        System.out.println("thisurl" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                try {

                    final JSONArray jsonArray2 = new JSONArray(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));
                    System.out.println("ward" + jsonArray2);

                    JSONObject jsonObject = jsonArray2.getJSONObject(0);
                    List<String> list = new ArrayList<String>();
                    list.add("Select Caste");

                    for (int i = 0; i < jsonArray2.length(); i++) {
                        list.add(jsonArray2.getJSONObject(i).getString("ElectionCasteName"));
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddNewVoter.this,
                            android.R.layout.simple_spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    castespin.setAdapter(dataAdapter);
                    castespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            try {
                                if (position == 0) {
                                    castespinpos = 0;
                                } else {
                                    castespinpos = jsonArray2.getJSONObject(position - 1).getInt("ElectionCasteId");
                                }
                                // Toast.makeText(AddNewVoter.this, String.valueOf(castespinpos), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
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

    private void loadregion() {
        SharedPreferences sharedPreference = getSharedPreferences("userid", MODE_PRIVATE);
        int user_id = sharedPreference.getInt("user_id", 1);
        String url = "http://electionapp.uxservices.in/Web_Services/Bind_Regional.asmx/Regional_Bind";
        System.out.println("thisurl" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                final String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                try {

                    final JSONArray jsonArray5 = new JSONArray(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));
                    System.out.println("ward" + jsonArray5);

                    JSONObject jsonObject = jsonArray5.getJSONObject(0);
                    List<String> list = new ArrayList<String>();
                    list.add("Select Region");

                    for (int i = 0; i < jsonArray5.length(); i++) {
                        list.add(jsonArray5.getJSONObject(i).getString("ERegionalName"));
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddNewVoter.this,
                            android.R.layout.simple_spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    regionalspin.setAdapter(dataAdapter);
                    regionalspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            try {
                                if (position == 0) {
                                    regionalspinpos = 0;
                                } else {
                                    regionalspinpos = jsonArray5.getJSONObject(position - 1).getInt("ERegionalId");
                                }
                                //  Toast.makeText(AddNewVoter.this, String.valueOf(regionalspinpos), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
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

    private void loadlanguage() {
        SharedPreferences sharedPreference = getSharedPreferences("userid", MODE_PRIVATE);
        int user_id = sharedPreference.getInt("user_id", 1);
        String url = "http://electionapp.uxservices.in/Web_Services/Bind_Language.asmx/Language_Bind";
        System.out.println("thisurl" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                final String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                try {

                    final JSONArray jsonArray6 = new JSONArray(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));
                    System.out.println("ward" + jsonArray6);

                    JSONObject jsonObject = jsonArray6.getJSONObject(0);
                    List<String> list = new ArrayList<String>();
                    list.add("Select Language");

                    for (int i = 0; i < jsonArray6.length(); i++) {
                        list.add(jsonArray6.getJSONObject(i).getString("ELanguageName"));
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddNewVoter.this,
                            android.R.layout.simple_spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    languagespin.setAdapter(dataAdapter);
                    languagespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            try {
                                if (position == 0) {
                                    languagespinpos = 0;
                                } else {
                                    languagespinpos = jsonArray6.getJSONObject(position - 1).getInt("ELanguageId");
                                }
                                // Toast.makeText(AddNewVoter.this, String.valueOf(languagespinpos), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
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

    private void loadresidential() {
        SharedPreferences sharedPreference = getSharedPreferences("userid", MODE_PRIVATE);
        int user_id = sharedPreference.getInt("user_id", 1);
        String url = "http://electionapp.uxservices.in/Web_Services/Bind_Residential.asmx/Residentail_Bind";
        System.out.println("thisurl" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                final String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                try {

                    final JSONArray jsonArray7 = new JSONArray(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));
                    System.out.println("ward" + jsonArray7);

                    JSONObject jsonObject = jsonArray7.getJSONObject(0);
                    List<String> list = new ArrayList<String>();
                    list.add("Select Residential Type");

                    for (int i = 0; i < jsonArray7.length(); i++) {
                        list.add(jsonArray7.getJSONObject(i).getString("EResidentialName"));
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddNewVoter.this,
                            android.R.layout.simple_spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    resindentialspin.setAdapter(dataAdapter);
                    resindentialspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            try {
                                if (position == 0) {
                                    resindentialspinpos = 0;
                                } else {
                                    resindentialspinpos = jsonArray7.getJSONObject(position - 1).getInt("EResidentialId");
                                }
                                // Toast.makeText(AddNewVoter.this, String.valueOf(resindentialspinpos), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
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

    private void loaddistrict() {
        SharedPreferences sharedPreference = getSharedPreferences("userid", MODE_PRIVATE);
        int user_id = sharedPreference.getInt("user_id", 1);
        String url = "http://electionapp.uxservices.in/Web_Services/Bind_Distric.asmx/Distric_Bind";
        System.out.println("thisurl" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                try {

                    final JSONArray jsonArray8 = new JSONArray(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));
                    System.out.println("ward" + jsonArray8);

                    JSONObject jsonObject = jsonArray8.getJSONObject(0);
                    final List<String> list = new ArrayList<String>();
                    list.add("Select Area");

                    for (int i = 0; i < jsonArray8.length(); i++) {
                        list.add(jsonArray8.getJSONObject(i).getString("DistricName"));
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddNewVoter.this,
                            android.R.layout.simple_spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    districspin.setAdapter(dataAdapter);
                    districspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            try {
                                if (position == 0) {
                                    districspinpos = 0;
                                } else {
                                    x = jsonArray8.getJSONObject(position - 1).getInt("DistricId");
                                    districspinpos = jsonArray8.getJSONObject(position - 1).getInt("DistricId");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            SharedPreferences sharedPreference = getSharedPreferences("userid", MODE_PRIVATE);

                            String url = "http://electionapp.uxservices.in/Web_Services/Bind_SubDistric.asmx/SubDistric_Bind?Distric_id=" + x;
                            System.out.println("thisurl" + url);
                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    final String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                                    try {

                                        final JSONArray jsonArray9 = new JSONArray(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));
                                        System.out.println("ward" + jsonArray9);

                                      //  JSONObject jsonObject = jsonArray9.getJSONObject(0);
                                        List<String> list = new ArrayList<String>();
                                        for (int i = 0; i < jsonArray9.length(); i++) {
                                            list.add(jsonArray9.getJSONObject(i).getString("SubDistricName"));
                                        }
                                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddNewVoter.this,
                                                android.R.layout.simple_spinner_item, list);
                                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        subdistrictspin.setAdapter(dataAdapter);
                                        subdistrictspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                try {
                                                    y = jsonArray9.getJSONObject(position).getInt("SubDistricId");
                                                    subdistrictspinpos = jsonArray9.getJSONObject(position).getInt("SubDistricId");

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                SharedPreferences sharedPreference = getSharedPreferences("userid", MODE_PRIVATE);
                                                int user_id = sharedPreference.getInt("user_id", 1);
                                                String url = "http://electionapp.uxservices.in/Web_Services/Bind_Village.asmx/Village_bind?Sub_distric_id=" + y;
                                                System.out.println("thisurl" + url);
                                                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                                                        try {

                                                            final JSONArray jsonArray10 = new JSONArray(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));
                                                            System.out.println("ward" + jsonArray10);

                                                            JSONObject jsonObject = jsonArray10.getJSONObject(0);
                                                            List<String> list = new ArrayList<String>();
                                                            for (int i = 0; i < jsonArray10.length(); i++) {
                                                                list.add(jsonArray10.getJSONObject(i).getString("CityName"));
                                                            }
                                                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddNewVoter.this,
                                                                    android.R.layout.simple_spinner_item, list);
                                                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                            villagespin.setAdapter(dataAdapter);
                                                            villagespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                @Override
                                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                                    try {
                                                                        villagespinpos = jsonArray10.getJSONObject(position).getString("CityId");
                                                                        System.out.println("Ward" + warspinpos + "Booth" + boothspinpos + "Residential" + resindentialspinpos + "Language" + languagespinpos
                                                                                + "Regional" + regionalspinpos + "Caste" + castespinpos + "District" + districspinpos + "subdistrict" + subdistrictspinpos +
                                                                                "Village" + villagespinpos);
                                                                    } catch (JSONException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }

                                                                @Override
                                                                public void onNothingSelected(AdapterView<?> parent) {

                                                                }
                                                            });
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }


                                                    }
                                                }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {

                                                    }
                                                });
                                                RequestQueue requestQueue = Volley.newRequestQueue(AddNewVoter.this);
                                                requestQueue.add(stringRequest);

                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> parent) {

                                            }
                                        });
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                            RequestQueue requestQueue = Volley.newRequestQueue(AddNewVoter.this);
                            requestQueue.add(stringRequest);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

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

    private void loadState() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Select State");
        strings.add("Maharashtra");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddNewVoter.this,
                android.R.layout.simple_spinner_item, strings);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statespin.setAdapter(dataAdapter);
        statespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  areapos=yourList.get(position).
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
            case R.id.housenumber:

                housenumber.clearFocus();

                idcardno.requestFocus();


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

            case R.id.occupationdetails:

                occupationdetails.clearFocus();
                picode.requestFocus();

                break;

            case R.id.pincode:

                picode.clearFocus();
                staydetails.requestFocus();

                break;

            case R.id.staydetails:

                staydetails.clearFocus();
                sendreq();
                break;

        }
        return false;
    }
}
