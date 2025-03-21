package com.arcralius.ff.lwjgl3.collision;

import com.arcralius.ff.lwjgl3.entity.BaseEntity;
import com.arcralius.ff.lwjgl3.entity.FoodEntity;
import com.arcralius.ff.lwjgl3.entity.NonPlayableEntity;
import com.arcralius.ff.lwjgl3.specific_scene.Gameplay_Specific_scene;
import com.arcralius.ff.lwjgl3.scene.GameplayScreen;
import com.arcralius.ff.lwjgl3.scene.EndScreen;
import com.arcralius.ff.lwjgl3.scene.SceneController;
import com.arcralius.ff.lwjgl3.input_output.IO_Controller;

import java.util.List;

public class CollisionController implements CollisionInterface {
    private GameplayScreen gameplayScreen;
    private IO_Controller ioController;
    private SceneController sceneController;


    public CollisionController(IO_Controller ioController, GameplayScreen gameplayScreen, SceneController sceneController) {
        this.ioController = ioController;
        this.gameplayScreen = gameplayScreen;
        this.sceneController = sceneController;
    }

    @Override
    public void checkCollisions(BaseEntity player, List<BaseEntity> entityList) {
        try {
            for (BaseEntity entity : entityList) {
                if (player != entity && player.getBoundary().overlaps(entity.getBoundary())) {
                    // Print debug info
                    System.out.println("Collision detected with entity type: " + entity.getClass().getSimpleName());

                    // Check if it's a food entity
                    if (entity instanceof FoodEntity) {
                        System.out.println("Food collision detected");
                        handleFoodCollection(player, (FoodEntity) entity);
                    } else if (entity instanceof NonPlayableEntity) {
                        // Handle regular NPC collision
                        handleCollision(player, entity);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error in checkCollisions: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void handleCollision(BaseEntity a, BaseEntity b) {
        String aString = a.getId();
        String bString = b.getId();
        System.out.println("Collision detected between " + aString + " and " + bString);
        gameplayScreen.displayCollisionMessage("Collided with " + bString + "!");

        System.out.println("Entity ID: " + b.getId()); // Debugging
        if ("enemy 3".equals(bString)) {
            System.out.println("Collision with entity 3 detected! Switching to EndScreen...");
            sceneController.changeScreen(new EndScreen(ioController, sceneController));
        }
    }

    private void handleFoodCollection(BaseEntity player, FoodEntity food) {
        try {
            if (gameplayScreen instanceof Gameplay_Specific_scene) {
                Gameplay_Specific_scene exampleScreen = (Gameplay_Specific_scene) gameplayScreen;
                String foodType = food.getFoodType() != null ? food.getFoodType() : "unknown food";
                exampleScreen.showFoodInfo(foodType);
                exampleScreen.incrementFoodCollected();
            }

            ioController.getAudioManager().playSoundEffect("item_collected");

            // Safely remove the food entity
            gameplayScreen.getEntityController().removeEntity(food);
        } catch (Exception e) {
            System.err.println("Error in handleFoodCollection: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
