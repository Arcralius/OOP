package com.arcralius.ff.lwjgl3.scene;

import com.arcralius.ff.lwjgl3.entity.*;
import com.arcralius.ff.lwjgl3.collision.CollisionController;
import com.arcralius.ff.lwjgl3.input_output.IO_Controller;
import com.arcralius.ff.lwjgl3.movement.MovementController;
import com.arcralius.ff.lwjgl3.scene.component.FoodInfoDisplay;
import com.arcralius.ff.lwjgl3.scene.component.FoodSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.ArrayList;
import java.util.List;

public class GameplayScreen extends BaseScreen {
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final Texture backgroundTexture;
    private final Sprite backgroundSprite;
    private final MovementController movementController;
    private final SceneController sceneController;

    // Entity management
    private final List<BaseEntity> entityList;
    public final EntityController entityController;
    private final CollisionController collisionController;

    // UI and state
    private BitmapFont font;
    private String collisionMessage = "";
    private float collisionTimer = 0;
    protected boolean isPaused = false;
    private BaseEntity playableEntity;

    // Food system components
    private FoodSystem foodSystem;
    private FoodInfoDisplay foodInfoDisplay;
    private int foodCollected = 0;
    private int totalFood = 0;
    private BitmapFont scoreFont;

    // UI camera for fixed position elements
    private OrthographicCamera uiCamera;
    private SpriteBatch uiBatch;

    public GameplayScreen(IO_Controller ioController, SceneController sceneController, MovementController movementController) {
        super(ioController);
        this.sceneController = sceneController;
        this.movementController = movementController;
        this.collisionController = new CollisionController(ioController, this, sceneController);

        // Initialize entity system
        entityList = new ArrayList<>();
        IEntityFactory entityFactory = new ConcreteEntityFactory();
        entityController = new EntityController(entityList, entityFactory);

        // Load map and textures
        this.map = new TmxMapLoader().load("background.tmx");
        this.mapRenderer = new OrthogonalTiledMapRenderer(map, batch);
        this.backgroundTexture = new Texture("plains.png");
        this.backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Create player entity
        entityController.addEntity("Playable", "bucket.png", 100, 100, "player", 1000, 20, 20);
        this.playableEntity = entityController.getEntityById("player");

        // Create enemy entities
        entityController.addEntity("Non_playable", "droplet.png", 300, 300, "enemy 1", 100, 32, 32);
        entityController.addEntity("Non_playable", "droplet.png", 200, 100, "enemy 2", 200, 32, 32);
        entityController.addEntity("Non_playable", "droplet.png", 300, 200, "enemy 3", 300, 32, 32);

        // Initialize font
        font = new BitmapFont();

        // Initialize food-related components
        foodSystem = new FoodSystem(entityController, 1000, 1000, 40); // Spawn 40 food items randomly across the map
        addComponent(foodSystem);

        foodInfoDisplay = new FoodInfoDisplay();
        addComponent(foodInfoDisplay);

        // Initialize food counting system
        this.totalFood = foodSystem.getTotalFoodCount();
        this.foodCollected = 0;
        this.scoreFont = new BitmapFont();
        scoreFont.getData().setScale(1.5f);

        // Setup UI camera for fixed position UI elements
        this.uiCamera = new OrthographicCamera();
        this.uiCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.uiBatch = new SpriteBatch();
    }

    public void incrementFoodCollected() {
        foodCollected++;
        System.out.println("Food collected: " + foodCollected + "/" + totalFood);
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        this.isPaused = paused;
    }

    public void displayCollisionMessage(String message) {
        this.collisionMessage = message;
        this.collisionTimer = 2.0f; // Display message for 2 seconds
    }

    public void showFoodInfo(String foodType) {
        foodInfoDisplay.showInfo(foodType);
    }

    public FoodSystem getFoodSystem() {
        return foodSystem;
    }

    public EntityController getEntityController() {
        return entityController;
    }

    private void handleInput() {
        if (ioController.getInputManager().isKeyJustReleased(Input.Keys.ESCAPE)) {
            System.out.println("ESC pressed! Switching to PauseScreen...");
            isPaused = true;
            sceneController.changeScreen(new PauseScreen(ioController, sceneController, this));
        }
    }

    @Override
    protected void update(float delta) {
        if (isPaused) return;

        movementController.handleMovement(playableEntity, delta);
        ioController.update();
        handleInput();

        // Check collisions
        collisionController.checkCollisions(playableEntity, entityList);

        // Update NPC movements
        for (BaseEntity entity : entityList) {
            if (entity instanceof NonPlayableEntity && !(entity instanceof FoodEntity)) {
                movementController.handleNPCMovement((NonPlayableEntity) entity, delta);
            }
        }

        // Update collision message timer
        if (collisionTimer > 0) {
            collisionTimer -= delta;
            if (collisionTimer <= 0) {
                collisionMessage = "";
            }
        }

        // Camera follow player
        camera.zoom = 0.60f;
        camera.position.set(playableEntity.getX(), playableEntity.getY(), 0);
        camera.update();

        // Update UI camera
        uiCamera.update();
    }

    @Override
    protected void draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw background
        batch.begin();
        backgroundSprite.draw(batch);
        batch.end();

        // Render the map
        mapRenderer.setView(camera);
        mapRenderer.render();

        // Draw all entities
        entityController.draw(batch);

        // Draw game world elements with the game camera
        batch.begin();
        // Draw collision message at player position
        if (!collisionMessage.isEmpty()) {
            font.draw(batch, collisionMessage, playableEntity.getX(), playableEntity.getY() + 50);
        }
        batch.end();

        // Draw UI elements with fixed screen positions using the UI camera
        uiBatch.setProjectionMatrix(uiCamera.combined);
        uiBatch.begin();

        // Draw the food counter in top left corner
        String scoreText = foodCollected + "/" + totalFood + " healthy foods collected";
        scoreFont.setColor(1, 1, 1, 1); // White color
        scoreFont.draw(uiBatch, scoreText, 20, Gdx.graphics.getHeight() - 20);

        uiBatch.end();
    }

    @Override
    public void render(float delta) {
        super.render(delta); // This calls update and draw
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        // Update UI camera for new dimensions
        uiCamera.setToOrtho(false, width, height);
        uiCamera.update();
    }

    @Override
    public void dispose() {
        super.dispose();
        map.dispose();
        mapRenderer.dispose();
        backgroundTexture.dispose();
        font.dispose();
        scoreFont.dispose();
        uiBatch.dispose();
    }
}
