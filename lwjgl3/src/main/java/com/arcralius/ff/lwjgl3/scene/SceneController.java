package com.arcralius.ff.lwjgl3.scene;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.arcralius.ff.lwjgl3.input_output.IO_Controller;
import com.badlogic.gdx.Gdx;

public class SceneController {
    private final Game game;
    private final IO_Controller ioController;

    public SceneController(Game game, IO_Controller ioController) {
        this.game = game;
        this.ioController = ioController;
    }

    public void changeScreen(Screen screen) {
        if (game.getScreen() != null) {
            System.out.println("Disposing current screen: " + game.getScreen().getClass().getSimpleName());
        }
        // Clear the screen before switching to the new screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        // Switch to new screen
        String screenName = screen.getClass().getSimpleName();
        System.out.println("Switching to new screen: " + screenName);

        // Update DisplayManager with the new active screen
        ioController.getDisplayManager().setCurrentScreen(screenName);

        game.setScreen(screen);
    }

    public void render() {
    }

    public void dispose() {
        if (game.getScreen() != null) {
            game.getScreen().dispose();
        }
    }

    public void resize(int width, int height) {
        System.out.println("Resizing screen to: " + width + "x" + height);
        ioController.getDisplayManager().setResolution(width + "x" + height);
    }
}
