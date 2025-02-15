package com.arcralius.ff.lwjgl3.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class BaseEntity {
    protected Texture textureObject;
    protected Sprite sprite; // Add sprite as a field
    private float x, y;
    private float speed, width, height;
    private String id;
    private Rectangle boundary;

    // Constructor to initialize the entity
    public BaseEntity(String texturePath, float x, float y, String id, float speed, float width, float height) {
        this.textureObject = new Texture(texturePath);
        this.sprite = new Sprite(textureObject); // Initialize sprite with the texture
        this.sprite.setSize(width, height); // Set sprite size
        this.sprite.setPosition(x, y); // Set initial position

        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.id = id;
        this.speed = speed;
        this.boundary = new Rectangle(x, y, width, height); // Initialize the boundary
    }

    // Draw the sprite (visual representation)
    public void draw(SpriteBatch batch) {
        if (sprite != null) {
            batch.begin();
            sprite.draw(batch); // Draw the sprite
            batch.end();
        }
    }

    // Getter for boundary
    public Rectangle getBoundary() {
        return boundary;
    }

    // Getter for the texture object
    public Texture getTextureObject() {
        return textureObject;
    }

    // Getter and setter for position and other properties
    public float getX() {
        return x;
    }

    public void setX(float newX) {
        x = newX;
        sprite.setX(newX); // Update sprite position
        boundary.setX(newX); // Update boundary position
    }

    public float getY() {
        return y;
    }

    public void setY(float newY) {
        y = newY;
        sprite.setY(newY); // Update sprite position
        boundary.setY(newY); // Update boundary position
    }

    // Setter and getter for speed
    public float getSpeed() {
        return speed;
    }

    void setSpeed(float newSpeed) {
        speed = newSpeed;
    }

    public String getId() {
        return id;
    }

    void setId(String newId) {
        id = newId;
    }

    // Dispose of resources (dispose texture)
    public void dispose() {
        textureObject.dispose(); // Dispose of the texture
    }
}
