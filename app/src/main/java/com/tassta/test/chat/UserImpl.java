package com.tassta.test.chat;

import javafx.scene.image.Image;

public class UserImpl implements User {

    private String name;
    private int id;
    private boolean isOnline;
    private Image icon;

    public UserImpl(String name, int id, boolean isOnline, Image icon) {
        this.name = name;
        this.id = id;
        this.isOnline = isOnline;
        this.icon = icon;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public boolean isOnline() {
        return isOnline;
    }

    @Override
    public Image getIcon() {
        return icon;
    }
}