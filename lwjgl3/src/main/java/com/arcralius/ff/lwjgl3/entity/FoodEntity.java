package com.arcralius.ff.lwjgl3.entity;

public class FoodEntity extends BaseEntity {
    private String foodType;
    private String nutritionalInfo;

    public FoodEntity(String texturePath, float x, float y, String id, float width, float height,
                      String foodType, String nutritionalInfo) {
        super(texturePath, x, y, id, 0, width, height); // Speed is 0 for stationary food
        this.foodType = foodType;
        this.nutritionalInfo = nutritionalInfo;
    }

    public String getFoodType() {
        return foodType;
    }

    public String getNutritionalInfo() {
        return nutritionalInfo;
    }
}
