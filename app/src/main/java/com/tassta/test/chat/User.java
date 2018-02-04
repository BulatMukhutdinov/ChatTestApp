package com.tassta.test.chat;

import javafx.scene.image.Image;

/**
 * Represents a user
 */
public interface User
{
    /**
     * @return User's name
     */
    String getName();

    /**
     * @return User's unique ID
     */
    int getId();

    /**
     * @return True if user is online
     */
    boolean isOnline();

    /**
     * @return User's avatar
     */
    Image getIcon();
}
