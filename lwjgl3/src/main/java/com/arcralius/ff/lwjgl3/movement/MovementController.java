package com.arcralius.ff.lwjgl3.movement;

import com.arcralius.ff.lwjgl3.entity.BaseEntity;
import com.arcralius.ff.lwjgl3.entity.NonPlayableEntity;
import com.arcralius.ff.lwjgl3.entity.PlayableEntity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;


public class MovementController {
    protected final OrthographicCamera camera;
    protected static final float speed = 200.0f; // Increased speed
    protected static final float npc_speed = 50.0f; // Increased speed

    private float movementCooldown = 0.5f; // Time in seconds between movement changes
    private float timeSinceLastMove = 0;
    private float directionChangeTimer = 0; // Timer for changing directions
    private static final float DIRECTION_CHANGE_INTERVAL = 3f; // 3 seconds for each direction change
    private int currentDirection = MathUtils.random(0, 3); // Random initial direction (0 = Up, 1 = Down, 2 = Left, 3 = Right)

    public MovementController(OrthographicCamera camera) {
        this.camera = camera;
    }

    public void handleMovement(BaseEntity entity, float delta) {
        if (entity instanceof PlayableEntity) {
            handlePlayerMovement((PlayableEntity) entity, delta);
        } else if (entity instanceof NonPlayableEntity) {
            handleNPCMovement((NonPlayableEntity) entity, delta);
        }
    }

    private void handlePlayerMovement(PlayableEntity player, float delta) {
        float newX = player.getX();
        float newY = player.getY();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) newY += speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) newX -= speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) newY -= speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) newX += speed * delta;

        player.setX(newX);
        player.setY(newY);
    }

    public void handleNPCMovement(NonPlayableEntity entity, float delta) {
        // Update the direction change timer
        directionChangeTimer += delta;

        // If the timer exceeds the interval, change direction
        if (directionChangeTimer >= DIRECTION_CHANGE_INTERVAL) {
            directionChangeTimer = 0; // Reset timer
            currentDirection = MathUtils.random(0, 3); // Randomize direction
        }

        // Move the droplet in the current direction
        switch (currentDirection) {
            case 0: // UP
                entity.setY(entity.getY() + npc_speed * delta);
                break;
            case 1: // DOWN
                entity.setY(entity.getY() - npc_speed * delta);
                break;
            case 2: // LEFT
                entity.setX(entity.getX() - npc_speed * delta);
                break;
            case 3: // RIGHT
                entity.setX(entity.getX() + npc_speed * delta);
                break;
        }
    }
}
