package com.arcralius.ff.lwjgl3.specific_scene;

import com.arcralius.ff.lwjgl3.entity.BaseEntity;
import com.arcralius.ff.lwjgl3.scene.GameplayScreen;
import com.arcralius.ff.lwjgl3.scene.SceneController;
import com.arcralius.ff.lwjgl3.input_output.IO_Controller;
import com.arcralius.ff.lwjgl3.movement.MovementController;
import com.arcralius.ff.lwjgl3.scene.VictoryScreen;
import com.arcralius.ff.lwjgl3.scene.component.FoodSystem;
import com.arcralius.ff.lwjgl3.scene.component.FoodInfoDisplay;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.arcralius.ff.lwjgl3.entity.Entitybuilder.GameEntityBuilder;

public class Gameplay_Specific_scene extends GameplayScreen {
    private FoodSystem foodSystem;
    private FoodInfoDisplay foodInfoDisplay;
    private int foodCollected = 0;
    private int totalFood;
    private BitmapFont scoreFont;
    private BaseEntity enemy1, enemy2, enemy3;

    // UI elements
    private SpriteBatch uiBatch;
    private OrthographicCamera uiCamera;
    private SceneController sceneController;

    public Gameplay_Specific_scene(IO_Controller ioController, SceneController sceneController, MovementController movementController) {
        super(ioController, sceneController, movementController);
        ioController.getDisplayManager().setCurrentScreen("Gameplay_specific_scene");
        ioController.getDisplayManager().setResolution(ioController.getDisplayManager().getResolution());

        // Load and start music
        ioController.getAudioManager().stopMusic("main_menu_music");
        ioController.getAudioManager().playMusic("gameplay_music", true);

        // Initialize food system
        foodSystem = new FoodSystem(entityController, 1000, 1000, 40);
        foodInfoDisplay = new FoodInfoDisplay();
        addComponent(foodSystem);
        addComponent(foodInfoDisplay);
        totalFood = foodSystem.getTotalFoodCount();
        scoreFont = new BitmapFont();
        scoreFont.getData().setScale(1.5f);






        // Initialize playable entity
        BaseEntity player = GameEntityBuilder.createEntity("playable", "player", "bucket.png", 100, 100, 1000, 20, 20);
        entityController.addEntity(player);



        // Initialize enemies
        initialiseEnemies();

        // Initialize UI components
        uiBatch = new SpriteBatch();
        uiCamera = new OrthographicCamera();
        uiCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.sceneController = sceneController;
    }

    public void incrementFoodCollected() {
        foodCollected++;
        checkVictoryCondition();
    }

    public void showFoodInfo(String foodType) {
        foodInfoDisplay.showInfo(foodType);
    }

    private void checkVictoryCondition() {
        if (foodCollected >= totalFood) {
            ioController.getAudioManager().stopMusic("gameplay_music");
            sceneController.changeScreen(new VictoryScreen(ioController, sceneController));
        }
    }

    private void initialiseEnemies() {
        String[] ids = { "enemy 1", "enemy 2", "enemy 3" };
        float[][] positions = {
            { 300f, 300f, 100f },
            { 200f, 100f, 200f },
            { 300f, 200f, 300f }
        };

        for (int i = 0; i < ids.length; i++) {
            String id = ids[i];
            float x = positions[i][0];
            float y = positions[i][1];
            float speed = positions[i][2];

            BaseEntity enemy = GameEntityBuilder.createEntity("non_playable", id, "droplet.png", x, y, speed, 32, 32);
            entityController.addEntity(enemy);


        }
    }


    @Override
    protected void draw() {
        super.draw(); // Calls GameplayScreen's draw method

        // Draw UI elements
        uiBatch.setProjectionMatrix(uiCamera.combined);
        uiBatch.begin();
        scoreFont.draw(uiBatch, foodCollected + "/" + totalFood + " food collected", 20, Gdx.graphics.getHeight() - 20);

        // Draw food info display if active
        if (foodInfoDisplay.isActive()) {
            foodInfoDisplay.render(uiBatch);
        }

        uiBatch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        uiBatch.dispose();
        scoreFont.dispose();
    }
}
