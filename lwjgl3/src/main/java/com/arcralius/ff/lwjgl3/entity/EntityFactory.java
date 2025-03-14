package com.arcralius.ff.lwjgl3.entity;

public class EntityFactory {
    public static BaseEntity createEntity(String type, String texturePath, float x, float y, String id, float speed, float width, float height) {
        if (type.equalsIgnoreCase("Playable")) {
            return new PlayableEntity(texturePath, x, y, id, speed, width, height);
        } else if (type.equalsIgnoreCase("Non_playable")) {
            return new NonPlayableEntity(texturePath, x, y, id, speed, width, height);
        }
        return null; // Return null if the type is invalid
    }

    public static FoodEntity createFoodEntity(String texturePath, float x, float y, String id,
                                              float width, float height, String foodType, String nutritionalInfo) {
        return new FoodEntity(texturePath, x, y, id, width, height, foodType, nutritionalInfo);
    }
}
