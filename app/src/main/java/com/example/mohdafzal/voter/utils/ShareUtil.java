package com.example.mohdafzal.voter.utils;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohdafzal.voter.R;
import com.example.mohdafzal.voter.activities.Voterlist;
import com.example.mohdafzal.voter.models.PostsHirarchyModel;
import com.example.mohdafzal.voter.models.VoterModel;
import com.example.mohdafzal.voter.models.VoterSlipModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class ShareUtil {
    public ShareUtil(final AppCompatActivity context, final VoterModel voterModel) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.share_dialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.My_Dialog_Theme;

        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.findViewById(R.id.sharePhone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (voterModel.getVoter_PhoneNo() == null) {
                    dialog.dismiss();
                    Toast.makeText(context, "Phone number not available", Toast.LENGTH_SHORT).show();
                } else {
                    String s = "Voter Name- " + voterModel.getE_User_Eng_FName() + " " + voterModel.getE_User_Eng_LName() + "\n"
                        + "Phone Number- " + voterModel.getVoter_PhoneNo();
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, s);
                    context.startActivity(Intent.createChooser(shareIntent, "Share phone using"));
                }
            }
        });
        dialog.findViewById(R.id.shareVoterSlip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                context.findViewById(R.id.blurredloader).setVisibility(View.VISIBLE);
                String url2 = null;
                url2 = "http://electionapp.uxservices.in/Web_Services/Voter_Slip_Details.asmx/details?Voter_id=" + voterModel.getVoter_Id();

                System.out.println("thisurl" + url2);
                StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        context.findViewById(R.id.blurredloader).setVisibility(View.GONE);

                        String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                        try {
                            System.out.println("this" + jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));
                            JSONObject jsonObject1 = null;
                            JSONObject jsonObject = new JSONObject(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "").replace("<string xmlns=\"http://electionapp.uxservices.in\">", ""));

                            JSONArray jsonArray1 = jsonObject.getJSONArray("data:");
                            Type listType = new TypeToken<List<VoterSlipModel>>() {
                            }.getType();
                            List<VoterSlipModel> yourList = new Gson().fromJson(String.valueOf(jsonArray1), listType);
                            VoterSlipModel voterSlipModel = yourList.get(0);

                            String s = "Election Name- " + voterSlipModel.getM_Election_Name() + "\n"

                                + "Voter Name- " + voterSlipModel.getVoter_Eng_FName() + " " + voterSlipModel.getVoter_Eng_LName() + "\n"
                                + "Phone Number- " + voterSlipModel.getVoter_PhoneNo() + "\n"
                                + "Address- " + voterSlipModel.getVoter_Address() + "\n"
                                + "Gender- " + voterSlipModel.getVoter_Gender() + "\n"
                                + "Booth Name- " + voterSlipModel.getElection_Booth_Name() + "\n"

                                + "Booth address- " + voterSlipModel.getElection_Booth_Address() + "\n"
                                + "Election Start Time- " + voterSlipModel.getM_Election_Start_Time() + "\n"
                                + "Election End Time- " + voterSlipModel.getM_Election_End_Time() + "\n"
                                + "Party Name- " + voterSlipModel.getPartyName() + "\n"
                                + "Room number- " + voterSlipModel.getRoom_No() + "\n";

                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("text/plain");
                            shareIntent.putExtra(Intent.EXTRA_TEXT, s);
                            context.startActivity(Intent.createChooser(shareIntent, "Share details using"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        context.findViewById(R.id.blurredloader).setVisibility(View.GONE);
                    }
                });
                RequestQueue requestQueue2 = Volley.newRequestQueue(context);
                requestQueue2.add(stringRequest2);
            }
        });


        dialog.show();

    }

    public ShareUtil(final Voterlist context, final PostsHirarchyModel voterModel) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.share_dialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.My_Dialog_Theme;

        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.findViewById(R.id.sharePhone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (voterModel.getPhonenumber() == null) {
                    dialog.dismiss();
                    Toast.makeText(context, "Phone number not available", Toast.LENGTH_SHORT).show();
                } else {
                    String s = "Voter Name- " + voterModel.getIncharge_fname() + " " + voterModel.getIncharge_lname() + "\n"
                        + "Phone Number- " + voterModel.getPhonenumber();
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, s);
                    context.startActivity(Intent.createChooser(shareIntent, "Share phone using"));
                }
            }
        });
        dialog.findViewById(R.id.shareVoterSlip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                context.findViewById(R.id.blurredloader).setVisibility(View.VISIBLE);
                String url2 = null;
                url2 = "http://electionapp.uxservices.in/Web_Services/Voter_Slip_Details.asmx/details?Voter_id=" + voterModel.getE_User_id();

                System.out.println("thisurl" + url2);
                StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        context.findViewById(R.id.blurredloader).setVisibility(View.GONE);

                        String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                        try {
                            System.out.println("this" + jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));
                            JSONObject jsonObject1 = null;
                            JSONObject jsonObject = new JSONObject(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "").replace("<string xmlns=\"http://electionapp.uxservices.in\">", ""));

                            JSONArray jsonArray1 = jsonObject.getJSONArray("data:");
                            Type listType = new TypeToken<List<VoterSlipModel>>() {
                            }.getType();
                            List<VoterSlipModel> yourList = new Gson().fromJson(String.valueOf(jsonArray1), listType);
                            VoterSlipModel voterSlipModel = yourList.get(0);

                            String s = "Election Name- " + voterSlipModel.getM_Election_Name() + "\n"

                                + "Voter Name- " + voterSlipModel.getVoter_Eng_FName() + " " + voterSlipModel.getVoter_Eng_LName() + "\n"
                                + "Phone Number- " + voterSlipModel.getVoter_PhoneNo() + "\n"
                                + "Address- " + voterSlipModel.getVoter_Address() + "\n"
                                + "Gender- " + voterSlipModel.getVoter_Gender() + "\n"
                                + "Booth Name- " + voterSlipModel.getElection_Booth_Name() + "\n"

                                + "Booth address- " + voterSlipModel.getElection_Booth_Address() + "\n"
                                + "Election Start Time- " + voterSlipModel.getM_Election_Start_Time() + "\n"
                                + "Election End Time- " + voterSlipModel.getM_Election_End_Time() + "\n"
                                + "Party Name- " + voterSlipModel.getPartyName() + "\n"
                                + "Room number- " + voterSlipModel.getRoom_No() + "\n";

                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("text/plain");
                            shareIntent.putExtra(Intent.EXTRA_TEXT, s);
                            context.startActivity(Intent.createChooser(shareIntent, "Share details using"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        context.findViewById(R.id.blurredloader).setVisibility(View.GONE);
                    }
                });
                RequestQueue requestQueue2 = Volley.newRequestQueue(context);
                requestQueue2.add(stringRequest2);
            }
        });


        dialog.show();

    }
}
