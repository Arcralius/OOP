package engine.input_output;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import java.util.HashMap;
import java.util.Map;

public class AudioManager {
    private float volume;
    private Map<String, Music> musicTracks;
    private Map<String, Sound> soundEffects; // New map for sound effects
    private Music currentTrack;
    private String currentTrackName;
    private boolean isMuted = false;

    public AudioManager() {
        this.volume = 1.0f;
        this.musicTracks = new HashMap<>();
        this.soundEffects = new HashMap<>(); // Initialize sound effects map
    }

    public String getCurrentTrack(){
        return currentTrackName;
    }

    public void loadAllMusic(){
        loadMusic("gameplay_music","music/game_music.mp3");
        loadMusic("main_menu_music","music/main_menu.mp3");
        loadMusic("gameover_music","music/game_over.mp3");
        loadMusic("victory_music","music/victory.mp3");

        // Load the collection sound as a sound effect, not music
        loadSoundEffect("item_collected", "music/item_collected.mp3");
        loadSoundEffect("hit_sound", "music/hit_sound.mp3");;
    }

    // New method to load sound effects
    public void loadSoundEffect(String effectName, String filePath) {
        FileHandle soundFile = Gdx.files.internal(filePath);
        if(!soundFile.exists()) {
            System.out.println("ERROR: Sound effect file not found at " + soundFile.path());
            return;
        }
        Sound sound = Gdx.audio.newSound(soundFile);
        soundEffects.put(effectName, sound);
        System.out.println("Loaded sound effect: " + effectName);
    }

    // New method to play sound effects
    public void playSoundEffect(String effectName) {
        if (isMuted) return;

        Sound sound = soundEffects.get(effectName);
        if (sound != null) {
            sound.play(volume);
            System.out.println("Playing sound effect: " + effectName);
        } else {
            System.out.println("Sound effect not found: " + effectName);
        }
    }

    public void loadMusic(String trackName, String filePath) {
        FileHandle musicFile = Gdx.files.internal(filePath);
        if(!musicFile.exists()) {
            System.out.println("ERROR: Music file not found at " + musicFile.path());
            return;
        }
        Music music = Gdx.audio.newMusic(musicFile);
        musicTracks.put(trackName, music);
    }

    public void playMusic(String trackName, boolean looping) {
        if (!musicTracks.containsKey(trackName)) {
            System.out.println("Error: Music track '" + trackName + "' not found!");
            return;
        }

        stopCurrentMusic();
        currentTrack = musicTracks.get(trackName);
        currentTrackName = trackName;
        currentTrack.setLooping(looping);
        currentTrack.setVolume(isMuted ? 0 : volume);
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

    public float getVolume(){
        return volume;
    }

    public void setVolume(float level) {
        if (!isMuted) {
            this.volume = level;
            // Update volume for music
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

        if (currentTrack != null) {
            currentTrack.setVolume(isMuted ? 0 : volume);
        }
    }

    public void dispose() {
        stopCurrentMusic();

        // Dispose music
        for (Music track : musicTracks.values()) {
            track.dispose();
        }
        musicTracks.clear();

        // Dispose sound effects
        for (Sound effect : soundEffects.values()) {
            effect.dispose();
        }
        soundEffects.clear();

        System.out.println("Audio resources disposed.");
    }
}
