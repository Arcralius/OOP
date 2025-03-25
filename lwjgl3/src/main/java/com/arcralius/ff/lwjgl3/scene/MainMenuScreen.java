package com.arcralius.ff.lwjgl3.scene;

import com.arcralius.ff.lwjgl3.specific_scene.Gameplay_Specific_scene;
import com.arcralius.ff.lwjgl3.input_output.IO_Controller;
import com.arcralius.ff.lwjgl3.movement.MovementController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen extends BaseScreen {
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
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
        // Update DisplayManager resolution when menu is loaded
        ioController.getDisplayManager().setResolution(Gdx.graphics.getWidth() + "x" + Gdx.graphics.getHeight());

        // Initialize UI handling
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load the background texture
        backgroundTexture = new Texture(Gdx.files.internal("menuBackground.png"));

        // Initialize background sprites for scrolling
        backgroundSprite1 = new Sprite(backgroundTexture);
        backgroundSprite2 = new Sprite(backgroundTexture);
        backgroundSprite1.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backgroundSprite2.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Position the second sprite to the right of the first one
        backgroundX2 = Gdx.graphics.getWidth();

        // Instantiate the UIComponentFactory with the atlas and font paths.
        uiFactory = new UIComponentFactory("fonts/ChangaOneRegular.ttf");

        // Initialize UI elements
        setupUI();
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
                sceneController.changeScreen(new Gameplay_Specific_scene(ioController, sceneController, new MovementController(ioController, camera)));
            }
        });

        // Create Settings Button using the factory
        TextButton buttonSettings = uiFactory.createTextButton("Settings", UIComponentFactory.FRENCH_BEIGE, UIComponentFactory.boxDrawable);
        buttonSettings.pad(20, 50, 20, 50);
        buttonSettings.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
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
        // Draw the scrolling background
        batch.begin();
        backgroundSprite1.setPosition(backgroundX1, 0);
        backgroundSprite2.setPosition(backgroundX2, 0);
        backgroundSprite1.draw(batch);
        backgroundSprite2.draw(batch);
        batch.end();

        // Draw UI elements
        stage.draw();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        // Scroll the background
        float SCROLL_SPEED = 50f;
        backgroundX1 -= SCROLL_SPEED * delta;
        backgroundX2 -= SCROLL_SPEED * delta;

        // Reset background positions if they go off-screen
        if (backgroundX1 + backgroundSprite1.getWidth() <= 0) {
            backgroundX1 = backgroundX2 + backgroundSprite2.getWidth();
        }
        if (backgroundX2 + backgroundSprite2.getWidth() <= 0) {
            backgroundX2 = backgroundX1 + backgroundSprite1.getWidth();
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
