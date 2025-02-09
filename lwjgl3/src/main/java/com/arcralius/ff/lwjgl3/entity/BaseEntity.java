package com.arcralius.ff.lwjgl3.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class BaseEntity {

    private float x;
    private float y;
    private float speed;
    private Color color;


    // Texture setters and getters

    public BaseEntity() {

        this.x = 0;
        this.y = 0;
        this.speed = 0;
        this.color = Color.WHITE; // Default color
    }



    public BaseEntity(float x, float y, Color color, float speed) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.speed = speed;

    }


    // x setters and getters

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

    public Color getColor() {
        return color;

    }

    void setColor(Color newColor) {
        color = newColor;
    }




}


