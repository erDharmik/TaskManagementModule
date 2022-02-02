package com.example.taskmangementmodule;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class achievedproject_myAdapter extends RecyclerView.Adapter<achievedproject_myAdapter.aholder> {

    public static achievedmodel achievedmodeldata[];
    Context context;

    public achievedproject_myAdapter(achievedmodel achievedmodeldata[], Context context) {
        this.achievedmodeldata = achievedmodeldata;
        this.context = context;
    }

    @NonNull
    @Override
    public achievedproject_myAdapter.aholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        View view = layout.inflate(R.layout.achieved_list ,parent , false);
        return new aholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull achievedproject_myAdapter.aholder holder, int position) {

        final achievedmodel newtemp = achievedmodeldata[position];

        holder.id.setText(achievedmodeldata[position].getId());
        holder.title.setText(achievedmodeldata[position].getTitle());
        holder.desc.setText(achievedmodeldata[position].getDescription());
        holder.duedate.setText(achievedmodeldata[position].getEndDate());

        holder.cardclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , particularAchievedProject.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("aid" , newtemp.getId());
                intent.putExtra("atitle" , newtemp.getTitle());
                intent.putExtra("adescription" , newtemp.getDescription());
                intent.putExtra("astartDate" , newtemp.getStartDate());
                intent.putExtra("aendDate" , newtemp.getEndDate());
                intent.putExtra("aleader" , newtemp.getLeader());
                intent.putExtra("amember" , newtemp.getMember());

                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return achievedmodeldata.length;
    }

    class aholder extends RecyclerView.ViewHolder{

        TextView id , title, desc, duedate;
        CardView cardclick;

        public aholder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.aid);
            title = itemView.findViewById(R.id.atitle);
            desc = itemView.findViewById(R.id.adesc);
            duedate = itemView.findViewById(R.id.adate);
            cardclick = itemView.findViewById(R.id.cardclick);

        }
    }
}


