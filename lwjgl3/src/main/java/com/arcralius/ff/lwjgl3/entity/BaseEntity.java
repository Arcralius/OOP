package com.arcralius.ff.lwjgl3.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class BaseEntity {
    protected Texture textureObject;

    protected float x,y;
    protected float speed, width, height;
    protected String id;
    private Rectangle boundary;




    public BaseEntity(String texturePath, float x, float y, String id, float speed, float width, float height) {
    	this.textureObject = new Texture(texturePath);
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.id = id;
        this.speed = speed;
        this.boundary = new Rectangle(x,y,width,height);

    }


    public void draw(SpriteBatch batch) {
        if (textureObject != null) {
            batch.begin();
            batch.draw(textureObject, x, y);
            batch.end();
        }

    }

    public Rectangle getboundary(){
        return boundary;
    }



    public void handleMovement() {
        // Default movement logic, override in subclasses if needed
    }

    public Texture getTextureObject(){
        return textureObject;
    }






    public float getX()
    {
        return x;
    }

    public void setX(float newX) {
        x = newX;
    }

    // y setters and getters

    public float getY()
    {
        return y;
    }

    public void setY(float newY) {
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


