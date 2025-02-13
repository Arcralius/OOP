package com.arcralius.ff.lwjgl3.scene;

import com.badlogic.gdx.Gdx;
import com.arcralius.ff.lwjgl3.BaseScreen;
import com.arcralius.ff.lwjgl3.movement.MovementController;
import com.arcralius.ff.lwjgl3.scene.SceneController;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GameplayScreen extends BaseScreen {
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final Sprite bucketSprite;
    private final Texture bucketTexture;
    private final Texture backgroundTexture;
    private final Sprite backgroundSprite;
    private final MovementController movementController;

    public GameplayScreen(SceneController sceneController, MovementController movementController) {
        super();
        this.movementController = movementController;
        this.map = new TmxMapLoader().load("background.tmx");
        this.mapRenderer = new OrthogonalTiledMapRenderer(map, batch);
        this.bucketTexture = new Texture("bucket.png");
        this.bucketSprite = new Sprite(bucketTexture);
        this.bucketSprite.setSize(16, 16);
        this.backgroundTexture = new Texture("plains.png");
        this.backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(bucketSprite.getX(), bucketSprite.getY(), 0);
    }

    @Override
    protected void update(float delta) {
        movementController.handleMovement(delta);
        camera.update();
    }

    @Override
    protected void draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        backgroundSprite.draw(batch);
        batch.end();
        mapRenderer.setView(camera);
        mapRenderer.render();
        batch.begin();
        bucketSprite.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        map.dispose();
        bucketTexture.dispose();
        backgroundTexture.dispose();
    }
}
