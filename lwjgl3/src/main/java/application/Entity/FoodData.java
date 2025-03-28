package application.Entity;

import java.util.HashMap;
import java.util.Map;

public class FoodData {
    private static final Map<String, String> foodTextures = new HashMap<>();
    private static final Map<String, String> collectionMessages = new HashMap<>();

    static {
        // Initialize food data

        // Apple
        foodTextures.put("apple", "healthy food/apple.png");
        collectionMessages.put("apple", " An apple a day keeps the doctor away! Packed with fiber and antioxidants for heart health.");

        // Banana
        foodTextures.put("banana", "healthy food/Banana.png");
        collectionMessages.put("banana", "Going bananas over potassium! Great for muscle recovery and blood pressure control.");

        // Blueberry
        foodTextures.put("blueberry", "healthy food/Blueberry.png");
        collectionMessages.put("blueberry", "Berry impressive brain food! These antioxidant powerhouses support cognitive function.");

        // Cabbage
        foodTextures.put("cabbage", "healthy food/Cabbage.png");
        collectionMessages.put("cabbage", "Cabbage patch of vitamin K! Excellent for bone health and blood clotting.");

        // Carrot
        foodTextures.put("carrot", "healthy food/Carrot.png");
        collectionMessages.put("carrot", "I carrot believe how much vitamin A is in here! Fantastic for eye health and immunity.");

        // Cauliflower
        foodTextures.put("cauliflower", "healthy food/Cauliflower.png");
        collectionMessages.put("cauliflower", "Cauli me impressed! Rich in choline for brain development and metabolism.");

        // Cherry
        foodTextures.put("cherry", "healthy food/Cherry.png");
        collectionMessages.put("cherry", "Cherry nice anti-inflammatory properties! Helps reduce muscle pain and improve sleep.");

        // Corn
        foodTextures.put("corn", "healthy food/Corn.png");
        collectionMessages.put("corn", "A-maize-ing source of fiber! Promotes digestive health and sustained energy.");

        // Cucumber
        foodTextures.put("cucumber", "healthy food/Cucumber.png");
        collectionMessages.put("cucumber", "Cool as a cucumber with 95% water content! Excellent for hydration and skin health.");

        // Eggplant
        foodTextures.put("eggplant", "healthy food/Eggplant.png");
        collectionMessages.put("eggplant", "Egg-cellent source of antioxidants! The purple skin contains nasunin for brain protection.");

        // Grapes
        foodTextures.put("grapes", "healthy food/grapes.png");
        collectionMessages.put("grapes", "Grape news for heart health! Resveratrol helps lower blood pressure and cholesterol.");

        // Leek
        foodTextures.put("leek", "healthy food/Leek.png");
        collectionMessages.put("leek", "Take a leek at these prebiotic benefits! Great for gut health and digestion.");

        // Lemon
        foodTextures.put("lemon", "healthy food/Lemon.png");
        collectionMessages.put("lemon", "When life gives you lemons, get vitamin C! Boosts immunity and skin elasticity.");

        // Onion
        foodTextures.put("onion", "healthy food/Onion.png");
        collectionMessages.put("onion", "Brings tears to my eyes how many antioxidants are in here! Supports heart health.");

        // Orange
        foodTextures.put("orange", "healthy food/Orange.png");
        collectionMessages.put("orange", "Orange you glad to get all this vitamin C? Supports immune function and collagen production.");

        // Paprika
        foodTextures.put("paprika", "healthy food/Paprika.png");
        collectionMessages.put("paprika", "Spice up your immunity! Rich in capsaicin to reduce inflammation and boost metabolism.");

        // Pear
        foodTextures.put("pear", "healthy food/Pear.png");
        collectionMessages.put("pear", "We make a great pear! Full of soluble fiber for digestive health and cholesterol control.");

        // Pineapple
        foodTextures.put("pineapple", "healthy food/Pineapple.png");
        collectionMessages.put("pineapple", "Pine for this bromelain-rich fruit! Reduces inflammation and aids protein digestion.");

        // Plum
        foodTextures.put("plum", "healthy food/Plum.png");
        collectionMessages.put("plum", "Plum-believable source of antioxidants! Supports heart health and bone strength.");

        // Potato
        foodTextures.put("potato", "healthy food/Potato.png");
        collectionMessages.put("potato", "I'm rooting for potatoes and their potassium! Great for nerve function and blood pressure.");

        // Pumpkin
        foodTextures.put("pumpkin", "healthy food/Pumpkin.png");
        collectionMessages.put("pumpkin", "Pump(kin) up your vitamin A intake! Excellent for eye health and immune function.");

        // Raspberry
        foodTextures.put("raspberry", "healthy food/Raspberry.png");
        collectionMessages.put("raspberry", "Rasp-berry good source of fiber! Helps with weight management and blood sugar control.");

        // Strawberry
        foodTextures.put("strawberry", "healthy food/Strawberry.png");
        collectionMessages.put("strawberry", "Berry beneficial for heart health! Rich in antioxidants and vitamin C.");

        // Tomato
        foodTextures.put("tomato", "healthy food/Tomato.png");
        collectionMessages.put("tomato", "Tomato, to-mah-to, either way it's full of lycopene! Supports prostate health and reduces cancer risk.");

        // Watermelon
        foodTextures.put("watermelon", "healthy food/Watermelon.png");
        collectionMessages.put("watermelon", "Water you waiting for? Stay hydrated with 92% water content plus heart-healthy citrulline!");


    }

    public static String getTexturePath(String foodType) {
        return foodTextures.getOrDefault(foodType, "healthy food/default.png");
    }

    public static String getCollectionMessage(String foodType) {
        return collectionMessages.getOrDefault(foodType, "Collected " + foodType + "!");
    }

    public static String[] getAllFoodTypes() {
        return foodTextures.keySet().toArray(new String[0]);
    }
}
