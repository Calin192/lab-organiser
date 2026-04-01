package com.example.laborganiser.app;

import javafx.scene.media.AudioClip;

public class SoundUtil {

    private static final AudioClip clickSound =
            new AudioClip(SoundUtil.class.getResource("/Sounds/fahh!.mp3").toExternalForm());

    public static void playClick() {
        clickSound.play();
    }
}
