package com.arcralius.ff.lwjgl3;

import com.badlogic.gdx.Game;
import com.arcralius.ff.lwjgl3.scene.SceneController;
import com.arcralius.ff.lwjgl3.scene.MainMenuScreen;
import com.arcralius.ff.lwjgl3.input_output.AudioManager;
import com.arcralius.ff.lwjgl3.input_output.IO_Controller;


public class GameMaster extends Game {
    private SceneController sceneController;
    private AudioManager audioManager;
    private IO_Controller ioController;

    @Override
    public void create() {
        // Pass (Game instance) to SceneController
        sceneController = new SceneController(this);
        audioManager = new AudioManager();
        ioController = new IO_Controller();
        audioManager.loadMusic("gameplay_music","music/game_music.mp3");
        audioManager.loadMusic("main_menu_music","music/main_menu.mp3");
        audioManager.loadMusic("gameover_music","music/game_over.mp3");


        sceneController.changeScreen(new MainMenuScreen(ioController, sceneController, audioManager));



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
