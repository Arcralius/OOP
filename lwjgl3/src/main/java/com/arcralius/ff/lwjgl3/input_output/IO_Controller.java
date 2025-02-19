package com.arcralius.ff.lwjgl3.input_output;

public class IO_Controller {
    private final InputManager inputManager;
    private final DisplayManager displayManager;
    private final AudioManager audioManager;

    public IO_Controller() {
        this.inputManager = new InputManager();
        this.displayManager = new DisplayManager();
        this.audioManager = new AudioManager();

        displayManager.initializeDisplay();
    }

    public void update() {
        inputManager.update();
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public AudioManager getAudioManager() {
        return audioManager;
    }

    public DisplayManager getDisplayManager() {
        return displayManager;
    }
}
