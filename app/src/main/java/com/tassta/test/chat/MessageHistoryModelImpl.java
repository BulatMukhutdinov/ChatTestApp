package com.tassta.test.chat;

import java.util.ArrayList;
import java.util.List;

public class MessageHistoryModelImpl extends MessageHistory implements MessageHistoryModel {
    private List<Message> messages = new ArrayList<>();

    @Override
    public MessageHistory getMessageHistory(User user) {
        return this;
    }

    public void saveMessage(Message message) {
        messages.add(message);
    }

    public List<Message> getMessages() {
        return messages;
    }
}
