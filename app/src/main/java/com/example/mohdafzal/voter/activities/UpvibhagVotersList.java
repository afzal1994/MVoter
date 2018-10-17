package com.example.mohdafzal.voter.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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
import com.example.mohdafzal.voter.adapter.UpVibhagVotersListAdapter;
import com.example.mohdafzal.voter.models.VoterModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class UpvibhagVotersList extends BaseActivityNavigation {

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private RecyclerView upVibhagRecycler;
    private TextView incharge_name;
    private TextView voterCount;
    private TextView upvibhagName;
    private RelativeLayout call;
    private int user_id2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upvibhag_voters_list);
        getSupportActionBar().setTitle("Out Of Town List");

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load
        // titles
        // from
        // strings.xml

        navMenuIcons = getResources()
            .obtainTypedArray(R.array.nav_drawer_icons);// load icons from
        // strings.xml

        set(navMenuTitles, navMenuIcons);
        initUI();
        sendreq();


    }

    private void initUI() {
        incharge_name = (TextView) findViewById(R.id.incharge_name);
        voterCount = (TextView) findViewById(R.id.voterCount);
        upvibhagName = (TextView) findViewById(R.id.upvibhagName);
        call = (RelativeLayout) findViewById(R.id.call);
        final Bundle extras = getIntent().getExtras();
        incharge_name.setText(extras.getString("incharge_name"));
        getSupportActionBar().setTitle(extras.getString("incharge_name"));

        if (extras.getString("phone_number") == null) {
            ImageView callImage = (ImageView) findViewById(R.id.call_image);
            callImage.setImageResource(R.drawable.call_grey);



        }
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (extras.getString("phone_number") != null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + extras.getString("phone_number")));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        startActivity(intent, ActivityOptions
                            .makeSceneTransitionAnimation(UpvibhagVotersList.this).toBundle());
                    } else {
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(UpvibhagVotersList.this, "No Phone Number Provided", Toast.LENGTH_SHORT).show();
                }
            }
        });

        upvibhagName.setText("Upvibhag : " + extras.getString("vibhag_name"));
        user_id2 = extras.getInt("incharge_id");


    }

    private void sendreq() {


        String url2 = "http://electionapp.uxservices.in/Web_Services/My_Voter_List_Upavibhag.asmx/My_Voter_List?user_id=" + user_id2;
        System.out.println("thisurl" + url2);
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                findViewById(R.id.blurredloader).setVisibility(View.GONE);

                String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                try {
                    System.out.println("this" + jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));

                    JSONObject jsonObject = new JSONObject(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "").replace("<string xmlns=\"http://electionapp.uxservices.in\">", ""));
                    JSONArray jsonArray1 = jsonObject.getJSONArray("data:");
                    Type listType = new TypeToken<List<VoterModel>>() {
                    }.getType();
                    List<VoterModel> yourList = new Gson().fromJson(String.valueOf(jsonArray1), listType);
                    SharedPreferences sharedPreferences = getSharedPreferences("userid", MODE_PRIVATE);
                    int id = sharedPreferences.getInt("user_id", 1);
                    UpVibhagVotersListAdapter areAdapter = new UpVibhagVotersListAdapter(UpvibhagVotersList.this, yourList);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.upvibhagRecycler);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(UpvibhagVotersList.this);
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

