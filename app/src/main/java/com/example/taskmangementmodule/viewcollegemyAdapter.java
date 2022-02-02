package com.example.taskmangementmodule;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class viewcollegemyAdapter extends RecyclerView.Adapter<viewcollegemyAdapter.holder> {

    public static  viewcollegemodel viewcollegemodeldata[];
    Context context;

    public viewcollegemyAdapter(viewcollegemodel viewcollegemodeldata[],Context context) {
        this.viewcollegemodeldata = viewcollegemodeldata;
        this.context = context;

    }

    @NonNull
    @Override
    public viewcollegemyAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        View view = layout.inflate(R.layout.view_college_list ,parent , false);

        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewcollegemyAdapter.holder holder, int position) {

        final viewcollegemodel temp = viewcollegemodeldata[position];

        holder.college_id.setText(viewcollegemodeldata[position].getId());
        holder.college_name.setText(viewcollegemodeldata[position].getName());
        holder.college_code.setText(viewcollegemodeldata[position].getCode());
        holder.college_description.setText(viewcollegemodeldata[position].getDescription());

        holder.cardclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , particular_college.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("college_id" , temp.getId());
                intent.putExtra("college_name" , temp.getName());
                intent.putExtra("college_desc" , temp.getDescription());
                intent.putExtra("college_code" , temp.getCode());

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return viewcollegemodeldata.length;
    }

    public class holder extends RecyclerView.ViewHolder {

        TextView college_id , college_name, college_code , college_description;
        CardView cardclick;

        public holder(@NonNull View itemView) {
            super(itemView);


            college_id = itemView.findViewById(R.id.college_id);
            college_name = itemView.findViewById(R.id.college_name);
            college_code =  itemView.findViewById(R.id.college_code);
            college_description = itemView.findViewById(R.id.college_description);
            cardclick = itemView.findViewById(R.id.cardclick);

        }
    }

}
