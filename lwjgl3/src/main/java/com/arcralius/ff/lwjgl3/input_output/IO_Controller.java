package com.arcralius.ff.lwjgl3.input_output;

public class IO_Controller {
    private InputManager inputManager;
    private DisplayManager displayManager;
    private AudioManager audioManager;

    public IO_Controller() {
        this.inputManager = new InputManager();
        this.displayManager = new DisplayManager();
        this.audioManager = new AudioManager();
    }

    public void update() {

    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public DisplayManager getDisplayManager() {
        return displayManager;
    }

    public AudioManager getAudioManager() {
        return audioManager;
    }
}
