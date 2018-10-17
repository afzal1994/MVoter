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
import com.example.mohdafzal.voter.activities.Voterlist;
import com.example.mohdafzal.voter.models.PostsHirarchyModel;

import java.util.List;

public class VotersPostHirarchyAdapter extends RecyclerView.Adapter<VotersPostHirarchyAdapter.InfoHolder> {

    private final List<PostsHirarchyModel> list;
    private final Voterlist context;


    public VotersPostHirarchyAdapter(Voterlist voterAccordingArealist, List<PostsHirarchyModel> yourList) {
        this.context = voterAccordingArealist;
        this.list = yourList;
    }

   /* public VoterAdapter(AppCompatActivity fieldAssistantVoterList, List<VoterModel> yourList) {
        this.context=fieldAssistantVoterList;
        this.list=yourList;
    }*/

    @Override
    public VotersPostHirarchyAdapter.InfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.posts_hirarchy_adapter_new, parent, false);

        return new VotersPostHirarchyAdapter.InfoHolder(view, context, list);
    }

    @Override
    public void onBindViewHolder(VotersPostHirarchyAdapter.InfoHolder holder, int position) {
        holder.subTitle.setText(list.get(position).getIncharge_fname() + " " + list.get(position).getIncharge_lname());
        holder.count.setText(String.valueOf(position + 1));
        holder.textView.setText(list.get(position).getE_Upvibhag_Name());
        holder.address.setText("Actual Voters : " + list.get(position).get_$TotalVoterCount233() + " | " +
            "Possible Votes : " + list.get(position).get_$VoterCount0() +
            " | " + "Out of town : " + list.get(position).get_$OutOfTownVoterCount259());

        if (list.get(position).getPhonenumber() == null) {
            holder.callImage.setImageResource(R.drawable.call_grey);


        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class InfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView count, textView;
        private final Voterlist context;
        private final List<PostsHirarchyModel> list;
        private final RelativeLayout call;
        private final TextView subTitle;
        private final TextView address;
        private final ImageView callImage;

        public InfoHolder(View itemView, Voterlist context, List<PostsHirarchyModel> list) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.name);
            count = (TextView) itemView.findViewById(R.id.number);
            call = (RelativeLayout) itemView.findViewById(R.id.call);
            callImage = (ImageView) itemView.findViewById(R.id.call_image);

            subTitle = (TextView) itemView.findViewById(R.id.subTitle);
            address = (TextView) itemView.findViewById(R.id.address);
            this.context = context;
            this.list = list;
            itemView.setOnClickListener(this);
            call.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == call.getId()) {
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
                    context.getVoterList();
                }
            }

        }
    }
}
