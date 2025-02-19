package com.arcralius.ff.lwjgl3;

import com.badlogic.gdx.Game;
import com.arcralius.ff.lwjgl3.scene.SceneController;
import com.arcralius.ff.lwjgl3.scene.MainMenuScreen;
import com.arcralius.ff.lwjgl3.input_output.AudioManager;


public class GameMaster extends Game {
    private SceneController sceneController;
    private AudioManager audioManager;

    @Override
    public void create() {
        // Pass (Game instance) to SceneController
        sceneController = new SceneController(this);
        audioManager = new AudioManager();
        audioManager.loadAllMusic();


        sceneController.changeScreen(new MainMenuScreen(sceneController, audioManager));



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
