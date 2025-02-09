package com.arcralius.ff.lwjgl3.input_output;
import java.util.HashMap;
import java.util.Map;

public class InputManager {
    private Map<String, Boolean> keyStates;
    private Map<String, Boolean> mouseButtonStates;
    private float mouseX, mouseY;

    public InputManager() {
        keyStates = new HashMap<>();
        mouseButtonStates = new HashMap<>();
        mouseX = 0;
        mouseY = 0;
    }

    public void pollInput() {
        System.out.println("Polling input...");
    }

    public boolean isKeyPressed(String key) {
        return keyStates.getOrDefault(key, false);
    }

    public boolean isKeyReleased(String key) {
        return !isKeyPressed(key);
    }

    public boolean isMouseButtonPressed(String button) {
        return mouseButtonStates.getOrDefault(button, false);
    }

    public boolean isMouseButtonReleased(String button) {
        return !isMouseButtonPressed(button);
    }

    public float getMouseX() {
        return mouseX;
    }

    public float getMouseY() {
        return mouseY;
    }

    public void resetInputs() {
        keyStates.clear();
        mouseButtonStates.clear();
    }
}
