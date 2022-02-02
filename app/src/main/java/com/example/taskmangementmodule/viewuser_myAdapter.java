package com.example.taskmangementmodule;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class viewuser_myAdapter extends RecyclerView.Adapter<viewuser_myAdapter.holder> {

    public static usermodel userdata[];
    Context context;

    public viewuser_myAdapter(usermodel[] userdata, Context context) {
        this.userdata = userdata;
        this.context = context;
    }

    @NonNull
    @Override
    public viewuser_myAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        View view = layout.inflate(R.layout.viewuser_row,parent,false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewuser_myAdapter.holder holder, int position) {

        final usermodel temp = userdata[position];

        holder.userid.setText(userdata[position].getId());
        holder.userName.setText(userdata[position].getName());
        holder.userEmail.setText(userdata[position].getEmail());


        holder.cardclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , user_info.class);
                intent.putExtra("user_id" , temp.getId());
                intent.putExtra("user_name" , temp.getName());
                intent.putExtra("user_email" , temp.getEmail());
                intent.putExtra("user_contact" , temp.getContact());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userdata.length;
    }

    public class holder extends RecyclerView.ViewHolder {

        TextView userid, userName, userEmail;
        CardView cardclick;
        public holder(@NonNull View itemView) {
            super(itemView);

            userid = itemView.findViewById(R.id.userid1);
            userName = itemView.findViewById(R.id.username1);
            userEmail = itemView.findViewById(R.id.useremail1);
            cardclick = itemView.findViewById(R.id.cardclick);


        }
    }
}
