package com.arcralius.ff.lwjgl3.scene;

import com.arcralius.ff.lwjgl3.input_output.AudioManager;
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
    private final GameplayScreen gameplayScreen;
    private final AudioManager audioManager;
    private TextButton buttonToggleMute;


    private Texture backgroundTexture;
    private Sprite backgroundSprite;

    public PauseScreen(SceneController sceneController, GameplayScreen gameplayScreen, AudioManager audioManager) {
        this.sceneController = sceneController;
        this.gameplayScreen = gameplayScreen;
        this.audioManager = audioManager;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture(Gdx.files.internal("gameplay_background.png"));
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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

        // Resume Button
        TextButton buttonResume = new TextButton("Resume Game", textButtonStyle);
        buttonResume.pad(20, 50, 20, 50);
        buttonResume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Resuming Game...");
                gameplayScreen.setPaused(false);  //unpause the game
                sceneController.changeScreen(gameplayScreen);
            }
        });

        // Mute/Unmute Button
        buttonToggleMute = new TextButton(audioManager.isMuted() ? "Unmute Audio" : "Mute Audio", textButtonStyle);
        buttonToggleMute.pad(20, 50, 20, 50);
        buttonToggleMute.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleAudio();
            }
        });

        // Quit Button
        TextButton buttonQuit = new TextButton("Quit", textButtonStyle);
        buttonQuit.pad(20, 50, 20, 50);
        buttonQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        table.row().pad(20);
        table.add(buttonResume).fillX().uniformX();
        table.row().pad(20);
        table.add(buttonToggleMute).fillX().uniformX();
        table.row().pad(20);
        table.add(buttonQuit).fillX().uniformX();

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
        batch.begin();
        backgroundSprite.setPosition(0, 0);
        backgroundSprite.draw(batch);
        batch.end();

        stage.draw();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
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
