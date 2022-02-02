package com.example.taskmangementmodule;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class myAdapter extends RecyclerView.Adapter<myAdapter.holder> {

   public static model data[];
    Context context;

    public myAdapter(model[] data, Context context) {
        this.data = data;
        this.context = context;
    }


    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        View view = layout.inflate(R.layout.view_project_list ,parent , false);
        return new holder(view);
    }

    @Override


    public void onBindViewHolder(@NonNull holder holder, @SuppressLint("RecyclerView") int position) {


        final model temp = data[position];

        holder.id.setText(data[position].getId());
        holder.title.setText(data[position].getTitle());
        holder.endDate.setText(data[position].getEndDate());
        holder.description.setText(data[position].getDescription());

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
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    class holder extends RecyclerView.ViewHolder{


        TextView id , title, endDate , description;
        CardView cardclick;
        public holder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id);
            title = itemView.findViewById(R.id.project_title);
            endDate =  itemView.findViewById(R.id.due_date);
            description = itemView.findViewById(R.id.project_desc);
            cardclick = itemView.findViewById(R.id.cardclick);

        }
    }
}
