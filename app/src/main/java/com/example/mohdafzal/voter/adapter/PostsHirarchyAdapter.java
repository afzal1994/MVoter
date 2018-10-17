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
import com.example.mohdafzal.voter.activities.PostsHirarchyActivity;
import com.example.mohdafzal.voter.activities.UpvibhagVotersList;
import com.example.mohdafzal.voter.models.PostsHirarchyModel;

import java.util.List;

public class PostsHirarchyAdapter extends RecyclerView.Adapter<PostsHirarchyAdapter.InfoHolder> {

    private final List<PostsHirarchyModel> list;
    private final PostsHirarchyActivity context;


    public PostsHirarchyAdapter(PostsHirarchyActivity voterAccordingArealist, List<PostsHirarchyModel> yourList) {
        this.context = voterAccordingArealist;
        this.list = yourList;
    }

   /* public VoterAdapter(AppCompatActivity fieldAssistantVoterList, List<VoterModel> yourList) {
        this.context=fieldAssistantVoterList;
        this.list=yourList;
    }*/

    @Override
    public PostsHirarchyAdapter.InfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.posts_hirarchy_adapter_new, parent, false);

        return new PostsHirarchyAdapter.InfoHolder(view, context, list);
    }

    @Override
    public void onBindViewHolder(PostsHirarchyAdapter.InfoHolder holder, int position) {
        holder.setIsRecyclable(false);
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
        private final PostsHirarchyActivity context;
        private final List<PostsHirarchyModel> list;
        private final RelativeLayout call;
        private final TextView subTitle;
        private final TextView address;
        private final TextView outoftowncount;
        private final ImageView callImage;

        public InfoHolder(View itemView, PostsHirarchyActivity context, List<PostsHirarchyModel> list) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.name);
            count = (TextView) itemView.findViewById(R.id.number);
            call = (RelativeLayout) itemView.findViewById(R.id.call);
            subTitle = (TextView) itemView.findViewById(R.id.subTitle);
            address = (TextView) itemView.findViewById(R.id.address);
            callImage = (ImageView) itemView.findViewById(R.id.call_image);

            outoftowncount = (TextView) itemView.findViewById(R.id.outoftowncount);
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
                    context.getCount(list.get(getAdapterPosition()).getE_User_id());
                } else {
                    Intent intent = new Intent(context, UpvibhagVotersList.class);
                    intent.putExtra("incharge_id", list.get(getAdapterPosition()).getE_User_id());
                    intent.putExtra("incharge_name", list.get(getAdapterPosition()).getIncharge_fname() + " " + list.get(getAdapterPosition()).getIncharge_lname());
                    intent.putExtra("phone_number", list.get(getAdapterPosition()).getPhonenumber());
                    intent.putExtra("voter_count", list.get(getAdapterPosition()).get_$TotalVoterCount233());
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
