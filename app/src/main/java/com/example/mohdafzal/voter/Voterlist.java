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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import java.util.List;

public class Voterlist extends BaseActivity {
    private EditText editText;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_voterlist);
        SharedPreferences sharedPreference2 = getSharedPreferences("userid", MODE_PRIVATE);
        int user_id2 = sharedPreference2.getInt("user_id", 1);
        getSupportActionBar().setTitle("Voters List");

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load
        // titles
        // from
        // strings.xml

        navMenuIcons = getResources()
            .obtainTypedArray(R.array.nav_drawer_icons);// load icons from
        // strings.xml

        set(navMenuTitles, navMenuIcons);
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

        if (user_id2 == 5 || user_id2 == 6) {
            button.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.GONE);
        }
        if (user_id2==6){
            getVoterList();
        }else{
            sendreq(user_id2);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Voterlist.this, AddNewVoter.class);
                intent.putExtra("voter_id", 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions
                        .makeSceneTransitionAnimation(Voterlist.this).toBundle());
                } else {
                    startActivity(intent);
                }

            }
        });
    }

    public void sendreq() {
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

                        ((TextView) findViewById(R.id.voterCount)).setText("Voters Count : " + yourList.size());

                        SharedPreferences sharedPreferences = getSharedPreferences("userid", MODE_PRIVATE);
                        int id = sharedPreferences.getInt("user_id", 1);
                        VoterAdapter areAdapter = new VoterAdapter(Voterlist.this, yourList, 0);
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.workerrec);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Voterlist.this);
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

    @Override
    protected void onResume() {
        super.onResume();

    }
    public void sendreq(int user_id2) {


        String url2 = "http://electionapp.uxservices.in/Web_Services/Manager_Worker_list.asmx/Manager_Worker_List?user_id=" + user_id2;
        System.out.println("thisurl" + url2);
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                findViewById(R.id.blurredloader).setVisibility(View.GONE);

                String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                try {
                    System.out.println("this" + jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));

                    JSONObject jsonObject = new JSONObject(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "").replace("<string xmlns=\"http://electionapp.uxservices.in\">", ""));
                    JSONArray jsonArray1=jsonObject.getJSONArray("data:");
                    Type listType = new TypeToken<List<UpVibhagModel>>() {
                    }.getType();
                    List<UpVibhagModel> yourList = new Gson().fromJson(String.valueOf(jsonArray1), listType);
                    SharedPreferences sharedPreferences = getSharedPreferences("userid", MODE_PRIVATE);
                    int id = sharedPreferences.getInt("user_id", 1);
                    UpvibhagVotersAdapter areAdapter = new UpvibhagVotersAdapter(Voterlist.this, yourList);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.workerrec);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Voterlist.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(areAdapter);


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
        RequestQueue requestQueue2 = Volley.newRequestQueue(this);
        requestQueue2.add(stringRequest2);

    }
    public void getVoterList(){
        {

            SharedPreferences sharedPreference1 = getSharedPreferences("userid", MODE_PRIVATE);
            int user_id1 = sharedPreference1.getInt("user_id", 1);
            String url1 = "http://electionapp.uxservices.in/Web_Services/Manager_Voter_List.asmx/Voter_List?user_id=" + user_id1;
            System.out.println("thisurl" + url1);
            StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    findViewById(R.id.blurredloader).setVisibility(View.GONE);

                    String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                    try {
                        System.out.println("this" + jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));
                        JSONObject jsonObject1 = new JSONObject(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "").replace("<string xmlns=\"http://tempuri.org/\">", ""));
                        JSONArray jsonArray1 = jsonObject1.getJSONArray("list:");

                        JSONObject jsonObject = jsonArray1.getJSONObject(0);
                        Type listType = new TypeToken<List<VoterModel>>() {
                        }.getType();
                        List<VoterModel> yourList = new Gson().fromJson(String.valueOf(jsonArray1), listType);
                        ((TextView) findViewById(R.id.voterCount)).setText("Voters Count : " + yourList.size());

                        SharedPreferences sharedPreferences = getSharedPreferences("userid", MODE_PRIVATE);
                        int id = sharedPreferences.getInt("user_id", 1);
                        VoterAdapter areAdapter = new VoterAdapter(Voterlist.this, yourList, 0);
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.workerrec);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Voterlist.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(areAdapter);


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
            RequestQueue requestQueue1 = Volley.newRequestQueue(this);
            requestQueue1.add(stringRequest1);

        }
    }

}
