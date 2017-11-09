package com.example.mohdafzal.mvoter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Afzal on 10-Oct-17.
 */

public class WorkerAdapter extends RecyclerView.Adapter<WorkerAdapter.InfoHolder> {

    private final List<WorkerModel> list;
    private final DashBoard context;

    public WorkerAdapter(DashBoard dashBoard, List<WorkerModel> yourList) {
        this.list=yourList;
        this.context=dashBoard;

    }

    @Override
    public WorkerAdapter.InfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.inchargeadapter,parent,false);

        return new WorkerAdapter.InfoHolder(view);
    }

    @Override
    public void onBindViewHolder(WorkerAdapter.InfoHolder holder, int position) {
        holder.textView.setText(list.get(position).getIncharge_fname()+" "+list.get(position).getIncharge_lname());
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
