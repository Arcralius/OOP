package com.arcralius.ff.lwjgl3.collision;

import com.arcralius.ff.lwjgl3.entity.BaseEntity;

import java.util.ArrayList;

public class CollisionController {
    private boolean isEnabled;
    private List<SimpleEntry<BaseEntity, BaseEntity>> collisionPairs;
    private CollisionStrategy strategy;

    public CollisionController() {
        this.isEnabled = true;  // Default: Collision is enabled
        this.collisionPairs = new ArrayList<>();
    }

    // Check if two entities collide (Basic AABB Collision Check)
    public boolean checkCollision(BaseEntity entityA, BaseEntity entityB) {
        if (!isEnabled) return false;

        // Simple bounding box collision detection
        return entityA.getBounds().intersects(entityB.getBounds());
    }

    // Handles collision using the defined strategy
    public void handleCollision(BaseEntity entityA, BaseEntity entityB) {
        if (strategy != null) {
            strategy.handleCollision(entityA, entityB);
        } else {
            System.out.println("No collision strategy set!");
        }
    }

    // Add a collision pair for checking
    public void addCollisionPair(BaseEntity entityA, BaseEntity entityB) {
        collisionPairs.add(new SimpleEntry<>(entityA, entityB));
    }

    // Detect all collisions in the current pairs
    public void detectAllCollisions() {
        if (!isEnabled) return;

        for (SimpleEntry<BaseEntity, BaseEntity> pair : collisionPairs) {
            BaseEntity entityA = pair.getKey();
            BaseEntity entityB = pair.getValue();

            if (checkCollision(entityA, entityB)) {
                handleCollision(entityA, entityB);
            }
        }
    }

    // Resolves all detected collisions
    public void resolveCollisions() {
        System.out.println("Resolving all detected collisions...");
    }

    // Clear all stored collision pairs
    public void clearCollisionPairs() {
    }

    // Set the collision handling strategy
    public void setStrategy(CollisionHanding collisionHanding) {

    }

    // Enable or disable collision checking
    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }
}
