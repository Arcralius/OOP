package com.arcralius.ff.lwjgl3.collision;


import com.arcralius.ff.lwjgl3.entity.BaseEntity;
import com.arcralius.ff.lwjgl3.entity.NonPlayableEntity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import java.util.List;


public class CollisionController {
    public void checkCollisions(BaseEntity player, List<BaseEntity> entityList) {
        for (int i = 0; i < entityList.size(); i++) {
            BaseEntity npc = entityList.get(i);

            if (npc instanceof NonPlayableEntity) {
                if (player.getBoundary().overlaps(npc.getBoundary())) {
                    handleCollision(player, npc);
                }
            }
        }
    }

    private void handleCollision(BaseEntity a, BaseEntity b) {
        System.out.println("Collision detected between " + a.getId() + " and " + b.getId());
        // Implement additional collision handling logic (e.g., health reduction, bounce effect)
    }
}
