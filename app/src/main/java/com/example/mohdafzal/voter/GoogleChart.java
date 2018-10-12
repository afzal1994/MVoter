package com.example.mohdafzal.voter;

import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohdafzal.voter.navigationdrawer.BaseActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GoogleChart extends BaseActivity {


    private float[] ydata = {1.2f, 4.5f};
    private String[] xdata = {"sayali", "aniket"};

   /* private BarChart barChart;

    private Button btn;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private BarChart barChart1;
    private BarChart barChart2;
    private List<ChartRegionalModel> yourList;
    private List<ChartPartyModel> partyList;

    private Spinner areaspin;
    private List<AgeModel> agelist;
    private BarChart barChart3;
    private List<VoterLanguageModel> voterLanguagelist;
    private BarChart barChart4;
    private Spinner spinState, spinDistrict, spinSubDistrict, spinVillage, spinArea;
    private int districtId;
    private int subDisrtrictId;
    private int villageId;
    private int spinDistrictpos;
    private String type = "all";
    private int areaId = 0;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_chart);
        getSupportActionBar().setTitle("Reports");
/*
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load
        // titles
        // from
        // strings.xml

        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);// load icons from
        // strings.xml

        set(navMenuTitles, navMenuIcons);


        spinState = (Spinner) findViewById(R.id.spinstate);
        spinDistrict = (Spinner) findViewById(R.id.spindistric);
        spinSubDistrict = (Spinner) findViewById(R.id.spinsubdistrict);
        spinVillage = (Spinner) findViewById(R.id.spinvillage);
        spinArea = (Spinner) findViewById(R.id.spinarea);
        loadDistrict();
        loadChart(R.id.barchart);
        loadChart(R.id.barchart1);
        loadChart(R.id.barchart2);
        loadChart(R.id.barchart3);
        loadChart(R.id.barchart4);
        spinState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    type = "all";
                    areaId = 0;

                } else {
                    type = "state";
                    areaId = 21;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void loadDistrict() {
        SharedPreferences sharedPreference = getSharedPreferences("userid", MODE_PRIVATE);
        int user_id = sharedPreference.getInt("user_id", 1);
        String url = "http://electionapp.uxservices.in/Web_Services/Bind_Distric.asmx/Distric_Bind?state_id=21";
        System.out.println("thisurl" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                try {

                    final JSONArray jsonArray = new JSONArray(response.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));
                    System.out.println("ward" + jsonArray);

                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    final List<String> list = new ArrayList<String>();
                    list.add("Select District");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        list.add(jsonArray.getJSONObject(i).getString("DistricName"));
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(GoogleChart.this,
                            android.R.layout.simple_spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinDistrict.setAdapter(dataAdapter);
                    spinDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            try {
                                if (position == 0) {

                                } else {
                                    type = "distric";
                                    districtId = jsonArray.getJSONObject(position - 1).getInt("DistricId");
                                    areaId = districtId;
                                    loadSubDistrict(districtId);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(GoogleChart.this);
        requestQueue.add(stringRequest);


    }

    public void loadSubDistrict(int districtId) {
        SharedPreferences sharedPreference = getSharedPreferences("userid", MODE_PRIVATE);
        int user_id = sharedPreference.getInt("user_id", 1);
        String url = "http://electionapp.uxservices.in/Web_Services/Bind_SubDistric.asmx/SubDistric_Bind?Distric_id=" + districtId;
        System.out.println("thisurl" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                try {

                    final JSONArray jsonArray = new JSONArray(response.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));
                    System.out.println("ward" + jsonArray);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    final List<String> list = new ArrayList<String>();
                    list.add("Select SubDistrict");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        list.add(jsonArray.getJSONObject(i).getString("SubDistricName"));
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(GoogleChart.this,
                            android.R.layout.simple_spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinSubDistrict.setAdapter(dataAdapter);
                    spinSubDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            try {
                                if (position == 0) {
                                    subDisrtrictId = 0;
                                } else {
                                    type = "subdistric";
                                    subDisrtrictId = jsonArray.getJSONObject(position - 1).getInt("SubDistricId");
                                    areaId = subDisrtrictId;
                                    loadVillage(subDisrtrictId);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(GoogleChart.this);
        requestQueue.add(stringRequest);


    }

    private void loadVillage(int subDisrtrictId) {
        SharedPreferences sharedPreference = getSharedPreferences("userid", MODE_PRIVATE);
        int user_id = sharedPreference.getInt("user_id", 1);
        String url = "http://electionapp.uxservices.in/Web_Services/Bind_Village.asmx/Village_bind?Sub_distric_id=" + subDisrtrictId;
        System.out.println("thisurl" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                try {

                    final JSONArray jsonArray = new JSONArray(response.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));

                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    List<String> list = new ArrayList<String>();
                    list.add("Select City");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        list.add(jsonArray.getJSONObject(i).getString("CityName"));
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(GoogleChart.this,
                            android.R.layout.simple_spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinVillage.setAdapter(dataAdapter);
                    spinVillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            try {
                                if (position == 0) {

                                } else {
                                    type = "city";
                                    villageId = jsonArray.getJSONObject(position - 1).getInt("CityId");
                                    areaId = villageId;
                                    loadArea(areaId);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(GoogleChart.this);
        requestQueue.add(stringRequest);


    }

    private void loadArea(int x) {
        SharedPreferences sharedPreference = getSharedPreferences("userid", MODE_PRIVATE);
        int user_id = sharedPreference.getInt("user_id", 1);
        String url = "http://electionapp.uxservices.in/Web_Services/Bind_Area.asmx/Area_Bind?cityid=" + x + "&user_id=" + user_id;
        System.out.println("thisurl" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                try {

                    final JSONArray jsonArray = new JSONArray(response.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));

                    List<String> list = new ArrayList<String>();
                    list.add("Select Area");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        list.add(jsonArray.getJSONObject(i).getString("ElectionAreaName"));
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(GoogleChart.this,
                            android.R.layout.simple_spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinArea.setAdapter(dataAdapter);
                    spinArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            try {
                                if (position == 0) {

                                } else {
                                    type = "area";
                                    areaId = jsonArray.getJSONObject(position - 1).getInt("ElectionAreaId");

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(GoogleChart.this);
        requestQueue.add(stringRequest);


    }

    public void showCharts() {
        loadRegionalChart(areaId);
    }

    private void loadRegionalChart(int areaId) {
        SharedPreferences sharedPreference = getSharedPreferences("userid", MODE_PRIVATE);
        int user_id = sharedPreference.getInt("user_id", 1);
        String url = "http://electionapp.uxservices.in/Web_Services/Bind_Area.asmx/Area_Bind?cityid=" + villageId + "&user_id=" + user_id;
        System.out.println("thisurl" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                try {

                    final JSONArray jsonArray = new JSONArray(response.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(GoogleChart.this);
        requestQueue.add(stringRequest);

    }

    public void loadChart(int id) {
        BarChart barChart = (BarChart) findViewById(id);
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<BarEntry> barEntries1 = new ArrayList<>();


        barEntries.add(new BarEntry(1, 989));
        barEntries.add(new BarEntry(2, 420));
        barEntries.add(new BarEntry(3, 758));

        barEntries1.add(new BarEntry(1, 950));
        barEntries1.add(new BarEntry(2, 791));
        barEntries1.add(new BarEntry(3, 630));


        BarDataSet barDataSet = new BarDataSet(barEntries, "Male");
        barDataSet.setColor(Color.parseColor("#F44336"));
        BarDataSet barDataSet1 = new BarDataSet(barEntries1, "Female");
        barDataSet1.setColors(Color.parseColor("#9C27B0"));

        BarData data = new BarData(barDataSet, barDataSet1);
        barChart.setData(data);
        final String[] weekdays = {"Sun", "Mon", "Tue"};

        XAxis xAxis = barChart.getXAxis();
        barChart.getAxisLeft().setAxisMinimum(0);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(weekdays));

        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularityEnabled(true);

        float barSpace = 0.02f;
        float groupSpace = 0.66f;
        int groupCount = 3;

        data.setBarWidth(0.15f);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        barChart.groupBars(0, groupSpace, barSpace);
    }*/

    }
}
