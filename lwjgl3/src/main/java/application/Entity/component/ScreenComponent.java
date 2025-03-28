package application.Entity.component;

import engine.scene.BaseScreen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface ScreenComponent {
    void initialize(BaseScreen screen);
    void update(float delta);
    void render(SpriteBatch batch);
    void dispose();
}
