package com.example.taskmangementmodule;

import android.view.View;

public class chat_model {

    String comment_id;
    String taskid;
    String date_time;
    String description;
    String username;
    String image;
    String pdf;



    public chat_model(String comment_id, String taskid, String date_time, String description, String username , String image, String pdf) {
        this.comment_id = comment_id;
        this.taskid = taskid;
        this.date_time = date_time;
        this.description = description;
        this.username = username;
        this.image = image;
        this.pdf = pdf;

    }

//getter setter for viewtype........................



    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }
}
