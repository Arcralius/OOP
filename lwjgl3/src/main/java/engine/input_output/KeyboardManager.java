package engine.input_output;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import java.util.HashMap;
import java.util.Map;

public class KeyboardManager {
    private Map<Integer, Boolean> keyStates;
    private Map<Integer, Boolean> keyReleasedStates;

    public KeyboardManager() {
        keyStates = new HashMap<>();
        keyReleasedStates = new HashMap<>();
    }

    public void update() {
        for (int key = 0; key < Input.Keys.MAX_KEYCODE; key++) { // Corrected key range
            boolean isPressed = Gdx.input.isKeyPressed(key);
            boolean wasPressed = keyStates.getOrDefault(key, false);

            keyStates.put(key, isPressed);
            keyReleasedStates.put(key, wasPressed && !isPressed);

            // Debugging: Print key when it is first pressed
            if (isPressed && !wasPressed) {
                System.out.println("Key Pressed: " + Input.Keys.toString(key));
            }
        }
    }

    public boolean isKeyPressed(int key) {
        return keyStates.getOrDefault(key, false);
    }

    public boolean isKeyJustReleased(int key) {
        return keyReleasedStates.getOrDefault(key, false);
    }
}
