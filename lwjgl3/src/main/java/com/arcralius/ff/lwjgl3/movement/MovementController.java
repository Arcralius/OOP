package com.arcralius.ff.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class MovementController {
    private static final float SPEED = 100.0f;
    private final Sprite playerSprite;
    private final OrthographicCamera camera;
    private final TiledMapTileLayer collisionLayer;

    public MovementController(Sprite playerSprite, OrthographicCamera camera, TiledMapTileLayer collisionLayer) {
        this.playerSprite = playerSprite;
        this.camera = camera;
        this.collisionLayer = collisionLayer;
    }

    public void handleMovement(float delta) {
        float newX = playerSprite.getX();
        float newY = playerSprite.getY();

        if (Gdx.input.isKeyPressed(Input.Keys.A) && !isColliding(newX - SPEED * delta, newY)) {
            playerSprite.translateX(-SPEED * delta);
            camera.position.x -= SPEED * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) && !isColliding(newX + SPEED * delta, newY)) {
            playerSprite.translateX(SPEED * delta);
            camera.position.x += SPEED * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W) && !isColliding(newX, newY + SPEED * delta)) {
            playerSprite.translateY(SPEED * delta);
            camera.position.y += SPEED * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) && !isColliding(newX, newY - SPEED * delta)) {
            playerSprite.translateY(-SPEED * delta);
            camera.position.y -= SPEED * delta;
        }
    }

    private boolean isColliding(float x, float y) {
        int spriteWidth = 16;
        int spriteHeight = 16;

        int startTileX = (int) (x / collisionLayer.getTileWidth());
        int startTileY = (int) (y / collisionLayer.getTileHeight());

        int endTileX = (int) ((x + spriteWidth) / collisionLayer.getTileWidth());
        int endTileY = (int) ((y + spriteHeight) / collisionLayer.getTileHeight());

        if (startTileX < 0 || startTileX >= collisionLayer.getWidth() || startTileY < 0 || startTileY >= collisionLayer.getHeight()) {
            return true;
        }

        for (int tileX = startTileX; tileX <= endTileX; tileX++) {
            for (int tileY = startTileY; tileY <= endTileY; tileY++) {
                if (tileX < 0 || tileX >= collisionLayer.getWidth() || tileY < 0 || tileY >= collisionLayer.getHeight()) {
                    return true;
                }

                TiledMapTileLayer.Cell cell = collisionLayer.getCell(tileX, tileY);

                if (cell != null && cell.getTile() != null) {
                    return true;
                }
            }
        }
        return false;
    }
}
