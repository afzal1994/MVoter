package com.example.mohdafzal.voter.Adapter;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohdafzal.voter.ElectionCountWRTBooth;
import com.example.mohdafzal.voter.R;
import com.example.mohdafzal.voter.VotingCountModel;

import java.util.List;

/**
 * Created by Afzal on 14-Feb-18.
 */

public class CountAdapter extends RecyclerView.Adapter<CountAdapter.InfoHolder> {
    private final FragmentActivity context;
    private final List<VotingCountModel> list;
    private final int area_id;

    public CountAdapter(FragmentActivity activity, List<VotingCountModel> yourList, int areapos) {
        this.area_id=areapos;
        this.context=activity;
        this.list=yourList;
    }

    @Override
    public InfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.count_adapter,parent,false);
        return new InfoHolder(view,context,list);
    }

    @Override
    public void onBindViewHolder(InfoHolder holder, int position) {
        holder.textTitle.setText(list.get(position).getElection_Booth_Name());
        holder.counteText.setText(list.get(position).getVotedVoters()+"/"+list.get(position).getTotalvoters());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class InfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView counteText,textTitle;
        private final FragmentActivity context;
        private final List<VotingCountModel> list;

        public InfoHolder(View itemView, FragmentActivity context, List<VotingCountModel> list) {
            super(itemView);
            this.context=context;
            this.list=list;
             counteText=(TextView)itemView.findViewById(R.id.countText);
             textTitle=(TextView)itemView.findViewById(R.id.textTitle);
             itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context, ElectionCountWRTBooth.class);
            intent.putExtra("booth_name",list.get(getAdapterPosition()).getElection_Booth_Name());
            intent.putExtra("booth_id",list.get(getAdapterPosition()).getElection_Booth_Id());
            intent.putExtra("area_id",area_id);

            context.startActivity(intent);


        }
    }
}
