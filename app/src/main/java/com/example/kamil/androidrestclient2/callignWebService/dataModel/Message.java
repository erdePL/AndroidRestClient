package com.example.kamil.androidrestclient2.callignWebService.dataModel;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class Message implements Parcelable {
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("messageContent")
    @Expose
    private String messageContent;
    @SerializedName("creationDate")
    @Expose
    private Date creationDate;
    @SerializedName("author")
    @Expose
    private String author;
    public Message() {
    }
    public Message(long id, String messageContent, String author) {
        this.id = id;
        this.messageContent = messageContent;
        this.creationDate = new Date();
        this.author = author;
    }
    protected Message(Parcel in) {
        id = in.readLong();
        messageContent = in.readString();
        author = in.readString();
    }
    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }
        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(messageContent);
        dest.writeString(author);
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

