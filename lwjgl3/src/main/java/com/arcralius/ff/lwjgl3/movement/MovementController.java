package com.arcralius.ff.lwjgl3.movement;

import com.arcralius.ff.lwjgl3.entity.BaseEntity;
import com.arcralius.ff.lwjgl3.entity.NonPlayableEntity;
import com.arcralius.ff.lwjgl3.entity.PlayableEntity;
import com.arcralius.ff.lwjgl3.input_output.IO_Controller;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;

public class MovementController {
    private final IO_Controller ioController;
    protected final OrthographicCamera camera;
    protected static final float speed = 200.0f; // Increased speed
    protected static final float npc_speed = 50.0f; // Increased speed

    private float movementCooldown = 0.5f; // Time in seconds between movement changes
    private float timeSinceLastMove = 0;
    private float directionChangeTimer = 0; // Timer for changing directions
    private static final float DIRECTION_CHANGE_INTERVAL = 2f; // seconds for each direction change
    private int currentDirection = MathUtils.random(0, 3); // Random initial direction (0 = Up, 1 = Down, 2 = Left, 3 = Right)

    public MovementController(IO_Controller ioController, OrthographicCamera camera) {
        this.ioController = ioController;
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

        // Use IO_Controller for keyboard input
        if (ioController.getInputManager().isKeyPressed(Input.Keys.W)) newY += speed * delta;
        if (ioController.getInputManager().isKeyPressed(Input.Keys.A)) newX -= speed * delta;
        if (ioController.getInputManager().isKeyPressed(Input.Keys.S)) newY -= speed * delta;
        if (ioController.getInputManager().isKeyPressed(Input.Keys.D)) newX += speed * delta;

        // Boundary restrictions
        if (newX < 0) {
            newX = 0;
        } else if (newX > 940) {
            newX = 940;
        }

        if (newY < 0) {
            newY = 0;
        } else if (newY > 940) {
            newY = 940;
        }

        player.setX(newX);
        player.setY(newY);
    }

    public void handleNPCMovement(NonPlayableEntity entity, float delta) {
        // Update the direction change timer
        entity.setDirectionChangeTimer(entity.getDirectionChangeTimer() + delta);

        // If the timer exceeds the interval, change direction
        if (entity.getDirectionChangeTimer() >= DIRECTION_CHANGE_INTERVAL) {
            entity.setDirectionChangeTimer(0); // Reset timer
            entity.setCurrentDirection(MathUtils.random(0, 3)); // Randomize direction
        }

        // Move NPC based on direction
        switch (entity.getCurrentDirection()) {
            case 0: // UP
                if (entity.getY() + npc_speed * delta > 940) {
                    entity.setY(940);
                    entity.setCurrentDirection(1); // Change Direction to DOWN since hit boundary
                } else {
                    entity.setY(entity.getY() + npc_speed * delta);
                }
                break;
            case 1: // DOWN
                if (entity.getY() - npc_speed * delta < 0) {
                    entity.setY(0);
                    entity.setCurrentDirection(0); // Change Direction to UP since hit boundary
                } else {
                    entity.setY(entity.getY() - npc_speed * delta);
                }
                break;
            case 2: // LEFT
                if (entity.getX() - npc_speed * delta < 0) {
                    entity.setX(0);
                    entity.setCurrentDirection(3);
                } else {
                    entity.setX(entity.getX() - npc_speed * delta);
                }
                break;
            case 3: // RIGHT
                if (entity.getX() + npc_speed * delta > 940) { // Adjust 1280 based on your actual boundary
                    entity.setX(940);
                    entity.setCurrentDirection(2);
                } else {
                    entity.setX(entity.getX() + npc_speed * delta);
                }
                break;
        }
    }
}
