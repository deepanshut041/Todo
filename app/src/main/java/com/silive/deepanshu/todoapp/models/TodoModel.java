package com.silive.deepanshu.todoapp.models;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by deepanshu on 11/4/18.
 */

public class TodoModel {
    private int id;
    private String title;
    private String keyword;
    private Boolean notification;
    private String created_at;

    public TodoModel() {
    }

    public TodoModel(int id, String title, String keyword, Boolean notification, String created_at) {
        this.id = id;
        this.title = title;
        this.keyword = keyword;
        this.notification = notification;
        this.created_at = created_at;
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Boolean getNotification() {
        return notification;
    }

    public void setNotification(Boolean notification) {
        this.notification = notification;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
