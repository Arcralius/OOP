package com.arcralius.ff.lwjgl3.scene;

import com.arcralius.ff.lwjgl3.input_output.AudioManager;
import com.arcralius.ff.lwjgl3.input_output.IO_Controller;
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

public class SettingScreen extends BaseScreen {
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private final SceneController sceneController;
    private final AudioManager audioManager;
    private final IO_Controller ioController;

    private Texture backgroundTexture;
    private Sprite backgroundSprite1, backgroundSprite2;
    private float backgroundX1 = 0, backgroundX2;
    private TextButton buttonToggleMute;

    public SettingScreen(IO_Controller ioController, SceneController sceneController, AudioManager audioManager) {
        this.sceneController = sceneController;
        this.audioManager = audioManager;
        this.ioController = ioController;
    }

    @Override
    public void show() {
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

        setupUI();
    }

    private void setupUI() {
        atlas = new TextureAtlas(Gdx.files.internal("button.atlas"));
        skin = new Skin(atlas);

        Table table = new Table();
        table.setFillParent(true);

        BitmapFont whiteFont = new BitmapFont(Gdx.files.internal("white.fnt"), false);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("menacing");
        textButtonStyle.over = skin.getDrawable("menacing2");
        textButtonStyle.font = whiteFont;

        // Mute/Unmute Button
        buttonToggleMute = new TextButton(audioManager.isMuted() ? "Unmute Audio" : "Mute Audio", textButtonStyle);
        buttonToggleMute.pad(20, 50, 20, 50);
        buttonToggleMute.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleAudio();
            }
        });

        // Back Button
        TextButton buttonBack = new TextButton("Back to Main Menu", textButtonStyle);
        buttonBack.pad(20, 50, 20, 50);
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneController.changeScreen(new MainMenuScreen(ioController, sceneController, audioManager));
            }
        });

        // Add buttons to the table
        table.row().pad(20);
        table.add(buttonToggleMute).fillX().uniformX();
        table.row().pad(20);
        table.add(buttonBack).fillX().uniformX();

        stage.addActor(table);
    }

    private void toggleAudio() {
        audioManager.toggleMute();
        buttonToggleMute.setText(audioManager.isMuted() ? "Unmute Audio" : "Mute Audio");
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
