package com.arcralius.ff.lwjgl3;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class SceneController {
    private final Game game;

    public SceneController(Game game) {
        this.game = game; //assign to game field to control screen changes in the game
    }

    //define method to change the current screen in the game
    public void changeScreen(Screen screen) {
        if (game.getScreen() != null) {
            System.out.println("Disposing current screen: " + game.getScreen().getClass().getSimpleName()); //retrieve class name of current screen
            game.getScreen().dispose();
        }

    //switch to new screen
        System.out.println("Switching to new screen: " + screen.getClass().getSimpleName()); //logs the new screen name
        game.setScreen(screen);
    }
}
