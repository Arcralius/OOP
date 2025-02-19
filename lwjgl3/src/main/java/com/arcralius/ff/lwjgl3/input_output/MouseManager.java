package com.arcralius.ff.lwjgl3.input_output;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import java.util.HashMap;
import java.util.Map;

public class MouseManager extends InputManager {
    private float mouseX, mouseY;
    private Map<Integer, Boolean> mouseButtonStates;

    public MouseManager() {
        super();
        mouseX = 0;
        mouseY = 0;
        mouseButtonStates = new HashMap<>();
    }

    @Override
    public void pollInput() {
        // Update mouse position
        mouseX = Gdx.input.getX();
        mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Convert to game coordinates

        // Update mouse button states
        mouseButtonStates.put(Input.Buttons.LEFT, Gdx.input.isButtonPressed(Input.Buttons.LEFT));
        mouseButtonStates.put(Input.Buttons.RIGHT, Gdx.input.isButtonPressed(Input.Buttons.RIGHT));
        mouseButtonStates.put(Input.Buttons.MIDDLE, Gdx.input.isButtonPressed(Input.Buttons.MIDDLE));
    }

    public boolean isMouseButtonPressed(int button) {
        return mouseButtonStates.getOrDefault(button, false);
    }

    public boolean isMouseButtonReleased(int button) {
        return !isMouseButtonPressed(button);
    }

    public float getMouseX() {
        return mouseX;
    }

    public float getMouseY() {
        return mouseY;
    }
}
