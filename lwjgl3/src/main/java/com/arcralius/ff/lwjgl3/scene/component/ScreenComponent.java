package com.arcralius.ff.lwjgl3.scene.component;

import com.arcralius.ff.lwjgl3.scene.BaseScreen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface ScreenComponent {
    void initialize(BaseScreen screen);
    void update(float delta);
    void render(SpriteBatch batch);
    void dispose();
}
