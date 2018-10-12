package com.example.mohdafzal.voter.fragments;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohdafzal.voter.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Afzal on 14-Feb-18.
 */

public class VotingFragment extends Fragment {

    private EditText serialNumber;
    private Button submit;
    private Spinner spinbooth;
    private int boothspinpos;

    public VotingFragment() {
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
        View view=inflater.inflate(R.layout.voting_fragment, container, false);
          serialNumber=(EditText)view.findViewById(R.id.serialNumber);
        spinbooth=(Spinner)view.findViewById(R.id.spinbooth);
loadbooth();
        submit=(Button)view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (serialNumber.getText().toString().isEmpty()){
                    serialNumber.setError("Please enter Serial numeber");
                }else if(boothspinpos==0){
                    Toast.makeText(getActivity(), "Please Select booth", Toast.LENGTH_SHORT).show();
                }else{
                    final Dialog dialog=new Dialog(getActivity());
                    dialog.setContentView(R.layout.serial_confirmation);
                    dialog.getWindow().getAttributes().windowAnimations = R.style.My_Dialog_Theme;

                    Button submit=(Button)dialog.findViewById(R.id.submit);
                    Button cancel=(Button)dialog.findViewById(R.id.cancel);
submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        sendreq();
    }
});
cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        dialog.dismiss();
    }
});
dialog.show();
                }
            }
        });
        return view ;
    }
    private void sendreq() {
        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.show();
        String s="http://electionapp.uxservices.in/Web_Services/Voter_Serial_No.asmx/SerialNo?serailno="+serialNumber.getText().toString()+"&booth="+boothspinpos;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, s, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.hide();
                System.out.println("first"+response);
                String newString = response.replace("http://electionapp.uxservices.in", "");
                String newString1 = newString.replace("<string xmlns=\"\">", "");
                String newString2 = newString1.replace("</string>", "");
                String newstring3=newString2.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>","");
                System.out.println("dasdxaws"+newstring3.replace("<string xmlns=\"/\">",""));
                try {
                    JSONObject jsonObject=new JSONObject(newstring3.replace("<string xmlns=\"/\">",""));
                    Toast.makeText(getActivity(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                 serialNumber.setText("");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
    private void loadbooth() {
        SharedPreferences sharedPreference = getActivity().getSharedPreferences("userid", MODE_PRIVATE);
        int user_id = sharedPreference.getInt("user_id", 1);
        String url = "http://electionapp.uxservices.in/Web_Services/Bind_Booth.asmx/Booth_Bind";
        System.out.println("thisurl" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                try {

                    final JSONArray jsonArray3 = new JSONArray(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));
                    System.out.println("ward" + jsonArray3);

                    JSONObject jsonObject = jsonArray3.getJSONObject(0);
                    List<String> list = new ArrayList<String>();
                    list.add("Select Booth");

                    for (int i = 0; i < jsonArray3.length(); i++) {
                        list.add(jsonArray3.getJSONObject(i).getString("ElectionBoothName"));
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinbooth.setAdapter(dataAdapter);
                    spinbooth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            try {
                                if (position == 0) {
                                    boothspinpos = 0;
                                } else {
                                    boothspinpos = jsonArray3.getJSONObject(position - 1).getInt("ElectionBoothId");
                                }
                                //  Toast.makeText(AddNewVoter.this, String.valueOf(boothspinpos), Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}
