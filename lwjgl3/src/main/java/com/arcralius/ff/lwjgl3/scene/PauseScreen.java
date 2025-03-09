package com.arcralius.ff.lwjgl3.scene;

import com.arcralius.ff.lwjgl3.input_output.IO_Controller;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.ScreenUtils;

public class PauseScreen extends BaseScreen {
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private final SceneController sceneController;
    private final GameplayScreen gameplayScreen;
    private TextButton buttonToggleMute;
    private Label volumeLabel;
    private Slider volumeSlider;

    private Texture backgroundTexture;
    private Sprite backgroundSprite;

    public PauseScreen(IO_Controller ioController, SceneController sceneController, GameplayScreen gameplayScreen) {
        super(ioController);
        this.sceneController = sceneController;
        this.gameplayScreen = gameplayScreen;
    }

    @Override
    public void show() {
        ioController.getDisplayManager().setCurrentScreen("PauseScreen");
        ioController.getDisplayManager().setResolution(Gdx.graphics.getWidth() + "x" + Gdx.graphics.getHeight());

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

        volumeLabel = new Label(getVolumeText(), new Label.LabelStyle(whiteFont, null));

        TextButton buttonResume = new TextButton("Resume Game", textButtonStyle);
        buttonResume.pad(20, 50, 20, 50);
        buttonResume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameplayScreen.setPaused(false);
                sceneController.changeScreen(gameplayScreen);
            }
        });

        buttonToggleMute = new TextButton(ioController.getAudioManager().isMuted() ? "Unmute Audio" : "Mute Audio", textButtonStyle);
        buttonToggleMute.pad(20, 50, 20, 50);
        buttonToggleMute.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleAudio();
            }
        });

        TextButton buttonQuit = new TextButton("Quit", textButtonStyle);
        buttonQuit.pad(20, 50, 20, 50);
        buttonQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Texture sliderTexture = new Texture(Gdx.files.internal("music/slide_horizontal_grey.png"));
        TextureRegion sliderTextureRegion = new TextureRegion(sliderTexture);
        TextureRegionDrawable sliderGreyDrawable = new TextureRegionDrawable(sliderTextureRegion);

        Texture slider2Texture = new Texture(Gdx.files.internal("music/slide_horizontal_color_section.png"));
        TextureRegion slider2TextureRegion = new TextureRegion(slider2Texture);
        TextureRegionDrawable sliderYellowDrawable = new TextureRegionDrawable(slider2TextureRegion);

        Slider.SliderStyle sliderStyle = new Slider.SliderStyle();
        sliderStyle.background = sliderGreyDrawable;
        sliderStyle.knob = sliderYellowDrawable;

        volumeSlider = new Slider(0, 1, 0.01f, false, sliderStyle);
        volumeSlider.setValue(ioController.getAudioManager().getVolume()); // Set the slider to the audio manager volume.
        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float sliderValue = volumeSlider.getValue();
                ioController.getAudioManager().setVolume(sliderValue); // Set the audio manager to the slider value.
                updateVolumeLabel();
            }
        });

        Table volumeLabelTable = new Table();
        volumeLabelTable.add(volumeLabel).center();

        Table sliderRow = new Table();
        sliderRow.add(volumeSlider).width(200);

        Table centerRow = new Table();
        centerRow.add(buttonResume).fillX().uniformX();
        centerRow.row().padTop(20);
        centerRow.add(buttonToggleMute).fillX().uniformX();

        Table quitRow = new Table();
        quitRow.add(buttonQuit).fillX().uniformX();

        table.add(centerRow).center().padTop(20);
        table.row().padTop(20);
        table.add(volumeLabelTable).center().padTop(20);
        table.row().padTop(10);
        table.add(sliderRow).center();
        table.row().padTop(20);
        table.add(quitRow).center().padBottom(20);

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
