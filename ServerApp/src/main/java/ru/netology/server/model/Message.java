package ru.netology.server.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Message {

    @SerializedName("username")
    private String username;
    @SerializedName("message")
    private String message;
    @SerializedName("time")
    private Date time;

    public Message(String username, String message) {
        this.username = username;
        this.message = message;
        this.time = new Date();
    }

    public static Message fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Message.class);
    }

    public String getUsername() {
        return this.username;
    }

    public String getMessage() {
        return this.message;
    }

    public String getDate() {
        return time.toString();
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}

