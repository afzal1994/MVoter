package com.example.mohdafzal.voter.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohdafzal.voter.R;
import com.example.mohdafzal.voter.adapter.PendingDoneAdapter;
import com.example.mohdafzal.voter.models.VoterModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.List;

public class PendingDoneVotersList extends BaseActivityNavigation {

    private TextView time;
    private LinearLayout doneButton;
    private LinearLayout updateStatus;
    private int id;
    private LinearLayout pendingButton;
    private TextView total, done, pending;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private RelativeLayout call;
    private Button updateStatusText;
    private Button pendingText;
    private Button doneText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_done_voters_list);
        getSupportActionBar().setTitle("Voterlist");

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


        total = (TextView) findViewById(R.id.total);
        done = (TextView) findViewById(R.id.done);
        pending = (TextView) findViewById(R.id.pending);
        call = (RelativeLayout) findViewById(R.id.phone);
        updateStatusText = (Button) findViewById(R.id.updateStatusText);
        pendingText = (Button) findViewById(R.id.pendingText);
        doneText = (Button) findViewById(R.id.doneText);
        TextView upVibhag = (TextView) findViewById(R.id.upVibhag);
        TextView name = (TextView) findViewById(R.id.name);
        Intent intent = getIntent();


        if (intent.hasExtra("upvibhagName")) {
            final Bundle bundle = intent.getExtras();
            upVibhag.setText(bundle.getString("upvibhagName"));
            name.setText(bundle.getString("incharge_name"));
            total.setText(String.valueOf(Integer.parseInt(bundle.getString("done")) + Integer.parseInt(bundle.getString("pending"))));
            done.setText(bundle.getString("done"));
            pending.setText(bundle.getString("pending"));
            id = bundle.getInt("id");
            if (bundle.getString("phone_number") == null) {
                ImageView callImage = (ImageView) findViewById(R.id.call_image);

                callImage.setImageResource(R.drawable.call_grey);


            }
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bundle.getString("phone_number") != null) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + bundle.getString("phone_number")));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(intent, ActivityOptions
                                .makeSceneTransitionAnimation(PendingDoneVotersList.this).toBundle());
                        } else {
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(PendingDoneVotersList.this, "No Phone Number Provided", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            id = 6;
            sendreqTopCount();

            findViewById(R.id.ui).setVisibility(View.GONE);

        }


        pendingButton = (LinearLayout) findViewById(R.id.pendingButton);
        updateStatus = (LinearLayout) findViewById(R.id.updateStatus);
        doneButton = (LinearLayout) findViewById(R.id.doneButton);
        updateStatusText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendreq(2);
                findViewById(R.id.share).setVisibility(View.GONE);
                updateStatusText.setTextColor(PendingDoneVotersList.this.getResources().getColor(R.color.blue));
                pendingText.setTextColor(Color.parseColor("#d3d3d3"));
                doneText.setTextColor(Color.parseColor("#d3d3d3"));

            }
        });
        doneText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendreq(1);
                findViewById(R.id.share).setVisibility(View.GONE);
                doneText.setTextColor(PendingDoneVotersList.this.getResources().getColor(R.color.green));
                pendingText.setTextColor(Color.parseColor("#d3d3d3"));
                updateStatusText.setTextColor(Color.parseColor("#d3d3d3"));


            }
        });


        pendingText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendreq(3);
                findViewById(R.id.share).setVisibility(View.VISIBLE);
                pendingText.setTextColor(PendingDoneVotersList.this.getResources().getColor(R.color.red));
                updateStatusText.setTextColor(Color.parseColor("#d3d3d3"));
                doneText.setTextColor(Color.parseColor("#d3d3d3"));

            }
        });
        SharedPreferences sharedPreference2 = getSharedPreferences("userid", MODE_PRIVATE);
        int user_id2 = sharedPreference2.getInt("user_option", 0);

        if (user_id2 != 6) {
            updateStatus.setVisibility(View.GONE);
            doneText.setTextColor(PendingDoneVotersList.this.getResources().getColor(R.color.green));
            sendreq(1);
            LinearLayout buttons = (LinearLayout) findViewById(R.id.buttons);
            buttons.setWeightSum(2);

        } else {
            sendreq(2);
            updateStatusText.setTextColor(PendingDoneVotersList.this.getResources().getColor(R.color.blue));
        }

    }

    public void sendreq(final int option) {
        findViewById(R.id.blurredloader).setVisibility(View.VISIBLE);
        SharedPreferences sharedPreference2 = getSharedPreferences("userid", MODE_PRIVATE);
        int user_id2 = sharedPreference2.getInt("user_option", 0);
        String url2 = null;
        if (option == 1) {
            url2 = "http://electionapp.uxservices.in/Web_Services/Voting_Done.asmx/My_Voter_List_Done?user_id=" + id;

        } else {
            url2 = "http://electionapp.uxservices.in/Web_Services/Voting_NotDone.asmx/My_Voter_List_NotDone?user_id=" + id;
        }
        System.out.println("thisurl" + url2);
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                findViewById(R.id.blurredloader).setVisibility(View.GONE);

                String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                try {
                    System.out.println("this" + jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));
                    JSONArray jsonArray1 = null;
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.upvibhagRecycler);

                    String s = jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "").replace("<string xmlns=\"http://electionapp.uxservices.in\">", "");

                    JSONObject jsonObject = new JSONObject(s);

                    jsonArray1 = jsonObject.getJSONArray("list:");

                    Type listType = new TypeToken<List<VoterModel>>() {
                    }.getType();
                    List<VoterModel> yourList = new Gson().fromJson(String.valueOf(jsonArray1), listType);

                    SharedPreferences sharedPreferences = getSharedPreferences("userid", MODE_PRIVATE);
                    int id = sharedPreferences.getInt("user_id", 1);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PendingDoneVotersList.this);
                    recyclerView.setLayoutManager(layoutManager);

                    PendingDoneAdapter pendingDoneAdapter = new PendingDoneAdapter(PendingDoneVotersList.this, yourList, option);
                    recyclerView.setAdapter(pendingDoneAdapter);
                   /* VotingDayAdapter areAdapter = new VotingDayAdapter(PendingDoneVotersList.this, yourList);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.upvibhagRecycler);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(VotingDayActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(areAdapter);*/


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
