package com.arcralius.ff.lwjgl3.input_output;

public class IO_Controller {
    private KeyboardManager keyboardManager;
    private DisplayManager displayManager;
    private AudioManager audioManager;

    public IO_Controller() {
        this.keyboardManager = new KeyboardManager();
        this.displayManager = new DisplayManager();
        this.audioManager = new AudioManager();
    }

    public void update() {
        keyboardManager.pollInput();
        displayManager.renderFrame();
    }

    public KeyboardManager getKeyboardManager() {
        return keyboardManager;
    }

    public DisplayManager getDisplayManager() {
        return displayManager;
    }

    public AudioManager getAudioManager() {
        return audioManager;
    }
}
