package com.arcralius.ff.lwjgl3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GameplayScreen extends BaseScreen {
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final Sprite bucketSprite;
    private final Texture bucketTexture;
    private final MovementController movementController;

    public GameplayScreen(SceneController sceneController) {
        // Load the tile map
        this.map = new TmxMapLoader().load("ui/background.tmx");

        //Ensure `batch` is initialized before using `mapRenderer`
        this.mapRenderer = new OrthogonalTiledMapRenderer(map, batch);

        // Retrieve the collision layer from the map
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get("collisions");

        // Load the player sprite
        this.bucketTexture = new Texture("ui/bucket.png");
        this.bucketSprite = new Sprite(bucketTexture);
        this.bucketSprite.setSize(16, 16);

        // Initialize the movement controller
        this.movementController = new MovementController(bucketSprite, camera, collisionLayer);

        // Center the camera on the player
        camera.position.set(bucketSprite.getX(), bucketSprite.getY(), 0);
    }

    @Override
    protected void update(float delta) {
        movementController.handleMovement(delta);
        camera.update();
    }

    @Override
    protected void draw() {
        mapRenderer.setView(camera);
        mapRenderer.render();

        //start batch for sprites
        batch.begin();
        bucketSprite.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        map.dispose();
        bucketTexture.dispose(); //Properly dispose of texture to prevent memory leaks
    }
}
