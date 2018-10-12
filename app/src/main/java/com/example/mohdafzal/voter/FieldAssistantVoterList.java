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

public class FieldAssistantVoterList extends BaseActivity {

    private EditText editText;
    public static int area_id;
    private int user_id;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private int user_option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_field_assistant_voter_list);
        Bundle extras = getIntent().getExtras();
        user_id = extras.getInt("user_id");
        getSupportActionBar().setTitle(extras.getString("title"));

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

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.workerrec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button button = (Button) findViewById(R.id.addnewvoter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FieldAssistantVoterList.this, AddNewVoter.class);
                intent.putExtra("voter_id", 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions
                        .makeSceneTransitionAnimation(FieldAssistantVoterList.this).toBundle());
                } else {
                    startActivity(intent);
                }

            }
        });
        if (NetworkUtil.isNetworkAvailable(this)) {

            sendreq();
        } else {
            findViewById(R.id.blurredloader).setVisibility(View.GONE);
            Toast.makeText(this, "not connected to internet", Toast.LENGTH_SHORT).show();
        }
    }


    private void sendreq() {
        SharedPreferences sharedPreference2 = getSharedPreferences("userid", MODE_PRIVATE);
        user_option = sharedPreference2.getInt("user_option", 0);
        int user_id2 = sharedPreference2.getInt("user_id", 1);
        String url2 = null;
        url2 = "http://electionapp.uxservices.in/Web_Services/My_Voter_List_Upavibhag.asmx/My_Voter_List?user_id=" + user_option;

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

                    Type listType = new TypeToken<List<VoterModel>>() {
                    }.getType();
                    List<VoterModel> yourList = new Gson().fromJson(String.valueOf(jsonArray1), listType);
                    ((TextView) findViewById(R.id.voterCount)).setText("Voters Count : " + yourList.size());
                    SharedPreferences sharedPreferences = getSharedPreferences("userid", MODE_PRIVATE);
                    int id = sharedPreferences.getInt("user_id", 1);
                    VoterAdapter areAdapter = new VoterAdapter(FieldAssistantVoterList.this, yourList, 0);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.workerrec);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FieldAssistantVoterList.this);
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
