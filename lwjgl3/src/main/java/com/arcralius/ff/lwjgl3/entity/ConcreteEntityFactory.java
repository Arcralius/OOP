package com.arcralius.ff.lwjgl3.entity;

public class ConcreteEntityFactory implements IEntityFactory {
    @Override
    public PlayableEntity createPlayableEntity(String texturePath, float x, float y, String id, float speed, float width, float height) {
        return new PlayableEntity(texturePath, x, y, id, speed, width, height);
    }

    @Override
    public NonPlayableEntity createNonPlayableEntity(String texturePath, float x, float y, String id, float speed, float width, float height) {
        return new NonPlayableEntity(texturePath, x, y, id, speed, width, height);
    }

    @Override
    public FoodEntity createFoodEntity(String texturePath, float x, float y, String id, float width, float height, String foodType, String nutritionalInfo) {
        return new FoodEntity(texturePath, x, y, id, width, height, foodType, nutritionalInfo);
    }
}
