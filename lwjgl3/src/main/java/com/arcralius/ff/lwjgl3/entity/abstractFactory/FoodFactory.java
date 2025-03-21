package com.arcralius.ff.lwjgl3.entity.abstractFactory;

import com.arcralius.ff.lwjgl3.entity.BaseEntity;
import com.arcralius.ff.lwjgl3.entity.FoodEntity;

public class FoodFactory implements Entity_abstract_factory {
    private String id, texturePath, foodType;
    private float x, y, width, height;

    public FoodFactory() {
        this.id = "";
        this.texturePath = "";
        this.foodType = "";
        this.x = 0;
        this.y = 0;
        this.height = 0;
        this.width = 0;
    }

    public FoodFactory(String id, String foodType, String texturePath, float x, float y, float width, float height) {
        this.id = id;
        this.texturePath = texturePath;
        this.foodType = foodType;
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    @Override
    public BaseEntity createEntity() {
        return new FoodEntity(this.texturePath, this.x, this.y, this.id, this.width, this.height, this.foodType);
    }
}

