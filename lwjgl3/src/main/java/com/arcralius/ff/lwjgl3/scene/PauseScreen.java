package com.arcralius.ff.lwjgl3.scene;

import com.arcralius.ff.lwjgl3.input_output.IO_Controller;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

public class PauseScreen extends BaseScreen {
    private Stage stage;
    private ImageButton buttonToggleMute;
    private TextureRegionDrawable muteDrawable;
    private TextureRegionDrawable unmuteDrawable;
    private final SceneController sceneController;
    private final GameplayScreen gameplayScreen;
    private Label volumeLabel;
    private Slider volumeSlider;
    private float lastVolume;
    private Texture backgroundTexture;
    private Sprite backgroundSprite;

    //  factory field:
    private UIComponentFactory uiFactory;

    public PauseScreen(IO_Controller ioController, SceneController sceneController, GameplayScreen gameplayScreen) {
        super(ioController);
        this.sceneController = sceneController;
        this.gameplayScreen = gameplayScreen;
        this.lastVolume = ioController.getAudioManager().getVolume();
    }

    @Override
    public void show() {
        ioController.getDisplayManager().setCurrentScreen("PauseScreen");

        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture(Gdx.files.internal("Forest.png"));
        backgroundSprite = new Sprite(backgroundTexture);

        uiFactory = new UIComponentFactory("fonts/ChangaOneRegular.ttf");

        setupUI();

        // Scale the background sprite after it is created.
        float scaleX = viewport.getWorldWidth() / backgroundSprite.getWidth();
        float scaleY = viewport.getWorldHeight() / backgroundSprite.getHeight();
        float scale = Math.max(scaleX, scaleY);

        backgroundSprite.setSize(backgroundSprite.getWidth() * scale, backgroundSprite.getHeight() * scale);
    }

    private void setupUI() {
        // Create the volume label
        volumeLabel = uiFactory.createLabel(getVolumeText(), UIComponentFactory.FRENCH_BEIGE, UIComponentFactory.boxDrawable);

        // Play Button
        ImageButton buttonResume = uiFactory.createImageButton("play_icon.png");
        buttonResume.pad(20, 50, 20, 50);
        buttonResume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameplayScreen.setPaused(false);
                sceneController.changeScreen(gameplayScreen);
            }
        });

        // Mute/Unmute Button
        muteDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("SoundOff.png")));
        unmuteDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("SoundOn.png")));

        buttonToggleMute = new ImageButton(ioController.getAudioManager().isMuted() ? muteDrawable : unmuteDrawable);
        buttonToggleMute.pad(20, 50, 20, 50);
        buttonToggleMute.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleAudio();
            }
        });

        // Quit Button
        TextButton buttonQuit = uiFactory.createTextButton("Quit", UIComponentFactory.FRENCH_BEIGE, UIComponentFactory.boxDrawable);
        buttonQuit.pad(20, 50, 20, 50);
        buttonQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        // Slider Setup
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
        volumeSlider.setValue(ioController.getAudioManager().getVolume());
        volumeSlider.setWidth(200);
        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float sliderValue = volumeSlider.getValue();
                ioController.getAudioManager().setVolume(sliderValue);
                updateVolumeLabel();
                lastVolume = sliderValue;
            }
        });

        // Initial slider value based on mute state
        if (ioController.getAudioManager().isMuted()) {
            volumeSlider.setValue(0);
        } else {
            volumeSlider.setValue(ioController.getAudioManager().getVolume());
        }

        // Main UI Layout
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        // Top row (Play Button)
        table.add(buttonResume).center().padBottom(30);
        table.row();

        // Middle row (Mute Button & Volume Label)
        Table volumeTable = new Table();
        volumeTable.add(buttonToggleMute).left().padRight(30);
        volumeTable.add(volumeLabel).right();
        table.add(volumeTable).center().padBottom(10);
        table.row();

        // Volume Slider Row
        table.add(volumeSlider).width(200).center().padBottom(30);
        table.row();

        // Bottom row (Quit Button)
        table.add(buttonQuit).center().padBottom(10);

        // Add table to stage
        stage.addActor(table);
    }


    private void toggleAudio() {
        boolean isMuted = ioController.getAudioManager().isMuted();
        ioController.getAudioManager().toggleMute();
        buttonToggleMute.getStyle().imageUp = isMuted ? unmuteDrawable : muteDrawable;

        if (ioController.getAudioManager().isMuted()) {
            lastVolume = volumeSlider.getValue();
            volumeSlider.setValue(0);
        } else {
            volumeSlider.setValue(lastVolume);
        }

        volumeSlider.invalidateHierarchy();
        volumeSlider.layout();

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

        float scaleX = viewport.getWorldWidth() / backgroundSprite.getWidth();
        float scaleY = viewport.getWorldHeight() / backgroundSprite.getHeight();
        float scale = Math.max(scaleX, scaleY);

        backgroundSprite.setSize(backgroundSprite.getWidth() * scale, backgroundSprite.getHeight() * scale);

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
        uiFactory.dispose(); // Dispose the skin and font loaded by the factory.
        backgroundTexture.dispose();
    }
}
