package com.example.mohdafzal.mvoter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class DashBoard extends AppCompatActivity implements View.OnClickListener {

    private ImageView logout;
    private RelativeLayout booth;
    private RelativeLayout Area, worker, voter;
    private TextView boothcountt, areacountt, workercountt, votercountt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dash_board);
        initui();

        Button button = (Button) findViewById(R.id.addnewvoter);
        logout = (ImageView) findViewById(R.id.logout);
        logout.setOnClickListener(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoard.this, AddNewVoter.class);
                intent.putExtra("voter_id",0);
                startActivity(intent);

            }
        });
    }

    private void initui() {
        booth = (RelativeLayout) findViewById(R.id.booth);
        Area = (RelativeLayout) findViewById(R.id.area);
        worker = (RelativeLayout) findViewById(R.id.worker);
        voter = (RelativeLayout) findViewById(R.id.voters);

        boothcountt = (TextView) findViewById(R.id.boothcount);
        areacountt = (TextView) findViewById(R.id.areacount);
        workercountt = (TextView) findViewById(R.id.workercount);
        votercountt = (TextView) findViewById(R.id.votercount);
        Area.setOnClickListener(this);
booth.setOnClickListener(this);
        worker.setOnClickListener(this);
        voter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout:
                Intent intent = new Intent(this, LoginActivity.class);
                finish();
                finishAffinity();
                SharedPreferences.Editor editor=getSharedPreferences("Loginstatus",MODE_PRIVATE).edit();
                editor.putBoolean("loginstatus",false);
                editor.clear();
                editor.apply();

                startActivity(intent);
                break;
            case R.id.voters:
                Intent intent1 = new Intent(this, Voterlist.class);
                startActivity(intent1);
                break;
            case R.id.area:
                LinearLayout linearLayout=(LinearLayout)findViewById(R.id.lin1);
if (linearLayout.isShown()){
    linearLayout.setVisibility(View.GONE);

}
else {
    linearLayout.setVisibility(View.VISIBLE);
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
                            Type listType = new TypeToken<List<AreaModel>>() {}.getType();
                            List<AreaModel> yourList = new Gson().fromJson(String.valueOf(jsonArray1),listType);
                            SharedPreferences sharedPreferences = getSharedPreferences("userid", MODE_PRIVATE);
                            int id = sharedPreferences.getInt("user_id", 1);
                            System.out.println(yourList.get(0).getElection_Area_Name());
                            AreAdapter areAdapter=new AreAdapter(DashBoard.this,yourList);
                            RecyclerView recyclerView=(RecyclerView)findViewById(R.id.arearec);
                            RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(DashBoard.this);
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

        break;
            case R.id.booth:
                LinearLayout linearLayout1=(LinearLayout)findViewById(R.id.lin2);
                if (linearLayout1.isShown()){
                    linearLayout1.setVisibility(View.GONE);

                }
                else {
                    linearLayout1.setVisibility(View.VISIBLE);
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

                            JSONArray jsonArray1 = new JSONArray(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "").replace("<string xmlns=\"http://tempuri.org/\">",""));
                            JSONObject jsonObject = jsonArray1.getJSONObject(0);
                            Type listType = new TypeToken<List<InchageModel>>() {}.getType();
                            List<InchageModel> yourList = new Gson().fromJson(String.valueOf(jsonArray1),listType);
                            SharedPreferences sharedPreferences = getSharedPreferences("userid", MODE_PRIVATE);
                            int id = sharedPreferences.getInt("user_id", 1);
                            InchargeAdapter areAdapter=new InchargeAdapter(DashBoard.this,yourList);
                            RecyclerView recyclerView=(RecyclerView)findViewById(R.id.boothname);
                            RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(DashBoard.this);
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
                LinearLayout linearLayout2=(LinearLayout)findViewById(R.id.lin3);
                if (linearLayout2.isShown()){
                    linearLayout2.setVisibility(View.GONE);

                }
                else {
                    linearLayout2.setVisibility(View.VISIBLE);
                }
                SharedPreferences sharedPreference2 = getSharedPreferences("userid", MODE_PRIVATE);
                int user_id2 = sharedPreference2.getInt("user_id", 1);
                String url2 = "http://electionapp.uxservices.in/Web_Services/Manager_Worker_List.asmx/Manager_Worker_List?user_id=" + user_id2;
                System.out.println("thisurl" + url2);
                StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                        try {
                            System.out.println("this" + jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));

                            JSONObject jsonObject = new JSONObject(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));
                            JSONArray jsonArray1 = jsonObject.getJSONArray("data:");
                            Type listType = new TypeToken<List<WorkerModel>>() {}.getType();
                            List<WorkerModel> yourList = new Gson().fromJson(String.valueOf(jsonArray1),listType);
                            SharedPreferences sharedPreferences = getSharedPreferences("userid", MODE_PRIVATE);
                            int id = sharedPreferences.getInt("user_id", 1);
                            WorkerAdapter areAdapter=new WorkerAdapter(DashBoard.this,yourList);
                            RecyclerView recyclerView=(RecyclerView)findViewById(R.id.workername);
                            RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(DashBoard.this);
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
        SharedPreferences sharedPreference =getSharedPreferences("userid",MODE_PRIVATE);
        int user_id=sharedPreference.getInt("user_id",1);
        String url="http://electionapp.uxservices.in/Web_Services/dashboard.asmx/dashboard?&user_id="+user_id;
        System.out.println("thisurl"+url);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String jsonArray=response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">","").replace("</string>","");
                try {
                    System.out.println("this"+jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>",""));

                    JSONArray jsonArray1=new JSONArray(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>",""));
                    JSONObject jsonObject=jsonArray1.getJSONObject(0);

                    SharedPreferences sharedPreferences=getSharedPreferences("userid",MODE_PRIVATE);
                   int id=sharedPreferences.getInt("user_id",1);
                    if(id==1){
                        Area.setVisibility(View.VISIBLE);
                        booth.setVisibility(View.VISIBLE);
                        worker.setVisibility(View.VISIBLE);
                        voter.setVisibility(View.VISIBLE);
                        int areacount=jsonObject.getInt("Area Count");
                        int boothcount=jsonObject.getInt(" Booth Incharge Count");
                        int workercount=jsonObject.getInt("Worker Count");
                        int votercount=jsonObject.getInt("Voter Count");
                        areacountt.setText(String.valueOf(areacount));
                        boothcountt.setText(String.valueOf(boothcount));
                        workercountt.setText(String.valueOf(workercount));
                        votercountt.setText(String.valueOf(votercount));



                    }
                   else if(id==3){
                        Area.setVisibility(View.VISIBLE);
                        worker.setVisibility(View.VISIBLE);
                        voter.setVisibility(View.VISIBLE);
                        int workercount=jsonObject.getInt("Worker Count");
                        int votercount=jsonObject.getInt("Voter Count");
                        int areacount=jsonObject.getInt("Area Count");
                        areacountt.setText(String.valueOf(areacount));
                        workercountt.setText(String.valueOf(workercount));
                        votercountt.setText(String.valueOf(votercount));


                    }
                   else if(id==6){
                        Area.setVisibility(View.VISIBLE);

                        voter.setVisibility(View.VISIBLE);

                        int votercount=jsonObject.getInt("Voter Count");
                        int areacount=jsonObject.getInt("Area Count");
                        areacountt.setText(String.valueOf(areacount));
                        votercountt.setText(String.valueOf(votercount));
                    }
                    else if(id==0){
                        Area.setVisibility(View.VISIBLE);
                        worker.setVisibility(View.VISIBLE);
                        voter.setVisibility(View.VISIBLE);
                        int workercount=jsonObject.getInt("Worker Count");
                        int votercount=jsonObject.getInt("Voter Count");
                        int areacount=jsonObject.getInt("Area Count");
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

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
