package application.scene;

import application.Gameplay_Specific_scene;
import engine.input_output.IO_Controller;
import engine.movement.MovementController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.ScreenUtils;
import engine.scene.BaseScreen;
import engine.scene.SceneController;

public class MainMenuScreen extends BaseScreen {
    private Stage stage;
    private final SceneController sceneController;
    // Background scrolling
    private Texture backgroundTexture;
    private Sprite backgroundSprite1, backgroundSprite2;
    private float backgroundX1 = 0, backgroundX2;
    private UIComponentFactory uiFactory;

    public MainMenuScreen(IO_Controller ioController, SceneController sceneController) {
        super(ioController);
        this.sceneController = sceneController;

        // Update DisplayManager with the current screen
        ioController.getDisplayManager().setCurrentScreen("MainMenuScreen");

        // Ensure the correct music is playing
        if (!"main_menu_music".equals(ioController.getAudioManager().getCurrentTrack())) {
            ioController.getAudioManager().playMusic("main_menu_music", true);
        }
    }

    @Override
    public void show() {
        // Initialize UI handling
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        // Load the background texture
        backgroundTexture = new Texture(Gdx.files.internal("menuBackground.png"));

        // Initialize background sprites for scrolling
        backgroundSprite1 = new Sprite(backgroundTexture);
        backgroundSprite2 = new Sprite(backgroundTexture);

        // Instantiate the UIComponentFactory with the atlas and font paths.
        uiFactory = new UIComponentFactory("fonts/ChangaOneRegular.ttf");

        // Initialize UI elements
        setupUI();

        // Scale the background sprites after they are created.
        float scaleX = viewport.getWorldWidth() / backgroundSprite1.getWidth();
        float scaleY = viewport.getWorldHeight() / backgroundSprite1.getHeight();
        float scale = Math.max(scaleX, scaleY);

        backgroundSprite1.setSize(backgroundSprite1.getWidth() * scale, backgroundSprite1.getHeight() * scale);
        backgroundSprite2.setSize(backgroundSprite2.getWidth() * scale, backgroundSprite2.getHeight() * scale);

        // Position the second sprite to the right of the first one, after scaling.
        backgroundX2 = backgroundSprite1.getWidth();
    }

    private void setupUI() {
        Table table = new Table();
        table.setFillParent(true); // Properly centers elements

        // Create Play Button using the factory
        ImageButton buttonPlay = uiFactory.createImageButton("play_button.png");
        buttonPlay.pad(20, 50, 20, 50);
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                sceneController.changeScreen(new Gameplay_Specific_scene(ioController, sceneController, new MovementController(ioController, camera)));
            }
        });

        // Create Settings Button using the factory
        TextButton buttonSettings = uiFactory.createTextButton("Settings", UIComponentFactory.FRENCH_BEIGE, UIComponentFactory.boxDrawable);
        buttonSettings.pad(20, 50, 20, 50);
        buttonSettings.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                sceneController.changeScreen(new SettingScreen(ioController, sceneController));
            }
        });

        // Create Quit Button using the factory
        TextButton buttonQuit = uiFactory.createTextButton("Quit", UIComponentFactory.FRENCH_BEIGE, UIComponentFactory.boxDrawable);
        buttonQuit.pad(20, 50, 20, 50);
        buttonQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        // Add buttons to the table
        table.row().pad(20);
        table.add(buttonPlay).fillX().uniformX();
        table.row().pad(20);
        table.add(buttonSettings).fillX().uniformX();
        table.row().pad(20);
        table.add(buttonQuit).fillX().uniformX();

        stage.addActor(table);
    }

    @Override
    protected void update(float delta) {
        stage.act(delta);
    }

    @Override
    protected void draw() {
        batch.begin();

        float scaleX = viewport.getWorldWidth() / backgroundSprite1.getWidth();
        float scaleY = viewport.getWorldHeight() / backgroundSprite1.getHeight();
        float scale = Math.max(scaleX, scaleY); // Scale to fill the larger dimension

        backgroundSprite1.setSize(backgroundSprite1.getWidth() * scale, backgroundSprite1.getHeight() * scale);
        backgroundSprite2.setSize(backgroundSprite2.getWidth() * scale, backgroundSprite2.getHeight() * scale);

        // Calculate the scaled width for positioning
        float scaledWidth = backgroundSprite1.getWidth();

        backgroundSprite1.setPosition(backgroundX1, 0);
        backgroundSprite2.setPosition(backgroundX2, 0);

        backgroundSprite1.draw(batch);
        backgroundSprite2.draw(batch);

        batch.end();
        stage.draw();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        float SCROLL_SPEED = 50f;
        backgroundX1 -= SCROLL_SPEED * delta;
        backgroundX2 -= SCROLL_SPEED * delta;

        // Calculate the scaled width for resetting positions
        float scaledWidth = backgroundSprite1.getWidth();

        // Reset background positions if they go off-screen
        if (backgroundX1 + scaledWidth <= 0) {
            backgroundX1 = backgroundX2 + scaledWidth;
        }
        if (backgroundX2 + scaledWidth <= 0) {
            backgroundX2 = backgroundX1 + scaledWidth;
        }

        draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        uiFactory.dispose();  // Dispose the Skin and Font managed by the factory.

        backgroundTexture.dispose();
    }
}
