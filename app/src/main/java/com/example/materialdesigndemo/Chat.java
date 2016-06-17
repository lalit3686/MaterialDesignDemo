package com.example.materialdesigndemo;

/**
 * Created by plalit on 6/17/2016.
 */
public class Chat {
    private long currentTime;
    private String sender;
    private String message;

    public Chat(long currentTime, String sender, String message){
        this.currentTime = currentTime;
        this.sender = sender;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }
}
