package engine.scene;

import application.scene.PauseScreen;
import engine.collision.CollisionController;
//import com.arcralius.ff.lwjgl3.entity.abstractFactory.ConcreteEntityFactory;
//import com.arcralius.ff.lwjgl3.entity.abstractFactory.IEntityFactory;
import engine.input_output.IO_Controller;
import engine.movement.MovementController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import engine.entity.BaseEntity;
import engine.entity.EntityController;
import engine.entity.NonPlayableEntity;

import java.util.ArrayList;
import java.util.List;

public class GameplayScreen extends BaseScreen {
    protected final TiledMap map;
    protected final OrthogonalTiledMapRenderer mapRenderer;
    //private final Texture backgroundTexture;
    //private final Sprite backgroundSprite;
    private final MovementController movementController;
    private final SceneController sceneController;
    private final List<BaseEntity> entityList;
    private final CollisionController collisionController;
    protected final EntityController entityController;

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
        //this.backgroundTexture = new Texture("plains.png");
        //this.backgroundSprite = new Sprite(backgroundTexture);
        //backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Removed playable entity initialization
        font = new BitmapFont();
        this.uiCamera = new OrthographicCamera();
        this.uiCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.uiBatch = new SpriteBatch();

        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        camera.update();
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
        this.collisionTimer = 1.85f;
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
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        // 1. Draw background
        batch.begin();
        //backgroundSprite.draw(batch);
        batch.end();

        // 2. Render the map
        mapRenderer.setView(camera);
        mapRenderer.render();

        // 3. Draw entities
        entityController.draw(batch);

        // 4. Draw collision message in world coordinates, relative to camera position
        batch.begin();
        if (!collisionMessage.isEmpty() && playableEntity != null) {
            float messageX = playableEntity.getX();
            float messageY = playableEntity.getY() + 50;

            font.draw(batch, collisionMessage, messageX, messageY);
        }
        batch.end();

        // 5. Draw UI elements in screen coordinates
        uiBatch.setProjectionMatrix(uiCamera.combined);
        uiBatch.begin();
        // No UI elements currently being drawn here, but this is where they would go.
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

        camera.setToOrtho(false, width, height);
        viewport.update(width, height, true);
        camera.update();
    }

    @Override
    public void dispose() {
        super.dispose();
        map.dispose();
        mapRenderer.dispose();
        //backgroundTexture.dispose();
        font.dispose();
        uiBatch.dispose();
    }
}
