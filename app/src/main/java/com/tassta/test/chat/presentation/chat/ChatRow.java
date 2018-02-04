package com.tassta.test.chat.presentation.chat;

import com.tassta.test.chat.Message;
import com.tassta.test.chat.User;

public class ChatRow {
    private User user;
    private Message lastMessage;
    private boolean hasNewMessage;

    ChatRow(User user, Message lastMessage) {
        this.user = user;
        this.lastMessage = lastMessage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    public boolean hasNewMessage() {
        return hasNewMessage;
    }

    public void setHasNewMessage(boolean hasNewMessage) {
        this.hasNewMessage = hasNewMessage;
    }
}
