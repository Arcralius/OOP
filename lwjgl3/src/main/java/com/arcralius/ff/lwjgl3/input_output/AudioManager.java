package com.arcralius.ff.lwjgl3.input_output;
import java.util.ArrayList;
import java.util.List;

public class AudioManager {
    private float volume;
    private List<String> musicTracks;

    public AudioManager() {
        this.volume = 1.0f; // Default volume
        this.musicTracks = new ArrayList<>();
    }

    public void stopMusic() {
        System.out.println("Stopping music...");
    }

    public void pauseAllAudio() {
        System.out.println("Pausing all audio...");
    }

    public void playMusic(String track) {
        System.out.println("Playing music track: " + track);
    }

    public void setVolume(float level) {
        this.volume = level;
        System.out.println("Volume set to: " + volume);
    }
}
