package com.example.mohdafzal.voter;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class PendingDoneAdapter extends RecyclerView.Adapter<PendingDoneAdapter.InfoHoler> {
    private final PendinDoneVotersList context;
    private final List<VoterModel> list;
    private final int option;

    public PendingDoneAdapter(PendinDoneVotersList pendinDoneVotersList, List<VoterModel> yourList, int i) {
        this.context = pendinDoneVotersList;
        this.list = yourList;
        this.option = i;

    }

    @Override
    public PendingDoneAdapter.InfoHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_done_adapter, parent, false);
        return new InfoHoler(view, list, context);
    }

    @Override
    public void onBindViewHolder(final PendingDoneAdapter.InfoHoler holder, final int position) {
        holder.textView.setText(list.get(position).getVoter_Eng_FName() + " " + list.get(position).getVoter_Eng_LName());
        SharedPreferences sharedPreference2 = context.getSharedPreferences("userid", MODE_PRIVATE);
        int user_id2 = sharedPreference2.getInt("user_option", 0);
        holder.count.setText(String.valueOf(position + 1));
        if (option != 2 || user_id2 != 6) {
            holder.status.setVisibility(View.GONE);
        }


        holder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url2 = null;
                url2 = "http://electionapp.uxservices.in/Web_Services/Update_Voter_Status.asmx/My_Voter_List?voter_id=" + list.get(position).getVoter_Id();

                System.out.println("thisurl" + url2);
                StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        context.findViewById(R.id.blurredloader).setVisibility(View.GONE);

                        String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                        try {
                            System.out.println("this" + jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", ""));
                            JSONObject jsonObject1 = null;
                            JSONArray jsonObject = new JSONArray(jsonArray.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "").replace("<string xmlns=\"http://electionapp.uxservices.in\">", ""));

                            jsonObject1 = jsonObject.getJSONObject(0);
                            Toast.makeText(context, jsonObject1.getString("msg"), Toast.LENGTH_SHORT).show();

                            context.sendreq(2);

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
        holder.subTitle.setText(list.get(position).getE_Upvibhag_Name());
        holder.address.setText(list.get(position).getVoter_Address());
        if (list.get(position).getVoter_PhoneNo() == null) {
            holder.call.getBackground().setColorFilter(Color.parseColor("#e0e0e0"), PorterDuff.Mode.SRC_ATOP);


        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class InfoHoler extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView count, textView;
        private final PendinDoneVotersList context;
        private final List<VoterModel> list;
        private final RelativeLayout call;
        private final TextView subTitle;
        private final TextView address;
        private final RelativeLayout status;
        private final TextView statusText;

        public InfoHoler(View itemView, List<VoterModel> list, PendinDoneVotersList context) {
            super(itemView);
            this.context = context;
            this.list = list;
            textView = (TextView) itemView.findViewById(R.id.name);
            count = (TextView) itemView.findViewById(R.id.number);
            call = (RelativeLayout) itemView.findViewById(R.id.call);
            subTitle = (TextView) itemView.findViewById(R.id.subTitle);
            address = (TextView) itemView.findViewById(R.id.address);
            status = (RelativeLayout) itemView.findViewById(R.id.status);
            statusText = (TextView) itemView.findViewById(R.id.statusText);
            itemView.setOnClickListener(this);
            call.setOnClickListener(this);
            status.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == call.getId()) {
                if (list.get(getAdapterPosition()).getVoter_PhoneNo() != null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    System.out.println("sdzx" + list.get(getAdapterPosition()).getVoter_PhoneNo());
                    intent.setData(Uri.parse("tel:" + list.get(getAdapterPosition()).getVoter_PhoneNo()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        context.startActivity(intent, ActivityOptions
                            .makeSceneTransitionAnimation(context).toBundle());
                    } else {
                        context.startActivity(intent);
                    }
                } else {
                    Toast.makeText(context, "No phone number available", Toast.LENGTH_SHORT).show();
                }

            }


        }
    }


}
