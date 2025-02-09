package com.arcralius.ff.lwjgl3.input_output;

public class KeyboardManager extends InputManager{

    public KeyboardManager() {
        super();
    }

    @Override
    public void pollInput() {
        // Simulated keyboard polling (replace with actual implementation)
        System.out.println("Polling keyboard...");
    }

    public boolean isKeyPressed(String key) {
        return true;

    }

    public boolean isKeyReleased(String key) {
       return true;
    }
}
