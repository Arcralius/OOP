package com.arcralius.ff.lwjgl3.entity;

public class FoodEntity extends BaseEntity {
    private String foodType;

    public FoodEntity(String texturePath, float x, float y, String id, float width, float height,
                      String foodType) {
        super(texturePath, x, y, id, 0, width, height); // Speed is 0 for stationary food
        this.foodType = foodType;
    }

    public String getFoodType() {
        return foodType;
    }
}


