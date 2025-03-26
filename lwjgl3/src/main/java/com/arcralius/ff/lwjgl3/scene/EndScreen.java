package com.arcralius.ff.lwjgl3.scene;

import com.arcralius.ff.lwjgl3.specific_scene.Gameplay_Specific_scene;
import com.arcralius.ff.lwjgl3.input_output.IO_Controller;
import com.arcralius.ff.lwjgl3.movement.MovementController;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Gdx;

public class EndScreen extends BaseScreen {
    private final SceneController sceneController;
    private final Texture background;
    private final BitmapFont font;
    private Texture bottomTexture;

    public EndScreen(IO_Controller ioController, SceneController sceneController) {
        super(ioController);
        this.sceneController = sceneController;

        // Stop gameplay music and play game over music
        ioController.getAudioManager().stopMusic("gameplay_music");
        ioController.getAudioManager().playMusic("gameover_music", true);

        // Load assets
        this.background = new Texture("menuBackground.png");
        this.font = new BitmapFont(); // Customize font as needed
        this.bottomTexture = new Texture("tj/tj.png");
    }

    @Override
    public void render(float delta) {
        // Force clear color to black and clear buffer
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
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
        font.draw(batch, "Game Over", viewport.getWorldWidth() / 2 - 50, viewport.getWorldHeight() / 2 + 30);
        font.draw(batch,"You feel a heart attack coming! You lay down and fell asleep forever!",viewport.getWorldWidth() / 2 - 220,viewport.getWorldHeight() / 2);
        font.draw(batch, "Press ESC to Exit or R to Restart", viewport.getWorldWidth() / 2 - 100, viewport.getWorldHeight() / 2 - 30);
        float x = (viewport.getWorldWidth() - bottomTexture.getWidth()) / 2;
        batch.draw(bottomTexture, x, 0);
    }

    private void handleInput() {
        if (ioController.getInputManager().isKeyJustReleased(Input.Keys.R)) {
            sceneController.changeScreen(new Gameplay_Specific_scene(ioController, sceneController, new MovementController(ioController, camera)));
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
        bottomTexture.dispose();
    }
}
