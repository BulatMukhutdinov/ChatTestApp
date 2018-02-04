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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserImpl user = (UserImpl) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + id;
        result = 31 * result + (isOnline ? 1 : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        return result;
    }
}