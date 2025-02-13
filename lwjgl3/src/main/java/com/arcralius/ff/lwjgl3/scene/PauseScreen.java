package com.arcralius.ff.lwjgl3.scene;

import com.arcralius.ff.lwjgl3.BaseScreen;
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

public class PauseScreen extends BaseScreen {
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private final SceneController sceneController;
    private final GameplayScreen gameplayScreen; // Store reference to the current game state

    // Background texture
    private Texture backgroundTexture;
    private Sprite backgroundSprite1, backgroundSprite2;
    private float backgroundX1 = 0, backgroundX2;

    public PauseScreen(SceneController sceneController, GameplayScreen gameplayScreen) {
        this.sceneController = sceneController;
        this.gameplayScreen = gameplayScreen; // Store the gameplay screen reference
    }

    @Override
    public void show() {
        // Initialize UI handling
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load the background texture
        backgroundTexture = new Texture(Gdx.files.internal("menubackground.png"));

        // Initialize background sprites for scrolling effect
        backgroundSprite1 = new Sprite(backgroundTexture);
        backgroundSprite2 = new Sprite(backgroundTexture);
        backgroundSprite1.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backgroundSprite2.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Position second sprite for scrolling
        backgroundX2 = Gdx.graphics.getWidth();

        // Initialize UI elements
        setupUI();
    }

    private void setupUI() {
        atlas = new TextureAtlas(Gdx.files.internal("button.atlas"));
        skin = new Skin(atlas);

        Table table = new Table();
        table.setFillParent(true);

        BitmapFont whiteFont = new BitmapFont(Gdx.files.internal("white.fnt"), false);

        // Define button style with hover effect
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("menacing");
        textButtonStyle.over = skin.getDrawable("menacing2");
        textButtonStyle.font = whiteFont;

        // Resume Button
        TextButton buttonResume = new TextButton("Resume Game", textButtonStyle);
        buttonResume.pad(20, 50, 20, 50);
        buttonResume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneController.changeScreen(gameplayScreen); // Resume the game
            }
        });

        // Restart Button
        TextButton buttonRestart = new TextButton("Restart Game", textButtonStyle);
        buttonRestart.pad(20, 50, 20, 50);
        buttonRestart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneController.changeScreen(new GameplayScreen(sceneController)); // Restart game
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
        table.add(buttonResume).fillX().uniformX();
        table.row().pad(20);
        table.add(buttonRestart).fillX().uniformX();
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
