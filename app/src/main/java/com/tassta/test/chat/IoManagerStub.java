package com.tassta.test.chat;

import com.tassta.test.chat.noimpl.IoManger;

import java.util.Date;

import javax.inject.Inject;

public class IoManagerStub implements IoManger {

    static boolean sent;

    @Inject
    public IoManagerStub() {
    }

    @Override
    public void sendMessage(User receiver, String text) throws Exception {

    }

    @Override
    public void setReceiveMessageHandler(Consumer<Message> handler) {
        User sender = new UserImpl("Timur", 2, true, null);
        if (!sent) {
            sent = true;
            handler.accept(new MessageImpl(new Date(), "Hi there!", sender, MainActivity.me));
        }
    }

    @Override
    public void setUserStateChangeHandler(UserStateChangeHandler handler) {

    }

    @Override
    public void setUserAddedHandler(Consumer<User> handler) {
        User user = new UserImpl("Timur", 2, true, null);
        if (!sent) {
            sent = true;
            handler.accept(user);
        }
    }

    @Override
    public void setUserRemovedHandler(int userId) {

    }
}