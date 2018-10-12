package com.example.mohdafzal.voter;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Afzal on 09-Oct-17.
 */

public class AreAdapter extends RecyclerView.Adapter<AreAdapter.InfoHolder> {

    private final List<AreaModel> list;
    private final DashBoard context;

    public AreAdapter(DashBoard dashBoard, List<AreaModel> yourList) {
        this.list=yourList;
        this.context=dashBoard;

    }

    @Override
    public AreAdapter.InfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.areaadapter,parent,false);

        return new InfoHolder(view,context,list);
    }

    @Override
    public void onBindViewHolder(AreAdapter.InfoHolder holder, int position) {
holder.textView.setText(list.get(position).getElection_Area_Name());
        holder.count.setText(String.valueOf(list.get(position).getVotercount()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class InfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView count,textView;
        private final DashBoard context;
        private final List<AreaModel> list;

        public InfoHolder(View itemView, DashBoard context, List<AreaModel> list) {
            super(itemView);
            this.context=context;
            this.list=list;
             textView=(TextView)itemView.findViewById(R.id.name);
             count=(TextView)itemView.findViewById(R.id.count);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context,VoterAccordingArealist.class);
            intent.putExtra("area_id",list.get(getAdapterPosition()).getElection_Area_id());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                context.startActivity(intent, ActivityOptions
                        .makeSceneTransitionAnimation(context).toBundle());
            }
            else{
                context.startActivity(intent);
            }
        }
    }
}
