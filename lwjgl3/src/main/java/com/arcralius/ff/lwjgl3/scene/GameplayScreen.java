package com.arcralius.ff.lwjgl3.scene;

import com.arcralius.ff.lwjgl3.entity.*;
import com.arcralius.ff.lwjgl3.collision.CollisionController;
//import com.arcralius.ff.lwjgl3.entity.abstractFactory.ConcreteEntityFactory;
//import com.arcralius.ff.lwjgl3.entity.abstractFactory.IEntityFactory;
import com.arcralius.ff.lwjgl3.input_output.IO_Controller;
import com.arcralius.ff.lwjgl3.movement.MovementController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.ArrayList;
import java.util.List;

public class GameplayScreen extends BaseScreen {
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final Texture backgroundTexture;
    private final Sprite backgroundSprite;
    private final MovementController movementController;
    private final SceneController sceneController;
    private final List<BaseEntity> entityList;
    private final CollisionController collisionController;
    protected final EntityController entityController;  //

    private BitmapFont font;
    private String collisionMessage = "";
    private float collisionTimer = 0;
    protected boolean isPaused = false;
    protected BaseEntity playableEntity; // Make this protected
    private OrthographicCamera uiCamera;
    private SpriteBatch uiBatch;

    public GameplayScreen(IO_Controller ioController, SceneController sceneController, MovementController movementController) {
        super(ioController);
        this.sceneController = sceneController;
        this.movementController = movementController;
        this.collisionController = new CollisionController(ioController, this, sceneController);

        entityList = new ArrayList<>();
        entityController = new EntityController(entityList);


        this.map = new TmxMapLoader().load("background.tmx");
        this.mapRenderer = new OrthogonalTiledMapRenderer(map, batch);
        this.backgroundTexture = new Texture("plains.png");
        this.backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Removed playable entity initialization
        font = new BitmapFont();
        this.uiCamera = new OrthographicCamera();
        this.uiCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.uiBatch = new SpriteBatch();
    }


    public boolean isPaused() {
        return isPaused;
    }

    public EntityController getEntityController() {
        return entityController;
    }

    public void setPaused(boolean paused) {
        this.isPaused = paused;
    }

    public void displayCollisionMessage(String message) {
        this.collisionMessage = message;
        this.collisionTimer = 2.0f;
    }

    private void handleInput() {
        if (ioController.getInputManager().isKeyJustReleased(Input.Keys.ESCAPE)) {
            isPaused = true;
            sceneController.changeScreen(new PauseScreen(ioController, sceneController, this));
        }
    }

    @Override
    protected void update(float delta) {
        playableEntity = entityController.getEntityById("player");

        if (isPaused) return;
        if (playableEntity != null) {
            movementController.handleMovement(playableEntity, delta);
            collisionController.checkCollisions(playableEntity, entityList);
        }
        ioController.update();
        handleInput();

        for (BaseEntity entity : entityList) {
            if (entity instanceof NonPlayableEntity) {
                movementController.handleNPCMovement((NonPlayableEntity) entity, delta);
            }
        }

        if (collisionTimer > 0) {
            collisionTimer -= delta;
            if (collisionTimer <= 0) {
                collisionMessage = "";
            }
        }

        camera.zoom = 0.60f;
        if (playableEntity != null) {
            camera.position.set(playableEntity.getX(), playableEntity.getY(), 0);
        }
        camera.update();
        uiCamera.update();
    }

    @Override
    protected void draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        backgroundSprite.draw(batch);
        batch.end();
        mapRenderer.setView(camera);
        mapRenderer.render();



        entityController.draw(batch);

        batch.begin();
        if (!collisionMessage.isEmpty()) {
            font.draw(batch, collisionMessage, playableEntity.getX(), playableEntity.getY() + 50);
        }
        batch.end();

        uiBatch.setProjectionMatrix(uiCamera.combined);
        uiBatch.begin();
        uiBatch.end();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        uiCamera.setToOrtho(false, width, height);
        uiCamera.update();
    }

    @Override
    public void dispose() {
        super.dispose();
        map.dispose();
        mapRenderer.dispose();
        backgroundTexture.dispose();
        font.dispose();
        uiBatch.dispose();
    }
}
