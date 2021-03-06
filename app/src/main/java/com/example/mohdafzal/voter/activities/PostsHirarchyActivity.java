package com.example.mohdafzal.voter.activities;

import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohdafzal.voter.R;
import com.example.mohdafzal.voter.adapter.PostsHirarchyAdapter;
import com.example.mohdafzal.voter.models.PostsHirarchyModel;
import com.example.mohdafzal.voter.utils.IContants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class PostsHirarchyActivity extends BaseActivityNavigation {

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private RecyclerView upVibhagRecycler;
    private TextView outOfTownCount;
    private TextView possibleVotesCount;
    private TextView actualVotersCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_hirarchy);
        SharedPreferences sharedPreference2 = getSharedPreferences("userid", MODE_PRIVATE);
        int user_id2 = sharedPreference2.getInt("user_id", 1);
        getSupportActionBar().setTitle(IContants.getName(user_id2));

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load
        // titles
        // from
        // strings.xml

        navMenuIcons = getResources()
            .obtainTypedArray(R.array.nav_drawer_icons);// load icons from
        // strings.xml

        set(navMenuTitles, navMenuIcons);

        possibleVotesCount = (TextView) findViewById(R.id.possibleVotesCount);
        outOfTownCount = (TextView) findViewById(R.id.outOfTownCount);
        actualVotersCount = (TextView) findViewById(R.id.actualVotersCount);

        sendreq(user_id2);
        getCount(user_id2);


    }

    public void sendreq(int user_id2) {
        getSupportActionBar().setTitle(IContants.getName(user_id2));


        String url2 = "http://electionapp.uxservices.in/Web_Services/Manager_Worker_list.asmx/Manager_Worker_List?user_id=" + user_id2;
        System.out.println("thisurl" + url2);
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                findViewById(R.id.blurredloader).setVisibility(View.GONE);

                String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                try {
                    System.out.println("thisone" + jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));

                    JSONObject jsonObject = new JSONObject(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "").replace("<string xmlns=\"http://electionapp.uxservices.in\">", ""));
                    JSONArray jsonArray1 = jsonObject.getJSONArray("data:");
                    Type listType = new TypeToken<List<PostsHirarchyModel>>() {
                    }.getType();
                    List<PostsHirarchyModel> yourList = new Gson().fromJson(String.valueOf(jsonArray1), listType);
                    SharedPreferences sharedPreferences = getSharedPreferences("userid", MODE_PRIVATE);
                    int id = sharedPreferences.getInt("user_id", 1);
                    PostsHirarchyAdapter areAdapter = new PostsHirarchyAdapter(PostsHirarchyActivity.this, yourList);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.upvibhagRecycler);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PostsHirarchyActivity.this);
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

    public void getCount(int user_id2) {


        String url2 = "http://electionapp.uxservices.in/Web_Services/My_Upavibhag_Counts.asmx/Counts?user_id=" + user_id2;
        System.out.println("thisurl" + url2);
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                findViewById(R.id.blurredloader).setVisibility(View.GONE);

                String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                try {
                    System.out.println("this" + jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));
                    JSONArray jsonArray1 = new JSONArray(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "").replace("<string xmlns=\"http://electionapp.uxservices.in\">", ""));
                    JSONObject jsonObject = jsonArray1.getJSONObject(0);
                    String totalVoterCount = jsonObject.getString("Total Voter Count");
                    String possible_voter_count = jsonObject.getString("Actual voter Counts");
                    possibleVotesCount.setText(possible_voter_count);
                    actualVotersCount.setText(totalVoterCount);
                    outOfTownCount.setText(jsonObject.getString("out of town"));


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

}

