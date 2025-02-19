package com.arcralius.ff.lwjgl3.input_output;

public class InputManager {
    private KeyboardManager keyboardManager;
    private MouseManager mouseManager;

    public InputManager() {
        this.keyboardManager = new KeyboardManager();
        this.mouseManager = new MouseManager();
    }

    public void pollInput() {
        keyboardManager.pollInput();
        mouseManager.pollInput();
    }

    public KeyboardManager getKeyboardManager() {
        return keyboardManager;
    }

    public MouseManager getMouseManager() {
        return mouseManager;
    }
}
