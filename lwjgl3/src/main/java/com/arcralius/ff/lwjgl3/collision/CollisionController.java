package com.arcralius.ff.lwjgl3.collision;

import com.arcralius.ff.lwjgl3.entity.BaseEntity;
import com.arcralius.ff.lwjgl3.entity.FoodEntity;
import com.arcralius.ff.lwjgl3.entity.NonPlayableEntity;
import com.arcralius.ff.lwjgl3.entity.PlayableEntity;
import com.arcralius.ff.lwjgl3.specific_scene.Gameplay_Specific_scene;
import com.arcralius.ff.lwjgl3.scene.GameplayScreen;
import com.arcralius.ff.lwjgl3.scene.EndScreen;
import com.arcralius.ff.lwjgl3.scene.SceneController;
import com.arcralius.ff.lwjgl3.input_output.IO_Controller;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
            Iterator<BaseEntity> iterator = entityList.iterator();
            while (iterator.hasNext()) {
                BaseEntity entity = iterator.next();
                if (player != entity && player.getBoundary().overlaps(entity.getBoundary())) {


                    // Check if it's a food entity
                    if (entity instanceof FoodEntity) {
                        System.out.println("Food collision detected");
                        handleFoodCollection(player, (FoodEntity) entity, iterator); // Pass iterator
                    } else if (entity instanceof NonPlayableEntity) {
                        // Handle regular NPC collision
                        handleCollision((PlayableEntity) player, entity);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error in checkCollisions: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleCollision(PlayableEntity a, BaseEntity b) {
        String aString = a.getId();
        String bString = b.getId();

        // Check if the player is invincible
        if (a.canTakeDamage()) {
            int damage = ThreadLocalRandom.current().nextInt(10, 41); // Generates a random number between 10 and 45
            a.setHP(a.getHP() - damage);
            a.registerHit(); // Update last hit time
            ioController.getAudioManager().playSoundEffect("hit_sound");

            System.out.println("Player took " + damage + " damage. HP = " + a.getHP());
            gameplayScreen.displayCollisionMessage("Invincible!");

            if (a.getHP() <= 0) {
                sceneController.changeScreen(new EndScreen(ioController, sceneController));
            }
            if(damage > 30){
                gameplayScreen.displayCollisionMessage("Invincible! Critical Hit!");
            }
        }
    }

    private void handleFoodCollection(BaseEntity player, FoodEntity food, Iterator<BaseEntity> iterator) { // Added iterator parameter
        try {
            if (gameplayScreen instanceof Gameplay_Specific_scene) {
                Gameplay_Specific_scene exampleScreen = (Gameplay_Specific_scene) gameplayScreen;
                String foodType = food.getFoodType() != null ? food.getFoodType() : "unknown food";
                exampleScreen.showFoodInfo(foodType);
                exampleScreen.incrementFoodCollected();
            }

            ioController.getAudioManager().playSoundEffect("item_collected");

            // Safely remove the food entity using the iterator
            iterator.remove();
            gameplayScreen.getEntityController().removeEntity(food);
        } catch (Exception e) {
            System.err.println("Error in handleFoodCollection: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
