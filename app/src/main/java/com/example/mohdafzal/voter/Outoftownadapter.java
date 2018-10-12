package com.example.mohdafzal.voter;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

class Outoftownadapter extends RecyclerView.Adapter<Outoftownadapter.InfoHolder> {
    private final OutOfTownActivity context;
    private final List<OutOfTownModel> list;

    public Outoftownadapter(OutOfTownActivity outOfTownActivity, List<OutOfTownModel> upVibhagModelList) {
        this.context = outOfTownActivity;
        this.list = upVibhagModelList;
    }

    @Override
    public Outoftownadapter.InfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.outoftown_adapter, parent, false);
        return new InfoHolder(view, context, list);
    }

    @Override
    public void onBindViewHolder(Outoftownadapter.InfoHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.name.setText(list.get(position).getName());
        holder.city.setText(list.get(position).getCity());
        holder.pramukh.setText(list.get(position).getPramukh());
        holder.upvibhag.setText(list.get(position).getUpvibhag());
        if (list.get(position).getPhone() == null) {
            holder.phone.getBackground().setColorFilter(Color.parseColor("#e0e0e0"), PorterDuff.Mode.SRC_ATOP);


        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class InfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView name, pramukh, city, upvibhag;
        private final OutOfTownActivity context;
        private final RelativeLayout phone;
        private final List<OutOfTownModel> list;

        public InfoHolder(View itemView, OutOfTownActivity context, List<OutOfTownModel> list) {
            super(itemView);
            this.context = context;
            this.list = list;
            name = (TextView) itemView.findViewById(R.id.name);
            pramukh = (TextView) itemView.findViewById(R.id.pramukh);
            upvibhag = (TextView) itemView.findViewById(R.id.upvibhag);
            city = (TextView) itemView.findViewById(R.id.city);
            phone = (RelativeLayout) itemView.findViewById(R.id.phone);
            itemView.setOnClickListener(this);
            phone.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if (v.getId() == phone.getId()) {
                if (list.get(getAdapterPosition()).getPhone()!=null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    System.out.println("sdzx" + list.get(getAdapterPosition()).getPhone());
                    intent.setData(Uri.parse("tel:" + list.get(getAdapterPosition()).getPhone()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        context.startActivity(intent, ActivityOptions
                            .makeSceneTransitionAnimation(context).toBundle());
                    } else {
                        context.startActivity(intent);
                    }
                }
                else{
                    Toast.makeText(context, "No phone number available", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}
