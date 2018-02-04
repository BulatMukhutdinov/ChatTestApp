package com.tassta.test.chat;

import android.graphics.Bitmap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javafx.scene.image.Image;

public class Utils {

    // TODO: 04.02.2018 add implementation
    public static Bitmap imageToBitmap(Image image) {
        return null;
    }

    public static String formatDate(Date date) {
        DateFormat df = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return df.format(date);
    }

}