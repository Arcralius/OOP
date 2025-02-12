package com.arcralius.ff.lwjgl3.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayableEntity extends BaseEntity {
    public PlayableEntity(String texturePath, float x, float y, String id, float speed) {
        super(texturePath, x, y, id, speed);
        this.textureObject = new Texture(texturePath); // Load entity texture
    }


    @Override
    public void handleMovement() {
        // Example movement logic (override as needed)
        x += speed;
    }

}
