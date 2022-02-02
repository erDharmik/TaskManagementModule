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

public class viewbranchmyAdapter extends RecyclerView.Adapter<viewbranchmyAdapter.holder>{

    public static  viewbranchmodel viewbranchmodeldata[];
    Context context;

    public viewbranchmyAdapter(viewbranchmodel[] viewbranchmodeldata , Context context) {
        this.viewbranchmodeldata  = viewbranchmodeldata;
        this.context = context;

    }


    @NonNull
    @Override
    public viewbranchmyAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        View view = layout.inflate(R.layout.view_branch_list ,parent , false);

        return new holder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull viewbranchmyAdapter.holder holder, int position) {

        final viewbranchmodel temp = viewbranchmodeldata[position];

        holder.branchid.setText(viewbranchmodeldata[position].getId());
        holder.branchname.setText(viewbranchmodeldata[position].getBranchName());
        holder.branchcode.setText(viewbranchmodeldata[position].getBranchCode());
        holder.branchdescription.setText(viewbranchmodeldata[position].getDescription());

        holder.cardclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , particular_branch.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("branch_id" , temp.getId());
                intent.putExtra("branch_name" , temp.getBranchName());
                intent.putExtra("branch_code" , temp.getBranchCode());
                intent.putExtra("branch_desc" , temp.getDescription());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return viewbranchmodeldata.length;
    }

    public class holder extends RecyclerView.ViewHolder {

        TextView branchname , branchcode ,branchid , branchdescription;
        CardView cardclick;

        public holder(@NonNull View itemView) {
            super(itemView);


            branchid = itemView.findViewById(R.id.branch_id);
            branchname = itemView.findViewById(R.id.branch_name);
            branchcode = itemView.findViewById(R.id.branch_code);
            branchdescription = itemView.findViewById(R.id.branch_description);
            cardclick = itemView.findViewById(R.id.cardclick);

        }
    }
}