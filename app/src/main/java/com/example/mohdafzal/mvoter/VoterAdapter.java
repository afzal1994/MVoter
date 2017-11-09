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

public class VoterAdapter extends RecyclerView.Adapter<VoterAdapter.InfoHolder> {

    private final List<VoterModel> list;
    private final Voterlist context;

    public VoterAdapter(Voterlist dashBoard, List<VoterModel> yourList) {
        this.list=yourList;
        this.context=dashBoard;

    }

    @Override
    public VoterAdapter.InfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.voteradapter,parent,false);

        return new VoterAdapter.InfoHolder(view);
    }

    @Override
    public void onBindViewHolder(VoterAdapter.InfoHolder holder, int position) {
        holder.textView.setText(list.get(position).getVoter_Eng_FName()+" "+list.get(position).getVoter_Eng_LName());
        holder.count.setText(String.valueOf(position+1));
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
            count=(TextView)itemView.findViewById(R.id.number);

        }
    }
}
