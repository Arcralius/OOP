package com.arcralius.ff.lwjgl3.input_output;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import java.util.HashMap;
import java.util.Map;

public class AudioManager {
    private float volume;
    private Map<String, Music> musicTracks;
    private Music currentTrack;
    private boolean isMuted = false; // Track mute state

    public AudioManager() {
        this.volume = 1.0f; // Default volume
        this.musicTracks = new HashMap<>();
    }

    public void loadMusic(String trackName, String filePath) {
        //FileHandle musicFile = Gdx.files.internal("music/game_music.mp3");

        FileHandle musicFile = Gdx.files.internal(filePath);
        Music music = Gdx.audio.newMusic(musicFile);
        musicTracks.put(trackName, music); // Store music with a name

        if(!musicFile.exists()) {
            System.out.println("ERROR: Music file not found at " + musicFile.path());
            return;
        }
    }

    public void playMusic(String trackName, boolean looping) {
        if (!musicTracks.containsKey(trackName)) {
            System.out.println("Error: Music track '" + trackName + "' not found!");
            return;
        }

        stopCurrentMusic();
        currentTrack = musicTracks.get(trackName);
        currentTrack.setLooping(looping);
        currentTrack.setVolume(isMuted ? 0 : volume); // Apply mute state
        currentTrack.play();
        System.out.println("Playing music track: " + trackName);
    }

    private void stopCurrentMusic() {
        if (currentTrack != null) {
            currentTrack.stop();
            currentTrack = null;
        }
    }

    public void stopMusic(String trackName) {
        if (!musicTracks.containsKey(trackName)) {
            System.out.println("Error: Music track '" + trackName + "' not found!");
            return;
        }

        Music track = musicTracks.get(trackName);
        track.stop();
        if (currentTrack == track) {
            currentTrack = null;
        }
        System.out.println("Stopping music track: " + trackName);
    }

    public boolean isMuted() {
        return isMuted;
    }

    public void setVolume(float level) {
        if (!isMuted) { // Only update volume if NOT muted
            this.volume = level;
            for (Music track : musicTracks.values()) {
                if (track.isPlaying()) {
                    track.setVolume(volume);
                }
            }
            System.out.println("Volume set to: " + volume);
        } else {
            System.out.println("Cannot change volume while muted.");
        }
    }

    public void muteAll() {
        isMuted = true;
        for (Music track : musicTracks.values()) {
            track.setVolume(0);
        }
        System.out.println("Audio muted.");
    }

    public void unmuteAll() {
        isMuted = false;
        for (Music track : musicTracks.values()) {
            track.setVolume(volume);
        }
        System.out.println("Audio unmuted.");
    }

    public void toggleMute() {
        if (isMuted) {
            unmuteAll();
        } else {
            muteAll();
        }

        // If a track is playing, update its volume based on mute state
        if (currentTrack != null) {
            currentTrack.setVolume(isMuted ? 0 : volume);
        }
    }

    public void dispose() {
        stopCurrentMusic(); // Stop any currently playing track
        for (Music track : musicTracks.values()) {
            track.dispose();
        }
        musicTracks.clear();
        System.out.println("Audio resources disposed.");
    }
}
