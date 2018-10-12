package com.example.mohdafzal.voter;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohdafzal.voter.navigationdrawer.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class VoterAccordingArealist extends BaseActivity {
    private EditText editText;
    public static int area_id;
    private Spinner areaspin;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_voter_according_arealist);
        getSupportActionBar().setTitle("Voters List");

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load
        // titles
        // from
        // strings.xml

        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);// load icons from
        // strings.xml

        set(navMenuTitles, navMenuIcons);
        Bundle extras = getIntent().getExtras();
        if (NetworkUtil.isNetworkAvailable(this)) {
            loadarea();
        } else {
            findViewById(R.id.blurredloader).setVisibility(View.GONE);

            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
        area_id = extras.getInt("area_id");
        editText = (EditText) findViewById(R.id.searchkeyword);
        ImageView button1 = (ImageView) findViewById(R.id.searchbutton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendreq();

            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.workerrec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button button = (Button) findViewById(R.id.addnewvoter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VoterAccordingArealist.this, AddNewVoter.class);
                intent.putExtra("voter_id", 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions
                            .makeSceneTransitionAnimation(VoterAccordingArealist.this).toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });
    }

    private void loadarea() {
        areaspin = (Spinner) findViewById(R.id.areaspinner);

        SharedPreferences sharedPreference = getSharedPreferences("userid", MODE_PRIVATE);
        int user_id = sharedPreference.getInt("user_id", 1);
        String url = "http://electionapp.uxservices.in/Web_Services/Area_List.asmx/Getlist?user_id=" + user_id;
        System.out.println("thisurl" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                findViewById(R.id.blurredloader).setVisibility(View.GONE);

                String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                try {
                    System.out.println("this" + jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));

                    JSONArray jsonArray1 = new JSONArray(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));
                    JSONObject jsonObject = jsonArray1.getJSONObject(0);
                    Type listType = new TypeToken<List<AreaModel>>() {
                    }.getType();
                    final List<AreaModel> yourList = new Gson().fromJson(String.valueOf(jsonArray1), listType);
                    ArrayList<String> strings = new ArrayList<>();
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        strings.add(jsonArray1.getJSONObject(i).getString("Election_Area_Name"));
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(VoterAccordingArealist.this, R.layout.spin, strings);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    areaspin.setAdapter(adapter);
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        if (!jsonArray1.getJSONObject(i).get("Election_Area_id").equals(null)) {
                            if (area_id == jsonArray1.getJSONObject(i).getInt("Election_Area_id")) {
                                areaspin.setSelection(i);
                            }
                        }
                    }
                    areaspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            //  areapos=yourList.get(position).

                                getSupportActionBar().setTitle("Voter List (" + areaspin.getSelectedItem().toString() + ")");


                            area_id = yourList.get(position).getElection_Area_id();
                            getData();

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
                findViewById(R.id.blurredloader).setVisibility(View.GONE);

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void sendreq() {
        if (NetworkUtil.isNetworkAvailable(this)) {

            SharedPreferences sharedPreference2 = getSharedPreferences("userid", MODE_PRIVATE);
            int user_id2 = sharedPreference2.getInt("user_id", 1);
            String url2 = "http://electionapp.uxservices.in/Web_Services/Search_Voter.asmx/search?user_id=" + user_id2 + "&search=" + editText.getText().toString();
            System.out.println("thisurl" + url2);
            StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                    try {
                        System.out.println("this" + jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));

                        JSONObject jsonObject = new JSONObject(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "").replace("<string xmlns=\"http://electionapp.uxservices.in\">", ""));
                        JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                        Type listType = new TypeToken<List<VoterModel>>() {
                        }.getType();
                        List<VoterModel> yourList = new Gson().fromJson(String.valueOf(jsonArray1), listType);
                        SharedPreferences sharedPreferences = getSharedPreferences("userid", MODE_PRIVATE);
                        int id = sharedPreferences.getInt("user_id", 1);
                        VoterAdapter areAdapter = new VoterAdapter(VoterAccordingArealist.this, yourList, 0);
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.workerrec);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(VoterAccordingArealist.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(areAdapter);


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

    protected void getData() {
        if (NetworkUtil.isNetworkAvailable(this)) {

            SharedPreferences sharedPreference1 = getSharedPreferences("userid", MODE_PRIVATE);
            int user_id1 = sharedPreference1.getInt("user_id", 1);
            String url1 = "http://electionapp.uxservices.in/Web_Services/Area_Voter_List.asmx/Getlist?area_id=" + area_id;
            System.out.println("thisurl" + url1);
            StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                    try {
                        System.out.println("this" + jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));

                        JSONArray jsonArray1 = new JSONArray(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "").replace("<string xmlns=\"http://tempuri.org/\">", ""));
                        JSONObject jsonObject = jsonArray1.getJSONObject(0);
                        Type listType = new TypeToken<List<VoterModel>>() {
                        }.getType();
                        List<VoterModel> yourList = new Gson().fromJson(String.valueOf(jsonArray1), listType);
                        SharedPreferences sharedPreferences = getSharedPreferences("userid", MODE_PRIVATE);
                        int id = sharedPreferences.getInt("user_id", 1);
                        VoterAdapter areAdapter = new VoterAdapter(VoterAccordingArealist.this, yourList, 0);
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.workerrec);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(VoterAccordingArealist.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(areAdapter);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            RequestQueue requestQueue1 = Volley.newRequestQueue(this);
            requestQueue1.add(stringRequest1);

        } else {
            Toast.makeText(this, "Not connected to internet", Toast.LENGTH_SHORT).show();
        }
    }
}