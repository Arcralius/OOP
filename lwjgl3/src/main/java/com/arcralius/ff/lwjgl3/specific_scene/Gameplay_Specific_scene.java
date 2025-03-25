package com.arcralius.ff.lwjgl3.specific_scene;

import com.arcralius.ff.lwjgl3.entity.BaseEntity;
import com.arcralius.ff.lwjgl3.entity.PlayableEntity;
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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.arcralius.ff.lwjgl3.entity.Entitybuilder.GameEntityBuilder;
import com.badlogic.gdx.graphics.g2d.GlyphLayout; // Import this at the top

public class Gameplay_Specific_scene extends GameplayScreen {
    private FoodSystem foodSystem;
    private FoodInfoDisplay foodInfoDisplay;
    private int foodCollected = 0;
    private int totalFood;
    private BitmapFont scoreFont;

    // UI elements
    private SpriteBatch uiBatch;
    private OrthographicCamera uiCamera;
    private SceneController sceneController;
    private PlayableEntity player;
    private ShapeRenderer shapeRenderer; // HP bar renderer

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

        // Initialize playable entity
        player = (PlayableEntity) GameEntityBuilder.createEntity("playable", "player", "playable_character/playable_character_forward.png", 100, 100, 1000, 40, 40, 100);
        entityController.addEntity(player);

        // Initialize enemies
        initialiseEnemies();

        // Initialize UI components
        uiBatch = new SpriteBatch();
        uiCamera = new OrthographicCamera();
        uiCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer = new ShapeRenderer();

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

            BaseEntity enemy = GameEntityBuilder.createEntity("non_playable", id, "enemy_food/burger.png", x, y, speed, 64, 64, 0);
            entityController.addEntity(enemy);
        }
    }

    @Override
    protected void draw() {
        super.draw(); // Calls GameplayScreen's draw method

        // Draw UI elements
        uiBatch.setProjectionMatrix(uiCamera.combined);
        uiBatch.begin();

        // Calculate font scale based on screen size
        float scale = Math.min(uiCamera.viewportWidth / 800f, uiCamera.viewportHeight / 600f);
        scoreFont.getData().setScale(1.5f * scale);
        scoreFont.draw(uiBatch, foodCollected + "/" + totalFood + " food collected", 20, uiCamera.viewportHeight - 20);

        // Draw food info display if active
        if (foodInfoDisplay.isActive()) {
            foodInfoDisplay.render(uiBatch);
        }
        uiBatch.end();

        // Draw HP bar
        drawHPBar();
    }

    private void drawHPBar() {
        float maxHP = 100f;
        float currentHP = player.getHP();
        float screenWidth = uiCamera.viewportWidth;
        float screenHeight = uiCamera.viewportHeight;

        // Set health bar dimensions relative to screen size
        float hpBarWidth = screenWidth * 0.7f; // 70% of screen width
        float hpBarHeight = screenHeight * 0.035f; // 3.5% of screen height (slightly bigger)
        float barX = (screenWidth - hpBarWidth) / 2;
        float barY = screenHeight * 0.03f;

        // Calculate health percentage
        float hpPercentage = currentHP / maxHP;
        float filledWidth = hpBarWidth * hpPercentage;

        shapeRenderer.setProjectionMatrix(uiCamera.combined);

        // Begin drawing the HP bar
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // --- Background border ---
        shapeRenderer.setColor(Color.BLACK); // Black border
        shapeRenderer.rect(barX - 3, barY - 3, hpBarWidth + 6, hpBarHeight + 6);

        // --- Background (Gray Gradient) ---
        shapeRenderer.setColor(new Color(0.2f, 0.2f, 0.2f, 1f)); // Dark gray
        shapeRenderer.rect(barX, barY, hpBarWidth, hpBarHeight);

        // --- HP Bar with gradient effect ---
        Color hpColor = (hpPercentage > 0.5f) ? Color.GREEN : (hpPercentage > 0.2f) ? Color.ORANGE : Color.RED;
        shapeRenderer.setColor(hpColor);
        shapeRenderer.rect(barX, barY, filledWidth, hpBarHeight);

        shapeRenderer.end();

        // --- Draw "HP" label above the bar, centered ---
        uiBatch.begin();
        scoreFont.getData().setScale(screenWidth * 0.0025f);
        GlyphLayout layout = new GlyphLayout(scoreFont, "HP");
        float textWidth = layout.width;
        float textX = (screenWidth - textWidth) / 2;
        float textY = barY + hpBarHeight + screenHeight * 0.009f;

        scoreFont.setColor(Color.WHITE);
        scoreFont.draw(uiBatch, "HP", textX, textY);
        uiBatch.end();
    }




    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        uiCamera.setToOrtho(false, width, height);
        uiCamera.update();
    }

    @Override
    public void dispose() {
        super.dispose();
        uiBatch.dispose();
        scoreFont.dispose();
        shapeRenderer.dispose();
    }
}
