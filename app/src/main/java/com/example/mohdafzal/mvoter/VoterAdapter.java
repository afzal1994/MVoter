package com.example.mohdafzal.mvoter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Afzal on 10-Oct-17.
 */

public class VoterAdapter extends RecyclerView.Adapter<VoterAdapter.InfoHolder> {

    private final List<VoterModel> list;
    private final AppCompatActivity context;

    public VoterAdapter(Voterlist dashBoard, List<VoterModel> yourList) {
        this.list=yourList;
        this.context=dashBoard;

    }

    public VoterAdapter(VoterAccordingArealist voterAccordingArealist, List<VoterModel> yourList) {
        this.context=voterAccordingArealist;
        this.list=yourList;
    }

    public VoterAdapter(FieldAssistantVoterList fieldAssistantVoterList, List<VoterModel> yourList) {
        this.context=fieldAssistantVoterList;
        this.list=yourList;
    }

    @Override
    public VoterAdapter.InfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.voteradapter,parent,false);

        return new VoterAdapter.InfoHolder(view,context,list);
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

    public class InfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView count,textView;
        private final AppCompatActivity context;
        private final List<VoterModel> list;
        private final RelativeLayout call;

        public InfoHolder(View itemView, AppCompatActivity context, List<VoterModel> list) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.name);
            count=(TextView)itemView.findViewById(R.id.number);
            call=(RelativeLayout)itemView.findViewById(R.id.call);
            this.context=context;
            this.list=list;
            itemView.setOnClickListener(this);
            call.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v.getId()==call.getId()){
                Intent intent = new Intent(Intent.ACTION_DIAL);
                System.out.println("sdzx"+list.get(getAdapterPosition()).getVoter_PhoneNo());
                intent.setData(Uri.parse("tel:"+list.get(getAdapterPosition()).getVoter_PhoneNo()));
                context.startActivity(intent);

            }
            else{
                Intent intent=new Intent(context,AddNewVoter.class);
                intent.putExtra("voter_id",list.get(getAdapterPosition()).getVoter_Id());
                context.startActivity(intent);

                context.finish();
            }

        }
    }
}
