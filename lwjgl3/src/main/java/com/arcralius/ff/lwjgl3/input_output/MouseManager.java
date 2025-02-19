package com.arcralius.ff.lwjgl3.input_output;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import java.util.HashMap;
import java.util.Map;

public class MouseManager {
    private Map<Integer, Boolean> mouseButtonStates;

    public MouseManager() {
        mouseButtonStates = new HashMap<>();
    }

    public void update() {
        for (int button = 0; button < 5; button++) {
            mouseButtonStates.put(button, Gdx.input.isButtonPressed(button));
        }
    }

    public boolean isMouseButtonPressed(int button) {
        return mouseButtonStates.getOrDefault(button, false);
    }

    public float getMouseX() {
        return Gdx.input.getX();
    }

    public float getMouseY() {
        return Gdx.input.getY();
    }
}
