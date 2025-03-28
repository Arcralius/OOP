package application.scene;

import engine.input_output.IO_Controller;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.ScreenUtils;
import engine.scene.BaseScreen;
import engine.scene.SceneController;

public class SettingScreen extends BaseScreen {
    private Stage stage;
    private final SceneController sceneController;
    private Texture backgroundTexture;
    private Sprite backgroundSprite1, backgroundSprite2;
    private float backgroundX1 = 0, backgroundX2;
    private TextButton buttonToggleMute;
    private TextButton buttonVolumeUp;
    private TextButton buttonVolumeDown;
    private Label volumeLabel;
    private final float volumeStep = 0.1f;
    private UIComponentFactory uiFactory;

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
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture(Gdx.files.internal("menuBackground.png"));
        backgroundSprite1 = new Sprite(backgroundTexture);
        backgroundSprite2 = new Sprite(backgroundTexture);

        // Instantiate the UIComponentFactory with font paths.
        uiFactory = new UIComponentFactory("fonts/ChangaOneRegular.ttf");

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
        table.setFillParent(true);

        // Create the volume label using the factory.
        volumeLabel = uiFactory.createLabel(getVolumeText(), UIComponentFactory.FRENCH_BEIGE, UIComponentFactory.boxDrawable);
        volumeLabel.pack();

        // Create and configure the Toggle Mute button.
        buttonToggleMute = uiFactory.createTextButton(ioController.getAudioManager().isMuted() ? "Unmute Audio" : "Mute Audio", UIComponentFactory.FRENCH_BEIGE, UIComponentFactory.boxDrawable);
        buttonToggleMute.pad(20, 50, 20, 50);
        buttonToggleMute.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleAudio();
            }
        });

        // Create and configure the Volume Up button.
        buttonVolumeUp = uiFactory.createTextButton("Volume Up", UIComponentFactory.FRENCH_BEIGE, UIComponentFactory.boxDrawable);
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

        // Create and configure the Volume Down button.
        buttonVolumeDown = uiFactory.createTextButton("Volume Down", UIComponentFactory.FRENCH_BEIGE, UIComponentFactory.boxDrawable);
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

        // Create and configure the Back button.
        TextButton buttonBack = uiFactory.createTextButton("Back to Main Menu", UIComponentFactory.FRENCH_BEIGE, UIComponentFactory.boxDrawable);
        buttonBack.pad(20, 50, 20, 50);
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneController.changeScreen(new MainMenuScreen(ioController, sceneController));
            }
        });

        // Build layout tables.
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

        float scaleX = viewport.getWorldWidth() / backgroundSprite1.getWidth();
        float scaleY = viewport.getWorldHeight() / backgroundSprite1.getHeight();
        float scale = Math.max(scaleX, scaleY);

        backgroundSprite1.setSize(backgroundSprite1.getWidth() * scale, backgroundSprite1.getHeight() * scale);
        backgroundSprite2.setSize(backgroundSprite2.getWidth() * scale, backgroundSprite2.getHeight() * scale);

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

        float scaledWidth = backgroundSprite1.getWidth();

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
