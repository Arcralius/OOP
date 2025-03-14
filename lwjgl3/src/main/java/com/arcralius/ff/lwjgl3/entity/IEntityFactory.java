package com.arcralius.ff.lwjgl3.entity;

public interface IEntityFactory {
    PlayableEntity createPlayableEntity(String texturePath, float x, float y, String id, float speed, float width, float height);
    NonPlayableEntity createNonPlayableEntity(String texturePath, float x, float y, String id, float speed, float width, float height);
    FoodEntity createFoodEntity(String texturePath, float x, float y, String id, float width, float height, String foodType, String nutritionalInfo);
}
