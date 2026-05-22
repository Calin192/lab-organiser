package com.example.laborganiser.app;

import javafx.scene.image.Image;

public class ImageCache {

    public static Image BOTTLE_IMAGE;
    public static Image CAP_IMAGE;

    public static void loadImages() {

        BOTTLE_IMAGE = new Image(
                ImageCache.class.getResourceAsStream("/Images/Bottle.png"),
                120,
                120,
                true,
                true
        );

        CAP_IMAGE = new Image(
                ImageCache.class.getResourceAsStream("/Images/Cap.png"),
                160,
                160,
                true,
                true
        );
    }
}