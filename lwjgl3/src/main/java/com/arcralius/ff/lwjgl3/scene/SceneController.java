package com.arcralius.ff.lwjgl3.scene;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class SceneController {
    private BaseScreen baseScreen;

    public SceneController() {
        this.baseScreen = null;
    }


    public void changeScreen(BaseScreen newScreen) {
        this.baseScreen = newScreen;
        System.out.println("Switched to: " + newScreen.getClass().getSimpleName());
    }

    public void create() {
        if (baseScreen != null) {
            baseScreen.changeScreen(baseScreen);
        }
    }

    public void render() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        if (baseScreen != null) {
            System.out.println("Rendering: " + baseScreen.getClass().getSimpleName());


            baseScreen.render(); // Call render on the active screen
        }
    }

    public void dispose() {
        System.out.println("Disposing current screen...");

    }

    // Example of SceneController interacting with BaseScreen
    public void updateBackground(Texture newTexture) {

        }

}

