package com.example.mohdafzal.voter.adapter;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohdafzal.voter.R;
import com.example.mohdafzal.voter.activities.PendingDoneVotersList;
import com.example.mohdafzal.voter.activities.VotingDayActivity;
import com.example.mohdafzal.voter.models.VotingDayModel;

import java.util.List;

public class VotingDayAdapter extends RecyclerView.Adapter<VotingDayAdapter.InfoHolder> {
    private final VotingDayActivity context;
    private final List<VotingDayModel> list;

    public VotingDayAdapter(VotingDayActivity outOfTownActivity, List<VotingDayModel> upVibhagModelList) {
        this.context = outOfTownActivity;
        this.list = upVibhagModelList;
    }

    @Override
    public VotingDayAdapter.InfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.voting_day_adapter, parent, false);
        return new VotingDayAdapter.InfoHolder(view, context, list);
    }

    @Override
    public void onBindViewHolder(VotingDayAdapter.InfoHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.city.setText(list.get(position).getIncharge_fname() + " " + list.get(position).getIncharge_lname());
        holder.name.setText(list.get(position).getE_Upvibhag_Name());
        holder.done.setText(String.valueOf(list.get(position).get_$DoneVoterCount211()));
        holder.pending.setText(String.valueOf(list.get(position).get_$PendingVoterCount305()));
        holder.total.setText(String.valueOf(Integer.parseInt(list.get(position).get_$PendingVoterCount305()) + Integer.parseInt(list.get(position).get_$DoneVoterCount211())));

        if (list.get(position).getPhonenumber() == null) {
            holder.callImage.setImageResource(R.drawable.call_grey);


        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class InfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView name, done, city, total;
        private final VotingDayActivity context;
        private final List<VotingDayModel> list;
        private final TextView pending;
        private final RelativeLayout phone;
        private final ImageView callImage;


        public InfoHolder(View itemView, VotingDayActivity context, List<VotingDayModel> list) {
            super(itemView);
            this.context = context;
            this.list = list;
            name = (TextView) itemView.findViewById(R.id.name);
            done = (TextView) itemView.findViewById(R.id.done);
            pending = (TextView) itemView.findViewById(R.id.pending);
            city = (TextView) itemView.findViewById(R.id.city);

            callImage = (ImageView) itemView.findViewById(R.id.call_image);

            phone = (RelativeLayout) itemView.findViewById(R.id.phone);
            total = (TextView) itemView.findViewById(R.id.total);

            itemView.setOnClickListener(this);
            phone.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == phone.getId()) {
                if (list.get(getAdapterPosition()).getPhonenumber() != null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    System.out.println("sdzx" + list.get(getAdapterPosition()).getPhonenumber());
                    intent.setData(Uri.parse("tel:" + list.get(getAdapterPosition()).getPhonenumber()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        context.startActivity(intent, ActivityOptions
                            .makeSceneTransitionAnimation(context).toBundle());
                    } else {
                        context.startActivity(intent);
                    }
                } else {
                    Toast.makeText(context, "No phone number available", Toast.LENGTH_SHORT).show();
                }

            } else {
                if (list.get(getAdapterPosition()).getE_User_id() <= 5) {
                    context.sendreq(list.get(getAdapterPosition()).getE_User_id());
                } else {
                    Intent intent = new Intent(context, PendingDoneVotersList.class);
                    intent.putExtra("id", list.get(getAdapterPosition()).getE_User_id());
                    intent.putExtra("done", list.get(getAdapterPosition()).get_$DoneVoterCount211());
                    intent.putExtra("pending", list.get(getAdapterPosition()).get_$PendingVoterCount305());
                    intent.putExtra("incharge_name", list.get(getAdapterPosition()).getIncharge_fname() + " " + list.get(getAdapterPosition()).getIncharge_lname());
                    intent.putExtra("upvibhagName", list.get(getAdapterPosition()).getE_Upvibhag_Name());
                    intent.putExtra("phone_number", list.get(getAdapterPosition()).getPhonenumber());
                    context.startActivity(intent);
                }


            }

        }
    }
}
