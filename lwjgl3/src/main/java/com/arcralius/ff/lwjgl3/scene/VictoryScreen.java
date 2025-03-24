package com.arcralius.ff.lwjgl3.scene;

import com.arcralius.ff.lwjgl3.specific_scene.Gameplay_Specific_scene;
import com.arcralius.ff.lwjgl3.input_output.IO_Controller;
import com.arcralius.ff.lwjgl3.movement.MovementController;
import com.arcralius.ff.lwjgl3.entity.FoodEntity;
import com.arcralius.ff.lwjgl3.entity.abstractFactory.FoodFactory;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class VictoryScreen extends BaseScreen {
    private final SceneController sceneController;
    private final Texture background;
    private final BitmapFont font;

    // Added for falling fruits
    private final List<FoodEntity> fallingFruits = new ArrayList<>();
    private final String[] foodTextures = {
        "apple.png", "Banana.png", "Blueberry.png", "Cabbage.png", "Carrot.png",
        "Cauliflower.png", "Cherry.png", "Corn.png", "Cucumber.png", "Eggplant.png",
        "grapes.png", "Leek.png", "Lemon.png", "Onion.png", "Orange.png", "Paprika.png",
        "Pear.png", "Pineapple.png", "Plum.png", "Potato.png", "Pumpkin.png",
        "Raspberry.png", "Strawberry.png", "Tomato.png", "Watermelon.png"
    };
    private final Random random = new Random();
    private long lastSpawnTime = TimeUtils.nanoTime();

    public VictoryScreen(IO_Controller ioController, SceneController sceneController) {
        super(ioController);
        this.sceneController = sceneController;

        // Stop gameplay music and play game over music
        ioController.getAudioManager().stopMusic("victory_music");
        ioController.getAudioManager().playMusic("victory_music", true);

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

        // Update falling fruits
        spawnFruitIfNeeded();
        for (Iterator<FoodEntity> it = fallingFruits.iterator(); it.hasNext(); ) {
            FoodEntity fruit = it.next();
            fruit.setY(fruit.getY() - 200 * delta);
            if (fruit.getY() + fruit.getHeight() < 0) {
                it.remove();
            }
        }
    }

    @Override
    protected void draw() {
        batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        font.draw(batch, "Victory!", viewport.getWorldWidth() / 2 - 50, viewport.getWorldHeight() / 2);
        font.draw(batch, "Press ESC to Exit or R to Restart", viewport.getWorldWidth() / 2 - 100, viewport.getWorldHeight() / 2 - 30);

        // Draw falling fruits
        for (FoodEntity fruit : fallingFruits) {
            batch.draw(fruit.getTextureObject(), fruit.getX(), fruit.getY(), fruit.getWidth(), fruit.getHeight());
        }
    }

    private void handleInput() {
        if (ioController.getInputManager().isKeyJustReleased(Input.Keys.R)) {
            sceneController.changeScreen(new Gameplay_Specific_scene(ioController, sceneController, new MovementController(ioController, camera)));
        }
        if (ioController.getInputManager().isKeyJustReleased(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    private void spawnFruitIfNeeded() {
        if (TimeUtils.nanoTime() - lastSpawnTime > 100_000_000) {
            float x = random.nextFloat() * (viewport.getWorldWidth() - 32);
            String textureName = foodTextures[random.nextInt(foodTextures.length)];
            String fullPath = "healthy food/" + textureName;

            FoodFactory factory = new FoodFactory("fruit", "food", fullPath, x, viewport.getWorldHeight(), 32, 32);
            FoodEntity fruit = (FoodEntity) factory.createEntity();
            fallingFruits.add(fruit);
            lastSpawnTime = TimeUtils.nanoTime();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        background.dispose();
        font.dispose();
    }
}
