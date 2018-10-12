package com.example.mohdafzal.voter;

import android.app.ActivityOptions;
import android.content.Intent;
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

import java.util.List;

class UpvibhagAdapter extends RecyclerView.Adapter<UpvibhagAdapter.InfoHolder> {

    private final List<UpVibhagModel> list;
    private final UpvibhagActivity context;


    public UpvibhagAdapter(UpvibhagActivity voterAccordingArealist, List<UpVibhagModel> yourList) {
        this.context = voterAccordingArealist;
        this.list = yourList;
    }

   /* public VoterAdapter(AppCompatActivity fieldAssistantVoterList, List<VoterModel> yourList) {
        this.context=fieldAssistantVoterList;
        this.list=yourList;
    }*/

    @Override
    public UpvibhagAdapter.InfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upvibhag_adapter, parent, false);

        return new UpvibhagAdapter.InfoHolder(view, context, list);
    }

    @Override
    public void onBindViewHolder(UpvibhagAdapter.InfoHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.subTitle.setText(list.get(position).getIncharge_fname() + " " + list.get(position).getIncharge_lname());
        holder.count.setText(String.valueOf(position + 1));
        holder.textView.setText(list.get(position).getE_Upvibhag_Name());
        holder.outoftowncount.setText("Total Voters Count : " + list.get(position).get_$TotalVoterCount327());
        holder.address.setText("Voters Count : " + list.get(position).get_$VoterCount5());
        if (list.get(position).getPhonenumber() == null) {
            holder.call.getBackground().setColorFilter(Color.parseColor("#e0e0e0"), PorterDuff.Mode.SRC_ATOP);


        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class InfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView count, textView;
        private final UpvibhagActivity context;
        private final List<UpVibhagModel> list;
        private final RelativeLayout call;
        private final TextView subTitle;
        private final TextView address;
        private final TextView outoftowncount;

        public InfoHolder(View itemView, UpvibhagActivity context, List<UpVibhagModel> list) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.name);
            count = (TextView) itemView.findViewById(R.id.number);
            call = (RelativeLayout) itemView.findViewById(R.id.call);
            subTitle = (TextView) itemView.findViewById(R.id.subTitle);
            address = (TextView) itemView.findViewById(R.id.address);
            outoftowncount=(TextView)itemView.findViewById(R.id.outoftowncount);

            this.context = context;
            this.list = list;
            itemView.setOnClickListener(this);
            call.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == call.getId()) {
                if (list.get(getAdapterPosition()).getPhonenumber()!=null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    System.out.println("sdzx" + list.get(getAdapterPosition()).getPhonenumber());
                    intent.setData(Uri.parse("tel:" + list.get(getAdapterPosition()).getPhonenumber()));
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

            } else {
                if (list.get(getAdapterPosition()).getE_User_id() <= 5) {
                    context.sendreq(list.get(getAdapterPosition()).getE_User_id());
                    context.getCount(list.get(getAdapterPosition()).getE_User_id());
                }else {
                    Intent intent = new Intent(context, UpvibhagOnclick.class);
                    intent.putExtra("incharge_id", list.get(getAdapterPosition()).getE_User_id());
                    intent.putExtra("incharge_name", list.get(getAdapterPosition()).getIncharge_fname() + " " + list.get(getAdapterPosition()).getIncharge_lname());
                    intent.putExtra("phone_number", list.get(getAdapterPosition()).getPhonenumber());
                    intent.putExtra("voter_count", list.get(getAdapterPosition()).get_$TotalVoterCount327());
                    intent.putExtra("vibhag_name", list.get(getAdapterPosition()).getE_Upvibhag_Name());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        context.startActivity(intent, ActivityOptions
                            .makeSceneTransitionAnimation(context).toBundle());
                    } else {
                        context.startActivity(intent);
                    }


                }
            }

        }
    }
}
