package com.arcralius.ff.lwjgl3.scene;

import com.arcralius.ff.lwjgl3.input_output.IO_Controller;
import com.arcralius.ff.lwjgl3.scene.component.ScreenComponent;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseScreen implements Screen {
    protected final IO_Controller ioController;
    protected final List<ScreenComponent> components = new ArrayList<>();

    // Existing fields
    private Texture background;
    private List<String> menuOptions;
    private int selectedOption;
    private float deltaTime;
    protected OrthographicCamera camera;
    protected Viewport viewport;
    protected SpriteBatch batch;

    public BaseScreen(IO_Controller ioController) {
        this.ioController = ioController;
        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 550, camera);
        batch = new SpriteBatch();
    }

    public void addComponent(ScreenComponent component) {
        components.add(component);
        component.initialize(this);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Viewport getViewport() {
        return viewport;
    }

    // Existing getters and setters
    public Texture getBackground() {
        return background;
    }

    public void setBackground(Texture background) {
        this.background = background;
    }

    public List<String> getMenuOptions() {
        return menuOptions;
    }

    public void setMenuOptions(List<String> menuOptions) {
        this.menuOptions = menuOptions;
    }

    public int getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(int selectedOption) {
        this.selectedOption = selectedOption;
    }

    public float getDeltaTime() {
        return deltaTime;
    }

    @Override
    public void render(float delta) {
        this.deltaTime = delta;

        for (ScreenComponent component : components) {
            component.update(delta);
        }
        update(delta);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Draw components
        batch.begin();
        drawBackground(batch);
        for (ScreenComponent component : components) {
            component.render(batch);
        }
        batch.end();

        // Draw screen-specific elements
        draw();
    }

    // Required abstract methods for screen-specific logic
    protected abstract void update(float delta);
    protected abstract void draw();

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);

        camera.viewportWidth = viewport.getWorldWidth();
        camera.viewportHeight = viewport.getWorldHeight();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    public void drawBackground(SpriteBatch batch) {
        if (background != null) {
            batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        }
    }

    @Override
    public void dispose() {
        // Dispose components
        for (ScreenComponent component : components) {
            component.dispose();
        }

        if (batch != null) batch.dispose();
        if (background != null) background.dispose();
    }

    @Override
    public void show() {
        // To be implemented by subclasses
    }

    @Override
    public void pause() {
        // To be implemented by subclasses
    }

    @Override
    public void resume() {
        // To be implemented by subclasses
    }

    @Override
    public void hide() {
        // To be implemented by subclasses
    }
}
