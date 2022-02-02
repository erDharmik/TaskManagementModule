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
import androidx.recyclerview.widget.RecyclerView;

public class addTask_myAdapter extends RecyclerView.Adapter<addTask_myAdapter.holder> {

    public static model data[];
    Context context;

    public addTask_myAdapter(model[] data, Context context) {
        this.data = data;
        this.context = context;
    }


    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        View view = layout.inflate(R.layout.addtask_raw ,parent , false);
        return new holder(view);
    }

    @Override


    public void onBindViewHolder(@NonNull holder holder, @SuppressLint("RecyclerView") int position) {

        final model temp = data[position];

        holder.id.setText(data[position].getId());
        holder.title.setText(data[position].getTitle());

        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context, chat.class);
//golden line
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
        ImageView chat;
        public holder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.did);
            title = itemView.findViewById(R.id.dproject);
            chat = itemView.findViewById(R.id.chat_raw);

        }
    }
}
