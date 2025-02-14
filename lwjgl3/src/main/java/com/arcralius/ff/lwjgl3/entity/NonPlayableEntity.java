package com.arcralius.ff.lwjgl3.entity;

import com.badlogic.gdx.graphics.Texture;

public class NonPlayableEntity extends BaseEntity{
    public NonPlayableEntity(String texturePath, float x, float y, String id, float speed,float width, float height) {
        super(texturePath, x, y, id, speed,width, height);
        this.textureObject = new Texture(texturePath); // Load entity texture
    }


    @Override
    public void handleMovement() {
        // Example movement logic (override as needed)

    }
}
