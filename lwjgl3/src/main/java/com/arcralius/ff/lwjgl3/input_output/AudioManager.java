package com.arcralius.ff.lwjgl3.input_output;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AudioManager {
    private float volume;
    private Map<String, Music> musicTracks;
    private Music currentTrack;

    public AudioManager() {
        this.volume = 1.0f; // Default volume
        this.musicTracks = new HashMap<>();
    }
    // public void loadMusic(String trackName, String filePath)
    public void loadMusic() {
        FileHandle musicFile = Gdx.files.internal("music/test.mp3");
        Music music = Gdx.audio.newMusic(musicFile);
        musicTracks.put("test", music); // Store music with a name
    }
    public void playMusic(String trackName, boolean looping) {
        Music track = musicTracks.get(trackName);
        if (track != null) {
            stopCurrentMusic();
            currentTrack = track;
            currentTrack.setLooping(looping);
            currentTrack.setVolume(volume);
            currentTrack.play();
            System.out.println("Playing music track: " + trackName);
        }
    }
    private void stopCurrentMusic() {
        if (currentTrack != null) {
            currentTrack.stop();
            currentTrack = null;
        }
    }
    public void stopMusic(String trackName) {
        Music track = musicTracks.get(trackName);
        if (track != null) {
            track.stop();
            if (currentTrack == track) {
                currentTrack = null;
            }
            System.out.println("Stopping music track: " + trackName);
        } else {
            Gdx.app.log("AudioManager", "Track not found: " + trackName);
        }
    }

    public void pauseAllAudio() {

        for (Music track : musicTracks.values()) {
            if (track.isPlaying()) {
                track.pause();
            }
        }
        System.out.println("Pausing all audio...");
    }
    public void setVolume(float level) {
        this.volume = level;
        for (Music track : musicTracks.values()) {
            if (track.isPlaying()) {
                track.setVolume(volume); // Update volume of playing tracks
            }
        }
        System.out.println("Volume set to: " + volume);
    }
    public void dispose() {
        for (Music track : musicTracks.values()) {
            track.dispose();
        }
        musicTracks.clear();
        System.out.println("Audio resources disposed.");
    }

}
