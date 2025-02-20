package com.arcralius.ff.lwjgl3.scene;

import com.arcralius.ff.lwjgl3.input_output.IO_Controller;
import com.arcralius.ff.lwjgl3.movement.MovementController;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.List;

//abstract class used as base screen cannot be instantiated directly, it will be extended
public abstract class BaseScreen implements Screen {
    protected final IO_Controller ioController;

    // 🔹 Private attributes for encapsulation
    private Texture background; //store screen bg image
    private List<String> menuOptions; //store the list of menu options
    private int selectedOption; //track which menu option is selected
    private float deltaTime;
    protected OrthographicCamera camera;
    protected Viewport viewport;
    protected SpriteBatch batch;

    public BaseScreen(IO_Controller ioController) {
        this.ioController = ioController;
        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 600, camera);
        batch = new SpriteBatch();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Viewport getViewport() {
        return viewport;
    }


    // 🔹 Getters and Setters (Encapsulated Fields)
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

    //render the screen
    protected abstract void update(float delta);
    protected abstract void draw();

    @Override
    public void render(float delta) {
        this.deltaTime = delta;
        update(delta); //update game logic before rendering
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        draw(); //each subclass will define what to draw
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    @Override
    public void dispose() {
        if (batch != null) batch.dispose();
        if (background != null) background.dispose();
    }

    //to load assets when screen appears
    @Override
    public void show() {

    }

    //to pause game state
    @Override
    public void pause() {

    }

    //to continue from where player left off
    @Override
    public void resume() {

    }

    //to clean up when switching screens
    @Override
    public void hide() {

    }
}
