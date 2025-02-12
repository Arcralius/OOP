package com.arcralius.ff.lwjgl3.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public  class BaseEntity {
    protected Texture textureObject;

    protected float x,y;
    protected float speed;
    protected String id;




    //default values
    public BaseEntity() {
        this.x = 0;
        this.y = 0;
        this.speed = 0;
    }



    public BaseEntity(String texturePath, float x, float y, String id, float speed) {
    	this.textureObject = new Texture(texturePath);
        this.x = x;
        this.y = y;
        this.id = id;
        this.speed = speed;

    }


    public void draw(SpriteBatch batch) {
        if (textureObject != null) {
            batch.begin();
            batch.draw(textureObject, x, y);
            batch.end();
        }

    }

    public void handleMovement() {
        // Default movement logic, override in subclasses if needed
    }



    // x setters and getters

    public Texture getTextureObject(){
        return textureObject;
    }






    public float getX()
    {
        return x;
    }

    void setX(float newX) {
        x = newX;
    }

    // y setters and getters

    public float getY()
    {
        return y;
    }

    void setY(float newY) {
        y = newY;
    }

    // setters and getters for speed
    public float getSpeed()
    {
        return speed;
    }

    void setSpeed(float NewSpeed) {
        speed = NewSpeed;
    }






}


