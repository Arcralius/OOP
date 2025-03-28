package application.Entity.component;

import engine.scene.BaseScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera; // Import OrthographicCamera

public class FoodInfoDisplay implements ScreenComponent {
    private String foodType;
    private Texture foodImage;
    private float displayTime;
    private float maxDisplayTime = 3.0f;
    private boolean isActive = false;
    private BaseScreen screen;
    private OrthographicCamera camera; // Add camera field
    private SpriteBatch foodInfoBatch; // Separate batch

    public FoodInfoDisplay() {
        System.out.println("FoodInfoDisplay created");
        camera = new OrthographicCamera(); // Initialize camera
        foodInfoBatch = new SpriteBatch(); // Initialize separate batch
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
                camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                camera.update();
                foodInfoBatch.setProjectionMatrix(camera.combined); // Use separate batch

                float scaleX = camera.viewportWidth / 800f;
                float scaleY = camera.viewportHeight / 600f;
                float scale = Math.min(scaleX, scaleY);

                float baseWidth = 600;
                float baseHeight = 160;

                float scaledWidth = baseWidth * scale;
                float scaledHeight = baseHeight * scale;

                float imgX = camera.viewportWidth / 2f - scaledWidth / 2f;
                float imgY = 50 * scale; // adjust to control dist from bottom of screen

                foodInfoBatch.begin(); // Begin separate batch
                foodInfoBatch.draw(foodImage, imgX, imgY, scaledWidth, scaledHeight);
                foodInfoBatch.end(); // End separate batch

                System.out.println("FoodInfoDisplay rendered: " + foodType +
                    ", Texture ID: " + foodImage.getTextureObjectHandle() +
                    ", Batch: " + foodInfoBatch); // Log batch instance

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
        if (foodInfoBatch != null) {
            foodInfoBatch.dispose(); // Dispose separate batch
        }
    }

    public boolean isActive() {
        return isActive;
    }
}
