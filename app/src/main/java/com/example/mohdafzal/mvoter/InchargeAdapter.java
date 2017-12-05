package com.example.mohdafzal.mvoter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Afzal on 10-Oct-17.
 */

public class InchargeAdapter extends RecyclerView.Adapter<InchargeAdapter.InfoHolder> {

    private final List<InchageModel> list;
    private final DashBoard context;

    public InchargeAdapter(DashBoard dashBoard, List<InchageModel> yourList) {
        this.list=yourList;
        this.context=dashBoard;

    }

    @Override
    public InchargeAdapter.InfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.inchargeadapter,parent,false);

        return new InchargeAdapter.InfoHolder(view,context,list);
    }

    @Override
    public void onBindViewHolder(InchargeAdapter.InfoHolder holder, int position) {
        holder.textView.setText(list.get(position).getIncharge_fname()+" "+list.get(position).getIncharge_lname());
        holder.count.setText(String.valueOf(list.get(position).getVotercount()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class InfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView count,textView;
        private final List<InchageModel> list;
        private final DashBoard context;

        public InfoHolder(View itemView, DashBoard context, List<InchageModel> list) {
            super(itemView);
            this.context=context;
            this.list=list;
            textView=(TextView)itemView.findViewById(R.id.name);
            count=(TextView)itemView.findViewById(R.id.count);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context,FieldAssistantVoterList.class);
            intent.putExtra("user_id",list.get(getAdapterPosition()).getE_User_id());
            context.startActivity(intent);

        }
    }
}

