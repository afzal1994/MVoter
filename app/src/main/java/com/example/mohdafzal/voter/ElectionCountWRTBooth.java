package com.example.mohdafzal.voter;

import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohdafzal.voter.Adapter.CountAdapter;
import com.example.mohdafzal.voter.navigationdrawer.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class ElectionCountWRTBooth extends BaseActivity {

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private AbsListView countRecycler;
    private int booth_id;
    private int area_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_election_count_wrtbooth);
        Bundle extras = getIntent().getExtras();

        getSupportActionBar().setTitle(extras.getString("booth_name"));
         booth_id=extras.getInt("booth_id");
         area_id=extras.getInt("area_id");

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load
        // titles
        // from
        // strings.xml

        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);// load icons from
        // strings.xml

        set(navMenuTitles, navMenuIcons);
    }
    private void loadcount() {
        SharedPreferences sharedPreference = this.getSharedPreferences("userid", MODE_PRIVATE);
        int user_id = sharedPreference.getInt("user_id", 1);
        String url = "http://electionapp.uxservices.in/Web_Services/Elections_Count.asmx/Results?userid=" + user_id+"&areaid="+area_id+"&booth_id="+booth_id;
        System.out.println("thisurl" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                try {
                    System.out.println("this" + jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));

                    JSONObject jsonArray1 = new JSONObject(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));
                    if (jsonArray1.equals(null)){
                        countRecycler.setVisibility(View.GONE);
                    }
                    else {
                        countRecycler.setVisibility(View.VISIBLE);

                        JSONArray jsonArray2 = jsonArray1.getJSONArray("data:");

                        Type listType = new TypeToken<List<VotingCountModel>>() {
                        }.getType();
                        List<VotingCountModel> yourList = new Gson().fromJson(String.valueOf(jsonArray2), listType);
                        /* ElectionCountWRTBoothAdapter countAdapter = new ElectionCountWRTBoothAdapter(ElectionCountWRTBooth.this, yourList);
                        countRecycler.setAdapter(countAdapter);*/
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(ElectionCountWRTBooth.this);
        requestQueue.add(stringRequest);

    }
}
