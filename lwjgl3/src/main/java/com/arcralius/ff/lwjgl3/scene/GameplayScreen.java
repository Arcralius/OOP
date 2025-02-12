package com.arcralius.ff.lwjgl3.scene;
import com.badlogic.gdx.graphics.Texture;
import java.util.Arrays;

public class GameplayScreen extends BaseScreen {

    public GameplayScreen() {
        super();
        this.background = new Texture("gameplay_background.png"); // Load background texture
        this.menuOptions = Arrays.asList("Resume", "Settings", "Exit");
    }

    @Override
    public void render() {

        super.render(); // Render background using SpriteBatch
        System.out.println("Rendering Gameplay Screen");
        System.out.println("Menu Options: " + menuOptions);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}

