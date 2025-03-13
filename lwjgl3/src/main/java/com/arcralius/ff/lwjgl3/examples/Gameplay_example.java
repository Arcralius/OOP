package com.arcralius.ff.lwjgl3.examples;
import com.arcralius.ff.lwjgl3.entity.NonPlayableEntity;
import com.arcralius.ff.lwjgl3.scene.*;
import com.arcralius.ff.lwjgl3.collision.CollisionController;
import com.arcralius.ff.lwjgl3.entity.BaseEntity;
import com.arcralius.ff.lwjgl3.entity.EntityController;
import com.arcralius.ff.lwjgl3.entity.PlayableEntity;
import com.arcralius.ff.lwjgl3.input_output.IO_Controller;
import com.arcralius.ff.lwjgl3.movement.MovementController;
import com.arcralius.ff.lwjgl3.scene.SceneController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.ArrayList;
import java.util.List;
public class Gameplay_example extends GameplayScreen {


    // Create the EntityController and List for managing entities


    private BitmapFont font; // Font for displaying text
    private String collisionMessage = ""; // Stores collision message
    private float collisionTimer = 0; // Timer to make message disappear
    private BaseEntity enemy1; //
    private BaseEntity enemy2; //
    private BaseEntity enemy3; //


    public Gameplay_example(IO_Controller ioController, SceneController sceneController, MovementController movementController) {
        super(ioController, sceneController, movementController);

        // Update DisplayManager with the current screen
        ioController.getDisplayManager().setCurrentScreen("Gameplay_example");

        // Set display resolution dynamically
        ioController.getDisplayManager().setResolution(ioController.getDisplayManager().getResolution());

        // Load and start music
        ioController.getAudioManager().stopMusic("main_menu_music");
        ioController.getAudioManager().playMusic("gameplay_music", true);

        // Initialize entity list and controller

        // Load map and textures


        // Initialize the playable entity
//
//        // Add the playable entity to the entity controller
        // Create an enemy (NonPlayableEntity) and add it to the entity controller
        //NonPlayableEntity enemy1 = new NonPlayableEntity("droplet.png", 300, 300, "enemy 1", 100, 32, 32);
        //NonPlayableEntity enemy2 = new NonPlayableEntity("droplet.png", 200, 100, "enemy 2", 200, 32, 32);
        //NonPlayableEntity enemy3 = new NonPlayableEntity("droplet.png", 300, 200, "enemy 3", 300, 32, 32);
        entityController.addEntity("Non_playable", "droplet.png", 300, 300, "enemy 1", 100, 32, 32);
        entityController.addEntity("Non_playable", "droplet.png", 200, 100, "enemy 2", 200, 32, 32);
        entityController.addEntity("Non_playable", "droplet.png", 300, 200, "enemy 3", 300, 32, 32);


        this.enemy1 = entityController.getEntityById("enemy 1");
        this.enemy2 = entityController.getEntityById("enemy 2");
        this.enemy3 = entityController.getEntityById("enemy 3");

        //ntityController.addEntity(enemy1);
        //entityController.addEntity(enemy2);
        //entityController.addEntity(enemy3);

        // Initialize font
        font = new BitmapFont();
    }







}
