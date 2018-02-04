package com.tassta.test.chat.noimpl;

import com.tassta.test.chat.Consumer;
import com.tassta.test.chat.Message;
import com.tassta.test.chat.User;
import com.tassta.test.chat.UserStateChangeHandler;

/**
 * It's the interface you supposed to use with your virtual fellow programmer who implements network module.
 * You don't need to implement it but your application must to work with any implementation of this interface.
 */
public interface IoManger
{
    void sendMessage(User receiver, String text) throws Exception;

    void setReceiveMessageHandler(Consumer<Message> handler);

    void setUserStateChangeHandler(UserStateChangeHandler handler);

    void setUserAddedHandler(Consumer<User> handler);

    void setUserRemovedHandler(int userId);
}
