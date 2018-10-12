package com.example.mohdafzal.voter;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mohdafzal.voter.navigationdrawer.BaseActivity;

public class MainPage extends BaseActivity implements View.OnClickListener {

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private LinearLayout upvibhag;
    private LinearLayout allVoters;
    private LinearLayout outoftownlist;
    private LinearLayout myWard;
    private LinearLayout votingday;
    private int user_option;
    private TextView myWardTextView;
    private TextView welcomeText;
    private TextView upvibhagText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        getSupportActionBar().setTitle("Dashboard");


        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load
        navMenuIcons = getResources()
            .obtainTypedArray(R.array.nav_drawer_icons);// load icons from


        set(navMenuTitles, navMenuIcons);
        initUI();
        SharedPreferences sharedPreferences = getSharedPreferences("userid", MODE_PRIVATE);
        String user_name = sharedPreferences.getString("user_name", "Guest");
        welcomeText = (TextView) findViewById(R.id.welcomeText);
        welcomeText.setText("Welcome " + user_name);
       /* if (sharedPreferences.getBoolean("election_access", true)) {
            elections.setVisibility(View.VISIBLE);
        }
        if (sharedPreferences.getBoolean("report_access", true)) {
            reports.setVisibility(View.VISIBLE);
        }*/
    }

    private void initUI() {
        upvibhagText=(TextView)findViewById(R.id.upvibhagText);
        upvibhag = (LinearLayout) findViewById(R.id.upvibhag);
        allVoters = (LinearLayout) findViewById(R.id.allVoters);
        outoftownlist = (LinearLayout) findViewById(R.id.outoftownlist);
        myWard = (LinearLayout) findViewById(R.id.myWard);
        votingday = (LinearLayout) findViewById(R.id.votingday);
        myWardTextView = (TextView) findViewById(R.id.myWardText);

        SharedPreferences sharedPreferences = getSharedPreferences("userid", MODE_PRIVATE);
        user_option = sharedPreferences.getInt("user_option", 0);
        upvibhagText.setText(IContants.getName(user_option));
        if (user_option == 6) {
            upvibhag.setVisibility(View.GONE);
            outoftownlist.setVisibility(View.GONE);
            myWardTextView.setText("Add New Voter");
        } else {
            myWard.setVisibility(View.GONE);
        }
        upvibhag.setOnClickListener(this);
        allVoters.setOnClickListener(this);
        outoftownlist.setOnClickListener(this);
        votingday.setOnClickListener(this);
        myWard.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.upvibhag:
                Intent intent = new Intent(this, UpvibhagActivity.class);
                intent.putExtra("user_id", 2);
                intent.putExtra("title", "Upvibhag");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions
                        .makeSceneTransitionAnimation(this).toBundle());
                } else {
                    startActivity(intent);
                }
                break;

            case R.id.allVoters:
                Intent intent1 = new Intent(this, Voterlist.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent1, ActivityOptions
                        .makeSceneTransitionAnimation(this).toBundle());
                } else {
                    startActivity(intent1);
                }
                break;

            case R.id.outoftownlist:
                intent = new Intent(MainPage.this, OutOfTownActivity.class);
                startActivity(intent);
                break;

            case R.id.myWard:
                if (user_option == 6) {
                    Intent intent3 = new Intent(this, AddNewVoter.class);
                    intent3.putExtra("voter_id", "0");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        startActivity(intent3, ActivityOptions
                            .makeSceneTransitionAnimation(this).toBundle());
                    } else {
                        startActivity(intent3);
                    }
                } else {
                   /* Intent intent3 = new Intent(this, GoogleChart.class);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        startActivity(intent3, ActivityOptions
                            .makeSceneTransitionAnimation(this).toBundle());
                    } else {
                        startActivity(intent3);
                    }*/
                }
                break;
            case R.id.votingday:
                SharedPreferences sharedPreference2 = getSharedPreferences("userid", MODE_PRIVATE);
                int user_id2 = sharedPreference2.getInt("user_option", 0);

                Intent intent4 = new Intent(this, user_id2 == 6 ? PendinDoneVotersList.class : VotingDayActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent4, ActivityOptions
                        .makeSceneTransitionAnimation(this).toBundle());
                } else {
                    startActivity(intent4);
                }
                break;
        }

    }

}
