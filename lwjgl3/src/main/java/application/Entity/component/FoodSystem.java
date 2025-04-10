package application.Entity.component;

import engine.entity.BaseEntity;
import engine.entity.EntityController;
import application.Entity.GameEntityBuilder;
import application.Entity.FoodData;
import engine.scene.BaseScreen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class FoodSystem implements ScreenComponent {
    private EntityController entityController;
    private BaseScreen screen;
    private List<String> activeFoodIds;
    private int totalFoodCount;
    private float mapWidth;
    private float mapHeight;

    public FoodSystem(EntityController entityController, float mapWidth, float mapHeight, int totalFoodCount) {
        this.entityController = entityController;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.totalFoodCount = totalFoodCount;
        this.activeFoodIds = new ArrayList<>();
    }

    // Add this method to FoodSystem
    public int getTotalFoodCount() {
        return totalFoodCount;
    }

    @Override
    public void initialize(BaseScreen screen) {
        this.screen = screen;

        // Generate random food items when the system initializes
        spawnRandomFoodItems();
    }

    @Override
    public void update(float delta) {
        // No periodic spawning - all food is spawned at initialization
    }

    @Override
    public void render(SpriteBatch batch) {
        // No rendering needed here
    }

    @Override
    public void dispose() {
        // Nothing to dispose
    }

//    private void spawnRandomFoodItems() {
//        String[] foodTypes = FoodData.getAllFoodTypes();
//        List<Vector2> occupiedPositions = new ArrayList<>();
//        float minDistance = 80f; // Minimum distance between food items
//
//        for (int i = 0; i < totalFoodCount; i++) {
//            String foodType = foodTypes[MathUtils.random(foodTypes.length - 1)];
//
//            // Try to find a valid position (not too close to other items)
//            float x, y;
//            boolean validPosition = false;
//            int attempts = 0;
//
//            do {
//                x = MathUtils.random(100, mapWidth - 100);
//                y = MathUtils.random(100, mapHeight - 100);
//
//                // Check distance from other food items
//                validPosition = true;
//                for (Vector2 pos : occupiedPositions) {
//                    float distance = Vector2.dst(x, y, pos.x, pos.y);
//                    if (distance < minDistance) {
//                        validPosition = false;
//                        break;
//                    }
//                }
//
//                attempts++;
//            } while (!validPosition && attempts < 50); // Limit attempts to avoid infinite loops
//
//            if (validPosition) {
//                // Create food entity and store position
//                String id = "food_" + foodType + "_" + i;
//
//                FoodEntity food = entityController.addFoodEntity(
//                    FoodData.getTexturePath(foodType), x, y, id, 32, 32, foodType
//                );
//
//                if (food != null) {
//                    activeFoodIds.add(id);
//                    occupiedPositions.add(new Vector2(x, y));
//                    System.out.println("Spawned " + foodType + " at position (" + x + ", " + y + ")");
//                }
//            }
//        }
//
//        System.out.println("Food system initialized - Spawned " + occupiedPositions.size() + " food items");
//    }

    private void spawnRandomFoodItems() {
        String[] foodTypes = FoodData.getAllFoodTypes();
        List<Vector2> occupiedPositions = new ArrayList<>();
        float minDistance = 80f;

        for (int i = 0; i < totalFoodCount; i++) {
            String foodType = foodTypes[MathUtils.random(foodTypes.length - 1)];

            float x, y;
            boolean validPosition = false;
            int attempts = 0;

            do {
                x = MathUtils.random(100, mapWidth - 100);
                y = MathUtils.random(100, mapHeight - 100);
                validPosition = true;

                for (Vector2 pos : occupiedPositions) {
                    if (Vector2.dst(x, y, pos.x, pos.y) < minDistance) {
                        validPosition = false;
                        break;
                    }
                }

                attempts++;
            } while (!validPosition && attempts < 50);

            if (validPosition) {
                String id = "food_" + foodType + "_" + i;

                BaseEntity food = GameEntityBuilder.createFoodEntity(id, foodType, FoodData.getTexturePath(foodType), x, y, 32, 32);

                if (food != null) {
                    entityController.addEntity(food);
                    activeFoodIds.add(id);
                    occupiedPositions.add(new Vector2(x, y));
                    System.out.println("Spawned " + foodType + " at position (" + x + ", " + y + ")");
                }
            }
        }

        System.out.println("Food system initialized - Spawned " + occupiedPositions.size() + " food items");
    }




    public void foodCollected(String foodId) {
        if (activeFoodIds.remove(foodId)) {
            System.out.println("Food collected: " + foodId + " - Remaining food: " + activeFoodIds.size());
        }
    }
}
