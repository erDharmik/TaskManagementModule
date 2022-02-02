package com.example.taskmangementmodule;

public class achievedmodel {

    String id, title, leader, member, startDate, endDate, description;

    public achievedmodel(String id, String title, String leader, String member, String startDate, String endDate, String description, String progress) {
        this.id = id;
        this.title = title;
        this.leader = leader;
        this.member = member;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}


