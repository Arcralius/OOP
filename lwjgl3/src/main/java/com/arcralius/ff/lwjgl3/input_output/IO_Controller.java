package com.arcralius.ff.lwjgl3.input_output;

public class IO_Controller {
    private KeyboardManager keyboardManager;
    private MouseManager mouseManager;
    private DisplayManager displayManager;
    private AudioManager audioManager;

    public IO_Controller() {
        this.keyboardManager = new KeyboardManager();
        this.mouseManager = new MouseManager();
        this.displayManager = new DisplayManager();
        this.audioManager = new AudioManager();
    }

    public void update() {
        keyboardManager.pollInput();
        mouseManager.pollInput();
        displayManager.renderFrame();
    }

    public KeyboardManager getKeyboardManager() {
        return keyboardManager;
    }

    public MouseManager getMouseManager() {  // Added getter for MouseManager
        return mouseManager;
    }

    public DisplayManager getDisplayManager() {
        return displayManager;
    }

    public AudioManager getAudioManager() {
        return audioManager;
    }
}
