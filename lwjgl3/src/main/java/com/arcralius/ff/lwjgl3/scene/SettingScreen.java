package com.arcralius.ff.lwjgl3.scene;

import com.arcralius.ff.lwjgl3.input_output.IO_Controller;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    private Texture backgroundTexture;
    private Sprite backgroundSprite1, backgroundSprite2;
    private float backgroundX1 = 0, backgroundX2;
    private TextButton buttonToggleMute;
    private TextButton buttonVolumeUp;
    private TextButton buttonVolumeDown;
    private Label volumeLabel;
    private final float volumeStep = 0.1f;

    public SettingScreen(IO_Controller ioController, SceneController sceneController) {
        super(ioController);
        this.sceneController = sceneController;

        // Update DisplayManager with the current screen
        ioController.getDisplayManager().setCurrentScreen("SettingScreen");

        // Set display resolution dynamically
        ioController.getDisplayManager().setResolution(ioController.getDisplayManager().getResolution());
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture(Gdx.files.internal("menuBackground.png"));
        backgroundSprite1 = new Sprite(backgroundTexture);
        backgroundSprite2 = new Sprite(backgroundTexture);
        backgroundSprite1.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backgroundSprite2.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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

        volumeLabel = new Label(getVolumeText(), new Label.LabelStyle(whiteFont, null));

        buttonToggleMute = new TextButton(ioController.getAudioManager().isMuted() ? "Unmute Audio" : "Mute Audio", textButtonStyle);
        buttonToggleMute.pad(20, 50, 20, 50);
        buttonToggleMute.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleAudio();
            }
        });

        buttonVolumeUp = new TextButton("Volume Up", textButtonStyle);
        buttonVolumeUp.pad(20);
        buttonVolumeUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                float currentVolume = ioController.getAudioManager().getVolume();
                float newVolume = Math.min(1.0f, currentVolume + volumeStep);
                ioController.getAudioManager().setVolume(newVolume);
                updateVolumeLabel();
            }
        });

        buttonVolumeDown = new TextButton("Volume Down", textButtonStyle);
        buttonVolumeDown.pad(20);
        buttonVolumeDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                float currentVolume = ioController.getAudioManager().getVolume();
                float newVolume = Math.max(0.0f, currentVolume - volumeStep);
                ioController.getAudioManager().setVolume(newVolume);
                updateVolumeLabel();
            }
        });

        TextButton buttonBack = new TextButton("Back to Main Menu", textButtonStyle);
        buttonBack.pad(20, 50, 20, 50);
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneController.changeScreen(new MainMenuScreen(ioController, sceneController));
            }
        });

        Table volumeLabelTable = new Table();
        volumeLabelTable.add(volumeLabel).center();

        Table volumeRow = new Table();
        volumeRow.add(buttonVolumeDown).padRight(20);
        volumeRow.add(buttonVolumeUp).padLeft(20);

        Table muteRow = new Table();
        muteRow.add(buttonToggleMute).fillX().uniformX();

        Table backRow = new Table();
        backRow.add(buttonBack).fillX().uniformX();

        table.add(muteRow).center().padTop(20).fillX().uniformX();
        table.row().padTop(20);
        table.add(volumeLabelTable).center().padTop(20);
        table.row().padTop(10);
        table.add(volumeRow).center().padTop(20);
        table.row().padTop(20);
        table.add(backRow).center().padBottom(20);

        stage.addActor(table);
    }

    private void toggleAudio() {
        ioController.getAudioManager().toggleMute();
        buttonToggleMute.setText(ioController.getAudioManager().isMuted() ? "Unmute Audio" : "Mute Audio");
        updateVolumeLabel();
    }

    private void updateVolumeLabel() {
        volumeLabel.setText(getVolumeText());
    }

    private String getVolumeText() {
        return "Volume: " + (ioController.getAudioManager().isMuted() ? "Muted" : String.format("%.1f", ioController.getAudioManager().getVolume()));
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

        float SCROLL_SPEED = 50f;
        backgroundX1 -= SCROLL_SPEED * delta;
        backgroundX2 -= SCROLL_SPEED * delta;

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
