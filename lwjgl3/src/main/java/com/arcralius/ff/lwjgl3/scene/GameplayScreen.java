package com.arcralius.ff.lwjgl3.scene;

import com.arcralius.ff.lwjgl3.entity.BaseEntity;
import com.arcralius.ff.lwjgl3.entity.PlayableEntity;
import com.arcralius.ff.lwjgl3.entity.NonPlayableEntity;
import com.arcralius.ff.lwjgl3.entity.EntityController;
import com.arcralius.ff.lwjgl3.collision.CollisionController;
import com.arcralius.ff.lwjgl3.input_output.AudioManager;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.arcralius.ff.lwjgl3.movement.MovementController;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.compression.lzma.Base;

import java.util.ArrayList;
import java.util.List;

public class GameplayScreen extends BaseScreen {
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final Texture backgroundTexture;
    private final Sprite backgroundSprite;
    private final MovementController movementController;
    private final SceneController sceneController;
    private final AudioManager audioManager;


    private final PlayableEntity playableEntity;

    // Create the EntityController and List for managing entities
    private final List<BaseEntity> entityList;
    private final EntityController entityController;
    private final CollisionController collisionController;

    private BitmapFont font; // Font for displaying text
    private String collisionMessage = ""; // Stores collision message
    private float collisionTimer = 0; // Timer to make message disappear

    public GameplayScreen(SceneController sceneController, MovementController movementController, AudioManager audioManager) {
        this.sceneController = sceneController;
        this.movementController = movementController;
        this.audioManager = audioManager;
        this.collisionController = new CollisionController(this);

        // Initialize entity list and controller
        entityList = new ArrayList<>();
        entityController = new EntityController(entityList);

        // Load map and textures
        this.map = new TmxMapLoader().load("background.tmx");
        this.mapRenderer = new OrthogonalTiledMapRenderer(map, batch);
        this.backgroundTexture = new Texture("plains.png");
        this.backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Initialize the playable entity
        playableEntity = new PlayableEntity("bucket.png", 100, 100, "player", 1000, 20, 20);

        // Add the playable entity to the entity controller
        entityController.addEntity(playableEntity);

        // Create an enemy (NonPlayableEntity) and add it to the entity controller
        NonPlayableEntity enemy1 = new NonPlayableEntity("droplet.png", 300, 300, "enemy 1", 100, 32, 32);
        NonPlayableEntity enemy2 = new NonPlayableEntity("droplet.png", 200, 100, "enemy 2", 200, 32, 32);
        NonPlayableEntity enemy3 = new NonPlayableEntity("droplet.png", 300, 200, "enemy 3", 300, 32, 32);
        entityController.addEntity(enemy1);
        entityController.addEntity(enemy2);
        entityController.addEntity(enemy3);

        // Initialize font
        font = new BitmapFont();
    }

    private boolean isPaused = false; // Tracks whether the game is paused

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

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            System.out.println("ESC pressed! Switching to PauseScreen...");
            isPaused = true; //Ensures update() stops running
            sceneController.changeScreen(new PauseScreen(sceneController, this, audioManager));
        }
    }


    @Override
    protected void update(float delta) {
        if (isPaused) return; // Stops updates when paused
        movementController.handleMovement(playableEntity, delta); // Call movement
        handleInput();

        collisionController.checkCollisions(playableEntity, entityList);

        for (BaseEntity entity : entityList) {
            if (entity instanceof NonPlayableEntity) {
                movementController.handleNPCMovement((NonPlayableEntity) entity, delta); // Handle NPC movement
            }
        }

        camera.position.set(playableEntity.getX(), playableEntity.getY(), 0); // Camera follows the player
        camera.update();

        // Reduce collision message display time
        if (collisionTimer > 0) {
            collisionTimer -= delta;
            if (collisionTimer <= 0) {
                collisionMessage = ""; // Remove message after time is up
            }
        }
    }

    @Override
    protected void draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        // Draw the background
        backgroundSprite.draw(batch);

        batch.end();

        // Render the map
        mapRenderer.setView(camera);
        mapRenderer.render();

        // Draw all entities (including player and enemies) using the entityController
        entityController.draw(batch);

        // Draw collision message at the player’s position
        if (!collisionMessage.isEmpty()) {
            batch.begin();
            font.draw(batch, collisionMessage, playableEntity.getX(), playableEntity.getY() + 50);
            batch.end();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        map.dispose();
        mapRenderer.dispose();
        backgroundTexture.dispose();
        font.dispose();
    }
}
