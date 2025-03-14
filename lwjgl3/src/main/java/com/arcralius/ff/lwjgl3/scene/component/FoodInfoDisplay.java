package com.arcralius.ff.lwjgl3.scene.component;

import com.arcralius.ff.lwjgl3.scene.BaseScreen;
import com.arcralius.ff.lwjgl3.scene.component.ScreenComponent;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

public class FoodInfoDisplay implements ScreenComponent {
    private String foodType;
    private String nutritionalInfo;
    private float displayTime;
    private float maxDisplayTime = 5.0f; // Display info for 5 seconds
    private BitmapFont font;
    private float x, y;
    private boolean isActive = false;
    private BaseScreen screen;

    public FoodInfoDisplay(BitmapFont font) {
        this.font = font;
    }

    @Override
    public void initialize(BaseScreen screen) {
        this.screen = screen;
    }

    public void showInfo(String foodType, String nutritionalInfo, float x, float y) {
        this.foodType = foodType;
        this.nutritionalInfo = nutritionalInfo;
        this.x = x;
        this.y = y;
        this.displayTime = 0;
        this.isActive = true;
    }

    @Override
    public void update(float delta) {
        if (isActive) {
            displayTime += delta;
            if (displayTime >= maxDisplayTime) {
                isActive = false;
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (isActive) {
            // Draw title with larger font
            float titleScale = 1.2f;
            font.getData().setScale(titleScale);
            font.draw(batch, foodType.toUpperCase(), x, y + 50, 200, Align.center, false);

            // Draw info with normal font
            font.getData().setScale(1.0f);
            font.draw(batch, nutritionalInfo, x, y + 30, 200, Align.center, true);
        }
    }

    @Override
    public void dispose() {
        // No need to dispose the font, as it's managed elsewhere
    }

    public boolean isActive() {
        return isActive;
    }
}
