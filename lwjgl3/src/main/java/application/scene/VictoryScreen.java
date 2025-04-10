package application.scene;

import application.Gameplay_Specific_scene;
import engine.input_output.IO_Controller;
import engine.movement.MovementController;
import engine.entity.FoodEntity;
import application.Entity.abstractFactory.FoodFactory;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import engine.scene.BaseScreen;
import engine.scene.SceneController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class VictoryScreen extends BaseScreen {
    private final SceneController sceneController;
    private final Texture backgroundTexture; // Change to Texture
    private Sprite backgroundSprite; // Add Sprite
    private final BitmapFont font;
    private GlyphLayout glyphLayout;
    private boolean backgroundDrawn = false;
    private float lastViewportWidth = 0;
    private float lastViewportHeight = 0;
    private SpriteBatch backgroundBatch; // Separate batch for background

    // Added for falling fruits
    private final List<FoodEntity> fallingFruits = new ArrayList<>();
    private final String[] foodTextures = {
        "apple.png", "Banana.png", "Blueberry.png", "Cabbage.png", "Carrot.png",
        "Cauliflower.png", "Cherry.png", "Corn.png", "Cucumber.png", "Eggplant.png",
        "grapes.png", "Leek.png", "Lemon.png", "Onion.png", "Orange.png", "Paprika.png",
        "Pear.png", "Pineapple.png", "Plum.png", "Potato.png", "Pumpkin.png",
        "Raspberry.png", "Strawberry.png", "Tomato.png", "Watermelon.png"
    };
    private final Random random = new Random();
    private long lastSpawnTime = TimeUtils.nanoTime();

    public VictoryScreen(IO_Controller ioController, SceneController sceneController) {
        super(ioController);
        this.sceneController = sceneController;

        ioController.getAudioManager().stopMusic("victory_music");
        ioController.getAudioManager().playMusic("victory_music", true);

        this.backgroundTexture = new Texture("menuBackground.png");
        this.backgroundSprite = new Sprite(backgroundTexture);
        this.font = new BitmapFont();
        this.glyphLayout = new GlyphLayout();
        this.backgroundBatch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        // Force clear color and clear buffer
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        update(delta);
        camera.update();

        // Draw background using separate batch
        backgroundBatch.setProjectionMatrix(camera.combined);
        backgroundBatch.begin();
        if (!backgroundDrawn) {
            backgroundSprite.setSize((int)viewport.getWorldWidth(), (int)viewport.getWorldHeight()); // Integer size
            backgroundDrawn = true;
            lastViewportWidth = viewport.getWorldWidth();
            lastViewportHeight = viewport.getWorldHeight();
        }
        backgroundSprite.draw(backgroundBatch);
        backgroundBatch.end();

        // Draw text and fruits using main batch
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        draw();
        batch.end();
    }

    @Override
    protected void update(float delta) {
        handleInput();
        ioController.update();

        spawnFruitIfNeeded();
        for (Iterator<FoodEntity> it = fallingFruits.iterator(); it.hasNext(); ) {
            FoodEntity fruit = it.next();
            fruit.setY(fruit.getY() - 200 * delta);
            if (fruit.getY() + fruit.getHeight() < 0) {
                it.remove();
            }
        }
    }

    @Override
    protected void draw() {
        batch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        font.draw(batch, "Victory!", viewport.getWorldWidth() / 2 - 50, viewport.getWorldHeight() / 2);
        font.draw(batch, "Press ESC to Exit or R to Restart", viewport.getWorldWidth() / 2 - 100, viewport.getWorldHeight() / 2 - 30);

        for (FoodEntity fruit : fallingFruits) {
            batch.draw(fruit.getTextureObject(), fruit.getX(), fruit.getY(), fruit.getWidth(), fruit.getHeight());
        }
    }

    private void handleInput() {
        if (ioController.getInputManager().isKeyJustReleased(Input.Keys.R)) {
            sceneController.changeScreen(new Gameplay_Specific_scene(ioController, sceneController, new MovementController(ioController, camera)));
        }
        if (ioController.getInputManager().isKeyJustReleased(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    private void spawnFruitIfNeeded() {
        if (TimeUtils.nanoTime() - lastSpawnTime > 100_000_000) {
            float x = random.nextFloat() * (viewport.getWorldWidth() - 32);
            String textureName = foodTextures[random.nextInt(foodTextures.length)];
            String fullPath = "healthy food/" + textureName;

            FoodFactory factory = new FoodFactory("fruit", "food", fullPath, x, viewport.getWorldHeight(), 32, 32);
            FoodEntity fruit = (FoodEntity) factory.createEntity();
            fallingFruits.add(fruit);
            lastSpawnTime = TimeUtils.nanoTime();
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        if (lastViewportWidth != viewport.getWorldWidth() || lastViewportHeight != viewport.getWorldHeight()) {
            backgroundDrawn = false;
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        backgroundTexture.dispose();
        font.dispose();
        backgroundBatch.dispose();
    }
}
