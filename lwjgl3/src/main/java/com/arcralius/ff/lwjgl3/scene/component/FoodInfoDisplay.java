package com.arcralius.ff.lwjgl3.scene.component;

import com.arcralius.ff.lwjgl3.scene.BaseScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FoodInfoDisplay implements ScreenComponent {
    private String foodType;
    private Texture foodImage;
    private float displayTime;
    private float maxDisplayTime = 3.0f; // Display info by set time
    private boolean isActive = false;
    private BaseScreen screen;

    public FoodInfoDisplay() {
        System.out.println("FoodInfoDisplay created");
    }

    @Override
    public void initialize(BaseScreen screen) {
        this.screen = screen;
        System.out.println("FoodInfoDisplay initialized with screen: " + screen.getClass().getSimpleName());
    }

    public void showInfo(String foodType) {
        System.out.println("showInfo called for food type: " + foodType);
        this.foodType = foodType;
        this.displayTime = 0;
        this.isActive = true;

        // Load the food card image
        try {
            // Dispose previous image if exists
            if (foodImage != null) {
                foodImage.dispose();
            }

            // Load the new image with _info suffix
            String imagePath = "food_cards/" + foodType + "_info.png";
            System.out.println("Attempting to load image from: " + imagePath);

            if (!Gdx.files.internal(imagePath).exists()) {
                System.err.println("ERROR: Image file not found at: " + imagePath);

                // Try a direct path as fallback
                imagePath = foodType + "_info.png";
                System.out.println("Trying fallback path: " + imagePath);

                if (!Gdx.files.internal(imagePath).exists()) {
                    System.err.println("ERROR: Fallback image not found either");
                    isActive = false;
                    return;
                }
            }

            foodImage = new Texture(Gdx.files.internal(imagePath));
            System.out.println("Successfully loaded food info image: " + imagePath +
                " (dimensions: " + foodImage.getWidth() + "x" + foodImage.getHeight() + ")");
        } catch (Exception e) {
            System.err.println("Error loading food info image: " + e.getMessage());
            e.printStackTrace();
            foodImage = null;
            isActive = false;
        }
    }

    @Override
    public void update(float delta) {
        if (isActive) {
            System.out.println("FoodInfoDisplay updating. Time: " + displayTime + "/" + maxDisplayTime +
                ", Food: " + foodType + ", Image loaded: " + (foodImage != null));

            displayTime += delta;
            if (displayTime >= maxDisplayTime) {
                System.out.println("FoodInfoDisplay deactivating after " + displayTime + " seconds");
                isActive = false;

                // Dispose the image when done displaying
                if (foodImage != null) {
                    foodImage.dispose();
                    foodImage = null;
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (isActive && foodImage != null) {
            try {
                // Fixed size for all food card images
                float fixedWidth = 600;  // Width in pixels
                float fixedHeight = 160; // Height in pixels

                // Center the image
                float imgX = Gdx.graphics.getWidth()/2f - fixedWidth/2f;
                float imgY = Gdx.graphics.getHeight()/2f - fixedHeight/2f;

                // Draw with fixed size
                batch.draw(foodImage, imgX, imgY, fixedWidth, fixedHeight);

            } catch (Exception e) {
                System.err.println("Error rendering food info: " + e.getMessage());
                e.printStackTrace();
                isActive = false;
            }
        }
    }

    @Override
    public void dispose() {
        System.out.println("FoodInfoDisplay disposing resources");
        if (foodImage != null) {
            foodImage.dispose();
            foodImage = null;
        }
    }

    public boolean isActive() {
        return isActive;
    }
}
