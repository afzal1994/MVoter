package com.example.mohdafzal.voter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

import java.lang.reflect.Type;
import java.util.List;

public class OutOfTownActivity extends BaseActivity {

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private RecyclerView upVibhagRecycler;
    private Button upvibhagButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_of_town);
        getSupportActionBar().setTitle("Out of town list");

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load
        // titles
        // from
        // strings.xml

        navMenuIcons = getResources()
            .obtainTypedArray(R.array.nav_drawer_icons);// load icons from
        // strings.xml

        set(navMenuTitles, navMenuIcons);
        upvibhagButton = (Button) findViewById(R.id.upvibhagButton);
        upvibhagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OutOfTownActivity.this, VibhagPramukhList.class);
                startActivity(intent);
            }
        });
        sendreq();
    }

    private void sendreq() {
findViewById(R.id.blurredloader).setVisibility(View.VISIBLE);
        SharedPreferences sharedPreference2 = getSharedPreferences("userid", MODE_PRIVATE);
        int user_id2 = sharedPreference2.getInt("user_id", 1);
        String url2 = "http://electionapp.uxservices.in/Web_Services/Out_sider_voter_list_with_Head.asmx/Voter_List_With_Head?created_id=" + user_id2;
        System.out.println("thisurl" + url2);
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                findViewById(R.id.blurredloader).setVisibility(View.GONE);

                String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                try {
                    System.out.println("this" + jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));

                    JSONArray jsonArray1 = new JSONArray(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "").replace("<string xmlns=\"http://electionapp.uxservices.in\">", ""));
                    Type listType = new TypeToken<List<VoterModel>>() {
                    }.getType();
                    List<VoterModel> yourList = new Gson().fromJson(String.valueOf(jsonArray1), listType);
                    ((TextView) findViewById(R.id.text)).setText("Voters Count : " + yourList.size());

                    SharedPreferences sharedPreferences = getSharedPreferences("userid", MODE_PRIVATE);
                    int id = sharedPreferences.getInt("user_id", 1);
                    VoterAdapter areAdapter = new VoterAdapter(OutOfTownActivity.this, yourList, 10);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.upvibhagRecycler);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(OutOfTownActivity.this);
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

}
