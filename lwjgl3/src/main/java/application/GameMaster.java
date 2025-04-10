package application;

import com.badlogic.gdx.Game;
import engine.scene.SceneController;
import application.scene.MainMenuScreen;
import engine.input_output.IO_Controller;

public class GameMaster extends Game {
    private SceneController sceneController;
    private IO_Controller ioController;

    @Override
    public void create() {
        // Initialize IO_Controller and SceneController
        ioController = new IO_Controller();
        sceneController = new SceneController(this, ioController);

        // Initialize DisplayManager and set resolution
        ioController.getDisplayManager().initializeDisplay();
        ioController.getDisplayManager().setResolution("800x550");

        // Load all audio files via IO_Controller
        ioController.getAudioManager().loadAllMusic();

        // Start with the main menu screen
        sceneController.changeScreen(new MainMenuScreen(ioController, sceneController));
    }

    @Override
    public void render() {
        super.render(); // Use Game's render method
        ioController.getDisplayManager().renderFrame(); // Debugging display updates
    }

    @Override
    public void dispose() {
        if (sceneController != null) {
            sceneController.dispose();
        }
    }
}
