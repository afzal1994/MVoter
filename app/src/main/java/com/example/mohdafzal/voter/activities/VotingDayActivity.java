package com.example.mohdafzal.voter.activities;

import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.example.mohdafzal.voter.adapter.VotingDayAdapter;
import com.example.mohdafzal.voter.models.VotingDayModel;
import com.example.mohdafzal.voter.utils.IContants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.List;

public class VotingDayActivity extends BaseActivityNavigation {

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private RecyclerView upVibhagRecycler;
    private TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_day);
        SharedPreferences sharedPreference2 = getSharedPreferences("userid", MODE_PRIVATE);
        int user_id2 = sharedPreference2.getInt("user_option", 0);
        getSupportActionBar().setTitle(IContants.getName(user_id2));

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load
        // titles
        // from
        // strings.xml

        navMenuIcons = getResources()
            .obtainTypedArray(R.array.nav_drawer_icons);// load icons from
        // strings.xml

        set(navMenuTitles, navMenuIcons);
        time = (TextView) findViewById(R.id.time);
        CountDownTimer newtimer = new CountDownTimer(1000000000, 1000) {

            public void onTick(long millisUntilFinished) {
                Calendar c = Calendar.getInstance();
                int a = c.get(Calendar.AM_PM);
                String text = null;
                if (a == Calendar.AM) {
                    text = "AM";
                } else {
                    text = "PM";
                }
                time.setText(c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND) + " " + text);
            }

            public void onFinish() {

            }
        };
        newtimer.start();

        sendreq(user_id2);
        sendreqTopCount();


    }

    public void sendreq(int user_id2) {
        getSupportActionBar().setTitle(IContants.getName(user_id2));

        String url2 = null;
        url2 = "http://electionapp.uxservices.in/Web_Services/Voting_Day_List.asmx/Voter_Day?user_id=" + user_id2;

        System.out.println("thisurl" + url2);
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                findViewById(R.id.blurredloader).setVisibility(View.GONE);

                String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                try {
                    System.out.println("this" + jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));
                    JSONArray jsonArray1 = null;
                    JSONObject jsonObject = new JSONObject(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "").replace("<string xmlns=\"http://electionapp.uxservices.in\">", ""));

                    jsonArray1 = jsonObject.getJSONArray("data:");

                    Type listType = new TypeToken<List<VotingDayModel>>() {
                    }.getType();
                    List<VotingDayModel> yourList = new Gson().fromJson(String.valueOf(jsonArray1), listType);
                    SharedPreferences sharedPreferences = getSharedPreferences("userid", MODE_PRIVATE);
                    int id = sharedPreferences.getInt("user_id", 1);
                    VotingDayAdapter areAdapter = new VotingDayAdapter(VotingDayActivity.this, yourList);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.upvibhagRecycler);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(VotingDayActivity.this);
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

    private void sendreqTopCount() {
        SharedPreferences sharedPreference2 = getSharedPreferences("userid", MODE_PRIVATE);
        int user_id2 = sharedPreference2.getInt("user_option", 0);
        String url2 = null;
        url2 = "http://electionapp.uxservices.in/Web_Services/Total_Voter_List_Voting_Day.asmx/Voter_Count?user_id=" + user_id2;

        System.out.println("thisurl" + url2);
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                findViewById(R.id.blurredloader).setVisibility(View.GONE);

                String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                try {
                    System.out.println("this" + jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));
                    JSONArray jsonArray1 = null;
                    JSONArray jsonObject = new JSONArray(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "").replace("<string xmlns=\"http://electionapp.uxservices.in\">", ""));
                    JSONObject jsonObject1 = jsonObject.getJSONObject(0);
                    JSONObject jsonObject2 = jsonObject1.getJSONArray("Total Voter Count").getJSONObject(0);
                    int totalVoterCount = jsonObject2.getInt("Total_VoterCount");
                    int doneVoterCount = jsonObject1.getJSONArray("Done voter Counts").getJSONObject(0).getInt("Done_VoterCount");
                    int pendingVoterCount = jsonObject1.getJSONArray("Pending voter Counts").getJSONObject(0).getInt("Pending_VoterCount");
                    TextView total = (TextView) findViewById(R.id.total);
                    TextView done = (TextView) findViewById(R.id.done);
                    TextView pending = (TextView) findViewById(R.id.pending);
                    total.setText(String.valueOf(totalVoterCount));
                    done.setText(String.valueOf(doneVoterCount));
                    pending.setText(String.valueOf(pendingVoterCount));

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
