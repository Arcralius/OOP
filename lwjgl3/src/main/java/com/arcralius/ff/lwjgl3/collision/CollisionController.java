package com.arcralius.ff.lwjgl3.collision;

import com.arcralius.ff.lwjgl3.entity.BaseEntity;
import com.arcralius.ff.lwjgl3.entity.NonPlayableEntity;
import com.arcralius.ff.lwjgl3.scene.GameplayScreen;
import com.arcralius.ff.lwjgl3.scene.EndScreen;
import com.arcralius.ff.lwjgl3.scene.SceneController;
import com.arcralius.ff.lwjgl3.input_output.IO_Controller; // Import IO_Controller

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

    public void checkCollisions(BaseEntity player, List<BaseEntity> entityList) {
        for (BaseEntity npc : entityList) {
            if (npc instanceof NonPlayableEntity) {
                if (player.getBoundary().overlaps(npc.getBoundary())) {
                    handleCollision(player, npc);
                }
            }
        }
    }

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
}
