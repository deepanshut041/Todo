package com.silive.deepanshu.todoapp.models;

import java.util.Date;

/**
 * Created by deepanshu on 11/4/18.
 */

public class TodoModel {
    private int id;
    private String title;
    private String description;
    private Boolean notification;
    private Date created_at;
    private Date updated_at;

    public TodoModel() {
    }

    public TodoModel(int id, String title, String description, Boolean notification, Date created_at, Date updated_at) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.notification = notification;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getNotification() {
        return notification;
    }

    public void setNotification(Boolean notification) {
        this.notification = notification;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
