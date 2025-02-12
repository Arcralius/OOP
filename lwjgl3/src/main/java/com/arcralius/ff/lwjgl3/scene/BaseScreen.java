package com.arcralius.ff.lwjgl3.scene;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

import java.util.List;

public abstract class BaseScreen {
    protected Texture background;
    protected List<String> menuOptions;
    protected int selectedOption;
    protected SpriteBatch batch;

 public BaseScreen(){
     this.batch = new SpriteBatch();
 }

    public void render() {
        if (background != null) {
            batch.begin();
            batch.draw(background, 0, 0); // Draw texture at (0,0) full screen
            batch.end();
        }
    }

    public void dispose(){
        batch.dispose();
    }


    public void changeScreen(BaseScreen Newscreen) {

    }

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
}
