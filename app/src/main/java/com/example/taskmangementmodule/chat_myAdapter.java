package com.example.taskmangementmodule;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class chat_myAdapter extends RecyclerView.Adapter {

    chat_model data[];
    Context context;

    public chat_myAdapter(chat_model[] data, Context context) {
        this.data = data;
        this.context = context;
    }




    @Override
    public int getItemViewType(int position) {

      if(data[position].getImage().equals("0") && data[position].getPdf().equals("0")){
          return 1;
        }

      else if(data[position].getImage().equals("0") && !data[position].getPdf().equals("0")){
          return 2;
      }


      else {
          return 3;
      }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;

        if(viewType == 1){
          view = layoutInflater.inflate(R.layout.comment_raw , parent , false);
          return new commentHolder(view);
        }
          else if (viewType == 2){
            view = layoutInflater.inflate(R.layout.pdfdownloadraw , parent , false);
            return new pdfHolder(view);
        }
        else {
            view = layoutInflater.inflate(R.layout.imageview_raw, parent, false);
            return new commentWithImageHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


          if(data[position].getImage().equals("0") && data[position].getPdf().equals("0")) {


              commentHolder commentHolder = (commentHolder) holder;

              commentHolder.taskId.setText(data[position].getTaskid());
              commentHolder.datetime.setText(data[position].getDate_time());
              commentHolder.description.setText(data[position].getDescription());
              commentHolder.username.setText(data[position].getUsername());

          }

          else if(data[position].getImage().equals("0") && !data[position].getPdf().equals("0")){
              pdfHolder pdfHolder = (pdfHolder) holder;


              pdfHolder.name.setText(data[position].getUsername());
              pdfHolder.date_time.setText(data[position].getDate_time());
              pdfHolder.caption.setText(data[position].getDescription());

             pdfHolder.pdf.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {

                     Intent intent = new Intent( context, pdfRetrive.class);
                     intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                   String commentId =  data[position].getComment_id();
                   String pdfName = data[position].getPdf();
                   intent.putExtra("commentId",commentId);
                   intent.putExtra("pdfName",pdfName);
                     context.startActivity(intent);




                 }
             });


          }

          else {


              commentWithImageHolder commentImage = (commentWithImageHolder) holder;

              commentImage.datetime.setText(data[position].getDate_time());
              commentImage.caption.setText(data[position].getDescription());
              commentImage.username.setText(data[position].getUsername());
              Glide.with(commentImage.caption.getContext()).load("http://192.168.154.249/crudapi/images/"+data[position].getImage()).into(commentImage.image);
          }


        }





    @Override
    public int getItemCount() {
        return data.length;
    }


    class  commentHolder extends RecyclerView.ViewHolder{

         TextView username;
         TextView datetime;
        TextView description;
        TextView taskId;



        public commentHolder(@NonNull View itemView) {
            super(itemView);

            username= itemView.findViewById(R.id.Username);
           datetime= itemView.findViewById(R.id.commentTime);
           description= itemView.findViewById(R.id.comment_description);
           taskId = itemView.findViewById(R.id.description_id);
        }

    }

     class commentWithImageHolder extends RecyclerView.ViewHolder {

      TextView username , datetime , caption;
      ImageView image;

        public commentWithImageHolder(@NonNull View itemView) {
            super(itemView);
            username= itemView.findViewById(R.id.captionUsername);
            datetime= itemView.findViewById(R.id.imageTime);
            caption= itemView.findViewById(R.id.captionImage);
            image= itemView.findViewById(R.id.setImage_chat);

        }
    }

    private class pdfHolder extends RecyclerView.ViewHolder {


        ImageView pdf;
        TextView name , date_time , caption;
        public pdfHolder(@NonNull View itemView) {
            super(itemView);

            pdf = itemView.findViewById(R.id.pdfRetrive);
            date_time = itemView.findViewById(R.id.pdfTime);
            name = itemView.findViewById(R.id.pdfUsername);
            caption = itemView.findViewById(R.id.caption_Pdf);

        }
    }

}



