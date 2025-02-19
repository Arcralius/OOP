package com.arcralius.ff.lwjgl3.scene;

import com.arcralius.ff.lwjgl3.example.GameplayScreen_examples;
import com.arcralius.ff.lwjgl3.input_output.AudioManager;
import com.arcralius.ff.lwjgl3.movement.MovementController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
    private final AudioManager audioManager;

    // Background scrolling
    private Texture backgroundTexture;
    private Sprite backgroundSprite1, backgroundSprite2;
    private float backgroundX1 = 0, backgroundX2;

    public MainMenuScreen(SceneController sceneController, AudioManager audioManager) {
        this.sceneController = sceneController;
        this.audioManager = audioManager;

        // Load and start music
        this.audioManager.playMusic("main_menu_music", true);
    }


    @Override
    public void show() {
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

        // Initialize UI elements
        setupUI();
    }

    private void setupUI() {
        atlas = new TextureAtlas(Gdx.files.internal("button.atlas"));
        skin = new Skin(atlas);

        Table table = new Table();
        table.setFillParent(true); // Properly centers elements

        BitmapFont whiteFont = new BitmapFont(Gdx.files.internal("white.fnt"), false);

        // Define button style with hover effect
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("menacing");
        textButtonStyle.over = skin.getDrawable("menacing2");
        textButtonStyle.font = whiteFont;

        // Create Play Button
        TextButton buttonPlay = new TextButton("Start Game", textButtonStyle);
        buttonPlay.pad(20, 50, 20, 50);
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                sceneController.changeScreen(new GameplayScreen_examples(sceneController, new MovementController(camera),audioManager));
            }
        });

        // Create Settings Button
        TextButton buttonSettings = new TextButton("Settings", textButtonStyle);
        buttonSettings.pad(20, 50, 20, 50);
        buttonSettings.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                sceneController.changeScreen(new SettingScreen(sceneController, audioManager));
            }
        });

        // Create Quit Button
        TextButton buttonQuit = new TextButton("Quit", textButtonStyle);
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
        skin.dispose();
        atlas.dispose();
        backgroundTexture.dispose();
    }
}
