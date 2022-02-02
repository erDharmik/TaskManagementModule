package com.example.taskmangementmodule;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class dashboard_myAdapter extends RecyclerView.Adapter<dashboard_myAdapter.holder> {

    public static model data[];
    Context context;

    public dashboard_myAdapter(model[] data, Context context) {
        this.data = data;
        this.context = context;
    }


    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        View view = layout.inflate(R.layout.dashbord_raw ,parent , false);
        return new holder(view);
    }

    @Override


    public void onBindViewHolder(@NonNull holder holder, @SuppressLint("RecyclerView") int position) {

        final model temp = data[position];

        holder.id.setText(data[position].getId());
        holder.title.setText(data[position].getTitle());
        Glide.with(holder.title.getContext()).load("http://192.168.154.249/Log_In/images/"+data[position].getProgress()).into(holder.progressBar);


        holder.cardclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , perticular_details.class);
                intent.putExtra("id" , temp.getId());
                intent.putExtra("title" , temp.getTitle());
                intent.putExtra("description" , temp.getDescription());
                intent.putExtra("startDate" , temp.getStartDate());
                intent.putExtra("endDate" , temp.getEndDate());
                intent.putExtra("leader" , temp.getLeader());
                intent.putExtra("member" , temp.getMember());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                SharedPreferences sharedPreferences = context.getSharedPreferences("demo" , context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("commentId", temp.getId());
                editor.apply();

            }
        });



    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    class holder extends RecyclerView.ViewHolder{


        TextView id , title;
        ImageView eye , progressBar;
        CardView cardclick;
        public holder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.did);
            title = itemView.findViewById(R.id.dproject);
            eye = itemView.findViewById(R.id.eye);
            progressBar = itemView.findViewById(R.id.img);
            cardclick = itemView.findViewById(R.id.cardclick);

        }
    }
}
