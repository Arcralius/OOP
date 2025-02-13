package com.arcralius.ff.lwjgl3;

import com.badlogic.gdx.Game;
import com.arcralius.ff.lwjgl3.scene.SceneController;
import com.arcralius.ff.lwjgl3.scene.MainMenuScreen;

public class GameMaster extends Game {
    private SceneController sceneController;

    @Override
    public void create() {
        // Pass (Game instance) to SceneController
        sceneController = new SceneController(this);
        sceneController.changeScreen(new MainMenuScreen(sceneController));
    }

    @Override
    public void render() {
        super.render(); //Use Game's render method
    }

    @Override
    public void dispose() {
        if (sceneController != null) {
            sceneController.dispose();
        }
    }
}
