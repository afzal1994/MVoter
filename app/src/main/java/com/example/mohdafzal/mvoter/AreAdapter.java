package com.example.mohdafzal.mvoter;

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

        return new InfoHolder(view);
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

    public class InfoHolder extends RecyclerView.ViewHolder {
        private final TextView count,textView;

        public InfoHolder(View itemView) {
            super(itemView);
             textView=(TextView)itemView.findViewById(R.id.name);
             count=(TextView)itemView.findViewById(R.id.count);

        }
    }
}
