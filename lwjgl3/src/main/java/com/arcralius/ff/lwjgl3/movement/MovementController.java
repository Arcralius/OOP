package com.arcralius.ff.lwjgl3.movement;

import com.arcralius.ff.lwjgl3.entity.PlayableEntity;
// import com.arcralius.ff.lwjgl3.collision.CollisionController; // Import CollisionController
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MovementController {
    private final PlayableEntity player;
    private final OrthographicCamera camera;
    private static final float SPEED = 2.0f; // Movement speed

    public MovementController(PlayableEntity player, OrthographicCamera camera) {
        this.player = player;
        this.camera = camera;
    }

    public void handleMovement(PlayableEntity player, float delta) {

        float newX = player.getX();
        float newY = player.getY();

        // Movement input handling
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            newX -= SPEED * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            newX += SPEED * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            newY += SPEED * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            newY -= SPEED * delta;
        }

        camera.position.set(player.getX(), player.getY(), 0);
        camera.update();
    }
}
