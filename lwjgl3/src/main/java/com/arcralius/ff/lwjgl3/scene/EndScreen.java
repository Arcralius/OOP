package com.arcralius.ff.lwjgl3.scene;

import com.arcralius.ff.lwjgl3.examples.Gameplay_example;
import com.arcralius.ff.lwjgl3.input_output.IO_Controller;
import com.arcralius.ff.lwjgl3.movement.MovementController;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Gdx;

public class EndScreen extends BaseScreen {
    private final IO_Controller ioController;
    private final SceneController sceneController;
    private final Texture background;
    private final BitmapFont font;

    public EndScreen(IO_Controller ioController, SceneController sceneController) {
        this.ioController = ioController;
        this.sceneController = sceneController;

        // Stop gameplay music and play game over music
        this.ioController.getAudioManager().stopMusic("gameplay_music");
        this.ioController.getAudioManager().playMusic("gameover_music", true);

        // Load assets
        this.background = new Texture("menuBackground.png");
        this.font = new BitmapFont(); // Customize font as needed
    }

    @Override
    public void render(float delta) {
        update(delta); // Call update for game logic
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        draw(); // Call draw for rendering
        batch.end();
    }

    @Override
    protected void update(float delta) {
        handleInput(); // Handle user input during the game loop
        ioController.update();
    }

    @Override
    protected void draw() {
        batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        font.draw(batch, "Game Over", viewport.getWorldWidth() / 2 - 50, viewport.getWorldHeight() / 2);
        font.draw(batch, "Press ESC to Exit or R to Restart", viewport.getWorldWidth() / 2 - 100, viewport.getWorldHeight() / 2 - 30);
    }

    private void handleInput() {
        if (ioController.getInputManager().isKeyJustReleased(Input.Keys.R)) {
            sceneController.changeScreen(new Gameplay_example(ioController, sceneController, new MovementController(ioController, camera)));
        }
        if (ioController.getInputManager().isKeyJustReleased(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        background.dispose();
        font.dispose();
    }
}
