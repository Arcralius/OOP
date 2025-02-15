package com.arcralius.ff.lwjgl3.collision;


    import com.arcralius.ff.lwjgl3.entity.BaseEntity; // Adjust package path as needed
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import java.util.List;


public class CollisionController {
    public void checkCollisions(List<BaseEntity> entityList) {
        for (int i = 0; i < entityList.size(); i++) {
            for (int j = i + 1; j < entityList.size(); j++) {
                BaseEntity a = entityList.get(i);
                BaseEntity b = entityList.get(j);

                if (a.getBoundary().overlaps(b.getBoundary())) { // Use overlaps() instead
                    handleCollision(a, b);
                }
            }
        }
    }

    private void handleCollision(BaseEntity a, BaseEntity b) {
        System.out.println("Collision detected between " + a.getId() + " and " + b.getId());
        // Implement additional collision handling logic (e.g., health reduction, bounce effect)
    }
}
