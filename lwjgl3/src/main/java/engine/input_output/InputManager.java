package engine.input_output;

public class InputManager {
    private final KeyboardManager keyboardManager;
    private final MouseManager mouseManager;

    public InputManager() {
        this.keyboardManager = new KeyboardManager();
        this.mouseManager = new MouseManager();
    }

    // Update method to refresh input states
    public void update() {
        keyboardManager.update();
        mouseManager.update();
    }

    // Check if a specific key is currently pressed
    public boolean isKeyPressed(int key) {
        return keyboardManager.isKeyPressed(key);
    }

    // Check if a specific key was just released
    public boolean isKeyJustReleased(int key) {
        return keyboardManager.isKeyJustReleased(key);
    }

    // Check if a mouse button is pressed
    public boolean isMouseButtonPressed(int button) {
        return mouseManager.isMouseButtonPressed(button);
    }

    // Get mouse position
    public float getMouseX() {
        return mouseManager.getMouseX();
    }

    public float getMouseY() {
        return mouseManager.getMouseY();
    }
}
