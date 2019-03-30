package com.example.kamil.androidrestclient2.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Kamil on 29/03/2019.
 */

public class Message {
    @SerializedName("id")
    private long id;
    @SerializedName("messageContent")
    private String messageContent;
    @SerializedName("creationDate")
    private Date creationDate;
    @SerializedName("author")
    private String author;
    public Message() {
    }
    public Message(long id, String messageContent, String author) {
        this.id = id;
        this.messageContent = messageContent;
        this.creationDate = new Date();
        this.author = author;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getMessageContent() {
        return messageContent;
    }
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
    public Date getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
}

