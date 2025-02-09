package com.arcralius.ff.lwjgl3.input_output;

public class MouseManager extends InputManager{
    private float mouseX, mouseY;

    public MouseManager() {
        super();
        mouseX = 0;
        mouseY = 0;
    }

    @Override
    public void pollInput() {
        // Simulated mouse polling (replace with actual implementation)
        System.out.println("Polling mouse...");
    }

    public boolean isMouseButtonPressed(String button) {
        return true;
    }

    public boolean isMouseButtonReleased(String button) {
        return true;
    }

    public float getMouseX() {
        return mouseX;
    }

    public float getMouseY() {
        return mouseY;
    }

    public void updateMousePosition(float x, float y) {
        this.mouseX = x;
        this.mouseY = y;
    }
}
