package com.arcralius.ff.lwjgl3.scene;

import com.arcralius.ff.lwjgl3.entity.BaseEntity;
import com.arcralius.ff.lwjgl3.entity.PlayableEntity;
import com.arcralius.ff.lwjgl3.entity.NonPlayableEntity;
import com.arcralius.ff.lwjgl3.entity.EntityController;
import com.badlogic.gdx.Gdx;
import com.arcralius.ff.lwjgl3.movement.MovementController;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

    private final PlayableEntity playableEntity;

    // Create the EntityController and List for managing entities
    private final List<BaseEntity> entityList;
    private final EntityController entityController;

    public GameplayScreen(SceneController sceneController, MovementController movementController) {
        this.sceneController = sceneController;
        this.movementController = movementController;

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
        NonPlayableEntity enemy = new NonPlayableEntity("droplet.png", 300, 300, "enemy", 300, 32, 32);
        entityController.addEntity(enemy);
    }

    private boolean isPaused = false; // Tracks whether the game is paused

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        this.isPaused = paused;
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            System.out.println("ESC pressed! Switching to PauseScreen...");
            isPaused = true; //Ensures update() stops running
            sceneController.changeScreen(new PauseScreen(sceneController, this));
        }
    }


    @Override
    protected void update(float delta) {
        if (isPaused) return; // Stops updates when paused
        movementController.handleMovement(playableEntity, delta); // Call movement
        handleInput();

        for (BaseEntity entity : entityList) {
            if (entity instanceof NonPlayableEntity) {
                movementController.handleNPCMovement((NonPlayableEntity) entity, delta); // Handle NPC movement
            }
        }

        camera.position.set(playableEntity.getX(), playableEntity.getY(), 0); // Camera follows the player
        camera.update();
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
    }

    @Override
    public void dispose() {
        super.dispose();
        map.dispose();
        mapRenderer.dispose();
        backgroundTexture.dispose();
    }
}
