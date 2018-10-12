package com.example.mohdafzal.voter.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohdafzal.voter.Adapter.CountAdapter;
import com.example.mohdafzal.voter.AddNewVoter;
import com.example.mohdafzal.voter.AreaModel;
import com.example.mohdafzal.voter.R;
import com.example.mohdafzal.voter.VotingCountModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Afzal on 14-Feb-18.
 */

public class CountFragment extends Fragment {

    private RecyclerView countRecycler;
    private Spinner areaspin;
    private int areapos;

    public CountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.count_fragment, container, false);
        areaspin=(Spinner)view.findViewById(R.id.areaspin);
        loadarea();
        countRecycler=(RecyclerView)view.findViewById(R.id.countRecycler);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        countRecycler.setLayoutManager(layoutManager);

        return view;
    }

    private void loadcount() {
        SharedPreferences sharedPreference = getActivity().getSharedPreferences("userid", MODE_PRIVATE);
        int user_id = sharedPreference.getInt("user_id", 1);
        String url = "http://electionapp.uxservices.in/Web_Services/Elections_Count.asmx/Results?userid=" + user_id+"&areaid="+areapos+"&booth_id="+null;
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
                        CountAdapter countAdapter = new CountAdapter(getActivity(), yourList,areapos);
                        countRecycler.setAdapter(countAdapter);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    private void loadarea() {
        SharedPreferences sharedPreference = getActivity().getSharedPreferences("userid", MODE_PRIVATE);
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
                    final List<AreaModel> yourList = new Gson().fromJson(String.valueOf(jsonArray1), listType);
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userid", MODE_PRIVATE);
                    List<String> list = new ArrayList<String>();
                    for (int i = 0; i < yourList.size(); i++) {
                        list.add(yourList.get(i).getElection_Area_Name());
                    }
                    System.out.println("listarea" + list);
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    areaspin.setAdapter(dataAdapter);
                    areaspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                areapos = yourList.get(position).getElection_Area_id();
                            loadcount();


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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

}
