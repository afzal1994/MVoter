package com.example.mohdafzal.voter;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
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

import org.w3c.dom.Text;

import java.util.List;

public class UpVibhagOnclickAdapter extends RecyclerView.Adapter<UpVibhagOnclickAdapter.InfoHolder> {

    private final List<VoterModel> list;
    private final AppCompatActivity context;


    public UpVibhagOnclickAdapter(AppCompatActivity voterAccordingArealist, List<VoterModel> yourList) {
        this.context=voterAccordingArealist;
        this.list=yourList;
    }

   /* public VoterAdapter(AppCompatActivity fieldAssistantVoterList, List<VoterModel> yourList) {
        this.context=fieldAssistantVoterList;
        this.list=yourList;
    }*/

    @Override
    public UpVibhagOnclickAdapter.InfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.upvibhag_adapter,parent,false);

        return new UpVibhagOnclickAdapter.InfoHolder(view,context,list);
    }

    @Override
    public void onBindViewHolder(UpVibhagOnclickAdapter.InfoHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.textView.setText(list.get(position).getVoter_Eng_FName()+" "+list.get(position).getVoter_Eng_LName());
        holder.subTitle.setText(list.get(position).getE_Upvibhag_Name());
        holder.address.setText(list.get(position).getVoter_Address());
        if (list.get(position).getVoter_PhoneNo() == null) {
            holder.call.getBackground().setColorFilter(Color.parseColor("#e0e0e0"), PorterDuff.Mode.SRC_ATOP);


        }
        holder.outOfTownCount.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class InfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView count,textView;
        private final AppCompatActivity context;
        private final List<VoterModel> list;
        private final RelativeLayout call;
        private final TextView subTitle;
        private final TextView address;
        private final TextView outOfTownCount;

        public InfoHolder(View itemView, final AppCompatActivity context, final List<VoterModel> list) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.name);
            count=(TextView)itemView.findViewById(R.id.number);
            call=(RelativeLayout)itemView.findViewById(R.id.call);
            subTitle = (TextView) itemView.findViewById(R.id.subTitle );
            address = (TextView) itemView.findViewById(R.id.address);
            outOfTownCount=(TextView)itemView.findViewById(R.id.outoftowncount);
            this.context=context;
            this.list=list;
            itemView.setOnClickListener(this);
            call.setOnClickListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.delete_dialog);
                    dialog.getWindow().getAttributes().windowAnimations = R.style.My_Dialog_Theme;

                    dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                        }
                    });
                    dialog.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.hide();
                            context.findViewById(R.id.blurredloader).setVisibility(View.VISIBLE);
                            String url2 = "http://electionapp.uxservices.in/Web_Services/Delete_Voters.asmx/Delete?voter_Id=" + list.get(getAdapterPosition()).getVoter_Id();
                            System.out.println("thisurl" + url2);
                            StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    String jsonArray = response.replace("<string xmlns=\"http://electionapp.uxservices.in/\">", "").replace("</string>", "");
                                    System.out.println("jsonArray" + jsonArray);
                                    context.finish();
                                    Toast.makeText(context, "Voter Deleted Sucessfully", Toast.LENGTH_SHORT).show();
                                    context.findViewById(R.id.blurredloader).setVisibility(View.GONE);


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
                    return false;

                }
            });

        }

        @Override
        public void onClick(View v) {
            if (v.getId()==call.getId()){
                if (list.get(getAdapterPosition()).getVoter_PhoneNo()!=null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    System.out.println("sdzx" + list.get(getAdapterPosition()).getVoter_PhoneNo());
                    intent.setData(Uri.parse("tel:" + list.get(getAdapterPosition()).getVoter_PhoneNo()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        context.startActivity(intent, ActivityOptions
                            .makeSceneTransitionAnimation(context).toBundle());
                    } else {
                        context.startActivity(intent);
                    }
                }
                else{
                    Toast.makeText(context, "No phone number available", Toast.LENGTH_SHORT).show();
                }

            }
            else{
               /* Intent intent=new Intent(context,AddNewVoter.class);
                intent.putExtra("voter_id",list.get(getAdapterPosition()).getVoter_Id());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    context.startActivity(intent, ActivityOptions
                        .makeSceneTransitionAnimation(context).toBundle());
                }
                else{
                    context.startActivity(intent);
                }

                context.finish();*/
            }

        }
    }
}
