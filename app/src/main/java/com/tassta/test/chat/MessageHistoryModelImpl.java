package com.tassta.test.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageHistoryModelImpl implements MessageHistoryModel {
    private Map<User, MessageHistory> userMessageHistoryMap = new HashMap<>();

    @Override
    public MessageHistory getMessageHistory(User user) {
        return userMessageHistoryMap.get(user);
    }

    public void saveMessage(User user, Message message) {
        List<Message> messages;
        if (userMessageHistoryMap.get(user) == null) {
            messages = new ArrayList<>();
        } else {
            messages = userMessageHistoryMap.get(user).getMessages();
        }
        messages.add(message);
        MessageHistory messageHistory = new MessageHistory() {
            @Override
            public List<Message> getMessages() {
                return messages;
            }
        };
        userMessageHistoryMap.put(user, messageHistory);
    }
}