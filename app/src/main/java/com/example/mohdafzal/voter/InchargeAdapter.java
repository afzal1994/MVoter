package com.example.mohdafzal.voter;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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
        holder.area.setText(list.get(position).getArea());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class InfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView count,textView;
        private final List<InchageModel> list;
        private final DashBoard context;
        private final RelativeLayout call;
        private final TextView area;

        public InfoHolder(View itemView, final DashBoard context, final List<InchageModel> list) {
            super(itemView);
            this.context=context;
            this.list=list;
            textView=(TextView)itemView.findViewById(R.id.name);
            area=(TextView)itemView.findViewById(R.id.area);
            count=(TextView)itemView.findViewById(R.id.count);
            call=(RelativeLayout)itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
            call.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId()==call.getId()){
                Intent intent = new Intent(Intent.ACTION_DIAL);
                System.out.println("sdzx"+list.get(getAdapterPosition()).getE_User_PhoneNo());
                intent.setData(Uri.parse("tel:"+list.get(getAdapterPosition()).getE_User_PhoneNo()));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    context.startActivity(intent, ActivityOptions
                            .makeSceneTransitionAnimation(context).toBundle());
                }
                else{
                    context.startActivity(intent);
                }
            }
            else {
                Intent intent = new Intent(context, FieldAssistantVoterList.class);
                intent.putExtra("user_id", list.get(getAdapterPosition()).getE_User_id());
                intent.putExtra("title","Voter List"+"("+list.get(getAdapterPosition()).getIncharge_fname()+" "+
                        list.get(getAdapterPosition()).getIncharge_lname()+")");
                context.startActivity(intent); if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    context.startActivity(intent, ActivityOptions
                            .makeSceneTransitionAnimation(context).toBundle());
                }
                else{
                    context.startActivity(intent);
                }
            }
        }
    }
}

