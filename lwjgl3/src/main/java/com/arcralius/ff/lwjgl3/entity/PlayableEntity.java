package com.arcralius.ff.lwjgl3.entity;

import
    com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayableEntity extends BaseEntity {
    public PlayableEntity(String texturePath, float x, float y, String id, float speed,float width, float height) {
        super(texturePath, x, y, id, speed,width, height);
        this.textureObject = new Texture(texturePath); // Load entity texture
    }


    @Override
    public void handleMovement() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.A)) this.setX(this.getX() - 200*deltaTime);
        if(Gdx.input.isKeyPressed(Input.Keys.D)) this.setX(this.getX() + 200*deltaTime);
    }

}
