package com.example.taskmangementmodule;

public class viewbranchmodel {
    String id, branchName, branchCode, description;

    public viewbranchmodel(String id, String branchName, String branchCode, String description) {
        this.id = id;
        this.branchName = branchName;
        this.branchCode = branchCode;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
