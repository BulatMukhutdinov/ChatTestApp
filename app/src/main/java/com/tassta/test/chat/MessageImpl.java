package com.tassta.test.chat;

import java.util.Date;

public class MessageImpl implements Message {
    private Date date;
    private String text;
    private User sender;
    private User receiver;

    public MessageImpl(Date date, String text, User sender, User receiver) {
        this.date = date;
        this.text = text;
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public User getSender() {
        return sender;
    }

    @Override
    public User getReceiver() {
        return receiver;
    }
}
