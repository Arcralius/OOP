package com.arcralius.ff.lwjgl3.scene;

import com.arcralius.ff.lwjgl3.BaseScreen;
import com.arcralius.ff.lwjgl3.movement.MovementController;
import com.arcralius.ff.lwjgl3.entity.*;
import com.arcralius.ff.lwjgl3.collision.CollisionController;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.ArrayList;

public class GameplayScreen extends BaseScreen {
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final PlayableEntity player;
    private final EntityController entityController;
    private final MovementController movementController;
    private final TiledMapTileLayer collisionLayer;
    private final CollisionController collisionController;

    public GameplayScreen(SceneController sceneController) {
        super(); //Call BaseScreen constructor

        //Initialize entity controller with an empty list
        this.entityController = new EntityController(new ArrayList<>());

        //Load the tile map before initializing entities
        this.map = new TmxMapLoader().load("background.tmx");
        this.mapRenderer = new OrthogonalTiledMapRenderer(map, batch);

        //Retrieve the collision layer from the map
        this.collisionLayer = (TiledMapTileLayer) map.getLayers().get("collisions");

        //Initialize collision controller
        this.collisionController = new CollisionController(collisionLayer);

        //Initialize the player entity
        this.player = new PlayableEntity("bucket.png", 100, 100, "player", 2.0f);
        entityController.addEntity(player); //Add player to entity list

        //Initialize movement controller using `collisionController`
        this.movementController = new MovementController(player, camera, collisionController);

        //Center the camera on the player
        camera.position.set(player.getX(), player.getY(), 0);
    }

    @Override
    protected void update(float delta) {
        movementController.handleMovement(delta); //Player movement handled here
        camera.update();
    }

    @Override
    protected void draw() {
        mapRenderer.setView(camera);
        mapRenderer.render(); //Render the tile map

        //Start batch for rendering entities
        batch.begin();
        entityController.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        map.dispose();
        mapRenderer.dispose();
    }
}
