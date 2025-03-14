package com.arcralius.ff.lwjgl3.entity;

import java.util.HashMap;
import java.util.Map;

public class FoodData {
    private static final Map<String, String> foodTextures = new HashMap<>();
    private static final Map<String, String> nutritionalInfo = new HashMap<>();

    static {
        // Initialize food data

        // Apple
        foodTextures.put("apple", "healthy food/Apple_Green.png");
        nutritionalInfo.put("apple", "Rich in fiber and vitamin C.\nHelps with digestion and immune health.");

        // Carrot
        foodTextures.put("carrot", "healthy food/Carrot.png");
        nutritionalInfo.put("carrot", "High in beta-carotene.\nGood for eye health and immunity.");

        // Banana
        foodTextures.put("banana", "healthy food/Banana_Peeled.png");
        nutritionalInfo.put("banana", "Contains potassium and magnesium.\nHelps with muscle recovery.");

        // Broccoli
        foodTextures.put("broccoli", "healthy food/Cabbage.png");
        nutritionalInfo.put("broccoli", "Rich in vitamins K and C.\nSupports bone health and immunity.");

        // Spinach
        foodTextures.put("spinach", "healthy food/Corn.png");
        nutritionalInfo.put("spinach", "High in iron and calcium.\nSupports energy levels and bone health.");
    }

    public static String getTexturePath(String foodType) {
        return foodTextures.getOrDefault(foodType, "food/default.png");
    }

    public static String getNutritionalInfo(String foodType) {
        return nutritionalInfo.getOrDefault(foodType, "A healthy food item.");
    }

    public static String[] getAllFoodTypes() {
        return foodTextures.keySet().toArray(new String[0]);
    }
}
