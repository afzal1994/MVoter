package com.example.mohdafzal.voter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
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
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
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

public class DashBoard extends BaseActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    private RelativeLayout booth;
    private RelativeLayout Area, worker, voter;
    private TextView boothcountt, areacountt, workercountt, votercountt;
    private Button syncnow;
    private ConnectivityReceiver connectivityReceiver;
    private ImageView imageView;
    private LinearLayout linearone;
    private LinearLayout lineartwo;
    private LinearLayout linearthree;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dash_board);
        getSupportActionBar().setTitle("Manage Voters");

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load
        // titles
        // from
        // strings.xml

        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);// load icons from
        // strings.xml

        set(navMenuTitles, navMenuIcons);
         imageView = (ImageView) findViewById(R.id.deliveryindicator);

        RequestQueue volleyQueue = Volley.newRequestQueue(this);
        DiskBasedCache cache = new DiskBasedCache(getCacheDir(), 16 * 1024 * 1024);
        volleyQueue = new RequestQueue(cache, new BasicNetwork(new HurlStack()));
        volleyQueue.start();
        connectivityReceiver = new ConnectivityReceiver();

        initui();

        Button button = (Button) findViewById(R.id.addnewvoter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoard.this, AddNewVoter.class);
                intent.putExtra("voter_id", 0);
                startActivity(intent);

            }
        });
    }

    private void initui() {
        booth = (RelativeLayout) findViewById(R.id.booth);
        Area = (RelativeLayout) findViewById(R.id.area);
        worker = (RelativeLayout) findViewById(R.id.worker);
        voter = (RelativeLayout) findViewById(R.id.voters);
        syncnow = (Button) findViewById(R.id.syncnowButton);
        boothcountt = (TextView) findViewById(R.id.boothcount);
        areacountt = (TextView) findViewById(R.id.areacount);
        workercountt = (TextView) findViewById(R.id.workercount);
        votercountt = (TextView) findViewById(R.id.votercount);
  linearone = (LinearLayout) findViewById(R.id.lin1);
 lineartwo = (LinearLayout) findViewById(R.id.lin2);
         linearthree = (LinearLayout) findViewById(R.id.lin3);
loadchart();


        Area.setOnClickListener(this);
        booth.setOnClickListener(this);
        worker.setOnClickListener(this);
        voter.setOnClickListener(this);
        syncnow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.syncnowButton:

                break;
            case R.id.logout:
                Intent intent = new Intent(this, LoginActivity.class);
                finish();
                finishAffinity();
                SharedPreferences.Editor editor = getSharedPreferences("Loginstatus", MODE_PRIVATE).edit();
                editor.putBoolean("loginstatus", false);
                editor.clear();
                editor.apply();

                startActivity(intent);
                break;
            case R.id.voters:
                Intent intent1 = new Intent(this, Voterlist.class);
                startActivity(intent1);
                break;
            case R.id.area:
                if(NetworkUtil.isNetworkAvailable(this)) {
                    if (linearone.isShown()) {
                        linearone.animate()
                                .translationY(0)
                                .alpha(0.0f)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        linearone.setVisibility(View.GONE);
                                    }
                                });

                    } else {
                        linearone.setVisibility(View.VISIBLE);
                        linearone.setAlpha(0.0f);

// Start the animation
                        linearone.animate()
                                .translationY(0)
                                .alpha(1.0f)
                                .setListener(null);
                        lineartwo.animate()
                                .translationY(0)
                                .alpha(0.0f)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        lineartwo.setVisibility(View.GONE);
                                    }
                                });
                        linearthree.animate()
                                .translationY(0)
                                .alpha(0.0f)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        linearthree.setVisibility(View.GONE);
                                    }
                                });
                    }
                    SharedPreferences sharedPreference = getSharedPreferences("userid", MODE_PRIVATE);
                    int user_id = sharedPreference.getInt("user_id", 1);
                    String url = "http://electionapp.uxservices.in/Web_Services/Area_List.asmx/Getlist?user_id=" + user_id;
                    System.out.println("thisurl" + url);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                            try {
                                System.out.println("this" + jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));

                                JSONArray jsonArray1 = new JSONArray(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));
                                JSONObject jsonObject = jsonArray1.getJSONObject(0);
                                Type listType = new TypeToken<List<AreaModel>>() {
                                }.getType();
                                List<AreaModel> yourList = new Gson().fromJson(String.valueOf(jsonArray1), listType);
                                SharedPreferences sharedPreferences = getSharedPreferences("userid", MODE_PRIVATE);
                                int id = sharedPreferences.getInt("user_id", 1);
                                System.out.println(yourList.get(0).getElection_Area_Name());
                                AreAdapter areAdapter = new AreAdapter(DashBoard.this, yourList);
                                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.arearec);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DashBoard.this);
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
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    requestQueue.add(stringRequest);
                }

                break;
            case R.id.booth:
                if (lineartwo.isShown()) {
                    lineartwo.animate()
                            .translationY(0)
                            .alpha(0.0f)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    lineartwo.setVisibility(View.GONE);
                                }
                            });

                } else {
                    lineartwo.setVisibility(View.VISIBLE);
                    lineartwo.setAlpha(0.0f);

// Start the animation
                    lineartwo.animate()
                            .translationY(0)
                            .alpha(1.0f)
                            .setListener(null);
                    linearone.animate()
                            .translationY(0)
                            .alpha(0.0f)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    linearone.setVisibility(View.GONE);
                                }
                            });
                    linearthree.animate()
                            .translationY(0)
                            .alpha(0.0f)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    linearthree.setVisibility(View.GONE);
                                }
                            });
                }
                SharedPreferences sharedPreference1 = getSharedPreferences("userid", MODE_PRIVATE);
                int user_id1 = sharedPreference1.getInt("user_id", 1);
                String url1 = "http://electionapp.uxservices.in/Web_Services/Incharge_Voter_List.asmx/Incharge_List?user_id=" + user_id1;
                System.out.println("thisurl" + url1);
                StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                        try {
                            System.out.println("this" + jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));

                            JSONArray jsonArray1 = new JSONArray(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "").replace("<string xmlns=\"http://tempuri.org/\">", ""));
                            JSONObject jsonObject = jsonArray1.getJSONObject(0);
                            Type listType = new TypeToken<List<InchageModel>>() {
                            }.getType();
                            List<InchageModel> yourList = new Gson().fromJson(String.valueOf(jsonArray1), listType);
                            SharedPreferences sharedPreferences = getSharedPreferences("userid", MODE_PRIVATE);
                            int id = sharedPreferences.getInt("user_id", 1);
                            InchargeAdapter areAdapter = new InchargeAdapter(DashBoard.this, yourList);
                            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.boothname);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DashBoard.this);
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

                break;
            case R.id.worker:
                if (linearthree.isShown()) {
                    linearthree.animate()
                            .translationY(0)
                            .alpha(0.0f)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    linearthree.setVisibility(View.GONE);
                                }
                            });

                } else {
                    linearthree.setVisibility(View.VISIBLE);
                    linearthree.setAlpha(0.0f);

// Start the animation
                    linearthree.animate()
                            .translationY(0)
                            .alpha(1.0f)
                            .setListener(null);
                    linearone.animate()
                            .translationY(0)
                            .alpha(0.0f)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    linearone.setVisibility(View.GONE);
                                }
                            });
                    lineartwo.animate()
                            .translationY(0)
                            .alpha(0.0f)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    lineartwo.setVisibility(View.GONE);
                                }
                            });
                }
                SharedPreferences sharedPreference2 = getSharedPreferences("userid", MODE_PRIVATE);
                int user_id2 = sharedPreference2.getInt("user_id", 1);
                String url2 = "http://electionapp.uxservices.in/Web_Services/Manager_Field_Assistant.asmx/Manager_Worker_List?user_id=" + user_id2;
                System.out.println("thisurl" + url2);
                StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                        try {
                            System.out.println("this" + jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));

                            JSONObject jsonObject = new JSONObject(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));
                            JSONArray jsonArray1 = jsonObject.getJSONArray("data:");
                            Type listType = new TypeToken<List<WorkerModel>>() {
                            }.getType();
                            List<WorkerModel> yourList = new Gson().fromJson(String.valueOf(jsonArray1), listType);
                            SharedPreferences sharedPreferences = getSharedPreferences("userid", MODE_PRIVATE);
                            int id = sharedPreferences.getInt("user_id", 1);
                            WorkerAdapter areAdapter = new WorkerAdapter(DashBoard.this, yourList);
                            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.workername);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DashBoard.this);
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

                break;
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        /*final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        registerReceiver(connectivityReceiver, intentFilter);*/

        /*register connection status listener*/
        if (NetworkUtil.isNetworkAvailable(this)) {
            MyApplication.getInstance().setConnectivityListener(this);
            SharedPreferences sharedPreference = getSharedPreferences("userid", MODE_PRIVATE);
            int user_id = sharedPreference.getInt("user_id", 1);
            String url = "http://electionapp.uxservices.in/Web_Services/dashboard.asmx/dashboard?&user_id=" + user_id;
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

                        SharedPreferences sharedPreferences = getSharedPreferences("userid", MODE_PRIVATE);
                        int id = sharedPreferences.getInt("user_id", 1);
                        if (id == 1) {
                            Area.setVisibility(View.VISIBLE);
                            booth.setVisibility(View.VISIBLE);
                            worker.setVisibility(View.VISIBLE);
                            voter.setVisibility(View.VISIBLE);
                            int areacount = jsonObject.getInt("Area Count");
                            int boothcount = jsonObject.getInt(" Booth Incharge Count");
                            int workercount = jsonObject.getInt("Worker Count");
                            int votercount = jsonObject.getInt("Voter Count");
                            areacountt.setText(String.valueOf(areacount));
                            boothcountt.setText(String.valueOf(boothcount));
                            workercountt.setText(String.valueOf(workercount));
                            votercountt.setText(String.valueOf(votercount));


                        } else if (id == 3) {
                            Area.setVisibility(View.VISIBLE);
                            worker.setVisibility(View.VISIBLE);
                            voter.setVisibility(View.VISIBLE);
                            int workercount = jsonObject.getInt("Worker Count");
                            int votercount = jsonObject.getInt("Voter Count");
                            int areacount = jsonObject.getInt("Area Count");
                            areacountt.setText(String.valueOf(areacount));
                            workercountt.setText(String.valueOf(workercount));
                            votercountt.setText(String.valueOf(votercount));


                        } else if (id == 6) {
                            Area.setVisibility(View.VISIBLE);

                            voter.setVisibility(View.VISIBLE);

                            int votercount = jsonObject.getInt("Voter Count");
                            int areacount = jsonObject.getInt("Area Count");
                            areacountt.setText(String.valueOf(areacount));
                            votercountt.setText(String.valueOf(votercount));
                        } else if (id == 0) {
                            Area.setVisibility(View.VISIBLE);
                            worker.setVisibility(View.VISIBLE);
                            voter.setVisibility(View.VISIBLE);
                            int workercount = jsonObject.getInt(" Workers Count");
                            int votercount = jsonObject.getInt("Voter Count");
                            int areacount = jsonObject.getInt("Area Count");
                            areacountt.setText(String.valueOf(areacount));
                            workercountt.setText(String.valueOf(workercount));
                            votercountt.setText(String.valueOf(votercount));

                        }

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
        else{
            findViewById(R.id.blurredloader).setVisibility(View.GONE);

            imageView.setColorFilter(Color.parseColor("#ff0000"));

        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {


        if (isConnected) {
            imageView.clearColorFilter();
            onResume();
            Toast.makeText(this, "Connected online", Toast.LENGTH_SHORT).show();
        } else {
            imageView.setColorFilter(Color.parseColor("#ff0000"));

            Toast.makeText(this, "Not Connected to internet Add Voters in offline mode", Toast.LENGTH_SHORT).show();
        }
    }

  /*  @Override
    protected void onStop()
    {
        unregisterReceiver(connectivityReceiver);
        super.onStop();
    }*/
  public void slideUp(View view){
      view.setVisibility(View.VISIBLE);
      TranslateAnimation animate = new TranslateAnimation(
              0,                 // fromXDelta
              0,                 // toXDelta
              view.getHeight(),  // fromYDelta
              0);                // toYDelta
      animate.setDuration(500);
      animate.setFillAfter(true);
      view.startAnimation(animate);
  }

    // slide the view from its current position to below itself
    public void slideDown(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }
    private void loadchart() {
        SharedPreferences sharedPreference = this.getSharedPreferences("userid", MODE_PRIVATE);
        int user_id = sharedPreference.getInt("user_id", 1);
        String url = "http://electionapp.uxservices.in/Web_Services/GoogleChart.asmx/AllCharts?userid=" + user_id+"&Areaid=1";
        System.out.println("thisurl" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                try {
                    System.out.println("this" + jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));

                    JSONArray jsonArray1 = new JSONArray(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));
                    JSONObject jsonObject = jsonArray1.getJSONObject(0);
                    SharedPreferences.Editor editor=getSharedPreferences("json",MODE_PRIVATE).edit();
                    editor.putString("json",jsonObject.toString());
                    editor.apply();

                    System.out.println(jsonObject);



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(DashBoard.this);
        requestQueue.add(stringRequest);

    }

}
