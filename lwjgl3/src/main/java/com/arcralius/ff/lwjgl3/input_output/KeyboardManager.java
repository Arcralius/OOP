package com.arcralius.ff.lwjgl3.input_output;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import java.util.HashMap;
import java.util.Map;

public class KeyboardManager extends InputManager {
    private Map<Integer, Boolean> keyStates;

    public KeyboardManager() {
        super();
        keyStates = new HashMap<>();
    }

    @Override
    public void pollInput() {
        // Update key states for movement keys
        keyStates.put(Input.Keys.W, Gdx.input.isKeyPressed(Input.Keys.W));
        keyStates.put(Input.Keys.A, Gdx.input.isKeyPressed(Input.Keys.A));
        keyStates.put(Input.Keys.S, Gdx.input.isKeyPressed(Input.Keys.S));
        keyStates.put(Input.Keys.D, Gdx.input.isKeyPressed(Input.Keys.D));

        keyStates.put(Input.Keys.SPACE, Gdx.input.isKeyPressed(Input.Keys.SPACE));
        keyStates.put(Input.Keys.ESCAPE, Gdx.input.isKeyPressed(Input.Keys.ESCAPE));
    }

    public boolean isKeyPressed(int key) {
        return keyStates.getOrDefault(key, false);
    }

    public boolean isKeyReleased(int key) {
        return !isKeyPressed(key);
    }
}
